package abschlussprojekt.webseite.Controller;

import abschlussprojekt.webseite.Models.Buchung;
import abschlussprojekt.webseite.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.SimpleDateFormat;

public class BuchungEmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody Buchung buchung) {
        System.out.println("E-Mail-Adresse in Backend: " + buchung.getEmail());

        // Format der Daten (optional - für die Darstellung des Datums)
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
