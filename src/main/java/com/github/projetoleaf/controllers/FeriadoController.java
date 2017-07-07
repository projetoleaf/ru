package com.github.projetoleaf.controllers;

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

import com.github.projetoleaf.beans.Feriado;
import com.github.projetoleaf.dto.FeriadoDTO;
import com.github.projetoleaf.service.FeriadoService;

@Controller
public class FeriadoController {
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    sdf.setLenient(true);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
	
	@Autowired
    private FeriadoService feriadoService;
	
	@GetMapping("/manutencao/feriados/pesquisar")
	public String tipo(Model model) {
		model.addAttribute("feriados", feriadoService.listar());
		return "/manutencao/feriados/pesquisar";
	}
	
	@GetMapping("/manutencao/feriados/incluir")
	public String adicionarTipo(Model model) {
		model.addAttribute("feriado", new FeriadoDTO());
		return "/manutencao/feriados/incluir";
	}
	
	@GetMapping(value="/manutencao/feriados/editar/{id}")
    public ModelAndView editarFeriado(@PathVariable Integer id, Model model) {
        Feriado feriado = feriadoService.buscar(id);

        FeriadoDTO feriadoDTO = new FeriadoDTO();
        feriadoDTO.setId(feriado.getId());
        feriadoDTO.setDescricao(feriado.getDescricao());
        feriadoDTO.setData(feriado.getData());
        
        return new ModelAndView("/manutencao/feriados/editar","command", feriadoDTO);
    }
	
	@PostMapping(value="/manutencao/feriados/salvar")
    public String salvarFeriado(@ModelAttribute("feriado") FeriadoDTO feriadoDTO, BindingResult result) {
		Feriado feriado;

        if (feriadoDTO.getId() != null) {
        	feriado = feriadoService.buscar(feriadoDTO.getId());
        } else {
        	feriado = new Feriado();
        }
        
        feriado.setDescricao(feriadoDTO.getDescricao());
        feriado.setData(feriadoDTO.getData());
        
        feriadoService.incluir(feriado);
        
        return "redirect:/manutencao/feriados/pesquisar";

    }
	
	@PostMapping(value="/manutencao/feriados/excluir/{id}")
    public String excluirFeriado(@PathVariable Integer id) {		
		feriadoService.excluir(id);

        return "redirect:/manutencao/feriados/pesquisar";
    }
	
}