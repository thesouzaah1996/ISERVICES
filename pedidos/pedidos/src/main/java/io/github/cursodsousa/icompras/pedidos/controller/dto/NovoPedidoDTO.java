package io.github.cursodsousa.icompras.pedidos.controller.dto;

import lombok.Data;

import java.util.List;

public record NovoPedidoDTO(
        Long codigoCliente,
        DadosPagamentoDTO dadosPagamento,
        List<ItemPedidoDTO> itens
) {}
