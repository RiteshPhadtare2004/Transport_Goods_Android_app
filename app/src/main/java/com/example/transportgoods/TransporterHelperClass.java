package com.example.transportgoods;

public class TransporterHelperClass {
    String Starting_Point_of_Journey,Destination_of_Journey,Vehical_Type;

    public TransporterHelperClass() {
    }

    public TransporterHelperClass(String starting_Point_of_Journey, String destination_of_Journey, String vehical_Type) {
        Starting_Point_of_Journey = starting_Point_of_Journey;
        Destination_of_Journey = destination_of_Journey;
        Vehical_Type = vehical_Type;
    }

    public String getStarting_Point_of_Journey() {
        return Starting_Point_of_Journey;
    }

    public void setStarting_Point_of_Journey(String starting_Point_of_Journey) {
        Starting_Point_of_Journey = starting_Point_of_Journey;
    }

    public String getDestination_of_Journey() {
        return Destination_of_Journey;
    }

    public void setDestination_of_Journey(String destination_of_Journey) {
        Destination_of_Journey = destination_of_Journey;
    }

    public String getVehical_Type() {
        return Vehical_Type;
    }

    public void setVehical_Type(String vehical_Type) {
        Vehical_Type = vehical_Type;
    }
}
