import 'bootstrap-icons/font/bootstrap-icons.css';
import React, { useState } from 'react';
import { Toast, ToastContainer } from 'react-bootstrap'; // Neu
import { useNavigate } from 'react-router-dom';
import logo from '../../Bilder/logo_shop.jpg';
import logoKlein from '../../Bilder/logo_shop_klein.jpg';
import { useAuth } from '../../context/AuthProvider';

export default function NavBar() {
    const { isLoggedIn, userRole, logout } = useAuth();
    const navigate = useNavigate();
    const [showLogoutToast, setShowLogoutToast] = useState(false); // Neu

    const handleLogout = (e) => {
        e.preventDefault(); // Seite nicht neu laden
        logout();
        setShowLogoutToast(true); // Zeige Toast
        setTimeout(() => {
            navigate('/');
        }, 1500); // nach 1,5 Sekunden zurück zur Startseite
    };

    return (
        <>
            <nav className="navbar navbar-light">
                <div className="container-fluid">
                    <a className="navbar-brand" href="/">
                        <img src={logo} alt="Logo" style={{ height: '50px' }} className="logo normal-logo" />
                        <img src={logoKlein} alt="Logo" className="logo small-logo" />
                    </a>
                    <div className="d-flex ms-auto">
                        <a className="nav-link text-light" href="/">
                            <i className="bi bi-house me-2"></i>
                        </a>

                        {isLoggedIn ? (
                            <>
                                <a className="nav-link text-light" href="/" onClick={handleLogout}>
                                    <i className="bi bi-box-arrow-left me-2"></i> {/* Ausloggen-Icon */}
                                </a>

                                {userRole === 'ROLE_ADMIN' && (
                                    <>
                                        <a className="nav-link text-light" href="/admin/buchungen_details">
                                            <i className="bi bi-journal-text"></i> {/* Buch-Icon */}
                                        </a>
                                        <a className="nav-link text-light" href="/admin">
                                            <i className="bi bi-house-gear"></i> {/* Haus-Icon */}
                                        </a>
                                    </>
                                )}

                                {userRole === 'USER' && (
                                    <a className="nav-link text-light" href="/mein_konto">
                                        <i className="bi bi-person-circle"></i> {/* Person-Icon */}
                                    </a>
                                )}
                            </>
                        ) : (
                            <>
                                <a className="nav-link text-light" href="/einloggen">
                                    <i className="bi bi-box-arrow-in-right me-2"></i> {/* Login-Icon */}
                                </a>
                                <a className="nav-link text-light" href="/registrieren">
                                    <i className="bi bi-person-plus me-2"></i> {/* Person_Plus-Icon */}
                                </a>
                            </>
                        )}
                    </div>
                </div>
            </nav>

            {/* ✅ Logout Toast */}
            <ToastContainer position="bottom-start" className="p-3" style={{ bottom: '80px' }}>
                <Toast bg="success" show={showLogoutToast} onClose={() => setShowLogoutToast(false)} delay={2000} autohide>
                    <Toast.Body className="text-white">Auf wiedersehen! Sie sind ausgeloggt.</Toast.Body>
                </Toast>
            </ToastContainer>
        </>
    );
}
