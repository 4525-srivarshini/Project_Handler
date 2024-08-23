Supervisor Allocation System

**Introduction**

The **Supervisor Allocation System** is a Spring Boot application designed to automate and streamline the process of assigning supervisors to student groups based on criteria such as CGPA and specialization.
This system aims to enhance efficiency by automatically dividing students into groups and matching them with the most suitable supervisors.

**Features**

- **Student Group Division**: Automatically divide students into groups based on their CGPA.
- **Supervisor Allocation**: Assign supervisors to student groups according to their specializations and other criteria.
- **Admin Management**: Admins can update student and supervisor data, ensuring the allocation process is always up-to-date.
- **Data Persistence**: Leverages Spring Data JPA for managing student and supervisor data in a relational database.

## Technologies Used

- **Spring Boot 2.7.7**: The framework used to build the application, providing an out-of-the-box setup for Spring-based applications.
- **Maven**: Used as the build automation tool, managing dependencies, and building the project.
- **Spring Data JPA**: Used for data persistence and interaction with the relational database.

## Getting Started

### Prerequisites

- Java 8
- Maven 3.6
- A relational database (e.g., MySQL, PostgreSQL)

### Installation

1. **Clone the repository**:

   ```bash
   [git clone https://github.com/yourusername/supervisor-allocation-system.git](https://github.com/4525-srivarshini/Project_Handler)
  
   cd supervisor-allocation-system
   ```

2. **Build the project**:

   Use Maven to build the project and resolve dependencies:

   ```bash
   mvn clean install
   ```

3. **Configure the Database**:

   Update the `application.properties` or `application.yml` file with your database configuration:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/yourdatabase
   spring.datasource.username=yourusername
   spring.datasource.password=yourpassword
   spring.jpa.hibernate.ddl-auto=update
   ```

4. **Run the application**:

   Start the Spring Boot application using Maven:

   ```bash
   mvn spring-boot:run
   ```

   Or, run the generated JAR file:

   ```bash
   java -jar target/supervisor-allocation-system-0.0.1-SNAPSHOT.jar
   ```

## Usage

1. **Access the Application**:

   Once the application is running, you can access it via `http://localhost:8080`.

2. **Admin Operations**:

   - Update student and supervisor data.
   - Monitor and manage the allocation of supervisors.

3. **View Allocations**:

   - Access the allocated supervisor details for each student group.
---

Feel free to adjust the sections and content as per your specific project requirements!
