package com.uva.moneyapi.controller;

import com.uva.moneyapi.model.Lancamento;
import com.uva.moneyapi.repository.CategoriaRepository;
import com.uva.moneyapi.repository.LancamentoRepository;
import com.uva.moneyapi.repository.PessoaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/lancamentos")
public class LancamentoController {

    private Logger LOGGER = LoggerFactory.getLogger(LancamentoController.class);

    private LancamentoRepository repository;
    private PessoaRepository pessoaRepository;
    private CategoriaRepository categoriaRepository;

    @Autowired
    public LancamentoController(LancamentoRepository repository,
                                PessoaRepository pessoaRepository, CategoriaRepository categoriaRepository) {
        this.repository = repository;
        this.pessoaRepository = pessoaRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("lancamentos", repository.findAll());
        return "/lancamento/lancamentos";
    }

    @GetMapping("/create")
    public String paginaNovoLancamento(Model model) {
        model.addAttribute("lancamentos", repository.findAll());
        model.addAttribute("categorias", categoriaRepository.findAll());
        model.addAttribute("pessoas", pessoaRepository.findAll());
        return "/lancamento/novo-lancamento";
    }

    @GetMapping("/{codigo}")
    public ModelAndView updateLancamento(@PathVariable Long codigo) {
        var lancamento = repository.findById(codigo).get();
        var mv = new ModelAndView("lancamento/atualizar-lancamento");
        mv.addObject("categorias", categoriaRepository.findAll());
        mv.addObject("pessoas", pessoaRepository.findAll());
        mv.addObject("lID", lancamento.getCodigo());
        mv.addObject("lancamento", lancamento);
        return mv;
    }

    @GetMapping("/detalhes/{id}")
    public ModelAndView getdetalhes(@PathVariable Long id) {
        var lancamento = repository.findFullLancamentoByID(id);
        var mv = new ModelAndView("lancamento/detalhes-lancamento");
//        mv.addObject("categorias", lancamento.getCategoria());
//        mv.addObject("pessoas", lancamento.getPessoa());
//        mv.addObject("lID", lancamento.getCodigo());
        mv.addObject("lancamento", lancamento);
        return mv;
    }

    @RequestMapping("/getOne")
    @ResponseBody
    public Lancamento buscarPorId(Long codigo) {
        var lancamento = repository.findById(codigo);
        if (!lancamento.isPresent()) {
            LOGGER.info("Pessoa de ID: " + codigo + " não foi encontrado");
            return new Lancamento();
        }
        return lancamento.get();
    }

    @PostMapping
    public String create(@Valid Lancamento lancamento, BindingResult result, RedirectAttributes attr) {
        if (!result.hasErrors()) {
            repository.save(lancamento);
            attr.addFlashAttribute("sucesso", "Lancamento feito com sucesso!");
            return "redirect:/lancamentos";
        } else {
            attr.addFlashAttribute("falha", "Erro ao tentar salvar "+ result.getFieldError());
            return "redirect:/lancamentos/create";
        }

    }

    @RequestMapping("/update")
    public String update(@Valid Lancamento lancamento, BindingResult result) {
        if (!result.hasErrors()) {
            repository.save(lancamento);
        }
        return "redirect:/lancamentos";
    }

    @RequestMapping("/delete")
    public String delete(Long codigo) {
        var lancamento = repository.findById(codigo);
        if (lancamento.isPresent()) {
            repository.delete(lancamento.get());
        }
        return "redirect:/lancamentos";
    }

}
