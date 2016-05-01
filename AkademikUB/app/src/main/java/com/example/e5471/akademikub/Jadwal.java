package com.example.e5471.akademikub;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by E5471 on 01/05/2016.
 */
public class Jadwal {

    public String tanggal;
    public String bulan;
    public String tahun;
    public String judul;
    public String keterangan;

    public Jadwal(String s_tanggal, String judul, String keterangan) {

        tahun = s_tanggal.substring(0,5);
        bulan = s_tanggal.substring(5,8);
        tanggal = s_tanggal.substring(8);

        this.judul = judul;
        this.keterangan = keterangan;

        switch (bulan){
            case "01" :
                bulan = "JAN";
                break;
            case "02" :
                bulan = "FEB";
                break;
            case "03" :
                bulan = "MAR";
                break;
            case "04" :
                bulan = "APR";
                break;
            case "05" :
                bulan = "MEI";
                break;
            case "06" :
                bulan = "JUN";
                break;
            case "07" :
                bulan = "JUL";
                break;
            case "08" :
                bulan = "AGU";
                break;
            case "09" :
                bulan = "SEP";
                break;
            case "10" :
                bulan = "OKT";
                break;
            case "11" :
                bulan = "NOV";
                break;
            case "12" :
                bulan = "DES";
                break;
            default:
                bulan = null;
        }

    }

}
