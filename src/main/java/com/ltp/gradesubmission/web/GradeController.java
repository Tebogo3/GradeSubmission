package com.ltp.gradesubmission.web;

import com.ltp.gradesubmission.entity.Grade;
import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.exception.ErrorResponse;
import com.ltp.gradesubmission.service.GradeService;
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

@Tag(name = "Grade Controller", description = " CRUD Grade Management")
@AllArgsConstructor
@RestController
@RequestMapping("/grade")
public class GradeController {
    GradeService gradeService;
    @Operation(summary = "Get Student Grade", description = "Returns student grade for a course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Student enrolled successful"),
            @ApiResponse(responseCode = "404", description = "Not Found: unsuccessful submission", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping(value = "/student/{studentId}/course/{courseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Grade> getGrade(@PathVariable Long studentId, @PathVariable Long courseId){
        return new ResponseEntity<>(gradeService.getGrade(studentId,courseId),HttpStatus.OK);
    }
    @Operation(summary = "Create Student Grade", description = "Create student course grade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created: Student grade created"),
            @ApiResponse(responseCode = "404", description = "Not Found: Student and Course ID does not exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping(value = "/student/{studentId}/course/{courseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Grade> saveGrade(@Valid @RequestBody Grade grade, @PathVariable Long studentId, @PathVariable Long courseId){
        return new ResponseEntity<>(gradeService.saveGrade(grade, studentId, courseId), HttpStatus.CREATED);
    }
    @Operation(summary = "Update Student Course Grade", description = "Update student course grade by IDs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: Record Updated"),
            @ApiResponse(responseCode = "404", description = "Not Found: Student and Course ID does not exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping(value = "/student/{studentId}/course/{courseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Grade> updateGrade(@Valid @RequestBody Grade grade, @PathVariable Long studentId, @PathVariable Long courseId){
        return new ResponseEntity<>(gradeService.updateGrade(grade.getScore(),studentId,courseId),HttpStatus.OK);
    }
    @Operation(summary = "Delete Student Course Grade", description = "Deletes student course grade by IDs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content: Deleted"),
            @ApiResponse(responseCode = "404", description = "Not Found: Student and Course ID does not exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping(value = "/student/{studentId}/course/{courseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> deleteGrade(@PathVariable Long studentId, @PathVariable Long courseId){
          gradeService.deleteGrade(studentId,courseId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get Student Grade(s)", description = "Returns student grades for course(s)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Returns a list of Student grades"),
            @ApiResponse(responseCode = "404", description = "Not Found: Student ID does not exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping(value = "/student/{studentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Grade>> getStudentGrades(@PathVariable Long studentId){
        return new ResponseEntity<>(gradeService.getStudentGrades(studentId),HttpStatus.OK);
    }
    @Operation(summary = "Get Course Grade(s)", description = "Returns course grades for student(s)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Returns a list of Course grades"),
            @ApiResponse(responseCode = "404", description = "Not Found: Student ID does not exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping(value = "/course/{courseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Grade>> getCourseGrades(@PathVariable Long courseId){
        return new ResponseEntity<>(gradeService.getCourseGrades(courseId),HttpStatus.OK);
    }
    @Operation(summary = "Retrieves Grades", description = "Provides a list of all grade for student(s) enrolled courses")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of student grades",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Student.class))))
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Grade>> getGrades(){
        return new ResponseEntity<>(gradeService.getAllGrades(),HttpStatus.OK);
    }





}
