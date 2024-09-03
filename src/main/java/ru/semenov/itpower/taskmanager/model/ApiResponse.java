package ru.semenov.itpower.taskmanager.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private String message;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private boolean status;
}
