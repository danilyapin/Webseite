package abschlussprojekt.webseite.Controller;

import abschlussprojekt.webseite.Models.Buchung;
import abschlussprojekt.webseite.Service.BuchungService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buchungen")
public class BuchungController {

    private final BuchungService buchungService;

    public BuchungController(BuchungService buchungService) {
        this.buchungService = buchungService;
    }

    @PostMapping
    public ResponseEntity<Buchung> createBooking(@RequestBody Buchung buchung) {
        try {
            Buchung savedBuchung = buchungService.saveBuchung(buchung);
            return ResponseEntity.ok(savedBuchung);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Buchung>> getBuchungen(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        List<Buchung> buchungen = isAdmin
                ? buchungService.getAllBuchungen()
                : buchungService.getBuchungenByEmail(email);

        return ResponseEntity.ok(buchungen);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Buchung> getBuchungById(@PathVariable Long id) {
        return buchungService.getBuchungById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBuchung(@PathVariable Long id) {
        try {
            buchungService.deleteBuchung(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
