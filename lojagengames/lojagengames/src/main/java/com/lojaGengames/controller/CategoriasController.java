package com.lojaGengames.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.lojaGengames.model.Categorias;
import com.lojaGengames.repository.CategoriasRepository;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriasController {
	 
	 @Autowired
	    private CategoriasRepository repositoty;
	    
	    @GetMapping
	    public ResponseEntity<List<Categorias>> getAll(){
	        return ResponseEntity.ok(repositoty.findAll());
	    }
	    
	    @GetMapping("/{id}")
	    public ResponseEntity<Categorias> getById(@PathVariable Long id){
	        return repositoty.findById(id)
	            .map(resposta -> ResponseEntity.ok(resposta))
	            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	    }
	    
	    @GetMapping("/descricao/{descricao}")
	    public ResponseEntity<List<Categorias>> getBydescricao(@PathVariable 
	    String descricao){
	        return ResponseEntity.ok(repositoty
	            .findAllByDescricaoContainingIgnoreCase(descricao));
	    }
	    
	    @PostMapping
	    public ResponseEntity<Categorias> post(@Valid @RequestBody Categorias categorias){
	        return ResponseEntity.status(HttpStatus.CREATED)
	                .body(repositoty.save(categorias));
	    }
	    
	    @PutMapping
	    public ResponseEntity<Categorias> put(@Valid @RequestBody Categorias categorias){
	        return repositoty.findById(categorias.getId())
	            .map(resposta -> ResponseEntity.status(HttpStatus.CREATED)
	            .body(repositoty.save(categorias)))
	            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	    }
	    
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    @DeleteMapping("/{id}")
	    public void delete(@PathVariable Long id) {
	        Optional<Categorias> categorias = repositoty.findById(id);
	        
	        if(categorias.isEmpty())
	            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	        
	        repositoty.deleteById(id);              
	    }

}
