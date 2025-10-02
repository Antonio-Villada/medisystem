package medisystem.avanzada.uq.citas_service.configurations;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI cumOpenAPI(@Value("0.0.1-SNAPSHOT")String appVersion){
        return new OpenAPI()
                .info(new Info()
                    .title(" MediSystem ")
                    .version(appVersion)
                    .description("Aplicación web desarrollada en Java Spring Boot para la gestión de citas médicas. " +
                            "Permite a los pacientes agendar, modificar y cancelar citas, mientras los médicos administran horarios y especialidades. " +
                            "Incluye panel de administración y base de datos para un control seguro y eficiente.")
                );

    }
}
