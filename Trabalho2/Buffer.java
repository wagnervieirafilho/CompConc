import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

class Buffer{

	static final int N = 5; // tamanho do buffer
	private Struct[] buffer = new Struct[N];  //reserva espaco para o buffer
 	static private int count=0, in=0, out=0;   //variaveis compartilhadas
 	
 	int nAssentos;

 	Buffer(int nAssentos){
 		int i;
 		this.nAssentos = nAssentos;
 		for( i= 0; i < N; i++){
 			buffer[i] = new Struct();
 		}
 	}

 	public synchronized void Insere (int id, int task, int[] mapa) {
 		int i;
    		try {
			while(count == N){
			              //System.out.println("Buffer cheio, bloqueando thread produtora ");
			              this.wait();
			              //System.out.println("Buffer disponivel, liberando uma thread produtora..... ");
			 }
			 
			 System.out.print(id+","+task+"[");
			 for (i = 0; i < this.nAssentos; i++){		
					System.out.print(mapa[i]+" ");
				}
				System.out.print("]\n");

		               buffer[in].setItens(id, task, mapa);  //escreve no buffer o id da thread, qual tarefa realizou e o mapa de assentos

		               in = (in + 1) % N;
		               count++;
		               this.notify();
		}
		catch (InterruptedException e) { }
	}

  	public  synchronized void Remove(String caminho, Consumidor c) throws IOException{
   		
   		
	   		try {
	   			
				while(count == 0){
				       //System.out.println("Buffer vazio, bloquendo thread consumidora ");
				       this.wait();
				       //System.out.println("Posição liberada para consumo, liberando thread consumidora..... ");
				}

				buffer[out].salvaNoArquivo(this.nAssentos, caminho);
				out = (out + 1) % N;
				count--;

				this.notify();
			}
			
			catch (InterruptedException e) {}		
	}	
}