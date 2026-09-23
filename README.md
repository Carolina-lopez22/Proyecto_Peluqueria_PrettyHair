#Peluquería PrettyHair

Descripción del proyecto
PrettyHair es una aplicación de escritorio desarrollada para facilitar la administración de una peluquería.

El sistema permite registrar clientes, consultar los servicios ofrecidos y crear, editar y eliminar citas. Las operaciones se realizan mediante una interfaz gráfica desarrollada con Java Swing y la información se almacena en una base de datos MySQL.
El proyecto utiliza una arquitectura Maven multi-módulo, separando la lógica de acceso a datos de la interfaz gráfica.

✨ Funcionalidades
👤 Gestión de clientes
* Registrar nuevos clientes.
* Consultar clientes registrados.
* Editar información de los clientes.
* Eliminar clientes.
* Validar que el nombre no esté vacío.
* Validar que el teléfono tenga exactamente 8 dígitos.

💇 Servicios
El sistema permite consultar los servicios disponibles en la peluquería.
Entre los servicios registrados se encuentran:
* Corte de cabello
* Lavado de cabello
* Peinado
* Tinte
* Mechas
* Planchado
* Keratina
* Tratamiento capilar
* Ondulación Permanente
* Alisado Permanente
Cada servicio cuenta con un precio registrado en la base de datos.

📅 Gestión de citas
* Registrar nuevas citas.
* Consultar las citas existentes.
* Editar citas.
* Eliminar citas.
* Seleccionar el cliente.
* Seleccionar el servicio.
* Registrar fecha y hora.
* Registrar duración de la cita.
* Asignar el estado de la cita.
* El estado inicial de una nueva cita es pendiente.

Los estados disponibles son:
* pendiente
* confirmada
* cancelada

✅ Validaciones
El sistema cuenta con validaciones para evitar el ingreso de información incorrecta.

Clientes
* El nombre es obligatorio.
* El teléfono es obligatorio.
* El teléfono debe contener exactamente 8 dígitos.

Citas
* Debe seleccionarse un cliente.
* Debe seleccionarse un servicio.
* La fecha y hora deben tener el formato:

yyyy-MM-dd HH:mm
* No se permite registrar una cita con fecha u hora anterior al momento actual.
* La duración debe ser un número entero mayor que 0.
* El estado debe corresponder a uno de los estados permitidos.
  
🛠️ Tecnologías utilizadas
* Java
* Java Swing
* Maven
* JDBC
* MySQL
* MySQL Connector/J
* Git
* GitHub

🏗️ Arquitectura del proyecto

El proyecto utiliza una estructura Maven multi-módulo, formada por dos módulos principales:
peluqueria-prettyhair/
│
├── prettyhair-core/
│   └── src/
│       └── main/
│           └── java/
│               └── edu/umg/peluqueria/
│                   ├── conexion/
│                   ├── dao/
│                   └── modelo/
│
├── prettyhair-ui/
│   └── src/
│       └── main/
│           └── java/
│               └── edu/umg/peluqueria/
│                   └── ui/
│
├── pom.xml
└── README.md

prettyhair-core

Contiene la lógica relacionada con los datos y la conexión con la base de datos.

Incluye:
* Modelos.
* DAO.
* Conexión a MySQL.

prettyhair-ui
Contiene la interfaz gráfica desarrollada con Java Swing.

Incluye:
* Ventana principal.
* Formularios.
* Tablas.
* Botones.
* Navegación entre las diferentes secciones.

🗄️ Base de datos
El sistema utiliza una base de datos llamada:

peluqueria
Está compuesta principalmente por las siguientes tablas:

clientes
Almacena la información de los clientes.
id_cliente
nombre
telefono

servicios
Almacena los servicios disponibles.
id_servicio
nombre
precio

citas
Almacena las citas registradas.
id_cita
id_cliente
id_servicio
fecha_hora
duracion
estado

Las tablas citas, clientes y servicios están relacionadas mediante claves foráneas.

🔌 Acceso a datos
El proyecto utiliza JDBC para realizar la comunicación entre Java y MySQL.
El acceso a la base de datos se realiza mediante:
Connection
    ↓
PreparedStatement
    ↓
ResultSet

Las operaciones de cada entidad se encuentran organizadas mediante clases DAO.
Entre ellas:
* ClienteDAO
* ServiciosDAO
* CitasDAO

Las consultas SQL utilizan PreparedStatement para ejecutar las operaciones de forma estructurada.

🖥️ Interfaz gráfica
La interfaz fue desarrollada utilizando Java Swing.
La ventana principal cuenta con un menú lateral que permite acceder a:
Citas
Agendar citas
Editar citas
Servicios
Clientes

La aplicación utiliza una interfaz visual basada en tonos lilas para mantener una apariencia relacionada con la identidad de la peluquería.


📂 Clases principales
Modelo
Las clases del modelo representan la información utilizada por el sistema:

Cliente
Servicios
Citas

DAO
Las clases DAO se encargan de realizar las operaciones CRUD:
ClienteDAO
ServiciosDAO
CitasDAO

Conexión
La clase:
Conexion
se encarga de establecer la conexión con la base de datos.

Interfaz
La clase principal de la interfaz gráfica es:
VentanaPrincipal
Esta clase administra las diferentes vistas y formularios de la aplicación.

🧪 Operaciones CRUD
El sistema implementa las operaciones principales de un CRUD:
Operación	Clientes	Servicios	Citas
Crear	✅	—	✅
Consultar	✅	✅	✅
Editar	✅	—	✅
Eliminar	✅	—	✅
Los servicios se muestran como un catálogo de los servicios disponibles, por lo que la interfaz no incluye opciones para agregar o eliminar servicios.
+ Maven + JDBC + MySQL
