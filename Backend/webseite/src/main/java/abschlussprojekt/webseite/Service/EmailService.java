package abschlussprojekt.webseite.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    private static final String FROM_ADDRESS = "info.mm.display@gmail.com";

    // Methode, um eine E-Mail an info@plasma-halter.de zu senden (nach der Buchung)
    public void sendBookingEmail(String artikelnummer, String ansprechpartner, String unternehmen, String email, String telefonnummer, String lieferStrasse, String lieferPLZ, String lieferOrt, String abholStrasse, String abholPLZ, String abholOrt, String mieteBegin, String mieteEnde, String zusatzinfo) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(FROM_ADDRESS);
        message.setTo(FROM_ADDRESS);
        message.setSubject("Neue Buchung für " + artikelnummer);
        message.setText(
                        "Neue Buchung eingegangen: " + "\n\n" +

                        "Artikelnummer: " + artikelnummer + "\n" +
                        "Unternehmen: " + unternehmen + "\n" +
                        "E-Mail: " + email + "\n" +
                        "Telefon: " + telefonnummer + "\n\n" +

                        "Mietbeginn: " + mieteBegin + "\n" +
                        "Mietende: " + mieteEnde + "\n\n" +

                        "Lieferadresse:" + "\n" +
                        "Straße: " + lieferStrasse + "\n" +
                        "Postleitzahl: " + lieferPLZ + "\n" +
                        "Ort: " + lieferOrt + "\n\n" +

                        "Abholadresse:" + "\n" +
                        "Straße: " + abholStrasse + "\n" +
                        "Postleitzahl: " + abholPLZ + "\n" +
                        "Ort: " + abholOrt + "\n\n" +

                        "Zusätzliche Infos: " + zusatzinfo);
        javaMailSender.send(message);
    }

    // Methode, um eine Bestätigungs-E-Mail an den Kunden nach der Buchung zu senden
    public void sendBookingConfirmationEmail(String ansprechpartner, String email, String artikelnummer, String unternehmen, String lieferStrasse, String lieferPLZ, String lieferOrt, String abholStrasse, String abholPLZ, String abholOrt, String mieteBegin, String mieteEnde) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(FROM_ADDRESS);
        message.setTo(email);
        message.setSubject("Die Buchungsbestätigung für " + artikelnummer);
        message.setText("Hallo " + ansprechpartner + ",\n\n" +

                        "Ihre Buchung war erfolgreich!\n\n" +

                        "Buchungsdetails:\n\n" +

                        "Artikelnummer: " + artikelnummer + "\n" +
                        "Unternehmen: " + unternehmen + "\n" +
                        "E-Mail: " + email + "\n\n" +

                        "Mietbeginn: " + mieteBegin + "\n" +
                        "Mietende: " + mieteEnde + "\n\n" +

                        "Lieferadresse" + "\n" +
                        "Straße: " + lieferStrasse + "\n" +
                        "Postleitzahl: " + lieferPLZ + "\n" +
                        "Ort: " + lieferOrt + "\n\n" +

                        "Abholadresse" + "\n" +
                        "Straße: " + abholStrasse + "\n" +
                        "Postleitzahl: " + abholPLZ + "\n" +
                        "Ort: " + abholOrt + "\n\n" +

                        "Wir danken Ihnen für Ihre Buchung und stehen bei Fragen jederzeit zur Verfügung.\n\n" +

                        "Mit freundlichen Grüßen\n" +
                        "Multimedia Display");
        javaMailSender.send(message);
    }
}
