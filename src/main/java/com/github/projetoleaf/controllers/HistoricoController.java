package com.github.projetoleaf.controllers;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.projetoleaf.beans.Geral;
import com.github.projetoleaf.beans.ReservaItem;
import com.github.projetoleaf.repositories.ClienteCategoriaRepository;
import com.github.projetoleaf.repositories.ClienteRepository;
import com.github.projetoleaf.repositories.ReservaItemRepository;

@Controller
public class HistoricoController {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ReservaItemRepository reservaItemRepository;
	
	@Autowired
	private ClienteCategoriaRepository clienteCategoriaRepository;

	@SuppressWarnings("unlikely-arg-type")
	@GetMapping("/historico")
	public String historico(Model model,
			@RequestParam(value = "dataInicial", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date dataInicial,
			@RequestParam(value = "dataFinal", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date dataFinal) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (!(authentication instanceof AnonymousAuthenticationToken)) {

			NumberFormat nf = NumberFormat.getCurrencyInstance();
			
			List<Geral> historico = new ArrayList<Geral>();
			List<ReservaItem> historicoDoCliente = reservaItemRepository
					.todasAsReservasDoCliente(clienteRepository.findByIdentificacao(authentication.getName()).getId());			
			
			if(dataInicial == null && dataFinal == null) {
				for (ReservaItem rI : historicoDoCliente) {
					Geral geral = new Geral();
					geral.setCardapio(rI.getCardapio());
					geral.setDescricaoTipo(rI.getTipoRefeicao().getDescricao());
					geral.setDescricaoStatus(rI.getStatus().getDescricao());
					
					if(rI.getReserva().getTipoValor().getId().equals("Custo"))
						geral.setValor(nf.format(clienteCategoriaRepository.findByCliente(clienteRepository.findByIdentificacao(authentication.getName())).getCategoria().getValorSemSubsidio()));
					else
						geral.setValor(nf.format(clienteCategoriaRepository.findByCliente(clienteRepository.findByIdentificacao(authentication.getName())).getCategoria().getValorComSubsidio()));
					
					historico.add(geral);
				}
			} else {
				for (ReservaItem reserva : historicoDoCliente) {// Adiciona todo histórico do cliente com base na data de
					// filtragem da página
					if (reserva.getCardapio().getData().compareTo(dataInicial) >= 0 && reserva.getCardapio().getData().compareTo(dataFinal) <= 0) {
						Geral geral = new Geral();
						geral.setCardapio(reserva.getCardapio());
						geral.setDescricaoTipo(reserva.getTipoRefeicao().getDescricao());
						geral.setDescricaoStatus(reserva.getStatus().getDescricao());
						
						if(reserva.getReserva().getTipoValor().getId().equals("Custo"))
							geral.setValor(nf.format(clienteCategoriaRepository.findByCliente(clienteRepository.findByIdentificacao(authentication.getName())).getCategoria().getValorSemSubsidio()));
						else
							geral.setValor(nf.format(clienteCategoriaRepository.findByCliente(clienteRepository.findByIdentificacao(authentication.getName())).getCategoria().getValorComSubsidio()));
						
						historico.add(geral);
					}
				}
			}			

			model.addAttribute("listagemHistorico", historico);
		}

		return "historico";
	}
}