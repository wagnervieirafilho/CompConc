class Struct{
	private int id;
	private int task;
	private int[] assentos;

	Struct(int nAssentos){
		int i;
		assentos = new int[nAssentos];
		for(i = 0; i < nAssentos; i++){
			assentos[i] = 0;
		}
		id  = 0;
		task = 0;
	}

	public void setItens(int id, int task, int[] mapa){
		this.id = id;
		this.task = task;
		this.assentos = mapa;
	}

	public int getId(){
		return this.id;
	}
	public int getTask(){
		return this.task;
	}
	public int[] getMapa(){
		return this.assentos;
	}

}