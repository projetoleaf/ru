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
import com.github.projetoleaf.beans.ClienteCategoria;
import com.github.projetoleaf.beans.Extrato;
import com.github.projetoleaf.beans.TipoValor;
import com.github.projetoleaf.repositories.CardapioRepository;
import com.github.projetoleaf.repositories.ClienteCategoriaRepository;
import com.github.projetoleaf.repositories.ClienteRepository;
import com.github.projetoleaf.repositories.ExtratoRepository;
import com.github.projetoleaf.repositories.FeriadoRepository;
import com.github.projetoleaf.repositories.ReservaRepository;
import com.github.projetoleaf.repositories.StatusRepository;
import com.github.projetoleaf.repositories.TipoRefeicaoRepository;
import com.github.projetoleaf.repositories.TipoValorRepository;
import com.github.projetoleaf.repositories.ReservaItemRepository;

@Controller
public class ReservaClienteController {	
	
	@Autowired
	private StatusRepository statusRepository;
	
	@Autowired
	private ReservaRepository reservaRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ExtratoRepository extratoRepository;
	
	@Autowired
	private FeriadoRepository feriadoRepository;
	
	@Autowired
	private CardapioRepository cardapioRepository;
	
	@Autowired
	private TipoValorRepository tipoValorRepository;
	
	@Autowired
	private ReservaItemRepository reservaItemRepository;
	
	@Autowired
	private TipoRefeicaoRepository tipoRefeicaoRepository;
	
	@Autowired
	private ClienteCategoriaRepository clienteCategoriaRepository;
	
	@GetMapping("/reserva")
	public String reservaRefeicoes(Model model) throws JsonGenerationException, JsonMappingException, IOException, ParseException {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();		
		
		BigDecimal saldo = new BigDecimal(0.00);
		List<Cardapio> cardapio = new ArrayList<Cardapio>();		
		ClienteCategoria clienteCategoria = new ClienteCategoria();		
		
		if (!(authentication instanceof AnonymousAuthenticationToken)) {	
			
			int countSegunda = 0;
			int countTerca = 0;
			int countQuarta = 0;
			int countQuinta = 0;
			int countSexta = 0;	
			
			SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd/MM/yyyy");	
			SimpleDateFormat formatoHora = new SimpleDateFormat("HH");	
			
		    String identificacao = authentication.getName();			    
		    
		    Cliente cliente = clienteRepository.buscarCliente(identificacao); //Pega todos os dados da pessoa logada	
		    
		    List<Extrato> ultimoRegistroDoCliente = extratoRepository.buscarUltimoRegistroDoCliente(cliente.getId());		    
		    
		    if(!ultimoRegistroDoCliente.isEmpty()) {
		    	saldo = ultimoRegistroDoCliente.get(0).getSaldo();
		    }			 	
			
			List<Object[]> todasAsReservasDoCliente = reservaItemRepository.todasAsReservasDoCliente(cliente.getId());		
				
			Calendar dataAtual = Calendar.getInstance();					
			dataAtual = verificarData(dataAtual);   
			
			Calendar penultimoDiaUtil = Calendar.getInstance();			
			penultimoDiaUtil.setTime(dataAtual.getTime());			
			penultimoDiaUtil.add(Calendar.DATE, 4);
			
			List<Date> todasAsDatasDaProximaSemana = cardapioRepository.todasAsDatasDaProximaSemana(dataAtual.getTime(), penultimoDiaUtil.getTime());
			penultimoDiaUtil.setTime(todasAsDatasDaProximaSemana.get(todasAsDatasDaProximaSemana.size() - 2));
			
			Calendar primeiroDiaUtil = Calendar.getInstance();
			primeiroDiaUtil.setTime(todasAsDatasDaProximaSemana.get(0));
			
			Calendar dataHoje = Calendar.getInstance();			
			Calendar diaDaSemana = Calendar.getInstance();
			
			Calendar seg = Calendar.getInstance();
			Calendar ter = Calendar.getInstance();
			Calendar qua = Calendar.getInstance();
			Calendar qui = Calendar.getInstance();			
			
			seg.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			ter.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
			qua.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
			qui.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);	
			
			if(dataHoje.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY && feriadoRepository.findByData(dataHoje.getTime()) == null) {
				diaDaSemana.setTime(seg.getTime());
			} 
			else if (dataHoje.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY && feriadoRepository.findByData(seg.getTime()) != null) {
				diaDaSemana.setTime(ter.getTime());
			} 
			else if (dataHoje.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY && feriadoRepository.findByData(ter.getTime()) != null) {
				diaDaSemana.setTime(qua.getTime());
			} 
			else if (dataHoje.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY && feriadoRepository.findByData(qua.getTime()) != null) {
				diaDaSemana.setTime(qui.getTime());
			} 
			else {
				diaDaSemana.setTime(seg.getTime());
			}
			
			Integer horaDeHoje = Integer.parseInt(formatoHora.format(dataHoje.getTime()));
			
			
			//Consistências dos finais de semana. Não apague \/ 
			//if(!(dataHoje.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) && !(dataHoje.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) && (dataHoje.get(Calendar.DAY_OF_WEEK) <= dataSemana.get(Calendar.DAY_OF_WEEK))) {
			if(dataHoje.getTime().equals(diaDaSemana.getTime()) && horaDeHoje >= 7  && dataHoje.get(Calendar.DAY_OF_WEEK) <= penultimoDiaUtil.get(Calendar.DAY_OF_WEEK)) {			
				
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
						
						if(countSegunda < 360 || countTerca < 360 || countQuarta < 360 || countQuinta < 360 || countSexta < 360) {
							
							String dataFormatada = formatoDesejado.format((Date)linhaDoBanco[1]);
			                Date dataVar = formatoDesejado.parse(dataFormatada);
			                
			                c.setId((Long)linhaDoBanco[0]);
			                c.setData(dataVar);
			                
			                if(!(todasAsReservasDoCliente.isEmpty())) {       
			                	
			                	if(reservaItemRepository.verificarSeReservaExiste(cliente.getId(), (Date)linhaDoBanco[1]) == null && formatoDesejado.format(c.getData()).equals(formatoDesejado.format(dataAtual.getTime()))) {
			                		cardapio.add(c);
			                	}    		                	
			                } else {		                	
			                	if(formatoDesejado.format(c.getData()).equals(formatoDesejado.format(dataAtual.getTime()))) {
			                		cardapio.add(c);
			                	}  
			                }   		                
						}
		            }    
		        	
		        	dataAtual.add(Calendar.DAY_OF_MONTH, 1);	            
		        } 		
				
			} else if (!dataHoje.getTime().equals(diaDaSemana.getTime()) && dataHoje.get(Calendar.DAY_OF_WEEK) <= penultimoDiaUtil.get(Calendar.DAY_OF_WEEK)) {			
				
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
						
						if(countSegunda < 360 || countTerca < 360 || countQuarta < 360 || countQuinta < 360 || countSexta < 360) {
							
							String dataFormatada = formatoDesejado.format((Date)linhaDoBanco[1]);
			                Date dataVar = formatoDesejado.parse(dataFormatada);
			                
			                c.setId((Long)linhaDoBanco[0]);
			                c.setData(dataVar);
			                
			                if(!(todasAsReservasDoCliente.isEmpty())) {       
			                	
			                	if(reservaItemRepository.verificarSeReservaExiste(cliente.getId(), (Date)linhaDoBanco[1]) == null && formatoDesejado.format(c.getData()).equals(formatoDesejado.format(dataAtual.getTime()))) {
			                		cardapio.add(c);
			                	}    		                	
			                } else {		                	
			                	if(formatoDesejado.format(c.getData()).equals(formatoDesejado.format(dataAtual.getTime()))) {
			                		cardapio.add(c);
			                	}  
			                }   		                
						}
		            }    
		        	
		        	dataAtual.add(Calendar.DAY_OF_MONTH, 1);	            
		        } 		
			}
			
			clienteCategoria = clienteCategoriaRepository.findByCliente(cliente);
		}
			
		model.addAttribute("datas", new Cardapio());
		model.addAttribute("todasAsDatas", cardapio);
		model.addAttribute("todosOsTipos", tipoRefeicaoRepository.findAll());	
		model.addAttribute("saldo", saldo);
		model.addAttribute("valorRefeicao", clienteCategoria.getCategoria().getValorComSubsidio());
		
		ObjectMapper mapper = new ObjectMapper();// jackson lib for converting to json
        String objectJSON = mapper.writeValueAsString(cardapio);// json string
        model.addAttribute("objectJSON", objectJSON);
		
		return "reserva";
	}
	
	@PostMapping("/reserva/salvar")
	public String salvarReservaRefeicoes(@RequestParam("data") String[] idsCardapios, @RequestParam("tipoRefeicao") Integer[] idsTipoRefeicao, @RequestParam("pagamento") String pag) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (!(authentication instanceof AnonymousAuthenticationToken)) {	
			
		    String identificacao = authentication.getName();			    
		    
		    Cliente cliente = clienteRepository.buscarCliente(identificacao); //Pega o id da pessoa logada
		    
			TipoValor tipoValor = tipoValorRepository.findByDescricao("Subsidiada");
			
			List<Extrato> ultimoRegistroDoCliente = extratoRepository.buscarUltimoRegistroDoCliente(cliente.getId());	
			BigDecimal saldo = new BigDecimal(0.00);
		    
		    if(!ultimoRegistroDoCliente.isEmpty()) {
		    	saldo = ultimoRegistroDoCliente.get(0).getSaldo();
		    }	
			
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
		    
		    List<Integer> tiposRefeicoesSelecionados = new ArrayList<Integer>();
		    
		    for (int z = 0; z < idsTipoRefeicao.length; z++) {
		    	
		    	if(idsTipoRefeicao[z] != null) {
		    		tiposRefeicoesSelecionados.add(idsTipoRefeicao[z]);
		    	}
		    }
		    
		    ClienteCategoria clienteCategoria = clienteCategoriaRepository.findByCliente(cliente);
		    
		    extrato.setCliente(cliente);
			extrato.setDataTransacao(timestamp);
			
			BigDecimal transacao = BigDecimal.valueOf(idsCardapios.length).multiply(clienteCategoria.getCategoria().getValorComSubsidio());
			
			if(pag.equals("1")) { //Créditos				
				extrato.setSaldo(saldo.subtract(transacao));
			} else if(pag.equals("2")) {  //Dinheiro
				extrato.setSaldo(saldo);
			}			
			
			extrato.setTransacao(transacao.negate());
			
			extratoRepository.save(extrato);
			
		    for (int x = 0; x <= idsCardapios.length -1; x++) {
			   
			   ReservaItem reservaItem = new ReservaItem();		
			   Reserva r = new Reserva();
			   Cardapio c = new Cardapio();
			   Status s = new Status();
			   TipoRefeicao t = null;			   
			   
			   if(tiposRefeicoesSelecionados.get(x) % 2 == 0) {
				   t = tipoRefeicaoRepository.findByDescricao("Vegetariano");  
			   } else {
				   t = tipoRefeicaoRepository.findByDescricao("Tradicional"); 
			   } 
			   
			   if(pag.equals("1")) { //Créditos				
					s = statusRepository.findByDescricao("Pago");
				} else if(pag.equals("2")) {  //Dinheiro
					s = statusRepository.findByDescricao("Solicitado");
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