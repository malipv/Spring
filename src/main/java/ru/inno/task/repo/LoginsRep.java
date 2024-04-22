package ru.inno.task.repo;

import org.springframework.data.repository.CrudRepository;
import ru.inno.task.model.Logins;

// Объявление репозитория для сущности logins
public interface LoginsRep extends CrudRepository<Logins, Integer> {}