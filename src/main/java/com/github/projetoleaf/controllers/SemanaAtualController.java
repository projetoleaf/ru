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
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.projetoleaf.beans.Cardapio;
import com.github.projetoleaf.beans.Cliente;
import com.github.projetoleaf.beans.ClienteCategoria;
import com.github.projetoleaf.beans.Extrato;
import com.github.projetoleaf.beans.Reserva;
import com.github.projetoleaf.beans.ReservaItem;
import com.github.projetoleaf.beans.ReservasAdmin;
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

@Controller
@RequestMapping("/semanaAtual")
public class SemanaAtualController {

	@Autowired
	private StatusRepository statusRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ExtratoRepository extratoRepository;

	@Autowired
	private FeriadoRepository feriadoRepository;

	@Autowired
	private ReservaRepository reservaRepository;

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

	@GetMapping
	public String pesquisarSemanaAtual(Model model) throws ParseException {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (clienteRepository.findByIdentificacao(authentication.getName()) == null)
			return "redirect:/boasvindas";

		int countDisponivelSegunda = 0, countDisponivelTerca = 0, countDisponivelQuarta = 0, countDisponivelQuinta = 0, countDisponivelSexta = 0;
		int countExpiradoSegunda = 0, countExpiradoTerca = 0, countExpiradoQuarta = 0, countExpiradoQuinta = 0, countExpiradoSexta = 0;
		int countNãoSubsidiadaSegunda = 0, countNãoSubsidiadaTerca = 0, countNãoSubsidiadaQuarta = 0, countNãoSubsidiadaQuinta = 0, countNãoSubsidiadaSexta = 0;
		

		int qtdeSubs = qtdeRefeicoesRepository.findAll().get(-1).getSubsidiada();
		int qtdeCusto = qtdeRefeicoesRepository.findAll().get(-1).getCusto();

		Date segunda = new Date();
		Date terca = new Date();
		Date quarta = new Date();
		Date quinta = new Date();
		Date sexta = new Date();

		Calendar dataHoje = Calendar.getInstance();
		Calendar dataDaSemanaAtual = Calendar.getInstance();

		NumberFormat nf = NumberFormat.getCurrencyInstance();
		SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd/MM/yyyy");
		
		List<ReservasAdmin> todasAsReservas = new ArrayList<ReservasAdmin>();

		List<Cliente> todosOsClientesDoBD = clienteRepository.findAll();
		List<ReservaItem> reservasItensDoBD = reservaItemRepository.findAll();		

		if (!(dataHoje.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
				&& !(dataHoje.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
				&& feriadoRepository.findByData(dataHoje.getTime()) == null) {

			for (Cliente cliente : todosOsClientesDoBD) {

				dataDaSemanaAtual.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);// Define o dia segunda-feira desta semana
				
				Extrato ultimoExtrato = extratoRepository.findFirstByClienteOrderByIdDesc(cliente);
				BigDecimal saldo = ultimoExtrato != null ? ultimoExtrato.getSaldo() : new BigDecimal(0.00);

				ReservasAdmin reservasAdmin = new ReservasAdmin();
				reservasAdmin.setId(cliente.getId());
				reservasAdmin.setNome(cliente.getNome());
				reservasAdmin.setCreditos(nf.format(saldo));

				for (int i = 0; i < 5; i++) {

					Cardapio dataDoBanco = cardapioRepository.findByData(dataDaSemanaAtual.getTime());

					if (dataDoBanco != null) {

						Calendar cal = Calendar.getInstance();
						cal.setTime(dataDoBanco.getData());

						if (dataHoje.get(Calendar.DAY_OF_WEEK) <= cal.get(Calendar.DAY_OF_WEEK)) {

							if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
								countDisponivelSegunda = reservaItemRepository
										.qtdeDeReservasPorData(cal.getTime());
								countExpiradoSegunda = reservaItemRepository
										.qtdeDeReservasExpiradasPorData(cal.getTime());
								countNãoSubsidiadaSegunda = reservaItemRepository
										.qtdeDeReservasNãoSubsidiadasPorData(cal.getTime());
							}

							if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
								countDisponivelTerca = reservaItemRepository
										.qtdeDeReservasPorData(cal.getTime());
								countExpiradoTerca = reservaItemRepository
										.qtdeDeReservasExpiradasPorData(cal.getTime());
								countNãoSubsidiadaTerca = reservaItemRepository
										.qtdeDeReservasNãoSubsidiadasPorData(cal.getTime());
							}

							if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
								countDisponivelQuarta = reservaItemRepository
										.qtdeDeReservasPorData(cal.getTime());
								countExpiradoQuarta = reservaItemRepository
										.qtdeDeReservasExpiradasPorData(cal.getTime());
								countNãoSubsidiadaQuarta = reservaItemRepository
										.qtdeDeReservasNãoSubsidiadasPorData(cal.getTime());
							}

							if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
								countDisponivelQuinta = reservaItemRepository
										.qtdeDeReservasPorData(cal.getTime());
								countExpiradoQuinta = reservaItemRepository
										.qtdeDeReservasExpiradasPorData(cal.getTime());
								countNãoSubsidiadaQuinta = reservaItemRepository
										.qtdeDeReservasNãoSubsidiadasPorData(cal.getTime());
							}

							if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
								countDisponivelSexta = reservaItemRepository
										.qtdeDeReservasPorData(cal.getTime());
								countExpiradoSexta = reservaItemRepository
										.qtdeDeReservasExpiradasPorData(cal.getTime());
								countNãoSubsidiadaSexta = reservaItemRepository
										.qtdeDeReservasNãoSubsidiadasPorData(cal.getTime());
							}

							// Verifica se o count disponível é menor que a qtde de refeições subsidiadas
							if (countDisponivelSegunda < qtdeSubs || countDisponivelTerca < qtdeSubs
									|| countDisponivelQuarta < qtdeSubs || countDisponivelQuinta < qtdeSubs
									|| countDisponivelSexta < qtdeSubs || countExpiradoSegunda > 0 || countExpiradoTerca > 0
									|| countExpiradoQuarta > 0 || countExpiradoQuinta > 0 || countExpiradoSexta > 0) {

								for (ReservaItem reserva : reservasItensDoBD) {//Mudar para query?

									if (cliente.getNome() == reserva.getReserva().getCliente().getNome()) {

										if (reservaItemRepository.verificarSeReservaExiste(
												cliente.getId(), cal.getTime()) != null) {

											if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
												reservasAdmin.setSegundaStatus(
														reserva.getStatus().getDescricao());
											}

											if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
												reservasAdmin.setTercaStatus(
														reserva.getStatus().getDescricao());
											}

											if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
												reservasAdmin.setQuartaStatus(
														reserva.getStatus().getDescricao());
											}

											if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
												reservasAdmin.setQuintaStatus(
														reserva.getStatus().getDescricao());
											}

											if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
												reservasAdmin.setSextaStatus(
														reserva.getStatus().getDescricao());
											}
										}
									}
								}
							}

							if (countNãoSubsidiadaSegunda < qtdeCusto || countNãoSubsidiadaTerca < qtdeCusto
									|| countNãoSubsidiadaQuarta < qtdeCusto || countNãoSubsidiadaQuinta < qtdeCusto
									|| countNãoSubsidiadaSexta < qtdeCusto) {

								for (ReservaItem reserva :reservasItensDoBD) {

									if (cliente.getNome() == reserva.getReserva().getCliente().getNome()) {

										if (reservaItemRepository.verificarSeReservaExiste(
												cliente.getId(), cal.getTime()) != null) {

											if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
												reservasAdmin.setSegundaStatus(
														reserva.getStatus().getDescricao());
											}

											if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
												reservasAdmin.setTercaStatus(
														reserva.getStatus().getDescricao());
											}

											if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
												reservasAdmin.setQuartaStatus(
														reserva.getStatus().getDescricao());
											}

											if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
												reservasAdmin.setQuintaStatus(
														reserva.getStatus().getDescricao());
											}

											if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
												reservasAdmin.setSextaStatus(
														reserva.getStatus().getDescricao());
											}
										}
									}
								}
							}
						}	
						//Verifica se o status está nulo, caso sim, define status
						if (reservasAdmin.getSegundaStatus() == null) {
							reservasAdmin.setSegundaStatus("Não reservado");
						}

						if (reservasAdmin.getTercaStatus() == null) {
							reservasAdmin.setTercaStatus("Não reservado");
						}

						if (reservasAdmin.getQuartaStatus() == null) {
							reservasAdmin.setQuartaStatus("Não reservado");
						}

						if (reservasAdmin.getQuintaStatus() == null) {
							reservasAdmin.setQuintaStatus("Não reservado");
						}

						if (reservasAdmin.getSextaStatus() == null) {
							reservasAdmin.setSextaStatus("Não reservado");
						}
					} else {
						//Se a data não existir, define o status como indisponível
						Calendar call = Calendar.getInstance();
						call.setTime(dataDaSemanaAtual.getTime());

						if (call.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
							reservasAdmin.setSegundaStatus("Dia indisponível");
						}

						if (call.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
							reservasAdmin.setTercaStatus("Dia indisponível");
						}

						if (call.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
							reservasAdmin.setQuartaStatus("Dia indisponível");
						}

						if (call.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
							reservasAdmin.setQuintaStatus("Dia indisponível");
						}

						if (call.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
							reservasAdmin.setSextaStatus("Dia indisponível");
						}
					}

					switch (i) {
					case 0:
						segunda = dataDaSemanaAtual.getTime();
					case 1:
						terca = dataDaSemanaAtual.getTime();
					case 2:
						quarta = dataDaSemanaAtual.getTime();
					case 3:
						quinta = dataDaSemanaAtual.getTime();
					case 4:
						sexta = dataDaSemanaAtual.getTime();
					}
					
					if (!(reservasAdmin.getSegundaStatus() == "Não reservado"
							&& reservasAdmin.getSegundaStatus() == "Dia indisponível"
							&& reservasAdmin.getTercaStatus() == "Não reservado"
							&& reservasAdmin.getTercaStatus() == "Dia indisponível"
							&& reservasAdmin.getQuartaStatus() == "Não reservado"
							&& reservasAdmin.getQuartaStatus() == "Dia indisponível"
							&& reservasAdmin.getQuintaStatus() == "Não reservado"
							&& reservasAdmin.getQuintaStatus() == "Dia indisponível"
							&& reservasAdmin.getSextaStatus() == "Não reservado"
							&& reservasAdmin.getSextaStatus() == "Dia indisponível")) {
						todasAsReservas.add(reservasAdmin);
					}

					dataDaSemanaAtual.add(Calendar.DAY_OF_MONTH, 1);
				}				
			}
		} 

		model.addAttribute("listagemSemanaAtual", todasAsReservas);
		model.addAttribute("segunda", formatoDesejado.format(segunda));
		model.addAttribute("terca", formatoDesejado.format(terca));
		model.addAttribute("quarta", formatoDesejado.format(quarta));
		model.addAttribute("quinta", formatoDesejado.format(quinta));
		model.addAttribute("sexta", formatoDesejado.format(sexta));

		return "/semanaAtual/pesquisar";
	}

	@GetMapping("/pagamento/{nome}")
	public String efetivarPagamentoSemanaAtual(@PathVariable String nome, Model model)
			throws ParseException, JsonGenerationException, JsonMappingException, IOException {

		int count = 0;

		int countDisponivelSegunda = 0, countDisponivelTerca = 0, countDisponivelQuarta = 0, countDisponivelQuinta = 0, countDisponivelSexta = 0;
		int countExpiradoSegunda = 0,  countExpiradoTerca = 0, countExpiradoQuarta = 0, countExpiradoQuinta = 0, countExpiradoSexta = 0;
		int countNãoSubsidiadaSegunda = 0, countNãoSubsidiadaTerca = 0, countNãoSubsidiadaQuarta = 0, countNãoSubsidiadaQuinta = 0, countNãoSubsidiadaSexta = 0;
		
		int qtdeSubs = qtdeRefeicoesRepository.findAll().get(-1).getSubsidiada();
		int qtdeCusto = qtdeRefeicoesRepository.findAll().get(-1).getCusto();
		
		BigDecimal saldo = new BigDecimal(0.00);

		Calendar dataHoje = Calendar.getInstance();
		Calendar dataDaSemanaAtual = Calendar.getInstance();

		NumberFormat nf = NumberFormat.getCurrencyInstance();
		SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd/MM/yyyy");

		Cliente cliente = clienteRepository.findByNome(nome);
		ClienteCategoria clienteCategoria = clienteCategoriaRepository.findByCliente(cliente);
		
		List<Integer> contadores = new ArrayList<Integer>();
		List<String> todasAsDatas = new ArrayList<String>();
		
		if (!(dataHoje.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
				&& !(dataHoje.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
				&& feriadoRepository.findByData(dataHoje.getTime()) == null) {
			
				dataDaSemanaAtual.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);// Define o dia segunda-feira desta semana

				Extrato ultimoExtrato = extratoRepository.findFirstByClienteOrderByIdDesc(cliente);
				saldo = ultimoExtrato != null ? ultimoExtrato.getSaldo() : new BigDecimal(0.00);

				for (int i = 0; i < 5; i++) {

					Cardapio dataDoBanco = cardapioRepository.findByData(dataDaSemanaAtual.getTime());

					if (dataDoBanco != null) {
		
						Calendar cal = Calendar.getInstance();
						cal.setTime(dataDoBanco.getData());

						if (dataHoje.get(Calendar.DAY_OF_WEEK) <= cal.get(Calendar.DAY_OF_WEEK)) {

							if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
								countDisponivelSegunda = reservaItemRepository
										.qtdeDeReservasPorData(cal.getTime());
								countExpiradoSegunda = reservaItemRepository
										.qtdeDeReservasExpiradasPorData(cal.getTime());
								countNãoSubsidiadaSegunda = reservaItemRepository
										.qtdeDeReservasNãoSubsidiadasPorData(cal.getTime());
							}

							if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
								countDisponivelTerca = reservaItemRepository
										.qtdeDeReservasPorData(cal.getTime());
								countExpiradoTerca = reservaItemRepository
										.qtdeDeReservasExpiradasPorData(cal.getTime());
								countNãoSubsidiadaTerca = reservaItemRepository
										.qtdeDeReservasNãoSubsidiadasPorData(cal.getTime());
							}

							if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
								countDisponivelQuarta = reservaItemRepository
										.qtdeDeReservasPorData(cal.getTime());
								countExpiradoQuarta = reservaItemRepository
										.qtdeDeReservasExpiradasPorData(cal.getTime());
								countNãoSubsidiadaQuarta = reservaItemRepository
										.qtdeDeReservasNãoSubsidiadasPorData(cal.getTime());
							}

							if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
								countDisponivelQuinta = reservaItemRepository
										.qtdeDeReservasPorData(cal.getTime());
								countExpiradoQuinta = reservaItemRepository
										.qtdeDeReservasExpiradasPorData(cal.getTime());
								countNãoSubsidiadaQuinta = reservaItemRepository
										.qtdeDeReservasNãoSubsidiadasPorData(cal.getTime());
							}

							if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
								countDisponivelSexta = reservaItemRepository
										.qtdeDeReservasPorData(cal.getTime());
								countExpiradoSexta = reservaItemRepository
										.qtdeDeReservasExpiradasPorData(cal.getTime());
								countNãoSubsidiadaSexta = reservaItemRepository
										.qtdeDeReservasNãoSubsidiadasPorData(cal.getTime());
							}

							// Verifica se o count disponível é menor que a qtde de refeições subsidiadas
							if (countDisponivelSegunda < qtdeSubs || countDisponivelTerca < qtdeSubs
									|| countDisponivelQuarta < qtdeSubs || countDisponivelQuinta < qtdeSubs
									|| countDisponivelSexta < qtdeSubs || countExpiradoSegunda > 0 || countExpiradoTerca > 0
									|| countExpiradoQuarta > 0 || countExpiradoQuinta > 0 || countExpiradoSexta > 0) {

								if (reservaItemRepository.verificarSeReservaExiste(cliente.getId(),
										cal.getTime()) == null) {
									
									todasAsDatas.add(formatoDesejado.format(cal.getTime()));									
									count = 1;
								}					
							}

							if (countNãoSubsidiadaSegunda < qtdeCusto || countNãoSubsidiadaTerca < qtdeCusto
									|| countNãoSubsidiadaQuarta < qtdeCusto || countNãoSubsidiadaQuinta < qtdeCusto
									|| countNãoSubsidiadaSexta < qtdeCusto) {

								if (count == 0) {

									if (reservaItemRepository.verificarSeReservaExiste(cliente.getId(),
											cal.getTime()) == null) {
										
										int caiu = 0;
										
										for(String daLista : todasAsDatas) {
											
											if(daLista.equals(formatoDesejado.format(cal.getTime()))) {
												caiu = 1;
											}
										}
											
										if(caiu == 0) {
											todasAsDatas.add(formatoDesejado.format(cal.getTime()));
										}										
										
										count = 2;						
									}		

								} else {

									if (reservaItemRepository.verificarSeReservaExiste(cliente.getId(),
											cal.getTime()) == null) {
										
										int caiu = 0;
										
										for(String daLista : todasAsDatas) {
											
											if(daLista.equals(formatoDesejado.format(cal.getTime()))) {
												caiu = 1;
											}
										}
											
										if(caiu == 0) {
											todasAsDatas.add(formatoDesejado.format(cal.getTime()));
										}	
										
										count = 3;
									}	
								}
							}

							contadores.add(count);
						}					
					}	

					dataDaSemanaAtual.add(Calendar.DAY_OF_MONTH, 1);
				}
			}
		
		model.addAttribute("nome", nome);
		model.addAttribute("contadores", contadores);
		model.addAttribute("saldo", nf.format(saldo));
		model.addAttribute("todasAsDatas", todasAsDatas);
		model.addAttribute("semanaAtual", new ReservasAdmin());
		model.addAttribute("valorRefeicaoS", clienteCategoria.getCategoria().getValorComSubsidio());
		model.addAttribute("valorRefeicaoC", clienteCategoria.getCategoria().getValorSemSubsidio());

		
		ObjectMapper mapper = new ObjectMapper();// jackson lib for converting to json
		String objectJSON = mapper.writeValueAsString(todasAsDatas);// json string
		model.addAttribute("objectJSON", objectJSON);

		return "/semanaAtual/pagamento";
	}

	@PostMapping("/salvar")
	public String salvarSemanaAtual(@RequestParam("nome") String nome, @RequestParam("data") String[] Datas,
			@RequestParam("tipoRefeicao") Integer[] idsTipoRefeicao,
			@RequestParam("tipoValor") Integer[] idsTipoValor,
			@RequestParam(value = "valor", required = false) String valor,
			@RequestParam(value = "recargas", required = false) String recargas,
			@RequestParam(value = "utilizarCreditos", required = false) Boolean creditos) throws ParseException {

		Cliente cliente = clienteRepository.findByNome(nome);
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis()); // Data e hora atual
		SimpleDateFormat formatoDesejado = new SimpleDateFormat("yyyy-MM-dd");
		
		List<Integer> tiposValoresCusto = new ArrayList<Integer>();
		List<Integer> tiposRefeicoesCusto = new ArrayList<Integer>();
		List<String> datasSelecionadasCusto = new ArrayList<String>();
		List<Integer> tiposValoresSubsidiada = new ArrayList<Integer>();
		List<Integer> tiposRefeicoesSubsidiada = new ArrayList<Integer>();
		List<Integer> tiposRefeicoesSelecionados = new ArrayList<Integer>();		
		List<String> datasSelecionadasSubsidiada = new ArrayList<String>();	
		
		for (int z = 0; z < idsTipoRefeicao.length; z++) {
			if (idsTipoRefeicao[z] != null) {
				tiposRefeicoesSelecionados.add(idsTipoRefeicao[z]);
			}
		}

		for (int z = 0; z < idsTipoValor.length; z++) {
			if (idsTipoValor[z] != null) {
				if (idsTipoValor[z] % 2 == 0) {
					tiposValoresSubsidiada.add(idsTipoValor[z]);
					datasSelecionadasSubsidiada.add(Datas[z]);
					tiposRefeicoesSubsidiada.add(tiposRefeicoesSelecionados.get(z));
				} else {
					tiposValoresCusto.add(idsTipoValor[z]);
					datasSelecionadasCusto.add(Datas[z]);
					tiposRefeicoesCusto.add(tiposRefeicoesSelecionados.get(z));
				}
			}
		}

		if (creditos == null) {

			if (recargas == null) {
				
				if (!tiposValoresSubsidiada.isEmpty()) {
					
					Reserva reserva = new Reserva();
					reserva.setCliente(cliente);
					reserva.setDataReserva(timestamp);
					reserva.setTipoValor(tipoValorRepository.findByDescricao("Subsidiada")); 

					reservaRepository.save(reserva);

					Long ultimaReserva = reservaRepository.findFirstByClienteOrderByIdDesc(cliente).getId();

					for (int x = 0; x <= datasSelecionadasSubsidiada.size() - 1; x++) {						
						Reserva r = new Reserva();
						Cardapio c = new Cardapio();
						ReservaItem reservaItem = new ReservaItem();	

						r.setId(ultimaReserva);
						c.setId(cardapioRepository.findByData(formatoDesejado.parse(datasSelecionadasSubsidiada.get(x))).getId());

						reservaItem.setReserva(r);
						reservaItem.setCardapio(c);
						reservaItem.setStatus(statusRepository.findByDescricao("Pago"));
						reservaItem.setTipoRefeicao(tiposRefeicoesSelecionados.get(x) % 2 == 0
								? tipoRefeicaoRepository.findByDescricao("Vegetariano")
										: tipoRefeicaoRepository.findByDescricao("Tradicional"));
						reservaItem.setExtrato(null);

						reservaItemRepository.save(reservaItem);
					}
				}
				
				if (!tiposValoresCusto.isEmpty()) {
					
					Reserva reserva = new Reserva();
					reserva.setCliente(cliente);
					reserva.setDataReserva(timestamp);
					reserva.setTipoValor(tipoValorRepository.findByDescricao("Custo"));

					reservaRepository.save(reserva);

					Long ultimaReserva = reservaRepository.findFirstByClienteOrderByIdDesc(cliente).getId();

					for (int x = 0; x <= datasSelecionadasCusto.size() - 1; x++) {		
						Reserva r = new Reserva();
						Cardapio c = new Cardapio();
						ReservaItem reservaItem = new ReservaItem();

						r.setId(ultimaReserva);
						c.setId(cardapioRepository.findByData(formatoDesejado.parse(datasSelecionadasCusto.get(x))).getId());

						reservaItem.setReserva(r);
						reservaItem.setCardapio(c);
						reservaItem.setStatus(statusRepository.findByDescricao("Pago"));
						reservaItem.setTipoRefeicao(tiposRefeicoesSelecionados.get(x) % 2 == 0
								? tipoRefeicaoRepository.findByDescricao("Vegetariano")
										: tipoRefeicaoRepository.findByDescricao("Tradicional"));
						reservaItem.setExtrato(null);

						reservaItemRepository.save(reservaItem);
					}
				}
				
			} else {
				
				Extrato ultimoExtrato = extratoRepository.findFirstByClienteOrderByIdDesc(cliente);
				BigDecimal saldo = ultimoExtrato != null ? ultimoExtrato.getSaldo() : new BigDecimal(0.00);

				Extrato extrato = new Extrato();
				extrato.setCliente(cliente);
				extrato.setTransacao(new BigDecimal(recargas.replaceAll(",", ".")));
				extrato.setDataTransacao(timestamp);
				extrato.setSaldo(saldo.add(new BigDecimal(recargas.replaceAll(",", "."))));

				extratoRepository.save(extrato);

				if (!tiposValoresSubsidiada.isEmpty()) {
					
					Reserva reserva = new Reserva();
					reserva.setCliente(cliente);
					reserva.setDataReserva(timestamp);
					reserva.setTipoValor(tipoValorRepository.findByDescricao("Subsidiada")); 

					reservaRepository.save(reserva);

					Long ultimaReserva = reservaRepository.findFirstByClienteOrderByIdDesc(cliente).getId();

					for (int x = 0; x <= datasSelecionadasSubsidiada.size() - 1; x++) {			
						Reserva r = new Reserva();
						Cardapio c = new Cardapio();
						ReservaItem reservaItem = new ReservaItem();	

						r.setId(ultimaReserva);
						c.setId(cardapioRepository.findByData(formatoDesejado.parse(datasSelecionadasSubsidiada.get(x))).getId());

						reservaItem.setReserva(r);
						reservaItem.setCardapio(c);
						reservaItem.setStatus(statusRepository.findByDescricao("Pago"));
						reservaItem.setTipoRefeicao(tiposRefeicoesSelecionados.get(x) % 2 == 0
								? tipoRefeicaoRepository.findByDescricao("Vegetariano")
										: tipoRefeicaoRepository.findByDescricao("Tradicional"));
						reservaItem.setExtrato(null);

						reservaItemRepository.save(reservaItem);
					}
				}
				
				if (!tiposValoresCusto.isEmpty()) {					
					Reserva reserva = new Reserva();
					reserva.setCliente(cliente);
					reserva.setDataReserva(timestamp);
					reserva.setTipoValor(tipoValorRepository.findByDescricao("Custo"));

					reservaRepository.save(reserva);

					Long ultimaReserva = reservaRepository.findFirstByClienteOrderByIdDesc(cliente).getId();

					for (int x = 0; x <= datasSelecionadasCusto.size() - 1; x++) {
						Reserva r = new Reserva();
						Cardapio c = new Cardapio();
						ReservaItem reservaItem = new ReservaItem();

						r.setId(ultimaReserva);
						c.setId(cardapioRepository.findByData(formatoDesejado.parse(datasSelecionadasCusto.get(x))).getId());

						reservaItem.setReserva(r);
						reservaItem.setCardapio(c);
						reservaItem.setStatus(statusRepository.findByDescricao("Pago"));
						reservaItem.setTipoRefeicao(tiposRefeicoesSelecionados.get(x) % 2 == 0
								? tipoRefeicaoRepository.findByDescricao("Vegetariano")
										: tipoRefeicaoRepository.findByDescricao("Tradicional"));
						reservaItem.setExtrato(null);

						reservaItemRepository.save(reservaItem);
					}
				}
			}
		} else {
			
			Extrato ultimoExtrato = extratoRepository.findFirstByClienteOrderByIdDesc(cliente);
			BigDecimal saldo = ultimoExtrato != null ? ultimoExtrato.getSaldo() : new BigDecimal(0.00);

			Extrato extrato = new Extrato();
			extrato.setCliente(cliente);
			extrato.setTransacao(new BigDecimal(recargas.replaceAll(",", ".")));
			extrato.setDataTransacao(timestamp);
			extrato.setSaldo(saldo.add(new BigDecimal(recargas.replaceAll(",", "."))));

			extratoRepository.save(extrato);
			
			BigDecimal saldoAtualizado = extratoRepository.findFirstByClienteOrderByIdDesc(cliente).getSaldo();
			
			if (!tiposValoresSubsidiada.isEmpty()) {
				
				Extrato eSubsidiada = new Extrato();
				eSubsidiada.setCliente(cliente);
				eSubsidiada.setDataTransacao(timestamp);

				BigDecimal transacao = BigDecimal.valueOf(Datas.length)
						.multiply(clienteCategoriaRepository.findByCliente(cliente).getCategoria().getValorComSubsidio());

				eSubsidiada.setSaldo(saldoAtualizado.subtract(transacao));
				eSubsidiada.setTransacao(transacao.negate());

				extratoRepository.save(eSubsidiada);
				
				Reserva reserva = new Reserva();
				reserva.setCliente(cliente);
				reserva.setDataReserva(timestamp);
				reserva.setTipoValor(tipoValorRepository.findByDescricao("Subsidiada")); 

				reservaRepository.save(reserva);

				Long ultimaReserva = reservaRepository.findFirstByClienteOrderByIdDesc(cliente).getId();

				for (int x = 0; x <= datasSelecionadasSubsidiada.size() - 1; x++) {					
					Reserva r = new Reserva();
					Cardapio c = new Cardapio();
					ReservaItem reservaItem = new ReservaItem();	

					r.setId(ultimaReserva);
					c.setId(cardapioRepository.findByData(formatoDesejado.parse(datasSelecionadasSubsidiada.get(x))).getId());

					reservaItem.setReserva(r);
					reservaItem.setCardapio(c);
					reservaItem.setStatus(statusRepository.findByDescricao("Pago"));
					reservaItem.setTipoRefeicao(tiposRefeicoesSelecionados.get(x) % 2 == 0
							? tipoRefeicaoRepository.findByDescricao("Vegetariano")
									: tipoRefeicaoRepository.findByDescricao("Tradicional"));
					reservaItem.setExtrato(eSubsidiada);

					reservaItemRepository.save(reservaItem);
				}
			}
			
			if (!tiposValoresCusto.isEmpty()) {
				
				Extrato eCusto = new Extrato();
				eCusto.setCliente(cliente);
				eCusto.setDataTransacao(timestamp);

				BigDecimal transacao = BigDecimal.valueOf(Datas.length)
						.multiply(clienteCategoriaRepository.findByCliente(cliente).getCategoria().getValorSemSubsidio());

				eCusto.setSaldo(saldoAtualizado.subtract(transacao));
				eCusto.setTransacao(transacao.negate());

				extratoRepository.save(eCusto);
				
				Reserva reserva = new Reserva();
				reserva.setCliente(cliente);
				reserva.setDataReserva(timestamp);
				reserva.setTipoValor(tipoValorRepository.findByDescricao("Custo"));

				reservaRepository.save(reserva);

				Long ultimaReserva = reservaRepository.findFirstByClienteOrderByIdDesc(cliente).getId();

				for (int x = 0; x <= datasSelecionadasCusto.size() - 1; x++) {	
					Reserva r = new Reserva();
					Cardapio c = new Cardapio();
					ReservaItem reservaItem = new ReservaItem();

					r.setId(ultimaReserva);
					c.setId(cardapioRepository.findByData(formatoDesejado.parse(datasSelecionadasCusto.get(x))).getId());

					reservaItem.setReserva(r);
					reservaItem.setCardapio(c);
					reservaItem.setStatus(statusRepository.findByDescricao("Pago"));
					reservaItem.setTipoRefeicao(tiposRefeicoesSelecionados.get(x) % 2 == 0
							? tipoRefeicaoRepository.findByDescricao("Vegetariano")
									: tipoRefeicaoRepository.findByDescricao("Tradicional"));
					reservaItem.setExtrato(eCusto);

					reservaItemRepository.save(reservaItem);
				}
			}			
		}

		return "redirect:/semanaAtual";
	}
}