package com.github.projetoleaf.controllers;

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

import com.github.projetoleaf.beans.Curso;
import com.github.projetoleaf.dto.CursoDTO;
import com.github.projetoleaf.service.CursoService;

@Controller
public class CursoController {
	
	@Autowired
    private CursoService cursoService;
	
	@GetMapping("/manutencao/cursos/pesquisar")
	public String tipo(Model model) {
		model.addAttribute("cursos", cursoService.listar());
		return "/manutencao/cursos/pesquisar";
	}
	
	@GetMapping("/manutencao/cursos/incluir")
	public String adicionarTipo(Model model) {
		model.addAttribute("curso", new CursoDTO());
		return "/manutencao/cursos/incluir";
	}
	
	@GetMapping(value="/manutencao/cursos/editar/{id}")
    public ModelAndView editarCurso(@PathVariable Integer id, Model model) {
        Curso curso = cursoService.buscar(id);

        CursoDTO cursoDTO = new CursoDTO();
        cursoDTO.setId(curso.getId());
        cursoDTO.setDescricao(curso.getDescricao());
        cursoDTO.setPeriodo(curso.getPeriodo());
        
        return new ModelAndView("/manutencao/cursos/editar","command", cursoDTO);
    }
	
	@PostMapping(value="/manutencao/cursos/salvar")
    public String salvarCurso(@ModelAttribute("curso") CursoDTO cursoDTO, BindingResult result) {
		Curso curso;

        if (cursoDTO.getId() != null) {
        	curso = cursoService.buscar(cursoDTO.getId());
        } else {
        	curso = new Curso();
        }
        
        curso.setDescricao(cursoDTO.getDescricao());        
        curso.setPeriodo(cursoDTO.getPeriodo());
        
        cursoService.incluir(curso);
        
        return "redirect:/manutencao/cursos/pesquisar";

    }
	 
	@PostMapping(value="/manutencao/cursos/incluir")
    public String incluirCurso(@ModelAttribute CursoDTO cursoDTO, BindingResult result) {
		Curso curso;

        if (cursoDTO.getId() != null) {
        	curso = cursoService.buscar(cursoDTO.getId());
        } else {
        	curso = new Curso();
        }
        
        curso.setDescricao(cursoDTO.getDescricao());
        curso.setPeriodo(cursoDTO.getPeriodo());
        
        cursoService.incluir(curso);
        
        return "redirect:/manutencao/cursos/pesquisar";

    }
	
	@PostMapping(value="/manutencao/cursos/excluir/{id}")
    public String excluirCurso(@PathVariable Integer id) {		
		cursoService.excluir(id);

        return "redirect:/manutencao/cursos/pesquisar";
    }
	
}