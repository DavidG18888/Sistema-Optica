# Sistema Óptica Web — guía rápida

Esta versión convierte la interfaz Swing en una aplicación web Java con Spring Boot y Thymeleaf. Mantiene el acceso JDBC a Turso mediante la variable `TURSO_AUTH_TOKEN`.

## Antes de usar

- Java 21 y Maven 3.9+ para ejecutarla localmente, o Docker.
- La base Turso existente debe contener las tablas `Usuarios`, `Clientes`, `Productos`, `Mediciones`, `Apartados`, `Ventas` y `DetalleVenta`, con las columnas que usa el proyecto de escritorio.
- No publiques el token de Turso en GitHub ni lo pegues en el código.

## Abrir en NetBeans

1. Descomprimí el ZIP.
2. En NetBeans elegí **File > Open Project** y seleccioná la carpeta `SistemaOptica` que contiene `pom.xml`.
3. Configurá la variable de entorno `TURSO_AUTH_TOKEN` con el token de tu base Turso.
4. Configurá `TURSO_DATABASE_URL` con `https://TU-BASE.turso.io` si querés sobrescribir la URL por defecto.
5. Ejecutá `com.mycompany.sistemaoptica.web.SistemaOpticaWebApplication`.
6. Abrí `http://localhost:8080`.

En Windows PowerShell, para la sesión actual:

```powershell
$env:TURSO_AUTH_TOKEN="PEGAR_TOKEN_DE_TURSO_AQUI"
$env:TURSO_DATABASE_URL="https://TU-BASE.turso.io"
```

## Desplegar en Render

1. Subí el contenido del proyecto a un repositorio privado o público de GitHub. No incluyas tokens.
2. En Render elegí **New + > Web Service** y conectá el repositorio.
3. Seleccioná runtime Docker; Render detectará el `Dockerfile`.
4. En **Environment** configurá:
   - `TURSO_DATABASE_URL` = URL HTTPS de tu base Turso.
   - `TURSO_AUTH_TOKEN` = token de autenticación de Turso (secreto).
5. Desplegá y revisá los logs. Al finalizar, Render te dará una URL HTTPS.

El archivo `render.yaml` declara el servicio y solicita que agregues el token manualmente.

## Módulos incluidos en la interfaz web

- Login y control de rol Cliente/Vendedor.
- Registro de clientes.
- Catálogo con filtros y apartado de aros.
- Historial y registro de mediciones.
- CRUD de productos e indicador de stock bajo.
- Registro de ventas, detalle de producto, impuesto del 15% y descuento de inventario.
- Listado de clientes y últimas ventas.

## Importante antes de entregar

Este paquete fue adaptado a partir del proyecto proporcionado. Como el driver JDBC libSQL de DBeaver tiene diferencias respecto a JDBC estándar y no se dispone aquí del token de Turso, hay que ejecutar una prueba real con tus variables de entorno y revisar los logs de Render. La primera venta de prueba debe ser con un producto de prueba o en un entorno de datos respaldado. El registro de venta y el descuento de stock no se envuelven en una transacción porque el driver actual ha presentado limitaciones con transacciones.
