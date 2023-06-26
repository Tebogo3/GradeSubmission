package com.ltp.gradesubmission;

import com.ltp.gradesubmission.entity.Course;
import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.repository.CourseRepository;
import com.ltp.gradesubmission.repository.StudentRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.hibernate.engine.transaction.jta.platform.internal.JRun4JtaPlatform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

/**
 * we implemented the CommandlineRunner at the entrypoint so that everytime
 * we run the application the student data will be saved into database
 */

@SpringBootApplication
public class GradeSubmissionApplication implements CommandLineRunner {
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	CourseRepository courseRepository;
	public static void main(String[] args) {
		SpringApplication.run(GradeSubmissionApplication.class, args);
	}
	@Operation
	public void run(String... args) throws Exception {
		Student[] students = new Student[]{
				new Student( "Harry Potter", LocalDate.parse("1900-03-30")),
				new Student("Superman",LocalDate.parse("1800-04-30")),
				new Student("Batman",LocalDate.parse("1950-03-30")),
				new Student("Hulk",LocalDate.parse("1960-03-30"))
		};
		for(int i = 0; i < students.length; i++){
			studentRepository.save(students[i]);
		}
		Course[] courses = new Course[] {
				new Course("Charms", "CH104", "In this class, you will learn spells concerned with giving an object new and unexpected properties."),
				new Course("Defence Against the Dark Arts", "DADA", "In this class, you will learn defensive techniques against the dark arts."),
				new Course("Herbology", "HB311", "In this class, you will learn the study of magical plants and how to take care of, utilise and combat them."),
				new Course("History of Magic", "HIS393", "In this class, you will learn about significant events in wizarding history."),
				new Course("Potions", "POT102", "In this class, you will learn correct mixing and stirring of ingredients to create mixtures with magical effects."),
				new Course("Transfiguration", "TR442", "In this class, you will learn the art of changing the form or appearance of an object.")
		};

		for (int j = 0; j < courses.length; j++){
			courseRepository.save(courses[j]);
		}
	}
}
