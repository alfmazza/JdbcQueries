package com.sa.jdbc.controller;

import com.sa.jdbc.exceptions.NotFoundException;
import com.sa.jdbc.model.Properties;
import com.sa.jdbc.service.PropertiesDAO;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jdbc")
public class PropertiesController {
    
    @Autowired
    private PropertiesDAO pDAO;
    
    
    
    @GetMapping("")
    public ResponseEntity<?> findAll() throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(pDAO.findAll());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"notice\":\"Notice. No se encontraron registros.(CODE 404)\",\"timestamp\":\"" + LocalDateTime.now() + "\"}");
        }
    }
    
    //Paginado general
    @GetMapping("/paged")
    public ResponseEntity<?> findAll(Pageable pageable) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(pDAO.findAll(pageable));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"notice\":\"Notice. No se encontraron registros.(CODE 404)\",\"timestamp\":\"" + LocalDateTime.now() + "\"}");
        }
    }
    
    //Búsqueda por filtro según query: SELECT * FROM healthplan_properties WHERE copay > 100 AND orthodontics LIKE ?
    @GetMapping("/search")
    public ResponseEntity<?> findByFilter(@RequestParam String filter) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(pDAO.findByFilter(filter));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"notice\":\"Notice. No se encontro el registros que coincidan.(CODE 404)\",\"timestamp\":\"" + LocalDateTime.now() + "\"}");
        }
    }
    
    //Búsqueda por filtro anterior y paginada.
    @GetMapping("/searchpaged")
    public ResponseEntity<?> findByFilterPaged(Pageable pageable, @RequestParam String filter) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(pDAO.findByFilterPaged(pageable, filter));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"notice\":\"Notice. No se encontro el registros que coincidan.(CODE 404)\",\"timestamp\":\"" + LocalDateTime.now() + "\"}");
        }
    
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) throws Exception{
        try {
            return ResponseEntity.status(HttpStatus.OK).body(pDAO.findById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"notice\":\"Notice. No se encontró el registro.(CODE 404)\",\"timestamp\":\"" + LocalDateTime.now() + "\"}");
        }
    }
    
    //Guarda el registro en la base de datos pero devuelve int. (aprox 7ms / 170b Response size)
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Properties proper) throws Exception{
        
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(pDAO.save(proper));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"notice\":\"Error.Intente nuevamente más tarde (CODE 400)\",\"timestamp\":\"" + LocalDateTime.now() + "\"}");
        }
    }
    
    //Guarda el registro en la base de datos y lo devuelve para verificar.(aprox 7ms / 324b Response size) 
    @PostMapping("/save")
    public ResponseEntity<?> save2(@RequestBody Properties proper) {
        
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(pDAO.save2(proper));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"notice\":\"Error.Intente nuevamente más tarde (CODE 400)\",\"timestamp\":\"" + LocalDateTime.now() + "\"}");
        }
    }
   
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
    
        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(pDAO.delete(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"notice\":\"Notice. No se encontro el registro.(CODE 404)\",\"timestamp\":\"" + LocalDateTime.now() + "\"}");
        }
    }
    
    
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Properties proper, @PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(pDAO.update(id, proper));
        } catch(NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"notice\":\"Notice. No se encontro el registro.(CODE 404)\",\"timestamp\":\"" + LocalDateTime.now() + "\"}");
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"notice\":\"Notice. Intente nuevamente más tarde.(CODE 400)\",\"timestamp\":\"" + LocalDateTime.now() + "\"}");
        }
    }
}
