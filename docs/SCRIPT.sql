/* Tabela com os dados dos usuarios
Quais dados terão no nosso banco e quais não visto que serão puxados do banco da central de acesso da unesp ?
Como será o primeiro login ? -> Precisa saber o tipo de refeição que ele quer consumir.
Como saber se o cara já tem cadastro no nosso banco - login ?
Fazer histórico de créditos.
Pode mudar o curso ?
Haverá perfis de usuários na central de acessos que fará a autenticação */
CREATE TABLE cliente
(
  id SERIAL NOT NULL,
  identificacao CHARACTER VARYING(50) NULL,
  cpf CHARACTER VARYING(14) NOT NULL,
  nome CHARACTER VARYING(100) NOT NULL,  
  biometria CHARACTER VARYING(100) NOT NULL,
  data_criado TIMESTAMP NOT NULL,

  PRIMARY KEY(id)
);

/* Tabela para diferenciar tipo de refeição (tradicional, vegetariano, vegano) */
CREATE TABLE tipo_refeicao
(
  id SERIAL NOT NULL,
  descricao CHARACTER VARYING(50),

  PRIMARY KEY(id)
);

/* Tabela com os cursos e seus periodos,
Pode haver uma mesma descrição mas diferente período */
CREATE TABLE curso
(
  id SERIAL NOT NULL,
  descricao CHARACTER VARYING(100),
  periodo CHARACTER VARYING(50),

  PRIMARY KEY(id)
);

/* Tabela referente aos status que as refeições podem ter antes de serem consumidas (pago, solicitado, expirado/não pago????, transferido, transferente, consuimida) */
CREATE TABLE status
(
  id SERIAL NOT NULL,
  descricao CHARACTER VARYING(50),

  PRIMARY KEY(id)
);

/* Tabela referente aos períodos que as refeições podem ter ao ser atrelado na tabela cardapio
(Café da manhã, almoço e janta) */
CREATE TABLE periodo_refeicao
(
  id SERIAL NOT NULL,
  descricao CHARACTER VARYING(50),

  PRIMARY KEY(id)
);

/* Tabela referente aos itens do cardápio */
CREATE TABLE cardapio
(
  id SERIAL NOT NULL,
  data DATE NOT NULL,
  prato_base varchar(50) NOT NULL,
  prato_tradicional varchar(100) NOT NULL,
  prato_vegetariano varchar(100) NOT NULL,
  guarnicao varchar(100) NOT NULL,
  salada varchar(50) NOT NULL,
  sobremesa varchar(50) NOT NULL,
  suco varchar(50) NOT NULL,
  id_periodo_refeicao INTEGER NOT NULL,

  PRIMARY KEY (id),
  CONSTRAINT fk_id_periodo_refeicao FOREIGN KEY(id_periodo_refeicao) REFERENCES periodo_refeicao(id)
);

/* Tabela para identificar se a reserva foi feita com o valor sem ou com subsídio
Pensar em um nome melhor */
CREATE TABLE tipo_valor 
(
  id SERIAL NOT NULL,
  descricao CHARACTER VARYING(50),

  PRIMARY KEY(id)
);

/* Tabela para especificar as categorias e seus preços */
CREATE TABLE categoria
(
  id SERIAL NOT NULL,
  descricao CHARACTER VARYING(100) NOT NULL,
  valor_sem_subsidio numeric(15,2) NOT NULL,
  valor_com_subsidio numeric(15,2) NOT NULL,

  PRIMARY KEY(id)
);

/* Tabela que informa a quantidade de refeição
Não necessita criar histórico, pois ela é usada somente como limite para reserva e não para saber quantas foram vendidas já que esse dado pode ser obtido pela status consumido */
CREATE TABLE quantidade_refeicao
(
  id SERIAL NOT NULL,
  subsidiada INTEGER NOT NULL,
  custo INTEGER NOT NULL,

  PRIMARY KEY(id)
);

/* Tabela responsável para saber quando será o penultimo dia util da semana */
CREATE TABLE feriado
(
  id SERIAL NOT NULL,
  descricao CHARACTER VARYING(100),
  data DATE NOT NULL,

  PRIMARY KEY(id)
);

/* Tabela extrato -> transacao: + Entrada - Saída (saída -> registra em reserva_item) */
CREATE TABLE extrato
(
  id SERIAL NOT NULL,
  id_cliente INTEGER NOT NULL,
  transacao numeric(15,2) NOT NULL,
  data_transacao TIMESTAMP NOT NULL,
  saldo numeric(15,2) NOT NULL,
  
  PRIMARY KEY(id),
  CONSTRAINT fk_id_cliente FOREIGN KEY(id_cliente) REFERENCES cliente(id)
);

/* Tabela responsável por associar a reserva com um cliente e o tipo do valor da refeição (com ou sem subsídio). */
CREATE TABLE reserva
(
  id SERIAL NOT NULL,
  id_cliente INTEGER NOT NULL,
  id_tipo_valor INTEGER NOT NULL,
  data_reserva TIMESTAMP NOT NULL,

  PRIMARY KEY(id),
  CONSTRAINT fk_id_cliente FOREIGN KEY(id_cliente) REFERENCES cliente(id),
  CONSTRAINT fk_id_tipo_valor FOREIGN KEY(id_tipo_valor) REFERENCES tipo_valor(id)
);

/* TENTAR FAZER SEM A TEMP
Tabela responsável por associar a reserva (id_reserva) com seus itens que possuem data e status */
CREATE TABLE reserva_item
(
  id SERIAL NOT NULL,
  id_reserva INTEGER NOT NULL,
  id_cardapio INTEGER NOT NULL,
  id_status INTEGER NOT NULL,
  id_tipo_refeicao INTEGER NOT NULL,
  id_extrato INTEGER,
  
  PRIMARY KEY(id),
  CONSTRAINT fk_id_reserva FOREIGN KEY(id_reserva) REFERENCES reserva(id),
  CONSTRAINT fk_id_cardapio FOREIGN KEY(id_cardapio) REFERENCES cardapio(id),
  CONSTRAINT fk_id_status FOREIGN KEY(id_status) REFERENCES status(id),
  CONSTRAINT fk_id_tipo_refeicao FOREIGN KEY(id_tipo_refeicao) REFERENCES tipo_refeicao(id),
  CONSTRAINT fk_id_extrato FOREIGN KEY(id_extrato) REFERENCES extrato(id)
);

/* VER COMO É MELHOR ??
 Tabela temporária para gravar das 7h às 10h do primeiro dia útil todas as solicitações de reservas e associar a reserva com um cliente 
CREATE TABLE reserva_temp (solic. reserva)
(
  id SERIAL NOT NULL,
  id_usuario INTEGER NOT NULL,
  data_hora TIMESTAMP NOT NULL,

  PRIMARY KEY(id),
  CONSTRAINT fk_id_cliente FOREIGN KEY(id_cliente) REFERENCES cliente(id)
); */

/* Tabela temporária para gravar das 7h às 10h do primeiro dia útil todas as solicitações de reservas e associar seus itens com a data
Somente colocará status pago nas datas reservadas se ele tiver créditos para todos os dias 
CREATE TABLE reserva_item_temp
(
  id SERIAL NOT NULL,
  id_reserva_temp INTEGER NOT NULL,
  id_cardapio INTEGER NOT NULL,
  
  PRIMARY KEY(id),
  CONSTRAINT fk_id_reserva_temp FOREIGN KEY(id_reserva_temp) REFERENCES reserva_temp(id),
  CONSTRAINT fk_id_data FOREIGN KEY(id_data) REFERENCES cardapio(id)
); */

/* Tabela que relaciona o cliente com sua categoria e mantém em histórico através de Triggers (UPDATE) e campos para data_inicio e data_fim.
Usuário pode até pertencer a duas categorias, mas deve escolher uma. */
CREATE TABLE cliente_categoria
(
  id SERIAL NOT NULL,
  id_cliente INTEGER NOT NULL,
  id_categoria INTEGER NOT NULL,
  ra_matricula INTEGER NOT NULL, /* Terá ? Por enquanto sim */
  data_inicio TIMESTAMP NOT NULL,
  data_fim TIMESTAMP, /* Pode ser NULL ->saber atual(ais) */

  PRIMARY KEY(id),
  CONSTRAINT fk_id_cliente FOREIGN KEY(id_cliente) REFERENCES cliente(id),
  CONSTRAINT fk_id_categoria FOREIGN KEY(id_categoria) REFERENCES categoria(id)
);

/* Tabela que relaciona o cliente com seu curso e mantém em histórico através de Triggers (UPDATE) e campos para data_inicio e data_fim.*/
CREATE TABLE cliente_curso
(
  id SERIAL NOT NULL,
  id_cliente INTEGER NOT NULL,
  id_curso INTEGER NOT NULL,
  data_inicio TIMESTAMP NOT NULL,
  data_fim TIMESTAMP, /* Pode ser NULL ->saber atual(ais) */

  PRIMARY KEY(id),
  CONSTRAINT fk_id_cliente FOREIGN KEY(id_cliente) REFERENCES cliente(id),
  CONSTRAINT fk_id_curso FOREIGN KEY(id_curso) REFERENCES curso(id)
);