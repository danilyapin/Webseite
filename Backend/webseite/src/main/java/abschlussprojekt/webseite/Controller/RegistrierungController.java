package abschlussprojekt.webseite.Controller;

import abschlussprojekt.webseite.Models.Benutzer;
import abschlussprojekt.webseite.Repository.BenutzerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class RegistrierungController {

    @Autowired
    private BenutzerRepository benutzerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // RegistrierungController.java
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Benutzer benutzer) {
        if (benutzerRepository.findByEmail(benutzer.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("E-Mail ist bereits registriert!");
        }

        // Überprüfe, ob die Rolle bereits das Präfix enthält, falls nicht, füge es hinzu
        String role = benutzer.getRole();
        if (!role.startsWith("")) {
            if ("admin3@mm-display.de".equals(benutzer.getEmail())) {
                benutzer.setRole("ADMIN");  // Achte darauf, dass "ROLE_" hier hinzugefügt wird
            } else {
                benutzer.setRole("USER");   // Achte darauf, dass "ROLE_" hier hinzugefügt wird
            }
        }

        // Passwort hashen und Benutzer speichern
        benutzer.setPasswort(passwordEncoder.encode(benutzer.getPasswort()));
        benutzerRepository.save(benutzer);

        return ResponseEntity.ok("Benutzer erfolgreich registriert");
    }

}
