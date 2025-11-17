import React from 'react';
import { Navigate } from 'react-router-dom';
import { useAuth } from './AuthProvider';

export const AdminRoute = ({ children }) => {
    const { isLoggedIn, userRole, isLoading } = useAuth();

    if (isLoading) {
        return <p>Lädt...</p>;
    }

    if (!isLoggedIn || userRole !== 'ROLE_ADMIN') {
        return <Navigate to="/" replace />;
    }

    return children;
};

export default AdminRoute;
