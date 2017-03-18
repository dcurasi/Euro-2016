package com.example.dario.euro2016;

/**
 * Created by Dario on 18/07/2016.
 */
public class Classifica {
    private long id;
    private long id_api_sq;
    private String gruppo;
    private String nome_sq;
    private int posizione;
    private int num_partite;
    private int punti;
    private int gol_fatti;
    private int gol_subiti;
    private int diff_reti;

    public Classifica() {
        this.id = -1;
        this.id_api_sq = -1;
        this.gruppo = null;
        this.nome_sq = null;
        this.posizione = -1;
        this.num_partite = -1;
        this.punti = -1;
        this.gol_fatti = -1;
        this.gol_subiti = -1;
        this.diff_reti = -1;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId_api_sq() {
        return id_api_sq;
    }

    public void setId_api_sq(long id_api_sq) {
        this.id_api_sq = id_api_sq;
    }

    public String getGruppo() {
        return "Gruppo " + gruppo;
    }

    public void setGruppo(String gruppo) {
        this.gruppo = gruppo;
    }

    public String getNome_sq() {
        return nome_sq;
    }

    public void setNome_sq(String nome_sq) {
        this.nome_sq = nome_sq;
    }

    public int getPosizione() {
        return posizione;
    }

    public void setPosizione(int posizione) {
        this.posizione = posizione;
    }

    public int getNum_partite() {
        return num_partite;
    }

    public void setNum_partite(int num_partite) {
        this.num_partite = num_partite;
    }

    public int getPunti() {
        return punti;
    }

    public void setPunti(int punti) {
        this.punti = punti;
    }

    public int getGol_fatti() {
        return gol_fatti;
    }

    public void setGol_fatti(int gol_fatti) {
        this.gol_fatti = gol_fatti;
    }

    public int getGol_subiti() {
        return gol_subiti;
    }

    public void setGol_subiti(int gol_subiti) {
        this.gol_subiti = gol_subiti;
    }

    public int getDiff_reti() {
        return diff_reti;
    }

    public void setDiff_reti(int diff_reti) {
        this.diff_reti = diff_reti;
    }
}
