package com.github.projetoleaf.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.projetoleaf.beans.Catraca;
import com.github.projetoleaf.beans.Cliente;
import com.github.projetoleaf.beans.Geral;
import com.github.projetoleaf.beans.ReservaItem;
import com.github.projetoleaf.repositories.CatracaRepository;
import com.github.projetoleaf.repositories.ClienteRepository;
import com.github.projetoleaf.repositories.ReservaItemRepository;
import com.github.projetoleaf.repositories.StatusRepository;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@PreAuthorize("hasRole('ROLE_FC.UNESP.RU_ADMIN') or hasRole('ROLE_FC.UNESP.RU_STN')")
@Controller
@RequestMapping("/catraca")
public class CatracaController {
	
	@Autowired
	private StatusRepository statusRepository;	
	
	@Autowired
	private CatracaRepository catracaRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ReservaItemRepository reservaItemRepository;

	@GetMapping
	public String pesquisarCatraca(Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (clienteRepository.findByIdentificacao(authentication.getName()) == null)
			return "redirect:/boasvindas";
		
		Calendar dataAtual = Calendar.getInstance();
		SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");	
		
		List<Geral> catraca = new ArrayList<Geral>();
		List<Cliente> clientes = clienteRepository.findAll();

		for (Cliente c : clientes) {
			
			if(reservaItemRepository.buscarReserva(c.getId(), dataAtual.getTime()) != null) {
				
				if (catracaRepository.verificarStatusCatraca(c.getNome(), dataAtual.getTime()) == null) {
					Geral base = new Geral();	
					base.setNome(c.getNome());
					base.setMensagem("Não passou");
					
					catraca.add(base);
				}
			}			
		}
		
		model.addAttribute("listagemCatraca", catraca);
		model.addAttribute("dataAtual", formatoData.format(dataAtual.getTime()));
		model.addAttribute("listagemHistorico", catracaRepository.findByData(dataAtual.getTime()));

		return "/catraca/pesquisar";
	}

	@GetMapping("/historico/{nome}")
	public String buscarHistoricoDoCliente(@PathVariable String nome, Model model) {
		
		Cliente cliente = clienteRepository.findByNome(nome);
		model.addAttribute("historico", catracaRepository.findByCliente(cliente));

		return "/catraca/historico";
	}

	@PostMapping("/salvar")
	public @ResponseBody void salvarGiroDaRoleta(@RequestParam("nome") String nome) {
		
		Cliente cliente = clienteRepository.findByNome(nome);
		ReservaItem reserva = reservaItemRepository.buscarReserva(cliente.getId(), Calendar.getInstance().getTime());
		reserva.setStatus(statusRepository.findByDescricao("Consumida"));
		reservaItemRepository.save(reserva);
		
		Catraca catraca = new Catraca();
		catraca.setCliente(cliente);
		catraca.setReservaItem(reserva);
		catraca.setData(Calendar.getInstance().getTime());
		catracaRepository.save(catraca);
	}
	
	@GetMapping("/concluirdia")
	public String concluirDia() {
		
		Calendar dataAtual = Calendar.getInstance();
		
		List<Cliente> clientes = clienteRepository.findAll();

		for (Cliente c : clientes) {
			
			if(reservaItemRepository.buscarReserva(c.getId(), dataAtual.getTime()) != null) {
				
				if (catracaRepository.verificarStatusCatraca(c.getNome(), dataAtual.getTime()) == null) {
					ReservaItem reserva = reservaItemRepository.buscarReserva(c.getId(), dataAtual.getTime());
					reserva.setStatus(statusRepository.findByDescricao("Expirado"));
					reservaItemRepository.save(reserva);
				}
			}			
		}
		
		return "redirect:/catraca";
	}
}