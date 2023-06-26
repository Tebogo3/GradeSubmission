package com.ltp.gradesubmission;

import com.ltp.gradesubmission.entity.Course;
import com.ltp.gradesubmission.repository.CourseRepository;


import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;



@SpringBootTest
@AutoConfigureMockMvc
class CourseIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    ObjectMapper objectMapper;
    private Course[] courses = new Course[]{
            new Course("Charms", "CH104", "In this class, you will learn spells concerned with giving an object new and unexpected properties."),
            new Course("Defence Against the Dark Arts", "DADA", "In this class, you will learn defensive techniques against the dark arts."),
            new Course("Herbology", "HB311", "In this class, you will learn the study of magical plants and how to take care of, utilise and combat them."),
            new Course("History of Magic", "HIS393", "In this class, you will learn about significant events in wizard history."),
            new Course("Potions", "POT102", "In this class, you will learn correct mixing and stirring of ingredients to create mixtures with magical effects."),
            new Course("Transfiguration", "TR442", "In this class, you will learn the art of changing the form or appearance of an object.")
    };
    @Before
    void setUp(){

        for(int i = 0; i < courses.length; i++){
            courseRepository.save(courses[i]);
        }
    }
    @AfterEach
    void clear(){
        courseRepository.deleteAll();
    }
    @Test
    public void getAllCourses() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/course/all");
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(courses.length))
                .andExpect(jsonPath("$.[?(@.id == \"3\" && @.code == \"HB311\")]").exists())
                .andExpect(jsonPath("$.[?(@.id == \"5\" && @.subject == \"Potions\")]").exists());
    }
    @Test
    public void getCourseById() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders.get("/course/1");
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(courses[0].getCode()))
                .andExpect(jsonPath("$.subject").value(courses[0].getSubject()));
    }
    @Test
    public void validCourseCreation() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post("/course/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Course("Java", "JV0903A", "In this class, you will learn correct " +
                                                     "mixing and stirring of ingredients to create mixtures with magical effects. ")));
                mockMvc.perform(request).andExpect(status().isCreated());
    }
    @Test
    public void validEnrollStudent() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.put("/course/1/student/3")
                .contentType(MediaType.APPLICATION_JSON);
                mockMvc.perform(request).andExpect(status().isOk());
    }
    @Test
    public void deleteStudentById() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.delete("/course/2");
        mockMvc.perform(request)
                .andExpect(status().isNoContent());
    }
    @Test
    public void CourseNotFound() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/course/7");
         mockMvc.perform(request).andExpect(status().isNotFound());
    }
}
