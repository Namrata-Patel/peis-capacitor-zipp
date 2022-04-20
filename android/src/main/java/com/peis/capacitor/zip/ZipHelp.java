package com.peis.capacitor.zip;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;


public class ZipHelp {

    public String echo(String value) {
        Log.i("Echo", value);
        return value;
    }

    // #region Public Methods

    public void unZip(PluginCall call) {
        String source = call.getString("source", "");
        String destination = call.getString("destination", "");
        String password = call.getString("password", "");

        Log.i("Unzip", source);
        
        // Validate Inputs
        if (source.equals("")) {
            call.reject("No source specified", ErrorCodes.NO_SOURCE_SPECIFIED);
            return;
        }
        if (destination.equals("")) {
            call.reject("No destination specified", ErrorCodes.NO_DESTINATION_SPECIFIED);
            return;
        }

        File zfile = new File(source);
        if (!zfile.exists()) {
            call.reject("Source file doesn't exist", ErrorCodes.NO_FILE_EXISTS);
            return;
        }

        try (ZipInputStream zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(source)))) {
            ZipEntry ze;
            int count;
            byte[] buffer = new byte[8192];
            while ((ze = zis.getNextEntry()) != null) {
                File file = new File(destination, ze.getName());
                File dir = ze.isDirectory() ? file : file.getParentFile();
                if (!dir.isDirectory() && !dir.mkdirs())
                    throw new FileNotFoundException("Failed to ensure directory: " + dir.getAbsolutePath());
                if (ze.isDirectory())
                    continue;
                try (FileOutputStream fout = new FileOutputStream(file)) {
                    while ((count = zis.read(buffer)) != -1)
                        fout.write(buffer, 0, count);
                }
            }
        } catch (Exception e) {
            call.reject("Zip Error Occurred", ErrorCodes.UNKNOWN_ERROR, e);
            e.printStackTrace();
        }

        JSObject ret = new JSObject();
        ret.put("message", ErrorCodes.SUCCESS);
        ret.put("uri", "file://" + destination);
        call.resolve(ret);
    }

    // #endregion

    


}
