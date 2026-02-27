package termo;

import java.util.LinkedHashSet;
import java.util.Set;

//Mostra o estado da partida atual
public class EstadoPartida {
    //Numero maxima de tentativa
    public static final int MAX_TENTATIVAS = 6;

    private String palavraSecreta;
    private int tentativasRestantes;
    private int turnoAtual;
    private final Set<Character> letrasUsadas;

    //Construtor
    public EstadoPartida() {
        //Começa com todas as tentivas
        tentativasRestantes = MAX_TENTATIVAS;
        //Começa na tentativa zero
        turnoAtual = 0;
        letrasUsadas = new LinkedHashSet<>();
    }

    //Inicia a partida
    public void iniciar(String palavraSecreta) {
        this.palavraSecreta = palavraSecreta;
        this.tentativasRestantes = MAX_TENTATIVAS;
        this.turnoAtual = 0;
        this.letrasUsadas.clear();
    }

    //Os gets
    public String getPalavraSecreta() { return palavraSecreta; }
    public int getTentativasRestantes() { return tentativasRestantes; }
    public int getTurnoAtual() { return turnoAtual; }

    //Incremento o turno atual e diminuo as tentativas restantes
    public void consumirTentativa() {
        turnoAtual++;
        tentativasRestantes--;
    }

    //Pego a tentativa
    public void adicionarLetrasUsadas(String tentativa) {
        //Normalizo ela
        String norm = UtilTexto.normalizar(tentativa);
        //Pego cada letra da tentativa e adiciona na lista de letras usadas
        for (int i = 0; i < norm.length(); i++) {
            letrasUsadas.add(norm.charAt(i));
        }
    }

    //Retorna o string de letras usadas
    public String letrasUsadasComoTexto() {
        //Crio um objeto que monta uma string aos poucos (ao inves de ficar usando +)
        StringBuilder sb = new StringBuilder();
        //Para cada letra da lista
        for (Character c : letrasUsadas) {
            //Vai acrescentando a letra
            sb.append(c).append(' ');
        }
        //retorna a string
        return sb.toString().trim();
    }
}
