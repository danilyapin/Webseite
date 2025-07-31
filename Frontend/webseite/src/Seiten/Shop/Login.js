import axios from 'axios';
import { jwtDecode } from 'jwt-decode';
import React, { useState } from 'react';
import { Toast, ToastContainer } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/AuthProvider';

function Login() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [loading, setLoading] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');
    const [showToast, setShowToast] = useState(false);
    const navigate = useNavigate();
    const { login } = useAuth();

    const handleLogin = (e) => {
        e.preventDefault();

        const loginData = {
            email,
            passwort: password,
        };

        setLoading(true);

        axios.post('http://localhost:8081/api/login', loginData)
            .then((response) => {
                console.log('Backend Response:', response.data);
                // Angenommen, der Token wird unter "token" zurückgegeben:
                const token = response.data.token;
                if (token) {
                    localStorage.setItem('token', token);
                    const decodedToken = jwtDecode(token);
                    const role = decodedToken.role || 'USER';
                    localStorage.setItem('role', role);
                    // Profil abrufen (zum Beispiel, um den Namen zu erhalten)
                    return axios.get('http://localhost:8081/api/profil', {
                        headers: {
                            Authorization: `Bearer ${token}`,
                        },
                    }).then((res) => {
                        const name = res.data.vorname || res.data.name || 'Benutzer';
                        localStorage.setItem('name', name);
                        login(token, role);

                        setShowToast(true);
                        setTimeout(() => {
                            if (role === 'ROLE_ADMIN') {
                                navigate('/admin');
                            } else {
                                navigate('/mein_konto');
                            }
                        }, 2000);
                    });
                } else {
                    setErrorMessage('Kein Token erhalten!');
                }
            })
            .catch((error) => {
                setErrorMessage('Falsche Anmeldedaten!');
                console.error('Fehler beim Login:', error);
            })
            .finally(() => {
                setLoading(false);
            });
    };

    return (
        // Der äußere Div nimmt die gesamte Viewport-Höhe ein und nutzt Flexbox, um den Inhalt zu zentrieren
        <div className="d-flex justify-content-center align-items-center" style={{ minHeight: "70vh" }}>
            <div className="card p-4" style={{ maxWidth: "400px", width: "100%" }}>
                <h2 className="text-center mb-4">Einloggen</h2>
                <form onSubmit={handleLogin}>
                    <div className="mb-3">
                        <label htmlFor="email" className="form-label">E-Mail</label>
                        <input
                            type="email"
                            className="form-control"
                            id="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="password" className="form-label">Passwort</label>
                        <input
                            type="password"
                            className="form-control"
                            id="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>
                    {errorMessage && <div className="alert alert-danger">{errorMessage}</div>}
                    <button type="submit" className="btn btn-custom w-100" disabled={loading}>
                        {loading ? "Einloggen..." : "Einloggen"}
                    </button>
                    <div className="mt-3 text-center">
                        <p className="text-muted">
                            Noch kein Konto? <a href="/registrieren" className="text-decoration-none text-primary">Jetzt registrieren</a>
                        </p>
                    </div>
                </form>
            </div>

            {/* Toast für Erfolgsmeldung, unten links */}
            <ToastContainer position="bottom-start" className="p-3" style={{ bottom: "20px", left: "20px" }}>
                <Toast bg="success" show={showToast} delay={2000} autohide onClose={() => setShowToast(false)}>
                    <Toast.Body className="text-white">Willkommen zurück! Sie sind jetzt eingeloggt.</Toast.Body>
                </Toast>
            </ToastContainer>
        </div>
    );
}

export default Login;
