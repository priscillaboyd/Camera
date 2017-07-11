package com.develogical.camera;

public class Camera{
    private MemoryCard memCard;
    private boolean isOn = false;
    private Sensor sensor;
    private boolean writeComplete = true;

    public Camera(Sensor sensor) {
        this.sensor = sensor;
    }

    public Camera(Sensor sensor, MemoryCard memCard) {
        this.sensor = sensor;
        this.memCard = memCard;
    }


    public void pressShutter() {
        if(this.isOn)
        {
            writeComplete = false;
            byte[] data = sensor.readData();
            this.memCard.write(data, () -> {
                writeComplete = true;
                sensor.powerDown();
            });
        }
    }

    public void powerOn() {
        this.sensor.powerUp();
        this.isOn = true ;
    }

    public void powerOff() {
        if(writeComplete){
            this.sensor.powerDown();
        }
    }

}
