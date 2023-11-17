package repository.imp;

import dB.ConnectionFactory;
import model.ClienteDto;
import repository.Dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ClienteImplDaoImpl extends ConnectionFactory implements Dao<ClienteDto> {
    @Override
    public void create() {
        String email = scString("Digite o email do cliente ou digite 0 para voltar ao menu:");
        if (Objects.equals(email, "0")) return;
        Optional<ClienteDto> byId = this.findByEmail(email);
        try {
            if (byId.isEmpty()) {
                String nome = scString("Digite o nome do cliente:");
                Connection connection = ConnectionFactory.createConnection();
                String query = "INSERT INTO `a3`.`clientes` (`nome`, `email`) VALUES (?, ?);";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, nome);
                ps.setString(2, email);
                int i = ps.executeUpdate();
                if (i == 0) {
                    System.out.println("Cliente nao cadastrado verifique!");
                } else {
                    System.out.println("Cliente cadastrado!");
                }

            } else System.out.println("Cliente ja cadastrado");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<ClienteDto> getAll() {
        final ArrayList<ClienteDto> clientes = new ArrayList<>();
        try {
            Connection connection = ConnectionFactory.createConnection();
            String query = "select * from a3.clientes";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet resulQuery = ps.executeQuery();
            while (resulQuery.next()) clientes.add(resultToObject(resulQuery));
            if (clientes.size() == 0) {
                System.out.println("Nenhuma cliente encontrado\n");
                return new ArrayList<>();
            } else {
                System.out.println(clientes.size());
                return clientes;
            }
        } catch (SQLException se) {
            System.out.println(se);
            return null;

        }
    }

    @Override
    public void delete() {
        this.get().ifPresent(e -> {
            try {
                Connection connection = ConnectionFactory.createConnection();
                String query = "delete from a3.clientes where id = ?";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setLong(1, e.getId());
                int i = ps.executeUpdate();
                if (i == 0) {
                    System.out.println("Erro ao deletar Cliente");
                } else System.out.println("Cliente deletado com sucesso");
            } catch (SQLException se) {
                System.out.println(se);
            }
        });
    }

    @Override
    public Optional<ClienteDto> findById(Long id) {
        try {
            Connection connection = ConnectionFactory.createConnection();
            String query = "select * from a3.clientes where id = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setLong(1, id);
            ResultSet resulQuery = ps.executeQuery();
            while (resulQuery.next()) return Optional.of(resultToObject(resulQuery));
            return Optional.empty();
        } catch (SQLException se) {
            System.out.println(se);
            return Optional.empty();
        }
    }

    @Override
    public Optional<ClienteDto> get() {

        try {
            Connection connection = ConnectionFactory.createConnection();
            String email = scString("Digite o email do cliente ou digite 0 para voltar ao menu:");
            if (email.equals("0")) return Optional.empty();
            String query = "select * from a3.clientes where email = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, email);
            ResultSet resulQuery = ps.executeQuery();
            while (resulQuery.next()) return Optional.of(resultToObject(resulQuery));
            System.out.println("cliente nao encontrado");
            return Optional.empty();
        } catch (SQLException se) {
            System.out.println(se);
            return Optional.empty();
        }
    }


    public Optional<ClienteDto> findByEmail(String email) {
        try {
            Connection connection = ConnectionFactory.createConnection();
            if (email.equals("0")) return Optional.empty();
            String query = "select * from a3.clientes where email = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, email);
            ResultSet resulQuery = ps.executeQuery();
            while (resulQuery.next()) return Optional.of(resultToObject(resulQuery));
            return Optional.empty();
        } catch (SQLException se) {
            System.out.println(se);
            return Optional.empty();
        }
    }


    @Override
    public void atualizarNomeCliente() {
        this.get().ifPresent(e -> {
            try {
                String email = scString("Digite o novo nome do cliente ou digite 0 para voltar ao menu:");
                if (email.equals("0")) return;

                Connection connection = ConnectionFactory.createConnection();
                String query = "UPDATE `a3`.`clientes` SET `nome` = ? WHERE `id` = ?";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, email);
                ps.setLong(2, e.getId());


                int i = ps.executeUpdate();
                if (i == 0) {
                    System.out.println("Erro ao alterar nome do cliente");
                } else System.out.println("nome alterado com sucesso");
            } catch (SQLException se) {
                System.out.println(se);
            }
        });
    }

    private ClienteDto resultToObject(ResultSet resultSet) {
        try {
            ClienteDto clienteTransfer = new ClienteDto(resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3));
            return clienteTransfer;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
