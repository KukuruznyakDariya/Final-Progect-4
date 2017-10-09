//package com.kukuruznyak.bettingcompany.command.impl.event;
//
//import com.kukuruznyak.bettingcompany.command.Command;
//import com.kukuruznyak.bettingcompany.entity.event.Event;
//import com.kukuruznyak.bettingcompany.entity.user.User;
//import com.kukuruznyak.bettingcompany.entity.user.UserRole;
//import com.kukuruznyak.bettingcompany.exception.ApplicationException;
//import com.kukuruznyak.bettingcompany.service.EventService;
//import com.kukuruznyak.bettingcompany.service.factory.ServiceFactory;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//public class EditEventCommand extends Command {
//    @Override
//    public String execute(HttpServletRequest request, HttpServletResponse response) {
//        HttpSession currentSession = request.getSession();
//        User authorizedUser = (User) currentSession.getAttribute("user");
//        try {
//            if (!authorizedUser.getUserRole().equals(UserRole.BOOKMAKER)) {
//                throw new ApplicationException("Access denied");
//            }
//            EventService eventService = ServiceFactory.getInstance().getEventService();
//            Event event = eventService.getById(request.getParameter("eventId"));
//            if (event == null) {
//                throw new ApplicationException("No event selected.");
//            }
//        } catch (ApplicationException e) {
//            LOGGER.error(e.getMessage());
//            currentSession.setAttribute("errorMessage", e.getMessage());
//        }
//        return pagesResourceBundle.getString("editEvent");
//    }
//}