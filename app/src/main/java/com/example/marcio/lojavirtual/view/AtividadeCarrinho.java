package com.example.marcio.lojavirtual.view;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.marcio.lojavirtual.Api_manipulation.BuscaCarrinho;
import com.example.marcio.lojavirtual.Api_manipulation.FinalizaCompras;
import com.example.marcio.lojavirtual.Api_manipulation.Mostra_carrinho;
import com.example.marcio.lojavirtual.R;
import com.example.marcio.lojavirtual.model.Categoria;
import com.example.marcio.lojavirtual.model.ItemCarrinho;
import com.example.marcio.lojavirtual.model.Produto;
import com.example.marcio.lojavirtual.model.Session;

import java.util.ArrayList;
import java.util.List;

public class AtividadeCarrinho extends AppCompatActivity implements View.OnClickListener {


    Mostra_carrinho mostra_carrinho;
    Session session;
    TextView valor_total;
    public static List<Produto> listProdutos = new ArrayList<>();
    public static ArrayAdapter<Produto> adapter;
    public static List<ItemCarrinho> listItemCarrinho = new ArrayList<>();
    public static ListView mListView;
    public static Context context;
    public static Activity atividade_carrinho;
    Button btn_finalizar_compra;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        session = new Session(this);

        atividade_carrinho = this;

        btn_finalizar_compra = (Button) findViewById(R.id.btn_fin_compra);
        btn_finalizar_compra.setOnClickListener(this);

        mListView = (ListView) findViewById(R.id.listv_carrinho);
        valor_total = (TextView) findViewById(R.id.valor_compra);

        valor_total.setText("Valor Total: " + AtividadeLoja.txt_valor.getText());

        mostra_carrinho = new Mostra_carrinho(this,valor_total);

        mostra_carrinho.request("auth/carrinho", "GET", session.token());


    }

    @Override
    public void onClick(View v) {

        FinalizaCompras finalizaCompras = new FinalizaCompras();
        finalizaCompras.request("auth/compra", "POST", session.token());

    }
}
