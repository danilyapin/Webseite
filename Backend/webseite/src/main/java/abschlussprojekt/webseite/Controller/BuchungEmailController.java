package abschlussprojekt.webseite.Controller;

import abschlussprojekt.webseite.Models.Buchung;
import abschlussprojekt.webseite.Service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;

@RestController
@RequestMapping("/api")
public class BuchungEmailController {

    private final EmailService emailService;

    public BuchungEmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/buchung")
    public ResponseEntity<?> createBooking(@RequestBody Buchung buchung) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        // E-Mail an Admin senden
        emailService.sendBookingEmail(
                buchung.getArtikelnummer(),
                buchung.getAnsprechpartner(),
                buchung.getUnternehmen(),
                buchung.getEmail(),
                buchung.getTelefon(),
                buchung.getLieferStrasse(),
                buchung.getLieferPLZ(),
                buchung.getLieferOrt(),
                buchung.getAbholStrasse(),
                buchung.getAbholPLZ(),
                buchung.getAbholOrt(),
                dateFormat.format(buchung.getMieteBegin()),
                dateFormat.format(buchung.getMieteEnde()),
                buchung.getZusatzInfo()
        );

        // E-Mail an den Kunden senden
        emailService.sendBookingConfirmationEmail(
                buchung.getAnsprechpartner(),
                buchung.getEmail(),
                buchung.getArtikelnummer(),
                buchung.getUnternehmen(),
                buchung.getLieferStrasse(),
                buchung.getLieferPLZ(),
                buchung.getLieferOrt(),
                buchung.getAbholStrasse(),
                buchung.getAbholPLZ(),
                buchung.getAbholOrt(),
                dateFormat.format(buchung.getMieteBegin()),
                dateFormat.format(buchung.getMieteEnde())
        );

        return ResponseEntity.ok("Buchung erfolgreich!");
    }
}