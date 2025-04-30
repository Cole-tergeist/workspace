package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Appointment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAppointmentDao implements AppointmentDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcAppointmentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Appointment createAppointment(Appointment appointment) {
        String sql = "INSERT INTO appointments (pregnant_user_id, professional_id, appointment_time, status) " +
                "VALUES (?, ?, ?, ?) RETURNING appointment_id";
        try {
            Integer appointmentId = jdbcTemplate.queryForObject(sql, Integer.class,
                    appointment.getPregnantUserId(),
                    appointment.getProfessionalId(),
                    appointment.getAppointmentTime(),
                    appointment.getStatus());
            if (appointmentId != null) {
                return getAppointmentById(appointmentId);
            } else {
                throw new DaoException("Failed to create appointment, ID returned null.");
            }
        } catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        } catch(DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public Appointment getAppointmentById(int appointmentId) {
        String sql = "SELECT * FROM appointments WHERE appointment_id = ?";

        try {
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, appointmentId);
            if (rs.next()) {
                return mapRowToAppointment(rs);
            } else {
                return null;
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the database", e);
        }
    }

    @Override
    public List<Appointment> findAll() {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            appointments.add(mapRowToAppointment(results));
        }
        return appointments;
    }


    @Override
    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                appointments.add(mapRowToAppointment(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return appointments;
    }

    @Override
    public List<Appointment> getAppointmentsForPregnantUser(int userId) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE pregnant_user_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                appointments.add(mapRowToAppointment(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return appointments;
    }

    @Override
    public List<Appointment> getAppointmentsForProfessional(int userId) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE professional_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                appointments.add(mapRowToAppointment(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return appointments;
    }

    @Override
    public Appointment updateAppointment(Appointment appointment) {
        String sql = "UPDATE appointments SET pregnant_user_id = ?, professional_id = ?, appointment_time = ?, status = ? " +
                "WHERE appointment_id = ?";

        try {
            int rowsAffected = jdbcTemplate.update(sql,
                    appointment.getPregnantUserId(),
                    appointment.getProfessionalId(),
                    appointment.getAppointmentTime(),
                    appointment.getStatus(),
                    appointment.getAppointmentId());

            if (rowsAffected == 0) {
                throw new DaoException("No appointment found to update with ID: " + appointment.getAppointmentId());
            }

            return getAppointmentById(appointment.getAppointmentId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Invalid data provided for updating appointment", e);
        }
    }

    @Override
    public boolean deleteAppointmentById(int appointmentId) {
        String sql = "DELETE FROM appointments WHERE appointment_id = ?";
        try {
            int rowsAffected = jdbcTemplate.update(sql, appointmentId);
            if (rowsAffected == 0) {
                throw new DaoException("No appointment found to delete with ID: " + appointmentId);
            }
            return true;
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the database", e);
        }
    }



    private Appointment mapRowToAppointment(SqlRowSet rs) {
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(rs.getInt("appointment_id"));
        appointment.setPregnantUserId(rs.getInt("pregnant_user_id"));
        appointment.setProfessionalId(rs.getInt("professional_id"));
        appointment.setAppointmentTime(rs.getTimestamp("appointment_time").toLocalDateTime());
        appointment.setStatus(rs.getString("status"));
        return appointment;
    }
}
