package com.prohacker.busapp.activities;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.location.LocationListenerCompat;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.prohacker.busapp.R;
import com.prohacker.busapp.services.SoundPlayer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Timer;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HomeActivity extends AppCompatActivity implements LocationListenerCompat {

    static final UUID mUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    ImageView noBluetoothImage;
    TextView noBluetoothText;
    Button bluetoothButton;
    Button testButton;
    Button gpsButton;
    ListView listView;

    String[] strings;

    BluetoothManager bluetoothManager;
    BluetoothAdapter bluetoothAdapter;

    SendReceive sendReceive;

    static final int STATE_LISTENING = 1;
    static final int STATE_CONNECTING = 2;
    static final int STATE_CONNECTED = 3;
    static final int STATE_CONNECTION_FAILED = 4;
    static final int STATE_MESSAGE_RECEIVED = 5;

    int BLUETOOTH_ACTION_REQUEST_ENABLE = 1;
    int ACCESS_FINE_LOCATION_REQUEST_CODE = 2;
    int ACCESS_COARSE_LOCATION_REQUEST_CODE = 3;
    int BLUETOOTH_ADMIN_REQUEST = 4;
    int SEND_SMS = 5;

    LocationManager mLocationManager;
    SmsManager smsManager;

    SoundPlayer player;

    private String testNumber = "+96176866097";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void initView() {
        player = new SoundPlayer(this);
        testButton = findViewById((R.id.button_play_sound));
        noBluetoothImage = findViewById(R.id.bluetooth_error_image);
        noBluetoothText = findViewById(R.id.bluetooth_error_text);
        bluetoothButton = findViewById(R.id.button_bluetooth);
        gpsButton = findViewById(R.id.button_gps);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        listView = findViewById(R.id.list_view);
        bluetoothButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBluetooth();
            }
        });
        checkBluetooth();
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSMS("");
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void checkBluetooth() {
        bluetoothManager = getSystemService(BluetoothManager.class);
        //checks if device has bluetooth adapter
        if (bluetoothAdapter == null) {
            showError();
            noBluetoothText.setText(R.string.bluetooth_unsupported);
        }
        //checks if device bluetooth is on if not request user to enable it
        else if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            showError();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.BLUETOOTH_ADMIN},
                        BLUETOOTH_ADMIN_REQUEST
                );
            }
            startActivityForResult(enableBtIntent, BLUETOOTH_ACTION_REQUEST_ENABLE);
        } else {
            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
            hideError();
            if (pairedDevices.size() > 0) {
                String[] strings = new String[pairedDevices.size()];
                int index = 0;
                // There are paired devices. Get the name and address of each paired device.
                for (BluetoothDevice device : pairedDevices) {
                    String deviceName = device.getName();
                    String deviceHardwareAddress = device.getAddress(); // MAC address\
                    strings[index] = device.getName();
                    index++;
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, strings);
                    listView.setAdapter(arrayAdapter);
                }
                bluetoothButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(
                                    HomeActivity.this,
                                    new String[]{Manifest.permission.BLUETOOTH_ADMIN},
                                    BLUETOOTH_ADMIN_REQUEST
                            );
                        }

                        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
                        BluetoothDevice[] Geeks = pairedDevices.toArray(new BluetoothDevice[pairedDevices.size()]);
                        ClientClass serverClass = new ClientClass(Geeks[0]);
                        serverClass.start();
                    }
                });
            }

        }
    }


    //To show the error bluetooth on the screen
    void showError() {
        listView.setActivated(false);
        listView.setVisibility(View.INVISIBLE);
        noBluetoothText.setActivated(true);
        noBluetoothText.setVisibility(View.VISIBLE);
        noBluetoothImage.setActivated(true);
        noBluetoothImage.setVisibility(View.VISIBLE);
    }

    //To hide the error bluetooth on the screen
    void hideError() {
        listView.setActivated(true);
        listView.setVisibility((View.VISIBLE));
        noBluetoothText.setActivated(false);
        noBluetoothText.setVisibility(View.INVISIBLE);
        noBluetoothImage.setActivated(false);
        noBluetoothImage.setVisibility(View.INVISIBLE);
    }

    Handler handler = new Handler(new Handler.Callback() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public boolean handleMessage(Message msg) {

            switch (msg.what) {

                case STATE_MESSAGE_RECEIVED:
                    byte[] readBuff = (byte[]) msg.obj;
                    String tempMsg = new String(readBuff, 0, msg.arg1);
                    if(tempMsg.trim().equals("")) break;
                    Toast.makeText(HomeActivity.this, tempMsg, Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        }
    });

    @Override
    public void onLocationChanged(@NonNull Location location) {

            String message = "Some emergency happened, here is my current location: http://maps.google.com/?q=" + location.getLatitude() + "," + + location.getLongitude();
            smsManager.sendTextMessage(testNumber, null, message, null, null);
            Toast.makeText(this, "message sent", Toast.LENGTH_SHORT).show();
            mLocationManager.removeUpdates(this);

    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        LocationListenerCompat.super.onLocationChanged(locations);
    }

    @Override
    public void onFlushComplete(int requestCode) {
        LocationListenerCompat.super.onFlushComplete(requestCode);
    }

    public class ServerClass extends Thread {
        private BluetoothServerSocket serverSocket;


        public ServerClass() {

            try {
                if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            HomeActivity.this,
                            new String[]{Manifest.permission.BLUETOOTH_ADMIN},
                            BLUETOOTH_ADMIN_REQUEST
                    );
                }
                serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord("3akrout", HomeActivity.mUUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            BluetoothSocket socket = null;

            while (socket == null) {
                try {
                    Message message = Message.obtain();
                    message.what = STATE_CONNECTING;
                    handler.sendMessage(message);

                    socket = serverSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                    Message message = Message.obtain();
                    message.what = STATE_CONNECTION_FAILED;
                    handler.sendMessage(message);
                }

                if (socket != null) {
                    Message message = Message.obtain();
                    message.what = STATE_CONNECTED;
                    handler.sendMessage(message);

                    sendReceive = new SendReceive(socket);
                    sendReceive.start();

                    break;
                }
            }
        }
    }

    private class ClientClass extends Thread {
        private BluetoothDevice device;
        private BluetoothSocket socket;

        public ClientClass(BluetoothDevice device1) {
            device = device1;

            try {
                if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            HomeActivity.this,
                            new String[]{Manifest.permission.BLUETOOTH_ADMIN},
                            BLUETOOTH_ADMIN_REQUEST
                    );
                }
                socket = device.createRfcommSocketToServiceRecord(mUUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            HomeActivity.this,
                            new String[]{Manifest.permission.BLUETOOTH_ADMIN},
                            BLUETOOTH_ADMIN_REQUEST
                    );
                }
                socket.connect();
                Message message=Message.obtain();
                message.what=STATE_CONNECTED;
                handler.sendMessage(message);

                sendReceive=new SendReceive(socket);
                sendReceive.start();

            } catch (IOException e) {
                e.printStackTrace();
                Message message=Message.obtain();
                message.what=STATE_CONNECTION_FAILED;
                handler.sendMessage(message);
            }
        }
    }

    private class SendReceive extends Thread
    {
        private final BluetoothSocket bluetoothSocket;
        private final InputStream inputStream;
        private final OutputStream outputStream;

        public SendReceive (BluetoothSocket socket)
        {
            bluetoothSocket=socket;
            InputStream tempIn=null;
            OutputStream tempOut=null;

            try {
                tempIn=bluetoothSocket.getInputStream();
                tempOut=bluetoothSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            inputStream=tempIn;
            outputStream=tempOut;
        }

        public void run()
        {
            byte[] buffer=new byte[1024];
            int bytes;

            while (true)
            {
                try {
                    bytes=inputStream.read(buffer);
                    handler.obtainMessage(STATE_MESSAGE_RECEIVED,bytes,-1,buffer).sendToTarget();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void write(byte[] bytes)
        {
            try {
                outputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void sendSMS(String phoneNumber){
        if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    HomeActivity.this,
                    new String[]{Manifest.permission.SEND_SMS},
                    SEND_SMS
            );
            return;
        }
        smsManager = SmsManager.getDefault();
        double longitude, latitude;
        if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    HomeActivity.this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    ACCESS_COARSE_LOCATION_REQUEST_CODE
            );
            return;
        }
        if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    HomeActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_FINE_LOCATION_REQUEST_CODE
            );
            return;
        }
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(location != null && location.getTime() > Calendar.getInstance().getTimeInMillis() - 2 * 60 * 1000) {
            // Do something with the recent location fix
            //  otherwise wait for the update below
        }
        else {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
    }
}

