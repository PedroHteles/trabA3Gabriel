package repository;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    void create();

    List<T> getAll();

    void delete();

    Optional<T> findById(Long id);

    Optional<T> get();

    void atualizarNomeCliente();
}