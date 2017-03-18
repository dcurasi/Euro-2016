package com.example.dario.euro2016;

/**
 * Created by Dario on 07/07/2016.
 */
public class Torneo {

    private long id;
    private String nome;
    private String anno;
    private long id_api_torneo;
    private int current_giornata;
    private int num_giornate;
    private int num_squadre;
    private int num_partite;
    private String last_update;

    public Torneo() {
        this.id = -1;
        this.nome = null;
        this.anno = null;
        this.id_api_torneo = -1;
        this.current_giornata = -1;
        this.num_giornate = -1;
        this.num_squadre = -1;
        this.num_partite = -1;
        this.last_update = null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAnno() {
        return anno;
    }

    public void setAnno(String anno) {
        this.anno = anno;
    }

    public long getId_api_torneo() {
        return id_api_torneo;
    }

    public void setId_api_torneo(long id_api_torneo) {
        this.id_api_torneo = id_api_torneo;
    }

    public int getCurrent_giornata() {
        return current_giornata;
    }

    public void setCurrent_giornata(int current_giornata) {
        this.current_giornata = current_giornata;
    }

    public int getNum_giornate() {
        return num_giornate;
    }

    public void setNum_giornate(int num_giornate) {
        this.num_giornate = num_giornate;
    }

    public int getNum_squadre() {
        return num_squadre;
    }

    public void setNum_squadre(int num_squadre) {
        this.num_squadre = num_squadre;
    }

    public int getNum_partite() {
        return num_partite;
    }

    public void setNum_partite(int num_partite) {
        this.num_partite = num_partite;
    }

    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = Utility.setLast_update(last_update);
    }
}
