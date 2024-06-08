package Sistemas_Reservas;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Hash_trab {
    static Scanner entrada = new Scanner(System.in);

    public static class hash {
        hash prox;
        String chave;
        String nomeHotel;
        int numeroQuarto;
        String dataEntrada;
        String dataSaida;
    }

    static int tam = 211; // tamanho da função hash
    static hash tabela[] = new hash[tam];
    static int count = 0;
    
    public static void inserir(String chave, String nomeHotel, int numeroQuarto, String dataEntrada, String dataSaida) { //função inserir. Insere elementos no vetor, e utiliza da lista encadeada para trata colisões (registros que caíram na mesma posição).
        int pos = funcao_hashing(chave); 
        hash novo = new hash();

        novo.chave = chave;
        novo.nomeHotel = nomeHotel;
        novo.numeroQuarto = numeroQuarto;
        novo.dataEntrada = dataEntrada;
        novo.dataSaida = dataSaida;
        novo.prox = tabela[pos];
        tabela[pos] = novo;
    }

    public static void carregarReservas(String nomeArquivo) { //função para carregar os registros a partir de um arquivo .txt
        try {
            File arquivo = new File(nomeArquivo);
            Scanner leitor = new Scanner(arquivo);

            while (leitor.hasNextLine()) {
                String[] dados = leitor.nextLine().split(",");
                String nomeHotel = dados[0];
                int numeroQuarto = Integer.parseInt(dados[1]);
                String dataEntrada = dados[2];
                String dataSaida = dados[3];
                String chave = nomeHotel + numeroQuarto + dataEntrada + dataSaida;
                inserir(chave, nomeHotel, numeroQuarto, dataEntrada, dataSaida);
            }

            leitor.close();

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado");
        }
    }

    static int funcao_hashing(String chave) { //função hashing usando embaralhamento com deslocamento de bits para direita e para esquerda.
        int hash = 0;

        char atual = ' ';
        char proximo = ' ';

        int seed = 163;

        for (int i = 0; i < chave.length() - 1; i++) {
            atual = chave.charAt(i);
            proximo = chave.charAt(i + 1);

            hash += ((int) atual * (i + 1) * (i + 2) * (int) proximo);
            
            hash += ((hash << 5) + hash >> 10) + (int) atual;
            
            hash += (hash << 5) ^ (hash >> 28) ^ seed ^ (int) atual;
        }

        hash = (hash << 5) ^ (hash >> 28) ^ seed ^(int) atual; 
        hash = (hash << 4) ^ (hash >> 27) ^ seed ^ (int) atual;
        hash = (hash << 3) ^ (hash >> 26) ^ seed ^ (int) atual;

        hash = (hash << 6) ^ (hash >> 29) ^ seed ^ (int) atual;
        hash = (hash << 5) ^ (hash >> 28) ^ seed ^ (int) atual;
        hash = (hash << 4) ^ (hash >> 27) ^ seed ^ (int) atual;

        hash = (hash << 3) ^ (hash >> 26) ^ seed ^ (int) atual;
        hash = (hash << 2) ^ (hash >> 25) ^ seed ^ (int) atual;
        hash = (hash << 1) ^ (hash >> 24) ^ seed ^ (int) atual;
        
        hash = (hash << 9) ^ (hash >> 32) ^ seed ^ (int) atual;
        hash = (hash << 8) ^ (hash >> 31) ^ seed ^ (int) atual;
        hash = (hash << 7) ^ (hash >> 30) ^ seed ^ (int) atual;

        hash = (hash >> 6) ^ (hash >> 29) ^ seed ^ (int) atual;

        return hash % tam;
    }

    static void mostrar_hash(String Chave) { //função que busca um registro pela chave e mostra todas as suas informações.     
        hash aux;

        for (int i = 0; i < tam; i++) {
            aux = tabela[i];
            while (aux != null) {
                if (aux.chave.equals(Chave)){
                    System.out.println("Chave: " + aux.chave);
                    System.out.println("Nome do Hotel: " + aux.nomeHotel);
                    System.out.println("Número do Quarto: " + aux.numeroQuarto);
                    System.out.println("Data de Entrada: " + aux.dataEntrada);
                    System.out.println("Data de Saída: " + aux.dataSaida);
                    System.out.println(); // linha em branco para separar as reservas.
                }
                aux = aux.prox;
            }
        }
    }

    static void mostrar_hash1() { //função para mostrar onde os registros cairam.
        hash aux;

        for (int i = 0; i < tam; i++) {
            aux = tabela[i];
            while (aux != null) {
                System.out.println("Entrada " + i + ": " + aux.chave);
                aux = aux.prox;
            }
        }
    }

    static void mostrar_hash2() { // função para mostrar onde cada registro caiu, quantas posições foram ocupadas e quantas posições estão livres. 
        int ocupados = 0;
        int vazios = 0;
    
        for (int i = 0; i < tam; i++) {
            if (tabela[i] == null) {
                System.out.println("Posição " + i + ": .");
                vazios++;
            } else {
                System.out.println("Posição " + i + ": " + tabela[i].chave);
                ocupados++;
            }
        }
    
        System.out.println("Total de posições ocupadas: " + ocupados);
        System.out.println("Total de posições vazias: " + vazios);
    }
    

    static void excluir_hash(String Chave) { //função para excluir um elemento.
        int pos = funcao_hashing(Chave);
        hash aux;

        if (tabela[pos] != null) {
            if (tabela[pos].chave.equals(Chave)) 
                tabela[pos] = tabela[pos].prox;
            else {
                aux = tabela[pos].prox;
                hash ant = tabela[pos];
                while (aux != null && !aux.chave.equals(Chave)){
                    ant = aux;
                    aux = aux.prox;
                }
                if (aux != null) 
                    ant.prox = aux.prox;    
                else {
                    System.out.println("Elemento não encontrado");   
                } 
            }
        }
        else 
            System.out.println("Elemento não encontrado");
    }
    
    public static void main(String[] args) {
        int op;
        carregarReservas("Sistemas_Reservas/reservas.txt");
        
        do {
            System.out.println("===== RESERVAS =====");
            System.out.println("[1] - CONSULTAR RESERVA");
            System.out.println("[2] - EXCLUIR RESERVA");
            System.out.println("[3] - SAIR");
    
            System.out.print("Digite sua escolha: ");
            op = entrada.nextInt();
    
            switch (op) {
                case 1:
                    System.out.println("\nCONSULTAR RESERVAS\n");

                    mostrar_hash1();

                    mostrar_hash2();
                    break;
                case 2:
                    System.out.println("EXCLUIR RESERVA");
    
                    System.out.println("Digite a chave da reserva: ");
                    String chave_excluir = entrada.next();

                    excluir_hash(chave_excluir);
                    break;
                case 3:
                    System.out.println("SAINDO...");
                    System.exit(0);
                    break;
            }
        } while (op >= 1 || op <= 4);
    }
}