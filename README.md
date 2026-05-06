# FakeStore App

Aplicación Android que consume la [Fake Store API](https://fakestoreapi.com), desarrollada como prueba técnica con las últimas prácticas de desarrollo Android moderno.

## Capturas de Pantalla
https://github.com/user-attachments/assets/820198a2-2934-42c3-b841-3dc914f3001c

## Decisiones Técnicas
- **Arquitectura:** Clean Architecture con patrón MVI (Model-View-Intent) para garantizar un flujo de datos unidireccional y un estado de UI predecible y testeable.
- **Inyección de Dependencias:** Hilt para facilitar el desacoplamiento entre capas y mejorar la testeabilidad de cada componente.
- **Persistencia:** Room como única fuente de verdad (Single Source of Truth), permitiendo soporte offline y persistencia de favoritos entre sesiones.
- **Networking:** Retrofit + OkHttp con interceptores para la gestión centralizada de peticiones y logging.
- **UI:** Jetpack Compose para una interfaz declarativa, moderna y reactiva.
- **Navegación:** Navigation Compose con rutas tipadas y centralizadas.
- **Imágenes:** Coil para carga y caché eficiente de imágenes desde red.
- **Gestión de Versiones:** Gradle Version Catalog (`libs.versions.toml`) para centralizar y estandarizar las dependencias del proyecto.
- **Concurrencia:** Kotlin Coroutines + Flow para el manejo reactivo y asíncrono de datos a través de todas las capas.

## Estructura del Proyecto
```
app/
├── data/
│   ├── database/       # Room: Database, DAOs, Entities
│   ├── datasource/     # Implementaciones Local y Remote DataSource
│   └── network/        # Retrofit: ApiService, DTOs
├── domain/
│   ├── di/             # Módulos Hilt de la capa de dominio
│   ├── mappers/        # Conversión entre capas (DTO → Entity → Model)
│   ├── models/         # Modelos de negocio
│   ├── repositories/   # Contratos e implementaciones de repositorios
│   ├── usecases/       # Casos de uso
│   └── utils/          # Extensiones y utilidades de dominio
└── ui/
    ├── di/             # DispatcherModule, DispatchersQualifiers
    ├── features/
    │   ├── alerts/     # Componentes de alertas y errores
    │   ├── home/       # Listado de productos
    │   ├── product/    # Detalle de producto
    │   ├── profile/    # Perfil de usuario y contador de favoritos
    │   └── splash/     # Pantalla de inicio
    ├── main/           # MainActivity
    ├── navigation/     # AppNavigation, AppRoutes
    ├── presentation/
    │   ├── components/ # Componentes Compose reutilizables
    │   └── theme/      # Colores, tipografía y tema
    └── utils/          # Extensions, FlowExtensions
```

## Flujo de Datos
UI (Compose) → ViewModel (MVI) → UseCase → Repository → LocalDataSource / RemoteDataSource
↑
Room (cache) + Retrofit (API)

El repositorio actúa como árbitro: sirve datos locales cuando están disponibles y recurre a la red en caso contrario, manteniendo Room como Single Source of Truth.

## Funcionalidades

- Listado de productos cargados desde red con caché local.
- Marcado y desmarcado de productos como favoritos persistido en base de datos.
- Pantalla de favoritos con los productos guardados.
- Perfil de usuario con contador de items favoritos.
- Gestión de estados: **Loading / Error / Content** en todas las pantallas.
- Búsqueda de productos por nombre.

## Testing

La estrategia de pruebas cubre las capas críticas de la aplicación:

**Pruebas unitarias (`test/`)**
- `ApiServiceTest` — contratos del servicio de red.
- `RemoteDataSourceTest` — comportamiento de la fuente de datos remota.
- `LocalDataSourceTest` — comportamiento de la fuente de datos local.
- `RepositoryTest` — lógica de caché y fallback entre fuentes.
- `UseCaseTest` — reglas de negocio aisladas.
- `MapperTest` — conversión correcta entre capas.

**Pruebas instrumentadas (`androidTest/`)**
- `RoomDaoTest` — operaciones CRUD reales sobre base de datos en memoria.

## Fuera de Alcance

- Paginación infinita con Paging 3.
- Animaciones de transición entre pantallas.
- Snapshot Testing para componentes Compose.
- Modularización por capas o features.

## Mejoras con más tiempo

- Implementar **Paging 3** para gestión eficiente de listas largas.
- Añadir **Snapshot Testing** con Paparazzi para garantizar integridad visual.
- Implementar **WorkManager** para sincronización periódica en segundo plano.
- **Modularizar** el proyecto por features para mejorar tiempos de compilación y escalabilidad.
- Añadir un sistema de **notificaciones** para alertar sobre nuevos productos.
- Implementar **DataStore** para persistir preferencias del usuario.

## Uso de Inteligencia Artificial

Se utilizó IA de la siguiente manera:

- **Generación de Commits:** Para estandarizar y redactar mensajes descriptivos y consistentes.
- **Scaffolding de Pruebas:** Para agilizar la creación de estructuras repetitivas en pruebas unitarias de DAOs, Mappers, Repositorios y Casos de Uso.

## Requisitos

- Android Studio Hedgehog o superior.
- JDK 17.
- Android SDK 34.
- Conexión a internet para la primera carga de datos.

## Configuración

```bash
git clone https://github.com/tu-usuario/fakestore-app.git
cd fakestore-app
./gradlew assembleDebug
```
