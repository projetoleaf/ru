package com.github.projetoleaf.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.github.projetoleaf.beans.Cliente;
import com.github.projetoleaf.beans.ReservaItem;
import com.github.projetoleaf.beans.ReservasAdmin;
import com.github.projetoleaf.repositories.CardapioRepository;
import com.github.projetoleaf.repositories.ClienteRepository;
import com.github.projetoleaf.repositories.ReservaItemRepository;
import com.github.projetoleaf.repositories.ReservaRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/reservas")
public class ReservaAdminController {
	
	@Autowired
	private CardapioRepository cardapioRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ReservaRepository reservaRepository;
	
	@Autowired
	private ReservaItemRepository reservaItemRepository;
	
	@Autowired
    private MessageSource config;
	
	@GetMapping
	public String pesquisarReserva(Model model) throws ParseException {
		
		SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd/MM/yyyy");
		
		List<ReservaItem> reservasItensDoBD = reservaItemRepository.findAll();
		
		List<Cliente> todosOsClientesDoBD = clienteRepository.findAll();
		
		List<ReservasAdmin> todasAsReservas = new ArrayList<ReservasAdmin>();
		
		Date segunda = new Date();
		Date terca = new Date();
		Date quarta = new Date();
		Date quinta = new Date();
		Date sexta = new Date();
		
		for (int z = 0; z < todosOsClientesDoBD.size(); z++) {
			
			Calendar dataAtual = Calendar.getInstance();		
			dataAtual = verificarData(dataAtual);   
			
			ReservasAdmin reservasAdmin = new ReservasAdmin();
			
        	reservasAdmin.setId(Long.valueOf(z));
        	reservasAdmin.setNome(todosOsClientesDoBD.get(z).getNome());
        	reservasAdmin.setCreditos(todosOsClientesDoBD.get(z).getCreditos());
        
	        for (int i = 0; i < 5; i++) {	     	
	        	
	        	List<Object[]> dataDoBanco = cardapioRepository.verificarSeDataExisteNoBD(dataAtual.getTime());
	        	
	        	for(Object[] linhaDoBanco : dataDoBanco){
	        		
	        		String dataFormatada = formatoDesejado.format((Date) linhaDoBanco[1]);
	                Date dataVar = formatoDesejado.parse(dataFormatada);             
	                	
                	for(int x = 0; x < reservasItensDoBD.size(); x++) 
            		{
                		if(todosOsClientesDoBD.get(z).getNome() == reservasItensDoBD.get(x).getReserva().getCliente().getNome()) 
                		{
                			if(formatoDesejado.format(dataVar).equals(formatoDesejado.format(dataAtual.getTime())) && formatoDesejado.format(dataVar).equals(formatoDesejado.format(reservasItensDoBD.get(x).getCardapio().getData())))
                        	{    
                				if(reservasItensDoBD.get(x).getStatus().getDescricao().equals("Solicitado"))
                				{
                					Calendar cal = Calendar.getInstance();
                    				cal.setTime(reservasItensDoBD.get(x).getCardapio().getData());
                    				
                    				if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY)
                    				{
                    					reservasAdmin.setSegundaStatus(reservasItensDoBD.get(x).getStatus().getDescricao());
                    				}
                    				
                    				if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY)
                    				{
                    					reservasAdmin.setTercaStatus(reservasItensDoBD.get(x).getStatus().getDescricao());
                    				}
                    				
                    				if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY)
                    				{
                    					reservasAdmin.setQuartaStatus(reservasItensDoBD.get(x).getStatus().getDescricao());
                    				}
                    				
                    				if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY)
                    				{
                    					reservasAdmin.setQuintaStatus(reservasItensDoBD.get(x).getStatus().getDescricao());
                    				}
                    				
                    				if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)
                    				{
                    					reservasAdmin.setSextaStatus(reservasItensDoBD.get(x).getStatus().getDescricao());
                    				}
                				}            				
                        	}
                		}      			
            		}
                }       	
	        	
	        	switch(i) 
	        	{
	        		case 0:
	        			segunda = dataAtual.getTime();
	        		case 1:
	        			terca = dataAtual.getTime();
	        		case 2:
	        			quarta = dataAtual.getTime();
	        		case 3:
	        			quinta = dataAtual.getTime();
	        		case 4:
	        			sexta = dataAtual.getTime();
	        	}
	        	
	        	dataAtual.add(Calendar.DAY_OF_MONTH, 1);	
	        }
	        
	        if(reservasAdmin.getSegundaStatus() == null) {
	        	reservasAdmin.setSegundaStatus("Não reservado");
	        }        
	        
	        if(reservasAdmin.getTercaStatus() == null) {
	        	reservasAdmin.setTercaStatus("Não reservado");
	        }
	        
	        if(reservasAdmin.getQuartaStatus() == null) {
	        	reservasAdmin.setQuartaStatus("Não reservado");
	        }
	        
	        if(reservasAdmin.getQuintaStatus() == null) {
	        	reservasAdmin.setQuintaStatus("Não reservado");
	        }
	        
	        if(reservasAdmin.getSextaStatus() == null) {
	        	reservasAdmin.setSextaStatus("Não reservado");
	        }
	        
	        if(!(reservasAdmin.getSegundaStatus() == "Não reservado" && reservasAdmin.getTercaStatus() == "Não reservado" && reservasAdmin.getQuartaStatus() == "Não reservado" && reservasAdmin.getQuintaStatus() == "Não reservado" && reservasAdmin.getSextaStatus() == "Não reservado")) {
	        	todasAsReservas.add(reservasAdmin);
	        }	        
        }        
        
		model.addAttribute("listagemReservas", todasAsReservas);
		model.addAttribute("segunda", formatoDesejado.format(segunda));
		model.addAttribute("terca", formatoDesejado.format(terca));
		model.addAttribute("quarta", formatoDesejado.format(quarta));
		model.addAttribute("quinta", formatoDesejado.format(quinta));
		model.addAttribute("sexta", formatoDesejado.format(sexta));
		
		return "/reservas/pesquisar";
	}
	
	@GetMapping("/pagamento/{nome}")
	public String efetivarPagamento(@PathVariable String nome, Model model) throws ParseException, JsonGenerationException, JsonMappingException, IOException {
		
		SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd/MM/yyyy");
		
		List<ReservaItem> reservasItensDoBD = reservaItemRepository.findAll();
		
		Cliente cliente = clienteRepository.findByNome(nome);
		
		List<String> datasDasReservas = new ArrayList<String>();
		
		ReservasAdmin reservasAdmin = new ReservasAdmin();
		
		Date segunda = new Date();
		Date terca = new Date();
		Date quarta = new Date();
		Date quinta = new Date();
		Date sexta = new Date();
		
		Calendar dataAtual = Calendar.getInstance();		
		dataAtual = verificarData(dataAtual);   
    
        for (int i = 0; i < 5; i++) {	     	
        	
        	List<Object[]> dataDoBanco = cardapioRepository.verificarSeDataExisteNoBD(dataAtual.getTime());
        	
        	for(Object[] linhaDoBanco : dataDoBanco){
        		
        		String dataFormatada = formatoDesejado.format((Date) linhaDoBanco[1]);
                Date dataVar = formatoDesejado.parse(dataFormatada);   
                	
            	for(int x = 0; x < reservasItensDoBD.size(); x++) 
        		{
            		if(cliente.getNome() == reservasItensDoBD.get(x).getReserva().getCliente().getNome()) 
            		{
            			if(formatoDesejado.format(dataVar).equals(formatoDesejado.format(dataAtual.getTime())) && formatoDesejado.format(dataVar).equals(formatoDesejado.format(reservasItensDoBD.get(x).getCardapio().getData())))
                    	{    
            				if(reservasItensDoBD.get(x).getStatus().getDescricao().equals("Solicitado"))
            				{
            					Calendar cal = Calendar.getInstance();
                				cal.setTime(reservasItensDoBD.get(x).getCardapio().getData());
                				
                				if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY)
                				{
                					reservasAdmin.setSegundaStatus(reservasItensDoBD.get(x).getStatus().getDescricao());
                				}
                				
                				if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY)
                				{
                					reservasAdmin.setTercaStatus(reservasItensDoBD.get(x).getStatus().getDescricao());
                				}
                				
                				if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY)
                				{
                					reservasAdmin.setQuartaStatus(reservasItensDoBD.get(x).getStatus().getDescricao());
                				}
                				
                				if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY)
                				{
                					reservasAdmin.setQuintaStatus(reservasItensDoBD.get(x).getStatus().getDescricao());
                				}
                				
                				if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)
                				{
                					reservasAdmin.setSextaStatus(reservasItensDoBD.get(x).getStatus().getDescricao());
                				}
            				}            				
                    	}
            		}      			
        		}
            }       
        	
        	switch(i) 
        	{
        		case 0:
        			segunda = dataAtual.getTime();
        		case 1:
        			terca = dataAtual.getTime();
        		case 2:
        			quarta = dataAtual.getTime();
        		case 3:
        			quinta = dataAtual.getTime();
        		case 4:
        			sexta = dataAtual.getTime();
        	}
        	
        	dataAtual.add(Calendar.DAY_OF_MONTH, 1);	
        }
        
        if(!(reservasAdmin.getSegundaStatus() == null)) {
        	datasDasReservas.add(formatoDesejado.format(segunda));
        }
        
        if(!(reservasAdmin.getTercaStatus() == null)) {
        	datasDasReservas.add(formatoDesejado.format(terca));
        }
        
        if(!(reservasAdmin.getQuartaStatus() == null)) {
        	datasDasReservas.add(formatoDesejado.format(quarta));
        }
        
        if(!(reservasAdmin.getQuintaStatus() == null)) {
        	datasDasReservas.add(formatoDesejado.format(quinta));
        }
        
        if(!(reservasAdmin.getSextaStatus() == null)) {
        	datasDasReservas.add(formatoDesejado.format(sexta));
        }
		
	    model.addAttribute("datasReservas", datasDasReservas);	    
	    model.addAttribute("reserva", new ReservasAdmin());
	    model.addAttribute(cliente);
	    
		return "/reservas/pagamento";
	}
	
	@PostMapping("/reservas/pagamento/salvar")
	public String salvarReservas(@RequestParam("nome") Date[] idsDatas, @RequestParam("valor") BigDecimal valor) {
		
		try {
			System.out.println("Valor = " + valor);
			
			for(Date i : idsDatas) {
				System.out.println("O que tem aqui? = " + i);
			}
        }
        catch (Exception ex) {
            log.error("Erro de processamento", ex);
        }
		
		return "redirect:/reservas";
	}
	
	public static Calendar verificarData(Calendar data) {
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
        // se for sabado
        else if (data.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
        {
            data.add(Calendar.DATE, 2);
        }
        // se for domingo
        else if (data.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
        {
            data.add(Calendar.DATE, 1);
        }
        return data;
    }
}