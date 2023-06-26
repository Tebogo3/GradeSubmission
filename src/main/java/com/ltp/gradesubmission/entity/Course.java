package com.ltp.gradesubmission.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NonNull
    @NotBlank(message = "Subject cannot be blank" )
    @Column(name = "subject", nullable = false)
    private String subject;
    /**
     * unique because it involves only one column - will prevent having same code for more than one courses.
     * error you get if not prevented is DataIntegrityViolationException
     */
    @NonNull
    @NotBlank(message = "Course code cannot be blank")
    @Column(name = "code", nullable = false, unique = true)
    private String code;
    @NonNull
    @NotBlank(message = "Description cannot be blank")
    @Column(name = "description", nullable = false)
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Grade> grades;

    /**
     * A Set is a collection type that prevents the addition of duplicate elements.
     * A student cannot enroll in duplicate courses
     */

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "course_student",
            joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id")
    )
    private Set<Student> students;
}
