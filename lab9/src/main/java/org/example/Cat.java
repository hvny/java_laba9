package org.example;

import org.example.annotations.Column;
import org.example.annotations.Table;

@Table(title = "animals")
public class Cat {
    @Column(name = "id", type = "INTEGER PRIMARY KEY AUTOINCREMENT")
    private int id;

    @Column(name = "name", type = "TEXT")
    private String name;

    @Column(name = "age", type = "INTEGER")
    private int age;

    @Column(name = "gender", type = "TEXT")
    private Gender gender;

    public Cat(String name, int age, Gender gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    // Убираем сеттер для id, чтобы значение устанавливалось автоматически
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String toString() {
        return "Cat{" +
                " Name='" + name + '\'' +
                ", Age=" + age +
                ", gender=" + gender +
                " }";
    }
}
