package es.ariel.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import es.ariel.app.models.entity.Contacto;

public interface IContactoDao extends CrudRepository<Contacto, Long> {

}
