package com.example.marcio.lojavirtual.Api_manipulation;

import android.content.Intent;
import android.os.AsyncTask;

import com.example.marcio.lojavirtual.model.Cliente;
import com.example.marcio.lojavirtual.view.AtividadeLogin;
import com.example.marcio.lojavirtual.view.AtividadeLoja;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Verifica_token extends AsyncTask<String, Void, Integer> {

    private Cliente cli;
    private String API_URL = Url_config.API_URL;


    public void request(String url, String method, String token) {
        this.execute(url, method, token);
    }


    @Override
    protected Integer doInBackground(String... param) {

        int statusCode = 0;
        HttpURLConnection httpURLConnection = null;

        try {
            URL url = new URL(API_URL + param[0]);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(param[1]);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setRequestProperty("Authorization", param[2]);
            httpURLConnection.setDoInput(true);


            /*DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(param[2]);
            wr.flush();
            wr.close();*/

            statusCode = httpURLConnection.getResponseCode();
            //auth = httpURLConnection.getHeaderField("Authorization");


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }

        return statusCode;

    }


    @Override
    protected void onPostExecute(Integer code) {

        if (code.equals(200)) {
            Intent i = new Intent(AtividadeLogin.context, AtividadeLoja.class);
            AtividadeLogin.context.startActivity(i);

        } else {
            return;
        }

    }
}
