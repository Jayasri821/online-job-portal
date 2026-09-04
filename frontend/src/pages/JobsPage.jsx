import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import api from '../api';
import { demoJobs } from '../demoJobs';

export default function JobsPage() {
  const [jobs, setJobs] = useState([]);
  const [filters, setFilters] = useState({ keyword: '', location: '', jobType: '' });

  const load = async (next = filters) => {
    try {
      const { data } = await api.get('/jobs', { params: next });
      setJobs(data);
    } catch {
      const keyword = next.keyword.trim().toLowerCase();
      const location = next.location.trim().toLowerCase();
      setJobs(demoJobs.filter((job) => (
        (!keyword || `${job.title} ${job.skills}`.toLowerCase().includes(keyword))
        && (!location || job.location.toLowerCase().includes(location))
        && (!next.jobType || job.jobType === next.jobType)
      )));
    }
  };

  useEffect(() => { load(); }, []);

  return (
    <>
      <h2>Open jobs</h2>
      <form className="panel row" onSubmit={(e) => { e.preventDefault(); load(filters); }}>
        <label style={{ flex: 1 }}>Keyword<input value={filters.keyword} onChange={(e) => setFilters({ ...filters, keyword: e.target.value })} placeholder="Java, React..." /></label>
        <label style={{ flex: 1 }}>Location<input value={filters.location} onChange={(e) => setFilters({ ...filters, location: e.target.value })} placeholder="Bengaluru" /></label>
        <label>Type
          <select value={filters.jobType} onChange={(e) => setFilters({ ...filters, jobType: e.target.value })}>
            <option value="">Any</option>
            <option value="FULL_TIME">Full time</option>
            <option value="PART_TIME">Part time</option>
            <option value="INTERNSHIP">Internship</option>
            <option value="CONTRACT">Contract</option>
            <option value="REMOTE">Remote</option>
          </select>
        </label>
        <button className="btn" type="submit">Search</button>
      </form>
      <div className="stack" style={{ marginTop: 16 }}>
        {jobs.map((job) => (
          <article className="card job-card" key={job.id}>
            <h3>{job.title}</h3>
            <p className="muted">{job.companyName} · {job.location} · {job.jobType}</p>
            <p>{job.skills}</p>
            <Link className="btn" to={`/jobs/${job.id}`}>View details</Link>
          </article>
        ))}
        {jobs.length === 0 && <p className="muted">No jobs match your filters.</p>}
      </div>
    </>
  );
}
