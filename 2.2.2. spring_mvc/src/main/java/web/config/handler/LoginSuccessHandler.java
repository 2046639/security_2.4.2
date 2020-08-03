package web.config.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import web.model.Role;
import web.model.User;
import web.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        System.out.println(user);

        Set<Role> roleSet = user.getRoles();
        System.out.println(roleSet);

        if (roleSet.size()>1) {
            httpServletResponse.sendRedirect("/admin/users");
        }
        else {
            Object[] roles = roleSet.toArray();
            Role role = (Role) roles[0];
            if ((role.getName()).equals("admin")) {
                httpServletResponse.sendRedirect("/admin/users");
            }
            else {
                httpServletResponse.sendRedirect("/user");
            }

        }



    }
}