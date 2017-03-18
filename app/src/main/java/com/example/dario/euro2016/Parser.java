package com.example.dario.euro2016;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Vector;

/**
 * Created by Dario on 21/07/2016.
 */
public class Parser {

    private static final String TAG = Parser.class.getSimpleName();

    public static void getTorneoDataFromJson(Context context, String torneoJsonStr) throws JSONException {
        final String TORNEO_ID = "id";
        final String TORNEO_NOME = "caption";
        final String TORNEO_ANNO = "year";
        final String TORNEO_GIORNATA_CORRENTE = "currentMatchday";
        final String TORNEO_NUM_GIORNATE = "numberOfMatchdays";
        final String TORNEO_NUM_SQUADRE = "numberOfTeams";
        final String TORNEO_NUM_PARTITE = "numberOfGames";
        final String TORNEO_LAST_UPDATE = "lastUpdated";

        JSONObject torneoJson = new JSONObject(torneoJsonStr);
        Vector<ContentValues> cVVector = new Vector<>(1);
        Torneo currentTorneo = new Torneo();

        currentTorneo.setId_api_torneo(torneoJson.getLong(TORNEO_ID));
        currentTorneo.setNome(torneoJson.getString(TORNEO_NOME));
        currentTorneo.setAnno(torneoJson.getString(TORNEO_ANNO));
        currentTorneo.setCurrent_giornata(torneoJson.getInt(TORNEO_GIORNATA_CORRENTE));
        currentTorneo.setNum_giornate(torneoJson.getInt(TORNEO_NUM_GIORNATE));
        currentTorneo.setNum_squadre(torneoJson.getInt(TORNEO_NUM_SQUADRE));
        currentTorneo.setNum_partite(torneoJson.getInt(TORNEO_NUM_PARTITE));
        currentTorneo.setLast_update(torneoJson.getString(TORNEO_LAST_UPDATE));

        ContentValues torneoValues = new ContentValues();
        torneoValues.put(Contract.Torneo.COLUMN_ID_API, currentTorneo.getId_api_torneo());
        torneoValues.put(Contract.Torneo.COLUMN_NOME, currentTorneo.getNome());
        torneoValues.put(Contract.Torneo.COLUMN_ANNO, currentTorneo.getAnno());
        torneoValues.put(Contract.Torneo.COLUMN_GIOR_CORRENTE, currentTorneo.getCurrent_giornata());
        torneoValues.put(Contract.Torneo.COLUMN_NUM_GIORNATE, currentTorneo.getNum_giornate());
        torneoValues.put(Contract.Torneo.COLUMN_NUM_SQ, currentTorneo.getNum_squadre());
        torneoValues.put(Contract.Torneo.COLUMN_NUM_PARTITE, currentTorneo.getNum_partite());
        torneoValues.put(Contract.Torneo.COLUMN_LAST_UPDATE, torneoJson.getString(TORNEO_LAST_UPDATE));

        cVVector.add(torneoValues);

        int inserted = 0;
        if ( cVVector.size() > 0 ) {
            ContentValues[] cvArray = new ContentValues[cVVector.size()];
            cVVector.toArray(cvArray);
            inserted = context.getContentResolver().bulkInsert(Contract.Torneo.CONTENT_URI, cvArray);
        }
        Log.d(TAG, "Partite Complete. " + inserted + " Inserted");
    }

    public static void getPartiteDataFromJson(Context context, String partiteJsonStr) throws JSONException {
        final String PARTITE = "fixtures";
        final String PARTITA_LINKS = "_links";
        final String PARTITA_SELF = "self";
        final String PARTITA_SQ_CASA = "homeTeam";
        final String PARTITA_SQ_FUORI = "awayTeam";
        final String PARTITA_LINK_FOR_ID = "href";
        final String PARTITA_DATA = "date";
        final String PARTITA_STATO = "status";
        final String PARTITA_GIORNATA = "matchday";
        final String PARTITA_NOME_SQ_CASA = "homeTeamName";
        final String PARTITA_NOME_SQ_FUORI = "awayTeamName";
        final String PARTITA_RISULTATO = "result";
        final String PARTITA_GOL_SQ_CASA = "goalsHomeTeam";
        final String PARTITA_GOL_SQ_FUORI = "goalsAwayTeam";
        final String PARTITA_PRIMO_TEMPO = "halfTime";
        final String PARTITA_SUPPLEMENTARI = "extraTime";
        final String PARTITA_RIGORI = "penaltyShootout";

        JSONObject partiteJson = new JSONObject(partiteJsonStr);
        JSONArray partiteArray = partiteJson.getJSONArray(PARTITE);

        Vector<ContentValues> cVVector = new Vector<>(partiteArray.length());

        for(int i = 0; i < partiteArray.length(); i++) {

            ContentValues partitaValues = new ContentValues();
            Partita currentPartita = new Partita();

            JSONObject partita = partiteArray.getJSONObject(i);
            JSONObject partitaLinks = partita.getJSONObject(PARTITA_LINKS);
            JSONObject partitaId_api = partitaLinks.getJSONObject(PARTITA_SELF);
            JSONObject partitaSqCasa = partitaLinks.getJSONObject(PARTITA_SQ_CASA);
            JSONObject partitaSqFuori = partitaLinks.getJSONObject(PARTITA_SQ_FUORI);

            currentPartita.setId_api_partita(Utility.getApiIdPartitaByLinkPartita(partitaId_api.getString(PARTITA_LINK_FOR_ID)));
            currentPartita.setId_api_casa(Utility.getApiIdByLinkPartita(partitaSqCasa.getString(PARTITA_LINK_FOR_ID)));
            currentPartita.setId_api_fuori(Utility.getApiIdByLinkPartita(partitaSqFuori.getString(PARTITA_LINK_FOR_ID)));
            currentPartita.setData(partita.getString(PARTITA_DATA));
            currentPartita.setStato(partita.getString(PARTITA_STATO));
            currentPartita.setGiornata(partita.getInt(PARTITA_GIORNATA));
            currentPartita.setSquadra_casa(partita.getString(PARTITA_NOME_SQ_CASA));
            currentPartita.setSquadra_fuori(partita.getString(PARTITA_NOME_SQ_FUORI));

            if (!currentPartita.getStato().equals("TIMED")) {
                JSONObject partitaRisultato = partita.getJSONObject(PARTITA_RISULTATO);
                currentPartita.setGol_casa(partitaRisultato.getInt(PARTITA_GOL_SQ_CASA));
                currentPartita.setGol_fuori(partitaRisultato.getInt(PARTITA_GOL_SQ_FUORI));
                if (partitaRisultato.has(PARTITA_PRIMO_TEMPO)) {
                    JSONObject partitaPrimoTempo = partitaRisultato.getJSONObject(PARTITA_PRIMO_TEMPO);
                    currentPartita.setGol_pt_casa(partitaPrimoTempo.getInt(PARTITA_GOL_SQ_CASA));
                    currentPartita.setGol_pt_fuori(partitaPrimoTempo.getInt(PARTITA_GOL_SQ_FUORI));
                    if (partitaRisultato.has(PARTITA_SUPPLEMENTARI)) {
                        JSONObject partitaSupplementari = partitaRisultato.getJSONObject(PARTITA_SUPPLEMENTARI);
                        currentPartita.setGol_sup_casa(partitaSupplementari.getInt(PARTITA_GOL_SQ_CASA));
                        currentPartita.setGol_sup_fuori(partitaSupplementari.getInt(PARTITA_GOL_SQ_FUORI));
                        if (partitaRisultato.has(PARTITA_RIGORI)) {
                            JSONObject partitaRigori = partitaRisultato.getJSONObject(PARTITA_RIGORI);
                            currentPartita.setGol_rig_casa(partitaRigori.getInt(PARTITA_GOL_SQ_CASA));
                            currentPartita.setGol_rig_fuori(partitaRigori.getInt(PARTITA_GOL_SQ_FUORI));
                        }
                    }
                }
            }

            partitaValues.put(Contract.Partite.COLUMN_ID_API_PARTITA, currentPartita.getId_api_partita());
            partitaValues.put(Contract.Partite.COLUMN_ID_API_CASA, currentPartita.getId_api_casa());
            partitaValues.put(Contract.Partite.COLUMN_ID_API_FUORI, currentPartita.getId_api_fuori());
            partitaValues.put(Contract.Partite.COLUMN_DATA, currentPartita.getData());
            partitaValues.put(Contract.Partite.COLUMN_STATO, currentPartita.getStato());
            partitaValues.put(Contract.Partite.COLUMN_GIORNATA, currentPartita.getGiornata());
            partitaValues.put(Contract.Partite.COLUMN_SQ_CASA, currentPartita.getSquadra_casa());
            partitaValues.put(Contract.Partite.COLUMN_SQ_FUORI, currentPartita.getSquadra_fuori());
            partitaValues.put(Contract.Partite.COLUMN_GOL_CASA, currentPartita.getGol_casa());
            partitaValues.put(Contract.Partite.COLUMN_GOL_FUORI, currentPartita.getGol_fuori());
            partitaValues.put(Contract.Partite.COLUMN_GOL_PT_CASA, currentPartita.getGol_pt_casa());
            partitaValues.put(Contract.Partite.COLUMN_GOL_PT_FUORI, currentPartita.getGol_pt_fuori());
            partitaValues.put(Contract.Partite.COLUMN_GOL_SUP_CASA, currentPartita.getGol_sup_casa());
            partitaValues.put(Contract.Partite.COLUMN_GOL_SUP_FUORI, currentPartita.getGol_sup_fuori());
            partitaValues.put(Contract.Partite.COLUMN_GOL_RIG_CASA, currentPartita.getGol_rig_casa());
            partitaValues.put(Contract.Partite.COLUMN_GOL_RIG_FUORI, currentPartita.getGol_rig_fuori());

            cVVector.add(partitaValues);
        }
        int inserted = 0;
        if ( cVVector.size() > 0 ) {
            ContentValues[] cvArray = new ContentValues[cVVector.size()];
            cVVector.toArray(cvArray);
            inserted = context.getContentResolver().bulkInsert(Contract.Partite.CONTENT_URI, cvArray);
        }
        Log.d(TAG, "Partite Complete. " + inserted + " Inserted");
    }

    public static void getClassificheDataFromJson(Context context, String classificheJsonStr) throws JSONException {

        final String CLASSIFICHE = "standings";
        final String CLASSIFICHE_A = "A";
        final String CLASSIFICHE_B = "B";
        final String CLASSIFICHE_C = "C";
        final String CLASSIFICHE_D = "D";
        final String CLASSIFICHE_E = "E";
        final String CLASSIFICHE_F = "F";
        final String CLASSIFICHE_GRUPPO = "group";
        final String CLASSIFICHE_RANK = "rank";
        final String CLASSIFICHE_SQ = "team";
        final String CLASSIFICHE_ID_API_SQ = "teamId";
        final String CLASSIFICHE_GIOCATE = "playedGames";
        final String CLASSIFICHE_PUNTI = "points";
        final String CLASSIFICHE_GOL_FATTI = "goals";
        final String CLASSIFICHE_GOL_SUB = "goalsAgainst";
        final String CLASSIFICHE_GOL_DIFF = "goalDifference";

        JSONObject classificheJson = new JSONObject(classificheJsonStr);
        JSONObject gruppiJson = classificheJson.getJSONObject(CLASSIFICHE);

        String[] nome_gruppi = new String[] {
                CLASSIFICHE_A,
                CLASSIFICHE_B,
                CLASSIFICHE_C,
                CLASSIFICHE_D,
                CLASSIFICHE_E,
                CLASSIFICHE_F
        };

        Vector<ContentValues> cVVector = new Vector<>(nome_gruppi.length * 4);

        for(int j = 0; j < nome_gruppi.length; j++) {

            JSONArray gruppoArray = gruppiJson.getJSONArray(nome_gruppi[j]);

            for(int i = 0; i < gruppoArray.length(); i++) {

                ContentValues classificheValues = new ContentValues();
                JSONObject classifica = gruppoArray.getJSONObject(i);
                classificheValues.put(Contract.Classifiche.COLUMN_GRUPPO, classifica.getString(CLASSIFICHE_GRUPPO));
                classificheValues.put(Contract.Classifiche.COLUMN_POSIZIONE, classifica.getInt(CLASSIFICHE_RANK));
                classificheValues.put(Contract.Classifiche.COLUMN_NOME_SQ, classifica.getString(CLASSIFICHE_SQ));
                classificheValues.put(Contract.Classifiche.COLUMN_ID_API_SQ, classifica.getLong(CLASSIFICHE_ID_API_SQ));
                classificheValues.put(Contract.Classifiche.COLUMN_NUM_PARTITE, classifica.getInt(CLASSIFICHE_GIOCATE));
                classificheValues.put(Contract.Classifiche.COLUMN_PUNTI, classifica.getInt(CLASSIFICHE_PUNTI));
                classificheValues.put(Contract.Classifiche.COLUMN_GOL_FATTI, classifica.getInt(CLASSIFICHE_GOL_FATTI));
                classificheValues.put(Contract.Classifiche.COLUMN_GOL_SUBITI, classifica.getInt(CLASSIFICHE_GOL_SUB));
                classificheValues.put(Contract.Classifiche.COLUMN_DIFF_RETI, classifica.getInt(CLASSIFICHE_GOL_DIFF));

                cVVector.add(classificheValues);
            }
        }
        int inserted = 0;
        if ( cVVector.size() > 0 ) {
            ContentValues[] cvArray = new ContentValues[cVVector.size()];
            cVVector.toArray(cvArray);
            inserted = context.getContentResolver().bulkInsert(Contract.Classifiche.CONTENT_URI, cvArray);
        }
        Log.d(TAG, "Classifiche Complete. " + inserted + " Inserted");
    }
}