package com.ceiba.biblioteca.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
@Entity
@Table(name = "prestamo")

public class PrestamoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_prestamo")
    private int idPrestamo;

    @Column(name = "id_usuario", length = 10)
    private String idUsuario;

    @Column(name = "id_libro", length = 10)
    private String idLibro;

    @Column(name = "tipo_usuario")
    private int tipoUsuario;

    private LocalDate fechaPrestamo;

    private LocalDate fechaDevolucion;

    private String fechaDevolucionString;

    public PrestamoEntity() {
    }
    public PrestamoEntity(String idUsuario, String idLibro, int tipoUsuario, LocalDate fechaPrestamo, LocalDate fechaDevolucion, String fechaDevolucionString) {
//        this.idPrestamo = idPrestamo;
        this.idUsuario = idUsuario;
        this.idLibro = idLibro;
        this.tipoUsuario = tipoUsuario;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.fechaDevolucionString = fechaDevolucionString;
    }



    public int getIdPrestamo() {
        return this.idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(String idLibro) {
        this.idLibro = idLibro;
    }

    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public String getFechaDevolucionString() {
        return fechaDevolucionString;
    }

    public void setFechaDevolucionString(String fechaDevolucionString) {
        this.fechaDevolucionString = fechaDevolucionString;
    }
}
