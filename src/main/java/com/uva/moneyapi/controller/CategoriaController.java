package com.uva.moneyapi.controller;

import com.uva.moneyapi.model.Categoria;
import com.uva.moneyapi.repository.CategoriaRepository;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    private Logger LOGGER = LoggerFactory.getLogger(CategoriaController.class);

    private CategoriaRepository repository;

    @Autowired
    public CategoriaController(CategoriaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("categorias", repository.findAll());
        return "/categorias";
    }

    @RequestMapping("/getOne")
    @ResponseBody
    public Categoria buscarPorId(Long codigo) {
        var categoria = repository.findById(codigo);
        if (!categoria.isPresent()) {
            LOGGER.info("Pessoa de ID: " + codigo + " não foi encontrado");
            return new Categoria();
        }
        return categoria.get();
    }

    @PostMapping
    public String create(@Valid Categoria categoria, BindingResult result, RedirectAttributes attr) {
        if (!result.hasErrors()) {
            repository.save(categoria);
            attr.addFlashAttribute("sucesso", "Categoria criada com sucesso!");
        }
        return "redirect:/categorias";
    }

    @RequestMapping("/update")
    public String update(@Valid Categoria categoria, BindingResult result) {
        if (!result.hasErrors()) {
            repository.save(categoria);
        }
        return "redirect:/categorias";
    }

    @RequestMapping("/delete")
    public String delete(Long codigo) {
        var categoria = repository.findById(codigo);
        if (categoria.isPresent()) {
            repository.delete(categoria.get());
        }
        return "redirect:/categorias";
    }

}
