package com.example.CalencareApi.listaObj;

public class Fila<T>{
    private T[] vetor;
    private int inicio;
    private int fim;
    private int tamanho;
    private int capacidade;

    @SuppressWarnings("unchecked")
    public Fila(int capacidade) {
        this.capacidade = capacidade;
        vetor = (T[]) new Object[capacidade];
        tamanho = 0;
        inicio = 0;
        fim = -1;
    }

    public void enqueue(T elemento) {
        if (tamanho == capacidade) {
            throw new IllegalStateException("A fila está cheia");
        }
        fim = (fim + 1) % capacidade;
        vetor[fim] = elemento;
        tamanho++;
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("A fila está vazia");
        }
        T elemento = vetor[inicio];
        vetor[inicio] = null;
        inicio = (inicio + 1) % capacidade;
        tamanho--;
        return elemento;
    }

    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("A fila está vazia");
        }
        return vetor[inicio];
    }

    public boolean isEmpty() {
        return tamanho == 0;
    }

    public int size() {
        return tamanho;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < tamanho; i++) {
            sb.append(vetor[(inicio + i) % capacidade]);
            if (i < tamanho - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
