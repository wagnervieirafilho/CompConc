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

		Consumidor cons;
    		Produtor1[] prod1 = new Produtor1[P1];
    		Produtor2[] prod2 = new Produtor2[P2];
    		Produtor3[] prod3= new Produtor3[P3];
    		Produtor4[] prod4 = new Produtor4[P4];

		

    		for (i=1; i<=P1; i++) {
       			prod1[i-1] = new Produtor1(i,assentos);
       			prod1[i-1].start();
    		}
		
    		id = P1+1;
		for (i=0; i<P2; i++) {
       			prod2[i] = new Produtor2(id,assentos);
       			id++;
       			prod2[i].start();
    		}

    		for (i=0; i<P3; i++) {
       			prod3[i] = new Produtor3(id,assentos);
       			id++;
       			prod3[i].start();
    		}

    		for (i=0; i<P4; i++) {
       			prod4[i] = new Produtor4(id,assentos);
       			id++;
       			prod4[i].start();
    		}

    		cons = new Consumidor(0, assentos, caminhoArqSaida, buffer);	//inicia a thread consumidora
		cons.start();

		/*prod1 = new Produtor1(1, assentos);		//inicia a thread que simula um cliente fazendo uma certa série de operações
		prod1.start();

		prod2 = new Produtor2(2, assentos);		//inicia a thread que simula um cliente fazendo outra certa série de operações
		prod2.start();

		prod3 = new Produtor3(3, assentos);		//inicia a thread que simula um cliente fazendo outra certa série de operações
		prod3.start();

		prod4 = new Produtor4(4, assentos);		//inicia a thread que simula um cliente fazendo outra certa série de operações
		prod4.start();

		cons = new Consumidor(0, assentos, caminhoArqSaida, buffer);	//inicia a thread consumidora
		cons.start();*/

		/*try { prod1.join(); } catch (InterruptedException e) { return; }	// espera a thread terminar
		try { prod2.join(); } catch (InterruptedException e) { return; }	// espera a thread terminar
		try { prod3.join(); } catch (InterruptedException e) { return; }	// espera a thread terminar
		try { prod4.join(); } catch (InterruptedException e) { return; }	//espera a thread terminar*/

		for (i=0; i<P1; i++) {
			try{ prod1[i].join(); }catch(InterruptedException e){return;}
    		}

    		for (i=0; i<P2; i++) {
			try{ prod2[i].join(); }catch(InterruptedException e){return;}
    		}

    		for (i=0; i<P3; i++) {
			try{ prod3[i].join(); }catch(InterruptedException e){return;}
    		}

    		for (i=0; i<P4; i++) {
			try{ prod4[i].join(); }catch(InterruptedException e){return;}
    		}
		
		buffer.setEstado(true);

		try { cons.join(); } catch (InterruptedException e) { return; }	// espera a thread terminar
		
	}

}
