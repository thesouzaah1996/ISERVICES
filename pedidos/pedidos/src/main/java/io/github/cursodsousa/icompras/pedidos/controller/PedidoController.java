package io.github.cursodsousa.icompras.pedidos.controller;

import io.github.cursodsousa.icompras.pedidos.controller.dto.AdicaoNovoPagamentoDTO;
import io.github.cursodsousa.icompras.pedidos.controller.dto.AdicaoNovoPagamentoDTO;
import io.github.cursodsousa.icompras.pedidos.controller.dto.NovoPedidoDTO;
import io.github.cursodsousa.icompras.pedidos.controller.mappers.PedidoMapper;
import io.github.cursodsousa.icompras.pedidos.model.ErroResposta;
import io.github.cursodsousa.icompras.pedidos.model.exception.ItemNaoEncontradoException;
import io.github.cursodsousa.icompras.pedidos.model.exception.ValidationException;
import io.github.cursodsousa.icompras.pedidos.publisher.DetalhePedidoMapper;
import io.github.cursodsousa.icompras.pedidos.publisher.representation.DetalhePedidoRepresentation;
import io.github.cursodsousa.icompras.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoMapper pedidoMapper;
    private final DetalhePedidoMapper detalhePedidoMapper;

    @PostMapping
    public ResponseEntity<Object> criar(@RequestBody NovoPedidoDTO novoPedidoDTO) {
        try {
            var pedido = pedidoMapper.map(novoPedidoDTO);
            var pedidoCadastrado = pedidoService.criarPedido(pedido);
            return ResponseEntity.ok(pedidoCadastrado.getCodigo());
        } catch (ValidationException e) {
            var erro = new ErroResposta("Erro validação", e.getField(), e.getMessage());
            return ResponseEntity.badRequest().body(erro);
        }
    }

    @PostMapping("pagamentos")
    public ResponseEntity<Object> adicionarNovoPagamento(@RequestBody AdicaoNovoPagamentoDTO adicionarNovoPagamentoDTO) {
        try {
            pedidoService.adicionarNovoPagamento(adicionarNovoPagamentoDTO.codigoPedido(), adicionarNovoPagamentoDTO.dados(), adicionarNovoPagamentoDTO.tipoPagamento());
            return ResponseEntity.noContent().build();
        } catch (ItemNaoEncontradoException e) {
            var erro = new ErroResposta("Item não encontrado", "codigoPedido", e.getMessage());
            return ResponseEntity.badRequest().body(erro);
        }
    }

    @GetMapping("{codigo}")
    public ResponseEntity<DetalhePedidoRepresentation> obterDetalhesPedido(@PathVariable Long codigo) {
        return pedidoService
                .carregarDadosCompletosPedido(codigo)
                .map(detalhePedidoMapper::map)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
