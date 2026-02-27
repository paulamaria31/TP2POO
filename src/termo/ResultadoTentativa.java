package termo;

public class ResultadoTentativa {
    private final String tentativaOriginal;
    private final EstadoLetra[] estados;

    //Construtor
    public ResultadoTentativa(String tentativaOriginal, EstadoLetra[] estados) {
        this.tentativaOriginal = tentativaOriginal;
        this.estados = estados;
    }

    //Retorno a tentativa
    public String getTentativaOriginal() {
        return tentativaOriginal;
    }

    //Retorno o estado
    public EstadoLetra[] getEstados() {
        return estados;
    }

    //Vejo se acertou, se as letras for diferente de verde ja nao acertei
    public boolean acertou() {
        for (EstadoLetra e : estados) {
            if (e != EstadoLetra.CORRETA) return false;
        }
        return true;
    }
}
