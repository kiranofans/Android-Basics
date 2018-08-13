package Utilities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;
/** Use virtual bluetooth to connect to Inner Printer*/
public class BluetoothUtil {
    private static UUID PRINTER_UUID=UUID.fromString("0001101-0000-1000-8000-00805F9B34FB");
    private static final String InnerPrinter_Address="00:11:22:33:44:55";

    //Initialize BluetoothAdapter
    public static BluetoothAdapter getBTAdapter(){
        return BluetoothAdapter.getDefaultAdapter();
    }

    //Searching for bluetooth devices
    public static BluetoothDevice findDevices(BluetoothAdapter blueAdp){
        BluetoothDevice innerPrinter=null;
        Set<BluetoothDevice> blueDevices=blueAdp.getBondedDevices();
        for(BluetoothDevice device:blueDevices){
            if(device.getAddress().equals(InnerPrinter_Address)){
                innerPrinter=device;
                break;
            }
        }
        return innerPrinter;
    }
    public static BluetoothSocket getBluetoothSocket(BluetoothDevice device){
        BluetoothSocket blueSocket=null;
        try {
            blueSocket=device
                    .createInsecureRfcommSocketToServiceRecord(PRINTER_UUID);
            blueSocket.connect();

        } catch (IOException e) {
            Log.d("IO ERROR",e.getMessage());
        }
        return blueSocket;
    }
    public static void sendData(byte[] bytes,BluetoothSocket blueSocket){
        try{
            OutputStream outputData=blueSocket.getOutputStream();
            outputData.write(bytes,0,bytes.length);
            outputData.close();
        }catch (IOException e){
            Log.d("IO ERROR",e.getMessage());
        }
    }
}
