package es.ariel.app.models.service;

import es.ariel.app.models.entity.Contacto;

public interface IContactoService  {

	public void save(Contacto contacto);
	
	public Contacto findOne(Long id);
}
