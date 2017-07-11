package com.develogical.camera;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class CameraTest {
    @Test
    public void switchingTheCameraOnPowersUpTheSensor() {
        Sensor mockedSensor = mock(Sensor.class);
        Camera camera = new Camera(mockedSensor);
        camera.powerOn();
        verify(mockedSensor).powerUp();
    }

    @Test
    public void switchingTheCameraOfPowersDownTheSensor() {
        Sensor mockedSensor = mock(Sensor.class);
        Camera camera = new Camera(mockedSensor);
        camera.powerOn();
        camera.powerOff();
        verify(mockedSensor).powerDown();
    }

    @Test
    public void pressingTheShutterWhenThePowerIsOffDoesNothing() {
        Sensor mockedSensor = mock(Sensor.class);
        Camera camera = new Camera(mockedSensor);
        camera.powerOff();
        camera.pressShutter();
        assertThat(camera.shutterCount, is(0));
    }

}
