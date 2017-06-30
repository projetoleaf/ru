package com.github.projetoleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/desktop")
public class DesktopController {
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/inicio")
	public String inicio() {
		return "inicio";
	}

	@GetMapping("/iniciousuario")
	public String iniciousuario() {
		return "iniciousuario";
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

	@GetMapping("/inicioadmin")
	public String inicioadmin() {
		return "inicioadmin";
	}

	@GetMapping("/categorias")
	public String categorias() {
		return "categorias";
	}

	@GetMapping("/cursos")
	public String cursos() {
		return "cursos";
	}

	@GetMapping("/feriados")
	public String feriados() {
		return "feriados";
	}

	@GetMapping("/sobre")
	public String sobre() {
		return "sobre";
	}

	@GetMapping("/tipos")
	public String tipos() {
		return "tipos";
	}

	@GetMapping("/transferencias")
	public String transferencias() {
		return "transferencias";
	}
	
	@GetMapping("/usuarios")
	public String usuarios() {
		return "usuarios";
	}
	
	@GetMapping("/redefinirsenha")
	public String redefinirsenha() {
		return "redefinirsenha";
	}
	
	@GetMapping("/consulta")
	public String consulta() {
		return "consulta";
	}
	
}
