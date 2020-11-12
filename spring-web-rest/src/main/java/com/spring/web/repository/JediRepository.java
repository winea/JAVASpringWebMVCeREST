package com.spring.web.repository;

import com.spring.web.model.Jedi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//CRUD
// Uma forma fácil de fazer isso é criar uma interface que extende a interface JpaRepository (do Spring Data)
//A interface JpaRepository precisa de dois parâmetros do tipo Generics: o primeiro é a entidade JPA que
// representa a tabela e o segundo é o tipo da chave primária (o mesmo tipo do atributo id)

@Repository
public interface JediRepository extends JpaRepository<Jedi, Long> {

    List<Jedi> findByNameContainingIgnoreCase(final String name);

}

