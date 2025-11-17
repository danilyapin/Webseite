package abschlussprojekt.webseite.Controller;

import abschlussprojekt.webseite.Config.SecurityConfig;
import abschlussprojekt.webseite.Models.Artikel;
import abschlussprojekt.webseite.Service.ArtikelService;

import abschlussprojekt.webseite.Util.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ArtikelController.class)
@Import(SecurityConfig.class)
@AutoConfigureMockMvc
class ArtikelControllerTest {

    @MockitoBean
    private ArtikelService artikelService;

    @MockitoBean
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void addArtikel() throws Exception {

        Artikel artikel = new Artikel();
        artikel.setId(1L);
        artikel.setTitel("Testartikel");

        when(artikelService.addArtikel(anyString(), anyString(), anyString(), anyString(), anyDouble(), any()))
                .thenReturn(artikel);
        when(artikelService.convertArtikelToMap(artikel))
                .thenReturn(Map.of("id", 1L, "titel", "Testartikel"));

        mockMvc.perform(post("/api/artikel")
                        .param("titel", "Testartikel")
                        .param("beschreibung", "Beschreibung")
                        .param("eanNummer", "EAN123")
                        .param("artikelnummer", "ART123")
                        .param("preis", "99.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titel").value("Testartikel"));

        verify(artikelService, times(1))
                .addArtikel(anyString(), anyString(), anyString(), anyString(), anyDouble(), any());
        verify(artikelService, times(1)).convertArtikelToMap(artikel);
    }

    @Test
    void getArtikelByArtikelnummer() throws Exception {

        Artikel mockArtikel = new Artikel();
        mockArtikel.setTitel("1234");
        mockArtikel.setBeschreibung("beschreibung");
        mockArtikel.setEanNummer("123456789");
        mockArtikel.setArtikelnummer("1A2B3C");
        mockArtikel.setPreis(199.0);

        when(artikelService.getArtikelByArtikelnummer("1A2B3C")).thenReturn(Optional.of(mockArtikel));

        when(artikelService.convertArtikelToMap(mockArtikel))
                .thenReturn(Map.of(
                        "titel", mockArtikel.getTitel(),
                        "beschreibung", mockArtikel.getBeschreibung(),
                        "eanNummer", mockArtikel.getEanNummer(),
                        "artikelnummer", mockArtikel.getArtikelnummer(),
                        "preis", mockArtikel.getPreis()
                ));

        mockMvc.perform(get("/api/artikel/artikelnummer/1A2B3C"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.titel").value("1234"))
                .andExpect(jsonPath("$.artikelnummer").value("1A2B3C"))
                .andExpect(jsonPath("$.preis").value(199.0));

        verify(artikelService, times(1)).getArtikelByArtikelnummer("1A2B3C");
    }

    @Test
    void getAllArtikel() throws Exception {

        List<Map<String, Object>> artikelListe = List.of(
                Map.of("id", 1L, "titel", "Artikel1"),
                Map.of("id", 2L, "titel", "Artikel2")
        );

        when(artikelService.getAllArtikel()).thenReturn(artikelListe);

        mockMvc.perform(get("/api/artikel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].titel").value("Artikel1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].titel").value("Artikel2"));

        verify(artikelService, times(1)).getAllArtikel();
    }

    @Test
    void getArtikelById() throws Exception {

        Artikel artikel = new Artikel();
        artikel.setId(1L);
        artikel.setTitel("Artikel1");

        when(artikelService.getArtikelById(1L)).thenReturn(Optional.of(artikel));
        when(artikelService.convertArtikelToMap(artikel))
                .thenReturn(Map.of("id", 1L, "titel", "Artikel1"));

        mockMvc.perform(get("/api/artikel/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titel").value("Artikel1"));

        verify(artikelService, times(1)).getArtikelById(1L);
        verify(artikelService, times(1)).convertArtikelToMap(artikel);
    }

    @Test
    void updateArtikel() throws Exception {

        Artikel artikel = new Artikel();
        artikel.setId(1L);
        artikel.setTitel("Updated");

        when(artikelService.updateArtikel(anyLong(), anyString(), anyString(), anyString(), anyString(), anyDouble(), any()))
                .thenReturn(artikel);
        when(artikelService.convertArtikelToMap(artikel))
                .thenReturn(Map.of("id", 1L, "titel", "Updated"));

        mockMvc.perform(put("/api/artikel/1")
                        .param("titel", "Updated")
                        .param("beschreibung", "Beschreibung")
                        .param("eanNummer", "EAN123")
                        .param("artikelnummer", "ART123")
                        .param("preis", "199.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titel").value("Updated"));

        verify(artikelService, times(1))
                .updateArtikel(anyLong(), anyString(), anyString(), anyString(), anyString(), anyDouble(), any());
        verify(artikelService, times(1)).convertArtikelToMap(artikel);
    }

    @Test
    void deleteArtikel() throws Exception {

        doNothing().when(artikelService).deleteArtikel(1L);

        mockMvc.perform(delete("/api/artikel/1"))
                .andExpect(status().isNoContent());

        verify(artikelService, times(1)).deleteArtikel(1L);
    }
}