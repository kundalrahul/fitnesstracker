package com.example.fitnesstracker.controller;

import com.example.fitnesstracker.model.UserInfo;
import com.example.fitnesstracker.model.Workout;
import com.example.fitnesstracker.service.WorkoutService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WorkoutController.class)
public class WorkoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WorkoutService workoutService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createWorkoutTest() throws Exception {
        UserInfo user = new UserInfo();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john@example.com");

        Workout workout = new Workout();
        workout.setId(1L);
        workout.setType("Running");
        workout.setDuration(30);
        workout.setUserInfo(user);

        Mockito.when(workoutService.saveWorkout(any(Workout.class))).thenReturn(workout);

        mockMvc.perform(post("/api/workouts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(workout)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.type").value("Running"))
                .andExpect(jsonPath("$.duration").value(30))
                .andExpect(jsonPath("$.userInfo.id").value(1L))
                .andExpect(jsonPath("$.userInfo.name").value("John Doe"))
                .andExpect(jsonPath("$.userInfo.email").value("john@example.com"));
    }

    @Test
    public void getAllWorkoutsTest() throws Exception {
        UserInfo user = new UserInfo();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john@example.com");

        Workout workout1 = new Workout();
        workout1.setId(1L);
        workout1.setType("Running");
        workout1.setDuration(30);
        workout1.setUserInfo(user);

        Workout workout2 = new Workout();
        workout2.setId(2L);
        workout2.setType("Swimming");
        workout2.setDuration(45);
        workout2.setUserInfo(user);

        List<Workout> workouts = Arrays.asList(workout1, workout2);

        Mockito.when(workoutService.getAllWorkouts()).thenReturn(workouts);

        mockMvc.perform(get("/api/workouts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].type").value("Running"))
                .andExpect(jsonPath("$[0].duration").value(30))
                .andExpect(jsonPath("$[0].userInfo.id").value(1L))
                .andExpect(jsonPath("$[0].userInfo.name").value("John Doe"))
                .andExpect(jsonPath("$[0].userInfo.email").value("john@example.com"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].type").value("Swimming"))
                .andExpect(jsonPath("$[1].duration").value(45))
                .andExpect(jsonPath("$[1].userInfo.id").value(1L))
                .andExpect(jsonPath("$[1].userInfo.name").value("John Doe"))
                .andExpect(jsonPath("$[1].userInfo.email").value("john@example.com"));
    }

    @Test
    public void getWorkoutsByUserIdTest() throws Exception {
        UserInfo user = new UserInfo();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john@example.com");

        Workout workout = new Workout();
        workout.setId(1L);
        workout.setType("Running");
        workout.setDuration(30);
        workout.setUserInfo(user);

        List<Workout> workouts = Arrays.asList(workout);

        Mockito.when(workoutService.getWorkoutsByUserId(anyLong())).thenReturn(workouts);

        mockMvc.perform(get("/api/workouts/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].type").value("Running"))
                .andExpect(jsonPath("$[0].duration").value(30))
                .andExpect(jsonPath("$[0].userInfo.id").value(1L))
                .andExpect(jsonPath("$[0].userInfo.name").value("John Doe"))
                .andExpect(jsonPath("$[0].userInfo.email").value("john@example.com"));
    }
}
