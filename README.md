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
11. [Spring Boot y @SpringBootApplication](#spring-boot-y-springbootapplication)
12. [Resumen visual de las anotaciones principales](#resumen-visual-de-las-anotaciones-principales)
13. [Cómo se relaciona con este proyecto](#cómo-se-relaciona-con-este-proyecto)

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

---

> *"Spring no es solo un framework de inyección de dependencias; es un modelo
> de programación que promueve el desacoplamiento, la testabilidad y la
> mantenibilidad del código."*
