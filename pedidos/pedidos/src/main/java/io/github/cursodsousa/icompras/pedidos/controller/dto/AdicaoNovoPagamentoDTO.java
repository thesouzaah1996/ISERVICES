package io.github.cursodsousa.icompras.pedidos.controller.dto;

import io.github.cursodsousa.icompras.pedidos.model.TipoPagamento;

public record AdicionarNovoPagamentoDTO(
        Long codigoPedido,
        String dadosCartao,
        TipoPagamento tipoPagamento
) {
}
