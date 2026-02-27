package termo;

public class Usuario {
    private final String nome;
    private final String senhaHash;
    private final Estatisticas estatisticas;

    public Usuario(String nome, String senhaHash, Estatisticas estatisticas) {
        this.nome = nome;
        this.senhaHash = senhaHash;
        this.estatisticas = estatisticas;
    }

    public String getNome() {
        return nome;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public Estatisticas getEstatisticas() {
        return estatisticas;
    }
}

