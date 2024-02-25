package com.jarellano.controller;

import com.jarellano.entities.Empleado;
import com.jarellano.repository.EmpleadoRepository;
import com.jarellano.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.*;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private EmpleadoService empleadoService;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /* LOGIN ENDPOINTS */
    @GetMapping("/registrarse")
    public String registrarse() {
        return "/autenticacion/registrar";
    }

    @PostMapping("/registrarEmpleado")
    public String registrarEmpleado(Empleado empleado, RedirectAttributes redirectAttributes) {
        empleado.setPassword(passwordEncoder.encode(empleado.getPassword()));
        empleado.setEstado(1);
        empleadoService.saveEmpleado(empleado);
        redirectAttributes.addFlashAttribute("msgConfirmacionEmp","¡Empleado registrado correctamente!");
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "/autenticacion/login";
    }

    @GetMapping("/acceder")
    public String acceder(Model model, HttpSession session) {
        Optional<Empleado> optionalEmpleado = empleadoService.findEmpleadoById(Long.parseLong(session.getAttribute("sid_empleado").toString()));
        if (optionalEmpleado.isPresent()) {
            session.setAttribute("sid_empleado",optionalEmpleado.get().getIdEmpleado());
            model.addAttribute("empleados",empleadoRepository.findAllEmpleados());
            return "empleado/listaEmpleados";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/cerrarSesion")
    public String cerrarSesion(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }
}
