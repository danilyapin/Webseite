import React from 'react';
import ArtikelListe from '../Artikel/ArtikelListe';

function Startseite() {
    return (
        <div className="container my-4">
            {/* Titel */}
            <h1 className="text-left">Mietgeräte</h1>

            {/* Text darunter */}
            <p>
                Sie möchten Zusatzinformationen zu Ihren Produkten anbieten und so Aufmerksamkeit erwecken?
                Sie suchen Möglichkeiten zum Dialog und zur Interaktion mit Ihren Kunden und Besuchern?
                Sie wollen mit weit sichtbaren Präsentationen Menschen auf Ihre Botschaft aufmerksam machen?
                Oder brauchen Sie eine Video-Wall, Digital Signage Kioskgeräte oder Stelen für eine Veranstaltung?
                Wir bieten Ihnen auch die Möglichkeit, einige unserer Geräte zu mieten.
                Unser Mietpark beinhaltet diverse Standardgeräte, vom einfachen Bild-Videoplayer-Display,
                über Digital Signage Stelen und Kiosk-Systeme bis hin zu Flachbildschirmen und Video Walls
                in den unterschiedlichsten Bildschirmdiagonalen mit und ohne Transportcase.
            </p>
            <ArtikelListe />
        </div>
    );
}

export default Startseite
