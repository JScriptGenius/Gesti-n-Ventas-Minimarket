package com.jarellano.controller;

import com.jarellano.repository.EmpleadoRepository;
import com.jarellano.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/empleado")
public class EmpleadoController {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private EmpleadoService empleadoService;

    /*EMPLEADO ENDPOINTS*/
    @GetMapping("/listaEmpleados")
    public String listaEmpleados(Model model) {
        model.addAttribute("empleados",empleadoRepository.findAllEmpleados());
        return "/empleado/listaEmpleados";
    }
}
