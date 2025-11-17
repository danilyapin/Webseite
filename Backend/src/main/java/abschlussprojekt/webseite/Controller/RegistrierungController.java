package abschlussprojekt.webseite.Controller;

import abschlussprojekt.webseite.Models.Benutzer;
import abschlussprojekt.webseite.Service.RegistrierungService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RegistrierungController {

    private final RegistrierungService registrierungService;

    public RegistrierungController(RegistrierungService registrierungService) {
        this.registrierungService = registrierungService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Benutzer benutzer) {

        try {
            registrierungService.registerUser(benutzer);
            return ResponseEntity.ok("Benutzer erfolgreich registriert!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
