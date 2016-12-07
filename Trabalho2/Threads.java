import java.io.IOException;
// Consumidor
class Consumidor extends Thread {
              int id;
              Assentos a;
              String caminho;
              int i;
              Buffer buffer;

              Consumidor (int id, Assentos a,  String caminhoSaida, Buffer buffer) {
                    this.id = id;
                    this.a = a;
                    this.caminho = caminhoSaida;
                    this.buffer = buffer;
              }

              // Método executado pela thread
              public void run (){

              try{  
                      while(this.buffer.getEstado()== false)    {   // indica quando as threds produtoras já terminaram de executar
                                this.buffer.Remove(this.caminho);
                        }
              } catch(IOException e){}
                        
              }
}

// Produtor
class Produtor1 extends Thread {    // thread que simula um cliente realizando determinada sequência de ações
        int id;
        Assentos a;
        int t_assento;
        int[] v;

        // Construtor
        Produtor1 (int id, Assentos a) {
          this.id = id;
          this.a = a;
        }

        // Método executado pelo thread
        public void run () {
          this.a.visualizaAssentos(this.id);

          v = this.a.alocaAssentoLivre(this.id);                

          this.a.visualizaAssentos(this.id);
       }
} 

class Produtor2 extends Thread {    // thread que simula um cliente realizando outra sequência de ações
        Assentos a;
        int t_assento;
        int id;

        // Construtor
        Produtor2 (int id, Assentos a) {
          this.id = id;
          this.a = a;
        }

        // Método executado pelo thread
        public void run () {
              this.a.visualizaAssentos(this.id);

              this.t_assento = 2;                                                 //assento que vai tentar alocar

              this.a.alocaAssentoDado(t_assento, this.id);    // aloca o assento dado

              this.a.visualizaAssentos(this.id);

        }
} 

class Produtor3 extends Thread {    // thread que simula um cliente realizando outra sequência de ações
        int id;
        Assentos a;
        int t_assento;
        int[] v;

        // Construtor
        Produtor3 (int id, Assentos a) {
          this.id = id;
          this.a = a;
          this.v = new int[2];
        }

        // Método executado pelo thread
        public void run () {
            this.a.visualizaAssentos(this.id);

            v = this.a.alocaAssentoLivre(this.id);                 //aloca assento aleatório e retorna vetor que diz se foi ou não possivel alocar e qual assento foi alocado

            if(v[0] != 0){                                                                       // se a alocação de fato ocorreu, t_assento recebe o assento que foi alocado
                  t_assento = v[1];

                  this.a.visualizaAssentos(this.id);

                  this.a.liberaAssento(t_assento, this.id); // libera assento que acabou de ser alocado
             }
            this.a.visualizaAssentos(this.id);

        }
} 

class Produtor4 extends Thread {    // thread que simula um cliente realizando outra sequência de ações
        int id;
        Assentos a;

        // Construtor
        Produtor4 (int id, Assentos a) {
          this.id = id;
          this.a = a;
        }

        // Método executado pelo thread
        public void run () {
             this.a.visualizaAssentos(this.id);
             
              this.a.alocaAssentoDado(6,this.id);   //aloca assento 6 para cliente 4
              this.a.alocaAssentoDado(7,this.id);   //aloca assento 7 para cliente 4
              this.a.alocaAssentoDado(8,this.id);   //aloca assento 8 para cliente 4
              this.a.liberaAssento(1, this.id);           // tenta liberar assento que não foi alocado pelo cliente 4, vai dar erro!
              this.a.visualizaAssentos(this.id);              
            
        }
} 