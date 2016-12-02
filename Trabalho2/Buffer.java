
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

class Buffer{

	static final int N = 5; // tamanho do buffer
	private Struct[] buffer = new Struct[N];  //reserva espaco para o buffer
 	private int count=0, in=0, out=0;   //variaveis compartilhadas
 	
 	int nAssentos;

 	Buffer(int nAssentos){
 		int i;
 		this.nAssentos = nAssentos;
 		for( i= 0; i < N; i++){
 			buffer[i] = new Struct(nAssentos);
 		}
 	}

 	public synchronized void Insere (int id, int task, int[] mapa) {
    		try {
			while(count == N){
			              System.out.println("Buffer cheio, bloqueando thread produtora ");
			              this.wait();
			              System.out.println("Buffer disponivel, liberando uma thread produtora..... ");
			 }
		               buffer[in].setItens(id, task, mapa);  //escreve no buffer o id da thread, qual tarefa realizou e o mapa de assentos
		               in = (in + 1) % N;
		               count++;
		               this.notify();
		}
		catch (InterruptedException e) { }
	}

  	public synchronized void Remove(String caminho) throws IOException{
   		FileWriter arq = new FileWriter(caminho, true);
    		PrintWriter gravarArq = new PrintWriter(arq);

   		int id;
   		int task;
   		int[] map;
   		int i;

   		try {
			while(count == 0){
			       System.out.println("Buffer vazio, bloquendo thread consumidora ");
			       this.wait();
			       System.out.println("Posição liberada para consumo, liberando thread consumidora..... ");
			}

			id = buffer[out].getId(); 		// pega do buffer o id da thread
			task = buffer[out].getTask();		// pega do buffer qual ação foi realizada por esta thread
			map = buffer[out].getMapa();		// pega do buffer o mapa de assentos atual

			gravarArq.printf("%d, %d, [", id, task);	//	|
			for (i = 0; i < this.nAssentos; i++){		//	|
				gravarArq.printf("%d ", map[i]);	//	|--------> grava as informações do arquivo de saída
			}						//	|
			gravarArq.printf("]%n");			//	|
			arq.close();

			out = (out + 1) % N;
			count--;
			this.notify();
		} 
		catch (InterruptedException e) {}
	}	
}