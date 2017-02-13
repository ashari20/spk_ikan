package com.example.rika.spk.data;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by Rika on 11/4/2016.
 */
public class Data implements Serializable{
    private String id, nama_ikan, url_gambar, suhu, ph,tinggi_darat,lama_ikan,oksigen,mudah_pakan,minat_masy,luas_kolam,deskripsi;

    private float hasil;

    public Data(){

    }
    public Data(String id, String nama_ikan, String url_gambar, String suhu, String ph, String tinggi_darat, String lama_ikan, String oksigen, String mudah_pakan, String minat_masy, String luas_kolam, String deskripsi){
        this.id = id;
        this.nama_ikan = nama_ikan;
        this.url_gambar = url_gambar;
        this.suhu = suhu;
        this.ph = ph;
        this.tinggi_darat = tinggi_darat;
        this.lama_ikan = lama_ikan;
        this.oksigen = oksigen;
        this.mudah_pakan=mudah_pakan;
        this.minat_masy=minat_masy;
        this.luas_kolam=luas_kolam;
        this.deskripsi=deskripsi;

    }

    public float getHasil() {
        return hasil;
    }

    public void setHasil(float hasil) {
        this.hasil = hasil;
    }
    public String getId(){return id;}

    public void setId(String id) {
        this.id = id;
    }

    public String getNama_ikan() {
        return nama_ikan;
    }

    public void setNama_ikan(String nama_ikan) {
        this.nama_ikan = nama_ikan;
    }

    public String getGambar() {
        return url_gambar;
    }

    public void setGambar(String url_gambar) {
        this.url_gambar = url_gambar;
    }

    public String getSUHU() {
        return suhu;
    }

    public void setSUHU(String suhu) {
        this.suhu = suhu;
    }
    public String getPH() {
        return ph;
    }

    public void setPH(String ph) {

        this.ph = ph;
    }
    public String getTINNGI() {
        return tinggi_darat;
    }

    public void setTINGGI(String tinggi_darat) {

        this.tinggi_darat = tinggi_darat;
    }
    public String getLAMA() {
        return lama_ikan;
    }

    public void setLAMA(String lama_ikan) {

        this.lama_ikan = lama_ikan;
    }
    public String getOKSIGEN() {return oksigen;}

    public void setOKSIGEN(String oksigen) {

        this.oksigen = oksigen;
    }
    public String getMUDAH() {
        return mudah_pakan;
    }

    public void setMUDAH(String mudah_pakan) {

        this.mudah_pakan = mudah_pakan;
    }
    public String getMINAT() {
        return minat_masy;
    }

    public void setMINAT(String minat_masy) {

        this.minat_masy = minat_masy;
    }
    public String getLUAS() {
        return luas_kolam;
    }

    public void setLUAS(String luas_kolam) {

        this.luas_kolam = luas_kolam;
    }
    public String getDESKRIPSI() {
        return deskripsi;
    }

    public void setDESKRIPSI(String deskripsi) {

        this.deskripsi = deskripsi;
    }

    public static Comparator<Data> StuRollno = new Comparator<Data>() {

        public int compare(Data lhs, Data rhs) {
            float rollno1 = lhs.getHasil();
            float rollno2 = lhs.getHasil();

//           int result = (int)rollno2-(int)rollno1;return result;
            if (rollno2 > rollno1)
                return -1;
            if (rollno1 > rollno2)
                return 1;

            return 0;
        }
    };
}
