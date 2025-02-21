# Pruebas Usuarios

En esta sección se documentan las pruebas de los endpoints de **Login** y **Registro**, incluyendo capturas de **Postman** y de la aplicación móvil.

***

### 📚 **Índice**

* [🔑 Registro de Usuario](pruebas-usuarios.md#registro-de-usuario-post-api-usuarios-register)
  * [✅ Registro Exitoso](pruebas-usuarios.md#registro-exitoso)
  * ❌ [Usuario ya existente](pruebas-usuarios.md#usuario-ya-existente)
  * [❌ Email ya existente](pruebas-usuarios.md#email-ya-existente)
  * [❌ Contraseñas no coinciden](pruebas-usuarios.md#contrasenas-no-coinciden)
  * ❌ [Campos vacíos](pruebas-usuarios.md#campos-vacios)
* [🔐 Inicio de Sesión](pruebas-usuarios.md#inicio-de-sesion-post-api-usuarios-login)
  * [✅ Login Exitoso](pruebas-usuarios.md#login-exitoso)
  * [❌ Credenciales incorrectas](pruebas-usuarios.md#credenciales-incorrectas)

***

### 🔑 **Registro de Usuario (`POST /api/usuarios/register`)**

#### ✅ **Registro Exitoso**

**Descripción:** Se registra un usuario correctamente y la API responde con `201 Created`.

📸 **Captura en Postman:**

<figure><img src=".gitbook/assets/201 Created.png" alt=""><figcaption></figcaption></figure>

📱 **Captura en la App (En este caso pongo el usuario de la base de datos que se va a intentar crear mal):**

<figure><img src=".gitbook/assets/Resultado Mongo.png" alt=""><figcaption></figcaption></figure>

***

#### ❌ **Usuario ya existente**

**Descripción:** Se intenta registrar un usuario con un nombre que ya está en uso. La API devuelve `400 Bad Request`.

📸 **Captura en Postman:**\


<figure><img src=".gitbook/assets/400 Bad (Existe).png" alt=""><figcaption></figcaption></figure>

📱 **Captura en la App:**\


<figure><img src=".gitbook/assets/400 usuario existente.png" alt="" width="188"><figcaption></figcaption></figure>

***

#### ❌ **Email ya existente**

**Descripción:** Se intenta registrar un usuario con un email que ya está en uso. La API devuelve `400 Bad Request`.

📸 **Captura en Postman:**\


<figure><img src=".gitbook/assets/400 Bad (Email ya existe).png" alt=""><figcaption></figcaption></figure>

📱 **Captura en la App:**

<figure><img src=".gitbook/assets/400 emai existente.png" alt="" width="188"><figcaption></figcaption></figure>

***

#### ❌ **Contraseñas no coinciden**

**Descripción:** Se intenta registrar un usuario con contraseñas que no coinciden. La API devuelve `400 Bad Request`.

📸 **Captura en Postman:**\


<figure><img src=".gitbook/assets/400 Bad (Contraseñas n coinciden) (1).png" alt=""><figcaption></figcaption></figure>

📱 **Captura en la App:**\


<figure><img src=".gitbook/assets/400 contraseñas distintas.png" alt="" width="188"><figcaption></figcaption></figure>

***

#### ❌ **Campos vacíos**

**Descripción:** Se intenta registrar un usuario sin completar todos los campos obligatorios. La API devuelve `400 Bad Request`.

📸 **Captura en Postman:**\


<figure><img src=".gitbook/assets/400 Bad(Null o Vacio).png" alt=""><figcaption></figcaption></figure>

📱 **Captura en la App:**\


<figure><img src=".gitbook/assets/400 Rellenar campos.png" alt="" width="188"><figcaption></figcaption></figure>

***

### 🔐 **Inicio de Sesión (`POST /api/usuarios/login`)**

#### ✅ **Login Exitoso**

**Descripción:** Se inicia sesión correctamente y la API devuelve `200 OK`, proporcionando un token JWT.

📸 **Captura en Postman:**\


<figure><img src=".gitbook/assets/200 Ok (1).png" alt=""><figcaption></figcaption></figure>

📱 **Captura en la App:**\


<figure><img src=".gitbook/assets/200 Ok.png" alt="" width="188"><figcaption></figcaption></figure>

***

#### ❌ **Credenciales incorrectas**

**Descripción:** Se intenta iniciar sesión con un usuario o contraseña incorrectos. La API devuelve `401 Unauthorized`.

📸 **Captura en Postman:**\


<figure><img src=".gitbook/assets/401 Crendenciales incorrectas.png" alt=""><figcaption></figcaption></figure>

📱 **Captura en la App, (en un futuro pasara a la HomeScreen y no mostrará el JWT):**\


<figure><img src=".gitbook/assets/200 Ok (2).png" alt="" width="188"><figcaption></figcaption></figure>
