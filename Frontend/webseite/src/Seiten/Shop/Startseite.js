import React from 'react';
import ArtikelListe from '../Artikel/ArtikelListe';

function Startseite() {
    return (
        <div className="container my-4">
            <h1 className="text-left mb-4">Mietgeräte</h1>

            <div className="fs-5 text-muted" style={{ lineHeight: "1.7" }}>
            <p>
                Möchten Sie Ihre Produkte aufmerksamkeitsstark präsentieren und Zusatzinformationen anbieten?
                Suchen Sie nach Möglichkeiten für den Dialog und die Interaktion mit Ihren Kunden und Besuchern?
                Oder planen Sie eine Veranstaltung und benötigen passende Präsentationsgeräte?
                Wir bieten Ihnen die Möglichkeit, ausgewählte Geräte aus unserem Mietpark zu leihen.
                Dazu gehören Standardgeräte wie Bild- und Videoplayer-Displays, Digital Signage Stelen, Kiosk-Systeme
                sowie Flachbildschirme und Video Walls in verschiedenen Bildschirmdiagonalen – mit oder ohne Transportcase.
                So können Sie Ihre Botschaft wirkungsvoll präsentieren, ohne gleich in eine eigene Hardware investieren zu müssen.
                Nutzen Sie die Flexibilität unseres Mietangebots für Veranstaltungen, Messen oder digitale Präsentationen.
            </p>
            </div>
            <ArtikelListe />
        </div>
    );
}

export default Startseite;
