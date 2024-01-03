package Domain;

//identifiable superclass that implements both car and reservations

public interface Identifiable<Id>{
    Id getId();
    void setId(Id Id);
}
