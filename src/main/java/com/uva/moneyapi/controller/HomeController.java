package com.uva.moneyapi.controller;

import com.uva.moneyapi.repository.LancamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private LancamentoRepository repository;

    @Autowired
    public HomeController(LancamentoRepository repository){
        this.repository = repository;
    }

    @GetMapping
    public String getDashboard(Model model){
        model.addAttribute("lancamentos", repository.findAll());
        return "/home";
    }
}
