package com.example.rika.spk;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.rika.spk.data.Data;

import java.util.ArrayList;
import java.util.HashMap;

public class hasil_spk extends AppCompatActivity {

    ActionBar actionBar;

    TextView userinput;
    TextView userinputsuhu;
    TextView userinputph;
    TextView userinputoksigen;
    TextView userinputlama;
    TextView userinputluas;
    TextView userinputtinggi;
    TextView userinputminat;
    TextView userinputmudah;
    TextView hasilentropi;
    TextView hasilentropisuhu;
    TextView hasilentropiph;
    TextView hasilentropioksigen;
    TextView hasilentropilama;
    TextView hasilentropiluas;
    TextView hasilentropitinggi;
    TextView hasilentropiminat;
    TextView hasilentropimudah;
    TextView tablerekomendasi;
    TextView rekomendasispk;

    LinearLayout pagelayout;
    RelativeLayout.LayoutParams relativeParams;

    HashMap<String, Double> inputan;
    HashMap<String, Double> entropi;

    ArrayList<Data> hasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_spk);

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        pagelayout = (LinearLayout) findViewById(R.id.pagelayout);
        userinput = (TextView) findViewById(R.id.textInputanUser);
        userinputsuhu = (TextView) findViewById(R.id.textInputanSuhu);
        userinputph = (TextView) findViewById(R.id.textInputanPh);
        userinputoksigen = (TextView) findViewById(R.id.textInputanOksigen);
        userinputlama = (TextView) findViewById(R.id.textInputanLama);
        userinputluas = (TextView) findViewById(R.id.textInputanLuas);
        userinputtinggi = (TextView) findViewById(R.id.textInputanTinggi);
        userinputminat = (TextView) findViewById(R.id.textInputanMinat);
        userinputmudah = (TextView) findViewById(R.id.textInputanMudah);
        hasilentropi = (TextView) findViewById(R.id.textHasilEntropi);
        hasilentropisuhu = (TextView) findViewById(R.id.textEntropiSuhu);
        hasilentropiph = (TextView) findViewById(R.id.textEntropiPh);
        hasilentropioksigen = (TextView) findViewById(R.id.textEntropiOksigen);
        hasilentropilama = (TextView) findViewById(R.id.textEntropiLama);
        hasilentropiluas = (TextView) findViewById(R.id.textEntropiLuas);
        hasilentropitinggi = (TextView) findViewById(R.id.textEntropiTinggi);
        hasilentropiminat = (TextView) findViewById(R.id.textEntropiMinat);
        hasilentropimudah = (TextView) findViewById(R.id.textEntropiMudah);
        tablerekomendasi = (TextView) findViewById(R.id.textHasilRekomendasi);
        rekomendasispk = (TextView) findViewById(R.id.textHasilRekomendasiSPK);

        Bundle extra = getIntent().getBundleExtra("bundlehasil");
        hasil = (ArrayList<Data>) extra.getSerializable("hasilspk");
        inputan = (HashMap<String, Double>) extra.getSerializable("listinput");
        entropi = (HashMap<String, Double>) extra.getSerializable("listentropi");

        userinputsuhu.setText("• Suhu: " + inputan.get("suhu"));
        userinputph.setText("• PH: " + inputan.get("ph"));
        userinputoksigen.setText("• Oksigen: " + inputan.get("oksigen"));
        userinputlama.setText("• Lama: " + inputan.get("lama"));
        userinputluas.setText("• Luas: " + inputan.get("luas"));
        userinputtinggi.setText("• Tinggi: " + inputan.get("tinggi"));
        userinputminat.setText("• Minat: " + inputan.get("minat"));
        userinputmudah.setText("• Mudah: " + inputan.get("mudah"));

        hasilentropisuhu.setText("• Suhu: " + entropi.get("suhu"));
        hasilentropiph.setText("• PH: " + entropi.get("ph"));
        hasilentropioksigen.setText("• Oksigen: " + entropi.get("oksigen"));
        hasilentropilama.setText("• Lama: " + entropi.get("lama"));
        hasilentropiluas.setText("• Luas: " + entropi.get("luas"));
        hasilentropitinggi.setText("• Tinggi: " + entropi.get("tinggi"));
        hasilentropiminat.setText("• Minat: " + entropi.get("minat"));
        hasilentropimudah.setText("• Mudah: " + entropi.get("mudah"));

        createTableHasil();

        rekomendasispk.setText("Hasil Rekomendasi: " + hasil.get(0).getNama_ikan());

        for (Data dt : hasil){
            Log.d("HASIL SPK: ", dt.getNama_ikan() + ": " + dt.getHasil());
        }
    }

    private void createTableHasil() {
        TableLayout tableHasil = (TableLayout) findViewById(R.id.tablerekomendasi);
        tableHasil.removeAllViews();
        for (int i = 0; i < hasil.size(); i++) {

            TableRow row = new TableRow(hasil_spk.this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            TextView tv = new TextView(hasil_spk.this);
            tv.setText("" + hasil.get(i).getNama_ikan());
            tv.setTextSize(16);
//            tv.setBackgroundColor(getResources().getColor(R.color.primary));
            tv.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv.setPadding(20, 10, 10, 10);
            tv.setBackgroundResource(R.drawable.cell_shape);
            row.addView(tv);


            TextView tv2 = new TextView(hasil_spk.this);
            tv2.setText("" + hasil.get(i).getHasil());
            tv2.setTextSize(16);
            tv2.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv2.setPadding(20, 10, 10, 10);
            tv2.setBackgroundResource(R.drawable.cell_shape);
            row.addView(tv2);
            tableHasil.addView(row, new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.FILL_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
