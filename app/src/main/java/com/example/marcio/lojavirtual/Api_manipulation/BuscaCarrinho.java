package com.example.marcio.lojavirtual.Api_manipulation;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import com.example.marcio.lojavirtual.model.Carrinho;
import com.example.marcio.lojavirtual.model.ItemCarrinho;
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

public class BuscaCarrinho extends AsyncTask<String, Void, String> {

    private String API_URL = Url_config.API_URL;
    int statusCode = 0;
    double valor_carrinho = 0;
    AtividadeLoja at_loja = new AtividadeLoja();
    List<ItemCarrinho> listItemCarrinho = new ArrayList<>();
    Context con;
    TextView t;


    public BuscaCarrinho(Context context, TextView textView) {
        this.con = context;
        this.t = textView;
    }


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
            httpURLConnection.setDoInput(true);

            statusCode = httpURLConnection.getResponseCode();


            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            res_body = response.toString();

            Gson gson = new Gson();
            Carrinho cat_array = gson.fromJson(res_body, Carrinho.class);

            listItemCarrinho = cat_array.getItens();


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
        at_loja.listItemCarrinho.clear();


        at_loja.listItemCarrinho.addAll(listItemCarrinho);

        if (listItemCarrinho.size() > 0) {
            for (int i = 0; i < listItemCarrinho.size(); i++) {
                double qtd = listItemCarrinho.get(i).getQuantidade();
                double preco = listItemCarrinho.get(i).getProduto().getPreco();
                valor_carrinho = valor_carrinho + (qtd * preco);
            }

            String valor = String.valueOf(valor_carrinho);

            t.setText(valor);
        }
    }
}
