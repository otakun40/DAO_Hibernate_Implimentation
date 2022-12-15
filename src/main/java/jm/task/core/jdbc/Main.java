package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    static User[] users = new User[]{
            new User("Ivan", "Antonov", (byte) 18),
            new User("Andrey", "Ivanov", (byte) 23),
            new User("Viktor", "Popov", (byte) 24),
            new User("Alexey", "Petrov", (byte) 32)
    };

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.dropUsersTable();
        userService.createUsersTable();
        for (int i = 0; i < 4; i++) {
            userService.saveUser(users[i].getName(),users[i].getLastName(), users[i].getAge());
            System.out.printf("User с именем – %s добавлен в базу данных\n", users[i].getName());
        }
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
        Util.getSessionFactory().close();
    }
}
