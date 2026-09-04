import { useEffect, useState } from 'react';
import api from '../api';

const emptyCompany = { name: '', website: '', location: '', industry: '', description: '' };
const emptyJob = {
  title: '',
  description: '',
  location: '',
  skills: '',
  experienceYears: 0,
  salaryMin: 0,
  salaryMax: 0,
  jobType: 'FULL_TIME'
};

export default function RecruiterDashboard() {
  const [tab, setTab] = useState('company');
  const [company, setCompany] = useState(emptyCompany);
  const [jobs, setJobs] = useState([]);
  const [jobForm, setJobForm] = useState(emptyJob);
  const [editingId, setEditingId] = useState(null);
  const [selectedJob, setSelectedJob] = useState(null);
  const [applicants, setApplicants] = useState([]);
  const [interview, setInterview] = useState({ scheduledAt: '', mode: 'Online', meetingLink: '', notes: '' });
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');

  const loadCompany = async () => {
    const { data } = await api.get('/recruiter/company');
    if (data) setCompany(data);
  };

  const loadJobs = async () => {
    const { data } = await api.get('/recruiter/jobs');
    setJobs(data);
  };

  useEffect(() => {
    loadCompany().catch(() => {});
    loadJobs().catch(() => {});
  }, []);

  const saveCompany = async (e) => {
    e.preventDefault();
    setError('');
    try {
      const { data } = await api.put('/recruiter/company', company);
      setCompany(data);
      setMessage('Company profile saved');
    } catch (err) {
      setError(err.response?.data?.message || 'Could not save company');
    }
  };

  const saveJob = async (e) => {
    e.preventDefault();
    setError('');
    try {
      if (editingId) {
        await api.put(`/recruiter/jobs/${editingId}`, jobForm);
      } else {
        await api.post('/recruiter/jobs', jobForm);
      }
      setJobForm(emptyJob);
      setEditingId(null);
      setMessage('Job saved');
      loadJobs();
    } catch (err) {
      setError(err.response?.data?.message || 'Create a company profile first, then post jobs');
    }
  };

  const openApplicants = async (job) => {
    setSelectedJob(job);
    setTab('applicants');
    const { data } = await api.get(`/recruiter/jobs/${job.id}/applications`);
    setApplicants(data);
  };

  const setStatus = async (id, status) => {
    await api.put(`/recruiter/applications/${id}/status`, { status });
    openApplicants(selectedJob);
  };

  const schedule = async (id) => {
    const scheduledAt = interview.scheduledAt.length === 16
      ? `${interview.scheduledAt}:00`
      : interview.scheduledAt;
    await api.post(`/recruiter/applications/${id}/interview`, {
      ...interview,
      scheduledAt
    });
    setMessage('Interview scheduled');
    openApplicants(selectedJob);
  };

  return (
    <div className="layout">
      <aside className="side">
        <button className={tab === 'company' ? 'btn active' : 'btn'} onClick={() => setTab('company')}>Company</button>
        <button className={tab === 'jobs' ? 'btn active' : 'btn'} onClick={() => setTab('jobs')}>Jobs</button>
        <button className={tab === 'applicants' ? 'btn active' : 'btn'} onClick={() => setTab('applicants')}>Applicants</button>
      </aside>
      <section className="panel">
        {error && <div className="alert">{error}</div>}
        {message && <div className="success">{message}</div>}

        {tab === 'company' && (
          <form className="stack" onSubmit={saveCompany}>
            <h2>Company profile</h2>
            <label>Name<input value={company.name || ''} onChange={(e) => setCompany({ ...company, name: e.target.value })} required /></label>
            <label>Website<input value={company.website || ''} onChange={(e) => setCompany({ ...company, website: e.target.value })} /></label>
            <label>Location<input value={company.location || ''} onChange={(e) => setCompany({ ...company, location: e.target.value })} /></label>
            <label>Industry<input value={company.industry || ''} onChange={(e) => setCompany({ ...company, industry: e.target.value })} /></label>
            <label>Description<textarea value={company.description || ''} onChange={(e) => setCompany({ ...company, description: e.target.value })} /></label>
            <button className="btn" type="submit">Save company</button>
          </form>
        )}

        {tab === 'jobs' && (
          <>
            <h2>{editingId ? 'Edit job' : 'Post a job'}</h2>
            <form className="stack" onSubmit={saveJob}>
              <label>Title<input value={jobForm.title} onChange={(e) => setJobForm({ ...jobForm, title: e.target.value })} required /></label>
              <label>Description<textarea value={jobForm.description} onChange={(e) => setJobForm({ ...jobForm, description: e.target.value })} required /></label>
              <div className="row">
                <label style={{ flex: 1 }}>Location<input value={jobForm.location} onChange={(e) => setJobForm({ ...jobForm, location: e.target.value })} /></label>
                <label style={{ flex: 1 }}>Skills<input value={jobForm.skills} onChange={(e) => setJobForm({ ...jobForm, skills: e.target.value })} /></label>
              </div>
              <div className="row">
                <label>Experience<input type="number" value={jobForm.experienceYears} onChange={(e) => setJobForm({ ...jobForm, experienceYears: Number(e.target.value) })} /></label>
                <label>Min salary<input type="number" value={jobForm.salaryMin} onChange={(e) => setJobForm({ ...jobForm, salaryMin: Number(e.target.value) })} /></label>
                <label>Max salary<input type="number" value={jobForm.salaryMax} onChange={(e) => setJobForm({ ...jobForm, salaryMax: Number(e.target.value) })} /></label>
                <label>Type
                  <select value={jobForm.jobType} onChange={(e) => setJobForm({ ...jobForm, jobType: e.target.value })}>
                    <option value="FULL_TIME">Full time</option>
                    <option value="PART_TIME">Part time</option>
                    <option value="INTERNSHIP">Internship</option>
                    <option value="CONTRACT">Contract</option>
                    <option value="REMOTE">Remote</option>
                  </select>
                </label>
              </div>
              <button className="btn" type="submit">{editingId ? 'Update job' : 'Post job'}</button>
            </form>
            <h3>My jobs</h3>
            {jobs.map((job) => (
              <article className="card" key={job.id}>
                <h3>{job.title} <span className={`badge ${job.status}`}>{job.status}</span></h3>
                <p className="muted">{job.location} · {job.jobType}</p>
                <div className="row">
                  <button className="btn" onClick={() => { setEditingId(job.id); setJobForm(job); }}>Edit</button>
                  <button className="btn" onClick={() => openApplicants(job)}>View applicants</button>
                  {job.status === 'OPEN' && (
                    <button className="btn warn" onClick={async () => { await api.put(`/recruiter/jobs/${job.id}/close`); loadJobs(); }}>Close</button>
                  )}
                </div>
              </article>
            ))}
          </>
        )}

        {tab === 'applicants' && (
          <>
            <h2>Applicants {selectedJob ? `for ${selectedJob.title}` : ''}</h2>
            {!selectedJob && <p className="muted">Open a job from the Jobs tab to see applicants.</p>}
            {applicants.map((app) => (
              <article className="card stack" key={app.id}>
                <p><b>{app.seekerName}</b> · {app.seekerEmail}</p>
                <p className="muted">{app.coverLetter}</p>
                <p>Status: <span className={`badge ${app.status}`}>{app.status}</span></p>
                <div className="row">
                  <button className="btn ok" onClick={() => setStatus(app.id, 'SHORTLISTED')}>Shortlist</button>
                  <button className="btn bad" onClick={() => setStatus(app.id, 'REJECTED')}>Reject</button>
                </div>
                <div className="row">
                  <label>Interview time<input type="datetime-local" value={interview.scheduledAt} onChange={(e) => setInterview({ ...interview, scheduledAt: e.target.value })} /></label>
                  <label>Mode<input value={interview.mode} onChange={(e) => setInterview({ ...interview, mode: e.target.value })} /></label>
                  <label>Link<input value={interview.meetingLink} onChange={(e) => setInterview({ ...interview, meetingLink: e.target.value })} /></label>
                </div>
                <button className="btn" onClick={() => schedule(app.id)}>Schedule interview</button>
              </article>
            ))}
          </>
        )}
      </section>
    </div>
  );
}
