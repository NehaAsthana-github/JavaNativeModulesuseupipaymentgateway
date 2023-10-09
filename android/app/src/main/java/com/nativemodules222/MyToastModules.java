package com.nativemodules222;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MyToastModules extends ReactContextBaseJavaModule {
    static final int UPI_PAYMENT_REQUEST_CODE =1 ;

    public MyToastModules(@Nullable ReactApplicationContext reactContext){
        super(reactContext);
    }
    @NonNull
    @Override
    public String getName() {
        return "MyToastNativeModule";
    }
    @ReactMethod

    public void subModuleMethode(){
        Context applicationContext= getReactApplicationContext();
        Toast.makeText(applicationContext, "Hii This is toast showing message", Toast.LENGTH_SHORT).show();
    }
    @ReactMethod
    public void showAlertMessage(String name, Callback callback){
       String message="my name is," +name;
       callback.invoke(null,message);
    }
    private String generateTransactionReference() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = sdf.format(new Date());

        // Generate a random 6-digit number
        Random random = new Random();
        int randomNum = random.nextInt(900000) + 100000;

        // Combine timestamp and random number to create a unique reference
        String transactionReference = timestamp + randomNum;

        return transactionReference;
    }
    @ReactMethod
    public void initiateUpiPayment(String upiId, String amount, Callback callback) {
        try {
            Uri uri = new Uri.Builder()
                    .scheme("upi")
                    .authority("pay")
                    .appendQueryParameter("pa", upiId)
                    .appendQueryParameter("pn", "Golden Duck") // Replace with recipient's name
                    .appendQueryParameter("tn", "") // Replace with transaction note
                    .appendQueryParameter("tr", generateTransactionReference()) // Replace with a unique transaction reference
                    .appendQueryParameter("am", amount)
                    .appendQueryParameter("cu", "INR")
                    .build();

            Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
            upiPayIntent.setData(uri);
            Activity currentActivity = getCurrentActivity();
            if (currentActivity != null) {
                currentActivity.startActivityForResult(upiPayIntent, UPI_PAYMENT_REQUEST_CODE);
                callback.invoke(null, "Payment initiated successfully");
            } else {
                callback.invoke("No current activity found", null);
            }
        } catch (Exception e) {
            callback.invoke(e.getMessage(), null);
        }
    }
}
