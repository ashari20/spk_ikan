package com.example.rika.spk;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rika.spk.data.Data;
import com.example.rika.spk.data.NilaiBobot;
import com.example.rika.spk.util.Server;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class spk extends AppCompatActivity implements Serializable {

    ActionBar actionBar;

    Spinner suhu;
    Spinner ph;
    Spinner tinggi;
    Spinner lama;
    Spinner oksigen;
    Spinner mudah;
    Spinner minat;
    Spinner luas;

    RelativeLayout mainlayout;

    Validator validator;
    List<Data> listIkan;

    ProgressDialog progressDialog;

    NilaiBobot _bobotsuhu;
    NilaiBobot _bobotph;
    NilaiBobot _bobotoksigen;
    NilaiBobot _bobotlama;
    NilaiBobot _bobotluas;
    NilaiBobot _bobotmudah;
    NilaiBobot _bobotminat;
    NilaiBobot _bobottinggi;

    double _suhu;
    double _ph;
    double _oksigen;
    double _lama;
    double _luas;
    double _mudah;
    double _minat;
    double _tinggi;

    double wSuhu;
    double wPh;
    double wOksigen;
    double wLama;
    double wLuas;
    double wMudah;
    double wMinat;
    double wTinggi;

    List<Float> ListSuhu;
    List<Float> ListPh;
    List<Float> ListOksigen;
    List<Float> ListLama;
    List<Float> ListLuas;
    List<Float> ListMudah;
    List<Float> ListMinat;
    List<Float> ListTinggi;

    List<Float> ListBobotSuhu;
    List<Float> ListBobotPh;
    List<Float> ListBobotOksigen;
    List<Float> ListBobotLama;
    List<Float> ListBobotLuas;
    List<Float> ListBobotMudah;
    List<Float> ListBobotMinat;
    List<Float> ListBobotTinggi;

    List<Float> hasilAkhir;

    int m_suhu = 0;
    int m_ph = 0;
    int m_oksigen = 0;
    int m_lama = 0;
    int m_luas = 0;
    int m_tinggi = 0;
    int m_mudah = 0;
    int m_minat = 0;

    float EkSuhu = 0;
    float EkPh = 0;
    float EkOksigen = 0;
    float EkLama = 0;
    float EkLuas = 0;
    float EkTinggi = 0;
    float EkMudah = 0;
    float EkMinat = 0;
    float SumOfEk = 0;

    float LkSuhu = 0;
    float LkPh = 0;
    float LkOksigen = 0;
    float LkLama = 0;
    float LkLuas = 0;
    float LkTinggi = 0;
    float LkMudah = 0;
    float LkMinat = 0;

    float WEkSuhu = 0;
    float WEkPh = 0;
    float WEkOksigen = 0;
    float WEkLama = 0;
    float WEkLuas = 0;
    float WEkTinggi = 0;
    float WEkMudah = 0;
    float WEkMinat = 0;
    float SumOfWEk = 0;

    float bobotAkhirSuhu= 0;
    float bobotAkhirPh = 0;
    float bobotAkhirOksigen = 0;
    float bobotAkhirLama = 0;
    float bobotAkhirLuas = 0;
    float bobotAkhirTinggi = 0;
    float bobotAkhirMinat = 0;
    float bobotAkhirMudah = 0;

    float sumOfVarSuhu = 0;
    float sumOfVarPh = 0;
    float sumOfVarOksigen = 0;
    float sumOfVarLama = 0;
    float sumOfVarLuas = 0;
    float sumOfVarTinggi = 0;
    float sumOfVarMudah = 0;
    float sumOfVarMinat = 0;

    float sumOfVarBobotSuhu = 0;
    float sumOfVarBobotPh = 0;
    float sumOfVarBobotOksigen = 0;
    float sumOfVarBobotLama = 0;
    float sumOfVarBobotLuas = 0;
    float sumOfVarBobotTinggi = 0;
    float sumOfVarBobotMudah = 0;
    float sumOfVarBobotMinat = 0;

    float sumOfVarBobotEntropySuhu = 0;
    float sumOfVarBobotEntropyPh = 0;
    float sumOfVarBobotEntropyOksigen = 0;
    float sumOfVarBobotEntropyLama = 0;
    float sumOfVarBobotEntropyLuas = 0;
    float sumOfVarBobotEntropyTinggi = 0;
    float sumOfVarBobotEntropyMudah = 0;
    float sumOfVarBobotEntropyMinat = 0;

    float mBobotEvaluasi[][] = new float[100][8];
    float mBobotProbabilitas[][] = new float[100][8];
    double mBobotEntropi[][] = new double[100][8];

    float hasil[][] = new float[1][100];
    float m2[][] = new float[100][8];
    float m3[][] = new float[100][8];

    float s, p, o, l, ls, t, mi, mu, b, jum, pro;

    List<Float> arraySuhum3 = new ArrayList<>();
    List<Float> arrayPhm3 = new ArrayList<>();
    List<Float> arrayOksigenm3 = new ArrayList<>();
    List<Float> arrayLamam3 = new ArrayList<>();
    List<Float> arrayLuasm3 = new ArrayList<>();
    List<Float> arrayTinggim3 = new ArrayList<>();
    List<Float> arrayMinatm3 = new ArrayList<>();
    List<Float> arrayMudahm3 = new ArrayList<>();

    List<Float> sqrtSuhum3 = new ArrayList<>();
    List<Float> sqrtPhm3 = new ArrayList<>();
    List<Float> sqrtOksigenm3 = new ArrayList<>();
    List<Float> sqrtLamam3 = new ArrayList<>();
    List<Float> sqrtLuasm3 = new ArrayList<>();
    List<Float> sqrtTinggim3 = new ArrayList<>();
    List<Float> sqrtMinatm3 = new ArrayList<>();
    List<Float> sqrtMudahm3 = new ArrayList<>();

    List<Float> listMatriksEvaluasiSuhu;
    List<Float> listMatriksEvaluasiPh;
    List<Float> listMatriksEvaluasiOksigen;
    List<Float> listMatriksEvaluasiLama;
    List<Float> listMatriksEvaluasiLuas;
    List<Float> listMatriksEvaluasiTinggi;
    List<Float> listMatriksEvaluasiMinat;
    List<Float> listMatriksEvaluasiMudah;

    List<Float> listProbabilitasSuhu;
    List<Float> listProbabilitasPh;
    List<Float> listProbabilitasOksigen;
    List<Float> listProbabilitasLama;
    List<Float> listProbabilitasLuas;
    List<Float> listProbabilitasTinggi;
    List<Float> listProbabilitasMinat;
    List<Float> listProbabilitasMudah;

    List<Float> listEntropiSuhu;
    List<Float> listEntropiPh;
    List<Float> listEntropiOksigen;
    List<Float> listEntropiLama;
    List<Float> listEntropiLuas;
    List<Float> listEntropiTinggi;
    List<Float> listEntropiMinat;
    List<Float> listEntropiMudah;

    List<Data> list_hasil = new ArrayList<>();
    HashMap<String, Double> list_input;
    HashMap<String, Float> list_hasilentropi;
    Data hsl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spk);

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        mainlayout = (RelativeLayout) findViewById(R.id.mainlayout);
        suhu = (Spinner) findViewById(R.id.input_suhu);
        ph = (Spinner) findViewById(R.id.input_ph);
        tinggi = (Spinner) findViewById(R.id.input_tinggi);
        oksigen = (Spinner) findViewById(R.id.input_oksigen);
        lama = (Spinner) findViewById(R.id.input_lama);
        mudah = (Spinner) findViewById(R.id.input_mudah);
        minat = (Spinner) findViewById(R.id.input_minat);
        luas = (Spinner) findViewById(R.id.input_luas);

        validator = new Validator(spk.this);
        validator.setValidationListener(new Validator.ValidationListener() {
            @Override
            public void onValidationSucceeded() {

                _suhu = getSelectValue(suhu.getSelectedItem().toString());
                _ph = Float.parseFloat(ph.getSelectedItem().toString());
                _lama = Float.parseFloat(lama.getSelectedItem().toString());
                _oksigen = Float.parseFloat(oksigen.getSelectedItem().toString());
                _luas = Float.parseFloat(luas.getSelectedItem().toString());
                _mudah = Float.parseFloat(mudah.getSelectedItem().toString());
                _minat = Float.parseFloat(minat.getSelectedItem().toString());
                _tinggi = Float.parseFloat(tinggi.getSelectedItem().toString());

//                double ck = _suhu + _ph + _lama + _oksigen + _luas + _mudah + _minat + _tinggi;
//                if (ck < 100 || ck > 100){
//                    Snackbar.make(mainlayout, "Total Nilai Bobot tidak boleh kurang atau lebih dari 100", Snackbar.LENGTH_LONG).show();
//                }else{

                    wSuhu = _suhu / 100;
                    wPh = _ph / 100;
                    wLama = _lama / 100;
                    wOksigen = _oksigen / 100;
                    wLuas = _luas / 100;
                    wMudah = _mudah / 100;
                    wMinat = _minat / 100;
                    wTinggi = _tinggi / 100;

                    list_input = new HashMap<String, Double>();
                    list_input.put("suhu", wSuhu);
                    list_input.put("ph", wPh);
                    list_input.put("oksigen", wOksigen);
                    list_input.put("lama", wLama);
                    list_input.put("luas", wLuas);
                    list_input.put("tinggi", wTinggi);
                    list_input.put("minat", wMinat);
                    list_input.put("mudah", wMudah);

                    progressDialog = new ProgressDialog(spk.this);
                    progressDialog.setMessage("Loading..");
                    progressDialog.show();

                    getData();
//                }

            }

            @Override
            public void onValidationFailed(List<ValidationError> errors) {
                for (ValidationError error : errors) {
                    View view = error.getView();
                    String message = error.getCollatedErrorMessage(spk.this);

                    // Display error messages ;)
                    if (view instanceof EditText) {
                        ((EditText) view).setError(message);
                    } else {
                        Toast.makeText(spk.this, message, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        progressDialog = new ProgressDialog(spk.this);
        progressDialog.setMessage("Loading..");
        progressDialog.show();
        getKonfigurasiData();
    }

    private void getData(){
        if (isOnline()){
            requestData(Server.URL+"select.php");
        }else{
            Snackbar.make(mainlayout, "No Network Available", Snackbar.LENGTH_LONG).setAction("REFRESH", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getData();
                }
            });
        }
    }

    private void getKonfigurasiData(){
        if (isOnline()){
            getKonfigurasiBobot(Server.URL+"select_all_bobot.php");
        }else{
            Snackbar.make(mainlayout, "No Network Available", Snackbar.LENGTH_LONG).setAction("REFRESH", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getData();
                }
            });
        }
    }

    private void getKonfigurasiBobot(String uri){
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(uri, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("success", response.toString());

                try {
                    JSONObject suhuobj = response.getJSONObject("suhu");
                    JSONObject phobj = response.getJSONObject("ph");
                    JSONObject tinggiobj = response.getJSONObject("tinggi");
                    JSONObject lamaobj = response.getJSONObject("lama");
                    JSONObject oksigenobj = response.getJSONObject("oksigen");
                    JSONObject pakanobj = response.getJSONObject("pakan");
                    JSONObject minatobj = response.getJSONObject("minat");
                    JSONObject luasobj = response.getJSONObject("luas");

                    _bobotsuhu = new NilaiBobot();
                    _bobotph = new NilaiBobot();
                    _bobottinggi = new NilaiBobot();
                    _bobotlama = new NilaiBobot();
                    _bobotoksigen = new NilaiBobot();
                    _bobotmudah = new NilaiBobot();
                    _bobotminat = new NilaiBobot();
                    _bobotluas = new NilaiBobot();

                    _bobotsuhu.setSangatrendah(suhuobj.getString("suhu_sgt_rdh"));
                    _bobotsuhu.setRendah(suhuobj.getString("suhu_rdh_1"));
                    _bobotsuhu.setRendah_2(suhuobj.getString("suhu_rdh_2"));
                    _bobotsuhu.setSedang(suhuobj.getString("suhu_sdg_1"));
                    _bobotsuhu.setSedang_2(suhuobj.getString("suhu_sdg_2"));
                    _bobotsuhu.setTinggi(suhuobj.getString("suhu_ting_1"));
                    _bobotsuhu.setTinggi_2(suhuobj.getString("suhu_ting_2"));
                    _bobotsuhu.setSangattinggi(suhuobj.getString("suhu_sgt_ting"));

                    _bobotph.setSangatrendah(phobj.getString("ph_sgt_rdh"));
                    _bobotph.setRendah(phobj.getString("ph_rdh_1"));
                    _bobotph.setRendah_2(phobj.getString("ph_rdh_2"));
                    _bobotph.setSedang(phobj.getString("ph_sdg_1"));
                    _bobotph.setSedang_2(phobj.getString("ph_sdg_2"));
                    _bobotph.setTinggi(phobj.getString("ph_ting_1"));
                    _bobotph.setTinggi_2(phobj.getString("ph_ting_2"));
                    _bobotph.setSangattinggi(phobj.getString("ph_sgt_ting"));

                    _bobottinggi.setSangatrendah(tinggiobj.getString("ting_sgt_rdh"));
                    _bobottinggi.setRendah(tinggiobj.getString("ting_rdh_1"));
                    _bobottinggi.setRendah_2(tinggiobj.getString("ting_rdh_2"));
                    _bobottinggi.setSedang(tinggiobj.getString("ting_sdg_1"));
                    _bobottinggi.setSedang_2(tinggiobj.getString("ting_sdg_2"));
                    _bobottinggi.setTinggi(tinggiobj.getString("ting_ting_1"));
                    _bobottinggi.setTinggi_2(tinggiobj.getString("ting_ting_2"));
                    _bobottinggi.setSangattinggi(tinggiobj.getString("ting_sgt"));

                    _bobotlama.setSangatrendah(lamaobj.getString("lama_sgt_rdh"));
                    _bobotlama.setRendah(lamaobj.getString("lama_rdh_1"));
                    _bobotlama.setRendah_2(lamaobj.getString("lama_rdh_2"));
                    _bobotlama.setSedang(lamaobj.getString("lama_sdg_1"));
                    _bobotlama.setSedang_2(lamaobj.getString("lama_sdg_2"));
                    _bobotlama.setTinggi(lamaobj.getString("lama_ting_1"));
                    _bobotlama.setTinggi_2(lamaobj.getString("lama_ting_2"));
                    _bobotlama.setSangattinggi(lamaobj.getString("lama_sgt_ting"));

                    _bobotoksigen.setSangatrendah(oksigenobj.getString("oks_sgt_rdh_1"));
                    _bobotoksigen.setSangatrendah2(oksigenobj.getString("oks_sgt_rdh_2"));
                    _bobotoksigen.setRendah(oksigenobj.getString("oks_rdh_1"));
                    _bobotoksigen.setRendah_2(oksigenobj.getString("oks_rdh_2"));
                    _bobotoksigen.setSedang(oksigenobj.getString("oks_sdg_1"));
                    _bobotoksigen.setSedang_2(oksigenobj.getString("oks_sdg_2"));
                    _bobotoksigen.setTinggi(oksigenobj.getString("oks_ting_1"));
                    _bobotoksigen.setTinggi_2(oksigenobj.getString("oks_ting_2"));
                    _bobotoksigen.setSangattinggi(oksigenobj.getString("oks_sgt_ting"));

                    _bobotluas.setSangatrendah(luasobj.getString("luas_sgt_rdh"));
                    _bobotluas.setRendah(luasobj.getString("luas_rdh_1"));
                    _bobotluas.setRendah_2(luasobj.getString("luas_rdh_2"));
                    _bobotluas.setSedang(luasobj.getString("luas_sdg_1"));
                    _bobotluas.setSedang_2(luasobj.getString("luas_sdg_2"));
                    _bobotluas.setTinggi(luasobj.getString("luas_ting_1"));
                    _bobotluas.setTinggi_2(luasobj.getString("luas_ting_2"));
                    _bobotluas.setSangattinggi(luasobj.getString("luas_sgt_ting"));

                    _bobotmudah.setSangatrendah(pakanobj.getString("pkn_sgt_tdk_mdh"));
                    _bobotmudah.setRendah(pakanobj.getString("pkn_tdk_mdh"));
                    _bobotmudah.setSedang(pakanobj.getString("pkn_sdg"));
                    _bobotmudah.setTinggi(pakanobj.getString("pkn_mdh"));
                    _bobotmudah.setSangattinggi(pakanobj.getString("pkn_sgt_mdh"));

                    _bobotminat.setSangatrendah(minatobj.getString("minat_tdk_suka"));
                    _bobotminat.setRendah(minatobj.getString("minat_krg_suka"));
                    _bobotminat.setSedang(minatobj.getString("minat_sdg"));
                    _bobotminat.setTinggi(minatobj.getString("minat_suka"));
                    _bobotminat.setSangattinggi(minatobj.getString("minat_sgt_suka"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("success", statusCode+" "+responseString);
                progressDialog.dismiss();
            }
        });

    }

    private void requestData(String uri){

        listIkan = new ArrayList<>();

        ListSuhu = new ArrayList<>();
        ListPh = new ArrayList<>();
        ListLama = new ArrayList<>();
        ListLuas = new ArrayList<>();
        ListOksigen = new ArrayList<>();
        ListTinggi = new ArrayList<>();
        ListMinat = new ArrayList<>();
        ListMudah = new ArrayList<>();

        ListBobotSuhu = new ArrayList<>();
        ListBobotPh = new ArrayList<>();
        ListBobotLama = new ArrayList<>();
        ListBobotLuas = new ArrayList<>();
        ListBobotOksigen = new ArrayList<>();
        ListBobotTinggi = new ArrayList<>();
        ListBobotMinat = new ArrayList<>();
        ListBobotMudah = new ArrayList<>();

        hasilAkhir = new ArrayList<>();
        list_hasil = new ArrayList<>();

        listMatriksEvaluasiSuhu = new ArrayList<>();
        listMatriksEvaluasiPh = new ArrayList<>();
        listMatriksEvaluasiOksigen = new ArrayList<>();
        listMatriksEvaluasiLama = new ArrayList<>();
        listMatriksEvaluasiLuas = new ArrayList<>();
        listMatriksEvaluasiTinggi = new ArrayList<>();
        listMatriksEvaluasiMinat = new ArrayList<>();
        listMatriksEvaluasiMudah = new ArrayList<>();

        listProbabilitasSuhu = new ArrayList<>();
        listProbabilitasPh = new ArrayList<>();
        listProbabilitasOksigen = new ArrayList<>();
        listProbabilitasLama = new ArrayList<>();
        listProbabilitasLuas = new ArrayList<>();
        listProbabilitasTinggi = new ArrayList<>();
        listProbabilitasMinat = new ArrayList<>();
        listProbabilitasMudah = new ArrayList<>();

        listEntropiSuhu = new ArrayList<>();
        listEntropiPh = new ArrayList<>();
        listEntropiOksigen = new ArrayList<>();
        listEntropiLama = new ArrayList<>();
        listEntropiLuas = new ArrayList<>();
        listEntropiTinggi = new ArrayList<>();
        listEntropiMinat = new ArrayList<>();
        listEntropiMudah = new ArrayList<>();

        sqrtSuhum3 = new ArrayList<>();
        sqrtPhm3 = new ArrayList<>();
        sqrtOksigenm3 = new ArrayList<>();
        sqrtLamam3 = new ArrayList<>();
        sqrtLuasm3 = new ArrayList<>();
        sqrtTinggim3 = new ArrayList<>();
        sqrtMinatm3 = new ArrayList<>();
        sqrtMudahm3 = new ArrayList<>();

        arraySuhum3 = new ArrayList<>();
        arrayPhm3 = new ArrayList<>();
        arrayOksigenm3 = new ArrayList<>();
        arrayLamam3 = new ArrayList<>();
        arrayLuasm3 = new ArrayList<>();
        arrayTinggim3 = new ArrayList<>();
        arrayMinatm3 = new ArrayList<>();
        arrayMudahm3 = new ArrayList<>();

        list_hasilentropi = new HashMap<String, Float>();


        AsyncHttpClient client = new AsyncHttpClient();
        client.post(uri, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("success", response.toString());

                for (int i = 0; i < response.length(); i++){
                    try {
                        Data dtikan = new Data();
                        JSONObject ikan = response.getJSONObject(i);
                        dtikan.setNama_ikan(ikan.getString("nama_ikan"));
                        dtikan.setSUHU(ikan.getString("suhu"));
                        dtikan.setPH(ikan.getString("ph"));
                        dtikan.setTINGGI(ikan.getString("tinggi_darat"));
                        dtikan.setLAMA(ikan.getString("lama_ikan"));
                        dtikan.setOKSIGEN(ikan.getString("oksigen"));
                        dtikan.setMUDAH(ikan.getString("mudah_pakan"));
                        dtikan.setMINAT(ikan.getString("minat_masy"));
                        dtikan.setLUAS(ikan.getString("luas_kolam"));

                        listIkan.add(dtikan);

                        bentukMatriksEvaluasi(i);

                        ListSuhu.add(Float.parseFloat(dtikan.getSUHU()));
                        ListPh.add(Float.parseFloat(dtikan.getPH()));
                        ListTinggi.add(Float.parseFloat(dtikan.getTINNGI()));
                        ListLama.add(Float.parseFloat(dtikan.getLAMA()));
                        ListLuas.add(Float.parseFloat(dtikan.getLUAS()));
                        ListOksigen.add(Float.parseFloat(dtikan.getOKSIGEN()));
                        ListMudah.add(Float.parseFloat(dtikan.getMUDAH()));
                        ListMinat.add(Float.parseFloat(dtikan.getMINAT()));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                sumOfVarBobotSuhu = 0;
                sumOfVarBobotPh = 0;
                sumOfVarBobotOksigen = 0;
                sumOfVarBobotLama = 0;
                sumOfVarBobotLuas = 0;
                sumOfVarBobotTinggi = 0;
                sumOfVarBobotMudah = 0;
                sumOfVarBobotMinat = 0;

                sumOfVarBobotEntropySuhu = 0;
                sumOfVarBobotEntropyPh = 0;
                sumOfVarBobotEntropyOksigen = 0;
                sumOfVarBobotEntropyLama = 0;
                sumOfVarBobotEntropyLuas = 0;
                sumOfVarBobotEntropyTinggi = 0;
                sumOfVarBobotEntropyMudah = 0;
                sumOfVarBobotEntropyMinat = 0;

                normalisasiMatriksEvaluasi();

                matriksProbabilitasDanEntropy();

                hitungBobotEntropy();

                hitungRasio();

                for (int j = 0; j < arraySuhum3.size(); j++) {
                    hasil[0][j] = (float) (bobotAkhirSuhu * m3[j][0]) + (bobotAkhirPh * m3[j][1]) + (bobotAkhirMudah * (m3[j][7])) + (bobotAkhirMinat * (m3[j][6])) - (bobotAkhirOksigen * (m3[j][2])) - (bobotAkhirTinggi * (m3[j][5])) - (bobotAkhirLama * (m3[j][3])) - (bobotAkhirLuas * (m3[j][4]));

                    hasilAkhir.add(hasil[0][j]);
//
                    hsl = new Data();
                    hsl.setNama_ikan(listIkan.get(j).getNama_ikan());
                    hsl.setHasil(hasil[0][j]);
                    list_hasil.add(hsl);
                }

                Collections.sort(list_hasil, new Comparator<Data>() {
                    @Override
                    public int compare(Data lhs, Data rhs) {
                        if (lhs.getHasil() > rhs.getHasil()) {
                            return -1;
                        } else if (lhs.getHasil() < rhs.getHasil()) {
                            return 1;
                        }
                        return 0;
                    }
                });

                ArrayList<Float> hasilrek = new ArrayList<Float>();
                for (Data hasil: list_hasil) {
                    hasilrek.add(hasil.getHasil());
                    Log.d("HASIL: ", hasil.getNama_ikan()+": "+hasil.getHasil());
                }

//                float rek = Collections.max(hasilrek);

//                Log.d("HASIL REKOMENDASI: ", list_hasil.get(idx).getNama_ikan() + " "+ rek);

                progressDialog.dismiss();

                Bundle bundle = new Bundle();
                bundle.putSerializable("hasilspk", (Serializable) list_hasil);
                bundle.putSerializable("listinput", list_input);
                bundle.putSerializable("listentropi", list_hasilentropi);

                Intent hasilspk = new Intent(spk.this, hasil_spk.class);

                hasilspk.putExtra("bundlehasil", bundle);
                startActivity(hasilspk);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("success", statusCode+" "+responseString);
                progressDialog.dismiss();
            }
        });

    }

    //LANGKAH 1
    private void bentukMatriksEvaluasi(int pos) {
        m_suhu = 0;

        if (Float.parseFloat(listIkan.get(pos).getSUHU()) <= Float.parseFloat(_bobotsuhu.getSangatrendah())) {
            m_suhu = 1;
        } else if (Float.parseFloat(_bobotsuhu.getRendah()) < Float.parseFloat(listIkan.get(pos).getSUHU()) && Float.parseFloat(listIkan.get(pos).getSUHU()) <= Float.parseFloat(_bobotsuhu.getRendah_2())) {
            m_suhu = 2;
        } else if (Float.parseFloat(_bobotsuhu.getSedang()) < Float.parseFloat(listIkan.get(pos).getSUHU()) && Float.parseFloat(listIkan.get(pos).getSUHU()) <= Float.parseFloat(_bobotsuhu.getSedang_2())) {
            m_suhu = 3;
        } else if (Float.parseFloat(_bobotsuhu.getTinggi()) < Float.parseFloat(listIkan.get(pos).getSUHU()) && Float.parseFloat(listIkan.get(pos).getSUHU()) <= Float.parseFloat(_bobotsuhu.getTinggi_2())) {
            m_suhu = 4;
        } else if (Float.parseFloat(listIkan.get(pos).getSUHU()) > Float.parseFloat(_bobotsuhu.getSangattinggi())) {
            m_suhu = 5;
        }

        ListBobotSuhu.add(Float.parseFloat(String.valueOf(m_suhu)));

        m_ph = 0;

        if (Float.parseFloat(listIkan.get(pos).getPH()) <= Float.parseFloat(_bobotph.getSangatrendah())) {
            m_ph = 1;
        } else if (Float.parseFloat(_bobotph.getRendah()) < (Float.parseFloat(listIkan.get(pos).getPH())) && (Float.parseFloat(listIkan.get(pos).getPH())) <= Float.parseFloat(_bobotph.getRendah_2())) {
            m_ph = 2;
        } else if (Float.parseFloat(_bobotph.getSedang()) < (Float.parseFloat(listIkan.get(pos).getPH())) && (Float.parseFloat(listIkan.get(pos).getPH()) <= Float.parseFloat(_bobotph.getSedang_2()))) {
            m_ph = 3;
        } else if (Float.parseFloat(_bobotph.getTinggi()) < (Float.parseFloat(listIkan.get(pos).getPH())) && (Float.parseFloat(listIkan.get(pos).getPH()) < Float.parseFloat(_bobotph.getTinggi_2()))) {
            m_ph = 4;
        } else if (Float.parseFloat(listIkan.get(pos).getPH()) >= Float.parseFloat(_bobotph.getSangattinggi())) {
            m_ph = 5;
        }

        ListBobotPh.add(Float.parseFloat(String.valueOf(m_ph)));

        m_oksigen = 0;

        if (Float.parseFloat(listIkan.get(pos).getOKSIGEN()) > Float.parseFloat(_bobotoksigen.getSangatrendah()) && Float.parseFloat(listIkan.get(pos).getOKSIGEN()) <= Float.parseFloat(_bobotoksigen.getSangatrendah2())) {
            m_oksigen = 5;
        } else if (Float.parseFloat(_bobotoksigen.getRendah()) < (Float.parseFloat(listIkan.get(pos).getOKSIGEN())) && (Float.parseFloat(listIkan.get(pos).getOKSIGEN())) <= Float.parseFloat(_bobotoksigen.getRendah_2())) {
            m_oksigen = 4;
        } else if (Float.parseFloat(_bobotoksigen.getSedang()) <= (Float.parseFloat(listIkan.get(pos).getOKSIGEN())) && Float.parseFloat(listIkan.get(pos).getOKSIGEN()) <= Float.parseFloat(_bobotoksigen.getSedang_2())) {
            m_oksigen = 3;
        } else if (Float.parseFloat(_bobotoksigen.getTinggi()) <= (Float.parseFloat(listIkan.get(pos).getOKSIGEN())) && (Float.parseFloat(listIkan.get(pos).getOKSIGEN()) <= Float.parseFloat(_bobotoksigen.getTinggi_2()))) {
            m_oksigen = 2;
        } else if (Float.parseFloat(listIkan.get(pos).getOKSIGEN()) <= Float.parseFloat(_bobotoksigen.getSangattinggi())) {
            m_oksigen = 1;
        }

        ListBobotOksigen.add(Float.parseFloat(String.valueOf(m_oksigen)));

        m_lama = 0;

        if (Float.parseFloat(listIkan.get(pos).getLAMA()) >= Float.parseFloat(_bobotlama.getSangatrendah())) {
            m_lama = 5;
        } else if (Float.parseFloat(_bobotlama.getRendah()) < (Float.parseFloat(listIkan.get(pos).getLAMA())) && Float.parseFloat(listIkan.get(pos).getLAMA()) < Float.parseFloat(_bobotlama.getRendah_2())) {
            m_lama = 4;
        } else if (Float.parseFloat(_bobotlama.getSedang()) < (Float.parseFloat(listIkan.get(pos).getLAMA())) && Float.parseFloat(listIkan.get(pos).getLAMA()) <= Float.parseFloat(_bobotlama.getSedang_2())) {
            m_lama = 3;
        } else if (Float.parseFloat(_bobotlama.getTinggi()) < (Float.parseFloat(listIkan.get(pos).getLAMA())) && Float.parseFloat(listIkan.get(pos).getLAMA()) <= Float.parseFloat(_bobotlama.getTinggi_2())) {
            m_lama = 2;
        } else if (Float.parseFloat(listIkan.get(pos).getLAMA()) <= Float.parseFloat(_bobotlama.getSangattinggi())) {
            m_lama = 1;
        }

        ListBobotLama.add(Float.parseFloat(String.valueOf(m_lama)));

        m_luas = 0;

        if (Float.parseFloat(listIkan.get(pos).getLUAS()) >= Float.parseFloat(_bobotluas.getSangatrendah())) {
            m_luas = 5;
        } else if (Float.parseFloat(_bobotluas.getRendah()) <= (Float.parseFloat(listIkan.get(pos).getLUAS())) && Float.parseFloat(listIkan.get(pos).getLUAS()) < Float.parseFloat(_bobotluas.getRendah_2())) {
            m_luas = 4;
        } else if (Float.parseFloat(_bobotluas.getSedang()) <= (Float.parseFloat(listIkan.get(pos).getLUAS())) && Float.parseFloat(listIkan.get(pos).getLUAS()) < Float.parseFloat(_bobotluas.getSedang_2())) {
            m_luas = 3;
        } else if (Float.parseFloat(_bobotluas.getTinggi()) <= (Float.parseFloat(listIkan.get(pos).getLUAS())) && Float.parseFloat(listIkan.get(pos).getLUAS()) < Float.parseFloat(_bobotluas.getTinggi_2())) {
            m_luas = 2;
        } else if (Float.parseFloat(listIkan.get(pos).getLUAS()) < Float.parseFloat(_bobotluas.getSangattinggi())) {
            m_luas = 1;
        }

        ListBobotLuas.add(Float.parseFloat(String.valueOf(m_luas)));

        m_tinggi = 0;

        if (Float.parseFloat(listIkan.get(pos).getTINNGI()) >= Float.parseFloat(_bobottinggi.getSangatrendah())) {
            m_tinggi = 5;
        } else if (Float.parseFloat(_bobottinggi.getRendah()) < (Float.parseFloat(listIkan.get(pos).getTINNGI())) && Float.parseFloat(listIkan.get(pos).getTINNGI()) < Float.parseFloat(_bobottinggi.getRendah_2())) {
            m_tinggi = 4;
        } else if (Float.parseFloat(_bobottinggi.getSedang()) < (Float.parseFloat(listIkan.get(pos).getTINNGI())) && Float.parseFloat(listIkan.get(pos).getTINNGI()) <= Float.parseFloat(_bobottinggi.getSedang_2())) {
            m_tinggi = 3;
        } else if (Float.parseFloat(_bobottinggi.getTinggi()) < (Float.parseFloat(listIkan.get(pos).getTINNGI())) && Float.parseFloat(listIkan.get(pos).getTINNGI()) <= Float.parseFloat(_bobottinggi.getTinggi_2())) {
            m_tinggi = 2;
        } else if (Float.parseFloat(listIkan.get(pos).getTINNGI()) <= Float.parseFloat(_bobottinggi.getSangattinggi())) {
            m_tinggi = 1;
        }

        ListBobotTinggi.add(Float.parseFloat(String.valueOf(m_tinggi)));

        m_minat = 0;

        if (listIkan.get(pos).getMINAT().equals(_bobotminat.getSangatrendah())){
            m_minat = 1;
        }else if(listIkan.get(pos).getMINAT().equals(_bobotminat.getRendah())){
            m_minat = 2;
        }else if(listIkan.get(pos).getMINAT().equals(_bobotminat.getSedang())){
            m_minat = 3;
        }else if(listIkan.get(pos).getMINAT().equals(_bobotminat.getTinggi())){
            m_minat = 4;
        }else if(listIkan.get(pos).getMINAT().equals(_bobotminat.getSangattinggi())){
            m_minat = 5;
        }

        ListBobotMinat.add(Float.parseFloat(String.valueOf(m_minat)));

        m_mudah = 0;

        if (listIkan.get(pos).getMUDAH().equals(_bobotmudah.getSangatrendah())){
            m_mudah = 1;
        }else if(listIkan.get(pos).getMUDAH().equals(_bobotmudah.getRendah())){
            m_mudah = 2;
        }else if(listIkan.get(pos).getMUDAH().equals(_bobotmudah.getSedang())){
            m_mudah = 3;
        }else if(listIkan.get(pos).getMUDAH().equals(_bobotmudah.getTinggi())){
            m_mudah = 4;
        }else if(listIkan.get(pos).getMUDAH().equals(_bobotmudah.getSangattinggi())){
            m_mudah = 5;
        }

        ListBobotMudah.add(Float.parseFloat(String.valueOf(m_mudah)));
    }
    //LANGKAH 2
    private void normalisasiMatriksEvaluasi() {

        float maxBobotSuhu = Collections.max(ListBobotSuhu);
        float maxBobotPh = Collections.max(ListBobotPh);
        float minBobotOksigen = Collections.min(ListBobotOksigen);
        float minBobotLama = Collections.min(ListBobotLama);
        float minBobotLuas = Collections.min(ListBobotLuas);
        float minBobotTinggi = Collections.min(ListBobotTinggi);
        float maxBobotMinat = Collections.max(ListBobotMinat);
        float maxBobotMudah = Collections.max(ListBobotMudah);

        for (int a = 0; a < ListBobotSuhu.size(); a++) {
            mBobotEvaluasi[a][0] = ListBobotSuhu.get(a) / maxBobotSuhu;
            listMatriksEvaluasiSuhu.add(mBobotEvaluasi[a][0]);
            sumOfVarBobotSuhu = sumOfVarBobotSuhu + mBobotEvaluasi[a][0];
        }
        for (int a = 0; a < ListBobotPh.size(); a++) {
            mBobotEvaluasi[a][1] = ListBobotPh.get(a) / maxBobotPh;
            listMatriksEvaluasiPh.add(mBobotEvaluasi[a][1]);
            sumOfVarBobotPh = sumOfVarBobotPh + mBobotEvaluasi[a][1];
        }

        for (int a = 0; a < ListBobotOksigen.size(); a++) {
            mBobotEvaluasi[a][2] = minBobotOksigen / ListBobotOksigen.get(a);
            listMatriksEvaluasiOksigen.add(mBobotEvaluasi[a][2]);
            sumOfVarBobotOksigen = sumOfVarBobotOksigen + mBobotEvaluasi[a][2];
        }

        for (int a = 0; a < ListBobotLama.size(); a++) {
            mBobotEvaluasi[a][3] = minBobotLama / ListBobotLama.get(a);
            listMatriksEvaluasiLama.add(mBobotEvaluasi[a][3]);
            sumOfVarBobotLama = sumOfVarBobotLama + mBobotEvaluasi[a][3];
        }

        for (int a = 0; a < ListBobotLuas.size(); a++) {
            mBobotEvaluasi[a][4] = minBobotLuas / ListBobotLuas.get(a);
            listMatriksEvaluasiLuas.add(mBobotEvaluasi[a][4]);
            sumOfVarBobotLuas = sumOfVarBobotLuas + mBobotEvaluasi[a][4];
        }

        for (int a = 0; a < ListBobotTinggi.size(); a++) {
            mBobotEvaluasi[a][5] = minBobotTinggi / ListBobotTinggi.get(a);
            listMatriksEvaluasiTinggi.add(mBobotEvaluasi[a][5]);
            sumOfVarBobotTinggi = sumOfVarBobotTinggi + mBobotEvaluasi[a][5];
        }

        for (int a = 0; a < ListBobotMinat.size(); a++) {
            mBobotEvaluasi[a][6] = ListBobotMinat.get(a) / maxBobotMinat;
            listMatriksEvaluasiMinat.add(mBobotEvaluasi[a][6]);
            sumOfVarBobotMinat = sumOfVarBobotMinat + mBobotEvaluasi[a][6];
        }

        for (int a = 0; a < ListBobotMudah.size(); a++) {
            mBobotEvaluasi[a][7] = ListBobotMudah.get(a) / maxBobotMudah;
            listMatriksEvaluasiMudah.add(mBobotEvaluasi[a][7]);
            sumOfVarBobotMudah = sumOfVarBobotMudah + mBobotEvaluasi[a][7];
        }
    }
    //LANGKAH 3
    private void matriksProbabilitasDanEntropy() {

        for (int a = 0; a < ListBobotSuhu.size(); a++) {
            mBobotProbabilitas[a][0] = mBobotEvaluasi[a][0] / sumOfVarBobotSuhu;
            listProbabilitasSuhu.add(mBobotProbabilitas[a][0]);
            mBobotEntropi[a][0] = mBobotProbabilitas[a][0] * Math.log(mBobotProbabilitas[a][0]);
            listEntropiSuhu.add((float) mBobotEntropi[a][0]);
            sumOfVarBobotEntropySuhu = (float) (sumOfVarBobotEntropySuhu + mBobotEntropi[a][0]);
            EkSuhu = (float) ((-1 / (Math.log(ListBobotSuhu.size()))) * sumOfVarBobotEntropySuhu);
        }

        for (int a = 0; a < ListBobotPh.size(); a++) {
            mBobotProbabilitas[a][1] = mBobotEvaluasi[a][1] / sumOfVarBobotPh;
            listProbabilitasPh.add(mBobotProbabilitas[a][1]);
            mBobotEntropi[a][1] = mBobotProbabilitas[a][1] * Math.log(mBobotProbabilitas[a][1]);
            listEntropiPh.add((float) mBobotEntropi[a][1]);
            sumOfVarBobotEntropyPh = (float) (sumOfVarBobotEntropyPh + mBobotEntropi[a][1]);
            EkPh = (float) ((-1 / (Math.log(ListBobotPh.size()))) * sumOfVarBobotEntropyPh);
        }

        for (int a = 0; a < ListBobotOksigen.size(); a++) {
            mBobotProbabilitas[a][2] = mBobotEvaluasi[a][2] / sumOfVarBobotOksigen;
            listProbabilitasOksigen.add(mBobotProbabilitas[a][2]);
            mBobotEntropi[a][2] = mBobotProbabilitas[a][2] * Math.log(mBobotProbabilitas[a][2]);
            listEntropiOksigen.add((float) mBobotEntropi[a][2]);
            sumOfVarBobotEntropyOksigen = (float) (sumOfVarBobotEntropyOksigen + mBobotEntropi[a][2]);
            EkOksigen = (float) ((-1 / (Math.log(ListBobotOksigen.size()))) * sumOfVarBobotEntropyOksigen);
        }

        for (int a = 0; a < ListBobotLama.size(); a++) {
            mBobotProbabilitas[a][3] = mBobotEvaluasi[a][3] / sumOfVarBobotLama;
            listProbabilitasLama.add(mBobotProbabilitas[a][3]);
            mBobotEntropi[a][3] = mBobotProbabilitas[a][3] * Math.log(mBobotProbabilitas[a][3]);
            listEntropiLama.add((float) mBobotEntropi[a][3]);
            sumOfVarBobotEntropyLama = (float) (sumOfVarBobotEntropyLama + mBobotEntropi[a][3]);
            EkLama = (float) ((-1 / (Math.log(ListBobotLama.size()))) * sumOfVarBobotEntropyLama);
        }

        for (int a = 0; a < ListBobotLuas.size(); a++) {
            mBobotProbabilitas[a][4] = mBobotEvaluasi[a][4] / sumOfVarBobotLuas;
            listProbabilitasLuas.add(mBobotProbabilitas[a][4]);
            mBobotEntropi[a][4] = mBobotProbabilitas[a][4] * Math.log(mBobotProbabilitas[a][4]);
            listEntropiLuas.add((float) mBobotEntropi[a][4]);
            sumOfVarBobotEntropyLuas = (float) (sumOfVarBobotEntropyLuas + mBobotEntropi[a][4]);
            EkLuas = (float) ((-1 / (Math.log(ListBobotLuas.size()))) * sumOfVarBobotEntropyLuas);
        }

        for (int a = 0; a < ListBobotTinggi.size(); a++) {
            mBobotProbabilitas[a][5] = mBobotEvaluasi[a][5] / sumOfVarBobotTinggi;
            listProbabilitasTinggi.add(mBobotProbabilitas[a][5]);
            mBobotEntropi[a][5] = mBobotProbabilitas[a][5] * Math.log(mBobotProbabilitas[a][5]);
            listEntropiTinggi.add((float) mBobotEntropi[a][5]);
            sumOfVarBobotEntropyTinggi = (float) (sumOfVarBobotEntropyTinggi + mBobotEntropi[a][5]);
            EkTinggi = (float) ((-1 / (Math.log(ListBobotTinggi.size()))) * sumOfVarBobotEntropyTinggi);
        }

        for (int a = 0; a < ListBobotMinat.size(); a++) {
            mBobotProbabilitas[a][6] = mBobotEvaluasi[a][6] / sumOfVarBobotMinat;
            listProbabilitasMinat.add(mBobotProbabilitas[a][6]);
            mBobotEntropi[a][6] = mBobotProbabilitas[a][6] * Math.log(mBobotProbabilitas[a][6]);
            listEntropiMinat.add((float) mBobotEntropi[a][6]);
            sumOfVarBobotEntropyMinat = (float) (sumOfVarBobotEntropyMinat + mBobotEntropi[a][6]);
            EkMinat = (float) ((-1 / (Math.log(ListBobotMinat.size()))) * sumOfVarBobotEntropyMinat);
        }

        for (int a = 0; a < ListBobotMudah.size(); a++) {
            mBobotProbabilitas[a][7] = mBobotEvaluasi[a][7] / sumOfVarBobotMudah;
            listProbabilitasMudah.add(mBobotProbabilitas[a][7]);
            mBobotEntropi[a][7] = mBobotProbabilitas[a][7] * Math.log(mBobotProbabilitas[a][7]);
            listEntropiMudah.add((float) mBobotEntropi[a][7]);
            sumOfVarBobotEntropyMudah = (float) (sumOfVarBobotEntropyMudah + mBobotEntropi[a][7]);
            EkMudah = (float) ((-1 / (Math.log(ListBobotMudah.size()))) * sumOfVarBobotEntropyMudah);
        }

    }
    //LANGKAH 4
    private void hitungBobotEntropy() {
        SumOfEk = EkSuhu + EkPh + EkOksigen + EkLama + EkLuas + EkTinggi + EkMinat + EkMudah;

        LkSuhu = (1 / (8 - (SumOfEk))) * (1 - EkSuhu);
        LkPh = (1 / (8 - (SumOfEk))) * (1 - EkPh);
        LkOksigen = (1 / (8 - (SumOfEk))) * (1 - EkOksigen);
        LkLama = (1 / (8 - (SumOfEk))) * (1 - EkLama);
        LkLuas = (1 / (8 - (SumOfEk))) * (1 - EkLuas);
        LkTinggi = (1 / (8 - (SumOfEk))) * (1 - EkTinggi);
        LkMinat = (1 / (8 - (SumOfEk))) * (1 - EkMinat);
        LkMudah = (1 / (8 - (SumOfEk))) * (1 - EkMudah);

        WEkSuhu = LkSuhu * (float)wSuhu;
        WEkPh = LkPh * (float)wPh;
        WEkOksigen = LkOksigen * (float)wOksigen;
        WEkLama = LkLama * (float)wLama;
        WEkLuas = LkLuas * (float)wLuas;
        WEkTinggi = LkTinggi * (float)wTinggi;
        WEkMinat = LkMinat * (float)wMinat;
        WEkMudah = LkMudah * (float)wMudah;

        SumOfWEk = WEkSuhu + WEkPh + WEkOksigen + WEkLama + WEkLuas + WEkTinggi + WEkMinat + WEkMudah;

        bobotAkhirSuhu = WEkSuhu / SumOfWEk;
        bobotAkhirPh = WEkPh / SumOfWEk;
        bobotAkhirOksigen = WEkOksigen / SumOfWEk;
        bobotAkhirLama = WEkLama / SumOfWEk;
        bobotAkhirLuas = WEkLuas / SumOfWEk;
        bobotAkhirTinggi = WEkTinggi / SumOfWEk;
        bobotAkhirMinat = WEkMinat / SumOfWEk;
        bobotAkhirMudah = WEkMudah / SumOfWEk;

        list_hasilentropi.put("suhu", bobotAkhirSuhu);
        list_hasilentropi.put("ph", bobotAkhirPh);
        list_hasilentropi.put("oksigen", bobotAkhirOksigen);
        list_hasilentropi.put("lama", bobotAkhirLama);
        list_hasilentropi.put("luas", bobotAkhirLuas);
        list_hasilentropi.put("tinggi", bobotAkhirTinggi);
        list_hasilentropi.put("minat", bobotAkhirMinat);
        list_hasilentropi.put("mudah", bobotAkhirMudah);
    }
    //LANGKAH 5
    private void hitungRasio() {
        sumOfVarSuhu = 0;
        sumOfVarPh = 0;
        sumOfVarOksigen = 0;
        sumOfVarLama = 0;
        sumOfVarLuas = 0;
        sumOfVarTinggi = 0;
        sumOfVarMinat = 0;
        sumOfVarMudah = 0;


        for (int a = 0; a < ListBobotSuhu.size(); a++) {
            m2[a][0] = ListBobotSuhu.get(a);
        }

        for (int a = 0; a < ListBobotSuhu.size(); a++) {
            s = (m2[a][0] * m2[a][0]);
            sumOfVarSuhu = sumOfVarSuhu + s;
        }

//------------------
        for (int b = 0; b < ListBobotPh.size(); b++) {
            m2[b][1] = ListPh.get(b);
        }

        for (int a = 0; a < ListBobotPh.size(); a++) {
            p = (m2[a][1] * m2[a][1]);
            sumOfVarPh = sumOfVarPh + p;
        }
//--------------------
        for (int b = 0; b < ListBobotOksigen.size(); b++) {
            m2[b][2] = ListBobotOksigen.get(b);
        }

        for (int a = 0; a < ListBobotOksigen.size(); a++) {
            o = (m2[a][2] * m2[a][2]);
            sumOfVarOksigen = sumOfVarOksigen + o;
        }
//---------------------
        for (int b = 0; b < ListBobotLama.size(); b++) {
            m2[b][3] = ListBobotLama.get(b);
        }

        for (int a = 0; a < ListBobotLama.size(); a++) {
            l = (m2[a][3] * m2[a][3]);
            sumOfVarLama = sumOfVarLama + l;
        }
//-------------------
        for (int b = 0; b < ListBobotLuas.size(); b++) {
            m2[b][4] = ListBobotLuas.get(b);
        }

        for (int a = 0; a < ListBobotLuas.size(); a++) {
            ls = (m2[a][4] * m2[a][4]);
            sumOfVarLuas = sumOfVarLuas + ls;
        }
//---------------------
        for (int b = 0; b < ListBobotTinggi.size(); b++) {
            m2[b][5] = ListBobotTinggi.get(b);
        }

        for (int a = 0; a < ListBobotTinggi.size(); a++) {
            t = (m2[a][5] * m2[a][5]);
            sumOfVarTinggi = sumOfVarTinggi + t;
        }
//---------------------
        for (int b = 0; b < ListBobotMinat.size(); b++) {
            m2[b][6] = ListBobotMinat.get(b);
        }

        for (int a = 0; a < ListBobotMinat.size(); a++) {
            mi = (m2[a][6] * m2[a][6]);
            sumOfVarMinat = sumOfVarMinat + mi;
        }
//----------------------
        for (int b = 0; b < ListBobotMudah.size(); b++) {
            m2[b][7] = ListBobotMudah.get(b);
        }

        for (int a = 0; a < ListBobotMudah.size(); a++) {
            mu = (m2[a][7] * m2[a][7]);
            sumOfVarMudah = sumOfVarMudah + mu;
        }

                            /**/
        for (int a = 0; a < ListSuhu.size(); a++) {
            m3[a][0] = (float) (m2[a][0] / Math.sqrt(sumOfVarSuhu));
            arraySuhum3.add(m3[a][0]);
            sqrtSuhum3.add((float) Math.sqrt(sumOfVarSuhu));
        }

        for (int b = 0; b < ListPh.size(); b++) {
            m3[b][1] = (float) (m2[b][1] / Math.sqrt(sumOfVarPh));
            arrayPhm3.add(m3[b][1]);
            sqrtPhm3.add((float) Math.sqrt(sumOfVarPh));
        }

        for (int c = 0; c < ListOksigen.size(); c++) {
            m3[c][2] = (float) (m2[c][2] / Math.sqrt(sumOfVarOksigen));
            arrayOksigenm3.add(m3[c][2]);
            sqrtOksigenm3.add((float) Math.sqrt(sumOfVarOksigen));
        }

        for (int c = 0; c < ListLama.size(); c++) {
            m3[c][3] = (float) (m2[c][3] / Math.sqrt(sumOfVarLama));
            arrayLamam3.add(m3[c][3]);
            sqrtLamam3.add((float) Math.sqrt(sumOfVarLama));
        }

        for (int c = 0; c < ListLuas.size(); c++) {
            m3[c][4] = (float) (m2[c][4] / Math.sqrt(sumOfVarLuas));
            arrayLuasm3.add(m3[c][4]);
            sqrtLuasm3.add((float) Math.sqrt(sumOfVarLuas));
        }

        for (int c = 0; c < ListTinggi.size(); c++) {
            m3[c][5] = (float) (m2[c][5] / Math.sqrt(sumOfVarTinggi));
            arrayTinggim3.add(m3[c][5]);
            sqrtTinggim3.add((float) Math.sqrt(sumOfVarTinggi));
        }

        for (int c = 0; c < ListBobotMinat.size(); c++) {
            m3[c][6] = (float) (m2[c][6] / Math.sqrt(sumOfVarMinat));
            arrayMinatm3.add(m3[c][6]);
            sqrtMinatm3.add((float) Math.sqrt(sumOfVarMinat));
        }

        for (int c = 0; c < ListBobotMudah.size(); c++) {
            m3[c][7] = (float) (m2[c][7] / Math.sqrt(sumOfVarMudah));
            arrayMudahm3.add(m3[c][6]);
            sqrtMudahm3.add((float) Math.sqrt(sumOfVarMudah));
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

    private void reset(){
        suhu.setSelection(0);
        ph.setSelection(0);
        oksigen.setSelection(0);
        lama.setSelection(0);
        luas.setSelection(0);
        tinggi.setSelection(0);
        minat.setSelection(0);
        mudah.setSelection(0);
    }

    private double getSelectValue(String val) {
        double v = 0;
        switch (val){
            case "Prioritas 1":
                v = 0.222;
            break;
            case "Prioritas 2":
                v = 0.194;
            break;
            case "Prioritas 3":
                v = 0.111;
            break;
            case "Prioritas 4":
                v = 0.055;
            break;
            case "Prioritas 5":
                v = 0.138;
            break;
            case "Prioritas 6":
                v = 0.167;
            break;
            case "Prioritas 7":
                v = 0.027;
            break;
            case "Prioritas 8":
                v = 0.083;
            break;
            default:
                v = 0;
            break;
        }
        return v;
    }


    @Override
    protected void onResume() {
        super.onResume();
        reset();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void klikproses(View view) {
        validator.validate();
    }
}
