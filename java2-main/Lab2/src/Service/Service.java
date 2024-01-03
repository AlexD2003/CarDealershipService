package Service;
import Domain.Identifiable;

import Domain.Identifiable;

public interface Service <Id,T extends Identifiable<Id>>{
    void add(T object);
    void updateAtId(Id id,T object);
    void deleteAtId(Id id);
    Iterable<T> getAllEntities();

    T getEntityById(Id id);
}
