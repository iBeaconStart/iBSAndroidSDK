package com.ibeaconstart.ibsandroidsdk;

import android.content.Context;
import android.util.Log;

import org.altbeacon.beacon.BeaconManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rgiurea on 31/07/15.
 */
public class IBSSDK {

    private static IBSSDK instance;
    private List<IBSSDKListener> listeners;

    private IBSSDK() {
        listeners = new ArrayList<>();
    }

    public static IBSSDK getInstance() {
        if (instance == null) {
            instance = new IBSSDK();
        }

        return instance;
    }

    public boolean initWithListener(IBSSDKListener listener){
        return addListener(listener);
    }

    public boolean addListener(IBSSDKListener listener) {
        boolean success = false;

        if (listener != null && !listeners.contains(listener)) {
            success = listeners.add(listener);
        }

        return success;
    }


    public boolean removeListener(IBSSDKListener listener) {
        boolean success = false;

        if (listener != null) {
            success = listeners.remove(listener);
        }

        return success;
    }


    public void verifyBluetooth(Context context) {
        try {
            if (!BeaconManager.getInstanceForApplication(context).checkAvailability()) {
                bluetoothNotActivated();

            }
        } catch (RuntimeException e) {
            bluetoothLENotSupported();

        }
    }


    protected void bluetoothNotActivated() {
        Log.d("IBS-App", "bluetoothNotActivated");

        for (IBSSDKListener listener : listeners) {
            listener.bluetoothNotActivated();
        }
    }


    protected void bluetoothLENotSupported() {
        Log.d("IBS-App", "bluetoothLENotSupported");
        for (IBSSDKListener listener : listeners) {
            listener.bluetoothLENotSupported();
        }
    }
}
