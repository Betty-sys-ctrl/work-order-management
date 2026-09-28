# Sistema de Gestión de Órdenes de Trabajo

## Descripción
El **Sistema de Gestión de Órdenes de Trabajo** es una plataforma integral (Backend + Frontend) diseñada para coordinar y administrar órdenes de servicio, asignar técnicos, controlar historiales de estado y gestionar el consumo y stock de materiales en cada orden de trabajo.

## Stack Tecnológico
**Backend:**
- Java 21, Spring Boot 3.4
- Spring Security, JWT, Spring Data JPA, Hibernate, PostgreSQL 16
- Maven, Docker & Docker Compose

**Frontend:**
- **React (v18)** y **Vite**
- **Material-UI (MUI)** para componentes visuales y diseño responsivo
- **React Router** para enrutamiento de vistas SPA
- **Axios** para consumo de API REST
- **React Toastify** para notificaciones interactivas

## Instrucciones de Ejecución Conjunta

### 1. Requisitos Previos
- **Java 21**
- **Node.js** (v18+)
- **Docker Desktop** (para PostgreSQL) abierto y en ejecución.

### 2. Levantar el Backend
Gracias a la integración con spring-boot-docker-compose, Spring Boot levantará automáticamente el contenedor de PostgreSQL y lo detendrá al finalizar. La base de datos se poblará automáticamente mediante un Seeder seguro (Data Seeding).

En una terminal, ejecuta:
`ash
./mvnw clean compile
./mvnw spring-boot:run
`
> La API estará escuchando en **http://localhost:8081**.

### 3. Levantar el Frontend
En una **terminal separada**, dirígete a la carpeta rontend y lanza el servidor de desarrollo de Vite:

`ash
cd frontend
npm install
npm run dev
`
> La interfaz web estará disponible en **http://localhost:5173**.

### 4. Credenciales por Defecto (Acceso)
Para acceder a la plataforma (tanto en el portal web como en la colección de Postman), utiliza las credenciales generadas automáticamente por el Database Seeder:
- **Usuario:** admin
- **Contraseña:** admin123

## Pruebas de API (Postman)
En la raíz del proyecto encontrarás el archivo WorkOrderManagement.postman_collection.json.
Contiene ejemplos estructurados, incluyendo:
- Endpoint de Autenticación (POST /api/auth/login)
- Métricas del Dashboard (GET /api/metrics/dashboard)
- Operaciones de Órdenes (Asignar Técnico, Consumir Material, Transicionar Estado)
- Catálogos paginados (?page=0&size=20) para orders, 	echnicians y materials.