package com.github.projetoleaf.controllers;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
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

		List<Cliente> todosOsClientesDoBD = clienteRepository.findAll();
		List<Creditos> todosOsCreditos = new ArrayList<Creditos>();

		NumberFormat nf = NumberFormat.getCurrencyInstance();

		for (int z = 0; z < todosOsClientesDoBD.size(); z++) {

			Creditos creditos = new Creditos();
			creditos.setId(Long.valueOf(z));
			creditos.setNome(todosOsClientesDoBD.get(z).getNome());

			List<Extrato> ultimoRegistroDoCliente = extratoRepository
					.buscarTodasTransacoesDoCliente(todosOsClientesDoBD.get(z).getId());

			if (!ultimoRegistroDoCliente.isEmpty()) {
				creditos.setSaldo(ultimoRegistroDoCliente.get(ultimoRegistroDoCliente.size() - 1).getSaldo());
			} else {
				creditos.setSaldo(new BigDecimal(0.00));
			}

			String creditosFormatado = nf.format(creditos.getSaldo());

			creditos.setCreditos(creditosFormatado);
			todosOsCreditos.add(creditos);
		}

		model.addAttribute("listagemCreditos", todosOsCreditos);

		return "/creditos/pesquisar";
	}

	@GetMapping("/recarga/{nome}")
	public String colocarCreditos(@PathVariable String nome, Model model) {

		Cliente cliente = clienteRepository.findByNome(nome);
		List<Extrato> ultimoRegistroDoCliente = extratoRepository.buscarTodasTransacoesDoCliente(cliente.getId());

		NumberFormat nf = NumberFormat.getCurrencyInstance();

		BigDecimal saldo = null;

		if (!ultimoRegistroDoCliente.isEmpty()) {
			saldo = ultimoRegistroDoCliente.get(ultimoRegistroDoCliente.size() - 1).getSaldo();
		} else {
			saldo = new BigDecimal(0.00);
		}
		String saldoFormatado = nf.format(saldo);

		model.addAttribute("saldo", saldoFormatado);
		model.addAttribute("creditos", new Creditos());
		model.addAttribute(nome);

		return "/creditos/recarga";
	}

	@PostMapping("/salvar")
	public String salvarRecarga(@RequestParam("nome") String nome, @RequestParam("saldo") String safety,
			@RequestParam("recarga") String recarga) {

		Cliente cliente = clienteRepository.findByNome(nome);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis()); // Data e hora atual

		safety = safety.replaceAll("[R$ .]", "");
		BigDecimal saldo = null;

		System.out.println(safety);

		saldo = new BigDecimal(safety.replaceAll(",", "."));

		recarga = recarga.replaceAll("\\.", "");

		Extrato extrato = new Extrato();
		extrato.setCliente(cliente);
		extrato.setTransacao(new BigDecimal(recarga.replaceAll(",", ".")));
		extrato.setDataTransacao(timestamp);
		extrato.setSaldo(saldo.add(new BigDecimal(recarga.replaceAll(",", "."))));

		extratoRepository.save(extrato);

		return "redirect:/creditos";
	}
}