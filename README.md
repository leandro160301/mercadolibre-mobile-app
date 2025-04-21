## - Mercado Libre Mobile Candidate

Esta aplicación fue desarrollada como parte del proceso de entrevista técnica. El objetivo fue implementar una app que permita a un usuario buscar productos y visualizar sus detalles, utilizando las APIs públicas de Mercado Libre.

## - ✅ Funcionalidad requerida

Campo de búsqueda

Resultados de búsqueda

Detalle de producto

## - ⚠️ Aclaraciones importantes

Actualmente los endpoints públicos de búsqueda de Mercado Libre (por ejemplo, https://api.mercadolibre.com/sites/MLA/search?q=) devuelven errores 400 y 403, por lo que la app fue modificada para:

Usar una cuenta autenticada del desarrollador.

Mostrar productos propios, publicados previamente con stock en 0 para que no aparezca en busquedas generales, utilizando el seller_id.

Devolver una lista y detalle de productos reales desde esa cuenta.

Esta decisión permitió mantener intacto el flujo esperado: búsqueda → listado → detalle, a pesar de la limitación actual de la API pública.

## - 🧱 Arquitectura y stack técnico

Clean Architecture: separación en capas (data, domain, presentation).

MVI (Model-View-Intent): gestión de estado de UI reactiva y predecible.

StateFlow: flujo de datos reactivo para estados y eventos.

Coroutines: para manejo asíncrono y concurrencia.

Retrofit: para consumo de APIs.

Hilt: inyección de dependencias.

## - 🔧 Consideraciones adicionales

Los productos se cargan usando el seller_id de la cuenta autenticada.

Se utilizó una sola cuenta, pero la arquitectura está preparada para escalar si se habilitaran los endpoints públicos.

El flujo de navegación y estados está desacoplado de la fuente de datos, lo que permite fácilmente cambiar de API en el futuro.

## - 📝 Cómo correr la app

Clonar el repositorio.

Abrir en Android Studio.

Ejecutar en un emulador o dispositivo.

