# Endpoint Diferencias - Documentación de Uso

## Descripción
El endpoint `/api/v1/diferencias` calcula las diferencias entre los valores MEP y crypto, devolviendo la resta (MEP - Crypto) para cada tipo de cambio. Utiliza una estructura EnumMap para mejor escalabilidad y performance.

## Endpoint
- **URL**: `POST /api/v1/diferencias`
- **Content-Type**: `application/json`

## Estructura del Request (EnumMap)
```json
{
  "rates": {
    "crypto": {
      "value_avg": 940.0,
      "value_sell": 945.0,
      "value_buy": 935.0
    },
    "mep": {
      "value_avg": 1250.0,
      "value_sell": 1260.0,
      "value_buy": 1240.0
    }
  }
}
```

### Campos del Request:
- `rates` (objeto, **requerido**): Mapa que contiene los tipos de exchange rate
  - `crypto` (objeto, **requerido**): Datos de exchange rate para criptomonedas
    - `value_avg` (número, **requerido**): Valor promedio. Debe ser positivo.
    - `value_sell` (número, **requerido**): Valor de venta. Debe ser positivo.
    - `value_buy` (número, **requerido**): Valor de compra. Debe ser positivo.
  - `mep` (objeto, **requerido**): Datos de exchange rate para MEP (Mercado Electrónico de Pagos)
    - `value_avg` (número, **requerido**): Valor promedio. Debe ser positivo.
    - `value_sell` (número, **requerido**): Valor de venta. Debe ser positivo.
    - `value_buy` (número, **requerido**): Valor de compra. Debe ser positivo.

## Estructura del Response (Éxito)
```json
{
  "diferencia_avg": 310.0,
  "diferencia_sell": 315.0,
  "diferencia_buy": 305.0
}
```

## 🚫 **Error Responses**

### **400 Bad Request - Diferencias Negativas**
Si algún valor crypto es mayor que el MEP correspondiente, se devuelve error 400 con detalles:

```json
{
  "error": "Negative differences detected",
  "message": "Negative differences found for items: avg",
  "timestamp": "2025-10-21T18:25:17.359664280Z"
}
```

### **400 Bad Request - Datos Inválidos**
Si faltan campos requeridos o hay valores inválidos:

```json
{
  "error": "Validation error",
  "message": "Rates map cannot be null",
  "timestamp": "2025-10-21T18:25:17.359664280Z"
}
```

### **415 Unsupported Media Type**
Si el `Content-Type` header no es `application/json`:

```http
HTTP/1.1 415 Unsupported Media Type
Content-Length: 0
```

### **500 Internal Server Error**
Para errores inesperados del servidor:

```json
{
  "error": "Internal server error",
  "message": "An unexpected error occurred",
  "timestamp": "2025-10-21T18:25:17.359664280Z"
}
```

## Ejemplo de Uso con cURL
```bash
curl -X POST http://localhost:8080/api/v1/diferencias \
  -H "Content-Type: application/json" \
  -d '{
    "rates": {
      "crypto": {
        "value_avg": 940.0,
        "value_sell": 945.0,
        "value_buy": 935.0
      },
      "mep": {
        "value_avg": 1250.0,
        "value_sell": 1260.0,
        "value_buy": 1240.0
      }
    }
  }'
```

## Ejemplo de Respuesta Exitosa
```json
{
  "diferencia_avg": 310.0,
  "diferencia_sell": 315.0,
  "diferencia_buy": 305.0
}
```

## Ejemplo de Error (Diferencias Negativas)
Si algún valor crypto es mayor que el MEP correspondiente, se devuelve error 400:

**Request:**
```json
{
  "rates": {
    "crypto": {
      "value_avg": 200.0,
      "value_sell": 200.0,
      "value_buy": 200.0
    },
    "mep": {
      "value_avg": 100.0,
      "value_sell": 100.0,
      "value_buy": 100.0
    }
  }
}
```

**Response:** `400 Bad Request`
```json
{
  "error": "Negative differences detected",
  "message": "Negative differences found for items: avg, sell, buy",
  "timestamp": "2025-10-21T18:25:17.359664280Z"
}
```

## Ejemplo de Error (Estructura Incorrecta)
Si envías la estructura antigua (sin el wrapper `rates`), obtendrás un error:

**Request Incorrecto:**
```json
{
  "crypto": {
    "value_avg": 940.0,
    "value_sell": 945.0,
    "value_buy": 935.0
  },
  "mep": {
    "value_avg": 1250.0,
    "value_sell": 1260.0,
    "value_buy": 1240.0
  }
}
```

**Response:** `400 Bad Request` - "Rates map cannot be null"
```json
{
  "error": "Validation error",
  "message": "Rates map cannot be null",
  "timestamp": "2025-10-21T18:25:17.359664280Z"
}
```

## Validaciones
- Todos los valores deben ser positivos
- No se permiten valores nulos
- El campo `rates` es obligatorio
- Los campos `crypto` y `mep` son obligatorios dentro de `rates`
- Si alguna diferencia es negativa, se lanza `ApiTestException` con detalles de qué items causaron el error

## Ventajas del Diseño EnumMap
- **Tipo seguro**: Compile-time validation para tipos de exchange rate
- **Escalabilidad**: Fácil agregar nuevos tipos de exchange rate (ej: `stablecoin`, `dolar_blue`)
- **Performance**: EnumMap es más eficiente que HashMap para claves enum
- **Mantenibilidad**: Estructura clara y consistente

## Casos de Uso
1. **Análisis de arbitraje**: Comparar precios entre mercados crypto y MEP
2. **Monitoreo de spreads**: Calcular diferencias de precios en tiempo real
3. **Alertas de mercado**: Detectar cuando crypto supera a MEP (diferencias negativas)
4. **Integración con sistemas**: Estructura predecible para APIs externas
