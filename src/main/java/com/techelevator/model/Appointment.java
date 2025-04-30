package com.techelevator.model;

import java.time.LocalDateTime;

public class Appointment {
    private int appointmentId;
    private int pregnantUserId;
    private int professionalId;
    private LocalDateTime appointmentTime;
    private String status; // PENDING, CONFIRMED, CANCELED

    public Appointment() {}

    public Appointment(int appointmentId, int pregnantUserId, int professionalId, LocalDateTime appointmentTime, String status) {
        this.appointmentId = appointmentId;
        this.pregnantUserId = pregnantUserId;
        this.professionalId = professionalId;
        this.appointmentTime = appointmentTime;
        this.status = status;
    }

    // Getters and setters
    public int getAppointmentId() {
        return appointmentId;
    }
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getPregnantUserId() {
        return pregnantUserId;
    }
    public void setPregnantUserId(int pregnantUserId) {
        this.pregnantUserId = pregnantUserId;
    }

    public int getProfessionalId() {
        return professionalId;
    }
    public void setProfessionalId(int professionalId) {
        this.professionalId = professionalId;
    }

    public LocalDateTime getAppointmentTime() {
        return appointmentTime;
    }
    public void setAppointmentTime(LocalDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AppointmentDto{" +
                "id=" + appointmentId +
                ", pregnantUserId=" + pregnantUserId +
                ", professionalId=" + professionalId +
                ", appointmentTime='" + appointmentTime + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
