package dgtic.core.inicio;

import dgtic.core.modelo.Persona;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class Inicio {
    public static void main(String[] args) {
        final Resource resource = new ClassPathResource("bean-configuration.xml");

        final DefaultListableBeanFactory beanFactory
                = new DefaultListableBeanFactory();

        final XmlBeanDefinitionReader xmlBeanDefinitionReader
                = new XmlBeanDefinitionReader(beanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions(resource);

        Persona persona = (Persona) beanFactory.getBean("persona");
        System.out.println(persona.getNombre());
        System.out.println(persona);
        System.out.println(beanFactory.getBean("persona") instanceof Persona);
        System.out.println(beanFactory.isSingleton("persona"));
        System.out.println(beanFactory.isTypeMatch("persona", Persona.class));
        System.out.println(beanFactory.getAliases("persona").length);

    }
}
