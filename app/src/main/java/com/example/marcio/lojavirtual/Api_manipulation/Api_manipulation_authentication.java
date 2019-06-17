package com.example.marcio.lojavirtual.Api_manipulation;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.marcio.lojavirtual.view.AtividadeLogin;
import com.example.marcio.lojavirtual.view.AtividadeLoja;
import com.example.marcio.lojavirtual.model.Cliente;
import com.example.marcio.lojavirtual.model.Session;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Api_manipulation_authentication extends AsyncTask<String, Void, String> {
    private Cliente cli;
    private String API_URL = Url_config.API_URL;
    int statusCode = 0;


    public void request(String url, String method, JSONObject json) {
        this.execute(url, method, json.toString());
    }


    @Override
    protected String doInBackground(String... param) {


        String auth = null;
        HttpURLConnection httpURLConnection = null;

        try {
            URL url = new URL(API_URL + param[0]);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(param[1]);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);


            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(param[2]);
            wr.flush();
            wr.close();

            statusCode = httpURLConnection.getResponseCode();
            auth = httpURLConnection.getHeaderField("Authorization");


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

        return auth;

    }


    @Override
    protected void onPostExecute(String token) {

        if (statusCode == 200) {
            //Verifica_token verifica = new Verifica_token();
            //Busca_categorias busca_categorias = new Busca_categorias();

            Session session = new Session(AtividadeLogin.context);
            session.setToken(token);

            //Iniciar async task para pegar produtos etc e preparar para atvidadeLoja
            // busca_categorias.request("categoriainfo", "GET");

            Intent i = new Intent(AtividadeLogin.context, AtividadeLoja.class);
            AtividadeLogin.context.startActivity(i);
        } else {
            Toast.makeText(AtividadeLogin.context,
                    "Verifique os Dados e Tente Novamente!",
                    Toast.LENGTH_LONG).show();
        }


        //verifica.request("auth/carrinho", "GET", session.token());


    }
}
