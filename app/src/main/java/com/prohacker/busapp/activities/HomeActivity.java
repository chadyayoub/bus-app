package com.prohacker.busapp.activities;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.prohacker.busapp.R;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    BluetoothAdapter mBluetoothAdapter;
    ImageView noBluetoothImage;
    private ArrayList<String> mDeviceList = new ArrayList<String>();
    TextView noBluetoothText;
    ListView listView;
    Button bluetoothButton;
    Button gpsButton;
    BluetoothManager mBluetoothManager;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void initView() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        noBluetoothImage = findViewById(R.id.bluetooth_error_image);
        noBluetoothText = findViewById(R.id.bluetooth_error_text);
        bluetoothButton = findViewById(R.id.button_bluetooth);
        gpsButton = findViewById(R.id.button_gps);
        listView = (ListView) findViewById(R.id.list_view);
        mBluetoothManager = getSystemService(BluetoothManager.class);
        bluetoothButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBluetooth();
            }
        });

        checkBluetooth();
    }

    void showError() {
        listView.setActivated(false);
        listView.setVisibility(View.INVISIBLE);
        noBluetoothText.setActivated(true);
        noBluetoothText.setVisibility(View.VISIBLE);
        noBluetoothImage.setActivated(true);
        noBluetoothImage.setVisibility(View.VISIBLE);
    }

    void checkBluetooth() {
        //Toast.makeText(this, "Trying to connect", Toast.LENGTH_SHORT).show();
        if (mBluetoothAdapter == null) {
            noBluetoothText.setText(R.string.bluetooth_unsupported);
            showError();
            //Toast.makeText(this, "Bluetooth not supported on this device!", Toast.LENGTH_SHORT).show();
        } else if (!mBluetoothAdapter.isEnabled()) {
            //Toast.makeText(this, "Please enable bluetooth and try again", Toast.LENGTH_SHORT).show();
            showError();
            noBluetoothImage.setVisibility(View.VISIBLE);
        } else {
            //Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
            hideError();
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "W ER", Toast.LENGTH_SHORT).show();
                startActivityForResult(enableBtIntent, 5);
            }
            getListDevices();
        }
    }
    void requestPermission(String perm, int code, String reason){
        new AlertDialog.Builder(this)
                .setTitle("Permission needed")
                .setMessage("This permission is needed "+reason)
                .setPositiveButton("ok", (dialog, which) -> ActivityCompat.requestPermissions(HomeActivity.this,
                        new String[] {perm}, code))
                .setNegativeButton("cancel", (dialog, which) -> dialog.dismiss())
                .create().show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 5) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
    void hideError(){
        listView.setActivated(true);
        listView.setVisibility((View.VISIBLE));
        noBluetoothText.setActivated(false);
        noBluetoothText.setVisibility(View.INVISIBLE);
        noBluetoothImage.setActivated(false);
        noBluetoothImage.setVisibility(View.INVISIBLE);
    }

    void getListDevices() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {

            requestPermission(Manifest.permission.BLUETOOTH_SCAN, 5, " to be able to communicate with smart boots");

        }
        mBluetoothAdapter.startDiscovery();
        Toast.makeText(this, "reached startDiscovery", Toast.LENGTH_SHORT).show();
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.

                }
                Toast.makeText(HomeActivity.this, "reached discovered", Toast.LENGTH_SHORT).show();
                Log.d("dsadsa","mDeviceList.add(device.getName() + \"\\n\" + device.getAddress());");
                mDeviceList.add(device.getName() + "\n" + device.getAddress());
                listView.setAdapter(new ArrayAdapter<String>(context,
                        android.R.layout.simple_list_item_1, mDeviceList));
            }
        }
    };
}