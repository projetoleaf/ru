package com.github.projetoleaf.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.github.projetoleaf.data.Feriado;
import com.github.projetoleaf.dto.FeriadoDTO;
import com.github.projetoleaf.service.FeriadoService;

@Controller
@RequestMapping("/desktop")
public class FeriadoController {
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    sdf.setLenient(true);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
	
	@Autowired
    private FeriadoService feriadoService;
	
	@GetMapping("/feriado")
	public String tipo(Model model) {
		model.addAttribute("feriados", feriadoService.listar());
		return "feriado";
	}
	
	@GetMapping("/adicionarFeriado")
	public String adicionarTipo(Model model) {
		model.addAttribute("feriado", new FeriadoDTO());
		return "adicionarFeriado";
	}
	
	@GetMapping(value="/editarFeriado/{id}")
    public ModelAndView editarFeriado(@PathVariable Integer id, Model model) {
        Feriado feriado = feriadoService.buscar(id);

        FeriadoDTO feriadoDTO = new FeriadoDTO();
        feriadoDTO.setId(feriado.getId());
        feriadoDTO.setDescricao(feriado.getDescricao());
        
        return new ModelAndView("editarFeriado","command", feriadoDTO);
    }
	
	@PostMapping(value="/salvarFeriado")
    public String salvarFeriado(@ModelAttribute("feriado") FeriadoDTO feriadoDTO, BindingResult result) {
		Feriado feriado;

        if (feriadoDTO.getId() != null) {
        	feriado = feriadoService.buscar(feriadoDTO.getId());
        } else {
        	feriado = new Feriado();
        }
        
        feriado.setDescricao(feriadoDTO.getDescricao());
        
        feriadoService.incluir(feriado);
        
        return "redirect:/desktop/feriado";

    }
	 
	@PostMapping(value="/incluirFeriado")
    public String incluirFeriado(@ModelAttribute FeriadoDTO feriadoDTO, BindingResult result) {
		Feriado feriado;

        if (feriadoDTO.getId() != null) {
        	feriado = feriadoService.buscar(feriadoDTO.getId());
        } else {
        	feriado = new Feriado();
        }
        
        feriado.setDescricao(feriadoDTO.getDescricao());
        
        feriadoService.incluir(feriado);
        
        return "redirect:/desktop/feriado";

    }
	
	@PostMapping(value="/excluirFeriado/{id}")
    public String excluirFeriado(@PathVariable Integer id) {		
		feriadoService.excluir(id);

        return "redirect:/desktop/feriado";
    }
	
}