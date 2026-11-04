## 🛠️ Instrucciones Paso a Paso para Desplegar el Proyecto

Si vas a levantar este proyecto en otra máquina, sigue atentamente estos tres pasos:

### 1. Inicialización de la Base de Datos en MySQL
Abre tu cliente de base de datos (SQLyog, MySQL Workbench o la terminal de MySQL) y ejecuta el siguiente script SQL para crear la base de datos e insertar los roles por defecto:

```sql
CREATE DATABASE IF NOT EXISTS security_db;
USE security_db;

CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

INSERT INTO roles (name) VALUES ('USER'), ('ADMIN'), ('MODERATOR');
```

### 2. Configuración de Credenciales
Revisa el archivo `src/main/resources/application.properties` y verifica que el usuario y la contraseña coincidan con tu servidor MySQL local:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/security_db
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Clave secreta para la firma del Token JWT (mínimo 256 bits)
jwt.secret=clave_super_segura_clave_super_segura_para_firmar_tokens
jwt.expiration=3600000
```

### 3. Compilación y Ejecución desde el CMD
Abre la consola de comandos (CMD), dirígete a la carpeta del proyecto y ejecuta el wrapper de Maven:

```cmd
cd "Semana 09\demo_Security_JWT"
mvnw.cmd spring-boot:run
```

*Verificación:* La aplicación estará lista y escuchando peticiones HTTP en `http://localhost:8080`.

---

## 🔑 Especificación y Matriz de Endpoints REST

La siguiente tabla detalla la especificación de la API RESTful implementada, indicando los niveles de protección y la función de cada ruta:

| Método | Endpoint | Permiso / Acceso | Descripción y Uso |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/auth/signup` | Público | Registra nuevos usuarios en la BD y les asigna los roles indicados en el cuerpo JSON (`USER`, `ADMIN`, etc.). |
| `POST` | `/api/auth/signin` | Público | Valida las credenciales (username/password) y genera el Token JWT retornado en el cuerpo de la respuesta. |
| `GET` | `/api/test/all` | Público | Endpoint libre de prueba accesible por cualquier cliente sin cabecera de autenticación. |
| `GET` | `/api/test/user` | `USER` / `ADMIN` | Recurso protegido. Requiere la cabecera HTTP `Authorization: Bearer <token>` enviando un token válido. |
| `GET` | `/api/test/admin` | `ADMIN` | Recurso protegido exclusivo. Verifica que el usuario autenticado cuente con el rol específico de Administrador. |