package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository=roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

   public List<User> listAll() {
        return (List<User>) userRepository.findAll();
   }

   public List<Role> listRoles() {
        return (List<Role>) roleRepository.findAll();
   }
    public void save(User user) {
        encodePassword(user);
        userRepository.save(user);
    }

    private void encodePassword(User user) {
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
    }

    public boolean isEmailUnique(Integer id,String email){
        User userByEmail = userRepository.getUserByEmail(email);
        if(userByEmail == null) return true;
        boolean isCreatingNew = (id == null);
        if (isCreatingNew) {
            if(userByEmail !=null) return false;
        } else {
            if(userByEmail.getId() != id)
                return false;
        }
        return true;
    }

    public User get(Integer id) throws UserNotFoundExeption {
        try {
            return userRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new UserNotFoundExeption("Could not find any user with ID" + id);
        }
    }
}
