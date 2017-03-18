package com.example.dario.euro2016;

import android.util.Log;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Dario on 30/06/2016.
 */
public class Partita {
    private long id;
    private long id_api_partita;
    private String data;
    private String stato;
    private int giornata;
    private String squadra_casa;
    private String squadra_fuori;
    private int gol_casa;
    private int gol_fuori;
    private int gol_pt_casa;
    private int gol_pt_fuori;
    private int gol_sup_casa;
    private int gol_sup_fuori;
    private int gol_rig_casa;
    private int gol_rig_fuori;
    private long id_api_casa;
    private long id_api_fuori;

    public Partita() {
        this.id = -1;
        this.id_api_partita = -1;
        this.data = null;
        this.stato = null;
        this.giornata = -1;
        this.squadra_casa = null;
        this.squadra_fuori = null;
        this.gol_casa = -1;
        this.gol_fuori = -1;
        this.gol_pt_casa = -1;
        this.gol_pt_fuori = -1;
        this.gol_sup_casa = -1;
        this.gol_sup_fuori = -1;
        this.gol_rig_casa = -1;
        this.gol_rig_fuori = -1;
        this.id_api_casa = -1;
        this.id_api_fuori = -1;
    }

    public long getId() {
        return id;
    }

    public long getId_api_partita() {
        return id_api_partita;
    }

    public String getData() {
        return data;
    }

    public String getStato() {
        return stato;
    }

    public int getGiornata() {
        return giornata;
    }

    public String getGiornataName() {
        switch (giornata) {
            case 1:
                return "Giornata 1";
            case 2:
                return "Giornata 2";
            case 3:
                return "Giornata 3";
            case 4:
                return "Ottavi di finale";
            case 5:
                return "Quarti di finale";
            case 6:
                return "Semifinale";
            case 7:
                return "Finale";
            default:
                return "";
        }
    }

    public String getSquadra_casa() {
        return squadra_casa;
    }

    public String getSquadra_fuori() {
        return squadra_fuori;
    }

    public int getGol_casa() {
        return gol_casa;
    }

    public int getGol_fuori() {
        return gol_fuori;
    }

    public int getGol_pt_casa() {
        return gol_pt_casa;
    }

    public int getGol_pt_fuori() {
        return gol_pt_fuori;
    }

    public String getRisultatoPt() {
        return "H.T.: " + gol_pt_casa + " - " + gol_pt_fuori;
    }

    public boolean isSupplementari() {
        return gol_sup_casa != -1;
    }

    public int getGol_sup_casa() {
        return gol_sup_casa;
    }

    public int getGol_sup_fuori() {
        return gol_sup_fuori;
    }

    public String getRisultatoSup() {
        return "( " + gol_sup_casa + " - " + gol_sup_fuori + " d.t.s. )";
    }

    public boolean isRigori() {
        return gol_rig_casa != -1;
    }

    public int getGol_rig_casa() {
        return gol_rig_casa;
    }

    public int getGol_rig_fuori() {
        return gol_rig_fuori;
    }

    public String getRisultatoRig() {
        return "( " + gol_rig_casa + " - " + gol_rig_fuori + " d.c.r. )";
    }

    public long getId_api_casa() {
        return id_api_casa;
    }

    public long getId_api_fuori() {
        return id_api_fuori;
    }

    public String getRisultato() {
        return gol_casa + " - " + gol_fuori;
    }

    public String getGiorno() {
        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        String outputPattern = "dd/MM";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        inputFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(data);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            Log.d("testdata", e.getMessage() );
        }
        return "il " + str;
    }

    public String getOra() {
        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        String outputPattern = "HH:mm";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        inputFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(data);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            Log.d("testora", e.getMessage() );
        }
        return "ore " + str;
    }

    public String getSlugCasa() {
        return squadra_casa.substring(0,3).toUpperCase();
    }

    public String getSlugFuori() {
        return squadra_fuori.substring(0,3).toUpperCase();
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setId_api_partita(long id_api_partita) {
        this.id_api_partita = id_api_partita;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public void setGiornata(int giornata) {
        this.giornata = giornata;
    }

    public void setSquadra_casa(String squadra_casa) {
        this.squadra_casa = squadra_casa;
    }

    public void setSquadra_fuori(String squadra_fuori) {
        this.squadra_fuori = squadra_fuori;
    }

    public void setGol_casa(int gol_casa) {
        this.gol_casa = gol_casa;
    }

    public void setGol_fuori(int gol_fuori) {
        this.gol_fuori = gol_fuori;
    }

    public void setGol_pt_casa(int gol_pt_casa) {
        this.gol_pt_casa = gol_pt_casa;
    }

    public void setGol_pt_fuori(int gol_pt_fuori) {
        this.gol_pt_fuori = gol_pt_fuori;
    }

    public void setGol_sup_casa(int gol_sup_casa) {
        this.gol_sup_casa = gol_sup_casa;
    }

    public void setGol_sup_fuori(int gol_sup_fuori) {
        this.gol_sup_fuori = gol_sup_fuori;
    }

    public void setGol_rig_casa(int gol_rig_casa) {
        this.gol_rig_casa = gol_rig_casa;
    }

    public void setGol_rig_fuori(int gol_rig_fuori) {
        this.gol_rig_fuori = gol_rig_fuori;
    }

    public void setId_api_casa(long id_api_casa) {
        this.id_api_casa = id_api_casa;
    }

    public void setId_api_fuori(long id_api_fuori) {
        this.id_api_fuori = id_api_fuori;
    }
}
