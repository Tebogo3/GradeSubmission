package com.ltp.gradesubmission.web;

import com.ltp.gradesubmission.entity.Course;
import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.exception.ErrorResponse;
import com.ltp.gradesubmission.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
/**
 * @Tag allow us to give the name and description of the controller.
 * @Operation provide each operation a summary and description
 * @ApiResponse documents how each operation respond
 *   returns - status code of success = 200
 *             status code of
 *
 *             JSON array of Contact resources
 *
 * @Schema contains all the properties of our entities
 * Or the @Schema can be the content of the Response Class
 *
 * Response Type produces JSON content: MediaType.APPLICATION_JSON_VALUE
 **/
@Tag(name = "Student Controller", description = " CRUD Student Management")
@AllArgsConstructor
@RestController
@RequestMapping("/student")
public class StudentController {
    StudentService studentService;
/*
   * handles GET requests made on /student/{id}.
   * with return type: ResponseEntity<Student>.
   * named getStudent.
   * that accepts a @PathVariable Long id.
   * that returns a ResponseEntity with no data and a status of 200.
*/
        @Operation(summary = "Get Student by ID", description = "Returns a student based on an ID")
        @ApiResponses(value = {
        @ApiResponse(responseCode = "404", description = "Student doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "200", description = "Successful retrieval of student", content = @Content(schema = @Schema(implementation = Student.class))),
                        })
    @GetMapping(value = "/{Id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> getStudent (@PathVariable Long Id){
        return new ResponseEntity<>(studentService.getStudent(Id), HttpStatus.ACCEPTED.OK);
    }
    /*
     * handles POST requests made on /student.
     * with return type ResponseEntity<Student>
     * named saveStudent
     * param: @Valid - validate payload based of constraints from Pojo Class,
     *        @RequestBody - deserializes incoming JSON properties into a Student object
     * returns a ResponseEntity which re-serializes the object into a
     * JSON with a status code of 201.
     */
    @Operation(summary = "Create Student", description = "Creates a student from the provided payload")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful creation of student"),
            @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful submission", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> saveStudent(@Valid @RequestBody Student student){
        return new ResponseEntity<>(studentService.saveStudent(student),HttpStatus.CREATED);
    }
    @Operation(summary = "Update Student", description = "Update Student based on ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: Student Updated"),
            @ApiResponse(responseCode = "404", description = "Not Found: unsuccessful submission", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> updateStudent(@Valid @RequestBody Student student, @PathVariable Long id){
        return new ResponseEntity<>(studentService.updatStudent(id,student.getName(),student.getBirthDate()), HttpStatus.OK);
    }
    /*
     * handles DELETE requests made on /student/{id}
     * with return type ResponseEntity<HTTPStatus>
     * method name: deleteStudent
     * param: @PathVariable Long id
     * returns ResponseEntity with a status code of 204
     */
    @Operation(summary = "Delete Student", description = "Deletes Student based on an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "No Content: Successful deletion of student"),
            @ApiResponse(responseCode = "404", description = "Not Found: Student doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping(value = "/{Id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable Long Id){
        studentService.deleteStudent(Id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*
     * handles GET requests made on /student/all
     * with return type: ResponseEntity<List<Student>>
     * method name: getStudents
     * returns a ResponseEntity with status code of 200
     */
    @Operation(summary = "Retrieves Students", description = "Provides a list of all students")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of students",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Student.class))))
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<List<Student>> getStudents(){
         List<Student> students = studentService.getStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
        }

    /*
     * handles GET requests made on /{id}/courses
     * with return type: ResponseEntity<Set<Course>>
     * method name: getEnrolledCourses
     * returns a ResponseEntity with status code of 200
     */
    @Operation(summary = "Enrolled Courses", description = "Returns a list of courses enrolled for student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "No enrolled course(s) for student", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful retrieval of student for courses", content = @Content(schema = @Schema(implementation = Student.class))),
    })
    @GetMapping(value = "/{id}/courses", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Course>> getEnrolledCourses(@PathVariable Long id){
        return new ResponseEntity<>(studentService.getEnrolledCourses(id), HttpStatus.OK);
    }
}

