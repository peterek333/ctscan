package pl.inz.ctscan.core.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pl.inz.ctscan.core.utils.FileManager;
import pl.inz.ctscan.core.utils.ImageConverter;
import pl.inz.ctscan.core.utils.queue.pool.ProcessFileExecutor;
import pl.inz.ctscan.model.validator.UserValidator;

@Configuration
public class Beans {

    @Value("${process.file.executor.maxthread}")
    private int MAX_THREADS;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());

        return source;
    }

    @Bean
    public FileManager fileManager() {
        return new FileManager();
    }

    @Bean
    public ImageConverter imageConverter() {
        return new ImageConverter();
    }

    @Bean
    public UserValidator userValidator() { return new UserValidator(); }

    @Bean
    public ProcessFileExecutor processFileExecutor() {
        return new ProcessFileExecutor(MAX_THREADS);
    }
}
