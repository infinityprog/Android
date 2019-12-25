package com.example.projetandroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetandroid.Entity.BitAdaptater;
import com.example.projetandroid.Simatic_S7.IntByRef;
import com.example.projetandroid.Simatic_S7.S7;
import com.example.projetandroid.Simatic_S7.S7Client;
import com.example.projetandroid.Simatic_S7.S7CpuInfo;
import com.example.projetandroid.Simatic_S7.S7OrderCode;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class ReadTaskS7 {
    private static final int MESSAGE_PRE_EXECUTE = 1;
    private static final int MESSAGE_STATUS_UPDATE = 2;
    private static final int MESSAGE_POST_EXECUTE = 3;
    private static final int MESSAGE_BIT_UPDATE = 4;
    private AtomicBoolean isRunning = new AtomicBoolean(false);
    private ProgressBar pb_main_progressionS7;
    private Button bt_main_ConnexS7;
    private String name;
    private View vi_main_ui;
    private TextView tv_main_plc;
    private AutomateS7 plcS7;
    private Thread readThread;
    private boolean finish = false;

    private S7Client comS7;
    private String[] param = new String[10];
    private byte[] datasPLC = new byte[512];

    private int dbNumber;
    private EditText address;
    private ListView lst_Byte;
    private TextView version;
    private TextView statut;
    private Spinner size;
    private EditText position;
    private Context context;
    private ArrayList<Integer> data;

    private TextView nbrCPB;
    private TextView nbrBouteille;

    private TextView bits[] ;
    private TextView valeur;

    //Cuve

    private ProgressBar niveau;
    private TextView pourcent;



    public ReadTaskS7(View v, Button b, ProgressBar p, TextView t) {
        vi_main_ui = v;
        bt_main_ConnexS7 = b;
        pb_main_progressionS7 = p;
        tv_main_plc = t;
        comS7 = new S7Client();
        plcS7 = new AutomateS7();
        readThread = new Thread(plcS7);
    }


    public ReadTaskS7(View v, TextView t,TextView version,TextView statut,ListView lst_Byte, int valBit, String size, int dbNumber,Context context) {
        vi_main_ui = v;
        tv_main_plc = t;
        this.version = version;
        this.statut = statut;
        this.lst_Byte = lst_Byte;
        /*this.address = valBit;
        this.size = size;*/
        this.dbNumber = dbNumber;
        this.context = context;
        comS7 = new S7Client();
        plcS7 = new AutomateS7();
        readThread = new Thread(plcS7);
    }

    //Comprimé
    public ReadTaskS7(View v, TextView t,TextView version,TextView statut, TextView nbrCPB, TextView nbrBouteille,int dbNumber) {
        vi_main_ui = v;
        tv_main_plc = t;
        this.version = version;
        this.statut = statut;
        this.nbrCPB = nbrCPB;
        this.nbrBouteille = nbrBouteille;
        this.dbNumber = dbNumber;
        comS7 = new S7Client();
        plcS7 = new AutomateS7();
        readThread = new Thread(plcS7);
    }

    public ReadTaskS7(View vi_main_ui, int dbNumber, TextView statut, TextView nbrCPB, TextView nbrBouteille, TextView bits[]) {
        this.vi_main_ui = vi_main_ui;
        this.dbNumber = dbNumber;
        this.statut = statut;
        this.nbrCPB = nbrCPB;
        this.nbrBouteille = nbrBouteille;
        this.bits = bits;
        comS7 = new S7Client();
        plcS7 = new AutomateS7();
        readThread = new Thread(plcS7);
    }

    public ReadTaskS7(View vi_main_ui, int dbNumber, EditText address, TextView statut, Spinner size, EditText position, TextView valeur) {
        this.vi_main_ui = vi_main_ui;
        this.dbNumber = dbNumber;
        this.address = address;
        this.statut = statut;
        this.size = size;
        this.position = position;
        this.valeur = valeur;
        comS7 = new S7Client();
        plcS7 = new AutomateS7();
        readThread = new Thread(plcS7);
    }

    //Cuve

    //info


    public ReadTaskS7(View vi_main_ui,TextView t,TextView version,TextView statut, int dbNumber, ProgressBar niveau, TextView pourcent) {
        this.vi_main_ui = vi_main_ui;
        tv_main_plc = t;
        this.version = version;
        this.statut = statut;
        this.dbNumber = dbNumber;
        this.niveau = niveau;
        this.pourcent = pourcent;
        comS7 = new S7Client();
        plcS7 = new AutomateS7();
        readThread = new Thread(plcS7);
    }

    public void Stop() {
        isRunning.set(false);
        comS7.Disconnect();
        readThread.interrupt();
    }

    public void Start(String a, String r, String s) {
        if (!readThread.isAlive()) {
            param[0] = a;
            param[1] = r;
            param[2] = s;
            readThread.start();
            isRunning.set(true);
        }
    }

    private void downloadOnPreExecute(int t) {
        Toast.makeText(vi_main_ui.getContext(),
                "Le traitement de la tâche de fond est démarré !" + "\n"
                , Toast.LENGTH_SHORT).show();
        if(t == 0){
            tv_main_plc.setText("Erreur connexion automate");
            this.name = ("Erreur connexion automate");
            //this.Stop();
        }
        else {
            tv_main_plc.setText("PLC : " + String.valueOf(t));
            this.name = "PLC : " + String.valueOf(t);
        }
    }

    public String getName() {
        return this.name;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    private void downloadOnProgressUpdate(ArrayList<Integer> bits, String address) {
        //pb_main_progressionS7.setProgress(progress);
    }

    private void updateListView(ArrayList<Integer> bits, String address){
        BitAdaptater adapter = new BitAdaptater(context, bits,address);
        this.getLst_Byte().setAdapter(adapter);
    }

    public int getDbNumber() {
        return dbNumber;
    }

    public void setDbNumber(int dbNumber) {
        this.dbNumber = dbNumber;
    }

    /*public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }*/

    public ListView getLst_Byte() {
        return lst_Byte;
    }

    public void setLst_Byte(ListView lst_Byte) {
        this.lst_Byte = lst_Byte;
    }

    public TextView getVersion() {
        return version;
    }

    public void setVersion(TextView version) {
        this.version = version;
    }

    public TextView getStatut() {
        return statut;
    }

    public void setStatut(TextView statut) {
        this.statut = statut;
    }

   /* public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }*/

    private void statusType(int status){

        switch (status){

            case 4:
                this.getStatut().setText("Stop");
                this.getStatut().setTextColor(Color.parseColor("#FF8C42"));
                break;
            case 8:
                this.getStatut().setText("Run");
                this.getStatut().setTextColor(Color.parseColor("#8AFF56"));
                break;
            default:
                this.getStatut().setText("Error");
                this.getStatut().setTextColor(Color.parseColor("#EB524D"));
                break;
        }
    }

    private int convBoolInt(boolean bool){
        if(bool){
            return 1;
        }
        else {
            return 0;
        }
    }

    private void setBits(){

        for (int i = 0; i < 8 ; i++){
        bits[i].setText(String.valueOf(convBoolInt(S7.GetBitAt(datasPLC, 0,i))));
        }

        bits[8].setText(String.valueOf(convBoolInt(S7.GetBitAt(datasPLC, 1,0))));
        bits[9].setText(String.valueOf(convBoolInt(S7.GetBitAt(datasPLC, 1,2))));
        bits[10].setText(String.valueOf(convBoolInt(S7.GetBitAt(datasPLC, 1,3))));
        bits[11].setText(String.valueOf(convBoolInt(S7.GetBitAt(datasPLC, 1,6))));

        for (int i = 0; i < 5 ; i++){
            bits[i+12].setText(String.valueOf(convBoolInt(S7.GetBitAt(datasPLC, 4,i))));
        }


    }

    private void write(){

        if (size.getSelectedItem().toString().equals("Bit")){
            if(address.getText().toString().equals("") || position.getText().toString().equals("")){
                valeur.setText("");
            }
            else {
                valeur.setText(String.valueOf(convBoolInt(S7.GetBitAt(datasPLC, Integer.parseInt(address.getText().toString()), Integer.parseInt(position.getText().toString())))));
                System.out.println("bit : " + String.valueOf(convBoolInt(S7.GetBitAt(datasPLC, Integer.parseInt(address.getText().toString()), Integer.parseInt(position.getText().toString())))));
            }
        }
        else {
            if(address.getText().toString().equals("")){
                valeur.setText("");
            }
            else {
                valeur.setText(String.valueOf(S7.GetWordAt(datasPLC, Integer.parseInt(address.getText().toString()))));
                System.out.println("word : " + String.valueOf(S7.GetWordAt(datasPLC, Integer.parseInt(address.getText().toString()))));
            }
        }
    }

    private void infoCuve(){

        niveau.setProgress(S7.GetWordAt(datasPLC, 16)/10);
        pourcent.setText(String.valueOf(S7.GetWordAt(datasPLC, 16)/10)+"%");
    }

    private void downloadOnPostExecute() {
        Toast.makeText(vi_main_ui.getContext(),
                "Le traitement de la tâche de fond est terminé !"
                , Toast.LENGTH_LONG).show();
        //pb_main_progressionS7.setProgress(0);
        tv_main_plc.setText("PLC : /!\\");
    }

    private Handler monHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE_PRE_EXECUTE:
                    downloadOnPreExecute(msg.arg1);
                    break;
                case MESSAGE_STATUS_UPDATE:
                    statusType(msg.arg1);
                    break;
                case MESSAGE_POST_EXECUTE:
                    downloadOnPostExecute();
                    break;
                case MESSAGE_BIT_UPDATE:
                    updateListView(data, address + ".");
                    break;
                default:
                    break;
            }
        }
    };



    private class AutomateS7 implements Runnable {
        @Override
        public void run() {
            try {
                comS7.SetConnectionType(S7.S7_BASIC);
                Integer res = comS7.ConnectTo(param[0], Integer.valueOf(param[1]), Integer.valueOf(param[2]));
                S7OrderCode orderCode = new S7OrderCode();
                Integer result = comS7.GetOrderCode(orderCode);
                int numCPU = -1;
                if (res.equals(0) && result.equals(0)) {
                    numCPU = Integer.valueOf(orderCode.Code().toString().substring(5, 8));
                } else numCPU = 0000;
                if(tv_main_plc != null) {
                    sendPreExecuteMessage(numCPU);
                }
                S7CpuInfo info = new S7CpuInfo();
                comS7.GetCpuInfo(info);
                if(getVersion() != null) {
                    getVersion().setText(info.ASName());
                }

                while(isRunning.get()){
                    if (res.equals(0)){
                        int retInfo = comS7.ReadArea(S7.S7AreaDB,dbNumber,0,34,datasPLC);
                        data= new ArrayList<>();
//int dataB=0;
                        IntByRef status = new IntByRef();
                        comS7.GetPlcStatus(status);
                        sendStatusMessage(status.Value);

                        if (retInfo ==0) {
                            if (nbrCPB != null) {
                                nbrCPB.setText(String.valueOf(S7.GetWordAt(datasPLC, 14)));
                            }

                            if (nbrBouteille != null) {
                                nbrBouteille.setText(String.valueOf(S7.GetWordAt(datasPLC, 16)));
                            }
                            if(bits != null) {
                                setBits();
                            }

                            if(size != null){
                                write();
                            }

                            if(niveau != null){
                                infoCuve();
                            }
                        }
                        //Log.i("Variable A.P.I. -> ", String.valueOf(data));
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                sendPostExecuteMessage();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        private void sendPostExecuteMessage() {
            Message postExecuteMsg = new Message();
            postExecuteMsg.what = MESSAGE_POST_EXECUTE;
            monHandler.sendMessage(postExecuteMsg);
        }
        private void sendPreExecuteMessage(int v) {
            Message preExecuteMsg = new Message();
            preExecuteMsg.what = MESSAGE_PRE_EXECUTE;
            preExecuteMsg.arg1 = v;
            monHandler.sendMessage(preExecuteMsg);
        }
        /*private void sendProgressMessage(ArrayList<Integer> bits, String address) {
            Message progressMsg = new Message();
            progressMsg.what = MESSAGE_PROGRESS_UPDATE;
            progressMsg.arg1 = 1;
            monHandler.sendMessage(progressMsg);
        }*/
        private void sendStatusMessage(int s){
            Message progressMsg = new Message();
            progressMsg.what = MESSAGE_STATUS_UPDATE;
            progressMsg.arg1 = s;
            monHandler.sendMessage(progressMsg);
        }

        private void sendBitMessage(){
            Message progressMsg = new Message();
            progressMsg.what = MESSAGE_BIT_UPDATE;
            monHandler.sendMessage(progressMsg);
        }
    }
}

