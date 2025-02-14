# API Rest Segura - Gestión de tareas del hogar

### 📄 **Documento Usuario**

Representa a los usuarios que pueden registrarse en la aplicación, iniciar sesión y gestionar las tareas dentro de un hogar.

#### **Atributos:**

* `id`: **ObjectId** – Identificador único del usuario.
* `username`: **String** – Nombre de usuario único para autenticación.
* `password`: **String** – Contraseña almacenada de forma segura (hasheada).
* `roles`: **List\<String>** – Lista de roles que puede tener el usuario (`USER`, `ADMIN`).
* `hogar`: **Hogar** – Relación con **Hogar** para indicar a qué hogar pertenece el usuario.
* `tareas`: **List\<Tarea>** – Lista de tareas que le han sido asignadas.

#### **Relaciones:**

* Un usuario puede pertenecer a un **Hogar** (ManyToOne).
* Un usuario puede tener varias **Tareas** asignadas (OneToMany).

***

### 📄 **Documento Tareas**

Representa las tareas que deben realizar los usuarios dentro de un hogar.

#### **Atributos:**

* `id`: **Int** – Identificador único de la tarea.
* `descripcion`: **String** – Texto que describe la tarea.
* `estado`: **Enum (`PENDIENTE`, `HECHA`)** – Estado actual de la tarea.
* `usuario`: **Usuario** – Usuario al que está asignada la tarea.
* `hogar`: **Hogar** – Relación con el **Hogar** en el que se debe realizar la tarea.

#### **Relaciones:**

* Una **Tarea** pertenece a un **Usuario** (ManyToOne).
* Una **Tarea** pertenece a un **Hogar** (ManyToOne).

***

### 📄 **Documento Hogares**

Representa los hogares en los que varios usuarios pueden colaborar para gestionar tareas.

#### **Atributos:**

* `id`: **Int** – Identificador único del hogar.
* `nombre`: **String** – Nombre identificativo del hogar.
* `direccion`: **Direccion** – Relación con la entidad **Dirección**.
* `usuarios`: **List\<Usuario>** – Lista de usuarios que pertenecen al hogar.
* `tareas`: **List\<Tarea>** – Lista de tareas asignadas dentro del hogar.

#### **Relaciones:**

* Un **Hogar** tiene una **Dirección** (OneToOne).
* Un **Hogar** puede tener varios **Usuarios** asociados (OneToMany o ManyToMany).
* Un **Hogar** puede tener varias **Tareas** (OneToMany).

***

### 📄 **Documento Direcciones**

Contiene la información de ubicación de un hogar.

#### **Atributos:**

* `id`: **Int** – Identificador único de la dirección.
* `calle`: **String** – Nombre de la calle.
* `numero`: **Int** – Número de la vivienda.
* `municipio`: **String** – Municipio al que pertenece.
* `provincia`: **String** – Provincia donde se encuentra.

#### **Relaciones:**

* Una **Dirección** pertenece a un **Hogar** (OneToOne).
