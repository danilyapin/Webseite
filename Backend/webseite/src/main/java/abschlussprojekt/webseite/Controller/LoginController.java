package abschlussprojekt.webseite.Controller;

import abschlussprojekt.webseite.DTO.LoginRequest;
import abschlussprojekt.webseite.DTO.LoginResponse;
import abschlussprojekt.webseite.Models.Benutzer;
import abschlussprojekt.webseite.Repository.BenutzerRepository;
import abschlussprojekt.webseite.Util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class LoginController {

    @Autowired
    private BenutzerRepository benutzerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        Benutzer benutzer = benutzerRepository.findByEmail(loginRequest.getEmail()).orElse(null);
        if (benutzer == null) {
            return ResponseEntity.badRequest().body("Benutzer nicht gefunden");
        }

        // Passwort prüfen
        boolean passwordCorrect = passwordEncoder.matches(loginRequest.getPasswort(), benutzer.getPasswort());
        if (!passwordCorrect) {
            return ResponseEntity.badRequest().body("Ungültige Anmeldedaten – Passwort stimmt nicht");
        }

        // Token erstellen
        String token = jwtTokenUtil.generateToken(benutzer.getEmail(), benutzer.getRole());  // Dein Token-Generator für JWT
        return ResponseEntity.ok(new LoginResponse(token));
    }
}

