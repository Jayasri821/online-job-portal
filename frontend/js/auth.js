// CareerMatch AI - Auth & Session Manager

const Auth = {
  getUser() {
    const userStr = localStorage.getItem('careermatch_user');
    if (!userStr) return null;
    try {
      return JSON.parse(userStr);
    } catch {
      return null;
    }
  },

  getToken() {
    return localStorage.getItem('careermatch_token');
  },

  isLoggedIn() {
    return !!this.getToken() && !!this.getUser();
  },

  getRole() {
    const user = this.getUser();
    return user ? user.role : null;
  },

  setSession(authResponse) {
    localStorage.setItem('careermatch_token', authResponse.token);
    const user = {
      id: authResponse.id,
      email: authResponse.email,
      fullName: authResponse.fullName,
      role: authResponse.role,
      profileId: authResponse.profileId,
      companyName: authResponse.companyName
    };
    localStorage.setItem('careermatch_user', JSON.stringify(user));
  },

  logout() {
    localStorage.removeItem('careermatch_token');
    localStorage.removeItem('careermatch_user');
    window.location.href = 'login.html';
  },

  requireAuth(allowedRoles = []) {
    if (!this.isLoggedIn()) {
      window.location.href = 'login.html?redirect=' + encodeURIComponent(window.location.pathname);
      return false;
    }

    if (allowedRoles.length > 0) {
      const userRole = this.getRole();
      if (!allowedRoles.includes(userRole)) {
        alert('Access denied. You do not have permission to view this page.');
        this.redirectToDashboard();
        return false;
      }
    }
    return true;
  },

  redirectToDashboard() {
    const role = this.getRole();
    if (role === 'JOB_SEEKER') {
      window.location.href = 'dashboard.html';
    } else if (role === 'EMPLOYER') {
      window.location.href = 'employer-dashboard.html';
    } else if (role === 'ADMIN') {
      window.location.href = 'admin-dashboard.html';
    } else {
      window.location.href = 'index.html';
    }
  },

  renderNavbar() {
    const navPlaceholder = document.getElementById('navbar-container');
    if (!navPlaceholder) return;

    const loggedIn = this.isLoggedIn();
    const user = this.getUser();
    const role = user ? user.role : null;

    let roleNavLinks = '';
    if (loggedIn) {
      if (role === 'JOB_SEEKER') {
        roleNavLinks = `
          <li class="nav-item"><a class="nav-link" href="dashboard.html"><i class="fas fa-chart-line me-1"></i> Dashboard</a></li>
          <li class="nav-item"><a class="nav-link" href="jobs.html"><i class="fas fa-briefcase me-1"></i> Jobs</a></li>
          <li class="nav-item"><a class="nav-link" href="interview.html"><i class="fas fa-graduation-cap me-1 text-warning"></i> Interview & MCQ Kit</a></li>
          <li class="nav-item"><a class="nav-link" href="applications.html"><i class="fas fa-tasks me-1"></i> Applications</a></li>
          <li class="nav-item"><a class="nav-link" href="companies.html"><i class="fas fa-columns me-1"></i> Compare</a></li>
          <li class="nav-item"><a class="nav-link" href="profile.html"><i class="fas fa-user-circle me-1"></i> Profile</a></li>
          <li class="nav-item"><a class="nav-link" href="resume.html"><i class="fas fa-file-alt me-1"></i> Resume</a></li>
        `;
      } else if (role === 'EMPLOYER') {
        roleNavLinks = `
          <li class="nav-item"><a class="nav-link" href="employer-dashboard.html"><i class="fas fa-building me-1"></i> Recruiter Hub</a></li>
          <li class="nav-item"><a class="nav-link" href="post-job.html"><i class="fas fa-plus-circle me-1"></i> Post Job</a></li>
          <li class="nav-item"><a class="nav-link" href="jobs.html"><i class="fas fa-search me-1"></i> View Jobs</a></li>
          <li class="nav-item"><a class="nav-link" href="interview.html"><i class="fas fa-graduation-cap me-1 text-warning"></i> Interview Kit</a></li>
        `;
      } else if (role === 'ADMIN') {
        roleNavLinks = `
          <li class="nav-item"><a class="nav-link" href="admin-dashboard.html"><i class="fas fa-user-shield me-1"></i> Admin Panel</a></li>
          <li class="nav-item"><a class="nav-link" href="jobs.html"><i class="fas fa-briefcase me-1"></i> All Jobs</a></li>
          <li class="nav-item"><a class="nav-link" href="interview.html"><i class="fas fa-graduation-cap me-1 text-warning"></i> Interview Kit</a></li>
        `;
      }
    } else {
      roleNavLinks = `
        <li class="nav-item"><a class="nav-link" href="jobs.html"><i class="fas fa-briefcase me-1"></i> Browse Jobs</a></li>
        <li class="nav-item"><a class="nav-link" href="interview.html"><i class="fas fa-graduation-cap me-1 text-warning"></i> Interview & MCQ Kit</a></li>
        <li class="nav-item"><a class="nav-link" href="companies.html"><i class="fas fa-columns me-1"></i> Compare Jobs</a></li>
      `;
    }

    let authButtons = '';
    if (loggedIn) {
      authButtons = `
        <div class="d-flex align-items-center gap-2">
          <span class="badge bg-primary-subtle text-primary border border-primary-subtle px-3 py-2 rounded-pill">
            <i class="fas ${role === 'JOB_SEEKER' ? 'fa-user-graduate' : (role === 'EMPLOYER' ? 'fa-building' : 'fa-shield-alt')} me-1"></i>
            ${(user.fullName || user.email || 'User').split(' ')[0]} (${(role || '').replace('_', ' ')})
          </span>
          <button class="btn btn-outline-danger btn-sm rounded-pill px-3" onclick="Auth.logout()">
            <i class="fas fa-sign-out-alt me-1"></i> Logout
          </button>
        </div>
      `;
    } else {
      authButtons = `
        <div class="d-flex align-items-center gap-2">
          <a href="login.html" class="btn btn-outline-primary btn-sm rounded-pill px-3">Sign In</a>
          <a href="register.html" class="btn btn-primary btn-sm rounded-pill px-3 shadow-sm">Get Started</a>
        </div>
      `;
    }

    navPlaceholder.innerHTML = `
      <nav class="navbar navbar-expand-lg navbar-custom sticky-top">
        <div class="container">
          <a class="navbar-brand brand-logo" href="index.html">
            <i class="fas fa-brain text-primary"></i>
            <span>CareerMatch</span>
            <span class="brand-badge">AI</span>
          </a>
          <button class="navbar-toggler border-0" type="button" data-bs-toggle="collapse" data-bs-target="#mainNavbar">
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse" id="mainNavbar">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0 ms-lg-4">
              ${roleNavLinks}
            </ul>
            ${authButtons}
          </div>
        </div>
      </nav>
    `;

    // Highlight active link
    const currentFile = window.location.pathname.split('/').pop() || 'index.html';
    const activeLink = document.querySelector(`#mainNavbar a[href="${currentFile}"]`);
    if (activeLink) {
      activeLink.classList.add('active', 'fw-bold', 'text-primary');
    }
  }
};

// Automatically render navbar on page load
document.addEventListener('DOMContentLoaded', () => {
  Auth.renderNavbar();
});
