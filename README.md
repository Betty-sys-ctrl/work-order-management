# Work Order Management System

## Descripción
El **Sistema de Gestión de Órdenes de Trabajo** es una API RESTful robusta diseñada para coordinar y administrar órdenes de servicio, asignar técnicos, controlar historiales de estado y gestionar el consumo y stock de materiales utilizados en cada orden de trabajo.

## Stack Tecnológico
- **Lenguaje:** Java 21
- **Framework:** Spring Boot 3.4
- **Base de Datos:** PostgreSQL 16
- **Contenedores:** Docker & Docker Compose
- **Dependencias Principales:** Maven, Spring Data JPA, Hibernate, Jakarta Validation, Lombok.

## Requisitos Previos
- **Java Development Kit (JDK):** Versión 21 o superior.
- **Docker Desktop:** Para ejecutar el contenedor de base de datos PostgreSQL localmente.

## Instalación y Ejecución

### 1. Ejecutar la Aplicación (Zero-Touch Initialization)
Gracias a la integración con spring-boot-docker-compose, el único requisito es tener **Docker Desktop abierto**. Al ejecutar la aplicación, Spring Boot detectará automáticamente el archivo docker-compose.yml, levantará el contenedor de PostgreSQL y lo detendrá al finalizar la ejecución.

La base de datos se poblará de forma automática (Data Seeding) con los registros necesarios para que la colección de Postman funcione sin intervención manual.

Ejecuta:
\\\ash
./mvnw clean compile
./mvnw spring-boot:run
\\\
> **Nota:** La aplicación se inicializará y estará escuchando peticiones en **http://localhost:8081**. Hibernate creará automáticamente las tablas necesarias al iniciar la aplicación.

## Pruebas de API
Se incluye una colección lista para importar en Postman y probar los endpoints de la API.
1. Abre Postman.
2. Haz clic en **Import** y selecciona el archivo \WorkOrders_Postman_Collection.json\ ubicado en la raíz del proyecto.
3. La colección ya incluye una variable de entorno \{{base_url}}\ preconfigurada para apuntar a \http://localhost:8081\.
4. Todos los payloads (cuerpos de petición JSON) cumplen con las validaciones de negocio implementadas en los DTOs. Puedes usar la opción 'Run All' de Postman.