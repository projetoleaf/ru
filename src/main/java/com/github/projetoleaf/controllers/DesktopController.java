package com.github.projetoleaf.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/desktop")
public class DesktopController {

	@GetMapping("/inicio")
	public String inicio() {
		return "inicio";
	}
}
