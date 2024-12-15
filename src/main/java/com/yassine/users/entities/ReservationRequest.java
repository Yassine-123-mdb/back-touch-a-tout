package com.yassine.users.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class ReservationRequest {

    private Long userId;
    private Long serviceId;
    private Date reservationDate;

    @JsonFormat(pattern = "HH:mm")
    private Date reservationTime;

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Date getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(Date reservationTime) {
        this.reservationTime = reservationTime;
    }
}
