package com.github.projetoleaf.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClienteController {
	
	@GetMapping("/clientes")
	public String clientes() {
		return "clientes";
	}
}