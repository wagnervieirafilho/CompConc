import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
class Struct{
	private int id;
	private int task;
	private int[] assentos;

	Struct(){}

	public synchronized  void setItens(int id, int task, int[] mapa){
		int i;
		this.id = id;
		this.task = task;
		this.assentos = mapa;
	}

	public synchronized int getId(){
		return this.id;
	}
	public synchronized int getTask(){
		return this.task;
	}
	public synchronized int[] getMapa(){
		return this.assentos;
	}

	public synchronized void salvaNoArquivo(int n, String caminho){
    		int i;

		try{
			FileWriter arq = new FileWriter(caminho, true);
	    		PrintWriter gravarArq = new PrintWriter(arq);
	    		//////////////////////////////////////////////////////////////////////////////////
	    		/*System.out.print(this.id+","+this.task+"[");
			 for (i = 0; i < n; i++){		
					System.out.print(this.assentos[i]+" ");
				}
				System.out.print("]\n");*/
			////////////////////////////////////////////////////////////////////////////

			gravarArq.printf("%d, %d [", this.id, this.task);
			for (i = 0; i < n; i++){		
				gravarArq.printf("%d ", this.assentos[i]);
			}						
			gravarArq.printf("]%n");			
			arq.close();
		}catch(IOException e){}
	}

}