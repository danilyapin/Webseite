import React, { useState } from "react";
import { Container, Form, Button, Alert } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import ReactQuill from "react-quill";
import "react-quill/dist/quill.snow.css";

function ArtikelEinfügen() {
    const navigate = useNavigate();
    const [artikel, setArtikel] = useState({
        titel: "",
        beschreibung: "",
        eanNummer: "",
        artikelnummer: "",
        preis: "",
        bild: null,
    });
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(null);

    const handleChange = (e) => {
        setArtikel({ ...artikel, [e.target.name]: e.target.value });
    };

    const handleBildUpload = (e) => {
        const file = e.target.files[0];
        if (file) {
            const validImageTypes = ["image/jpeg", "image/png", "image/jpg"];
            if (!validImageTypes.includes(file.type)) {
                setError("Nur PNG, JPG oder JPEG-Dateien sind erlaubt!");
                setTimeout(() => setError(null), 5000);
            } else {
                setArtikel({ ...artikel, bild: file });
            }
        }
    };

    const handleBeschreibungChange = (value) => {
        setArtikel({ ...artikel, beschreibung: value });
    };

    const handleUpdate = async (e) => {
        e.preventDefault();
        if (!artikel.titel || !artikel.beschreibung || !artikel.preis || !artikel.artikelnummer) {
            setError("Bitte fülle alle erforderlichen Felder aus!");
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
            await axios.post("/api/artikel", formData, {
                headers: { "Content-Type": "multipart/form-data" },
            });
            setSuccess("Artikel erfolgreich eingefügt!");
            setTimeout(() => navigate(-1), 2000);
        } catch (err) {
            setError("Fehler beim Speichern!");
            console.error(err);
        }
    };

    return (
        <Container className="mt-4 mb-5">
            <h2>Artikel einstellen</h2>
            {error &&
                <Alert variant="danger">{error}
                </Alert>}
            {success &&
                <Alert variant="success">{success}
                </Alert>}
            <Form
                onSubmit={handleUpdate}
                style={{
                    boxShadow: "0 4px 12px rgba(0,0,0,0.1)",
                    padding: "25px",
                    borderRadius: "10px",
                }}
            >
                <Form.Group className="mb-4 text-center">
                    {artikel.bild && (
                        <img
                            src={URL.createObjectURL(artikel.bild)}
                            alt="Artikel"
                            style={{ maxWidth: "250px", maxHeight: "250px", objectFit: "contain", marginBottom: "10px" }}
                        />
                    )}
                    <Form.Control type="file" accept="image/*" onChange={handleBildUpload} />
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>Titel</Form.Label>
                    <Form.Control type="text" name="titel" value={artikel.titel} onChange={handleChange} required />
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>Beschreibung</Form.Label>
                    <ReactQuill
                        theme="snow"
                        value={artikel.beschreibung}
                        onChange={handleBeschreibungChange}
                        modules={{
                            toolbar: [
                                [{ header: [1, 2, 3, 4, 5, 6, false] }],
                                ["bold", "italic", "underline", "strike"],
                                [{ list: "ordered" }, { list: "bullet" }],
                                ["link", "image"],
                                ["clean"],
                            ],
                        }}
                        style={{ minHeight: "50px" }}
                    />
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>
                        Artikelnummer <small className="text-muted">(später nicht änderbar)</small>
                    </Form.Label>
                    <Form.Control
                        type="text"
                        name="artikelnummer"
                        value={artikel.artikelnummer}
                        onChange={handleChange}
                        required
                    />
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>EAN Nummer</Form.Label>
                    <Form.Control type="text" name="eanNummer" value={artikel.eanNummer} onChange={handleChange} />
                </Form.Group>

                <Form.Group className="mb-4">
                    <Form.Label>Preis</Form.Label>
                    <Form.Control type="number" step="0.01" name="preis" value={artikel.preis} onChange={handleChange} required />
                </Form.Group>

                <div className="d-flex justify-content-center gap-3">
                    <Button type="submit" variant="success">Einstellen</Button>
                    <Button variant="secondary" className="custom-btn" onClick={() => navigate(-1)}>Abbrechen</Button>
                </div>
            </Form>
        </Container>
    );
}

export default ArtikelEinfügen;
