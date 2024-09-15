package org.example;

public class Main {
    public static void main(String[] args) {
        TableCreator.createTable(Cat.class);

        Gender male = Gender.MALE;
        Gender female = Gender.FEMALE;


        Cat[] cats = {
                new Cat(1, "Barsik", 2, male),
                new Cat(2, "Murzik", 1, male),
                new Cat(3, "Murka", 3, female),
                new Cat(4, "test", 1, female),

        };

        cats[0].setAge(10);

        // Добавление объектов в таблицу
        for (Cat cat : cats) {
            EntityManager.insert(cat);
        }
    }
}