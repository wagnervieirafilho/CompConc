/* Disciplina: Computacao Concorrente */
/* Prof.: Silvana Rossetto */
/* Laboratório: 1 */
/* Codigo: "Hello World" usando threads em C e a funcao join*/

#include <stdio.h>
#include <stdlib.h> 
#include <pthread.h>

#define TAMANHO 10
#define NTHREADS  2

//cria a estrutura de dados para armazenar os argumentos da thread
typedef struct {
   int idThread, nThreads;
   //int vetor[TAMANHO];
   int *v;
} t_Args;


//funcao executada pelas threads
void *PrintHello (void *arg) {
  t_Args *args = (t_Args *) arg;

  printf("Sou a thread %d de %d threads\n", args->idThread, args->nThreads);
  free(arg);
  int i;
  
 	 if(args->idThread == 0){
 	 	
 	 	for(i = 0 ; i<TAMANHO ; i+=2)
 	 		args->v[i]++;
  	}
  	else{
  		for(i = 1 ; i<TAMANHO ; i+=2)
 	 		args->v[i]++;
  	}
    
  
  
  pthread_exit(NULL);
}

//funcao principal do programa
int main() {

  pthread_t tid_sistema[NTHREADS];
  int t;
  int i;
  int vetor[TAMANHO];
  t_Args *arg; //receberá os argumentos para a thread
  
  
  for(i=0 ; i< TAMANHO ; i++){
  	vetor[i]=0;
  	//arg->vetor[i]=0;
  }

  for(t=0; t<NTHREADS; t++) {

    printf("--Aloca e preenche argumentos para thread %d\n", t);
    arg = malloc(sizeof(t_Args));
    
    if (arg == NULL) {
      printf("--ERRO: malloc()\n"); exit(-1);
    }
    arg->idThread = t; 
    arg->nThreads = NTHREADS; 
    arg->v = vetor;

    printf("--Cria a thread %d\n", t);
    if (pthread_create(&tid_sistema[t], NULL, PrintHello, (void*) arg)) {
      printf("--ERRO: pthread_create()\n"); exit(-1);
    }
  }

  //--espera todas as threads terminarem
  for (t=0; t<NTHREADS; t++) {
    if (pthread_join(tid_sistema[t], NULL)) {
         printf("--ERRO: pthread_join() \n"); exit(-1); 
    } 
  }

  for(t=0; t<TAMANHO; t++) {
  	printf("%d\n", vetor[t]);
  }


  printf("--Thread principal terminou\n");
  pthread_exit(NULL);
}
