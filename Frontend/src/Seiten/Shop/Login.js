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
    const {login} = useAuth();

    const handleLogin = async (e) => {
        e.preventDefault();
        setLoading(true);
        setErrorMessage('');

        try {
            const response = await axios.post('/api/login', {email, passwort: password});
            const token = response.data.token;

            if (!token) {
                setErrorMessage('Kein Token erhalten!');
                return;
            }

            const decoded = jwtDecode(token);
            let role = decoded['role'] || 'USER';
            role = role.replace(/^ROLE_/, '');

            login(token, 'ROLE_' + role);

            const profilResponse = await axios.get('/api/profil', {
                headers: {Authorization: `Bearer ${token}`},
            })

            const name = profilResponse.data.name || 'Benutzer';
            localStorage.setItem('name', name);

            setShowToast(true);

            setTimeout(() => {
                if (role === 'ADMIN') {
                    navigate('/admin');
                } else {
                    navigate('/mein_konto');
                }
            })

        } catch (error) {
            console.log('Fehler beim Login:', error);
            setErrorMessage("Falsche Anmeldedaten!");
        } finally {
            setLoading(false);
        }
    }

        return (
            <div className="d-flex justify-content-center align-items-center" style={{minHeight: "70vh"}}>
                <div className="card p-4" style={{maxWidth: "400px", width: "100%"}}>
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
                                Noch kein Konto? <a href="/registrieren" className="text-decoration-none text-primary">Jetzt
                                registrieren</a>
                            </p>
                        </div>
                    </form>
                </div>

                <ToastContainer position="bottom-start" className="p-3" style={{bottom: "20px", left: "20px"}}>
                    <Toast bg="success" show={showToast} delay={2000} autohide onClose={() => setShowToast(false)}>
                        <Toast.Body className="text-white">Willkommen zurück! Sie sind jetzt eingeloggt.</Toast.Body>
                    </Toast>
                </ToastContainer>
            </div>
        );
}

export default Login;
