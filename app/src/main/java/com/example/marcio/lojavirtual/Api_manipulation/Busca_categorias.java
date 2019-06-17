package com.example.marcio.lojavirtual.Api_manipulation;

import android.os.AsyncTask;

import com.example.marcio.lojavirtual.model.Categoria;
import com.example.marcio.lojavirtual.view.AtividadeLoja;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Busca_categorias extends AsyncTask<String, Void, String> {


    private String API_URL = Url_config.API_URL;
    int statusCode = 0;
    AtividadeLoja at_loja = new AtividadeLoja();
    List<Categoria> listCategoria = new ArrayList<>();


    public void request(String url, String method) {
        this.execute(url, method);
    }


    @Override
    protected String doInBackground(String... param) {


        String res_body = null;
        HttpURLConnection httpURLConnection = null;
        StringBuffer response = new StringBuffer();

        try {
            URL url = new URL(API_URL + param[0]);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(param[1]);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoInput(true);

            statusCode = httpURLConnection.getResponseCode();


            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            res_body = response.toString();

            Gson gson = new Gson();
            Categoria[] cat_array = gson.fromJson(res_body, Categoria[].class);

            for (int i = 0; i < cat_array.length; i++) {
                listCategoria.add(cat_array[i]);
            }


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

        return null;

    }

    @Override
    protected void onPostExecute(String categorias) {

        at_loja.listCategoria.addAll(listCategoria);
        at_loja.adapter.notifyDataSetChanged();
    }
}
