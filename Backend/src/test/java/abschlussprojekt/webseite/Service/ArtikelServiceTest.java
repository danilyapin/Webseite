package abschlussprojekt.webseite.Service;

import abschlussprojekt.webseite.Models.Artikel;
import abschlussprojekt.webseite.Repository.ArtikelRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ArtikelServiceTest {

    private final ArtikelRepository repo = Mockito.mock(ArtikelRepository.class);
    private final ArtikelService service = new ArtikelService(repo);

    @Test
    void addArtikel(){

        Artikel savedArtikel = new Artikel();
        savedArtikel.setId(1L);
        savedArtikel.setTitel("Testartikel");

        when(repo.save(any(Artikel.class))).thenReturn(savedArtikel);

        Artikel result = service.addArtikel("Testartikel", "Beschreibung", "EAN123", "ART123", 99.0, null);

        assertEquals(1L, result.getId());
        assertEquals("Testartikel", result.getTitel());
        verify(repo, times(1)).save(any(Artikel.class));
    }

    @Test
    void getAllArtikel() {

        Artikel existing = new Artikel();
        existing.setId(1L);
        existing.setTitel("Alt");

        when(repo.findById(1L)).thenReturn(Optional.of(existing));
        when(repo.save(existing)).thenReturn(existing);

        Artikel result = service.updateArtikel(1L, "Neu", "Beschreibung", "EAN123", "ART123", 199.0, null);

        assertEquals("Neu", result.getTitel());
        assertEquals("Beschreibung", result.getBeschreibung());
        verify(repo, times(1)).findById(1L);
        verify(repo, times(1)).save(existing);
    }

    @Test
    void deleteArtikel() {

        when(repo.existsById(1L)).thenReturn(true);
        doNothing().when(repo).deleteById(1L);

        service.deleteArtikel(1L);

        verify(repo, times(1)).existsById(1L);
        verify(repo, times(1)).deleteById(1L);
    }

    @Test
    void getArtikelById() {

        Artikel artikel = new Artikel();
        artikel.setId(1L);

        when(repo.findById(1L)).thenReturn(Optional.of(artikel));

        Optional<Artikel> result = service.getArtikelById(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(repo, times(1)).findById(1L);
    }

    @Test
    void getArtikelByArtikelnummer() {

        Artikel artikel = new Artikel();
        artikel.setArtikelnummer("ART123");

        when(repo.findByArtikelnummer("ART123")).thenReturn(Optional.of(artikel));

        Optional<Artikel> result = service.getArtikelByArtikelnummer("ART123");
        assertTrue(result.isPresent());
        assertEquals("ART123", result.get().getArtikelnummer());
        verify(repo, times(1)).findByArtikelnummer("ART123");
    }
}