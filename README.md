# CareerMatch AI – Smart Job Seeking and Career Guidance Platform

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17%2B-orange.svg)](https://www.oracle.com/java/)
[![MySQL](https://img.shields.io/badge/Database-MySQL-blue.svg)](https://www.mysql.com/)
[![Bootstrap](https://img.shields.io/badge/Bootstrap-5.3-purple.svg)](https://getbootstrap.com/)
[![License](https://img.shields.io/badge/License-Academic%20%2F%20Open-lightgrey.svg)]()

> **An intelligent next-generation career assistant and job-matching platform designed for students, freshers, and software developers.**

---

## 📌 1. Project Overview & Problem Statement

### ❌ The Problem with Existing Job Portals
Traditional job portals (e.g., Naukri, Indeed, LinkedIn) primarily act as basic bulletin boards. They suffer from:
1. **Black-box matching:** Candidates receive irrelevant job recommendations with no explanation of *why* they matched.
2. **Missing skill opacity:** Rejections happen without actionable feedback on which exact skills caused disqualification.
3. **No readiness benchmark:** Students lack a quantitative indicator of whether their profile is industry-ready.
4. **Scattered interview prep:** Candidates must browse external sites to find relevant technical, HR, and project questions.

### ✅ The CareerMatch AI Solution
**CareerMatch AI** combines a **Smart Job Portal + Intelligent Match Engine + Skill Gap Analyzer + Career Readiness Assessor + Interview Preparation Center + Visual Kanban Application Tracker**.

---

## 🌟 2. Unique & Core Features

### 1. 🎯 Dynamic 6-Point Smart Job Match Algorithm
Calculates real-time percentage suitability for every candidate-job pair using weighted scoring:
- **Skills Match (50%)**: Tokenized fuzzy matching of candidate technical skills against job requirements.
- **Experience Match (15%)**: Ratio of candidate experience to required experience.
- **Education / Degree Match (10%)**: Relevance of academic degree (B.Tech, BE, MCA, etc.).
- **Location Fit (10%)**: Preferred location vs job location (100% for remote).
- **Salary Compatibility (10%)**: Alignment between expected compensation and employer budget.
- **Work Mode Fit (5%)**: Work From Home, Hybrid, or On-site preference.

### 2. 💡 "Why This Job?" Contextual Explainability
Generates dynamic, human-readable bullet points explaining the exact factors making a job suitable (e.g. *5 of 6 required skills match*, *Role matches target Java Developer preference*, *Salary is within your expected band*).

### 3. 🔍 Skill Gap Analyzer & Learning Pathways
Compares candidate competencies against required skills, clearly separating **Matched Skills (✓)** from **Missing Skills (✗)** and generating a prioritized learning roadmap with direct documentation links.

### 4. 📈 0–100 Career Readiness Score
Evaluates 8 profile dimensions (Profile Completeness, Technical Skills, Education, Projects, Certifications, Experience, Resume, and Interview Practice) with actionable point boosts (e.g. `+10 Upload Resume`, `+7 Add 2 GitHub Projects`).

### 5. 🛡️ Pre-Application Strength Indicator
Evaluates your competitive position before submitting an application, highlighting strengths, vulnerabilities, and improvement tips.

### 6. 📊 Visual Kanban Application Tracker
Track application pipelines in real-time across stages:
`SAVED` → `APPLIED` → `ASSESSMENT` → `INTERVIEW` → `SHORTLISTED` → `SELECTED` / `REJECTED`.

### 7. 🎓 Tailored Interview Preparation Kits
Job-specific question banks categorized into:
- **Technical Questions** (Core Java, Spring Boot, SQL, REST, Data Structures, Concurrency)
- **HR & Behavioral Questions** (Present-Past-Future & STAR frameworks)
- **Architecture & Project Questions**
- Interactive Practice Tracker (**Not Started**, **Practicing**, **Completed**) with personal notes.

### 8. ⚖️ Side-by-Side Job Comparison Matrix
Compare up to 3 jobs simultaneously across match percentage, compensation, work mode, required skills, and closing deadlines.

---

## 🛠️ 3. Technology Stack

| Layer | Technology |
| :--- | :--- |
| **Frontend** | HTML5, CSS3 (Modern Glassmorphism & Custom Properties), JavaScript (ES6+, Fetch API), Bootstrap 5.3, FontAwesome 6 |
| **Backend** | Java 17+, Spring Boot 3.4.5, Spring Security, Spring Data JPA, Hibernate, JJWT (JSON Web Token), Maven |
| **Database** | MySQL (Target Production) / H2 In-Memory (Dev/Zero-Config Fallback) |
| **Security** | BCrypt Password Hashing, Stateless JWT Bearer Filter, Role-Based Access Control (`JOB_SEEKER`, `EMPLOYER`, `ADMIN`) |

---

## 🏗️ 4. System Architecture & ER Model

```
 ┌─────────────────────────────────────────────────────────────┐
 │                      Frontend Client                        │
 │  (HTML5 + Bootstrap 5 + Vanilla JS Fetch API + CSS3)        │
 └──────────────────────────────┬──────────────────────────────┘
                                │ REST APIs (JSON / JWT)
 ┌──────────────────────────────▼──────────────────────────────┐
 │                    Spring Boot Backend                      │
 │  ┌───────────────────────────────────────────────────────┐  │
 │  │ Controllers: Auth, Job, Seeker, Employer, Admin, App  │  │
 │  ├───────────────────────────────────────────────────────┤  │
 │  │ Services: JobMatching, SkillGap, CareerScore, Prep   │  │
 │  ├───────────────────────────────────────────────────────┤  │
 │  │ Security: JwtAuthFilter, DaoAuthProvider, BCrypt      │  │
 │  ├───────────────────────────────────────────────────────┤  │
 │  │ Repositories: Spring Data JPA Repositories            │  │
 │  └───────────────────────────────────────────────────────┘  │
 └──────────────────────────────┬──────────────────────────────┘
                                │ JDBC
 ┌──────────────────────────────▼──────────────────────────────┐
 │                      MySQL Database                         │
 │  Users ──< Profiles ──< Jobs ──< Applications ──< Interview │
 └─────────────────────────────────────────────────────────────┘
```

---

## 🔑 5. Demo Login Credentials

| Role | Email | Password | Details |
| :--- | :--- | :--- | :--- |
| **Job Seeker** | `seeker@careermatch.com` | `Password@123` | Jayasri (Java Full Stack Developer) |
| **Employer** | `recruiter@google.com` | `Password@123` | Google Cloud Hiring Lead |
| **Employer** | `careers@abctech.com` | `Password@123` | ABC Technologies Recruiter |
| **Admin** | `admin@careermatch.com` | `Password@123` | Platform Administrator |

---

## 🚀 6. Installation & How to Run

### Step 1: Clone Repository
```bash
git clone https://github.com/your-username/careermatch-ai.git
cd careermatch-ai
```

### Step 2: Database Setup (MySQL)
1. Ensure MySQL Server is running on port `3306`.
2. Run `database/schema.sql` or let Spring Boot Hibernate auto-create tables.
3. Update `backend/src/main/resources/application.yml` with your database credentials:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/careermatchdb?createDatabaseIfNotExist=true
    username: root
    password: your_mysql_password
```
*(Note: If MySQL is not configured, the application automatically uses H2 memory fallback for instant out-of-the-box demoing).*

### Step 3: Run Spring Boot Backend
```bash
cd backend
mvn clean spring-boot:run
```
Backend API will start at: `http://localhost:8080`

### Step 4: Open Frontend
Open `frontend/index.html` in any modern web browser or serve with Live Server / VS Code.

---

## 📡 7. Core REST API Endpoints

### Authentication (`/api/auth`)
- `POST /api/auth/register` – Register new Job Seeker or Employer
- `POST /api/auth/login` – Authenticate and receive JWT Bearer token
- `GET /api/auth/me` – Retrieve current authenticated user profile

### Jobs & AI Matching (`/api/jobs`)
- `GET /api/jobs` – Search jobs by title, skills, location, work mode, salary, and sort by match %
- `GET /api/jobs/{id}` – Get job details with candidate context
- `GET /api/jobs/{id}/match` – 6-point dynamic match score breakdown
- `GET /api/jobs/{id}/skill-gap` – Matched vs missing skills & learning roadmap
- `GET /api/jobs/{id}/why-recommended` – Dynamic "Why this job?" bullet explanations
- `GET /api/jobs/{id}/strength` – Pre-application strength evaluation
- `GET /api/jobs/recommended` – Top personalized job recommendations
- `POST /api/jobs/compare` – Side-by-side multi-job comparison matrix
- `POST /api/jobs/{id}/save` – Bookmark job opening

### Candidate Guidance (`/api/seeker`)
- `GET /api/seeker/profile` – Get candidate portfolio
- `PUT /api/seeker/profile` – Update skills, education, projects, certifications
- `GET /api/seeker/career-readiness` – 0–100 score across 8 dimensions with point boost tips
- `POST /api/seeker/resume/upload` – Upload PDF resume
- `GET /api/seeker/dashboard` – Candidate metrics and top skill gap

### Applications & Kanban (`/api/applications`)
- `POST /api/applications` – Apply for opening with cover letter and profile resume
- `GET /api/applications` – Retrieve candidate's active application submissions
- `PUT /api/applications/{id}/status` – Update pipeline status (`APPLIED` → `INTERVIEW` → `SELECTED`)

### Interview Preparation (`/api/interview`)
- `GET /api/interview/{jobId}` – Job-tailored question kit (Technical, HR, STAR Project questions)
- `POST /api/interview/progress` – Update practice status and personal notes

### Recruiter & Admin (`/api/employer`, `/api/admin`)
- `GET /api/employer/jobs` – List recruiter's posted openings
- `POST /api/employer/jobs` – Post new opening with required skills
- `GET /api/employer/jobs/{id}/applicants` – Applicants ranked dynamically by match score %
- `GET /api/admin/dashboard` – System-wide analytics and user moderation

---

## 🎓 8. Academic Project & Viva Highlights

When presenting this project for B.Tech final-year major project evaluation or software developer interviews, highlight:
1. **Explainable AI Matching**: Weighted mathematical calculation preventing black-box recommendations.
2. **Dynamic Skill Gap Engine**: Actionable learning roadmaps tailored to specific job descriptions.
3. **8-Dimension Career Readiness Model**: Quantifiable score assessing resume completeness and coding competency.
4. **End-to-End Enterprise Architecture**: Decoupled REST architecture with JWT security, JPA relationships, and responsive UI.

---

## 📄 License
This project is developed for academic, portfolio, and technical interview demonstration purposes.
