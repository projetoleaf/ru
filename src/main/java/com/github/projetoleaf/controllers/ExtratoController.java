package com.github.projetoleaf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.github.projetoleaf.beans.Cliente;
import com.github.projetoleaf.repositories.ClienteRepository;
import com.github.projetoleaf.repositories.ExtratoRepository;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@PreAuthorize("hasRole('ROLE_FC.UNESP.RU_ADMIN') or hasRole('ROLE_FC.UNESP.RU_CLIENTE')")
@Controller
public class ExtratoController {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ExtratoRepository extratoRepository;

	@GetMapping("/extrato")
	public String extrato(Model model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		Cliente cliente = clienteRepository.findByIdentificacao(authentication.getName());

		model.addAttribute("listagemExtrato", extratoRepository.findByCliente(cliente));

		return "extrato";
	}
}