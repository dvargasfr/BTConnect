package com.example.david.btconnect;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by David on 24/11/2016.
 */

public class ConnectThread extends Thread {

    private final BluetoothSocket BTsocket;
    private final BluetoothDevice BTdevice;

    public ConnectThread(BluetoothDevice BTdevice, UUID mUUID) {
        BluetoothSocket tmp = null;
        this.BTdevice = BTdevice;

        try {
            tmp = this.BTdevice.createRfcommSocketToServiceRecord(mUUID);
        } catch (IOException e) {
        }
        BTsocket = tmp;
    }

    public boolean connect(){
        try{
            BTsocket.connect();
        }catch (IOException e){
            try{
                BTsocket.close();
            }catch(IOException close){
                return false;
            }
        }
        return true;
    }

    public boolean cancel() {
        try {
            BTsocket.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
