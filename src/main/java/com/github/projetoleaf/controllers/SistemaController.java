package com.github.projetoleaf.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SistemaController {

	private String retorno;

	@GetMapping("/")
	public String index() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (!(authentication instanceof AnonymousAuthenticationToken)) {

			retorno = "redirect:/boasVindas";
		} else {
			retorno = "redirect:/login";
		}

		return retorno;
	}

	@GetMapping("/sobre")
	public String sobre() {
		return "sobre";
	}

	@GetMapping("/transferencias")
	public String transferencias() {
		return "transferencias";
	}
}