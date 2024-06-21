package com.example.fitnesstracker.service;

import com.example.fitnesstracker.model.Workout;
import com.example.fitnesstracker.repository.UserRepository;
import com.example.fitnesstracker.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutService {
    private final WorkoutRepository workoutRepository;
    private final UserRepository userRepository;

    @Autowired
    public WorkoutService(WorkoutRepository workoutRepository, UserRepository userRepository) {
        this.workoutRepository = workoutRepository;
        this.userRepository = userRepository;
    }

    public Workout saveWorkout(Workout workout) {
        Long userId = workout.getUserInfo().getId();
        return userRepository.findById(userId)
                .map(userInfo -> {
                    workout.setUserInfo(userInfo);
                    return workoutRepository.save(workout);
                })
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
    }

    public List<Workout> getAllWorkouts() {
        return workoutRepository.findAll();
    }

    public List<Workout> getWorkoutsByUserId(Long userId) {
        return workoutRepository.findByUserInfoId(userId);
    }
}
