package termo;

import java.text.Normalizer;

public class UtilTexto {

    //Pego a string para normalizar
    public static String normalizar(String s) {
        //se a string for nula retorna vazio
        if (s == null) return "";
        //Coloco todas as letras minuscula e remove espacos
        String t = s.trim().toLowerCase();
        //separa a letra do acento
        t = Normalizer.normalize(t, Normalizer.Form.NFD);
        //remove os acentos
        t = t.replaceAll("\\p{M}+", "");
        return t;
    }

    //entrada com varios caracteres especiais
    public static String somenteLetrasAte5(String s) {
        //Se a string for nula retorna vazio
        if (s == null) return "";
        //Crio um objeto para concatenar
        StringBuilder sb = new StringBuilder();
        //remov espaçoes
        String t = s.trim();
        //enquanto tiver letras na string e quando ja tiver 5 guardada
        for (int i = 0; i < t.length() && sb.length() < 5; i++) {
            //pego a letra
            char c = t.charAt(i);
            //se for letra
            if (Character.isLetter(c) || c == 'ç' || c == 'Ç') {
                //adiciono
                sb.append(c);
            }
        }
        //retorno tudo concatenado
        return sb.toString();
    }
}
