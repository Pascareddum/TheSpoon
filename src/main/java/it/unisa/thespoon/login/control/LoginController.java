package it.unisa.thespoon.login.control;

import it.unisa.thespoon.login.service.LoginServiceImpl;
import it.unisa.thespoon.model.request.SignupRequest;
import it.unisa.thespoon.model.response.JwtAuthenticationResponse;
import it.unisa.thespoon.model.request.LoginRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * @author Jacopo Gennaro Esposito
 * Controller contenente gli endpoint delle API di TheSpoon per il sottosistema di login
 * */
@RestController
@RequestMapping("/auth")
public class LoginController {
    private final LoginServiceImpl loginService;

    public LoginController(LoginServiceImpl loginService) {
        this.loginService = loginService;
    }

    //POST http://localhost:8080/login
    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> Login(@RequestBody LoginRequest loginRequest){
        JwtAuthenticationResponse r = loginService.login(loginRequest);

        return ResponseEntity.status(HttpStatus.OK).body(r);
    }


    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> SignUp(@RequestBody SignupRequest signupRequest){
        JwtAuthenticationResponse r = loginService.signUP(signupRequest);
        if(r != null){
            return ResponseEntity.status(HttpStatus.OK).body(r);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
}
