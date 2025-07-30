package abschlussprojekt.webseite.Service;

import abschlussprojekt.webseite.Models.Buchung;
import abschlussprojekt.webseite.Repository.BuchungRepository;
import abschlussprojekt.webseite.Repository.ArtikelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Service
public class BuchungService {

    @Autowired
    private BuchungRepository buchungRepository;

    @Autowired
    private ArtikelRepository artikelRepository;

    @Autowired
    private EmailService emailService;

    // Datum-Format für die E-Mails
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    // Gibt alle gebuchten Daten für ein bestimmtes Artikelnummer zurück
    public List<Buchung> getAllBookedDates(String artikelnummer) {
        return buchungRepository.findByArtikelnummer(artikelnummer);
    }

    // Speichert eine Buchung und sendet Benachrichtigungen
    public Buchung saveBuchung(Buchung buchung) {
        // Buchung speichern
        Buchung savedBuchung = buchungRepository.save(buchung);

        // E-Mails senden
        sendAdminNotification(savedBuchung);
        sendCustomerConfirmation(savedBuchung);

        return savedBuchung;
    }

    // Gibt alle Buchungen zurück
    public List<Buchung> getAllBuchungen() {
        return buchungRepository.findAll();
    }

    // Gibt Buchungen zu einer bestimmten E-Mail-Adresse zurück
    public List<Buchung> getBuchungenByEmail(String email) {
        return buchungRepository.findByEmail(email);
    }

    // Gibt eine Buchung anhand ihrer ID zurück
    public Optional<Buchung> getBuchungById(Long id) {
        return buchungRepository.findById(id);
    }

    // Löscht eine Buchung anhand ihrer ID
    public void deleteBuchung(Long id) {
        Buchung buchung = buchungRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Buchung mit der ID " + id + " nicht gefunden."));
        buchungRepository.deleteById(id);
    }

    // --------------------------------------------------
    // Hilfsmethoden für E-Mail-Kommunikation
    // --------------------------------------------------

    private void sendAdminNotification(Buchung buchung) {
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
    }

    private void sendCustomerConfirmation(Buchung buchung) {
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
    }
}
