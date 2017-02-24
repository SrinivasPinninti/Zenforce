package com.evoke.zenforce.utility;

import com.evoke.zenforce.view.application.ZenForceApplication;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

/**
 * Created by spinninti on 12/28/2016.
 */
public class Test {


    public Test() {
        BarcodeDetector detector =
                new BarcodeDetector.Builder(ZenForceApplication.getInstance())
                        .setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE)
                        .build();
        if(!detector.isOperational()){
//            txtView.setText("Could not set up the detector!");
            return;
        }
    }



}
