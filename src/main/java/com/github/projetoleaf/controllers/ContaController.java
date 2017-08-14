package com.github.projetoleaf.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.github.projetoleaf.beans.UsuarioDetails;

@Controller
public class ContaController {
	
	@GetMapping("/conta")
	public String conta(Model model) {
		
		UsuarioDetails usuario = new UsuarioDetails();
		
		usuario.getCpf();
		usuario.getNome();
		
		model.addAttribute("usuario", usuario);
		
		return "conta";
	}
}