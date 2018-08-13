package project.android_projects.com.bluetoothprintervirtual;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import woyou.aidlservice.jiuiv5.IWoyouService;

public class MainActivity extends AppCompatActivity{
    private static TextView displayTv,printerName,labelTv;
    private static BluetoothAdapter blueAdp;
    private static BluetoothDevice innerPrinter;
    private EditText userNameInput;
    private static UUID PRINTER_UUID=UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String InnerPrinter_Address="00:11:22:33:44:55";
    private BluetoothSocket blueSocket;
    private static final String SERVICE＿PACKAGE = "woyou.aidlservice.jiuiv5";
    private static final String SERVICE＿ACTION = "woyou.aidlservice.jiuiv5.IWoyouService";
    private static String serviceVer;
    private static byte[] readBuff;
    private static OutputStream outputData;
    private static InputStream inputData;
    private static IWoyouService woyouService;
    private Button findBtn,connectBtn,printBtn,disconnectBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //View widget inits
        displayTv=(TextView)findViewById(R.id.displayTxt);
        printerName=(TextView)findViewById(R.id.printerNameTV);
        labelTv=(TextView)findViewById(R.id.labelTv);
        userNameInput=(EditText)findViewById(R.id.usrNameInput);

        btnInit();
        setBtnOnClicks();
    }

    private void btnInit(){
        findBtn=(Button)findViewById(R.id.searchBtn);
        connectBtn=(Button)findViewById(R.id.connectBtn);
        printBtn=(Button)findViewById(R.id.printBtn);
        disconnectBtn=(Button)findViewById(R.id.disconnectBtn);
    }
    private ServiceConnection serviceConn=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            woyouService=IWoyouService.Stub.asInterface(iBinder);
            try{
                serviceVer=woyouService.getServiceVersion();
                labelTv.setText("Service Version: "+serviceVer+"\n");
            }catch (RemoteException re){
                Log.d("REMOTE",re.getMessage());
            }
            bindingService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Toast.makeText(MainActivity.this,"Service Disconnected"
                    ,Toast.LENGTH_SHORT).show();
            woyouService=null;
            try{
                Thread.sleep(2000);
            }catch (InterruptedException ie){
                Log.d("INTERRUPT",ie.getMessage());
            }
        }
    };

    private void bindingService() {
        Intent intent=new Intent();
        intent.setPackage("woyou.aidlservice.jiuiv5");
        intent.setAction("woyou.aidlservice.jiuiv5.IWoyouService");
        this.getApplicationContext().startService(intent);
        this.getApplicationContext().bindService(intent,serviceConn, Context.BIND_AUTO_CREATE);
    }

    private void setBtnOnClicks(){

        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    bindingService();
                }catch (Exception e){
                    Log.d("Connect",e.getMessage());
                }
            }
        });
        printBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isConnected()){
                    printData();
                }else{
                    Toast.makeText(MainActivity.this,
                            "Service Not Connected", Toast.LENGTH_SHORT).show();
                }
            }
        });
        disconnectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**The service must implement the IBinder interface as appropriate.
                 * The client applications can then bind to the service and call methods from the
                 * IBinder to perform IPC.*/
                if(woyouService!=null){
                    getApplicationContext().unbindService(serviceConn);
                    woyouService=null;
                }
                Toast.makeText(MainActivity.this,"Service Disconnected",Toast.LENGTH_SHORT).show();
            }
        });
    }


    /** Print text through Inner Printer*/
    private void printData(){
        try{
            String msg=userNameInput.getText().toString();
            msg+="\n";
            outputData.write(msg.getBytes());
            displayTv.setText("Printing Text..");
        }catch (IOException e){
            Log.d("Print ERROR",e.getMessage());
        }
    }
    private boolean isConnected(){
        if(woyouService!=null){
            return true;
        }else{
            return false;
        }
    }
}
