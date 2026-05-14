package io.github.cursodsousa.icompras.pedidos.model;

import lombok.Data;

@Data
public class DadosPagamento {
    private String dados;
    private TipoPagamento tipoPagamento;
}
