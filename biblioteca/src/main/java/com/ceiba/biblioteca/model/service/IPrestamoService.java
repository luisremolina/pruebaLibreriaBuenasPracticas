package com.ceiba.biblioteca.model.service;

import com.ceiba.biblioteca.model.dto.BuscarRegistroDTO;
import com.ceiba.biblioteca.model.dto.RegistroDTO;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public interface IPrestamoService {
    ResponseEntity<HashMap<String, String>> registrarPrestamo(RegistroDTO RegistroDTO);
    boolean usuarioTienePrestamo (String idUsuario);
    BuscarRegistroDTO buscarPrestamo(int id);
}
