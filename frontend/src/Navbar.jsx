import { Link, NavLink, useNavigate } from 'react-router-dom';
import { useAuth } from './AuthContext.jsx';

export default function Navbar() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const dashboardPath = () => {
    if (!user) return '/login';
    if (user.role === 'ADMIN') return '/admin';
    if (user.role === 'RECRUITER') return '/recruiter';
    return '/seeker';
  };

  return (
    <header className="nav">
      <Link to="/" className="brand">HireHub</Link>
      <nav>
        <NavLink to="/jobs">Jobs</NavLink>
        {user && <NavLink to={dashboardPath()}>Dashboard</NavLink>}
      </nav>
      <div className="nav-actions">
        {user ? (
          <>
            <span className="muted">{user.fullName}</span>
            <button className="btn ghost" onClick={() => { logout(); navigate('/'); }}>Logout</button>
          </>
        ) : (
          <>
            <Link className="btn ghost" to="/login">Login</Link>
            <Link className="btn" to="/register">Register</Link>
          </>
        )}
      </div>
    </header>
  );
}
