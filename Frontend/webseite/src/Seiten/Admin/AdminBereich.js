import axios from "axios";
import React, { useEffect, useState } from "react";
import { Button, ListGroup, Modal } from "react-bootstrap";
import { Link } from "react-router-dom";

function AdminBereich() {
    const [artikelListe, setArtikelListe] = useState([]);
    const [showModal, setShowModal] = useState(false);
    const [selectedArtikel, setSelectedArtikel] = useState(null);

    // Artikel laden
    useEffect(() => {
        fetchArtikel();
    }, []);

    const fetchArtikel = () => {
        axios.get("http://localhost:8081/api/artikel")
            .then(response => {
                setArtikelListe(response.data);
                console.log(response.data)
            })
            .catch(error => {
                console.error("Fehler beim Laden der Artikel:", error);
            });
    };

    // Modal öffnen mit Artikel-Infos
    const handleShowModal = (artikel) => {
        setSelectedArtikel(artikel);
        setShowModal(true);
    };

    // Modal schließen
    const handleCloseModal = () => {
        setShowModal(false);
        setSelectedArtikel(null);
    };

    // Artikel löschen
    const handleDelete = () => {
        if (selectedArtikel) {
            axios.delete(`http://localhost:8081/api/artikel/${selectedArtikel.id}`)
                .then(() => {
                    setArtikelListe(artikelListe.filter(a => a.id !== selectedArtikel.id));
                    handleCloseModal();
                })
                .catch(error => {
                    console.error("Fehler beim Löschen des Artikels:", error);
                });
        }
    };

    return (
        <div className="container mt-4">
            <div className="d-flex justify-content-between align-items-center">
                <h2>Admin Bereich</h2>
                <Link to="/admin/neu_artikel_einstellen/">
                    <Button variant="success">Neuen Artikel einstellen</Button>
                </Link>
            </div>

            <ListGroup className="my-4 list-group-item-shadow">
                {artikelListe.map((artikel) => (
                    <ListGroup.Item key={artikel.id} className="d-flex align-items-center justify-content-between">
                        <div className="d-flex align-items-center w-100">
                            {/* Linkes Bild */}
                            <img
                                src={`http://localhost:8081${artikel.bild_pfad}`}
                                className="me-3"
                                alt={artikel.titel}
                                style={{ width: "80px", height: "80px", objectFit: "cover", borderRadius: "8px" }}
                            />

                            {/* Mittlerer Text */}
                            <div className="flex-grow-1">
                                {artikel.titel || "Keine Beschreibung verfügbar"}
                            </div>

                            {/* Rechte Buttons */}
                            <div className="d-flex">
                                <Link to={`/admin/artikel/${artikel.id}/bearbeiten`}>
                                    <Button className="custom-btn me-2">Bearbeiten</Button>
                                </Link>
                                <Button variant="danger" onClick={() => handleShowModal(artikel)}>Löschen</Button>
                            </div>
                        </div>
                    </ListGroup.Item>
                ))}
            </ListGroup>

            {/* Modal zur Bestätigung der Löschung */}
            <Modal show={showModal} onHide={handleCloseModal} centered>
                <Modal.Header closeButton>
                    <Modal.Title>Artikel löschen</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    Bist du sicher, dass du den Artikel <strong>{selectedArtikel?.titel}</strong> löschen möchtest?
                    Diese Aktion kann nicht rückgängig gemacht werden.
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleCloseModal}>Abbrechen</Button>
                    <Button variant="danger" onClick={handleDelete}>Ja, löschen</Button>
                </Modal.Footer>
            </Modal>
        </div>
    );
}

export default AdminBereich;
