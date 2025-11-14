package com.example.EcomerceUribe.modelos;

import com.example.EcomerceUribe.ayudas.Cargos;
import com.example.EcomerceUribe.ayudas.Sedes;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(name="empleados")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name="rol",nullable = false, unique = false)
    private Cargos cargo;
    @Column(name="salary",nullable = false, unique = false)
    private Integer salario;
    @Enumerated(EnumType.STRING)
    @Column(name="site",nullable = false, unique = false)
    private Sedes sede;

    @OneToOne
    @JoinColumn(name = "fk_usuario", referencedColumnName = "id")
    @JsonManagedReference(value = "relacionempleadousuario")
    private Usuario usuario;

    public Empleado() {
    }

    public Empleado(Integer id, Cargos cargo, Integer salario, Sedes sede) {
        this.id = id;
        this.cargo = cargo;
        this.salario = salario;
        this.sede = sede;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cargos getCargo() {
        return cargo;
    }

    public void setCargo(Cargos cargo) {
        this.cargo = cargo;
    }

    public Integer getSalario() {
        return salario;
    }

    public void setSalario(Integer salario) {
        this.salario = salario;
    }

    public Sedes getSede() {
        return sede;
    }

    public void setSede(Sedes sede) {
        this.sede = sede;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
