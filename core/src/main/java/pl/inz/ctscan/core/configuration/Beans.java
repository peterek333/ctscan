package pl.inz.ctscan.core.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import pl.inz.ctscan.core.utils.FileManager;
import pl.inz.ctscan.core.utils.queue.ProcessFileExecutor;
import pl.inz.ctscan.core.utils.response.DbFormatConverter;
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
    public UserValidator userValidator() { return new UserValidator(); }

    @Bean
    public ProcessFileExecutor processFileExecutor() {
        return new ProcessFileExecutor(MAX_THREADS);
    }

    @Bean
    public DbFormatConverter dbFormatConverter() {
        return new DbFormatConverter();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE");
            }
        };
    }
}
