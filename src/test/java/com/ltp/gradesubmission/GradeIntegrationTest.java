package com.ltp.gradesubmission;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ltp.gradesubmission.entity.Course;
import com.ltp.gradesubmission.entity.Grade;
import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.repository.CourseRepository;
import com.ltp.gradesubmission.repository.GradeRepository;
import com.ltp.gradesubmission.repository.StudentRepository;
import org.assertj.core.internal.Arrays;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.Array;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class GradeIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    GradeRepository gradeRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CourseRepository courseRepository;
    private Grade[] grades = new Grade[]{
            new Grade("A"),
            new Grade("C+")
    };
    @Before
    void setUp(){
        for(int i = 0; i < grades.length; i++){
            gradeRepository.save(grades[i]);
        }
    }
    @AfterEach
    void clear(){
        gradeRepository.deleteAll();
    }
    @Test
    public void getGrades() throws Exception{
        createGrade();
        RequestBuilder request = MockMvcRequestBuilders.get("/grade/all");
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(grades.length))
                .andExpect(jsonPath("$.[?(@.id == \"1\" && @.score == \"A\")]").exists());
    }
    @Test
    public void createGrade() throws Exception {
            int i = 1;
            while (i < 3){
            RequestBuilder request = MockMvcRequestBuilders.put("/course/1/student/"+i+"" )
                    .contentType(MediaType.APPLICATION_JSON);
            mockMvc.perform(request).andExpect(status().isOk());

            RequestBuilder request1 = MockMvcRequestBuilders.post("/grade/student/"+i+"/course/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(new Grade("A")));
            mockMvc.perform(request1).andExpect(status().isCreated());
            i++;
            }
    }
    @Test
    public void readStudentGrade() throws Exception {
        createGrade();
        RequestBuilder request2 = MockMvcRequestBuilders.get("/grade/student/1/course/1");
        mockMvc.perform(request2)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.score").value(grades[0].getScore()));
    }
    @Test
    public void deleteStudentGrade() throws Exception {
        createGrade();
        RequestBuilder request = MockMvcRequestBuilders.delete("/grade/student/1/course/1");
        mockMvc.perform(request)
                .andExpect(status().isNoContent());
    }
}
