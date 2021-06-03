package payments;

import org.mitre.dsmiley.httpproxy.ProxyServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

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
}
