import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
class Struct{
	public int id;
	public int task;
	public int assento;
	public int[] assentos;

	Struct(){}

	public synchronized  void setItens(int id, int task, int[] mapa, int assento){
		int i;
		this.id = id;
		this.task = task;
		this.assentos = mapa;
		this.assento = assento;
	}

	public synchronized void salvaNoArquivo(int n, String caminho){
    		int i;

		try{
			FileWriter arq = new FileWriter(caminho, true);
	    		PrintWriter gravarArq = new PrintWriter(arq);

			gravarArq.printf("%d, %d, %d [", this.id, this.task, this.assento);
			for (i = 0; i < n; i++){		
				gravarArq.printf("%d ", this.assentos[i]);
			}						
			gravarArq.printf("]%n");			
			arq.close();
		}catch(IOException e){}
	}

}