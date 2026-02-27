package termo;

public class Estatisticas {
    private int jogos;
    private int vitorias;
    private int derrotas;

    // extra (sequências)
    private int sequenciaAtual;
    private int melhorSequencia;

    public void registrarVitoria() {
        jogos++;
        vitorias++;
        sequenciaAtual++;
        if (sequenciaAtual > melhorSequencia) {
            melhorSequencia = sequenciaAtual;
        }
    }

    public void registrarDerrota() {
        jogos++;
        derrotas++;
        sequenciaAtual = 0;
    }

    public int getJogos() { return jogos; }
    public int getVitorias() { return vitorias; }
    public int getDerrotas() { return derrotas; }

    public int getSequenciaAtual() { return sequenciaAtual; }
    public int getMelhorSequencia() { return melhorSequencia; }

    // usado pelo repositório pra carregar do arquivo
    public void setDados(int jogos, int vitorias, int derrotas, int sequenciaAtual, int melhorSequencia) {
        this.jogos = Math.max(0, jogos);
        this.vitorias = Math.max(0, vitorias);
        this.derrotas = Math.max(0, derrotas);
        this.sequenciaAtual = Math.max(0, sequenciaAtual);
        this.melhorSequencia = Math.max(0, melhorSequencia);
    }

    @Override
    public String toString() {
        return "Jogos: " + jogos +
                " | Vitórias: " + vitorias +
                " | Derrotas: " + derrotas +
                " | Sequência atual: " + sequenciaAtual +
                " | Melhor sequência: " + melhorSequencia;
    }
}
