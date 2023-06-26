package com.ltp.gradesubmission.exception;

public class StudentNotEnrolledException extends RuntimeException {
    public StudentNotEnrolledException (Long studentId, Long courseId){
        super("The course with student id: " + studentId + " and course id: " + courseId + " does not exist in our records");
    }
}
