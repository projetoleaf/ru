package com.github.projetoleaf.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.projetoleaf.beans.Cliente;
import com.github.projetoleaf.beans.Extrato;
import com.github.projetoleaf.beans.Relatorios;
import com.github.projetoleaf.repositories.ClienteRepository;
import com.github.projetoleaf.repositories.ExtratoRepository;

@Controller
public class GraficosController {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ExtratoRepository extratoRepository;

	@GetMapping("/graficos")
	public String graficos(Model model) {

		List<String> setor = new ArrayList<String>();
		List<String> linha = new ArrayList<String>();
		List<String> coluna = new ArrayList<String>();
		
		setor.add("Categorias");
		setor.add("Período");
		setor.add("Faculdade");
		setor.add("Refeição");
		setor.add("Idade");
		setor.add("Sexo");
		setor.add("Venda por categoria");
		
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
	public String graficoGerado(Model model, @RequestParam(value = "setor", required = false) String[] setores,
			@RequestParam(value = "coluna", required = false) String[] colunas,
			@RequestParam(value = "linha", required = false) String[] linhas) {

		String titulo, texto, valor;

		List<String> setor = new ArrayList<String>();
		List<String> linha = new ArrayList<String>();
		List<String> coluna = new ArrayList<String>();

		setor.add("Categorias");
		setor.add("Período");
		setor.add("Faculdade");
		setor.add("Refeição");
		setor.add("Idade");
		setor.add("Sexo");
		setor.add("Venda por categoria");

		coluna.add("Cursos");
		coluna.add("Venda por mês");
		coluna.add("Venda por categoria");

		linha.add("Cursos");
		linha.add("Venda por mês");
		linha.add("Venda por categoria");

		Map<String, BigDecimal> dados = new TreeMap<String, BigDecimal>();
		List<Cliente> todosOsClientes = clienteRepository.findAll();

		for (Cliente cliente : todosOsClientes) {
			Extrato ultimoExtrato = extratoRepository.findFirstByClienteOrderByIdDesc(cliente);
			BigDecimal saldo = ultimoExtrato != null ? ultimoExtrato.getSaldo() : new BigDecimal(0.00);
			dados.put(cliente.getNome(), saldo);
		}

		model.addAttribute("titulo", "Saldo das pessoinhas");
		model.addAttribute("textos", "Nome");
		model.addAttribute("valores", "Saldo");
		model.addAttribute("dados", dados);

		return "graficogerado";
	}
}