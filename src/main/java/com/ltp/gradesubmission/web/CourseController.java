package com.ltp.gradesubmission.web;

import com.ltp.gradesubmission.entity.Course;
import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.exception.ErrorResponse;
import com.ltp.gradesubmission.service.CourseService;
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

@Tag(name = "Course Controller", description = " CRUD Student Management")
@AllArgsConstructor
@RestController
@RequestMapping("/course")
public class CourseController {

    CourseService courseService;
    /*
     * handles GET requests made on /course/{id}.
     * with return type: ResponseEntity<Student>.
     * named getCourse.
     * that accepts a @PathVariable Long id.
     * that returns a ResponseEntity with no data and a status of 200.
     */
    @Operation(summary = "Get Course by ID", description = "Returns a course based on an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Course doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful retrieval of a course", content = @Content(schema = @Schema(implementation = Student.class))),
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Course> getCourse(@PathVariable Long id) {
        Course course = courseService.getCourse(id);
        return new ResponseEntity<>(course,HttpStatus.OK);
    }

    /*
     * handles POST requests made on /course.
     * with return type ResponseEntity<Course>
     * named saveStudent
     * deserializes incoming JSON properties into a Course object.
     * returns a ResponseEntity which re-serializes the object into a
     * JSON with a status code of 201.
     */
    @Operation(summary = "Create Course", description = "Creates a course from the provided payload")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful creation of a course"),
            @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful submission", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Course> saveCourse(@Valid @RequestBody Course course) {
        return new ResponseEntity<>(courseService.saveCourse(course), HttpStatus.CREATED);
    }

    /*
     * handles PUT requests made on /{courseId}/student/{studentId}
     * with return type ResponseEntity<HTTPStatus>
     * method name: enrollStudentToCourse
     * param: @PathVariable studentId, @PathVariable Long courseId
     * returns ResponseEntity with a status code of 200
     */
    @Operation(summary = "Enroll Student to Course", description = "Enrolls Students to Courses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student enrolled successful"),
            @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful submission", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping(value = "/{courseId}/student/{studentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Course> enrollStudentToCourse(@PathVariable Long studentId, @PathVariable Long courseId){
        return new ResponseEntity<>(courseService.addStudentToCourse(studentId,courseId), HttpStatus.OK);
    }
    /*
     * handles DELETE requests made on /course/{id}
     * with return type ResponseEntity<HTTPStatus>
     * method name: deleteCourse
     * param: @PathVariable Long id
     * returns ResponseEntity with a status code of 204
     */
    @Operation(summary = "Delete Course", description = "Deletes course based on an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful deletion of a course"),
            @ApiResponse(responseCode = "404", description = "Course doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    /*
     * handles GET requests made on /course/all
     * with return type: ResponseEntity<List<Student>>
     * method name: getCourses
     * returns a ResponseEntity with status code of 200
     */
    @Operation(summary = "Retrieves Courses", description = "Provides a list of all courses")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of course",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Student.class))))
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Course>> getCourses() {
//        List<Course> courses = courseService.getCourses();
        return new ResponseEntity<>(courseService.getCourses(), HttpStatus.OK);
    }
    /*
     * handles GET requests made on /course/{id}/students
     * with return type: ResponseEntity<Set<Course>>
     * method name: getEnrolledStudents
     * returns a ResponseEntity with status code of 200
     */
    @Operation(summary = "Enrolled Students", description = "Returns a list of enrolled students for a course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "No enrolled course for student(s)", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful retrieval of a course for student", content = @Content(schema = @Schema(implementation = Student.class))),
    })
    @GetMapping(value = "/{id}/students", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Student>> getEnrolledStudents(@PathVariable Long id){
        return new ResponseEntity<>(courseService.getEnrolledStudents(id), HttpStatus.OK);
    }
}