import axios from "axios";
import React, { useEffect, useState } from "react";
import { Alert, Button, Container, Form } from "react-bootstrap";
import { useNavigate, useParams } from "react-router-dom";

function ArtikelBearbeiten() {
    const { id } = useParams();
    const navigate = useNavigate();
    const [artikel, setArtikel] = useState(null);
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(null);
    const [alertVisible, setAlertVisible] = useState(false);

    useEffect(() => {
        axios.get(`http://localhost:8081/api/artikel/${id}`)
            .then(response => {
                const data = response.data;
                setArtikel({ ...data, bild: null }); // damit bei neuem Upload keine Verwechslung
                console.log(response.data)
            })
            .catch(error => {
                console.error("Fehler beim Laden des Artikels:", error);
            });
    }, [id]);

    if (!artikel) {
        return <p>Lade Artikel...</p>;
    }

    const handleChange = (e) => {
        setArtikel({ ...artikel, [e.target.name]: e.target.value });
    };

    const handleImageUpload = (e) => {
        const file = e.target.files[0];
        if (file) {
            const validImageTypes = ["image/jpeg", "image/png", "image/jpg"];
            if (!validImageTypes.includes(file.type)) {
                setError("Nur PNG, JPG oder JPEG-Dateien sind erlaubt!");
                setAlertVisible(true);
                setTimeout(() => {
                    setError(null);
                    setAlertVisible(false);
                }, 5000);
            } else {
                setArtikel({ ...artikel, bild: file });
            }
        }
    };

    const handleUpdate = async (e) => {
        e.preventDefault();

        if (!artikel.titel || !artikel.beschreibung || !artikel.preis) {
            setError("Bitte fülle alle erforderlichen Felder aus!");
            setAlertVisible(true);
            return;
        }

        const formData = new FormData();
        formData.append("titel", artikel.titel);
        formData.append("beschreibung", artikel.beschreibung);
        formData.append("eanNummer", artikel.eanNummer);
        formData.append("artikelnummer", artikel.artikelnummer);
        formData.append("preis", artikel.preis);

        if (artikel.bild) {
            formData.append("bild", artikel.bild);
        }

        try {
            await axios.put(`http://localhost:8081/api/artikel/${id}`, formData, {
                headers: {
                    "Content-Type": "multipart/form-data",
                },
            });
            setSuccess("Artikel erfolgreich aktualisiert!");
            setTimeout(() => {
                setSuccess(null);
                navigate(-1);
            }, 2000);
        } catch (error) {
            console.error("Fehler beim Aktualisieren des Artikels:", error);
            setError("Fehler beim Aktualisieren. Bitte versuche es erneut.");
            setAlertVisible(true);
        }
    };

    return (
        <Container className="mt-4 mb-5">
            <h2>Artikel bearbeiten</h2>
            <Form
                onSubmit={handleUpdate}
                style={{
                    boxShadow: "0 4px 8px rgba(0, 0, 0, 0.1)",
                    padding: "20px",
                    borderRadius: "8px",
                }}
            >
                {error && alertVisible && (
                    <Alert variant="danger">{error}</Alert>
                )}
                {success && (
                    <Alert variant="success">{success}</Alert>
                )}

                <Form.Group className="mb-3 text-center">
                    <img
                        src={
                            artikel.bild
                                ? URL.createObjectURL(artikel.bild) // Wenn ein Bild hochgeladen wurde, zeige dieses
                                : artikel.bild_pfad
                                    ? `http://localhost:8081${artikel.bild_pfad}`
                                    : "/default-image.png"
                        }
                        className="card-img-top p-3"
                        alt={artikel.titel}
                        style={{ height: "200px", objectFit: "contain" }}
                    />

                    <Form.Control type="file" accept="image/*" onChange={handleImageUpload} />
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>Titel</Form.Label>
                    <Form.Control type="text" name="titel" value={artikel.titel || ""} onChange={handleChange} required />
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>Beschreibung</Form.Label>
                    <Form.Control as="textarea" rows={3} name="beschreibung" value={artikel.beschreibung || ""} onChange={handleChange} required />
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>EAN Nummer</Form.Label>
                    <Form.Control type="text" name="eanNummer" value={artikel.eanNummer || ""} onChange={handleChange} />
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>Artikelnummer</Form.Label>
                    <Form.Control type="text" name="artikelnummer" value={artikel.artikelnummer || ""} onChange={handleChange} readOnly />
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>Preis</Form.Label>
                    <Form.Control type="number" step="0.01" name="preis" value={artikel.preis || ""} onChange={handleChange} required />
                </Form.Group>

                <div className="d-flex justify-content-center gap-3">
                    <Button type="submit" variant="success">Aktualisieren</Button>
                    <Button className="custom-btn" onClick={() => navigate(-1)}>Abbrechen</Button>
                </div>
            </Form>
        </Container>
    );
}

export default ArtikelBearbeiten;
