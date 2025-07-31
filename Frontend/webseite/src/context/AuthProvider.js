import { jwtDecode } from 'jwt-decode';
import React, { createContext, useContext, useEffect, useState } from 'react';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [userRole, setUserRole] = useState(null);
    const [token, setToken] = useState(null);
    const [isLoading, setIsLoading] = useState(true); // NEU

    useEffect(() => {
        const storedToken = localStorage.getItem('token');
        if (storedToken) {
            try {
                const decoded = jwtDecode(storedToken);
                setToken(storedToken);
                setIsLoggedIn(true);
                setUserRole(decoded.role); // Stelle sicher, dass `role` im Token enthalten ist
            } catch (err) {
                console.error('Ungültiger Token:', err);
                localStorage.removeItem('token');
            }
        }
        setIsLoading(false); // Wichtig – auch wenn kein Token da ist
    }, []);

    const login = (token) => {
        localStorage.setItem('token', token);
        const decoded = jwtDecode(token);
        setToken(token);
        setIsLoggedIn(true);
        setUserRole(decoded.role);
    };

    const logout = () => {
        localStorage.removeItem('token');
        setToken(null);
        setIsLoggedIn(false);
        setUserRole(null);
    };

    return (
        <AuthContext.Provider value={{ isLoggedIn, userRole, login, logout, token, isLoading }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => useContext(AuthContext);
