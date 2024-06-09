arquivo read me com o descritivo da função hash. 

A função hash baseia-se no princípio de embaralhamento, para que assim, produzaum número de hash único para cada registro. 

A função recebe alguns valores de entrada......

A função incia-se com algumas declarações: 
    int hash = Variável na qual será armazenada o valor de hash após algumas misturas e embaralhamentos
    char atual = Váriavel que armazena o caracter atual da chave.
    char proximo = Váriavel que armazena o próximo caracter da chave. 

Após as declarações, inicia-se um loop for que percorre de 0 até o tamanho da chave - 1. (para garantir que o loop não acesse algum caracter de fora da palavra, gerando erros de index.).

Ao entrar no loop, a variável atual recebe o índice do primeiro caracter da palavra. A variável próximo recebe o indíce + 1, que é o segundo caracter da palavra.

A linha: 
    
    hash += ((int) atual * (i+1) * (i + 2) * (int) proximo)

        Transforma o valor de atual (char) em um tipo inteiro (int), para que seja feita as operações. Após a transformação, vem as operações. 

        (int) atual * (i + 1) * (i + 2) * (int) próximo = valor atua * segundo caracter * terceiro caracter * o valor armazendao na variável próximo. 
    
    hash += ((hash << 5) + hash >> 10) + (int) atual;

        Utiliza de deslocamento de bits para fazer o embaralhamento

        pega o valor armazenado em "hash", desloca 5 bits para a esquerda e depois soma com o valor de "hash" deslocado 10 bits a direita. Após esse embaralhamento soma-se o valor que está armazenado na variável "atual". 

    hash += (hash << 5) ^ (hash >> 28) ^ seed ^ (int) atual;

        Este trecho continua usando deslocamento de bits para embaralhamento. 

        o diferencial deste trecho é o operador ^ (XOR) que contribui ainda mais com o embaralhamento. 

        Além do operador XOR, este trecho introduz o uso da variável seed. Uma semente usada para garantir o embaralhamento. 


Após isso, o loop for é encerrado.

Depois, é feito uma sequência de operações, utilizando este mesmo princípio: hash += (hash << 5) ^ (hash >> 28) ^ seed ^ (int) atual; Apenas alterando a quantidade de deslocamento. 

Para encerrar, a função tem um return que garante que os valores estejam dentro do tamanho do vetor, utilizando o hash % tam



