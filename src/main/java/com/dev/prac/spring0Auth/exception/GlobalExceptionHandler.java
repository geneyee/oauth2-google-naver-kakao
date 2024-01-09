package com.dev.prac.spring0Auth.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(UsernameExistException.class)
//    public ModelAndView handleUsernameExistException(UsernameExistException e) {
//        ModelAndView mav = new ModelAndView("error/username exist");
//        mav.addObject("message", "이미 존재하는 아이디입니다.");
//        mav.addObject("location", "/");
//        return mav;
//    }
}
