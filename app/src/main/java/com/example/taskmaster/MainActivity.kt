package com.example.taskmaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskMasterTheme {
                TaskMasterApp()
            }
        }
    }
}

data class Task(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val isCompleted: Boolean = false
)

@Composable
fun TaskMasterTheme(content: @Composable () -> Unit) {
    // Manual dark mode state
    var isDarkMode by remember { mutableStateOf(false) }

    MaterialTheme(
        colorScheme = if (isDarkMode) {
            darkColorScheme(
                primary = Color(0xFF4ADE80),
                onPrimary = Color(0xFF003D1A),
                primaryContainer = Color(0xFF166534),
                onPrimaryContainer = Color(0xFFBBF7D0),
                secondary = Color(0xFF34D399),
                onSecondary = Color(0xFF003D1A),
                secondaryContainer = Color(0xFF047857),
                onSecondaryContainer = Color(0xFFA7F3D0),
                surface = Color(0xFF1E293B),
                onSurface = Color(0xFFF1F5F9),
                surfaceVariant = Color(0xFF334155),
                onSurfaceVariant = Color(0xFFCBD5E1),
                error = Color(0xFFF87171),
                onError = Color(0xFF7F1D1D),
                background = Color(0xFF0F172A),
                onBackground = Color(0xFFF1F5F9)
            )
        } else {
            lightColorScheme(
                primary = Color(0xFF16A34A),
                onPrimary = Color.White,
                primaryContainer = Color(0xFFDCFCE7),
                onPrimaryContainer = Color(0xFF14532D),
                secondary = Color(0xFF059669),
                onSecondary = Color.White,
                secondaryContainer = Color(0xFFECFDF5),
                onSecondaryContainer = Color(0xFF064E3B),
                surface = Color.White,
                onSurface = Color(0xFF0F172A),
                surfaceVariant = Color(0xFFF8FAFC),
                onSurfaceVariant = Color(0xFF64748B),
                error = Color(0xFFEF4444),
                onError = Color.White,
                background = Color(0xFFFAFBFF),
                onBackground = Color(0xFF0F172A)
            )
        }
    ) {
        // Pass the dark mode state and toggle function to content
        CompositionLocalProvider(
            LocalDarkModeState provides DarkModeState(isDarkMode) { isDarkMode = !isDarkMode }
        ) {
            content()
        }
    }
}

data class DarkModeState(
    val isDark: Boolean,
    val toggle: () -> Unit
)

val LocalDarkModeState = compositionLocalOf { DarkModeState(false) {} }

@Composable
fun TaskMasterApp() {
    var tasks by remember { mutableStateOf(listOf<Task>()) }
    var newTaskText by remember { mutableStateOf("") }
    val darkModeState = LocalDarkModeState.current

    fun addTask() {
        if (newTaskText.trim().isNotEmpty()) {
            val newTask = Task(title = newTaskText.trim())
            tasks = tasks + newTask
            newTaskText = ""
        }
    }

    fun toggleTaskCompletion(taskId: String) {
        tasks = tasks.map { task ->
            if (task.id == taskId) {
                task.copy(isCompleted = !task.isCompleted)
            } else {
                task
            }
        }
    }

    fun deleteTask(taskId: String) {
        tasks = tasks.filter { it.id != taskId }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = if (darkModeState.isDark) {
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF0F172A),
                            Color(0xFF1E293B),
                            Color(0xFF166534)
                        )
                    )
                } else {
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFAFBFF),
                            Color(0xFFECFDF5),
                            Color(0xFFDCFCE7)
                        )
                    )
                }
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header with Dark Mode Toggle
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.Transparent
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Spacer(modifier = Modifier.height(8.dp))

                    // Cool Dark Mode Toggle Button - FIXED
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        // Animated toggle switch style - CORREGIDO
                        val toggleOffset by animateDpAsState(
                            targetValue = if (darkModeState.isDark) 28.dp else 0.dp,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessMedium
                            ),
                            label = "toggle_offset"
                        )

                        Box(
                            modifier = Modifier
                                .width(60.dp)
                                .height(32.dp)
                                .background(
                                    if (darkModeState.isDark) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        MaterialTheme.colorScheme.surfaceVariant
                                    },
                                    RoundedCornerShape(16.dp)
                                )
                                .shadow(4.dp, RoundedCornerShape(16.dp))
                                .padding(2.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .offset(x = toggleOffset)
                                    .background(
                                        Color.White,
                                        CircleShape
                                    )
                                    .shadow(2.dp, CircleShape)
                                    .clip(CircleShape)
                            ) {
                                IconButton(
                                    onClick = { darkModeState.toggle() },
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = if (darkModeState.isDark) "Modo claro" else "Modo oscuro",
                                        tint = if (darkModeState.isDark) {
                                            Color(0xFFFFD700)
                                        } else {
                                            MaterialTheme.colorScheme.primary
                                        },
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // App Icon & Title
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(
                                    brush = Brush.radialGradient(
                                        colors = listOf(
                                            MaterialTheme.colorScheme.primary,
                                            MaterialTheme.colorScheme.secondary
                                        )
                                    ),
                                    shape = CircleShape
                                )
                                .shadow(8.dp, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "TaskMaster Icon",
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = "Taskngles",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )

                            Text(
                                text = "Organiza tu vida con estilo bro",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Stats
                    val completedTasks = tasks.count { it.isCompleted }
                    val totalTasks = tasks.size

                    if (totalTasks > 0) {
                        val progress = completedTasks.toFloat() / totalTasks.toFloat()

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(6.dp, RoundedCornerShape(24.dp)),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            ),
                            shape = RoundedCornerShape(24.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "$completedTasks de $totalTasks completadas",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.primary
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                // Progress Bar - más redondeada
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(8.dp)
                                        .background(
                                            MaterialTheme.colorScheme.surfaceVariant,
                                            RoundedCornerShape(4.dp)
                                        )
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth(progress)
                                            .fillMaxHeight()
                                            .background(
                                                brush = Brush.horizontalGradient(
                                                    colors = listOf(
                                                        MaterialTheme.colorScheme.primary,
                                                        MaterialTheme.colorScheme.secondary
                                                    )
                                                ),
                                                shape = RoundedCornerShape(4.dp)
                                            )
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = "${(progress * 100).toInt()}% completado",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }

            // Content
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                TaskInputSection(
                    newTaskText = newTaskText,
                    onTextChange = { newTaskText = it },
                    onAddTask = ::addTask
                )

                Spacer(modifier = Modifier.height(20.dp))

                TaskList(
                    tasks = tasks,
                    onToggleTask = ::toggleTaskCompletion,
                    onDeleteTask = ::deleteTask
                )
            }
        }
    }
}

@Composable
fun TaskInputSection(
    newTaskText: String,
    onTextChange: (String) -> Unit,
    onAddTask: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(24.dp)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = newTaskText,
                onValueChange = onTextChange,
                placeholder = {
                    Text(
                        "Que te gustaria hacer hoy?",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 14.sp
                    )
                },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { onAddTask() }
                ),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                ),
                shape = RoundedCornerShape(20.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            val scale by animateFloatAsState(
                targetValue = if (newTaskText.isNotEmpty()) 1f else 0.9f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                ),
                label = "button_scale"
            )

            FloatingActionButton(
                onClick = onAddTask,
                modifier = Modifier
                    .size(52.dp)
                    .scale(scale)
                    .shadow(12.dp, CircleShape),
                containerColor = Color.Transparent,
                shape = CircleShape
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.colorScheme.secondary
                                )
                            ),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Agregar tarea",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TaskList(
    tasks: List<Task>,
    onToggleTask: (String) -> Unit,
    onDeleteTask: (String) -> Unit
) {
    if (tasks.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                                    MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f)
                                )
                            ),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Sin tareas",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(40.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Todo perfecto!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = "Agrega tus tareas, para empezar",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            items(tasks, key = { it.id }) { task ->
                AnimatedVisibility(
                    visible = true,
                    enter = slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                    ) + fadeIn(),
                    exit = slideOutVertically() + fadeOut()
                ) {
                    TaskItem(
                        task = task,
                        onToggleTask = onToggleTask,
                        onDeleteTask = onDeleteTask
                    )
                }
            }
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onToggleTask: (String) -> Unit,
    onDeleteTask: (String) -> Unit
) {
    val animatedElevation by animateDpAsState(
        targetValue = if (task.isCompleted) 3.dp else 6.dp,
        animationSpec = tween(300),
        label = "elevation"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .shadow(animatedElevation, RoundedCornerShape(24.dp)),
        colors = CardDefaults.cardColors(
            containerColor = if (task.isCompleted) {
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onToggleTask(task.id) },
                modifier = Modifier.size(40.dp)
            ) {
                if (task.isCompleted) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.primary,
                                        MaterialTheme.colorScheme.secondary
                                    )
                                ),
                                shape = CircleShape
                            )
                            .shadow(6.dp, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Completada",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .border(
                                width = 3.dp,
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.primary,
                                        MaterialTheme.colorScheme.secondary
                                    )
                                ),
                                shape = CircleShape
                            )
                            .background(
                                MaterialTheme.colorScheme.surface,
                                CircleShape
                            )
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = task.title,
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 4.dp),
                fontSize = 16.sp,
                fontWeight = if (task.isCompleted) FontWeight.Medium else FontWeight.SemiBold,
                textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                color = if (task.isCompleted) {
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            )

            Spacer(modifier = Modifier.width(12.dp))

            IconButton(
                onClick = { onDeleteTask(task.id) },
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(
                        MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.4f)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar tarea",
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "TaskMaster Light")
@Composable
fun TaskMasterPreview() {
    TaskMasterTheme {
        TaskMasterApp()
    }
}

@Preview(showBackground = true, name = "Task Items")
@Composable
fun TaskItemsPreview() {
    TaskMasterTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TaskItem(
                task = Task(title = "Completar el proyecto de rodolfo", isCompleted = false),
                onToggleTask = {},
                onDeleteTask = {}
            )
            TaskItem(
                task = Task(title = "Subir a Github", isCompleted = true),
                onToggleTask = {},
                onDeleteTask = {}
            )
            TaskItem(
                task = Task(title = "Hacer documentacion", isCompleted = false),
                onToggleTask = {},
                onDeleteTask = {}
            )
        }
    }
}
