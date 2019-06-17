package com.example.marcio.lojavirtual.view;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marcio.lojavirtual.Api_manipulation.BuscaCarrinho;
import com.example.marcio.lojavirtual.Api_manipulation.PopulaCarrinho;
import com.example.marcio.lojavirtual.R;
import com.example.marcio.lojavirtual.model.ItemCarrinho;
import com.example.marcio.lojavirtual.model.Produto;
import com.example.marcio.lojavirtual.model.Session;

import org.json.JSONException;
import org.json.JSONObject;

public class Custom_alertDialog extends Dialog implements View.OnClickListener {

    public Activity c;
    public Button mais, menos, ok;
    TextView txt_qtd;
    BuscaCarrinho buscaCarrinho;
    Produto prod;
    ItemCarrinho item;
    double qtd = 0;


    public Custom_alertDialog(Activity a, Produto prod, ItemCarrinho item) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.prod = prod;
        this.item = item;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_alert_dialog);
        mais = (Button) findViewById(R.id.btn_mais);
        menos = (Button) findViewById(R.id.btn_menos);
        ok = (Button) findViewById(R.id.btn_ok);
        txt_qtd = (TextView) findViewById(R.id.txt_qtd);

        mais.setOnClickListener(this);
        menos.setOnClickListener(this);
        ok.setOnClickListener(this);

        txt_qtd.setText(String.valueOf(item.getQuantidade()));
        qtd = item.getQuantidade();

        buscaCarrinho = new BuscaCarrinho(c, AtividadeLoja.txt_valor);

    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_mais) {
            qtd = qtd + 1;
            if (qtd > prod.getQnt()) {
                qtd--;
                Toast.makeText(c, "Quantidade de produto insuficiente no estoque!", Toast.LENGTH_LONG).show();
            } else {
                txt_qtd.setText(String.valueOf(qtd));
            }

        } else if (v.getId() == R.id.btn_menos) {
            qtd = qtd - 1;
            if (qtd < 0) {
                qtd++;
                Toast.makeText(c, "Mínimo é 0!", Toast.LENGTH_LONG).show();
            }
            txt_qtd.setText(String.valueOf(qtd));


        } else if (v.getId() == R.id.btn_ok) {

            JSONObject prodJson = new JSONObject();
            Session session = new Session(c);
            try {
                prodJson.put("id", prod.getId());
                prodJson.put("qnt", qtd);

                if (item.getId() == null) {
                    PopulaCarrinho populaCarrinho = new PopulaCarrinho();
                    populaCarrinho.request("auth/carrinho", "POST", prodJson, session.token(), c);
                } else if (item.getId() != null && qtd > 0) {
                    PopulaCarrinho populaCarrinho = new PopulaCarrinho();
                    populaCarrinho.request("auth/carrinho", "PUT", prodJson, session.token(), c);
                } else if (item.getId() != null && qtd == 0){
                    PopulaCarrinho populaCarrinho = new PopulaCarrinho();
                    populaCarrinho.request("auth/carrinho/" + prod.getId(), "DELETE", prodJson, session.token(), c);
                }


                buscaCarrinho.request("auth/carrinho", "GET", session.token());

                this.dismiss();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
