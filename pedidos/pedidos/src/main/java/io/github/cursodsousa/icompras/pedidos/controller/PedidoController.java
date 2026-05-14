package io.github.cursodsousa.icompras.pedidos.controller;

import io.github.cursodsousa.icompras.pedidos.controller.dto.NovoPedidoDTO;
import io.github.cursodsousa.icompras.pedidos.controller.mappers.PedidoMapper;
import io.github.cursodsousa.icompras.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoMapper pedidoMapper;

    public ResponseEntity<Object> criar(@RequestBody NovoPedidoDTO novoPedidoDTO) {
        var pedido = pedidoMapper.map(novoPedidoDTO);
        var pedidoCadastrado = pedidoService.criarPedido(pedido);
        return ResponseEntity.ok(pedidoCadastrado.getCodigo());
    }
}
