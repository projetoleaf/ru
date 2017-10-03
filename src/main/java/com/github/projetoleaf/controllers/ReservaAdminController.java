package com.github.projetoleaf.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.github.projetoleaf.beans.Cliente;
import com.github.projetoleaf.beans.ClienteCategoria;
import com.github.projetoleaf.beans.Extrato;
import com.github.projetoleaf.beans.ReservaItem;
import com.github.projetoleaf.beans.ReservasAdmin;
import com.github.projetoleaf.beans.Status;
import com.github.projetoleaf.repositories.CardapioRepository;
import com.github.projetoleaf.repositories.ClienteCategoriaRepository;
import com.github.projetoleaf.repositories.ClienteRepository;
import com.github.projetoleaf.repositories.ExtratoRepository;
import com.github.projetoleaf.repositories.ReservaItemRepository;
import com.github.projetoleaf.repositories.StatusRepository;

@Controller
@RequestMapping("/reservas")
public class ReservaAdminController {
	
	@Autowired
	private CardapioRepository cardapioRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ReservaItemRepository reservaItemRepository;
	
	@Autowired
	private StatusRepository statusRepository;
	
	@Autowired
	private ExtratoRepository extratoRepository;
	
	@Autowired
	private ClienteCategoriaRepository clienteCategoriaRepository;
	
	@GetMapping
	public String pesquisarReserva(Model model) throws ParseException {
		
		SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd/MM/yyyy");
		NumberFormat nf = NumberFormat.getCurrencyInstance(); 
		
		List<ReservaItem> reservasItensDoBD = reservaItemRepository.findAll();		
		List<Cliente> todosOsClientesDoBD = clienteRepository.findAll();
		
		List<ReservasAdmin> todasAsReservas = new ArrayList<ReservasAdmin>();
		
		BigDecimal saldo = new BigDecimal(0.00);
		
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
        	
        	List<Extrato> ultimoRegistroDoCliente = extratoRepository.buscarUltimoRegistroDoCliente(todosOsClientesDoBD.get(z).getId());		    
		    
		    if(!ultimoRegistroDoCliente.isEmpty()) {
		    	saldo = ultimoRegistroDoCliente.get(0).getSaldo();
		    }	
        
		    reservasAdmin.setCreditos(nf.format(saldo));
		    
	        for (int i = 0; i < 5; i++) {	     	
	        	
	        	List<Object[]> dataDoBanco = cardapioRepository.verificarSeDataExisteNoBD(dataAtual.getTime()); 	
	        		
        		for(Object[] linhaDoBanco : dataDoBanco) {
	        		
	        		String dataFormatada = formatoDesejado.format((Date) linhaDoBanco[1]);
	        		
	        		Date dataVar = null;
	        		dataVar = formatoDesejado.parse(dataFormatada);    
	                	
                	for(int x = 0; x < reservasItensDoBD.size(); x++) 
            		{
                		if(todosOsClientesDoBD.get(z).getNome() == reservasItensDoBD.get(x).getReserva().getCliente().getNome()) 
                		{
                			if(formatoDesejado.format(dataVar).equals(formatoDesejado.format(dataAtual.getTime())) && formatoDesejado.format(dataVar).equals(formatoDesejado.format(reservasItensDoBD.get(x).getCardapio().getData())))
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
        		
        		if(!dataDoBanco.isEmpty()) {
        				        		
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
        		} else {
        		
        			Calendar cal = Calendar.getInstance();
    				cal.setTime(dataAtual.getTime());
        			
	        		if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
	    	        	reservasAdmin.setSegundaStatus("Dia indisponível");
	    	        }        
	    	        
	    	        if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
	    	        	reservasAdmin.setTercaStatus("Dia indisponível");
	    	        }
	    	        
	    	        if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
	    	        	reservasAdmin.setQuartaStatus("Dia indisponível");
	    	        }
	    	        
	    	        if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
	    	        	reservasAdmin.setQuintaStatus("Dia indisponível");
	    	        }
	    	        
	    	        if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
	    	        	reservasAdmin.setSextaStatus("Dia indisponível");
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
	        
	        if(!(reservasAdmin.getSegundaStatus() == "Não reservado" && reservasAdmin.getSegundaStatus() == "Dia indisponível" && reservasAdmin.getTercaStatus() == "Não reservado" && reservasAdmin.getTercaStatus() == "Dia indisponível" && reservasAdmin.getQuartaStatus() == "Não reservado" && reservasAdmin.getQuartaStatus() == "Dia indisponível" && reservasAdmin.getQuintaStatus() == "Não reservado" && reservasAdmin.getQuintaStatus() == "Dia indisponível" && reservasAdmin.getSextaStatus() == "Não reservado" && reservasAdmin.getSextaStatus() == "Dia indisponível")) {
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
		
		Date segunda = new Date();
		Date terca = new Date();
		Date quarta = new Date();
		Date quinta = new Date();
		Date sexta = new Date();
		
		BigDecimal creditos = new BigDecimal(0.00);
		
		ReservasAdmin reservasAdmin = new ReservasAdmin();
		ClienteCategoria clienteCategoria = new ClienteCategoria();
		
		List<String> datasDasReservas = new ArrayList<String>();
		NumberFormat nf = NumberFormat.getCurrencyInstance(); 
		SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd/MM/yyyy");
		
		Cliente cliente = clienteRepository.findByNome(nome);
		List<ReservaItem> reservasItensDoBD = reservaItemRepository.findAll();		
		List<Extrato> ultimoRegistroDoCliente = extratoRepository.buscarUltimoRegistroDoCliente(cliente.getId());	
		
		if(!ultimoRegistroDoCliente.isEmpty()) {
	    	creditos = ultimoRegistroDoCliente.get(0).getSaldo();
	    }		
		
		reservasAdmin.setCreditos(nf.format(creditos));
		
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
        
        clienteCategoria = clienteCategoriaRepository.findByCliente(cliente);
		
        model.addAttribute(cliente);
	    model.addAttribute(reservasAdmin);
	    model.addAttribute("reserva", new ReservasAdmin());	  
	    model.addAttribute("datasReservas", datasDasReservas);	      
	    model.addAttribute("valorRefeicao", clienteCategoria.getCategoria().getValorComSubsidio());
	    
		return "/reservas/pagamento";
	}
	
	@PostMapping("/salvar")
	public String salvarReservas(@RequestParam("nome") String nome, @RequestParam("data") String[] datasSelecionadas, @RequestParam("valor") String valor, @RequestParam("recargas") String recargas, @RequestParam("utilizarCreditos") Boolean creditos) {
		
		SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd/MM/yyyy");
		
		List<ReservaItem> reservasItensDoBD = reservaItemRepository.findAll();
		
		System.out.println(recargas);
		System.out.println(valor);
		System.out.println(creditos);
		
		if(!creditos) {
			
			if(recargas == "0,00") {
				System.out.println("caiu");
				/*for(int z = 0; z < datasSelecionadas.length; z++) {
				
					for(int x = 0; x < reservasItensDoBD.size(); x++) {
						
						if(reservasItensDoBD.get(x).getReserva().getCliente().getNome().equals(nome)) {
							
							if(formatoDesejado.format(reservasItensDoBD.get(x).getCardapio().getData()).equals(datasSelecionadas[z])) {
								
								Status status = statusRepository.findByDescricao("Pago");						
								reservasItensDoBD.get(x).setStatus(status);						
								reservaItemRepository.save(reservasItensDoBD.get(x));
								
								z++;
							}
						}
					}
				}*/	
			} else {
				Cliente cliente = clienteRepository.findByNome(nome);
				List<Extrato> ultimoRegistroDoCliente = extratoRepository.buscarUltimoRegistroDoCliente(cliente.getId());
				
				Timestamp timestamp = new Timestamp(System.currentTimeMillis()); //Data e hora atual			
				BigDecimal saldo = null;
				
				if(!ultimoRegistroDoCliente.isEmpty()) {
				 	saldo = ultimoRegistroDoCliente.get(0).getSaldo();
				} else {
					saldo = new BigDecimal(0.00);
				}
				
				Extrato extrato = new Extrato();
				extrato.setCliente(cliente);
				extrato.setTransacao(new BigDecimal(recargas.replaceAll(",",".")));
				extrato.setDataTransacao(timestamp);
				extrato.setSaldo(saldo.add(new BigDecimal(recargas.replaceAll(",","."))));
				
				extratoRepository.save(extrato);
				
				/*for(int z = 0; z < datasSelecionadas.length; z++) {
				
					for(int x = 0; x < reservasItensDoBD.size(); x++) {
						
						if(reservasItensDoBD.get(x).getReserva().getCliente().getNome().equals(nome)) {
							
							if(formatoDesejado.format(reservasItensDoBD.get(x).getCardapio().getData()).equals(datasSelecionadas[z])) {
								
								Status status = statusRepository.findByDescricao("Pago");						
								reservasItensDoBD.get(x).setStatus(status);						
								reservaItemRepository.save(reservasItensDoBD.get(x));
								
								z++;
							}
						}
					}
				}*/	
			}
		} else {
			
			if(recargas == "0,00") {
				
				Cliente cliente = clienteRepository.findByNome(nome);
				List<Extrato> ultimoRegistroDoCliente = extratoRepository.buscarUltimoRegistroDoCliente(cliente.getId());
				
				Timestamp timestamp = new Timestamp(System.currentTimeMillis()); //Data e hora atual			
				BigDecimal saldo = null;
				
				if(!ultimoRegistroDoCliente.isEmpty()) {
				 	saldo = ultimoRegistroDoCliente.get(0).getSaldo();
				} else {
					saldo = new BigDecimal(0.00);
				}
				
				Extrato extrato = new Extrato();
				extrato.setCliente(cliente);
				extrato.setTransacao(new BigDecimal(recargas.replaceAll(",",".")));
				extrato.setDataTransacao(timestamp);
				extrato.setSaldo(saldo.add(new BigDecimal(recargas.replaceAll(",","."))));
				
				extratoRepository.save(extrato);
			}
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