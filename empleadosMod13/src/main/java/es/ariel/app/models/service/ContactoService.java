package es.ariel.app.models.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.ariel.app.models.dao.IContactoDao;
import es.ariel.app.models.entity.Contacto;

@Service
public class ContactoService implements IContactoService{

	@Autowired
	private IContactoDao contactoDao;
	
	@Override
	@Transactional
	public void save(Contacto contacto) {
		contactoDao.save(contacto);
		
	}

	@Override
	@Transactional(readOnly = true) 
	public Contacto findOne(Long id) {
		
		return contactoDao.findById(id).orElse(null);
	}

}
