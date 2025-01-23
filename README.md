Movie Ticket Booking System - Backend Development

1. Project Overview
   This assignment aims to develop a RESTful API for a movie ticket booking system using
   Spring Boot. The system will manage movies, showtimes, users, and ticket bookings.
   This tests the candidate&#39;s ability to design a robust backend system.


2. Functional Requirements

   2.1 Movie Management

   Features:

        ○ Add new movies with details: title, genre, duration, rating, release_year
        ○ Update movie information.
        ○ Delete a movie.
        ○ Fetch a list of movies or specific movie details.

   2.2 Showtime Management

   Features:

        ○ Add showtimes for movies with details: movie, theater, start_time, end_time.
        ○ Update showtime details.
        ○ Delete a showtime.
        ○ Fetch all showtimes for a specific movie or theater.

   Constraints:

        ○ No overlapping showtimes for the same theater.

   2.3 User Management

   Features:

        ○ Register users with details such as name, email, password, and role (Admin/Customer).
        ○ Authenticate users via login.
        ○ Admin users can manage movies and showtimes.
        ○ Customers can book tickets.

    2.4 Ticket Booking System

    Features:

        ○ Allow customers to book tickets for available showtimes.
        ○ Track booking details: user, movie, showtime, seat_number, price.
        ○ Ensure no seat is booked twice for the same showtime.

    Constraints:

        ○ Maximum seats per showtime must be configurable.

3. Technical Requirements

    Frameworks and Tools


        ● Backend Framework: Spring Boot
        ● Database: H2, PostgreSQL, or MySQL (candidate&#39;s choice)
        ● Authentication &amp; Authorization: Spring Security
        ● Testing: JUnit and Mockito, Test Containers
        ● API Documentation: Swagger or detailed README, Postman collection

4. Technologies Used

        Java 17
        Spring Boot
        Spring MVC
        Spring Data JPA
        Spring Security
        Lombok
        H2 (as the database)
        Maven (for dependency management)

5. Database Setup

   This project uses H2 as the database. Follow these steps to set up the database:

        Connect by go to http://localhost:8080/h2-console
        If you want Update the database configuration, got to application.properties file.

6. Getting Started

    To set up the project on your local machine, follow these steps:


        Clone the repository: git clone https://github.com/OlegGavrIsr/manage_movie_system
        Run the application: mvn spring-boot:run
        The application will be accessible at http://localhost:8080.

7. API Endpoints for Postman

        Import Collection NewCollection.postman_collection.json file to Postmap

    