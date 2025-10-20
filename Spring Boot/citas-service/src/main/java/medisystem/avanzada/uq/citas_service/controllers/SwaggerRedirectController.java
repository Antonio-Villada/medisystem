package medisystem.avanzada.uq.citas_service.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerRedirectController {

    @GetMapping("/swagger-ui.html")
    public String redirectToSwagger() {
        return "redirect:/swagger-ui/index.html";
    }
}
