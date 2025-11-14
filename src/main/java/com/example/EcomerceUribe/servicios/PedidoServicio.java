package com.example.EcomerceUribe.servicios;

import com.example.EcomerceUribe.modelos.Cliente;
import com.example.EcomerceUribe.modelos.DTOS.PedidoDTO;
import com.example.EcomerceUribe.modelos.Pedido;
import com.example.EcomerceUribe.modelos.Producto;
import com.example.EcomerceUribe.modelos.mapas.IPedidoMapa;
import com.example.EcomerceUribe.repositorios.IClienteRepositorio;
import com.example.EcomerceUribe.repositorios.IPedidoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoServicio {

    @Autowired
    private IPedidoRepositorio repositorio;

    @Autowired
    private IClienteRepositorio clienteRepositorio;

    @Autowired
    private IPedidoMapa mapa;

    public PedidoDTO guardarPedido(Pedido pedido) {
        validarPedido(pedido);

        Cliente cliente = clienteRepositorio.findById(pedido.getCliente().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No se encontró el cliente con id " + pedido.getCliente().getId()));
        pedido.setCliente(cliente);

        if (pedido.getFechaCreacion() == null) {
            pedido.setFechaCreacion(LocalDate.now());
        }
        if (pedido.getFechaEntrega() != null && pedido.getFechaEntrega().isBefore(pedido.getFechaCreacion())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "La fecha de entrega no puede ser anterior a la fecha de creación");
        }

        if (pedido.getProductos() != null) {
            for (Producto producto : pedido.getProductos()) {
                producto.setPedido(pedido);
            }
        }

        Pedido pedidoGuardado = this.repositorio.save(pedido);
        if (pedidoGuardado == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo guardar el pedido");
        }
        return this.mapa.convertir_pedido_a_pedidodto(pedidoGuardado);
    }

    public List<PedidoDTO> listarPedidos() {
        List<Pedido> pedidos = this.repositorio.findAll();
        return this.mapa.convertir_lista_pedido_a_listapedidodto(pedidos);
    }

    public PedidoDTO buscarPedidoPorId(Integer id) {
        Optional<Pedido> pedido = this.repositorio.findById(id);
        if (pedido.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró el pedido con id " + id);
        }
        return this.mapa.convertir_pedido_a_pedidodto(pedido.get());
    }

    public void eliminarPedido(Integer id) {
        Optional<Pedido> pedido = this.repositorio.findById(id);
        if (pedido.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró el pedido con id " + id);
        }
        try {
            this.repositorio.delete(pedido.get());
        } catch (Exception error) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "No se pudo eliminar el pedido: " + error.getMessage());
        }
    }

    public PedidoDTO actualizarPedido(Integer id, Pedido datosActualizados) {
        Optional<Pedido> pedidoBusqueda = this.repositorio.findById(id);
        if (pedidoBusqueda.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró el pedido con id " + id);
        }
        Pedido pedidoEncontrado = pedidoBusqueda.get();

        if (datosActualizados.getMontoTotal() != null) {
            if (datosActualizados.getMontoTotal() < 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El monto total debe ser positivo");
            }
            pedidoEncontrado.setMontoTotal(datosActualizados.getMontoTotal());
        }
        if (datosActualizados.getCostoEnvio() != null) {
            if (datosActualizados.getCostoEnvio() < 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El costo de envío debe ser positivo");
            }
            pedidoEncontrado.setCostoEnvio(datosActualizados.getCostoEnvio());
        }
        if (datosActualizados.getFechaCreacion() != null) {
            pedidoEncontrado.setFechaCreacion(datosActualizados.getFechaCreacion());
        }
        if (datosActualizados.getFechaEntrega() != null) {
            if (pedidoEncontrado.getFechaCreacion() != null
                    && datosActualizados.getFechaEntrega().isBefore(pedidoEncontrado.getFechaCreacion())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "La fecha de entrega no puede ser anterior a la fecha de creación");
            }
            pedidoEncontrado.setFechaEntrega(datosActualizados.getFechaEntrega());
        }
        if (datosActualizados.getCliente() != null && datosActualizados.getCliente().getId() != null) {
            Cliente cliente = clienteRepositorio.findById(datosActualizados.getCliente().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "No se encontró el cliente con id " + datosActualizados.getCliente().getId()));
            pedidoEncontrado.setCliente(cliente);
        }

        Pedido pedidoActualizado = this.repositorio.save(pedidoEncontrado);
        if (pedidoActualizado == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "No se pudo actualizar el pedido, intenta nuevamente");
        }
        return this.mapa.convertir_pedido_a_pedidodto(pedidoActualizado);
    }

    private void validarPedido(Pedido pedido) {
        if (pedido.getCliente() == null || pedido.getCliente().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El pedido debe estar asociado a un cliente válido");
        }
        if (pedido.getMontoTotal() == null || pedido.getMontoTotal() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El monto total del pedido es obligatorio y debe ser positivo");
        }
        if (pedido.getCostoEnvio() == null || pedido.getCostoEnvio() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El costo de envío es obligatorio y debe ser positivo");
        }
    }
}
