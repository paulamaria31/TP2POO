package termo;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

//Um quadrado da letra que herda de JLabel
public class QuadradoLetra extends JLabel {

    //Toda vez que inicia
    public QuadradoLetra() {
        //Chama o construtor da super com " " e alinhado no centro
        super("", SwingConstants.CENTER);
        //é opaco
        setOpaque(true);
        //Coloco a neutra
        setBackground(TemaCores.VAZIO);
        //coloco uma borda simples
        setBorder(new LineBorder(TemaCores.BORDA));
        //Pego a fonte padrao e crio uma variacao
        setFont(getFont().deriveFont(Font.BOLD, 22f));
        //Tamanho para o quadrado
        setPreferredSize(new Dimension(45, 45));
    }

    //Limpo o quadrado, com vazio, for de fundo cinza e cor do texto como preto
    public void limpar() {
        setText("");
        setBackground(TemaCores.VAZIO);
        setForeground(Color.BLACK);
    }

    //Metodo para atualizar o quadrado
    public void aplicar(char letra, EstadoLetra estado) {
        //Pego a letra, converto para string e maiuscula
        setText(String.valueOf(Character.toUpperCase(letra)));
        //Dependendo do estado
        switch (estado) {
            case CORRETA:
                //Se for correta coloco o fundo como verde e pinto a letra de branco
                setBackground(TemaCores.VERDE);
                setForeground(Color.WHITE);
                break;
            case EXISTE:
                //Se esta fora do lugar coloco o fundo como amarelo e pinto a letra de preto
                setBackground(TemaCores.AMARELO);
                setForeground(Color.BLACK);
                break;
            case NAO_EXISTE:
            default:
                //Se nao existe coloco como cinza e pinto a letra de branco
                setBackground(TemaCores.CINZA);
                setForeground(Color.WHITE);
                break;
        }
    }
}
