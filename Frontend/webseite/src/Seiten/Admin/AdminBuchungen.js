import axios from 'axios';
import React, { useEffect, useState } from "react";
import { Button, Modal } from "react-bootstrap";

function AdminDashboard() {
    const [buchungen, setBuchungen] = useState([]);
    const [selectedBuchung, setSelectedBuchung] = useState(null);
    const [showModal, setShowModal] = useState(false);
    const [showDeleteModal, setShowDeleteModal] = useState(false);
    const [deleteId, setDeleteId] = useState(null);

    const formatDate = (dateString) => {
        const date = new Date(dateString);
        const day = String(date.getDate()).padStart(2, '0');
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const year = date.getFullYear();
        return `${day}.${month}.${year}`;
    };

    useEffect(() => {
        const token = localStorage.getItem('token');

        axios.get('/api/buchungen', {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })
            .then((response) => {
                const daten = Array.isArray(response.data)
                    ? response.data
                    : Array.isArray(response.data?.data) ? response.data.data : [];
                setBuchungen(daten);
            })
            .catch((error) => {
                if (error.response && error.response.status === 401) {
                    console.error("Fehler 401: Unauthorized - Der Token ist ungültig oder abgelaufen.");
                } else {
                    console.error("Fehler beim Abrufen der Buchungen", error);
                }
            });
    }, []);

    const handleShowDetails = (buchung) => {
        setSelectedBuchung(buchung);
        setShowModal(true);
    };

    const handleShowDeleteModal = (id) => {
        setDeleteId(id);
        setShowDeleteModal(true);
    };

    const handleDelete = () => {
        if (!deleteId) return;

        axios.delete(`/api/buchungen/${deleteId}`)
            .then(() => {
                setBuchungen(prev => prev.filter(b => b.id !== deleteId));
                setShowDeleteModal(false);
            })
            .catch(error => {
                console.error("Fehler beim Löschen der Buchung", error);
            });
    };

    return (
        <div className="container mt-4">
            <h2>Buchungsauslastung</h2>

            {Array.isArray(buchungen) && buchungen.length === 0 ? (
                <div className="d-flex flex-column justify-content-center align-items-center text-center">
                    <div style={{ fontSize: "1.5rem", color: "#555" }}>
                        Es sind aktuell keine Buchungen verfügbar.
                    </div>
                    <p className="text-muted mt-2">
                        Schauen Sie später noch einmal oder kontaktieren Sie uns für weitere Informationen.
                    </p>
                </div>
            ) : (
                <table className="table">
                    <thead>
                    <tr>
                        <th>Artikelnummer</th>
                        <th>Belegte Zeiträume</th>
                        <th>Aktionen</th>
                    </tr>
                    </thead>
                    <tbody>
                    {buchungen.map((buchung) => (
                        <tr key={buchung.id}>
                            <td>
                                <div>{buchung.artikelnummer}</div>
                                <div className="text-muted" style={{ fontSize: '0.9em' }}>Buchung-ID: {buchung.id}</div>
                            </td>
                            <td>
                                {formatDate(buchung.mieteBegin)} bis {formatDate(buchung.mieteEnde)}
                            </td>
                            <td>
                                <Button className="custom-btn" onClick={() => handleShowDetails(buchung)}>
                                    Details
                                </Button>
                                <Button
                                    variant="danger"
                                    className="ms-2"
                                    onClick={() => handleShowDeleteModal(buchung.id)}
                                >
                                    Löschen
                                </Button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}

    <Modal show={showModal} onHide={() => setShowModal(false)} centered size="lg">
                <Modal.Header closeButton>
                    <Modal.Title>Buchungsdetails</Modal.Title>
                </Modal.Header>
                <Modal.Body style={{ maxHeight: '70vh', overflowY: 'auto' }}>
                    {selectedBuchung ? (
                        <>
                            <div className="">
                                <h5 className="mb-3 mt-3">Kontakt:</h5>
                                <div className="row">
                                    <div className="col-md-6">
                                        <p><strong>Unternehmen:</strong> {selectedBuchung.unternehmen}</p>
                                    </div>
                                    <div className="col-md-6">
                                        <p><strong>Ansprechpartner:</strong> {selectedBuchung.ansprechpartner}</p>
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-6">
                                        <p><strong>Telefon:</strong> {selectedBuchung.telefon}</p>
                                    </div>
                                    <div className="col-md-6">
                                        <p><strong>Email:</strong> {selectedBuchung.email}</p>
                                    </div>
                                </div>
                            </div>
                            <hr />
                            <div className="row">
                                <div className="col-md-6">
                                    <h5 className="mb-4 mt-3">Lieferadresse:</h5>
                                    <p><strong>Straße:</strong> {selectedBuchung.lieferStrasse}</p>
                                    <p><strong>Postleitzahl:</strong> {selectedBuchung.lieferPLZ}</p>
                                    <p><strong>Ort:</strong> {selectedBuchung.lieferOrt}</p>
                                </div>

                                <div className="col-md-6">
                                    <h5 className="mb-4 mt-3">Abholadresse:</h5>
                                    <p><strong>Straße:</strong> {selectedBuchung.abholStrasse}</p>
                                    <p><strong>Postleitzahl:</strong> {selectedBuchung.abholPLZ}</p>
                                    <p><strong>Ort:</strong> {selectedBuchung.abholOrt}</p>
                                </div>
                            </div>

                            <hr />

                            <div className="mb-4">
                                <h5>Zusätzliche Informationen:</h5>
                                <p>{selectedBuchung.zusatzInfo}</p>
                            </div>
                        </>
                    ) : (
                        <p>Lade Details...</p>
                    )}
                </Modal.Body>
                <Modal.Footer>
                    <Button className="custom-btn" onClick={() => setShowModal(false)}>
                        Schließen
                    </Button>
                </Modal.Footer>
            </Modal>
            <Modal show={showDeleteModal} onHide={() => setShowDeleteModal(false)} centered>
                <Modal.Header closeButton>
                    <Modal.Title>Buchung löschen</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <p>Bist du sicher, dass du diese <strong>Buchung</strong> löschen möchtest?</p>
                </Modal.Body>
                <Modal.Footer>
                    <Button className="custom-btn" onClick={() => setShowDeleteModal(false)}>Abbrechen</Button>
                    <Button variant="danger" onClick={handleDelete}>Ja, löschen</Button>
                </Modal.Footer>
            </Modal>
        </div>
    );
}

export default AdminDashboard;
