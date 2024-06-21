drop database if exists dbsmartshop;
create database dbsmartshop;
use dbsmartshop;

create table bloco (
	ID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    BLOCO VARCHAR(30) NOT NULL
);

create table apartamento (
	ID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    APARTAMENTO INT NOT NULL
);

create table usuario (
	ID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    ID_BLOCO INT,
    ID_APARTAMENTO INT,
    NOME VARCHAR(255) NOT NULL,
    TELEFONE VARCHAR(255) NOT NULL,
    EMAIL VARCHAR(200) NOT NULL,
    CPF CHAR(11) NOT NULL,
    TIPO VARCHAR(30) DEFAULT 'CLIENTE',
    SENHA VARCHAR(8),
    FOREIGN KEY (ID_BLOCO) REFERENCES BLOCO (ID),
    FOREIGN KEY (ID_APARTAMENTO) REFERENCES APARTAMENTO (ID)
);

create table categoria (
	ID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    NOME VARCHAR(100) NOT NULL
);

create table marca (
	ID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    NOME VARCHAR(100) NOT NULL
);

create table produto (
	ID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    ID_CATEGORIA INT,
    ID_MARCA INT,
    NOME VARCHAR(255) NOT NULL,
    CODIGO_DE_BARRAS VARCHAR(13) NOT NULL,
    PRECO DECIMAL(4,2) NOT NULL,
    ESTOQUE INT NOT NULL,
    FOREIGN KEY (ID_CATEGORIA) REFERENCES CATEGORIA (ID),
    FOREIGN KEY (ID_MARCA) REFERENCES MARCA (ID)
);

create table forma_pagamento (
	ID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    TIPO VARCHAR(255) NOT NULL
);

create table carrinho (
	ID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    ID_FORMA_PAGAMENTO INT,
    ID_CLIENTE INT,
    DATA_COMPRA DATE NOT NULL,
    TOTAL DECIMAL(7,2) NOT NULL,
    FOREIGN KEY (ID_FORMA_PAGAMENTO) REFERENCES FORMA_PAGAMENTO (ID)
);

create table item (
	ID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    ID_CARRINHO INT,
    ID_PRODUTO INT,
    PRECO DECIMAL(6,2) NOT NULL,
    QUANTIDADE INT NOT NULL,
    FOREIGN KEY (ID_CARRINHO) REFERENCES CARRINHO (ID),
    FOREIGN KEY (ID_PRODUTO) REFERENCES PRODUTO (ID)
);