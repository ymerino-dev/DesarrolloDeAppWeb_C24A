# Semana 08 Laboratorio 08

## Implementación de pruebas y módulo Categoría

Este laboratorio completa pruebas unitarias y de integración para el API de Productos e incorpora el CRUD de Categorías con validaciones y manejo de excepciones.

## Proyecto

El código fuente está en [`demo01`](demo01/).

```text
Semana 08/
├── README.md
└── demo01/
    ├── pom.xml
    ├── .mvn/wrapper/
    └── src/
        ├── main/
        │   ├── java/com/tecsup/
        │   │   ├── controller/    # Endpoints de Productos y Categorías
        │   │   ├── dto/           # DTOs y restricciones de validación
        │   │   ├── exception/     # Excepción 404 y manejador global
        │   │   ├── model/         # Entidades JPA
        │   │   ├── repository/    # Interfaces Spring Data JPA
        │   │   └── service/       # Lógica CRUD
        │   └── resources/
        │       └── application.properties
        └── test/
            ├── java/com/tecsup/
            │   ├── controller/    # Pruebas de integración MockMvc
            │   └── service/       # Pruebas unitarias Mockito
            └── resources/
                └── application.properties  # H2 para pruebas
```

## Trabajo realizado

### Productos

- CRUD REST bajo `/api/productos`.
- Validaciones: nombre obligatorio; precio y stock mayores o iguales a cero.
- Pruebas unitarias para listar, guardar, obtener, actualizar, eliminar y producto inexistente.
- Pruebas de integración para POST, GET, validación, PUT y DELETE.

### Categorías

- CRUD REST bajo `/api/categorias`.
- Entidad, DTO, repositorio, servicio y controlador.
- Validación de nombre obligatorio y longitud de campos.
- Manejo global de recursos inexistentes (404) y errores de validación (400).
- Pruebas unitarias y de integración del CRUD y validación.

## Tecnologías

| Tecnología | Versión o uso |
|---|---|
| Java | 17 como nivel de compilación |
| Spring Boot | 3.5.6 |
| Spring Web y Spring Data JPA | API REST y persistencia |
| MySQL Connector/J | Base de datos al ejecutar la aplicación |
| H2 | Base en memoria para pruebas |
| JUnit 5, Mockito y MockMvc | Pruebas |
| Maven Wrapper | Maven 3.9.16 |

## Ejecutar

Se requiere JDK 17 o posterior. Para ejecutar la aplicación, configura y activa una instancia local de MySQL según `demo01/src/main/resources/application.properties`.

En PowerShell:

```powershell
Set-Location "Semana 08\demo01"
.\mvnw.cmd test
.\mvnw.cmd spring-boot:run
```

Las pruebas usan H2 y no necesitan MySQL. La suite verificada contiene **20 pruebas** y terminó sin fallos.

## Endpoints

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