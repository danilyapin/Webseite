import React from 'react';
import { Link, useLocation } from 'react-router-dom';

function BuchungBestätigung() {
    const location = useLocation();
    const { customerInfo, startDate, endDate, artikelnummer, buchungId } = location.state || {};

    if (!customerInfo) {
        return (
            <div className="container mt-5 text-center">
                <h2>Keine Buchungsdaten gefunden.</h2>
                <Link to="/" className="btn btn-primary mt-3">Zurück zur Startseite</Link>
            </div>
        );
    }

    const formatDate = (dateStr) => {
        const date = new Date(dateStr);
        return date.toLocaleDateString("de-DE", {
            day: "2-digit",
            month: "2-digit",
            year: "numeric",
        });
    };

    return (
        <div className="container mt-5">
            <div className="row justify-content-center">
                <div className="col-md-8">
                    <div className="card p-4 border-light">
                        <h1 className="text-center mb-4">Buchungsbestätigung</h1>
                        <div className="card-body">
                            <h4 className="text-muted">Danke für Ihre Buchung, {customerInfo.ansprechpartner}!</h4>
                            <p>Hier sind Ihre Buchungsdetails:</p>
                            <ul className="list-group mb-4">
                                <li className="list-group-item"><strong>Buchungs-ID:</strong> {buchungId}</li> {/* Hier wird die Buchungs-ID angezeigt */}
                                <li className="list-group-item"><strong>Artikelnummer:</strong> {artikelnummer}</li>
                                <li className="list-group-item"><strong>Von:</strong> {formatDate(startDate)}</li>
                                <li className="list-group-item"><strong>Bis:</strong> {formatDate(endDate)}</li>
                                <li className="list-group-item"><strong>Email:</strong> {customerInfo.email}</li>
                                <li className="list-group-item"><strong>Telefon:</strong> {customerInfo.telefon}</li>
                                <li className="list-group-item"><strong>Lieferadresse:</strong> {customerInfo.lieferStrasse}, {customerInfo.lieferPLZ} {customerInfo.lieferOrt}</li>
                                <li className="list-group-item"><strong>Abholadresse:</strong> {customerInfo.abholStrasse}, {customerInfo.abholPLZ} {customerInfo.abholOrt}</li>
                                {customerInfo.zusatzInfo && (
                                    <li className="list-group-item"><strong>Zusätzliche Info:</strong> {customerInfo.zusatzInfo}</li>
                                )}
                            </ul>
                            <p className="text-muted">
                                Eine Bestätigung wurde an Ihre E-Mail-Adresse <strong>{customerInfo.email}</strong> gesendet.
                            </p>
                            <div className="text-center">
                                <Link to="/" className="btn btn-custom">
                                    Zurück zur Startseite
                                </Link>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default BuchungBestätigung;
