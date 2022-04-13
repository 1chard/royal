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
    saldo DECIMAL(14 , 2 ) NOT NULL,
    UNIQUE INDEX (idUsuario)
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

-- criação da tabela DespesaUsuario
CREATE TABLE IF NOT EXISTS tblDespesaUsuario (
    idDespesaUsuario INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    valor DECIMAL(14 , 2 ) NOT NULL,
    data DATE NOT NULL,
    pendente TINYINT NOT NULL,
    anexo TEXT,
    descricao TEXT NOT NULL,
    observacao TEXT,
    favorito TINYINT NOT NULL,
    iniciorepeticao DATE,
    totalparcelas INT,
    parcelaspagas INT,
    parcelasfixas BOOLEAN,
    nomeFrequencia ENUM('DIAS', 'SEMANAS', 'QUINZENAS', 'MESES', 'BIMESTRES', 'TRIMESTRES', 'SEMESTRES', 'ANOS'),
    idUsuario INT UNSIGNED NOT NULL,
    CONSTRAINT FK_Usuario_DespesaUsuario FOREIGN KEY (idUsuario)
        REFERENCES tblUsuario (idUsuario),
    idCategoria INT UNSIGNED NOT NULL,
    CONSTRAINT FK_Categoria_DespesaUsuario FOREIGN KEY (idCategoria)
        REFERENCES tblCategoria (idCategoria),
    UNIQUE INDEX (idDespesaUsuario)
);

-- criação da tabela ReceitaUsuario
CREATE TABLE IF NOT EXISTS tblReceitaUsuario (
    idReceitaUsuario INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    valor DECIMAL(14 , 2 ) NOT NULL,
    data DATE NOT NULL,
    pendente TINYINT NOT NULL,
    anexo TEXT,
    descricao TEXT NOT NULL,
    observacao TEXT,
    favorito TINYINT NOT NULL,
    iniciorepeticao DATE,
    totalparcelas INT,
    parcelaspagas INT,
    parcelasfixas TINYINT,
    nomeFrequencia ENUM('DIAS', 'SEMANAS', 'QUINZENAS', 'MESES', 'BIMESTRES', 'TRIMESTRES', 'SEMESTRES', 'ANOS'),
    idUsuario INT UNSIGNED NOT NULL,
    CONSTRAINT FK_Usuario_ReceitaUsuario FOREIGN KEY (idUsuario)
        REFERENCES tblUsuario (idUsuario),
    idCategoria INT UNSIGNED NOT NULL,
    CONSTRAINT FK_Categoria_ReceitaUsuario FOREIGN KEY (idCategoria)
        REFERENCES tblCategoria (idCategoria),
    UNIQUE INDEX (idReceitaUsuario)
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

-- criação da tabela DespesaGrupo
CREATE TABLE IF NOT EXISTS tblDespesaGrupo (
    idDespesaGrupo INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    valor DECIMAL(14 , 2 ) NOT NULL,
    data DATE NOT NULL,
    pendente TINYINT NOT NULL,
    anexo TEXT,
    descricao TEXT NOT NULL,
    observacao TEXT,
    favorito TINYINT NOT NULL,
    iniciorepeticao DATE,
    totalparcelas INT,
    parcelaspagas INT,
    parcelasfixas TINYINT,
    nomeFrequencia ENUM('DIAS', 'SEMANAS', 'QUINZENAS', 'MESES', 'BIMESTRES', 'TRIMESTRES', 'SEMESTRES', 'ANOS'),
    idGrupo INT UNSIGNED NOT NULL,
    CONSTRAINT FK_Grupo_DespesaGrupo FOREIGN KEY (idGrupo)
        REFERENCES tblGrupo (idGrupo),
    idCategoria INT UNSIGNED NOT NULL,
    CONSTRAINT FK_Categoria_DespesaGrupo FOREIGN KEY (idCategoria)
        REFERENCES tblCategoria (idCategoria),
    UNIQUE INDEX (idDespesaGrupo)
);

-- criação da tabela ReceitaGrupo
CREATE TABLE IF NOT EXISTS tblReceitaGrupo (
    idReceitaGrupo INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    valor DECIMAL(14 , 2 ) NOT NULL,
    data DATE NOT NULL,
    pendente TINYINT NOT NULL,
    anexo TEXT,
    descricao TEXT NOT NULL,
    observacao TEXT,
    favorito TINYINT NOT NULL,
    iniciorepeticao DATE,
    totalparcelas INT,
    parcelaspagas INT,
    parcelasfixas TINYINT,
    nomeFrequencia ENUM('DIAS', 'SEMANAS', 'QUINZENAS', 'MESES', 'BIMESTRES', 'TRIMESTRES', 'SEMESTRES', 'ANOS'),
    idGrupo INT UNSIGNED NOT NULL,
    CONSTRAINT FK_Grupo_ReceitaGrupo FOREIGN KEY (idGrupo)
        REFERENCES tblGrupo (idGrupo),
    idCategoria INT UNSIGNED NOT NULL,
    CONSTRAINT FK_Categoria_ReceitaGrupo FOREIGN KEY (idCategoria)
        REFERENCES tblCategoria (idCategoria),
    UNIQUE INDEX (idReceitaGrupo)
);

