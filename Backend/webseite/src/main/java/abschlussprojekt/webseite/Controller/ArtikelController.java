package abschlussprojekt.webseite.Controller;

import abschlussprojekt.webseite.Models.Artikel;
import abschlussprojekt.webseite.Service.ArtikelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/api/artikel")
public class ArtikelController {

    private final ArtikelService artikelService;

    public ArtikelController(ArtikelService artikelService) {
        this.artikelService = artikelService;
    }

    @PostMapping
    public ResponseEntity<?> addArtikel(
            @RequestParam("titel") String titel,
            @RequestParam("beschreibung") String beschreibung,
            @RequestParam(value = "eanNummer", required = false) String eanNummer,
            @RequestParam(value = "artikelnummer", required = false) String artikelnummer,
            @RequestParam("preis") double preis,
            @RequestParam(value = "bild", required = false) MultipartFile bild) {

        Artikel artikel = artikelService.addArtikel(titel, beschreibung, eanNummer, artikelnummer, preis, bild);
        return ResponseEntity.ok(artikelService.convertArtikelToMap(artikel));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateArtikel(
            @PathVariable Long id,
            @RequestParam("titel") String titel,
            @RequestParam("beschreibung") String beschreibung,
            @RequestParam(value = "eanNummer", required = false) String eanNummer,
            @RequestParam(value = "artikelnummer", required = false) String artikelnummer,
            @RequestParam("preis") double preis,
            @RequestParam(value = "bild", required = false) MultipartFile bild) {

        Artikel updated = artikelService.updateArtikel(id, titel, beschreibung, eanNummer, artikelnummer, preis, bild);
        return ResponseEntity.ok(artikelService.convertArtikelToMap(updated));
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllArtikel() {
        return ResponseEntity.ok(artikelService.getAllArtikel());
    }

    @GetMapping("/artikelnummer/{artikelnummer}")
    public ResponseEntity<Map<String, Object>> getArtikelByArtikelnummer(
            @PathVariable String artikelnummer) {

        return artikelService.getArtikelByArtikelnummer(artikelnummer)
                .map(a -> ResponseEntity.ok(artikelService.convertArtikelToMap(a)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("{id}")
    public ResponseEntity<Map<String, Object>> getArtikelById(@PathVariable Long id) {
        return artikelService.getArtikelById(id)
                .map(a -> ResponseEntity.ok(artikelService.convertArtikelToMap(a)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteArtikel(@PathVariable Long id) {
        artikelService.deleteArtikel(id);
        return ResponseEntity.noContent().build();
    }
}