package com.github.projetoleaf.controllers;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.github.projetoleaf.repositories.ClienteRepository;
import com.github.projetoleaf.repositories.ReservaRepository;
import com.github.projetoleaf.repositories.StatusRepository;
import com.github.projetoleaf.repositories.TipoValorRepository;
import com.github.projetoleaf.repositories.ReservaItemRepository;

@Controller
public class ReservaClienteController {
	
	@Autowired
	private ReservaRepository reservaRepository;
	
	@Autowired
	private CardapioRepository cardapioRepository;
	
	@Autowired
	private ReservaItemRepository reservaItemRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private TipoValorRepository tipoValorRepository;
	
	@Autowired
	private StatusRepository statusRepository;
	
	@GetMapping("/reserva")
	public String reservaRefeicoes(Model model) throws JsonGenerationException, JsonMappingException, IOException, ParseException {
		
		SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd/MM/yyyy");
		
		List<Cardapio> cardapio = new ArrayList<Cardapio>();				
		List<Reserva> todasAsReservasDoBD = reservaRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
		
		Calendar dataAtual = Calendar.getInstance();		
		
		int count = 0;
		
		for(int c = 0; c < todasAsReservasDoBD.size(); c++) {
			String dataHoraBanco = formatoDesejado.format(todasAsReservasDoBD.get(c).getDataHora());
			String dataDeHoje = formatoDesejado.format(dataAtual.getTime());
			
			if(dataHoraBanco.equals(dataDeHoje)) {
				count++;
			}
		}	
		
		System.out.println("Contador = " + count);
		
		if(count < 360) {
			dataAtual = verificarData(dataAtual);   
			
			for (int i = 0; i < 5; i++) {
	        	
	        	Cardapio c = new Cardapio();        	
	        	List<Object[]> dataDoBanco = cardapioRepository.verificarSeDataExisteNoBD(dataAtual.getTime());
	        	
	        	for(Object[] linhaDoBanco : dataDoBanco){
	        		
	        		String dataFormatada = formatoDesejado.format((Date)linhaDoBanco[1]);
	                Date dataVar = formatoDesejado.parse(dataFormatada);
	                
	                c.setId((Long)linhaDoBanco[0]);
	                c.setData(dataVar);
	            	
	            	if(formatoDesejado.format(c.getData()).equals(formatoDesejado.format(dataAtual.getTime())))
	                	cardapio.add(c);
	            }       	
	        	
	        	dataAtual.add(Calendar.DAY_OF_MONTH, 1);	            
	        }
		}        
        
		model.addAttribute("datas", new Cardapio());
		model.addAttribute("todasAsDatas", cardapio);
		
		//List<Cardapio> teste = new ArrayList<Cardapio>();
		
		ObjectMapper mapper = new ObjectMapper();// jackson lib for converting to json
        String objectJSON = mapper.writeValueAsString(cardapio);// json string
        model.addAttribute("objectJSON", objectJSON);
		
		return "reserva";
	}
	
	@PostMapping("/reserva/salvar")
	public String salvarReservaRefeicoes(@RequestParam("data") String[] idsCardapios) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();			
		
		if (!(authentication instanceof AnonymousAuthenticationToken)) {	
			
		    String identificacao = authentication.getName();			    
		    
		    Cliente cliente = clienteRepository.buscarCliente(identificacao); //Pega o id da pessoa logada
		    
			TipoValor tipoValor = tipoValorRepository.findByDescricao("Subsidiada");
			
			Reserva reserva = new Reserva();
			
		    reserva.setCliente(cliente); 
		    reserva.setTipoValor(tipoValor); //Definir como subsidiada caso seja umas das 360 primeiras refeições
		    Timestamp timestamp = new Timestamp(System.currentTimeMillis()); //Data e hora atual	    
		    reserva.setDataHora(timestamp);
		    
		    reservaRepository.save(reserva);		
		    
		    List<Reserva> teste = reservaRepository.findAll();
		    
		    Long id = null;
		    
		    for(int x = 0; x < teste.size(); x++){
		    	
		    	if(teste.get(x).getCliente().getIdentificacao() == cliente.getIdentificacao())
		    	{
		    		id = teste.get(teste.size() - 1).getId();
		    	}
		    }
		    
		    System.out.println("Vamos ver se funciona o ultimo ID Reserva de acordo com o cliente " + id);
			
		    for (int x = 0; x <= idsCardapios.length -1; x++) {
			   
			   ReservaItem reservaItem = new ReservaItem();		
			   Reserva r = new Reserva();
			   Cardapio c = new Cardapio();
			   Status s = statusRepository.findByDescricao("Solicitado");
			   
			   r.setId(id);
			   c.setId(Long.parseLong((idsCardapios[x])));
			   
			   reservaItem.setReserva(r);
			   reservaItem.setCardapio(c);
			   reservaItem.setStatus(s);	   
			   
			   reservaItemRepository.save(reservaItem);
		    }
		}
	    
	    return "redirect:/historico";
	}
	
	public static Calendar verificarData(Calendar data)
    {
		// se for segunda
        if (data.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY)
        {
            data.add(Calendar.DATE, 7);
        }
        // se for terça
        else if (data.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY)
        {
            data.add(Calendar.DATE, 6);
        }
        // se for quarta
        else if (data.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY)
        {
            data.add(Calendar.DATE, 5);
        }
        // se for quinta
        else if (data.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY)
        {
            data.add(Calendar.DATE, 4);
        }
        // se for sexta
        else if (data.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)
        {
            data.add(Calendar.DATE, 3);
        }
        return data;
    }
	
}