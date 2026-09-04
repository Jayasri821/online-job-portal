// CareerMatch AI - UI Helpers, Toast Notifications & Formatters

const UI = {
  showToast(message, type = 'success') {
    let container = document.getElementById('toast-container');
    if (!container) {
      container = document.createElement('div');
      container.id = 'toast-container';
      document.body.appendChild(container);
    }

    const toast = document.createElement('div');
    const bgClass = type === 'success' ? 'bg-success text-white' : (type === 'error' ? 'bg-danger text-white' : 'bg-primary text-white');
    const icon = type === 'success' ? 'fa-check-circle' : (type === 'error' ? 'fa-exclamation-circle' : 'fa-info-circle');

    toast.className = `toast align-items-center ${bgClass} border-0 show shadow-lg mb-2`;
    toast.role = 'alert';
    toast.innerHTML = `
      <div class="d-flex">
        <div class="toast-body d-flex align-items-center gap-2">
          <i class="fas ${icon} fs-5"></i>
          <span>${message}</span>
        </div>
        <button type="button" class="btn-close btn-close-white me-2 m-auto" onclick="this.parentElement.parentElement.remove()"></button>
      </div>
    `;

    container.appendChild(toast);
    setTimeout(() => {
      if (toast.parentElement) toast.remove();
    }, 4500);
  },

  formatSalary(amount) {
    if (!amount) return 'Competitive';
    if (amount >= 100000) {
      const lpa = (amount / 100000).toFixed(1);
      return `₹${lpa.endsWith('.0') ? lpa.slice(0, -2) : lpa} LPA`;
    }
    return `₹${amount.toLocaleString('en-IN')}`;
  },

  formatSalaryRange(min, max) {
    if (!min && !max) return 'Best in Industry';
    if (min && max) return `${this.formatSalary(min)} – ${this.formatSalary(max)}`;
    if (min) return `From ${this.formatSalary(min)}`;
    return `Up to ${this.formatSalary(max)}`;
  },

  formatDate(dateStr) {
    if (!dateStr) return 'N/A';
    const d = new Date(dateStr);
    return d.toLocaleDateString('en-US', { month: 'short', day: 'numeric', year: 'numeric' });
  },

  getMatchBadge(score) {
    if (score == null) return '<span class="badge bg-secondary-subtle text-secondary">Match N/A</span>';
    let badgeClass = 'match-low';
    let icon = 'fa-chart-pie';
    if (score >= 80) {
      badgeClass = 'match-high';
      icon = 'fa-bolt';
    } else if (score >= 60) {
      badgeClass = 'match-mid';
      icon = 'fa-check-circle';
    }
    return `
      <span class="match-badge ${badgeClass}">
        <i class="fas ${icon}"></i> ${score}% Match
      </span>
    `;
  },

  getDeadlineBadge(daysLeft, isClosingSoon) {
    if (daysLeft == null) return '';
    if (daysLeft < 0) {
      return '<span class="badge bg-secondary">Closed</span>';
    }
    if (daysLeft === 0) {
      return '<span class="badge bg-danger animate-pulse"><i class="fas fa-hourglass-end me-1"></i> Closes Today</span>';
    }
    if (isClosingSoon || daysLeft <= 3) {
      return `<span class="badge bg-danger-subtle text-danger border border-danger-subtle"><i class="fas fa-exclamation-triangle me-1"></i> Closes in ${daysLeft} day${daysLeft > 1 ? 's' : ''}</span>`;
    }
    return `<span class="badge bg-light text-muted border"><i class="far fa-clock me-1"></i> ${daysLeft} days left</span>`;
  },

  showSpinner(containerId, text = 'Loading data...') {
    const el = document.getElementById(containerId);
    if (!el) return;
    el.innerHTML = `
      <div class="text-center py-5">
        <div class="spinner-border text-primary" role="status">
          <span class="visually-hidden">Loading...</span>
        </div>
        <p class="text-muted mt-2 small">${text}</p>
      </div>
    `;
  }
};
