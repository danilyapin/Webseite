import axios from "axios";
import React, { useEffect, useState } from "react";
import { Alert, Button, Col, Container, Form, Row, Spinner } from "react-bootstrap";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { useLocation, useNavigate } from "react-router-dom";

const formatDate = (date) => {
    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const day = date.getDate().toString().padStart(2, '0');
    return `${year}-${month}-${day}`;
};

const useQuery = () => new URLSearchParams(useLocation().search);

function ArtikelFormular() {
    const navigate = useNavigate();
    const query = useQuery();
    const artikelnummerFromQuery = query.get("artikelnummer");

    const [startDate, setStartDate] = useState(null);
    const [endDate, setEndDate] = useState(null);
    const [customerInfo, setCustomerInfo] = useState({
        unternehmen: "",
        ansprechpartner: "",
        email: "",
        telefon: "+49",
        lieferStrasse: "",
        lieferPLZ: "",
        lieferOrt: "",
        abholStrasse: "",
        abholPLZ: "",
        abholOrt: "",
        zusatzInfo: "",
    });
    const [artikelnummer, setArtikelnummer] = useState(null);
    const [error, setError] = useState(null);
    const [isLoading, setIsLoading] = useState(false);
    const [bookedDates, setBookedDates] = useState([]); // Array für gebuchte Daten

    useEffect(() => {
        if (artikelnummerFromQuery) {
            setArtikelnummer(artikelnummerFromQuery);
        }
    }, [artikelnummerFromQuery]);

    useEffect(() => {
        if (!artikelnummer) return;

        axios.get(`/api/buchungen/alle-gebuchten-daten/${artikelnummer}`)
            .then(response => {
                const bookedDatesArray = response.data.map(buchung => {

                    const startDate = new Date(buchung.mieteBegin);
                    const endDate = new Date(buchung.mieteEnde);
                    const dates = [];
                    let currentDate = startDate;
                    while (currentDate <= endDate) {
                        dates.push(new Date(currentDate));
                        currentDate.setDate(currentDate.getDate() + 1);
                    }
                    return dates;
                });

                const flatBookedDates = bookedDatesArray.flat();
                setBookedDates(flatBookedDates);
            })
            .catch(error => {
                console.error("Fehler beim Abrufen der gebuchten Daten", error);
            });
    }, [artikelnummer]);

    const handleChange = (e) => {
        setCustomerInfo({
            ...customerInfo,
            [e.target.name]: e.target.value,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setIsLoading(true);

        if (!startDate || !endDate || !customerInfo.unternehmen || !customerInfo.ansprechpartner || !customerInfo.email || !customerInfo.telefon || !customerInfo.lieferStrasse || !customerInfo.lieferPLZ || !customerInfo.lieferOrt || !customerInfo.abholStrasse || !customerInfo.abholPLZ || !customerInfo.abholOrt) {
            setError("Bitte alle erforderlichen Felder ausfüllen.");
            setIsLoading(false);
            return;
        }

        const rentalData = {
            artikelnummer: artikelnummer,
            unternehmen: customerInfo.unternehmen,
            ansprechpartner: customerInfo.ansprechpartner,
            email: customerInfo.email,
            telefon: customerInfo.telefon,
            lieferStrasse: customerInfo.lieferStrasse,
            lieferPLZ: customerInfo.lieferPLZ,
            lieferOrt: customerInfo.lieferOrt,
            abholStrasse: customerInfo.abholStrasse,
            abholPLZ: customerInfo.abholPLZ,
            abholOrt: customerInfo.abholOrt,
            mieteBegin: formatDate(startDate),
            mieteEnde: formatDate(endDate),
            zusatzInfo: customerInfo.zusatzInfo,
        };

        try {
            const response = await fetch("/api/buchungen", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(rentalData),
            });

            if (!response.ok) {
                throw new Error(`Fehler: ${response.statusText}`);
            }

            const data = await response.json();
            setError(null);

            setTimeout(() => {
                setIsLoading(false);
                navigate("/buchungsbestaetigung", {
                    state: {
                        customerInfo,
                        startDate,
                        endDate,
                        artikelnummer,
                        buchungId: data.buchungId
                    }
                });
            }, 2000);
        } catch (error) {
            setError("Es gab ein Problem mit der Buchung. Bitte versuchen Sie es erneut.");
            setIsLoading(false);
        }
    };

    return (
        <Container className="mt-4 mb-5" style={{ position: "relative" }}>
            <h2>Artikel mieten</h2>
            {error &&
                <Alert variant="danger">{error}</Alert>
            }
            <Form
                onSubmit={handleSubmit}
                style={{
                    boxShadow: "0 4px 8px rgba(0, 0, 0, 0.1)",
                    padding: "20px",
                    borderRadius: "8px",
                    marginBottom: "20px",
                }}
            >
                <Row className="mb-3">
                    <Col md={6}>
                        <Form.Label>Artikelnummer:</Form.Label>
                        <Form.Control type="text" value={artikelnummer || ""} readOnly />
                    </Col>
                </Row>

                <Row className="mb-3">
                    <Col md={6}><Form.Label>Unternehmen: *</Form.Label><Form.Control name="unternehmen" value={customerInfo.unternehmen} onChange={handleChange} /></Col>
                    <Col md={6}><Form.Label>Ansprechpartner: *</Form.Label><Form.Control name="ansprechpartner" value={customerInfo.ansprechpartner} onChange={handleChange} /></Col>
                </Row>
                <Row className="mb-3">
                    <Col md={6}><Form.Label>E-Mail: *</Form.Label><Form.Control name="email" type="email" value={customerInfo.email} onChange={handleChange} /></Col>
                    <Col md={6}><Form.Label>Telefon: *</Form.Label><Form.Control name="telefon" value={customerInfo.telefon} onChange={handleChange} /></Col>
                </Row>

                <hr />
                <h4 className="mt-4 mb-2">Lieferadresse *</h4>
                <Row className="mb-3">
                    <Col md={4}>
                        <Form.Label>Straße: *</Form.Label>
                        <Form.Control name="lieferStrasse" value={customerInfo.lieferStrasse} onChange={handleChange} />
                    </Col>
                    <Col md={4}>
                        <Form.Label>Postleitzahl: *</Form.Label>
                        <Form.Control name="lieferPLZ" value={customerInfo.lieferPLZ} onChange={handleChange} />
                    </Col>
                    <Col md={4}>
                        <Form.Label>Ort: *</Form.Label>
                        <Form.Control name="lieferOrt" value={customerInfo.lieferOrt} onChange={handleChange} />
                    </Col>
                </Row>

                <hr />
                <h4 className="mt-4 mb-2">Abholadresse *</h4>
                <Row className="mb-3">
                    <Col md={4}>
                        <Form.Label>Straße: *</Form.Label>
                        <Form.Control name="abholStrasse" value={customerInfo.abholStrasse} onChange={handleChange} />
                    </Col>
                    <Col md={4}>
                        <Form.Label>Postleitzahl: *</Form.Label>
                        <Form.Control name="abholPLZ" value={customerInfo.abholPLZ} onChange={handleChange} />
                    </Col>
                    <Col md={4}>
                        <Form.Label>Ort: *</Form.Label>
                        <Form.Control name="abholOrt" value={customerInfo.abholOrt} onChange={handleChange} />
                    </Col>
                </Row>

                <hr />
                <h5 className="mt-4 mb-2">Zusätzliche Informationen</h5>
                <Row className="mb-3">
                    <Col>
                        <Form.Control
                            as="textarea"
                            name="zusatzInfo"
                            value={customerInfo.zusatzInfo}
                            onChange={handleChange}
                        />
                    </Col>
                </Row>

                <h5 className="mt-4 mb-2">Mietdauer</h5>
                <Row className="mb-4">
                    <Col md={6}>
                        <Form.Label>Mietebeginn:</Form.Label>
                        <DatePicker
                            selected={startDate}
                            onChange={(date) => setStartDate(date)}
                            dateFormat="dd.MM.yyyy"
                            className="form-control"
                            minDate={new Date()}
                            placeholderText="Startdatum wählen"
                            excludeDates={bookedDates}
                        />
                    </Col>
                    <Col md={6}>
                        <Form.Label>Mieteende:</Form.Label>
                        <DatePicker
                            selected={endDate}
                            onChange={(date) => setEndDate(date)}
                            dateFormat="dd.MM.yyyy"
                            className="form-control"
                            minDate={startDate || new Date()}
                            placeholderText="Enddatum wählen"
                            excludeDates={bookedDates}
                        />
                    </Col>
                </Row>

                <div className="d-flex justify-content-center gap-3 mt-4">
                    <Button
                        type="submit"
                        className="btn btn-success"
                        disabled={isLoading}
                    >
                        {isLoading ? <Spinner animation="border" size="sm" /> : "Mieten"}
                    </Button>

                    <Button
                        className="btn-custom"
                        onClick={() => navigate(-1)}
                    >
                        Zurück
                    </Button>
                </div>
            </Form>
        </Container>
    );
}

export default ArtikelFormular;
