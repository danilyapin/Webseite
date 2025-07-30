package abschlussprojekt.webseite.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Buchung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "artikel_id")
    private Artikel artikel;

    @Column(name = "artikelnummer")
    private String artikelnummer;

    @Column(name = "unternehmen")
    private String unternehmen;

    @Column(name = "ansprechpartner")
    private String ansprechpartner;

    @Column(name = "email")
    private String email;

    @Column(name = "telefon")
    private String telefon;

    @Column(name = "liefer_strasse")
    private String lieferStrasse;

    @Column(name = "liefer_plz")
    private String lieferPLZ;

    @Column(name = "liefer_ort")
    private String lieferOrt;

    @Column(name = "abhol_strasse")
    private String abholStrasse;

    @Column(name = "abhol_plz")
    private String abholPLZ;

    @Column(name = "abhol_ort")
    private String abholOrt;

    @Column(name = "miete_begin")
    private Date mieteBegin;

    @Column(name = "miete_ende")
    private Date mieteEnde;

    @Column(name = "zusatz_info")
    private String zusatzInfo;



public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArtikelnummer() {
        return artikelnummer;
    }

    public void setArtikelnummer(String artikelnummer) {
        this.artikelnummer = artikelnummer;
    }

    public String getUnternehmen() {
        return unternehmen;
    }

    public void setUnternehmen(String unternehmen) {
        this.unternehmen = unternehmen;
    }

    public String getAnsprechpartner() {
        return ansprechpartner;
    }

    public void setAnsprechpartner(String ansprechpartner) {
        this.ansprechpartner = ansprechpartner;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }


    public String getLieferStrasse() {
        return lieferStrasse;
    }

    public void setLieferStrasse(String lieferStrasse) {
        this.lieferStrasse = lieferStrasse;
    }

    public String getLieferPLZ() {
        return lieferPLZ;
    }

    public void setLieferPLZ(String lieferPLZ) {
        this.lieferPLZ = lieferPLZ;
    }

    public String getLieferOrt() {
        return lieferOrt;
    }

    public void setLieferOrt(String lieferOrt) {
        this.lieferOrt = lieferOrt;
    }

    public String getAbholStrasse() {
        return abholStrasse;
    }

    public void setAbholStrasse(String abholStrasse) {
        this.abholStrasse = abholStrasse;
    }

    public String getAbholPLZ() {
        return abholPLZ;
    }

    public void setAbholPLZ(String abholPLZ) {
        this.abholPLZ = abholPLZ;
    }

    public String getAbholOrt() {
        return abholOrt;
    }

    public void setAbholOrt(String abholOrt) {
        this.abholOrt = abholOrt;
    }


    public Date getMieteBegin() {
        return mieteBegin;
    }

    public void setMieteBegin(Date mieteBegin) {
        this.mieteBegin = mieteBegin;
    }

    public Date getMieteEnde() {
        return mieteEnde;
    }

    public void setMieteEnde(Date mieteEnde) {
        this.mieteEnde = mieteEnde;
    }

    public String getZusatzInfo() {
        return zusatzInfo;
    }

    public void setZusatzInfo(String zusatzInfo) {
        this.zusatzInfo = zusatzInfo;
    }

}
