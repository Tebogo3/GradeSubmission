package com.ltp.gradesubmission.repository;

import com.ltp.gradesubmission.entity.Course;
import com.ltp.gradesubmission.entity.Grade;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * interface inherit from CrudRepository.
 * CrudRepository will be managing (save,edited, view,deleted) Grade Entity
 * Entity identifies each record by Long Id
 */
@Repository
public interface GradeRepository extends CrudRepository<Grade, Long> {
    /**
     * this is a custom query to find a student grade by Id
     * Optional - to guard against the return object if it has a null
     */
    Optional<Grade> findByStudentIdAndCourseId(Long studentId, Long courseId);

    /**
     * Transactional annotation to make that our query can access the database.
     *  usaully if an Entity has two foreign keys
     */
    @Transactional
    void deleteByStudentIdAndCourseId(Long studentId, Long courseId);
    List<Grade> findByStudentId(Long studentId);
    List<Grade> findByCourseId(Long courseId);
}
