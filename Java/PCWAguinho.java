/* Disciplina: Computacao Concorrente */
/* Prof.: Silvana Rossetto */
/* Laboratório: 12 */
/* Codigo: Produtor/consumidor em Java */
/* -------------------------------------------------------------------*/
// Monitor
class Buffer {
  static final int N = 10;    //tamanho do buffer
  private int[] buffer = new int[N];  //reserva espaco para o buffer
  private int count=0, in=0, out=0;   //variaveis compartilhadas

  // Construtor
  Buffer() {
    for (int i=0;i<N;i++)  buffer[i] = -1;
  } // inicia o buffer

  // Insere um item
  public synchronized void Insere (int item) {
    try {
        while(count == N){
                System.out.println("Buffer cheio, bloqueando thread produtora "+item);
                this.wait();
                System.out.println("Buffer disponivel, liberando uma thread produtora..... "+item);
        }
                buffer[in] = item;
                System.out.println("Item produzido == "+buffer[in]);
                in = (in + 1) % N;
                count++;
                this.notify();


    } catch (InterruptedException e) { }
  }

  // Remove um item
  public synchronized int Remove (int id) {
   int aux;
   try {
           while(count == 0){
                   System.out.println("Buffer vazio, bloquendo thread consumidora "+id);
                   this.wait();
                   System.out.println("Posição liberada para consumo, liberando thread consumidora..... "+id);
           }

                   aux = buffer[out];
                   System.out.println("Elemento consumido == "+ aux);
                   out = (out + 1) % N;
                   count--;
                 this.notify();

           return aux;
   } catch (InterruptedException e) { System.out.println("entrou no catch");return -1;}
  }
}

//--------------------------------------------------------
// Consumidor
class Consumidor extends Thread {
  int id;
  int delay;
  Buffer buffer;

  // Construtor
  Consumidor (int id, int delayTime, Buffer b) {
    this.id = id;
    this.delay = delayTime;
    this.buffer = b;
  }

  // Método executado pela thread
  public void run () {
    int item;
    try {
      for (;;) {
        item = this.buffer.Remove(this.id);
        sleep(this.delay); //...simula o tempo para fazer algo com o item retirado
        //System.out.println(item);
      }
    } catch (InterruptedException e) { return; }
  }
}

//--------------------------------------------------------
// Produtor
class Produtor extends Thread {
  int id;
  int delay;
  Buffer buffer;

  // Construtor
  Produtor (int id, int delayTime, Buffer b) {
    this.id = id;
    this.delay = delayTime;
    this.buffer = b;
  }

  // Método executado pelo thread
  public void run () {
    try {
      for (;;) {
        this.buffer.Insere(this.id); //simplificacao: insere o proprio ID
        sleep(this.delay);
      }
    } catch (InterruptedException e) { return; }
  }
}

//--------------------------------------------------------
// Classe principal
class PC {
  static final int P = 10;
  static final int C = 10;

  public static void main (String[] args) {
    int i;
    Buffer buffer = new Buffer();      // Monitor
    Consumidor[] cons = new Consumidor[C];   // Consumidores
    Produtor[] prod = new Produtor[P];       // Produtores

    for (i=0; i<C; i++) {
       cons[i] = new Consumidor(i+1, 1000, buffer);
       cons[i].start();
    }
    for (i=0; i<P; i++) {
       prod[i] = new Produtor(i+1, 1000, buffer);
       prod[i].start();
    }
  }
}
