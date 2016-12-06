import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
class Struct{
	public int id;		// variável dentro do buffer que  guarda o ID da thread que realizou a ação
	public int task;		// variável dentro do buffer que  guarda qual tarefa foi realizada pea thread
	public int assento;	// variável dentro do buffer que  guarda qual assento foi alocado ou liberado
	public int[] mapa;	// vetor que guarda o mapa de assentos corrente
	public int n;		// número de assentos
	
	Struct(int n){	// construtor
		mapa = new int[n];	// aloca o numero de assentos  em "mapa"
		this.n = n;		// pega o numero de assentos
	}

	public synchronized  void setItens(int id, int task, int[] mapa, int assento){  // insere os elementos na posição do buffer
		int i;
		this.id = id;		// pega o ID da thread que executou
		this.task = task;	// pega a tarefa que foi executada
		this.assento = assento;	// pega o numero do assento que foi alocado/liberado (0 se tiver apenas visualizado)

		for(i = 0; i < this.n; i++){
			this.mapa[i] = mapa[i];	// copia para o buffer o mapa de assentos atual
		}
	}

	public synchronized void salvaNoArquivo(int n, String caminho){
    		int i;

		try{
			FileWriter arq = new FileWriter(caminho, true);
	    		PrintWriter gravarArq = new PrintWriter(arq);

			gravarArq.printf("%d, %d, %d [", this.id, this.task, this.assento);	// grava no arquivo o ID, task e assento que estão gravado no buffer
			for (i = 0; i < n; i++){							
				gravarArq.printf("%d ", this.mapa[i]);				// grava no arquivo o mapa de assentos que está gravado no buffer
			}						
			gravarArq.printf("]%n");			
			arq.close();
		}catch(IOException e){}
	}

}