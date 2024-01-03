package Repositories;

import Domain.Identifiable;
import Domain.Reservation;

import java.util.List;
//basic repo layout using lists
public class ReservationRepository extends MemoryRepository<Integer, Reservation> {
    public ReservationRepository() {
    }
}
