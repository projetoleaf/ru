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
import com.github.projetoleaf.data.Tipo;
import com.github.projetoleaf.dto.TipoDTO;
import com.github.projetoleaf.service.TipoService;

@Controller
@RequestMapping("/desktop")
public class TipoController {
	
	@Autowired
    private TipoService tipoService;
	
	@GetMapping("/tipo")
	public String tipo(Model model) {
		model.addAttribute("tipos", tipoService.listar());
		return "tipo";
	}
	
	@GetMapping("/adicionarTipo")
	public String adicionarTipo(Model model) {
		model.addAttribute("tipo", new TipoDTO());
		return "adicionarTipo";
	}
	
	@GetMapping(value="/editarTipo/{id}")
    public ModelAndView editarTipo(@PathVariable Integer id, Model model) {
        Tipo tipo = tipoService.buscar(id);

        TipoDTO tipoDTO = new TipoDTO();
        tipoDTO.setId(tipo.getId());
        tipoDTO.setDescricao(tipo.getDescricao());
        
        return new ModelAndView("editarTipo","command", tipoDTO);
    }
	
	@PostMapping(value="/salvarTipo")
    public String salvarTipo(@ModelAttribute("tipo") TipoDTO tipoDTO, BindingResult result) {
        Tipo tipo;

        if (tipoDTO.getId() != null) {
            tipo = tipoService.buscar(tipoDTO.getId());
        } else {
        	tipo = new Tipo();
        }
        
        tipo.setDescricao(tipoDTO.getDescricao());
        
        tipoService.incluir(tipo);
        
        return "redirect:/desktop/tipo";

    }
	 
	@PostMapping(value="/incluirTipo")
    public String incluirTipo(@ModelAttribute TipoDTO tipoDTO, BindingResult result) {
        Tipo tipo;

        if (tipoDTO.getId() != null) {
            tipo = tipoService.buscar(tipoDTO.getId());
        } else {
        	tipo = new Tipo();
        }
        
        tipo.setDescricao(tipoDTO.getDescricao());
        
        tipoService.incluir(tipo);
        
        return "redirect:/desktop/tipo";

    }
	
	@PostMapping(value="/excluirTipo/{id}")
    public String excluirTipo(@PathVariable Integer id) {		
		tipoService.excluir(id);

        return "redirect:/desktop/tipo";
    }
	
}