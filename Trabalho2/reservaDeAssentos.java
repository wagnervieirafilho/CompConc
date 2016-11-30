/*
	Nome: Wagner Vieira
	Matéria: Computação Concorrente
	Prof: Silvana Rossetto

*/

import java.util.Random;
import java.io.IOException;

class Principal{
	static int nASSENTOS;

	public static void main(String[] args){
		String caminhoArqSaida;
		caminhoArqSaida = args[0];
		nASSENTOS = Integer.parseInt(args[1]);
		Assentos assentos = new Assentos(nASSENTOS);
		//falta criar as threads clientes e a thread consumidora do buffer de log
		assentos.visualizaAssentos();

	}


}
