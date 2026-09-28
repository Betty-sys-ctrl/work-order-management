# Work Order Management System

## Descripción
El **Sistema de Gestión de Órdenes de Trabajo** es una API RESTful robusta diseñada para coordinar y administrar órdenes de servicio, asignar técnicos, controlar historiales de estado y gestionar el consumo y stock de materiales utilizados en cada orden de trabajo.

## Stack Tecnológico
- **Lenguaje:** Java 21
- **Framework:** Spring Boot 3.4
- **Base de Datos:** PostgreSQL 16
- **Contenedores:** Docker & Docker Compose
- **Dependencias Principales:** Maven, Spring Data JPA, Hibernate, Jakarta Validation, Lombok, Spring Security, JWT (JJWT), DataFaker.

## Requisitos Previos
- **Java Development Kit (JDK):** Versión 21 o superior.
- **Docker Desktop:** Para ejecutar el contenedor de base de datos PostgreSQL localmente.

## Instalación y Ejecución

### 1. Ejecutar la Aplicación (Zero-Touch Initialization)
Gracias a la integración con spring-boot-docker-compose, el único requisito es tener **Docker Desktop abierto**. Al ejecutar la aplicación, Spring Boot detectará automáticamente el archivo docker-compose.yml, levantará el contenedor de PostgreSQL y lo detendrá al finalizar la ejecución.

La base de datos se poblará de forma automática (Data Seeding) con **50 registros de prueba de técnicos y materiales** (garantizando entropía y resolviendo unicidad con UUIDs) para que la colección de Postman funcione sin intervención manual. Adicionalmente, se creará un usuario administrador por defecto con las siguientes credenciales:
- **Usuario:** dmin
- **Contraseña:** dmin123

Ejecuta:
\\\ash
./mvnw clean compile
./mvnw spring-boot:run
\\\
> **Nota:** La aplicación se inicializará y estará escuchando peticiones en **http://localhost:8081**. Hibernate creará automáticamente las tablas necesarias al iniciar la aplicación.

## Pruebas de API
Se incluye una colección lista para importar en Postman y probar los endpoints de la API. La API ahora es **privada y está protegida por JWT**.
1. Abre Postman.
2. Haz clic en **Import** y selecciona el archivo \WorkOrders_Postman_Collection.json\ ubicado en la raíz del proyecto.
3. La colección incluye una variable de entorno \{{base_url}}\ apuntando a \http://localhost:8081\.
4. **IMPORTANTE:** Ve a la carpeta **Auth** y ejecuta la petición **Login**. Esta petición tiene preconfiguradas las credenciales del administrador y un script inteligente que capturará automáticamente el JWT de la respuesta y lo guardará en las variables de la colección (\{{jwt_token}}\). De esta forma, el resto de las peticiones de la colección heredarán automáticamente el token \Bearer\ sin que tengas que copiar y pegar nada.
5. Puedes utilizar la opción 'Run All' o 'Run Collection' de Postman para probar el flujo de negocio completo de una sola vez.