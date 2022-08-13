package com.ceiba.biblioteca.model.service;

import com.ceiba.biblioteca.model.dto.BuscarRegistroDTO;
import com.ceiba.biblioteca.model.dto.RegistroDTO;
import com.ceiba.biblioteca.model.entity.PrestamoEntity;
import com.ceiba.biblioteca.model.repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class PrestamoServiceImpl implements  IPrestamoService{
    @Autowired
    PrestamoRepository prestamoRepository;
    @Override
    public ResponseEntity<HashMap<String, String>> registrarPrestamo(RegistroDTO registroDTO) {

        int tipoUsuario = registroDTO.getTipoUsuario(); // Obtenemos que tipo de usuario llega del body 1-2-3
        String identificacionUsuario = registroDTO.getIdentificacionUsuario();
        HashMap<String, String> respuestaServidor = new HashMap<String, String>();

        if (!validarTipoUsuarioValido(tipoUsuario)){ // usamos el operador '!' para indicar cuando el usuario sea invalido
            respuestaServidor.put("mensaje",  "Tipo de usuario no permitido en la biblioteca");
            return new ResponseEntity<>( respuestaServidor, HttpStatus.BAD_REQUEST );
        }

        if(tipoUsuario == 3){
            if(usuarioTienePrestamo(identificacionUsuario)){ // Validamos que el usuario invitado tenga una prestamo activo
                String message = "El usuario con identificación "+identificacionUsuario+" ya tiene un libro prestado por lo cual no se le puede realizar otro préstamo";
                respuestaServidor.put("mensaje",  message);
                return new ResponseEntity<>( respuestaServidor, HttpStatus.BAD_REQUEST );
            }
        }
        // con esto sabremos cuantos dias habiles tiene cada tipo de usuario
        // Si el usuario es tipo 1 devolvera 10, si es tipo 2 devolvera 8 y asi en caso de tener mas usuarios sera mas facil validar
        HashMap<Integer, Integer> tipoUsuarioMap = new HashMap<Integer, Integer>();
        tipoUsuarioMap.put(1,10);
        tipoUsuarioMap.put(2,8);
        tipoUsuarioMap.put(3,7);

        LocalDate fechaDevolucion = LocalDate.now().plusDays(contarDiasHabiles(tipoUsuarioMap.get(tipoUsuario)));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fecha = formatter.format(fechaDevolucion);
        PrestamoEntity prestamoEntity = new PrestamoEntity(
                identificacionUsuario,
                registroDTO.getIsbn(),registroDTO.getTipoUsuario(),
                fechaDevolucion,
                fecha
        );
        prestamoRepository.save(prestamoEntity);
        Optional<PrestamoEntity> pres = prestamoRepository.findById(prestamoEntity.getIdPrestamo());
        respuestaServidor.put("id",  String.valueOf(pres.get().getIdPrestamo()));
        respuestaServidor.put("fechaMaximaDevolucion",  pres.get().getFechaDevolucionString());
        return ResponseEntity.ok(respuestaServidor);
    }

    @Override
    public boolean usuarioTienePrestamo(String identificacionUsuario) {
        List<PrestamoEntity> listaPrestamos;
        listaPrestamos = prestamoRepository.findAll();
        for (PrestamoEntity prestamo: listaPrestamos ) {
            if (prestamo.getIdUsuario().equals(identificacionUsuario)){
                return true;
            }
        }
        return false;
    }
    @Override
    public BuscarRegistroDTO buscarPrestamo(int id) {
        Optional<PrestamoEntity> prestamoEncontrado = prestamoRepository.findById(id);
        if (prestamoEncontrado.isPresent()){
            return new BuscarRegistroDTO(prestamoEncontrado.get().getIdPrestamo(),
                    prestamoEncontrado.get().getIdLibro(),
                    prestamoEncontrado.get().getIdUsuario(),
                    prestamoEncontrado.get().getTipoUsuario(),
                    prestamoEncontrado.get().getFechaDevolucionString());
        }
        return new BuscarRegistroDTO(1,"Clean Code","1010122859",1,prestamoEncontrado.get().getFechaDevolucionString());
    }

    public int contarDiasHabiles(int fin){
        LocalDate fechaActual = LocalDate.now();
        int diasHabiles = fin;
        int daysToAdd = 0;
        for (int i = 0; i < diasHabiles; i++) {
            fechaActual = fechaActual.plusDays(1);
            if(fechaActual.getDayOfWeek().equals(DayOfWeek.SUNDAY) || fechaActual.getDayOfWeek().equals(DayOfWeek.SATURDAY) ){
                diasHabiles++;
            }
            daysToAdd++;
        }
        return diasHabiles;
    }
    public boolean validarTipoUsuarioValido(int tipoUsuario){
        return !(tipoUsuario > 3 || tipoUsuario < 1);
    }
}
