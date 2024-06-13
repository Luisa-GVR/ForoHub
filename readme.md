Descripción
Este proyecto fue creado con JavaSpringBoot para el challenge de ForoHub, en el que se plantea un foro donde hay usuarios, topicos, respuestas y un login.

Requisitos Previos
Es necesario modificar según sus necesidades la base de datos, todo esto se modifica en el application.yml

Instalación
No es necesario descargar dependencias, ya que la mayoría se encuentra en el .pom, lo único que será necesario será crear una base de datos forohub, después los sql armarán lo necesario.

Uso
Al ser tipo web además de contar con un front end sencillo, es cuestión de correr el programa e ir al localhost:puerto, en el caso de como está programado, es en el 9090. Ya que no hay ningún usuario predeterminado, deberás de entrar en tu mysql -uroot -p, use forohub, y agregar un usuario. Es cuestión de agregar nombre, correo electronico, y una contraseña que deberá de estar encriptada con BCrypt.

Estructura del Proyecto
Se separa en 3 partes más importantes, seguridad, controladores y dominios. En seguridad es la información referente al login y el token, el dominio para la estructura de las clases, enum y el repositorio para la base de datos, y en el controlador se encuentran los endpoints necesarios.