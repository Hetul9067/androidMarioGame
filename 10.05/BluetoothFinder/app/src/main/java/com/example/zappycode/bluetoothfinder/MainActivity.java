package com.example.zappycode.bluetoothfinder;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    TextView statusTextView;
    Button searchButton;
    ArrayList<String> bluetoothDevices = new ArrayList<>();
    ArrayList<String> addresses = new ArrayList<>();
    ArrayAdapter arrayAdapter;


    BluetoothAdapter bluetoothAdapter;

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.i("Action", action);

            if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                statusTextView.setText("Finished");
                searchButton.setEnabled(true);
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                String name = device.getName();
                String address = device.getAddress();
                String rssi = Integer.toString(intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE));
                //Log.i("Device Found","Name: " + name + " Address: " + address + " RSSI: " + rssi);

                if (!addresses.contains(address)) {
                    addresses.add(address);
                    String deviceString = "";
                    if (name == null || name.equals("")) {
                        deviceString = address + " - RSSI " + rssi + "dBm";
                    } else {
                        deviceString = name + " - RSSI " + rssi + "dBm";
                    }

                    bluetoothDevices.add(deviceString);
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        }
    };

    public void searchClicked(View view) {
        statusTextView.setText("Searching...");
        searchButton.setEnabled(false);
        bluetoothDevices.clear();
        addresses.clear();


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions

            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.i("error", "hello this is searchClicked method");
            return;
        }
        bluetoothAdapter.startDiscovery();
    }


    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        statusTextView = findViewById(R.id.statusTextView);
        searchButton = findViewById(R.id.searchButton);

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, bluetoothDevices);

        listView.setAdapter(arrayAdapter);

        BluetoothManager bluetoothManager = getSystemService(BluetoothManager.class);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
//        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        if (bluetoothAdapter == null) {
            Log.i("error bluetooth hetul", "doesn't support ");
        }
//        if(!bluetoothAdapter.isEnabled()){
//            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
//
//        }


        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            int REQUEST_ENABLE_BT = 0;

            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(broadcastReceiver, intentFilter);


    }
}
