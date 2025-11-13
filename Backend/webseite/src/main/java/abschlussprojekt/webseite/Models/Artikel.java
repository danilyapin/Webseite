package abschlussprojekt.webseite.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Base64;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Artikel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titel;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] bild;

    public String getBildBase64() {
        if (bild != null) {
            return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(bild);
        }
        return null;
    }

    @Lob
    private String beschreibung;

    private String artikelnummer;
    private Double preis;
    private String eanNummer;
}
