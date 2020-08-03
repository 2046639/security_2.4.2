package web.controller;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {
    private UserService userService;
    private RoleService roleService;

    //использовать конструктор
    @Autowired(required = true)
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired(required = true)
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

//писать в одном стиле
    @GetMapping("/admin/users")
    public String listUsers(Model model) {
        List<User> listOfUsers = this.userService.listUsers();

        model.addAttribute("listUsers", listOfUsers);

        return "users";
    }

    @RequestMapping(value = "/admin/users/add", method = RequestMethod.POST)
    public String addUser(User user, String[] role) {
        //переделать циклом
        if (role.length>1) {
            String role1 = role[0];
            System.out.println(role1);
            String role2 = role[1];
            System.out.println(role2);

            Set<Role> roleSet = new HashSet<>();
            roleSet.add(this.roleService.getRoleByName(role1));
            roleSet.add(this.roleService.getRoleByName(role2));

            user.setRoles(roleSet);
        }
        else {
            String role1= role[0];
            System.out.println(role1);

            Set<Role> roleSet = new HashSet<>();
            roleSet.add(this.roleService.getRoleByName(role1));

            user.setRoles(roleSet);
        }

        //убрать ненужное
        if (user.getId() == 0) {
            this.userService.addUser(user);
        } else {
            this.userService.updateUser(user);
        }

        return "redirect:/admin/users";
    }

    @RequestMapping(value = "/admin/remove", method = RequestMethod.POST)
    public String removeUser(User user) {
        this.userService.removeUser(user.getId());

        return "redirect:/admin/users";
    }

    @RequestMapping(value = "/admin/edit/user", method = RequestMethod.GET)//гет
    public String editUser(User user, Model model){
        model.addAttribute("user", this.userService.getUserById(user.getId()));

        return "edit";
    }

    @RequestMapping(value = "/admin/edit", method = RequestMethod.POST)
    public String editUserPost(User user, String[] role) {

        //
        if (role.length>1) {
            String role1 = role[0];
            System.out.println(role1);
            String role2 = role[1];
            System.out.println(role2);

            Set<Role> roleSet = new HashSet<>();
            roleSet.add(this.roleService.getRoleByName(role1));
            roleSet.add(this.roleService.getRoleByName(role2));

            user.setRoles(roleSet);
        }
        else {
            String role1= role[0];
            System.out.println(role1);

            Set<Role> roleSet = new HashSet<>();
            roleSet.add(this.roleService.getRoleByName(role1));

            user.setRoles(roleSet);
        }

        this.userService.updateUser(user);


        return "redirect:/admin/users";
    }

    @GetMapping(value = "/user")
    public String helloUser(Model model) {
        User user;

        user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("user", user);

        return "user";
    }

    //вынести
    @RequestMapping(value = "hello", method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello! Java Mentor");
        messages.add("I'm Spring MVC-SECURITY application");
        messages.add("01/08/2020 ");
        model.addAttribute("messages", messages);

        return "hello";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView AllUsers() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }


    //убрать
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

}
