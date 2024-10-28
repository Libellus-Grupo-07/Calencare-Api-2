-- Criação do banco de dados
CREATE DATABASE calencare;
USE calencare;

-- Tabela empresa
CREATE TABLE empresa (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         intervalo_atendimento INT,
                         dt_criacao DATETIME,
                         cnpj VARCHAR(255),
                         email_principal VARCHAR(255),
                         razao_social VARCHAR(255),
                         telefone_principal VARCHAR(255)
);

-- Tabela categoria_despesa
CREATE TABLE categoria_despesa (
                                   id INT PRIMARY KEY AUTO_INCREMENT,
                                   descricao VARCHAR(255),
                                   nome VARCHAR(255)
);

-- Tabela categoria_servico
CREATE TABLE categoria_servico (
                                   id INT PRIMARY KEY AUTO_INCREMENT,
                                   descricao VARCHAR(255),
                                   nome VARCHAR(255)
);

-- Tabela cliente
CREATE TABLE cliente (
                         bit_status INT,
                         empresa_id INT,
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         dt_criacao DATETIME,
                         email VARCHAR(255),
                         nome VARCHAR(255),
                         sobrenome VARCHAR(255),
                         telefone VARCHAR(255),
                         FOREIGN KEY (empresa_id) REFERENCES empresa(id)
);

-- Tabela despesa
CREATE TABLE despesa (
                         bit_status INT,
                         categoria_despesa_id INT,
                         empresa_id INT,
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         valor FLOAT,
                         dt_criacao DATETIME,
                         descricao VARCHAR(255),
                         nome VARCHAR(255),
                         FOREIGN KEY (categoria_despesa_id) REFERENCES categoria_despesa(id),
                         FOREIGN KEY (empresa_id) REFERENCES empresa(id)
);

-- Tabela endereco
CREATE TABLE endereco (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          empresa_id INT UNIQUE,
                          bairro VARCHAR(255),
                          cep VARCHAR(255),
                          complemento VARCHAR(255),
                          localidade VARCHAR(255),
                          logradouro VARCHAR(255),
                          numero VARCHAR(255),
                          uf VARCHAR(255),
                          FOREIGN KEY (empresa_id) REFERENCES empresa(id)
);

-- Tabela funcionario
CREATE TABLE funcionario (
                             bit_status INT,
                             empresa_id INT,
                             id INT PRIMARY KEY AUTO_INCREMENT,
                             dt_criacao DATETIME,
                             email VARCHAR(255),
                             nome VARCHAR(255),
                             perfil_acesso VARCHAR(255),
                             senha VARCHAR(255),
                             telefone VARCHAR(255),
                             FOREIGN KEY (empresa_id) REFERENCES empresa(id)
);

-- Tabela horario_funcionamento
CREATE TABLE horario_funcionamento (
                                       id INT PRIMARY KEY AUTO_INCREMENT,
                                       cod_dia_semana INT,
                                       empresa_id INT,
                                       fim TIME,
                                       inicio TIME,
                                       status INT,
                                       dia_semana VARCHAR(255),
                                       FOREIGN KEY (empresa_id) REFERENCES empresa(id)
);

-- Tabela servico
CREATE TABLE servico (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         categoria_id INT,
                         nome VARCHAR(255),
                         FOREIGN KEY (categoria_id) REFERENCES categoria_servico(id)
);

-- Tabela servico_preco
CREATE TABLE servico_preco (
                               bit_status INT,
                               comissao FLOAT,
                               duracao INT,
                               empresa_id INT,
                               id INT PRIMARY KEY AUTO_INCREMENT,
                               preco FLOAT,
                               servico_id INT,
                               categoria VARCHAR(255),
                               descricao VARCHAR(255),
                               FOREIGN KEY (empresa_id) REFERENCES empresa(id),
                               FOREIGN KEY (servico_id) REFERENCES servico(id)
);

-- Tabela agendamento
CREATE TABLE agendamento (
                             bit_status INT,
                             cliente_id INT,
                             dia DATE,
                             funcionario_id INT,
                             horario TIME,
                             id INT PRIMARY KEY AUTO_INCREMENT,
                             servico_preco_id INT,
                             dt_hora DATETIME,
                             metodo_pagamento VARCHAR(255),
                             FOREIGN KEY (cliente_id) REFERENCES cliente(id),
                             FOREIGN KEY (funcionario_id) REFERENCES funcionario(id),
                             FOREIGN KEY (servico_preco_id) REFERENCES servico_preco(id)
);

-- Tabela chat
CREATE TABLE chat (
                      id INT PRIMARY KEY AUTO_INCREMENT,
                      fk_agendamento INT,
                      fk_cliente INT,
                      fk_empresa INT,
                      fk_funcionario INT,
                      dt_envio DATETIME,
                      mensagem VARCHAR(255),
                      FOREIGN KEY (fk_agendamento) REFERENCES agendamento(id),
                      FOREIGN KEY (fk_cliente) REFERENCES cliente(id),
                      FOREIGN KEY (fk_empresa) REFERENCES empresa(id),
                      FOREIGN KEY (fk_funcionario) REFERENCES funcionario(id)
);

-- Inserção de dados

-- Inserindo dados na tabela empresa
INSERT INTO empresa (intervalo_atendimento, dt_criacao, cnpj, email_principal, razao_social, telefone_principal)
VALUES (30, NOW(), '12345678000195', 'empresa@exemplo.com', 'Empresa Exemplo', '11999999999');

-- Inserindo dados na tabela categoria_despesa
INSERT INTO categoria_despesa (descricao, nome)
VALUES ('Despesas gerais', 'Despesa Padrão');

-- Inserindo dados na tabela categoria_servico
INSERT INTO categoria_servico (descricao, nome)
VALUES ('Serviço padrão', 'Serviço Exemplo');

-- Inserindo dados na tabela cliente
INSERT INTO cliente (bit_status, empresa_id, dt_criacao, email, nome, sobrenome, telefone)
VALUES (1, 1, NOW(), 'cliente@exemplo.com', 'João', 'Silva', '11988888888');

-- Inserindo dados na tabela despesa
INSERT INTO despesa (bit_status, categoria_despesa_id, empresa_id, valor, dt_criacao, descricao, nome)
VALUES (1, 1, 1, 500.00, NOW(), 'Compra de materiais', 'Material Escritório');

-- Inserindo dados na tabela funcionario
INSERT INTO funcionario (bit_status, empresa_id, dt_criacao, email, nome, perfil_acesso, senha, telefone)
VALUES (1, 1, NOW(), 'funcionario@exemplo.com', 'Maria', 'Admin', 'senha123', '11977777777');

-- Inserindo dados na tabela servico
INSERT INTO servico (categoria_id, nome)
VALUES (1, 'Corte de Cabelo');

-- Inserindo dados na tabela servico_preco
INSERT INTO servico_preco (bit_status, comissao, duracao, empresa_id, preco, servico_id, categoria, descricao)
VALUES (1, 10.5, 60, 1, 100.00, 1, 'Corte', 'Corte de cabelo masculino');

-- Inserindo dados na tabela agendamento
INSERT INTO agendamento (bit_status, cliente_id, dia, funcionario_id, horario, servico_preco_id, dt_hora, metodo_pagamento)
VALUES (1, 1, '2024-10-10', 1, '14:00:00', 1, NOW(), 'Cartão de Crédito');

-- Inserindo dados na tabela chat
INSERT INTO chat (fk_agendamento, fk_cliente, fk_empresa, fk_funcionario, dt_envio, mensagem)
VALUES (1, 1, 1, 1, NOW(), 'Mensagem de exemplo no chat');
