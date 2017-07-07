package com.github.projetoleaf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.projetoleaf.beans.Tipo;
import com.github.projetoleaf.dto.TipoDTO;
import com.github.projetoleaf.service.TipoService;

@Controller
public class TipoController {
	
	@Autowired
    private TipoService tipoService;
	
	@GetMapping("/manutencao/tipos/pesquisar")
	public String pesquisar(Model model) {
		model.addAttribute("tipos", tipoService.listar());
		return "/manutencao/tipos/pesquisar";
	}
	
	@GetMapping("/manutencao/tipos/incluir")
	public String incluir(Model model) {
		model.addAttribute("tipo", new TipoDTO());
		return "/manutencao/tipos/incluir";
	}
	
	@GetMapping(value="/manutencao/tipos/editar/{id}")
    public ModelAndView editar(@PathVariable Integer id, Model model) {
        Tipo tipo = tipoService.buscar(id);

        TipoDTO tipoDTO = new TipoDTO();
        tipoDTO.setId(tipo.getId());
        tipoDTO.setDescricao(tipo.getDescricao());
        
        return new ModelAndView("/manutencao/tipos/editar","command", tipoDTO);
    }
	 
	@PostMapping(value="/manutencao/tipos/salvar")
    public String salvar(@ModelAttribute("tipo") TipoDTO tipoDTO, BindingResult result) {
        Tipo tipo;

        if (tipoDTO.getId() != null) {
            tipo = tipoService.buscar(tipoDTO.getId());
        } else {
        	tipo = new Tipo();
        }
        
        tipo.setDescricao(tipoDTO.getDescricao());
        
        tipoService.incluir(tipo);
        
        return "redirect:/manutencao/tipos/pesquisar";

    }
	
	@PostMapping(value="/manutencao/tipos/excluir/{id}")
    public String excluir(@PathVariable Integer id) {		
		tipoService.excluir(id);

        return "redirect:/manutencao/tipos/pesquisar";
    }
	
}