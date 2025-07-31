import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

function ArtikelListe() {
    // State für Artikel-Daten
    const [artikelListe, setArtikelListe] = useState([]);

    // useEffect hook, um Artikel beim Laden der Seite zu holen
    useEffect(() => {
        // API-Anfrage zum Backend, um Artikel zu holen
        axios.get('http://localhost:8081/api/artikel') // Endpunkt für die Artikel
            .then((response) => {
                // Wenn die Anfrage erfolgreich war, setze die Artikel in den State
                setArtikelListe(response.data);
                console.log("Daten vom Server:", response.data);
            })
            .catch((error) => {
                console.error("Es gab ein Problem mit der Anfrage: ", error);
            });
    }, []); // Leer-Array bedeutet, dass der Effekt nur einmal beim Laden der Komponente ausgeführt wird

    return (
        <div className="container my-5">
            <div className="row justify-content-center">
                {artikelListe.map((item) => (
                    <div key={item.id} className="col-12 col-md-6 col-lg-4 mb-4">
                        <div className="card h-100 shadow-md d-flex flex-column artikel-card">
                            {/* Dynamisches Bild */}
                            <img
                                src={`http://localhost:8081${item.bild_pfad}`}
                                className="card-img-top p-3"
                                alt={item.titel}
                                style={{ height: "250px", objectFit: "contain" }}
                            />
                            <div className="card-body d-flex flex-column justify-content-between text-center">
                                {/* Artikel Titel */}
                                <h5 className="card-title mb-3" style={{ minHeight: "60px" }}>
                                    {item.titel}
                                </h5>
                                {/* Preis */}
                                <p className="card-text fw-bold fs-5 mb-4">{item.preis}€/Tag</p>

                                {/* Link zum detaillierten Artikel */}
                                <Link to={`/mietartikel/${item.id}`} className="btn btn-custom mt-auto w-100">
                                    Mehr erfahren
                                </Link>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default ArtikelListe;
