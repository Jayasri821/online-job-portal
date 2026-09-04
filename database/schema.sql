-- =============================================================================
-- CareerMatch AI – Smart Job Seeking and Career Guidance Platform
-- MySQL Database Schema & Initial Seed Data
-- =============================================================================

CREATE DATABASE IF NOT EXISTS careermatchdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE careermatchdb;

-- 1. USERS TABLE
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    phone VARCHAR(50),
    role VARCHAR(50) NOT NULL, -- JOB_SEEKER, EMPLOYER, ADMIN
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at DATETIME NOT NULL,
    updated_at DATETIME
);

-- 2. EMPLOYER PROFILES TABLE
CREATE TABLE IF NOT EXISTS employer_profiles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    company_name VARCHAR(255) NOT NULL,
    industry VARCHAR(255),
    location VARCHAR(255),
    website VARCHAR(255),
    description TEXT,
    logo_url VARCHAR(255),
    verified BOOLEAN NOT NULL DEFAULT FALSE,
    created_at DATETIME,
    updated_at DATETIME,
    CONSTRAINT fk_employer_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 3. JOB SEEKER PROFILES TABLE
CREATE TABLE IF NOT EXISTS job_seeker_profiles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    education VARCHAR(255),
    degree VARCHAR(255),
    graduation_year INT,
    skills VARCHAR(2000),
    experience_years INT DEFAULT 0,
    preferred_role VARCHAR(255),
    preferred_location VARCHAR(255),
    expected_salary INT,
    preferred_work_mode VARCHAR(50) DEFAULT 'HYBRID',
    career_objective TEXT,
    projects TEXT,
    certifications TEXT,
    internships TEXT,
    achievements TEXT,
    resume_url VARCHAR(255),
    resume_original_name VARCHAR(255),
    created_at DATETIME,
    updated_at DATETIME,
    CONSTRAINT fk_seeker_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 4. JOBS TABLE
CREATE TABLE IF NOT EXISTS jobs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employer_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    responsibilities TEXT,
    qualifications TEXT,
    required_skills VARCHAR(2000) NOT NULL,
    location VARCHAR(255),
    experience_years INT DEFAULT 0,
    salary_min INT,
    salary_max INT,
    job_type VARCHAR(50) NOT NULL DEFAULT 'FULL_TIME',
    work_mode VARCHAR(50) NOT NULL DEFAULT 'HYBRID',
    application_deadline DATE,
    status VARCHAR(50) NOT NULL DEFAULT 'OPEN',
    posted_at DATETIME NOT NULL,
    updated_at DATETIME,
    CONSTRAINT fk_job_employer FOREIGN KEY (employer_id) REFERENCES employer_profiles(id) ON DELETE CASCADE
);

-- 5. JOB APPLICATIONS TABLE
CREATE TABLE IF NOT EXISTS job_applications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    job_id BIGINT NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'APPLIED',
    resume_url VARCHAR(255),
    cover_letter TEXT,
    recruiter_notes TEXT,
    match_score_at_application DOUBLE,
    applied_at DATETIME NOT NULL,
    updated_at DATETIME,
    CONSTRAINT uq_user_job UNIQUE (user_id, job_id),
    CONSTRAINT fk_app_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_app_job FOREIGN KEY (job_id) REFERENCES jobs(id) ON DELETE CASCADE
);

-- 6. SAVED JOBS TABLE
CREATE TABLE IF NOT EXISTS saved_jobs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    job_id BIGINT NOT NULL,
    saved_at DATETIME NOT NULL,
    CONSTRAINT uq_saved_user_job UNIQUE (user_id, job_id),
    CONSTRAINT fk_saved_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_saved_job FOREIGN KEY (job_id) REFERENCES jobs(id) ON DELETE CASCADE
);

-- 7. INTERVIEW QUESTIONS TABLE
CREATE TABLE IF NOT EXISTS interview_questions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR(255) NOT NULL,
    target_role VARCHAR(255),
    question_type VARCHAR(50) NOT NULL, -- TECHNICAL, HR, PROJECT
    question VARCHAR(1000) NOT NULL,
    sample_answer TEXT,
    key_points TEXT,
    difficulty_level INT DEFAULT 1
);

-- 8. INTERVIEW PROGRESS TABLE
CREATE TABLE IF NOT EXISTS interview_progress (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    question_id BIGINT NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'NOT_STARTED',
    notes TEXT,
    updated_at DATETIME,
    CONSTRAINT uq_user_question UNIQUE (user_id, question_id),
    CONSTRAINT fk_progress_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_progress_question FOREIGN KEY (question_id) REFERENCES interview_questions(id) ON DELETE CASCADE
);

-- 9. NOTIFICATIONS TABLE
CREATE TABLE IF NOT EXISTS notifications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    message VARCHAR(1000) NOT NULL,
    type VARCHAR(50),
    is_read BOOLEAN NOT NULL DEFAULT FALSE,
    reference_id BIGINT,
    created_at DATETIME NOT NULL,
    CONSTRAINT fk_notif_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
