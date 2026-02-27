package termo;

import java.util.HashMap;
import java.util.Map;

//Classe que vai avaliar a tentativa
public class AvaliadorTentativa {

    //Recebo a palavra secreta e a tentativa
    public ResultadoTentativa avaliar(String palavraSecretaOriginal, String tentativaOriginal) {
        //normalizo as palavras
        String secreta = UtilTexto.normalizar(palavraSecretaOriginal);
        String tentativa = UtilTexto.normalizar(tentativaOriginal);

        //Crio um vetor de estados com tam 5
        EstadoLetra[] estados = new EstadoLetra[5];

        // Cria um mapa com chave e quantas vezes a letra aparece
        Map<Character, Integer> contagem = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            //Pego a letra na poscao i
            char c = secreta.charAt(i);
            //Vê quantas vezes essa letra apareceu
            contagem.put(c, contagem.getOrDefault(c, 0) + 1);
        }

        //comparo cada letra
        for (int i = 0; i < 5; i++) {
            if (tentativa.charAt(i) == secreta.charAt(i)) {
                //se as letras forem iguais em um indice, recebe verde
                estados[i] = EstadoLetra.CORRETA;
                //Pego essa letra que é igual
                char c = tentativa.charAt(i);
                //Diminuo da quantidade de letras iguais para nao virar amarela
                contagem.put(c, contagem.get(c) - 1);
            }
        }

        //Agora vejo amarela e cnza
        for (int i = 0; i < 5; i++) {
            //Se a posicao for diferente de nulo quer dizer que é verde e nao pode ser marcada novamente
            if (estados[i] != null) continue;

            //Pego a letra da tentativa
            char c = tentativa.charAt(i);
            //Vejo se ainda existe no mapa
            Integer disp = contagem.get(c);
            //Existe mas esta no lugar errado
            if (disp != null && disp > 0) {
                //marco como amarelo
                estados[i] = EstadoLetra.EXISTE;
                //diminuo do mapa
                contagem.put(c, disp - 1);
            } else {
                //letra nao existe ou ja foi toda usada, marca como cinza
                estados[i] = EstadoLetra.NAO_EXISTE;
            }
        }

        //Retorno um objeto resultado tentativa com a palavra da tentativa e o estado da palavra
        return new ResultadoTentativa(tentativaOriginal, estados);
    }
}
