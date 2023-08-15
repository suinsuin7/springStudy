package com.example.quiz.service;

import com.example.quiz.entity.Quiz;

import java.util.Optional;

// Quiz 서비스 : service
public interface QuizService {

    //등록된 모든 퀴즈 정보를 가져옴
    Iterable<Quiz> selectAll();

    //id를 키로 사용해 퀴즈 정보를 한 건 가져옴
    Optional<Quiz> selectOneById(Integer id);

    //퀴즈 정보를 랜덤으로 한 건 가져옴
    Optional<Quiz> selectOneRandomQuiz();

    //퀴즈의 정답/오답 여부 판단
    Boolean checkQuiz(Integer id, Boolean myAnswer);

    //퀴즈 등록
    void insertQuiz(Quiz quiz);

    //퀴즈 변경
    void updateQuiz(Quiz quiz);

    //퀴즈 삭제
    void deleteQuizById(Integer id);
}
