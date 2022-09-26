package com.uva.moneyapi.controller;

import com.uva.moneyapi.model.Pessoa;
import com.uva.moneyapi.repository.PessoaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequestMapping("/pessoas")
public class PessoaController {

    private Logger LOGGER = LoggerFactory.getLogger(PessoaController.class);

    private PessoaRepository repository;

    @Autowired
    public PessoaController(PessoaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pessoas", repository.findAll());
        return "/pessoas";
    }

    @RequestMapping("/getOne")
    @ResponseBody
    public Pessoa buscarPorId(Long codigo) {
        var pessoa = repository.findById(codigo);
        if (!pessoa.isPresent()) {
            LOGGER.info("Pessoa de ID: " + codigo + " não foi encontrado");
            return new Pessoa();
        }
        return pessoa.get();
    }

    @PostMapping
    public String create(@Valid Pessoa pessoa, BindingResult result) {
        if (!result.hasErrors()) {
            repository.save(pessoa);
        }
        return "redirect:/pessoas";
    }

    @RequestMapping("/update")
    public String update(@Valid Pessoa pessoa, BindingResult result) {
        if (!result.hasErrors()) {
            repository.save(pessoa);
        }
        return "redirect:/pessoas";
    }

    @RequestMapping("/delete")
    public String delete(Long codigo) {
        var pessoa = repository.findById(codigo);
        if (pessoa.isPresent()) {
            repository.delete(pessoa.get());
        }
        return "redirect:/pessoas";
    }

}
