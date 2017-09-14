package com.github.projetoleaf.controllers;

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
import com.github.projetoleaf.beans.ClienteTipoRefeicao;
import com.github.projetoleaf.beans.Reserva;
import com.github.projetoleaf.repositories.ClienteCategoriaRepository;
import com.github.projetoleaf.repositories.ClienteRepository;
import com.github.projetoleaf.repositories.ClienteTipoRefeicaoRepository;
import com.github.projetoleaf.repositories.ReservaRepository;

@Controller
public class ContaController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ClienteCategoriaRepository clienteCategoriaRepository;
	
	@Autowired
	private ClienteTipoRefeicaoRepository clienteTipoRefeicaoRepository;
	
	@Autowired
	private ReservaRepository reservaRepository;
	
	@GetMapping("/conta")
	public String conta(Model model) throws ParseException {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();		
		
		if (!(authentication instanceof AnonymousAuthenticationToken)) {	
			
		    String identificacao = authentication.getName();	
		   
		    Cliente cliente = clienteRepository.buscarCliente(identificacao);
		    
		    List<ClienteTipoRefeicao> cTipoRefeicao = clienteTipoRefeicaoRepository.findAll();
		    
		    List<ClienteCategoria> cCategoria = clienteCategoriaRepository.findAll();
		    
		    SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
		    SimpleDateFormat formatoDataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		    NumberFormat nf = NumberFormat.getCurrencyInstance();  
		    
		    String tipo = null;
		    
		    ClienteCategoria categoria = new ClienteCategoria();
		    
		    for(int x = 0; x < cTipoRefeicao.size(); x++)
		    {
		    	if(cTipoRefeicao.get(x).getCliente().getIdentificacao() == cliente.getIdentificacao())
		    	{
		    		tipo = cTipoRefeicao.get(x).getTipoRefeicao().getDescricao();
		    	}
		    }	    
		    
		    for(int x = 0; x < cCategoria.size(); x++)
		    {
		    	if(cCategoria.get(x).getCliente().getIdentificacao() == cliente.getIdentificacao())
		    	{
		    		categoria = cCategoria.get(x);
		    	}
		    }
		    
		    List<Reserva> reservasDoCliente = reservaRepository.findAll();
		    
		    String mensagemReserva = "Nenhuma reserva solicitada";
		    String variavel = mensagemReserva;
		      
		    for(int x = 0; x < reservasDoCliente.size(); x++) {
		    	
		    	if(reservasDoCliente.get(x).getCliente().getIdentificacao() == cliente.getIdentificacao())
		    	{	
	    			variavel = formatoDataHora.format(reservasDoCliente.get(reservasDoCliente.size() - 1).getDataHora());
		    	}
		    }
		    		    
		    String dataFormatada = formatoData.format(cliente.getDataNascimento());
		    String creditosFormatado = nf.format (cliente.getCreditos());
		    
		    model.addAttribute("cliente", cliente);		   
		    model.addAttribute("tipo", tipo);
		    model.addAttribute("categoria", categoria);
		    model.addAttribute("ultimaReserva", variavel);
		    model.addAttribute("dtnasc", dataFormatada);
		    model.addAttribute("creditos", creditosFormatado);
		}
		
		return "conta";
	}	
}