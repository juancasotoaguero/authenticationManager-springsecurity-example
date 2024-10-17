# Proyecto :

Es una aplicación hecha en java con spring boot que sigue los requisitos mandados por nisum

# Apis disponibles 
POST:  /api/v1/user/auth/signup = Crear usuario

POST:  /api/v1/user/auth/signin = Iniciar sesion

PATCH: /api/v1/user/{id} = Modificar un usuario

GET:   /api/v1/user/ = Listar todos los usuarios

GET:   /api/v1/user/{id} = Obtener un usuario por id

DELETE:/api/v1/user/{id} = Eliminar un usuario

# Diagrama para el sign-up y singup implemente el Authentication Manager que ya tenemos con Spring Security
![image](https://github.com/user-attachments/assets/dccb61c1-c6c2-4f8d-a9de-ee3a4de287e2)

![image](https://github.com/user-attachments/assets/6fff2d08-53c9-4f3b-b294-20ed80a3e6e5)



# Prerequisitos
1- Java 17

2- Spring boot 


# Instalación, ejecución y pruebas 
1- Clonar repositorio

2- Abrir la terminal en la raiz del proyecto

3- Compilar el proyecto con el siguiente comando: ./mvnw clean install

4- Executar proyecto : ./mvnw spring-boot:run

# Probar aplicacion con postman
Dentro del proyecto se encuentra un archivo con los endpoints importados con postman , debes exportarlos:
![image](https://github.com/user-attachments/assets/256a17bb-6e61-4c6a-8a75-7e29647229a5)

Tienes los endpoints ordenados, los dos primeros endpoints son para Crear el usuario e iniciar sesion (signup y singin)
![image](https://github.com/user-attachments/assets/33fa0e6a-73c6-4a6b-8f36-0242364c5341)


Los demás endpoints, necesitan autenticación, debes usar el token que devuelve en el endpoint de signup o singin e insertarlos en la sección de authentication del postman.
* Aqui se ve el response del singup y singin
* ![image](https://github.com/user-attachments/assets/480e1963-cdb9-4f35-9605-ce5fbb0d240e)

* ![image](https://github.com/user-attachments/assets/39a4c8fe-4302-43e0-9a64-36cf031377fc)

Copiar el valor del token en esta sección
![image](https://github.com/user-attachments/assets/f79a7a36-cc60-41fb-936d-7e89828022d2)





