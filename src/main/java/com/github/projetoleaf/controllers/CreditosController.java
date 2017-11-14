package com.github.projetoleaf.controllers;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.projetoleaf.beans.Cliente;
import com.github.projetoleaf.beans.Creditos;
import com.github.projetoleaf.beans.Extrato;
import com.github.projetoleaf.repositories.ClienteRepository;
import com.github.projetoleaf.repositories.ExtratoRepository;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@PreAuthorize("hasRole('ROLE_FC.UNESP.RU_ADMIN')")
@Controller
@RequestMapping("/creditos")
public class CreditosController {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ExtratoRepository extratoRepository;

	@GetMapping
	public String pesquisarCreditos(Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (clienteRepository.findByIdentificacao(authentication.getName()) == null)
			return "redirect:/boasvindas";

		NumberFormat nf = NumberFormat.getCurrencyInstance();
		
		List<Creditos> todosOsCreditos = new ArrayList<Creditos>();
		List<Cliente> todosOsClientesDoBD = clienteRepository.findAll();

		for (Cliente cliente : todosOsClientesDoBD) {
			Creditos creditos = new Creditos();
			creditos.setId(cliente.getId());
			creditos.setNome(cliente.getNome());

			Extrato ultimoExtrato = extratoRepository.findFirstByClienteOrderByIdDesc(cliente);
			creditos.setSaldo(ultimoExtrato != null ? ultimoExtrato.getSaldo() : new BigDecimal(0.00));

			creditos.setCreditos(nf.format(creditos.getSaldo()));
			todosOsCreditos.add(creditos);
		}

		model.addAttribute("listagemCreditos", todosOsCreditos);

		return "/creditos/pesquisar";
	}

	@GetMapping("/recarga/{nome}")
	public String colocarCreditos(@PathVariable String nome, Model model) {

		NumberFormat nf = NumberFormat.getCurrencyInstance();
		
		Cliente cliente = clienteRepository.findByNome(nome);		
		Extrato ultimoExtrato = extratoRepository.findFirstByClienteOrderByIdDesc(cliente);
		
		BigDecimal saldo = ultimoExtrato != null ? ultimoExtrato.getSaldo() : new BigDecimal(0.00);

		model.addAttribute("saldo", nf.format(saldo));
		model.addAttribute("creditos", new Creditos());
		model.addAttribute(nome);

		return "/creditos/recarga";
	}

	@PostMapping("/salvar")
	public String salvarRecarga(@RequestParam("nome") String nome, @RequestParam("saldo") String safety,
			@RequestParam("recarga") String recarga) {

		Cliente cliente = clienteRepository.findByNome(nome);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis()); // Data e hora atual

		recarga = recarga.replaceAll("\\.", "");
		safety = safety.replaceAll("[R$ .]", "");
		
		BigDecimal saldo = new BigDecimal(safety.replaceAll(",", "."));

		Extrato extrato = new Extrato();
		extrato.setCliente(cliente);
		extrato.setTransacao(new BigDecimal(recarga.replaceAll(",", ".")));
		extrato.setDataTransacao(timestamp);
		extrato.setSaldo(saldo.add(new BigDecimal(recarga.replaceAll(",", "."))));

		extratoRepository.save(extrato);

		return "redirect:/creditos";
	}
}