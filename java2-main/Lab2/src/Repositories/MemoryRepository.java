package Repositories;

import Domain.Car;
import Domain.Identifiable;
import Domain.Reservation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//memory repository that represents both objects in a defined hashmap,storages items of type Identifiable
public abstract class MemoryRepository<Id,T extends Identifiable<Id>> implements IRepository<Id,T> {
    //hashmap def
    protected Map<Id, T> storage=new HashMap<>();

    @Override
    //def of add,get,set functions

    public void add(T item) {
        storage.put(item.getId(),item);
    }
    @Override
    public T getById(Id id) {
        return storage.get(id);
    }

    @Override
    public Iterable<T> getAll() {
        return storage.values();
    }

    @Override
    public void updateAtId(Id id, T updatedItem) {
        if(storage.containsKey(id)){
            storage.put(id,updatedItem);
        }
    }

    @Override
    public void deleteAtId(Id id) {
        storage.remove(id);
    }


}
