# Documentación de Arquitectura

## Diagrama de Entidad-Relación (ERD)
A continuación se detalla el modelo relacional subyacente que soporta la lógica de la API, diagramado mediante sintaxis Mermaid.js:

\\\mermaid
erDiagram
    TECHNICIAN ||--o{ WORK_ORDER : "assigned to"
    WORK_ORDER ||--o{ STATUS_HISTORY : "has"
    WORK_ORDER ||--o{ ORDER_MATERIAL : "consumes"
    MATERIAL ||--o{ ORDER_MATERIAL : "used in"

    SYSTEM_USER {
        Long id PK
        String username
        String password
    }

    TECHNICIAN {
        Long id PK
        String name
        String email
        String specialty
        Boolean active
    }

    WORK_ORDER {
        Long id PK
        String title
        String description
        String status
        LocalDateTime createdAt
        LocalDateTime updatedAt
        Long technicianId FK
    }

    STATUS_HISTORY {
        Long id PK
        String previousStatus
        String newStatus
        LocalDateTime changedAt
        Long workOrderId FK
    }

    MATERIAL {
        Long id PK
        String name
        String sku
        Integer stockQuantity
    }

    ORDER_MATERIAL {
        Long id PK
        Integer quantityUsed
        Long workOrderId FK
        Long materialId FK
    }
\\\

## Arquitectura de Software
El sistema ha sido estructurado basándose en el estándar de separación por capas (Layered Architecture) ampliamente adoptado en Spring Boot:

- **Controllers (\@RestController\):** Capa de presentación. Se encarga de recibir las peticiones HTTP REST, delegar la validación de los datos de entrada (mediante Jakarta \@Valid\ sobre DTOs) y retornar los DTOs de respuesta al cliente. Nunca exponen las Entidades JPA.
- **Services (\@Service\):** Capa de lógica de negocio. Contiene reglas complejas (como descuentos de stock o registro automático de historiales) encapsuladas bajo anotaciones \@Transactional\ para garantizar atomicidad y prevención de estados inconsistentes.
- **Repositories (\@Repository\):** Interfaces de Spring Data JPA que facilitan el mapeo relacional de objetos y proveen consultas avanzadas mediante JPQL dinámico y filtrado paginado (\Pageable\).

### Seguridad (Spring Security & JWT)
El proyecto implementa un módulo robusto de autenticación. 
- Se ha configurado una política de sesiones **STATELESS**, asegurando que el backend escale horizontalmente de forma eficiente y sin dependencias de sesión.
- Las peticiones se protegen globalmente mediante una clase \SecurityConfig\ y un filtro personalizado \JwtAuthenticationFilter\ (extensión de \OncePerRequestFilter\). 
- Dicho filtro intercepta el header \Authorization: Bearer <token>\ y valida de forma estricta el token (firma HSM256 y expiración). En caso de carecer de permisos (403) o presentar anomalías de token (401), se intercepta el error con manejadores (\AuthenticationEntryPoint\ y \AccessDeniedHandler\) que garantizan que el cliente siempre reciba la respuesta estructurada en formato JSON, en sintonía con las demás excepciones de negocio.

### Manejo de Excepciones y Validaciones
Se ha implementado un mecanismo de intercepción global mediante \@RestControllerAdvice\ en la clase \GlobalExceptionHandler\. Este componente consolida las excepciones generadas en cualquier capa (como \ResourceNotFoundException\, \BusinessRuleException\ o fallos de validación \MethodArgumentNotValidException\) y emite respuestas HTTP coherentes junto a un DTO estandarizado \ErrorResponse\.

### Prevención de StackOverflowError
Se evita el uso de anotaciones auto-generativas globales como \@Data\ de Lombok sobre entidades JPA que posean relaciones bidireccionales y colecciones. En su lugar, el sistema emplea separadamente \@Getter\ y \@Setter\ impidiendo ciclos de recursión infinita durante las fases de serialización (por ej. métodos \	oString\ o serialización JSON).

## Estrategia de Ramas (Git Workflow)
Se adoptó estrictamente el flujo de trabajo **Feature Branch Workflow**:
- Todo el desarrollo de nuevas características (CRUDs, endpoints específicos, y reglas de dominio) y reparaciones urgentes (\ix/\) se aislaron en ramas individuales y atómicas que se ramificaron directamente de \main\.
- Cada funcionalidad se consolidó en un único commit descriptivo.
- Las ramas de características fueron posteriormente fusionadas en \main\ preservando de esta manera una línea principal inmaculada, altamente trazable y fácil de auditar en la plataforma de control de versiones.