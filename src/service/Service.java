package service;

import model.Cliente;
import java.util.List;
import java.util.Optional;

public interface Service {

    Optional<Cliente> getCliente(Long id);
    List<Cliente> getClientes();
}
