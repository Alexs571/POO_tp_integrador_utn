
# Sistema de Gestión de Pacientes y Historias Clínicas  
Aplicación desarrollada en **Java** con acceso a datos mediante **JDBC** y almacenamiento en **MySQL**.  
Permite:

- Crear pacientes junto con su historia clínica.
- Buscar / seleccionar pacientes por DNI.
- Listar / actualizar / eliminar pacientes.
- Ver, actualizar y eliminar la **historia clínica del paciente seleccionado**.

---

## 1. Requisitos del sistema

### Software necesario
- **Java JDK 17** (recomendado) o 11+
- **MySQL Server 8.x**
- **MySQL Workbench** o consola MySQL
- **Git** (opcional)
- IDE recomendado: **IntelliJ IDEA**

### Dependencias
- **MySQL Connector/J 8.0.x**  
  Si el proyecto usa Maven:

```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
</dependency>
```

Si NO usa Maven, agregar el `.jar` manualmente al classpath:

```
mysql-connector-j-8.x.x.jar
```

---

## 2. Instalación y configuración

### Paso 1 — Clonar el repositorio

```bash
git clone https://github.com/tuusuario/tu-repo.git
cd tu-repo
```

### Paso 2 — Crear la base de datos MySQL

Ejecutar el script `baseDeDatos.sql` incluido en el proyecto:

```sql
CREATE DATABASE clinica2;
USE clinica2;
```

### Paso 3 — Configurar conexión a la BD

Editar `config/DataBaseConnection.java`:

```java
private static final String URL      = "jdbc:mysql://localhost:3308/clinica2";
private static final String USER     = "root";
private static final String PASSWORD = "tu_password";
```

---

## 3. Arquitectura del proyecto

```
src/main/java/
 ├── main/               
 ├── DAO/                
 ├── Models/             
 ├── Service/            
 └── config/             
```

---

## 4. Ejecución

```bash
java main.Main
```

---

## 5. Uso del sistema

- Crear paciente
- Ver paciente por DNI
- Ver / actualizar / eliminar historia clínica
- Volver atrás
- Salir

---

## 6. Troubleshooting

### Column not found  
Verificar nombres de columnas en el DAO.

### No se actualiza hasta reiniciar  
Refrescar historia después del update.

---

## 7. Git

```bash
git checkout test
git add .
git commit -m "Actualizaciones"
git checkout master
git merge test
git push
```

---

## 8. Autor

Trabajo realizado por **Walter Frias,Lujan Gonzalo, Tobías Pschepiurka, Alexis Da Silva **  
Tecnicatura Universitaria en Programación – UTN
