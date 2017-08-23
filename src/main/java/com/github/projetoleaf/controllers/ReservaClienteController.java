package com.github.projetoleaf.controllers;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.github.projetoleaf.beans.Cardapio;
import com.github.projetoleaf.beans.Reserva;
import com.github.projetoleaf.beans.ReservaItem;
import com.github.projetoleaf.beans.Status;
import com.github.projetoleaf.beans.Cliente;
import com.github.projetoleaf.beans.TipoValor;
import com.github.projetoleaf.repositories.CardapioRepository;
import com.github.projetoleaf.repositories.ReservaRepository;
import com.github.projetoleaf.repositories.ReservaItemRepository;

@Controller
public class ReservaClienteController {
	
	@Autowired
	private ReservaRepository reservaRepository;
	
	@Autowired
	private CardapioRepository cardapioRepository;
	
	@Autowired
	private ReservaItemRepository reservaItemRepository;
	
	@GetMapping("/reservaRefeicoes")
	public String reservaRefeicoes(Model model) throws JsonGenerationException, JsonMappingException, IOException {
		
		List<Cardapio> cardapio = cardapioRepository.findAll(new Sort(Sort.Direction.ASC, "data"));
		
		model.addAttribute("datas", new Cardapio());
		model.addAttribute("todasAsDatas", cardapio);
		
		ObjectMapper mapper = new ObjectMapper();// jackson lib for converting to json
        String objectJSON = mapper.writeValueAsString(cardapio);// json string
        model.addAttribute("objectJSON", objectJSON);
		
		return "reservaRefeicoes";
	}
	
	@PostMapping("/reservaRefeicoes/salvar")
	public String salvarReserva(@RequestParam("data") String[] idsCardapios) {
		
		Reserva reserva = new Reserva();	
		Cliente cliente = new Cliente();
		
		Integer variavel = 1;
		cliente.setId(Long.parseLong(variavel.toString()));
		TipoValor tipoValor = new TipoValor();
		tipoValor.setId(Long.parseLong(variavel.toString()));
		
	    reserva.setCliente(cliente); //pegar id da sessão do momento
	    reserva.setTipoValor(tipoValor); //Definir como subsidiada caso seja umas das 360 primeiras refeições
	    Timestamp timestamp = new Timestamp(System.currentTimeMillis()); //Data e hora atual	    
	    reserva.setDataHora(timestamp);
	    
	    reservaRepository.save(reserva);		
	    
	    long id = reservaRepository.findFirstByOrderByIdDesc().getId();
		
	    for (int x = 0; x <= idsCardapios.length -1; x++) {
		   
		   ReservaItem reservaItem = new ReservaItem();		
		   Reserva r = new Reserva();
		   Cardapio c = new Cardapio();
		   Status s = new Status();
		   
		   r.setId(id);
		   c.setId(Long.parseLong((idsCardapios[x])));
		   s.setId(Long.parseLong(variavel.toString()));
		   
		   reservaItem.setReserva(r);
		   reservaItem.setCardapio(c);
		   reservaItem.setStatus(s);	   
		   
		   reservaItemRepository.save(reservaItem);
	    }
	    
	    return "redirect:/historicoRefeicoes";
	}
	
}