package com.agroatlautla.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.agroatlautla.app.data.local.CalendarActivityEntity
import com.agroatlautla.app.data.local.CropEntity
import com.agroatlautla.app.data.local.ExpenseEntity
import com.agroatlautla.app.data.local.PestEntity
import com.agroatlautla.app.ui.AgroViewModel
import com.agroatlautla.app.ui.theme.AgroBackground
import com.agroatlautla.app.ui.theme.AgroDanger
import com.agroatlautla.app.ui.theme.AgroGreen
import com.agroatlautla.app.ui.theme.AgroGreenDark
import com.agroatlautla.app.ui.theme.AgroGreenSoft
import com.agroatlautla.app.ui.theme.AgroInfo
import com.agroatlautla.app.ui.theme.AgroMuted
import com.agroatlautla.app.ui.theme.AgroText
import com.agroatlautla.app.ui.theme.AgroWarning
import com.agroatlautla.app.ui.theme.AgroYellow

private val AgroBrown = Color(0xFF6D4C41)

@Composable
fun SplashScreen(onStart: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AgroGreenDark)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier.size(128.dp),
                color = Color(0xFF69C98B),
                shape = CircleShape
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text("A", color = Color.White, fontSize = 48.sp, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(Modifier.height(20.dp))
            Text("AgroAtlautla", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(10.dp))
            Text("Apoyo digital para productores del campo", color = Color.White.copy(alpha = 0.75f))
            Spacer(Modifier.height(36.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MiniRoundIcon("C", AgroGreen)
                MiniRoundIcon("R", AgroGreen)
                MiniRoundIcon("P", AgroDanger)
                MiniRoundIcon("G", AgroGreen)
            }
        }

        Button(
            onClick = onStart,
            colors = ButtonDefaults.buttonColors(containerColor = AgroYellow, contentColor = AgroText),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Iniciar", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun LoginScreen(
    viewModel: AgroViewModel,
    onLogin: () -> Unit,
    onRegister: () -> Unit,
    onRecover: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    AuthLayout(title = "Bienvenido", subtitle = "Inicia sesion para continuar") {
        MessageBanner(viewModel.message)
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contrasena") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(18.dp))
        Button(
            onClick = { viewModel.login(email, password, onLogin) },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AgroGreen),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Entrar")
        }
        Spacer(Modifier.height(8.dp))
        TextButton(onClick = onRecover) { Text("Recuperar cuenta") }
        TextButton(onClick = onRegister) { Text("Crear cuenta nueva") }
    }
}

@Composable
fun RegisterScreen(viewModel: AgroViewModel, onBack: () -> Unit, onRegistered: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    AuthLayout(title = "Crear cuenta", subtitle = "Registro de productor", onBack = onBack) {
        MessageBanner(viewModel.message)
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre completo") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Correo") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contrasena") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(18.dp))
        Button(
            onClick = { viewModel.register(name, email, password, onRegistered) },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AgroGreen),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Registrarme")
        }
    }
}

@Composable
fun RecoveryScreen(viewModel: AgroViewModel, onBack: () -> Unit) {
    var email by remember { mutableStateOf("") }

    AuthLayout(title = "Recuperar cuenta", subtitle = "Ingresa tu correo", onBack = onBack) {
        MessageBanner(viewModel.message)
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(18.dp))
        Button(
            onClick = { viewModel.recover(email) },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AgroGreen),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Solicitar recuperacion")
        }
    }
}

@Composable
fun DashboardScreen(viewModel: AgroViewModel, onNavigate: (String) -> Unit) {
    val crops by viewModel.crops.collectAsState()
    val activities by viewModel.activities.collectAsState()
    val pests by viewModel.pests.collectAsState()

    LaunchedEffect(Unit) { viewModel.refreshNetworkStatus() }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(AgroBackground)
    ) {
        item {
            GreenHeader {
                Text("Buenos dias,", color = Color.White.copy(alpha = 0.75f), fontSize = 13.sp)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            viewModel.currentUser?.fullName ?: "Productor",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text("Atlautla, Estado de Mexico", color = Color.White.copy(alpha = 0.8f), fontSize = 13.sp)
                    }
                    ConnectionPill(viewModel.isOnline)
                }
            }
            ContentSection(title = "ACCESO RAPIDO") {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    QuickAccessCard(
                        title = "Mis Cultivos",
                        subtitle = "${crops.size} cultivos activos",
                        icon = "C",
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate("crops") }
                    )
                    QuickAccessCard(
                        title = "Calendario Agricola",
                        subtitle = "${activities.size} actividades esta semana",
                        icon = "F",
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate("calendar") }
                    )
                }
                Spacer(Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    QuickAccessCard(
                        title = "Plagas",
                        subtitle = "${pests.size} plagas registradas",
                        icon = "P",
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate("pests") }
                    )
                    QuickAccessCard(
                        title = "Gastos",
                        subtitle = "Total: \$3,450",
                        icon = "G",
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate("expenses") }
                    )
                }
                Spacer(Modifier.height(12.dp))
                WideReportCard(onClick = { onNavigate("reports") })
            }
            ContentSection(title = "RESUMEN DE HOY") {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    StatCard(crops.size.toString(), "Cultivos", AgroGreen, Modifier.weight(1f))
                    StatCard("\$3,450", "Gastos", AgroWarning, Modifier.weight(1f))
                    StatCard(activities.size.toString(), "Actividades", AgroInfo, Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun CropsScreen(
    viewModel: AgroViewModel,
    onAddCrop: () -> Unit,
    onCropSelected: (Int) -> Unit
) {
    val crops by viewModel.crops.collectAsState()

    ScreenWithHeader(title = "Mis Cultivos") {
        item {
            SummaryStrip(
                first = "${crops.size}\nCultivos",
                second = "4.8 ha\nTotal",
                third = "3\nPendientes"
            )
        }
        items(crops) { crop -> CropCard(crop, onClick = { onCropSelected(crop.id) }) }
        item {
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = onAddCrop,
                modifier = Modifier.fillMaxWidth().height(54.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AgroGreen),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("+", fontWeight = FontWeight.Bold)
                Spacer(Modifier.width(8.dp))
                Text("Agregar cultivo")
            }
        }
    }
}

@Composable
fun AddCropScreen(viewModel: AgroViewModel, onBack: () -> Unit, onSaved: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var sowDate by remember { mutableStateOf("") }
    var irrigation by remember { mutableStateOf(irrigationTypes.first()) }
    var notes by remember { mutableStateOf("") }
    val canSave = name.isNotBlank() && sowDate.isNotBlank()

    ScreenWithHeader(title = "Agregar Cultivo", onBack = onBack) {
        item {
            itemCard {
                Column {
                    MessageBanner(viewModel.message)
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Nombre del cultivo *") },
                        placeholder = { Text("Ej: Maiz, Frijol, Avena") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(
                        value = sowDate,
                        onValueChange = { sowDate = it },
                        label = { Text("Fecha de siembra *") },
                        placeholder = { Text("Ej: 15 Mar 2026") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(12.dp))
                    Text("Tipo de riego", color = AgroMuted, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                        irrigationTypes.take(3).forEach { type ->
                            FilterChip(
                                selected = irrigation == type,
                                onClick = { irrigation = type },
                                label = { Text(type, fontSize = 11.sp) }
                            )
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        label = { Text("Notas adicionales") },
                        placeholder = { Text("Observaciones del terreno") },
                        minLines = 3,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
        item {
            Button(
                onClick = {
                    viewModel.addCrop(name, sowDate, irrigation, notes)
                    onSaved()
                },
                enabled = canSave,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp).height(54.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AgroGreen),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Guardar cultivo", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun CropDetailScreen(viewModel: AgroViewModel, cropId: Int, onBack: () -> Unit) {
    val crops by viewModel.crops.collectAsState()
    val crop = crops.firstOrNull { it.id == cropId }

    ScreenWithHeader(title = "Detalle del Cultivo", onBack = onBack) {
        if (crop == null) {
            item { itemCard { Text("Cultivo no encontrado", color = AgroMuted) } }
        } else {
            item {
                GreenHeader {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        MiniRoundIcon(cropIconText(crop), Color.White)
                        Spacer(Modifier.width(14.dp))
                        Column {
                            Text(crop.name, color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold)
                            Text(crop.stage, color = Color.White.copy(alpha = 0.8f), fontSize = 13.sp)
                        }
                    }
                }
            }
            item {
                itemCard {
                    Column {
                        Text("INFORMACION DEL CULTIVO", color = AgroMuted, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(10.dp))
                        InfoRow("Fecha de siembra", crop.areaLabel.substringBefore(" - ", crop.areaLabel))
                        InfoRow("Superficie", crop.areaLabel.substringAfter(" - ", "Sin superficie asignada"))
                        InfoRow("Proxima actividad", crop.riskLabel)
                        InfoRow("Tipo de riego", irrigationFromCrop(crop))
                        InfoRow("Estado actual", crop.stage)
                    }
                }
            }
            item {
                itemCard {
                    Column {
                        Text("NOTAS DEL AGRICULTOR", color = AgroMuted, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "Terreno preparado y condiciones del suelo favorables. Revisar humedad y actividad de plagas durante la semana.",
                            color = AgroMuted,
                            fontSize = 13.sp,
                            lineHeight = 19.sp
                        )
                    }
                }
            }
            item {
                itemCard {
                    Column {
                        Text("HISTORIAL DE ACTIVIDADES", color = AgroMuted, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(10.dp))
                        TimelineRow("15 Mar", "Siembra realizada", true)
                        TimelineRow("1 Abr", "Primer riego", true)
                        TimelineRow("20 Abr", "Fertilizacion NPK", true)
                        TimelineRow("28 Jun", "Riego programado", false)
                    }
                }
            }
        }
    }
}

@Composable
fun CalendarScreen(viewModel: AgroViewModel) {
    val activities by viewModel.activities.collectAsState()
    var filter by remember { mutableStateOf("Todos") }
    val filters = listOf("Todos", "Siembra", "Riego", "Fertilizacion", "Cosecha")
    val visibleActivities = if (filter == "Todos") {
        activities
    } else {
        activities.filter { it.type == filter }
    }

    ScreenWithHeader(title = "Calendario Agricola") {
        item {
            MonthSelector()
            Spacer(Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                filters.take(3).forEach { item ->
                    FilterChip(
                        selected = filter == item,
                        onClick = { filter = item },
                        label = { Text(item) }
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                filters.drop(3).forEach { item ->
                    FilterChip(
                        selected = filter == item,
                        onClick = { filter = item },
                        label = { Text(item) }
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
        }
        items(visibleActivities) { activity -> ActivityCard(activity) }
        item {
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = { viewModel.addActivity("Nueva actividad") },
                modifier = Modifier.fillMaxWidth().height(54.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AgroGreen),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("+", fontWeight = FontWeight.Bold)
                Spacer(Modifier.width(8.dp))
                Text("Agregar actividad")
            }
        }
    }
}

@Composable
fun PestsScreen(viewModel: AgroViewModel, onPestSelected: (Int) -> Unit) {
    val pests by viewModel.pests.collectAsState()

    ScreenWithHeader(title = "Catalogo de Plagas") {
        item {
            AlertBanner("${pests.count { it.severity == "Alta" }} plagas de riesgo alto identificadas en la region")
            Spacer(Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                SeverityPill("Alta", AgroDanger)
                SeverityPill("Media", AgroWarning)
                SeverityPill("Baja", AgroGreen)
            }
            Spacer(Modifier.height(12.dp))
        }
        items(pests) { pest -> PestCard(pest, onClick = { onPestSelected(pest.id) }) }
    }
}

@Composable
fun PestDetailScreen(viewModel: AgroViewModel, pestId: Int, onBack: () -> Unit) {
    val pests by viewModel.pests.collectAsState()
    val pest = pests.firstOrNull { it.id == pestId }
    var saved by remember { mutableStateOf(false) }

    ScreenWithHeader(title = "Detalle de Plaga", onBack = onBack, headerColor = AgroDanger) {
        if (pest == null) {
            item { itemCard { Text("Plaga no encontrada", color = AgroMuted) } }
        } else {
            val knowledge = pestKnowledge(pest)
            item {
                itemCard {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        MiniRoundIcon("P", severityColor(pest.severity))
                        Spacer(Modifier.width(12.dp))
                        Column(Modifier.weight(1f)) {
                            Text(pest.name, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                            Text("Afecta: ${pest.affectedCrop}", color = AgroMuted, fontSize = 12.sp)
                        }
                        SeverityPill(pest.severity, severityColor(pest.severity))
                    }
                }
            }
            item { PestInfoBlock("SINTOMAS", knowledge.symptoms, AgroDanger) }
            item { PestInfoBlock("RECOMENDACIONES DE CONTROL", knowledge.recommendations, AgroWarning) }
            item { PestInfoBlock("PREVENCION", knowledge.prevention, AgroGreen) }
            if (saved) {
                item { AlertBanner("Informacion guardada sin conexion") }
            }
            item {
                Button(
                    onClick = { saved = true },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp).height(54.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AgroDanger),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text(if (saved) "Guardado sin conexion" else "Guardar informacion sin conexion")
                }
            }
        }
    }
}

@Composable
fun ExpensesScreen(viewModel: AgroViewModel, onBack: () -> Unit, onAddExpense: () -> Unit) {
    val expenses by viewModel.expenses.collectAsState()
    val total = expenses.sumOf { it.amount }
    val byCategory = expenses.groupBy { it.category }.mapValues { entry -> entry.value.sumOf { it.amount } }

    ScreenWithHeader(title = "Gastos", onBack = onBack, headerColor = AgroBrown) {
        item {
            itemCard {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Text("Total de gastos", color = AgroMuted, fontSize = 13.sp)
                    Text("${'$'}$total", color = AgroBrown, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                    Text("${expenses.size} gastos registrados", color = AgroMuted, fontSize = 12.sp)
                }
            }
        }
        item {
            ContentSection(title = "GASTOS POR CATEGORIA") {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    byCategory.entries.take(3).forEach { (category, amount) ->
                        CategoryChip(category, amount, Modifier.weight(1f))
                    }
                }
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    byCategory.entries.drop(3).forEach { (category, amount) ->
                        CategoryChip(category, amount, Modifier.weight(1f))
                    }
                }
            }
        }
        item {
            ContentSection(title = "HISTORIAL DE GASTOS") {}
        }
        items(expenses) { expense -> ExpenseCard(expense) }
        item {
            Button(
                onClick = onAddExpense,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp).height(54.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AgroBrown),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Registrar gasto", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun AddExpenseScreen(viewModel: AgroViewModel, onBack: () -> Unit, onSaved: () -> Unit) {
    var concept by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var category by remember { mutableStateOf(expenseCategories.first()) }
    val parsedAmount = amount.toIntOrNull()
    val canSave = concept.isNotBlank() && parsedAmount != null && date.isNotBlank()

    ScreenWithHeader(title = "Registrar Gasto", onBack = onBack, headerColor = AgroBrown) {
        item {
            itemCard {
                Column {
                    OutlinedTextField(
                        value = concept,
                        onValueChange = { concept = it },
                        label = { Text("Concepto *") },
                        placeholder = { Text("Ej: Fertilizante DAP") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(
                        value = amount,
                        onValueChange = { amount = it },
                        label = { Text("Cantidad ($) *") },
                        placeholder = { Text("0.00") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(
                        value = date,
                        onValueChange = { date = it },
                        label = { Text("Fecha *") },
                        placeholder = { Text("Ej: 10 Mar 2026") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(12.dp))
                    Text("Categoria", color = AgroMuted, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                        expenseCategories.take(3).forEach { item ->
                            FilterChip(
                                selected = category == item,
                                onClick = { category = item },
                                label = { Text(item, fontSize = 11.sp) }
                            )
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                        expenseCategories.drop(3).forEach { item ->
                            FilterChip(
                                selected = category == item,
                                onClick = { category = item },
                                label = { Text(item, fontSize = 11.sp) }
                            )
                        }
                    }
                }
            }
        }
        item {
            Button(
                onClick = {
                    viewModel.addExpense(concept, parsedAmount ?: 0, date, category)
                    onSaved()
                },
                enabled = canSave,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp).height(54.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AgroBrown),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Guardar gasto", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun ReportsScreen(viewModel: AgroViewModel, onBack: () -> Unit) {
    val expenses by viewModel.expenses.collectAsState()
    val total = expenses.sumOf { it.amount }
    val byCategory = expenses.groupBy { it.category }.mapValues { entry -> entry.value.sumOf { it.amount } }
    val maxCategory = (byCategory.values.maxOrNull() ?: 1).coerceAtLeast(1)

    ScreenWithHeader(title = "Reportes", onBack = onBack, headerColor = AgroInfo) {
        item {
            Text(
                "Temporada agricola 2026 - Atlautla",
                color = AgroMuted,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                fontSize = 13.sp
            )
        }
        item {
            Row(Modifier.padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                ReportKpiCard("4", "Total cultivos", "activos", AgroGreen, Modifier.weight(1f))
                ReportKpiCard("${'$'}$total", "Gastos", "temporada 2026", AgroDanger, Modifier.weight(1f))
            }
        }
        item {
            Spacer(Modifier.height(10.dp))
            Row(Modifier.padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                ReportKpiCard("3", "Actividades", "proxima semana", AgroWarning, Modifier.weight(1f))
                ReportKpiCard("12 ton", "Prod. estimada", "en total", AgroInfo, Modifier.weight(1f))
            }
        }
        item {
            itemCard {
                Column {
                    Text("GASTOS POR CATEGORIA", color = AgroMuted, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(12.dp))
                    ProgressLine("Semillas", byCategory["Semillas"] ?: 0, maxCategory, AgroGreen)
                    ProgressLine("Fertilizante", byCategory["Fertilizante"] ?: 0, maxCategory, AgroWarning)
                    ProgressLine("Transporte", byCategory["Transporte"] ?: 0, maxCategory, AgroInfo)
                    ProgressLine("Mano de obra", byCategory["Mano de obra"] ?: 0, maxCategory, AgroBrown)
                    ProgressLine("Herramientas", byCategory["Herramientas"] ?: 0, maxCategory, AgroDanger)
                }
            }
        }
        item {
            itemCard {
                Column {
                    Text("DISTRIBUCION DE CULTIVOS", color = AgroMuted, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(12.dp))
                    DistributionRow("Maiz", "45%", AgroGreen)
                    DistributionRow("Frijol", "25%", AgroWarning)
                    DistributionRow("Avena", "20%", AgroInfo)
                    DistributionRow("Hortalizas", "10%", Color(0xFF7B1FA2))
                }
            }
        }
        item {
            itemCard {
                Column {
                    Text("ESTIMADOS DE TEMPORADA", color = AgroMuted, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(12.dp))
                    ProgressLine("Produccion estimada", 72, 100, AgroGreen, "12 ton")
                    ProgressLine("Ganancia aproximada", 60, 100, AgroInfo, "${'$'}28,000")
                    ProgressLine("Costo de produccion", 35, 100, AgroDanger, "${'$'}$total")
                }
            }
        }
    }
}

@Composable
fun ProfileScreen(viewModel: AgroViewModel, onLogout: () -> Unit) {
    val pendingSync by viewModel.pendingSync.collectAsState()
    val user = viewModel.currentUser

    ScreenWithHeader(title = "Perfil") {
        item {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 16.dp)) {
                Surface(shape = CircleShape, color = AgroGreenSoft, modifier = Modifier.size(64.dp)) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("U", color = AgroGreen, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(Modifier.width(14.dp))
                Column {
                    Text(user?.fullName ?: "Usuario", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Text(user?.location ?: "Atlautla, Estado de Mexico", color = Color.White.copy(alpha = 0.85f), fontSize = 13.sp)
                    Text("Productor desde 2018 - 4.8 ha", color = Color.White.copy(alpha = 0.75f), fontSize = 12.sp)
                }
            }
        }
        item {
            ProfileCard(title = "CONECTIVIDAD") {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    MiniRoundIcon("W", AgroGreen)
                    Spacer(Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(if (viewModel.isOnline) "En linea" else "Sin internet", fontWeight = FontWeight.Bold)
                        Text(if (viewModel.isOnline) "Conexion activa" else "Cambios se guardan localmente", color = AgroMuted, fontSize = 12.sp)
                    }
                    Switch(checked = viewModel.isOnline, onCheckedChange = null)
                }
                Spacer(Modifier.height(12.dp))
                OutlinedButton(onClick = { viewModel.syncNow() }, modifier = Modifier.fillMaxWidth()) {
                    Text("S", fontWeight = FontWeight.Bold)
                    Spacer(Modifier.width(8.dp))
                    Text("Sincronizar datos")
                }
                Spacer(Modifier.height(8.dp))
                Text("${pendingSync.size} cambios pendientes", color = AgroGreen, fontSize = 12.sp)
                MessageBanner(viewModel.message)
            }
            ProfileCard(title = "INFORMACION DEL PRODUCTOR") {
                InfoRow("Nombre completo", user?.fullName ?: "Sin registrar")
                InfoRow("Correo", user?.email ?: "Sin correo")
                InfoRow("Zona", user?.location ?: "Atlautla")
            }
            ProfileCard(title = "NOTIFICACIONES") {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("N", color = AgroWarning, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.width(12.dp))
                    Text("Alertas de riego, plagas y actividades")
                    Spacer(Modifier.weight(1f))
                    Text(">", color = AgroMuted)
                }
            }
            OutlinedButton(
                onClick = {
                    viewModel.logout()
                    onLogout()
                },
                modifier = Modifier.fillMaxWidth().height(54.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = AgroDanger),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("X", fontWeight = FontWeight.Bold)
                Spacer(Modifier.width(8.dp))
                Text("Cerrar sesion")
            }
        }
    }
}

@Composable
private fun AuthLayout(
    title: String,
    subtitle: String,
    onBack: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AgroBackground)
            .padding(22.dp),
        verticalArrangement = Arrangement.Center
    ) {
        if (onBack != null) {
            IconButton(onClick = onBack) { Text("<", fontWeight = FontWeight.Bold) }
        }
        Text(title, fontSize = 30.sp, fontWeight = FontWeight.Bold, color = AgroGreenDark)
        Text(subtitle, color = AgroMuted)
        Spacer(Modifier.height(22.dp))
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(18.dp), content = content)
        }
    }
}

@Composable
private fun ScreenWithHeader(
    title: String,
    onBack: (() -> Unit)? = null,
    headerColor: Color = AgroGreenDark,
    content: androidx.compose.foundation.lazy.LazyListScope.() -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(AgroBackground)
    ) {
        item {
            GreenHeader(color = headerColor) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val backModifier = if (onBack == null) Modifier else Modifier.clickable(onClick = onBack)
                    Surface(shape = CircleShape, color = Color.White.copy(alpha = 0.15f), modifier = backModifier) {
                        Text("<", color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
                    }
                    Spacer(Modifier.width(10.dp))
                    Text(title, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
        content()
    }
}

@Composable
private fun GreenHeader(color: Color = AgroGreenDark, content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color)
            .padding(20.dp),
        content = content
    )
}

@Composable
private fun ContentSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column(Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
        Text(title, color = AgroMuted, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        content()
    }
}

@Composable
private fun QuickAccessCard(
    title: String,
    subtitle: String,
    icon: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.height(118.dp).clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(Modifier.padding(14.dp)) {
            MiniRoundIcon(icon, AgroGreen)
            Spacer(Modifier.height(10.dp))
            Text(title, fontWeight = FontWeight.Bold, fontSize = 13.sp, maxLines = 2)
            Text(subtitle, color = AgroMuted, fontSize = 11.sp, maxLines = 2)
        }
    }
}

@Composable
private fun WideReportCard(onClick: () -> Unit) {
    Card(
        modifier = Modifier.clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(14.dp)
    ) {
        Row(Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            MiniRoundIcon("R", AgroInfo)
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text("Reportes", fontWeight = FontWeight.Bold)
                Text("Ver resumen de temporada", color = AgroMuted, fontSize = 12.sp)
            }
            Text("R", color = AgroInfo, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun StatCard(value: String, label: String, color: Color, modifier: Modifier = Modifier) {
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(14.dp)) {
        Column(Modifier.padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(value, color = color, fontWeight = FontWeight.Bold)
            Text(label, color = AgroMuted, fontSize = 11.sp)
        }
    }
}

@Composable
private fun SummaryStrip(first: String, second: String, third: String) {
    itemCard {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            SummaryBox(first, Modifier.weight(1f))
            SummaryBox(second, Modifier.weight(1f))
            SummaryBox(third, Modifier.weight(1f))
        }
    }
}

@Composable
private fun SummaryBox(text: String, modifier: Modifier = Modifier) {
    Surface(modifier = modifier, color = AgroGreen, shape = RoundedCornerShape(12.dp)) {
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(12.dp),
            lineHeight = 18.sp
        )
    }
}

@Composable
private fun CropCard(crop: CropEntity, onClick: () -> Unit) {
    itemCard(modifier = Modifier.clickable(onClick = onClick)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            MiniRoundIcon(cropIconText(crop), AgroGreen)
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(crop.name, fontWeight = FontWeight.Bold, fontSize = 17.sp)
                Text(crop.areaLabel, color = AgroMuted, fontSize = 12.sp)
                Spacer(Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    AssistChip(onClick = {}, label = { Text(crop.stage, fontSize = 11.sp) })
                    Text(crop.riskLabel, color = AgroMuted, fontSize = 11.sp, modifier = Modifier.align(Alignment.CenterVertically))
                }
            }
            Text(">", color = AgroMuted)
        }
    }
}

@Composable
private fun MonthSelector() {
    Surface(color = AgroGreen, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
        Row(Modifier.padding(14.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("<", color = Color.White, fontWeight = FontWeight.Bold)
            Text("Junio 2026", color = Color.White, fontWeight = FontWeight.Bold)
            Text(">", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun ActivityCard(activity: CalendarActivityEntity) {
    itemCard {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(color = tagColor(activity.colorTag).copy(alpha = 0.15f), shape = RoundedCornerShape(12.dp), modifier = Modifier.size(64.dp)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Text(activity.day.toString(), color = tagColor(activity.colorTag), fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Text(activity.month, color = AgroMuted, fontSize = 11.sp)
                }
            }
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                AssistChip(onClick = {}, label = { Text(activity.type, fontSize = 11.sp) })
                Text(activity.title, fontWeight = FontWeight.Bold)
                Text("Cultivo: ${activity.cropName}", color = AgroMuted, fontSize = 12.sp)
            }
        }
    }
}

@Composable
private fun PestCard(pest: PestEntity, onClick: () -> Unit) {
    itemCard(modifier = Modifier.clickable(onClick = onClick)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            MiniRoundIcon("P", severityColor(pest.severity))
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(pest.name, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    SeverityPill(pest.severity, severityColor(pest.severity))
                }
                Text("Cultivo: ${pest.affectedCrop}", color = AgroGreen, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                Text(pest.description, color = AgroMuted, fontSize = 12.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)
            }
            Text(">", color = AgroMuted)
        }
    }
}

@Composable
private fun ProfileCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(title, color = AgroMuted, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(10.dp))
            content()
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(Modifier.fillMaxWidth().padding(vertical = 6.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = AgroMuted, fontSize = 12.sp)
        Text(value, fontWeight = FontWeight.Bold, fontSize = 12.sp)
    }
}

@Composable
private fun ConnectionPill(isOnline: Boolean) {
    Surface(color = AgroGreenSoft, shape = RoundedCornerShape(18.dp)) {
        Row(Modifier.padding(horizontal = 10.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("W", color = AgroGreen, fontWeight = FontWeight.Bold, fontSize = 11.sp)
            Spacer(Modifier.width(4.dp))
            Text(if (isOnline) "En linea" else "Offline", color = AgroGreen, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }
    }
}

@Composable
private fun AlertBanner(text: String) {
    Surface(color = Color(0xFFFFE3E3), shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
        Text(text, color = AgroDanger, modifier = Modifier.padding(12.dp), fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun SeverityPill(text: String, color: Color) {
    Surface(color = color.copy(alpha = 0.14f), shape = RoundedCornerShape(12.dp)) {
        Text(text, color = color, modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp), fontSize = 11.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun MessageBanner(message: String?) {
    if (!message.isNullOrBlank()) {
        Surface(color = AgroGreenSoft, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            Text(message, color = AgroGreenDark, modifier = Modifier.padding(12.dp), fontSize = 12.sp)
        }
    }
}

@Composable
private fun MiniRoundIcon(icon: String, color: Color) {
    Surface(color = color.copy(alpha = 0.14f), shape = CircleShape, modifier = Modifier.size(42.dp)) {
        Box(contentAlignment = Alignment.Center) {
            Text(icon, color = color, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun itemCard(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Card(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 7.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Box(Modifier.padding(14.dp)) { content() }
    }
}

private val irrigationTypes = listOf("Temporal (lluvia)", "Goteo", "Aspersion", "Gravedad", "Manual")
private val expenseCategories = listOf("Semillas", "Fertilizante", "Transporte", "Mano de obra", "Herramientas")

private data class PestKnowledge(
    val symptoms: String,
    val recommendations: String,
    val prevention: String
)

@Composable
private fun TimelineRow(date: String, activity: String, done: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 5.dp)) {
        Surface(color = if (done) AgroGreen else AgroMuted, shape = CircleShape, modifier = Modifier.size(8.dp)) {}
        Spacer(Modifier.width(10.dp))
        Text(date, color = AgroMuted, fontSize = 12.sp, modifier = Modifier.width(48.dp))
        Text(activity, color = if (done) AgroText else AgroMuted, fontSize = 13.sp)
    }
}

@Composable
private fun PestInfoBlock(title: String, text: String, color: Color) {
    itemCard {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                MiniRoundIcon(title.first().toString(), color)
                Spacer(Modifier.width(10.dp))
                Text(title, color = AgroMuted, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(10.dp))
            Text(text, color = AgroMuted, fontSize = 13.sp, lineHeight = 19.sp)
        }
    }
}

@Composable
private fun CategoryChip(category: String, amount: Int, modifier: Modifier = Modifier) {
    val color = expenseColor(category)
    Surface(color = color.copy(alpha = 0.14f), shape = RoundedCornerShape(12.dp), modifier = modifier) {
        Column(Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(category, color = color, fontSize = 11.sp, fontWeight = FontWeight.Bold, maxLines = 1)
            Text("${'$'}$amount", color = color, fontSize = 13.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun ExpenseCard(expense: ExpenseEntity) {
    val color = expenseColor(expense.category)
    itemCard {
        Row(verticalAlignment = Alignment.CenterVertically) {
            MiniRoundIcon("G", color)
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(expense.concept, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    SeverityPill(expense.category, color)
                    Spacer(Modifier.width(8.dp))
                    Text(expense.date, color = AgroMuted, fontSize = 11.sp)
                }
            }
            Text("-${'$'}${expense.amount}", color = AgroDanger, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun ReportKpiCard(value: String, label: String, sub: String, color: Color, modifier: Modifier = Modifier) {
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(16.dp)) {
        Column(Modifier.padding(14.dp)) {
            MiniRoundIcon(label.first().toString(), color)
            Spacer(Modifier.height(10.dp))
            Text(value, color = color, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text(label, color = AgroText, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            Text(sub, color = AgroMuted, fontSize = 11.sp)
        }
    }
}

@Composable
private fun ProgressLine(label: String, value: Int, max: Int, color: Color, valueLabel: String = "${'$'}$value") {
    val fraction = if (max == 0) 0f else (value.toFloat() / max.toFloat()).coerceIn(0f, 1f)
    Column(Modifier.padding(vertical = 6.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(label, color = AgroMuted, fontSize = 12.sp)
            Text(valueLabel, color = color, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(5.dp))
        Box(Modifier.fillMaxWidth().height(8.dp).background(Color(0xFFECEFED), RoundedCornerShape(10.dp))) {
            Box(Modifier.fillMaxWidth(fraction).height(8.dp).background(color, RoundedCornerShape(10.dp)))
        }
    }
}

@Composable
private fun DistributionRow(label: String, value: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 5.dp)) {
        Surface(color = color, shape = CircleShape, modifier = Modifier.size(10.dp)) {}
        Spacer(Modifier.width(10.dp))
        Text(label, color = AgroText, fontSize = 13.sp)
        Spacer(Modifier.weight(1f))
        Text(value, color = color, fontWeight = FontWeight.Bold, fontSize = 13.sp)
    }
}

private fun cropIconText(crop: CropEntity): String = when (crop.icon) {
    "corn" -> "M"
    "bean" -> "F"
    "wheat" -> "A"
    else -> "H"
}

private fun irrigationFromCrop(crop: CropEntity): String {
    return irrigationTypes.firstOrNull { crop.riskLabel.startsWith(it) } ?: "Temporal (lluvia)"
}

private fun expenseColor(category: String): Color = when (category) {
    "Semillas" -> AgroGreen
    "Fertilizante" -> AgroWarning
    "Transporte" -> AgroInfo
    "Mano de obra" -> Color(0xFF7B1FA2)
    else -> AgroDanger
}

private fun pestKnowledge(pest: PestEntity): PestKnowledge = when (pest.name) {
    "Gusano cogollero" -> PestKnowledge(
        symptoms = pest.description,
        recommendations = "Aplicar insecticida biologico Bacillus thuringiensis y monitorear semanalmente.",
        prevention = "Rotacion de cultivos, siembra en epoca adecuada y uso de trampas de feromonas."
    )
    "Pulgon negro" -> PestKnowledge(
        symptoms = pest.description,
        recommendations = "Usar jabon potasico o extracto de ajo y fomentar insectos beneficos.",
        prevention = "Evitar exceso de nitrogeno y usar plantas repelentes como albahaca."
    )
    "Roya del frijol" -> PestKnowledge(
        symptoms = pest.description,
        recommendations = "Aplicar fungicida cuprico y retirar plantas afectadas.",
        prevention = "Usar variedades resistentes, espaciamiento adecuado y evitar riego nocturno."
    )
    "Trips del aguacate" -> PestKnowledge(
        symptoms = pest.description,
        recommendations = "Aplicar neem o spinosad y colocar trampas azules adhesivas.",
        prevention = "Eliminar malezas hospederas y mantener cobertura del suelo."
    )
    "Chahuixtle" -> PestKnowledge(
        symptoms = pest.description,
        recommendations = "Aplicar fungicidas preventivos y retirar plantas infectadas.",
        prevention = "Usar semilla certificada libre de enfermedad y rotar cultivos."
    )
    else -> PestKnowledge(
        symptoms = pest.description,
        recommendations = "Aplicar acaricida especifico, aumentar humedad y vigilar la recuperacion.",
        prevention = "Mantener riego adecuado, evitar estres hidrico y usar plantas trampa."
    )
}

private fun tagColor(tag: String): Color = when (tag) {
    "blue" -> AgroInfo
    "orange" -> AgroWarning
    else -> AgroGreen
}

private fun severityColor(severity: String): Color = when (severity) {
    "Alta" -> AgroDanger
    "Media" -> AgroWarning
    else -> AgroGreen
}
