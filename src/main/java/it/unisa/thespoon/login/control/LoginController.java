package it.unisa.thespoon.login.control;

import it.unisa.thespoon.login.service.LoginServiceImpl;
import it.unisa.thespoon.model.request.SignupRequest;
import it.unisa.thespoon.model.response.JwtAuthenticationResponse;
import it.unisa.thespoon.model.request.LoginRequest;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * @author Jacopo Gennaro Esposito
 * Controller contenente gli endpoint delle API di TheSpoon per il sottosistema di login
 * */
@Validated
@RestController
@RequestMapping("/auth")
public class LoginController {
    private final LoginServiceImpl loginService;

    public LoginController(LoginServiceImpl loginService) {
        this.loginService = loginService;
    }

    //POST http://localhost:8080/login
    @PostMapping("/login")
    public JwtAuthenticationResponse Login(@Valid @RequestBody LoginRequest loginRequest){
        return loginService.login(loginRequest);
    }


    @PostMapping("/signup")
    public JwtAuthenticationResponse SignUp(@Valid @RequestBody SignupRequest signupRequest){
        return loginService.signUP(signupRequest);
    }
}
