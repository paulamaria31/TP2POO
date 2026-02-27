package termo;

import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

public class Principal {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Uso: java -cp bin termo.Principal <arquivo_de_palavras>");
            System.exit(1);
        }

        final String caminho = args[0];

        SwingUtilities.invokeLater(() -> {
            try {
                BancoPalavras banco = new BancoPalavras(caminho);

                // === LOGIN/CADASTRO (extra) ===
                UsuarioRepositorio repo = new UsuarioRepositorio();
                LoginDialog login = new LoginDialog(null, repo);
                login.setVisible(true);

                Usuario usuario = login.getUsuarioLogado();
                if (usuario == null) {
                    // usuário cancelou
                    System.exit(0);
                }

                ControladorJogo controlador = new ControladorJogo(banco, usuario.getEstatisticas());
                JanelaPrincipal janela = new JanelaPrincipal(controlador, repo, usuario);
                janela.setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(
                        null,
                        "Erro ao iniciar o jogo:\n" + e.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
                System.exit(1);
            }
        });
    }
}
