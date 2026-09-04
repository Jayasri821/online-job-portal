import { Navigate, Route, Routes } from 'react-router-dom';
import Navbar from './Navbar.jsx';
import ProtectedRoute from './ProtectedRoute.jsx';
import HomePage from './pages/HomePage.jsx';
import JobsPage from './pages/JobsPage.jsx';
import JobDetailsPage from './pages/JobDetailsPage.jsx';
import LoginPage from './pages/LoginPage.jsx';
import RegisterPage from './pages/RegisterPage.jsx';
import SeekerDashboard from './pages/SeekerDashboard.jsx';
import RecruiterDashboard from './pages/RecruiterDashboard.jsx';
import AdminDashboard from './pages/AdminDashboard.jsx';

export default function App() {
  return (
    <div className="app-shell">
      <Navbar />
      <main className="page">
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/jobs" element={<JobsPage />} />
          <Route path="/jobs/:id" element={<JobDetailsPage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />
          <Route element={<ProtectedRoute roles={['JOB_SEEKER']} />}>
            <Route path="/seeker" element={<SeekerDashboard />} />
          </Route>
          <Route element={<ProtectedRoute roles={['RECRUITER']} />}>
            <Route path="/recruiter" element={<RecruiterDashboard />} />
          </Route>
          <Route element={<ProtectedRoute roles={['ADMIN']} />}>
            <Route path="/admin" element={<AdminDashboard />} />
          </Route>
          <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
      </main>
    </div>
  );
}
