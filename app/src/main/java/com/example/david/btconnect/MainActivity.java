package com.example.david.btconnect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.*;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.view.*;
import android.util.Log;
import android.bluetooth.*;

import java.util.*;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private TextView status_txt;
    private ListView devices_list;
    private BluetoothAdapter mBluetoothAdapter;
    private Set<BluetoothDevice> pairedDevices;
    private ArrayList<BluetoothDevice> list;
    private ArrayAdapter adapter;

    static final int REQUEST_ENABLE_BT = 1;
    static final UUID MY_UUID = UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        status_txt = (TextView)findViewById(R.id.status_text);
        devices_list = (ListView)findViewById(R.id.devices_list);
        devices_list.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?>adapter, View v, int position, long id){
                Log.d("LOG","Item "+list.get(position)+" pulsado");
                connect(list.get(position));
            }
        });
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            Toast.makeText(getBaseContext(), "El dispositivo no soporta bluetooth", Toast.LENGTH_LONG).show();
        } else {
            Log.d("LOG","Bluetooth soportado");

            // Habilito el Bluetooth si está deshabilitado
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }

            pairedDevices = mBluetoothAdapter.getBondedDevices();
            list = new ArrayList();
            for (BluetoothDevice device : pairedDevices){
                list.add(device);
            }
            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
            devices_list.setAdapter(adapter);
        }
    }

    public void on(View view){
        Log.d("LOG","Button ON clicked");
    }

    public void off(View view){
        Log.d("LOG","Button OFF clicked");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_ENABLE_BT){
            if(mBluetoothAdapter.isEnabled()){
                status_txt.setText("Bluetooth Enabled");
                Log.d("LOG","status modified: Enabled");
            }else{
                status_txt.setText("Bluetooth Disabled");
                Log.d("LOG","status modified: Disabled");
            }
        }
    }

    public void connect(BluetoothDevice device){
        BluetoothSocket mSocket = null;
        try {
            mSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) { }

        try {
            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception
            mSocket.connect();
        } catch (IOException connectException) {
            // Unable to connect; close the socket and get out
            try {
                mSocket.close();
            } catch (IOException closeException) { }
            return;
        }
    }

    /*
    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            // Use a temporary object that is later assigned to mmSocket,
            // because mmSocket is final
            BluetoothSocket tmp = null;
            mmDevice = device;

            // Get a BluetoothSocket to connect with the given BluetoothDevice
            try {
                // MY_UUID is the app's UUID string, also used by the server code
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) { }
            mmSocket = tmp;
        }

        public void run() {
            // Cancel discovery because it will slow down the connection
            mBluetoothAdapter.cancelDiscovery();

            try {
                // Connect the device through the socket. This will block
                // until it succeeds or throws an exception
                mmSocket.connect();
            } catch (IOException connectException) {
                // Unable to connect; close the socket and get out
                try {
                    mmSocket.close();
                } catch (IOException closeException) { }
                return;
            }

            // Do work to manage the connection (in a separate thread)
            manageConnectedSocket(mmSocket);
        }

        /** Will cancel an in-progress connection, and close the socket
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }
    */

    public void manageConnectedSocket(BluetoothSocket bs){
        Log.d("LOG","manageConnectedSocket");
    }
    /*
    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
    */
}
