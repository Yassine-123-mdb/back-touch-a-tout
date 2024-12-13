package com.yassine.users.restControllers;

import com.yassine.users.entities.Reservation;
import com.yassine.users.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/create")
    public ResponseEntity<Reservation> createReservation(@RequestParam Long userId,
                                                         @RequestParam Long serviceId,
                                                         @RequestParam Date reservationDate,
                                                         @RequestParam Date reservationTime) {
        try {
            Reservation reservation = reservationService.createReservation(userId, serviceId, reservationDate, reservationTime);
            return ResponseEntity.ok(reservation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Reservation>> getReservationsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(reservationService.getReservationsByUser(userId));
    }
}
