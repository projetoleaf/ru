package com.github.projetoleaf.controllers;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.github.projetoleaf.beans.Cliente;
import com.github.projetoleaf.beans.Extrato;
import com.github.projetoleaf.beans.Reserva;
import com.github.projetoleaf.repositories.ClienteCategoriaRepository;
import com.github.projetoleaf.repositories.ClienteCursoRepository;
import com.github.projetoleaf.repositories.ClienteRepository;
import com.github.projetoleaf.repositories.ExtratoRepository;
import com.github.projetoleaf.repositories.ReservaRepository;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@PreAuthorize("hasRole('ROLE_FC.UNESP.RU_ADMIN') or hasRole('ROLE_FC.UNESP.RU_CLIENTE')")
@Controller
public class MeusDadosController {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ExtratoRepository extratoRepository;

	@Autowired
	private ReservaRepository reservaRepository;

	@Autowired
	private ClienteCursoRepository clienteCursoRepository;

	@Autowired
	private ClienteCategoriaRepository clienteCategoriaRepository;

	@GetMapping("/meusdados")
	public String meusDados(Model model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (!(authentication instanceof AnonymousAuthenticationToken)) {

			NumberFormat nf = NumberFormat.getCurrencyInstance();
			SimpleDateFormat formatoDataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

			Cliente cliente = clienteRepository.findByIdentificacao(authentication.getName());

			Extrato ultimoExtrato = extratoRepository.findFirstByClienteOrderByIdDesc(cliente);
			BigDecimal saldo = ultimoExtrato != null ? ultimoExtrato.getSaldo() : new BigDecimal(0.00);

			Reserva ultimoRegistro = reservaRepository.findFirstByClienteOrderByIdDesc(cliente);
			String ultimaReserva = ultimoRegistro != null ? formatoDataHora.format(ultimoRegistro.getDataReserva())
					: "Nenhuma reserva solicitada";

			model.addAttribute("curso", clienteCursoRepository.findByCliente(cliente));
			model.addAttribute("cliente", cliente);
			model.addAttribute("categoria", clienteCategoriaRepository.findByCliente(cliente));
			model.addAttribute("creditos", nf.format(saldo));
			model.addAttribute("ultimaReserva", ultimaReserva);
		}

		return "meusdados";
	}
}