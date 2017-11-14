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

@EnableGlobalMethodSecurity(prePostEnabled = true)
@PreAuthorize("hasRole('ROLE_FC.UNESP.RU_ADMIN') or hasRole('ROLE_FC.UNESP.RU_STF')")
@Controller
@RequestMapping("/remanescentes")
public class RemanescentesAdminController {

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
	public String pesquisarRemanescentes(Model model) throws ParseException {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (clienteRepository.findByIdentificacao(authentication.getName()) == null)
			return "redirect:/boasvindas";

		int countSegunda = 0, countTerca = 0, countQuarta = 0, countQuinta = 0, countSexta = 0,
				countDisponivelSegunda = 0, countDisponivelTerca = 0, countDisponivelQuarta = 0,
				countDisponivelQuinta = 0, countDisponivelSexta = 0, countExpiradoSegunda = 0,
				countExpiradoTerca = 0, countExpiradoQuarta = 0, countExpiradoQuinta = 0,
				countExpiradoSexta = 0;
		
		int qtdeSubs = qtdeRefeicoesRepository.findAll().get(-1).getSubsidiada();

		Date segunda = new Date();
		Date terca = new Date();
		Date quarta = new Date();
		Date quinta = new Date();
		Date sexta = new Date();

		Calendar dataHoje = Calendar.getInstance();
		Calendar ultimoDiaUtil = Calendar.getInstance();
		Calendar diasDaProximaSemana = Calendar.getInstance();

		NumberFormat nf = NumberFormat.getCurrencyInstance();
		SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd/MM/yyyy");

		List<ReservasAdmin> todasAsReservas = new ArrayList<ReservasAdmin>();

		List<Cliente> todosOsClientesDoBD = clienteRepository.findAll();
		List<ReservaItem> reservasItensDoBD = reservaItemRepository.findAll();

		diasDaProximaSemana = verificarData(diasDaProximaSemana);
		
		ultimoDiaUtil.setTime(diasDaProximaSemana.getTime());
		ultimoDiaUtil.add(Calendar.DATE, 4); // Com base no valor do diasDaProximaSemana, define a sexta-feira da prÃ³xima
												// semana

		List<Date> todasAsDatasDaProximaSemana = cardapioRepository.todasAsDatasDaSemana(diasDaProximaSemana.getTime(),
				ultimoDiaUtil.getTime());
		ultimoDiaUtil.setTime(todasAsDatasDaProximaSemana.get(todasAsDatasDaProximaSemana.size() - 1)); // Define
																										// o
																										// Ãºltimo
																										// dia
																										// util,
																										// conforme
																										// o
																										// range
																										// de
																										// datas
																										// do BD

		if (!(dataHoje.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
				&& !(dataHoje.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
				&& feriadoRepository.findByData(dataHoje.getTime()) == null
				&& dataHoje.get(Calendar.DAY_OF_WEEK) == ultimoDiaUtil.get(Calendar.DAY_OF_WEEK)
				&& todasAsDatasDaProximaSemana.size() >= 2) {

			for (Cliente cliente : todosOsClientesDoBD) {
				
				Calendar dataAtualDaProxSemana = Calendar.getInstance();
				dataAtualDaProxSemana = verificarData(dataAtualDaProxSemana);

				Extrato ultimoExtrato = extratoRepository.findFirstByClienteOrderByIdDesc(cliente);
				BigDecimal saldo = ultimoExtrato != null ? ultimoExtrato.getSaldo() : new BigDecimal(0.00);

				ReservasAdmin reservasAdmin = new ReservasAdmin();
				reservasAdmin.setId(cliente.getId());
				reservasAdmin.setNome(cliente.getNome());
				reservasAdmin.setCreditos(nf.format(saldo));

				for (int i = 0; i < 5; i++) {

					// Busca todos os dados do cardÃ¡pio por data
					Cardapio dataDoBanco = cardapioRepository.findByData(dataAtualDaProxSemana.getTime());
					
					if (dataDoBanco != null) {// Checa se nÃ£o estÃ¡ nulo
						
						Calendar cal = Calendar.getInstance();
						cal.setTime(dataDoBanco.getData());											
						
						if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
							countDisponivelSegunda = reservaItemRepository
									.qtdeDeReservasPorData(cal.getTime());
							countExpiradoSegunda = reservaItemRepository
									.qtdeDeReservasExpiradasPorData(cal.getTime());
						}
	
						if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
							countDisponivelTerca = reservaItemRepository
									.qtdeDeReservasPorData(cal.getTime());
							countExpiradoTerca = reservaItemRepository
									.qtdeDeReservasExpiradasPorData(cal.getTime());
						}
	
						if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
							countDisponivelQuarta = reservaItemRepository
									.qtdeDeReservasPorData(cal.getTime());
							countExpiradoQuarta = reservaItemRepository
									.qtdeDeReservasExpiradasPorData(cal.getTime());
						}
	
						if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
							countDisponivelQuinta = reservaItemRepository
									.qtdeDeReservasPorData(cal.getTime());
							countExpiradoQuinta = reservaItemRepository
									.qtdeDeReservasExpiradasPorData(cal.getTime());
						}
	
						if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
							countDisponivelSexta = reservaItemRepository
									.qtdeDeReservasPorData(cal.getTime());
							countExpiradoSexta = reservaItemRepository
									.qtdeDeReservasExpiradasPorData(cal.getTime());
						}
						
						// Qtde de reservas jÃ¡ feitas no Ãºltimo dia Ãºtil, por data da semana que vem
						for (ReservaItem reserva : reservasItensDoBD) { 	//Refazer este mÃ©todo para query?
							//Checa se a data da reserva remanescente foi feita hoje (o Ãºltimo dia Ãºtil)
							if (reserva.getReserva().getDataReserva().equals(dataHoje.getTime())) {
								//Checa se a data do cardÃ¡pio reservado Ã© igual a do banco
								if (reserva.getCardapio().getData().equals(cal.getTime())) {
	
									if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
										countSegunda++;
									}
	
									if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
										countTerca++;
									}
	
									if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
										countQuarta++;
									}
	
									if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
										countQuinta++;
									}
	
									if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
										countSexta++;
									}
								}
							}
						}
	
						// Verifica se o count disponÃ­vel Ã© menor que a qtde de refeiÃ§Ãµes subsidiadas e se o count das
						// reservas do Ãºltimo dia Ãºtil Ã© maior que 0
						if (countDisponivelSegunda < qtdeSubs || countDisponivelTerca < qtdeSubs
								|| countDisponivelQuarta < qtdeSubs || countDisponivelQuinta < qtdeSubs
								|| countDisponivelSexta < qtdeSubs || countExpiradoSegunda > 0 || countExpiradoTerca > 0
								|| countExpiradoQuarta > 0 || countExpiradoQuinta > 0 || countExpiradoSexta > 0) {
	
							// Verifica se a qtde de reservas do Ãºltimo dia Ãºtil Ã© menor ou igual a qtde de
							// reservas expiradas (que agora estÃ£o disponÃ­veis)
							if (countSegunda <= countExpiradoSegunda || countTerca <= countExpiradoTerca
									|| countQuarta <= countExpiradoQuarta || countQuinta <= countExpiradoQuinta
									|| countSexta <= countExpiradoSexta) {
								
								for (ReservaItem reserva : reservasItensDoBD) {
	
									if (cliente.getNome() == reserva.getReserva().getCliente().getNome()) {
	
										if (reservaItemRepository.verificarSeReservaExiste(cliente.getId(), cal.getTime()) != null) {	
	
											if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
												reservasAdmin
														.setSegundaStatus(reserva.getStatus().getDescricao());
											}
				
											if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
												reservasAdmin
														.setTercaStatus(reserva.getStatus().getDescricao());
											}
				
											if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
												reservasAdmin
														.setQuartaStatus(reserva.getStatus().getDescricao());
											}
				
											if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
												reservasAdmin
														.setQuintaStatus(reserva.getStatus().getDescricao());
											}
				
											if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
												reservasAdmin
														.setSextaStatus(reserva.getStatus().getDescricao());
											}			
										}
									}
								}
							}
						}	
						
						//Verifica se o status estÃ¡ nulo, caso sim, define status
						if (reservasAdmin.getSegundaStatus() == null) {
							reservasAdmin.setSegundaStatus("NÃ£o reservado");
						}

						if (reservasAdmin.getTercaStatus() == null) {
							reservasAdmin.setTercaStatus("NÃ£o reservado");
						}

						if (reservasAdmin.getQuartaStatus() == null) {
							reservasAdmin.setQuartaStatus("NÃ£o reservado");
						}

						if (reservasAdmin.getQuintaStatus() == null) {
							reservasAdmin.setQuintaStatus("NÃ£o reservado");
						}

						if (reservasAdmin.getSextaStatus() == null) {
							reservasAdmin.setSextaStatus("NÃ£o reservado");
						}
						
					} else {
						//Se a data nÃ£o existir, define o status como indisponÃ­vel
						Calendar cal = Calendar.getInstance();
						cal.setTime(dataAtualDaProxSemana.getTime());

						if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
							reservasAdmin.setSegundaStatus("Dia indisponÃ­vel");
						}

						if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
							reservasAdmin.setTercaStatus("Dia indisponÃ­vel");
						}

						if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
							reservasAdmin.setQuartaStatus("Dia indisponÃ­vel");
						}

						if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
							reservasAdmin.setQuintaStatus("Dia indisponÃ­vel");
						}

						if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
							reservasAdmin.setSextaStatus("Dia indisponÃ­vel");
						}
					}
	
					switch (i) {//Com base no indice do for, define as datas da prÃ³xima semana para aparecer nas colunas dos dias no datatable 
						case 0:
							segunda = dataAtualDaProxSemana.getTime();
						case 1:
							terca = dataAtualDaProxSemana.getTime();
						case 2:
							quarta = dataAtualDaProxSemana.getTime();
						case 3:
							quinta = dataAtualDaProxSemana.getTime();
						case 4:
							sexta = dataAtualDaProxSemana.getTime();
					}
					
					//SÃ³ serÃ¡ adicionado o objeto reservasAdmin, quando o status for diferente dos ifs
					if (!(reservasAdmin.getSegundaStatus() == "NÃ£o reservado"
							&& reservasAdmin.getSegundaStatus() == "Dia indisponÃ­vel"
							&& reservasAdmin.getTercaStatus() == "NÃ£o reservado"
							&& reservasAdmin.getTercaStatus() == "Dia indisponÃ­vel"
							&& reservasAdmin.getQuartaStatus() == "NÃ£o reservado"
							&& reservasAdmin.getQuartaStatus() == "Dia indisponÃ­vel"
							&& reservasAdmin.getQuintaStatus() == "NÃ£o reservado"
							&& reservasAdmin.getQuintaStatus() == "Dia indisponÃ­vel"
							&& reservasAdmin.getSextaStatus() == "NÃ£o reservado"
							&& reservasAdmin.getSextaStatus() == "Dia indisponÃ­vel")) {
						todasAsReservas.add(reservasAdmin);
					}
					
					dataAtualDaProxSemana.add(Calendar.DAY_OF_MONTH, 1);//Define o prÃ³ximo dia
				}
			}
		} 

		model.addAttribute("listagemRemanescentes", todasAsReservas);
		model.addAttribute("segunda", formatoDesejado.format(segunda));
		model.addAttribute("terca", formatoDesejado.format(terca));
		model.addAttribute("quarta", formatoDesejado.format(quarta));
		model.addAttribute("quinta", formatoDesejado.format(quinta));
		model.addAttribute("sexta", formatoDesejado.format(sexta));

		return "/remanescentes/pesquisar";
	}

	@GetMapping("/pagamento/{nome}")
	public String efetivarPagamentoRemanescentes(@PathVariable String nome, Model model)
			throws ParseException, JsonGenerationException, JsonMappingException, IOException {
		
		int countSegunda = 0, countTerca = 0, countQuarta = 0, countQuinta = 0, countSexta = 0,
				countDisponivelSegunda = 0, countDisponivelTerca = 0, countDisponivelQuarta = 0,
				countDisponivelQuinta = 0, countDisponivelSexta = 0, countExpiradoSegunda = 0,
				countExpiradoTerca = 0, countExpiradoQuarta = 0, countExpiradoQuinta = 0,
				countExpiradoSexta = 0;		
		
		int qtdeSubs = qtdeRefeicoesRepository.findAll().get(-1).getSubsidiada();
		
		Calendar dataHoje = Calendar.getInstance();	
		Calendar ultimoDiaUtil = Calendar.getInstance();
		Calendar dataAtualDaProxSemana = Calendar.getInstance();

		NumberFormat nf = NumberFormat.getCurrencyInstance();
		SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd/MM/yyyy");
		
		ReservasAdmin reservasAdmin = new ReservasAdmin();
		List<String> todasAsDatas = new ArrayList<String>();

		Cliente cliente = clienteRepository.findByNome(nome);
		List<ReservaItem> reservasItensDoBD = reservaItemRepository.findAll();

		dataAtualDaProxSemana = verificarData(dataAtualDaProxSemana);
		
		ultimoDiaUtil.setTime(dataAtualDaProxSemana.getTime());
		ultimoDiaUtil.add(Calendar.DATE, 4); // Com base no valor da dataAtualDaProxSemana, define a sexta-feira da prÃ³xima
												// semana

		List<Date> todasAsDatasDaProximaSemana = cardapioRepository.todasAsDatasDaSemana(dataAtualDaProxSemana.getTime(),
				ultimoDiaUtil.getTime());
		ultimoDiaUtil.setTime(todasAsDatasDaProximaSemana.get(todasAsDatasDaProximaSemana.size() - 1)); // Define
																										// o
																										// Ãºltimo
																										// dia
																										// util,
																										// conforme
																										// o
																										// range
																										// de
																										// datas
																										// do BD
		
		Extrato ultimoExtrato = extratoRepository.findFirstByClienteOrderByIdDesc(cliente);
		BigDecimal saldo = ultimoExtrato != null ? ultimoExtrato.getSaldo() : new BigDecimal(0.00);

		reservasAdmin.setNome(cliente.getNome());
		reservasAdmin.setCreditos(nf.format(saldo));

		if (!(dataHoje.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
				&& !(dataHoje.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
				&& feriadoRepository.findByData(dataHoje.getTime()) == null
				&& dataHoje.get(Calendar.DAY_OF_WEEK) != ultimoDiaUtil.get(Calendar.DAY_OF_WEEK)//NÃ£o se esqueÃ§a de voltar para igual
				&& todasAsDatasDaProximaSemana.size() >= 2) {

			for (int i = 0; i < todasAsDatasDaProximaSemana.size(); i++) {	
				
				Cardapio dataDoBanco = cardapioRepository.findByData(dataAtualDaProxSemana.getTime());
	
				if (dataDoBanco != null) {
	
					Calendar cal = Calendar.getInstance();
					cal.setTime(dataDoBanco.getData());
					
					if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
						countDisponivelSegunda = reservaItemRepository
								.qtdeDeReservasPorData(cal.getTime());
						countExpiradoSegunda = reservaItemRepository
								.qtdeDeReservasExpiradasPorData(cal.getTime());
					}

					if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
						countDisponivelTerca = reservaItemRepository
								.qtdeDeReservasPorData(cal.getTime());
						countExpiradoTerca = reservaItemRepository
								.qtdeDeReservasExpiradasPorData(cal.getTime());
					}

					if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
						countDisponivelQuarta = reservaItemRepository
								.qtdeDeReservasPorData(cal.getTime());
						countExpiradoQuarta = reservaItemRepository
								.qtdeDeReservasExpiradasPorData(cal.getTime());
					}

					if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
						countDisponivelQuinta = reservaItemRepository
								.qtdeDeReservasPorData(cal.getTime());
						countExpiradoQuinta = reservaItemRepository
								.qtdeDeReservasExpiradasPorData(cal.getTime());
					}

					if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
						countDisponivelSexta = reservaItemRepository
								.qtdeDeReservasPorData(cal.getTime());
						countExpiradoSexta = reservaItemRepository
								.qtdeDeReservasExpiradasPorData(cal.getTime());
					}
					
					// Qtde de reservas jÃ¡ feitas no Ãºltimo dia Ãºtil, por data dos dias da semana que vem
					for (ReservaItem reserva : reservasItensDoBD) {//Converter para query?

						if (reserva.getReserva().getDataReserva().equals(dataHoje.getTime())) {

							if (reserva.getCardapio().getData().equals(cal.getTime())) {
								
								if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
									countSegunda++;
								}

								if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
									countTerca++;
								}

								if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
									countQuarta++;
								}

								if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
									countQuinta++;
								}

								if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
									countSexta++;
								}
							}
						}
					}

					// Verifica se o count disponÃ­vel Ã© menor que qtde de refeiÃ§Ãµes subsidiadas e se o count das
					// reservas do Ãºltimo dia Ãºtil Ã© maior que 0
					if (countDisponivelSegunda < qtdeSubs || countDisponivelTerca < qtdeSubs
							|| countDisponivelQuarta < qtdeSubs || countDisponivelQuinta < qtdeSubs
							|| countDisponivelSexta < qtdeSubs || countExpiradoSegunda > 0 || countExpiradoTerca > 0
							|| countExpiradoQuarta > 0 || countExpiradoQuinta > 0 || countExpiradoSexta > 0) {

						// Verifica se a qtde de reservas do Ãºltimo dia Ãºtil Ã© menor ou igual a qtde de
						// reservas expiradas (que agora estÃ£o disponÃ­veis)
						if (countSegunda <= countExpiradoSegunda || countTerca <= countExpiradoTerca
								|| countQuarta <= countExpiradoQuarta || countQuinta <= countExpiradoQuinta
								|| countSexta <= countExpiradoSexta) {
							
							if(reservaItemRepository.verificarSeReservaExiste(cliente.getId(), cal.getTime()) == null) {
								
								todasAsDatas.add(formatoDesejado.format(cal.getTime()));
							}
						}
					}
				}
	
				dataAtualDaProxSemana.add(Calendar.DAY_OF_MONTH, 1);
			}		
		}

		model.addAttribute(reservasAdmin);
		model.addAttribute("remanescentes", new ReservasAdmin());
		model.addAttribute("datasReservas", todasAsDatas);
		model.addAttribute("valorRefeicao",	clienteCategoriaRepository.findByCliente(cliente).getCategoria().getValorComSubsidio());

		ObjectMapper mapper = new ObjectMapper();// jackson lib for converting to json
		String objectJSON = mapper.writeValueAsString(todasAsDatas);// json string
		model.addAttribute("objectJSON", objectJSON);

		return "/remanescentes/pagamento";
	}

	@PostMapping("/salvar")
	public String salvarRemanescentes(@RequestParam("nome") String nome, @RequestParam("data") String[] Datas,
			@RequestParam("tipoRefeicao") Integer[] idsTipoRefeicao,
			@RequestParam(value = "valor", required = false) String valor,
			@RequestParam(value = "recargas", required = false) String recargas,
			@RequestParam(value = "utilizarCreditos", required = false) Boolean creditos) throws ParseException {

		Cliente cliente = clienteRepository.findByNome(nome);
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis()); // Data e hora atual

		List<Integer> tiposRefeicoesSelecionados = new ArrayList<Integer>();
		
		SimpleDateFormat formatoDesejado = new SimpleDateFormat("yyyy-MM-dd");

		if (creditos == null) {

			if (recargas == null) {
				
				Reserva reserva = new Reserva();
				reserva.setCliente(cliente);
				reserva.setTipoValor(tipoValorRepository.findByDescricao("Subsidiada"));
				reserva.setDataReserva(timestamp);

				reservaRepository.save(reserva);

				Long ultimaReserva = reservaRepository.findFirstByClienteOrderByIdDesc(cliente).getId();

				for (int z = 0; z < idsTipoRefeicao.length; z++) {
					if (idsTipoRefeicao[z] != null) {
						tiposRefeicoesSelecionados.add(idsTipoRefeicao[z]);
					}
				}

				for (int z = 0; z < Datas.length; z++) {
					Reserva r = new Reserva();
					Cardapio c = new Cardapio();
					ReservaItem reservaItem = new ReservaItem();

					r.setId(ultimaReserva);
					c.setId(cardapioRepository.findByData(formatoDesejado.parse(Datas[z])).getId());

					reservaItem.setReserva(r);
					reservaItem.setCardapio(c);
					reservaItem.setStatus(statusRepository.findByDescricao("Pago"));
					reservaItem.setTipoRefeicao(tiposRefeicoesSelecionados.get(z) % 2 == 0
							? tipoRefeicaoRepository.findByDescricao("Vegetariano")
							: tipoRefeicaoRepository.findByDescricao("Tradicional"));
					reservaItem.setExtrato(null);

					reservaItemRepository.save(reservaItem);
				}
			} else {
				
				Reserva reserva = new Reserva();
				reserva.setCliente(cliente);
				reserva.setTipoValor(tipoValorRepository.findByDescricao("Subsidiada"));
				reserva.setDataReserva(timestamp);

				reservaRepository.save(reserva);

				Long ultimaReserva = reservaRepository.findFirstByClienteOrderByIdDesc(cliente).getId();

				Extrato ultimoExtrato = extratoRepository.findFirstByClienteOrderByIdDesc(cliente);
				BigDecimal saldo = ultimoExtrato != null ? ultimoExtrato.getSaldo() : new BigDecimal(0.00);

				Extrato extrato = new Extrato();
				extrato.setCliente(cliente);
				extrato.setTransacao(new BigDecimal(recargas.replaceAll(",", ".")));
				extrato.setDataTransacao(timestamp);
				extrato.setSaldo(saldo.add(new BigDecimal(recargas.replaceAll(",", "."))));

				extratoRepository.save(extrato);

				for (int z = 0; z < idsTipoRefeicao.length; z++) {
					if (idsTipoRefeicao[z] != null) {
						tiposRefeicoesSelecionados.add(idsTipoRefeicao[z]);
					}
				}

				for (int z = 0; z < Datas.length; z++) {
					Reserva r = new Reserva();
					Cardapio c = new Cardapio();
					ReservaItem reservaItem = new ReservaItem();

					r.setId(ultimaReserva);
					c.setId(cardapioRepository.findByData(formatoDesejado.parse(Datas[z])).getId());

					reservaItem.setReserva(r);
					reservaItem.setCardapio(c);
					reservaItem.setStatus(statusRepository.findByDescricao("Pago"));
					reservaItem.setTipoRefeicao(tiposRefeicoesSelecionados.get(z) % 2 == 0
							? tipoRefeicaoRepository.findByDescricao("Vegetariano")
							: tipoRefeicaoRepository.findByDescricao("Tradicional"));
					reservaItem.setExtrato(extrato);

					reservaItemRepository.save(reservaItem);
				}
			}
		} else {

			BigDecimal saldo = extratoRepository.findFirstByClienteOrderByIdDesc(cliente).getSaldo();

			Extrato extrato = new Extrato();
			extrato.setCliente(cliente);
			extrato.setTransacao(new BigDecimal(recargas.replaceAll(",", ".")));
			extrato.setDataTransacao(timestamp);
			extrato.setSaldo(saldo.add(new BigDecimal(recargas.replaceAll(",", "."))));

			extratoRepository.save(extrato);

			ClienteCategoria clienteCategoria = clienteCategoriaRepository.findByCliente(cliente);

			BigDecimal saldoAtualizado = extratoRepository.findFirstByClienteOrderByIdDesc(cliente).getSaldo();

			Extrato debitoNoSaldo = new Extrato();
			debitoNoSaldo.setCliente(cliente);
			debitoNoSaldo.setDataTransacao(timestamp);

			BigDecimal transacao = BigDecimal.valueOf(Datas.length)
					.multiply(clienteCategoria.getCategoria().getValorComSubsidio());

			debitoNoSaldo.setSaldo(saldoAtualizado.subtract(transacao));
			debitoNoSaldo.setTransacao(transacao.negate());

			extratoRepository.save(debitoNoSaldo);

			Reserva reserva = new Reserva();
			reserva.setCliente(cliente);
			reserva.setTipoValor(tipoValorRepository.findByDescricao("Subsidiada"));
			reserva.setDataReserva(timestamp);

			reservaRepository.save(reserva);

			Long ultimaReserva = reservaRepository.findFirstByClienteOrderByIdDesc(cliente).getId();

			for (int z = 0; z < idsTipoRefeicao.length; z++) {
				if (idsTipoRefeicao[z] != null) {
					tiposRefeicoesSelecionados.add(idsTipoRefeicao[z]);
				}
			}

			for (int z = 0; z < Datas.length; z++) {
				Reserva r = new Reserva();
				Cardapio c = new Cardapio();
				ReservaItem reservaItem = new ReservaItem();

				r.setId(ultimaReserva);
				c.setId(cardapioRepository.findByData(formatoDesejado.parse(Datas[z])).getId());

				reservaItem.setReserva(r);
				reservaItem.setCardapio(c);
				reservaItem.setStatus(statusRepository.findByDescricao("Pago"));
				reservaItem.setTipoRefeicao(tiposRefeicoesSelecionados.get(z) % 2 == 0
						? tipoRefeicaoRepository.findByDescricao("Vegetariano")
						: tipoRefeicaoRepository.findByDescricao("Tradicional"));
				reservaItem.setExtrato(debitoNoSaldo);

				reservaItemRepository.save(reservaItem);
			}
		}

		return "redirect:/remanescentes";
	}

	public static Calendar verificarData(Calendar data) {
		// se for segunda
		if (data.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
			data.add(Calendar.DATE, 7);
		}
		// se for terÃ§a
		else if (data.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
			data.add(Calendar.DATE, 6);
		}
		// se for quarta
		else if (data.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
			data.add(Calendar.DATE, 5);
		}
		// se for quinta
		else if (data.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
			data.add(Calendar.DATE, 4);
		}
		// se for sexta
		else if (data.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
			data.add(Calendar.DATE, 3);
		}
		// se for sabado
		else if (data.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			data.add(Calendar.DATE, 2);
		}
		// se for domingo
		else if (data.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			data.add(Calendar.DATE, 1);
		}
		return data;
	}
}