package com.spring.web.controller;

import javax.validation.Valid;

import com.spring.web.model.Jedi;
import com.spring.web.repository.JediRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//@Controller import do spring
@Controller
public class JediController {

    //@Autowired injetando, crie a instancia do repositorio para usar
    @Autowired
    private JediRepository repository;

    // @GetMapping("/jedi"): qdo usuario acessar o caminho http://localhost:8080/jedi
    // ele renderiza o jedi.html
    @GetMapping("/jedi")
    //ModelAndView classe do Spring
    public ModelAndView jedi() {

        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jedi");
        //Lista esta na pasta repository/JediRepository
        modelAndView.addObject("allJedi", repository.getAll());

        //new Jedi esta em model/Jedi
        //final Jedi luke = new Jedi("Luke", "Skywalker"); //criado 1 item lista
        //modelAndView.addObject("allJedi", List.of(Luke));

        return modelAndView;
    }

    //path http://localhost:8080/new-jedi
    @GetMapping("/new-jedi")
    public String createJedi(Model model) {

        //nome objeto + retorna somente 1 jedi new Jedi() null
        model.addAttribute("jedi", new Jedi());
        
        return "new-jedi";
    }

    //button submit
    @PostMapping("/jedi")
    //@ModelAttribute Jedi jedi assim que receber name e last name ja converte model Jedi
    public String createJedi(@Valid @ModelAttribute Jedi jedi, BindingResult result, RedirectAttributes redirect) {

        if (result.hasErrors()) {
            return "new-jedi"; //continua na pagina new-jedi
        }
        //metodo add do repositorio
        repository.add(jedi);

        //redireciona com  nova mensagem
        redirect.addFlashAttribute("message", "Jedi successfully created.");

        return "redirect:jedi";
    }

}
