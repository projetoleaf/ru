package com.github.projetoleaf.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.github.projetoleaf.beans.Cardapio;
import com.github.projetoleaf.repositories.CardapioRepository;

@Controller
public class ReservaController {
	
	/*@Autowired
	private ReservaRepository reservaRepository;
	
	@Autowired
	private ReservaItemRepository reservaItemRepository;

	@Autowired
	private ClienteRepository clienteRepository;*/
	
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
    public String salvarReserva(Model model, @ModelAttribute("datas") Cardapio cardapio, BindingResult result) {
		
		model.addAttribute("datasSelecionadas", cardapio);
		
		return "teste";
    }
	
}