package payments;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.n2oapp.framework.api.rest.ControllerFactory;
import net.n2oapp.framework.api.ui.ErrorMessageBuilder;
import net.n2oapp.framework.boot.ObjectMapperConstructor;
import net.n2oapp.framework.ui.controller.N2oControllerFactory;
import net.n2oapp.framework.ui.controller.action.SetController;
import net.n2oapp.framework.ui.controller.query.GetController;
import org.mitre.dsmiley.httpproxy.ProxyServlet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @Bean
    //Все запросы с n2o будут использовать этот токен для аутентификации
    public RestTemplateBuilder restTemplate() {
        return new RestTemplateBuilder()
                .basicAuthentication("user", "pass");
    }

    @Bean
    ServletRegistrationBean<ProxyServlet> proxyInputDataServiceServlet(
            @Value("${coinkeeper.backend.url}") String inputDataUrl
    ) {
        ServletRegistrationBean<ProxyServlet> bean =
                new ServletRegistrationBean<>(new ProxyServlet(), "/proxy/backend/api/*");
        Map<String, String> params = new HashMap<>();
        params.put("targetUri", inputDataUrl);
        params.put(ProxyServlet.P_LOG, "true");
        bean.setInitParameters(params);
        return bean;
    }

//
//    @Bean
//    public ObjectMapper objectMapper() {
//        return ObjectMapperConstructor.metaObjectMapper();
//    }
//
//    @Bean
//    public ControllerFactory controllerFactory(Map<String, SetController> setControllers,
//                                               Map<String, GetController> getControllers) {
//        Map<String, Object> controllers = new HashMap<>();
//        controllers.putAll(setControllers);
//        controllers.putAll(getControllers);
//        return new N2oControllerFactory(controllers);
//    }
//
//
//    @Bean
//    public ErrorMessageBuilder errorMessageBuilder(@Qualifier("n2oMessageSourceAccessor") MessageSourceAccessor messageSourceAccessor) {
//        return new ErrorMessageBuilder(messageSourceAccessor);
//    }
//
//    @Bean
//    public WebMvcConfigurer forwardToIndex() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addViewControllers(ViewControllerRegistry registry) {
////                registry.addViewController("/").setViewName("redirect:/editor/");
//                registry.addViewController("/view/*/").setViewName("forward:/index.html");
//                registry.addViewController("/editor/*/").setViewName("forward:/editor/index.html");
//            }
//
//            @Override
//            public void addResourceHandlers(ResourceHandlerRegistry registry) {
//                registry.addResourceHandler("/view/*/static/**")
//                        .addResourceLocations("/static/").resourceChain(true).addResolver(new WebStaticResolver("META-INF/resources"));
//
//                registry.addResourceHandler(
//                        "/view/*/serviceWorker.js",
//                        "/view/*/manifest.json",
//                        "/view/*/favicon.ico",
//                        "/editor/*/*",
//                        "/editor/*/static/**"
//                )
//                        .addResourceLocations("/editor/static/").resourceChain(true).addResolver(new WebStaticResolver("META-INF/resources/editor"));
//            }
//
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**").allowedMethods("*");
//            }
//        };
//    }
}
