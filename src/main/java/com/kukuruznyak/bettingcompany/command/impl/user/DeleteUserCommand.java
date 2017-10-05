package com.kukuruznyak.bettingcompany.command.impl.user;

import com.kukuruznyak.bettingcompany.command.Command;
import com.kukuruznyak.bettingcompany.entity.user.User;
import com.kukuruznyak.bettingcompany.entity.user.UserRole;
import com.kukuruznyak.bettingcompany.exception.ApplicationException;
import com.kukuruznyak.bettingcompany.service.ClientService;
import com.kukuruznyak.bettingcompany.service.UserService;
import com.kukuruznyak.bettingcompany.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;

public class DeleteUserCommand extends Command {
    private UserService userService = ServiceFactory.getInstance().getUserService();
    private ClientService clientService = ServiceFactory.getInstance().getClientService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
        userService.delete(request.getParameter("id"));
        Collection<?> users = null;
        User authorizedUser = (User) request.getSession().getAttribute("user");
        if (authorizedUser.getUserRole().equals(UserRole.ADMINISTRATOR)) {
            users = userService.getStaff();
        }
        if (authorizedUser.getUserRole().equals(UserRole.RISK_CONTROLLER)) {
            users = clientService.getAllClients();
        }
        request.getSession().setAttribute("users", users);
        return pagesResourceBundle.getString("userList");
    }
}
