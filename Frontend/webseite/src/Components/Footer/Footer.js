import 'bootstrap/dist/css/bootstrap.min.css';
import React from 'react';
import { Link } from 'react-router-dom';

function Footer() {
    return (
        <footer className='bg-custom text-light text-center py-3 mt-auto'>
            <div>
                © 2024 Multimedia Display GmbH (DL) | Alle Rechte vorbehalten
                {' '}|{' '}
                <Link to="/impressum" className="text-light text-decoration-underline">
                    Impressum
                </Link>
            </div>
        </footer>
    );
}

export default Footer;
