package medisystem.avanzada.uq.citas_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI cumOpenAPI(@Value("${spring.application.version:0.0.1-SNAPSHOT}") String appVersion) {
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("MediSystem")
                        .version(appVersion)
                        .description("Aplicación web en Java Spring Boot para gestión de citas médicas. "
                                + "Permite a pacientes agendar, modificar y cancelar citas; "
                                + "y a médicos administrar horarios, especialidades y fórmulas médicas."))
                // 🔒 Sección de seguridad JWT
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Introduce tu token JWT obtenido desde /auth/login (sin 'Bearer ')")
                        )
                );
    }
}
