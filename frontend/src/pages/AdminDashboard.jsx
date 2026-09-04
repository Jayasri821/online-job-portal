import { useEffect, useState } from 'react';
import api from '../api';

export default function AdminDashboard() {
  const [tab, setTab] = useState('stats');
  const [stats, setStats] = useState(null);
  const [users, setUsers] = useState([]);
  const [companies, setCompanies] = useState([]);
  const [jobs, setJobs] = useState([]);
  const [applications, setApplications] = useState([]);

  const load = async () => {
    const [s, u, c, j, a] = await Promise.all([
      api.get('/admin/stats'),
      api.get('/admin/users'),
      api.get('/admin/companies'),
      api.get('/admin/jobs'),
      api.get('/admin/applications')
    ]);
    setStats(s.data);
    setUsers(u.data);
    setCompanies(c.data);
    setJobs(j.data);
    setApplications(a.data);
  };

  useEffect(() => { load(); }, []);

  const toggleUser = async (user) => {
    await api.put(`/admin/users/${user.id}/enabled`, { enabled: !user.enabled });
    load();
  };

  const setJobStatus = async (job, status) => {
    await api.put(`/admin/jobs/${job.id}/status`, { status });
    load();
  };

  return (
    <div className="layout">
      <aside className="side">
        <button className={tab === 'stats' ? 'btn active' : 'btn'} onClick={() => setTab('stats')}>Statistics</button>
        <button className={tab === 'users' ? 'btn active' : 'btn'} onClick={() => setTab('users')}>Users</button>
        <button className={tab === 'companies' ? 'btn active' : 'btn'} onClick={() => setTab('companies')}>Companies</button>
        <button className={tab === 'jobs' ? 'btn active' : 'btn'} onClick={() => setTab('jobs')}>Jobs</button>
        <button className={tab === 'apps' ? 'btn active' : 'btn'} onClick={() => setTab('apps')}>Applications</button>
      </aside>
      <section className="panel">
        {tab === 'stats' && stats && (
          <>
            <h2>Placement statistics</h2>
            <div className="stats">
              <div className="stat"><span className="muted">Users</span><b>{stats.totalUsers}</b></div>
              <div className="stat"><span className="muted">Seekers</span><b>{stats.jobSeekers}</b></div>
              <div className="stat"><span className="muted">Recruiters</span><b>{stats.recruiters}</b></div>
              <div className="stat"><span className="muted">Companies</span><b>{stats.companies}</b></div>
              <div className="stat"><span className="muted">Open jobs</span><b>{stats.openJobs}</b></div>
              <div className="stat"><span className="muted">Applications</span><b>{stats.totalApplications}</b></div>
              <div className="stat"><span className="muted">Shortlisted</span><b>{stats.shortlisted}</b></div>
              <div className="stat"><span className="muted">Interviews</span><b>{stats.interviews}</b></div>
              <div className="stat"><span className="muted">Rejected</span><b>{stats.rejected}</b></div>
            </div>
          </>
        )}
        {tab === 'users' && (
          <>
            <h2>Users</h2>
            <table>
              <thead><tr><th>Name</th><th>Email</th><th>Role</th><th>Status</th><th></th></tr></thead>
              <tbody>
                {users.map((u) => (
                  <tr key={u.id}>
                    <td>{u.fullName}</td>
                    <td>{u.email}</td>
                    <td>{u.role}</td>
                    <td>{u.enabled ? 'Active' : 'Disabled'}</td>
                    <td>
                      {u.role !== 'ADMIN' && (
                        <button className={u.enabled ? 'btn warn' : 'btn ok'} onClick={() => toggleUser(u)}>
                          {u.enabled ? 'Disable' : 'Enable'}
                        </button>
                      )}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </>
        )}
        {tab === 'companies' && (
          <>
            <h2>Companies</h2>
            <table>
              <thead><tr><th>Name</th><th>Industry</th><th>Location</th><th>Recruiter</th></tr></thead>
              <tbody>
                {companies.map((c) => (
                  <tr key={c.id}>
                    <td>{c.name}</td>
                    <td>{c.industry}</td>
                    <td>{c.location}</td>
                    <td>{c.recruiterName}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </>
        )}
        {tab === 'jobs' && (
          <>
            <h2>Jobs</h2>
            {jobs.map((job) => (
              <article className="card row" key={job.id} style={{ justifyContent: 'space-between', alignItems: 'center' }}>
                <div>
                  <b>{job.title}</b>
                  <p className="muted">{job.companyName} · {job.location}</p>
                  <span className={`badge ${job.status}`}>{job.status}</span>
                </div>
                <div className="row">
                  <button className="btn ok" onClick={() => setJobStatus(job, 'OPEN')}>Open</button>
                  <button className="btn warn" onClick={() => setJobStatus(job, 'CLOSED')}>Close</button>
                  <button className="btn bad" onClick={() => setJobStatus(job, 'DISABLED')}>Disable</button>
                </div>
              </article>
            ))}
          </>
        )}
        {tab === 'apps' && (
          <>
            <h2>Applications</h2>
            <table>
              <thead><tr><th>Candidate</th><th>Job</th><th>Company</th><th>Status</th></tr></thead>
              <tbody>
                {applications.map((a) => (
                  <tr key={a.id}>
                    <td>{a.seekerName}</td>
                    <td>{a.jobTitle}</td>
                    <td>{a.companyName}</td>
                    <td><span className={`badge ${a.status}`}>{a.status}</span></td>
                  </tr>
                ))}
              </tbody>
            </table>
          </>
        )}
      </section>
    </div>
  );
}
