package com.github.projetoleaf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.github.projetoleaf.data.Curso;
import com.github.projetoleaf.dto.CursoDTO;
import com.github.projetoleaf.service.CursoService;

@Controller
@RequestMapping("/desktop")
public class CursoController {
	
	@Autowired
    private CursoService cursoService;
	
	@GetMapping("/curso")
	public String tipo(Model model) {
		model.addAttribute("cursos", cursoService.listar());
		return "curso";
	}
	
	@GetMapping("/adicionarCurso")
	public String adicionarTipo(Model model) {
		model.addAttribute("curso", new CursoDTO());
		return "adicionarCurso";
	}
	
	@GetMapping(value="/editarCurso/{id}")
    public ModelAndView editarCurso(@PathVariable Integer id, Model model) {
        Curso curso = cursoService.buscar(id);

        CursoDTO cursoDTO = new CursoDTO();
        cursoDTO.setId(curso.getId());
        cursoDTO.setDescricao(curso.getDescricao());
        
        return new ModelAndView("editarCurso","command", cursoDTO);
    }
	
	@PostMapping(value="/salvarCurso")
    public String salvarCurso(@ModelAttribute("curso") CursoDTO cursoDTO, BindingResult result) {
		Curso curso;

        if (cursoDTO.getId() != null) {
        	curso = cursoService.buscar(cursoDTO.getId());
        } else {
        	curso = new Curso();
        }
        
        curso.setDescricao(cursoDTO.getDescricao());
        
        cursoService.incluir(curso);
        
        return "redirect:/desktop/curso";

    }
	 
	@PostMapping(value="/incluirCurso")
    public String incluirCurso(@ModelAttribute CursoDTO cursoDTO, BindingResult result) {
		Curso curso;

        if (cursoDTO.getId() != null) {
        	curso = cursoService.buscar(cursoDTO.getId());
        } else {
        	curso = new Curso();
        }
        
        curso.setDescricao(cursoDTO.getDescricao());
        
        cursoService.incluir(curso);
        
        return "redirect:/desktop/curso";

    }
	
	@PostMapping(value="/excluirCurso/{id}")
    public String excluirCurso(@PathVariable Integer id) {		
		cursoService.excluir(id);

        return "redirect:/desktop/curso";
    }
	
}