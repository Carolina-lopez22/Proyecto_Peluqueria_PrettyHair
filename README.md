💜 #Peluquería PrettyHair

Sistema para gestionar las citas de una peluquería. El proyecto fue realizado en Java utilizando Swing para la interfaz gráfica, Maven para la organización del proyecto y MySQL para la base de datos.

📌 Descripción

PrettyHair permite llevar el control de clientes, servicios y citas de la peluquería.

Desde el sistema se pueden registrar y editar clientes, consultar los servicios disponibles y agendar, editar o eliminar citas.

🛠️ Tecnologías utilizadas

* Java
* Java Swing
* Maven
* JDBC
* MySQL
* Git y GitHub

📂 Estructura del proyecto

El proyecto está dividido en dos módulos:

* prettyhair-core: contiene los modelos, DAO y la conexión con la base de datos.
* prettyhair-ui: contiene la interfaz gráfica realizada con Java Swing.

peluqueria-prettyhair/
├── prettyhair-core/
├── prettyhair-ui/
├── pom.xml
└── README.md

🗄️ Base de datos

La base de datos utilizada se llama peluqueria y contiene las siguientes tablas:

* clientes
* servicios
* citas

Las citas están relacionadas con un cliente y un servicio.

Funciones principales

Clientes

* Agregar clientes.
* Editar clientes.
* Consultar clientes.
* Eliminar clientes.

Servicios

* Consultar los servicios disponibles y sus precios.

Citas

* Agendar citas.
* Editar citas.
* Eliminar citas.
* Consultar las citas registradas.
* Seleccionar cliente y servicio.
* Registrar fecha, hora y duración.
* Cambiar el estado de la cita.

Los estados disponibles son:

* Pendiente
* Confirmada
* Cancelada

Validaciones

El sistema valida algunos datos antes de guardarlos, por ejemplo:

* El nombre del cliente no puede quedar vacío.
* El teléfono debe tener 8 dígitos.
* La fecha de una cita no puede estar en el pasado.
* La duración debe ser mayor que 0.
* Se debe seleccionar un cliente y un servicio.

Ejecución

Para utilizar el proyecto es necesario tener instalado Java, Maven y MySQL.

1. Crear la base de datos utilizando el archivo schema.sql.
2. Configurar los datos de conexión en Conexion.java.
3. Abrir el proyecto en el IDE.
4. Ejecutar la aplicación desde el módulo prettyhair-ui.
