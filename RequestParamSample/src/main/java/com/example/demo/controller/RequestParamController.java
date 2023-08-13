package com.example.demo.controller;

import com.example.demo.form.Form;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.time.LocalDate;

@Controller
public class RequestParamController {
    @GetMapping("show")
    public String showView() {
        return "entry";
    }

    /*
    @PostMapping("confirm")
    public String confirmView(Model model, @RequestParam String name, @RequestParam Integer age,
                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam LocalDate birth)
    {
      model.addAttribute("name", name);
      model.addAttribute("age", age);
      model.addAttribute("birth", birth);

      return "confirm";
    }
    */

    /** 확인 화면을 표시: Form 클래스용 */
    @PostMapping("confirm")
    public String confirmView(Form f) {
        // 반환값으로 뷰 이름을 돌려줌
        return "confirm2";
    }
}
