package com.github.projetoleaf.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.github.projetoleaf.beans.Cardapio;
import com.github.projetoleaf.beans.Reserva;
import com.github.projetoleaf.repositories.CardapioRepository;
import com.github.projetoleaf.repositories.ReservaRepository;

@Controller
public class ReservaController {
	
	@Autowired
	private ReservaRepository reservaRepository;
	
	@Autowired
	private CardapioRepository cardapioRepository;
	
	@GetMapping("/reservaRefeicoes")
	public String reservaRefeicoes(Model model) {
		
		List<Cardapio> cardapio = cardapioRepository.findAll(new Sort(Sort.Direction.ASC, "data"));
		
		model.addAttribute("datas", new Cardapio());
		model.addAttribute("todasAsDatas", cardapio);
		
		return "reservaRefeicoes";
	}
	
	@PostMapping("/reservaRefeicoes/salvar")
	public String salvarReserva(@RequestParam("data") String[] idsCardapios) {
		
	   Reserva reserva = new Reserva();
	   
	   for (int x = 0; x < idsCardapios.length; x++) {
		   reserva.setIdStatus(1);
		   reserva.setIdCliente(1);
		   reserva.setIdCardapio(Integer.parseInt(idsCardapios[x]));
		   reservaRepository.save(reserva);		   
	   }
	   
	   return "redirect:/historicoRefeicoes";
	}
	
}