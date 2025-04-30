package com.techelevator.controller;

import com.techelevator.dao.AppointmentDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Appointment;
import com.techelevator.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/appointments")
@CrossOrigin(origins = "*")
public class AppointmentController {

    private final AppointmentDao appointmentDao;
    private final UserDao userDao;

    public AppointmentController(AppointmentDao appointmentDao, UserDao userDao) {
        this.appointmentDao = appointmentDao;
        this.userDao = userDao;
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public Appointment bookAppointment(@RequestBody Appointment appointment) {
        return appointmentDao.createAppointment(appointment);
    }

    @PutMapping
    @PreAuthorize("hasRole('PROFESSIONAL')")
    public Appointment updateAppointment(@RequestBody Appointment appointment) {
        Appointment updated = appointmentDao.updateAppointment(appointment);
        if (updated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found");
        }
        return updated;
    }


    //added for mod3 mid, dev access
    @GetMapping("/all")
    @CrossOrigin(origins = "*")
    public List<Appointment> getAllAppointments() {
        return appointmentDao.findAll();
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<Appointment> getAppointments(Principal principal) {
        User user = userDao.getUserByUsername(principal.getName());
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
        }
        // Determine which appointments to retrieve based on the user's role.
        if (user.getRole().equals("ROLE_PROFESSIONAL")) {
            return appointmentDao.getAppointmentsForProfessional(user.getId());
        } else {
            return appointmentDao.getAppointmentsForPregnantUser(user.getId());
        }
    }
}
