# Makefile simples (Linux) para compilar e executar o TERMO (Java Swing)
# Inclui um passo para ajustar o timestamp do próprio Makefile e evitar
# o aviso de "relógio está errado" em execuções futuras.

JAVAC=javac
JAVA=java
SRC=src
BIN=bin
PRINCIPAL=termo.Principal

FONTES=$(shell find $(SRC) -name "*.java")

.PHONY: all compilar run clean ajustar_hora

all: ajustar_hora compilar

# Atualiza a data do Makefile (e opcionalmente dos fontes) para "agora".
# Isso normalmente remove o aviso do make nas próximas execuções.
ajustar_hora:
	@touch Makefile
	@touch $(FONTES) 2>/dev/null || true

compilar:
	@mkdir -p $(BIN)
	$(JAVAC) -encoding UTF-8 -d $(BIN) $(FONTES)

run: all
	@if [ -z "$(PALAVRAS)" ]; then \
		echo "Uso: make run PALAVRAS=arquivo.txt"; \
		exit 1; \
	fi
	$(JAVA) -cp $(BIN) $(PRINCIPAL) "$(PALAVRAS)"

clean:
	rm -rf $(BIN)

