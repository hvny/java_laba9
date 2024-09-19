package org.example;

import org.example.Column;
import org.example.Table;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EntityManager {
    private static final String URL = "jdbc:postgresql://localhost:5432/java_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "8631";

    public static void insert(Object obj) {
        Class<?> clazz = obj.getClass();

        Table table = clazz.getAnnotation(Table.class);
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(table.title()).append(" (");

        StringBuilder placeholders = new StringBuilder();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                sql.append(column.name()).append(", ");
                placeholders.append("?, ");
            }
        }

        // Удаляем последнюю запятую и пробел
        sql.setLength(sql.length() - 2);
        placeholders.setLength(placeholders.length() - 2);

        sql.append(") VALUES (").append(placeholders).append(");");

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            int index = 1;
            for (Field field : fields) {
                if (field.isAnnotationPresent(Column.class)) {
                    field.setAccessible(true);
                    Object value = field.get(obj);

                    // Обработка Enum типов
                    if (field.getType().isEnum()) {
                        pstmt.setString(index, value.toString());
                    } else {
                        pstmt.setObject(index, value);
                    }

                    index++;
                }
            }

            pstmt.executeUpdate();
            System.out.println("Объект " + obj + " успешно добавлен в таблицу " + table.title());

        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) { // Код ошибки для уникального ограничения
                System.out.println("Объект " + obj +  " уже есть в таблице.");
            } else {
                e.printStackTrace();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
