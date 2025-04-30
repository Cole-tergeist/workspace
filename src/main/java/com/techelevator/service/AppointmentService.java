package com.techelevator.service;

import com.techelevator.dao.AppointmentDao;
import com.techelevator.model.Appointment;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentDao appointmentDao;

    public AppointmentService(AppointmentDao appointmentDao) {
        this.appointmentDao = appointmentDao;
    }

    public Appointment createAppointment(Appointment appointment) {
        return appointmentDao.createAppointment(appointment);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentDao.getAllAppointments();
    }

    public List<Appointment> getAppointmentsForPregnantUser(int userId) {
        return appointmentDao.getAppointmentsForPregnantUser(userId);
    }

    public List<Appointment> getAppointmentsForProfessional(int userId) {
        return appointmentDao.getAppointmentsForProfessional(userId);
    }

    public Appointment updateAppointment(Appointment appointment) {
        return appointmentDao.updateAppointment(appointment);
    }

    public boolean deleteAppointmentById(int appointmentId) {
        return appointmentDao.deleteAppointmentById(appointmentId);
    }
}
