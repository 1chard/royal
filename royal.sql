-- Criação de tabelas
drop database if exists royal;
create database IF NOT EXISTS royal;

-- usar a tabela Royal Inance
USE royal;

-- Criação da tabela Usuario
CREATE TABLE IF NOT EXISTS tblUsuario (
    idUsuario INT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
    nome TEXT NOT NULL,
    email VARCHAR(256) NOT NULL UNIQUE,
    senha TEXT NOT NULL,
    foto TEXT NULL DEFAULT NULL,
    duasetapas BOOLEAN NOT NULL DEFAULT FALSE,
    UNIQUE INDEX (idUsuario)
);

CREATE TABLE IF NOT EXISTS autoLogin (
	idAutoLogin int UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
    token text not null,
    idUsuario INT UNSIGNED NOT NULL,
    CONSTRAINT FK_AutoLogin_Recuperacao FOREIGN KEY (idUsuario)
        REFERENCES tblUsuario (idUsuario),
    UNIQUE INDEX (idAutoLogin)
);

-- criação da tabela recuperação
CREATE TABLE IF NOT EXISTS tblRecuperacao (
    idRecuperacao INT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
    codigo INT UNSIGNED NOT NULL,
    data DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    idUsuario INT UNSIGNED NOT NULL,
    CONSTRAINT FK_Usuario_Recuperacao FOREIGN KEY (idUsuario)
        REFERENCES tblUsuario (idUsuario),
    UNIQUE INDEX (idRecuperacao)
);

-- function pra resetar a senha
DELIMITER $$
CREATE function resetar_senha(email varchar(256))
RETURNS INTEGER UNSIGNED 
READS SQL DATA
BEGIN
    DECLARE idusuario int unsigned;
    DECLARE codigo int;
    
    select tblUsuario.idUsuario
    into idusuario
    from tblUsuario
    where tblUsuario.email = email;
    
    if NOT isnull(idusuario) THEN
	SET codigo = floor(rand() * 999999);
	INSERT into tblRecuperacao(codigo, idusuario) values(codigo, idusuario);
    END IF;
    
    RETURN codigo;
END $$
DELIMITER ;

-- function pra somar tempo
DELIMITER $$
create function somar_tempo(data date, soma int, modo varchar(10))
returns date deterministic
BEGIN
	return case(modo)
    when 'DIAS' then date_add(data, interval soma day)
    when 'SEMANAS' then date_add(data, interval soma week)
    when 'QUINZENAS' then date_add(data, interval (soma * 15) day)
    when 'MESES' then date_add(data, interval soma month)
    when 'BIMESTRES' then date_add(data, interval (soma * 2) month)
    when 'TRIMESTRES' then date_add(data, interval (soma * 3) month)
    when 'SEMESTRES' then date_add(data, interval (soma * 6) month)
    when 'ANOS' then date_add(data, interval soma year)
    END;
END $$
DELIMITER ;

-- criação da tabela tipotransferencia
CREATE TABLE IF NOT EXISTS tblTipoTransferencia (
    idTipoTransferencia INT UNSIGNED NOT NULL PRIMARY KEY auto_increment,
    nome VARCHAR(10) NOT NULL unique,
    UNIQUE INDEX (idTipoTransferencia)
);

INSERT INTO tblTipoTransferencia(nome) value("DESPESA");
INSERT INTO tblTipoTransferencia(nome) value("RECEITA");

-- criação da tabela categori
CREATE TABLE IF NOT EXISTS tblCategoria (
    idCategoria INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome varchar(50) NOT NULL unique,
    cor VARCHAR(6) NOT NULL,
    icone TEXT NOT NULL,
    idTipoTransferencia INT UNSIGNED NOT NULL,
    CONSTRAINT FK_TipoTransferencia_Categoria FOREIGN KEY (idTipoTransferencia)
        REFERENCES tblTipoTransferencia (idTipoTransferencia),
    UNIQUE INDEX (idCategoria)
);

INSERT INTO tblCategoria(nome, cor, icone, idTipoTransferencia) VALUES ('Casa', '2D98B3', 'house', (select idTipoTransferencia from tblTipoTransferencia where nome = 'DESPESA'));
INSERT INTO tblCategoria(nome, cor, icone, idTipoTransferencia) VALUES ('Restaurante', 'D11B1B', 'storefront', (select idTipoTransferencia from tblTipoTransferencia where nome = 'DESPESA'));
INSERT INTO tblCategoria(nome, cor, icone, idTipoTransferencia) VALUES ('Serviço', '3C6E10', 'home_repair_service', (select idTipoTransferencia from tblTipoTransferencia where nome = 'DESPESA'));
INSERT INTO tblCategoria(nome, cor, icone, idTipoTransferencia) VALUES ('Alimentação', '6B3528', 'restaurant_menu', (select idTipoTransferencia from tblTipoTransferencia where nome = 'DESPESA'));
INSERT INTO tblCategoria(nome, cor, icone, idTipoTransferencia) VALUES ('Cuidados pessoais', '58C91A', 'volunteer_activism',(select idTipoTransferencia from tblTipoTransferencia where nome = 'DESPESA'));
INSERT INTO tblCategoria(nome, cor, icone, idTipoTransferencia) VALUES ('Empréstimos', '0AF57B', 'real_estate_agent', (select idTipoTransferencia from tblTipoTransferencia where nome = 'RECEITA'));
INSERT INTO tblCategoria(nome, cor, icone, idTipoTransferencia) VALUES ('Educação', 'C88DF2', 'school', (select idTipoTransferencia from tblTipoTransferencia where nome = 'DESPESA'));
INSERT INTO tblCategoria(nome, cor, icone, idTipoTransferencia) VALUES ('Família', 'EB8F05', 'family_restroom', (select idTipoTransferencia from tblTipoTransferencia where nome = 'DESPESA'));
INSERT INTO tblCategoria(nome, cor, icone, idTipoTransferencia) VALUES ('Impostos', 'B84F2C', 'price_change', (select idTipoTransferencia from tblTipoTransferencia where nome = 'DESPESA'));
INSERT INTO tblCategoria(nome, cor, icone, idTipoTransferencia) VALUES ('Investimentos', 'EDDC1F', 'savings', (select idTipoTransferencia from tblTipoTransferencia where nome = 'RECEITA'));
INSERT INTO tblCategoria(nome, cor, icone, idTipoTransferencia) VALUES ('Lazer', 'FF8629', 'attractions', (select idTipoTransferencia from tblTipoTransferencia where nome = 'DESPESA'));
INSERT INTO tblCategoria(nome, cor, icone, idTipoTransferencia) VALUES ('Mercado', '87AD07', 'storefront', (select idTipoTransferencia from tblTipoTransferencia where nome = 'DESPESA'));
INSERT INTO tblCategoria(nome, cor, icone, idTipoTransferencia) VALUES ('Pets', 'BEF01D ', 'pets', (select idTipoTransferencia from tblTipoTransferencia where nome = 'DESPESA'));
INSERT INTO tblCategoria(nome, cor, icone, idTipoTransferencia) VALUES ('Doações', 'FABE32', 'redeem', (select idTipoTransferencia from tblTipoTransferencia where nome = 'DESPESA'));
INSERT INTO tblCategoria(nome, cor, icone, idTipoTransferencia) VALUES ('Roupas', 'D9025F', 'checkroom', (select idTipoTransferencia from tblTipoTransferencia where nome = 'DESPESA'));
INSERT INTO tblCategoria(nome, cor, icone, idTipoTransferencia) VALUES ('Saúde', '029999', 'medication_liquid', (select idTipoTransferencia from tblTipoTransferencia where nome = 'DESPESA'));
INSERT INTO tblCategoria(nome, cor, icone, idTipoTransferencia) VALUES ('Salário', 'A607B8', ' work', (select idTipoTransferencia from tblTipoTransferencia where nome = 'RECEITA'));
INSERT INTO tblCategoria(nome, cor, icone, idTipoTransferencia) VALUES ('Transporte', '322AD1', 'directions_bus', (select idTipoTransferencia from tblTipoTransferencia where nome = 'DESPESA'));
INSERT INTO tblCategoria(nome, cor, icone, idTipoTransferencia) VALUES ('Viagem', '2AB5D1', 'flight', (select idTipoTransferencia from tblTipoTransferencia where nome = 'DESPESA'));
INSERT INTO tblCategoria(nome, cor, icone, idTipoTransferencia) VALUES ('Outras despesas', '919191', 'menu', (select idTipoTransferencia from tblTipoTransferencia where nome = 'DESPESA'));
INSERT INTO tblCategoria(nome, cor, icone, idTipoTransferencia) VALUES ('Outras receitas', '919191', 'menu', (select idTipoTransferencia from tblTipoTransferencia where nome = 'RECEITA'));

-- criação da tabela MetaUsuario
CREATE TABLE IF NOT EXISTS tblMetaUsuario (
    idMetaUsuario INT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
    descricao TEXT NOT NULL,
    valor DECIMAL(14 , 2 ) NOT NULL,
    dataInicio DATE NOT NULL,
    dataFim DATE NOT NULL,
    idTipoTransferencia INT UNSIGNED NOT NULL,
    CONSTRAINT FK_TipoTransferencia_MetaUsuario FOREIGN KEY (idTipoTransferencia)
        REFERENCES tblTipoTransferencia (idTipoTransferencia),
    idUsuario INT UNSIGNED NOT NULL,
    CONSTRAINT FK_Usuario_MetaUsuario FOREIGN KEY (idUsuario)
        REFERENCES tblUsuario (idUsuario),
    idCategoria INT UNSIGNED NOT NULL,
    CONSTRAINT FK_Categoria_MetaUsuario FOREIGN KEY (idCategoria)
        REFERENCES tblCategoria (idCategoria),
    UNIQUE INDEX (idMetaUsuario)
);



-- criação da tabela TransferenciaUsuario
CREATE TABLE IF NOT EXISTS tblTransferenciaUsuario (
    idTransferenciaUsuario INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    valor DECIMAL(14 , 2 ) NOT NULL,
    data DATE NOT NULL,
    anexo TEXT,
    descricao TEXT NOT NULL,
    observacao TEXT,
    favorito BOOLEAN NOT NULL,
    parcelada BOOLEAN NOT NULL,
    fixa BOOLEAN NOT NULL,
    frequencia ENUM('DIAS', 'SEMANAS', 'QUINZENAS', 'MESES', 'BIMESTRES', 'TRIMESTRES', 'SEMESTRES', 'ANOS'),
    parcelas INT UNSIGNED,
    idUsuario INT UNSIGNED NOT NULL,
    CONSTRAINT FK_Usuario_TransferenciaUsuario FOREIGN KEY (idUsuario)
        REFERENCES tblUsuario (idUsuario),
    idCategoria INT UNSIGNED NOT NULL,
    CONSTRAINT FK_Categoria_TransferenciaUsuario FOREIGN KEY (idCategoria)
        REFERENCES tblCategoria (idCategoria),
    UNIQUE INDEX (idTransferenciaUsuario)
);

-- idUsuario = id AND parcelada = false AND valor < 0

CREATE TABLE IF NOT EXISTS tblTransferenciaUsuarioParcela(
    idTransferenciaUsuarioParcela INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    valor DECIMAL(14 , 2 ) NOT NULL,
    data DATE NOT NULL,
    indice int unsigned not null,
    idTransferenciaUsuario INT UNSIGNED NOT NULL,
    CONSTRAINT FK_TransferenciaUsuario_TransferenciaUsuarioParcela FOREIGN KEY (idTransferenciaUsuario)
        REFERENCES tblTransferenciaUsuario (idTransferenciaUsuario),
	idUsuario INT UNSIGNED NOT NULL,
    CONSTRAINT FK_Usuario_TransferenciaUsuarioParcela FOREIGN KEY (idUsuario)
        REFERENCES tblUsuario (idUsuario),
    UNIQUE INDEX (idTransferenciaUsuarioParcela)
);

-- criação da tabela grupo
CREATE TABLE IF NOT EXISTS tblGrupo (
    idGrupo INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
    nome TEXT NOT NULL,
    foto TEXT NULL DEFAULT NULL,
    idCriador INT UNSIGNED NOT NULL,
    CONSTRAINT FK_Usuario_Grupo FOREIGN KEY (idCriador)
        REFERENCES tblUsuario (idUsuario),
    UNIQUE INDEX (idGrupo)
);

-- criação da tabela UsuarioGrupo
CREATE TABLE IF NOT EXISTS tblUsuarioGrupo (
    idUsuarioGrupo INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    saldo DECIMAL(14 , 2 ) NOT NULL,
    idUsuario INT UNSIGNED NOT NULL,
    CONSTRAINT FK_UsuarioGrupo_Usuario FOREIGN KEY (idUsuario)
        REFERENCES tblUsuario (idUsuario),
    idGrupo INT UNSIGNED NOT NULL,
    CONSTRAINT FK_UsuarioGrupo_Grupo FOREIGN KEY (idGrupo)
        REFERENCES tblGrupo (idGrupo),
    UNIQUE INDEX (idUsuarioGrupo)
);

-- criação da tabela Convite
CREATE TABLE IF NOT EXISTS tblConvite (
    idConvite INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    titulo TEXT NOT NULL,
    mensagem TEXT,
    data DATETIME NOT NULL,
    idUsuario INT UNSIGNED NOT NULL,
    CONSTRAINT FK_Convite_Usuario FOREIGN KEY (idUsuario)
        REFERENCES tblUsuario (idUsuario),
    idGrupo INT UNSIGNED NOT NULL,
    CONSTRAINT FK_Convite_Grupo FOREIGN KEY (idGrupo)
        REFERENCES tblGrupo (idGrupo),
    UNIQUE INDEX (idConvite)
);

-- criação da tabela MetaGrupo
CREATE TABLE IF NOT EXISTS tblMetaGrupo (
    idMetaGrupo INT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
    descricao TEXT NOT NULL,
    valor DECIMAL(14 , 2 ) NOT NULL,
    dataIncio DATE NOT NULL,
    dataFim DATE NOT NULL,
    idTipoTransferencia INT UNSIGNED NOT NULL,
    CONSTRAINT FK_TipoTransferencia_MetaGrupo FOREIGN KEY (idTipoTransferencia)
        REFERENCES tblTipoTransferencia (idTipoTransferencia),
    idGrupo INT UNSIGNED NOT NULL,
    CONSTRAINT FK_Usuario_MetaGrupo FOREIGN KEY (idGrupo)
        REFERENCES tblGrupo (idGrupo),
    idCategoria INT UNSIGNED NOT NULL,
    CONSTRAINT FK_Categoria_MetaGrupo FOREIGN KEY (idCategoria)
        REFERENCES tblCategoria (idCategoria),
    UNIQUE INDEX (idMetaGrupo)
);

-- criação da tabela TransferenciaGrupo
CREATE TABLE IF NOT EXISTS tblTransferenciaGrupo (
    idTransferenciaGrupo INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    valor DECIMAL(14 , 2 ) NOT NULL,
    data DATE NOT NULL,
    anexo TEXT,
    descricao TEXT NOT NULL,
    observacao TEXT,
    favorito BOOLEAN NOT NULL,
    parcelada BOOLEAN NOT NULL,
    fixa BOOLEAN NOT NULL,
    frequencia ENUM('DIAS', 'SEMANAS', 'QUINZENAS', 'MESES', 'BIMESTRES', 'TRIMESTRES', 'SEMESTRES', 'ANOS'),
	parcelas INT UNSIGNED,
    idGrupo INT UNSIGNED NOT NULL,
    CONSTRAINT FK_Grupo_TransferenciaGrupo FOREIGN KEY (idGrupo)
        REFERENCES tblGrupo (idGrupo),
    idCategoria INT UNSIGNED NOT NULL,
    CONSTRAINT FK_Categoria_TransferenciaGrupo FOREIGN KEY (idCategoria)
        REFERENCES tblCategoria (idCategoria),
    UNIQUE INDEX (idTransferenciaGrupo)
);

CREATE TABLE IF NOT EXISTS tblTransferenciaGrupoParcela(
    idTransferenciaGrupoParcela INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    valor DECIMAL(14 , 2 ) NOT NULL,
    data DATE NOT NULL,
    indice int unsigned not null,
    idTransferenciaGrupo INT UNSIGNED NOT NULL,
    CONSTRAINT FK_TransferenciaGrupo_TransferenciaGrupoParcela FOREIGN KEY (idTransferenciaGrupo)
        REFERENCES tblTransferenciaGrupo (idTransferenciaGrupo),
    idGrupo INT UNSIGNED NOT NULL,
    CONSTRAINT FK_Grupo_TransferenciaGrupoParcela FOREIGN KEY (idGrupo)
        REFERENCES tblGrupo (idGrupo),
    UNIQUE INDEX (idTransferenciaGrupoParcela)
);

DELIMITER $$
create procedure desfixar(in idTransferencia int unsigned)
BEGIN
	update tblTransferenciaUsuario set fixa = false, parcelada = true, valor = valor * parcelas where idTransferenciaUsuario = idTransferencia; 
END $$;
DELIMITER ;

-- procedure pra cadastrar transferenciausuario
DELIMITER $$
create procedure inserir_transferencia_usuario(IN valor DECIMAL(14 , 2 ), in data date, in descricao TEXT, IN favorito boolean, IN parcelada boolean, IN fixa boolean, in idusuario int unsigned, in idcategoria int unsigned, in anexo TEXT, in observacao TEXT, in frequencia varchar(10), in parcelas int unsigned)
BEGIN
	declare id int unsigned;
SELECT 
    IFNULL(MAX(idTransferenciaUsuario), 0)
INTO id FROM
    tblTransferenciaUsuario;
	set id = id + 1;
    set parcelada = parcelada OR fixa;
    
	INSERT INTO tblTransferenciaUsuario (valor, data, anexo, descricao, observacao, favorito, parcelada, fixa, frequencia, idUsuario, idCategoria, idTransferenciaUsuario, parcelas) 
    VALUES (valor, data, anexo, descricao, observacao, favorito, parcelada, fixa, frequencia, idUsuario, idCategoria, id, if(parcelada, parcelas, null));

	if fixa then
		begin
			declare parcela_iterator int unsigned default 0;
        
			while data <= now() do
			begin
				insert into tblTransferenciaUsuarioParcela(valor, data, idTransferenciaUsuario, idUsuario, indice) 
                values(
					valor,
					data,
					id,
                    idUsuario,
                    parcela_iterator
                );
                
				set parcela_iterator = parcela_iterator + 1;
				set data = somar_tempo(data, 1, frequencia);
			end;
			end while;
            
            
            update tblTransferenciaUsuario set tblTransferenciaUsuario.parcelas = parcela_iterator where idTransferenciaUsuario = id;
		end;
    else 
		
		if parcelada then
         begin
		declare diferenca int unsigned default (abs(valor * 100) % (parcelas * 100)) / 100;
		declare valorparcela decimal(14, 2);
        declare valordiferenca decimal(14, 2);
        declare iterator int unsigned default 0;
        
        if valor < 0 then
			set valorparcela = -floor((abs(valor) * 100)/ parcelas) / 100;
			set valordiferenca = -0.01;
        else
			set valorparcela = floor((abs(valor) * 100)/ parcelas) / 100;
            set valordiferenca = 0.01;
        end if;
        
        while iterator < parcelas do
        begin
			
			if diferenca > 0 then
				set diferenca = diferenca - 1;
                insert into tblTransferenciaUsuarioParcela(valor, data, idTransferenciaUsuario, idUsuario, indice) 
                values(
					valorparcela + valordiferenca,
					somar_tempo(data, iterator, frequencia),
					id,
                    idUsuario,
                    iterator
                );
			else 
                insert into tblTransferenciaUsuarioParcela(valor, data, idTransferenciaUsuario, idUsuario, indice) 
                values(
					valorparcela,
					somar_tempo(data, iterator, frequencia),
					id,
                    idUsuario,
                    iterator
                );
            end if;
            
            set iterator = iterator + 1; 
		end;
        end while;
        
    end;
        
		
		end if;
    end if;

	
END $$
DELIMITER ;

DELIMITER $$
create procedure despesa_geral(in id int unsigned)
BEGIN
	SELECT -ifnull(sum(valor), 0) as valor 
    FROM tblTransferenciaUsuario 
    WHERE idUsuario = id AND valor < 0;
END $$
DELIMITER ;

DELIMITER $$
create procedure despesa_anual(in id int unsigned, in ano int unsigned)
BEGIN
	declare retorno DECIMAL(14 , 2 );

	 SELECT ifnull(sum(valor), 0)
     INTO retorno
     FROM tblTransferenciaUsuario 
     WHERE idUsuario = id AND parcelada = false AND valor < 0 AND year(data) = ano;

    select -(retorno + ifnull(sum(valor), 0)) as valor
    from tblTransferenciaUsuarioParcela
    where idUsuario = id AND valor < 0 AND year(data) = ano ;
END $$
DELIMITER ;

DELIMITER $$
create procedure despesa_mensal(in id int unsigned, in ano int unsigned, in mes int unsigned)
BEGIN
	declare retorno DECIMAL(14 , 2 );

	 SELECT ifnull(sum(valor), 0)
     INTO retorno
     FROM tblTransferenciaUsuario 
     WHERE idUsuario = id AND parcelada = false AND valor < 0 AND year(data) = ano AND month(data) = mes;

    select -(retorno + ifnull(sum(tblTransferenciaUsuarioParcela.valor), 0)) as valor
    from tblTransferenciaUsuarioParcela
    where idUsuario = id AND valor < 0 AND year(data) = ano AND month(tblTransferenciaUsuarioParcela.data) = mes;
END $$
DELIMITER ;

DELIMITER $$
create procedure receita_geral(in id int unsigned)
BEGIN
	SELECT ifnull(sum(valor), 0) as valor
    FROM tblTransferenciaUsuario
    WHERE idusuario = id AND valor > 0;
END $$
DELIMITER ;

DELIMITER $$
create procedure receita_anual(in id int unsigned, in ano int unsigned)
BEGIN
	declare retorno DECIMAL(14 , 2 );

	 SELECT ifnull(sum(valor), 0)
     INTO retorno
     FROM tblTransferenciaUsuario 
     WHERE idUsuario = id AND parcelada = false AND valor > 0 AND year(data) = ano;

    select retorno + ifnull(sum(tblTransferenciaUsuarioParcela.valor), 0) as valor
    from tblTransferenciaUsuarioParcela
    where idUsuario = id AND valor > 0 AND year(data) = ano;
END $$
DELIMITER ;

DELIMITER $$
create procedure receita_mensal(in id int unsigned, in ano int unsigned, in mes int unsigned)
BEGIN
	declare retorno DECIMAL(14 , 2 );

	 SELECT ifnull(sum(valor), 0)
     INTO retorno
     FROM tblTransferenciaUsuario 
     WHERE idUsuario = id AND parcelada = false AND valor > 0 AND year(data) = ano AND month(data) = mes;

    select retorno + ifnull(sum(tblTransferenciaUsuarioParcela.valor), 0) as valor
    from tblTransferenciaUsuarioParcela
    where idUsuario = id AND valor > 0 AND year(data) = ano AND month(data) = mes;
END $$
DELIMITER ; 

DELIMITER $$
create procedure despesa_liquida_geral(in id int unsigned)
BEGIN
	declare retorno DECIMAL(14 , 2 );

	 SELECT ifnull(sum(valor), 0)
     INTO retorno
     FROM tblTransferenciaUsuario 
     WHERE idUsuario = id AND parcelada = false AND valor < 0 AND data <= now();

    select -(retorno + ifnull(sum(valor), 0)) as valor
    from tblTransferenciaUsuarioParcela
    where idUsuario = id AND valor < 0 AND data <= now();
END $$
DELIMITER ;

DELIMITER $$
create procedure despesa_liquida_anual(in id int unsigned, in ano int unsigned)
BEGIN
	declare retorno DECIMAL(14 , 2 );

	 SELECT ifnull(sum(valor), 0)
     INTO retorno
     FROM tblTransferenciaUsuario 
     WHERE idUsuario = id AND parcelada = false  AND valor < 0 AND data <= now() AND year(data) = ano;

    select -(retorno + ifnull(sum(valor), 0)) as valor
    from tblTransferenciaUsuarioParcela
    where idUsuario = id AND valor < 0 AND data <= now() AND year(data) = ano ;
END $$
DELIMITER ;

DELIMITER $$
create procedure despesa_liquida_mensal(in id int unsigned, in ano int unsigned, in mes int unsigned)
BEGIN
	declare retorno DECIMAL(14 , 2 );

	 SELECT ifnull(sum(valor), 0)
     INTO retorno
     FROM tblTransferenciaUsuario 
     WHERE idUsuario = id AND parcelada = false AND valor < 0 AND data <= now() AND year(data) = ano AND month(data) = mes;

    select -(retorno + ifnull(sum(tblTransferenciaUsuarioParcela.valor), 0)) as valor
    from tblTransferenciaUsuarioParcela
    where idUsuario = id AND valor < 0 AND data <= now() AND year(data) = ano AND month(tblTransferenciaUsuarioParcela.data) = mes;
END $$
DELIMITER ;

DELIMITER $$
create procedure receita_liquida_geral(in id int unsigned)
BEGIN
	declare retorno DECIMAL(14 , 2 );

	 SELECT ifnull(sum(valor), 0)
     INTO retorno
     FROM tblTransferenciaUsuario 
     WHERE idUsuario = id AND parcelada = false AND valor > 0 AND data <= now();

    select retorno + ifnull(sum(tblTransferenciaUsuarioParcela.valor), 0) as valor
    from tblTransferenciaUsuarioParcela
    where idUsuario = id AND valor > 0 AND data <= now();
END $$
DELIMITER ;

DELIMITER $$
create procedure receita_liquida_anual(in id int unsigned, in ano int unsigned)
BEGIN
	declare retorno DECIMAL(14 , 2 );

	 SELECT ifnull(sum(valor), 0)
     INTO retorno
     FROM tblTransferenciaUsuario 
     WHERE idUsuario = id AND parcelada = false AND valor > 0 AND data <= now() AND year(data) = ano;

    select retorno + ifnull(sum(tblTransferenciaUsuarioParcela.valor), 0) as valor
    from tblTransferenciaUsuarioParcela
    where idUsuario = id AND valor > 0 AND data <= now() AND year(data) = ano;
END $$
DELIMITER ;

DELIMITER $$
create procedure receita_liquida_mensal(in id int unsigned, in ano int unsigned, in mes int unsigned)
BEGIN
	declare retorno DECIMAL(14 , 2 );

	 SELECT ifnull(sum(valor), 0)
     INTO retorno
     FROM tblTransferenciaUsuario 
     WHERE idUsuario = id AND parcelada = false AND valor > 0 AND data <= now() AND year(data) = ano AND month(data) = mes;

    select retorno + ifnull(sum(tblTransferenciaUsuarioParcela.valor), 0) as valor
    from tblTransferenciaUsuarioParcela
    where idUsuario = id AND valor > 0 AND data <= now() AND year(data) = ano AND month(data) = mes;
END $$
DELIMITER ;

DELIMITER $$
create procedure saldo_liquido_geral(in id int unsigned)
BEGIN
	declare retorno DECIMAL(14 , 2 );

	 SELECT ifnull(sum(valor), 0)
     INTO retorno
     FROM tblTransferenciaUsuario 
     WHERE idUsuario = id AND parcelada = false AND data <= now();

    select retorno + ifnull(sum(tblTransferenciaUsuarioParcela.valor), 0) as valor
    from tblTransferenciaUsuarioParcela
    where idUsuario = id AND data <= now();
END $$
DELIMITER ;

DELIMITER $$
create procedure saldo_liquido_anual(in id int unsigned, in ano int unsigned)
BEGIN
	declare retorno DECIMAL(14 , 2 );

	 SELECT ifnull(sum(valor), 0)
     INTO retorno
     FROM tblTransferenciaUsuario 
     WHERE idUsuario = id AND parcelada = false AND data <= now() AND year(data) = ano;

    select retorno + ifnull(sum(tblTransferenciaUsuarioParcela.valor), 0) as valor
    from tblTransferenciaUsuarioParcela
    where idUsuario = id AND data <= now() AND year(data) = ano;
END $$
DELIMITER ;

DELIMITER $$
create procedure saldo_liquido_mensal(in id int unsigned, in ano int unsigned, in mes int unsigned)
BEGIN
	declare retorno DECIMAL(14 , 2 );

	 SELECT ifnull(sum(valor), 0)
     INTO retorno
     FROM tblTransferenciaUsuario 
     WHERE idUsuario = id AND parcelada = false AND data <= now() AND year(data) = ano AND month(data) = mes;

    select retorno + ifnull(sum(tblTransferenciaUsuarioParcela.valor), 0) as valor
    from tblTransferenciaUsuarioParcela
    where idUsuario = id AND data <= now() AND year(data) = ano AND month(data) = mes;
END $$
DELIMITER ;

DELIMITER $$
create procedure extrato_geral(in id int unsigned)
BEGIN
	SELECT 
    valor,
    data,
    descricao,
    idcategoria,
    NULL AS indice,
    idTransferenciaUsuario,
    parcelas
FROM
    tblTransferenciaUsuario
WHERE
    idUsuario = id AND parcelada = false 
UNION ALL SELECT 
    tblTransferenciaUsuarioParcela.valor,
    tblTransferenciaUsuarioParcela.data,
    tblTransferenciaUsuario.descricao,
    tblTransferenciaUsuario.idCategoria,
    tblTransferenciaUsuarioParcela.indice,
    tblTransferenciaUsuarioParcela.idTransferenciaUsuario,
    tblTransferenciaUsuario.parcelas
FROM
    tblTransferenciaUsuarioParcela
        INNER JOIN
    tblTransferenciaUsuario ON tblTransferenciaUsuarioParcela.idUsuario = id AND tblTransferenciaUsuario.idTransferenciaUsuario = tblTransferenciaUsuarioParcela.idTransferenciaUsuario; 
END $$
DELIMITER ;

DELIMITER $$
create procedure extrato_anual(in id int unsigned, in ano int unsigned)
BEGIN
	SELECT 
    valor,
    data,
    descricao,
    idcategoria,
    NULL AS indice,
    idTransferenciaUsuario,
    parcelas
FROM
    tblTransferenciaUsuario
WHERE
    idUsuario = id AND parcelada = false AND year(data) = ano
UNION ALL SELECT 
    tblTransferenciaUsuarioParcela.valor,
    tblTransferenciaUsuarioParcela.data,
    tblTransferenciaUsuario.descricao,
    tblTransferenciaUsuario.idCategoria,
    tblTransferenciaUsuarioParcela.indice,
    tblTransferenciaUsuarioParcela.idTransferenciaUsuario,
    tblTransferenciaUsuario.parcelas
FROM
    tblTransferenciaUsuarioParcela
        INNER JOIN
    tblTransferenciaUsuario ON tblTransferenciaUsuarioParcela.idUsuario = id AND year(tblTransferenciaUsuarioParcela.data) = ano AND tblTransferenciaUsuario.idTransferenciaUsuario = tblTransferenciaUsuarioParcela.idTransferenciaUsuario; 
END $$
DELIMITER ;

DELIMITER $$
create procedure extrato_mensal(in id int unsigned, in ano int unsigned, in mes int unsigned)
BEGIN
	SELECT 
    valor,
    data,
    descricao,
    idcategoria,
    NULL AS indice,
    idTransferenciaUsuario,
    parcelas
FROM
    tblTransferenciaUsuario
WHERE
    idUsuario = id AND parcelada = false AND year(data) = ano AND month(data) = mes
UNION ALL SELECT 
    tblTransferenciaUsuarioParcela.valor,
    tblTransferenciaUsuarioParcela.data,
    tblTransferenciaUsuario.descricao,
    tblTransferenciaUsuario.idCategoria,
    tblTransferenciaUsuarioParcela.indice,
    tblTransferenciaUsuarioParcela.idTransferenciaUsuario,
    tblTransferenciaUsuario.parcelas
FROM
    tblTransferenciaUsuarioParcela
        INNER JOIN
    tblTransferenciaUsuario ON tblTransferenciaUsuarioParcela.idUsuario = id AND year(tblTransferenciaUsuarioParcela.data) = ano AND month(tblTransferenciaUsuarioParcela.data) = mes AND tblTransferenciaUsuario.idTransferenciaUsuario = tblTransferenciaUsuarioParcela.idTransferenciaUsuario; 
END $$
DELIMITER ;

DELIMITER $$
create procedure extrato_receita_geral(in id int unsigned)
BEGIN
	SELECT 
    valor,
    data,
    descricao,
    idcategoria,
    NULL AS indice,
    idTransferenciaUsuario,
    parcelas
FROM
    tblTransferenciaUsuario
WHERE
    idUsuario = id AND parcelada = false AND valor > 0
UNION ALL SELECT 
    tblTransferenciaUsuarioParcela.valor,
    tblTransferenciaUsuarioParcela.data,
    tblTransferenciaUsuario.descricao,
    tblTransferenciaUsuario.idCategoria,
    tblTransferenciaUsuarioParcela.indice,
    tblTransferenciaUsuarioParcela.idTransferenciaUsuario,
    tblTransferenciaUsuario.parcelas
FROM
    tblTransferenciaUsuarioParcela
        INNER JOIN
    tblTransferenciaUsuario ON tblTransferenciaUsuarioParcela.idUsuario = id AND tblTransferenciaUsuarioParcela.valor > 0 AND tblTransferenciaUsuario.idTransferenciaUsuario = tblTransferenciaUsuarioParcela.idTransferenciaUsuario; 
END $$
DELIMITER ;

DELIMITER $$
create procedure extrato_receita_anual(in id int unsigned, in ano int unsigned)
BEGIN
	SELECT 
    valor,
    data,
    descricao,
    idcategoria,
    NULL AS indice,
    idTransferenciaUsuario,
    parcelas
FROM
    tblTransferenciaUsuario
WHERE
    idUsuario = id AND parcelada = false AND valor > 0 AND year(data) = ano
UNION ALL SELECT 
    tblTransferenciaUsuarioParcela.valor,
    tblTransferenciaUsuarioParcela.data,
    tblTransferenciaUsuario.descricao,
    tblTransferenciaUsuario.idCategoria,
    tblTransferenciaUsuarioParcela.indice,
    tblTransferenciaUsuarioParcela.idTransferenciaUsuario,
    tblTransferenciaUsuario.parcelas
FROM
    tblTransferenciaUsuarioParcela
        INNER JOIN
    tblTransferenciaUsuario ON tblTransferenciaUsuarioParcela.idUsuario = id AND tblTransferenciaUsuarioParcela.valor > 0 AND year(tblTransferenciaUsuarioParcela.data) = ano AND tblTransferenciaUsuario.idTransferenciaUsuario = tblTransferenciaUsuarioParcela.idTransferenciaUsuario; 
END $$
DELIMITER ;

DELIMITER $$
create procedure extrato_receita_mensal(in id int unsigned, in ano int unsigned, in mes int unsigned)
BEGIN
	SELECT 
    valor,
    data,
    descricao,
    idcategoria,
    NULL AS indice,
    idTransferenciaUsuario,
    parcelas
FROM
    tblTransferenciaUsuario
WHERE
    idUsuario = id AND parcelada = false AND valor > 0 AND year(data) = ano AND month(data) = mes
UNION ALL SELECT 
    tblTransferenciaUsuarioParcela.valor,
    tblTransferenciaUsuarioParcela.data,
    tblTransferenciaUsuario.descricao,
    tblTransferenciaUsuario.idCategoria,
    tblTransferenciaUsuarioParcela.indice,
    tblTransferenciaUsuarioParcela.idTransferenciaUsuario,
    tblTransferenciaUsuario.parcelas
FROM
    tblTransferenciaUsuarioParcela
        INNER JOIN
    tblTransferenciaUsuario ON tblTransferenciaUsuarioParcela.idUsuario = id AND tblTransferenciaUsuarioParcela.valor > 0 AND year(tblTransferenciaUsuarioParcela.data) = ano AND month(tblTransferenciaUsuarioParcela.data) = mes AND tblTransferenciaUsuario.idTransferenciaUsuario = tblTransferenciaUsuarioParcela.idTransferenciaUsuario; 
END $$
DELIMITER ;


DELIMITER $$
create procedure extrato_despesa_geral(in id int unsigned)
BEGIN
	SELECT 
    valor,
    data,
    descricao,
    idcategoria,
    NULL AS indice,
    idTransferenciaUsuario,
    parcelas
FROM
    tblTransferenciaUsuario
WHERE
    idUsuario = id AND parcelada = false AND valor < 0
UNION ALL SELECT 
    tblTransferenciaUsuarioParcela.valor,
    tblTransferenciaUsuarioParcela.data,
    tblTransferenciaUsuario.descricao,
    tblTransferenciaUsuario.idCategoria,
    tblTransferenciaUsuarioParcela.indice,
    tblTransferenciaUsuarioParcela.idTransferenciaUsuario,
    tblTransferenciaUsuario.parcelas
FROM
    tblTransferenciaUsuarioParcela
        INNER JOIN
    tblTransferenciaUsuario ON tblTransferenciaUsuarioParcela.idUsuario = id AND tblTransferenciaUsuarioParcela.valor < 0 AND tblTransferenciaUsuario.idTransferenciaUsuario = tblTransferenciaUsuarioParcela.idTransferenciaUsuario; 
END $$
DELIMITER ;

DELIMITER $$
create procedure extrato_despesa_anual(in id int unsigned, in ano int unsigned)
BEGIN
	SELECT 
    valor,
    data,
    descricao,
    idcategoria,
    NULL AS indice,
    idTransferenciaUsuario,
    parcelas
FROM
    tblTransferenciaUsuario
WHERE
    idUsuario = id AND parcelada = false AND valor < 0 AND year(data) = ano
UNION ALL SELECT 
    tblTransferenciaUsuarioParcela.valor,
    tblTransferenciaUsuarioParcela.data,
    tblTransferenciaUsuario.descricao,
    tblTransferenciaUsuario.idCategoria,
    tblTransferenciaUsuarioParcela.indice,
    tblTransferenciaUsuarioParcela.idTransferenciaUsuario,
    tblTransferenciaUsuario.parcelas
FROM
    tblTransferenciaUsuarioParcela
        INNER JOIN
    tblTransferenciaUsuario ON tblTransferenciaUsuarioParcela.idUsuario = id AND tblTransferenciaUsuarioParcela.valor < 0 AND year(tblTransferenciaUsuarioParcela.data) = ano AND tblTransferenciaUsuario.idTransferenciaUsuario = tblTransferenciaUsuarioParcela.idTransferenciaUsuario; 
END $$
DELIMITER ;

DELIMITER $$
create procedure extrato_despesa_mensal(in id int unsigned, in ano int unsigned, in mes int unsigned)
BEGIN
	SELECT 
    valor,
    data,
    descricao,
    idcategoria,
    NULL AS indice,
    idTransferenciaUsuario,
    parcelas
FROM
    tblTransferenciaUsuario
WHERE
    idUsuario = id AND parcelada = false AND valor < 0 AND year(data) = ano AND month(data) = mes
UNION ALL SELECT 
    tblTransferenciaUsuarioParcela.valor,
    tblTransferenciaUsuarioParcela.data,
    tblTransferenciaUsuario.descricao,
    tblTransferenciaUsuario.idCategoria,
    tblTransferenciaUsuarioParcela.indice,
    tblTransferenciaUsuarioParcela.idTransferenciaUsuario,
    tblTransferenciaUsuario.parcelas
FROM
    tblTransferenciaUsuarioParcela
        INNER JOIN
    tblTransferenciaUsuario ON tblTransferenciaUsuarioParcela.idUsuario = id AND tblTransferenciaUsuarioParcela.valor < 0 AND year(tblTransferenciaUsuarioParcela.data) = ano AND month(tblTransferenciaUsuarioParcela.data) = mes AND tblTransferenciaUsuario.idTransferenciaUsuario = tblTransferenciaUsuarioParcela.idTransferenciaUsuario; 
END $$
DELIMITER ;

DELIMITER $$
create procedure atualizar_parcelas_fixas(in id int unsigned)
begin
    
	declare var_idTransferenciaUsuario INT UNSIGNED;
    declare var_valor DECIMAL(14 , 2 );
    declare var_data DATE;
    declare var_frequencia VARCHAR(10);
    declare var_parcelas INT UNSIGNED;
    
    declare parcela_iterator INT UNSIGNED;
    
    
	DECLARE fim BOOLEAN DEFAULT FALSE;
    
	DECLARE cursor_teste CURSOR FOR 
    SELECT idTransferenciaUsuario, valor, data, frequencia, parcelas FROM tblTransferenciaUsuario WHERE idUsuario = id AND fixa = true;

	DECLARE CONTINUE HANDLER FOR NOT FOUND SET fim = TRUE;
  
	OPEN cursor_teste;
    
	loop_teste: LOOP
    
    FETCH cursor_teste INTO var_idTransferenciaUsuario, var_valor, var_data, var_frequencia, var_parcelas;
    
    IF fim THEN
      LEAVE loop_teste;
    END IF; 
    
    set parcela_iterator = var_parcelas;
	set var_data = somar_tempo(var_data, parcela_iterator, var_frequencia);
    
    while var_data <= now() do
    begin
		insert into tblTransferenciaUsuarioParcela(valor, data, idTransferenciaUsuario, idUsuario, indice) 
                values(
					var_valor,
					var_data,
					var_idTransferenciaUsuario,
                    id,
                    parcela_iterator
                );
                
		set parcela_iterator = parcela_iterator + 1;
        set var_data = somar_tempo(var_data, 1, var_frequencia);
    end;
    end while;
    
    if(parcela_iterator > var_parcelas) then
		update tblTransferenciaUsuario set parcelas = parcela_iterator where idTransferenciaUsuario = var_idTransferenciaUsuario;
    end if;
    
  END LOOP loop_teste;
  CLOSE cursor_teste;
end $$
DELIMITER ;
