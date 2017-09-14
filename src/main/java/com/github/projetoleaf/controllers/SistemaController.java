package com.github.projetoleaf.controllers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class SistemaController {

	private String retorno;
	
	@GetMapping("/")
	public String index() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();		
		
		if (!(authentication instanceof AnonymousAuthenticationToken)) {	
			
			retorno = "boasVindas";
		}
		else {
			retorno = "redirect:/login";
		}
		
		return retorno;
	}
	
	@GetMapping("/logout")
	public RedirectView logoutDo(HttpServletRequest request, HttpServletResponse response){
		
		HttpSession session= request.getSession(false);
	    SecurityContextHolder.clearContext();
	    session= request.getSession(false);
	    
        if(session != null) {
            session.invalidate();
        }
        for(Cookie cookie : request.getCookies()) {
            cookie.setMaxAge(0);
        }
       
		RedirectView redirectView = new RedirectView();
	    redirectView.setUrl("https://centraldev.unesp.br/");

        return redirectView;
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