package abschlussprojekt.webseite.Service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmailServiceTest {

    private final JavaMailSender mailSender = mock(JavaMailSender.class);
    private final EmailService emailService = new EmailService(mailSender);

    @Test
    void sendBookingEmail() {

        emailService.sendBookingEmail(
                "1234", "Max Mustermann", "Firma GmbH", "kunde@test.de",
                "0123456789", "LieferStrasse 1", "12345", "Stadt",
                "AbholStrasse 2", "54321", "Anderstadt",
                "16-10-2025", "20-10-2025", "Keine Infos"
        );

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(captor.capture());

        SimpleMailMessage sentMessage = captor.getValue();
        assertEquals("info.mm.display@gmail.com", sentMessage.getFrom());
        assertEquals("info.mm.display@gmail.com", sentMessage.getTo()[0]);
        assertEquals("Neue Buchung für 1234", sentMessage.getSubject());
        assertTrue(sentMessage.getText().contains("AbholStrasse 2"));
    }

    @Test
    void sendBookingConfirmationEmail() {

        emailService.sendBookingConfirmationEmail(
            "Max Mustermann", "kunde@test.de", "1234",
                "Test GmbH", "LieferStrasse 1", "12345",
                "Stadt", "AbholStrasse 2", "54321", "Anderstadt",
                "16-10-2025", "20-10-2025"
        );

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(captor.capture());

        SimpleMailMessage sentMessage = captor.getValue();
        assertEquals("info.mm.display@gmail.com", sentMessage.getFrom());
        assertEquals("kunde@test.de", sentMessage.getTo()[0]);
        assertEquals("Die Buchungsbestätigung für 1234", sentMessage.getSubject());
        assertTrue(sentMessage.getText().contains("AbholStrasse 2"));
    }
}