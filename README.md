# TP2POO
Trabalho prático 2 da disciplina Programação Orientada a Objetos

# Desenvolvimento do jogo TERMO em Java com Interface Gráfica (Swing)

Desenvolver o jogo TERMO, conhecido como também como Wordle em Português,utilizando a linguagem Java e a biblioteca Swing para interface gráfica. Neste jogo, o objetivo é descobrir a palavra secreta de 5 letras, considerando as dicas: ao inserir uma letra, o jogador irá ser informado se ela está na posição correta (indicado pela cor verde), na posição errada (cor amarela) ou se ela não compõem a palavra (cor cinza).

● Toda partida do jogo refere-se a adivinhação de uma única palavra de 5
letras.
● O sistema seleciona aleatoriamente uma das palavras disponíveis do banco
de palavras, ignorando palavras que já foram utilizadas anteriormente.
● Cada partida é composta por 6 turnos (tentativas). A cada turno, o jogador
insere uma palavra de 5 letras válida (que está no banco de palavras) na
tentativa de descobrir qual a palavra secreta. O jogo então informa a ele o
estado das letras na palavra sugerida de acordo com a palavra secreta.
● Este estado é representado graficamente por cores, e caso a palavra não
tenha sido a correta, o número de tentativas diminui em 1. As letras
informadas em tentativas anteriores devem ser destacadas e informadas ao
jogador, e o sistema não precisa impedi-lo de usá-las novamente.
● O jogador não necessita inserir o acento nas letras, informando apenas as
letras normais do alfabeto (incluindo k, y, w e ç).
● A partida termina quando o jogador descobre a palavra secreta, atinge o
número máximo de tentativas, inicia uma nova partida ou sai do jogo.
