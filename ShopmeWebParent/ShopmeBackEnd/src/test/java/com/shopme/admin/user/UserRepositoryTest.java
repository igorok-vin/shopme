package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Rollback(false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository repo;

    @Autowired
    private TestEntityManager entityManager;// JPA клас який управляє ролями. Знаходить таблицю roles так як вона відноситься до класу Role і бере значення ролі по ID-1 (перший тест) це буде Admin

    @Test
    public void testCreateUserWithOneRole() {
        //let it run empty test to create all tables first and then run test body
        Role roleAdmin = entityManager.find(Role.class, 1);
        User user = new User("igor@gmail.com", "igor2024","Igor","Nikolaienko");
        user.addRole(roleAdmin);
        User userSaved = repo.save(user);

        assertThat(userSaved.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateUserWithTwoRoles() {
        Role roleEditor = new Role(3);
        Role roleAssistant = new Role(5);

        Role roleAdmin = entityManager.find(Role.class, 1);
        User user = new User("john@gmail.com", "john2024","John","Walker");
        user.addRole(roleEditor);
        user.addRole(roleAssistant);
        User userSaved = repo.save(user);

        assertThat(userSaved.getId()).isGreaterThan(0);
    }

    @Test
    public void testAllUsers() {
        Iterable<User> userList =  repo.findAll();
        userList.forEach(user-> System.out.println(user));
    }

    @Test
    public void testGetUserById() {
        User user = repo.findById(1).get();
        assertThat(user).isNotNull();
        assertThat(user.getFirstName()).isEqualTo("Igor");
    }

    @Test
    public void updateUserDetails() {
        User user = repo.findById(1).get();
        user.setEnabled(true);
        user.setEmail("igorok@gmail.com");
        repo.save(user);
    }

    @Test
    public void testUpdateUserRoles() {
        User user = repo.findById(2).get();
        Role roleEditor = new Role(3);
        user.getRoles().remove(roleEditor);

        Role roleSalesPerson = new Role(2);
        user.addRole(roleSalesPerson);
    }

    @Test
    public void testDeleteUser() {
        User user = repo.findById(2).get();
        repo.delete(user);
    }

    @Test
    public void testGetUserByEmail() {
        String email = "igorok@gmail.com";
    String findEmail = repo.getUserByEmail(email).getEmail();
    assertThat(findEmail).isEqualTo(email);
    }

}
