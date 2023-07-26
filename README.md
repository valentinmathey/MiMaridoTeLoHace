# 🏠 Proyecto MiMaridoTeLoHace - Web App de Servicios
¡Bienvenido al repositorio de MiMaridoTeLoHace! Este proyecto es una emocionante y ambiciosa web app que busca solucionar la dificultad de encontrar proveedores de servicios confiables y de forma inmediata para los residentes de tres barrios cerrados en Chacras de Coria, Mendoza. La aplicación permitirá contactar a proveedores de servicios (gasistas, plomeros, electricistas, etc.) que carguen su perfil con lo que ofrecen. Además, se podrán dejar comentarios y puntajes para cada perfil, fomentando así una red de proveedores confiables.

## 🎥 Vista Previa [ -> Link Video]([https://undefinedshell.notion.site/Semana-3-73f4277f993c4eb78d0d5aab7be42c94](https://youtu.be/ARXmC18vZOM))

![Captura de pantalla 2023-07-25 230609](https://github.com/valentinmathey/MiMaridoTeLoHace/assets/108497495/e46502ef-0d3a-426a-8f76-6bf12f6c0510)
![Captura de pantalla 2023-07-25 230625](https://github.com/valentinmathey/MiMaridoTeLoHace/assets/108497495/69910529-1b60-464e-8165-9416229e0c07)

## 🎯 Objetivo

El objetivo principal de MiMaridoTeLoHace es brindar una lista de proveedores calificados por la experiencia de otros usuarios, facilitando así el proceso de encontrar proveedores confiables para los servicios que necesitan los propietarios o residentes de los barrios cerrados.

## 🌟 Audiencia

La audiencia principal de la aplicación son los proveedores de servicios, así como los propietarios o residentes de los barrios privados que necesiten contratar algún proveedor para realizar distintas tareas.

## 📖 Casos de Uso

• El administrador determinará los servicios que se pueden ofrecer y otorgar roles.

• Los administradores pueden eliminar/censurar comentarios ofensivos, pero dejando la caliﬁcación.

• El proveedor puede aceptar un trabajo o cancelarlo. También puede marcarlo como ﬁnalizado una vez que el trabajo haya sido aceptado.

• El usuario puede pedir un servicio o cancelarlo. Una vez aceptado el trabajo puede cancelarlo o darlo por ﬁnalizado.

• Una vez que el trabajo fue ﬁnalizado el usuario está habilitado a caliﬁcar el servicio, caliﬁcación que se agregará al perﬁl del proveedor.

• El usuario puede contactar al proveedor, calcular un estimativo de los honorarios del proveedor por horas y caliﬁcarlo luego de prestado el servicio.

## 🔧 Requerimientos

Obligatorios:

📝 Registro y Login con Spring Security

🔐 Dos o más roles distintos para los usuarios

📊 Tabla HTML en alguna vista

🖼️ Carga y actualización de imagen

🔍 Query de búsqueda personalizada

📝 CRUD (Crear, Leer, Actualizar, Eliminar)

📝 Uno o más formularios

📝 Tres o más vistas distintas

📊 Diagrama UML de entidades

Optativos:

🔍 Motor de búsqueda

📝 Tabla de agregar/modificar y eliminar registros

✅ Atributos booleanos de alta y baja de usuarios (y poder modificarlos)

🔍 Listado en tabla por filtro (nombre, fechas, altas o bajas, etc)

📄 Implementen th:fragments en al menos cinco vistas distintas

📊 Dashboard de administración de la app (el rol Administrador tendrá acceso a información que un rol User o 
Guest no tiene)

🧼 Aplicar principios de código limpio y buenas prácticas

📊 Añadir diagrama de casos de uso en UML

Específicos:

👥 La app permite crear un perfil de USUARIO QUE OFRECE SERVICIOS (PROVEEDOR) o un perfil de USUARIO QUE QUIERE 
CONTACTAR EL SERVICIO (USER)

🌟 La app muestra cada perfil con la cantidad de usuarios que lo contactaron y la cantidad de reseñas/puntaje/calificación que corresponde (sólo pueden calificar a un PROVEEDOR aquellos USER que ya contactaron/contrataron el servicio de este perfil)

⭐ Cada PROVEEDOR puede ser calificado por quienes hayan utilizado su servicio (esto lo decide el equipo, si con promedio entre 1/5 o con estrellas, etcétera)

📞 Un PROVEEDOR (gasista, plomero, etc.) genera un perfil propio, con foto, contacto y descripción del servicio que ofrece

👤 Un USER puede acceder a la app, navegar en los servicios que quiera y seleccionar a un proveedor de servicios para contactarlo o para calificarlo

👁️ Un GUEST puede ver los servicios, pero no puede ver la información de contacto

🛠️ Un ADMIN puede otorgar permisos, cambiar roles, eliminar comentarios y crear nuevos servicios

🔍 Los USERS deben poder encontrar fácilmente a un proveedor por rubro y ordenarlo bajo distintos criterios

⭐ Opcionalmente, un USER puede elegir pasar su perfil a PROVEEDOR
  

## 📂Estructura del Proyecto

El proyecto está organizado de la siguiente manera:

🔧MiMaridoTeLoHace/src/main/java/com/egg/MiMaridoTeLoHace
    
    📁 MiMaridoTeLoHace/src/main/java/com/egg/MiMaridoTeLoHace/controllers: Contiene los controladores de Spring que gestionan las solicitudes HTTP y definen las rutas para acceder a las funcionalidades de la aplicación.

    📁 MiMaridoTeLoHace/src/main/java/com/egg/MiMaridoTeLoHace/entities: Aquí se definen las entidades JPA que representan las tablas de la base de datos, como la entidad de Proveedor y la entidad de Usuario.

    🔢 MiMaridoTeLoHace/src/main/java/com/egg/MiMaridoTeLoHace/enums: Contiene clases enum que definen valores constantes utilizados en el proyecto, como roles de usuario.

    🔥 MiMaridoTeLoHace/src/main/java/com/egg/MiMaridoTeLoHace/exceptions: En esta carpeta se encuentran las clases personalizadas de excepciones para manejar situaciones excepcionales en la aplicación.

    💾 MiMaridoTeLoHace/src/main/java/com/egg/MiMaridoTeLoHace/repositories: Aquí se definen los repositorios JPA que interactúan con la base de datos para realizar operaciones de acceso a datos.

    🌐 MiMaridoTeLoHace/src/main/java/com/egg/MiMaridoTeLoHace/security: Contiene la configuración de seguridad de Spring para el login y registro de usuarios.

    🛠️ MiMaridoTeLoHace/src/main/java/com/egg/MiMaridoTeLoHace/services: Contiene los servicios de Spring que implementan la lógica de negocio relacionada con los proveedores y los usuarios.

    🔧 MiMaridoTeLoHace/src/main/java/com/egg/MiMaridoTeLoHace/converters: Contiene los conversores utilizados para mapear los datos entre entidades y DTOs.
    
💼 MiMaridoTeLoHace/src/main/resources/

    🌐 MiMaridoTeLoHace/src/main/resources/static: Aquí se encuentran los recursos estáticos, como archivos CSS o JavaScript, utilizados en el frontend de la aplicación.

    📃 MiMaridoTeLoHace/src/main/resources/templates: Contiene las plantillas HTML que utilizan Thymeleaf para integrar el backend de Spring con el frontend, permitiendo la visualización dinámica de los datos.

# 💻 Tech Stack:

🔹 Java: Lenguaje de programación para el desarrollo del backend.

🔹 Spring Boot: Framework que facilita el desarrollo de aplicaciones web con Spring.

🔹 Thymeleaf: Motor de plantillas para integrar el backend de Spring con el frontend en HTML.

🔹 Bootstrap: Framework de CSS y JavaScript para el diseño y maquetación de páginas web.

🔹 HTML: Lenguaje de marcado para la estructura de las páginas web.

🔹 CSS: Lenguaje de estilos para el diseño y presentación de las páginas web.

🔹 JavaScript: Lenguaje de programación para implementar interacciones en la parte frontend.

🔹 MySQL: Sistema de gestión de bases de datos para almacenar la información de los usuarios y proveedores.

# 🧑🏻‍💻 Autor:

<b>Valentin Mathey</b> | @valentinmathey

[![Discord](https://img.shields.io/badge/Discord-%237289DA.svg?logo=discord&logoColor=white)](https://discord.gg/valentinmathey) [![Facebook](https://img.shields.io/badge/Facebook-%231877F2.svg?logo=Facebook&logoColor=white)](https://facebook.com/https://www.facebook.com/ValentinEzequielMathey) [![Instagram](https://img.shields.io/badge/Instagram-%23E4405F.svg?logo=Instagram&logoColor=white)](https://instagram.com/https://www.instagram.com/valen.mathey/) [![LinkedIn](https://img.shields.io/badge/LinkedIn-%230077B5.svg?logo=linkedin&logoColor=white)](https://linkedin.com/in/https://www.linkedin.com/in/valentin-mathey/) [![Twitter](https://img.shields.io/badge/Twitter-%231DA1F2.svg?logo=Twitter&logoColor=white)](https://twitter.com/https://twitter.com/valen_mathey)
