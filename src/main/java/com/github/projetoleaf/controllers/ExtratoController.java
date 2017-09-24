package com.github.projetoleaf.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.github.projetoleaf.beans.Cliente;
import com.github.projetoleaf.beans.Extrato;
import com.github.projetoleaf.beans.Reserva;
import com.github.projetoleaf.beans.ReservaItem;
import com.github.projetoleaf.repositories.ClienteRepository;
import com.github.projetoleaf.repositories.ReservaItemRepository;

@Controller
public class ExtratoController {
	
	@Autowired
	private ClienteRepository clienteRepository;	

	@Autowired
	private ReservaItemRepository reservaItemRepository;	
	
	@GetMapping("/extrato")
	public String extrato(Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();		
		
		if (!(authentication instanceof AnonymousAuthenticationToken)) {	
			
		    String identificacao = authentication.getName();

		    Cliente cliente = clienteRepository.buscarCliente(identificacao);
		    
		    List<Object[]> reservasDoCliente = reservaItemRepository.todasAsReservasDoCliente(cliente.getId());
		    
		    List<ReservaItem> todasAsReservas = new ArrayList<ReservaItem>();
		    
		    for(Object[] linhaDoBanco : reservasDoCliente) {  		
		    	Reserva reserva = (Reserva) linhaDoBanco[0];
		    	Extrato extrato = (Extrato) linhaDoBanco[1];
		    	
		    	ReservaItem reservaItem = new ReservaItem();
		    	reservaItem.setReserva(reserva);
		    	reservaItem.setExtrato(extrato);
		    	
		    	todasAsReservas.add(reservaItem);
		    }
		    
		    model.addAttribute("listagemExtrato", todasAsReservas);
		}
		
		return "extrato";
	}
}