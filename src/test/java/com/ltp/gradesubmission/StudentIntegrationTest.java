package com.ltp.gradesubmission;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.repository.StudentRepository;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static java.time.LocalDate.parse;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
class StudentIntegrationTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	StudentRepository studentRepository;
	//convert entity into JSON
	@Autowired
	private ObjectMapper objectMapper;
	private Student[] students = new Student[]{
			new Student( "Harry Potter", LocalDate.parse("1900-03-30")),
			new Student("Superman", LocalDate.parse("1800-04-30")),
			new Student("Batman", LocalDate.parse("1950-03-30")),
			new Student("Hulk", LocalDate.parse("1960-03-30"))
	};
	@Before
	void setUp(){
		for(int i = 0; i < students.length; i++){
			studentRepository.save(students[i]);
		}
	}
	@AfterEach
	void clear(){
		studentRepository.deleteAll();
	}
	@Test
	public void getAllStudents() throws Exception{
		RequestBuilder request = MockMvcRequestBuilders.get("/student/all");
		mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.size()").value(students.length))
				.andExpect(jsonPath("$.[?(@.id == \"2\" && @.name == \"Superman\")]").exists());
	}
	@Test
	public void getStudentByIdTest() throws Exception{
		RequestBuilder request = MockMvcRequestBuilders.get("/student/1");
		mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.name").value(students[0].getName()));
			//	.andExpect(jsonPath("$.birthDate").value(students[0].getBirthDate()));
	}
	@Test
	public void validStudentCreationTest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.post("/student/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new Student("Vusana", LocalDate.parse("1900-03-30"))));
		mockMvc.perform(request).andExpect(status().isCreated());
	}
	@Test
	public void deleteStudentById() throws Exception{
		RequestBuilder request = MockMvcRequestBuilders.delete("/student/1");
		mockMvc.perform(request)
				.andExpect(status().isNoContent());
	}
	@Test
	public void studentNotFoundTest() throws Exception{
		RequestBuilder request = MockMvcRequestBuilders.get("/student/5");
		mockMvc.perform(request).andExpect(status().isNotFound());
	}
}
