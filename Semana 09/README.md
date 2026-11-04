# 🛡️ Semana 09: Autenticación JWT y Autorización por Roles con Spring Boot y MySQL

## 📝 Presentación y Autoría
- **Autor / Estudiante:** ymerino-dev
- **Curso:** Desarrollo de Aplicaciones Web
- **Institución:** Tecsup
- **Laboratorio:** Semana 09 - Autenticación y Autorización RESTful

Este repositorio contiene la implementación práctica del laboratorio de la **Semana 09**. En esta sesión se abordó el desarrollo e integración de un esquema de seguridad sin estado (**Stateless**) en una arquitectura RESTful. Se migró el control de sesiones tradicional a un mecanismo robusto basado en **JSON Web Tokens (JWT)** con firma criptográfica HS256, gestionando la persistencia de usuarios y roles en un servidor **MySQL** mediante **Spring Data JPA**.

---

## 🎯 Objetivos del Laboratorio
1. **Implementar Autenticación Stateless:** Desacoplar la gestión de sesiones del servidor mediante la generación, firma y validación de tokens de acceso JWT (JJWT).
2. **Aplicar Control de Acceso Basado en Roles (RBAC):** Restringir dinámicamente el acceso a endpoints según el rol asignado (`ROLE_USER`, `ROLE_ADMIN`, `ROLE_MODERATOR`).
3. **Persistir Datos de Seguridad con JPA:** Mapear y administrar las entidades relacionales de usuarios y roles en MySQL utilizando Hibernate/Spring Data JPA.
4. **Garantizar el Cifrado de Credenciales:** Implementar el cifrado unidireccional de contraseñas en la base de datos utilizando el algoritmo **BCrypt** (`BCryptPasswordEncoder`).
5. **Validación y Pruebas de API:** Verificar los flujos de registro (`/signup`), autenticación (`/signin`) y acceso a rutas protegidas adjuntando el encabezado `Authorization: Bearer <token>` a través de Postman.

---

## 🚀 Tecnologías y Herramientas Utilizadas

### Backend & Seguridad
- **Java 17 (JDK):** Entorno de ejecución y lenguaje base del backend.
- **Spring Boot 3.1.5:** Framework principal para la creación de la API REST.
- **Spring Security:** Gestión de la cadena de filtros de seguridad, codificación de contraseñas y control de autorización.
- **Spring Data JPA & Hibernate:** Mapeo objeto-relacional para persistencia de datos.
- **JJWT (`io.jsonwebtoken` v0.11.5):** Librería para la generación, firma y validación de tokens JWT.
- **Lombok & Validation:** Reducción de código repetitivo y validación de DTOs.

### Base de Datos y Entorno
- **MySQL Server (v8.0 / 8.4):** Sistema gestor de base de datos relacional.
- **SQLyog:** Cliente gráfico de administración para MySQL[cite: 1].
- **Maven Wrapper (`mvnw.cmd`):** Empaquetador portable para compilar y ejecutar la aplicación sin requerir instalación global de Maven[cite: 1].
- **Git & GitHub:** Control de versiones y alojamiento del repositorio académico.

---

## 📂 Estructura del Proyecto (`demo_Security_JWT`)

```text
Semana 09/
├── demo_Security_JWT/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/tecsup/
│   │   │   │   ├── config/
│   │   │   │   │   └── SecurityConfig.java          # Configuración global de Spring Security y gestión de CORS/CSRF
│   │   │   │   ├── controller/
│   │   │   │   │   ├── AuthController.java          # Endpoints públicos (/signup, /signin)
│   │   │   │   │   └── TestController.java          # Endpoints protegidos con verificación de roles
│   │   │   │   ├── dto/
│   │   │   │   │   ├── JwtResponse.java             # DTO de respuesta con el Token al autenticarse
│   │   │   │   │   ├── LoginRequest.java            # DTO de entrada para credenciales
│   │   │   │   │   ├── MessageResponse.java          # DTO para respuestas genéricas en JSON
│   │   │   │   │   └── SignupRequest.java           # DTO para registro de datos de nuevo usuario
│   │   │   │   ├── entity/
│   │   │   │   │   ├── Role.java                    # Mapeo de la tabla 'roles'
│   │   │   │   │   └── User.java                    # Mapeo de la tabla 'users' y relación N:M con roles
│   │   │   │   ├── jwt/
│   │   │   │   │   ├── JwtAuthFilter.java           # Filtro que intercepta peticiones y valida el Bearer Token
│   │   │   │   │   └── JwtUtils.java                # Utilidades para firmar, parsear y validar JWT
│   │   │   │   ├── repository/
│   │   │   │   │   ├── RoleRepository.java          # Consultas JPA para la entidad Role
│   │   │   │   │   └── UserRepository.java          # Consultas JPA para la entidad User
│   │   │   │   └── service/
│   │   │   │   │   ├── UserDetailsImpl.java         # Adaptador que convierte la entidad User a UserDetails de Spring Security
│   │   │   │   │   └── UserDetailsServiceImpl.java  # Servicio para cargar usuarios por email/username
│   │   │   │   └── DemoSecurityJwtApplication.java # Clase principal de arranque de Spring Boot
│   │   │   └── resources/
│   │   │       └── application.properties           # Conexión DB, propiedades de JPA y firma JWT
│   ├── mvnw.cmd                                     # Ejecutable ejecutor de Maven en Windows
│   └── pom.xml                                      # Configuración de dependencias de Maven
└── README.md                                        # Documentación formal de la Semana 09

