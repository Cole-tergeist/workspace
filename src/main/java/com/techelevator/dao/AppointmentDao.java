package com.techelevator.dao;

import com.techelevator.model.Appointment;
import java.util.List;

public interface AppointmentDao {
    Appointment createAppointment(Appointment appointment);
    List<Appointment> getAllAppointments();
    Appointment getAppointmentById(int appointmentId);
    List<Appointment> getAppointmentsForPregnantUser(int userId);
    List<Appointment> getAppointmentsForProfessional(int userId);
    Appointment updateAppointment(Appointment appointment);
    boolean deleteAppointmentById(int appointmentId);

    // added for mod2 mid, for dev access
    List<Appointment> findAll();


}

