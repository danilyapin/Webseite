import { jwtDecode } from 'jwt-decode';
import React, { createContext, useContext, useEffect, useState } from 'react';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [userRole, setUserRole] = useState(null);
    const [token, setToken] = useState(null);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        const checkAuth = () => {
            const storedToken = localStorage.getItem('token');

            if (storedToken) {
                try {
                    const decodedToken = jwtDecode(storedToken);
                    if (decodedToken.exp * 1000 > Date.now()) {
                        setToken(storedToken);

                        let role = decodedToken['role'] || 'USER';
                        if (!role.startsWith('ROLE_')) role = 'ROLE_' + role;

                        setUserRole(role);
                    } else {
                        localStorage.removeItem('token');
                    }
                } catch {
                    localStorage.removeItem('token');
                }
            }
            setIsLoading(false);
        }
        checkAuth();
    }, []);

    const login = (newToken) => {
        localStorage.setItem('token', newToken);
        const decoded = jwtDecode(newToken);
        const roleFromToken = decoded['role'] || 'USER';
        setToken(newToken);
        setUserRole(roleFromToken.startsWith('ROLE_') ? roleFromToken : 'ROLE_' + roleFromToken);
    };

    const logout = () => {
        localStorage.removeItem('token');
        setToken(null);
        setUserRole(null);
    };

    const isLoggedIn = !!token;

    return (
        <AuthContext.Provider value={{ token, userRole, isLoggedIn, login, logout, isLoading}}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => useContext(AuthContext);