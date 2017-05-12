CREATE TABLE cardapio (
data_refeicao date NOT NULL,
prato_base varchar(50) NOT NULL,
prato_trad varchar(50) NOT NULL,
prato_veg varchar(50) NOT NULL,
guarnicao varchar(50) NOT NULL,
salada varchar(50) NOT NULL,
sobremesa varchar(50) NOT NULL,
suco varchar(50) NOT NULL,
CONSTRAINT cardapio_pk PRIMARY KEY (data_refeicao)
);
CREATE TABLE feriados (
id_feriado serial NOT NULL,
descricao varchar(50) NOT NULL,
data_feriado date NOT NULL,
CONSTRAINT feriados_pk PRIMARY KEY (id_feriado)
);



CREATE TABLE tipo_usuario
(
  id_tipo SERIAL NOT NULL,
  descricao CHARACTER VARYING(50),
  valor_ref_sem_subsidio DECIMAL,
  valor_ref_com_subsidio DECIMAL,
  CONSTRAINT pk_id_tipo PRIMARY KEY(id_tipo)
);
CREATE TABLE cursos
(
  id_curso SERIAL NOT NULL UNIQUE,
  descricao CHARACTER VARYING(100),
  periodo CHARACTER VARYING(50),
  CONSTRAINT pk_id_desc_periodo PRIMARY KEY(id_curso,descricao,periodo)
);
CREATE TABLE categorias
(
  id_categoria SERIAL NOT NULL,
  descricao CHARACTER VARYING(100),
  CONSTRAINT pk_id_categorias PRIMARY KEY(id_categoria)
);

CREATE TABLE status
(
  id_status SERIAL NOT NULL,
  descricao CHARACTER VARYING(50),
  CONSTRAINT pk_id_status PRIMARY KEY(id_status)
);
CREATE TABLE qtde_refeicoes
(
   data DATE NOT NULL,
   subsidiada INTEGER NOT NULL,
   custo numeric NOT NULL,
   reserva INTEGER NOT NULL,
   CONSTRAINT pk_data PRIMARY KEY(data)
);


CREATE TABLE usuarios
(
  id_usuario SERIAL NOT NULL,
  cpf INTEGER NOT NULL,
  nome CHARACTER VARYING(100),
  matricula INTEGER NOT NULL,
  id_tipo INTEGER NOT NULL,
  id_curso INTEGER NOT NULL,
  id_categoria INTEGER NOT NULL,
  data_nasc DATE NOT NULL,
  excluido char NOT NULL,
  senha CHARACTER VARYING(100),
  CONSTRAINT pk_id_usuario PRIMARY KEY(id_usuario),
  CONSTRAINT fk_id_tipo FOREIGN KEY(id_tipo) REFERENCES tipo_usuario(id_tipo),
  CONSTRAINT fk_id_curso FOREIGN KEY(id_curso) REFERENCES cursos(id_curso),
  CONSTRAINT fk_id_categoria FOREIGN KEY(id_categoria) REFERENCES categorias(id_categoria)
);

CREATE TABLE pedido
(
  id_pedido SERIAL,
  data_cardapio DATE NOT NULL,
  id_status INTEGER NOT NULL,
  id_usuario INTEGER NOT NULL,
  qtde INTEGER NOT NULL,
  CONSTRAINT pk_id_pedido PRIMARY KEY(id_pedido),
  CONSTRAINT fk_id_status FOREIGN KEY(id_status) REFERENCES status(id_status), 
  CONSTRAINT fk_id_usuario FOREIGN KEY(id_usuario) REFERENCES usuarios(id_usuario),
  CONSTRAINT fk_data_cardapio FOREIGN KEY(data_cardapio) REFERENCES qtde_refeicoes(data)
);