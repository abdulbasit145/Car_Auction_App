package com.app.car;

public class CarModel {

    private String make;
    private String model;
    private int year;
    private double driven;
    private String fuel;
    private String city;
    private String assembly;
    private String transmission;
    private String description;

    private byte[] galleryImage; // Field for holding the gallery image data
    private byte[] cameraImage; // Field for holding the camera image data

    // getters and setters for the properties

    public CarModel(){}
    public CarModel(String make,String model,int year,double driven,String fuel,String city,String assembly, String transmission,String description,byte[] galleryImage,byte[] cameraImage){
        this.make=make;
        this.model=model;
        this.year=year;
        this.driven=driven;
        this.fuel=fuel;
        this.city=city;
        this.assembly=assembly;
        this.transmission=transmission;
        this.description=description;
        this.galleryImage=galleryImage;
        this.cameraImage=cameraImage;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }


    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getDriven() {
        return driven;
    }

    public void setDriven(double driven) {
        this.driven = driven;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAssembly() {
        return assembly;
    }

    public void setAssembly(String assembly) {
        this.assembly = assembly;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGalleryImage(byte[] galleryImage){this.galleryImage = galleryImage;};
    public byte[] getGalleryImage(){return this.galleryImage;};

    public void setCameraImage(byte[] cameraImage){this.cameraImage = cameraImage;};
    public byte[] getCameraImage(){return this.cameraImage;};
}
