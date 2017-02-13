package com.example.rika.spk;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.rika.spk.Adapter.AdapterIkan;
import com.example.rika.spk.app.AppController;
import com.example.rika.spk.data.Data;
import com.example.rika.spk.util.Server;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikepenz.materialize.color.Material;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    CoordinatorLayout mainlayout;

    private Toolbar toolbar;
    ActionBar actionBar = null;
    SwipeRefreshLayout refreshLayout;
    boolean canceledtask;
    AsyncHttpClient client;

    Drawer homeDrawer;
    AccountHeader accountHeader;
    ProfileDrawerItem user;
    PrimaryDrawerItem adminButton;

    ProgressDialog progressDialog;
    FloatingActionButton fab;

    ListView viewIkan;
    List<Data> ListIkan;

    SharedPreferences session;

    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    EditText txt_id, txt_nama_ikan, txt_deskripsi,txt_gambar, txt_suhu, txt_ph, txt_tinggi, txt_lama,txt_oksigen,txt_pakan,txt_minat,txt_luas;
    String id, nama_ikan, url_gambar,suhu,ph,tinggi_darat,lama_ikan,oksigen,mudah_pakan,minat_masy,luas_kolam,deskripsi;

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
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Jenis Ikan");
        setSupportActionBar(toolbar);

        session = getBaseContext().getSharedPreferences(config.SHARED_PREF_NAME, MODE_PRIVATE);

        mainlayout = (CoordinatorLayout) findViewById(R.id.mainlayout);
        viewIkan = (ListView) findViewById(R.id.ikanlist);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_swiperefresh);

        fab = (FloatingActionButton) findViewById(R.id.fab_add);

        user = new ProfileDrawerItem().withName((session.getBoolean(config.LOGGEDIN_SHARED_PREF, false) != true) ? "Guest" : session.getString(config.EMAIL_SHARED_PREF, "Guest"))
                .withEmail(session.getBoolean(config.LOGGEDIN_SHARED_PREF, false) ? "guest@spk.com" : session.getString(config.EMAIL_SHARED_PREF, "Guest") + "@spk.com")
                .withTextColor(Color.WHITE);
        adminButton = new PrimaryDrawerItem().withName((session.getBoolean(config.LOGGEDIN_SHARED_PREF, false) != true) ? "Admin" : "Logout")
                .withIcon((session.getBoolean(config.LOGGEDIN_SHARED_PREF, false)) ? GoogleMaterial.Icon.gmd_verified_user : GoogleMaterial.Icon.gmd_lock);

        if (session.getBoolean(config.LOGGEDIN_SHARED_PREF, false) == true){
            fab.setVisibility(View.VISIBLE);
        }else{
            fab.setVisibility(View.INVISIBLE);
        }

        accountHeader = new AccountHeaderBuilder().withActivity(this)
                .withHeaderBackground(R.drawable.accheadback)
                .addProfiles(
                        user
                ).withSelectionListEnabledForSingleProfile(false).build();

        homeDrawer = new DrawerBuilder().withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(accountHeader)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withTranslucentStatusBar(false)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("SPK Ikan")
                            .withIcon(GoogleMaterial.Icon.gmd_functions),
                        new PrimaryDrawerItem().withName("Lihat Bobot")
                        .withIcon(GoogleMaterial.Icon.gmd_search),
                        adminButton
                ).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem instanceof Nameable){
                            String menu = ((Nameable) drawerItem).getName().getText();
                            switch (menu){
                                case "SPK Ikan":
                                    startActivity(new Intent(MainActivity.this,spk.class ));
                                    break;
                                case "Lihat Bobot":
                                    startActivity(new Intent(MainActivity.this,lihat_bobot.class ));
                                    break;
                                case "Admin":
                                    startActivityForResult(new Intent(MainActivity.this,admin.class), config.USER_LOGIN);
                                    break;
                                case "Logout":
                                    doLogout();
                                    break;
                                default:
                                    break;
                            }
                        }
                        return false;
                    }
                })
                .build();

        if (isOnline()){
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Getting Data...");
            progressDialog.show();
            requestData(Server.URL + "select.php");
        }else{
            Snackbar.make(mainlayout, "No Connection Available", Snackbar.LENGTH_LONG).setAction("REFRESH", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requestData(Server.URL + "select.php");
                }
            });
        }

        viewIkan.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view,
                                           final int position, long id) {
                // TODO Auto-generated method stub
                final String idx = ListIkan.get(position).getId();

                final CharSequence[] dialogitem = {"Edit", "Delete"};
                dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setCancelable(true);
                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        switch (which) {
                            case 0:
                                if (session.getBoolean(config.LOGGEDIN_SHARED_PREF, false) == true){
                                    edit(idx);
                                }else{
                                    AlertDialog.Builder warningAlert = new AlertDialog.Builder(MainActivity.this);
                                    warningAlert.setTitle("Warning").setMessage("Only admin can edit the data").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).show();
                                }
                                break;
                            case 1:
                                if (session.getBoolean(config.LOGGEDIN_SHARED_PREF, false) ==true){
                                    final AlertDialog.Builder deletealert = new AlertDialog.Builder(MainActivity.this);
                                    deletealert.setTitle("Warning").setMessage("Are you sure want to delete?").setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            delete(idx);
                                        }
                                    }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).show();
                                }else{
                                    AlertDialog.Builder warningAlert = new AlertDialog.Builder(MainActivity.this);
                                    warningAlert.setTitle("Warning").setMessage("Only admin can delete the data").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).show();
                                }
                                break;
                        }
                    }
                }).show();
                return false;
            }
        });

        viewIkan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String idikan = ListIkan.get(position).getId();
                Log.d("ID IKAN: ", idikan);

                Intent intent = new Intent(MainActivity.this, Ikan_details.class);
                intent.putExtra("idikan", idikan+"");
                startActivity(intent);
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isOnline()){
                    requestData(Server.URL + "select.php");
                }else{
                    Snackbar.make(mainlayout, "No Connection Available", Snackbar.LENGTH_LONG).setAction("REFRESH", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            requestData(Server.URL + "select.php");
                        }
                    });
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogForm("", "", "", "", "", "", "", "", "", "", "", "", "SIMPAN");
            }
        });
    }

    private void doLogout() {
        SharedPreferences.Editor edit = session.edit();
        edit.clear();
        edit.commit();

        user.withName("Guest")
                .withEmail("guest@spk.com")
                .withTextColor(Color.WHITE);

        adminButton.withName("Admin")
                .withIcon(GoogleMaterial.Icon.gmd_verified_user);

        accountHeader.updateProfile(user);
        homeDrawer.updateItem(adminButton);
        fab.setVisibility(View.INVISIBLE);
    }

    private void edit(String idx) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("id", idx);
        client.post(url_edit, params, new JsonHttpResponseHandler(){
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("GETDATA SUCCESS: ", response.toString());

                try {
                    id = response.getString("id");
                    nama_ikan = response.getString("nama_ikan");
                    url_gambar = response.getString("url_gambar");
                    suhu = response.getString("suhu");
                    ph = response.getString("ph");
                    tinggi_darat = response.getString("tinggi_darat");
                    lama_ikan = response.getString("lama_ikan");
                    oksigen = response.getString("oksigen");
                    mudah_pakan = response.getString("mudah_pakan");
                    minat_masy = response.getString("minat_masy");
                    luas_kolam = response.getString("luas_kolam");
                    deskripsi = response.getString("deskripsi");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                DialogForm(id, nama_ikan, deskripsi, url_gambar, suhu, ph, lama_ikan, tinggi_darat, minat_masy, mudah_pakan, oksigen, luas_kolam, "UPDATE");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("GETDATA FAIL: ", statusCode+": "+ responseString);
            }
        });
    }

    private void delete(String idx) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("id", idx);
        client.post(url_delete, params, new JsonHttpResponseHandler(){
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("DELETE SUCCESS: ", response.toString());
                requestData(Server.URL + "select.php");
                try {
                    Snackbar.make(mainlayout, response.getString("message"), Snackbar.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("DELETE FAIL: ", statusCode+": "+responseString);
            }
        });
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

        client = new AsyncHttpClient();
        client.post(uri, new JsonHttpResponseHandler(){
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("SUCCESS: ", response.toString());
                progressDialog.dismiss();
                refreshLayout.setRefreshing(false);
                ListIkan = new ArrayList<Data>();
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        Data ikan = new Data();
                        ikan.setId(obj.getString("id"));
                        ikan.setNama_ikan(obj.getString("nama_ikan"));

                        ListIkan.add(ikan);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("PARSING ERROR: ", e.getMessage().toString());
                    }
                }

                AdapterIkan adapterIkan = new AdapterIkan(getBaseContext(), R.layout.list_row, ListIkan);
                viewIkan.setAdapter(adapterIkan);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("FAIL: ", statusCode + throwable.getStackTrace().toString());
                progressDialog.dismiss();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void DialogForm(String idx, String nama_ikanx, String deskripsix, String gambarx,
                            String suhux, String phx, String lamax, String tinggix, String minatx,
                            String pakanx, String oksigenx, String luasx,String button) {
        dialog = new AlertDialog.Builder(this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_input_ikan, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        //dialog.setIcon(R.mipmap.logo_ikan);
        dialog.setTitle("Form Input Ikan");

        txt_id      = (EditText) dialogView.findViewById(R.id.txt_id);
        txt_nama_ikan    = (EditText) dialogView.findViewById(R.id.txt_nama_ikan);
        txt_deskripsi  = (EditText) dialogView.findViewById(R.id.txt_deskripsi);
//        txt_gambar  = (EditText) dialogView.findViewById(R.id.txt_gambar);
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
//            txt_gambar.setText(gambarx);
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
//                url_gambar    = txt_gambar.getText().toString();
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

    private void kosong(){
        txt_id.setText(null);
        txt_nama_ikan.setText(null);
        txt_deskripsi.setText(null);
//        txt_gambar.setText(null);
        txt_suhu.setText(null);
        txt_ph.setText(null);
        txt_oksigen.setText(null);
        txt_lama.setText(null);
        txt_luas.setText(null);
        txt_minat.setText(null);
        txt_pakan.setText(null);
        txt_tinggi.setText(null);
    }

    private void simpan_update(){
        String action="";
        String process="";
        if (id.equals("") || id.isEmpty()){
            action = url_insert;
            process = "Inserting data...";
        }
        else{
            action = url_update;
            process = "Updating data...";
        }

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        if (!id.isEmpty()){
            params.add("id", id);
        }

        params.add("namaikan", nama_ikan);
        params.add("deskripsi", deskripsi);
//        params.add("urlgambar", url_gambar);
        params.add("suhu", suhu);
        params.add("ph", ph);
        params.add("lamaikan", lama_ikan);
        params.add("tinggidarat", tinggi_darat);
        params.add("minatmasy", minat_masy);
        params.add("mudahpakan", mudah_pakan);
        params.add("oksigen", oksigen);
        params.add("luaskolam", luas_kolam);

        client.post(action, params, new JsonHttpResponseHandler(){
            @Override
            public void onStart() {
                super.onStart();
                ProgressDialog loading = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Inserting data...");
                progressDialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                progressDialog.dismiss();
                Log.d("IN/UP SUCCESS: ", response.toString());
                requestData(Server.URL + "select.php");
                try {
                    Snackbar.make(mainlayout, response.getString("message"), Snackbar.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("IN/UP FAIL: ", statusCode+": "+responseString);
                progressDialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case config.USER_LOGIN:
                if (resultCode == RESULT_OK){
                    String username = data.getStringExtra("username");
                    user.withName(username).withEmail(username+"@spk.com").withTextColor(Color.WHITE);

                    adminButton.withName("Logout").withIcon(GoogleMaterial.Icon.gmd_lock);

                    accountHeader.updateProfile(user);
                    homeDrawer.updateItem(adminButton);

                    fab.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }
    }

    public void klikspk(View v){
        startActivity(new Intent(MainActivity.this,spk.class ));
    }
    public void klikjenis(View v){
        startActivity(new Intent(MainActivity.this,jenis_ikan.class ));
        }
    public void klikbobot(View v){
        startActivity(new Intent(MainActivity.this,lihat_bobot.class ));
    }
    public void klikadmin(View v){
        startActivity(new Intent(MainActivity.this,admin.class ));
    }
}
