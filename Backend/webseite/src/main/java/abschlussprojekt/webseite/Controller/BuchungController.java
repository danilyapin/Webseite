package abschlussprojekt.webseite.Controller;

import abschlussprojekt.webseite.Models.Buchung;
import abschlussprojekt.webseite.Service.BuchungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buchungen")
public class BuchungController {

    @Autowired
    private BuchungService buchungService;

    // Alle gebuchten Daten für einen Artikel abfragen
    @GetMapping("/alle-gebuchten-daten/{artikelnummer}")
    public List<Buchung> getAllBookedDates(@PathVariable String artikelnummer) {
        return buchungService.getAllBookedDates(artikelnummer);
    }

    // Buchung erstellen
    @PostMapping
    public ResponseEntity<Buchung> createBooking(@RequestBody Buchung buchung) {
        try {
            // Speichern der Buchung im Service
            Buchung savedBuchung = buchungService.saveBuchung(buchung);
            return ResponseEntity.ok(savedBuchung);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Fehler, wenn Artikel nicht verfügbar
        }
    }

    // Alle Buchungen für den eingeloggten Benutzer abrufen
    @GetMapping
    public ResponseEntity<List<Buchung>> getBuchungen(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();  // E-Mail des aktuell eingeloggten Benutzers

            // Admin-Überprüfung
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

            if (isAdmin) {
                // Wenn der Benutzer ein Admin ist, alle Buchungen zurückgeben
                List<Buchung> buchungen = buchungService.getAllBuchungen();
                return ResponseEntity.ok(buchungen);
            } else {
                // Wenn der Benutzer kein Admin ist, nur seine eigenen Buchungen zurückgeben
                List<Buchung> buchungen = buchungService.getBuchungenByEmail(email);
                return ResponseEntity.ok(buchungen);
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Benutzer ist nicht authentifiziert
        }
    }

    // Buchung nach ID abrufen
    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<Buchung> getBuchungById(@PathVariable Long id) {
        return buchungService.getBuchungById(id)
                .map(buchung -> new ResponseEntity<>(buchung, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Buchung löschen
    @DeleteMapping("/{id}")
    public void deleteBuchung(@PathVariable Long id) {
        buchungService.deleteBuchung(id);
    }
}
