package com.develogical.camera;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class CameraTest {

    private Sensor mockedSensor = mock(Sensor.class);
    private MemoryCard mockedMemCard = mock(MemoryCard.class);

    @Test
    public void switchingTheCameraOnPowersUpTheSensor() {

        Camera camera = new Camera(mockedSensor);
        camera.powerOn();
        verify(mockedSensor).powerUp();
    }

    @Test
    public void switchingTheCameraOfPowersDownTheSensor() {
        Camera camera = new Camera(mockedSensor);
        camera.powerOn();
        camera.powerOff();
        verify(mockedSensor).powerDown();
    }

    @Test
    public void pressingTheShutterWhenThePowerIsOffDoesNothing() {
        Camera camera = new Camera(mockedSensor);
        camera.powerOff();
        camera.pressShutter();
        verify(mockedSensor).powerDown();
        verifyNoMoreInteractions(mockedSensor);
    }

    @Test
    public void pressingTheShutterWhenThePowerIsOnReadsDataFromSensor() {
        Camera camera = new Camera(mockedSensor, mockedMemCard);
        camera.powerOn();
        camera.pressShutter();
        verify(mockedSensor).readData();
    }

    @Test
    public void pressingTheShutterWhenThePowerIsOnWritesDataFromSensorToMemoryCard() {
        Camera camera = new Camera(mockedSensor, mockedMemCard);
        byte[] data = new byte[10];

        // we need to read what's in the sensor
        when(mockedSensor.readData()).thenReturn(data);

        // we need to write to the mem card
        camera.powerOn();
        camera.pressShutter();
        verify(mockedMemCard).write(eq(data), any());

        // we need to compare that we've read + what we've written matches
        assertThat(mockedSensor.readData(), is(data));
    }

    @Test
    public void IfDataIsBeingWrittenPoweringOffIsDisabled() {
        Camera camera = new Camera(mockedSensor, mockedMemCard);
        camera.powerOn();
        camera.pressShutter();
        camera.powerOff();
        verify(mockedSensor).powerUp();
        verify(mockedSensor).readData();
        verifyNoMoreInteractions(mockedSensor);
    }

    @Test
    public void CheckDataHasBeenWrittenToMemCard(){
        Camera camera = new Camera(mockedSensor, mockedMemCard);
        camera.powerOn();
        camera.pressShutter();
        ArgumentCaptor<WriteCompleteListener> varArgs = ArgumentCaptor.forClass(WriteCompleteListener.class);
        verify(mockedMemCard).write(any(), varArgs.capture());
        camera.powerOff();
        verify(mockedSensor, times(0)).powerDown();
        varArgs.getValue().writeComplete();
        verify(mockedSensor, times(1)).powerDown();
    }

}
