package com.github.projetoleaf.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.projetoleaf.beans.Cliente;
import com.github.projetoleaf.beans.Extrato;
import com.github.projetoleaf.repositories.ClienteRepository;
import com.github.projetoleaf.repositories.ExtratoRepository;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@PreAuthorize("hasRole('ROLE_FC.UNESP.RU_ADMIN') or hasRole('ROLE_FC.UNESP.RU_CLIENTE')")
@Controller
public class ExtratoController {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ExtratoRepository extratoRepository;

	@GetMapping("/extrato")
	public String extrato(Model model,
			@RequestParam(value = "dataInicial", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date dataInicial,
			@RequestParam(value = "dataFinal", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date dataFinal) throws ParseException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (clienteRepository.findByIdentificacao(authentication.getName()) == null)
			return "redirect:/boasvindas";
		
		Cliente cliente = clienteRepository.findByIdentificacao(authentication.getName());
		
		List<Extrato> extratos = new ArrayList<Extrato>();
		
		if(dataInicial == null && dataFinal == null)
			extratos = extratoRepository.findByCliente(cliente);
		else {
			SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd/MM/yyyy");
			for (Extrato extrato : extratoRepository.findByCliente(cliente)) {
				if (formatoDesejado.format(extrato.getDataTransacao()).compareTo(formatoDesejado.format(dataInicial)) >= 0 && formatoDesejado.format(extrato.getDataTransacao()).compareTo(formatoDesejado.format(dataFinal)) <= 0) {
					extratos.add(extrato);
				}
			}
		}

		model.addAttribute("listagemExtrato", extratos);

		return "extrato";
	}
}