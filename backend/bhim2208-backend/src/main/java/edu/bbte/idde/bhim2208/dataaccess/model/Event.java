package edu.bbte.idde.bhim2208.dataaccess.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Event extends BaseEntity {
    private String title;
    private String location;
    private LocalDate date;
    private LocalTime time;
    private String description;
    private boolean online;

    public Event() {
        super();
    }

    public Event(String title, String location, LocalDate date, LocalTime time, String description, boolean online) {
        super();
        this.title = title;
        this.location = location;
        this.date = date;
        this.time = time;
        this.description = description;
        this.online = online;
    }

    public Event(Integer id,
                 String title,
                 String location,
                 LocalDate date,
                 LocalTime time,
                 String description,
                 boolean online) {
        super();
        this.setId(id);
        this.title = title;
        this.location = location;
        this.date = date;
        this.time = time;
        this.description = description;
        this.online = online;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    @Override
    public String toString() {
        return "Event{"
                + "id=" + id
                + ", title='" + title + '\''
                + ", location='" + location + '\''
                + ", date=" + date
                + ", time=" + time
                + ", description='" + description + '\''
                + ", online=" + online
                + '}';
    }
}
