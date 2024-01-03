package ServiceImpl;

import Domain.Identifiable;
import Repositories.DataBaseRepository;
import Service.Service;

public class GenericDatabaseService<Id,T extends Identifiable<Id>> implements Service<Id,T> {
    DataBaseRepository<Id,T> repository;
    public GenericDatabaseService(DataBaseRepository<Id,T> repository){
        this.repository=repository;
    }
    @Override
    public void add(T object) {
        repository.add(object);
    }

    @Override
    public void updateAtId(Id id, T object) {
        repository.updateAtId(id,object);
    }

    @Override
    public void deleteAtId(Id id) {
        repository.deleteAtId(id);
    }

    @Override
    public Iterable<T> getAllEntities() {
        repository.getData();
        return repository.getAll();
    }

    @Override
    public T getEntityById(Id id) {
        return repository.getById(id);
    }
}
