package es.ariel.app.controller;

import java.util.HashMap;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import es.ariel.app.models.entity.Empleado;

import es.ariel.app.models.service.IEmpleadoService;
import es.ariel.app.paginator.PageRender;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Controller
@SessionAttributes("empleado")
public class EmpleadoController {
	
	@Autowired
	private IEmpleadoService  empleadoService;	
	
	// doble funcion para llenar el select del form crear empleado y para mostrar sueldo en la lista de empleados
	@ModelAttribute("puestos")
	public Map<String,Double> puestos(){
		Map<String,Double>puestos = new HashMap<>();
		
		puestos.put("Soporte tecnico", 1000.00);
		puestos.put("programador junior", 1100.00);
		puestos.put("programador senior", 2100.00);
		puestos.put("RRHH", 1600.00);
		puestos.put("Asistente administrativo", 1000.00);		
		
		return puestos;
	}	
	
	
	@GetMapping({"/index","","/"})
	public String inicio (Model model) { 
		
			
		model.addAttribute("titulo", "app empleados" );
		model.addAttribute("titulo2","Bienvenido a la pagina de inicio  ");	
		return "index";
	}

	@GetMapping("/listar")
	public String listar (@RequestParam (name="page", defaultValue = "0") int page, Model model) {
		
		Pageable pageRequest = PageRequest.of(page, 4);				
		
		Page<Empleado> empleados =empleadoService.findAll(pageRequest);
		
		PageRender<Empleado> pageRender = new PageRender<>("/listar",empleados);
		
		//List <Empleado> empleados = empleadoService.findAll();
		model.addAttribute("titulo", "Listado de empleados" );
		model.addAttribute("empleados", empleados );
		model.addAttribute("page", pageRender);

	return "listar";
	}
	
	
	@GetMapping("/form")
	public String crear (Model model) { 
		
		Empleado empleado = new Empleado();		
		model.addAttribute("titulo", "Formulario de empleado" );
		model.addAttribute("empleado", empleado);			
		return "form";
	}
	
	@PostMapping("/form")
	public String guardar (@Valid @ModelAttribute("empleado") Empleado empleado, BindingResult result, Model model, 
			                RedirectAttributes flash,SessionStatus status) { //recibe el objeto del formulario
		
		boolean nuevo = true;
		if (empleado.getId()!=null) nuevo=false;
		
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de empleado" );
			return "form";
		}		
		String mensaje = (empleado.getId()!=null)?"Empleado editado con exito":"Empleado creado con exito";
		
		empleadoService.save(empleado);//guardamos el obj en BD	
		status.setComplete();// elimino el objeto empleado de la sesion (@SessionAttributes("empleado"))
		flash.addFlashAttribute("success", mensaje); //mensaje hacia la vista		
		
		if (!nuevo) return "redirect:/listar";  //si ya existe 
		else return "redirect:/contacto/"+ empleado.getId();                 // si no lo envio a crear contacto
	}

	@GetMapping("/form/{id}")
	public String editar(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
		Empleado empleado = new Empleado();
		
		if (id > 0) {			
			empleado = empleadoService.findOne(id);		
			if (empleado == null) {
				flash.addFlashAttribute("error", "El id del empleado no existe!"); // mensaje hacia la vista
				return "redirect:/listar";
			}
		} else {
			flash.addFlashAttribute("error", "El id del empleado no puede ser cero!"); // mensaje hacia la vista
			return "redirect:/listar";
		}		

		model.addAttribute("titulo", "Editar empleado");
		model.addAttribute("empleado", empleado);
		return "form";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar (@PathVariable (value="id")Long id,RedirectAttributes flash) {  
		
		if(id>0) {
			empleadoService.delete(id);
			 flash.addFlashAttribute("success", "Empleado eliminado con exito"); //mensaje hacia la vista
		}
		return "redirect:/listar";				
	}

}
