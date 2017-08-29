package com.github.projetoleaf.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HistoricoController {

	@GetMapping("/historico")
	public String historico() {
		return "historico";
	}
}