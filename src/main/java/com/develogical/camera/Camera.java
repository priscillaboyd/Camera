package com.develogical.camera;

class Camera{
    private MemoryCard memCard;
    private boolean isOn = false;
    private Sensor sensor;
    private boolean writeComplete = true;

    Camera(Sensor sensor) {
        this.sensor = sensor;
    }

    Camera(Sensor sensor, MemoryCard memCard) {
        this.sensor = sensor;
        this.memCard = memCard;
    }


    void pressShutter() {
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

    void powerOn() {
        this.sensor.powerUp();
        this.isOn = true ;
    }

    void powerOff() {
        if(writeComplete){
            this.sensor.powerDown();
        }
    }

}
