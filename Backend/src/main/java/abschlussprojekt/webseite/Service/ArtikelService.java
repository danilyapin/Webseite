package abschlussprojekt.webseite.Service;

import abschlussprojekt.webseite.Models.Artikel;
import abschlussprojekt.webseite.Repository.ArtikelRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArtikelService {

    private final ArtikelRepository artikelRepository;

    public ArtikelService(ArtikelRepository artikelRepository) {
        this.artikelRepository = artikelRepository;
    }

    public Artikel addArtikel(String titel, String beschreibung, String eanNummer,
                              String artikelnummer, double preis, MultipartFile bild) {
        try {
            Artikel artikel = new Artikel();
            artikel.setTitel(titel);
            artikel.setBeschreibung(beschreibung);
            artikel.setEanNummer(eanNummer);
            artikel.setArtikelnummer(artikelnummer);
            artikel.setPreis(preis);

            if (bild != null && !bild.isEmpty()) {
                artikel.setBild(bild.getBytes());
            }

            return artikelRepository.save(artikel);

        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Lesen des Bildes", e);
        }
    }

    public List<Map<String, Object>> getAllArtikel() {
        return artikelRepository.findAll().stream()
                .map(this::convertArtikelToMap)
                .collect(Collectors.toList());
    }

    public Map<String, Object> convertArtikelToMap(Artikel artikel) {
        Map<String, Object> response = new HashMap<>();
        response.put("id", artikel.getId());
        response.put("titel", artikel.getTitel());
        response.put("beschreibung", artikel.getBeschreibung());
        response.put("preis", artikel.getPreis());
        response.put("artikelnummer", artikel.getArtikelnummer());
        response.put("eanNummer", artikel.getEanNummer());
        response.put("bild",
                artikel.getBild() != null && artikel.getBild().length > 0
                        ? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(artikel.getBild())
                        : null
        );
        return response;
    }

    public Artikel updateArtikel(Long id, String titel, String beschreibung, String eanNummer,
                                 String artikelnummer, double preis, MultipartFile bild) {
        try {
            Artikel artikel = artikelRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Artikel nicht gefunden"));

            artikel.setTitel(titel);
            artikel.setBeschreibung(beschreibung);
            artikel.setEanNummer(eanNummer);
            artikel.setArtikelnummer(artikelnummer);
            artikel.setPreis(preis);

            if (bild != null && !bild.isEmpty()) {
                artikel.setBild(bild.getBytes());
            }

            return artikelRepository.save(artikel);

        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Aktualisieren des Bildes", e);
        }
    }

    public void deleteArtikel(Long id) {
        if (!artikelRepository.existsById(id)) {
            throw new IllegalArgumentException("Artikel mit ID " + id + " existiert nicht");
        }
        artikelRepository.deleteById(id);
    }

    public Optional<Artikel> getArtikelById(Long id) {
        return artikelRepository.findById(id);
    }

    public Optional<Artikel> getArtikelByArtikelnummer(String artikelnummer) {
        return artikelRepository.findByArtikelnummer(artikelnummer);
    }
}