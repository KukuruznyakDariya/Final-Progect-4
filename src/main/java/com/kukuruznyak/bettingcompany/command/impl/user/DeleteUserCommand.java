package com.kukuruznyak.bettingcompany.command.impl.user;

import com.kukuruznyak.bettingcompany.command.Command;
import com.kukuruznyak.bettingcompany.entity.user.User;
import com.kukuruznyak.bettingcompany.entity.user.UserRole;
import com.kukuruznyak.bettingcompany.service.ClientService;
import com.kukuruznyak.bettingcompany.service.UserService;
import com.kukuruznyak.bettingcompany.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collection;

public class DeleteUserCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        UserService userService = serviceFactory.getUserService();
        userService.delete(request.getParameter("id"));
        Collection<?> users = null;
        HttpSession currentSession = request.getSession();
        User authorizedUser = (User) currentSession.getAttribute("user");
        if (authorizedUser.getUserRole().equals(UserRole.ADMINISTRATOR)) {
            users = userService.getStaff();
        }
        if (authorizedUser.getUserRole().equals(UserRole.RISK_CONTROLLER)) {
            ClientService clientService = serviceFactory.getClientService();
            users = clientService.getAllClients();
        }
        currentSession.setAttribute("users", users);
        return pagesResourceBundle.getString("userList");
    }
}
