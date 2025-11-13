package com.example.EcomerceUribe.servicios;

import com.example.EcomerceUribe.modelos.DTOS.ProductoDTO;
import com.example.EcomerceUribe.modelos.Pedido;
import com.example.EcomerceUribe.modelos.Producto;
import com.example.EcomerceUribe.modelos.mapas.IProductoMapa;
import com.example.EcomerceUribe.repositorios.IPedidoRepositorio;
import com.example.EcomerceUribe.repositorios.IProductoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServicio {

    @Autowired
    private IProductoRepositorio repositorio;

    @Autowired
    private IPedidoRepositorio pedidoRepositorio;

    @Autowired
    private IProductoMapa mapa;

    public ProductoDTO guardarProducto(Producto producto) {
        validarProducto(producto);

        Pedido pedido = pedidoRepositorio.findById(producto.getPedido().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No se encontró el pedido con id " + producto.getPedido().getId()));
        producto.setPedido(pedido);

        Producto productoGuardado = this.repositorio.save(producto);
        if (productoGuardado == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo guardar el producto");
        }
        return this.mapa.convertir_producto_a_productodto(productoGuardado);
    }

    public List<ProductoDTO> listarProductos() {
        List<Producto> productos = this.repositorio.findAll();
        return this.mapa.convertir_lista_producto_a_listaproductodto(productos);
    }

    public ProductoDTO buscarProductoPorId(Integer id) {
        Optional<Producto> producto = this.repositorio.findById(id);
        if (producto.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró el producto con id " + id);
        }
        return this.mapa.convertir_producto_a_productodto(producto.get());
    }

    public void eliminarProducto(Integer id) {
        Optional<Producto> producto = this.repositorio.findById(id);
        if (producto.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró el producto con id " + id);
        }
        try {
            this.repositorio.delete(producto.get());
        } catch (Exception error) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "No se pudo eliminar el producto: " + error.getMessage());
        }
    }

    public ProductoDTO actualizarProducto(Integer id, Producto datosActualizados) {
        Optional<Producto> productoBusqueda = this.repositorio.findById(id);
        if (productoBusqueda.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró el producto con id " + id);
        }
        Producto productoEncontrado = productoBusqueda.get();

        if (datosActualizados.getNombre() != null && !datosActualizados.getNombre().isBlank()) {
            productoEncontrado.setNombre(datosActualizados.getNombre());
        }
        if (datosActualizados.getFotografia() != null) {
            productoEncontrado.setFotografia(datosActualizados.getFotografia());
        }
        if (datosActualizados.getDescripcion() != null) {
            productoEncontrado.setDescripcion(datosActualizados.getDescripcion());
        }
        if (datosActualizados.getCategoria() != null) {
            productoEncontrado.setCategoria(datosActualizados.getCategoria());
        }
        if (datosActualizados.getPrecioUnitario() != null) {
            if (datosActualizados.getPrecioUnitario() < 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El precio unitario debe ser positivo");
            }
            productoEncontrado.setPrecioUnitario(datosActualizados.getPrecioUnitario());
        }
        if (datosActualizados.getMarca() != null && !datosActualizados.getMarca().isBlank()) {
            productoEncontrado.setMarca(datosActualizados.getMarca());
        }
        productoEncontrado.setAplicaDescuento(datosActualizados.isAplicaDescuento());

        if (datosActualizados.getPedido() != null && datosActualizados.getPedido().getId() != null) {
            Pedido pedido = pedidoRepositorio.findById(datosActualizados.getPedido().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "No se encontró el pedido con id " + datosActualizados.getPedido().getId()));
            productoEncontrado.setPedido(pedido);
        }

        Producto productoActualizado = this.repositorio.save(productoEncontrado);
        if (productoActualizado == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "No se pudo actualizar el producto, intenta nuevamente");
        }
        return this.mapa.convertir_producto_a_productodto(productoActualizado);
    }

    private void validarProducto(Producto producto) {
        if (producto.getNombre() == null || producto.getNombre().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre del producto es obligatorio");
        }
        if (producto.getCategoria() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La categoría del producto es obligatoria");
        }
        if (producto.getPrecioUnitario() == null || producto.getPrecioUnitario() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El precio unitario es obligatorio y debe ser positivo");
        }
        if (producto.getMarca() == null || producto.getMarca().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La marca del producto es obligatoria");
        }
        if (producto.getPedido() == null || producto.getPedido().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El producto debe estar asociado a un pedido válido");
        }
    }
}
