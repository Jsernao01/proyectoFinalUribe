package com.example.EcomerceUribe.modelos.DTOS;

import com.example.EcomerceUribe.ayudas.Cargos;
import com.example.EcomerceUribe.ayudas.Sedes;

public class EmpleadoCrearDTO {

    private Cargos cargo;
    private Integer salario;
    private Sedes sede;
    private Integer usuarioId;

    public EmpleadoCrearDTO() {
    }

    public EmpleadoCrearDTO(Cargos cargo, Integer salario, Sedes sede, Integer usuarioId) {
        this.cargo = cargo;
        this.salario = salario;
        this.sede = sede;
        this.usuarioId = usuarioId;
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

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }
}
