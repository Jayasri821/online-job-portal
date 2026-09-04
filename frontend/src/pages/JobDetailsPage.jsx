import { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import api from '../api';
import { useAuth } from '../AuthContext.jsx';

export default function JobDetailsPage() {
  const { id } = useParams();
  const { user } = useAuth();
  const navigate = useNavigate();
  const [job, setJob] = useState(null);
  const [coverLetter, setCoverLetter] = useState('');
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');

  useEffect(() => {
    api.get(`/jobs/${id}`).then((res) => setJob(res.data));
  }, [id]);

  const apply = async (e) => {
    e.preventDefault();
    setError('');
    setMessage('');
    if (!user) {
      navigate('/login');
      return;
    }
    try {
      await api.post('/seeker/apply', { jobId: Number(id), coverLetter });
      setMessage('Application submitted. Track it on your dashboard.');
    } catch (err) {
      setError(err.response?.data?.message || 'Could not apply');
    }
  };

  if (!job) return <p>Loading...</p>;

  return (
    <article className="panel stack">
      <h2>{job.title}</h2>
      <p className="muted">{job.companyName} · {job.location} · {job.jobType}</p>
      <p><span className="badge">{job.status}</span> Experience: {job.experienceYears ?? 0} years</p>
      <p>Salary: {job.salaryMin ?? '-'} to {job.salaryMax ?? '-'}</p>
      <p><b>Skills:</b> {job.skills}</p>
      <p>{job.description}</p>
      {user?.role === 'JOB_SEEKER' && job.status === 'OPEN' && (
        <form className="stack" onSubmit={apply}>
          <label>Cover letter<textarea value={coverLetter} onChange={(e) => setCoverLetter(e.target.value)} /></label>
          {error && <div className="alert">{error}</div>}
          {message && <div className="success">{message}</div>}
          <button className="btn" type="submit">Apply now</button>
        </form>
      )}
      {!user && <Link className="btn" to="/login">Login to apply</Link>}
    </article>
  );
}
