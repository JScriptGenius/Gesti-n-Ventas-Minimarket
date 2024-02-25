package com.jarellano.security;

import com.jarellano.entities.Empleado;
import com.jarellano.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    HttpSession session;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Empleado> empleadoOptional = empleadoService.findEmpleadoByDni(username);
        if (empleadoOptional.isPresent()) {
            session.setAttribute("sid_empleado", empleadoOptional.get().getIdEmpleado());
            Empleado empleado = empleadoOptional.get();
            return User.builder()
                    .username(empleado.getDni())
                    .password(empleado.getPassword())
                    .roles()
                    .build();
        } else {
            throw new UsernameNotFoundException("¡Empleado no encontrado!");
        }
    }
}