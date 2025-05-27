---
icon: inbox-full
---

# API Rest Segura - Gestión de tareas del hogar

### Enlace al documento original

[Link de Gitbook](https://josemanuelluque-2dam.gitbook.io/acceso-a-datos/api-rest-segura-gestion-de-tareas-del-hogar)

***

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

* `_id`: **ObjectId** – Identificador único del usuario.
* `username`: **String** – Nombre de usuario único para autenticación.
* `password`: **String** – Contraseña almacenada de forma segura (hasheada).
* `email`: String – Email del usuario, debe ser uniqco en la base de datos.
* `roles`: **List\<String>** Lista de roles que puede tener el usuario (`USER`, `ADMIN`).
* `hogar`: **Hogar** – Relación con **Hogar** para indicar a qué hogar pertenece el usuario.

#### **Relaciones:**

* Un usuario puede pertenecer a un **Hogar** (ManyToOne).

***

### 📄 **Documento Tarea**

Representa las tareas que deben realizar los usuarios dentro de un hogar.

#### **Atributos:**

* `_id`: **Int** – Identificador único de la tarea.
* – : **String** – Texto que describe la tarea.
* `estado`: **Boolean** – Estado actual de la tarea (Completada o no).
* `usuario`: **Usuario** – Usuario al que está asignada la tarea.
* `hogar`: **Hogar** – Relación con el **Hogar** en el que se debe realizar la tarea.

#### **Relaciones:**

* Una **Tarea** pertenece a un **Usuario** (ManyToOne).

***

### 📄 **Documento Hogares (Proximamente)**

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

## 📌 **Endpoints de la API**

### 📄 **Usuarios** (`/api/usuarios`)

| Método   | Endpoint           | Descripción                                                                   | Seguridad                |
| -------- | ------------------ | ----------------------------------------------------------------------------- | ------------------------ |
| `POST`   | `/register`        | Registra un nuevo usuario con su nombre, contraseña y hogar.                  | Público                  |
| `POST`   | `/login`           | Autentica a un usuario y devuelve un token JWT.                               | Público                  |
| `GET`    | `/admin`           | Devuelbe un booleano true si el usuario que hace la peticion es administrador | Usuario autenticado      |
| `GET`    | `/me`              | Obtiene la información del usuario autenticado.                               | Usuario autenticado      |
| `PUT`    | `/update`          | Actualiza un usuario                                                          | Usuario autenticado      |
| `DELETE` | `/delete/{id}`     | Borra a un usuario por su id                                                  | ADMIN                    |
| `GET`    | `/tareas`          | Obtiene la información de las tareas                                          | ADMIN                    |
| `GET`    | `/hogar/{hogarId}` | Lista los usuarios de un hogar específico.                                    | ADMIN, Usuario del hogar |

### 📄 **Tareas** (`/api/tareas`)

| Método   | Endpoint                     | Descripción                                                 | Seguridad                 |
| -------- | ---------------------------- | ----------------------------------------------------------- | ------------------------- |
| `POST`   | `/crear`                     | Crea una nueva tarea.                                       | Usuario autenticado       |
| `POST`   | `/crear/usuario/{idUsuario}` | Crea una tarea al usuario especificado                      | ADMIN                     |
| `GET`    | `/usuario`                   | Lista las tareas del usuario autenticado.                   | Usuario autenticado       |
| `GET`    | `/getAll`                    | Devuelve todas las tareas de la base de datos               | ADMIN                     |
| `PUT`    | `/update/status/{id}`        | Marca una tarea como hecha o al réves (solo dueño o ADMIN). | Usuario autenticado/ADMIN |
| `DELETE` | `/delete/{id}`               | Elimina una tarea (solo dueño o ADMIN).                     | Usuario autenticado/ADMIN |

### 📄 **Hogares** (`/api/hogares`)

| Método   | Endpoint | Descripción                                       | Seguridad           |
| -------- | -------- | ------------------------------------------------- | ------------------- |
| `POST`   | `/`      | Crea un nuevo hogar.                              | ADMIN               |
| `GET`    | `/`      | Lista todos los hogares.                          | ADMIN               |
| `GET`    | `/{id}`  | Obtiene los detalles de un hogar específico.      | Usuario autenticado |
| `PUT`    | `/{id}`  | Modifica la información de un hogar (solo ADMIN). | ADMIN               |
| `DELETE` | `/{id}`  | Elimina un hogar (solo ADMIN).                    | ADMIN               |

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
