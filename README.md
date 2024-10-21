# Nearest Branch
---
### Descripción del Proyecto

**Nearest Branch** es una API REST desarrollada en **Java 17** con **Spring Boot** y que utiliza **MongoDB** como base de datos para almacenar la información. Tiene como objetivo gestionar un conjunto de sucursales, permitiendo a los usuarios crear nuevas, consultar los detalles de una sucursal específica por su ID y encontrar la más cercana dada una ubicación (latitud y longitud).

El proyecto sigue los principios de **Clean Architecture**, lo que garantiza una separación clara de responsabilidades, promoviendo un código modular, mantenible y fácil de testear.

---
### Levantar el Proyecto en el Entorno Local

Este proyecto está configurado para usar **Spring Boot** con **Maven** como gestor de dependencias, y **MongoDB** como base de datos. Además, incluye **Mongo Express** como una interfaz gráfica para administrar MongoDB. Usaremos **Docker Compose** para levantar tanto MongoDB como Mongo Express de manera fácil y rápida.

### Requisitos previos

Antes de ejecutar el proyecto, asegúrate de tener instalados los siguientes programas en tu entorno local:

- **Java 17** o superior.
- **Docker** y **Docker Compose** instalados en tu máquina.

### Paso 1: Levantar y bajar contenedores Mongo y Mongo Express

El archivo `docker-compose.yml` ya está preparado para levantar una instancia de **MongoDB** y **Mongo Express**. MongoDB estará accesible en el puerto `27017`, y Mongo Express estará disponible en el puerto `8081` para administrar MongoDB a través de una interfaz web.

##### Comando para levantar los contenedores:

Ejecutar desde la raíz del proyecto:

```bash
docker-compose up -d
```

##### Comando para bajar los contenedores:

Ejecuta desde la raíz del proyecto:

```bash
docker-compose down
```

### Paso 2: Compilar el proyecto

Una vez que los servicios de MongoDB y Mongo Express estén funcionando, es momento de compilar. Navega a la carpeta raíz del proyecto (donde está ubicado el archivo `pom.xml`) y ejecuta el siguiente comando para compilar el proyecto y descargar todas las dependencias necesarias:

```bash
./mvnw clean install
```

### Paso 3: Ejecutar el proyecto usando el plugin de Maven para Spring Boot

El proyecto puede ser ejecutado directamente utilizando el **plugin de Maven para Spring Boot**, lo que permite levantar la aplicación sin necesidad de generar un archivo JAR explícitamente. Este enfoque es muy útil para el desarrollo, ya que facilita la ejecución rápida del proyecto.

Desde la raíz del proyecto (donde se encuentra el archivo `pom.xml`), ejecutar el siguiente comando:

```bash
./mvnw spring-boot:run
```

### Paso 4: Acceder a Mongo Express

Una vez que hayas levantado los servicios de **MongoDB** y **Mongo Express** utilizando Docker Compose, puedes acceder a la interfaz web de **Mongo Express** para administrar y explorar tu base de datos **MongoDB** de manera visual.

Abre tu navegador y accede a la siguiente URL:

```bash
http://localhost:8081
```
**NOTA:** El usuario y el password para ingresar es: admin/admin.

---
## Cobertura de Tests con JaCoCo

Este proyecto utiliza **JaCoCo** para generar informes de cobertura de código durante la ejecución de los tests unitarios. JaCoCo es una herramienta muy útil para medir qué porcentaje del código es cubierto por las pruebas y ayuda a identificar las áreas que necesitan más atención en cuanto a testing.

Para ejecutar las pruebas unitarias y generar el informe de cobertura de JaCoCo, puedes utilizar **Maven** con el siguiente comando:

```bash
./mvnw clean test
```
Una vez ejecutado, se crea automáticamente una carpeta con el informe. Dentro de esta hay un archivo index.html al que tenemos que acceder:

```bash
target/site/jacoco/index.html
```
**NOTA:** Actualmente la cobertura es cercana al 100%, de agregar nuevo código este porcentaje puede variar en la medida que no contemos con la cantidad de tests necesarias.

---
## Endpoints disponibles

La API expone varios endpoints para gestionar sucursales. A continuación se detallan, junto con ejemplos de uso para cada uno.



### 1. Crear una Sucursal

Este endpoint permite crear una nueva sucursal proporcionando los datos necesarios como dirección, latitud y longitud.

- **URL**: `/api/sucursales`
- **Método HTTP**: `POST`
- **Descripción**: Crea una nueva sucursal en la base de datos.

##### Cuerpo de la solicitud (JSON)

```json
{
  "direccion": "Calle 123",
  "latitud": 10.0,
  "longitud": 20.0
}
```
##### Ejemplo de respuesta exitosa (HTTP 200)

```json
{
  "id": "1",
  "direccion": "Calle 123",
  "latitud": 10.0,
  "longitud": 20.0
}
```
##### Respuesta de error (HTTP 400 - Bad Request)

```json
{
  "timestamp": "2024-01-01T12:00:00",
  "message": "La dirección de la sucursal no puede estar vacía"
}
```
