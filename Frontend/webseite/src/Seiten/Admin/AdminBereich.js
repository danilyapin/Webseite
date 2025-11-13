import axios from "axios";
import React, { useEffect, useState } from "react";
import { Button, ListGroup, Modal, Spinner } from "react-bootstrap";
import { Link } from "react-router-dom";

function AdminBereich() {
    const [artikelListe, setArtikelListe] = useState([]);
    const [showModal, setShowModal] = useState(false);
    const [selectedArtikel, setSelectedArtikel] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        setLoading(true);
        axios.get("/api/artikel")
            .then(response => {
                setArtikelListe(response.data);
                setLoading(false);
            })
            .catch(error => {
                console.error("Fehler beim Laden der Artikel:", error);
            });
    }, []);

    const handleShowModal = (artikel) => {
        setSelectedArtikel(artikel);
        setShowModal(true);
    };

    const handleCloseModal = () => {
        setShowModal(false);
        setSelectedArtikel(null);
    };

    const handleDelete = () => {
        if (selectedArtikel) {
            axios.delete(`api/artikel/${selectedArtikel.id}`)
                .then(() => {
                    setArtikelListe(artikelListe.filter(a => a.id !== selectedArtikel.id));
                    handleCloseModal();
                })
                .catch(error => {
                    console.error("Fehler beim Löschen des Artikels:", error);
                });
        }
    };

    if (loading) {
        return (
            <div className="d-flex flex-column justify-content-center align-items-center" style={{ height: "50vh" }}>
                <Spinner animation="border" role="status" className="mb-3" />
                <p>Lade Artikel, bitte warten...</p>
            </div>
        );
    }

    return (
        <div className="container mt-4">
            <div className="d-flex justify-content-between align-items-center">
                <h2>Admin Bereich</h2>
                <Link to="/admin/neu_artikel_einstellen/">
                    <Button variant="success">Neuen Artikel einstellen</Button>
                </Link>
            </div>

            <ListGroup className="my-4 list-group-item-shadow">
                {artikelListe.length === 0 ? (
                        <div className="text-center my-5">
                            <div style={{ fontSize: "1.5rem", color: "#555" }}>
                                Es sind aktuell keine Artikel eingefügt.
                            </div>
                            <p className="text-muted mt-2">
                                Stellen Sie einen Artikel ein.
                            </p>
                        </div>
                    ) : (artikelListe.map((artikel) => (
                    <ListGroup.Item key={artikel.id} className="d-flex align-items-center justify-content-between">
                        <div className="d-flex align-items-center w-100">
                            <img
                                src={artikel.bild ? artikel.bild : "/default-image.jpg"}
                                className="me-3"
                                alt={artikel.titel}
                                style={{ width: "80px", height: "80px", objectFit: "cover", borderRadius: "8px" }}
                            />

                            <div className="flex-grow-1">
                                {artikel.titel || "Keine Beschreibung verfügbar"}
                            </div>

                            <div className="d-flex">
                                <Link to={`/admin/artikel/${artikel.id}/bearbeiten`}>
                                    <Button variant="secondary" className="custom-btn me-2">Bearbeiten</Button>
                                </Link>
                                <Button variant="danger" onClick={() => handleShowModal(artikel)}>Löschen</Button>
                            </div>
                        </div>
                    </ListGroup.Item>
                )))}
            </ListGroup>

            <Modal show={showModal} onHide={handleCloseModal} centered>
                <Modal.Header closeButton>
                    <Modal.Title>Artikel löschen</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    Bist du sicher, dass du den <strong>Artikel</strong> löschen möchtest?
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" className="custom-btn" onClick={handleCloseModal}>Abbrechen</Button>
                    <Button variant="danger" onClick={handleDelete}>Ja, löschen</Button>
                </Modal.Footer>
            </Modal>
        </div>
    );
}

export default AdminBereich;