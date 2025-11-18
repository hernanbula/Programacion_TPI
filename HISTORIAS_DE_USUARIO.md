# Historias de Usuario - Sistema de Gestión de Productos y Códigos de Barras

Especificaciones funcionales del sistema CRUD de productos y códigos de barras con relación 1→1 unidireccional.

## Tabla de Contenidos

- [Épica 1: Gestión de Productos](#épica-1-gestión-de-productos)
- [Épica 2: Gestión de Códigos de Barras](#épica-2-gestión-de-códigos-de-barras)
- [Épica 3: Operaciones Asociadas](#épica-3-operaciones-asociadas)
- [Reglas de Negocio](#reglas-de-negocio)
- [Modelo de Datos](#modelo-de-datos)

---

## Épica 1: Gestión de Productos

### HU-001: Crear Producto

**Como** usuario del sistema
**Quiero** crear un registro de producto con sus datos básicos
**Para** almacenar información de productos en el inventario

#### Criterios de Aceptación

```gherkin
Escenario: Crear producto sin código de barras
  Dado que el usuario selecciona "Crear producto"
  Cuando ingresa nombre "Leche", marca "La Serenísima", precio 1937.50
  Y responde "n" a agregar código de barras
  Entonces el sistema crea el producto con ID autogenerado
  Y muestra "✓ Producto creado exitosamente"

Escenario: Crear producto con código de barras
  Dado que el usuario selecciona "Crear producto"
  Cuando ingresa datos del producto
  Y responde "s" a agregar código de barras
  Y selecciona tipo EAN13, valor "7791234567890"
  Entonces el sistema crea el código de barras primero
  Y luego crea el producto con referencia al código
  Y muestra "✓ Producto con código de barras creado exitosamente"

Escenario: Crear producto con campos inválidos
  Dado que el usuario selecciona "Crear producto"
  Cuando ingresa precio negativo
  Entonces el sistema muestra "Error: El precio debe ser mayor o igual a 0"
  Y no crea el registro
```

#### Reglas de Negocio Aplicables

- **RN-001**: Nombre, precio y stock son obligatorios
- **RN-002**: Precio y stock no pueden ser negativos
- **RN-003**: El nombre no puede exceder 120 caracteres
- **RN-004**: El código de barras es opcional durante la creación

---

### HU-002: Listar Productos

**Como** usuario del sistema
**Quiero** ver productos con diferentes criterios de búsqueda
**Para** consultar el inventario de manera flexible

#### Criterios de Aceptación

```gherkin
Escenario: Listar todos los productos
  Dado que existen productos en el sistema
  Cuando el usuario selecciona "Listar productos" y opción "1"
  Entonces el sistema muestra todos los productos activos
  Y para cada producto con código de barras muestra sus detalles

Escenario: Buscar producto por nombre
  Dado que existen productos "Leche" y "Leche Descremada"
  Cuando el usuario busca por "Leche"
  Entonces el sistema muestra ambos productos

Escenario: Filtrar por categoría
  Dado que el usuario selecciona filtrar por "ALIMENTOS"
  Entonces el sistema muestra solo productos de esa categoría
```

#### Reglas de Negocio Aplicables

- **RN-005**: Solo se listan productos con `eliminado = FALSE`
- **RN-006**: La búsqueda por nombre es case-insensitive
- **RN-007**: Se puede filtrar por categorías predefinidas

---

### HU-003: Actualizar Producto

**Como** usuario del sistema
**Quiero** modificar los datos de un producto existente
**Para** mantener la información del inventario actualizada

#### Criterios de Aceptación

```gherkin
Escenario: Actualizar precio y stock
  Dado que existe producto con ID 1, precio 1937.50
  Cuando el usuario actualiza el producto ID 1
  Y escribe "2100.00" en precio y "50" en stock
  Entonces el sistema actualiza solo los campos modificados
  Y muestra "✓ Producto actualizado exitosamente"

Escenario: Cambiar categoría
  Dado que producto ID 1 tiene categoría "ALIMENTOS"
  Cuando el usuario cambia categoría a "BEBIDAS"
  Entonces el sistema actualiza la categoría
  Y mantiene los demás campos sin cambios
```

#### Reglas de Negocio Aplicables

- **RN-008**: Campos vacíos mantienen valor original
- **RN-009**: Se requieren validaciones igual que en creación
- **RN-010**: Se puede cambiar categoría sin afectar otros datos

---

### HU-004: Eliminar Producto

**Como** usuario del sistema
**Quiero** eliminar un producto del inventario
**Para** mantener solo productos activos

#### Criterios de Aceptación

```gherkin
Escenario: Eliminar producto existente
  Dado que existe producto con ID 1
  Cuando el usuario elimina el producto ID 1
  Y confirma la eliminación
  Entonces el sistema marca eliminado = TRUE
  Y muestra "✓ Producto eliminado exitosamente (soft delete)"

Escenario: Cancelar eliminación
  Dado que el usuario selecciona eliminar producto
  Cuando no confirma la eliminación
  Entonces el sistema cancela la operación
  Y el producto permanece activo
```

#### Reglas de Negocio Aplicables

- **RN-011**: Eliminación es lógica (soft delete)
- **RN-012**: Se requiere confirmación del usuario
- **RN-013**: Productos eliminados no aparecen en listados

---

### HU-005: Recuperar Producto

**Como** usuario del sistema
**Quiero** recuperar un producto previamente eliminado
**Para** restaurar productos eliminados por error

#### Criterios de Aceptación

```gherkin
Escenario: Recuperar producto eliminado
  Dado que producto ID 1 está marcado como eliminado
  Cuando el usuario recupera producto ID 1
  Y confirma la recuperación
  Entonces el sistema marca eliminado = FALSE
  Y muestra "✓ Producto recuperado exitosamente"

Escenario: Intentar recuperar producto activo
  Dado que producto ID 1 no está eliminado
  Cuando el usuario intenta recuperarlo
  Entonces el sistema muestra "El producto no está borrado"
```

#### Reglas de Negocio Aplicables

- **RN-014**: Solo se pueden recuperar productos eliminados
- **RN-015**: Se requiere confirmación del usuario

---

## Épica 2: Gestión de Códigos de Barras

### HU-006: Crear Código de Barras Independiente

**Como** usuario del sistema
**Quiero** crear un código de barras sin asociarlo a producto
**Para** tener códigos disponibles para asignación posterior

#### Criterios de Aceptación

```gherkin
Escenario: Crear código de barras válido
  Dado que el usuario selecciona "Crear código de barras"
  Cuando selecciona tipo EAN13, valor "7791234567890"
  Entonces el sistema crea el código con ID autogenerado
  Y muestra "✓ Código de Barra nuevo creado"

Escenario: Crear código con valor duplicado
  Dado que existe código con valor "7791234567890"
  Cuando el usuario intenta crear otro con mismo valor
  Entonces el sistema muestra "Ya existe un código de barras con el valor"
  Y no crea el registro
```

#### Reglas de Negocio Aplicables

- **RN-016**: Tipo y valor son obligatorios
- **RN-017**: El valor debe ser único en el sistema
- **RN-018**: El valor no puede exceder 20 caracteres

---

### HU-007: Listar Códigos de Barras

**Como** usuario del sistema
**Quiero** ver todos los códigos de barras registrados
**Para** consultar códigos disponibles

#### Criterios de Aceptación

```gherkin
Escenario: Listar todos los códigos
  Dado que existen códigos en el sistema
  Cuando el usuario selecciona "Listar códigos de barras"
  Entonces el sistema muestra ID, tipo, valor de cada código
  Y solo muestra códigos con eliminado = FALSE

Escenario: Buscar código por ID
  Dado que existe código con ID 5
  Cuando el usuario busca código por ID 5
  Entonces el sistema muestra solo ese código
```

#### Reglas de Negocio Aplicables

- **RN-019**: Solo se listan códigos con `eliminado = FALSE`
- **RN-020**: Se muestran tanto códigos asociados como independientes

---

### HU-008: Actualizar Código de Barras

**Como** usuario del sistema
**Quiero** modificar un código de barras existente
**Para** corregir información incorrecta

#### Criterios de Aceptación

```gherkin
Escenario: Actualizar observaciones
  Dado que existe código con ID 1
  Cuando el usuario actualiza sus observaciones
  Entonces el sistema actualiza el código
  Y muestra "✓ Código de barras actualizado exitosamente"

Escenario: Actualizar con valor duplicado
  Dado que existen códigos con valores "111" y "222"
  Cuando el usuario intenta cambiar valor "222" a "111"
  Entonces el sistema muestra error de valor duplicado
  Y no actualiza el registro
```

#### Reglas de Negocio Aplicables

- **RN-021**: Se valida unicidad del valor excepto para el mismo código
- **RN-022**: Campos vacíos mantienen valor original

---

### HU-009: Eliminar Código de Barras

**Como** usuario del sistema
**Quiero** eliminar un código de barras
**Para** remover códigos no utilizados

#### Criterios de Aceptación

```gherkin
Escenario: Eliminar código no asociado
  Dado que existe código ID 5 sin producto asociado
  Cuando el usuario elimina código ID 5
  Entonces el sistema marca eliminado = TRUE
  Y muestra "✓ Código de barras eliminado exitosamente"

Escenario: Eliminar código asociado (advertencia)
  Dado que código ID 1 está asociado a producto ID 10
  Cuando el usuario elimina código ID 1
  Entonces el sistema marca el código como eliminado
  Pero el producto ID 10 mantiene la referencia
  Y queda una referencia huérfana
```

#### Reglas de Negocio Aplicables

- **RN-023**: Eliminación es lógica
- **RN-024**: ⚠️ No verifica si está asociado a producto
- **RN-025**: Puede causar referencias huérfanas

---

### HU-010: Recuperar Código de Barras

**Como** usuario del sistema
**Quiero** recuperar un código previamente eliminado
**Para** restaurar códigos eliminados por error

#### Criterios de Aceptación

```gherkin
Escenario: Recuperar código eliminado
  Dado que código ID 1 está marcado como eliminado
  Cuando el usuario recupera código ID 1
  Entonces el sistema marca eliminado = FALSE
  Y muestra "✓ Código de barras recuperado exitosamente"
```

#### Reglas de Negocio Aplicables

- **RN-026**: Solo se pueden recuperar códigos eliminados

---

## Épica 3: Operaciones Asociadas

### HU-011: Asignar Código de Barras a Producto

**Como** usuario del sistema
**Quiero** asignar un código de barras existente a un producto
**Para** establecer la relación 1→1 entre producto y código

#### Criterios de Aceptación

```gherkin
Escenario: Asignar código a producto sin código
  Dado que producto ID 1 no tiene código asociado
  Y código ID 5 está disponible
  Cuando el usuario asigna código ID 5 a producto ID 1
  Entonces el sistema establece la relación
  Y muestra "✓ Código de barras asignado exitosamente"

Escenario: Producto ya tiene código asociado
  Dado que producto ID 1 ya tiene código ID 3
  Cuando el usuario intenta asignar código ID 5
  Entonces el sistema reemplaza la asociación
  Y el código ID 3 queda disponible para otros productos
```

#### Reglas de Negocio Aplicables

- **RN-027**: Un producto solo puede tener un código de barras
- **RN-028**: Un código solo puede estar asociado a un producto
- **RN-029**: La asignación reemplaza cualquier asociación existente

---

### HU-012: Crear Producto con Código de Barras en Transacción

**Como** usuario del sistema
**Quiero** crear un producto con su código de barras en una operación atómica
**Para** garantizar que ambas entidades se creen correctamente o ninguna

#### Criterios de Aceptación

```gherkin
Escenario: Transacción exitosa
  Dado que el usuario crea producto con código de barras
  Cuando ambas operaciones son exitosas
  Entonces el sistema confirma la transacción
  Y ambas entidades se persisten correctamente

Escenario: Rollback por error
  Dado que el usuario crea producto con código de barras
  Cuando falla la inserción del código
  Entonces el sistema hace rollback de toda la operación
  Y ninguna entidad se persiste
```

#### Reglas de Negocio Aplicables

- **RN-030**: Operación transaccional con commit/rollback
- **RN-031**: Se inserta código primero, luego producto con FK
- **RN-032**: Si falla cualquier paso, se revierte todo

---

## Reglas de Negocio

### Validación de Productos

| Código | Regla | Implementación |
|--------|-------|----------------|
| RN-001 | Nombre obligatorio, máximo 120 chars | `ProductoService.validarProducto()` |
| RN-002 | Precio ≥ 0, stock ≥ 0 | Validación en service layer |
| RN-003 | Marca máximo 80 caracteres | `Producto.setMarca()` |
| RN-008 | Campos vacíos mantienen valor en update | `MenuHandler.actualizarProducto()` |
| RN-011 | Soft delete con campo `eliminado` | `ProductoDAO.eliminar()` |

### Validación de Códigos de Barras

| Código | Regla | Implementación |
|--------|-------|----------------|
| RN-016 | Tipo y valor obligatorios | `CodigoBarrasService.validarCodigoBarras()` |
| RN-017 | Valor único en sistema | UNIQUE constraint en BD + validación service |
| RN-018 | Valor máximo 20 caracteres | `CodigoBarras.setValor()` |
| RN-023 | Soft delete para códigos | `CodigoBarrasDAO.eliminar()` |

### Relación 1→1

| Código | Regla | Implementación |
|--------|-------|----------------|
| RN-027 | Un producto → un código | FK única en tabla producto |
| RN-028 | Un código → un producto | Lógica de negocio en service |
| RN-029 | Asignación reemplaza asociación existente | `MenuHandler.asignarCodigoDeBarras()` |
| RN-032 | Transacciones atómicas | `ProductoService.insertarConCodigoBarras()` |

---

## Modelo de Datos

### Diagrama Entidad-Relación

```
┌─────────────────────────────────┐
│           producto              │
├─────────────────────────────────┤
│ id: BIGINT PK AUTO_INCREMENT    │
│ nombre: VARCHAR(120) NOT NULL   │
│ marca: VARCHAR(80)              │
│ categoria: VARCHAR(80)          │
│ precio: DECIMAL(10,2) NOT NULL  │
│ peso: DECIMAL(10,3)             │
│ stock: INT DEFAULT 0            │
│ eliminado: BOOLEAN DEFAULT FALSE│
│ codigo_barras_id: BIGINT FK UK  │
└──────────────┬──────────────────┘
               │ 0..1
               │
               │ FK UNIQUE
               │
               ▼
┌──────────────────────────────────┐
│         codigo_barras            │
├──────────────────────────────────┤
│ id: BIGINT PK AUTO_INCREMENT     │
│ tipo: VARCHAR(10) NOT NULL       │
│ valor: VARCHAR(20) NOT NULL UK   │
│ fecha_asignacion: DATE           │
│ observaciones: VARCHAR(255)      │
│ eliminado: BOOLEAN DEFAULT FALSE │
└──────────────────────────────────┘
```

### Enumeraciones

**CategoriaProducto:**
- ALIMENTOS, BEBIDAS, ELECTRODOMESTICOS, FERRETERIA, LIMPIEZA

**EnumTipo:**
- EAN13, EAN8, UPC

---

## Flujos Transaccionales

### Flujo: Crear Producto con Código de Barras

```
Usuario (MenuHandler.crearProducto)
    ↓ captura datos producto + código
ProductoService.insertarConCodigoBarras()
    ↓ conn.setAutoCommit(false)
    ↓ validarProducto(producto)
    ↓ validarCodigoBarras(codigo)
    ↓ CodigoBarrasDAO.insertar(codigo, conn)
    ↓ producto.setCodigoBarras(codigo)
    ↓ ProductoDAO.insertar(producto, conn)
    ↓ conn.commit()
Usuario recibe: "✓ Producto con código de barras creado"
```

### Flujo: Asignar Código Existente

```
Usuario (MenuHandler.asignarCodigoDeBarras)
    ↓ ingresa ID producto y ID código
ProductoService.asignarCodigoDeBarras()
    ↓ valida que producto exista
    ↓ valida que código exista  
    ↓ producto.setCodigoBarras(codigo)
    ↓ ProductoDAO.asignarCodigoDeBarras(producto, conn)
    ↓ conn.commit()
Usuario recibe: "✓ Código de barras asignado exitosamente"
```

---

## Resumen de Operaciones del Menú

| Opción | Operación | HU |
|--------|-----------|----|
| 1 | Crear producto | HU-001 |
| 2 | Listar productos | HU-002 |
| 3 | Actualizar producto | HU-003 |
| 4 | Eliminar producto | HU-004 |
| 5 | Asignar código de barras | HU-011 |
| 6 | Recuperar producto | HU-005 |
| 7 | Crear código de barras | HU-006 |
| 8 | Listar códigos de barras | HU-007 |
| 9 | Actualizar código de barras | HU-008 |
| 10 | Eliminar código de barras | HU-009 |
| 11 | Recuperar código de barras | HU-010 |

---

**Versión**: 1.0  
**Total Historias de Usuario**: 12  
**Total Reglas de Negocio**: 32  
**Arquitectura**: 5 capas (Main → Service → DAO → Models → Config)