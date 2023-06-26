package com.ltp.gradesubmission.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor //constructor will only update nonnull fields
@NoArgsConstructor
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @NonNull
    @NotBlank(message = "Name cannot be blank")
    @Column(name = "name", nullable = false)
    private String name;
    @NonNull
    @Past(message = "Date must be in the past")
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;
    /**
     * One student associates with many grades
     * mappedBy will prevent SpringJPA to create another
     * joint_table
     * JsonIgnore part of grade wont be serialized into json upon reading students
     * cascade - delete a record which associates with a record from related table
     */
    @JsonIgnore
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Grade> grades;

    /**
     * One student associates with many course
     * A Set is a collection type that prevents the addition of duplicate elements.
     * A course cannot enroll in duplicate students
     */
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "course_student",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id")
    )       private Set<Course> courses;

}
