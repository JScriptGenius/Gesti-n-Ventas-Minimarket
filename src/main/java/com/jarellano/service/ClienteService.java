package com.jarellano.service;

import com.jarellano.entities.Cliente;
import java.util.*;

public interface ClienteService {
    Cliente saveCliente(Cliente cliente);
    Optional<Cliente> findClienteById(Long id);
    void updateCliente(Cliente cliente);
}
