package com.example.projetandroid;

import android.util.Log;
import android.widget.EditText;

import com.example.projetandroid.Simatic_S7.S7;
import com.example.projetandroid.Simatic_S7.S7Client;

import java.util.concurrent.atomic.AtomicBoolean;

public class WriteTaskS7{
    private AtomicBoolean isRunning = new AtomicBoolean(false);
    private Thread writeThread;
    private AutomateS7 plcS7;
    private S7Client comS7;
    private String[] parConnexion = new String[10];
    private byte[] motCommande = new byte[10];

    private EditText adrress;
    private String size;
    private int dbNumber;

    public WriteTaskS7(int dbNumber, EditText adrress){
//monAPI = new AutomateS7();
        this.dbNumber = dbNumber;
        this.adrress = adrress;
        comS7 = new S7Client();
        plcS7 = new AutomateS7();
        writeThread = new Thread(plcS7);
    }
    public void Stop(){
        isRunning.set(false);
        comS7.Disconnect();
        writeThread.interrupt();
    }

    public void Start(String a, String r, String s){
        if (!writeThread.isAlive()) {
            parConnexion[0] = a;
            parConnexion[1] = r;
            parConnexion[2] = s;
            writeThread.start();
            isRunning.set(true);
        }
    }

    public AtomicBoolean getIsRunning() {
        return isRunning;
    }

    public void setIsRunning(AtomicBoolean isRunning) {
        this.isRunning = isRunning;
    }


    private class AutomateS7 implements Runnable{
        @Override
        public void run() {
            try {
                comS7.SetConnectionType(S7.S7_BASIC);
                Integer res = comS7.ConnectTo(parConnexion[0],
                        Integer.valueOf(parConnexion[1]),Integer.valueOf(parConnexion[2]));
                while(isRunning.get() && (res.equals(0))){
                    if (!(adrress.getText().toString().equals(""))){
                        Integer writePLC = comS7.WriteArea(S7.S7AreaDB,dbNumber,Integer.parseInt(adrress.getText().toString()),2,motCommande);
                    }

                    /*if(writePLC.equals(0)) {
                        Log.i("ret WRITE : ", String.valueOf(res) + "****" + String.valueOf(writePLC));
                    }*/
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

        public void setWriteBool(int b, int v){
    //Masquage
            if(v==1) motCommande[0] = (byte)(b | motCommande[0]);
            else motCommande[0] = (byte)(~b & motCommande[0]);
            //S7.SetBitAt(motCommande,/*Integer.parseInt(adrress.getText().toString())*/2,b,v);
        }

        public void  setWriteWord(int value){
            S7.SetWordAt(motCommande,0,value);
        }
    }