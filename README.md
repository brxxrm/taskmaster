#Taskngles

<div align="center">
  <img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" alt="Android">
  <img src="https://img.shields.io/badge/Kotlin-0095D5?style=for-the-badge&logo=kotlin&logoColor=white" alt="Kotlin">
  <img src="https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white" alt="Jetpack Compose">
  <img src="https://img.shields.io/badge/Material%20Design%203-757575?style=for-the-badge&logo=material-design&logoColor=white" alt="Material Design 3">
</div>

<div align="center">
  <h3>Gestiona tus tareas de manera sencilla</h3>
  <p><em>"Organiza tu vida con estilo bro xd"</em></p>
</div>

---

## Características Principales

### **Diseño**
- **Material Design 3** con esquemas de colores dinámicos
- **Esquinas súper redondeadas** (24dp) para un look premium
- **Gradientes verdes** elegantes y profesionales
- **Sombras suaves** que dan profundidad a la interfaz

### **Modo Oscuro**
- **Toggle estrella animado** - Cambia entre modo claro y oscuro
- **Colores adaptativos** - Verde brillante en oscuro, tonos suaves en claro
- **Transiciones suaves** entre temas

### **Funcionalidades**
- ✅ **Agregar tareas** con input inteligente
- 🔘 **Marcar como completadas** con animaciones fluidas
- 🗑️ **Eliminar tareas** con confirmación visual
- 📊 **Barra de progreso** que muestra el porcentaje completado
- 📱 **Diseño responsive** que se adapta a cualquier pantalla

### **Animaciones**
- **Spring animations** con rebote natural
- **Fade in/out** para transiciones suaves
- **Scale animations** en botones interactivos
- **Slide animations** para elementos de lista

---

## 🛠️ Tecnologías Utilizadas

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| **Kotlin** | Latest | Lenguaje principal |
| **Jetpack Compose** | Latest | UI moderna y declarativa |
| **Material Design 3** | Latest | Sistema de diseño |
| **Compose Animation** | Latest | Animaciones fluidas |
| **Compose Navigation** | Latest | Navegación (futuro) |

---

## 📋 Requisitos del Sistema

### 🔧 **Desarrollo**
- **Android Studio** Hedgehog | 2023.1.1 o superior
- **JDK** 17 o superior
- **Gradle** 8.0 o superior
- **Kotlin** 1.9.0 o superior

### 📱 **Dispositivo**
- **Android API Level** 24+ (Android 7.0)
- **RAM** mínima: 2GB
- **Almacenamiento** libre: 50MB

---

## 🚀 Instalación y Configuración

### 1️⃣ **Clonar el Repositorio**
\`\`\`bash
git clone https://github.com/tu-usuario/taskngles.git
cd taskngles
\`\`\`

### 2️⃣ **Abrir en Android Studio**
1. Abre **Android Studio**
2. Selecciona **"Open an existing project"**
3. Navega hasta la carpeta `taskngles`
4. Haz clic en **"OK"**

### 3️⃣ **Sincronizar Dependencias**
\`\`\`bash
# Android Studio sincronizará automáticamente
# O ejecuta manualmente:
./gradlew sync
\`\`\`

### 4️⃣ **Configurar Emulador**
1. Ve a **Tools > AVD Manager**
2. Crea un **nuevo dispositivo virtual**
3. Selecciona **API Level 24+**
4. Inicia el emulador

---

## ▶️ Cómo Ejecutar el Proyecto

### **Método 1: Android Studio**
1. Abre el proyecto en Android Studio
2. Espera a que termine la sincronización
3. Haz clic en el botón **"Run"** (▶️)
4. Selecciona tu dispositivo/emulador
5. ¡Disfruta Taskngles!

### **Método 2: Línea de Comandos**
\`\`\`bash
# Compilar el proyecto
./gradlew assembleDebug

# Instalar en dispositivo conectado
./gradlew installDebug

# Ejecutar tests
./gradlew test
\`\`\`

### **Método 3: APK Directo**
\`\`\`bash
# Generar APK de debug
./gradlew assembleDebug

# El APK se generará en:
# app/build/outputs/apk/debug/app-debug.apk
\`\`\`

---

## 📁 Estructura del Proyecto

\`\`\`
taskngles/
├── app/
│   ├── src/main/java/com/example/taskmaster/
│   │   └── MainActivity.kt              # Todo el código principal
│   ├── src/main/res/
│   │   ├── drawable/                   # Iconos y recursos
│   │   ├── values/                     # Colores y strings
│   │   └── layout/                     # Layouts (si los hay)
│   └── build.gradle.kts               # Configuración del módulo
├── gradle/
├── build.gradle.kts                   # Configuración del proyecto
├── settings.gradle.kts               # Configuración de Gradle
└── README.md                         # Este archivo
\`\`\`

---

## Componentes Principales

### **MainActivity**
- Punto de entrada de la aplicación
- Configura el tema y la UI principal

### **TaskMasterTheme**
- Maneja el modo claro/oscuro
- Define los colores y tipografías
- Implementa Material Design 3

### **TaskMasterApp**
- Composable principal de la aplicación
- Maneja el estado global de las tareas
- Coordina todos los componentes

### **TaskInputSection**
- Campo de texto para nuevas tareas
- Botón de agregar con animaciones
- Validación de entrada

### **TaskList & TaskItem**
- Lista scrolleable de tareas
- Items individuales con checkboxes
- Animaciones de entrada/salida

---

## 🎯 Funcionalidades Detalladas

### ✅ **Gestión de Tareas**
```kotlin
// Agregar nueva tarea
fun addTask() {
    if (newTaskText.trim().isNotEmpty()) {
        val newTask = Task(title = newTaskText.trim())
        tasks = tasks + newTask
        newTaskText = ""
    }
}

// Marcar como completada
fun toggleTaskCompletion(taskId: String) {
    tasks = tasks.map { task ->
        if (task.id == taskId) {
            task.copy(isCompleted = !task.isCompleted)
        } else task
    }
}
