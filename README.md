# Sienge
O exercicio 1 está na pasta /sienge, foi utilizado maven. 

Servidor de Aplicação:
GlassFish 4.1.1 (última versão)

Utilizei uma abordagem mvc, com o mapeamento das entidades em sienge-model, no ejb ficariam as regras de negócio, na camada web as views.
Foi desenvolvido uma Classe para teste das regras propostas (SimulacaoTest.java), para isso utilizei o Arquillian (http://arquillian.org/).
Obs: O arquillian está configurado como glassfish-embedded, para executar a classe de teste é necessário ativar o PROFILE chamado "arquillian-glassfish-embedded" no projeto sienge-ejb.
Antes de iniciar o teste com arquillian garanta que outro glassfish não esteja utilizando as portas padrões, ou seja, interrompa-o para iniciar os testes.

Resultados de testes utilizando profile "arquillian-glassfish-embedded":
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
<pre>
Running com.softplan.testes.SimulacaoTest
PlainTextActionReporterSUCCESSDescription: deploy AdminCommandnull
    [name=test

PER01003: Deployment encountered SQL Exceptions:
	PER01000: Got SQLException executing statement "CREATE TABLE configuracao (id INTEGER NOT NULL, chave VARCHAR(255), valor VARCHAR(255), PRIMARY KEY (id))": java.sql.SQLException: Table/View 'CONFIGURACAO' já existe em Schema 'APP'.
	PER01000: Got SQLException executing statement "CREATE TABLE veiculo (id INTEGER NOT NULL, descricao VARCHAR(255), fator_multiplicador FLOAT, PRIMARY KEY (id))": java.sql.SQLException: Table/View 'VEICULO' já existe em Schema 'APP'.
	PER01000: Got SQLException executing statement "CREATE TABLE simulacao_item (id INTEGER NOT NULL, distancia_pavimento INTEGER, distancia_nao_pavimento INTEGER, peso_carga INTEGER, valor_pavimento FLOAT, valor_sem_pavimento FLOAT, id_simulacao INTEGER, id_veiculo INTEGER, PRIMARY KEY (id))": java.sql.SQLException: Table/View 'SIMULACAO_ITEM' já existe em Schema 'APP'.
	PER01000: Got SQLException executing statement "CREATE TABLE usuario (id INTEGER NOT NULL, email VARCHAR(255), login VARCHAR(255), nome VARCHAR(255), senha VARCHAR(255), PRIMARY KEY (id))": java.sql.SQLException: Table/View 'USUARIO' já existe em Schema 'APP'.
	PER01000: Got SQLException executing statement "CREATE TABLE SIMULACAO (id INTEGER NOT NULL, dh_registro TIMESTAMP, ID_USUARIO INTEGER, PRIMARY KEY (id))": java.sql.SQLException: Table/View 'SIMULACAO' já existe em Schema 'APP'.
	PER01000: Got SQLException executing statement "ALTER TABLE simulacao_item ADD CONSTRAINT smlcaoitemdsmlacao FOREIGN KEY (id_simulacao) REFERENCES SIMULACAO (id)": java.sql.SQLException: Constraint 'SMLCAOITEMDSMLACAO' já existe em Schema 'APP'.
	PER01000: Got SQLException executing statement "ALTER TABLE simulacao_item ADD CONSTRAINT smlacaoitemdviculo FOREIGN KEY (id_veiculo) REFERENCES veiculo (id)": java.sql.SQLException: Constraint 'SMLACAOITEMDVICULO' já existe em Schema 'APP'.
	PER01000: Got SQLException executing statement "ALTER TABLE SIMULACAO ADD CONSTRAINT SIMULACAOIDUSUARIO FOREIGN KEY (ID_USUARIO) REFERENCES usuario (id)": java.sql.SQLException: Constraint 'SIMULACAOIDUSUARIO' já existe em Schema 'APP'.
	PER01000: Got SQLException executing statement "CREATE SEQUENCE SEQ_GEN_SEQUENCE INCREMENT BY 50 START WITH 50": java.sql.SQLException: Sequence 'SEQ_GEN_SEQUENCE' já existe.PlainTextActionReporterSUCCESSNo monitoring data to report.
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 12.62 sec
Results :
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
</pre>


-------------------------------------------------------
O Exercicio 2 está na pasta teste2, com um arquivo leiame.doc.
-------------------------------------------------------

<pre>


Segue a sequência de compilação do aplicativo.

------------------------------------------------------------------------
Reactor Summary:

sienge ............................................ SUCCESS [0.396s]
sienge-model ...................................... SUCCESS [1.828s]
sienge-ejb ........................................ SUCCESS [6.217s]
sienge-web ........................................ SUCCESS [2.018s]
------------------------------------------------------------------------
BUILD SUCCESS
------------------------------------------------------------------------
Total time: 10.714s
Finished at: Wed Mar 22 23:50:39 BRT 2017
Final Memory: 18M/393M
------------------------------------------------------------------------
</pre>
