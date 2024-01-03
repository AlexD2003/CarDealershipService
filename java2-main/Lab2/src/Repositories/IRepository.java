package Repositories;

import Domain.Car;
import Domain.Identifiable;
import Domain.Reservation;

import java.util.List;

//interface of the repository that works with identifiable type objects,def of basic methods

public interface IRepository<Id,T> {
    void add(T item);
    T getById(Id id);
    Iterable<T> getAll();

    void updateAtId(Id id,T updatedItem);
    void deleteAtId(Id id);

}
