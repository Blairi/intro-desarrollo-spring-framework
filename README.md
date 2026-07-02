# Introducción al Desarrollo con Spring Framework

Guía de fundamentos del core de Spring, diseñada como material de apoyo para
comprender los módulos de este repositorio.

## Índice

1.  [¿Qué es Spring Framework?](#qué-es-spring-framework)
2.  [Inversión de Control (IoC) y Dependency Injection (DI)](#inversión-de-control-ioc-y-dependency-injection-di)
3.  [El contenedor de Spring (IoC Container)](#el-contenedor-de-spring-ioc-container)
4.  [Beans en Spring](#beans-en-spring)
5.  [Anotaciones de estereotipo (Stereotype Annotations)](#anotaciones-de-estereotipo-stereotype-annotations)
6.  [Anotaciones de configuración](#anotaciones-de-configuración)
7.  [Autowiring: inyección automática de dependencias](#autowiring-inyección-automática-de-dependencias)
8.  [Ciclo de vida de un bean](#ciclo-de-vida-de-un-bean)
9.  [Scopes (ámbitos) de beans](#scopes-ámbitos-de-beans)
10. [Externalización de configuración con @Value](#externalización-de-configuración-con-value)
11. [Acceso a datos con Spring](#acceso-a-datos-con-spring)
12. [Spring Boot y @SpringBootApplication](#spring-boot-y-springbootapplication)
13. [Resumen visual de las anotaciones principales](#resumen-visual-de-las-anotaciones-principales)
14. [Cómo se relaciona con este proyecto](#cómo-se-relaciona-con-este-proyecto)

---

## ¿Qué es Spring Framework?

Spring es un **framework de desarrollo de aplicaciones Java** cuyo núcleo es un
**contenedor de inversión de control (IoC)**. En lugar de que los objetos creen
o busquen sus propias dependencias, Spring las inyecta desde el exterior. Esto
desacopla las capas de la aplicación y facilita las pruebas, el mantenimiento y
la evolución del código.

Este repositorio contiene módulos que recorren los conceptos fundamentales de
Spring: desde el acoplamiento directo (sin framework), pasando por la
configuración XML, hasta la configuración con anotaciones y Spring Boot.

---

## Inversión de Control (IoC) y Dependency Injection (DI)

### Acoplamiento directo (sin Spring)

Los módulos `EjercicioUno` y `EjercicioDos` muestran el problema inicial:

```java
// EjercicioUno: la clase crea sus propias dependencias (alto acoplamiento)
public class Profesor {
    private List<Responsabilidades> responsabilidades;

    public Profesor() {
        this.responsabilidades = new ArrayList<>();
        this.responsabilidades.add(new ExplicarClase());
        this.responsabilidades.add(new Calificar());
        this.responsabilidades.add(new Reportes());
    }
}
```

Problemas:
- `Profesor` está acoplado a implementaciones concretas (`ExplicarClase`,
  `Calificar`, `Reportes`).
- Para cambiar las responsabilidades hay que modificar el código de `Profesor`.
- Es difícil probar `Profesor` de forma aislada.

### Inversión de Control (IoC)

IoC significa **invertir el control**: en lugar de que los objetos controlen
la creación y localización de sus dependencias, un **contenedor externo**
gestiona ese ciclo de vida.

### Dependency Injection (DI)

DI es la forma concreta en que Spring implementa IoC: el contenedor **inyecta**
las dependencias en los objetos en lugar de que estos las creen.

```java
// Con DI: Profesor recibe sus dependencias desde el exterior
public class Profesor {
    private List<Responsabilidades> responsabilidades;

    // La dependencia se inyecta por constructor
    public Profesor(List<Responsabilidades> responsabilidades) {
        this.responsabilidades = responsabilidades;
    }
}
```

Formas de inyección en Spring:

| Tipo | Cómo se hace | Ejemplo |
|---|---|---|
| **Constructor** | Parámetros del constructor | `public Clase(Dependencia dep)` |
| **Setter** | Método `setXxx()` | `@Autowired public void setDep(Dependencia dep)` |
| **Campo (field)** | Anotación directa sobre el atributo | `@Autowired private Dependencia dep` |

---

## El contenedor de Spring (IoC Container)

Spring ofrece dos interfaces principales para el contenedor:

### BeanFactory

Es la interfaz más básica. Los módulos `spring-core-beanfactory` la usan
directamente:

```java
DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
reader.loadBeanDefinitions("bean-configuration.xml");
Persona p = factory.getBean("persona", Persona.class);
```

- **Evaluación perezosa (lazy)**: los beans solo se crean cuando se solicitan.
- Ideal cuando los recursos son limitados.

### ApplicationContext

Es una extensión de `BeanFactory` que añade:
- Carga anticipada (eager) de beans singleton.
- Internacionalización (i18n).
- Publicación de eventos.
- Integración con AOP.

```java
ApplicationContext context =
    new ClassPathXmlApplicationContext("bean-configuration.xml");
Persona p = context.getBean("persona", Persona.class);
```

En los módulos con anotaciones se usa `AnnotationConfigApplicationContext`:

```java
ApplicationContext context =
    new AnnotationConfigApplicationContext(ConfiguracionServicio.class);
```

---

## Beans en Spring

Un **bean** es un objeto gestionado por el contenedor de Spring. Se define
mediante:

1.  Configuración XML (`<bean id="..." class="..."/>`)
2.  Anotación `@Bean` dentro de una clase `@Configuration`
3.  Anotación de estereotipo (`@Component`, `@Service`, etc.) con
    `@ComponentScan`

### Definición con XML

```xml
<bean id="empleado" class="dgtic.core.modelo.Empleado">
    <property name="nombre" value="Blair"/>
    <property name="edad" value="25"/>
</bean>
```

### Definición con @Bean

```java
@Configuration
public class ConfiguracionServicio {

    @Bean
    public Empleado empleado() {
        Empleado emp = new Empleado();
        emp.setNombre("Blair");
        emp.setEdad(25);
        return emp;
    }
}
```

### Definición con anotación de estereotipo

```java
@Component
public class Automovil {
    private String marca;
    private String modelo;
    // ...
}
```

Cuando Spring escanea el paquete (con `@ComponentScan`), encuentra la
anotación y registra automáticamente la clase como un bean. El nombre del bean
por defecto es el nombre de la clase en camelCase (`automovil`).

---

## Anotaciones de estereotipo (Stereotype Annotations)

Spring proporciona cuatro anotaciones de estereotipo. **Técnicamente son
idénticas** (todas son un alias de `@Component`), pero semánticamente indican
el rol de la clase en la arquitectura.

| Anotación | Rol arquitectónico | Dónde se usa |
|---|---|---|
| `@Component` | Componente genérico | Cualquier clase que no encaje en los otros roles |
| `@Service` | Lógica de negocio | Clases que contienen la lógica de negocio (servicios) |
| `@Repository` | Acceso a datos (DAO) | Clases que interactúan con la base de datos |
| `@Controller` | Capa de presentación (MVC) | Controladores web en Spring MVC |

### ¿Por qué tener cuatro si son lo mismo?

Spring utiliza estas anotaciones para **propósitos específicos** más allá del
escaneo:

1.  **`@Repository`**: Spring automáticamente traduce las excepciones
    específicas de la tecnología de persistencia (como `SQLException`) a
    excepciones de la jerarquía `DataAccessException`.

2.  **`@Service`**: Marca la capa de negocio. Es puramente semántica — ayuda a
    identificar la intención de la clase.

3.  **`@Controller`**: Spring MVC la reconoce para mapear peticiones web. Al
    igual que `@Repository`, activa un comportamiento específico (detección de
    métodos `@RequestMapping`, `@GetMapping`, etc.).

4.  **`@Component`**: Anotación base. Cualquier clase anotada con
    `@Repository`, `@Service` o `@Controller` es también un `@Component`.

### Ejemplos del proyecto

```java
// @Repository: capa de persistencia
@Repository("baseDeDatosDAO")
public class BaseDeDatosDAOImpl implements BaseDeDatosDAO {
    public List<Estudiante> getEstudiantes(String carrera) { ... }
}

// @Service: capa de negocio
@Service
public class ServicioDAO {
    @Autowired
    @Qualifier("baseDeDatosDAOExtra")
    private BaseDeDatosDAO baseDeDatosDAO;
}

// @Service: otra implementación de servicio
@Service
public class AutomovilServiceImpl implements AutomovilService {
    @Autowired
    private Automovil automovil;
}

// @Component: bean genérico
@Component
public class Automovil {
    private String marca = "Volkswagen";
    private String modelo = "Vocho";
}

// @Controller: capa web MVC
@Controller
@RequestMapping("/portal")
public class ControladorPrincipal {
    @Autowired
    private AutomovilService automovilService;

    @GetMapping("/home")
    public String home(Model model) { ... }
}
```

### Jerarquía interna

```
@Component
  ├── @Service
  ├── @Repository
  └── @Controller
       └── @RestController (Spring 4+)
```

`@RestController` es una anotación combinada que incluye `@Controller` y
`@ResponseBody`.

### ¿Cuándo usar cada una?

- **`@Component`**: Clases de utilería, beans de infraestructura, wrappers, o
  cualquier clase que no pertenezca claramente a servicio, repositorio o
  controlador. Ejemplo del proyecto: `Automovil`, `Empleado` en `m5integrador`,
  `ServiceOffering`, `Account`, `Category`, `Review` y
  `SISPRO3DApplicationRunner` en `sispro3d`.

- **`@Service`**: Clases que implementan lógica de negocio, orquestan
  repositorios o realizan transformaciones de datos. Ejemplos del proyecto:
  `ServicioDAO`, `AutomovilServiceImpl`, `ServicioEmpleadoImpl`,
  `SISPRO3DService`.

- **`@Repository`**: Clases de acceso a datos (DAO). Ejemplos del proyecto:
  `BaseDeDatosDAOImpl`, `BaseDeDatosExtraDAOImpl`.

- **`@Controller`**: Clases que manejan peticiones HTTP y devuelven vistas o
  respuestas. Ejemplo del proyecto: `ControladorPrincipal`.

---

## Anotaciones de configuración

### @Configuration

Indica que una clase define beans mediante métodos anotados con `@Bean`. Es una
clase de configuración que reemplaza al XML.

```java
@Configuration
@ComponentScan(basePackages = "dgtic.core")
@ImportResource("classpath:bean-configuration.xml")
public class ConfiguracionServicio {

    @Bean
    @Scope("prototype")
    public ReporteEmpleadoServicio reporteEmpleadoServicio() {
        return new ReporteEmpleadoServicioImpl();
    }
}
```

### @ComponentScan

Le dice a Spring en qué paquetes buscar clases anotadas con `@Component`,
`@Service`, `@Repository`, `@Controller`, etc.

Si no se especifica `basePackages`, escanea el paquete de la clase anotada con
`@Configuration`.

### @Bean

Marca un método que devuelve un objeto que debe ser registrado como un bean en
el contenedor de Spring. El nombre del bean es el nombre del método (a menos
que se especifique un `name`).

---

## Autowiring: inyección automática de dependencias

### @Autowired

Le indica a Spring que debe inyectar automáticamente una dependencia. Spring
busca un bean compatible por tipo.

```java
@Service
public class ServicioEmpleadoImpl implements ServicioEmpleado {

    @Autowired
    private Empleado empleado;  // Spring inyecta el bean Empleado aquí

    @Override
    public void servicioEmpleado() {
        System.out.println(empleado.getNombre());
    }
}
```

### @Qualifier

Cuando hay **múltiples beans del mismo tipo**, `@Qualifier` especifica cuál
inyectar por nombre:

```java
@Service
public class ServicioDAO {

    @Autowired
    @Qualifier("baseDeDatosDAOExtra")
    private BaseDeDatosDAO baseDeDatosDAO;
}
```

Sin `@Qualifier`, Spring lanzaría una excepción por no poder determinar de
manera única la dependencia.

### @Primary

Cuando hay **múltiples beans del mismo tipo**, uno puede marcarse como
`@Primary` para que sea el predeterminado. Spring lo usará a menos que se
especifique un `@Qualifier`.

```java
@Repository("baseDeDatosDAOExtra")
@Primary
public class BaseDeDatosExtraDAOImpl implements BaseDeDatosDAO { ... }
```

Prioridad: `@Qualifier` > `@Primary` > error por ambigüedad.

---

## Ciclo de vida de un bean

Spring gestiona el ciclo de vida completo de los beans: creación, inicialización
y destrucción.

### Configuración XML

```xml
<bean id="empleado" class="..." scope="prototype"
      init-method="iniciar" destroy-method="limpiar"/>
```

### Anotaciones

```java
@Component
public class Empleado {

    @PostConstruct
    public void iniciar() {
        System.out.println("Bean Empleado inicializado: " + this.nombre);
    }

    @PreDestroy
    public void limpiar() {
        System.out.println("Bean Empleado destruido: " + this.nombre);
    }
}
```

| Fase | XML | Anotación |
|---|---|---|
| Después de construir e inyectar dependencias | `init-method` | `@PostConstruct` |
| Antes de destruir el bean | `destroy-method` | `@PreDestroy` |

**Nota**: Los beans con `scope="prototype"` no ejecutan `destroy-method` ni
`@PreDestroy` porque Spring no gestiona su ciclo de vida completo — los crea y
los entrega, pero no rastrea su destrucción.

---

## Scopes (ámbitos) de beans

| Scope | Descripción | Por defecto |
|---|---|---|
| **singleton** | Una sola instancia por contenedor de Spring | ✅ |
| **prototype** | Una nueva instancia cada vez que se solicita | ❌ |

### Singleton

```java
Persona p1 = context.getBean("persona", Persona.class);
Persona p2 = context.getBean("persona", Persona.class);
// p1 == p2 → true (misma instancia)
```

### Prototype

```java
// Configuración con @Bean
@Bean
@Scope("prototype")
public ReporteEmpleadoServicio reporteEmpleadoServicio() {
    return new ReporteEmpleadoServicioImpl();
}

// Configuración con anotación
@Component
@Scope("prototype")
public class MiBean { ... }
```

```java
ReporteEmpleadoServicio r1 = context.getBean(...);
ReporteEmpleadoServicio r2 = context.getBean(...);
// r1 != r2 → true (instancias diferentes)
```

El módulo `spring-core-scope` demuestra esta diferencia con el patrón
`factory-bean` + `factory-method`.

---

## Externalización de configuración con @Value

`@Value` inyecta valores desde archivos de propiedades (u otras fuentes) en los
beans.

```properties
# application.properties
EMPLEADO_NOMBRE=Juan
EMPLEADO_DEPARTAMENTO=Desarrollo Sistemas
```

```java
@Component
public class Empleado {

    @Value("${EMPLEADO_NOMBRE}")
    private String nombre;

    @Value("${EMPLEADO_DEPARTAMENTO}")
    private String departamento;
}
```

Spring evalúa la expresión `${...}` y reemplaza con el valor de la propiedad.
Si la propiedad no existe, lanza una excepción. Se puede proporcionar un valor
por defecto: `${EMPLEADO_NOMBRE:ValorPorDefecto}`.

---

## Acceso a datos con Spring

El acceso a datos es una de las áreas donde Spring más valor aporta.
Este repositorio contiene módulos que muestran la **evolución progresiva**
desde JDBC puro hasta JPA con Spring Boot.

```
  JDBC puro         Spring JDBC              JPA / EntityManager
  (SQL a pelo)      (JdbcTemplate)           (ORM + JPQL)
       │                  │                        │
       ▼                  ▼                        ▼
    JDBC/             Template/            EntityManager/
                                                practicauno/
```

---

### JDBC puro (sin Spring)

El módulo `JDBC` muestra el enfoque clásico previo a Spring:

```java
// DAO.java: conexión y consulta JDBC manual
public class DAO {
    private Connection conexion;

    public DAO() throws ClassNotFoundException, SQLException {
        Class.forName("org.mariadb.jdbc.Driver");
        conexion = DriverManager.getConnection(url, usuario, psw);
    }

    public EntidadAlumno getAlumno(String matricula) {
        String query = "SELECT * FROM alumnos where matricula=?";
        PreparedStatement pstm = conexion.prepareStatement(query);
        pstm.setString(1, matricula);
        ResultSet datos = pstm.executeQuery();
        // mapeo manual fila → objeto...
    }

    public void cerrarConexion() throws SQLException {
        conexion.close();
    }
}
```

Problemas de este enfoque:
- **Boilerplate**: try/catch/finally, apertura/cierre de conexión,
  `PreparedStatement`, `ResultSet`.
- **Sin pool de conexiones**: cada operación abre y cierra una conexión real.
- **Excepciones ambiguas**: `SQLException` no distingue entre error de
  conexión, constraint violation o sintaxis SQL.
- **Mapeo manual**: cada consulta requiere código para convertir filas a
  objetos.

---

### Spring JDBC con JdbcTemplate

El módulo `Template` utiliza **Spring Framework 5** (no Boot) con
`JdbcTemplate` para eliminar el boilerplate del JDBC puro.

#### Configuración del DataSource con HikariCP

```java
@Configuration
@ComponentScan("mx.unam.dgtic")
public class AppConfig {

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mariadb://localhost:3306/modulo6");
        config.setUsername("appuser");
        config.setPassword("MiPasswordSegura123!");
        config.setDriverClassName("org.mariadb.jdbc.Driver");
        return new HikariDataSource(config);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
```

**HikariCP** es un pool de conexiones de alto rendimiento. En lugar de abrir
y cerrar una conexión real cada vez, mantiene un conjunto de conexiones
abiertas reutilizables, lo que mejora drásticamente el rendimiento.

**JdbcTemplate** es la clase central de `spring-jdbc`. Recibe un `DataSource`,
gestiona el ciclo de vida de la conexión (`Connection`, `PreparedStatement`,
`ResultSet`) y traduce `SQLException` a la jerarquía
`DataAccessException` de Spring.

#### Repositorio con JdbcTemplate

```java
@Repository
public class UsuarioDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Long guardar(Usuario usuario) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO usuario (nombre, email, edad) VALUES (?, ?, ?)";
        jdbcTemplate.update(conexion -> {
            PreparedStatement psm = conexion.prepareStatement(
                sql, Statement.RETURN_GENERATED_KEYS);
            psm.setString(1, usuario.getNombre());
            psm.setString(2, usuario.getEmail());
            psm.setInt(3, usuario.getEdad());
            return psm;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public List<Usuario> listarTodos() {
        String sql = "SELECT * FROM usuario";
        return jdbcTemplate.query(sql, new UsuarioRowMapper());
    }
}
```

`KeyHolder` captura la clave generada automáticamente por la base de datos
(autoincremental) después de un `INSERT`.

#### RowMapper

`RowMapper` es una interfaz funcional que convierte una fila del
`ResultSet` en un objeto de dominio:

```java
public class UsuarioRowMapper implements RowMapper<Usuario> {
    @Override
    public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("id"));
        usuario.setNombre(rs.getString("nombre"));
        usuario.setEmail(rs.getString("email"));
        usuario.setEdad(rs.getInt("edad"));
        return usuario;
    }
}
```

Spring llama a `mapRow` por cada fila del resultado y arma la lista
automáticamente.

#### DataSourceInitializer con schema.sql

```java
@Bean
public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
    DataSourceInitializer initializer = new DataSourceInitializer();
    initializer.setDataSource(dataSource);
    ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
    populator.addScript(new ClassPathResource("schema.sql"));
    initializer.setDatabasePopulator(populator);
    return initializer;
}
```

Permite ejecutar scripts SQL (`schema.sql`) al arrancar la aplicación para
crear o inicializar tablas:

```sql
DROP TABLE IF EXISTS usuario;
CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    edad INT
);
```

**Nota**: `ServicioUsuario` en este módulo crea manualmente el contexto de
Spring con `new AnnotationConfigApplicationContext(AppConfig.class)` y
obtiene los beans con `context.getBean(...)`. No tiene anotaciones Spring
porque actúa como bootstrap manual, no como un bean gestionado.

---

### JPA con EntityManager

Los módulos `EntityManager` y `practicauno` usan **Spring Boot 3.5 + JPA**
con la API de `EntityManager` (no Spring Data JPA) para realizar operaciones
de persistencia.

#### Diferencia clave: JDBC vs JPA

| Aspecto | JDBC / JdbcTemplate | JPA / EntityManager |
|---|---|---|
| **Modelo** | SQL puro (tablas → filas) | ORM (objetos ↔ tablas) |
| **Mapeo** | Manual con `RowMapper` | Automático con anotaciones `@Entity` |
| **Consultas** | SQL nativo (`SELECT * FROM...`) | JPQL (`SELECT u FROM Usuario u...`) |
| **Transacciones** | Manuales (`connection.commit()`) | Automáticas con `@Transactional` |
| **Cache** | Ninguno | Cache de primer nivel (persistence context) |
| **Cambio de BD** | Posible pero trabajoso | Transparente (cambia dialecto y driver) |

#### Configuración JPA con @Configuration

```java
@Configuration
@ComponentScan("mx.unam.dgtic")
public class AppConfig {

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        // ... mismo que en Template
        return new HikariDataSource(config);
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emfb =
            new LocalContainerEntityManagerFactoryBean();
        emfb.setDataSource(dataSource);
        emfb.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emfb.setPackagesToScan("mx.unam.dgtic");
        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.dialect",
            "org.hibernate.dialect.MariaDBDialect");
        jpaProperties.put("hibernate.hbm2ddl.auto", "create");
        emfb.setJpaProperties(jpaProperties);
        return emfb;
    }
}
```

- `LocalContainerEntityManagerFactoryBean` crea el `EntityManagerFactory`
  de JPA y lo registra como bean en el contenedor de Spring.
- `HibernateJpaVendorAdapter` configura Hibernate como proveedor JPA.
- `hibernate.hbm2ddl.auto": "create"` le dice a Hibernate que cree las
  tablas automáticamente a partir de las entidades anotadas.

#### Entidad JPA

```java
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long idProducto;

    @Column(length = 255)
    private String nombre;

    @Column(precision = 38, scale = 2)
    private BigDecimal precio;

    @Column
    private Integer stock;

    @Column(length = 255)
    private String imagen;
}
```

En `practicauno` se usa además **Lombok** para eliminar el boilerplate de
getters, setters, constructores y `toString`:

```java
@Entity
@Table(name = "productos")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class Producto { ... }
```

| Anotación JPA | Propósito |
|---|---|
| `@Entity` | Marca la clase como entidad JPA (mapeada a una tabla) |
| `@Table(name = "...")` | Especifica el nombre de la tabla en la BD |
| `@Id` | Marca el campo como clave primaria |
| `@GeneratedValue(strategy = ...)` | Estrategia de generación de claves |
| `@Column(...)` | Configura la columna (nombre, longitud, precisión, etc.) |

#### @PersistenceContext vs @Autowired para EntityManager

```java
@Repository
public class UsuarioRepository {

    @PersistenceContext
    private EntityManager entityManager;
    // ...
}
```

`@PersistenceContext` es la anotación estándar de JPA para inyectar un
`EntityManager`. A diferencia de `@Autowired`, garantiza que:

1.  El `EntityManager` esté **asociado a la transacción actual**
    (thread-safe).
2.  Cada transacción reciba su propia instancia (proxy) que participa en el
    contexto de persistencia correcto.

Usar `@Autowired` directamente con `EntityManager` no es seguro en
entornos transaccionales.

#### Operaciones del repositorio

```java
@Repository
public class ProductoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // CREATE / UPDATE
    @Transactional
    public Producto guardar(Producto producto) {
        if (producto.getIdProducto() == null) {
            entityManager.persist(producto);   // INSERT
            return producto;
        } else {
            return entityManager.merge(producto);  // UPDATE
        }
    }

    // DELETE por ID
    @Transactional
    public void eliminar(Long id) {
        Producto producto = entityManager.find(Producto.class, id);
        if (producto != null) {
            entityManager.remove(producto);
        }
    }

    // DELETE con JPQL
    @Transactional
    public void eliminarPorNombre(String nombre) {
        Query query = entityManager.createQuery(
            "DELETE FROM Producto p WHERE p.nombre=:nombre");
        query.setParameter("nombre", nombre);
        query.executeUpdate();
    }

    // READ por ID
    public Optional<Producto> buscarPorId(Long id) {
        Producto producto = entityManager.find(Producto.class, id);
        return Optional.ofNullable(producto);
    }

    // READ todos
    public List<Producto> buscarTodos() {
        TypedQuery<Producto> query = entityManager.createQuery(
            "SELECT p FROM Producto p", Producto.class);
        return query.getResultList();
    }
}
```

#### @Transactional

`@Transactional` delimita una transacción de base de datos. Spring inicia
una transacción antes del método y la confirma (commit) al finalizar; si
ocurre una excepción, hace rollback automático. Sin esta anotación, cada
operación con el `EntityManager` se ejecutaría en una transacción separada.

#### JPQL

JPQL (Java Persistence Query Language) es un lenguaje de consultas similar a
SQL pero que opera sobre **objetos** (entidades) en lugar de tablas:

```sql
-- SQL:        SELECT * FROM productos WHERE nombre LIKE '%Lap%'
-- JPQL:       SELECT p FROM Producto p WHERE p.nombre LIKE :nombre
```

Ventajas de JPQL:
- Independiente de la base de datos (el dialecto se traduce automáticamente).
- Trabaja con objetos, no con filas.
- Soporta joins, subconsultas, funciones de agregación.

El `p` en `SELECT p FROM Producto p` es un **alias** que representa cada
instancia de la entidad `Producto`.

#### Criteria API

La Criteria API permite construir consultas de forma **programática** (con
objetos Java) en lugar de cadenas JPQL:

```java
public List<Producto> buscarPorCriterios(
        String nombre, BigDecimal precioMin, BigDecimal precioMax) {

    var cb = entityManager.getCriteriaBuilder();
    var query = cb.createQuery(Producto.class);
    var root = query.from(Producto.class);
    var predicate = cb.conjunction();  // predicado base: siempre verdadero

    if (nombre != null && !nombre.isEmpty()) {
        predicate = cb.and(predicate,
            cb.like(root.get("nombre"), "%" + nombre + "%"));
    }
    if (precioMin != null) {
        predicate = cb.and(predicate,
            cb.greaterThanOrEqualTo(root.get("precio"), precioMin));
    }
    if (precioMax != null) {
        predicate = cb.and(predicate,
            cb.lessThanOrEqualTo(root.get("precio"), precioMax));
    }

    query.select(root).where(predicate);
    return entityManager.createQuery(query).getResultList();
}
```

Esto genera dinámicamente una consulta del tipo:

```sql
SELECT p FROM Producto p
WHERE p.nombre LIKE '%Lap%'
  AND p.precio >= 300
  AND p.precio <= 20000
```

La Criteria API es útil cuando los filtros de búsqueda son opcionales y
desconocidos en tiempo de compilación.

---

## Spring Boot y @SpringBootApplication

### @SpringBootApplication

Es una **anotación compuesta** que combina tres anotaciones:

```java
@SpringBootApplication
public class M5integradorApplication {
    public static void main(String[] args) {
        SpringApplication.run(M5integradorApplication.class, args);
    }
}
```

Equivale a:

| Anotación | Propósito |
|---|---|
| `@Configuration` | Marca la clase como fuente de definición de beans |
| `@EnableAutoConfiguration` | Activa la configuración automática de Spring Boot |
| `@ComponentScan` | Escanea el paquete y subpaquetes en busca de componentes |

### CommandLineRunner

Interfaz funcional que ejecuta código después de que el contexto de Spring se
haya inicializado. Útil para aplicaciones de consola.

```java
@Component
public class SISPRO3DApplicationRunner implements CommandLineRunner {
    @Override
    public void run(String... args) {
        // Este código se ejecuta al arrancar la aplicación
    }
}
```

### @SpringBootTest

Anotación para pruebas de integración que levanta el contexto completo de
Spring Boot.

```java
@SpringBootTest
class EmpleadoTest {
    @Autowired
    @Qualifier("supervisarPersonalImpl")
    private Actividad primeraActividad;
}
```

---

## Resumen visual de las anotaciones principales

```
                    ┌──────────────────────────────┐
                    │      @Configuration           │
                    │   Define beans con @Bean       │
                    └──────────┬───────────────────┘
                               │
                    ┌──────────▼───────────────────┐
                    │       @ComponentScan          │
                    │  Busca clases anotadas en     │
                    │  el paquete especificado      │
                    └──────────┬───────────────────┘
                               │
              ┌────────────────┼────────────────────┐
              ▼                ▼                     ▼
      ┌────────────┐   ┌───────────┐        ┌──────────────┐
      │ @Component │   │ @Service  │        │ @Repository  │
      │  Genérico  │   │ Negocio   │        │   Datos/DAO  │
      └────────────┘   └───────────┘        └──────────────┘
                                              ┌──────────────┐
                                              │ @Controller  │
                                              │   Web/MVC    │
                                              └──────────────┘
                         │
                         ▼
              ┌─────────────────────┐
              │   Inyección de      │
              │   dependencias      │
              │                     │
              │  @Autowired         │
              │  @Qualifier("nom")  │
              │  @Primary           │
              │  @Value("${prop}")  │
              └─────────────────────┘
```

---

## Cómo se relaciona con este proyecto

Este repositorio está organizado para aprender Spring de forma progresiva. La
siguiente tabla muestra qué concepto cubre cada módulo:

| Módulo | Concepto principal |
|---|---|
| `EjercicioUno` | Problema: acoplamiento directo (sin Spring) |
| `EjercicioDos` | Polimorfismo sin DI (mejoría parcial) |
| `spring-core-beans` | Definición de beans en XML |
| `spring-core-beanfactory` | Contenedor BeanFactory (low-level) |
| `spring-core-applicationcontext` | ApplicationContext con múltiples XML |
| `spring-core-initdestroy` | Ciclo de vida: init-method / destroy-method |
| `spring-core-scope` | Singleton vs Prototype |
| `spring-core-collection` | Inyección de colecciones (List, Set, Map, Properties) |
| `spring-core-autowire` | Autowiring por constructor en XML |
| `spring-core-factory` | Factory Method con factory-method |
| `spring-core-factory-dos` | Factory Method con factory-bean |
| `spring-core-javabean` | Configuración con @Configuration + @Bean + @ComponentScan |
| `spring-core-pojodao` | @Repository, @Service, @Autowired, @Qualifier, @Primary |
| `m5integrador` | Spring Boot: @Component, @Service, @Value, @PostConstruct, @PreDestroy |
| `m5demoweb` | Spring Boot + MVC: @Controller, @GetMapping, Thymeleaf |
| `sispro3d` | Spring Boot + Lombok: @Data, @Value masivo |
| `JDBC` | JDBC puro (sin Spring): DriverManager, PreparedStatement |
| `Template` | Spring JDBC 5: @Configuration, JdbcTemplate, RowMapper, HikariCP |
| `EntityManager` | Spring Boot + JPA: @Entity, EntityManager, JPQL, Criteria API |
| `practicauno` | Spring Boot + JPA + Lombok: EntityManager, CRUD de productos |

---

> *"Spring no es solo un framework de inyección de dependencias; es un modelo
> de programación que promueve el desacoplamiento, la testabilidad y la
> mantenibilidad del código."*
