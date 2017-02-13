package com.example.rika.spk;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.rika.spk.util.Hash;
import com.example.rika.spk.util.Helputil;
import com.example.rika.spk.util.Server;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Random;

import cz.msebera.android.httpclient.Header;

public class Ikan_details extends AppCompatActivity {

    ProgressDialog updatepropicdialog;
    SharedPreferences session;
    ActionBar actionBar;

    private Uri fileUri;
    Bitmap bitmap;
    String idikan;
    SwipeRefreshLayout refreshLayout;
    ScrollView scrollView;

    ImageView foto_ikan;
    TextView namaikan;
    TextView suhu;
    TextView ph;
    TextView tinggidarat;
    TextView lamaikan;
    TextView oksigen;
    TextView mudahpakan;
    TextView minatmas;
    TextView luaskolam;
    TextView deskripsi;

    ProgressDialog loaddialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ikan_details);
        actionBar = getSupportActionBar();

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        session = getBaseContext().getSharedPreferences(config.SHARED_PREF_NAME, MODE_PRIVATE);

        idikan = getIntent().getStringExtra("idikan");

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_ikan_details);
        scrollView = (ScrollView) findViewById(R.id.scrolllayout);

        foto_ikan = (ImageView) findViewById(R.id.foto_ikan);
        namaikan = (TextView) findViewById(R.id.nama_ikan);
        suhu = (TextView) findViewById(R.id.suhu);
        ph = (TextView) findViewById(R.id.ph);
        tinggidarat = (TextView) findViewById(R.id.tinggidarat);
        lamaikan = (TextView) findViewById(R.id.lamaikan);
        oksigen = (TextView) findViewById(R.id.oksigen);
        mudahpakan = (TextView) findViewById(R.id.mudahpakan);
        minatmas = (TextView) findViewById(R.id.minat_mas);
        luaskolam = (TextView) findViewById(R.id.luaskolam);
        deskripsi = (TextView) findViewById(R.id.deskripsi);

        loaddialog = new ProgressDialog(this);
        loaddialog.setMessage("Loading..");
        loaddialog.show();
        getData();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
    }

    public void editimage(View view) {
        if (session.getBoolean(config.LOGGEDIN_SHARED_PREF, false) == true){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 500);
            intent.putExtra("outputY", 500);
            intent.putExtra("return-data", true);

            startActivityForResult(Intent.createChooser(intent, "Select Picture"), Helputil.LIB_IMAGE_REQUEST);
        }else{
            AlertDialog.Builder imagewarning = new AlertDialog.Builder(Ikan_details.this);
            imagewarning.setTitle("Warning").setMessage("Only Admin can upload the picture").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        }
    }

    private void do_updatePropic(String uri, final String path, String ikanid){
        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inSampleSize = 8;
        bitmap = BitmapFactory.decodeFile(path, options);

        File propicimg = new File(path);

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        try {
            params.put("propic", propicimg);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        params.add("id", ikanid);
        client.post(uri, params, new JsonHttpResponseHandler(){
            @Override
            public void onStart() {
                super.onStart();
                updatepropicdialog = new ProgressDialog(Ikan_details.this);
                updatepropicdialog.setMessage("updating...");
                updatepropicdialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("UPDATE PROPIC: ", response.toString());
                updatepropicdialog.dismiss();

                try {
                    if (response.getString("response").equals("pass")){
                        foto_ikan.setImageBitmap(bitmap);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                updatepropicdialog.dismiss();
                Log.d("UPDATE PROPIC FAIL: ", responseString + " " + throwable);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle extra = null;
        if (data != null){
            extra = data.getExtras();
        }
        switch (requestCode){
            case Helputil.LIB_IMAGE_REQUEST:
                if (resultCode == RESULT_OK){
                    fileUri = data.getData();
                    if (fileUri != null){
                        Log.d("file URI: ", fileUri.getPath().toString());
                        String RealPath = getPath(fileUri);
                        if (RealPath != null){
                            Log.d("IMAGE PATH: ", "null, " + RealPath);
                            do_updatePropic(Server.URL+"uploadpic.php", RealPath, idikan);
                        }else{
                            try {
                                InputStream is = getContentResolver().openInputStream(fileUri);
                                foto_ikan.setImageBitmap(BitmapFactory.decodeStream(is));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }else{
                        if (extra != null){
                            Bitmap croped = extra.getParcelable("data");
                            foto_ikan.setImageBitmap(croped);

                            fileUri = saveOutput(croped);
                            do_updatePropic(Server.URL+"uploadpic.php", fileUri.getPath(), idikan);
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    @SuppressLint("NewApi")
    private String getPath(Uri uri) {
        if( uri == null ) {
            return null;
        }

        String[] projection = { MediaStore.Images.Media.DATA };

        Cursor cursor;
        if(Build.VERSION.SDK_INT >19)
        {
            // Will return "image:x*"
            String wholeID = DocumentsContract.getDocumentId(uri);
            // Split at colon, use second item in the array
            String id = wholeID.split(":")[1];
            // where id is equal to
            String sel = MediaStore.Images.Media._ID + "=?";

            cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection, sel, new String[]{ id }, null);
        }
        else
        {
            cursor = getContentResolver().query(uri, projection, null, null, null);
        }
        String path = null;
        try
        {
            int column_index = cursor
                    .getColumnIndex(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            path = cursor.getString(column_index).toString();
            cursor.close();
        }
        catch(NullPointerException e) {

        }
        return path;
    }

    private Uri saveOutput(Bitmap croppedImage) {
        Uri saveUri = null;
        // Saves the image in cache, you may want to modify this to save it to Gallery
        Random random = new Random();
        int rand = random.nextInt(999);

        Calendar cal = Calendar.getInstance();
        String timeStamp = "" + cal.get(Calendar.YEAR) + cal.get(Calendar.MONTH) + cal.get(Calendar.DATE);

        String imgname = "IMG_"+ timeStamp + "_" + session.getString(config.EMAIL_SHARED_PREF, "") + Hash.md5(rand+"") +".jpg";

        File file = new File(getCacheDir(), imgname);
        OutputStream outputStream = null;
        try
        {
            file.getParentFile().mkdirs();
            saveUri = Uri.fromFile(file);
            outputStream = getContentResolver().openOutputStream(saveUri);
            if (outputStream != null)
            {
                croppedImage.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
            }
        } catch (IOException e)
        {
            // log the error
        }

        return saveUri;
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

    private void getData(){
        if (isOnline()){
            requestData(Server.URL+"select_details.php");
        }else{
            Snackbar.make(refreshLayout, "No Network Available", Snackbar.LENGTH_LONG).setAction("REFRESH", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getData();
                }
            });
        }
    }

    private void requestData(String uri){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("id", idikan+"");
        client.post(uri, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("IKAN DETAILS: ", response.toString());
                loaddialog.dismiss();
                refreshLayout.setRefreshing(false);
                try {
                    namaikan.setText("Nama ikan: " + response.getString("nama_ikan"));
                    deskripsi.setText("Deskripsi: " + response.getString("deskripsi"));
                    suhu.setText("Suhu: " + response.getString("suhu"));
                    ph.setText("Ph: "+ response.getString("ph"));
                    tinggidarat.setText("Ketinggian Daratan:" + response.getString("tinggi_darat"));
                    lamaikan.setText("Lama ideal pembesaran ikan: "+response.getString("lama_ikan"));
                    oksigen.setText("Oksigen: "+response.getString("oksigen"));
                    mudahpakan.setText("Kemudahan Pakan: "+response.getString("mudah_pakan"));
                    minatmas.setText("Minat Masyarakat terhadap ikan: "+response.getString("minat_masy"));
                    luaskolam.setText("Luas Kolam: " + response.getString("luas_kolam"));

                    Picasso.with(Ikan_details.this).load(Server.URL_IMG+response.getString("url_gambar")).error(R.drawable.no_image_available).into(foto_ikan);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("IKAN FAIL: ", statusCode+" "+responseString);
                loaddialog.dismiss();
                refreshLayout.setRefreshing(false);
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
}
