package com.github.projetoleaf.controllers;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.projetoleaf.beans.Cardapio;
import com.github.projetoleaf.beans.Cliente;
import com.github.projetoleaf.beans.Extrato;
import com.github.projetoleaf.beans.Reserva;
import com.github.projetoleaf.beans.ReservaItem;
import com.github.projetoleaf.beans.TipoRefeicao;
import com.github.projetoleaf.repositories.CardapioRepository;
import com.github.projetoleaf.repositories.ClienteCategoriaRepository;
import com.github.projetoleaf.repositories.ClienteRepository;
import com.github.projetoleaf.repositories.ExtratoRepository;
import com.github.projetoleaf.repositories.FeriadoRepository;
import com.github.projetoleaf.repositories.QuantidadeRefeicaoRepository;
import com.github.projetoleaf.repositories.ReservaItemRepository;
import com.github.projetoleaf.repositories.ReservaRepository;
import com.github.projetoleaf.repositories.StatusRepository;
import com.github.projetoleaf.repositories.TipoRefeicaoRepository;
import com.github.projetoleaf.repositories.TipoValorRepository;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@PreAuthorize("hasRole('ROLE_FC.UNESP.RU_ADMIN') or hasRole('ROLE_FC.UNESP.RU_CLIENTE')")
@Controller
public class ComprarController {

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
	private QuantidadeRefeicaoRepository qtdeRefeicoesRepository;

	@Autowired
	private ClienteCategoriaRepository clienteCategoriaRepository;
	
	private Cliente cliente;
	private Calendar dataAtual;
	private List<String> todasAsDatas;
	private List<Integer> contadores = new ArrayList<Integer>();
	private SimpleDateFormat formatar = new SimpleDateFormat("dd/MM/yyyy");
	
	@PostMapping("/verificarcomprar")
	public @ResponseBody String verificarComprar() throws JSONException {
		todasAsDatas = new ArrayList<String>();
		return verificar();
    }
	
	public String verificar() throws JSONException {
		JSONObject json = new JSONObject();
		
		if(!verificarSaldo())
			json.put("erro", "saldo");
		else if(!verificarDataAtual())
			json.put("erro", "data");
		else if(!verificarContagens())
			json.put("erro", "contagem");
		else
			json.put("sucesso", new Boolean(true));
		
		return json.toString();
	}
	
	public Boolean verificarSaldo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		cliente = clienteRepository.findByIdentificacao(authentication.getName());
		Extrato ultimoExtrato = extratoRepository.findFirstByClienteOrderByIdDesc(cliente);
		
		if(ultimoExtrato == null || ultimoExtrato.getSaldo().compareTo(new BigDecimal(4.00)) <= 0)
			return false;
		else
			return true;
	}
	
	public Boolean verificarDataAtual() {
		dataAtual = Calendar.getInstance();
		
		if(feriadoRepository.findByData(dataAtual.getTime()) == null)
			return true;
		
		return false;
	}
	
	public Boolean verificarContagens() {
		int count = -1;
		int countCusto;
		int countExpirado;
		int countDisponivel;
		int qtdeCusto = qtdeRefeicoesRepository.findAll().get(qtdeRefeicoesRepository.findAll().size() -1).getCusto();
		int qtdeSubs = qtdeRefeicoesRepository.findAll().get(qtdeRefeicoesRepository.findAll().size() -1).getSubsidiada();
		
		Calendar diasDaSemanaAtual = Calendar.getInstance();		
		diasDaSemanaAtual.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);//Define o dia segunda-feira da semana atual
		
		for (int i = 0; i < 5; i++) {
			Cardapio dataDoBanco = cardapioRepository.findByData(diasDaSemanaAtual.getTime());
			if (dataDoBanco != null) {
				if (dataAtual.getTime().compareTo(dataDoBanco.getData()) < 0) {
					countDisponivel = reservaItemRepository.qtdeDeReservasPorData(dataDoBanco.getData());
					countExpirado= reservaItemRepository.qtdeDeReservasExpiradasPorData(dataDoBanco.getData());
					countCusto = reservaItemRepository.qtdeDeReservasNãoSubsidiadasPorData(dataDoBanco.getData());
					if (countDisponivel < qtdeSubs || countExpirado > 0) {
						if (reservaItemRepository.verificarSeReservaExiste(cliente.getId(), dataDoBanco.getData()) == null) {
							todasAsDatas.add(formatar.format(dataDoBanco.getData()));
							count = 0;
						}						
					}
					
					if (countCusto < qtdeCusto) {
						if (count == -1) {
							if (reservaItemRepository.verificarSeReservaExiste(cliente.getId(), dataDoBanco.getData()) == null) {
								int caiu = 0;
								
								for(String daLista : todasAsDatas) {
									
									if(daLista.equals(formatar.format(dataDoBanco.getData()))) {
										caiu = 1;
									}
								}
									
								if(caiu == 0) {
									todasAsDatas.add(formatar.format(dataDoBanco.getData()));
								}										
								
								count = 1;										
							}							
						}
						else {
							if (reservaItemRepository.verificarSeReservaExiste(cliente.getId(), dataDoBanco.getData()) == null) {
								int caiu = 0;
								
								for(String daLista : todasAsDatas) {
									
									if(daLista.equals(formatar.format(dataDoBanco.getData()))) {
										caiu = 1;
									}
								}
									
								if(caiu == 0) {
									todasAsDatas.add(formatar.format(dataDoBanco.getData()));
								}										
								
								count = 2;										
							}			
						}						
					}
					contadores.add(count);
				}
			}
			diasDaSemanaAtual.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		if(todasAsDatas.isEmpty())
			return false;
		else
			return true;
	}
	
	@RequestMapping("/comprar")
	public String carregarComprar(Model model) throws JSONException {
		todasAsDatas = new ArrayList<String>();
		JSONObject json = new JSONObject(verificar());
		Boolean sucesso = json.getBoolean("sucesso");
		
		if(sucesso) {
			model.addAttribute("contadores", contadores);
			model.addAttribute("todasAsDatas", todasAsDatas);
			model.addAttribute("todosOsValores", tipoValorRepository.findAll());
			model.addAttribute("todasAsRefeicoes", tipoRefeicaoRepository.findAll());
			return "comprar";
		}
		
		return "boasvindas";
    }
	
	@PostMapping("/verificarcompra")
	public @ResponseBody String verificarCompra(@RequestParam(value = "datas[]", required = false) String[] datas, 
			@RequestParam(value = "tiposRefeicoes[]", required = false) String[] tiposRefeicoes,
			@RequestParam(value = "tiposValores[]", required = false) String[] tiposValores) throws JSONException {
		JSONObject json = new JSONObject();
		
		int count = 0;
		int countValor = 0;
		int countRefeicao = 0;
		
		BigDecimal valorTotal = new BigDecimal(0.00);
		BigDecimal saldo = extratoRepository.findFirstByClienteOrderByIdDesc(cliente).getSaldo();
		
		if(datas != null)
			count = datas.length;
		
		if(tiposRefeicoes != null) {
			for (String tR : tiposRefeicoes) {
				if(tR.length() != 0) {
					countRefeicao++;
				}					
			}
		}
		if(tiposValores != null) {
			for (String tV : tiposValores) {
				if(tV.length() != 0) {
					countValor++;
				}
				if(tV.equals("Custo"))
					valorTotal.add(clienteCategoriaRepository.findByCliente(cliente).getCategoria().getValorSemSubsidio());
				else
					valorTotal.add(clienteCategoriaRepository.findByCliente(cliente).getCategoria().getValorComSubsidio());
			}
		}	

		if(datas == null)
			json.put("erro", "data");
		else if(tiposRefeicoes == null || countRefeicao != count)
			json.put("erro", "tipoRefeicao");
		else if(tiposValores == null || countValor != count)
			json.put("erro", "tipoValor");
		else if(valorTotal.compareTo(saldo) > 0)
			json.put("erro", "saldo");
		else
			json.put("sucesso", new Boolean(true));
		
		return json.toString();
    }
	
	@SuppressWarnings("unlikely-arg-type")
	@PostMapping("/efetuarcompra")
	public @ResponseBody String efetuarCompra(@RequestParam(value = "datas[]") String[] datas, 
			@RequestParam(value = "tiposRefeicoes[]") String[] tRs,
			@RequestParam(value = "tiposValores[]") String[] tVs) throws ParseException, JSONException {		
		JSONObject json = new JSONObject();
		
		boolean verificaCusto = false;
		boolean verificaSubsidiada = false;
		
		todasAsDatas = new ArrayList<String>();		
		List<Cardapio> datasSelecionadasCusto = new ArrayList<Cardapio>();
		List<TipoRefeicao> tiposRefeicoes = new ArrayList<TipoRefeicao>();		
		List<Cardapio> datasSelecionadasSubsidiada = new ArrayList<Cardapio>();
		List<TipoRefeicao> tiposRefeicoesCusto = new ArrayList<TipoRefeicao>();
		List<TipoRefeicao> tiposRefeicoesSubsidiada = new ArrayList<TipoRefeicao>();

		Timestamp timestamp = new Timestamp(System.currentTimeMillis()); // Data e hora atual




		for (String s : tRs) {
			if (s.trim().length() != 0) {
				TipoRefeicao tR = new TipoRefeicao();
				if (s.equals("Tradicional"))
					tR = tipoRefeicaoRepository.findByDescricao("Tradicional");
				else if (tR.equals("Vegetariano"))
					tR = tipoRefeicaoRepository.findByDescricao("Vegetariano");
				else
					tR = tipoRefeicaoRepository.findByDescricao("Vegano");
				tiposRefeicoes.add(tR);
			}
		}
		
		for (int x = 0; x < datas.length; x++) {
			if (tVs[x].trim().length() != 0) {
				if (tVs[x].equals("Custo")) {
					tiposRefeicoesCusto.add(tiposRefeicoes.get(x));
					datasSelecionadasCusto.add(cardapioRepository.findByData(formatar.parse(datas[x])));

				}					
				else {
					tiposRefeicoesSubsidiada.add(tiposRefeicoes.get(x));
					datasSelecionadasSubsidiada.add(cardapioRepository.findByData(formatar.parse(datas[x])));

				}				
			}
		}
		
		Boolean teste = true;
		if(verificarContagens()) {
			for (String data : datas) {
				if(!todasAsDatas.contains(data))
					teste = false;
			}
		} else
			teste = false;
		
	
	    if (!teste)
			json.put("erro", "erro");
		else {
			BigDecimal saldo = extratoRepository.findFirstByClienteOrderByIdDesc(cliente).getSaldo();
			
			if (!verificaSubsidiada) {		
				Reserva reserva = new Reserva();
				reserva.setCliente(cliente);
				reserva.setDataReserva(timestamp);
				reserva.setTipoValor(tipoValorRepository.findByDescricao("Subsidiada")); 
				reservaRepository.save(reserva);

				Extrato extrato = new Extrato();
				extrato.setCliente(cliente);
				extrato.setDataTransacao(timestamp);

				BigDecimal transacao = BigDecimal.valueOf(datasSelecionadasSubsidiada.size())
						.multiply(clienteCategoriaRepository.findByCliente(cliente).getCategoria().getValorComSubsidio());

				extrato.setTransacao(transacao.negate());
				extrato.setSaldo(saldo.subtract(transacao));
				extratoRepository.save(extrato);

				Reserva ultimaReserva = reservaRepository.findFirstByClienteOrderByIdDesc(cliente);

				for (int x = 0; x < datasSelecionadasSubsidiada.size(); x++) {
					ReservaItem reservaItem = new ReservaItem();

					reservaItem.setReserva(ultimaReserva);
					reservaItem.setCardapio(datasSelecionadasSubsidiada.get(x));
					reservaItem.setStatus(statusRepository.findByDescricao("Pago"));
					reservaItem.setTipoRefeicao(tiposRefeicoesSubsidiada.get(x));
					reservaItem.setExtrato(extrato);

					reservaItemRepository.save(reservaItem);
				}
			}

			if (!verificaCusto) {
				Reserva reserva = new Reserva();
				reserva.setCliente(cliente);
				reserva.setDataReserva(timestamp);
				reserva.setTipoValor(tipoValorRepository.findByDescricao("Custo"));
				reservaRepository.save(reserva);

				Extrato extrato = new Extrato();
				extrato.setCliente(cliente);
				extrato.setDataTransacao(timestamp);

				BigDecimal transacao = BigDecimal.valueOf(datasSelecionadasCusto.size())
						.multiply(clienteCategoriaRepository.findByCliente(cliente).getCategoria().getValorSemSubsidio());

				extrato.setTransacao(transacao.negate());
				extrato.setSaldo(saldo.subtract(transacao));
				extratoRepository.save(extrato);

				Reserva ultimaReserva = reservaRepository.findFirstByClienteOrderByIdDesc(cliente);

				for (int x = 0; x < datasSelecionadasCusto.size(); x++) {
					ReservaItem reservaItem = new ReservaItem();

					reservaItem.setReserva(ultimaReserva);
					reservaItem.setCardapio(datasSelecionadasCusto.get(x));
					reservaItem.setStatus(statusRepository.findByDescricao("Pago"));
					reservaItem.setTipoRefeicao(tiposRefeicoesCusto.get(x));
					reservaItem.setExtrato(extrato);

					reservaItemRepository.save(reservaItem);
				}
			}
			
			json.put("sucesso", new Boolean(true));
		}		
		return json.toString();
    }
}