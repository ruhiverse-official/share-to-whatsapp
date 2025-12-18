package com.pareshgami.shareto;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import android.util.Log;

@CapacitorPlugin(name = "Shareto")
public class SharetoPlugin extends Plugin {

    private Shareto implementation;

    @Override
    public void load() {
        implementation = new Shareto(getContext());
    }

    @PluginMethod
    public void shareImage(PluginCall call) {
        String base64 = call.getString("base64");
        String fileName = call.getString("fileName");
        String phoneNumber = call.getString("phoneNumber");
        String message = call.getString("message");
        String bundleId = call.getString("bundleId");

        try {
            implementation.shareImage(base64, fileName, phoneNumber, message, bundleId);
            call.resolve();
        } catch (IllegalArgumentException e) {
            Log.e("SharetoPlugin", "Invalid argument: " + e.getMessage(), e);
            call.reject("Invalid argument: " + e.getMessage());
        } catch (Exception e) {
            Log.e("SharetoPlugin", "Unexpected error: " + e.getMessage(), e);
            call.reject("Unexpected error: " + e.getMessage());
        }
    }

    @PluginMethod
    public void sharePdf(PluginCall call) {
        String base64 = call.getString("base64");
        String fileName = call.getString("fileName");
        String phoneNumber = call.getString("phoneNumber");
        String message = call.getString("message");
        String bundleId = call.getString("bundleId");

        try {
            implementation.sharePdf(base64, fileName, phoneNumber, message, bundleId);
            call.resolve();
        } catch (IllegalArgumentException e) {
            Log.e("SharetoPlugin", "Invalid argument: " + e.getMessage(), e);
            call.reject("Invalid argument: " + e.getMessage());
        } catch (Exception e) {
            Log.e("SharetoPlugin", "Unexpected error: " + e.getMessage(), e);
            call.reject("Unexpected error: " + e.getMessage());
        }
    }
}
