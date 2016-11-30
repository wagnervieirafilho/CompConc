// Consumidor
class Consumidor extends Thread {
              int id;
              Assentos a;
              Buffer buffer;

              Consumidor (int id, Assentos a, Buffer b) {
                    this.id = id;
                    this.a = a;
                    this.buffer = b;
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

        // Construtor
        Produtor1 (int id, Assentos a, Buffer b) {
          this.id = id;
          this.a = a;
          this.buffer = b;
        }

        // Método executado pelo thread
        public void run () {
          System.out.println("Ta na produtora "+this.id);
        }
} 

class Produtor2 extends Thread {
        int id;
        Assentos a;
        Buffer buffer;

        // Construtor
        Produtor2 (int id, Assentos a, Buffer b) {
          this.id = id;
          this.a = a;
          this.buffer = b;
        }

        // Método executado pelo thread
        public void run () {
          System.out.println("Ta na produtora "+this.id);
        }
} 

class Produtor3 extends Thread {
        int id;
        Assentos a;
        Buffer buffer;

        // Construtor
        Produtor3 (int id, Assentos a, Buffer b) {
          this.id = id;
          this.a = a;
          this.buffer = b;
        }

        // Método executado pelo thread
        public void run () {
          System.out.println("Ta na produtora "+this.id);
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
          System.out.println("Ta na produtora "+this.id);
        }
} 