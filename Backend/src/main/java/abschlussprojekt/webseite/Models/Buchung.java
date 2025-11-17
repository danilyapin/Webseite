package abschlussprojekt.webseite.Models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Buchung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String artikelnummer;
    private String unternehmen;
    private String ansprechpartner;
    private String email;
    private String telefon;
    private String lieferStrasse;
    private String lieferPLZ;
    private String lieferOrt;
    private String abholStrasse;
    private String abholPLZ;
    private String abholOrt;
    private Date mieteBegin;
    private Date mieteEnde;
    private String zusatzInfo;

}
