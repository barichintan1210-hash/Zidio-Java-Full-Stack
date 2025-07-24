@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private String role; // STUDENT, RECRUITER, ADMIN
    // Getters and Setters
}
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
public interface UserService {
    User register(User user);
    Optional<User> findByEmail(String email);
    List<User> findAll();
}
@Service
public class UserServiceImpl implements UserService {
    @Autowired private UserRepository userRepository;

    public User register(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(userService.register(user));
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return userService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId; // Comes from User Service
    private String university;
    private String course;
    private String resumeUrl;
    // Getters and Setters
}
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByUserId(Long userId);
}
public interface StudentService {
    Student createProfile(Student student);
    Optional<Student> getByUserId(Long userId);
}
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired private StudentRepository studentRepository;

    public Student createProfile(Student student) {
        return studentRepository.save(student);
    }

    public Optional<Student> getByUserId(Long userId) {
        return studentRepository.findByUserId(userId);
    }
}
@Entity
@Table(name = "recruiters")
public class Recruiter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String companyName;
    private String website;
    private String contactEmail;
    // Getters and Setters
}
public interface RecruiterRepository extends JpaRepository<Recruiter, Long> {
    Optional<Recruiter> findByUserId(Long userId);
}
public interface RecruiterService {
    Recruiter createRecruiter(Recruiter recruiter);
    Optional<Recruiter> getByUserId(Long userId);
}
@Service
public class RecruiterServiceImpl implements RecruiterService {
    @Autowired private RecruiterRepository recruiterRepository;

    public Recruiter createRecruiter(Recruiter recruiter) {
        return recruiterRepository.save(recruiter);
    }

    public Optional<Recruiter> getByUserId(Long userId) {
        return recruiterRepository.findByUserId(userId);
    }
}
@RestController
@RequestMapping("/api/recruiters")
public class RecruiterController {
    @Autowired private RecruiterService recruiterService;

    @PostMapping
    public ResponseEntity<Recruiter> createRecruiter(@RequestBody Recruiter recruiter) {
        return ResponseEntity.ok(recruiterService.createRecruiter(recruiter));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Recruiter> getByUserId(@PathVariable Long userId) {
        return recruiterService.getByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
@Entity
@Table(name = "jobs")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long recruiterId;
    private String title;
    private String description;
    private String location;
    private String type; // FULL_TIME, INTERNSHIP
    private LocalDate deadline;
    // Getters and Setters
}
public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByRecruiterId(Long recruiterId);
}
public interface JobService {
    Job postJob(Job job);
    List<Job> getJobsByRecruiter(Long recruiterId);
}
@Service
public class JobServiceImpl implements JobService {
    @Autowired private JobRepository jobRepository;

    public Job postJob(Job job) {
        return jobRepository.save(job);
    }

    public List<Job> getJobsByRecruiter(Long recruiterId) {
        return jobRepository.findByRecruiterId(recruiterId);
    }
}
@Entity
@Table(name = "applications")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long jobId;
    private Long studentId;
    private String status; // APPLIED, SHORTLISTED, REJECTED
    private LocalDate appliedOn;
    // Getters and Setters
}
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByStudentId(Long studentId);
    List<Application> findByJobId(Long jobId);
}
public interface RecruiterService {
    Recruiter createRecruiter(Recruiter recruiter);
    Optional<Recruiter> getByUserId(Long userId);
}
@Service
public class RecruiterServiceImpl implements RecruiterService {
    @Autowired private RecruiterRepository recruiterRepository;

    public Recruiter createRecruiter(Recruiter recruiter) {
        return recruiterRepository.save(recruiter);
    }

    public Optional<Recruiter> getByUserId(Long userId) {
        return recruiterRepository.findByUserId(userId);
    }
}
@RestController
@RequestMapping("/api/recruiters")
public class RecruiterController {
    @Autowired private RecruiterService recruiterService;

    @PostMapping
    public ResponseEntity<Recruiter> createRecruiter(@RequestBody Recruiter recruiter) {
        return ResponseEntity.ok(recruiterService.createRecruiter(recruiter));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Recruiter> getByUserId(@PathVariable Long userId) {
        return recruiterService.getByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
@Entity
@Table(name = "jobs")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long recruiterId;
    private String title;
    private String description;
    private String location;
    private String type; // FULL_TIME, INTERNSHIP
    private LocalDate deadline;
    // Getters and Setters
}
public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByRecruiterId(Long recruiterId);
}
public interface JobService {
    Job postJob(Job job);
    List<Job> getJobsByRecruiter(Long recruiterId);
}
@Service
public class JobServiceImpl implements JobService {
    @Autowired private JobRepository jobRepository;

    public Job postJob(Job job) {
        return jobRepository.save(job);
    }

    public List<Job> getJobsByRecruiter(Long recruiterId) {
        return jobRepository.findByRecruiterId(recruiterId);
    }
}
@RestController
@RequestMapping("/api/jobs")
public class JobController {
    @Autowired private JobService jobService;

    @PostMapping
    public ResponseEntity<Job> postJob(@RequestBody Job job) {
        return ResponseEntity.ok(jobService.postJob(job));
    }

    @GetMapping("/recruiter/{recruiterId}")
    public ResponseEntity<List<Job>> getJobsByRecruiter(@PathVariable Long recruiterId) {
        return ResponseEntity.ok(jobService.getJobsByRecruiter(recruiterId));
    }
}
@Entity
@Table(name = "applications")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long jobId;
    private Long studentId;
    private String status; // APPLIED, SHORTLISTED, REJECTED
    private LocalDate appliedOn;
    // Getters and Setters
}
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByStudentId(Long studentId);
    List<Application> findByJobId(Long jobId);
}
public interface ApplicationService {
    Application apply(Application application);
    List<Application> getByStudent(Long studentId);
    List<Application> getByJob(Long jobId);
}
@Service
public class ApplicationServiceImpl implements ApplicationService {
    @Autowired private ApplicationRepository applicationRepository;

    public Application apply(Application application) {
        return applicationRepository.save(application);
    }

    public List<Application> getByStudent(Long studentId) {
        return applicationRepository.findByStudentId(studentId);
    }

    public List<Application> getByJob(Long jobId) {
        return applicationRepository.findByJobId(jobId);
    }
}
@RestController
@RequestMapping("/api/applications")
public class ApplicationController {
    @Autowired private ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<Application> apply(@RequestBody Application application) {
        return ResponseEntity.ok(applicationService.apply(application));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Application>> getByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(applicationService.getByStudent(studentId));
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<Application>> getByJob(@PathVariable Long jobId) {
        return ResponseEntity.ok(applicationService.getByJob(jobId));
    }
}
@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String message;
    private boolean read;
    private LocalDateTime sentAt;
    // Getters and Setters
}
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserId(Long userId);
}
public interface NotificationService {
    Notification sendNotification(Notification notification);
    List<Notification> getNotifications(Long userId);
}
@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired private NotificationRepository notificationRepository;

    public Notification sendNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    public List<Notification> getNotifications(Long userId) {
        return notificationRepository.findByUserId(userId);
    }
}
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    @Autowired private NotificationService notificationService;

    @PostMapping
    public ResponseEntity<Notification> sendNotification(@RequestBody Notification notification) {
        return ResponseEntity.ok(notificationService.sendNotification(notification));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getNotifications(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getNotifications(userId));
    }
}


@Component
public class JwtUtil {
    private String secretKey = "your_secret_key";

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateToken(String token, String email) {
        final String extractedEmail = extractUsername(token);
        return (extractedEmail.equals(email) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }
}
