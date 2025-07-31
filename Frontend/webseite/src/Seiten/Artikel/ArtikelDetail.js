import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';

function ArtikelDetail() {
    const { id } = useParams(); // Holt die ID aus der URL
    const [artikel, setArtikel] = useState(null); // Artikel-Details in einem State speichern
    const [loading, setLoading] = useState(true); // Lade-Status
    const navigate = useNavigate();

    useEffect(() => {
        // API-Anfrage, um die Artikeldetails zu bekommen
        axios.get(`http://localhost:8081/api/artikel/${id}`) // Der API-Endpunkt für den spezifischen Artikel
            .then((response) => {
                setArtikel(response.data); // Setze die Antwort in den State
                setLoading(false); // Lade-Status auf false setzen
                console.log("Daten vom Server:", response.data);
            })
            .catch((error) => {
                console.error("Es gab ein Problem mit der Anfrage: ", error);
                setLoading(false); // Lade-Status auf false setzen
            });
    }, [id]); // Der Effekt wird ausgeführt, wenn sich die ID ändert

    if (loading) {
        return <div>Artikel wird geladen...</div>; // Lade-Nachricht, falls noch geladen wird
    }

    if (!artikel) {
        return <div>Artikel nicht gefunden</div>; // Fehler, falls der Artikel nicht gefunden wird
    }

    return (
        <div className="container my-5">
            <div className="row">
                {/* Linke Seite: Bild in einer Card */}
                <div className="col-md-6 mb-4">
                    <div className="card h-100 shadow-sm image-card">
                        <img
                            src={`http://localhost:8081${artikel.bild_pfad}`} // Dynamisches Bild laden
                            alt={artikel.titel}
                            className="card-img-top"
                            style={{ maxHeight: "400px", objectFit: "contain" }}
                        />
                    </div>
                </div>

                {/* Rechte Seite: Informationen in einer Card */}
                <div className="col-md-6 mb-4">
                    <div className="card h-100 shadow-sm info-card">
                        <div className="card-body">
                            <h5 className="card-title">{artikel.titel}</h5>
                            <p><strong>Art.Nr.:</strong> {artikel.artikelnummer}</p>
                            <p><strong>GTIN/EAN:</strong> {artikel.eanNummer}</p>
                            <p><strong>Preis:</strong> {artikel.preis}€/Tag</p>
                            <p style={{ color: "red" }}><strong>Anlieferung: Am ersten Tag. Abholung: Am letzten Tag</strong></p>

                            {/* Links nebeneinander */}
                            <div className="d-flex gap-3">
                                <Link
                                    to={`/mietartikel/${artikel.id}/formular?artikelnummer=${encodeURIComponent(artikel.artikelnummer)}`} // Artikelnamen als Query-Parameter hinzufügen
                                    className="btn btn-custom w-50"
                                >
                                    Jetzt mieten
                                </Link>
                                <button
                                    className="btn btn-custom w-50"
                                    onClick={() => navigate(-1)} // Gehe eine Seite zurück
                                >
                                    Zurück
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            {/* Neue Box für die Beschreibung */}
            <div className="row mt-3">
                <div className="col-12">
                    <div className="card shadow-sm description-card">
                        <div className="card-body">
                            <h5 className="card-title">Beschreibung</h5>
                            <p className="card-text">{artikel.beschreibung}</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ArtikelDetail;
