package org.example;

import org.example.annotations.Column;
import org.example.annotations.Table;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class TableCreator {
    public static void createTable(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Table.class)) {
            Table table = clazz.getAnnotation(Table.class);
            StringBuilder createQuery = new StringBuilder("CREATE TABLE IF NOT EXISTS " + table.title() + " (");

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Column.class)) {
                    Column column = field.getAnnotation(Column.class);
                    createQuery.append(column.name())
                            .append(" ")
                            .append(column.type())
                            .append(", ");
                }
            }
            createQuery.setLength(createQuery.length() - 2);
            createQuery.append(");");

            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:lab9/db/animals.db");
                 Statement stmt = conn.createStatement()) {
                stmt.execute(createQuery.toString());
                System.out.println("Table created: " + table.title());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
