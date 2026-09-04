import { useEffect, useState } from 'react';
import api, { API_HOST } from '../api';

const emptyProfile = {
  fullName: '',
  phone: '',
  location: '',
  education: '',
  experience: '',
  skills: '',
  summary: '',
  resumeFileName: ''
};

export default function SeekerDashboard() {
  const [tab, setTab] = useState('profile');
  const [profile, setProfile] = useState(emptyProfile);
  const [applications, setApplications] = useState([]);
  const [file, setFile] = useState(null);
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');

  const load = async () => {
    const [p, a] = await Promise.all([
      api.get('/seeker/profile'),
      api.get('/seeker/applications')
    ]);
    setProfile({ ...emptyProfile, ...p.data });
    setApplications(a.data);
  };

  useEffect(() => { load(); }, []);

  const saveProfile = async (e) => {
    e.preventDefault();
    setError('');
    try {
      const { data } = await api.put('/seeker/profile', profile);
      setProfile({ ...emptyProfile, ...data });
      setMessage('Profile saved');
    } catch (err) {
      setError(err.response?.data?.message || 'Save failed');
    }
  };

  const upload = async (e) => {
    e.preventDefault();
    if (!file) return;
    const form = new FormData();
    form.append('file', file);
    try {
      const { data } = await api.post('/seeker/resume', form);
      setProfile({ ...emptyProfile, ...data });
      setMessage('Resume uploaded');
    } catch (err) {
      setError(err.response?.data?.message || 'Upload failed');
    }
  };

  return (
    <div className="layout">
      <aside className="side">
        <button className={tab === 'profile' ? 'btn active' : 'btn'} onClick={() => setTab('profile')}>My profile</button>
        <button className={tab === 'resume' ? 'btn active' : 'btn'} onClick={() => setTab('resume')}>Resume</button>
        <button className={tab === 'apps' ? 'btn active' : 'btn'} onClick={() => setTab('apps')}>Applications</button>
      </aside>
      <section className="panel">
        {error && <div className="alert">{error}</div>}
        {message && <div className="success">{message}</div>}
        {tab === 'profile' && (
          <form className="stack" onSubmit={saveProfile}>
            <h2>Job seeker profile</h2>
            <label>Full name<input value={profile.fullName} onChange={(e) => setProfile({ ...profile, fullName: e.target.value })} /></label>
            <label>Phone<input value={profile.phone} onChange={(e) => setProfile({ ...profile, phone: e.target.value })} /></label>
            <label>Location<input value={profile.location} onChange={(e) => setProfile({ ...profile, location: e.target.value })} /></label>
            <label>Education<input value={profile.education} onChange={(e) => setProfile({ ...profile, education: e.target.value })} /></label>
            <label>Experience<input value={profile.experience} onChange={(e) => setProfile({ ...profile, experience: e.target.value })} /></label>
            <label>Skills<input value={profile.skills} onChange={(e) => setProfile({ ...profile, skills: e.target.value })} /></label>
            <label>Summary<textarea value={profile.summary} onChange={(e) => setProfile({ ...profile, summary: e.target.value })} /></label>
            <button className="btn" type="submit">Save profile</button>
          </form>
        )}
        {tab === 'resume' && (
          <form className="stack" onSubmit={upload}>
            <h2>Resume upload</h2>
            <p className="muted">PDF, DOC or DOCX. You must upload a resume before applying.</p>
            {profile.resumeFileName && (
              <a href={`${API_HOST}/uploads/resumes/${profile.resumeFileName}`} target="_blank" rel="noreferrer">
                Current resume: {profile.resumeFileName}
              </a>
            )}
            <input type="file" accept=".pdf,.doc,.docx" onChange={(e) => setFile(e.target.files[0])} />
            <button className="btn" type="submit">Upload</button>
          </form>
        )}
        {tab === 'apps' && (
          <>
            <h2>Application tracker</h2>
            <table>
              <thead>
                <tr><th>Job</th><th>Company</th><th>Status</th><th>Applied</th><th>Interview</th></tr>
              </thead>
              <tbody>
                {applications.map((app) => (
                  <tr key={app.id}>
                    <td>{app.jobTitle}</td>
                    <td>{app.companyName}</td>
                    <td><span className={`badge ${app.status}`}>{app.status}</span></td>
                    <td>{app.appliedAt}</td>
                    <td>{app.interviewTime ? `${app.interviewTime} (${app.interviewMode})` : '-'}</td>
                  </tr>
                ))}
              </tbody>
            </table>
            {applications.length === 0 && <p className="muted">You have not applied yet.</p>}
          </>
        )}
      </section>
    </div>
  );
}
