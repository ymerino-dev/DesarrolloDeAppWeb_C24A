\# Semana 09: Autenticación JWT y Autorización por Roles



En esta sesión se implementó la seguridad y autenticación mediante \*\*JSON Web Tokens (JWT)\*\* y autorización basada en roles (`USER`, `ADMIN`, `MODERATOR`) en un backend desarrollado con \*\*Spring Boot 3\*\* y \*\*MySQL\*\*\[cite: 1].



\## 🛠️ Tecnologías Empleadas

\- \*\*Java 17\*\*\[cite: 1]

\- \*\*Spring Boot 3\*\* (Spring Security, Spring Data JPA, Spring Web)\[cite: 1]

\- \*\*JJWT (io.jsonwebtoken 0.11.5)\*\*\[cite: 1]

\- \*\*MySQL Server \& SQLyog\*\*\[cite: 1]

\- \*\*Postman\*\* (Pruebas de Endpoints)\[cite: 1]



\## 📂 Estructura del Proyecto (`demo\_Security\_JWT`)

\- `config/`: Configuración del filtro de seguridad (`SecurityConfig`) y cifrado BCrypt\[cite: 1].

\- `controller/`: Endpoints de autenticación (`AuthController`) y recursos protegidos (`TestController`)\[cite: 1].

\- `dto/`: Transferencia de datos (`LoginRequest`, `SignupRequest`, `JwtResponse`, `MessageResponse`)\[cite: 1].

\- `entity/`: Entidades de base de datos (`User`, `Role`)\[cite: 1].

\- `jwt/`: Generación, parsing y validación del token JWT (`JwtUtils`, `JwtAuthFilter`)\[cite: 1].

\- `repository/`: Interfaces de acceso a datos JPA (`UserRepository`, `RoleRepository`)\[cite: 1].

\- `service/`: Manejo de detalles del usuario en Spring Security (`UserDetailsServiceImpl`, `UserDetailsImpl`)\[cite: 1].



\## 🔐 Endpoints Principales

| Método | Endpoint | Acceso | Descripción |

| :--- | :--- | :--- | :--- |

| `POST` | `/api/auth/signup` | Público | Registro de usuarios con asignación de roles\[cite: 1]. |

| `POST` | `/api/auth/signin` | Público | Login de usuarios y generación del Bearer Token JWT\[cite: 1]. |

| `GET` | `/api/test/all` | Público | Contenido público sin autenticación\[cite: 1]. |

| `GET` | `/api/test/user` | Autenticado (`USER`, `ADMIN`) | Requiere cabecera `Authorization: Bearer <token>`\[cite: 1]. |

| `GET` | `/api/test/admin` | Autenticado (`ADMIN`) | Requiere rol `ADMIN` para acceder\[cite: 1]. |

