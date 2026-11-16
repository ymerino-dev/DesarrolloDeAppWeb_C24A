# 🚀 Desarrollo de Aplicaciones Web (C24A) - Semana 11: Arquitectura de Microservicios

<p align="center">
  <b>Institución:</b> TECSUP | <b>Curso:</b> Desarrollo de Aplicaciones Web (C24A)<br>
  <b>Arquitectura:</b> Microservicios distribuidos con Spring Boot, Spring Data JPA y MySQL.
</p>

---

## 📋 Tabla de Contenidos
1. [Resumen Técnico y Modificaciones](#-resumen-técnico-y-modificaciones)
2. [Arquitectura del Proyecto](#-arquitectura-del-proyecto)
3. [Stack Tecnológico y Prerrequisitos](#-stack-tecnológico-y-prerrequisitos)
4. [Estructura de Directorios](#-estructura-de-directorios)
5. [Guía de Configuración y Ejecución](#-guía-de-configuración-y-execution)
6. [Control de Versiones y Sincronización](#-control-de-versiones-y-sincronización)

---

## 🛠️ Resumen Técnico y Modificaciones

Durante el desarrollo de la Semana 11, se implementaron mejoras estructurales y de control de versiones orientadas a buenas prácticas en entornos profesionales:

* **Consolidación de Directorios:** Reestructuración de la jerarquía del proyecto, unificando los microservicios bajo el directorio maestro del curso (`Semana 11/microservicios/`) para mantener una única fuente de verdad y evitar duplicidades locales.
* **Normalización del Control de Versiones (Git):** Limpieza de repositorios internos independientes (`.git` anidados) y unificación del historial bajo el repositorio raíz mediante estrategias de integración segura (`--allow-unrelated-histories` y rebase).
* **Configuración Multi-entorno (YAML):** Migración de los archivos estáticos `application.properties` a una arquitectura modular basada en perfiles (`application.yml` para control central y `application-dev.yml` para parámetros de desarrollo y conexión a bases de datos específicas).
* **Estandarización de Capas (Layered Architecture):** Implementación de paquetes limpios bajo el estándar de Spring Boot (`model`, `repository`, `service`, `controller`, `dto`).

---

## 🏛️ Arquitectura del Proyecto

El sistema está compuesto por microservicios desacoplados, cada uno conectado a su propia instancia de base de datos relacional para garantizar independencia operativa:

| Microservicio | Puerto por Defecto | Base de Datos | Estado Actual |
| :--- | :---: | :---: | :---: |
| **`categoria-service`** | `8081` | `bd_categoria_dev` | Implementado y Configurado |
| **`producto-service`** | `8083` | `bd_producto_dev` | En Desarrollo / Estructura Base |
| **`cliente-service`** | `8082` | `bd_cliente_dev` | Estructura Base |
| **`pedido-service`** | `8084` | `bd_pedido_dev` | Estructura Base |

---

## ⚙️ Stack Tecnológico y Prerrequisitos

Para levantar y compilar exitosamente este proyecto en tu entorno local, asegúrate de contar con las siguientes herramientas:

* **Java JDK:** Versión 17 o superior.
* **Spring Boot:** Versión 3.x (con dependencias de *Spring Web*, *Spring Data JPA* y *MySQL Driver*).
* **Gestor de Base de Datos:** MySQL Server (vía XAMPP, SQLyog o MySQL Workbench).
* **Apache Maven:** Herramienta de gestión de dependencias y empaquetado (compatible con el wrapper incluido `mvnw`).
* **Entorno de Desarrollo:** IntelliJ IDEA (Ultimate o Community).
* **Control de Versiones:** Git & GitHub.

---

## 📂 Estructura de Directorios

```text
DesarrolloDeAppWeb_C24A/
│
├── Semana 10/
├── Semana 11/
│   └── microservicios/
│       ├── categoria-service/
│       │   ├── src/main/java/com/tecsup/
│       │   │   ├── controller/
│       │   │   ├── dto/
│       │   │   ├── model/
│       │   │   ├── repository/
│       │   │   └── service/
│       │   ├── src/main/resources/
│       │   │   ├── application.yml
│       │   │   └── application-dev.yml
│       │   └── pom.xml
│       ├── producto-service/
│       ├── cliente-service/
│       └── pedido-service/
├── .gitignore
└── README.md
```

---

## 🚀 Guía de Configuración y Ejecución

### 1. Creación de Bases de Datos en MySQL
Abre tu gestor de base de datos y ejecuta los scripts de inicialización:
```sql
CREATE DATABASE IF NOT EXISTS bd_categoria_dev;
CREATE DATABASE IF NOT EXISTS bd_producto_dev;
```

### 2. Apertura del Proyecto en IntelliJ
1. Abre **IntelliJ IDEA**.
2. Selecciona **File > Open** y elige la carpeta raíz **`DesarrolloDeAppWeb_C24A`** (esto cargará toda la estructura de la semana de forma unificada).
3. Espera a que Maven descargue e indexe las dependencias del archivo `pom.xml`.

### 3. Ejecución del Microservicio
1. Navega hasta la clase principal: `com.tecsup.CategoriaServiceApplication`.
2. Haz clic en el botón **Run** (ícono verde de reproducción).
3. Verifica en la consola que el servidor Spring Boot haya iniciado correctamente en el puerto asignado.

---

## 📌 Sincronización y Commit Final

Para guardar y actualizar tu repositorio remoto con esta nueva presentación profesional, ejecuta los siguientes comandos en tu terminal ubicada en la raíz del proyecto:

```powershell
git add README.md
git commit -m "docs: actualizar README profesional para la Semana 11"
git push origin main
```