
class Buffer{

	static final int N = 10; // tamanho do buffer
	private Struct[] buffer = new Struct[N];  //reserva espaco para o buffer
 	private int count=0, in=0, out=0;   //variaveis compartilhadas

 	Buffer(int nAssentos){
 		int i;
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
		               buffer[in].setItens(id, task, mapa);
		               System.out.println("Item produzido == ");
		               in = (in + 1) % N;
		               count++;
		               this.notify();
		}
		catch (InterruptedException e) { }
	}

  	public synchronized void Remove() {
   		int id;
   		int task;
   		int[] map;

   		try {
			while(count == 0){
			       System.out.println("Buffer vazio, bloquendo thread consumidora ");
			       this.wait();
			       System.out.println("Posição liberada para consumo, liberando thread consumidora..... ");
			}
			id = buffer[out].getId();
			task = buffer[out].getTask();
			map = buffer[out].getMapa();

			System.out.println("Elemento consumido == ");
			out = (out + 1) % N;
			count--;
			this.notify();
		} 
		catch (InterruptedException e) {}
	}


	
}