package cz.cvut.fel.aos.init;

import cz.cvut.fel.aos.config.RestConfig;
import cz.cvut.fel.aos.config.ServiceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
@SpringBootApplication
@Import({EmbeddedServletContainerAutoConfiguration.class, ServiceConfig.class})
public class AirlineServiceInit {

    /**
     * For development, run with --spring.profiles.active=dev
     */
    public static void main(String[] args) throws Exception {
        SpringApplication.run(AirlineServiceInit.class, args);
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(RestConfig.class);

        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(
                new DispatcherServlet(applicationContext),
                true,
                "/*"
        );
        servletRegistrationBean.setName("REST servlet registration");
        return servletRegistrationBean;
    }
}
