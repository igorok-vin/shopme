package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
/* by default, Spring Data JPA tests roll back the transaction after the test execution. This means that any changes made during the test, including data insertion, will be rolled back and not persisted in the database. However, by using the @Rollback(false) annotation, you can override this default behavior and commit the changes.*/
@Rollback(false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)//перезаписуєм діфолтні настройки щоб запуски тести для справжньої бази а не H2
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository repo;

    @Test
    public void testCreateFirstRole(){
        Role roleAdmin = new Role("Admin","manage everething" );
        Role savedRole= repo.save(roleAdmin);
        assertThat(savedRole.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateResttRole(){
        Role roleSalesperson = new Role("Salesperson", "manage product price, "
                + "customers, shipping, orders and sales report");

        Role roleEditor = new Role("Editor", "manage categories, brands, "
                + "products, articles and menus");

        Role roleShipper = new Role("Shipper", "view products, view orders "
                + "and update order status");

        Role roleAssistant = new Role("Assistant", "manage questions and reviews");

        repo.saveAll(List.of(roleSalesperson, roleEditor, roleShipper, roleAssistant));
    }
}
