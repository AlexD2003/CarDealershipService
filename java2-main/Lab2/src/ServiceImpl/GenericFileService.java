package ServiceImpl;

import Domain.Identifiable;
import Repositories.FileRepository;
import Service.Service;

public class GenericFileService<Id,T extends Identifiable<Id>> implements Service<Id,T> {
    private final FileRepository<Id,T> repository;
    public GenericFileService(FileRepository<Id,T> repository){
        this.repository=repository;
    }

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
        repository.readFromFile();
        return repository.getAll();
    }

    @Override
    public T getEntityById(Id id) {
        return repository.getById(id);
    }
}
