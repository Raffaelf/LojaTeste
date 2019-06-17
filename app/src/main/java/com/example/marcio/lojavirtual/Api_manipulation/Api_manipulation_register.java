package com.example.marcio.lojavirtual.Api_manipulation;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.marcio.lojavirtual.view.AtividadeCadastro;
import com.example.marcio.lojavirtual.view.AtividadeLogin;
import com.example.marcio.lojavirtual.model.Cliente;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class Api_manipulation_register extends AsyncTask<String, Void, Integer> {
    private Cliente cli;
    private String API_URL = Url_config.API_URL;


    public void request(String url, String method, JSONObject json) {
        this.execute(url, method, json.toString());
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
            //httpURLConnection.setRequestProperty("Accept", "application/json");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);


            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(param[2]);
            wr.flush();
            wr.close();

            statusCode = httpURLConnection.getResponseCode();


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


        if (code.equals(201)) {
            Toast.makeText(AtividadeCadastro.context,
                    "Usuario Cadastrado com Sucesso",
                    Toast.LENGTH_SHORT).show();

            Intent i = new Intent(AtividadeCadastro.context, AtividadeLogin.class);
            AtividadeCadastro.context.startActivity(i);
        } else {
            Toast.makeText(AtividadeCadastro.context,
                    "Verifique os Dados e Tente Novamente!",
                    Toast.LENGTH_LONG).show();
        }
    }
}
