import { Link } from 'react-router-dom';

export default function HomePage() {
  return (
    <>
      <section className="hero">
        <h1>Find work. Hire talent. Track every application.</h1>
        <p>
          HireHub is a working job portal with three roles: Job Seeker, Recruiter and Admin.
          Search jobs, apply with a resume, shortlist candidates and view placement stats.
        </p>
        <div className="row">
          <Link className="btn" to="/jobs">Browse jobs</Link>
          <Link className="btn ghost" to="/register">Create account</Link>
        </div>
      </section>
      <div className="grid">
        <article className="card">
          <h3>Job Seekers</h3>
          <p className="muted">Build a profile, upload a resume, filter jobs and track application status.</p>
        </article>
        <article className="card">
          <h3>Recruiters</h3>
          <p className="muted">Create a company, post jobs, shortlist or reject applicants and schedule interviews.</p>
        </article>
        <article className="card">
          <h3>Admin</h3>
          <p className="muted">Manage users, companies, jobs and applications, then view placement statistics.</p>
        </article>
      </div>
    </>
  );
}
