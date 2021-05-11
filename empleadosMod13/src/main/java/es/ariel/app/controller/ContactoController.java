package es.ariel.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.ariel.app.models.entity.Contacto;
import es.ariel.app.models.entity.Empleado;
import es.ariel.app.models.service.IContactoService;
import es.ariel.app.models.service.IEmpleadoService;

@Controller
@SessionAttributes("contacto")
public class ContactoController {
	
	@Autowired
	private IContactoService contactoService;
	
	@Autowired
	private IEmpleadoService  empleadoService;
	
	@GetMapping("/contacto/{empleadoId}")
	public String crear (@PathVariable(value = "empleadoId") Long empleadoId,Model model) { 		
		
		Empleado empleado = empleadoService.findOne(empleadoId);
		Contacto contacto = new Contacto();
		contacto.setEmpleado(empleado);
		model.addAttribute("titulo", "Formulario de contacto" );
		model.addAttribute("enviar", "crear contacto");
		model.addAttribute("contacto", contacto);
		return "contacto";
	}
	
	@PostMapping("/contacto")
	public String guardar (@Valid @ModelAttribute("contacto") Contacto contacto, BindingResult result, Model model, 
			                RedirectAttributes flash,SessionStatus status) { //recibe el objeto del formulario
		
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de contacto" );
			model.addAttribute("enviar", "crear contacto");
			return "contacto";
		}
		
		String mensaje = (contacto.getId()!=null)?"Contacto editado con exito":"Contacto creado con exito";
		contactoService.save(contacto);//guardamos el obj en BD
		status.setComplete();// elimino el objeto contacto de la sesion (@SessionAttributes("contacto"))
		flash.addFlashAttribute("success", mensaje); //mensaje hacia la vista
		
		return "redirect:listar";
	}

	@GetMapping("/verContacto/{id}")
	public String editarContacto(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
		Contacto contacto = new Contacto();
		
		if (id > 0) {			
			contacto = contactoService.findOne(id);			
			if (contacto == null) {
				flash.addFlashAttribute("error", "El id del contacto no existe!"); // mensaje hacia la vista
				return "redirect:/listar";
			}
		} else {
			flash.addFlashAttribute("error", "El id del contacto no puede ser cero!"); // mensaje hacia la vista
			return "redirect:/listar";
		}		

		model.addAttribute("titulo", "Ver/Editar contacto");
		model.addAttribute("enviar", "Editar contacto");
		model.addAttribute("contacto", contacto);
		return "contacto";
	}

}
