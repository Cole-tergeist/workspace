package com.techelevator.dao;

import com.techelevator.model.Appointment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcAppointmentDaoTest extends BaseDaoTest {
    protected static final Appointment APPOINTMENT_1 = new Appointment(1, 1, 3, LocalDateTime.of(2025, 3, 10, 10, 0), "PENDING");
    protected static final Appointment APPOINTMENT_2 = new Appointment(2, 2, 4, LocalDateTime.of(2025, 3, 12, 14, 0), "CONFIRMED");
    private JdbcAppointmentDao dao;

    @BeforeEach
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        dao = new JdbcAppointmentDao(jdbcTemplate);
    }

    @Test
    public void getAppointmentById_invalidId_returnsNull() {
        Appointment appointment = dao.getAppointmentById(-1);
        assertNull(appointment, "Expected null for invalid appointment ID");
    }

    @Test
    public void createAppointment_validData_createsAppointment() {
        Appointment newAppointment = new Appointment(1, 1, 2, LocalDateTime.now(), "PENDING");
        Appointment createdAppointment = dao.createAppointment(newAppointment);

        assertNotNull(createdAppointment, "Expected not null appointment after creation");
        assertEquals("PENDING", createdAppointment.getStatus(), "Expected status to be PENDING");
    }

    @Test
    public void updateAppointment_validData_updatesSuccessfully() {
        Appointment appointment = new Appointment(1,1, 2, LocalDateTime.now(), "CONFIRMED");
        dao.updateAppointment(appointment);

        Appointment updatedAppointment = dao.getAppointmentById(appointment.getAppointmentId());
        assertNotNull(updatedAppointment, "Updated appointment should exist");
        assertEquals("CONFIRMED", updatedAppointment.getStatus(), "Status should be updated to CONFIRMED");
    }

    @Test
    public void deleteAppointment_validId_deletesSuccessfully() {
        int appointmentId = 1;
        assertTrue(dao.deleteAppointmentById(appointmentId), "Expected delete to return true");
        assertNull(dao.getAppointmentById(appointmentId), "Deleted appointment should not exist");
    }
}

