# Ventas App 

Aplicación web desarrollada con **Spring Boot** y **MySQL** para la gestión de ventas.
Proyecto universitario que permite registrar ventas y sus detalles utilizando una base de datos relacional.

---

## Tecnologías utilizadas
- Java 17
- Spring Boot 4
- Spring Data JPA (Hibernate)
- MySQL 8
- Maven

---

## Estructura del proyecto
```
ventas/
├── src/
├── db/
│ └── ventasdb.sql
├── pom.xml
├── application.properties
├── .gitignore
├── README.md
├── mvnw
└── mvnw.cmd
```
---

## Requisitos
- Java 17 instalado
- MySQL Server 8
- Maven (o usar el wrapper incluido `mvnw`)

---

## Base de datos

**Datos de conexión (entorno académico):**
- Base de datos: `ventasdb`
- Usuario: `ventas_user`
- Contraseña: `ventas123`

### Crear la base de datos
```sql
CREATE DATABASE ventasdb;
```
Importar el respaldo incluido
```
mysql -u ventas_user -p ventasdb < db/ventasdb.sql
```
El archivo db/ventasdb.sql contiene la estructura y datos de ejemplo de la aplicación.

⚙️ Configuración de la aplicación

Archivo application.properties:
```
spring.application.name=ventas
server.port=8080

spring.datasource.url=jdbc:mysql://127.0.0.1:3306/ventasdb?useSSL=false&serverTimezone=America/Lima&allowPublicKeyRetrieval=true
spring.datasource.username=ventas_user
spring.datasource.password=ventas123
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
jakarta.persistence.jdbc.url=jdbc:mysql://127.0.0.1:3306/ventasdb

spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
```

Ejecución del proyecto

Desde la raíz del proyecto ejecutar:
```
./mvnw spring-boot:run
```
Luego abrir en el navegador:
```
http://localhost:8080
```
Verificación de funcionamiento

La aplicación se ejecuta en el puerto 8080

Spring Boot se conecta correctamente a MySQL

Hibernate crea y gestiona las tablas en la base ventasdb

Notas

Las credenciales incluidas son solo para fines académicos

El respaldo de la base de datos se incluye para facilitar la evaluación

El proyecto no utiliza Docker para simplificar su ejecución

Autor : Anthony Castillo

Año: 2026
