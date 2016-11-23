void barreira(int nthreads){
	static int contador = 0;
	pthread_mutex_lock(&mutex);
	contador++;
	if(contador == nthreads){
		pthread_cond_broadcast(&cond);
		contador = 0;
	}
	else{
		pthread_cond_wait(&cond, &mutex);
	}
	pthread_mutex_unlock(&mutex);
}