package com.example.quiz;

import com.example.quiz.entity.Quiz;
import com.example.quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
public class QuizApplication {
	public static void main(String[] args) {
		SpringApplication.run(QuizApplication.class, args);
	}
}