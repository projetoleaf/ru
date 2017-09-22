package com.github.projetoleaf.controllers;

import java.io.IOException;
import java.math.BigDecimal;
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
import com.github.projetoleaf.beans.TipoRefeicao;
import com.github.projetoleaf.beans.Cliente;
import com.github.projetoleaf.beans.Extrato;
import com.github.projetoleaf.beans.TipoValor;
import com.github.projetoleaf.repositories.CardapioRepository;
import com.github.projetoleaf.repositories.ClienteRepository;
import com.github.projetoleaf.repositories.ExtratoRepository;
import com.github.projetoleaf.repositories.ReservaRepository;
import com.github.projetoleaf.repositories.StatusRepository;
import com.github.projetoleaf.repositories.TipoRefeicaoRepository;
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
	
	@Autowired
	private TipoRefeicaoRepository tipoRefeicaoRepository;
	
	@Autowired
	private ExtratoRepository extratoRepository;
	
	
	@GetMapping("/reserva")
	public String reservaRefeicoes(Model model) throws JsonGenerationException, JsonMappingException, IOException, ParseException {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();		
		
		List<Cardapio> cardapio = new ArrayList<Cardapio>();
		List<String> diasDaSemanaReservados = new ArrayList<String>();
		
		int situacao = 0; //Se for zero, não há nenhuma data no banco. Se for um, limite de datas atingidos. Se for dois, fora do período de reservas
		
		if (!(authentication instanceof AnonymousAuthenticationToken)) {	
			
		    String identificacao = authentication.getName();			    
		    
		    Cliente cliente = clienteRepository.buscarCliente(identificacao); //Pega o id da pessoa logada
		
			SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd/MM/yyyy");	
			List<Date> todasAsReservasDoCliente = reservaItemRepository.todasAsReservasDoCliente(cliente.getId());		
				
			Calendar dataAtual = Calendar.getInstance();		
			
			int countSegunda = 0;
			int countTerca = 0;
			int countQuarta = 0;
			int countQuinta = 0;
			int countSexta = 0;			
			int count = 0;
			
			dataAtual = verificarData(dataAtual);   
			
			for (int i = 0; i < 5; i++) {
	        	
	        	Cardapio c = new Cardapio();        	
	        	List<Object[]> dataDoBanco = cardapioRepository.verificarSeDataExisteNoBD(dataAtual.getTime());
	        	
	        	for(Object[] linhaDoBanco : dataDoBanco) {  		
	                
	                Calendar cal = Calendar.getInstance();
					cal.setTime((Date)linhaDoBanco[1]);
					
					if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY)
					{
						countSegunda = reservaItemRepository.qtdeDeReservasPorData((Date)linhaDoBanco[1]);
					}
					
					if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY)
					{
						countTerca = reservaItemRepository.qtdeDeReservasPorData((Date)linhaDoBanco[1]);
					}
					
					if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY)
					{
						countQuarta = reservaItemRepository.qtdeDeReservasPorData((Date)linhaDoBanco[1]);
					}
					
					if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY)
					{
						countQuinta = reservaItemRepository.qtdeDeReservasPorData((Date)linhaDoBanco[1]);
					}
					
					if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)
					{
						countSexta = reservaItemRepository.qtdeDeReservasPorData((Date)linhaDoBanco[1]);
					}
					
					if(countSegunda < 360 && countTerca < 360 && countQuarta < 360 && countQuinta < 360 && countSexta < 360) {
						
						String dataFormatada = formatoDesejado.format((Date)linhaDoBanco[1]);
		                Date dataVar = formatoDesejado.parse(dataFormatada);
		                
		                c.setId((Long)linhaDoBanco[0]);
		                c.setData(dataVar);
		                
		                for(int a = 0; a < todasAsReservasDoCliente.size(); a++) {          	
		                	
		                	if(formatoDesejado.format(c.getData()).equals(formatoDesejado.format(todasAsReservasDoCliente.get(a)))) {
		                		
		                		if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY)
		    					{
		    						diasDaSemanaReservados.add("seg");
		    					}
		    					
		    					if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY)
		    					{
		    						diasDaSemanaReservados.add("ter");
		    					}
		    					
		    					if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY)
		    					{
		    						diasDaSemanaReservados.add("qua");
		    					}
		    					
		    					if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY)
		    					{
		    						diasDaSemanaReservados.add("qui");
		    					}
		    					
		    					if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)
		    					{
		    						diasDaSemanaReservados.add("sex");
		    					}
		                	}
		                	
		                	if(formatoDesejado.format(c.getData()).equals(formatoDesejado.format(dataAtual.getTime())) && count == 0) {
		                		cardapio.add(c);
		                		count = 1;
		                	}        	
		                }  	
		                
		                count = 0;
					} else {
						situacao = 1;
					}
	            }    
	        	
	        	dataAtual.add(Calendar.DAY_OF_MONTH, 1);	            
	        }     			
		}
			
		model.addAttribute("datas", new Cardapio());
		model.addAttribute("situacao", situacao);
		model.addAttribute("todasAsDatas", cardapio);
		model.addAttribute("todosOsTipos", tipoRefeicaoRepository.findAll());		
		model.addAttribute("diasDaSemanaReservados", diasDaSemanaReservados);
		
		ObjectMapper mapper = new ObjectMapper();// jackson lib for converting to json
        String objectJSON = mapper.writeValueAsString(cardapio);// json string
        model.addAttribute("objectJSON", objectJSON);
		
		return "reserva";
	}
	
	@PostMapping("/reserva/salvar")
	public String salvarReservaRefeicoes(@RequestParam("data") String[] idsCardapios, @RequestParam("tipoRefeicao") Integer[] idsTipoRefeicao) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();			
		
		if (!(authentication instanceof AnonymousAuthenticationToken)) {	
			
		    String identificacao = authentication.getName();			    
		    
		    Cliente cliente = clienteRepository.buscarCliente(identificacao); //Pega o id da pessoa logada
		    
			TipoValor tipoValor = tipoValorRepository.findByDescricao("Subsidiada");
			
			Reserva reserva = new Reserva();
			
			Extrato extrato = new Extrato();
			
		    reserva.setCliente(cliente); 
		    reserva.setTipoValor(tipoValor); //Definir como subsidiada caso seja umas das 360 primeiras refeições
		    Timestamp timestamp = new Timestamp(System.currentTimeMillis()); //Data e hora atual	    
		    reserva.setDataReserva(timestamp);
		    
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
		    
		    List<Integer> idsTeste = new ArrayList<Integer>();
		    
		    for (int z = 0; z < idsTipoRefeicao.length; z++) {
		    	
		    	if(idsTipoRefeicao[z] != null) {
		    		idsTeste.add(idsTipoRefeicao[z]);
		    	}
		    }
		    
		    extrato.setCliente(cliente);
			extrato.setDataTransacao(timestamp);
			extrato.setSaldo(new BigDecimal(0.00));
			extrato.setTransacao(new BigDecimal(0.00));
			
			extratoRepository.save(extrato);
			
		    for (int x = 0; x <= idsCardapios.length -1; x++) {
			   
			   ReservaItem reservaItem = new ReservaItem();		
			   Reserva r = new Reserva();
			   Cardapio c = new Cardapio();
			   Status s = statusRepository.findByDescricao("Solicitado");
			   TipoRefeicao t = null;			   
			   
			   if(idsTeste.get(x) % 2 == 0) {
				   t = tipoRefeicaoRepository.findByDescricao("Vegetariano");  
			   } else {
				   t = tipoRefeicaoRepository.findByDescricao("Tradicional"); 
			   } 
				   
			   r.setId(id);
			   c.setId(Long.parseLong((idsCardapios[x])));			   
			   
			   reservaItem.setReserva(r);
			   reservaItem.setCardapio(c);
			   reservaItem.setStatus(s);	   
			   reservaItem.setTipoRefeicao(t);
			   reservaItem.setExtrato(extrato);
			   
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