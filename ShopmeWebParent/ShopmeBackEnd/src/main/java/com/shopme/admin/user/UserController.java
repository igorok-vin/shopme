package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String listAll(Model model) {
        List<User> listUsers = userService.listAll();
        model.addAttribute("listUsers", listUsers);
        return "users";
    }

    @GetMapping("/users/new")
    public String createNewUser(Model model) {
        List<Role> listRoles = userService.listRoles();
        User user = new User();
        user.setEnabled(true);
        model.addAttribute("user",user);
        model.addAttribute("listRoles",listRoles);
        model.addAttribute("pageTitle", "Create New User");
        return "users_form";
    }

    @PostMapping("/users/save")
    public String saveNewUser(User user, RedirectAttributes redirectAttributes) {
        userService.save(user);
        redirectAttributes.addFlashAttribute("message", "The user has been saved successfully");
        return "redirect:/users";
    }

   @GetMapping("/users/edit/{id}")
    public String editUserProfile(@PathVariable(name="id") Integer id, Model model, RedirectAttributes redirectAttributes) {
       try {
           User user = userService.get(id);
           List<Role> listRoles = userService.listRoles();
           model.addAttribute("user",user);
           model.addAttribute("listRoles",listRoles);
           model.addAttribute("pageTitle", "Edit User (ID: "+id+")");
           return "users_form";
       } catch (UserNotFoundExeption e) {
           redirectAttributes.addFlashAttribute("message", e.getMessage() );
           return "redirect:/users";
       }
   }
}
