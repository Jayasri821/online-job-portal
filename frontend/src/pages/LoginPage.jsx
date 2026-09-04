import { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import api from '../api';
import { useAuth } from '../AuthContext.jsx';

export default function LoginPage() {
  const { login, user } = useAuth();
  const navigate = useNavigate();
  const [form, setForm] = useState({ email: '', password: '' });
  const [error, setError] = useState('');

  useEffect(() => {
    if (!user) return;
    if (user.role === 'ADMIN') navigate('/admin');
    else if (user.role === 'RECRUITER') navigate('/recruiter');
    else navigate('/seeker');
  }, [user, navigate]);

  const submit = async (e) => {
    e.preventDefault();
    setError('');
    try {
      const { data } = await api.post('/auth/login', form);
      login(data);
    } catch (err) {
      setError(err.response?.data?.message || 'Login failed');
    }
  };

  return (
    <form className="panel auth-box stack" onSubmit={submit}>
      <h2>Login</h2>
      {error && <div className="alert">{error}</div>}
      <label>Email<input type="email" value={form.email} onChange={(e) => setForm({ ...form, email: e.target.value })} required /></label>
      <label>Password<input type="password" value={form.password} onChange={(e) => setForm({ ...form, password: e.target.value })} required /></label>
      <button className="btn" type="submit">Sign in</button>
      <p className="muted">Demo: admin@jobportal.com / Admin@123</p>
      <p className="muted">No account? <Link to="/register">Register</Link></p>
    </form>
  );
}
