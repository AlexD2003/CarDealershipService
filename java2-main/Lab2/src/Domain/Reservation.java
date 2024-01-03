package Domain;

import java.io.Serializable;

//reservation class with getters,setters and defined methods
public class Reservation implements Identifiable<Integer>, Serializable {
    private int ID;
    private Car car;
    private String customerName;

    private String startDate;
    private String endDate;
    public Reservation(int ID,Car car, String customerName, String startDate, String endDate) {
        this.car = car;
        this.customerName = customerName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.ID=ID;
    }


    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    @Override
    public String toString() {
        return
                "Reservation id:"+this.ID+" "+car.toString2()+" " +", customerName='" + customerName + '\'' + ", startDate='" + startDate + '\'' + ", endDate='" + endDate + '\''+"\n"
                ;
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
