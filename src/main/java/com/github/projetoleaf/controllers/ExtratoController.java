package com.github.projetoleaf.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.github.projetoleaf.beans.Cliente;
import com.github.projetoleaf.beans.Extrato;
import com.github.projetoleaf.repositories.ClienteRepository;
import com.github.projetoleaf.repositories.ExtratoRepository;

@Controller
public class ExtratoController {
	
	@Autowired
	private ClienteRepository clienteRepository;	

	@Autowired
	private ExtratoRepository extratoRepository;	
	
	@GetMapping("/extrato")
	public String extrato(Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();		
		
		if (!(authentication instanceof AnonymousAuthenticationToken)) {	
			
		    String identificacao = authentication.getName();

		    Cliente cliente = clienteRepository.buscarCliente(identificacao);
		    
		    List<Extrato> reservasDoCliente = extratoRepository.buscarTodasTransacoesDoCliente(cliente.getId());
		    
		    model.addAttribute("listagemExtrato", reservasDoCliente);
		}
		
		return "extrato";
	}
}