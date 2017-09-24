package com.github.projetoleaf.controllers;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.github.projetoleaf.beans.Cliente;
import com.github.projetoleaf.beans.ClienteCategoria;
import com.github.projetoleaf.beans.ClienteCurso;
import com.github.projetoleaf.beans.Extrato;
import com.github.projetoleaf.beans.Reserva;
import com.github.projetoleaf.repositories.ClienteCategoriaRepository;
import com.github.projetoleaf.repositories.ClienteRepository;
import com.github.projetoleaf.repositories.ExtratoRepository;
import com.github.projetoleaf.repositories.ClienteCursoRepository;
import com.github.projetoleaf.repositories.ReservaRepository;

@Controller
public class ContaController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ClienteCategoriaRepository clienteCategoriaRepository;
	
	@Autowired
	private ClienteCursoRepository clienteCursoRepository;
	
	@Autowired
	private ReservaRepository reservaRepository;
	
	@Autowired
	private ExtratoRepository extratoRepository;
	
	@GetMapping("/conta")
	public String conta(Model model) throws ParseException {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();		
		
		if (!(authentication instanceof AnonymousAuthenticationToken)) {	
			
		    String identificacao = authentication.getName();	
		   
		    Cliente cliente = clienteRepository.buscarCliente(identificacao);		    
		    
		    SimpleDateFormat formatoDataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		    NumberFormat nf = NumberFormat.getCurrencyInstance(); 
		    
		    List<ClienteCategoria> todosOsClientesCategorias = clienteCategoriaRepository.findAll();
		    
		    ClienteCategoria categoria = new ClienteCategoria();
		    
		    for(int x = 0; x < todosOsClientesCategorias.size(); x++)
		    {
		    	if(todosOsClientesCategorias.get(x).getCliente().getIdentificacao() == cliente.getIdentificacao())
		    	{
		    		categoria = todosOsClientesCategorias.get(x);
		    	}
		    }
		    
		    List<ClienteCurso> todosOsClientesCursos = clienteCursoRepository.findAll();
		    
		    ClienteCurso curso = new ClienteCurso();
		    
		    for(int x = 0; x < todosOsClientesCursos.size(); x++)
		    {
		    	if(todosOsClientesCursos.get(x).getCliente().getIdentificacao() == cliente.getIdentificacao())
		    	{
		    		curso = todosOsClientesCursos.get(x);
		    	}
		    }
		    
		    List<Reserva> reservasDoCliente = reservaRepository.findAll();
		    
		    String ultimaReserva = "Nenhuma reserva solicitada";
		      
		    for(int x = 0; x < reservasDoCliente.size(); x++) {
		    	
		    	if(reservasDoCliente.get(x).getCliente().getIdentificacao() == cliente.getIdentificacao())
		    	{	
		    		ultimaReserva = formatoDataHora.format(reservasDoCliente.get(reservasDoCliente.size() - 1).getDataReserva());
		    	}
		    }
		    
		    List<Extrato> ultimoRegistroDoCliente = extratoRepository.buscarUltimoRegistroDoCliente(cliente.getId());
			
			BigDecimal saldo = null;
			
			if(!ultimoRegistroDoCliente.isEmpty()) {
			 	saldo = ultimoRegistroDoCliente.get(0).getSaldo();
			} else {
				saldo = new BigDecimal(0.00);
			}   
		    String creditosFormatado = nf.format(saldo);
		    
		    model.addAttribute("cliente", cliente);		   
		    model.addAttribute("categoria", categoria);
		    model.addAttribute("ultimaReserva", ultimaReserva);
		    model.addAttribute("curso", curso);
		    model.addAttribute("creditos", creditosFormatado);
		}
		
		return "conta";
	}	
}