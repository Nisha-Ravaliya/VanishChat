<<<<<<< HEAD
# VanishChat

# About the Project

VanishChat is a web application built using Spring Boot (Java) for the backend and Thymeleaf/HTML for the frontend.
It supports user registration & login and a separate admin login + panel.

Users can register their business details, upload proof, and log in to access their dashboard.

Admins have a dedicated login and an admin panel to manage the system.

Security is implemented using Spring Security, BCrypt password hashing, and JWT tokens for API access.

# 🚀 Features
## User Features

Register with business details and upload business proof.

Login using email and password.

Update profile info, password, profile picture, and business documents.

Real-time chat with other users.

Messages automatically vanish after a set time (10s, 30s, 60s).

Dashboard displays all users and pending chats.

Notifications for incoming messages.

## Security

Passwords stored securely using BCrypt hashing.

JWT tokens for authentication and API access.

WebSocket communication secured with JWT.

# 🧰 Tech Stack

Backend: Java 17+, Spring Boot, Spring Security

Frontend: Thymeleaf, HTML, CSS, JavaScript

Real-time Messaging: WebSocket with SockJS + STOMP.js

Database: MySQL / H2 (or any supported database)

Build Tools: Maven or Gradle

Storage: File system for uploaded business proofs and profile images



## 📥 Getting Started

###Prerequisites

Java JDK installed (version 17 or higher)

Maven or Gradle configured

Database set up (if using an external DB)

(Optional) Upload directory configured (e.g., D:/Spring/VanishChat/uploads/)

Installation & Setup

Clone the repository:

git clone https://github.com/nisha-ravaliya/VanishChat.git
cd VanishChat


Configure database and application properties in application.properties or application.yml.

Build and run the project:

mvn clean install
mvn spring-boot:run


Open in browser:

User dashboard: http://localhost:8080/dashboard

[//]: # (Admin panel: http://localhost:8080/admin_panel)
# VanishChat
>>>>>>> 95601f0b4cabf84c72cb219a71822f2d6d31c574
