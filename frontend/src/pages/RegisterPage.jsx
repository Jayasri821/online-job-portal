import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import api from '../api';
import { useAuth } from '../AuthContext.jsx';

export default function RegisterPage() {
  const { login } = useAuth();
  const navigate = useNavigate();
  const [form, setForm] = useState({
    fullName: '',
    email: '',
    password: '',
    role: 'JOB_SEEKER'
  });
  const [error, setError] = useState('');

  const submit = async (e) => {
    e.preventDefault();
    setError('');
    try {
      const { data } = await api.post('/auth/register', form);
      login(data);
      navigate(form.role === 'RECRUITER' ? '/recruiter' : '/seeker');
    } catch (err) {
      const users = JSON.parse(localStorage.getItem('demoUsers') || '[]');
      if (users.some((user) => user.email === form.email)) {
        setError('An account with this email already exists');
        return;
      }
      const demoUser = { ...form, id: Date.now() };
      localStorage.setItem('demoUsers', JSON.stringify([...users, demoUser]));
      login({ token: `demo-${demoUser.id}`, userId: demoUser.id, ...demoUser });
      navigate(form.role === 'RECRUITER' ? '/recruiter' : '/seeker');
    }
  };

  return (
    <form className="panel auth-box stack" onSubmit={submit}>
      <h2>Create account</h2>
      {error && <div className="alert">{error}</div>}
      <label>Full name<input value={form.fullName} onChange={(e) => setForm({ ...form, fullName: e.target.value })} required /></label>
      <label>Email<input type="email" value={form.email} onChange={(e) => setForm({ ...form, email: e.target.value })} required /></label>
      <label>Password<input type="password" value={form.password} onChange={(e) => setForm({ ...form, password: e.target.value })} required minLength={6} /></label>
      <label>Role
        <select value={form.role} onChange={(e) => setForm({ ...form, role: e.target.value })}>
          <option value="JOB_SEEKER">Job Seeker</option>
          <option value="RECRUITER">Recruiter</option>
        </select>
      </label>
      <button className="btn" type="submit">Register</button>
      <p className="muted">Already registered? <Link to="/login">Login</Link></p>
    </form>
  );
}
