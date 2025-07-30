package abschlussprojekt.webseite.Controller;

import abschlussprojekt.webseite.Models.Artikel;
import abschlussprojekt.webseite.Service.ArtikelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/artikel")
@CrossOrigin(origins = "*")
public class ArtikelController {

    private final String EXTERNAL_UPLOAD_DIR = "uploads/images/";

    @Autowired
    private ArtikelService artikelService;

    // POST: Artikel hinzufügen
    @PostMapping
    public ResponseEntity<?> addArtikel(
            @RequestParam("titel") String titel,
            @RequestParam("beschreibung") String beschreibung,
            @RequestParam(value = "eanNummer", required = false) String eanNummer,
            @RequestParam(value = "artikelnummer", required = false) String artikelnummer,
            @RequestParam("preis") double preis,
            @RequestParam(value = "bild", required = false) MultipartFile bild) {

        try {
            String bildPfad = null;

            // Zielordner überprüfen und erstellen, falls nicht vorhanden
            Path bilderVerzeichnis = Paths.get(EXTERNAL_UPLOAD_DIR);
            if (!Files.exists(bilderVerzeichnis)) {
                Files.createDirectories(bilderVerzeichnis);  // Ordner erstellen
            }

            if (bild != null && !bild.isEmpty()) {
                // Bild speichern
                String dateiname = System.currentTimeMillis() + "_" + bild.getOriginalFilename();
                Path bildSpeicherort = Paths.get(EXTERNAL_UPLOAD_DIR, dateiname);
                Files.write(bildSpeicherort, bild.getBytes());  // Bild speichern

                bildPfad = "/uploads/images/" + dateiname;  // Bildpfad setzen
            }

            Artikel artikel = new Artikel();
            artikel.setTitel(titel);
            artikel.setBeschreibung(beschreibung);
            artikel.setEanNummer(eanNummer);
            artikel.setArtikelnummer(artikelnummer);
            artikel.setPreis(preis);
            artikel.setBildPfad(bildPfad);

            Artikel savedArtikel = artikelService.addArtikel(artikel);
            return new ResponseEntity<>(savedArtikel, HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Fehler beim Speichern des Artikels: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/artikelnummer/{artikelnummer}")
    public ResponseEntity<Artikel> getArtikelByArtikelnummer(@PathVariable String artikelnummer) {
        Optional<Artikel> artikel = artikelService.getArtikelByArtikelnummer(artikelnummer);
        return artikel.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // GET: Alle Artikel abrufen
    @GetMapping
    public ResponseEntity<List<Artikel>> getAllArtikel() {
        List<Artikel> artikel = artikelService.getAllArtikel();
        return new ResponseEntity<>(artikel, HttpStatus.OK);
    }

    // GET: Artikel nach ID abrufen
    @GetMapping("{id}")
    public ResponseEntity<Artikel> getArtikelById(@PathVariable Long id) {
        Optional<Artikel> artikel = artikelService.getArtikelById(id);
        return artikel.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // PUT: Artikel aktualisieren
    @PutMapping("{id}")
    public ResponseEntity<?> updateArtikel(
            @PathVariable Long id,
            @RequestParam("titel") String titel,
            @RequestParam("beschreibung") String beschreibung,
            @RequestParam(value = "eanNummer", required = false) String eanNummer,
            @RequestParam(value = "artikelnummer", required = false) String artikelnummer,
            @RequestParam("preis") double preis,
            @RequestParam(value = "bild", required = false) MultipartFile bild) {

        Optional<Artikel> existingArtikel = artikelService.getArtikelById(id);

        if (existingArtikel.isPresent()) {
            Artikel artikel = existingArtikel.get();
            artikel.setTitel(titel);
            artikel.setBeschreibung(beschreibung);
            artikel.setEanNummer(eanNummer);
            artikel.setArtikelnummer(artikelnummer);
            artikel.setPreis(preis);

            String bildPfad = artikel.getBildPfad();
            if (bild != null && !bild.isEmpty()) {
                try {
                    String dateiname = System.currentTimeMillis() + "_" + bild.getOriginalFilename();
                    Path bildSpeicherort = Paths.get(EXTERNAL_UPLOAD_DIR, dateiname);
                    Files.createDirectories(bildSpeicherort.getParent());
                    Files.write(bildSpeicherort, bild.getBytes());
                    bildPfad = "/uploads/images/" + dateiname;
                } catch (Exception e) {
                    e.printStackTrace();
                    return new ResponseEntity<>("Fehler beim Speichern des Bildes: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

            artikel.setBildPfad(bildPfad);

            Artikel savedArtikel = artikelService.addArtikel(artikel);
            return new ResponseEntity<>(savedArtikel, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Artikel nicht gefunden", HttpStatus.NOT_FOUND);
        }
    }

    // DELETE: Artikel löschen
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtikel(@PathVariable Long id) {
        artikelService.deleteArtikel(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // GET: Bild herunterladen
    @GetMapping("/uploads/images/{filename:.+}")
    public ResponseEntity<FileSystemResource> getImage(@PathVariable String filename) {
        File imageFile = new File(EXTERNAL_UPLOAD_DIR + filename);
        if (imageFile.exists()) {
            // Bestimme den MIME-Typ des Bildes
            String contentType = "image/jpeg"; // Standardmäßig als JPEG, je nach Dateityp anpassen
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(new FileSystemResource(imageFile));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
