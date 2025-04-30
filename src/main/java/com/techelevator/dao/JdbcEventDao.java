package com.techelevator.dao;

import com.techelevator.model.Event;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcEventDao implements EventDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcEventDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Event> findAll() {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM events";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            events.add(mapRowToEvent(results));
        }
        return events;
    }

    @Override
    public List<Event> findByType(String type) {
        List<Event> filtered = new ArrayList<>();
        String sql = "SELECT * FROM events WHERE type = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, type);
        while (results.next()) {
            filtered.add(mapRowToEvent(results));
        }
        return filtered;
    }


    private Event mapRowToEvent(SqlRowSet rs) {
        Event event = new Event();
        event.setId(rs.getInt("id"));
        event.setTitle(rs.getString("title"));
        event.setLocation(rs.getString("location"));
        event.setEventDate(rs.getDate("event_date").toLocalDate());
        event.setEventTime(rs.getTime("event_time").toLocalTime());
        event.setType(rs.getString("type"));
        return event;
    }
}