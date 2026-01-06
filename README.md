#Sistema Web de Ventas – Spring Boot

Aplicación web de ventas desarrollada con Spring Boot, Spring Data JPA, MySQL y Thymeleaf.
Permite gestionar productos, stock y ventas en una tienda de forma sencilla.

---

##Características
```
Gestión de productos (CRUD)

Registro de ventas

Control de stock

Arquitectura MVC

Persistencia con JPA / Hibernate

Base de datos MySQL
```

##Tecnologías usadas

---
```
Java 21+ (recomendado)

Spring Boot 4

Spring Data JPA

Hibernate

MySQL 8

Maven Wrapper (mvnw)

Thymeleaf

Bootstrap
```
---

##Requisitos previos
---


Antes de empezar, asegúrate de tener:
```
☑ Java 21 o superior

☑ MySQL 8

☑ Git

```

##Verifica Java:
---
```
java -version
```

## Clonar el repositorio
---
```
git clone https://github.com/TU_USUARIO/TU_REPOSITORIO.git
cd Web-de-ventas
```

🗄️ Configuración de la base de datos (MySQL)
---

##1️ Iniciar MySQL
```
sudo systemctl start mysql
sudo systemctl enable mysql
```
##2️ Crear base de datos y usuarios

Entra a MySQL como root:
```
sudo mysql
```

Ejecuta:
```
CREATE DATABASE IF NOT EXISTS ventasdb;

CREATE USER IF NOT EXISTS 'admin'@'localhost' IDENTIFIED BY 'admin123';
CREATE USER IF NOT EXISTS 'vendedor'@'localhost' IDENTIFIED BY 'vendedor123';

GRANT ALL PRIVILEGES ON ventasdb.* TO 'admin'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE ON ventasdb.* TO 'vendedor'@'localhost';

FLUSH PRIVILEGES;
```



-admin → acceso total

-vendedor → operaciones básicas

##3 (Opcional pero recomendado) Importar datos iniciales

Si el proyecto incluye un dump SQL:

mysql -u admin -p ventasdb < db/ventasdb.sql

##Configuración de la aplicación 

Spring Boot permite usar variables de entorno, lo cual es más seguro y portable.

Linux / macOS
```
export SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3306/ventasdb?useSSL=false&serverTimezone=America/Lima&allowPublicKeyRetrieval=true"
export SPRING_DATASOURCE_USERNAME="admin"
export SPRING_DATASOURCE_PASSWORD="admin123"
```

Windows (PowerShell)
```
setx SPRING_DATASOURCE_URL "jdbc:mysql://localhost:3306/ventasdb?useSSL=false&serverTimezone=America/Lima&allowPublicKeyRetrieval=true"
setx SPRING_DATASOURCE_USERNAME "admin"
setx SPRING_DATASOURCE_PASSWORD "admin123"
```

##Ejecutar la aplicación
---

Usa el Maven Wrapper incluido:
```
./mvnw spring-boot:run
```

En Windows:
```
mvnw spring-boot:run
```

##Acceso a la aplicación
---

Por defecto:
```
http://localhost:8080
```

##Usuarios de la aplicación

(si están cargados en la base de datos)

Rol	Usuario	Contraseña
Administrador	admin	admin123
Vendedor	vendedor	vendedor123

Estos usuarios pertenecen a la base de datos de la app, no al sistema ni a MySQL.

-Problemas comunes y soluciones

Error: Communications link failure

 MySQL no está corriendo
```
sudo systemctl start mysql
```
Error: Access denied for user

Usuario o contraseña incorrectos
- Verifica credenciales
- Asegúrate de haber ejecutado GRANT PRIVILEGES

Puerto 8080 ocupado

Cambia el puerto en application.properties o usa:

server.port=8081

Construir JAR para producción
```
./mvnw clean package
```


#Ejecutar:
```
java -jar target/ventas-0.0.1-SNAPSHOT.jar
```


#Licencia

Proyecto de uso educativo / demostrativo.

#Autor

Desarrollado por Anthony
Proyecto académico / práctico con Spring Boot
