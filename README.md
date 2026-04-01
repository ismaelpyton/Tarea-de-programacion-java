# Sistema de Gestión de Usuarios (Java Swing + SQLite)

Un sistema completo de gestión de usuarios con interfaz de escritorio desarrollado en **Java Swing**. Aplica los principios fundamentales de la **Programación Orientada a Objetos (POO)** y patrones de diseño para garantizar una arquitectura escalable, segura y mantenible.

## 🚀 Características Principales

- **Interfaz Gráfica (GUI):** Ventanas modernas con estilos centralizados usando `Java Swing`.
- **Base de Datos Embebida:** Uso de **SQLite** para el almacenamiento local sin necesidad de servidores externos.
- **Operaciones CRUD Completas:** Función de Crear (Registro), Leer (Login y Tabla de Usuarios), Actualizar y Eliminar cuentas.
- **Validaciones Seguras:** Verificación de campos vacíos, coincidencias de contraseñas y prevención de usuarios duplicados.

## 🧠 Arquitectura y Diseño

El proyecto está diseñado teniendo en cuenta las mejores prácticas de ingeniería de software:

### 1. Pilares de la POO Implementados
- **Abstracción:** Uso de la clase abstracta `Persona` y la interfaz `IServicioUsuario` para definir modelos genéricos y contratos de servicio.
- **Encapsulamiento:** Ocultación de los atributos de los modelos (`private`) con acceso a través de métodos `getter` y `setter`.
- **Herencia:** La clase concreta `Usuario` hereda (`extends`) atributos y estructura de la clase base `Persona`.
- **Polimorfismo:** Implementación de `@Override` en el método `getInfoResumida()` y la implementación real del contrato `IServicioUsuario` en el `ServicioUsuario`.

### 2. Patrones de Diseño
- **Singleton:** Aplicado en `ConexionDB` y `ServicioUsuario` para asegurar que solo exista una instancia en memoria de la conexión a la base de datos y de la gestión de la lógica, optimizando recursos y evitando corrupciones.
- **Factory:** Implementado en `FabricaUsuario` para centralizar la validación y la creación instanciada de nuevos objetos `Usuario`, limpiando la lógica de la interfaz gráfica.

### 3. Seguridad
- Implementación de `PreparedStatements` en todas las consultas (SQL) para prevenir ataques de **Inyección SQL**.

## 📂 Estructura del Proyecto

```text
/
├── Main.java                        # Punto de entrada y configuración Look&Feel
├── modelo/
│   ├── Persona.java                 # Clase Base (Abstracta)
│   └── Usuario.java                 # Clase Hija
├── servicio/
│   ├── IServicioUsuario.java        # Interface CRUD
│   └── ServicioUsuario.java         # Lógica Crud y BD (Singleton)
├── fabricas/
│   └── FabricaUsuario.java          # Creador/Validador de usuarios (Factory)
├── util/
│   ├── ConexionDB.java              # Manejador de la conexión a SQLite (Singleton)
│   └── EstiloUI.java                # Centralización de colores y diseño gráfico
├── vista/
│   ├── PantallaLogin.java           # GUI de Autenticación
│   ├── PantallaRegistro.java        # GUI de Registro de nuevas cuentas
│   └── PantallaPrincipal.java       # GUI con la tabla (CRUD visual)
└── lib/
    └── sqlite-jdbc.jar              # Driver de conexión base de datos
```

## 🛠️ Ejecución y Compilación (Terminal/PowerShell)

Para probar la aplicación directamente desde la terminal, utiliza las siguientes instrucciones prestando atención a incluir el driver en el *Classpath* temporal.

**1. Compilar los archivos:**
En la raíz del proyecto, ejecuta:
```powershell
javac -encoding UTF-8 util/*.java modelo/*.java servicio/*.java fabricas/*.java vista/*.java Main.java
```

**2. Ejecutar el proyecto:**
Es requerido especificar el archivo jar del driver en el classpath (`-cp`).
```powershell
java -cp '.;lib/sqlite-jdbc.jar' Main
```
*(Nota: En PowerShell usa comillas simples para la ruta del driver)*
