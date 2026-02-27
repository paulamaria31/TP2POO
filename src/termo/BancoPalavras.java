package termo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class BancoPalavras {
    //Lista de palavra originais, normalizadas, conjunto normalizado e inidice usados
    private final List<String> palavrasOriginais;
    private final List<String> palavrasNormalizadas;
    private final Set<String> conjuntoNormalizado;
    private final Set<Integer> indicesUsados;
    //Aleatorio
    private final Random random;

    //Construtor
    public BancoPalavras(String caminho) throws Exception {
        //Inicializo as variaveis
        palavrasOriginais = new ArrayList<>();
        palavrasNormalizadas = new ArrayList<>();
        conjuntoNormalizado = new HashSet<>();
        indicesUsados = new HashSet<>();
        random = new Random();

        //Carrego o banco de dados
        carregar(caminho);

        //Se eu nao consegui carregar nenhuma palavra, meu banco de dados está vazio
        if (palavrasOriginais.isEmpty()) {
            throw new Exception("Banco de palavras vazio ou inválido.");
        }
    }

    //So a classe pode chamar esse metodo
    private void carregar(String caminho) throws Exception {
        //Abre o arquivo texto do caminho
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            //Leio linha por linha enquanto existir
            while ((linha = br.readLine()) != null) {
                //Removo espaços do começo e fim e como em um vetor as palavras
                String[] partes = linha.trim().split("\\s+");
                //Pego cada palavra
                for (String p : partes) {
                    //vejo se é nulo
                    if (p == null) continue;
                    //Tiro o espaço antes e depois de cada palavra
                    p = p.trim();
                    //Se for de tamanho 5 continua
                    if (p.length() != 5) continue;

                    //Cria a palavra sem acento e minusculo
                    String norm = UtilTexto.normalizar(p);
                    //verifico se ainda tem tamanho 5
                    if (norm.length() != 5) continue;

                    //Evito duplicidade
                    if (conjuntoNormalizado.contains(norm)) continue;

                    //A palavra fica valida para o banco
                    palavrasOriginais.add(p);
                    //Guardo a versão normalizada
                    palavrasNormalizadas.add(norm);
                    //Validacao rapido e evitar duplicatas
                    conjuntoNormalizado.add(norm);
                }
            }
        }
    }

    //Verifico se a palavra existe no banco na tentativa
    public boolean existeNoBanco(String tentativa) {
        //Pego a palavra e normalizo ela
        String norm = UtilTexto.normalizar(tentativa);
        //Vejo se possui tamanho 5 e se esta no conjunto normalizado
        return norm.length() == 5 && conjuntoNormalizado.contains(norm);
    }

    //Sorteio uma palavra do banco
    public String sortearPalavraSecreta() {
        //Vejo se a quantidade de palavras ja usada é maior que o numero total de palavras do banco
        if (indicesUsados.size() >= palavrasOriginais.size()) {
            //Se ja usou todas, limpo o conjunto
            indicesUsados.clear();
        }

        //Crio um index
        int idx;
        do {
            //O index recebe um numero aleatorio dentro do numero de palavras
            idx = random.nextInt(palavrasOriginais.size());
            //Verifico se essa palavra já não foi usada, senao sorteio novamente
        } while (indicesUsados.contains(idx));

        //Coloco a nova palavra como usada
        indicesUsados.add(idx);
        //Retorno a palavra da lista de palavras originais
        return palavrasOriginais.get(idx);
    }

    //retorna o numero total de palavras do banco de dados
    public int getTotalPalavras() {
        return palavrasOriginais.size();
    }

}
