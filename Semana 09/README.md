# Laboratorio semana 09 Autenticación JWT

Aplicación REST con Spring Boot, Spring Security, MySQL y JSON Web Tokens. Permite registrar usuarios con roles, iniciar sesión y proteger rutas según el rol asignado.

## Requisitos

- Java 17 o superior y MySQL 8 en ejecución.
- La aplicación crea la base de datos `security_db` si el usuario de MySQL tiene permiso.

Configura credenciales por variables de entorno si tu instalación no usa `root` sin contraseña:

```powershell
$env:DB_USERNAME="root"
$env:DB_PASSWORD="tu_clave"
$env:JWT_SECRET="una_clave_local_de_al_menos_32_caracteres"
```

También puedes crear previamente la base de datos con `CREATE DATABASE security_db;`. En PowerShell, desde `Semana 09`, ejecuta `.\mvnw.cmd spring-boot:run`. El servicio usa el puerto `8082` por defecto porque los proyectos de semanas anteriores pueden ocupar `8080` o `8081`. Si `8082` también está ocupado, elige otro puerto, por ejemplo `$env:SERVER_PORT="8090"`, antes de iniciar.

Para revisar qué proceso está usando un puerto en Windows, ejecuta `Get-NetTCPConnection -LocalPort 8082 -State Listen` y consulta el PID con `Get-Process -Id <PID>`. Cada instancia de Spring Boot que siga ejecutándose conserva su puerto hasta que se detenga; en la terminal donde se inició, pulsa `Ctrl+C`. No se debe cerrar un proceso que no reconozcas.

## Endpoints

### Registrar administrador

`POST http://localhost:8082/api/auth/signup`

```json
{"username":"admin","email":"admin@test.com","password":"123456","roles":["ADMIN"]}
```

### Registrar usuario

`POST http://localhost:8082/api/auth/signup`

```json
{"username":"user1","email":"user@test.com","password":"123456","roles":["USER"]}
```

Si no se envía `roles`, se asigna `USER`. Para este laboratorio se admite enviar `ADMIN` en el alta. En una aplicación real, el rol administrador debe concederse únicamente desde un proceso confiable.

### Iniciar sesión

`POST http://localhost:8082/api/auth/signin`

```json
{"email":"admin@test.com","password":"123456"}
```

La respuesta incluye `accessToken` y `tokenType: Bearer`. En Postman:

1. Inicia el servicio MySQL de Windows (por ejemplo, desde **Servicios** o el panel de XAMPP) y confirma que escucha en el puerto `3306`.
2. Inicia la API desde la carpeta `Semana 09` con `.\mvnw.cmd spring-boot:run`. Espera el mensaje `Started DemoSecurityJwtApplication`; el servidor HTTP escucha en `8082`.
3. Crea las solicitudes `POST` de signup y signin indicadas arriba. En **Body → raw → JSON**, pega cada objeto. Primero registra un administrador y luego un usuario normal.
4. Para probar lo público, envía `GET http://localhost:8082/api/test/all` sin autorización. Debe responder `Public Content`.
5. Para iniciar sesión como administrador, envía el signup correspondiente una sola vez y luego el signin. Copia el `accessToken` devuelto.
6. Crea `GET http://localhost:8082/api/test/admin`, abre **Authorization → Bearer Token**, pega el token y envía. Debe responder `Admin Content`. Repite con el token del usuario normal en `/api/test/user` (esperado `User Content`) y en `/api/test/admin` (esperado HTTP 403).

Si `8082` ya está ocupado, antes de ejecutar Maven establece `$env:SERVER_PORT="8090"` y reemplaza `8082` por `8090` en las URLs de Postman. Para regresar al puerto normal, cierra esa terminal o ejecuta `Remove-Item Env:SERVER_PORT`.

| Método | Ruta | Acceso | Respuesta esperada |
|---|---|---|---|
| GET | `/api/test/all` | Público | `Public Content` |
| GET | `/api/test/user` | USER o ADMIN | `User Content` |
| GET | `/api/test/admin` | ADMIN | `Admin Content` |

Con el puerto por defecto, usa `http://localhost:8082` como base URL en Postman. Las rutas y cuerpos de ejemplo de arriba se completan con esa base URL.

Sin autenticación, una ruta protegida devuelve HTTP 401. Con un token válido pero sin el rol requerido, devuelve HTTP 403. Un token modificado o expirado no autentica al solicitante y la ruta protegida devuelve HTTP 401.

## Análisis de seguridad JWT

### Caso 1 Ruta pública

1. Devuelve `Public Content`.
2. La respuesta está en `TestController`.
3. La genera `allAccess()`.
4. No exige autenticación porque está marcada como pública en la cadena de seguridad.
5. `SecurityConfig.securityFilterChain()` define `/api/test/all` con `permitAll()`.
6. Al quitar `permitAll()`, la ruta cae en la regla general y exigiría autenticación.
7. `SecurityFilterChain` aplica las reglas de autorización y ordena los filtros de Spring Security.

### Caso 2 Acceso sin token

1. No llega al controlador; Spring Security responde con un cuerpo de error de autenticación.
2. HTTP 401 Unauthorized.
3. `JwtAuthFilter` procesa la cabecera antes del controlador; después la cadena de autorización rechaza el acceso.
4. No se autenticó ningún usuario en el contexto de seguridad.
5. No, el método del controlador no se ejecuta.
6. Participan `JwtAuthFilter` y los filtros de autorización/autenticación de Spring Security.
7. La ruta exige un usuario autenticado y el contexto no contiene autenticación.

### Caso 3 Token inválido

1. La ruta protegida responde HTTP 401 porque no se establece una autenticación.
2. `validateJwtToken()` valida firma y vigencia.
3. Está en `JwtUtils`.
4. Se usa JJWT (`io.jsonwebtoken`).
5. JJWT puede producir `JwtException` (por ejemplo, `SignatureException` o `ExpiredJwtException`); `validateJwtToken()` las captura y devuelve `false`.
6. Sin token no se intenta validar uno; con token inválido se intenta validar y se rechaza. Ambos dejan la solicitud sin autenticar.
7. `JwtAuthFilter` continúa la cadena sin establecer un usuario; Spring Security bloquea el endpoint protegido.

### Caso 4 Acceso con token válido

1. Devuelve `User Content`.
2. Se ejecuta `userAccess()`.
3. `JwtAuthFilter` extrae y valida el token, carga al usuario y registra la autenticación.
4. Carga el email del claim `subject` mediante `UserDetailsServiceImpl`.
5. La autenticación se guarda en `SecurityContextHolder`.
6. Flujo: Postman envía `Authorization: Bearer <token>` → `JwtAuthFilter` extrae el token → `JwtUtils` verifica firma y expiración → se carga el usuario y sus roles → `SecurityContextHolder` recibe `Authentication` → Spring Security autoriza → `TestController` responde.
7. Un token expirado se rechaza, no crea autenticación y la ruta protegida responde HTTP 401.

### Caso 5 Usuario USER en ruta ADMIN

1. La API responde acceso denegado.
2. HTTP 403 Forbidden: el usuario sí se autenticó, pero carece del rol requerido.
3. El control se configura en `SecurityConfig`.
4. `securityFilterChain()` usa `hasRole("ADMIN")` para `/api/test/admin`.
5. `UserDetailsImpl.build()` convierte los roles persistidos a autoridades `ROLE_USER` o `ROLE_ADMIN`; luego viajan en `Authentication`.
6. Las autoridades están en la colección `GrantedAuthority` del `Authentication`.
7. Autenticación identifica quién es el usuario; autorización decide qué puede hacer.
8. `ROLE_USER` no satisface la regla `ROLE_ADMIN`.

### Caso 6 Usuario ADMIN en ruta ADMIN

1. Devuelve `Admin Content`.
2. Se ejecuta `adminAccess()`.
3. Se validaron la firma y expiración del JWT, se cargó el usuario y se verificó que la autenticación contenga `ROLE_ADMIN`.
4. El usuario tiene rol `ADMIN`.
5. `SecurityContextHolder` mantiene el objeto `Authentication` durante la solicitud.
6. Flujo: Postman → cabecera Bearer → `JwtAuthFilter` → `JwtUtils` → carga de usuario/roles → `SecurityContextHolder` → autorización ADMIN → controlador.
7. `JwtUtils.generateJwtToken()` genera el token al iniciar sesión.

## Observaciones

1. Se creó la base de datos `security_db` antes de iniciar el backend.
2. Las credenciales de MySQL dependen de la instalación local y pueden establecerse con variables de entorno.
3. Las contraseñas se guardan con BCrypt; no se almacenan en texto plano.
4. Las rutas protegidas requieren enviar el token con el prefijo `Bearer`.
5. Un token vencido o alterado no establece autenticación; un rol insuficiente produce HTTP 403.

## Conclusiones

1. JWT permite autenticar solicitudes REST sin mantener una sesión de servidor.
2. Spring Security valida la autenticación y aplica autorización usando las autoridades del usuario.
3. Separar `JwtAuthFilter`, `JwtUtils` y `UserDetailsServiceImpl` facilita identificar cada parte del flujo.
4. BCrypt protege las contraseñas, mientras la firma HMAC protege la integridad del token.
5. La práctica distingue los errores de autenticación (401) de los errores de permisos (403), lo que facilita diagnosticar el acceso.


