package com.ceiba.biblioteca.model.service;

import com.ceiba.biblioteca.model.dto.BuscarRegistroDTO;
import com.ceiba.biblioteca.model.dto.RegistroDTO;
import com.ceiba.biblioteca.model.entity.PrestamoEntity;
import com.ceiba.biblioteca.model.repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;

@Service
public class PrestamoServiceImpl implements  IPrestamoService{

    @Autowired
    PrestamoRepository prestamoRepository;

    @Override
    public ResponseEntity<HashMap<String, String>> guardarPrestamo(RegistroDTO registroDTO) {
        int tipoUsuario = registroDTO.getTipoUsuario();
        String idUser = registroDTO.getIdentificacionUsuario();

        if(tipoUsuario > 3 || tipoUsuario < 1){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("mensaje",  "Tipo de usuario no permitido en la biblioteca");
            return new ResponseEntity<>(
                    map,
                    HttpStatus.BAD_REQUEST
            );
        }
        if(tipoUsuario == 3){
            if(findByIdPrestamo(idUser)){
                HashMap<String, String> map = new HashMap<String, String>();
                String message = "El usuario con identificación "+idUser+" ya tiene un libro prestado por lo cual no se le puede realizar otro préstamo";
                map.put("mensaje",  message);
                return new ResponseEntity<>(
                        map,
                        HttpStatus.BAD_REQUEST
                );
            }
        }

        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaDevolucion;
        if(tipoUsuario == 1){
            fechaDevolucion = LocalDate.now().plusDays(contarDiasHabiles(10));
        }else if(tipoUsuario == 2){
            fechaDevolucion = LocalDate.now().plusDays(contarDiasHabiles(8));
        }else {
            fechaDevolucion = LocalDate.now().plusDays(contarDiasHabiles(7));
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String fecha = formatter.format(fechaDevolucion);

        PrestamoEntity responseData = new PrestamoEntity(
                idUser,
                registroDTO.getIsbn(),registroDTO.getTipoUsuario(),
                fechaActual,
                fechaDevolucion,
                fecha
        );

        prestamoRepository.save(responseData);
        Optional<PrestamoEntity> pres = prestamoRepository.findById(responseData.getIdPrestamo());
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("id",  String.valueOf(pres.get().getIdPrestamo()));
        map.put("fechaMaximaDevolucion",  pres.get().getFechaDevolucionString());
        return ResponseEntity.ok(map);

    }

    @Override
    public boolean findByIdPrestamo(String idUsuario) {
        List<PrestamoEntity> lista = new ArrayList<>();
        lista = prestamoRepository.findAll();
        //System.out.println("tamaño de la lista: "+ lista.size());

        for (int i = 0; i < lista.size(); i++) {
            System.out.println("ID EN LA TABLA: "+ lista.get(i).getIdUsuario());
            if(lista.get(i).getIdUsuario().equals(idUsuario)){
                return true;
            }
        }
        return false;
    }

    @Override
    public BuscarRegistroDTO findRegister(int id) {
        Optional<PrestamoEntity> data = prestamoRepository.findById(id);
        if (data.isPresent()){
            return new BuscarRegistroDTO(data.get().getIdPrestamo(),
                    data.get().getIdLibro(),
                    data.get().getIdUsuario(),
                    data.get().getTipoUsuario(),
                    data.get().getFechaDevolucionString());
        }
        return new BuscarRegistroDTO();

    }

    public int contarDiasHabiles(int fin){
        LocalDate fechaActual = LocalDate.now();
        int diasHabiles = fin;
        int daysToAdd = 0;
        for (int i = 0; i < diasHabiles; i++) {

            fechaActual = fechaActual.plusDays(1);
            if(fechaActual.getDayOfWeek().equals(DayOfWeek.SUNDAY) || fechaActual.getDayOfWeek().equals(DayOfWeek.SATURDAY) ){
                diasHabiles++;
                //System.out.println("Hoy es " + fechaActual.getDayOfWeek());
            }
            daysToAdd++;

        }
        // System.out.println("DIAS HABILES "+diasHabiles);
        return diasHabiles;
    }



}
