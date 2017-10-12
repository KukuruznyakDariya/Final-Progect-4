package com.kukuruznyak.bettingcompany.service;

import com.kukuruznyak.bettingcompany.dao.EventDao;
import com.kukuruznyak.bettingcompany.dao.MarketDao;
import com.kukuruznyak.bettingcompany.dao.OutcomeDao;
import com.kukuruznyak.bettingcompany.entity.event.*;
import com.kukuruznyak.bettingcompany.entity.event.eventbuilder.OutcomeBuilder;
import com.kukuruznyak.bettingcompany.entity.tournament.Participant;
import com.kukuruznyak.bettingcompany.exception.ServiceException;
import com.kukuruznyak.bettingcompany.service.factory.ServiceFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public class EventService extends AbstractService {
    private static EventService instance;
    private BetService betService = ServiceFactory.getInstance().getBetService();

    public static EventService getInstance() {
        if (instance == null) {
            synchronized (EventService.class) {
                if (instance == null) {
                    instance = new EventService();
                }
            }
        }
        return instance;
    }

    private EventService() {
    }

    public Event add(Event event) {
        try {
            Connection connection = dataSource.getConnection();
            try {
                connection.setAutoCommit(false);
                EventDao eventDao = daoFactory.getEventDao(connection);
                event = eventDao.add(event);
                MarketDao marketDao = daoFactory.getMarketDao(connection);
                for (Market market : event.getMarkets()) {
                    market.setId(marketDao.add(market).getId());
                }
                connection.commit();
                return event;
            } catch (SQLException e) {
                connection.rollback();
                throw new ServiceException(e);
            } finally {
                connection.close();
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    public void update(Event event) {
        try {
            Connection connection = dataSource.getConnection();
            try {
                connection.setAutoCommit(false);
                EventDao eventDao = daoFactory.getEventDao(connection);
                eventDao.update(event);
                MarketDao marketDao = daoFactory.getMarketDao(connection);
                for (Market market : event.getMarkets()) {
                    marketDao.update(market);
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new ServiceException(e);
            } finally {
                connection.close();
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    public Event getById(Long id) {
        try {
            try (Connection connection = dataSource.getConnection()) {
                EventDao eventDao = daoFactory.getEventDao(connection);
                Event event = eventDao.getById(id);
                MarketDao marketDao = daoFactory.getMarketDao(connection);
                event.setMarkets(marketDao.getAllByEventId(event.getId()));
                return event;
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    public Collection<Event> getAll() {
        try {
            try (Connection connection = dataSource.getConnection()) {
                EventDao eventDao = daoFactory.getEventDao(connection);
                Collection<Event> events = eventDao.getAll();
                return insertMarketsToEvent(connection, events);
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    public Collection<Event> getEventsByBookmakerId(Long id) {
        try {
            try (Connection connection = dataSource.getConnection()) {
                EventDao eventDao = daoFactory.getEventDao(connection);
                Collection<Event> events = eventDao.getAllByBookmakerId(id);
                return insertMarketsToEvent(connection, events);
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    public Collection<Event> getEvensByStatus(EventStatus status) {
        try {
            try (Connection connection = dataSource.getConnection()) {
                EventDao eventDao = daoFactory.getEventDao(connection);
                return insertMarketsToEvent(connection, eventDao.getAllByStatus(status));
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    public void finishEvent(Event event) {//TODO
        betService.calculateBets(event);
    }

    public Event createMarket(Event event, MarketNames marketNames) {
        try {
            Connection connection = dataSource.getConnection();
            try {
                connection.setAutoCommit(false);
                MarketDao marketDao = daoFactory.getMarketDao(connection);
                Market market = marketDao.add(new Market(marketNames, event.getId()));
                OutcomeDao outcomeDao = daoFactory.getOutcomeDao(connection);
                for (Participant participant : event.getTournament().getParticipants()) {
                    Outcome outcome = new OutcomeBuilder()
                            .buildName(participant.getName())
                            .buildMarketId(market.getId())
                            .build();
                    market.addOutcome(outcomeDao.add(outcome));
                }
                event.addMarket(market);
                connection.commit();
                return event;
            } catch (SQLException e) {
                connection.rollback();
                throw new ServiceException(e);
            } finally {
                connection.close();
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    private Collection<Event> insertMarketsToEvent(Connection connection, Collection<Event> events) {
        MarketDao marketDao = daoFactory.getMarketDao(connection);
        for (Event event : events) {
            event.setMarkets(marketDao.getAllByEventId(event.getId()));
        }
        return events;
    }
}
