package com.github.projetoleaf.controllers;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

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
import com.github.projetoleaf.beans.ClienteCategoria;
import com.github.projetoleaf.beans.ClienteCurso;
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
public class ContaController {

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

	@GetMapping("/conta")
	public String conta(Model model) throws ParseException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (!(authentication instanceof AnonymousAuthenticationToken)) {

			String identificacao = authentication.getName();
			Cliente cliente = clienteRepository.buscarCliente(identificacao);

			BigDecimal saldo = new BigDecimal(0.00);
			String ultimaReserva = "Nenhuma reserva solicitada";

			NumberFormat nf = NumberFormat.getCurrencyInstance();
			SimpleDateFormat formatoDataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

			ClienteCurso curso = clienteCursoRepository.findByCliente(cliente);
			ClienteCategoria categoria = clienteCategoriaRepository.findByCliente(cliente);

			List<Reserva> reservasDoCliente = reservaRepository.todasAsReservasDoCliente(cliente);
			List<Extrato> extratosDoCliente = extratoRepository.buscarTodasTransacoesDoCliente(cliente.getId());

			if (!reservasDoCliente.isEmpty()) {
				ultimaReserva = formatoDataHora
						.format(reservasDoCliente.get(reservasDoCliente.size() - 1).getDataReserva());
			}

			if (!extratosDoCliente.isEmpty()) {
				saldo = extratosDoCliente.get(extratosDoCliente.size() - 1).getSaldo();
			}

			String creditosFormatado = nf.format(saldo);

			model.addAttribute("curso", curso);
			model.addAttribute("cliente", cliente);
			model.addAttribute("categoria", categoria);
			model.addAttribute("creditos", creditosFormatado);
			model.addAttribute("ultimaReserva", ultimaReserva);
		}

		return "conta";
	}
}