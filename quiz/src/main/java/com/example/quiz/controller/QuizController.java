package com.example.quiz.controller;

import com.example.quiz.entity.Quiz;
import com.example.quiz.form.QuizForm;
import com.example.quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    QuizService service;

    @ModelAttribute
    public QuizForm setUpForm(){
        QuizForm form = new QuizForm();
        form.setAnswer(true);
        return form;
    }
    @GetMapping
    public String showList(QuizForm quizForm, Model model) {
        quizForm.setNewQuiz(true);

        Iterable<Quiz> list = service.selectAll();

        model.addAttribute("list", list);
        model.addAttribute("title", "등록 폼");

        return "crud";
    }
    @PostMapping("/insert")
    public String insert(@Validated QuizForm quizForm, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes) {

        Quiz quiz = new Quiz();
        quiz.setQuestion(quizForm.getQuestion());
        quiz.setAnswer(quizForm.getAnswer());
        quiz.setAuthor(quizForm.getAuthor());

        if (!bindingResult.hasErrors()) {
            service.insertQuiz(quiz);
            redirectAttributes.addFlashAttribute("complete", "등록이 완료되었습니다");
            return "redirect:/quiz";
        } else {
            return showList(quizForm, model);
        }

    }
    @GetMapping("/{id}")
    public String showUpdate(QuizForm quizForm, @PathVariable Integer id, Model model) {
        Optional<Quiz> quizOpt = service.selectOneById(id);

        Optional<QuizForm> quizFormOpt = quizOpt.map(t -> makeQuizForm(t));

        if (quizFormOpt.isPresent()) {
            quizForm = quizFormOpt.get();
        }

        //변경용 모델 생성
        makeUpdateModel(quizForm, model);
        return "crud";
    }
    
    //변경용 모델 생성
    private void makeUpdateModel(QuizForm quizForm, Model model){
        model.addAttribute("id", quizForm.getId());
        quizForm.setNewQuiz(false);
        model.addAttribute("quizForm", quizForm);
        model.addAttribute("title","변경 폼");
    }

    //id를 키로 사용해 데이터를 변경
    @PostMapping("/update")
    public String update(
           @Validated QuizForm quizForm,
           BindingResult result,
           Model model,
           RedirectAttributes redirectAttributes) {
        //QuizForm에서 Quiz로 채우기
        Quiz quiz = makeQuiz(quizForm);
        // 입력 체크
        if (!result.hasErrors()) {
            service.updateQuiz(quiz);
            redirectAttributes.addFlashAttribute("complete", "변경이 완료되었습니다.");

            return "redirect:/quiz/" + quiz.getId();
        } else {
            makeUpdateModel(quizForm, model);
            return "crud";
        }

    }

    //QuizForm에서 Quiz로 다시 채우기, 반환값으로 돌려줌
    private Quiz makeQuiz(QuizForm quizForm) {
        Quiz quiz = new Quiz();
        quiz.setId(quizForm.getId());
        quiz.setQuestion(quizForm.getQuestion());
        quiz.setAnswer(quizForm.getAnswer());
        quiz.setAuthor(quizForm.getAuthor());
        return quiz;
    }

    //Quiz에서 QuizForm로 다시 채우기, 반환값으로 돌려줌
    private QuizForm makeQuizForm(Quiz quiz) {
        QuizForm form = new QuizForm();
        form.setId(quiz.getId());
        form.setQuestion(quiz.getQuestion());
        form.setAnswer(quiz.getAnswer());
        form.setAuthor(quiz.getAuthor());
        form.setNewQuiz(false);
        return form;
    }

    //id를 키로 사용해 데이터를 삭제
    @PostMapping("/delete")
    public String delete(
            @RequestParam("id") String id,
            Model model,
            RedirectAttributes redirectAttributes) {
        //퀴즈 정보를 1건 삭제하고 리다이렉트
        service.deleteQuizById(Integer.parseInt(id));
        redirectAttributes.addFlashAttribute("delcomplete", "삭제 완료했습니다.");
        return "redirect:/quiz";
    }
    
    @GetMapping("/play")
    public String showQuiz(QuizForm quizForm, Model model) {
        //Quiz 정보 취득(Optional으로 래핑)
        Optional<Quiz> quizOpt = service.selectOneRandomQuiz();

        if (quizOpt.isPresent()) {
            Optional<QuizForm> quizFormOpt = quizOpt.map(t -> makeQuizForm(t));
            quizForm = quizFormOpt.get();
        } else {
            model.addAttribute("quizForm", quizForm);

            return "play";
        }

        //표시용 모델에 저장
        model.addAttribute("quizForm", quizForm);

        return "play";

    }

    @PostMapping("/check")
    //퀴즈의 정답/오답 판단
    public String checkQuiz(QuizForm quizForm, @RequestParam Boolean answer, Model model) {
        if (service.checkQuiz(quizForm.getId(), answer)) {
            model.addAttribute("msg", "정답입니다!");
        } else {
            model.addAttribute("msg", "오답입니다,,");
        }

        return "answer";
    }

}
