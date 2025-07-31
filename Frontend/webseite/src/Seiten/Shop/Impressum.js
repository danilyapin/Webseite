import React from "react";

function Impressum() {
    return (
        <div className="container mt-5 mb-5">
            <h2 className="mb-4">Impressum</h2>

            <p><strong>Gesetzliche Anbieterkennung:</strong></p>
            <p>
                <strong>Multimedia Display GmbH</strong><br />
                Diese vertreten durch den Geschäftsführer Helmut Schulz
            </p>

            <p><strong>Büroadresse:</strong><br />
                Zöllners Garten 1<br />
                30900 Wedemark
            </p>

            <p>
                <strong>Telefon:</strong> (+49) 05130 9758510<br />
                <strong>Fax:</strong> (+49) 05130 9758590<br />
                <strong>E-Mail:</strong> <a href="mailto:info@mm-display.de">info@mm-display.de</a>
            </p>

            <p>
                <strong>USt-IdNr.:</strong> DE274191042<br />
                <strong>Handelsregister:</strong> Eingetragen im Handelsregister des Amtsgerichtes Hannover<br />
                <strong>Handelsregisternummer:</strong> HRB 206627<br />
                <strong>Steuernummer:</strong> 16/205/37203
            </p>
        </div>
    );
}

export default Impressum;
