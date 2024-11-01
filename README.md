# 🏠 Proyecto MiMaridoTeLoHace - Web App de Servicios
¡Bienvenido al repositorio de MiMaridoTeLoHace! Este proyecto es una emocionante y ambiciosa web app que busca solucionar la dificultad de encontrar proveedores de servicios confiables y de forma inmediata para los residentes de tres barrios cerrados en Chacras de Coria, Mendoza. La aplicación permitirá contactar a proveedores de servicios (gasistas, plomeros, electricistas, etc) que carguen su perfil con lo que ofrecen. Además, se podrán dejar comentarios y puntajes para cada perfil, fomentando así una red de proveedores confiables.

## 🎥 Vista Previa  
💾 <a href="https://youtu.be/ARXmC18vZOM">Link Video Funcionalidades de App Web</a>

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

🔷 Obligatorios:

• Registro y Login con Spring Security

• Dos o más roles distintos para los usuarios

• Tabla HTML en alguna vista

•️ Carga y actualización de imagen

• Query de búsqueda personalizada

• CRUD (Crear, Leer, Actualizar, Eliminar)

• Uno o más formularios

• Tres o más vistas distintas

• Diagrama UML de entidades

🔷 Optativos:

• Motor de búsqueda

• Tabla de agregar/modificar y eliminar registros

• Atributos booleanos de alta y baja de usuarios (y poder modificarlos)

• Listado en tabla por filtro (nombre, fechas, altas o bajas, etc)

• Implementen th:fragments en al menos cinco vistas distintas

• Dashboard de administración de la app (el rol Administrador tendrá acceso a información que un rol User o 
Guest no tiene)

• Aplicar principios de código limpio y buenas prácticas

• Añadir diagrama de casos de uso en UML

🔷 Específicos:

• La app permite crear un perfil de USUARIO QUE OFRECE SERVICIOS (PROVEEDOR) o un perfil de USUARIO QUE QUIERE 
CONTACTAR EL SERVICIO (USER)

• La app muestra cada perfil con la cantidad de usuarios que lo contactaron y la cantidad de reseñas/puntaje/calificación que corresponde (sólo pueden calificar a un PROVEEDOR aquellos USER que ya contactaron/contrataron el servicio de este perfil)

• Cada PROVEEDOR puede ser calificado por quienes hayan utilizado su servicio (esto lo decide el equipo, si con promedio entre 1/5 o con estrellas, etcétera)

• Un PROVEEDOR (gasista, plomero, etc.) genera un perfil propio, con foto, contacto y descripción del servicio que ofrece

• Un USER puede acceder a la app, navegar en los servicios que quiera y seleccionar a un proveedor de servicios para contactarlo o para calificarlo

•️ Un GUEST puede ver los servicios, pero no puede ver la información de contacto

•️ Un ADMIN puede otorgar permisos, cambiar roles, eliminar comentarios y crear nuevos servicios

• Los USERS deben poder encontrar fácilmente a un proveedor por rubro y ordenarlo bajo distintos criterios

• Opcionalmente, un USER puede elegir pasar su perfil a PROVEEDOR
  
## 🛠️ Instalación
• Java: 17 <br>
• Spring Boot: 2.7.10

## 🗂️ Estructura del Proyecto

El proyecto está organizado de la siguiente manera:

📂 MiMaridoTeLoHace/src/main/java/com/egg/MiMaridoTeLoHace
    
    📁 MiMaridoTeLoHace/src/main/java/com/egg/MiMaridoTeLoHace/controllers: Contiene los controladores de Spring que gestionan las solicitudes HTTP y definen las rutas para acceder a las funcionalidades de la aplicación.

    📁 MiMaridoTeLoHace/src/main/java/com/egg/MiMaridoTeLoHace/entities: Aquí se definen las entidades JPA que representan las tablas de la base de datos, como la entidad de Proveedor y la entidad de Usuario.

    📁 MiMaridoTeLoHace/src/main/java/com/egg/MiMaridoTeLoHace/enums: Contiene clases enum que definen valores constantes utilizados en el proyecto, como roles de usuario.

    📁 MiMaridoTeLoHace/src/main/java/com/egg/MiMaridoTeLoHace/exceptions: En esta carpeta se encuentran las clases personalizadas de excepciones para manejar situaciones excepcionales en la aplicación.

    📁 MiMaridoTeLoHace/src/main/java/com/egg/MiMaridoTeLoHace/repositories: Aquí se definen los repositorios JPA que interactúan con la base de datos para realizar operaciones de acceso a datos.

    📁 MiMaridoTeLoHace/src/main/java/com/egg/MiMaridoTeLoHace/security: Contiene la configuración de seguridad de Spring para el login y registro de usuarios.

    📁 MiMaridoTeLoHace/src/main/java/com/egg/MiMaridoTeLoHace/services: Contiene los servicios de Spring que implementan la lógica de negocio relacionada con los proveedores y los usuarios.

    📁 MiMaridoTeLoHace/src/main/java/com/egg/MiMaridoTeLoHace/converters: Contiene los conversores utilizados para mapear los datos entre entidades y DTOs.
    
📂 MiMaridoTeLoHace/src/main/resources/

    📁 MiMaridoTeLoHace/src/main/resources/static: Aquí se encuentran los recursos estáticos, como archivos CSS o JavaScript, utilizados en el frontend de la aplicación.

    📁 MiMaridoTeLoHace/src/main/resources/templates: Contiene las plantillas HTML que utilizan Thymeleaf para integrar el backend de Spring con el frontend, permitiendo la visualización dinámica de los datos.

# 💻 Tech Stack:
<div align="center">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/javascript/javascript-original.svg" height="40" alt="javascript logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/html5/html5-original.svg" height="40" alt="html5 logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/css3/css3-original.svg" height="40" alt="css3 logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/bootstrap/bootstrap-original.svg" height="40" alt="bootstrap logo"  />
  <img width="12" />
  <img src="https://www.thymeleaf.org/images/thymeleaf.png" height="40" alt="materialui logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" height="40" alt="java logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg" height="40" alt="spring logo"  />
  <img width="12" />
  <img src="https://www.vectorlogo.zone/logos/getpostman/getpostman-icon.svg" height="40" alt="postman"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/mysql/mysql-original.svg" height="40" alt="mysql logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/git/git-original.svg" height="40" alt="git logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/visualstudio/visualstudio-plain.svg" height="40" alt="visualstudio logo"  />
  <img width="12" />
  <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/9/98/Apache_NetBeans_Logo.svg/1200px-Apache_NetBeans_Logo.svg.png" height="40" alt="visualstudio logo"  />
  <img width="12" />
</div>

# 🧑🏻‍💻 Autores:

Valentin Mathey | <a href="https://github.com/valentinmathey">@valentinmathey</a><br>
Eric Lemos      | <a href="https://github.com/ErosGael">@ErosGael</a><br>
Facundo Nedic   | <a href="https://github.com/fnedic">@fnedic</a><br>
Federico Trucco | <a href="https://github.com/truquinio">@truquinio</a>
