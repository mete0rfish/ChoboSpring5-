package config;

import controller.RegisterRequestValidator;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public Validator getValidator() {
        return new RegisterRequestValidator();
    }

    @Override
    public void configureDefaultServletHandling(
            DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/view/",".jsp");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/main").setViewName("main");
    }

    @Bean
    public MessageSource messageSource(){
        ResourceBundleMessageSource ms =
                new ResourceBundleMessageSource();
        ms.setBasenames("message.label"); // message 패키지에 속한 label 프로퍼티 파일로부터 메시지를 읽어온다고 설정한 것.
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }
}
