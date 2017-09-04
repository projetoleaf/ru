package com.github.projetoleaf.controllers;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
import com.github.projetoleaf.beans.ReservaItem;
import com.github.projetoleaf.repositories.ReservaItemRepository;

@Controller
public class HistoricoController {

	@Autowired
	private ReservaItemRepository reservaItemRepository;	
	
	@GetMapping("/historico")
	public String historico(Model model, @RequestParam(value = "txt_data", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date data) {
		
		if (data == null) {
            LocalDate fimMes = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
            data = java.sql.Date.valueOf(fimMes);
        }
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();		
		
		if (!(authentication instanceof AnonymousAuthenticationToken)) {	
			
		    String identificacao = authentication.getName();	
		    
		    List<ReservaItem> historico = new ArrayList<ReservaItem>();
		    
		    List<ReservaItem> base = reservaItemRepository.findAll();
		    
		    for(int x = 0; x < base.size(); x++) {
		    	
		    	if(base.get(x).getReserva().getCliente().getIdentificacao().equals(identificacao)) {
		    		
		    		if(base.get(x).getCardapio().getData().before(data) || base.get(x).getCardapio().getData().equals(data)) {
		    			
		    			historico.add(base.get(x));
		    		}		    
		    	}
		    }
		    
		    model.addAttribute("listagemHistorico", historico);
		    model.addAttribute("txt_data", new SimpleDateFormat("dd/MM/yyyy").format(data));
		}
		
		return "historico";
	}
}