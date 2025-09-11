package medisystem.avanzada.uq.citas_service.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MiController {


    @GetMapping("/")
    public String pruebas(){

        return "p";
    }
}
