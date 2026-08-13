package com.agroatlautla.app.data.repository

import android.content.Context
import com.agroatlautla.app.data.local.AgroDatabase
import com.agroatlautla.app.data.local.CalendarActivityEntity
import com.agroatlautla.app.data.local.CropEntity
import com.agroatlautla.app.data.local.ExpenseEntity
import com.agroatlautla.app.data.local.PestEntity
import com.agroatlautla.app.data.local.SeedData
import com.agroatlautla.app.data.local.SyncQueueEntity
import kotlinx.coroutines.flow.Flow

class AgroRepository(
    context: Context,
    private val database: AgroDatabase
) {
    private val cloudRepository = CloudRepository(context)

    val crops: Flow<List<CropEntity>> = database.cropDao().observeAll()
    val activities: Flow<List<CalendarActivityEntity>> = database.calendarActivityDao().observeAll()
    val pests: Flow<List<PestEntity>> = database.pestDao().observeAll()
    val expenses: Flow<List<ExpenseEntity>> = database.expenseDao().observeAll()
    val pendingSync: Flow<List<SyncQueueEntity>> = database.syncQueueDao().observePending()

    suspend fun seed() {
        SeedData.ensureInitialData(database)
    }

    suspend fun addCrop(
        name: String,
        sowDate: String = "Sin fecha de siembra",
        irrigationType: String = "Temporal (lluvia)",
        notes: String = ""
    ) {
        val areaLabel = listOf(sowDate.ifBlank { "Sin fecha de siembra" }, "Sin superficie asignada")
            .joinToString(" - ")
        val riskLabel = if (notes.isBlank()) irrigationType else "$irrigationType - ${notes.take(28)}"
        val id = database.cropDao().insert(
            CropEntity(
                name = name.ifBlank { "Nuevo cultivo" },
                stage = "Registrado",
                areaLabel = areaLabel,
                riskLabel = riskLabel,
                icon = "leaf"
            )
        )
        database.syncQueueDao().insert(
            SyncQueueEntity(entityName = "crops", entityId = id.toString(), action = "create")
        )
    }

    suspend fun addActivity(title: String) {
        val id = database.calendarActivityDao().insert(
            CalendarActivityEntity(
                day = 10,
                month = "JUL",
                type = "Actividad",
                title = title.ifBlank { "Nueva actividad" },
                cropName = "General",
                colorTag = "green"
            )
        )
        database.syncQueueDao().insert(
            SyncQueueEntity(entityName = "calendar_activities", entityId = id.toString(), action = "create")
        )
    }

    suspend fun addExpense(concept: String, amount: Int, date: String, category: String) {
        val id = database.expenseDao().insert(
            ExpenseEntity(
                concept = concept.ifBlank { "Gasto sin concepto" },
                amount = amount,
                date = date.ifBlank { "Sin fecha" },
                category = category.ifBlank { "Semillas" }
            )
        )
        database.syncQueueDao().insert(
            SyncQueueEntity(entityName = "expenses", entityId = id.toString(), action = "create")
        )
    }

    suspend fun refreshFromCloud(uid: String): Result<Unit> = runCatching {
        if (!cloudRepository.isConfigured()) error(FirebaseConfig.MissingConfigMessage)

        cloudRepository.downloadCrops(uid).forEach { database.cropDao().insert(it) }
        cloudRepository.downloadActivities(uid).forEach { database.calendarActivityDao().insert(it) }
        cloudRepository.downloadPests(uid).forEach { database.pestDao().insert(it) }
        cloudRepository.downloadExpenses(uid).forEach { database.expenseDao().insert(it) }
    }

    suspend fun syncPendingToCloud(uid: String?): Result<Int> = runCatching {
        if (uid.isNullOrBlank()) error("Inicia sesion para sincronizar.")
        if (!cloudRepository.isConfigured()) error(FirebaseConfig.MissingConfigMessage)

        val pending = database.syncQueueDao().getPending()
        pending.forEach { item ->
            when (item.entityName) {
                "crops" -> database.cropDao().getById(item.entityId.toInt())?.let {
                    cloudRepository.uploadCrop(uid, it)
                }
                "calendar_activities" -> database.calendarActivityDao().getById(item.entityId.toInt())?.let {
                    cloudRepository.uploadActivity(uid, it)
                }
                "pests" -> database.pestDao().getById(item.entityId.toInt())?.let {
                    cloudRepository.uploadPest(uid, it)
                }
                "expenses" -> database.expenseDao().getById(item.entityId.toInt())?.let {
                    cloudRepository.uploadExpense(uid, it)
                }
            }
            database.syncQueueDao().markSynced(item.id, System.currentTimeMillis())
        }
        pending.size
    }

    suspend fun pendingCount(): Int = database.syncQueueDao().pendingCount()
}
