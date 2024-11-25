package aplicacao;

import dados.Estado;
import dados.Transporte;

public class Processar {

    public static void alterarSituacao(Transporte transporte, Estado novaSituacao) {
        Estado situacaoAtual = transporte.getSituacao();

        if (situacaoAtual == Estado.TERMINADO || situacaoAtual == Estado.CANCELADO) {
            throw new IllegalStateException("Transporte já foi TERMINADO ou CANCELADO e não pode mudar de situação.");
        }

        switch (novaSituacao) {
            case ALOCADO:
                if (situacaoAtual != Estado.PENDENTE) {
                    throw new IllegalStateException("Apenas transportes PENDENTES podem ser ALOCADOS.");
                }
                break;

            case TERMINADO:
                if (situacaoAtual != Estado.ALOCADO) {
                    throw new IllegalStateException("Apenas transportes ALOCADOS podem ser TERMINADOS.");
                }
                break;

            case CANCELADO:
                break;

            case PENDENTE:
                throw new IllegalStateException("Não é permitido reverter um transporte para PENDENTE.");

            default:
                throw new IllegalArgumentException("Situação desconhecida: " + novaSituacao);
        }
        transporte.setSituacao(novaSituacao);
    }
}
