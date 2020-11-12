package com.spring.web.rest;


import com.spring.web.model.Jedi;
import com.spring.web.service.JediService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class JediResource {

/*    @Autowired
    private JediRepository repository;*/

    //OS METODOS IRAO PARA Service/JediService -> REFATORACAO
    @Autowired
    private JediService service;


    //http://localhost:8080/api/jedi
   /* @GetMapping("/api/jedi")
    public List<Jedi> getAllJedi() {
        return repository.findAll();
    }*/

    @GetMapping("/api/jedi")
    public List<Jedi> getAllJedi() {
        return service.findAll();
    }


    //http://localhost:8080/api/jedi/1
    /*@GetMapping("/api/jedi/{id}")
    public ResponseEntity<Jedi> getJedi(@PathVariable("id") Long id, HttpResponse response) {
        //se nao existir id devido ao get() gera erro exception
        //return repository.findById(id).get(); //coloca get() devido optional

        final Optional<Jedi> jedi = repository.findById(id);

        if(jedi.isPresent()) {
            //ResponseEntity.ok retorna status 200
            return ResponseEntity.ok(jedi.get());
        } else {
            //throw new JediNotFoundException();
            return ResponseEntity.notFound().build();
        }
    }*/

    @GetMapping("/api/jedi/{id}")
    public ResponseEntity<Jedi> getJedi(@PathVariable("id") Long id) {
        final Jedi jedi = service.findById(id);

        return ResponseEntity.ok(jedi);
    }

    /*@PostMapping("/api/jedi")
    @ResponseStatus(HttpStatus.CREATED)
    //@RequestBody transforma json em modelo Jedi
    public Jedi createJedi(@Valid @RequestBody Jedi jedi) {
        return repository.save(jedi);
    }*/

    @PostMapping("/api/jedi")
    @ResponseStatus(HttpStatus.CREATED)
    public Jedi createJedi(@Valid @RequestBody Jedi jedi){
        return service.save(jedi);
    }

    /*@PutMapping("/api/jedi/{id}")
    public ResponseEntity<Jedi> updateJedi(@Valid @RequestBody Jedi dto) {
        final Optional<Jedi> jediEntity = repository.findById(id);
        final Jedi jedi;

        if(jediEntity.isPresent()) {
            jedi = jediEntity.get();
        } else {
            return ResponseEntity.notFound().build();
        }

        jedi.setName(dto.getName());
        jedi.setLastName(dto.getLastName());

        return ResponseEntity.ok(repository.save(jedi));
    }*/

    @PutMapping("/api/jedi/{id}")
    public ResponseEntity<Jedi> updateJedi(@PathVariable("id") Long id, @Valid @RequestBody Jedi dto) {

        final Jedi jedi = service.update(id, dto);

        return ResponseEntity.ok(jedi);

    }

    /*@DeleteMapping("/api/jedi/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        final Optional<Jedi> jedi = repository.findById(id);

        if(jediEntity.isPresent()) {
            repository.delete(jedi.get());
            return ResponseEntity.noContent().build(); //return 204 deletado
        } else {
            return ResponseEntity.notFound().build(); //404
        }
    }*/

    @DeleteMapping("/api/jedi/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }

}
