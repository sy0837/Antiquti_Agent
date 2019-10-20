package com.example.antiquti_agent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

public class scanactivity extends AppCompatActivity {
    WifiManager wifiManager;
    public static final int REQUEST_CODE=100;
    public static final int PERMISSION_REQUEST=200;
    String SSID;
    String PASSWORD;
    TextView result;
    Button btn;
    Button connect;
    private int n=2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanactivity);

        wifiManager= (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},PERMISSION_REQUEST);
        }
        btn=findViewById(R.id.scanbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(scanactivity.this,scanner.class);
                startActivityForResult(intent,REQUEST_CODE);


            }
        });
        connect=findViewById(R.id.connectbtn);

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectToWifi(SSID,PASSWORD);
                ConnectivityManager connManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                final NetworkInfo mwifi=connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            }
        });

    }


    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, final int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                final Barcode barcode = data.getParcelableExtra("barcode");
                result.post(new Runnable() {
                    @Override
                    public void run() {

                        SSID=barcode.wifi.ssid;
                        PASSWORD=barcode.wifi.password;
                        connect.setVisibility(View.VISIBLE);



                    }
                });
            }
        }
    }

    public void connectToWifi(String SSID,String PASSWORD){
        if(!wifiManager.isWifiEnabled()){
            wifiManager.setWifiEnabled(true);
        }
        WifiConfiguration conf =new WifiConfiguration();
        conf.SSID=String.format("\"%s\"",SSID);
        conf.preSharedKey="\""+PASSWORD+"\"";

        wifiManager.addNetwork(conf);

        List<WifiConfiguration> wifiList=wifiManager.getConfiguredNetworks();
        for(WifiConfiguration i:wifiList){
            if(i.SSID!=null&&i.SSID.equals("\""+SSID+"\"")){
                wifiManager.disconnect();
                wifiManager.enableNetwork(i.networkId,true);
                conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
                conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
                conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
                conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
                conf.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
                wifiManager.reconnect();

                break;
            }
        }

    }
}
