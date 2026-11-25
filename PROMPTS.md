# Prompts de Gemini

| Prompt que escribí | Qué respondió Gemini | Qué cambié o descarté | Por qué |
|---|---|---|---|
| «Soy estudiante de programación móvil y conozco un poco de Kotlin. Implementa solo la mejora de dos canales en AvisosCampus: Avisos para avisos generales y Urgentes para los recordatorios. Conserva MVVM + Repository, no pongas `Context` ni `NotificationManager` en el ViewModel, no agregues dependencias ni hagas commits. Revisa y compila el proyecto.» | En `Notificador.kt`, creó los canales `Avisos` (`IMPORTANCE_DEFAULT`) y `Urgentes` (`IMPORTANCE_HIGH`), y asignó los recordatorios al canal urgente. | Conservé la propuesta. La compilación limpia pasó y, en el emulador Pixel 6 Pro, `dumpsys notification` confirmó el canal `urgentes` con importancia alta para el recordatorio. | Cumple la primera mejora del laboratorio, mantiene la lógica de notificaciones en `Notificador` y deja el ViewModel sin dependencias de Android. |
