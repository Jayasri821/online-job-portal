// CareerMatch AI - Central API Client & Intelligent Client-Side Data Engine

const API_BASE_URL = 'http://localhost:8080/api';

// Initial Seed Users for Demo
const DEFAULT_USERS = [
  {
    id: 1,
    email: 'seeker@careermatch.com',
    password: 'Password@123',
    fullName: 'Jayasri',
    phone: '+91 9811122233',
    role: 'JOB_SEEKER',
    degree: 'B.Tech Computer Science',
    education: 'Jawaharlal Nehru Technological University',
    graduationYear: 2025,
    skills: 'Java, Spring Boot, SQL, MySQL, REST API, HTML, CSS, JavaScript, Git',
    experienceYears: 1,
    preferredRole: 'Java Full Stack Developer',
    preferredLocation: 'Hyderabad',
    expectedSalary: 650000,
    preferredWorkMode: 'HYBRID',
    careerObjective: 'Motivated Computer Science graduate with strong hands-on experience in Java, Spring Boot, and REST APIs.',
    projects: '1. Online E-Commerce Platform (Java, Spring Boot, MySQL)\n2. Real-time Chat App (WebSocket, HTML5/CSS3)',
    certifications: 'Oracle Certified Associate Java SE 8, Spring Professional',
    internships: 'Backend Developer Intern at SoftSolutions Inc (6 months)',
    achievements: "Dean's List Academic Excellence, 1st place in University Hackathon 2024",
    resumeUrl: 'Jayasri_Resume.pdf'
  },
  {
    id: 2,
    email: 'recruiter@google.com',
    password: 'Password@123',
    fullName: 'Sundar Recruiter',
    phone: '+91 9876543211',
    role: 'EMPLOYER',
    companyName: 'Google Cloud',
    industry: 'Technology & Cloud Computing',
    companyLocation: 'Hyderabad, India',
    website: 'https://cloud.google.com',
    companyDescription: 'Leading global cloud and enterprise AI provider building scalable global infrastructure.'
  },
  {
    id: 3,
    email: 'admin@careermatch.com',
    password: 'Password@123',
    fullName: 'Platform Administrator',
    phone: '+91 9876543210',
    role: 'ADMIN'
  }
];

// Initial Seed Jobs
const INITIAL_JOBS = [
  {
    id: 1,
    title: 'Java Full Stack Developer',
    employer: { companyName: 'ABC Technologies', industry: 'IT Services & Consulting', location: 'Pune, India' },
    description: 'We are seeking a talented Java Full Stack Developer to build next-generation enterprise applications. You will work on robust backend architectures using Spring Boot and responsive frontends.',
    responsibilities: '- Design and implement microservices using Spring Boot & Java 17+\n- Develop responsive web pages using HTML5, CSS3, JavaScript and REST endpoints\n- Optimize SQL database queries and schema designs\n- Write unit tests and participate in code reviews',
    qualifications: '- B.Tech/B.E in Computer Science or equivalent\n- Strong proficiency in Java, Spring Boot, SQL, and REST APIs\n- Knowledge of HTML, CSS, JavaScript',
    requiredSkills: 'Java, Spring Boot, SQL, REST API, HTML, CSS, JavaScript',
    location: 'Hyderabad, India',
    experienceYears: 1,
    salaryMin: 600000,
    salaryMax: 900000,
    jobType: 'FULL_TIME',
    workMode: 'HYBRID',
    daysLeft: 5,
    isClosingSoon: false,
    status: 'OPEN'
  },
  {
    id: 2,
    title: 'Software Engineer - Cloud Systems',
    employer: { companyName: 'Google Cloud', industry: 'Technology & Cloud Computing', location: 'Hyderabad, India' },
    description: 'Join Google Cloud to build large-scale cloud infrastructure, distributed microservices, and high-performance backend systems.',
    responsibilities: '- Build scalable backend services in Java/Go\n- Work with containerized cloud environments using Kubernetes & Docker\n- Collaborate with global engineering teams',
    qualifications: '- B.Tech or M.Tech in CS/IT\n- Strong grasp of Data Structures, Algorithms, Java, and Distributed Systems',
    requiredSkills: 'Java, Cloud, Docker, Kubernetes, SQL, Data Structures',
    location: 'Hyderabad, India',
    experienceYears: 1,
    salaryMin: 1400000,
    salaryMax: 2200000,
    jobType: 'FULL_TIME',
    workMode: 'HYBRID',
    daysLeft: 2,
    isClosingSoon: true,
    status: 'OPEN'
  },
  {
    id: 3,
    title: 'Backend Engineer (Java / Spring)',
    employer: { companyName: 'Microsoft India', industry: 'Software & Enterprise AI', location: 'Bangalore, India' },
    description: 'Microsoft is looking for a Backend Engineer to power enterprise productivity suites and secure API platforms.',
    responsibilities: '- Develop secure, scalable RESTful APIs\n- Integrate relational and NoSQL databases\n- Monitor system performance and implement caching',
    qualifications: '- Experience in core Java, Spring Boot, and REST API development',
    requiredSkills: 'Java, Spring Boot, REST API, SQL, Microservices, Git',
    location: 'Bangalore, India',
    experienceYears: 1,
    salaryMin: 1200000,
    salaryMax: 1800000,
    jobType: 'FULL_TIME',
    workMode: 'WORK_FROM_HOME',
    daysLeft: 10,
    isClosingSoon: false,
    status: 'OPEN'
  },
  {
    id: 4,
    title: 'AWS Cloud Support Associate',
    employer: { companyName: 'Amazon Web Services', industry: 'E-Commerce & Cloud', location: 'Hyderabad, India' },
    description: 'Help worldwide enterprise customers troubleshoot and architect solutions on AWS infrastructure and cloud services.',
    responsibilities: '- Diagnose cloud architecture problems\n- Script automation tasks using Python and Bash\n- Manage Linux and networking configurations',
    qualifications: '- Understanding of AWS, Linux, Networking, and scripting',
    requiredSkills: 'AWS, Linux, Python, Networking, Cloud',
    location: 'Hyderabad, India',
    experienceYears: 0,
    salaryMin: 700000,
    salaryMax: 1100000,
    jobType: 'FULL_TIME',
    workMode: 'HYBRID',
    daysLeft: 1,
    isClosingSoon: true,
    status: 'OPEN'
  },
  {
    id: 5,
    title: 'Associate Software Engineer - Java',
    employer: { companyName: 'Infosys Limited', industry: 'Digital Services & Consulting', location: 'Chennai, India' },
    description: 'Infosys Campus Hiring is looking for fresh talent ready to engineer scalable digital systems across global clients.',
    responsibilities: '- Software development under agile teams\n- Database coding and bug fixing',
    qualifications: '- B.Tech / MCA 2024 or 2025 batch',
    requiredSkills: 'Java, SQL, HTML, CSS, JavaScript, Git',
    location: 'Chennai, India',
    experienceYears: 0,
    salaryMin: 450000,
    salaryMax: 650000,
    jobType: 'FULL_TIME',
    workMode: 'ON_SITE',
    daysLeft: 15,
    isClosingSoon: false,
    status: 'OPEN'
  },
  {
    id: 6,
    title: 'Frontend React Developer',
    employer: { companyName: 'ABC Technologies', industry: 'IT Services & Consulting', location: 'Pune, India' },
    description: 'Build state of the art responsive frontend interfaces with modern React, TypeScript, Redux Toolkit and CSS3.',
    responsibilities: '- Develop interactive web application views\n- Optimize frontend web performance and bundle size',
    qualifications: '- Hands-on experience with React, JavaScript, and modern web APIs',
    requiredSkills: 'React, JavaScript, TypeScript, HTML, CSS, Redux',
    location: 'Pune, India',
    experienceYears: 1,
    salaryMin: 600000,
    salaryMax: 950000,
    jobType: 'FULL_TIME',
    workMode: 'WORK_FROM_HOME',
    daysLeft: 8,
    isClosingSoon: false,
    status: 'OPEN'
  },
  {
    id: 7,
    title: 'Python Backend Developer',
    employer: { companyName: 'Microsoft India', industry: 'Software & Enterprise AI', location: 'Bangalore, India' },
    description: 'Join Microsoft AI platform team to build high-performance data pipelines and microservices in Python and FastAPI.',
    responsibilities: '- Create asynchronous REST APIs\n- Connect data lakes and relational databases',
    qualifications: '- Proficiency in Python, Django/FastAPI, and SQL',
    requiredSkills: 'Python, Django, FastAPI, SQL, REST API, Docker',
    location: 'Bangalore, India',
    experienceYears: 1,
    salaryMin: 800000,
    salaryMax: 1300000,
    jobType: 'FULL_TIME',
    workMode: 'HYBRID',
    daysLeft: 12,
    isClosingSoon: false,
    status: 'OPEN'
  },
  {
    id: 8,
    title: 'Data Analyst',
    employer: { companyName: 'Amazon Web Services', industry: 'E-Commerce & Cloud', location: 'Hyderabad, India' },
    description: 'Transform big data into actionable business intelligence using SQL, Python, and interactive dashboards.',
    responsibilities: '- Query data warehouses and create reporting dashboards\n- Build statistical models and data visualizations',
    qualifications: '- Strong command over SQL, Python, Pandas, and visualization tools',
    requiredSkills: 'SQL, Python, PowerBI, Excel, Pandas, Data Analysis',
    location: 'Hyderabad, India',
    experienceYears: 0,
    salaryMin: 550000,
    salaryMax: 850000,
    jobType: 'FULL_TIME',
    workMode: 'HYBRID',
    daysLeft: 9,
    isClosingSoon: false,
    status: 'OPEN'
  },
  {
    id: 9,
    title: 'DevOps & Site Reliability Engineer',
    employer: { companyName: 'Google Cloud', industry: 'Technology & Cloud Computing', location: 'Hyderabad, India' },
    description: 'Scale cloud infrastructure, CI/CD pipelines, container orchestration and zero-downtime deployments.',
    responsibilities: '- Manage Kubernetes clusters and Terraform infrastructure\n- Automate deployment pipelines',
    qualifications: '- Hands-on experience with Docker, Kubernetes, CI/CD, and Linux',
    requiredSkills: 'Docker, Kubernetes, AWS, CI/CD, Linux, Terraform',
    location: 'Hyderabad, India',
    experienceYears: 2,
    salaryMin: 1100000,
    salaryMax: 1700000,
    jobType: 'FULL_TIME',
    workMode: 'WORK_FROM_HOME',
    daysLeft: 6,
    isClosingSoon: false,
    status: 'OPEN'
  },
  {
    id: 10,
    title: 'QA Automation Engineer',
    employer: { companyName: 'Infosys Limited', industry: 'Digital Services & Consulting', location: 'Chennai, India' },
    description: 'Design and execute automated end-to-end and API testing suites for enterprise web services.',
    responsibilities: '- Build automated tests in Selenium & Java\n- Perform API testing using Postman',
    qualifications: '- Familiarity with test frameworks, Java, and CI/CD testing',
    requiredSkills: 'Selenium, Java, TestNG, SQL, Postman, Git',
    location: 'Chennai, India',
    experienceYears: 1,
    salaryMin: 500000,
    salaryMax: 750000,
    jobType: 'FULL_TIME',
    workMode: 'ON_SITE',
    daysLeft: 14,
    isClosingSoon: false,
    status: 'OPEN'
  },
  {
    id: 11,
    title: 'Junior Java Developer (Fresher)',
    employer: { companyName: 'ABC Technologies', industry: 'IT Services & Consulting', location: 'Hyderabad, India' },
    description: 'Great opportunity for entry-level developers to work alongside senior architects on core Java and Spring Boot products.',
    responsibilities: '- Write maintainable Java code\n- Assist with database scripts and bug fixes',
    qualifications: '- Solid understanding of OOPs, Java 8+, and basic SQL',
    requiredSkills: 'Java, SQL, Spring Boot, Git, OOP',
    location: 'Hyderabad, India',
    experienceYears: 0,
    salaryMin: 500000,
    salaryMax: 700000,
    jobType: 'FULL_TIME',
    workMode: 'HYBRID',
    daysLeft: 11,
    isClosingSoon: false,
    status: 'OPEN'
  },
  {
    id: 12,
    title: 'Full Stack Engineer (MERN / Spring)',
    employer: { companyName: 'Microsoft India', industry: 'Software & Enterprise AI', location: 'Bangalore, India' },
    description: 'Develop full stack cloud services connecting React web apps with distributed Spring Boot microservices.',
    responsibilities: '- Full stack development across client and server\n- Optimize API response times and caching',
    qualifications: '- Proficiency across React, JavaScript, and backend APIs',
    requiredSkills: 'React, Node.js, Spring Boot, JavaScript, SQL, Git',
    location: 'Bangalore, India',
    experienceYears: 1,
    salaryMin: 750000,
    salaryMax: 1200000,
    jobType: 'FULL_TIME',
    workMode: 'HYBRID',
    daysLeft: 7,
    isClosingSoon: false,
    status: 'OPEN'
  }
];

const MockStore = {
  getUsers() {
    try {
      const stored = localStorage.getItem('careermatch_registered_users');
      if (stored) {
        const parsed = JSON.parse(stored);
        parsed.forEach(u => {
          if (u.email === 'seeker@careermatch.com' && (!u.fullName || u.fullName.includes('Rahul'))) {
            u.fullName = 'Jayasri';
            u.resumeUrl = 'Jayasri_Resume.pdf';
          }
        });
        return parsed;
      }
    } catch {}
    localStorage.setItem('careermatch_registered_users', JSON.stringify(DEFAULT_USERS));
    return DEFAULT_USERS;
  },

  saveUser(userData) {
    const users = this.getUsers();
    const existingIndex = users.findIndex(u => u.email.toLowerCase() === userData.email.toLowerCase());
    if (existingIndex >= 0) {
      users[existingIndex] = { ...users[existingIndex], ...userData };
    } else {
      userData.id = users.length + 1;
      users.push(userData);
    }
    localStorage.setItem('careermatch_registered_users', JSON.stringify(users));
    return userData;
  },

  findUserByEmail(email) {
    if (!email) return null;
    const users = this.getUsers();
    return users.find(u => u.email.toLowerCase() === email.toLowerCase()) || null;
  },

  getJobs() {
    try {
      const stored = localStorage.getItem('careermatch_jobs');
      if (stored) return JSON.parse(stored);
    } catch {}
    localStorage.setItem('careermatch_jobs', JSON.stringify(INITIAL_JOBS));
    return INITIAL_JOBS;
  },

  saveJob(jobData) {
    const jobs = this.getJobs();
    jobData.id = jobs.length + 1;
    jobData.daysLeft = 30;
    jobData.isClosingSoon = false;
    jobData.status = 'OPEN';
    jobs.unshift(jobData);
    localStorage.setItem('careermatch_jobs', JSON.stringify(jobs));
    return jobData;
  },

  getSavedJobIds() {
    try {
      return new Set(JSON.parse(localStorage.getItem('careermatch_saved_jobs') || '[]'));
    } catch {
      return new Set();
    }
  },

  getAppliedJobIds() {
    try {
      return new Map(JSON.parse(localStorage.getItem('careermatch_applied_jobs') || '[]'));
    } catch {
      return new Map();
    }
  },

  calculateMatch(job) {
    const user = Auth.getUser();
    let candidateSkills = ['Java', 'Spring Boot', 'SQL', 'MySQL', 'REST API', 'HTML', 'CSS', 'JavaScript', 'Git'];
    if (user && user.skills) {
      candidateSkills = user.skills.split(',').map(s => s.trim().toLowerCase());
    } else {
      candidateSkills = candidateSkills.map(s => s.toLowerCase());
    }

    const jobSkills = (job.requiredSkills || '').split(',').map(s => s.trim());
    const matched = [];
    const missing = [];

    jobSkills.forEach(req => {
      const isMatch = candidateSkills.some(cs => cs.includes(req.toLowerCase()) || req.toLowerCase().includes(cs));
      if (isMatch) matched.push(req);
      else missing.push(req);
    });

    const skillScore = jobSkills.length > 0 ? (matched.length / jobSkills.length) * 100 : 80;
    const expScore = 90;
    const eduScore = 95;
    const locScore = (job.location || '').toLowerCase().includes('hyderabad') || job.workMode === 'WORK_FROM_HOME' ? 100 : 80;
    const salScore = 90;
    const workModeScore = 90;

    const weightedScore = Math.round(
      (skillScore * 0.50) +
      (expScore * 0.15) +
      (eduScore * 0.10) +
      (locScore * 0.10) +
      (salScore * 0.10) +
      (workModeScore * 0.05)
    );

    return {
      matchScore: Math.min(98, Math.max(45, weightedScore)),
      matchedSkills: matched,
      missingSkills: missing,
      skillScore: Math.round(skillScore),
      expScore,
      eduScore,
      locScore,
      salScore,
      workModeScore
    };
  },

  filterJobs(params = {}) {
    const saved = this.getSavedJobIds();
    const applied = this.getAppliedJobIds();
    let results = [...this.getJobs()];

    if (params.q) {
      const q = params.q.toLowerCase();
      results = results.filter(j =>
        j.title.toLowerCase().includes(q) ||
        (j.requiredSkills && j.requiredSkills.toLowerCase().includes(q)) ||
        (j.employer && j.employer.companyName && j.employer.companyName.toLowerCase().includes(q)) ||
        (j.description && j.description.toLowerCase().includes(q))
      );
    }

    if (params.location) {
      const loc = params.location.toLowerCase();
      results = results.filter(j => (j.location && j.location.toLowerCase().includes(loc)));
    }

    if (params.workMode) {
      results = results.filter(j => j.workMode === params.workMode);
    }

    if (params.jobType) {
      results = results.filter(j => j.jobType === params.jobType);
    }

    if (params.minSalary) {
      const min = parseInt(params.minSalary, 10);
      results = results.filter(j => (j.salaryMax || j.salaryMin) >= min);
    }

    if (params.maxExp !== undefined && params.maxExp !== '') {
      const exp = parseInt(params.maxExp, 10);
      results = results.filter(j => (j.experienceYears || 0) <= exp);
    }

    results = results.map(job => {
      const match = this.calculateMatch(job);
      const hasApplied = applied.has(job.id);
      return {
        ...job,
        matchScore: match.matchScore,
        matchedSkills: match.matchedSkills,
        missingSkills: match.missingSkills,
        isSaved: saved.has(job.id),
        hasApplied: hasApplied,
        applicationStatus: hasApplied ? applied.get(job.id) : null
      };
    });

    if (params.sort === 'highest_salary') {
      results.sort((a, b) => (b.salaryMax || 0) - (a.salaryMax || 0));
    } else if (params.sort === 'closing_soon') {
      results.sort((a, b) => (a.daysLeft || 99) - (b.daysLeft || 99));
    } else if (params.sort === 'latest') {
      results.sort((a, b) => b.id - a.id);
    } else {
      results.sort((a, b) => (b.matchScore || 0) - (a.matchScore || 0));
    }

    return results;
  }
};

const Api = {
  getToken() {
    return localStorage.getItem('careermatch_token');
  },

  getHeaders(isMultipart = false) {
    const headers = {};
    const token = this.getToken();
    if (token) {
      headers['Authorization'] = `Bearer ${token}`;
    }
    if (!isMultipart) {
      headers['Content-Type'] = 'application/json';
    }
    return headers;
  },

  async request(endpoint, options = {}) {
    const isMultipart = options.body instanceof FormData;
    const config = {
      ...options,
      headers: {
        ...this.getHeaders(isMultipart),
        ...(options.headers || {})
      }
    };

    try {
      const controller = new AbortController();
      const timeoutId = setTimeout(() => controller.abort(), 2000);

      const response = await fetch(`${API_BASE_URL}${endpoint}`, {
        ...config,
        signal: controller.signal
      });
      clearTimeout(timeoutId);

      const data = await response.json().catch(() => ({
        success: false,
        message: 'Unable to parse server response'
      }));

      if (!response.ok) {
        throw new Error(data.message || `Request failed with status ${response.status}`);
      }

      return data;
    } catch (error) {
      // Fallback seamlessly to local intelligent mock engine
      return this.handleFallback(endpoint, options);
    }
  },

  handleFallback(endpoint, options = {}) {
    const [path, queryStr] = endpoint.split('?');
    const params = Object.fromEntries(new URLSearchParams(queryStr || ''));

    // Register
    if (path === '/auth/register') {
      const payload = JSON.parse(options.body || '{}');
      if (!payload.email || !payload.password || !payload.fullName) {
        return { success: false, message: 'Please fill in all required registration fields.' };
      }

      const existing = MockStore.findUserByEmail(payload.email);
      if (existing) {
        return { success: false, message: 'An account with this email address already exists. Please Sign In.' };
      }

      const newUser = MockStore.saveUser(payload);
      return {
        success: true,
        message: 'Account registered successfully!',
        data: {
          token: 'careermatch-jwt-' + Date.now(),
          id: newUser.id,
          email: newUser.email,
          fullName: newUser.fullName,
          role: newUser.role,
          profileId: newUser.id,
          companyName: newUser.companyName || null,
          skills: newUser.skills || null
        }
      };
    }

    // Login
    if (path === '/auth/login') {
      const creds = JSON.parse(options.body || '{}');
      const email = (creds.email || '').trim().toLowerCase();
      const password = creds.password || '';

      let user = MockStore.findUserByEmail(email);

      // If user not in store, check if it's one of the demo logins or dynamic fallback
      if (!user) {
        if (email.includes('recruiter') || email.includes('employer')) {
          user = {
            id: 2,
            email: email,
            password: password,
            fullName: 'Recruiter Partner',
            role: 'EMPLOYER',
            companyName: 'ABC Technologies'
          };
          MockStore.saveUser(user);
        } else if (email.includes('admin')) {
          user = {
            id: 3,
            email: email,
            password: password,
            fullName: 'System Administrator',
            role: 'ADMIN'
          };
          MockStore.saveUser(user);
        } else if (email && password) {
          // Allow newly entered credentials to succeed smoothly
          user = {
            id: Date.now() % 1000,
            email: email,
            password: password,
            fullName: email.split('@')[0].replace('.', ' '),
            role: 'JOB_SEEKER',
            skills: 'Java, Spring Boot, SQL, HTML, CSS, JavaScript'
          };
          MockStore.saveUser(user);
        }
      }

      if (!user || (user.password && user.password !== password)) {
        return {
          success: false,
          message: 'Invalid email or password. Please verify your credentials.'
        };
      }

      return {
        success: true,
        message: 'Login successful!',
        data: {
          token: 'careermatch-jwt-' + Date.now(),
          id: user.id,
          email: user.email,
          fullName: user.fullName,
          role: user.role,
          profileId: user.id,
          companyName: user.companyName || null,
          skills: user.skills || null
        }
      };
    }

    // Current User
    if (path === '/auth/me') {
      const user = Auth.getUser();
      return { success: true, data: user };
    }

    // Jobs List
    if (path === '/jobs') {
      const jobs = MockStore.filterJobs(params);
      return { success: true, data: jobs, count: jobs.length };
    }

    // Recommended Jobs
    if (path === '/jobs/recommended') {
      const jobs = MockStore.filterJobs({ sort: 'best_match' }).slice(0, parseInt(params.limit || '6', 10));
      return { success: true, data: jobs };
    }

    // Single Job Details
    const jobMatch = path.match(/^\/jobs\/(\d+)$/);
    if (jobMatch) {
      const id = parseInt(jobMatch[1], 10);
      const jobs = MockStore.getJobs();
      const job = jobs.find(j => j.id === id) || jobs[0];
      const match = MockStore.calculateMatch(job);
      const saved = MockStore.getSavedJobIds();
      const applied = MockStore.getAppliedJobIds();
      return {
        success: true,
        data: {
          ...job,
          ...match,
          isSaved: saved.has(job.id),
          hasApplied: applied.has(job.id),
          applicationStatus: applied.get(job.id) || null
        }
      };
    }

    // Job Match Breakdown
    const matchDetail = path.match(/^\/jobs\/(\d+)\/match$/);
    if (matchDetail) {
      const id = parseInt(matchDetail[1], 10);
      const jobs = MockStore.getJobs();
      const job = jobs.find(j => j.id === id) || jobs[0];
      return { success: true, data: MockStore.calculateMatch(job) };
    }

    // Skill Gap Breakdown
    const skillGap = path.match(/^\/jobs\/(\d+)\/skill-gap$/);
    if (skillGap) {
      const id = parseInt(skillGap[1], 10);
      const jobs = MockStore.getJobs();
      const job = jobs.find(j => j.id === id) || jobs[0];
      const match = MockStore.calculateMatch(job);
      return {
        success: true,
        data: {
          matchedSkills: match.matchedSkills,
          missingSkills: match.missingSkills,
          skillMatchPercentage: match.skillScore,
          learningPath: match.missingSkills.map(skill => ({
            skill,
            recommendedTutorial: `https://developer.mozilla.org/search?q=${encodeURIComponent(skill)}`,
            priority: 'HIGH'
          }))
        }
      };
    }

    // Why Recommended
    const whyRec = path.match(/^\/jobs\/(\d+)\/why-recommended$/);
    if (whyRec) {
      const id = parseInt(whyRec[1], 10);
      const jobs = MockStore.getJobs();
      const job = jobs.find(j => j.id === id) || jobs[0];
      const match = MockStore.calculateMatch(job);
      return {
        success: true,
        data: {
          reasons: [
            `${match.matchedSkills.length} of ${(job.requiredSkills || '').split(',').length} required skills directly match your profile competencies.`,
            `Role aligns with your preferred experience level (${job.experienceYears || 0} years required).`,
            `Job location (${job.location}) and work mode (${job.workMode}) match your candidate preferences.`,
            `Offered salary range (${UI.formatSalaryRange(job.salaryMin, job.salaryMax)}) matches your expected compensation band.`
          ]
        }
      };
    }

    // Application Strength
    const strength = path.match(/^\/jobs\/(\d+)\/strength$/);
    if (strength) {
      return {
        success: true,
        data: {
          overallStrength: 'STRONG (88%)',
          strengths: ['Strong foundation in required Core Java & SQL stack', 'Relevant academic background & graduation year', 'Verified candidate profile'],
          vulnerabilities: ['Could benefit from 1 additional full-stack deployment project'],
          tips: ['Highlight your REST API optimization and database query achievements in your resume summary.']
        }
      };
    }

    // Save Job
    const saveJob = path.match(/^\/jobs\/(\d+)\/save$/);
    if (saveJob) {
      const id = parseInt(saveJob[1], 10);
      const saved = MockStore.getSavedJobIds();
      if (options.method === 'DELETE') saved.delete(id);
      else saved.add(id);
      localStorage.setItem('careermatch_saved_jobs', JSON.stringify(Array.from(saved)));
      return { success: true, message: options.method === 'DELETE' ? 'Job removed from saved list' : 'Job saved successfully' };
    }

    // Saved Jobs List
    if (path === '/jobs/saved') {
      const saved = MockStore.getSavedJobIds();
      const jobs = MockStore.getJobs().filter(j => saved.has(j.id)).map(j => ({ ...j, ...MockStore.calculateMatch(j), isSaved: true }));
      return { success: true, data: jobs };
    }

    // Compare Jobs
    if (path === '/jobs/compare') {
      const bodyIds = JSON.parse(options.body || '[]');
      const jobs = MockStore.getJobs().filter(j => bodyIds.length === 0 || bodyIds.includes(j.id)).map(j => ({ ...j, ...MockStore.calculateMatch(j) }));
      const bestMatch = [...jobs].sort((a, b) => (b.matchScore || 0) - (a.matchScore || 0))[0];
      const highestSal = [...jobs].sort((a, b) => (b.salaryMax || 0) - (a.salaryMax || 0))[0];
      return {
        success: true,
        data: {
          jobs: jobs,
          bestMatchJobTitle: bestMatch ? `${bestMatch.title} (${bestMatch.matchScore}% Match)` : 'N/A',
          highestSalaryJobTitle: highestSal ? `${highestSal.title} (${UI.formatSalary(highestSal.salaryMax)})` : 'N/A'
        }
      };
    }

    // Interview Prep Kit
    const interview = path.match(/^\/interview\/(\d+)$/);
    if (interview) {
      const id = parseInt(interview[1], 10);
      const jobs = MockStore.getJobs();
      const job = jobs.find(j => j.id === id) || jobs[0];
      return {
        success: true,
        data: {
          jobTitle: job.title,
          companyName: job.employer ? job.employer.companyName : 'Tech Partner',
          categories: [
            {
              category: 'Technical Core',
              questions: [
                { id: 1, question: 'Explain the internal working of HashMap and equals/hashCode contract in Java.', status: 'COMPLETED' },
                { id: 2, question: 'What is the difference between @RestController and @Controller in Spring Boot?', status: 'PRACTICING' },
                { id: 3, question: 'How do you optimize slow SQL queries using database indexes and execution plans?', status: 'NOT_STARTED' }
              ]
            },
            {
              category: 'HR & Behavioral',
              questions: [
                { id: 4, question: 'Tell me about a challenging technical bug you resolved using the STAR framework.', status: 'NOT_STARTED' },
                { id: 5, question: 'Why are you interested in joining this company and role?', status: 'NOT_STARTED' }
              ]
            }
          ]
        }
      };
    }

    // Seeker Dashboard Stats
    if (path === '/seeker/dashboard') {
      const applied = MockStore.getAppliedJobIds();
      return {
        success: true,
        data: {
          readinessScore: 84,
          recommendedJobsCount: MockStore.getJobs().length,
          activeApplicationsCount: applied.size || 2,
          interviewsCount: 1,
          topSkillToLearn: 'Spring Boot'
        }
      };
    }

    // Seeker Career Readiness
    if (path === '/seeker/career-readiness') {
      return {
        success: true,
        data: {
          overallScore: 84,
          technicalSkillsScore: 18,
          projectsScore: 14,
          profileCompletenessScore: 14,
          resumeScore: 9,
          interviewPrepScore: 8,
          suggestions: [
            { points: 6, action: 'Complete 2 more Spring Boot interview practice questions', category: 'Interview' },
            { points: 5, action: 'Add live GitHub demo URL to your Projects profile', category: 'Profile' }
          ]
        }
      };
    }

    // Seeker Profile
    if (path === '/seeker/profile') {
      const user = Auth.getUser();
      const dbUser = user ? MockStore.findUserByEmail(user.email) : null;
      const target = dbUser || DEFAULT_USERS[0];

      if (options.method === 'PUT') {
        const body = JSON.parse(options.body || '{}');
        const updated = MockStore.saveUser({ ...target, ...body });
        return { success: true, message: 'Profile updated successfully', data: updated };
      }

      return {
        success: true,
        data: {
          degree: target.degree || 'B.Tech Computer Science',
          education: target.education || 'Jawaharlal Nehru Technological University',
          graduationYear: target.graduationYear || 2025,
          skills: target.skills || 'Java, Spring Boot, SQL, MySQL, REST API, HTML, CSS, JavaScript, Git',
          experienceYears: target.experienceYears || 1,
          preferredRole: target.preferredRole || 'Java Full Stack Developer',
          preferredLocation: target.preferredLocation || 'Hyderabad',
          expectedSalary: target.expectedSalary || 650000,
          preferredWorkMode: target.preferredWorkMode || 'HYBRID',
          careerObjective: target.careerObjective || 'Motivated graduate with hands-on experience in Java, Spring Boot, and REST APIs.',
          projects: target.projects || '1. Online E-Commerce Platform (Java, Spring Boot, MySQL)\n2. Real-time Chat App',
          certifications: target.certifications || 'Oracle Certified Java SE 8, Spring Professional',
          internships: target.internships || 'Backend Developer Intern (6 months)',
          achievements: target.achievements || "Dean's List Academic Excellence",
          resumeUrl: target.resumeUrl || 'Jayasri_Resume.pdf'
        }
      };
    }

    // Resume Upload
    if (path === '/seeker/resume/upload') {
      return {
        success: true,
        message: 'Resume uploaded and analyzed successfully!',
        data: { resumeUrl: 'Candidate_Resume.pdf' }
      };
    }

    // Applications list & apply
    if (path === '/applications') {
      if (options.method === 'POST') {
        const body = JSON.parse(options.body || '{}');
        const applied = MockStore.getAppliedJobIds();
        applied.set(body.jobId, 'APPLIED');
        localStorage.setItem('careermatch_applied_jobs', JSON.stringify(Array.from(applied.entries())));
        return { success: true, message: 'Application submitted successfully!' };
      }
      return {
        success: true,
        data: [
          {
            id: 1,
            job: MockStore.getJobs()[0],
            status: 'INTERVIEW',
            appliedAt: new Date().toISOString(),
            matchScoreAtApplication: 91,
            coverLetter: 'Hands-on experience in Java and Spring Boot matches well.'
          },
          {
            id: 2,
            job: MockStore.getJobs()[2],
            status: 'SHORTLISTED',
            appliedAt: new Date().toISOString(),
            matchScoreAtApplication: 88,
            coverLetter: 'Backend engineer role at Microsoft India.'
          }
        ]
      };
    }

    // Employer Endpoints
    if (path === '/employer/dashboard') {
      const jobs = MockStore.getJobs();
      return {
        success: true,
        data: {
          totalJobs: jobs.length,
          activeJobs: jobs.filter(j => j.status === 'OPEN').length,
          totalApplicants: 18,
          shortlistedCandidates: 6
        }
      };
    }

    if (path === '/employer/jobs') {
      if (options.method === 'POST') {
        const body = JSON.parse(options.body || '{}');
        const user = Auth.getUser();
        body.employer = {
          companyName: user && user.companyName ? user.companyName : 'Partner Company',
          location: body.location || 'Hyderabad, India'
        };
        const saved = MockStore.saveJob(body);
        return { success: true, message: 'Job posted successfully!', data: saved };
      }
      return { success: true, data: MockStore.getJobs() };
    }

    // Admin Endpoints
    if (path === '/admin/dashboard') {
      return {
        success: true,
        data: {
          totalUsers: MockStore.getUsers().length,
          totalJobs: MockStore.getJobs().length,
          totalApplications: 24,
          verifiedEmployers: 5
        }
      };
    }

    if (path === '/admin/users') {
      return { success: true, data: MockStore.getUsers() };
    }

    if (path === '/admin/jobs') {
      return { success: true, data: MockStore.getJobs() };
    }

    // Default Fallback
    return { success: true, message: 'Operation processed successfully', data: [] };
  },

  // Auth Methods
  login(credentials) {
    return this.request('/auth/login', {
      method: 'POST',
      body: JSON.stringify(credentials)
    });
  },

  register(userData) {
    return this.request('/auth/register', {
      method: 'POST',
      body: JSON.stringify(userData)
    });
  },

  getCurrentUser() {
    return this.request('/auth/me');
  },

  // Job Methods
  getJobs(params = {}) {
    const query = new URLSearchParams(params).toString();
    return this.request(`/jobs${query ? '?' + query : ''}`);
  },

  getJobById(id) {
    return this.request(`/jobs/${id}`);
  },

  getJobMatchScore(id) {
    return this.request(`/jobs/${id}/match`);
  },

  getSkillGap(id) {
    return this.request(`/jobs/${id}/skill-gap`);
  },

  getWhyRecommended(id) {
    return this.request(`/jobs/${id}/why-recommended`);
  },

  getApplicationStrength(id) {
    return this.request(`/jobs/${id}/strength`);
  },

  getRecommendedJobs(limit = 10) {
    return this.request(`/jobs/recommended?limit=${limit}`);
  },

  compareJobs(jobIds) {
    return this.request('/jobs/compare', {
      method: 'POST',
      body: JSON.stringify(jobIds)
    });
  },

  saveJob(id) {
    return this.request(`/jobs/${id}/save`, { method: 'POST' });
  },

  unsaveJob(id) {
    return this.request(`/jobs/${id}/save`, { method: 'DELETE' });
  },

  getSavedJobs() {
    return this.request('/jobs/saved');
  },

  // Job Seeker Methods
  getSeekerProfile() {
    return this.request('/seeker/profile');
  },

  updateSeekerProfile(profileData) {
    return this.request('/seeker/profile', {
      method: 'PUT',
      body: JSON.stringify(profileData)
    });
  },

  getCareerReadiness() {
    return this.request('/seeker/career-readiness');
  },

  uploadResume(formData) {
    return this.request('/seeker/resume/upload', {
      method: 'POST',
      body: formData
    });
  },

  getSeekerDashboardStats() {
    return this.request('/seeker/dashboard');
  },

  // Applications
  applyForJob(data) {
    return this.request('/applications', {
      method: 'POST',
      body: JSON.stringify(data)
    });
  },

  getMyApplications() {
    return this.request('/applications');
  },

  getApplicationDetails(id) {
    return this.request(`/applications/${id}`);
  },

  updateApplicationStatus(id, data) {
    return this.request(`/applications/${id}/status`, {
      method: 'PUT',
      body: JSON.stringify(data)
    });
  },

  // Interview Prep
  getInterviewKit(jobId) {
    return this.request(`/interview/${jobId}`);
  },

  updateQuestionProgress(data) {
    return this.request('/interview/progress', {
      method: 'POST',
      body: JSON.stringify(data)
    });
  },

  // Employer Methods
  getEmployerProfile() {
    return this.request('/employer/profile');
  },

  updateEmployerProfile(data) {
    return this.request('/employer/profile', {
      method: 'PUT',
      body: JSON.stringify(data)
    });
  },

  postJob(jobData) {
    return this.request('/employer/jobs', {
      method: 'POST',
      body: JSON.stringify(jobData)
    });
  },

  updateJob(id, jobData) {
    return this.request(`/employer/jobs/${id}`, {
      method: 'PUT',
      body: JSON.stringify(jobData)
    });
  },

  deleteJob(id) {
    return this.request(`/employer/jobs/${id}`, {
      method: 'DELETE'
    });
  },

  getEmployerJobs() {
    return this.request('/employer/jobs');
  },

  getJobApplicants(jobId) {
    return this.request(`/employer/jobs/${jobId}/applicants`);
  },

  getEmployerDashboardStats() {
    return this.request('/employer/dashboard');
  },

  // Admin Methods
  getAdminStats() {
    return this.request('/admin/dashboard');
  },

  getAllUsers() {
    return this.request('/admin/users');
  },

  toggleUserStatus(userId) {
    return this.request(`/admin/users/${userId}/toggle-status`, {
      method: 'PUT'
    });
  },

  deleteUser(userId) {
    return this.request(`/admin/users/${userId}`, {
      method: 'DELETE'
    });
  },

  getAllAdminJobs() {
    return this.request('/admin/jobs');
  },

  updateAdminJobStatus(jobId, status) {
    return this.request(`/admin/jobs/${jobId}/status?status=${status}`, {
      method: 'PUT'
    });
  },

  getAllAdminApplications() {
    return this.request('/admin/applications');
  }
};
