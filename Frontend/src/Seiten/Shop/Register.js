import axios from 'axios';
import React, { useState } from 'react';
import { Toast, ToastContainer } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

function Register() {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [passwort, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [loading, setLoading] = useState(false);
    const [showSuccessToast, setShowSuccessToast] = useState(false);
    const [showErrorToast, setShowErrorToast] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');
    const navigate = useNavigate();

    const handleRegister = (e) => {
        e.preventDefault();

        if (passwort !== confirmPassword) {
            setErrorMessage('Passwörter stimmen nicht überein!');
            setShowErrorToast(true);
            return;
        }

        const userData = {
            name,
            email,
            passwort
        };

        setLoading(true);

        axios.post('/api/register', userData)
            .then((response) => {
                console.log('Erfolgreiche Registrierung:', response);
                setShowSuccessToast(true);
                setTimeout(() => {
                    navigate('/einloggen');
                }, 2000);
            })
            .catch((error) => {
                console.error('Fehler bei der Registrierung:', error);
                if (error.response && error.response.data) {
                    if (error.response.data.message === 'Email already exists') {
                        setErrorMessage('E-Mail bereits registriert!');
                    } else {
                        setErrorMessage('Es gab ein Problem bei der Registrierung.');
                    }
                } else {
                    setErrorMessage('Es gab ein Problem bei der Registrierung.');
                }
                setShowErrorToast(true);
            })
            .finally(() => {
                setLoading(false);
            });
    };

    return (
        <>
            <div className="container d-flex justify-content-center align-items-center" style={{ minHeight: '50vh', marginTop: '50px', marginBottom: '50px' }}>
                <div className="card p-4 auth-card" style={{ maxWidth: '800px', width: '100%', boxShadow: '0 4px 12px rgba(0, 0, 0, 0.1)' }}>
                    <h2 className="text-center mb-4">Registrieren</h2>
                    <form onSubmit={handleRegister}>
                        <div className="row">
                            <div className="col-12 col-md-6 mb-3">
                                <label htmlFor="name" className="form-label">Name</label>
                                <input type="text" className="form-control" id="email" value={name} onChange={(e) => setName(e.target.value)} required />
                            </div>
                            <div className="col-12 col-md-6 mb-3">
                                <label htmlFor="email" className="form-label">E-Mail</label>
                                <input type="email" className="form-control" id="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
                            </div>
                            <div className="col-12 col-md-6 mb-3">
                                <label htmlFor="password" className="form-label">Passwort</label>
                                <input type="password" className="form-control" id="password" value={passwort} onChange={(e) => setPassword(e.target.value)} required />
                            </div>
                            <div className="col-12 col-md-6 mb-3">
                                <label htmlFor="confirmPassword" className="form-label">Passwort wiederholen</label>
                                <input type="password" className="form-control" id="confirmPassword" value={confirmPassword} onChange={(e) => setConfirmPassword(e.target.value)} required />
                            </div>
                        </div>
                        <button type="submit" className="btn custom-btn w-100" disabled={loading}>
                            {loading ? 'Registrieren...' : 'Registrieren'}
                        </button>
                    </form>
                    <div className="mt-3 text-center">
                        <p className="text-muted">
                            Bereits ein Konto? <a href="/einloggen" className="text-decoration-none text-primary">Jetzt einloggen</a>
                        </p>
                    </div>
                </div>
            </div>

            <ToastContainer className="p-3"
                style={{
                    position: 'fixed',
                    bottom: `${window.scrollY - 330}px`,
                    left: '20px',
                    zIndex: 1050,
                }}>
                <Toast bg="success" show={showSuccessToast} onClose={() => setShowSuccessToast(false)} delay={2000} autohide>
                    <Toast.Body className="text-white">Registrierung erfolgreich! Sie können sich jetzt einloggen.</Toast.Body>
                </Toast>
            </ToastContainer>

            <ToastContainer
                className="p-3"
                style={{
                    position: 'fixed',
                    bottom: `${window.scrollY - 330}px`,
                    left: '20px',
                    zIndex: 1050,
                }}
            >
                <Toast bg="danger" show={showErrorToast} onClose={() => setShowErrorToast(false)} delay={5000} autohide>
                    <Toast.Body className="text-white">{errorMessage}</Toast.Body>
                </Toast>
            </ToastContainer>
        </>
    );
}

export default Register;
