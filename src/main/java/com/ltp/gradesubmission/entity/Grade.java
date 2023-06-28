package com.ltp.gradesubmission.entity;

import com.ltp.gradesubmission.validation.Score;
import lombok.*;
import javax.persistence.*;
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "grade", uniqueConstraints = {
        /**
         * uniqueConstraints to prevent a single student to have same grade
         * you get DataIntegrityViolationException if not prevented
        */
        @UniqueConstraint(columnNames = {"student_id", "course_id"})
})
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Score
    @NonNull
    @Column(name = "score", nullable = false)
    private String score;

    /**
     * optional = false to define that a Grade cannot exist without a student/course
     *   they cannot be null
     * */
    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;
}
