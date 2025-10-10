# App-Lol

Una aplicación Android que muestra una lista de campeones de League of Legends con sus descripciones e imágenes.

## 📱 Características

- Lista de campeones de League of Legends con información detallada
- Interfaz moderna construida con Jetpack Compose
- Diseño alternado de imágenes (izquierda/derecha) para mejor visualización
- Imágenes circulares de los campeones
- Descripción, título y rol de cada campeón
- Arquitectura MVVM (Model-View-ViewModel)
- Soporte multiidioma (Inglés y Español)
- **Navegación entre pantallas** con Jetpack Navigation Compose
- Pantalla de detalle de campeón con información completa

## 🛠️ Tecnologías Utilizadas

- **Kotlin** - Lenguaje de programación principal
- **Jetpack Compose** - Framework moderno de UI para Android
- **Material Design 3** - Sistema de diseño
- **Coil** - Librería para carga de imágenes
- **ViewModel** - Gestión del estado de la UI
- **StateFlow** - Gestión de flujos de datos reactivos
- **Navigation Compose** - Navegación entre pantallas

## 🧭 Navegación

La aplicación implementa un sistema de navegación usando **Jetpack Navigation Compose** que permite moverse entre diferentes pantallas:

### Pantallas

1. **Lista de Campeones** (`champion_list`)
   - Pantalla principal que muestra todos los campeones disponibles
   - Al hacer clic en un campeón, navega a la pantalla de detalles

2. **Detalle de Campeón** (`champion_detail/{championId}`)
   - Muestra información detallada de un campeón específico
   - Incluye imagen ampliada, título y descripción completa
   - Botón de retroceso en la barra superior para volver a la lista

### Características de Navegación

- **NavController**: Gestiona el estado de navegación
- **NavHost**: Define el grafo de navegación con las rutas disponibles
- **Argumentos de navegación**: Pasa el ID del campeón entre pantallas
- **Navegación hacia atrás**: Implementada con `navController.popBackStack()`

## 📋 Requisitos

- Android Studio Hedgehog o superior
- SDK de Android 34 o superior
- JDK 11

## 🚀 Instalación

1. Clona este repositorio:
```bash
git clone https://github.com/Mariogarluu/App-Lol.git
```

2. Abre el proyecto en Android Studio

3. Sincroniza el proyecto con los archivos Gradle

4. Ejecuta la aplicación en un emulador o dispositivo físico

## 📂 Estructura del Proyecto

```
app/src/main/java/com/example/app_lol/
├── Champion.kt              # Modelo de datos del campeón
├── ChampionRepository.kt    # Repositorio con los datos de campeones
├── ChampionViewModel.kt     # ViewModel para gestión de estado
├── MainActivity.kt          # Actividad principal con UI Compose
└── ui/theme/               # Tema y estilos de la aplicación
```

## 🎮 Campeones Incluidos

La aplicación actualmente incluye los siguientes campeones:

- **Ziggs** - The Hexplosives Expert
- **Vi** - The Piltover Enforcer
- **Teemo** - The Swift Scout
- **Taric** - The Shield of Valoran
- **Senna** - The Redeemer
- **Neeko** - The Curious Chameleon
- **Leona** - The Radiant Dawn
- **Irelia** - The Blade Dancer
- **Fizz** - The Tidal Trickster
- **Diana** - Scorn of the Moon
- **Annie** - The Dark Child

## 🌍 Idiomas Soportados

- Inglés (English)
- Español (Spanish)

## 👨‍💻 Autor

Mario García

## 📄 Licencia

Este proyecto es de uso educativo y personal.

---

**Nota:** Este proyecto es una aplicación de demostración y no está afiliado oficialmente con Riot Games o League of Legends.
