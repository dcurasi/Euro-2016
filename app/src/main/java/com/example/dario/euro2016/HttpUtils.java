package com.example.dario.euro2016;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Dario on 21/07/2016.
 */
public class HttpUtils {

    /**
     * usate queste costanti, che essendo static possono essere richiamate senza istanziare un oggetto della classe
     */
    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_DELETE = "DELETE";
    public static final String METHOD_PUT = "PUT";

    /**
     * Creazione della connection. In questo caso non ho previsto l'uso di header, che eventialmente possono
     * essere aggiunti (prima di chiamare connect) chiamando connection.setRequestProperty.
     * Usando questo metodo dunque ottenete un oggetto HttpUrlConnection. Su di esso potete controllare
     * l'esito della richiesta chiamando connection.getResponseCode, e potete leggere la risposta
     * usando il metodo sotto (HttpUtils.readIt(connection.getInputStream()).
     *
     * @param url       La url su cui effettuare la richiesta http
     * @param method    il tipo di richiesta (get, post, ecc.), potete usare le costanti presenti in questa classe
     * @param params    eventuali parametri da scrivere nel body della richiesta, può essere null
     * @return
     */
    public static HttpURLConnection buildConnection(String url, String method, String params) throws IOException {

        URL connectionUrl = new URL(url);

        HttpURLConnection connection = (HttpURLConnection) connectionUrl.openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("X-Auth-Token","a1791d4741ac4464b7df9705827a7df5");
        connection.setDoInput(true);
        connection.setConnectTimeout(10000);

        if (!method.equals(METHOD_GET)) {

            connection.setDoOutput(true);

            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            writer.write(params);

            writer.flush();
            writer.close();
            os.close();
        }

        return connection;
    }

    /**
     * Questo metodo legge il body della risposta.
     *
     * @param stream    lo stream da cui leggere i dati. Si ottiene chiamando HttpUrlConnection.getInputStream.
     *                  Occhio che se la richiesta non va in porto
     *                  (responsecode!= da 200, 201, 203, ecc.), l'inputstream è null, in quel caso
     *                  si può ottenere l'errorstrem.
     * @return
     * @throws IOException
     */
    public static String readIt(InputStream stream) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(stream));
        StringBuilder total = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            total.append(line);
        }
        return total.toString();
    }

}
