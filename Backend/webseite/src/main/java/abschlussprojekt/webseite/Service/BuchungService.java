package abschlussprojekt.webseite.Service;

import abschlussprojekt.webseite.Models.Buchung;
import abschlussprojekt.webseite.Repository.BuchungRepository;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Service
public class BuchungService {

    private final BuchungRepository buchungRepository;
    private final EmailService emailService;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public BuchungService(BuchungRepository buchungRepository, EmailService emailService) {
        this.buchungRepository = buchungRepository;
        this.emailService = emailService;
    }

    public Buchung saveBuchung(Buchung buchung) {
        Buchung savedBuchung = buchungRepository.save(buchung);

        sendAdminNotification(savedBuchung);
        sendCustomerConfirmation(savedBuchung);

        return savedBuchung;
    }

    public List<Buchung> getAllBuchungen() {
        return buchungRepository.findAll();
    }

    public List<Buchung> getBuchungenByEmail(String email) {
        return buchungRepository.findByEmail(email);
    }

    public Optional<Buchung> getBuchungById(Long id) {
        return buchungRepository.findById(id);
    }

    public void deleteBuchung(Long id) {
        Buchung buchung = buchungRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Buchung mit der ID " + id + " nicht gefunden."));
        buchungRepository.deleteById(id);
    }

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