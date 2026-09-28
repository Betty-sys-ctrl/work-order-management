# Arquitectura del Proyecto

## Backend
El backend está diseñado siguiendo una arquitectura de capas estándar de Spring Boot:
- **Controladores (Controllers):** Manejo de peticiones HTTP, validación y exposición de la API REST.
- **Servicios (Services):** Contienen toda la lógica de negocio y las transacciones.
- **Repositorios (Repositories):** Integración con la capa de datos mediante Spring Data JPA.
- **Seguridad (Security):** Spring Security intercepta todas las llamadas. Se implementó una configuración Stateless que valida tokens JWT emitidos en el endpoint de login.
- **DTOs & Java Records:** Para un transporte de datos inmutable y eficiente (ej. DashboardMetricsDTO).

## Frontend
El cliente web está construido como una **Single Page Application (SPA)** utilizando React y Vite.
- **Manejo de Estado y Peticiones:** Se gestiona el estado local mediante Hooks de React (useState, useEffect). Todas las comunicaciones HTTP se centralizan en una instancia configurada de **Axios**.
- **Interceptores de Axios:** Para garantizar la seguridad en cada petición, se configuró un interceptor que inyecta automáticamente el token JWT (almacenado en localStorage) en los encabezados Authorization: Bearer <token> de todas las solicitudes salientes, y redirige al /login si detecta un estado HTTP 401.
- **Enrutamiento Protegido:** Se utiliza eact-router-dom con un componente ProtectedRoute que encapsula la navegación y verifica la sesión del usuario, impidiendo el acceso anónimo al sistema.
- **Estructura de Componentes Reutilizables:**
  - **Pagination.jsx:** Un componente inteligente de paginación numérica, diseñado con lógica de ventanas dinámicas (elipsis) para gestionar grandes volúmenes de datos en las tablas del servidor sin sobrecargar el renderizado (basado en páginas y tamaños fijos de 20 elementos size=20).
  - **Componentes Material-UI:** Uso extensivo del sistema de cuadrícula (Grid), modales (Dialog) y tablas (Table) para estandarizar la estética del proyecto y mantener un diseño responsivo, profesional y coherente.