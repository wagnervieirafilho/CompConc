/*
	Nome: Wagner Vieira
	Matéria: Computação Concorrente
	Prof: Silvana Rossetto

*/

import java.util.Random;
import java.io.IOException;

class Principal{
	static int nASSENTOS;
	static final int P = 1;
	static String caminhoArqSaida;

	public static void main(String[] args){

		nASSENTOS = Integer.parseInt(args[1]);		//pega o numero de assentos da linha de comando
		caminhoArqSaida = args[0];				//pega o caminho para o arquivo de log

		Assentos assentos = new Assentos(nASSENTOS);	// assentos
		Buffer buffer = new Buffer(nASSENTOS);		// Monitor

		Consumidor cons;					// Consumidora
		Produtor1 prod1;					// Produtores
		Produtor2 prod2;
		Produtor3 prod3;
		Produtor4 prod4;

		cons = new Consumidor(0, assentos, buffer);		//inicia a thread consumidora
		cons.start();

		prod1 = new Produtor1(1, assentos, buffer);		//inicia a thread que simula um cliente fazendo uma certa série de operações
		prod1.start();

		prod2 = new Produtor2(2, assentos, buffer);		//inicia a thread que simula um cliente fazendo outra certa série de operações
		prod2.start();

		prod3 = new Produtor3(3, assentos, buffer);		//inicia a thread que simula um cliente fazendo outra certa série de operações
		prod3.start();

		prod4 = new Produtor4(4, assentos, buffer);		//inicia a thread que simula um cliente fazendo outra certa série de operações
		prod4.start();


		try { cons.join(); } catch (InterruptedException e) { return; }
		try { prod1.join(); } catch (InterruptedException e) { return; }
		try { prod2.join(); } catch (InterruptedException e) { return; }
		try { prod3.join(); } catch (InterruptedException e) { return; }
		try { prod4.join(); } catch (InterruptedException e) { return; }
	}

}
