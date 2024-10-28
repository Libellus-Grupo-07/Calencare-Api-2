    INSERT INTO empresa(cnpj, razao_social, email_principal, telefone_principal, dt_criacao, intervalo_atendimento)VALUES ('19.518.895/0001-89', 'Acme Corporation', 'acme@email.com', '(11) 2500-0101', '2023-01-01', 60);
--
    INSERT INTO horario_funcionamento(dia_semana, cod_dia_semana, inicio, fim, status, empresa_id)
    VALUES
        ('SEGUNDA-FEIRA',1, '08:00:00', '18:00:00', 1,1),
        ('TERÇA-FEIRA',2, '08:00:00', '18:00:00', 1,1),
        ('QUARTA-FEIRA',3, '08:00:00', '18:00:00', 1,1),
        ('QUINTA-FEIRA',4, '08:00:00', '18:00:00', 1,1),
        ('SEXTA-FEIRA',5, '08:00:00', '18:00:00', 1,1),
        ('SÁBADO',6, '08:00:00', '12:00:00', 1,1),
        ('DOMINGO',7, NULL, NULL, 0,1);
--

    INSERT INTO cliente(nome, sobrenome, telefone, email, dt_criacao, empresa_id)
    VALUES
        ('João', 'Silva', '(11) 99801-5638', 'joao@example.com', CURRENT_TIMESTAMP, 1),
        ('Maria', 'Santos', '(11) 90182-7891', 'maria@example.com', CURRENT_TIMESTAMP, 1),
        ('Pedro', 'Oliveira', '(11) 99456-3434', 'pedro@example.com', CURRENT_TIMESTAMP, 1),
        ('Ana', 'Lima', '(11) 99456-3435', 'ana@example.com', CURRENT_TIMESTAMP, 1),
        ('Lucas', 'Ferreira', '(11) 99456-3436', 'lucas@example.com', CURRENT_TIMESTAMP, 1);
--
    INSERT INTO categoria_servico(nome, descricao)
    VALUES
    ('Cabelo', 'Corte de cabelo'),
    ('Unha', 'Alongamento das unhas'),
    ('Podologia', 'Tratamento das unhas do pé');
--
    INSERT INTO servico(nome, categoria_id)
    VALUES
        ('Corte de cabelo', 1),
        ('Alongamento de unhas', 2),
        ('Corte unha pé', 2),
        ('Corte unha mão', 2),
        ('Podologia', 3);
--
    INSERT INTO servico_preco(descricao, preco, duracao, comissao, bit_status, empresa_id, servico_id)
    VALUES
        ('Cabelo', 70.00, 90, 00.50, 1, 1, 1),
        ('Unhas', 33.00, 60, 00.50, 1, 1, 2),
        ('podologia', 50.00, 60, 00.60, 1, 1, 5);
--
    INSERT INTO funcionario(nome, telefone, email, senha, perfil_acesso, bit_status, empresa_id)
    VALUES
    ('Keven Histolino', '(11) 99436-6790', 'keven@gmail.com', 'password123','admin', 1, 1),
    ('Keven Histolino SPTECH', '(11) 94869-9486', 'keven.histolino@sptech.school', 'password123','admin', 1, 1),
    ('Jessica Martins', '(11) 99018-6678', 'jessica.martins@sptech.school','password123','admin', 1, 1),
    ('Luma', '(11) 99446-4611', 'luma@gmail.com','password123','admin', 1, 1),
    ('Raquel', '(11) 99446-4611', 'raquel.silva@sptech.school','password123','admin', 1, 1),
    ('Torres', '(11) 99446-4611', 'gustavo.souza@sptech.school','password123','admin', 1, 1),
    ('Vânia', '(11) 9668-4565', 'vania@example.com', 'password123','normal', 1, 1);
--
    INSERT INTO servico_por_funcionario(dt_add, funcionario_id, servico_preco_id, bit_status)
    VALUES
        (CURRENT_TIMESTAMP, 1, 1,1),
        (CURRENT_TIMESTAMP, 1, 2,1),
        (CURRENT_TIMESTAMP, 2, 3,1),
        (CURRENT_TIMESTAMP, 3, 2,1),
        (CURRENT_TIMESTAMP, 4, 1,1),
        (CURRENT_TIMESTAMP, 5, 1,1);

--
    -- Insira agendamentos com diferentes status, datas e associações a clientes, funcionários e serviços
    INSERT INTO agendamento(dt_hora, bit_status, cliente_id, funcionario_id, servico_preco_id)
    VALUES
        ('2024-07-01 09:00:00', 2, 1, 1, 1), -- Pendente
        ('2024-07-02 10:00:00', 3, 2, 1, 2), -- Cancelado
        ('2024-07-03 11:00:00', 5, 3, 2, 3), -- Finalizado
        ('2024-07-04 12:00:00', 2, 4, 2, 1), -- Pendente
        ('2024-07-05 13:00:00', 3, 5, 3, 2), -- Cancelado
        ('2024-07-06 14:00:00', 5, 1, 1, 3), -- Finalizado
        ('2024-07-07 15:00:00', 2, 2, 2, 1), -- Pendente
        ('2024-07-08 16:00:00', 3, 3, 3, 2), -- Cancelado
        ('2024-07-09 17:00:00', 5, 4, 1, 3), -- Finalizado
        ('2024-07-10 18:00:00', 2, 5, 2, 1), -- Pendente
        ('2024-07-11 19:00:00', 3, 1, 3, 2), -- Cancelado
        ('2024-07-12 20:00:00', 5, 2, 1, 3), -- Finalizado
        ('2024-07-13 21:00:00', 2, 3, 2, 1), -- Pendente
        ('2024-07-14 22:00:00', 3, 4, 3, 2), -- Cancelado
        ('2024-07-15 23:00:00', 5, 5, 1, 3); -- Finalizado
--
    INSERT INTO agendamento(dt_hora, bit_status, cliente_id, funcionario_id, servico_preco_id)
    VALUES

        ('2024-06-13 09:00:00', 2, 1, 1, 1), -- Pendente
        ('2024-06-13 10:00:00', 3, 2, 1, 2), -- Cancelado
        ('2024-06-13 11:00:00', 5, 3, 2, 3), -- Finalizado
        ('2024-06-13 12:00:00', 2, 4, 2, 1), -- Pendente
        ('2024-06-14 13:00:00', 3, 5, 3, 2), -- Cancelado
        ('2024-06-14 14:00:00', 5, 1, 1, 3), -- Finalizado
        ('2024-06-13 15:00:00', 2, 2, 2, 1), -- Pendente
        ('2024-06-13 16:00:00', 3, 3, 3, 2), -- Cancelado
        ('2024-06-13 17:00:00', 5, 4, 1, 3), -- Finalizado
        ('2024-06-13 18:00:00', 2, 5, 2, 1), -- Pendente
        ('2024-06-13 19:00:00', 3, 1, 3, 2), -- Cancelado
        ('2024-06-13 20:00:00', 5, 2, 1, 3), -- Finalizado
        ('2024-06-13 21:00:00', 2, 3, 2, 1), -- Pendente
        ('2024-06-13 22:00:00', 3, 4, 3, 2), -- Cancelado
        ('2024-06-13 23:00:00', 5, 5, 1, 3); -- Finalizado
--
--
--         ('2024-07-08 09:00:00', 1, 1, 1, 1),
--         ('2024-07-08 09:00:00', 1, 2, 2, 2),
--         ('2024-07-08 09:00:00', 1, 3, 4, 3),
--         ('2024-07-08 10:00:00', 1, 4, 2, 3),
--         ('2024-07-08 10:00:00', 1, 5, 3, 2),
--         ('2024-07-08 11:00:00', 1, 6, 3, 1),
--         ('2024-07-08 11:00:00', 1, 7, 4, 2),
--         ('2024-07-08 12:00:00', 1, 8, 1, 3),
--         ('2024-07-08 12:00:00', 1, 9, 5, 2);
--
--     INSERT INTO agendamento(dt_hora, bit_status, cliente_id, funcionario_id, servico_preco_id)
--     VALUES
--
--         ('2024-06-14 22:00:00', 1, 1, 1, 1),
--         ('2024-06-14 22:00:00', 1, 2, 2, 2),
--         ('2024-06-12 22:00:00', 1, 3, 4, 3);


    INSERT INTO categoria_despesa (nome) VALUES
    ('Materiais Descartáveis'),
    ('Produtos de Beleza'),
    ('Utilitários');

    INSERT INTO despesa (nome, observacao, valor, forma_pagamento, dt_pagamento, bit_status, empresa_id, categoria_despesa_id)
    VALUES
        ('Compra de Materiais', 'Compra de materiais descartáveis', 150.00, 'Cartão de Crédito', '2024-06-13 10:00:00', 1, 1, 1),
        ('Compra de Materiais', 'Compra de materiais descartáveis', 150.00, 'Cartão de Crédito', '2024-06-13 10:00:00', 1, 1, 1),
        ('Compra de Materiais', 'Compra de materiais descartáveis', 150.00, 'Cartão de Crédito', '2024-06-12 10:00:00', 1, 1, 1),
        ('Serviços de Limpeza', 'Pagamento de serviços de limpeza', 300.00, 'Transferência Bancária', '2024-06-14 11:00:00', 1, 1, 3),
        ('Compra de Produtos de Beleza', 'Compra de produtos de beleza para revenda', 500.00, 'Dinheiro', '2024-06-14 12:00:00', 1, 1, 2);

-- Inserir registros na tabela categoria_produto
    INSERT INTO categoria_produto (nome)
    VALUES
        ('Capilar'),
        ('Unhas'),
        ('Descartáveis'),
        ('Limpeza');

-- Inserir registros na tabela produto
    INSERT INTO produto (nome, descricao, marca, dt_criacao, bit_status, categoria_produto_id, empresa_id)
    VALUES
        ('Shampoo Cabelos Secos', 'Shampoo para cabelos secos', 'Marca A', CURRENT_TIMESTAMP, 1, 1, 1),
        ('Condicionador Cabelo Seco', 'Condicionador para cabelos secos', 'Marca B', CURRENT_TIMESTAMP, 1, 1, 1),
        ('Esmalte', 'Esmalte vermelho', 'Marca C', CURRENT_TIMESTAMP, 1, 2, 1),
        ('Sabonete', 'Sabonete líquido', 'Marca D', CURRENT_TIMESTAMP, 1, 3, 1),
        ('Detergente', 'Detergente para louças', 'Marca E', CURRENT_TIMESTAMP, 1, 4, 1);

-- Inserir registros na tabela validade
    INSERT INTO validade (dt_validade, dt_criacao, produto_id, bit_status)
    VALUES
        ('2025-05-01 00:00:00', CURRENT_TIMESTAMP, 1, 1),
        ('2025-06-01 00:00:00', CURRENT_TIMESTAMP, 3, 1),
        ('2025-10-01 00:00:00', CURRENT_TIMESTAMP, 3, 1),
        ('2025-07-01 00:00:00', CURRENT_TIMESTAMP, 2, 1),
        (null, CURRENT_TIMESTAMP, 4, 1),
        ('2025-09-27 00:00:00', CURRENT_TIMESTAMP, 5, 1),
        ('1900-01-01 00:00:00', CURRENT_TIMESTAMP, 5, 1);

-- Inserir registros na tabela movimentacaovalidade
    INSERT INTO movimentacao_validade (tipo_movimentacao, dt_criacao, quantidade, bit_status, validade_id)
    VALUES
        (1, CURRENT_TIMESTAMP, 20, 1, 1),
        (1, CURRENT_TIMESTAMP, 20, 1, 2),
        (1, CURRENT_TIMESTAMP, 10, 1, 3),
        (1, CURRENT_TIMESTAMP, -3, 1, 3),
        (1, CURRENT_TIMESTAMP, 20, 1, 4),
        (1, CURRENT_TIMESTAMP, 20, 1, 6),
        (1, CURRENT_TIMESTAMP, 20, 1, 7),
        (1, CURRENT_TIMESTAMP, 50, 1, 5),
        (1, CURRENT_TIMESTAMP, -30, 1, 5),
        (1, CURRENT_TIMESTAMP, -10, 1, 5),
        (1, CURRENT_TIMESTAMP, 20, 1, 5),
        (1, CURRENT_TIMESTAMP, 20, 1, 5);
