# WearMet

Aplicación Android de tiempo meteorológico con widget de pantalla de inicio, favoritos y funcionamiento sin conexión. Hecha 100 % en Kotlin con Jetpack Compose.

> Paquete: `com.zekiee.wearmet`

<!-- Añade aquí capturas de pantalla: app, ajustes y widget -->

## Funcionalidades

- Búsqueda de ciudades por nombre.
- Tiempo actual, previsión por horas y previsión diaria.
- Favoritos guardados en local (Room).
- Ajustes: pantalla de inicio (splash) y su duración, abrir directamente la ubicación preferida, e intervalo de actualización del widget.
- Widget de Glance con dos tamaños (compacto y normal) que se actualiza en segundo plano.
- Caché local de la última previsión: si falla la red, el widget y la app siguen mostrando datos y se reintenta al volver la conexión.

## Datos y APIs públicas

WearMet no usa claves de API. Consume dos servicios públicos y gratuitos de [Open-Meteo](https://open-meteo.com/):

| Servicio | Para qué se usa | Endpoint base |
|---|---|---|
| Geocoding API | Convertir el nombre de una ciudad en coordenadas, país y zona horaria | `https://geocoding-api.open-meteo.com/v1/search` |
| Forecast API | Tiempo actual, previsión horaria y diaria a partir de latitud y longitud | `https://api.open-meteo.com/v1/forecast` |

Flujo de datos:

1. El usuario escribe una ciudad y la app llama a la Geocoding API con el parámetro `name`.
2. Con la latitud y longitud elegidas, la app llama a la Forecast API.
3. El resultado se convierte a modelos de dominio, se muestra y se guarda en caché.

### Condiciones de uso de Open-Meteo

- Uso gratuito solo para fines no comerciales, con límites de 10 000 llamadas al día, 5 000 por hora y 600 por minuto.
- Los datos tienen licencia CC BY 4.0: hay que dar crédito y enlazar a Open-Meteo.
- Open-Meteo puede bloquear usos abusivos. Si la app llegara a superar los límites o a ser comercial, hay que pasar a su plan de pago.

Datos meteorológicos de [Open-Meteo.com](https://open-meteo.com/).

## Tecnologías

| Área | Tecnología |
|---|---|
| Lenguaje | Kotlin 2.2.10 |
| Interfaz | Jetpack Compose, Material 3 |
| Navegación | Navigation 3 |
| Widget | Jetpack Glance (`SizeMode.Responsive`) |
| Segundo plano | WorkManager (trabajo periódico y reintento con restricción de red) |
| Base de datos | Room 3 con KSP |
| Preferencias | DataStore |
| Red | Retrofit |
| Serialización | kotlinx.serialization (caché del widget en JSON) |
| Build | Gradle, Android Gradle Plugin 9.4.1 |

## Arquitectura

```
com.zekiee.wearmet
├── data/          Cliente de red, servicios y repositorio de Open-Meteo
├── domain/        Modelos (Weather, CurrentWeather, HourlyForecast, DailyForecast) y WeatherResult
├── local/         Room (favoritos), DataStore (ajustes, última ubicación)
├── ui/            Pantallas, componentes, tema y ViewModels
└── widget/        Widget de Glance
    ├── state/     WidgetStateLoader: red, caché y mensajes de error
    ├── updater/   WidgetUpdateWorker, WidgetUpdateScheduler, WidgetCache, WidgetLocationResolver
    └── screens/   Composables del widget
```

### Cómo funciona el widget

1. `WidgetUpdateScheduler` programa un trabajo periódico (`widget_update`) con el intervalo elegido en ajustes y restricción de red.
2. `WidgetUpdateWorker` avisa al widget con `WidgetRefresh` y lo actualiza.
3. `WidgetStateLoader` pide el tiempo a la API. Si responde, guarda el resultado en `widget_weather.json`.
4. Si hay un error de red, muestra la caché y programa `widget_retry`, que espera a que vuelva la conexión.
5. Si la sesión de Glance sigue abierta, `WidgetRefresh` fuerza una recarga dentro de la composición para evitar mostrar datos viejos.
6. Al tocar el widget se abre la app directamente en la ubicación del widget.

Nota: el intervalo mínimo de un trabajo periódico de WorkManager es de 15 minutos. En algunos fabricantes (ColorOS, MIUI, etc.) el ahorro de batería puede retrasar las actualizaciones; conviene permitir la actividad en segundo plano para la app.

## Compilar

Requisitos: Android Studio reciente y JDK 17 o superior.

```bash
git clone <url-del-repositorio>
cd WearMet
./gradlew assembleDebug
```

El APK de depuración queda en `app/build/outputs/apk/debug/`.

## APK firmada (release)

1. Genera un keystore: **Build > Generate Signed Bundle / APK > APK > Create new**.
2. Guarda el `.jks` y las contraseñas fuera del repositorio (no lo subas a Git).
3. Genera la variante `release` y comprueba la firma con `apksigner verify --verbose app-release.apk`.
4. Prueba el APK en un móvil real antes de repartirlo.

Fuera de Google Play, Play Protect puede mostrar un aviso de app desconocida la primera vez que se instala; es normal en APK nuevas.

## Pendiente

- Mostrar el enlace de atribución "Weather data by Open-Meteo.com" dentro de la app.
- Revisar reglas de R8 antes de activar la minificación.
- Elegir y añadir una licencia para el repositorio.

## Créditos

- Datos meteorológicos y geocodificación: [Open-Meteo.com](https://open-meteo.com/) (CC BY 4.0).
