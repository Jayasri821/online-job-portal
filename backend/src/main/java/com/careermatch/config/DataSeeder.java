package com.careermatch.config;

import com.careermatch.entity.*;
import com.careermatch.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final JobSeekerProfileRepository seekerRepository;
    private final EmployerProfileRepository employerRepository;
    private final JobRepository jobRepository;
    private final JobApplicationRepository applicationRepository;
    private final SavedJobRepository savedJobRepository;
    private final InterviewQuestionRepository questionRepository;
    private final NotificationRepository notificationRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository,
                      JobSeekerProfileRepository seekerRepository,
                      EmployerProfileRepository employerRepository,
                      JobRepository jobRepository,
                      JobApplicationRepository applicationRepository,
                      SavedJobRepository savedJobRepository,
                      InterviewQuestionRepository questionRepository,
                      NotificationRepository notificationRepository,
                      PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.seekerRepository = seekerRepository;
        this.employerRepository = employerRepository;
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
        this.savedJobRepository = savedJobRepository;
        this.questionRepository = questionRepository;
        this.notificationRepository = notificationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            return; // Seed data already loaded
        }

        String defaultPass = passwordEncoder.encode("Password@123");

        // 1. ADMIN USER
        User admin = new User();
        admin.setEmail("admin@careermatch.com");
        admin.setPassword(defaultPass);
        admin.setFullName("Platform Administrator");
        admin.setPhone("+91 9876543210");
        admin.setRole(Role.ADMIN);
        userRepository.save(admin);

        // 2. FIVE EMPLOYERS
        List<EmployerProfile> employers = new ArrayList<>();

        // Employer 1
        User emp1User = new User();
        emp1User.setEmail("recruiter@google.com");
        emp1User.setPassword(defaultPass);
        emp1User.setFullName("Sundar Recruiter");
        emp1User.setPhone("+91 9876543211");
        emp1User.setRole(Role.EMPLOYER);
        userRepository.save(emp1User);

        EmployerProfile emp1 = new EmployerProfile();
        emp1.setUser(emp1User);
        emp1.setCompanyName("Google Cloud");
        emp1.setIndustry("Technology & Cloud Computing");
        emp1.setLocation("Hyderabad, India");
        emp1.setWebsite("https://cloud.google.com");
        emp1.setDescription("Leading global cloud and enterprise AI provider building scalable global infrastructure.");
        emp1.setVerified(true);
        employers.add(employerRepository.save(emp1));

        // Employer 2
        User emp2User = new User();
        emp2User.setEmail("hr@microsoft.com");
        emp2User.setPassword(defaultPass);
        emp2User.setFullName("Satya HR Lead");
        emp2User.setPhone("+91 9876543212");
        emp2User.setRole(Role.EMPLOYER);
        userRepository.save(emp2User);

        EmployerProfile emp2 = new EmployerProfile();
        emp2.setUser(emp2User);
        emp2.setCompanyName("Microsoft India");
        emp2.setIndustry("Software & Enterprise AI");
        emp2.setLocation("Bangalore, India");
        emp2.setWebsite("https://microsoft.com");
        emp2.setDescription("Empowering every person and organization on the planet to achieve more through modern software.");
        emp2.setVerified(true);
        employers.add(employerRepository.save(emp2));

        // Employer 3
        User emp3User = new User();
        emp3User.setEmail("talent@amazon.com");
        emp3User.setPassword(defaultPass);
        emp3User.setFullName("Amazon Talent Team");
        emp3User.setPhone("+91 9876543213");
        emp3User.setRole(Role.EMPLOYER);
        userRepository.save(emp3User);

        EmployerProfile emp3 = new EmployerProfile();
        emp3.setUser(emp3User);
        emp3.setCompanyName("Amazon Web Services");
        emp3.setIndustry("E-Commerce & Cloud");
        emp3.setLocation("Hyderabad, India");
        emp3.setWebsite("https://aws.amazon.com");
        emp3.setDescription("World leader in scalable cloud computing infrastructure, high volume distributed systems, and AI.");
        emp3.setVerified(true);
        employers.add(employerRepository.save(emp3));

        // Employer 4
        User emp4User = new User();
        emp4User.setEmail("careers@abctech.com");
        emp4User.setPassword(defaultPass);
        emp4User.setFullName("ABC Tech HR");
        emp4User.setPhone("+91 9876543214");
        emp4User.setRole(Role.EMPLOYER);
        userRepository.save(emp4User);

        EmployerProfile emp4 = new EmployerProfile();
        emp4.setUser(emp4User);
        emp4.setCompanyName("ABC Technologies");
        emp4.setIndustry("IT Services & Consulting");
        emp4.setLocation("Pune, India");
        emp4.setWebsite("https://abctech.example.com");
        emp4.setDescription("Fast-growing technology consulting and product engineering firm delivering scalable solutions.");
        emp4.setVerified(true);
        employers.add(employerRepository.save(emp4));

        // Employer 5
        User emp5User = new User();
        emp5User.setEmail("hiring@infosys.com");
        emp5User.setPassword(defaultPass);
        emp5User.setFullName("Infosys Campus Hiring");
        emp5User.setPhone("+91 9876543215");
        emp5User.setRole(Role.EMPLOYER);
        userRepository.save(emp5User);

        EmployerProfile emp5 = new EmployerProfile();
        emp5.setUser(emp5User);
        emp5.setCompanyName("Infosys Limited");
        emp5.setIndustry("Digital Services & Consulting");
        emp5.setLocation("Chennai, India");
        emp5.setWebsite("https://infosys.com");
        emp5.setDescription("Global leader in next-generation digital services and consulting for Fortune 500 enterprises.");
        emp5.setVerified(true);
        employers.add(employerRepository.save(emp5));

        // 3. TEN JOB SEEKERS
        List<User> seekers = new ArrayList<>();

        // Seeker 1 (Primary Demo User)
        User s1 = new User();
        s1.setEmail("seeker@careermatch.com");
        s1.setPassword(defaultPass);
        s1.setFullName("Jayasri");
        s1.setPhone("+91 9811122233");
        s1.setRole(Role.JOB_SEEKER);
        userRepository.save(s1);
        seekers.add(s1);

        JobSeekerProfile p1 = new JobSeekerProfile();
        p1.setUser(s1);
        p1.setDegree("B.Tech Computer Science");
        p1.setEducation("Jawaharlal Nehru Technological University");
        p1.setGraduationYear(2025);
        p1.setSkills("Java, Spring Boot, SQL, MySQL, REST API, HTML, CSS, JavaScript, Git");
        p1.setExperienceYears(1);
        p1.setPreferredRole("Java Full Stack Developer");
        p1.setPreferredLocation("Hyderabad");
        p1.setExpectedSalary(650000);
        p1.setPreferredWorkMode(WorkMode.HYBRID);
        p1.setCareerObjective("Motivated Computer Science graduate with strong hands-on experience in Java, Spring Boot microservices, relational databases, and REST APIs, seeking a Full Stack Developer role.");
        p1.setProjects("1. Online E-Commerce Platform (Java, Spring Boot, MySQL, REST API)\n2. Real-time Chat App (WebSocket, Spring Boot, HTML5/CSS3)\n3. Automated Task Tracker (Java, JDBC, Bootstrap)");
        p1.setCertifications("Oracle Certified Associate Java SE 8, Spring Professional Foundations, HackerRank Java 5-Star");
        p1.setInternships("Backend Developer Intern at SoftSolutions Inc (6 months) - Built REST APIs and optimized SQL database queries.");
        p1.setAchievements("Dean's List Academic Excellence, 1st place in University Hackathon 2024");
        p1.setResumeUrl("/uploads/sample-resume-jayasri.pdf");
        p1.setResumeOriginalName("Jayasri_Resume.pdf");
        seekerRepository.save(p1);

        // Seeker 2: Priya Patel (Frontend & React)
        User s2 = new User();
        s2.setEmail("priya.patel@example.com");
        s2.setPassword(defaultPass);
        s2.setFullName("Priya Patel");
        s2.setPhone("+91 9822233344");
        s2.setRole(Role.JOB_SEEKER);
        userRepository.save(s2);
        seekers.add(s2);

        JobSeekerProfile p2 = new JobSeekerProfile();
        p2.setUser(s2);
        p2.setDegree("B.Tech Information Technology");
        p2.setEducation("Anna University");
        p2.setGraduationYear(2024);
        p2.setSkills("React, JavaScript, TypeScript, HTML, CSS, Bootstrap, Tailwind CSS, Redux, REST API, Git");
        p2.setExperienceYears(1);
        p2.setPreferredRole("Frontend Developer");
        p2.setPreferredLocation("Bangalore");
        p2.setExpectedSalary(700000);
        p2.setPreferredWorkMode(WorkMode.WORK_FROM_HOME);
        p2.setCareerObjective("Creative frontend specialist focused on building dynamic, high performance, accessible web user interfaces.");
        p2.setProjects("1. Analytics Dashboard (React, Tailwind, Chart.js)\n2. SaaS Marketing Portal (Next.js, TypeScript)");
        p2.setCertifications("Meta Certified Front-End Developer");
        seekerRepository.save(p2);

        // Seeker 3: Aman Verma (Python & Data Science)
        User s3 = new User();
        s3.setEmail("aman.verma@example.com");
        s3.setPassword(defaultPass);
        s3.setFullName("Aman Verma");
        s3.setPhone("+91 9833344455");
        s3.setRole(Role.JOB_SEEKER);
        userRepository.save(s3);
        seekers.add(s3);

        JobSeekerProfile p3 = new JobSeekerProfile();
        p3.setUser(s3);
        p3.setDegree("B.Tech Data Science");
        p3.setEducation("IIT Madras");
        p3.setGraduationYear(2025);
        p3.setSkills("Python, Django, FastAPI, SQL, PostgreSQL, Pandas, NumPy, Machine Learning, PowerBI, Git");
        p3.setExperienceYears(0);
        p3.setPreferredRole("Data Analyst / Python Developer");
        p3.setPreferredLocation("Hyderabad");
        p3.setExpectedSalary(800000);
        p3.setPreferredWorkMode(WorkMode.HYBRID);
        seekerRepository.save(p3);

        // Seeker 4: Sneha Reddy (Cloud & DevOps)
        User s4 = new User();
        s4.setEmail("sneha.reddy@example.com");
        s4.setPassword(defaultPass);
        s4.setFullName("Sneha Reddy");
        s4.setPhone("+91 9844455566");
        s4.setRole(Role.JOB_SEEKER);
        userRepository.save(s4);
        seekers.add(s4);

        JobSeekerProfile p4 = new JobSeekerProfile();
        p4.setUser(s4);
        p4.setDegree("B.E. Computer Engineering");
        p4.setEducation("Osmania University");
        p4.setGraduationYear(2023);
        p4.setSkills("AWS, Docker, Kubernetes, Linux, CI/CD, Jenkins, Terraform, Python, Git");
        p4.setExperienceYears(2);
        p4.setPreferredRole("Cloud Engineer");
        p4.setPreferredLocation("Hyderabad");
        p4.setExpectedSalary(950000);
        p4.setPreferredWorkMode(WorkMode.WORK_FROM_HOME);
        seekerRepository.save(p4);

        // Seeker 5-10
        String[] names = {"Vikram Rao", "Ananya Deshmukh", "Karthik Nair", "Divya Menon", "Rohan Joshi", "Neha Gupta"};
        String[] roles = {"Backend Developer", "QA Automation Engineer", "Full Stack Developer", "Data Engineer", "Mobile App Developer", "Software Engineer"};
        String[] skillSets = {
                "Java, Spring Boot, Microservices, Kafka, SQL, Docker",
                "Selenium, Java, TestNG, Cucumber, Postman, SQL, JIRA",
                "Node.js, Express, React, MongoDB, JavaScript, REST API",
                "Python, Spark, SQL, BigQuery, Airflow, Data Warehousing",
                "Flutter, Dart, Android, Java, REST API, Firebase",
                "C++, Java, Data Structures, Algorithms, SQL, Git"
        };

        for (int i = 0; i < names.length; i++) {
            User s = new User();
            s.setEmail(names[i].toLowerCase().replace(" ", ".") + "@example.com");
            s.setPassword(defaultPass);
            s.setFullName(names[i]);
            s.setPhone("+91 98555" + (10000 + i));
            s.setRole(Role.JOB_SEEKER);
            userRepository.save(s);
            seekers.add(s);

            JobSeekerProfile p = new JobSeekerProfile();
            p.setUser(s);
            p.setDegree("B.Tech Computer Science");
            p.setEducation("National Institute of Technology");
            p.setGraduationYear(2024);
            p.setSkills(skillSets[i]);
            p.setExperienceYears(i % 3);
            p.setPreferredRole(roles[i]);
            p.setPreferredLocation(i % 2 == 0 ? "Hyderabad" : "Bangalore");
            p.setExpectedSalary(600000 + (i * 50000));
            p.setPreferredWorkMode(WorkMode.HYBRID);
            seekerRepository.save(p);
        }

        // 4. TWENTY DIVERSE JOBS
        List<Job> jobs = new ArrayList<>();

        // Job 1
        Job j1 = new Job();
        j1.setEmployer(emp4);
        j1.setTitle("Java Full Stack Developer");
        j1.setDescription("We are seeking a talented Java Full Stack Developer to build next-generation enterprise applications. You will work on robust backend architectures using Spring Boot and responsive frontends.");
        j1.setResponsibilities("- Design and implement microservices using Spring Boot & Java 17+\n- Develop responsive web pages using HTML5, CSS3, JavaScript and REST endpoints\n- Optimize SQL database queries and schema designs\n- Write unit tests and participate in code reviews");
        j1.setQualifications("- B.Tech/B.E in Computer Science or equivalent\n- Strong proficiency in Java, Spring Boot, SQL, and REST APIs\n- Knowledge of HTML, CSS, JavaScript");
        j1.setRequiredSkills("Java, Spring Boot, SQL, REST API, HTML, CSS, JavaScript");
        j1.setLocation("Hyderabad, India");
        j1.setExperienceYears(1);
        j1.setSalaryMin(600000);
        j1.setSalaryMax(900000);
        j1.setJobType(JobType.FULL_TIME);
        j1.setWorkMode(WorkMode.HYBRID);
        j1.setApplicationDeadline(LocalDate.now().plusDays(5));
        j1.setStatus(JobStatus.OPEN);
        jobs.add(jobRepository.save(j1));

        // Job 2
        Job j2 = new Job();
        j2.setEmployer(emp1);
        j2.setTitle("Software Engineer - Cloud Systems");
        j2.setDescription("Join Google Cloud to build large-scale cloud infrastructure, distributed microservices, and high-performance backend systems.");
        j2.setResponsibilities("- Build scalable backend services in Java/Go\n- Work with containerized cloud environments using Kubernetes & Docker\n- Collaborate with global engineering teams");
        j2.setQualifications("- B.Tech or M.Tech in CS/IT\n- Strong grasp of Data Structures, Algorithms, Java, and Distributed Systems");
        j2.setRequiredSkills("Java, Cloud, Docker, Kubernetes, SQL, Data Structures");
        j2.setLocation("Hyderabad, India");
        j2.setExperienceYears(1);
        j2.setSalaryMin(1400000);
        j2.setSalaryMax(2200000);
        j2.setJobType(JobType.FULL_TIME);
        j2.setWorkMode(WorkMode.HYBRID);
        j2.setApplicationDeadline(LocalDate.now().plusDays(2)); // CLOSING SOON!
        j2.setStatus(JobStatus.OPEN);
        jobs.add(jobRepository.save(j2));

        // Job 3
        Job j3 = new Job();
        j3.setEmployer(emp2);
        j3.setTitle("Backend Engineer (Java / Spring)");
        j3.setDescription("Microsoft is looking for a Backend Engineer to power enterprise productivity suites and secure API platforms.");
        j3.setResponsibilities("- Develop secure, scalable RESTful APIs\n- Integrate relational and NoSQL databases\n- Monitor system performance and implement caching");
        j3.setQualifications("- Experience in core Java, Spring Boot, and REST API development");
        j3.setRequiredSkills("Java, Spring Boot, REST API, SQL, Microservices, Git");
        j3.setLocation("Bangalore, India");
        j3.setExperienceYears(1);
        j3.setSalaryMin(1200000);
        j3.setSalaryMax(1800000);
        j3.setJobType(JobType.FULL_TIME);
        j3.setWorkMode(WorkMode.WORK_FROM_HOME);
        j3.setApplicationDeadline(LocalDate.now().plusDays(10));
        j3.setStatus(JobStatus.OPEN);
        jobs.add(jobRepository.save(j3));

        // Job 4
        Job j4 = new Job();
        j4.setEmployer(emp3);
        j4.setTitle("AWS Cloud Support Associate");
        j4.setDescription("Help worldwide enterprise customers troubleshoot and architect solutions on AWS infrastructure and cloud services.");
        j4.setResponsibilities("- Diagnose cloud architecture problems\n- Script automation tasks using Python and Bash\n- Manage Linux and networking configurations");
        j4.setQualifications("- Understanding of AWS, Linux, Networking, and scripting");
        j4.setRequiredSkills("AWS, Linux, Python, Networking, Cloud");
        j4.setLocation("Hyderabad, India");
        j4.setExperienceYears(0);
        j4.setSalaryMin(700000);
        j4.setSalaryMax(1100000);
        j4.setJobType(JobType.FULL_TIME);
        j4.setWorkMode(WorkMode.HYBRID);
        j4.setApplicationDeadline(LocalDate.now().plusDays(1)); // CLOSING SOON!
        j4.setStatus(JobStatus.OPEN);
        jobs.add(jobRepository.save(j4));

        // Job 5
        Job j5 = new Job();
        j5.setEmployer(emp5);
        j5.setTitle("Associate Software Engineer - Java");
        j5.setDescription("Infosys Campus Hiring is looking for fresh talent ready to engineer scalable digital systems across global clients.");
        j5.setResponsibilities("- Software development under agile teams\n- Database coding and bug fixing");
        j5.setQualifications("- B.Tech / MCA 2024 or 2025 batch");
        j5.setRequiredSkills("Java, SQL, HTML, CSS, JavaScript, Git");
        j5.setLocation("Chennai, India");
        j5.setExperienceYears(0);
        j5.setSalaryMin(450000);
        j5.setSalaryMax(650000);
        j5.setJobType(JobType.FULL_TIME);
        j5.setWorkMode(WorkMode.ON_SITE);
        j5.setApplicationDeadline(LocalDate.now().plusDays(15));
        j5.setStatus(JobStatus.OPEN);
        jobs.add(jobRepository.save(j5));

        // Job 6-20 (Additional jobs covering all domains)
        String[][] additionalJobs = {
                {"Frontend React Developer", "ABC Technologies", "Pune, India", "React, JavaScript, TypeScript, HTML, CSS, Redux", "600000", "950000", "1", "WORK_FROM_HOME"},
                {"Python Backend Developer", "Microsoft India", "Bangalore, India", "Python, Django, FastAPI, SQL, REST API, Docker", "800000", "1300000", "1", "HYBRID"},
                {"Data Analyst", "Amazon Web Services", "Hyderabad, India", "SQL, Python, PowerBI, Excel, Pandas, Data Analysis", "550000", "850000", "0", "HYBRID"},
                {"DevOps Engineer", "Google Cloud", "Hyderabad, India", "Docker, Kubernetes, AWS, CI/CD, Linux, Terraform", "1100000", "1700000", "2", "WORK_FROM_HOME"},
                {"QA Automation Tester", "Infosys Limited", "Chennai, India", "Selenium, Java, TestNG, SQL, Postman, Git", "500000", "750000", "1", "ON_SITE"},
                {"Junior Java Developer", "ABC Technologies", "Hyderabad, India", "Java, SQL, Spring Boot, Git, OOP", "500000", "700000", "0", "HYBRID"},
                {"Full Stack Engineer (MERN)", "Microsoft India", "Bangalore, India", "React, Node.js, Express, MongoDB, JavaScript", "750000", "1200000", "1", "HYBRID"},
                {"Machine Learning Engineer", "Google Cloud", "Bangalore, India", "Python, TensorFlow, PyTorch, Machine Learning, SQL", "1500000", "2400000", "2", "HYBRID"},
                {"Database Administrator (MySQL)", "Amazon Web Services", "Hyderabad, India", "MySQL, SQL, Database Tuning, Linux, Backup", "700000", "1100000", "2", "ON_SITE"},
                {"Mobile App Developer (Flutter)", "ABC Technologies", "Pune, India", "Flutter, Dart, REST API, Firebase, Mobile", "600000", "900000", "1", "WORK_FROM_HOME"},
                {"Spring Boot Microservices Architect", "Infosys Limited", "Hyderabad, India", "Java, Spring Boot, Microservices, Kafka, Docker, Kubernetes", "1200000", "1900000", "3", "HYBRID"},
                {"Graduate Trainee Engineer", "Infosys Limited", "Bangalore, India", "Java, Python, C++, SQL, Problem Solving", "400000", "550000", "0", "ON_SITE"},
                {"Cloud Security Analyst", "Microsoft India", "Hyderabad, India", "AWS, Cloud, Security, Linux, Python", "900000", "1500000", "1", "HYBRID"},
                {"UI/UX Frontend Specialist", "ABC Technologies", "Bangalore, India", "HTML, CSS, JavaScript, Bootstrap, Figma, UI/UX", "500000", "800000", "1", "WORK_FROM_HOME"},
                {"Site Reliability Engineer (SRE)", "Google Cloud", "Hyderabad, India", "Linux, Python, Docker, Kubernetes, Monitoring, Cloud", "1300000", "2000000", "2", "HYBRID"}
        };

        for (int i = 0; i < additionalJobs.length; i++) {
            String[] aj = additionalJobs[i];
            Job j = new Job();
            j.setEmployer(employers.get(i % employers.size()));
            j.setTitle(aj[0]);
            j.setDescription("Exciting opportunity for a " + aj[0] + " to contribute to high-impact projects at " + employers.get(i % employers.size()).getCompanyName() + ".");
            j.setResponsibilities("- Participate in design and feature implementation\n- Collaborate with team members across development lifecycle\n- Ensure code quality, documentation, and performance");
            j.setQualifications("- Relevant educational degree and foundational programming skills");
            j.setRequiredSkills(aj[3]);
            j.setLocation(aj[2]);
            j.setSalaryMin(Integer.parseInt(aj[4]));
            j.setSalaryMax(Integer.parseInt(aj[5]));
            j.setExperienceYears(Integer.parseInt(aj[6]));
            j.setJobType(JobType.FULL_TIME);
            j.setWorkMode(WorkMode.valueOf(aj[7]));
            j.setApplicationDeadline(LocalDate.now().plusDays(3 + (i * 2)));
            j.setStatus(JobStatus.OPEN);
            jobs.add(jobRepository.save(j));
        }

        // 5. SAMPLE APPLICATIONS IN VARIOUS STAGES
        JobApplication a1 = new JobApplication();
        a1.setCandidate(s1);
        a1.setJob(j1);
        a1.setStatus(ApplicationStatus.INTERVIEW);
        a1.setResumeUrl("/uploads/sample-resume-jayasri.pdf");
        a1.setCoverLetter("I am very excited to apply for the Java Full Stack Developer role. My hands-on Spring Boot and SQL projects align well with your tech stack.");
        a1.setRecruiterNotes("Strong Java core skills and good answers during initial technical round. Scheduled for Round 2 technical interview.");
        a1.setMatchScoreAtApplication(91.0);
        applicationRepository.save(a1);

        JobApplication a2 = new JobApplication();
        a2.setCandidate(s1);
        a2.setJob(j3);
        a2.setStatus(ApplicationStatus.SHORTLISTED);
        a2.setResumeUrl("/uploads/sample-resume-jayasri.pdf");
        a2.setCoverLetter("Applying for Backend Engineer position at Microsoft.");
        a2.setRecruiterNotes("Resume profile shortlisted for technical assessment test.");
        a2.setMatchScoreAtApplication(87.0);
        applicationRepository.save(a2);

        JobApplication a3 = new JobApplication();
        a3.setCandidate(s1);
        a3.setJob(j5);
        a3.setStatus(ApplicationStatus.ASSESSMENT);
        a3.setResumeUrl("/uploads/sample-resume-jayasri.pdf");
        a3.setCoverLetter("Applying for Campus Hiring Java opening.");
        a3.setRecruiterNotes("Online aptitude and coding test link sent.");
        a3.setMatchScoreAtApplication(94.0);
        applicationRepository.save(a3);

        JobApplication a4 = new JobApplication();
        a4.setCandidate(s2);
        a4.setJob(jobs.get(5)); // Frontend React Developer
        a4.setStatus(ApplicationStatus.SELECTED);
        a4.setResumeUrl("/uploads/sample-resume-priya.pdf");
        a4.setCoverLetter("Excited to apply for Frontend React role.");
        a4.setRecruiterNotes("Offer letter issued! Candidate accepted.");
        a4.setMatchScoreAtApplication(95.0);
        applicationRepository.save(a4);

        // Saved Jobs for Seeker 1
        SavedJob sj1 = new SavedJob();
        sj1.setCandidate(s1);
        sj1.setJob(j2);
        savedJobRepository.save(sj1);

        SavedJob sj2 = new SavedJob();
        sj2.setCandidate(s1);
        sj2.setJob(j4);
        savedJobRepository.save(sj2);

        // 6. INTERVIEW QUESTION BANK
        seedInterviewQuestions();

        // 7. NOTIFICATIONS
        Notification n1 = new Notification();
        n1.setUser(s1);
        n1.setTitle("Interview Scheduled!");
        n1.setMessage("ABC Technologies has scheduled your Round 2 Interview for Java Full Stack Developer on Monday.");
        n1.setType("INTERVIEW");
        n1.setReferenceId(a1.getId());
        notificationRepository.save(n1);

        Notification n2 = new Notification();
        n2.setUser(s1);
        n2.setTitle("Job Deadline Alert!");
        n2.setMessage("Software Engineer - Cloud Systems at Google Cloud closes in 2 days. Apply now!");
        n2.setType("DEADLINE_ALERT");
        n2.setReferenceId(j2.getId());
        notificationRepository.save(n2);
    }

    private void seedInterviewQuestions() {
        List<InterviewQuestion> list = new ArrayList<>();

        // TECHNICAL QUESTIONS - Java & Spring Boot
        InterviewQuestion q1 = new InterviewQuestion();
        q1.setCategory("Java");
        q1.setTargetRole("Java Developer");
        q1.setQuestionType(QuestionType.TECHNICAL);
        q1.setQuestion("Explain the differences between HashMap, ConcurrentHashMap, and HashTable in Java.");
        q1.setSampleAnswer("HashMap is non-synchronized and allows one null key. HashTable is thread-safe using method-level locks but slow. ConcurrentHashMap uses bucket-level/segment-level locking allowing concurrent reads and high throughput.");
        q1.setKeyPoints("Synchronization, Concurrency, Null keys, Segment Locking, Performance");
        q1.setDifficultyLevel(2);
        list.add(q1);

        InterviewQuestion q2 = new InterviewQuestion();
        q2.setCategory("Spring Boot");
        q2.setTargetRole("Java Developer");
        q2.setQuestionType(QuestionType.TECHNICAL);
        q2.setQuestion("How does Spring Boot auto-configuration work under the hood?");
        q2.setSampleAnswer("Spring Boot scans @EnableAutoConfiguration using SpringFactoriesLoader/AutoConfigurationImportSelector to inspect classpath dependencies and conditional annotations like @ConditionalOnClass and @ConditionalOnMissingBean to configure beans automatically.");
        q2.setKeyPoints("@EnableAutoConfiguration, @Conditional annotations, spring.factories, Starter dependencies");
        q2.setDifficultyLevel(2);
        list.add(q2);

        InterviewQuestion q3 = new InterviewQuestion();
        q3.setCategory("SQL");
        q3.setTargetRole("ALL");
        q3.setQuestionType(QuestionType.TECHNICAL);
        q3.setQuestion("What is the difference between WHERE and HAVING clauses in SQL?");
        q3.setSampleAnswer("WHERE filters individual rows before grouping (GROUP BY) takes place, and cannot use aggregate functions directly. HAVING filters summarized groups after aggregation.");
        q3.setKeyPoints("Row filtering vs Group filtering, Aggregate functions, Execution order");
        q3.setDifficultyLevel(1);
        list.add(q3);

        InterviewQuestion q4 = new InterviewQuestion();
        q4.setCategory("REST API");
        q4.setTargetRole("ALL");
        q4.setQuestionType(QuestionType.TECHNICAL);
        q4.setQuestion("What makes a REST API idempotent? Which HTTP methods are idempotent?");
        q4.setSampleAnswer("An operation is idempotent if making multiple identical requests has the same outcome as making a single request. GET, PUT, DELETE, and HEAD are idempotent, while POST is typically not idempotent.");
        q4.setKeyPoints("Idempotency definition, GET/PUT/DELETE vs POST, Side effects");
        q4.setDifficultyLevel(2);
        list.add(q4);

        InterviewQuestion q5 = new InterviewQuestion();
        q5.setCategory("Data Structures");
        q5.setTargetRole("ALL");
        q5.setQuestionType(QuestionType.TECHNICAL);
        q5.setQuestion("How would you detect a cycle in a singly Linked List?");
        q5.setSampleAnswer("Use Floyd's Cycle Detection Algorithm (Slow and Fast Pointer approach). Move slow pointer 1 step and fast pointer 2 steps. If they meet at the same node, a cycle exists (O(N) time, O(1) space).");
        q5.setKeyPoints("Floyd's Tortoise & Hare, Two Pointers, O(1) auxiliary space");
        q5.setDifficultyLevel(2);
        list.add(q5);

        // HR QUESTIONS
        InterviewQuestion q6 = new InterviewQuestion();
        q6.setCategory("HR");
        q6.setTargetRole("ALL");
        q6.setQuestionType(QuestionType.HR);
        q6.setQuestion("Tell me about yourself and your career journey.");
        q6.setSampleAnswer("Structure using Present-Past-Future: Describe your current academic/professional focus, highlight 1-2 major technical achievements and projects, and express why you are excited about this specific company and role.");
        q6.setKeyPoints("Concise (90 seconds), Relevant achievements, Enthusiasm, Future alignment");
        q6.setDifficultyLevel(1);
        list.add(q6);

        InterviewQuestion q7 = new InterviewQuestion();
        q7.setCategory("HR");
        q7.setTargetRole("ALL");
        q7.setQuestionType(QuestionType.HR);
        q7.setQuestion("Why should we hire you for this role over other candidates?");
        q7.setSampleAnswer("Connect your technical competencies directly to the job requirements. Emphasize your proactive problem-solving, rapid adaptability to new technologies, and demonstrated track record through hands-on project builds.");
        q7.setKeyPoints("Direct skill alignment, Passion for building, Cultural value add");
        q7.setDifficultyLevel(1);
        list.add(q7);

        InterviewQuestion q8 = new InterviewQuestion();
        q8.setCategory("HR");
        q8.setTargetRole("ALL");
        q8.setQuestionType(QuestionType.HR);
        q8.setQuestion("Where do you see yourself in 3 to 5 years?");
        q8.setSampleAnswer("Express a desire to grow into a strong end-to-end technical contributor or tech lead, mastering scalable system architecture and mentoring junior engineers while delivering high-impact business value.");
        q8.setKeyPoints("Continuous learning, Leadership trajectory, Commitment to growth");
        q8.setDifficultyLevel(1);
        list.add(q8);

        // PROJECT QUESTIONS
        InterviewQuestion q9 = new InterviewQuestion();
        q9.setCategory("Project");
        q9.setTargetRole("ALL");
        q9.setQuestionType(QuestionType.PROJECT);
        q9.setQuestion("Explain the architecture of your most significant project and why you chose your tech stack.");
        q9.setSampleAnswer("Break it down using STAR method: Situation (problem solved), Task (your role), Action (architecture choices: e.g., Spring Boot REST + MySQL + Bootstrap), and Result (outcomes, performance, learnings).");
        q9.setKeyPoints("STAR method, Architecture trade-offs, Technical rationale");
        q9.setDifficultyLevel(2);
        list.add(q9);

        InterviewQuestion q10 = new InterviewQuestion();
        q10.setCategory("Project");
        q10.setTargetRole("ALL");
        q10.setQuestionType(QuestionType.PROJECT);
        q10.setQuestion("What was the biggest technical roadblock you encountered in your project, and how did you resolve it?");
        q10.setSampleAnswer("Explain a realistic technical challenge (e.g. database N+1 query issue, JWT token invalidation on logout, CORS policy error), the debugging process you followed, and the permanent resolution.");
        q10.setKeyPoints("Root cause analysis, Debugging methodology, Concrete solution");
        q10.setDifficultyLevel(2);
        list.add(q10);

        questionRepository.saveAll(list);
    }
}
