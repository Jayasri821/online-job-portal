# Online Job Portal (HireHub)

Full-stack job portal: **Java 17 + Spring Boot + Spring Security + JWT + MySQL** backend and **React + Axios** frontend.

Roles: **Job Seeker**, **Recruiter**, **Admin**.

## What you can do

| Role | Features |
| --- | --- |
| Job Seeker | Register, profile, resume upload, search/filter jobs, apply, track status |
| Recruiter | Company profile, post/edit/close jobs, view applicants, shortlist/reject, schedule interviews |
| Admin | Manage users, companies, jobs, applications and view placement statistics |

## Project layout

```
online-job-portal/
  backend/     Spring Boot REST API (port 8080)
  frontend/    React + Vite UI (port 5173)
  docker-compose.yml   MySQL 8
```

Backend layers: **Controller → Service → Repository**, plus **DTOs**, **JWT security**, and **global exception handling**.

## Prerequisites

- JDK 17 or newer (Java 17 language level is set in `backend/pom.xml`)
- Maven 3.9+
- Node.js 18+
- MySQL 8 (or Docker)

## 1. Start MySQL

**Option A – Docker**

```bash
docker compose up -d
```

This starts MySQL on port `3306` with:

- database: `jobportal`
- user: `root`
- password: `root`

**Option B – local MySQL**

Create database `jobportal` and set `DB_USERNAME` / `DB_PASSWORD` if they are not `root` / `root`.

Edit `backend/src/main/resources/application.yml` if needed:

```yaml
spring:
  datasource:
    username: root
    password: root
```

Hibernate `ddl-auto: update` creates tables on first run.

## 2. Run the backend

```bash
cd backend
mvn spring-boot:run
```

API base URL: `http://localhost:8080/api`

On first start, demo users and sample jobs are inserted.

## 3. Run the frontend

```bash
cd frontend
npm install
npm run dev
```

Open `http://localhost:5173`

The React app calls the Spring Boot APIs with Axios. JWT is stored in `localStorage` and sent as `Authorization: Bearer <token>`.

## Demo logins

| Role | Email | Password |
| --- | --- | --- |
| Admin | `admin@jobportal.com` | `Admin@123` |
| Recruiter | `recruiter@techcorp.com` | `Recruiter@123` |
| Job Seeker | `seeker@gmail.com` | `Seeker@123` |

The demo seeker still needs to **upload a resume** before applying.

## Main REST APIs

- `POST /api/auth/register` – seeker or recruiter
- `POST /api/auth/login` – returns JWT
- `GET /api/auth/me`
- `GET /api/jobs?keyword=&location=&jobType=`
- `GET /api/jobs/{id}`
- Seeker: `/api/seeker/profile`, `/api/seeker/resume`, `/api/seeker/apply`, `/api/seeker/applications`
- Recruiter: `/api/recruiter/company`, `/api/recruiter/jobs`, `/api/recruiter/applications/{id}/status`, `/api/recruiter/applications/{id}/interview`
- Admin: `/api/admin/users`, `/api/admin/companies`, `/api/admin/jobs`, `/api/admin/applications`, `/api/admin/stats`

Passwords are stored with **BCrypt**. Authorization uses Spring Security `hasRole(...)` (`JOB_SEEKER`, `RECRUITER`, `ADMIN`).

## Interview talking points

1. **JWT**: login creates a signed token; `JwtAuthFilter` reads `Authorization` and sets the SecurityContext.
2. **Role-based access**: URLs are protected in `SecurityConfig`.
3. **JPA relationships**: User 1-1 Profile, User 1-1 Company, Company 1-N Job, Job 1-N Application, Application 1-1 Interview.
4. **Layered architecture**: controllers stay thin; services hold business rules; repositories talk to MySQL.
5. **DTOs**: entities stay inside the backend; React only receives records such as `JobDto`.

## Common issues

- **Cannot connect to MySQL**: start Docker or local MySQL and match username/password.
- **CORS error**: frontend must run on `http://localhost:5173` (allowed origin in `application.yml`).
- **401 on APIs**: login again; token expires after 24 hours.
