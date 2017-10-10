package com.kukuruznyak.bettingcompany.command.impl.user;

import com.kukuruznyak.bettingcompany.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetAddUserPageCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return pagesResourceBundle.getString(ADD_USER_PAGE);
    }
}
