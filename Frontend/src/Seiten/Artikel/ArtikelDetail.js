import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Spinner } from "react-bootstrap";
import "./QuillResizable.css";

function ArtikelDetail() {
    const { id } = useParams();
    const [artikel, setArtikel] = useState(null);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        setLoading(true);
        axios.get(`/api/artikel/${id}`)
            .then((response) => {
                setArtikel(response.data);
                setLoading(false);
                console.log("Daten vom Server:", response.data);
            })
            .catch((error) => {
                console.error("Es gab ein Problem mit der Anfrage: ", error);
                setLoading(false);
            });
    }, [id]);

    if (loading) {
        return (
            <div className="d-flex flex-column justify-content-center align-items-center" style={{ height: "50vh" }}>
                <Spinner animation="border" role="status" className="mb-3" />
                <p>Lade Artikel, bitte warten...</p>
            </div>
        );
    }

    if (!artikel) {
        return <div>Artikel nicht gefunden.</div>;
    }

    return (
        <div className="container my-5">
            <div className="row">
                <div className="col-md-6 mb-4">
                    <div className="card h-100 shadow-sm image-card">
                        <img
                            src={artikel.bild ? artikel.bild : "/default-image.jpg"}
                            alt={artikel.titel}
                            className="card-img-top"
                            style={{ maxHeight: "400px", objectFit: "contain" }}
                        />
                    </div>
                </div>

                <div className="col-md-6 mb-4">
                    <div className="card h-100 shadow-sm info-card">
                        <div className="card-body">
                            <h5 className="card-title">{artikel.titel}</h5>
                            <p><strong>Art.Nr.:</strong> {artikel.artikelnummer}</p>
                            <p><strong>GTIN/EAN:</strong> {artikel.eanNummer}</p>
                            <p><strong>Preis:</strong> {artikel.preis}€/Tag</p>
                            <p style={{ color: "red" }}><strong>Anlieferung: Am ersten Tag. Abholung: Am letzten Tag</strong></p>

                            <div className="d-flex gap-3">
                                <Link
                                    to={`/mietartikel/${artikel.id}/formular?artikelnummer=${encodeURIComponent(artikel.artikelnummer)}`}
                                    className="btn btn-custom w-50"
                                >
                                    Jetzt mieten
                                </Link>
                                <button
                                    className="btn btn-custom w-50"
                                    onClick={() => {
                                        setLoading(true);
                                        setTimeout(() => {
                                        navigate(-1);
                                    }, 50);
                                    }}
                                >
                                    Zurück
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div className="row mt-3">
                <div className="col-12">
                    <div className="card shadow-sm description-card">
                        <div className="card-body">
                            <h5 className="card-title">Beschreibung</h5>
                            <div className="card-text quill-content" dangerouslySetInnerHTML={{ __html: artikel.beschreibung }} />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ArtikelDetail;