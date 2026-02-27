package termo;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class UsuarioRepositorio {
    private final File arquivo;

    public UsuarioRepositorio() {
        this(new File(System.getProperty("user.home"), ".termo_usuarios.csv"));
    }

    public UsuarioRepositorio(File arquivo) {
        this.arquivo = arquivo;
    }

    public synchronized Usuario autenticar(String nome, String senha) throws Exception {
        nome = nome.trim();
        if (nome.isEmpty()) throw new Exception("Informe o nome de usuário.");
        if (senha == null) senha = "";

        Map<String, Registro> regs = carregarTodos();
        Registro r = regs.get(nome.toLowerCase());
        if (r == null) throw new Exception("Usuário não encontrado.");

        String hash = hashSenha(senha);
        if (!hash.equals(r.senhaHash)) throw new Exception("Senha incorreta.");

        return new Usuario(r.nomeOriginal, r.senhaHash, r.estatisticas);
    }

    public synchronized Usuario cadastrar(String nome, String senha) throws Exception {
        nome = nome.trim();
        if (nome.isEmpty()) throw new Exception("Informe o nome de usuário.");
        if (nome.contains(";")) throw new Exception("Nome inválido (não pode conter ';').");
        if (senha == null || senha.isEmpty()) throw new Exception("Informe uma senha.");
        if (senha.length() < 3) throw new Exception("Senha muito curta (mínimo 3 caracteres).");

        Map<String, Registro> regs = carregarTodos();
        String key = nome.toLowerCase();
        if (regs.containsKey(key)) throw new Exception("Esse usuário já existe.");

        Estatisticas est = new Estatisticas();
        String hash = hashSenha(senha);

        Registro novo = new Registro(nome, hash, est);
        regs.put(key, novo);
        salvarTodos(regs);

        return new Usuario(nome, hash, est);
    }

    public synchronized void salvar(Usuario u) throws Exception {
        Map<String, Registro> regs = carregarTodos();
        String key = u.getNome().toLowerCase();
        regs.put(key, new Registro(u.getNome(), u.getSenhaHash(), u.getEstatisticas()));
        salvarTodos(regs);
    }

    // ===== CSV simples =====
    // nome;senhaHash;jogos;vitorias;derrotas;seqAtual;melhorSeq
    private Map<String, Registro> carregarTodos() throws Exception {
        Map<String, Registro> map = new HashMap<>();
        if (!arquivo.exists()) return map;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(arquivo), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] p = line.split(";");
                if (p.length < 2) continue;

                String nome = p[0];
                String key = nome.toLowerCase();
                String senhaHash = p[1];

                Estatisticas est = new Estatisticas();
                int jogos = parseIntSafe(p, 2);
                int vit = parseIntSafe(p, 3);
                int der = parseIntSafe(p, 4);
                int seqAtual = parseIntSafe(p, 5);
                int melhor = parseIntSafe(p, 6);
                est.setDados(jogos, vit, der, seqAtual, melhor);

                map.put(key, new Registro(nome, senhaHash, est));
            }
        }

        return map;
    }

    private void salvarTodos(Map<String, Registro> regs) throws Exception {
        File parent = arquivo.getParentFile();
        if (parent != null && !parent.exists()) parent.mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(arquivo, false), StandardCharsets.UTF_8))) {
            for (Registro r : regs.values()) {
                Estatisticas e = r.estatisticas;
                String linha =
                        r.nomeOriginal + ";" +
                        r.senhaHash + ";" +
                        e.getJogos() + ";" +
                        e.getVitorias() + ";" +
                        e.getDerrotas() + ";" +
                        e.getSequenciaAtual() + ";" +
                        e.getMelhorSequencia();
                bw.write(linha);
                bw.newLine();
            }
        }
    }

    private static int parseIntSafe(String[] p, int idx) {
        if (idx >= p.length) return 0;
        try { return Integer.parseInt(p[idx]); } catch (Exception e) { return 0; }
    }

    // ===== hash SHA-256 (ok pro TP) =====
    public static String hashSenha(String senha) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(senha.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) sb.append(String.format("%02x", b));
        return sb.toString();
    }

    private static class Registro {
        String nomeOriginal;
        String senhaHash;
        Estatisticas estatisticas;

        Registro(String nomeOriginal, String senhaHash, Estatisticas estatisticas) {
            this.nomeOriginal = nomeOriginal;
            this.senhaHash = senhaHash;
            this.estatisticas = estatisticas;
        }
    }
}

