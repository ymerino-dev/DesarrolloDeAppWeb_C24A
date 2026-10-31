# Desarrollo de Aplicaciones Web C24A

Repositorio organizado por semanas para el curso de Desarrollo de Aplicaciones Web. En esta entrega se incorpora el laboratorio 08: pruebas automatizadas para Productos y el módulo CRUD de Categorías.

> **Contenido disponible:** el proyecto proporcionado para esta entrega corresponde a la Semana 08. Las carpetas de semanas anteriores se añadirán cuando se incorporen sus respectivos materiales.

## Estructura

```text
DesarrolloAppWeb_C24A/
├── README.md
└── Semana 08/
    └── demo01/
        ├── .mvn/wrapper/              # Configuración de Maven Wrapper
        ├── pom.xml                    # Dependencias y compilación Java 17
        └── src
            ├── main
            │   ├── java/com/tecsup
            │   │   ├── controller/    # Endpoints REST de Productos y Categorías
            │   │   ├── dto/           # Objetos de transferencia y validaciones
            │   │   ├── exception/     # Excepciones y manejador global
            │   │   ├── model/         # Entidades JPA
            │   │   ├── repository/    # Repositorios Spring Data JPA
            │   │   └── service/       # Lógica de negocio
            │   └── resources/
            │       └── application.properties
            └── test
                ├── java/com/tecsup
                │   ├── controller/    # Pruebas de integración con MockMvc
                │   └── service/       # Pruebas unitarias con Mockito
                └── resources/
                    └── application.properties  # H2 aislado para pruebas
```

## Semana 08

### Productos

- CRUD REST en `/api/productos`.
- Validación de nombre obligatorio, precio no negativo y stock no negativo.
- Pruebas unitarias para listar, guardar, obtener, actualizar, eliminar y producto inexistente.
- Pruebas de integración para POST, GET, validación, PUT y DELETE.

### Categorías

- CRUD REST en `/api/categorias`.
- Entidad, DTO, repositorio, servicio y controlador.
- Validación del nombre obligatorio y límites de longitud.
- Excepción global para recursos inexistentes y errores de validación.
- Pruebas unitarias con Mockito y pruebas de integración con MockMvc.

## Tecnologías

| Tecnología | Versión / uso |
|---|---|
| Java | 17 como versión de compilación |
| Spring Boot | 3.5.6 |
| Spring Web | API REST |
| Spring Data JPA | Persistencia |
| MySQL Connector/J | Base de datos para ejecución local |
| H2 | Base en memoria aislada para pruebas |
| JUnit 5, Mockito y MockMvc | Pruebas unitarias y de integración |
| Maven Wrapper | Maven 3.9.16 |

## Requisitos

- JDK 17 o posterior.
- MySQL local para ejecutar la aplicación con la configuración de `application.properties`.
- Maven no necesita instalación global: se usa el wrapper incluido.

## Ejecutar y probar

Desde PowerShell:

```powershell
Set-Location "Semana 08\demo01"
.\mvnw.cmd test
.\mvnw.cmd spring-boot:run
```

Las pruebas usan H2 y no requieren que MySQL esté iniciado. La aplicación usa MySQL en `localhost:3306`, base de datos `db_semana05`, según la configuración local de `application.properties`.

## Rutas del API

| Método | Ruta | Acción |
|---|---|---|
| GET | `/api/productos` | Listar productos |
| GET | `/api/productos/{id}` | Obtener producto |
| POST | `/api/productos` | Crear producto |
| PUT | `/api/productos/{id}` | Actualizar producto |
| DELETE | `/api/productos/{id}` | Eliminar producto |
| GET | `/api/categorias` | Listar categorías |
| GET | `/api/categorias/{id}` | Obtener categoría |
| POST | `/api/categorias` | Crear categoría |
| PUT | `/api/categorias/{id}` | Actualizar categoría |
| DELETE | `/api/categorias/{id}` | Eliminar categoría |

## Commits de la entrega

1. `Semana 08: preparar proyecto y probar API de productos`
2. `Semana 08: implementar CRUD y validaciones de categorías`
3. `Semana 08: agregar pruebas de categoría`
4. `Semana 08: documentar estructura y ejecución del laboratorio`