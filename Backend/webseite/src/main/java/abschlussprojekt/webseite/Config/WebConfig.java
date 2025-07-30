package abschlussprojekt.webseite.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Konfigurieren Sie CORS für alle Endpunkte
        registry.addMapping("/api/**")  // Alle API-Endpunkte
                .allowedOrigins("http://localhost:3000")  // Erlaubter Ursprung
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Erlaubte HTTP-Methoden
                .allowedHeaders("*");  // Erlaubte Header (wenn du bestimmte Header beschränken möchtest, kannst du dies anpassen)
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Füge eine Ressourcenkonfiguration für das Upload-Verzeichnis hinzu
        registry.addResourceHandler("/uploads/images/**")
                .addResourceLocations("file:C:/Users/Albertr/Desktop/Abschlussproekt/Backend/webseite/uploads/images/"); // Ersetze '/path/to/uploads/' mit dem tatsächlichen Pfad
    }
}
