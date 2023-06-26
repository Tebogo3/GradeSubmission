package com.ltp.gradesubmission.repository;

import com.ltp.gradesubmission.entity.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * interface inherit from CrudRepository.
 * CrudRepository will be managing (save,edited, view,deleted) Student Entity
 * Entity identifies each record by Long Id
 */

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {


}