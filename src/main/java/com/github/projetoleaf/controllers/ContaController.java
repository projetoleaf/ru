package com.github.projetoleaf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.github.projetoleaf.beans.Cliente;
import com.github.projetoleaf.repositories.ClienteRepository;

@Controller
public class ContaController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@GetMapping("/conta")
	public String conta(Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();		
		
		if (!(authentication instanceof AnonymousAuthenticationToken)) {	
			
		    String identificacao = authentication.getName();	
		   
		    Cliente cliente = clienteRepository.buscarCliente(identificacao);
		    
		    model.addAttribute("cliente", cliente);		
		    
		}
		
		return "conta";
	}
}