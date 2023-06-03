package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<User>> showAllUser() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<Collection<Role>> getMyRoles(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.findById(id).getRoles(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserId(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addNewUser(@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        userService.update(id, user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

/*
@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private RoleService roleService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public String printUsers(Model model, @ModelAttribute("newUser") User newUser, Principal principal) {
        User authenticatedUser = userService.findByUsername(principal.getName());
        model.addAttribute("authenticatedUser", authenticatedUser);
        model.addAttribute("authenticatedUserRoles", authenticatedUser.getRoles());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("roles", roleService.getAllRoles());
        return "user-list";
    }

    @GetMapping("/user-create")
    public String createUserForm(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("currentUser", userService.findByUsername(userDetails.getUsername()));
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("user", new User());
        return "user-create";
    }

    @PostMapping
    public String addUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,
                          @ModelAttribute("currentUser") User currentUser,
                          @ModelAttribute("roles") List<Role> roles) {
        if (bindingResult.hasErrors()) {
            return "user-create";
        }

        userService.saveUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/user-delete/{id}")
    public String deleteUserFromTable(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @PatchMapping("/user-update/{id}")
    public String updateUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            //return "user-list";
            return "redirect:/admin";
        }

        userService.saveUser(user);
        return "redirect:/admin";
    }
}
*/
