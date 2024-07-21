# TODO App

## Descripción

Esta aplicación es un sistema de gestión de tareas (TODOs) que permite a los usuarios crear, leer, actualizar y eliminar tareas. La aplicación está protegida por autenticación basada en JSON Web Tokens (JWT). Proporciona una interfaz RESTful para interactuar con los datos y tiene una base de datos configurada para almacenar la información de los usuarios y sus tareas.

## Requisitos

- Java 17 o superior
- Maven
- Base de datos (MySQL, PostgreSQL, etc.)

## Configuración del Proyecto

### Configuración de la Base de Datos

1. **Crear la Base de Datos**

   Ejecuta el siguiente comando en tu sistema de base de datos para crear la base de datos:

   ```sql
   CREATE DATABASE tododb1;
Configurar el Archivo application.properties

En el archivo src/main/resources/application.properties, configura los detalles de tu base de datos:

properties
Copiar código
spring.datasource.url=jdbc:mysql://localhost:3306/tododb1
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
Reemplaza tu_usuario y tu_contraseña con tus credenciales de base de datos.


La aplicación estará disponible en http://localhost:8080.
Todos los endpoints están incluidos en un archivo postman para poder importar el workspace.

Autenticación JWT
La aplicación utiliza autenticación basada en JSON Web Tokens (JWT). Para acceder a los endpoints que requieren autenticación, necesitarás un token JWT.


###Registro de Usuario
Endpoint:
http://localhost:8080/auth/register (POST)
Cuerpo:
json

{
  "name": "John Doe",
  "username": "johndoe",
  "password": "securepassword123",
  "street": "123 Main St",
  "city": "Anytown",
  "zipcode": "12345",
  "country": "USA"
}
Nos devolverá un token para iniciar sesión con las credenciales

###Inicio de Sesión
Endpoint:
http://localhost:8080/auth/login (POST)
Cuerpo:
json

{
  "username": "johndoe",
  "password": "securepassword123"
}
Introducimos las credenciales y en el Bearer el token que obtuvimos al registrar el usuario
Nos devolverá un 200OK conforme el usuario está logado y nos devolverá un token JWT con el que poder realizar todas las peticiones securizadas.
Necesitaremos el token para autenticar las siguientes solicitudes.

Incluye el token en el encabezado de tus solicitudes como sigue:


Authorization: Bearer <tu_token_jwt>


Endpoints

###Agregar TODO
Endpoint: http://localhost:8080/api/v1/todoapp/user/addTodo (POST)
Cuerpo:
json

{
  "title": "Complete homework",
  "completed": true
}

###Actualizar TODO
Endpoint: /api/v1/todoapp/update/{id} (PUT)
Cuerpo:
json
Copiar código
{
  "title": "Updated Title",
  "completed": false
}
Necesitaremos introducir el id del todo a modificar en la url y el token de logado en el Header. Si el todo no es del usuario logado no nos permitirá modificarlo.

###Eliminar TODO
Endpoint: /api/v1/todoapp/delete/{id} (DELETE)
Necesitaremos introducir el id del todo en la url y el token. Si el todo no es del usuario logado no nos permitirá eliminarlo.

###Ver todos los TODOS (paginable)
Endpoint: /api/v1/todoapp/getAll (GET)
Parámetros de consulta:
title (opcional): Filtrar por título que contenga el texto.
username (opcional): Filtrar por nombre de usuario (exacto).
page (opcional): Número de página.
size (opcional): Tamaño de página.
sort (opcional): Campo por el que ordenar (por defecto "title").
direction (opcional): Dirección de ordenación ("asc" o "desc", por defecto "asc").


