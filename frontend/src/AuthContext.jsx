import { createContext, useContext, useEffect, useMemo, useState } from 'react';
import api from './api';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(() => {
    const raw = localStorage.getItem('user');
    return raw ? JSON.parse(raw) : null;
  });

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (!token || token.startsWith('demo-')) {
      return;
    }
    api.get('/auth/me')
      .then((res) => {
        setUser(res.data);
        localStorage.setItem('user', JSON.stringify(res.data));
      })
      .catch(() => {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        setUser(null);
      });
  }, []);

  const value = useMemo(() => ({
    user,
    login: (auth) => {
      localStorage.setItem('token', auth.token);
      const next = {
        id: auth.userId,
        fullName: auth.fullName,
        email: auth.email,
        role: auth.role
      };
      localStorage.setItem('user', JSON.stringify(next));
      setUser(next);
    },
    logout: () => {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      setUser(null);
    }
  }), [user]);

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  return useContext(AuthContext);
}
