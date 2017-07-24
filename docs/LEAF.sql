CREATE TABLE tipo
(
	id SERIAL NOT NULL,
	descricao CHARACTER VARYING(50),

	PRIMARY KEY(id)
);

CREATE TABLE curso
(	
	id SERIAL NOT NULL,
	descricao CHARACTER VARYING(100),
	periodo CHARACTER VARYING(50),

	PRIMARY KEY(id)
);

CREATE TABLE categoria
(	
	id SERIAL NOT NULL,
	descricao CHARACTER VARYING(100) NOT NULL,
	valor_sem_subsidio numeric(15,2) NOT NULL,
	valor_com_subsidio numeric(15,2) NOT NULL,

	PRIMARY KEY(id)
);

CREATE TABLE status
(	
	id SERIAL NOT NULL,
	descricao CHARACTER VARYING(50),

	PRIMARY KEY(id)
);

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

	PRIMARY KEY (id)
);

CREATE TABLE cliente
(	
	id SERIAL NOT NULL,
	cpf CHARACTER VARYING(14) NOT NULL,
	email CHARACTER VARYING(100) NOT NULL,
	senha CHARACTER VARYING(100) NOT NULL,
	nome CHARACTER VARYING(100) NOT NULL,
	matricula SERIAL NOT NULL,
	id_tipo SERIAL NOT NULL,
	id_curso SERIAL NOT NULL,
	data_nascimento DATE NOT NULL,
	creditos numeric(15,2) NOT NULL, 
	
	PRIMARY KEY(id),
	CONSTRAINT fk_id_tipo FOREIGN KEY(id_tipo) REFERENCES tipo(id),
	CONSTRAINT fk_id_curso FOREIGN KEY(id_curso) REFERENCES curso(id)
);

CREATE TABLE cliente_categoria
(	
	id SERIAL NOT NULL,
	id_cliente SERIAL NOT NULL,
	id_categoria SERIAL NOT NULL,
	ativo SERIAL NOT NULL,

	PRIMARY KEY(id),
  	CONSTRAINT fk_id_cliente FOREIGN KEY(id_cliente) REFERENCES cliente(id),
  	CONSTRAINT fk_id_categoria FOREIGN KEY(id_categoria) REFERENCES categoria(id)
);

CREATE TABLE quantidade_refeicao
(	
	id SERIAL NOT NULL,
	subsidiada SERIAL NOT NULL,
	custo SERIAL NOT NULL,

	PRIMARY KEY(id)
);

CREATE TABLE feriado
(	
	id SERIAL NOT NULL,
	descricao CHARACTER VARYING(100),
	data DATE NOT NULL,

	PRIMARY KEY(id)
);	

CREATE TABLE reserva
(	
	id SERIAL NOT NULL,
	id_status SERIAL NOT NULL,
	id_cliente SERIAL NOT NULL,

	PRIMARY KEY(id),
	CONSTRAINT fk_id_status FOREIGN KEY(id_status) REFERENCES status(id), 
	CONSTRAINT fk_id_cliente FOREIGN KEY(id_cliente) REFERENCES cliente(id)
);

CREATE TABLE reserva_item
(	
	id SERIAL NOT NULL,
	id_reserva SERIAL NOT NULL,
	id_cardapio SERIAL NOT NULL,

	PRIMARY KEY(id),
	CONSTRAINT fk_id_reserva FOREIGN KEY(id_reserva) REFERENCES reserva(id),
	CONSTRAINT fk_id_cardapio FOREIGN KEY(id_cardapio) REFERENCES cardapio(id)
);