# VanishChat

## About the Project
VanishChat is a web‑application built using Spring Boot (Java) for backend and Thymeleaf/HTML for frontend, that supports **user registration & login** and a separate **admin login + panel**.
- Users can register their business details, upload proof, and login to access their dashboard.
- Admins have a dedicated login and an admin panel to manage the system.
- Security is implemented with Spring Security, BCrypt password hashing, and JWT tokens for API access.

## 🚀 Features
- User registration with file upload (business proof).
- User login via endpoint: `POST /api/login`.
- Admin login via endpoint: `POST /api/admin/login`.
- Role‑based access:
    - Normal users → Redirect to `/dashboard`.
    - Admin users → Redirect to `/admin_panel`.
- Secure password storage with BCrypt.
- REST API endpoints for login & register.
- Frontend pages for login, registration, dashboard, admin panel.

## 🧰 Tech Stack
- Java 17 (or the version you’re using)
- Spring Boot
- Spring Security
- JWT (JSON Web Tokens)
- Thymeleaf / HTML / CSS
- MySQL / H2 / any database (as configured)
- Maven or Gradle build tool
- (Optional) File system storage for uploads

## 📥 Getting Started

### Prerequisites
- JDK installed (version x.x)
- Maven or Gradle configured
- Database set up (if using external DB)
- (Optional) Upload directory configured (e.g., `D:/Spring/VanishChat/uploads/`)

### Installation & Setup
1. Clone the repository
   ```bash
   git clone https://github.com/your‑username/VanishChat.git
   cd VanishChat
