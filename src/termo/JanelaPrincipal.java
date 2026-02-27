package termo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class JanelaPrincipal extends JFrame {

    private final ControladorJogo controlador;

    // EXTRA (login + persistência)
    private final UsuarioRepositorio repo;
    private final Usuario usuario;

    private final QuadradoLetra[][] grade;

    private final JLabel lblEstatisticas;
    private final JLabel lblTentativas;
    private final JLabel lblLetrasUsadas;

    private final JTextField campoTentativa;

    private final JButton btnEnviar;
    private final JButton btnNovaPartida;
    private final JButton btnSair;

    public JanelaPrincipal(ControladorJogo controlador,
                           UsuarioRepositorio repo,
                           Usuario usuario) {

        super("TERMO (Java + Swing) — Usuário: " + usuario.getNome());

        this.controlador = controlador;
        this.repo = repo;
        this.usuario = usuario;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(560, 580);
        setLocationRelativeTo(null);

        lblEstatisticas = new JLabel(" ");
        lblTentativas = new JLabel(" ");
        lblLetrasUsadas = new JLabel(" ");

        JPanel painelTopo = new JPanel();
        painelTopo.setLayout(new BoxLayout(painelTopo, BoxLayout.Y_AXIS));
        painelTopo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painelTopo.add(lblEstatisticas);
        painelTopo.add(Box.createVerticalStrut(4));
        painelTopo.add(lblTentativas);
        painelTopo.add(Box.createVerticalStrut(4));
        painelTopo.add(lblLetrasUsadas);

        grade = new QuadradoLetra[EstadoPartida.MAX_TENTATIVAS][5];
        JPanel painelGrade = new JPanel(new GridLayout(EstadoPartida.MAX_TENTATIVAS, 5, 6, 6));
        painelGrade.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int r = 0; r < EstadoPartida.MAX_TENTATIVAS; r++) {
            for (int c = 0; c < 5; c++) {
                grade[r][c] = new QuadradoLetra();
                painelGrade.add(grade[r][c]);
            }
        }

        campoTentativa = new JTextField(10);
        btnEnviar = new JButton("Enviar");
        btnNovaPartida = new JButton("Nova partida");
        btnSair = new JButton("Sair");

        JPanel painelEntrada = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        painelEntrada.add(new JLabel("Palavra:"));
        painelEntrada.add(campoTentativa);
        painelEntrada.add(btnEnviar);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        painelBotoes.add(btnNovaPartida);
        painelBotoes.add(btnSair);

        JPanel painelBaixo = new JPanel();
        painelBaixo.setLayout(new BoxLayout(painelBaixo, BoxLayout.Y_AXIS));
        painelBaixo.add(painelEntrada);
        painelBaixo.add(painelBotoes);

        setLayout(new BorderLayout());
        add(painelTopo, BorderLayout.NORTH);
        add(painelGrade, BorderLayout.CENTER);
        add(painelBaixo, BorderLayout.SOUTH);

        btnNovaPartida.addActionListener(this::acaoNovaPartida);
        btnSair.addActionListener(this::acaoSair);
        btnEnviar.addActionListener(this::acaoEnviar);
        campoTentativa.addActionListener(this::acaoEnviar);

        habilitarEntrada(false);
        atualizarInfo();

        JOptionPane.showMessageDialog(
                this,
                "Bem-vindo, " + usuario.getNome() + "!\nClique em 'Nova partida' para começar.",
                "Aviso",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void habilitarEntrada(boolean habilitar) {
        campoTentativa.setEnabled(habilitar);
        btnEnviar.setEnabled(habilitar);
    }

    private void acaoNovaPartida(ActionEvent e) {
        controlador.novaPartida();
        limparGrade();
        habilitarEntrada(true);
        atualizarInfo();
        campoTentativa.setText("");
        campoTentativa.requestFocusInWindow();
    }

    private void acaoSair(ActionEvent e) {
        salvarUsuario();

        String msg = "Estatísticas finais:\n" +
                controlador.getEstatisticas().toString();

        JOptionPane.showMessageDialog(this, msg,
                "Fim do jogo", JOptionPane.INFORMATION_MESSAGE);

        dispose();
        System.exit(0);
    }

    private void acaoEnviar(ActionEvent e) {
        if (!controlador.isPartidaAtiva()) return;

        try {
            ResultadoTentativa resultado =
                    controlador.enviarTentativa(campoTentativa.getText());

            campoTentativa.setText("");

            int linha = controlador.getEstado().getTurnoAtual() - 1;
            pintarLinha(linha, resultado);

            atualizarInfo();

            if (!controlador.isPartidaAtiva()) {
                habilitarEntrada(false);

                String secreta = controlador.getEstado().getPalavraSecreta();
                String secretaNorm = UtilTexto.normalizar(secreta);

                if (resultado.acertou()) {
                    JOptionPane.showMessageDialog(this,
                            "Você acertou! Palavra: " + secretaNorm,
                            "Vitória", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Fim das tentativas! Palavra era: " + secretaNorm,
                            "Derrota", JOptionPane.INFORMATION_MESSAGE);
                }

                salvarUsuario();
                atualizarInfo();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void salvarUsuario() {
        try {
            repo.salvar(usuario);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao salvar estatísticas:\n" + ex.getMessage(),
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void pintarLinha(int linha, ResultadoTentativa resultado) {
        String tentativaNorm =
                UtilTexto.normalizar(resultado.getTentativaOriginal());
        EstadoLetra[] estados = resultado.getEstados();

        for (int c = 0; c < 5; c++) {
            grade[linha][c].aplicar(tentativaNorm.charAt(c), estados[c]);
        }
    }

    private void limparGrade() {
        for (int r = 0; r < EstadoPartida.MAX_TENTATIVAS; r++) {
            for (int c = 0; c < 5; c++) {
                grade[r][c].limpar();
            }
        }
    }

    private void atualizarInfo() {
        Estatisticas est = controlador.getEstatisticas();
        EstadoPartida ep = controlador.getEstado();

        lblEstatisticas.setText(
                "Usuário: " + usuario.getNome() +
                " | Jogos: " + est.getJogos() +
                " | Vitórias: " + est.getVitorias() +
                " | Derrotas: " + est.getDerrotas() +
                " | Seq. atual: " + est.getSequenciaAtual() +
                " | Melhor seq.: " + est.getMelhorSequencia() +
                " | Total de palavras: " +
                controlador.getBanco().getTotalPalavras()
        );

        lblTentativas.setText(
                "Tentativas restantes: " + ep.getTentativasRestantes());

        lblLetrasUsadas.setText(
                "Letras já utilizadas: " + ep.letrasUsadasComoTexto());
    }
}
