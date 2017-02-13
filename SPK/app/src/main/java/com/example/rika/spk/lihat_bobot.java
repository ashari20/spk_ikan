package com.example.rika.spk;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rika.spk.data.NilaiBobot;
import com.example.rika.spk.util.Server;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Max;
import com.mobsandgeeks.saripaar.annotation.Min;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class lihat_bobot extends AppCompatActivity {

    SharedPreferences session;

    ActionBar actionBar;
    SwipeRefreshLayout refreshLayout;
    ScrollView scrollView;

    ProgressDialog progressDialog;
    Validator validator;

    Button savebtn;

    NilaiBobot suhuBobot;
    NilaiBobot phBobot;
    NilaiBobot tinggiBobot;
    NilaiBobot lamaBobot;
    NilaiBobot mudahBobot;
    NilaiBobot luasBobot;
    NilaiBobot minatBobot;
    NilaiBobot oksigenBobot;

    @NotEmpty
    @Min(value = 0, message = "Nilai sangat rendah harus diatas 0")
    EditText suhu_sangatrendah_nilai;

    @NotEmpty
    EditText suhu_rendah_nilai_1;

    @NotEmpty
    EditText suhu_rendah_nilai_2;

    @NotEmpty
    EditText suhu_sedang_nilai_1;

    @NotEmpty
    EditText suhu_sedang_nilai_2;

    @NotEmpty
    EditText suhu_tinggi_nilai_1;

    @NotEmpty
    EditText suhu_tinggi_nilai_2;

    @NotEmpty
    EditText suhu_sangattinggi_nilai;

    @NotEmpty
    @Min(value = 0, message = "Nilai sangat rendah harus diatas 0")
    @Max(value = 14, message = "Nilai masksimal ph adalah 14")
    EditText ph_sangatrendah_nilai;

    @NotEmpty
    EditText ph_rendah_nilai_1;

    @NotEmpty
    EditText ph_rendah_nilai_2;

    @NotEmpty
    EditText ph_sedang_nilai_1;

    @NotEmpty
    EditText ph_sedang_nilai_2;

    @NotEmpty
    EditText ph_tinggi_nilai_1;

    @NotEmpty
    EditText ph_tinggi_nilai_2;

    @NotEmpty
    EditText ph_sangattinggi_nilai;

    @NotEmpty
    EditText tinggi_sangatrendah_nilai;

    @NotEmpty
    EditText tinggi_rendah_nilai_1;

    @NotEmpty
    EditText tinggi_rendah_nilai_2;

    @NotEmpty
    EditText tinggi_sedang_nilai_1;

    @NotEmpty
    EditText tinggi_sedang_nilai_2;

    @NotEmpty
    EditText tinggi_tinggi_nilai_1;

    @NotEmpty
    EditText tinggi_tinggi_nilai_2;

    @NotEmpty
    EditText tinggi_sangattinggi_nilai;

    @NotEmpty
    @Min(value = 1, message = "Nilai sangat rendah harus diatas 1 bulan")
    EditText lama_sangatrendah_nilai;

    @NotEmpty
    EditText lama_rendah_nilai_1;

    @NotEmpty
    EditText lama_rendah_nilai_2;

    @NotEmpty
    EditText lama_sedang_nilai_1;

    @NotEmpty
    EditText lama_sedang_nilai_2;

    @NotEmpty
    EditText lama_tinggi_nilai_1;

    @NotEmpty
    EditText lama_tinggi_nilai_2;

    @NotEmpty
    EditText lama_sangattinggi_nilai;

    @NotEmpty
    EditText oksigen_sangatrendah_nilai_1;

    @NotEmpty
    EditText oksigen_sangatrendah_nilai_2;

    @NotEmpty
    EditText oksigen_rendah_nilai_1;

    @NotEmpty
    EditText oksigen_rendah_nilai_2;

    @NotEmpty
    EditText oksigen_sedang_nilai_1;

    @NotEmpty
    EditText oksigen_sedang_nilai_2;

    @NotEmpty
    EditText oksigen_tinggi_nilai_1;

    @NotEmpty
    EditText oksigen_tinggi_nilai_2;

    @NotEmpty
    EditText oksigen_sangattinggi_nilai;

    @NotEmpty
    EditText luas_sangatrendah_nilai;

    @NotEmpty
    EditText luas_rendah_nilai_1;

    @NotEmpty
    EditText luas_rendah_nilai_2;

    @NotEmpty
    EditText luas_sedang_nilai_1;

    @NotEmpty
    EditText luas_sedang_nilai_2;

    @NotEmpty
    EditText luas_tinggi_nilai_1;

    @NotEmpty
    EditText luas_tinggi_nilai_2;

    @NotEmpty
    EditText luas_sangattinggi_nilai;

    Spinner minat_sangatrendah_nilai;
    Spinner minat_rendah_nilai;
    Spinner minat_sedang_nilai;
    Spinner minat_tinggi_nilai;
    Spinner minat_sangattinggi_nilai;

    Spinner mudah_sangatrendah_nilai;
    Spinner mudah_rendah_nilai;
    Spinner mudah_sedang_nilai;
    Spinner mudah_tinggi_nilai;
    Spinner mudah_sangattinggi_nilai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_bobot);

        session = getBaseContext().getSharedPreferences(config.SHARED_PREF_NAME, MODE_PRIVATE);

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        scrollView = (ScrollView) findViewById(R.id.scrolllayout);

        savebtn = (Button) findViewById(R.id.btnsave);

        suhu_sangatrendah_nilai = (EditText) findViewById(R.id.suhu_sangatrendah_nilai);
        suhu_rendah_nilai_1 = (EditText) findViewById(R.id.suhu_rendah_nilai_1);
        suhu_rendah_nilai_2 = (EditText) findViewById(R.id.suhu_rendah_nilai_2);
        suhu_sedang_nilai_1 = (EditText) findViewById(R.id.suhu_sedang_nilai_1);
        suhu_sedang_nilai_2 = (EditText) findViewById(R.id.suhu_sedang_nilai_2);
        suhu_tinggi_nilai_1 = (EditText) findViewById(R.id.suhu_tinggi_nilai_1);
        suhu_tinggi_nilai_2 = (EditText) findViewById(R.id.suhu_tinggi_nilai_2);
        suhu_sangattinggi_nilai = (EditText) findViewById(R.id.suhu_sangattinggi_nilai);

        ph_sangatrendah_nilai = (EditText) findViewById(R.id.ph_sangatrendah_nilai);
        ph_rendah_nilai_1 = (EditText) findViewById(R.id.ph_rendah_nilai_1);
        ph_rendah_nilai_2 = (EditText) findViewById(R.id.ph_rendah_nilai_2);
        ph_sedang_nilai_1 = (EditText) findViewById(R.id.ph_sedang_nilai_1);
        ph_sedang_nilai_2 = (EditText) findViewById(R.id.ph_sedang_nilai_2);
        ph_tinggi_nilai_1 = (EditText) findViewById(R.id.ph_tinggi_nilai_1);
        ph_tinggi_nilai_2 = (EditText) findViewById(R.id.ph_tinggi_nilai_2);
        ph_sangattinggi_nilai = (EditText) findViewById(R.id.ph_sangattinggi_nilai);

        lama_sangatrendah_nilai = (EditText) findViewById(R.id.lama_sangatrendah_nilai);
        lama_rendah_nilai_1 = (EditText) findViewById(R.id.lama_rendah_nilai_1);
        lama_rendah_nilai_2 = (EditText) findViewById(R.id.lama_rendah_nilai_2);
        lama_sedang_nilai_1 = (EditText) findViewById(R.id.lama_sedang_nilai_1);
        lama_sedang_nilai_2 = (EditText) findViewById(R.id.lama_sedang_nilai_2);
        lama_tinggi_nilai_1 = (EditText) findViewById(R.id.lama_tinggi_nilai_1);
        lama_tinggi_nilai_2 = (EditText) findViewById(R.id.lama_tinggi_nilai_2);
        lama_sangattinggi_nilai = (EditText) findViewById(R.id.lama_sangattinggi_nilai);

        tinggi_sangatrendah_nilai = (EditText) findViewById(R.id.tinggi_sangatrendah_nilai);
        tinggi_rendah_nilai_1 = (EditText) findViewById(R.id.tinggi_rendah_nilai_1);
        tinggi_rendah_nilai_2 = (EditText) findViewById(R.id.tinggi_rendah_nilai_2);
        tinggi_sedang_nilai_1 = (EditText) findViewById(R.id.tinggi_sedang_nilai_1);
        tinggi_sedang_nilai_2 = (EditText) findViewById(R.id.tinggi_sedang_nilai_2);
        tinggi_tinggi_nilai_1 = (EditText) findViewById(R.id.tinggi_tinggi_nilai_1);
        tinggi_tinggi_nilai_2 = (EditText) findViewById(R.id.tinggi_tinggi_nilai_2);
        tinggi_sangattinggi_nilai = (EditText) findViewById(R.id.tinggi_sangattinggi_nilai);

        oksigen_sangatrendah_nilai_1 = (EditText) findViewById(R.id.oks_sangatrendah_nilai_1);
        oksigen_sangatrendah_nilai_2 = (EditText) findViewById(R.id.oks_sangatrendah_nilai_2);
        oksigen_rendah_nilai_1 = (EditText) findViewById(R.id.oks_rendah_nilai_1);
        oksigen_rendah_nilai_2 = (EditText) findViewById(R.id.oks_rendah_nilai_2);
        oksigen_sedang_nilai_1 = (EditText) findViewById(R.id.oks_sedang_nilai_1);
        oksigen_sedang_nilai_2 = (EditText) findViewById(R.id.oks_sedang_nilai_2);
        oksigen_tinggi_nilai_1 = (EditText) findViewById(R.id.oks_tinggi_nilai_1);
        oksigen_tinggi_nilai_2 = (EditText) findViewById(R.id.oks_tinggi_nilai_2);
        oksigen_sangattinggi_nilai = (EditText) findViewById(R.id.oks_sangattinggi_nilai);

        luas_sangatrendah_nilai = (EditText) findViewById(R.id.luas_sangatrendah_nilai);
        luas_rendah_nilai_1 = (EditText) findViewById(R.id.luas_rendah_nilai_1);
        luas_rendah_nilai_2 = (EditText) findViewById(R.id.luas_rendah_nilai_2);
        luas_sedang_nilai_1 = (EditText) findViewById(R.id.luas_sedang_nilai_1);
        luas_sedang_nilai_2 = (EditText) findViewById(R.id.luas_sedang_nilai_2);
        luas_tinggi_nilai_1 = (EditText) findViewById(R.id.luas_tinggi_nilai_1);
        luas_tinggi_nilai_2 = (EditText) findViewById(R.id.luas_tinggi_nilai_2);
        luas_sangattinggi_nilai = (EditText) findViewById(R.id.luas_sangattinggi_nilai);

        minat_sangatrendah_nilai = (Spinner) findViewById(R.id.minat_sanggatrendah_nilai);
        minat_rendah_nilai = (Spinner) findViewById(R.id.minat_rendah_nilai);
        minat_sedang_nilai = (Spinner) findViewById(R.id.minat_sedang_nilai);
        minat_tinggi_nilai = (Spinner) findViewById(R.id.minat_tinggi_nilai);
        minat_sangattinggi_nilai = (Spinner) findViewById(R.id.minat_sangattinggi_nilai);

        mudah_sangatrendah_nilai = (Spinner) findViewById(R.id.mudahpakan_sanggatrendah_nilai);
        mudah_rendah_nilai = (Spinner) findViewById(R.id.mudahpakan_rendah_nilai);
        mudah_sedang_nilai = (Spinner) findViewById(R.id.mudahpakan_sedang_nilai);
        mudah_tinggi_nilai = (Spinner) findViewById(R.id.mudahpakan_tinggi_nilai);
        mudah_sangattinggi_nilai = (Spinner) findViewById(R.id.mudahpakan_sangattinggi_nilai);

        if (session.getBoolean(config.LOGGEDIN_SHARED_PREF,false) == true){

        }else{
            suhu_sangatrendah_nilai.setEnabled(false);
            suhu_rendah_nilai_1.setEnabled(false);
            suhu_rendah_nilai_2.setEnabled(false);
            suhu_sedang_nilai_1.setEnabled(false);
            suhu_sedang_nilai_2.setEnabled(false);
            suhu_tinggi_nilai_1.setEnabled(false);
            suhu_tinggi_nilai_2.setEnabled(false);
            suhu_sangattinggi_nilai.setEnabled(false);
            ph_sangatrendah_nilai.setEnabled(false);
            ph_rendah_nilai_1.setEnabled(false);
            ph_rendah_nilai_2.setEnabled(false);
            ph_sedang_nilai_1.setEnabled(false);
            ph_sedang_nilai_2.setEnabled(false);
            ph_tinggi_nilai_1.setEnabled(false);
            ph_tinggi_nilai_2.setEnabled(false);
            ph_sangattinggi_nilai.setEnabled(false);
            lama_sangatrendah_nilai.setEnabled(false);
            lama_rendah_nilai_1.setEnabled(false);
            lama_rendah_nilai_2.setEnabled(false);
            lama_sedang_nilai_1.setEnabled(false);
            lama_sedang_nilai_2.setEnabled(false);
            lama_tinggi_nilai_1.setEnabled(false);
            lama_tinggi_nilai_2.setEnabled(false);
            lama_sangattinggi_nilai.setEnabled(false);
            oksigen_sangatrendah_nilai_1.setEnabled(false);
            oksigen_sangatrendah_nilai_2.setEnabled(false);
            oksigen_rendah_nilai_1.setEnabled(false);
            oksigen_rendah_nilai_2.setEnabled(false);
            oksigen_sedang_nilai_1.setEnabled(false);
            oksigen_sedang_nilai_2.setEnabled(false);
            oksigen_tinggi_nilai_1.setEnabled(false);
            oksigen_tinggi_nilai_2.setEnabled(false);
            oksigen_sangattinggi_nilai.setEnabled(false);
            tinggi_sangatrendah_nilai.setEnabled(false);
            tinggi_rendah_nilai_1.setEnabled(false);
            tinggi_rendah_nilai_2.setEnabled(false);
            tinggi_sedang_nilai_1.setEnabled(false);
            tinggi_sedang_nilai_2.setEnabled(false);
            tinggi_tinggi_nilai_1.setEnabled(false);
            tinggi_tinggi_nilai_2.setEnabled(false);
            tinggi_sangattinggi_nilai.setEnabled(false);
            luas_sangatrendah_nilai.setEnabled(false);
            luas_rendah_nilai_1.setEnabled(false);
            luas_rendah_nilai_2.setEnabled(false);
            luas_sedang_nilai_1.setEnabled(false);
            luas_sedang_nilai_2.setEnabled(false);
            luas_tinggi_nilai_1.setEnabled(false);
            luas_tinggi_nilai_2.setEnabled(false);
            luas_sangattinggi_nilai.setEnabled(false);

            minat_sangattinggi_nilai.setEnabled(false);
            minat_tinggi_nilai.setEnabled(false);
            minat_sedang_nilai.setEnabled(false);
            minat_rendah_nilai.setEnabled(false);
            minat_sangatrendah_nilai.setEnabled(false);
            mudah_sangattinggi_nilai.setEnabled(false);
            mudah_tinggi_nilai.setEnabled(false);
            mudah_sedang_nilai.setEnabled(false);
            mudah_rendah_nilai.setEnabled(false);
            mudah_sangatrendah_nilai.setEnabled(false);
            savebtn.setVisibility(View.INVISIBLE);
        }

        progressDialog = new ProgressDialog(lihat_bobot.this);
        progressDialog.setMessage("Loading..");
        progressDialog.show();
        getData();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollY = scrollView.getScrollY();
                if (scrollY == 0) refreshLayout.setEnabled(true);
                else refreshLayout.setEnabled(false);
            }
        });

        validator = new Validator(lihat_bobot.this);
        validator.setValidationListener(new Validator.ValidationListener() {
            @Override
            public void onValidationSucceeded() {
                progressDialog = new ProgressDialog(lihat_bobot.this);
                progressDialog.setMessage("Saving data..");
                progressDialog.show();
                setData();
            }

            @Override
            public void onValidationFailed(List<ValidationError> errors) {
                for (ValidationError error : errors) {
                    View view = error.getView();
                    String message = error.getCollatedErrorMessage(lihat_bobot.this);

                    // Display error messages ;)
                    if (view instanceof EditText) {
                        ((EditText) view).setError(message);
                    } else {
                        Toast.makeText(lihat_bobot.this, message, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void getData(){
        if (isOnline()){
            requestData(Server.URL+"select_all_bobot.php");
        }else{
            Snackbar.make(refreshLayout, "No Network Available", Snackbar.LENGTH_LONG).setAction("REFRESH", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getData();
                }
            });
        }
    }

    private void setData(){
        if (isOnline()){
            saveData(Server.URL + "insert_all_bobot.php");
        }else{
            Snackbar.make(refreshLayout, "No Network Available", Snackbar.LENGTH_LONG).setAction("TRY AGAIN", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setData();
                }
            });
        }
    }

    private boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.isConnectedOrConnecting()){
            return true;
        }else{
            return false;
        }
    }

    private void requestData(String uri){
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(uri, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("SUCCESS: ", response.toString());
                progressDialog.dismiss();
                refreshLayout.setRefreshing(false);

                try {
                    JSONObject suhuobj = response.getJSONObject("suhu");
                    JSONObject phobj = response.getJSONObject("ph");
                    JSONObject tinggiobj = response.getJSONObject("tinggi");
                    JSONObject lamaobj = response.getJSONObject("lama");
                    JSONObject oksigenobj = response.getJSONObject("oksigen");
                    JSONObject pakanobj = response.getJSONObject("pakan");
                    JSONObject minatobj = response.getJSONObject("minat");
                    JSONObject luasobj = response.getJSONObject("luas");

                    suhuBobot = new NilaiBobot();
                    phBobot = new NilaiBobot();
                    tinggiBobot = new NilaiBobot();
                    lamaBobot = new NilaiBobot();
                    oksigenBobot = new NilaiBobot();
                    mudahBobot = new NilaiBobot();
                    minatBobot = new NilaiBobot();
                    luasBobot = new NilaiBobot();

                    suhuBobot.setSangatrendah(suhuobj.getString("suhu_sgt_rdh"));
                    suhuBobot.setRendah(suhuobj.getString("suhu_rdh_1"));
                    suhuBobot.setRendah_2(suhuobj.getString("suhu_rdh_2"));
                    suhuBobot.setSedang(suhuobj.getString("suhu_sdg_1"));
                    suhuBobot.setSedang_2(suhuobj.getString("suhu_sdg_2"));
                    suhuBobot.setTinggi(suhuobj.getString("suhu_ting_1"));
                    suhuBobot.setTinggi_2(suhuobj.getString("suhu_ting_2"));
                    suhuBobot.setSangattinggi(suhuobj.getString("suhu_sgt_ting"));

                    phBobot.setSangatrendah(phobj.getString("ph_sgt_rdh"));
                    phBobot.setRendah(phobj.getString("ph_rdh_1"));
                    phBobot.setRendah_2(phobj.getString("ph_rdh_2"));
                    phBobot.setSedang(phobj.getString("ph_sdg_1"));
                    phBobot.setSedang_2(phobj.getString("ph_sdg_2"));
                    phBobot.setTinggi(phobj.getString("ph_ting_1"));
                    phBobot.setTinggi_2(phobj.getString("ph_ting_2"));
                    phBobot.setSangattinggi(phobj.getString("ph_sgt_ting"));

                    tinggiBobot.setSangatrendah(tinggiobj.getString("ting_sgt_rdh"));
                    tinggiBobot.setRendah(tinggiobj.getString("ting_rdh_1"));
                    tinggiBobot.setRendah_2(tinggiobj.getString("ting_rdh_2"));
                    tinggiBobot.setSedang(tinggiobj.getString("ting_sdg_1"));
                    tinggiBobot.setSedang_2(tinggiobj.getString("ting_sdg_2"));
                    tinggiBobot.setTinggi(tinggiobj.getString("ting_ting_1"));
                    tinggiBobot.setTinggi_2(tinggiobj.getString("ting_ting_2"));
                    tinggiBobot.setSangattinggi(tinggiobj.getString("ting_sgt"));

                    lamaBobot.setSangatrendah(lamaobj.getString("lama_sgt_rdh"));
                    lamaBobot.setRendah(lamaobj.getString("lama_rdh_1"));
                    lamaBobot.setRendah_2(lamaobj.getString("lama_rdh_2"));
                    lamaBobot.setSedang(lamaobj.getString("lama_sdg_1"));
                    lamaBobot.setSedang_2(lamaobj.getString("lama_sdg_2"));
                    lamaBobot.setTinggi(lamaobj.getString("lama_ting_1"));
                    lamaBobot.setTinggi_2(lamaobj.getString("lama_ting_2"));
                    lamaBobot.setSangattinggi(lamaobj.getString("lama_sgt_ting"));

                    oksigenBobot.setSangatrendah(oksigenobj.getString("oks_sgt_rdh_1"));
                    oksigenBobot.setSangatrendah2(oksigenobj.getString("oks_sgt_rdh_2"));
                    oksigenBobot.setRendah(oksigenobj.getString("oks_rdh_1"));
                    oksigenBobot.setRendah_2(oksigenobj.getString("oks_rdh_2"));
                    oksigenBobot.setSedang(oksigenobj.getString("oks_sdg_1"));
                    oksigenBobot.setSedang_2(oksigenobj.getString("oks_sdg_2"));
                    oksigenBobot.setTinggi(oksigenobj.getString("oks_ting_1"));
                    oksigenBobot.setTinggi_2(oksigenobj.getString("oks_ting_2"));
                    oksigenBobot.setSangattinggi(oksigenobj.getString("oks_sgt_ting"));

                    luasBobot.setSangatrendah(luasobj.getString("luas_sgt_rdh"));
                    luasBobot.setRendah(luasobj.getString("luas_rdh_1"));
                    luasBobot.setRendah_2(luasobj.getString("luas_rdh_2"));
                    luasBobot.setSedang(luasobj.getString("luas_sdg_1"));
                    luasBobot.setSedang_2(luasobj.getString("luas_sdg_2"));
                    luasBobot.setTinggi(luasobj.getString("luas_ting_1"));
                    luasBobot.setTinggi_2(luasobj.getString("luas_ting_2"));
                    luasBobot.setSangattinggi(luasobj.getString("luas_sgt_ting"));

                    mudahBobot.setSangatrendah(pakanobj.getString("pkn_sgt_tdk_mdh"));
                    mudahBobot.setRendah(pakanobj.getString("pkn_tdk_mdh"));
                    mudahBobot.setSedang(pakanobj.getString("pkn_sdg"));
                    mudahBobot.setTinggi(pakanobj.getString("pkn_mdh"));
                    mudahBobot.setSangattinggi(pakanobj.getString("pkn_sgt_mdh"));

                    minatBobot.setSangatrendah(minatobj.getString("minat_tdk_suka"));
                    minatBobot.setRendah(minatobj.getString("minat_krg_suka"));
                    minatBobot.setSedang(minatobj.getString("minat_sdg"));
                    minatBobot.setTinggi(minatobj.getString("minat_suka"));
                    minatBobot.setSangattinggi(minatobj.getString("minat_sgt_suka"));

                    setValue(suhuBobot, phBobot, tinggiBobot, lamaBobot, oksigenBobot, mudahBobot, minatBobot, luasBobot);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("FAIL: ", statusCode+" "+responseString);
                progressDialog.dismiss();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void setValue(NilaiBobot suhu, NilaiBobot ph, NilaiBobot tinggi, NilaiBobot lama, NilaiBobot oksigen, NilaiBobot pakan, NilaiBobot minat, NilaiBobot luas){

        suhu_sangatrendah_nilai.setText(suhu.getSangatrendah());
        suhu_rendah_nilai_1.setText(suhu.getRendah());
        suhu_rendah_nilai_2.setText(suhu.getRendah_2());
        suhu_sedang_nilai_1.setText(suhu.getSedang());
        suhu_sedang_nilai_2.setText(suhu.getSedang_2());
        suhu_tinggi_nilai_1.setText(suhu.getTinggi());
        suhu_tinggi_nilai_2.setText(suhu.getTinggi_2());
        suhu_sangattinggi_nilai.setText(suhu.getSangattinggi());

        ph_sangatrendah_nilai.setText(ph.getSangatrendah());
        ph_rendah_nilai_1.setText(ph.getRendah());
        ph_rendah_nilai_2.setText(ph.getRendah_2());
        ph_sedang_nilai_1.setText(ph.getSedang());
        ph_sedang_nilai_2.setText(ph.getSedang_2());
        ph_tinggi_nilai_1.setText(ph.getTinggi());
        ph_tinggi_nilai_2.setText(ph.getTinggi_2());
        ph_sangattinggi_nilai.setText(ph.getSangattinggi());

        tinggi_sangatrendah_nilai.setText(tinggi.getSangatrendah());
        tinggi_rendah_nilai_1.setText(tinggi.getRendah());
        tinggi_rendah_nilai_2.setText(tinggi.getRendah_2());
        tinggi_sedang_nilai_1.setText(tinggi.getSedang());
        tinggi_sedang_nilai_2.setText(tinggi.getSedang_2());
        tinggi_tinggi_nilai_1.setText(tinggi.getTinggi());
        tinggi_tinggi_nilai_2.setText(tinggi.getTinggi_2());
        tinggi_sangattinggi_nilai.setText(tinggi.getSangattinggi());

        lama_sangatrendah_nilai.setText(lama.getSangatrendah());
        lama_rendah_nilai_1.setText(lama.getRendah());
        lama_rendah_nilai_2.setText(lama.getRendah_2());
        lama_sedang_nilai_1.setText(lama.getSedang());
        lama_sedang_nilai_2.setText(lama.getSedang_2());
        lama_tinggi_nilai_1.setText(lama.getTinggi());
        lama_tinggi_nilai_2.setText(lama.getTinggi_2());
        lama_sangattinggi_nilai.setText(lama.getSangattinggi());

        oksigen_sangatrendah_nilai_1.setText(oksigen.getSangatrendah());
        oksigen_sangatrendah_nilai_2.setText(oksigen.getSangatrendah2());
        oksigen_rendah_nilai_1.setText(oksigen.getRendah());
        oksigen_rendah_nilai_2.setText(oksigen.getRendah_2());
        oksigen_sedang_nilai_1.setText(oksigen.getSedang());
        oksigen_sedang_nilai_2.setText(oksigen.getSedang_2());
        oksigen_tinggi_nilai_1.setText(oksigen.getTinggi());
        oksigen_tinggi_nilai_2.setText(oksigen.getTinggi_2());
        oksigen_sangattinggi_nilai.setText(oksigen.getSangattinggi());

        luas_sangatrendah_nilai.setText(luas.getSangatrendah());
        luas_rendah_nilai_1.setText(luas.getRendah());
        luas_rendah_nilai_2.setText(luas.getRendah_2());
        luas_sedang_nilai_1.setText(luas.getSedang());
        luas_sedang_nilai_2.setText(luas.getSedang_2());
        luas_tinggi_nilai_1.setText(luas.getTinggi());
        luas_tinggi_nilai_2.setText(luas.getTinggi_2());
        luas_sangattinggi_nilai.setText(luas.getSangattinggi());

        List list2 = new ArrayList();
        List list = new ArrayList();
        Collections.addAll(list, getResources().getStringArray(R.array.mudahpakanval));

        mudah_sangatrendah_nilai.setSelection(list.indexOf(pakan.getSangatrendah()));
        mudah_rendah_nilai.setSelection(list.indexOf(pakan.getRendah()));
        mudah_sedang_nilai.setSelection(list.indexOf(pakan.getSedang()));
        mudah_tinggi_nilai.setSelection(list.indexOf(pakan.getTinggi()));
        mudah_sangattinggi_nilai.setSelection(list.indexOf(pakan.getSangattinggi()));

        Collections.addAll(list2, getResources().getStringArray(R.array.mudahminatval));

        minat_sangatrendah_nilai.setSelection(list2.indexOf(minat.getSangatrendah()));
        minat_rendah_nilai.setSelection(list2.indexOf(minat.getRendah()));
        minat_sedang_nilai.setSelection(list2.indexOf(minat.getSedang()));
        minat_tinggi_nilai.setSelection(list2.indexOf(minat.getTinggi()));
        minat_sangattinggi_nilai.setSelection(list2.indexOf(minat.getSangattinggi()));

    }

    private void saveData(String uri){
        suhuBobot.setSangatrendah(suhu_sangatrendah_nilai.getText().toString());
        suhuBobot.setRendah(suhu_rendah_nilai_1.getText().toString());
        suhuBobot.setRendah_2(suhu_rendah_nilai_2.getText().toString());
        suhuBobot.setSedang(suhu_sedang_nilai_1.getText().toString());
        suhuBobot.setSedang_2(suhu_sedang_nilai_2.getText().toString());
        suhuBobot.setTinggi(suhu_tinggi_nilai_1.getText().toString());
        suhuBobot.setTinggi_2(suhu_tinggi_nilai_2.getText().toString());
        suhuBobot.setSangattinggi(suhu_sangattinggi_nilai.getText().toString());

        phBobot.setSangatrendah(ph_sangatrendah_nilai.getText().toString());
        phBobot.setRendah(ph_rendah_nilai_1.getText().toString());
        phBobot.setRendah_2(ph_rendah_nilai_2.getText().toString());
        phBobot.setSedang(ph_sedang_nilai_1.getText().toString());
        phBobot.setSedang_2(ph_sedang_nilai_2.getText().toString());
        phBobot.setTinggi(ph_tinggi_nilai_1.getText().toString());
        phBobot.setTinggi_2(ph_tinggi_nilai_2.getText().toString());
        phBobot.setSangattinggi(ph_sangattinggi_nilai.getText().toString());

        oksigenBobot.setSangatrendah(oksigen_sangatrendah_nilai_1.getText().toString());
        oksigenBobot.setSangatrendah2(oksigen_sangatrendah_nilai_2.getText().toString());
        oksigenBobot.setRendah(oksigen_rendah_nilai_1.getText().toString());
        oksigenBobot.setRendah_2(oksigen_rendah_nilai_2.getText().toString());
        oksigenBobot.setSedang(oksigen_sedang_nilai_1.getText().toString());
        oksigenBobot.setSedang_2(oksigen_sedang_nilai_2.getText().toString());
        oksigenBobot.setTinggi(oksigen_tinggi_nilai_1.getText().toString());
        oksigenBobot.setTinggi_2(oksigen_tinggi_nilai_2.getText().toString());
        oksigenBobot.setSangattinggi(oksigen_sangattinggi_nilai.getText().toString());

        luasBobot.setSangatrendah(luas_sangatrendah_nilai.getText().toString());
        luasBobot.setRendah(luas_rendah_nilai_1.getText().toString());
        luasBobot.setRendah_2(luas_rendah_nilai_2.getText().toString());
        luasBobot.setSedang(luas_sedang_nilai_1.getText().toString());
        luasBobot.setSedang_2(luas_sedang_nilai_2.getText().toString());
        luasBobot.setTinggi(luas_tinggi_nilai_1.getText().toString());
        luasBobot.setTinggi_2(luas_tinggi_nilai_2.getText().toString());
        luasBobot.setSangattinggi(luas_sangattinggi_nilai.getText().toString());

        lamaBobot.setSangatrendah(lama_sangatrendah_nilai.getText().toString());
        lamaBobot.setRendah(lama_rendah_nilai_1.getText().toString());
        lamaBobot.setRendah_2(lama_rendah_nilai_2.getText().toString());
        lamaBobot.setSedang(lama_sedang_nilai_1.getText().toString());
        lamaBobot.setSedang_2(lama_sedang_nilai_2.getText().toString());
        lamaBobot.setTinggi(lama_tinggi_nilai_1.getText().toString());
        lamaBobot.setTinggi_2(lama_tinggi_nilai_2.getText().toString());
        lamaBobot.setSangattinggi(lama_sangattinggi_nilai.getText().toString());

        tinggiBobot.setSangatrendah(tinggi_sangatrendah_nilai.getText().toString());
        tinggiBobot.setRendah(tinggi_rendah_nilai_1.getText().toString());
        tinggiBobot.setRendah_2(tinggi_rendah_nilai_2.getText().toString());
        tinggiBobot.setSedang(tinggi_sedang_nilai_1.getText().toString());
        tinggiBobot.setSedang_2(tinggi_sedang_nilai_2.getText().toString());
        tinggiBobot.setTinggi(tinggi_tinggi_nilai_1.getText().toString());
        tinggiBobot.setTinggi_2(tinggi_tinggi_nilai_2.getText().toString());
        tinggiBobot.setSangattinggi(tinggi_sangattinggi_nilai.getText().toString());

        minatBobot.setSangatrendah(minat_sangatrendah_nilai.getSelectedItem().toString());
        minatBobot.setRendah(minat_rendah_nilai.getSelectedItem().toString());
        minatBobot.setSedang(minat_sedang_nilai.getSelectedItem().toString());
        minatBobot.setTinggi(minat_tinggi_nilai.getSelectedItem().toString());
        minatBobot.setSangattinggi(minat_sangattinggi_nilai.getSelectedItem().toString());

        mudahBobot.setSangatrendah(mudah_sangatrendah_nilai.getSelectedItem().toString());
        mudahBobot.setRendah(mudah_rendah_nilai.getSelectedItem().toString());
        mudahBobot.setSedang(mudah_sedang_nilai.getSelectedItem().toString());
        mudahBobot.setTinggi(mudah_tinggi_nilai.getSelectedItem().toString());
        mudahBobot.setSangattinggi(mudah_sangattinggi_nilai.getSelectedItem().toString());

        JSONObject suhuobj = new JSONObject();
        JSONObject phobj = new JSONObject();
        JSONObject oksigenobj = new JSONObject();
        JSONObject luasobj = new JSONObject();
        JSONObject tinggiobj = new JSONObject();
        JSONObject lamaobj = new JSONObject();
        JSONObject minatobj = new JSONObject();
        JSONObject mudahobj = new JSONObject();

        try {
            suhuobj.put("sangatrendah", suhuBobot.getSangatrendah().toString());
            suhuobj.put("rendah_1", suhuBobot.getRendah().toString());
            suhuobj.put("rendah_2", suhuBobot.getRendah_2().toString());
            suhuobj.put("sedang_1", suhuBobot.getSedang().toString());
            suhuobj.put("sedang_2", suhuBobot.getSedang_2().toString());
            suhuobj.put("tinggi_1", suhuBobot.getTinggi().toString());
            suhuobj.put("tinggi_2", suhuBobot.getTinggi_2().toString());
            suhuobj.put("sangattinggi", suhuBobot.getSangattinggi().toString());

            phobj.put("sangatrendah", phBobot.getSangatrendah().toString());
            phobj.put("rendah_1", phBobot.getRendah().toString());
            phobj.put("rendah_2", phBobot.getRendah_2().toString());
            phobj.put("sedang_1", phBobot.getSedang().toString());
            phobj.put("sedang_2", phBobot.getSedang_2().toString());
            phobj.put("tinggi_1", phBobot.getTinggi().toString());
            phobj.put("tinggi_2", phBobot.getTinggi_2().toString());
            phobj.put("sangattinggi", phBobot.getSangattinggi().toString());

            oksigenobj.put("sangatrendah", oksigenBobot.getSangatrendah().toString());
            oksigenobj.put("sangatrendah2", oksigenBobot.getSangatrendah2().toString());
            oksigenobj.put("rendah_1", oksigenBobot.getRendah().toString());
            oksigenobj.put("rendah_2", oksigenBobot.getRendah_2().toString());
            oksigenobj.put("sedang_1", oksigenBobot.getSedang().toString());
            oksigenobj.put("sedang_2", oksigenBobot.getSedang_2().toString());
            oksigenobj.put("tinggi_1", oksigenBobot.getTinggi().toString());
            oksigenobj.put("tinggi_2", oksigenBobot.getTinggi_2().toString());
            oksigenobj.put("sangattinggi", oksigenBobot.getSangattinggi().toString());

            luasobj.put("sangatrendah", luasBobot.getSangatrendah().toString());
            luasobj.put("rendah_1", luasBobot.getRendah().toString());
            luasobj.put("rendah_2", luasBobot.getRendah_2().toString());
            luasobj.put("sedang_1", luasBobot.getSedang().toString());
            luasobj.put("sedang_2", luasBobot.getSedang_2().toString());
            luasobj.put("tinggi_1", luasBobot.getTinggi().toString());
            luasobj.put("tinggi_2", luasBobot.getTinggi_2().toString());
            luasobj.put("sangattinggi", luasBobot.getSangattinggi().toString());

            tinggiobj.put("sangatrendah", tinggiBobot.getSangatrendah().toString());
            tinggiobj.put("rendah_1", tinggiBobot.getRendah().toString());
            tinggiobj.put("rendah_2", tinggiBobot.getRendah_2().toString());
            tinggiobj.put("sedang_1", tinggiBobot.getSedang().toString());
            tinggiobj.put("sedang_2", tinggiBobot.getSedang_2().toString());
            tinggiobj.put("tinggi_1", tinggiBobot.getTinggi().toString());
            tinggiobj.put("tinggi_2", tinggiBobot.getTinggi_2().toString());
            tinggiobj.put("sangattinggi", tinggiBobot.getSangattinggi().toString());

            lamaobj.put("sangatrendah", lamaBobot.getSangatrendah().toString());
            lamaobj.put("rendah_1", lamaBobot.getRendah().toString());
            lamaobj.put("rendah_2", lamaBobot.getRendah_2().toString());
            lamaobj.put("sedang_1", lamaBobot.getSedang().toString());
            lamaobj.put("sedang_2", lamaBobot.getSedang_2().toString());
            lamaobj.put("tinggi_1", lamaBobot.getTinggi().toString());
            lamaobj.put("tinggi_2", lamaBobot.getTinggi_2().toString());
            lamaobj.put("sangattinggi", lamaBobot.getSangattinggi().toString());

            minatobj.put("sangatrendah", minatBobot.getSangatrendah().toString());
            minatobj.put("rendah", minatBobot.getRendah().toString());
            minatobj.put("sedang", minatBobot.getSedang().toString());
            minatobj.put("tinggi", minatBobot.getTinggi().toString());
            minatobj.put("sangatminat", minatBobot.getSangattinggi().toString());

            mudahobj.put("sangatrendah", mudahBobot.getSangatrendah().toString());
            mudahobj.put("rendah", mudahBobot.getRendah().toString());
            mudahobj.put("sedang", mudahBobot.getSedang().toString());
            mudahobj.put("mudah", mudahBobot.getTinggi().toString());
            mudahobj.put("sangatmudah", mudahBobot.getSangattinggi().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("suhu", suhuobj);
        params.put("ph", phobj);
        params.put("oksigen", oksigenobj);
        params.put("lama", lamaobj);
        params.put("luas", luasobj);
        params.put("tinggi", tinggiobj);
        params.put("mudah", mudahobj);
        params.put("minat", minatobj);

        client.post(uri, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("SUCCESS: ", response.toString());
                progressDialog.dismiss();
                try {
                    Snackbar.make(refreshLayout, response.getString("message"), Snackbar.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("FAIL: ", responseString);
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void savebobot(View view) {
        validator.validate();
    }
}
