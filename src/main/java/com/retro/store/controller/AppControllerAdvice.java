package com.retro.store.controller;

import com.retro.store.exception.RecursoNaoEncontradoException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppControllerAdvice {
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public String handleNotFound(RecursoNaoEncontradoException ex, Model model) {
        model.addAttribute("mensagemErro", ex.getMessage());
        return "erro";
    }
}
