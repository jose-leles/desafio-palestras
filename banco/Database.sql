SET NOCOUNT ON
GO
USE Master
GO
PRINT 'Criando o banco de dados'
GO
IF NOT EXISTS(SELECT * FROM Sys.databases WHERE name = 'ProvaMobileComUsuario')
BEGIN
	CREATE DATABASE ProvaMobileComUsuario
END
GO
USE ProvaMobileComUsuario
GO
PRINT 'Criando as tabelas'
GO
IF NOT EXISTS(SELECT * FROM sys.objects WHERE name = 'Usuario' AND type_desc = 'USER_TABLE')
BEGIN
	CREATE TABLE Usuario (
		Codigo INT NOT NULL IDENTITY PRIMARY KEY,
		Senha VARCHAR(25) NOT NULL,
		Nome VARCHAR(100) NOT NULL,
		Email VARCHAR(255) NOT NULL,
		Empresa VARCHAR(255) NOT NULL,
		Cargo VARCHAR(50) NOT NULL,
		DataHoraCadastro DATETIME NOT NULL
	)
END
GO
IF NOT EXISTS(SELECT * FROM sys.objects WHERE name = 'TipoCategoria' AND type_desc = 'USER_TABLE')
BEGIN
	CREATE TABLE TipoCategoria (
		Codigo INT NOT NULL IDENTITY PRIMARY KEY,
		Descricao VARCHAR(30) NOT NULL
	)
END
GO
IF NOT EXISTS(SELECT * FROM sys.objects WHERE name = 'Palestra' AND type_desc = 'USER_TABLE')
BEGIN
	CREATE TABLE Palestra (
		Codigo INT NOT NULL IDENTITY PRIMARY KEY,
		CodigoTipoCategoria INT NOT NULL,
		Imagem VARCHAR(20) NOT NULL,
		Titulo VARCHAR(255) NOT NULL,
		Palestrante VARCHAR(255) NOT NULL,
		Descricao TEXT NOT NULL,
		DataHoraPalestra DATETIME NOT NULL,
		NumeroVagas INT NOT NULL,
		CONSTRAINT FK_Palestra_TipoCategoria FOREIGN KEY (CodigoTipoCategoria) REFERENCES TipoCategoria (Codigo)
	)
END
GO
IF NOT EXISTS(SELECT * FROM sys.objects WHERE name = 'Inscricao' AND type_desc = 'USER_TABLE')
BEGIN
	CREATE TABLE Inscricao (
		Codigo INT NOT NULL IDENTITY PRIMARY KEY,
		CodigoPalestra INT NOT NULL,
		CodigoUsuario INT NOT NULL,
		DataHoraCadastro DATETIME NOT NULL,
		CONSTRAINT FK_Usuario_Inscricao FOREIGN KEY (CodigoUsuario) REFERENCES Usuario (Codigo),
		CONSTRAINT FK_Inscricao_Palestra FOREIGN KEY (CodigoPalestra) REFERENCES Usuario (Codigo)
	)
END
GO
PRINT 'Criando as VIEWs'
GO
IF NOT EXISTS(SELECT * FROM sys.objects WHERE name = 'vTipoCategoria' AND type_desc = 'VIEW')
BEGIN
	EXEC sp_executesql @statement = N'CREATE VIEW vTipoCategoria AS
		SELECT
			Codigo,
			Descricao
		FROM
			TipoCategoria'
END
GO
IF NOT EXISTS(SELECT * FROM sys.objects WHERE name = 'vPalestra' AND type_desc = 'VIEW')
BEGIN
	EXEC sp_executesql @statement = N'CREATE VIEW vPalestra AS
		SELECT
			Codigo,
			CodigoTipoCategoria,
			Imagem,
			Titulo,
			Palestrante,
			Descricao,
			CONVERT(CHAR(10), DataHoraPalestra, 103) AS ''Data'',
			REPLACE(CONVERT(CHAR(5), DataHoraPalestra, 8), '':'', ''h'') AS ''Hora'',
			NumeroVagas - (SELECT 
								COUNT(*)
							FROM
								Inscricao
							WHERE
								Inscricao.CodigoPalestra = Palestra.Codigo) AS ''QtdVagasDisponiveis''
		FROM
			Palestra'
END
GO
IF NOT EXISTS(SELECT * FROM sys.objects WHERE name = 'vInscricao' AND type_desc = 'VIEW')
BEGIN
	EXEC sp_executesql @statement = N'CREATE VIEW vInscricao AS
		SELECT
			Codigo,
			CodigoPalestra,
			CodigoUsuario,
			CONVERT(CHAR(10), DataHoraCadastro, 103) AS ''DataCadastro'',
			CONVERT(CHAR(8), DataHoraCadastro, 8) AS ''HoraCadastro''
		FROM
			Inscricao'
END
GO
PRINT 'Criando a Stored Procedure'
GO
IF NOT EXISTS(SELECT * FROM sys.objects WHERE name = 'spInscricao' AND type_desc = 'SQL_STORED_PROCEDURE')
BEGIN
	EXEC sp_executesql @statement = N'CREATE PROCEDURE spInscricao 
		@CodigoPalestra INT,		
		@CodigoUsuario INT,
		@Retorno SMALLINT OUTPUT AS
		BEGIN
			IF NOT EXISTS(SELECT * FROM Inscricao WHERE CodigoPalestra = @CodigoPalestra AND CodigoUsuario = @CodigoUsuario)
			BEGIN
				IF EXISTS(SELECT * FROM Palestra WHERE Codigo = @CodigoPalestra)
				BEGIN
					IF (SELECT NumeroVagas FROM Palestra WHERE Codigo = @CodigoPalestra) > (SELECT COUNT(*) FROM Inscricao WHERE CodigoPalestra = @CodigoPalestra)
					BEGIN
						INSERT INTO Inscricao (CodigoPalestra, CodigoUsuario, DataHoraCadastro) VALUES (@CodigoPalestra, @CodigoUsuario, GETDATE())
						SET @Retorno = 0
					END
					ELSE
					BEGIN
						SET @Retorno = 3
					END
				END
				ELSE
				BEGIN
					SET @Retorno = 2
				END
			END
			ELSE
			BEGIN
				SET @Retorno = 1
			END
			RETURN 
		END'
END
GO
GO
IF NOT EXISTS(SELECT * FROM sys.objects WHERE name = 'spCadastrarUsuario' AND type_desc = 'SQL_STORED_PROCEDURE')
BEGIN
	EXEC sp_executesql @statement = N'CREATE PROCEDURE spCadastrarUsuario 
		@Senha VARCHAR(25),
		@Nome VARCHAR(100),
		@Email VARCHAR(255),
		@Empresa VARCHAR(255),
		@Cargo VARCHAR(50),
		@Retorno SMALLINT OUTPUT AS
		BEGIN
			IF NOT EXISTS(SELECT * FROM Usuario WHERE Email = @Email)
			BEGIN
			    INSERT INTO Usuario (Nome,Email,Empresa,Cargo,Senha, DataHoraCadastro) VALUES (@Nome,@Email,@Empresa,@Cargo,@Senha, GETDATE())
				SET @Retorno = 0
			END
			ELSE
			BEGIN
				SET @Retorno = 1
			END
			RETURN 
		END'
END
GO
IF NOT EXISTS(SELECT * FROM sys.objects WHERE name = 'spVerificaUsuario' AND type_desc = 'SQL_STORED_PROCEDURE')
BEGIN
	EXEC sp_executesql @statement = N'CREATE PROCEDURE spVerificaUsuario 
		@Senha VARCHAR(25),
		@Email VARCHAR(255)
		AS BEGIN
			IF EXISTS(SELECT * FROM Usuario WHERE Email = @Email AND Senha = @Senha)
			BEGIN
			    SELECT * FROM Usuario WHERE Email = @Email AND Senha = @Senha
			END
		END'
END
GO
PRINT 'Inserindo os registros'
GO
SET IDENTITY_INSERT TipoCategoria ON
GO
INSERT INTO TipoCategoria (Codigo, Descricao) VALUES (1, 'Gest�o de pessoas'),(2, 'Tecnologia'),(3, 'Empreendedorismo'),(4, 'Cultura Maker')
GO
SET IDENTITY_INSERT TipoCategoria OFF
GO
SET IDENTITY_INSERT Palestra ON
GO
INSERT INTO Palestra (Codigo, CodigoTipoCategoria, Imagem, Titulo, Palestrante, Descricao, DataHoraPalestra, NumeroVagas) VALUES (1, 1, '1.jpg', 'Gest�o de pessoas por compet�ncias', 'Maria do Anjos', 'A n�vel organizacional, o desenvolvimento cont�nuo de distintas formas de atua��o obstaculiza a aprecia��o da import�ncia do investimento em reciclagem t�cnica.', '20190916 19:20:00.000', 30)
INSERT INTO Palestra (Codigo, CodigoTipoCategoria, Imagem, Titulo, Palestrante, Descricao, DataHoraPalestra, NumeroVagas) VALUES (2, 1, '2.jpg', 'Foco nos resultados', 'Fl�vio da Silva', 'O cuidado em identificar pontos cr�ticos na crescente influ�ncia da m�dia � uma das consequ�ncias do impacto na agilidade decis�ria.', '20190917 19:20:00.000', 30)
INSERT INTO Palestra (Codigo, CodigoTipoCategoria, Imagem, Titulo, Palestrante, Descricao, DataHoraPalestra, NumeroVagas) VALUES (3, 2, '3.jpg', 'Tecnologias do futuro', 'Abner Calabrio', 'No entanto, n�o podemos esquecer que a consulta aos diversos militantes aponta para a melhoria dos n�veis de motiva��o departamental.', '20190916 19:20:00.000', 30)
INSERT INTO Palestra (Codigo, CodigoTipoCategoria, Imagem, Titulo, Palestrante, Descricao, DataHoraPalestra, NumeroVagas) VALUES (4, 2, '4.jpg', 'Inova��o com tecnologia', 'Pedro Suarez', 'Por conseguinte, a crescente influ�ncia da m�dia faz parte de um processo de gerenciamento do sistema de participa��o geral.', '20190917 19:20:00.000', 30)
INSERT INTO Palestra (Codigo, CodigoTipoCategoria, Imagem, Titulo, Palestrante, Descricao, DataHoraPalestra, NumeroVagas) VALUES (5, 2, '4.jpg', 'Programa��o 4.0', 'Pedro Suarez', 'Desta maneira, a cont�nua expans�o de nossa atividade garante a contribui��o de um grupo importante na determina��o do or�amento setorial.', '20190918 19:20:00.000', 30)
INSERT INTO Palestra (Codigo, CodigoTipoCategoria, Imagem, Titulo, Palestrante, Descricao, DataHoraPalestra, NumeroVagas) VALUES (6, 3, '5.jpg', 'Cen�rio nacional do empreendedorismo', 'Abilio de Vasconcelos', 'A n�vel organizacional, o surgimento do com�rcio virtual obstaculiza a aprecia��o da import�ncia dos modos de opera��o convencionais.', '20190916 19:20:00.000', 30)
INSERT INTO Palestra (Codigo, CodigoTipoCategoria, Imagem, Titulo, Palestrante, Descricao, DataHoraPalestra, NumeroVagas) VALUES (7, 3, '6.jpg', 'Empreendendo na sua carreira', 'Alcione Mello', 'Pensando mais a longo prazo, a valoriza��o de fatores subjetivos causa impacto indireto na reavalia��o do retorno esperado a longo prazo.', '20190917 19:20:00.000', 30)
INSERT INTO Palestra (Codigo, CodigoTipoCategoria, Imagem, Titulo, Palestrante, Descricao, DataHoraPalestra, NumeroVagas) VALUES (8, 4, '7.jpg', 'Novas ferramentas para os Makers', 'Miguel Sanches', 'Podemos j� vislumbrar o modo pelo qual a complexidade dos estudos efetuados pode nos levar a considerar a reestrutura��o dos modos de opera��o convencionais.', '20190916 19:20:00.000', 30)
INSERT INTO Palestra (Codigo, CodigoTipoCategoria, Imagem, Titulo, Palestrante, Descricao, DataHoraPalestra, NumeroVagas) VALUES (9, 4, '8.jpg', 'Teoria dos materiais', 'Genivaldo Souza', 'odas estas quest�es, devidamente ponderadas, levantam d�vidas sobre se a necessidade de renova��o processual deve passar por modifica��es independentemente do investimento em reciclagem t�cnica.', '20190917 19:20:00.000', 30)
GO
SET IDENTITY_INSERT Palestra OFF