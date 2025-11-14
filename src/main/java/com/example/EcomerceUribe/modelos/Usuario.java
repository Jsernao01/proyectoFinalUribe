package com.example.EcomerceUribe.modelos;

import com.example.EcomerceUribe.ayudas.EstadosUsuario;
import com.example.EcomerceUribe.ayudas.TipoDocumento;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="names",nullable = false, unique = false, length = 50)
    private String nombres;
    @Column(name="email",nullable = false, unique = true, length = 50)
    private String correo;
    @Column(name="password",nullable = false, unique = false, length = 10)
    private String contraseña;
    @Enumerated(EnumType.STRING)
    @Column(name="status",nullable = false, unique = false)
    private EstadosUsuario estado;
    @Column(name="dateOfBirth",nullable = true, unique = false)
    private LocalDate fechaNacimiento;
    @Enumerated(EnumType.STRING)
    @Column(name="idType",nullable = false, unique = false)
    private TipoDocumento tipoDocumento;
    @Column(name="document",nullable = false, unique = true, length = 12)
    private String documento;

    //Creando una relacion de 1 a 1 con Empleado
    @OneToOne(mappedBy = "usuario")
    @JsonBackReference(value = "relacionempleadousuario")
    private Empleado empleado;

    public Usuario() {
    }

    public Usuario(Integer id, String nombres, String correo, String contraseña, EstadosUsuario estado,
                   LocalDate fechaNacimiento, TipoDocumento tipoDocumento, String documento) {
        this.id = id;
        this.nombres = nombres;
        this.correo = correo;
        this.contraseña = contraseña;
        this.estado = estado;
        this.fechaNacimiento = fechaNacimiento;
        this.tipoDocumento = tipoDocumento;
        this.documento = documento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public EstadosUsuario getEstado() {
        return estado;
    }

    public void setEstado(EstadosUsuario estado) {
        this.estado = estado;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }
}
