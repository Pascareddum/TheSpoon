package it.unisa.thespoon.login.control;

import it.unisa.thespoon.login.service.LoginServiceImpl;
import it.unisa.thespoon.model.entity.Ristoratore;
import it.unisa.thespoon.model.request.LoginRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    private final LoginServiceImpl loginService;

    public LoginController(LoginServiceImpl loginService) {
        this.loginService = loginService;
    }

    //POST http://localhost:8080/login
    @PostMapping
    public String Login(@RequestBody LoginRequest loginRequest){
        Ristoratore r = loginService.login(loginRequest.getEmail(), loginRequest.getPassword());
        System.out.println(r.Cognome);
        return r.Cognome;
    }
}
