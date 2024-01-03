package Repositories;

import Domain.Identifiable;

public abstract class FileRepository<Id,T extends Identifiable<Id>> extends MemoryRepository<Id,T>{
protected String fileName;
    public FileRepository(String fileName){
        this.fileName=fileName;
        readFromFile();
    }
    public abstract void readFromFile();
    protected abstract void writeToFile();
    @Override
    public void deleteAtId(Id id){
        super.deleteAtId(id);
        writeToFile();
    }
    @Override
    public void updateAtId(Id id,T item){
        super.updateAtId(id,item);
        writeToFile();
    }
    @Override
    public void add(T item){
        super.add(item);
        writeToFile();
    }
}
