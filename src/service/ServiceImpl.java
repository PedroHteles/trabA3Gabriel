package service;

import model.Cliente;
import model.ClienteDto;
import repository.imp.ClienteImplDaoImpl;

import java.util.ArrayList;
import java.util.Optional;

public class ServiceImpl implements Service {

    private final ClienteImplDaoImpl clienteImplDaoImpl = new ClienteImplDaoImpl();

    public ClienteImplDaoImpl serviceCliente() {
        return clienteImplDaoImpl;
    }

    @Override
    public Optional<Cliente> getCliente(Long id) {
        Optional<ClienteDto> clienteDto = clienteImplDaoImpl.findById(id);

        if (clienteDto.isPresent()) {
            Cliente cliente = new Cliente(clienteDto.get());
            return Optional.of(cliente);
        } else return Optional.empty();
    }

    @Override
    public ArrayList<Cliente> getClientes() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        clienteImplDaoImpl.getAll().forEach(e -> {
            Cliente cliente = new Cliente(e);
            clientes.add(cliente);
        });
        return clientes;
    }
}
