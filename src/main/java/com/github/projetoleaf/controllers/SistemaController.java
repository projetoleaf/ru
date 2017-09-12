package com.github.projetoleaf.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SistemaController {

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/sobre")
	public String sobre() {
		return "sobre";
	}

	@GetMapping("/transferencias")
	public String transferencias() {
		return "transferencias";
	}
	
	@GetMapping("/remanescentes")
	public String remanescentes() {
		return "remanescentes";
	}
	
	@GetMapping("/creditos")
	public String creditos() {
		return "creditos";
	}
	
	@GetMapping("/semanaAtual")
	public String semanaAtual() {
		return "semanaAtual";
	}
	
	@GetMapping("/planilhas")
	public String planilhas() {
		return "planilhas";
	}
	
	@GetMapping("/graficos")
	public String graficos() {
		return "graficos";
	}
}