package com.example.rika.spk;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.rika.spk.Adapter.Adapter;
import com.example.rika.spk.app.AppController;
import com.example.rika.spk.data.Data;
import com.example.rika.spk.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class jenis_ikan extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    Toolbar toolbar;
    FloatingActionButton fab;
    ListView list;
    SwipeRefreshLayout swipe;
    List<Data> itemList = new ArrayList<Data>();
    Adapter adapter;
    int success;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    EditText txt_id, txt_nama_ikan, txt_deskripsi,txt_gambar, txt_suhu, txt_ph, txt_tinggi, txt_lama,txt_oksigen,txt_pakan,txt_minat,txt_luas;
    String id, nama_ikan, url_gambar,suhu,ph,tinggi_darat,lama_ikan,oksigen,mudah_pakan,minat_masy,luas_kolam,deskripsi;

    private static final String TAG = jenis_ikan.class.getSimpleName();

    private static String url_select = Server.URL + "select.php";
    private static String url_insert = Server.URL + "insert.php";
    private static String url_edit = Server.URL + "edit.php";
    private static String url_update = Server.URL + "update.php";
    private static String url_delete = Server.URL + "delete.php";

    public static final String TAG_ID = "id";
    public static final String TAG_NAMA_IKAN = "nama_ikan";
    public static final String TAG_GAMBAR = "url_gambar";
    public static final String TAG_SUHU = "suhu";
    public static final String TAG_PH = "ph";
    public static final String TAG_TINGGI="tinggi_darat";
    public static final String TAG_LAMA="lama_ikan";
    public static final String TAG_OKSIGEN="oksigen";
    public static final String TAG_MUDAH="mudah_pakan";
    public static final String TAG_MINAT="minat_masy";
    public static final String TAG_LUAS="luas_kolam";
    public static final String TAG_DESKRIPSI="deskripsi";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String tag_json_obj = "json_obj_req";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jenis_ikan);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // menghubungkan variablel pada layout dan pada java
        fab     = (FloatingActionButton) findViewById(R.id.fab_add);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        list = (ListView) findViewById(R.id.list);

        // untuk mengisi data dari JSON ke dalam adapter
        adapter = new Adapter(jenis_ikan.this, itemList);
        list.setAdapter(adapter);

        swipe.setOnRefreshListener(this);

        swipe.post(new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing(true);
                itemList.clear();
                adapter.notifyDataSetChanged();
                callVolley(); //ngambil data ikan
            }
        }
        );
        // fungsi floating action button memanggil form kontak
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogForm("", "", "", "", "", "", "", "", "", "", "", "", "SIMPAN");
            }
        });

        // listview ditekan lama akan menampilkan dua pilihan edit atau delete data
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view,
                                           final int position, long id) {
                // TODO Auto-generated method stub
                final String idx = itemList.get(position).getId();

                final CharSequence[] dialogitem = {"Edit", "Delete"};
                dialog = new AlertDialog.Builder(jenis_ikan.this);
                dialog.setCancelable(true);
                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        switch (which) {
                            case 0:
                                edit(idx);
                                break;
                            case 1:
                                delete(idx);
                                break;
                        }
                    }
                }).show();
                return false;
            }
        });
    }
    @Override
    public void onRefresh() {
        itemList.clear();
        adapter.notifyDataSetChanged();
        callVolley();
    }

    // untuk mengosongi edittext pada form
    private void kosong(){
        txt_id.setText(null);
        txt_nama_ikan.setText(null);
        txt_deskripsi.setText(null);
        txt_gambar.setText(null);
        txt_suhu.setText(null);
        txt_ph.setText(null);
        txt_oksigen.setText(null);
        txt_lama.setText(null);
        txt_luas.setText(null);
        txt_minat.setText(null);
        txt_pakan.setText(null);
        txt_tinggi.setText(null);
    }

    // untuk menampilkan dialog from kontak
    private void DialogForm(String idx, String nama_ikanx, String deskripsix, String gambarx,
                            String suhux, String phx, String lamax, String tinggix, String minatx,
                            String pakanx, String oksigenx, String luasx,String button) {
        dialog = new AlertDialog.Builder(jenis_ikan.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_input_ikan, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        //dialog.setIcon(R.mipmap.logo_ikan);
        dialog.setTitle("Form Input Ikan");

        txt_id      = (EditText) dialogView.findViewById(R.id.txt_id);
        txt_nama_ikan    = (EditText) dialogView.findViewById(R.id.txt_nama_ikan);
        txt_deskripsi  = (EditText) dialogView.findViewById(R.id.txt_deskripsi);
        txt_suhu  = (EditText) dialogView.findViewById(R.id.txt_suhu);
        txt_ph  = (EditText) dialogView.findViewById(R.id.txt_ph);
        txt_lama  = (EditText) dialogView.findViewById(R.id.txt_lama);
        txt_tinggi  = (EditText) dialogView.findViewById(R.id.txt_tinggi);
        txt_minat  = (EditText) dialogView.findViewById(R.id.txt_minat);
        txt_pakan  = (EditText) dialogView.findViewById(R.id.txt_pakan);
        txt_oksigen  = (EditText) dialogView.findViewById(R.id.txt_oksigen);
        txt_luas  = (EditText) dialogView.findViewById(R.id.txt_luas);

        if (!idx.isEmpty()){
            txt_id.setText(idx);
            txt_nama_ikan.setText(nama_ikanx);
            txt_deskripsi.setText(deskripsix);
            txt_gambar.setText(gambarx);
            txt_suhu.setText(suhux);
            txt_ph.setText(phx);
            txt_lama.setText(lamax);
            txt_tinggi.setText(tinggix);
            txt_minat.setText(minatx);
            txt_pakan.setText(pakanx);
            txt_oksigen.setText(oksigenx);
            txt_luas.setText(luasx);
        } else {
            kosong();
        }

        dialog.setPositiveButton(button, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                id      = txt_id.getText().toString();
                nama_ikan    = txt_nama_ikan.getText().toString();
                deskripsi    = txt_deskripsi.getText().toString();
                url_gambar    = txt_gambar.getText().toString();
                suhu    = txt_suhu.getText().toString();
                ph      = txt_ph.getText().toString();
                lama_ikan    = txt_lama.getText().toString();
                tinggi_darat    = txt_tinggi.getText().toString();
                minat_masy    = txt_minat.getText().toString();
                mudah_pakan    = txt_pakan.getText().toString();
                oksigen    = txt_oksigen.getText().toString();
                luas_kolam    = txt_luas.getText().toString();

                simpan_update();
                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                kosong();
            }
        });

        dialog.show();
    }


    //data list ga muncul
    // untuk menampilkan semua data pada listview
    private void callVolley(){
        itemList.clear();
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(true);

        // membuat request JSON
        JsonArrayRequest jArr = new JsonArrayRequest(url_select, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                // Parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        Data item = new Data();

                        item.setId(obj.getString(TAG_ID));
                        item.setNama_ikan(obj.getString(TAG_NAMA_IKAN));
                        item.setGambar(obj.getString(TAG_GAMBAR));
                        item.setSUHU(obj.getString(TAG_SUHU));
                        item.setPH(obj.getString(TAG_PH));
                        item.setTINGGI(obj.getString(TAG_TINGGI));
                        item.setLAMA(obj.getString(TAG_LAMA));
                        item.setOKSIGEN(obj.getString(TAG_OKSIGEN));
                        item.setMUDAH(obj.getString(TAG_MUDAH));
                        item.setMINAT(obj.getString(TAG_MINAT));
                        item.setLUAS(obj.getString(TAG_LUAS));
                        item.setDESKRIPSI(obj.getString(TAG_DESKRIPSI));

                        // menambah item ke array
                        itemList.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                // notifikasi adanya perubahan data pada adapter
                adapter.notifyDataSetChanged();

                swipe.setRefreshing(false);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                swipe.setRefreshing(false);
            }
        });

        // menambah request ke request queue
        AppController.getInstance().addToRequestQueue(jArr);
    }// fungsi untuk menyimpan atau update
    private void simpan_update() {
        String url;
        // jika id kosong maka simpan, jika id ada nilainya maka update
        if (id.isEmpty()){
            url = url_insert;
        } else {
            url = url_update;
        }

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("Add/update", jObj.toString());

                        callVolley();
                        kosong();

                        Toast.makeText(jenis_ikan.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(jenis_ikan.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(jenis_ikan.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                // jika id kosong maka simpan, jika id ada nilainya maka update
                if (id.isEmpty()){
                    params.put("nama_ikan", nama_ikan);
                    params.put("url_gambar",url_gambar);
                    params.put("suhu",suhu);
                    params.put("ph",ph);
                    params.put("tinggi_darat",tinggi_darat);
                    params.put("lama_ikan",lama_ikan);
                    params.put("oksigen",oksigen);
                    params.put("mudah_pakan",mudah_pakan);
                    params.put("minat_masy",minat_masy);
                    params.put("luas_kolam",luas_kolam);
                    params.put("deskripsi",deskripsi);
                } else {
                    params.put("id", id);
                    params.put("nama_ikan", nama_ikan);
                    params.put("url_gambar",url_gambar);
                    params.put("suhu",suhu);
                    params.put("ph",ph);
                    params.put("tinggi_darat",tinggi_darat);
                    params.put("lama_ikan",lama_ikan);
                    params.put("oksigen",oksigen);
                    params.put("mudah_pakan",mudah_pakan);
                    params.put("minat_masy",minat_masy);
                    params.put("luas_kolam",luas_kolam);
                    params.put("deskripsi",deskripsi);
                }

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }
    // fungsi untuk get edit data
    private void edit(final String idx){
        StringRequest strReq = new StringRequest(Request.Method.POST, url_edit, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("get edit data", jObj.toString());
                        String idx      = jObj.getString(TAG_ID);
                        String nama_ikanx    = jObj.getString(TAG_NAMA_IKAN);
                        String deskripsix  = jObj.getString(TAG_DESKRIPSI);
                        String gambarx  = jObj.getString(TAG_GAMBAR);
                        String suhux  = jObj.getString(TAG_SUHU);
                        String phx  = jObj.getString(TAG_PH);
                        String lamax  = jObj.getString(TAG_LAMA);
                        String tinggix  = jObj.getString(TAG_TINGGI);
                        String minatx  = jObj.getString(TAG_MINAT);
                        String pakanx  = jObj.getString(TAG_MUDAH);
                        String oksigenx  = jObj.getString(TAG_OKSIGEN);
                        String luasx  = jObj.getString(TAG_LUAS);

                        DialogForm(idx, nama_ikanx, deskripsix,gambarx,suhux,phx,lamax,luasx,tinggix,minatx,pakanx,oksigenx, "UPDATE");

                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(jenis_ikan.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(jenis_ikan.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", idx);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }
    // fungsi untuk menghapus
    private void delete(final String idx){
        StringRequest strReq = new StringRequest(Request.Method.POST, url_delete, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("delete", jObj.toString());

                        callVolley();

                        Toast.makeText(jenis_ikan.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(jenis_ikan.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(jenis_ikan.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", idx);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }
}
