package com.example.projetandroid.Cuve;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.projetandroid.R;
import com.example.projetandroid.ReadTaskS7;

public class ReadCuveActivity extends AppCompatActivity {

    private TextView bits[] = new TextView[9];
    private TextView consAuto;
    private  TextView niveauLiquide;
    private ProgressBar progressLiquide;
    private TextView consManu;
    private TextView pilotageVanne;
    private ReadTaskS7 readS7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_cuve);

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

        consAuto = findViewById(R.id.txv_consAuto);
        niveauLiquide = findViewById(R.id.txv_niv_liquide);
        progressLiquide = findViewById(R.id.prg_niv_liquide);
        consManu = findViewById(R.id.txv_consMan);
        pilotageVanne = findViewById(R.id.txv_pilotageVanne);

    }
}
