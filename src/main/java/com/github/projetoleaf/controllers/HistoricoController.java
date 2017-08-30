package com.github.projetoleaf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.github.projetoleaf.repositories.ReservaItemRepository;

@Controller
public class HistoricoController {

	@Autowired
	private ReservaItemRepository reservaItemRepository;
	
	@GetMapping("/historico")
	public String historico(Model model) {
		
		int x = 1;
		
		model.addAttribute("listagemHistorico" , reservaItemRepository.findAll(new Sort(Sort.Direction.ASC, "id")));
		model.addAttribute("id_cliente", x);
		
		return "historico";
	}
}