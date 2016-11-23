import static java.lang.System.out;
// recurso compartilhado
class Vetor{
   private int[] v;
   private int tam;

   Vetor(int tam){
      v = new int[tam];
      this.tam = tam;
   }

   void inicializa(int primeiro){
      int i;
      for(i = 0; i < this.tam; i++){
         v[i] = primeiro;
         primeiro++;
      }
   }

   void printaVetor(){
      int i;
      for(i = 0; i < this.tam; i++){
         System.out.println(v[i]);
      }

   }

   void alteraElemento(int posicao, int novoElem){
      if(posicao >= tam)
         System.out.println("Out of range");
      else{
         v[posicao] = novoElem;        
      }
   }

   int getTamVetor(){
      return this.tam;
   }

   int getElemento(int posicao){
         return v[posicao];     
   }
   void setElemento(int posicao, int valor){
         v[posicao] = valor;
   }
}

class T extends Thread{
   int id;
   Vetor a, b, c;
   int tamanho, nthreads;

   public T(int tid, Vetor a, Vetor b, Vetor c, int N) { 
      this.id = tid; 
      this.a = a;
      this.b = b;
      this.c = c;
      this.tamanho = a.getTamVetor();
      this.nthreads = N;
   }

   //metodo main da thread
   public void run() {
      int i, valor;
      System.out.println("Thread " + this.id + " iniciou!");
      for(i = this.id; i < this.tamanho; i += this.nthreads){
         valor = this.a.getElemento(i) + this.b.getElemento(i);
         this.c.setElemento(i, valor);
      }
      System.out.println("Thread " + this.id + " terminou!"); 
   }

}

   //classe da aplicacao
class Aplicacao {
   static final int tamanhoVetor = 10;
   static final int NTHREADS = 10;

   public static void main (String[] args) {
      
      Thread[] threads = new Thread[NTHREADS];

      Vetor a = new Vetor(tamanhoVetor);
      Vetor b = new Vetor(tamanhoVetor);
      Vetor c = new Vetor(tamanhoVetor);

      int i;

      a.inicializa(2);
      b.inicializa(2);

      //cria as threads da aplicacao
      for (i=0; i<threads.length; i++) {
         threads[i] = new T(i, a, b, c, NTHREADS);
      }

      //inicia as threads
      for (i=0; i<threads.length; i++) {
         threads[i].start();
      }

      //espera pelo termino de todas as threads
      for (i=0; i<threads.length; i++) {
         try { threads[i].join(); } catch (InterruptedException e) { return; }
      }

      c.printaVetor();

   
      

   }
}