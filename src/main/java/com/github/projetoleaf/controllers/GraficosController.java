package com.github.projetoleaf.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.projetoleaf.beans.Categoria;
import com.github.projetoleaf.beans.ClienteCategoria;
import com.github.projetoleaf.beans.Relatorios;
import com.github.projetoleaf.repositories.CategoriaRepository;
import com.github.projetoleaf.repositories.ClienteCategoriaRepository;
import com.github.projetoleaf.repositories.ClienteRepository;

@Controller
public class GraficosController {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ClienteCategoriaRepository clienteCategoriaRepository;

	@GetMapping("/graficos")
	public String graficos(Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (clienteRepository.findByIdentificacao(authentication.getName()) == null)
			return "redirect:/boasvindas";

		List<String> setor = new ArrayList<String>();
		List<String> linha = new ArrayList<String>();
		List<String> coluna = new ArrayList<String>();
		
		setor.add("Categoria");
/*		setor.add("Período");
		setor.add("Faculdade");
		setor.add("Refeição");
		setor.add("Idade");
		setor.add("Sexo");
		setor.add("Venda por categoria");*/
		
		coluna.add("Cursos");
		coluna.add("Venda por mês");
		coluna.add("Venda por categoria");
		
		linha.add("Cursos");
		linha.add("Venda por mês");
		linha.add("Venda por categoria");

		model.addAttribute("setores", setor);
		model.addAttribute("colunas", coluna);
		model.addAttribute("linhas", linha);
		model.addAttribute("tiposGraficos", new Relatorios());

		return "graficos";
	}

	@PostMapping("/graficogerado")
	public String graficoGerado(Model model, @RequestParam(value = "setor", required = false) String setores) {

		Map<String, Integer> dados = new TreeMap<String, Integer>();
		
		List<Categoria> categorias = categoriaRepository.findAll();
		List<ClienteCategoria> todasAsCategorias = clienteCategoriaRepository.findAll();		
	
		for (Categoria c : categorias) {
			int count = 0;		
			
			for (ClienteCategoria cliente : todasAsCategorias) {
				if(cliente.getCategoria().getDescricao().equals(c.getDescricao())) {
					count++;
				}
			}		
			
			dados.put(c.getDescricao(), count);
			count = 0;
		}


		model.addAttribute("titulo", "Clientes por Categoria");
		model.addAttribute("textos", "Categoria");
		model.addAttribute("valores", "Quantidade");
		model.addAttribute("dados", dados);

		return "graficogerado";
	}
}