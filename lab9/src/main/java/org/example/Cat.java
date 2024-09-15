package org.example;

@Table(title = "cats")
public class Cat {
    @Column(name = "id", type = "INTEGER", unique = true)
    private int id;

    @Column(name = "name", type = "VARCHAR(100)")
    private String name;

    @Column(name = "age", type = "INTEGER")
    private int age;

    @Column(name = "gender", type = "VARCHAR(10)")
    private Gender gender;

    public Cat(int id, String name, int age, Gender gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public void setId(int id) {
        this.id = id;
    }

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
                "ID=" + id +
                ", Name='" + name + '\'' +
                ", Age=" + age +
                ", gender=" + gender +
                '}';
    }
}