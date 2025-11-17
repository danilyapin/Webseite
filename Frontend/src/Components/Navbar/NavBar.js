import 'bootstrap-icons/font/bootstrap-icons.css';
import React, { useState } from 'react';
import { Toast, ToastContainer } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import logo from '../../Bilder/logo_shop.jpg';
import logoKlein from '../../Bilder/logo_shop_klein.jpg';
import { useAuth } from '../../context/AuthProvider';

export default function NavBar() {
    const { isLoggedIn, userRole, logout } = useAuth();
    const navigate = useNavigate();
    const [showLogoutToast, setShowLogoutToast] = useState(false);

    const handleLogout = (e) => {
        e.preventDefault();
        logout();
        setShowLogoutToast(true);
        setTimeout(() => {
            navigate('/');
        }, 1500);
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
                                    <i className="bi bi-box-arrow-left me-2"></i>
                                </a>

                                {userRole === 'ROLE_ADMIN' && (
                                    <>
                                        <a className="nav-link text-light" href="/admin/buchungen_details">
                                            <i className="bi bi-journal-text"></i>
                                        </a>
                                        <a className="nav-link text-light" href="/admin">
                                            <i className="bi bi-house-gear"></i>
                                        </a>
                                    </>
                                )}

                                {userRole === 'ROLE_USER' && (
                                    <a className="nav-link text-light" href="/mein_konto">
                                        <i className="bi bi-person-circle"></i>
                                    </a>
                                )}
                            </>
                        ) : (
                            <>
                                <a className="nav-link text-light" href="/einloggen">
                                    <i className="bi bi-box-arrow-in-right me-2"></i>
                                </a>
                                <a className="nav-link text-light" href="/registrieren">
                                    <i className="bi bi-person-plus me-2"></i>
                                </a>
                            </>
                        )}
                    </div>
                </div>
            </nav>

            <ToastContainer position="bottom-start" className="p-3" style={{ bottom: '80px' }}>
                <Toast bg="success" show={showLogoutToast} onClose={() => setShowLogoutToast(false)} delay={2000} autohide>
                    <Toast.Body className="text-white">Auf wiedersehen! Sie sind ausgeloggt.</Toast.Body>
                </Toast>
            </ToastContainer>
        </>
    );
}