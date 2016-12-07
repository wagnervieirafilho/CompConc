/*
	Nome: Wagner Vieira
	Matéria: Computação Concorrente
	Prof: Silvana Rossetto

*/

import java.util.Random;
import java.io.IOException;

class Principal{
	static int nASSENTOS;
	static String caminhoArqSaida;
	static int P1= 10;
	static int P2= 10;
	static int P3= 10;
	static int P4= 10;

	public static void main(String[] args){
		int i;
		int id;
		nASSENTOS = Integer.parseInt(args[1]);		//pega o numero de assentos da linha de comando
		caminhoArqSaida = args[0];				//pega o caminho para o arquivo de log

		Buffer buffer = new Buffer(nASSENTOS);		// Monitor
		Assentos assentos = new Assentos(nASSENTOS, buffer);	// assentos

		Consumidor cons; // thread consumidora
    		Produtor1[] prod1 = new Produtor1[P1];	// vetor de threads do produtor 1
    		Produtor2[] prod2 = new Produtor2[P2];	// vetor de threads do produtor 2
    		Produtor3[] prod3= new Produtor3[P3];	// vetor de threads do produtor 3
    		Produtor4[] prod4 = new Produtor4[P4];	// vetor de threads do produtor 4

		

    		for (i=1; i<=P1; i++) {
       			prod1[i-1] = new Produtor1(i,assentos);		// inicia todas as threads produtor 1
       			prod1[i-1].start();
    		}
		
    		id = P1+1;
		for (i=0; i<P2; i++) {
       			prod2[i] = new Produtor2(id,assentos);		// inicia todas as threads produtor 2
       			id++;
       			prod2[i].start();
    		}

    		for (i=0; i<P3; i++) {
       			prod3[i] = new Produtor3(id,assentos);		// inicia todas as threads produtor 3
       			id++;
       			prod3[i].start();
    		}

    		for (i=0; i<P4; i++) {
       			prod4[i] = new Produtor4(id,assentos);		// inicia todas as threads produtor 4
       			id++;
       			prod4[i].start();
    		}

    		cons = new Consumidor(0, assentos, caminhoArqSaida, buffer);		//inicia a thread consumidora
		cons.start();


		for (i=0; i<P1; i++) {
			try{ prod1[i].join(); }catch(InterruptedException e){return;}	// espera as threads terminarem
    		}

    		for (i=0; i<P2; i++) {
			try{ prod2[i].join(); }catch(InterruptedException e){return;}	// espera as threads terminarem
    		}

    		for (i=0; i<P3; i++) {
			try{ prod3[i].join(); }catch(InterruptedException e){return;}	// espera as threads terminarem
    		}

    		for (i=0; i<P4; i++) {
			try{ prod4[i].join(); }catch(InterruptedException e){return;}	// espera as threads terminarem
    		}
		
		buffer.setEstado(true);		// indica para a thread consumidora que as produtoras já terminaram e nada mais será produzido

		try { cons.join(); } catch (InterruptedException e) { return; }	// espera a thread terminar
		
	}

}
