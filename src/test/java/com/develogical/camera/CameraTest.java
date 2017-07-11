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

}
