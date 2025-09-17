/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.time.LocalDate;

/**
 *
 * @author Vader
 */
public class EventDTO {
    private int id;
    private String name;
    private LocalDate eventDate;
    private String location;
    private int numOfAttendee;
    private boolean status; 

    public EventDTO() {
    }

    public EventDTO(int id, String name, LocalDate eventDate, String location, int numOfAttendee, boolean status) {
        this.id = id;
        this.name = name;
        this.eventDate = eventDate;
        this.location = location;
        this.numOfAttendee = numOfAttendee;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getNumOfAttendee() {
        return numOfAttendee;
    }

    public void setNumOfAttendee(int numOfAttendee) {
        this.numOfAttendee = numOfAttendee;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    
    
}
