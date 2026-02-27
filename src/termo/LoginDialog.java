package termo;

import javax.swing.*;
import java.awt.*;

public class LoginDialog extends JDialog {
    private Usuario usuarioLogado;

    private final JTextField txtUsuario = new JTextField(18);
    private final JPasswordField txtSenha = new JPasswordField(18);

    public LoginDialog(Frame owner, UsuarioRepositorio repo) {
        super(owner, "Login / Cadastro", true);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 6, 6, 6);
        g.anchor = GridBagConstraints.WEST;

        g.gridx = 0; g.gridy = 0;
        form.add(new JLabel("Usuário:"), g);
        g.gridx = 1;
        form.add(txtUsuario, g);

        g.gridx = 0; g.gridy = 1;
        form.add(new JLabel("Senha:"), g);
        g.gridx = 1;
        form.add(txtSenha, g);

        JButton btnLogin = new JButton("Entrar");
        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnCancelar = new JButton("Cancelar");

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        botoes.add(btnCadastrar);
        botoes.add(btnLogin);
        botoes.add(btnCancelar);

        btnLogin.addActionListener(e -> {
            try {
                String u = txtUsuario.getText();
                String s = new String(txtSenha.getPassword());
                usuarioLogado = repo.autenticar(u, s);
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnCadastrar.addActionListener(e -> {
            try {
                String u = txtUsuario.getText();
                String s = new String(txtSenha.getPassword());
                usuarioLogado = repo.cadastrar(u, s);
                JOptionPane.showMessageDialog(this, "Usuário cadastrado! Você já está logado.",
                        "OK", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> {
            usuarioLogado = null;
            dispose();
        });

        setLayout(new BorderLayout());
        add(form, BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(owner);
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }
}

