# 📝 Pruebas de Gestión de Tareas

Esta sección documenta las pruebas realizadas sobre el endpoint de **Tareas**, incluyendo capturas de **Postman** y de la aplicación móvil.

***

### 📚 **Índice**

* 📝 Creación de Tareas
  * ✅ Crear tarea correctamente
  * ❌ Intentar crear tarea con campos vacíos
* 📋 Listado de Tareas
  * ✅ Ver listado de tareas propias
  * ❌ Intentar ver tareas sin autenticación
* ✔️ Marcar Tareas
  * ✅ Marcar tarea como hecha
  * ❌ Intentar marcar tarea que no pertenece al usuario
* 🗑 Eliminación de Tareas
  * ✅ Eliminar tarea propia
  * ❌ Intentar eliminar tarea que no pertenece al usuario
  * ✅ Eliminar tarea como ADMIN
  * ❌ Intentar eliminar tarea inexistente
* 📌 Notas Adicionales

***

### 📝 **Creación de Tareas**

#### ✅ **Crear tarea correctamente**

**Descripción:** Se crea una tarea correctamente y la API responde con `201 Created`.

📸 **Captura en Postman:**\


<figure><img src=".gitbook/assets/imagen_2025-03-02_033149030.png" alt=""><figcaption></figcaption></figure>

📱 **Captura en la App:**\


***

#### ❌ **Intentar crear tarea con campos vacíos**

**Descripción:** Se intenta crear una tarea sin completar todos los campos obligatorios. La API devuelve `400 Bad Request`.

📸 **Captura en Postman:**\


<figure><img src=".gitbook/assets/image.png" alt=""><figcaption></figcaption></figure>

📱 **Captura en la App:**\


***

### 📋 **Listado de Tareas**

#### ✅ **Ver listado de tareas propias**

**Descripción:** Se consulta correctamente la lista de tareas asignadas al usuario autenticado. La API devuelve `200 OK`.

📸 **Captura en Postman:**

<figure><img src=".gitbook/assets/image (1).png" alt=""><figcaption></figcaption></figure>

📱 **Captura en la App:**\


***

#### ❌ **Intentar ver tareas sin autenticación**

**Descripción:** Se intenta acceder al listado de tareas sin un token válido. La API devuelve `401 Unauthorized`.

📸 **Captura en Postman:**\


<figure><img src=".gitbook/assets/image (2).png" alt=""><figcaption></figcaption></figure>

📱 **Captura en la App:**\


***

#### ✅ Obtener todas las tareas

**Descripción: Siendo Administrador se obtienen todas las tareas de todos los usuarios. La API devuelve `200 OK.`**

📸 **Captura en Postman:**

<figure><img src=".gitbook/assets/image (3).png" alt=""><figcaption></figcaption></figure>

📱 **Captura en la App:**



***

#### ❌ Intentar ver todas las tareas sin ser Administrador



📸 **Captura en Postman:**



📱 **Captura en la App:**



***

### ✔️ **Marcar Tareas**

#### ✅ **Marcar tarea como hecha**

**Descripción:** Se actualiza el estado de una tarea a "HECHA". La API devuelve `200 OK`.

📸 **Captura en Postman:**\


📱 **Captura en la App:**\


***

#### ❌ **Intentar marcar tarea que no pertenece al usuario**

**Descripción:** Se intenta marcar como hecha una tarea de otro usuario. La API devuelve `403 Forbidden`.

📸 **Captura en Postman:**\


📱 **Captura en la App:**\


***

### 🗑 **Eliminación de Tareas**

#### ✅ **Eliminar tarea propia**

**Descripción:** Se elimina correctamente una tarea propia. La API devuelve `200 OK`.

📸 **Captura en Postman:**\


📱 **Captura en la App:**\


***

#### ❌ **Intentar eliminar tarea que no pertenece al usuario**

**Descripción:** Se intenta eliminar una tarea asignada a otro usuario. La API devuelve `403 Forbidden`.

📸 **Captura en Postman:**\


📱 **Captura en la App:**\


***

#### ✅ **Eliminar tarea como ADMIN**

**Descripción:** Un usuario con rol ADMIN elimina una tarea de cualquier usuario. La API devuelve `200 OK`.

📸 **Captura en Postman:**\


📱 **Captura en la App:**\


***

#### ❌ **Intentar eliminar tarea inexistente**

**Descripción:** Se intenta eliminar una tarea que no existe en la base de datos. La API devuelve `404 Not Found`.

📸 **Captura en Postman:**\


📱 **Captura en la App:**\
