> **Nota:** Este documento también está disponible en [Inglés](README-en.md).

# Proyecto de Patrones de Diseño Minimarket

Este proyecto es una plataforma de administración de Minimarket basada en web construida con Java y Spring Boot. Demuestra la aplicación de **principios SOLID**, **patrones GoF** y **GRASP** para resolver problemas de negocio del mundo real como la gestión de inventario, transacciones y reportes.

## Características

- **Gestión de Inventario**: Crear, listar y eliminar productos.
- **Transacciones**: Registrar **Compras** (entrada de stock) y **Ventas** (salida de stock) con actualizaciones automáticas de stock.
- **Reportes**: Generar reportes de transacciones en formatos **CSV** o **HTML**.
- **Autenticación**: Inicio de sesión seguro para acceso de administrador.

## Arquitectura y Patrones de Diseño

Este proyecto está diseñado para ser una implementación de referencia para arquitectura limpia y patrones de diseño en Java/Spring Boot. A continuación se detalla cómo se aplica cada principio y patrón.

### Principios SOLID

| Principio                          | Aplicado En          | Ejemplo en Código                                                                                               | Por qué es bueno aquí                                                           |
| :--------------------------------- | :------------------- | :-------------------------------------------------------------------------------------------------------------- | :------------------------------------------------------------------------------ |
| **SRP** (Responsabilidad Única)    | `StockUpdater`       | `StockUpdater` solo maneja actualizaciones de inventario. No sabe sobre solicitudes HTTP o formato de reportes. | Si la lógica para actualizar el stock cambia, solo tocamos esta clase.          |
| **OCP** (Abierto/Cerrado)          | `PricingStrategy`    | Podemos agregar una `HolidayPricingStrategy` sin modificar `TransactionService`.                                | Reduce el riesgo de errores en código existente y probado al agregar funciones. |
| **LSP** (Sustitución de Liskov)    | `Transaction`        | `Purchase` y `Sale` pueden usarse donde se espere `Transaction`.                                                | Simplifica el polimorfismo en `TransactionRepository` y `ReportGenerator`.      |
| **ISP** (Segregación de Interfaz)  | `PricingStrategy`    | La interfaz solo tiene `calculateTotal`, no métodos no relacionados como `printReceipt`.                        | Las implementaciones no están forzadas a depender de métodos que no usan.       |
| **DIP** (Inversión de Dependencia) | `TransactionService` | Depende de `StockObserver` (interfaz), no de `StockUpdater` (clase concreta).                                   | Desacopla la lógica de negocio de los detalles de implementación específicos.   |

### Patrones GoF Implementados

#### 1. Patrones de Comportamiento

**Patrón Strategy**

- **Contexto**: `TransactionService` necesita calcular precios de manera diferente (ej. Regular vs. Descuento).
- **Implementación**: Interfaz `PricingStrategy` con `RegularPricingStrategy` y `DiscountPricingStrategy`.
- **Beneficio**: Elimina lógica compleja `if-else` en el servicio.

```mermaid
classDiagram
    class TransactionService {
        +createTransaction()
    }
    class PricingStrategy {
        <<interface>>
        +calculateTotal()
    }
    class RegularPricingStrategy {
        +calculateTotal()
    }
    class DiscountPricingStrategy {
        +calculateTotal()
    }
    TransactionService --> PricingStrategy : usa
    PricingStrategy <|.. RegularPricingStrategy : implementa
    PricingStrategy <|.. DiscountPricingStrategy : implementa
```

**Patrón Observer**

- **Sujeto**: `TransactionService`.
- **Observador**: `StockObserver`.
- **Implementación**: Cuando se guarda una transacción, `TransactionService` notifica a todos los `StockObserver`s. `StockUpdater` (un observador) luego actualiza el inventario de productos.
- **Beneficio**: Desacopla el procesamiento de transacciones de efectos secundarios como actualizaciones de stock.

```mermaid
classDiagram
    class TransactionService {
        +createTransaction()
        -notifyObservers()
    }
    class StockObserver {
        <<interface>>
        +onTransaction()
    }
    class StockUpdater {
        +onTransaction()
    }
    TransactionService --> StockObserver : notifica
    StockObserver <|.. StockUpdater : implementa
```

**Patrón Template Method**

- **Contexto**: Generar reportes requiere una estructura estándar (Encabezado -> Filas -> Pie) pero contenido variable.
- **Implementación**: `ReportGenerator` define el flujo `generateReport` (método final) y delega pasos específicos (`getHeaderLabels`, `getRowData`) a subclases como `TransactionReportGenerator`.
- **Beneficio**: Impone una estructura de reporte consistente mientras permite personalización.

#### 2. Patrones Estructurales

**Patrón Bridge**

- **Contexto**: Necesitamos generar reportes en diferentes formatos (CSV, HTML) sin explotar la jerarquía de clases (ej. `HtmlTransactionReport`, `CsvTransactionReport`).
- **Implementación**: `ReportGenerator` (Abstracción) mantiene una referencia a `ReportFormatter` (Implementación).
- **Beneficio**: Podemos agregar nuevos tipos de reportes (PDF) o nuevos generadores (ProductReport) independientemente.

```mermaid
classDiagram
    class ReportGenerator {
        <<abstract>>
        +generateReport()
    }
    class ReportFormatter {
        <<interface>>
        +formatHeader()
        +formatRow()
    }
    class CsvReportFormatter {
        +formatHeader()
    }
    class HtmlReportFormatter {
        +formatHeader()
    }
    ReportGenerator --> ReportFormatter : usa (Bridge)
    ReportFormatter <|.. CsvReportFormatter : implementa
    ReportFormatter <|.. HtmlReportFormatter : implementa
```

**Patrón Facade**

- **Contexto**: La capa de Controlador no debería lidiar con la complejidad de múltiples servicios (Producto, Transacción, Reporte).
- **Implementación**: `MinimarketFacade` proporciona una interfaz simple para los controladores, delegando llamadas a los servicios apropiados.
- **Beneficio**: Reduce el acoplamiento entre la capa web y la capa de negocio.

#### 3. Patrones Creacionales

**Patrón Factory Method**

- **Contexto**: Crear transacciones de `Purchase` o `Sale` implica lógica de inicialización diferente.
- **Implementación**: `TransactionFactory` decide qué entidad instanciar basado en la cadena de tipo.
- **Beneficio**: Centraliza la lógica de creación de objetos.

**Patrón Builder**

- **Contexto**: Los objetos `Product` tienen muchos atributos (código, nombre, precio, stock).
- **Implementación**: `Product.Builder` permite una construcción de objetos legible y segura.
- **Beneficio**: Evita constructores con largas listas de parámetros (`new Product(null, "A", "B", 10.0, 5)`).

### Principios GRASP

- **Controlador**: `ProductController`, `TransactionController` manejan eventos de UI y delegan a la Facade.
- **Experto en Información**: `Product` mantiene sus propios datos; `PricingStrategy` mantiene la lógica para el cálculo. El objeto con la información hace el trabajo.
- **Bajo Acoplamiento**: `TransactionService` no sabe sobre `StockUpdater` directamente, solo la interfaz `StockObserver`.
- **Alta Cohesión**: `ReportService` se enfoca únicamente en reportes; no maneja actualizaciones de stock o inicio de sesión de usuarios.
- **Polimorfismo**: Usado extensamente en `PricingStrategy` y `ReportFormatter` para manejar variaciones sin verificación de tipos.
- **Fabricación Pura**: `TransactionFactory` y `ReportFormatter` no son conceptos de dominio pero son creados para soportar alta cohesión y bajo acoplamiento.

## Prerrequisitos

- **Java 17** o superior
- **Maven 3.6** o superior (o use el wrapper `mvnw` incluido)

## Instalación

1. **Clonar el repositorio:**

   ```bash
   git clone <repository-url>
   cd design_patterns
   ```

2. **Instalar dependencias:**
   ```bash
   ./mvnw clean install
   ```

## Ejecutando la Aplicación

Para iniciar la aplicación, ejecute el siguiente comando en la raíz del proyecto:

```bash
./mvnw spring-boot:run
```

La aplicación iniciará en el puerto `8080`.

- **URL:** [http://localhost:8080](http://localhost:8080)

## Credenciales de Acceso

El sistema está pre-configurado con un único usuario administrador:

- **Usuario:** `admin`
- **Contraseña:** `admin123`

## Base de Datos

Este proyecto usa **SQLite** para persistencia.

- **Archivo de Base de Datos:** `minimarket.db` (creado en la raíz del proyecto)
- **URL JDBC:** `jdbc:sqlite:minimarket.db`
- **Dialecto:** `org.hibernate.community.dialect.SQLiteDialect`

## Estructura del Proyecto

- `src/main/java/com/minimarket/model`: Entidades de dominio (Product, Transaction).
- `src/main/java/com/minimarket/service`: Lógica de negocio e implementaciones de Patrones (Strategy, Observer, Factory).
- `src/main/java/com/minimarket/controller`: Controladores Web.
- `src/main/java/com/minimarket/service/facade`: Punto de entrada del patrón Facade.
- `src/main/resources/templates`: Vistas Thymeleaf.

## Pruebas

Para ejecutar las pruebas automatizadas:

```bash
./mvnw clean test
```

### Pruebas Unitarias

El proyecto incluye pruebas unitarias para la capa de servicio central, verificando la correcta implementación de la lógica de negocio y los patrones de diseño:

- **ProductServiceTest**: Verifica operaciones CRUD para productos.
- **TransactionServiceTest**: Prueba la creación de transacciones, asegurando que los patrones **Factory**, **Strategy**, y **Observer** funcionen como se espera.
- **ReportServiceTest**: Valida la generación de reportes y la integración del patrón **Bridge**.
- **CustomUserDetailsServiceTest**: Prueba la lógica de autenticación de usuarios.
- **ProductTest**: Prueba el patrón **Builder** para crear instancias de productos.
- **TransactionFactoryTest**: Verifica el patrón **Factory Method** para crear diferentes tipos de transacciones.
