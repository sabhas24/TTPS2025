# Solución al Error: Unable to load class [org.h2.Driver]

## Problema

El error ocurría al intentar crear el `EntityManagerFactory` para la unidad de persistencia 'unlp-test':

```
Error al crear EntityManagerFactory for unit 'unlp-test': 
Unable to create requested service [org.hibernate.engine.jdbc.env.spi.JdbcEnvironment] 
due to: Unable to load class [org.h2.Driver]
```

## Causa

1. La dependencia de H2 Database no estaba incluida en `pom.xml`
2. No existía una unidad de persistencia llamada 'unlp-test' configurada para usar H2
3. El classpath de ejecución no contenía el driver org.h2.Driver

## Solución Implementada

### 1. Agregada Dependencia H2 en pom.xml

```xml
<!-- H2 Database (for testing) -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <version>2.2.220</version>
    <scope>test</scope>
</dependency>
```

El `scope` es `test` porque H2 se utiliza únicamente para pruebas, no en producción.

### 2. Creada Configuración de Persistencia para Tests

Se creó el archivo `src/test/resources/META-INF/persistence.xml` con la unidad 'unlp-test':

```xml
<persistence-unit name="unlp-test" transaction-type="RESOURCE_LOCAL">
    <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
    <!-- ... clases de entidades ... -->
    <properties>
        <property name="jakarta.persistence.jdbc.driver" value="org.h2.Driver"/>
        <property name="jakarta.persistence.jdbc.url" value="jdbc:h2:mem:unlp_test;DB_CLOSE_DELAY=-1"/>
        <property name="jakarta.persistence.jdbc.user" value="sa"/>
        <property name="jakarta.persistence.jdbc.password" value=""/>
        <property name="hibernate.dialect" value="org.hibernate.dialect.H2Dialect"/>
        <property name="hibernate.hbm2ddl.auto" value="create-drop"/>
        <property name="hibernate.show_sql" value="true"/>
        <property name="hibernate.format_sql" value="true"/>
    </properties>
</persistence-unit>
```

## Configuración de Bases de Datos

El proyecto ahora soporta dos configuraciones:

### Producción (unidad 'unlp')
- Base de datos: MySQL
- Configuración: `src/main/resources/META-INF/persistence.xml`
- Uso: Aplicación en runtime

### Testing (unidad 'unlp-test')
- Base de datos: H2 in-memory
- Configuración: `src/test/resources/META-INF/persistence.xml`
- Uso: Ejecución de pruebas unitarias

## Uso

Para usar la unidad de persistencia de tests:

```java
EntityManagerFactory emf = Persistence.createEntityManagerFactory("unlp-test");
EntityManager em = emf.createEntityManager();
// ... operaciones de prueba ...
em.close();
emf.close();
```

## Ventajas de H2 para Testing

1. **Base de datos en memoria**: No requiere instalación de MySQL
2. **Rápida**: Las pruebas se ejecutan más rápido
3. **Aislamiento**: Cada test puede usar una base de datos limpia
4. **Portabilidad**: Las pruebas funcionan en cualquier entorno sin configuración adicional

## Verificación

Para verificar que la solución funciona:

```bash
mvn test -Dtest=TestH2Connection
```

Este test verifica que:
- El driver H2 se carga correctamente
- El EntityManagerFactory se crea sin errores
- Se puede ejecutar una consulta contra la base de datos H2

## Nota sobre Java Version

El proyecto requiere Java 21 según `pom.xml`. Si se encuentra un error de compilación relacionado con la versión de Java, asegúrese de tener Java 21 instalado y configurado correctamente.
