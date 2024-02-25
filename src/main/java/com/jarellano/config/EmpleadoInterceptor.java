package com.jarellano.config;

import com.jarellano.entities.Empleado;
import com.jarellano.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.*;
import java.util.Optional;

@Component
public class EmpleadoInterceptor implements HandlerInterceptor {

    @Autowired
    private EmpleadoService empleadoService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("sid_empleado") != null) {
            Long empleadoId = Long.parseLong(session.getAttribute("sid_empleado").toString());
            Optional<Empleado> optionalEmpleado = empleadoService.findEmpleadoById(empleadoId);
            if (optionalEmpleado.isPresent()) {
                request.setAttribute("empleado", optionalEmpleado.get());
            }
        }
        return true;
    }
}
