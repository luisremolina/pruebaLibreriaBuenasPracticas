package com.ceiba.biblioteca.model.service;

import com.ceiba.biblioteca.model.dto.RegistroDTO;
import com.ceiba.biblioteca.model.dto.RespuestaDTO;

public interface IPrestamoService {
    RespuestaDTO guardarPrestamo(RegistroDTO RegistroDTO);

    boolean findByIdPrestamo (String idUsuario);
    int countPrestados();
}
