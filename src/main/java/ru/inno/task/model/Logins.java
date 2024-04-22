package ru.inno.task.model;

import jakarta.persistence.*;
import lombok.Data;
import ru.inno.task.model.Users;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Соотвествует таблице logins в базе Postgres
@Data
@Entity
public class Logins {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime access_date;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    private String application;

    public void setAccessDate(String date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        this.access_date = LocalDateTime.parse(date, dateTimeFormatter);
    }
}