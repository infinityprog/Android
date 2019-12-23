package com.example.projetandroid.Comprime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.projetandroid.R;
import com.example.projetandroid.ReadTaskS7;

public class ReadComprimeActivity extends AppCompatActivity {

    // nombre de bits
    private TextView bits[] = new TextView[18];
    private TextView statut;
    private TextView nbrCPB;
    private TextView nbrBouteille;
    private ReadTaskS7 readS7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_comprime);
        bits[0] = (TextView) findViewById(R.id.bite_0);
        bits[1] = (TextView) findViewById(R.id.bite_1);
        bits[2] = (TextView) findViewById(R.id.bite_2);
        bits[3] = (TextView) findViewById(R.id.bite_3);
        bits[4] = (TextView) findViewById(R.id.bite_4);
        bits[5] = (TextView) findViewById(R.id.bite_5);
        bits[6] = (TextView) findViewById(R.id.bite_6);
        bits[7] = (TextView) findViewById(R.id.bite_7);

        bits[8] = (TextView) findViewById(R.id.bite_8);
        bits[9] = (TextView) findViewById(R.id.bite_9);
        bits[10] = (TextView) findViewById(R.id.bite_10);
        bits[11] = (TextView) findViewById(R.id.bite_11);
        bits[12] = (TextView) findViewById(R.id.bite_12);
        bits[13] = (TextView) findViewById(R.id.bite_13);
        bits[14] = (TextView) findViewById(R.id.bite_14);
        bits[15] = (TextView) findViewById(R.id.bite_15);

        bits[16] = (TextView) findViewById(R.id.bite_16);
        bits[17] = (TextView) findViewById(R.id.bite_17);

        statut = (TextView)  findViewById(R.id.txv_status);
        nbrCPB = (TextView) findViewById(R.id.txv_nbrCPB);
        nbrBouteille = (TextView) findViewById(R.id.txv_nbrB);
        SharedPreferences sharedpreferences = getSharedPreferences("datablock", Context.MODE_PRIVATE);
        int db = sharedpreferences.getInt("db",-1);
        readS7 = new ReadTaskS7(this.findViewById(android.R.id.content),db,statut,nbrCPB,nbrBouteille,bits);
        sharedpreferences = getSharedPreferences("automate", Context.MODE_PRIVATE);
        readS7.Start(sharedpreferences.getString("ip",null), sharedpreferences.getString("rack",null), sharedpreferences.getString("slot",null));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
