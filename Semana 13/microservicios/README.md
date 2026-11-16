# 🏛️ Documentación Técnica: Arquitectura de Microservicios Distribuida con Spring Cloud

---

## 📋 1. Resumen Ejecutivo y Alcance
El presente documento detalla el diseño, la implementación y el despliegue de una arquitectura orientada a microservicios basada en **Spring Boot** y **Spring Cloud**. El sistema implementa un modelo de diseño desacoplado que garantiza la escalabilidad horizontal, el descubrimiento dinámico de servicios mediante **Netflix Eureka** y la centralización de peticiones a través de un **API Gateway**.

---

## 🏗️ 2. Topología y Componentes del Sistema

El ecosistema se encuentra estructurado bajo un esquema de red local, asignando responsabilidades específicas a cada módulo:

| Componente | Nombre del Módulo | Puerto | Rol Arquitectónico |
| :--- | :--- | :--- | :--- |
| **Service Discovery** | `ms-eureka-server` | `8761` | Servidor central de registro y monitoreo de instancias activas. |
| **API Gateway** | `gateway-service` | `8080` | Pasarela única de entrada, enrutamiento dinámico y balanceo de carga (`lb://`). |
| **Backend Service** | `categoria-service` | Dinámico | Microservicio de dominio para la gestión y persistencia de categorías. |
| **Backend Service** | `producto-service` | Dinámico | Microservicio de dominio para la gestión del catálogo de productos. |
| **Backend Service** | `cliente-service` | Dinámico | Microservicio de gestión de clientes *(Módulo de Actividad)*. |
| **Backend Service** | `pedido-service` | Dinámico | Microservicio de procesamiento de órdenes *(Módulo de Actividad)*. |

---

## ⚙️ 3. Especificaciones Técnicas y Restricciones de Entorno

*   **Lenguaje Base:** Java 17 (Estandarizado en todos los módulos para garantizar compatibilidad de compilación y ejecución).
*   **Framework Core:** Spring Boot (v3.x / v3.3.5 LTS).
*   **Orquestador Cloud:** Spring Cloud (Ecosistema Netflix OSS).
*   **Persistencia de Datos:** Spring Data JPA conectado a bases de datos relacionales (MySQL).
*   **Herramientas de Construcción:** Apache Maven con gestión centralizada de dependencias mediante `dependencyManagement`.

---

## 🚀 4. Protocolo de Arranque y Despliegue Local

Para evitar fallos de conectividad (`Connection Refused` o errores `503 Service Unavailable`), los servicios deben inicializarse respetando estrictamente el siguiente orden de precedencia:

> **Nota de Arquitectura:** El API Gateway y los microservicios de backend dependen del registro previo en Eureka. Intentar levantar las pasarelas o servicios de negocio sin el servidor de descubrimiento activo generará excepciones de enlace en el socket.

1. **Fase 1: Servidor de Descubrimiento**
    * Ejecutar `ms-eureka-server` en el puerto `8761`.
    * *Validación:* Verificar el panel de administración en `http://localhost:8761`.

2. **Fase 2: Microservicios de Dominio (Backend)**
    * Iniciar `categoria-service`, `producto-service`, `cliente-service` y `pedido-service`.
    * *Validación:* Comprobar que cada módulo efectúe su registro exitoso (`Status: UP`) en el dashboard de Eureka.

3. **Fase 3: Pasarela de Entrada (API Gateway)**
    * Ejecutar `gateway-service` en el puerto `8080`.

---

## 🌐 5. Matriz de Enrutamiento y Endpoints (API Gateway - Port 8080)

Las solicitudes externas consumen los recursos de manera unificada a través de la pasarela, la cual traduce y balancea las peticiones hacia los nodos correspondientes:

*   **Gestión de Categorías:**  
    `GET /api/categorias` ➔ Redirigido a `lb://categoria-service`
*   **Gestión de Productos:**  
    `GET /api/productos` ➔ Redirigido a `lb://producto-service`
*   **Gestión de Clientes (Actividad):**  
    `GET /api/clientes` ➔ Redirigido a `lb://cliente-service`
*   **Procesamiento de Pedidos (Actividad):**  
    `GET /api/pedidos` ➔ Redirigido a `lb://pedido-service`

---

## 💡 6. Conclusiones Técnicas

1. **Centralización del Descubrimiento de Servicios:**  
   La implementación de Netflix Eureka posibilitó un acoplamiento dinámico, permitiendo que los microservicios se autorregistren y actualicen su estado de salud de manera transparente, eliminando la necesidad de hardcodear direcciones IP o puertos fijos.
2. **Optimización mediante API Gateway:**  
   Spring Cloud Gateway funcionó como una capa de abstracción robusta, centralizando las reglas de enrutamiento y simplificando la exposición de los recursos del backend hacia los clientes externos.
3. **Estandarización Tecnológica:**  
   La unificación del ecosistema bajo **Java 17** y la correcta alineación de las versiones de Spring Cloud mitigaron los conflictos de compilación y aseguraron la estabilidad del entorno de ejecución.
4. **Tolerancia a Fallos y Escalabilidad:**  
   El uso del balanceo de carga integrado (`lb://`) permite escalar horizontalmente los microservicios de backend sin modificar la configuración de las rutas de entrada.
5. **Validación de Arquitecturas Distribuidas:**  
   Las pruebas funcionales de extremo a extremo demostraron una comunicación fluida y síncrona a través del protocolo HTTP, cumpliendo con los estándares de diseño de microservicios modernos.

---

## ✒️ Control de Versiones y Autoría

* **Desarrolladora:** Ana Yanira Merino Ramos
* **Especialidad:** Diseño y Desarrollo de Software (4to Ciclo)
* **Institución:** Tecsup
* **Repositorio Oficial:** [ymerino-dev/DesarrolloDeAppWeb_C24A](https://github.com/ymerino-dev/DesarrolloDeAppWeb_C24A)