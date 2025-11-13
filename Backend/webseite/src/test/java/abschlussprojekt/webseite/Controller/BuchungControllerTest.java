package abschlussprojekt.webseite.Controller;

import abschlussprojekt.webseite.Config.SecurityConfig;
import abschlussprojekt.webseite.Models.Buchung;
import abschlussprojekt.webseite.Service.BuchungService;
import abschlussprojekt.webseite.Util.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(BuchungController.class)
@Import(SecurityConfig.class)
@AutoConfigureMockMvc
class BuchungControllerTest {

    @MockitoBean
    private BuchungService buchungService;

    @MockitoBean
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "test@123.de")
    void createBooking() throws Exception {

        Buchung buchung = new Buchung();
        buchung.setArtikelnummer("1234");
        buchung.setUnternehmen("Test");
        buchung.setEmail("123456789");
        buchung.setTelefon("1A2B3C");
        buchung.setLieferStrasse("199");
        buchung.setLieferPLZ("199");
        buchung.setLieferOrt("199");
        buchung.setAbholStrasse("199");
        buchung.setAbholPLZ("199");
        buchung.setAbholOrt("199");
        buchung.setZusatzInfo("199");

        when(buchungService.saveBuchung(any(Buchung.class))).thenReturn(buchung);

        mockMvc.perform(post("/api/buchungen")
                        .contentType("application/json")
                        .content("""
                                {
                                "artikelnummer": "1234",
                                 "unternehmen": "Test",
                                 "email": "123456789",
                                 "telefon": "1A2B3C",
                                 "lieferStrasse": "199",
                                 "lieferPLZ": "199",
                                 "lieferOrt": "199",
                                 "abholStrasse": "199",
                                 "abholPLZ": "199",
                                 "abholOrt": "199",
                                 "zusatzInfo": "199"
                                }
                                """))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.artikelnummer").value("1234"))
                .andExpect(jsonPath("$.unternehmen").value("Test"));

        verify(buchungService, times(1)).saveBuchung(buchung);
    }

    @Test
    void getBuchungen_asAdmin() throws Exception {

        List<Buchung> mockBuchungen = List.of(
                new Buchung(),
                new Buchung()
        );

        when(buchungService.getAllBuchungen()).thenReturn(mockBuchungen);

        mockMvc.perform(get("/api/buchungen").with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    void getBuchungen_asUser() throws Exception {

        List<Buchung> mockBuchungen = List.of(
                new Buchung(),
                new Buchung()
        );

        when(buchungService.getBuchungenByEmail("email@test.de")).thenReturn(mockBuchungen);

        mockMvc.perform(get("/api/buchungen").with(user("email@test.de").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    void getBuchungById() throws Exception {

        Buchung mockBuchung = new Buchung();
        mockBuchung.setId(5L);

        when(buchungService.getBuchungById(mockBuchung.getId())).thenReturn(Optional.of(mockBuchung));

        mockMvc.perform(get("/api/buchungen/5").with(user("email@test.de").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(buchungService, times(1)).getBuchungById(mockBuchung.getId());
    }

    @Test
    @WithMockUser(username = "admin@123.de")
    void deleteBuchung() throws Exception {

        Buchung mockBuchung = new Buchung();
        mockBuchung.setId(5L);

        when(buchungService.getBuchungById(mockBuchung.getId())).thenReturn(Optional.of(mockBuchung));
        doNothing().when(buchungService).deleteBuchung(mockBuchung.getId());

        mockMvc.perform(delete("/api/buchungen/5"))
                .andExpect(status().isNoContent());

        verify(buchungService, times(1)).deleteBuchung(mockBuchung.getId());
    }
}