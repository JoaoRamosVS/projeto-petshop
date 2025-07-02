create database DB_PETSHOP;
use DB_PETSHOP;

create table TB_PERFIS (
	ID int primary key auto_increment,
    DESCRICAO varchar(20) not null
);

create table TB_USUARIOS (
	ID int auto_increment primary key,
    EMAIL varchar(50) not null unique,
    SENHA varchar(64) not null,
    FOTO varchar(255),
    ATIVO char(1) default 'S' CHECK (ATIVO IN ('S','N')),
    PERFIL_ID int,
    foreign key (PERFIL_ID) references TB_PERFIS(ID)
);

create table TB_FUNCIONARIOS (
	ID int auto_increment primary key,
    NOME varchar(50) not null,
    CPF varchar(14) not null unique,
    ENDERECO varchar(50),
    BAIRRO varchar(30),
	CIDADE varchar(50),
	UF varchar(2),
	CEP varchar(9) not null,
    TELEFONE varchar(15),
    CARGO varchar(20),
    SALARIO decimal(7,2) not null,
    USUARIO_ID int,
    foreign key (USUARIO_ID) references TB_USUARIOS(ID)
);

create table TB_TUTORES (
	ID int not null auto_increment primary key,
    NOME varchar(50) not null,
    CPF varchar(14) not null unique,
    ENDERECO varchar(50),
    BAIRRO varchar(30),
	CIDADE varchar(50),
	UF varchar(2),
	CEP varchar(9) not null,
    TELEFONE varchar(15) not null,
    USUARIO_ID int,
    foreign key (USUARIO_ID) references TB_USUARIOS(ID)
);

create table TB_PETS (
	ID int not null auto_increment,
    RACA varchar(30) not null,
    TAMANHO int check (TAMANHO IN (1,2,3,4)) not null, -- 1 - muito pequeno, 2 - pequeno, 3 - medio, 4 - grande
    PESO decimal(5,2) not null,
    IDADE int not null,
    OBS text,
    OCORRENCIAS text,
    TUTOR_ID int,
    foreign key (TUTOR_ID) references TB_TUTORES (ID)
);

create table TB_COMPRAS (
	ID int not null auto_increment,
    VENDEDOR_ID int,
    VALOR decimal(8,2) not null,
	DT_COMPRA datetime not null,
	DT_PAGAMENTO datetime,
    QTD_ITENS int not null,
    foreign key (VENDEDOR_ID) references TB_FUNCIONARIOS(ID)
);

create table TB_AGENDAMENTO (
	ID int primary key auto_increment,
    TIPO int not null check (TIPO in (1,2,3,4)), -- 1 - Banho, 2 - Tosa, 3 - Banho e Tosa, 4 - Consulta Médica
    DT_CRIACAO datetime default current_timestamp(),
    DT_AGENDAMENTO datetime not null,
    DESCRICAO text,
    OBS text,
    FUNC_ID int,
    CRIADOR_ID int,
    PET_ID int,
	foreign key (FUNC_ID) references TB_FUNCIONARIOS(ID),
    foreign key (CRIADOR_ID) references TB_USUARIOS(ID),
    foreign key (PET_ID) references TB_PETS(ID)
);

create table TB_CATEGORIAS (
	ID int primary key auto_increment,
    DESCRICAO varchar(50) not null
);

create table TB_MEDIDAS (
	ID int primary key auto_increment,
    DESCRICAO varchar(50) not null
);

create table TB_PRODUTOS (
	ID int primary key auto_increment,
    NOME varchar(50) not null,
    MARCA varchar(50) not null,
    FABRICANTE varchar(50),
    MODELO varchar(50),
    DESCRICAO text,
    COR varchar(15),
    PESO decimal(10,3), -- 9999999.999
    ALTURA decimal(10,3), -- 9999999.999
    LARGURA decimal(10,3), -- 9999999.999
	PROFUNDIDADE decimal(10,3), -- 9999999.999
    VLR_UNITARIO decimal(10,3), -- 9999999.999
    UN_MEDIDA_ID int, 
    CATEGORIA_ID int,
    foreign key (CATEGORIA_ID) references TB_CATEGORIAS(ID),
    foreign key (UN_MEDIDA_ID) references TB_MEDIDAS(ID)
);

create table TB_PEDIDOS (
	ID int primary key auto_increment,
    DT_PEDIDO datetime not null default current_timestamp(),
    VLR_TOTAL decimal(10,3), -- 9999999.999
    DT_PAGAMENTO datetime,
    NUM_NF int,
    DT_RETIRADA datetime, -- se pedido for feito online, podera ser diferente. no front deve ter tratamento 
    CLIENTE_ID int,
    FUNCIONARIO_ID int null, -- null indica que o campo pode estar vazio, no caso do pedido ser feito online. caso contrário deve ser preenchido com o id do caixa
    foreign key (CLIENTE_ID) references TB_TUTOR(ID),
    foreign key (FUNCIONARIO_ID) references TB_FUNCIONARIOS(ID)
);

CREATE TABLE TB_ITENS_PEDIDO (
    PEDIDO_ID int not null,
    PRODUTO_ID int not null,
    QUANTIDADE int not null default 1,
    VLR_UNITARIO_MOMENTO DECIMAL(10,3) NOT NULL, -- armazena o valor do produto no momento da compra, pois o preço em tb_produtos pode mudar futuramente
    PRIMARY KEY (PEDIDO_ID, PRODUTO_ID), -- chave primária composta, impedindo que o mesmo pedido tenha o mesmo produto adicionado duas vezes. no caso onde isso seja necessário, somente atualizar a quantidade
	FOREIGN KEY (PEDIDO_ID) REFERENCES TB_PEDIDOS(ID),
    FOREIGN KEY (PRODUTO_ID) REFERENCES TB_PRODUTOS(ID)
);

INSERT INTO TB_PERFIS (DESCRICAO) VALUES ('Administrador'); -- retornará ID 1
INSERT INTO TB_USUARIOS (EMAIL, SENHA, ATIVO, FOTO, PERFIL_ID) VALUES ('admin', 'admin', 'S', null, 1);