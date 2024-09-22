package spring.board.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class WebConfig {

    private static final String[] AUTH_WHITELIST = {
            "/swagger-ui/**",   // Swagger URL
            "/v3/api-docs/**",  // Swagger docs URL
            "/board/**",        // Board 관련 URL
            "/comments/**"      // Comments 관련 URL
    };

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedMethod("GET");  // 허용할 HTTP 메서드들
        configuration.addAllowedMethod("POST");
        configuration.addAllowedMethod("PUT");
        configuration.addAllowedMethod("DELETE");
        configuration.addAllowedMethod("OPTIONS");
        configuration.addAllowedHeader("*");  // 모든 헤더 허용
        configuration.setAllowCredentials(true);  // 자격 증명 허용

        // 특정 경로에 대해 CORS 설정 적용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        for (String path : AUTH_WHITELIST) {
            source.registerCorsConfiguration(path, configuration);
        }
        return source;
    }
}
