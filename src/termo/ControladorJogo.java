package termo;

//Tem as variaveis banco, estatisticas, estado e avaliador e partida ativa
public class ControladorJogo {
    private final BancoPalavras banco;
    private final Estatisticas estatisticas;
    private final EstadoPartida estado;
    private final AvaliadorTentativa avaliador;

    private boolean partidaAtiva;

    //Construtor e inicializo cada
    public ControladorJogo(BancoPalavras banco, Estatisticas estatisticas) {
        this.banco = banco;
        this.estatisticas = estatisticas;
        this.estado = new EstadoPartida();
        this.avaliador = new AvaliadorTentativa();
        this.partidaAtiva = false;
    }

    //Ao iniciar a partir, sorteio uma palavra do objeto banco
    public void novaPartida() {
        String secreta = banco.sortearPalavraSecreta();
        //Inicio o estado com a palavra
        estado.iniciar(secreta);
        //A partida esta ativa
        partidaAtiva = true;
    }

    //Vejo se a partida esta ativa
    public boolean isPartidaAtiva() {
        return partidaAtiva;
    }

    //pego as estatistica
    public Estatisticas getEstatisticas() {
        return estatisticas;
    }

    //retorno o estado
    public EstadoPartida getEstado() {
        return estado;
    }

    //Aqui eu envio a tentativa que eu recebi de parametro
    public ResultadoTentativa enviarTentativa(String texto) throws Exception {
        //So tem como enviar se a partida estiver ativa
        if (!partidaAtiva) {
            throw new Exception("Clique em 'Nova partida' para começar.");
        }

        String tentativaOriginal = texto.trim();
        String tentativa = UtilTexto.normalizar(tentativaOriginal);
        
        if (tentativa.length() != 5) {
            throw new Exception("Digite uma palavra com 5 letras.");
        }

        if (!tentativa.matches("[a-zç]{5}")) {
            throw new Exception("Digite apenas letras (5 letras).");
        }

        //Vejo se a palavra da tentativa corresponde com o do banco de palavra e recebo o resultado
        ResultadoTentativa r = avaliador.avaliar(estado.getPalavraSecreta(), tentativaOriginal);

        //Adiciono a tentativa como palavra ja usada
        estado.adicionarLetrasUsadas(tentativaOriginal);
        estado.consumirTentativa();

        //Verifico de acertou
        if (r.acertou()) {
            //A partida deixa de ser ativa e registro vitoria
            partidaAtiva = false;
            estatisticas.registrarVitoria();
            //Se nao acertou, verifico se o numero de tentativas restantes for menor que zero nao posso continuar
        } else if (estado.getTentativasRestantes() <= 0) {
            //A partir deixa de ser ativa e registro derrota
            partidaAtiva = false;
            estatisticas.registrarDerrota();
        }

        //retorno o resultado da tentativa
        return r;
    }

    //pego retorno o objeto banco
    public BancoPalavras getBanco() {
        return banco;
    }
}
