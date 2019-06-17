package com.example.marcio.lojavirtual.Api_manipulation;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marcio.lojavirtual.R;
import com.example.marcio.lojavirtual.model.Carrinho;
import com.example.marcio.lojavirtual.model.ItemCarrinho;
import com.example.marcio.lojavirtual.model.Produto;
import com.example.marcio.lojavirtual.view.AtividadeCarrinho;
import com.example.marcio.lojavirtual.view.AtividadeLogin;
import com.example.marcio.lojavirtual.view.AtividadeLoja;
import com.example.marcio.lojavirtual.view.CarrinhoListAdapter;
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

public class FinalizaCompras extends AsyncTask<String, Void, String> {
    private String API_URL = Url_config.API_URL;
    int statusCode = 0;
    AtividadeCarrinho at_carrinho = new AtividadeCarrinho();
    List<ItemCarrinho> listItemCarrinho = new ArrayList<>();
    List<Produto> listProdutos = new ArrayList<>();
    Context con;
    TextView t;


    public void request(String url, String method, String token) {
        this.execute(url, method, token);
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
            httpURLConnection.setRequestProperty("Authorization", param[2]);

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

        return null;
    }


    @Override
    protected void onPostExecute(String carrinho) {

        if (statusCode == 200){
            Toast.makeText(AtividadeLogin.context,"Compra Realizada Com Sucesso!", Toast.LENGTH_LONG).show();

            Intent i = new Intent(AtividadeLogin.context, AtividadeLoja.class);
            AtividadeLogin.context.startActivity(i);
        }else{
            Toast.makeText(AtividadeLogin.context,"Não FOI POSSIVEL COMPLETAR A COMPRA! TENTE NOVAMENTE!",Toast.LENGTH_LONG).show();
        }

    }
}
