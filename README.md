# GradeSubmission
This is a Springboot application with rest endpoints which communicate with H2 in-memory relational database.
It creates, retrieve, update and delete entities from database using Spring JPA. It has the following Entity Relationships:
•	Bi-directional (One to Many) relationship between Student and Grades Entity.
•	Bi-directional (One to Many) relationship between Course and Grades.
•	Many to Many relationship between Students and Course.
o	A Many to Many relationship results a Joint Table between Students and Course.
Entities uses Lombok library and ConstraintValidator on Grade entity to prevent invalid scores for grade entries.
Exceptions:
•	RuntimeExceptions for entities to catch non-existing ID exception errors.
•	Global Exception Handler to control the flow of projects behavior when encountering an exception error.
OpenAPI documentation that can be viewed via Swagger-UI (http://localhost:8080/swagger-ui/index.html) once the application is running.
The documentation describes:
•	Path of the operations as well descriptions of what they do.
•	Parameters to be included for the operations to success.
•	The response to expect if the client makes a good request as well as bad request.
Integration Testing with MockMvc. Integration testing:
•	Maps the entire request and response lifecycle.
•	Covers all the applications layers from controller to services to repository.
