// Consumidor
class Consumidor extends Thread {
              int id;
              Assentos a;
              Buffer buffer;
              String caminho;

              Consumidor (int id, Assentos a, Buffer b, String caminhoSaida) {
                    this.id = id;
                    this.a = a;
                    this.buffer = b;
                    this.caminho = caminhoSaida;
              }

              // Método executado pela thread
              public void run (){
                System.out.println("Ta na consumidora");
              }
}

// Produtor
class Produtor1 extends Thread {
        int id;
        Assentos a;
        Buffer buffer;
        int t_assento;
        int[] v;

        // Construtor
        Produtor1 (int id, Assentos a, Buffer b) {
          this.id = id;
          this.a = a;
          this.buffer = b;
        }

        // Método executado pelo thread
        public void run () {
          this.a.visualizaAssentos();
          v = this.a.alocaAssentoLivre(this.id);              //aloca assento aleatório e retorna vetor dizendo se foi possível alocar e qual assento foi alocado
          this.a.visualizaAssentos();
       }
} 

class Produtor2 extends Thread {
        int id;
        Assentos a;
        Buffer buffer;
        int t_assento;

        // Construtor
        Produtor2 (int id, Assentos a, Buffer b) {
          this.id = id;
          this.a = a;
          this.buffer = b;
        }

        // Método executado pelo thread
        public void run () {
              this.a.visualizaAssentos();
              this.t_assento = 2;                                                 //assento que vai tentar alocar
              this.a.alocaAssentoDado(t_assento, this.id);    // aloca o assento dado
              this.a.visualizaAssentos();

        }
} 

class Produtor3 extends Thread {
        int id;
        Assentos a;
        Buffer buffer;
        int t_assento;
        int[] v;

        // Construtor
        Produtor3 (int id, Assentos a, Buffer b) {
          this.id = id;
          this.a = a;
          this.buffer = b;
          this.v = new int[2];
        }

        // Método executado pelo thread
        public void run () {
            this.a.visualizaAssentos();
            v = this.a.alocaAssentoLivre(this.id);    //aloca assento aleatório e retorna vetor que diz se foi ou não possivel alocar e qual assento foi alocado
            if(v[0] != 0){                                              // se a alocação de fato ocorreu, t_assento recebe o assento que foi alocado
                t_assento = v[1];
                this.a.visualizaAssentos();
                this.a.liberaAssento(t_assento, this.id); // libera assento que acabou de ser alocado
            }
            this.a.visualizaAssentos();

        }
} 

class Produtor4 extends Thread {
        int id;
        Assentos a;
        Buffer buffer;

        // Construtor
        Produtor4 (int id, Assentos a, Buffer b) {
          this.id = id;
          this.a = a;
          this.buffer = b;
        }

        // Método executado pelo thread
        public void run () {
             this.a.visualizaAssentos();
              this.a.alocaAssentoDado(6,this.id);   //aloca assento 6 para cliente 4
              this.a.alocaAssentoDado(7,this.id);   //aloca assento 7 para cliente 4
              this.a.alocaAssentoDado(8,this.id);   //aloca assento 8 para cliente 4
              this.a.liberaAssento(1, this.id);           // tenta liberar assento que não foi alocado pelo cliente 4, vai dar erro!
              this.a.visualizaAssentos();
            
        }
} 