package es.ariel.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.ariel.app.models.dao.IEmpleadoDao;
import es.ariel.app.models.entity.Empleado;

@Service
public class EmpleadoService implements IEmpleadoService {

	@Autowired
	private IEmpleadoDao empleadoDao;
	
	
	@Override
	@Transactional
	public void save(Empleado empleado) {
		empleadoDao.save( empleado);

	}

	@Override
	@Transactional(readOnly = true) 
	public Empleado findOne(Long id) {	
		return empleadoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		empleadoDao.deleteById(id);

	}

	@Override
	@Transactional(readOnly = true) 
	public List<Empleado> findAll() {		
		return (List<Empleado>) empleadoDao.findAll();
	}

	
	@Override
	@Transactional(readOnly = true) 
	public Page<Empleado> findAll(Pageable page) {
		
		return empleadoDao.findAll(page);
	}

}
