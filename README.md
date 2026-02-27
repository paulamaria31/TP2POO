# TERMO (Wordle PT-BR) — Java + Swing

Este projeto implementa o jogo TERMO conforme requisitos do trabalho:
- Palavra secreta de 5 letras
- 6 tentativas
- Cores: verde (posição correta), amarelo (existe em outra posição), cinza (não existe)
- Banco de palavras lido de um arquivo .txt informado por argumento de linha de comando
- Estatísticas: jogos, vitórias, derrotas
- Informações da partida: tentativas restantes e letras já utilizadas
- Botões: Nova partida (interrompe a atual) e Sair (mostra estatísticas finais)

## Compilar
```bash
make
```

## Executar
Passe o arquivo do banco de palavras:
```bash
make run PALAVRAS=palavras.txt
```

ou:
```bash
java -cp bin termo.Principal palavras.txt
```

## Observações
- O jogador pode digitar sem acento (ex.: "acao" vale para "ação"), desde que exista no banco.
- O arquivo do banco pode ter várias palavras por linha, separadas por espaço.
- Vídeo foi colocado no Youtube.
- Aqui está o link para o próprio: https://youtu.be/Z1X_mQaPKzE