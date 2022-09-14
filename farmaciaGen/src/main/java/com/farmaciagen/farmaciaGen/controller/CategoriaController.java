package com.farmaciagen.farmaciaGen.controller;

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

import com.farmaciagen.farmaciaGen.model.Categoria;
import com.farmaciagen.farmaciaGen.repository.CategoriaRepository;

@RestController
@RequestMapping("/categoria")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriaController  {
	
	@Autowired
	private CategoriaRepository repositoty;
	
	@GetMapping
	public ResponseEntity<List<Categoria>> getAll(){
		return ResponseEntity.ok(repositoty.findAll());
		
	}
	
	@GetMapping("/descricao/{descricao}")
	public ResponseEntity<List<Categoria>> getByTitle(@PathVariable String descricao){
		return ResponseEntity.ok(repositoty
				.findAllByDescricaoContainingIgnoreCase(descricao));
	}
	
	 @PostMapping
	    public ResponseEntity<Categoria> post(@Valid @RequestBody Categoria categoria){
	        return ResponseEntity.status(HttpStatus.CREATED)
	                .body(repositoty.save(categoria));
	    }
	    
	    @PutMapping
	    public ResponseEntity<Categoria> put(@Valid @RequestBody Categoria categoria){
	        return repositoty.findById(categoria.getId())
	            .map(resposta -> ResponseEntity.status(HttpStatus.CREATED)
	            .body(repositoty.save(categoria)))
	            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	    }
	    
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    @DeleteMapping("/{id}")
	    public void delete(@PathVariable Long id) {
	        Optional<Categoria> categoria = repositoty.findById(id);
	        
	        if(categoria.isEmpty())
	            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	        
	       repositoty.deleteById(id);
	    }
}
