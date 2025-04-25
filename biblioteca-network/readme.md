# Sistema de Microservicios para Administración de Libros

Este proyecto implementa un sistema de microservicios para la gestión de libros y autores, aplicando buenas prácticas de pruebas automatizadas y DevOps.

## Arquitectura

El sistema está compuesto por tres microservicios:

1. **libro-service**: Gestión de libros (CRUD)
2. **autor-service**: Gestión de autores y su asociación con libros
3. **gateway-service**: API Gateway para enrutamiento centralizado

## Tecnologías utilizadas

- Spring Boot 3
- Spring WebFlux (Programación reactiva)
- MongoDB
- Spring Cloud Gateway
- Docker
- GitHub Actions (CI/CD)
- JWT para seguridad

## Endpoints disponibles

### Gateway Service (Puerto 8080)

- Punto de entrada para todas las peticiones
- Autenticación: `POST /auth/login`
  ```json
  {
    "username": "usuario",
    "password": "password"
  }
  ```

### Libro Service (Puerto 8081)

- Listar todos los libros: `GET /libros`
- Obtener libro por ID: `GET /libros/{id}`
- Crear nuevo libro: `POST /libros`
  ```json
  {
    "titulo": "El Quijote",
    "isbn": "123456789",
    "anioPublicacion": 1605
  }
  ```
- Asignar autor a libro: `PUT /libros/{id}/autor?autorId={autorId}`
- Eliminar libro: `DELETE /libros/{id}`

### Autor Service (Puerto 8082)

- Listar todos los autores: `GET /autores`
- Obtener autor por ID: `GET /autores/{id}`
- Crear nuevo autor: `POST /autores`
  ```json
  {
    "nombre": "Miguel de Cervantes",
    "nacionalidad": "Español"
  }
  ```
- Actualizar autor: `PUT /autores/{id}`
- Eliminar autor: `DELETE /autores/{id}`

## Cómo ejecutar el proyecto

### Requisitos previos

- Docker y Docker Compose
- Java 17 (para desarrollo)
- Maven (para desarrollo)

### Usando Docker Compose

1. Clonar el repositorio:
```bash
git clone 
cd biblioteca-microservicios
```

2. Ejecutar con Docker Compose:
```bash
docker-compose up -d
```

3. Verificar que los servicios estén funcionando:
```bash
docker-compose ps
```

### Ejecución manual (desarrollo)

1. Iniciar MongoDB:
```bash
docker run -d -p 27017:27017 --name mongodb mongo:latest
```

2. Compilar y ejecutar cada servicio:
```bash
# Libro Service
cd libro-service
mvn spring-boot:run

# Autor Service
cd autor-service
mvn spring-boot:run

# Gateway Service
cd gateway-service
mvn spring-boot:run
```

## Verificación de la salud de los servicios

- Gateway: http://localhost:8080/actuator/health
- Libro Service: http://localhost:8081/actuator/health
- Autor Service: http://localhost:8082/actuator/health

## Pruebas automatizadas

Para ejecutar las pruebas:

```bash
# Ejecutar pruebas en todos los servicios
mvn test

# O ejecutar pruebas por servicio
cd libro-service && mvn test
cd autor-service && mvn test
cd gateway-service && mvn test
```

## Integración Continua

El proyecto utiliza GitHub Actions para CI/CD. Cada push a la rama principal activa:
- Compilación del código
- Ejecución de pruebas unitarias e integración
- Construcción de imágenes Docker

Consulta el archivo `.github/workflows/test.yml` para más detalles.
