package abschlussprojekt.webseite.Controller;

import abschlussprojekt.webseite.Config.SecurityConfig;
import abschlussprojekt.webseite.Models.Buchung;
import abschlussprojekt.webseite.Service.EmailService;
import abschlussprojekt.webseite.Util.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BuchungEmailController.class)
@Import(SecurityConfig.class)
@AutoConfigureMockMvc
class BuchungEmailControllerTest {

    @MockitoBean
    private EmailService emailService;

    @MockitoBean
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "test@123.de")
    void createBooking() throws Exception {
        Buchung buchung = new Buchung();
        buchung.setArtikelnummer("1234");
        buchung.setAnsprechpartner("Max Mustermann");
        buchung.setUnternehmen("TestFirma");
        buchung.setEmail("max@test.de");
        buchung.setTelefon("123456");
        buchung.setLieferStrasse("Lieferstraße 1");
        buchung.setLieferPLZ("12345");
        buchung.setLieferOrt("Berlin");
        buchung.setAbholStrasse("Abholstraße 1");
        buchung.setAbholPLZ("54321");
        buchung.setAbholOrt("Berlin");
        buchung.setMieteBegin(new Date());
        buchung.setMieteEnde(new Date());
        buchung.setZusatzInfo("Keine");

        doNothing().when(emailService).sendBookingEmail(
                any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any()
        );
        doNothing().when(emailService).sendBookingConfirmationEmail(
                any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any()
        );

        mockMvc.perform(post("/api/buchung")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(buchung)))
                .andExpect(status().isOk())
                .andExpect(content().string("Buchung erfolgreich!"));

        verify(emailService, times(1)).sendBookingEmail(
                any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any()
        );
        verify(emailService, times(1)).sendBookingConfirmationEmail(
                any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any()
        );
    }
}