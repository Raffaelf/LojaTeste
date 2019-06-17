package com.example.marcio.lojavirtual.Api_manipulation;

import android.content.Context;
import android.os.AsyncTask;

import com.example.marcio.lojavirtual.model.ItemCarrinho;
import com.example.marcio.lojavirtual.view.AtividadeLoja;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PopulaCarrinho extends AsyncTask<String, Void, ItemCarrinho> {
    int statusCode = 0;
    AtividadeLoja at_loja = new AtividadeLoja();
    List<ItemCarrinho> listProduto_carrinho = new ArrayList<>();
    private String API_URL = Url_config.API_URL;
    private Context context;


    public void request(String url, String method, JSONObject json, String token, Context context) {
        this.execute(url, method, json.toString(), token);
        this.context = context;
    }


    @Override
    protected ItemCarrinho doInBackground(String... param) {


        HttpURLConnection httpURLConnection = null;

        try {

            String res_body = null;
            StringBuffer response = new StringBuffer();


            URL url = new URL(API_URL + param[0]);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(param[1]);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setRequestProperty("Authorization", param[3]);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);


            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(param[2]);
            wr.flush();
            wr.close();

            statusCode = httpURLConnection.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            res_body = response.toString();

            Gson gson = new Gson();
            ItemCarrinho item_add_carrinho = gson.fromJson(res_body, ItemCarrinho.class);

            /*for(int i = 0; i<cat_array.length; i++ ){
                listProduto_carrinho.add(cat_array[i]);
            }*/


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
    protected void onPostExecute(ItemCarrinho code) {



    }
}
