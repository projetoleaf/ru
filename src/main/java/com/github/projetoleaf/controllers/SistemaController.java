package com.github.projetoleaf.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SistemaController {

	@GetMapping("/inicio")
	public String inicio() {
		return "inicio";
	}

	@GetMapping("/conta")
	public String conta() {
		return "conta";
	}

	@GetMapping("/historicorefeicoes")
	public String historicorefeicoes() {
		return "historicorefeicoes";
	}

	@GetMapping("/reservarefeicoes")
	public String reservarefeicoes() {
		return "reservarefeicoes";
	}

	@GetMapping("/sobre")
	public String sobre() {
		return "sobre";
	}

	@GetMapping("/transferencias")
	public String transferencias() {
		return "transferencias";
	}
	
	@GetMapping("/clientes")
	public String clientes() {
		return "clientes";
	}
	
	
	@GetMapping("/consulta")
	public String consulta() {
		return "consulta";
	}
	
}