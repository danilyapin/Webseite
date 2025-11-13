import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { Spinner, Button } from 'react-bootstrap';

function ArtikelListe() {
    const [artikelListe, setArtikelListe] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        setLoading(true);
        axios.get('/api/artikel')
            .then((response) => {
                setArtikelListe(response.data);
                setLoading(false);
                console.log("Daten vom Server:", response.data);
            })
            .catch((error) => {
                console.error("Es gab ein Problem mit der Anfrage: ", error);
                setLoading(false);
            });
    }, []);

    if (loading) {
        return (
            <div className="d-flex flex-column justify-content-center align-items-center" style={{ height: "50vh" }}>
                <Spinner animation="border" role="status" className="mb-3" />
                <p>Lade Artikel, bitte warten...</p>
            </div>
        );
    }

    return (
        <div className="container my-5">
            <div className="row justify-content-center">
                {artikelListe.length === 0 ? (
                        <div className="text-center my-5">
                            <div style={{ fontSize: "1.5rem", color: "#555" }}>
                                Es sind aktuell keine Artikel verfügbar.
                            </div>
                            <p className="text-muted mt-2">
                                Schauen Sie später noch einmal oder kontaktieren Sie uns für weitere Informationen.
                            </p>
                        </div>
                ) :
                    (artikelListe.map((item) => (
                    <div key={item.id} className="col-12 col-md-6 col-lg-4 mb-4">
                        <div className="card h-100 shadow-sm artikel-card">
                            <div className="card-img-top-wrapper">
                                <img
                                    src={item.bild ? item.bild : "/default-image.jpg"}
                                    className="card-img-top p-3"
                                    alt={item.titel}
                                    style={{ height: "250px", objectFit: "contain" }}
                                />
                            </div>
                            <div className="card-body text-center d-flex flex-column justify-content-between">
                                <h5 className="card-title">{item.titel}</h5>
                                <p className="card-text fw-bold fs-5">{item.preis}€/Tag</p>
                                <Link to={`/mietartikel/${item.id}`}>
                                    <Button className="custom-btn">Mehr erfahren</Button>
                                </Link>
                            </div>
                        </div>
                    </div>
                )))}
            </div>
        </div>
    );
}

export default ArtikelListe;
