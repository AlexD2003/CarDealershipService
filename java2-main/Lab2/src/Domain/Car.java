package Domain;


import java.io.Serializable;

//car class with getters,setters and defined methods
public class Car implements Identifiable<Integer>, Serializable {
    private String model;
    private int yearOfProduction;
    private int ID;

    public Car(int ID,String model,int yearOfProduction) {
        this.ID=ID;
        this.model = model;
        this.yearOfProduction=yearOfProduction;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(int yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }

    @Override
    public String toString() {
        return "Car Id: "+this.ID+" Model of car='" + model + '\'' + ", yearOfProduction=" + yearOfProduction+"\n";
    }
    public String toString2() {
        return toString();
    }
    @Override
    public Integer getId() {
        return this.ID;
    }

    @Override
    public void setId(Integer Id) {
        this.ID=Id;
    }
}
