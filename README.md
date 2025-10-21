# Test API - Spring Boot REST API

Una API REST completa desarrollada con Spring Boot que proporciona endpoints para procesamiento de pedidos y obtención de cotizaciones de divisas.

## 📋 Descripción

Este proyecto es una API REST escalable y mantenible construida con Spring Boot que implementa una arquitectura por capas (controller/service/repository/model). La API incluye dos endpoints principales:

- **GET /api/v1/pedido**: Devuelve un mensaje de confirmación de procesamiento de pedido
- **GET /api/v1/cotizacion**: Obtiene cotizaciones de divisas desde una API externa (Bluelytics)

## 🚀 Características

- ✅ **Spring Boot 3.2.0** con Java 17
- ✅ **Arquitectura por capas** (Controller/Service/Repository/Model)
- ✅ **Configuración de PostgreSQL** para futuras operaciones de base de datos
- ✅ **WebClient** para llamadas a APIs externas
- ✅ **Spring DevTools** para desarrollo ágil
- ✅ **Tests unitarios** con JUnit 5 y Mockito
- ✅ **Tests de integración** completos
- ✅ **Documentación** completa en código
- ✅ **Manejo de errores** robusto
- ✅ **Logging** configurado

## 🛠️ Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Web**
- **Spring Data JPA**
- **Spring WebFlux** (para WebClient)
- **PostgreSQL**
- **Maven**
- **JUnit 5**
- **Mockito**
- **Spring DevTools**

## 📁 Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/example/testapi/
│   │   ├── TestApiApplication.java          # Clase principal de Spring Boot
│   │   ├── config/
│   │   │   └── WebClientConfig.java         # Configuración de WebClient
│   │   ├── controller/
│   │   │   ├── PedidoController.java        # Controlador para pedidos
│   │   │   └── CotizacionController.java    # Controlador para cotizaciones
│   │   ├── model/
│   │   │   ├── PedidoResponse.java          # Modelo de respuesta para pedidos
│   │   │   └── CotizacionResponse.java      # Modelo de respuesta para cotizaciones
│   │   └── service/
│   │       ├── PedidoService.java           # Lógica de negocio para pedidos
│   │       └── CotizacionService.java       # Lógica de negocio para cotizaciones
│   └── resources/
│       └── application.properties           # Configuración de la aplicación
└── test/
    ├── java/com/example/testapi/
    │   ├── TestApiApplicationIntegrationTest.java  # Tests de integración
    │   ├── controller/
    │   │   ├── PedidoControllerTest.java    # Tests del controlador de pedidos
    │   │   └── CotizacionControllerTest.java # Tests del controlador de cotizaciones
    │   ├── model/
    │   │   ├── PedidoResponseTest.java       # Tests del modelo de pedidos
    │   │   └── CotizacionResponseTest.java   # Tests del modelo de cotizaciones
    │   └── service/
    │       ├── PedidoServiceTest.java        # Tests del servicio de pedidos
    │       └── CotizacionServiceTest.java     # Tests del servicio de cotizaciones
    └── resources/
        └── application-test.properties       # Configuración para tests
```

## 🔧 Configuración

### Requisitos Previos

- **Java 17** o superior
- **Maven 3.6** o superior
- **PostgreSQL** (opcional, para desarrollo futuro)

### Configuración de Base de Datos

El proyecto está configurado para conectarse a PostgreSQL con los siguientes parámetros:

```properties
spring.datasource.url=jdbc:postgresql://localhost:3006/db-api
spring.datasource.username=pablo
spring.datasource.password=elpass242
```

**Nota**: La base de datos no se utiliza actualmente, pero está configurada para futuras implementaciones.

## 🚀 Instalación y Ejecución

### 1. Clonar el Repositorio

```bash
git clone <repository-url>
cd test-api
```

### 2. Compilar el Proyecto

```bash
mvn clean compile
```

### 3. Ejecutar Tests

```bash
mvn test
```

### 4. Ejecutar la Aplicación

```bash
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

### 5. Compilar JAR Ejecutable

```bash
mvn clean package
java -jar target/test-api-0.0.1-SNAPSHOT.jar
```

## 📚 API Endpoints

### 1. Procesar Pedido

**Endpoint**: `GET /api/v1/pedido`

**Descripción**: Devuelve un mensaje de confirmación de que el pedido fue procesado.

**Respuesta**:
```json
{
  "mensaje": "El pedido fue procesado"
}
```

**Ejemplo de uso**:
```bash
curl -X GET http://localhost:8080/api/v1/pedido
```

### 2. Obtener Cotización

**Endpoint**: `GET /api/v1/cotizacion`

**Descripción**: Obtiene cotizaciones de divisas desde la API externa de Bluelytics.

**Respuesta**:
```json
{
  "oficial": {
    "value_avg": 100.0,
    "value_sell": 100.5,
    "value_buy": 99.5
  },
  "blue": {
    "value_avg": 200.0,
    "value_sell": 200.5,
    "value_buy": 199.5
  },
  "oficial_euro": {
    "value_avg": 110.0,
    "value_sell": 110.5,
    "value_buy": 109.5
  },
  "blue_euro": {
    "value_avg": 220.0,
    "value_sell": 220.5,
    "value_buy": 219.5
  },
  "last_update": "2024-01-01T12:00:00Z"
}
```

**Ejemplo de uso**:
```bash
curl -X GET http://localhost:8080/api/v1/cotizacion
```

## 🧪 Testing

El proyecto incluye una cobertura completa de tests:

### Tests Unitarios

- **PedidoServiceTest**: Tests para la lógica de negocio de pedidos
- **CotizacionServiceTest**: Tests para la lógica de negocio de cotizaciones
- **PedidoControllerTest**: Tests para el controlador de pedidos
- **CotizacionControllerTest**: Tests para el controlador de cotizaciones
- **PedidoResponseTest**: Tests para el modelo de respuesta de pedidos
- **CotizacionResponseTest**: Tests para el modelo de respuesta de cotizaciones

### Tests de Integración

- **TestApiApplicationIntegrationTest**: Tests de integración para todos los endpoints

### Ejecutar Tests

```bash
# Ejecutar todos los tests
mvn test

# Ejecutar tests con cobertura
mvn test jacoco:report

# Ejecutar tests específicos
mvn test -Dtest=PedidoServiceTest
```

## 🔍 Desarrollo

### Spring DevTools

El proyecto incluye Spring DevTools para facilitar el desarrollo:

- **Restart automático** cuando se detectan cambios en el código
- **LiveReload** para recarga automática del navegador
- **Configuración automática** de propiedades de desarrollo

### Logging

El proyecto está configurado con logging detallado:

- **Nivel DEBUG** para la aplicación
- **Nivel DEBUG** para Spring Web
- **Logs estructurados** para facilitar el debugging

### Manejo de Errores

- **Manejo robusto** de excepciones en servicios
- **Respuestas HTTP apropiadas** para diferentes tipos de errores
- **Logging detallado** de errores para debugging

## 🚀 Escalabilidad y Mantenibilidad

### Arquitectura

- **Separación de responsabilidades** por capas
- **Inyección de dependencias** con Spring
- **Configuración externa** mediante properties
- **Patrones de diseño** aplicados (Service, Repository, etc.)

### Extensibilidad

- **Fácil agregado** de nuevos endpoints
- **Configuración modular** para diferentes entornos
- **Base preparada** para integración con base de datos
- **Estructura escalable** para crecimiento futuro

## 📝 Próximos Pasos

1. **Integración con Base de Datos**: Implementar operaciones CRUD con PostgreSQL
2. **Autenticación y Autorización**: Agregar seguridad con Spring Security
3. **Documentación API**: Integrar Swagger/OpenAPI
4. **Monitoreo**: Agregar Actuator para métricas y health checks
5. **Cache**: Implementar cache con Redis
6. **Docker**: Containerizar la aplicación

## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## 👨‍💻 Autor

**Pablo** - [@pablo](https://github.com/pablo)

---

**Nota**: Este proyecto fue desarrollado como una demostración de buenas prácticas en Spring Boot y arquitectura de software escalable.
