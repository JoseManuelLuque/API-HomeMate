---
icon: inbox-full
---

# API Rest Segura - Gestión de tareas del hogar

### 🔧 Pruebas

{% content-ref url="pruebas-usuarios.md" %}
[pruebas-usuarios.md](pruebas-usuarios.md)
{% endcontent-ref %}

{% content-ref url="pruebas-de-gestion-de-tareas.md" %}
[pruebas-de-gestion-de-tareas.md](pruebas-de-gestion-de-tareas.md)
{% endcontent-ref %}



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

***

## 📌 **Endpoints de la API**

### 📄 **Usuarios** (`/api/usuarios`)

| Método | Endpoint           | Descripción                                                   | Seguridad                |
| ------ | ------------------ | ------------------------------------------------------------- | ------------------------ |
| `POST` | `/register`        | Registra un nuevo usuario con su nombre, contraseña y hogar.  | Público                  |
| `POST` | `/login`           | Autentica a un usuario y devuelve un token JWT.               | Público                  |
| `GET`  | `/yo`              | Obtiene la información del usuario autenticado.               | Usuario autenticado      |
| `GET`  | `/{id}`            | Obtiene la información de un usuario específico (solo ADMIN). | ADMIN                    |
| `GET`  | `/hogar/{hogarId}` | Lista los usuarios de un hogar específico.                    | ADMIN, Usuario del hogar |

### 📄 **Tareas** (`/api/tareas`)

| Método   | Endpoint | Descripción                                      | Seguridad           |
| -------- | -------- | ------------------------------------------------ | ------------------- |
| `POST`   | `/`      | Crea una nueva tarea en un hogar.                | Usuario autenticado |
| `GET`    | `/`      | Lista las tareas del usuario autenticado.        | Usuario autenticado |
| `GET`    | `/{id}`  | Obtiene los detalles de una tarea específica.    | Usuario autenticado |
| `PUT`    | `/{id}`  | Marca una tarea como hecha (solo dueño o ADMIN). | Usuario autenticado |
| `DELETE` | `/{id}`  | Elimina una tarea (solo dueño o ADMIN).          | Usuario autenticado |

### 📄 **Hogares** (`/api/hogares`)

| Método   | Endpoint | Descripción                                       | Seguridad           |
| -------- | -------- | ------------------------------------------------- | ------------------- |
| `POST`   | `/`      | Crea un nuevo hogar.                              | ADMIN               |
| `GET`    | `/`      | Lista todos los hogares.                          | ADMIN               |
| `GET`    | `/{id}`  | Obtiene los detalles de un hogar específico.      | Usuario autenticado |
| `PUT`    | `/{id}`  | Modifica la información de un hogar (solo ADMIN). | ADMIN               |
| `DELETE` | `/{id}`  | Elimina un hogar (solo ADMIN).                    | ADMIN               |

### 📄 **Direcciones** (`/api/direcciones`)

| Método | Endpoint           | Descripción                        | Seguridad         |
| ------ | ------------------ | ---------------------------------- | ----------------- |
| `GET`  | `/hogar/{hogarId}` | Obtiene la dirección de un hogar.  | Usuario del hogar |
| `PUT`  | `/hogar/{hogarId}` | Modifica la dirección de un hogar. | ADMIN             |

***

## 🔧 **Lógica de Negocio**

* Los usuarios pueden registrarse y unirse a un hogar.
* Un hogar puede tener varios usuarios gestionando sus tareas.
* Un usuario puede ver y gestionar solo sus tareas, salvo que sea ADMIN.
* El ADMIN puede gestionar tareas y usuarios dentro de cualquier hogar.

***

## 🚨 **Manejo de Excepciones y Códigos de Estado**

| Código                      | Situación         | Explicación                                                |
| --------------------------- | ----------------- | ---------------------------------------------------------- |
| `200 OK`                    | Operación exitosa | La solicitud se completó correctamente.                    |
| `201 Created`               | Recurso creado    | Se ha creado un nuevo recurso (ej. usuario, tarea, hogar). |
| `400 Bad Request`           | Datos inválidos   | Error de validación en la solicitud.                       |
| `401 Unauthorized`          | Sin autenticación | El usuario no ha iniciado sesión o el token es inválido.   |
| `403 Forbidden`             | Sin permisos      | El usuario no tiene permisos para realizar la acción.      |
| `404 Not Found`             | No encontrado     | El recurso solicitado no existe.                           |
| `500 Internal Server Error` | Error interno     | Fallo inesperado en el servidor.                           |

***

## 🔐 **Seguridad Aplicada en la API**

✅ **Autenticación con JWT** – Los usuarios deben autenticarse con un token JWT en cada solicitud.\
✅ **Roles y permisos** – Solo los ADMIN pueden modificar hogares y usuarios.\
✅ **Cifrado de contraseñas** – Se usa BCrypt para almacenar contraseñas seguras.\
✅ **Restricciones en los endpoints** – Cada endpoint tiene restricciones según el rol del usuario.
