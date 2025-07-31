import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { Button, Modal } from 'react-bootstrap';

function UserKonto() {
    const [buchungen, setBuchungen] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [showModal, setShowModal] = useState(false);
    const [selectedBuchung, setSelectedBuchung] = useState(null);
    const name = localStorage.getItem('name') || 'Benutzer';

    const formatDate = (dateString) => {
        const date = new Date(dateString);
        const day = String(date.getDate()).padStart(2, '0');
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const year = date.getFullYear();
        return `${day}.${month}.${year}`;
    };

    // Benutzerbuchungen laden
    useEffect(() => {
        const token = localStorage.getItem('token');

        // Sicherstellen, dass der Benutzer eingeloggt ist
        if (token) {
            axios.get('http://localhost:8081/api/buchungen', {
                headers: {
                    Authorization: `Bearer ${token}`, // Token an die Anfrage anhängen
                },
            })
                .then((response) => {
                    setBuchungen(response.data); // Buchungen speichern
                })
                .catch((err) => {
                    setError('Fehler beim Laden der Buchungen.');
                    console.error('Buchungsfehler:', err);
                })
                .finally(() => {
                    setLoading(false);
                });
        } else {
            setError('Kein Token gefunden.');
            setLoading(false);
        }
    }, []); // Diese Anfrage wird einmal beim Laden des Components ausgeführt

    const handleShowModal = (buchung) => {
        setSelectedBuchung(buchung);
        setShowModal(true); // Modal öffnen
    };

    const handleCloseModal = () => {
        setShowModal(false);
        setSelectedBuchung(null); // Modal schließen und Buchung zurücksetzen
    };

    return (
        <div className="container my-5">
            <div className="card p-4 text-center shadow mb-5">
                <h4 className="mb-3">Herzlich Willkommen, {name}!</h4>
                <p className="lead">Schön, dass Sie wieder da sind.</p>
            </div>

            {!loading && !error && (
                <>
                    <h3 className="mb-3">Ihre Buchungen</h3>
                    {buchungen.length === 0 ? (
                        <p>Sie haben noch keine Buchungen.</p>
                    ) : (
                        <div className="row justify-content-center">
                            {buchungen.map((buchung, index) => (
                                <div key={index} className="col-12 col-md-6 col-lg-4 mb-4">
                                    <div className="card h-100 shadow-sm d-flex flex-column">
                                        {/* Buchungs-ID */}
                                        <div className="card-header">
                                            <h5 className="text-center">Buchungs-ID: {buchung.id}</h5>
                                        </div>

                                        {/* Kurzansicht der Buchungsdetails */}
                                        <div className="card-body">
                                            <div className="mb-3">
                                                <p><strong>Artikelnummer:</strong> {buchung.artikelnummer}</p>
                                                <p><strong>Mietbeginn:</strong> {formatDate(buchung.mieteBegin)}</p>
                                                <p><strong>Mieteende:</strong> {formatDate(buchung.mieteEnde)}</p>
                                            </div>

                                            {/* Button zum Öffnen des Modals */}
                                            <Button
                                                className='btn-custom'
                                                onClick={() => handleShowModal(buchung)}>
                                                Details anzeigen
                                            </Button>
                                        </div>
                                    </div>
                                </div>
                            ))}
                        </div>
                    )}
                </>
            )}
            {loading && <p>Loading...</p>}
            {error && <p className="text-danger">{error}</p>}

            {/* Modal mit den Buchungsdetails */}
            {selectedBuchung && (
                <Modal show={showModal} onHide={handleCloseModal}>
                    <Modal.Header closeButton>
                        <Modal.Title>Buchungsdetails</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        {/* Lieferadresse */}
                        <h5>Lieferadresse:</h5>
                        <p><strong>Straße:</strong> {selectedBuchung.lieferStrasse}</p>
                        <p><strong>Ort:</strong> {selectedBuchung.lieferOrt}</p>
                        <p><strong>PLZ:</strong> {selectedBuchung.lieferPLZ}</p>
                        <hr />

                        {/* Abholadresse */}
                        <h5>Abholadresse:</h5>
                        <p><strong>Straße:</strong> {selectedBuchung.abholStrasse}</p>
                        <p><strong>Ort:</strong> {selectedBuchung.abholOrt}</p>
                        <p><strong>PLZ:</strong> {selectedBuchung.abholPLZ}</p>
                        <hr />

                        {/* Weitere Buchungsinformationen */}
                        <p><strong>Ansprechpartner:</strong> {selectedBuchung.ansprechpartner}</p>
                        <p><strong>Telefon:</strong> {selectedBuchung.telefon}</p>
                        <p><strong>Email:</strong> {selectedBuchung.email}</p>
                        <p><strong>Zusätzliche Infos:</strong> {selectedBuchung.zusatzInfo}</p>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={handleCloseModal}>
                            Schließen
                        </Button>
                    </Modal.Footer>
                </Modal>
            )}
        </div>
    );
}

export default UserKonto;
