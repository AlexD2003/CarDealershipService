package ServiceImpl;

import Domain.Identifiable;
import Repositories.IRepository;
import Service.Service;

public class GenericService<Id,T extends Identifiable<Id>> implements Service<Id,T> {
    public IRepository<Id,T> repository;
    public GenericService(IRepository<Id,T> repository){
        this.repository=repository;
    }
    public void add(T object){
        repository.add(object);
    }

    @Override
    public void updateAtId(Id id,T object) {
        repository.updateAtId(id,object);
    }

    @Override
    public void deleteAtId(Id id) {
        repository.deleteAtId(id);
    }

    @Override
    public Iterable<T> getAllEntities() {
        return repository.getAll();
    }

    @Override
    public T getEntityById(Id id) {
        return repository.getById(id);
    }
}
