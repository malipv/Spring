package ru.inno.task.repo;

import org.springframework.data.repository.CrudRepository;
import ru.inno.task.model.Users;

import java.util.List;

// Объявление репозитория для сущности users
public interface UsersRep extends CrudRepository<Users, Integer> {
    public List<Users> findByUsernameAndFio(String username, String fio);
    public Users findFirstByUsernameAndFio(String username, String fio);
}