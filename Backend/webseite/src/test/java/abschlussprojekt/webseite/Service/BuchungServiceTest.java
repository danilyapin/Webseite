package abschlussprojekt.webseite.Service;

import abschlussprojekt.webseite.Models.Buchung;
import abschlussprojekt.webseite.Repository.BuchungRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuchungServiceTest {

    private final BuchungRepository repo = Mockito.mock(BuchungRepository.class);
    private final EmailService emailService = Mockito.mock(EmailService.class);
    private final BuchungService service = new BuchungService(repo, emailService);

    @Test
    void getAllBookedDates() {

        Buchung buchung = new Buchung();
        buchung.setArtikelnummer("1234567890");

        Buchung buchung2 = new Buchung();
        buchung2.setArtikelnummer("987654321");

        when(repo.findAll()).thenReturn(Arrays.asList(buchung, buchung2));

        List<Buchung> result = service.getAllBuchungen();
        assertEquals(2, result.size());

        verify(repo, times(1)).findAll();

    }

    @Test
    void saveBuchung() throws ParseException {

        Buchung buchung = new Buchung();
        buchung.setArtikelnummer("Artikelnummer");
        buchung.setUnternehmen("Unternehmen");
        buchung.setAnsprechpartner("Ansprechpartner");
        buchung.setEmail("Email");
        buchung.setTelefon("Telefon");
        buchung.setLieferStrasse("LieferStrasse");
        buchung.setLieferPLZ("LieferPLZ");
        buchung.setLieferOrt("LieferOrt");
        buchung.setAbholStrasse("AbholStrasse");
        buchung.setAbholPLZ("AbholPLZ");
        buchung.setAbholOrt("AbholOrt");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date begin = dateFormat.parse("05-10-2025");
        Date end = dateFormat.parse("10-10-2025");
        buchung.setMieteBegin(begin);
        buchung.setMieteEnde(end);
        buchung.setZusatzInfo("Test");

        when(repo.save(buchung)).thenReturn(buchung);

        Buchung result = service.saveBuchung(buchung);

        assertEquals(buchung, result);

        verify(repo, times(1)).save(buchung);

    }

    @Test
    void getAllBuchungen() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date begin = dateFormat.parse("05-10-2025");
        Date end = dateFormat.parse("10-10-2025");

        Buchung buchung = new Buchung();
        buchung.setMieteBegin(begin);
        buchung.setMieteEnde(end);

        Buchung buchung2 = new Buchung();
        buchung.setMieteBegin(begin);
        buchung.setMieteEnde(end);

        when(repo.findAll()).thenReturn(Arrays.asList(buchung, buchung2));

        List<Buchung> result = service.getAllBuchungen();

        assertEquals(2, result.size());

        verify(repo, times(1)).findAll();

    }

    @Test
    void getBuchungenByEmail() {

        Buchung buchung = new Buchung();
        buchung.setEmail("test123@gmail.com");

        Buchung buchung2 = new Buchung();
        buchung2.setEmail("test321@gmail.com");

        Buchung buchung3 = new Buchung();
        buchung3.setEmail("test123@gmail.com");

        when(repo.findByEmail("test123@gmail.com")).thenReturn(Arrays.asList(buchung, buchung3));

        List<Buchung> result = service.getBuchungenByEmail("test123@gmail.com");
        assertEquals(2, result.size());
        assertEquals("test123@gmail.com", result.get(0).getEmail());
    }

    @Test
    void getBuchungById() {

        Buchung buchung = new Buchung();
        buchung.setId(1234L);

        when(repo.findById(1234L)).thenReturn(Optional.of(buchung));

        Optional<Buchung> result = service.getBuchungById(1234L);
        assertTrue(result.isPresent());
    }

    @Test
    void deleteBuchung() {

        Buchung buchung = new Buchung();
        buchung.setId(123432L);

        when(repo.findById(123432L)).thenReturn(Optional.of(buchung));
        service.deleteBuchung(123432L);

        verify(repo, times(1)).deleteById(123432L);
    }
}