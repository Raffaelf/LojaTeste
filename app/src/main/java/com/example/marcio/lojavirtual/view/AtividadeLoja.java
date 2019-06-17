package com.example.marcio.lojavirtual.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marcio.lojavirtual.Api_manipulation.BuscaCarrinho;
import com.example.marcio.lojavirtual.Api_manipulation.Busca_categorias;
import com.example.marcio.lojavirtual.Api_manipulation.Busca_produtos;
import com.example.marcio.lojavirtual.R;
import com.example.marcio.lojavirtual.model.Categoria;
import com.example.marcio.lojavirtual.model.ItemCarrinho;
import com.example.marcio.lojavirtual.model.Produto;
import com.example.marcio.lojavirtual.model.Session;

import java.util.ArrayList;
import java.util.List;

public class AtividadeLoja extends AppCompatActivity implements View.OnClickListener {

    Spinner spinner;
    public static TextView txt_valor;

    BuscaCarrinho buscaCarrinho;
    Session session;
    public static List<Categoria> listCategoria = new ArrayList<>();
    public static List<Produto> listProdutos = new ArrayList<>();
    public static ArrayAdapter<Categoria> adapter;
    public static List<ItemCarrinho> listItemCarrinho = new ArrayList<>();
    public static ListView mListView;
    public static Context context;
    Custom_alertDialog cdd;
    Button btn_check_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loja);


        session = new Session(this);
        AtividadeLogin.fa.finish();
        if (AtividadeCarrinho.atividade_carrinho != null){
            AtividadeCarrinho.atividade_carrinho.finish();
        }

        btn_check_out = (Button) findViewById(R.id.btn_checkout);
        btn_check_out.setOnClickListener(this);

        this.context = getApplicationContext();

        txt_valor = (TextView) findViewById(R.id.txt_valor);

        buscaCarrinho = new BuscaCarrinho(this, txt_valor);


        mListView = (ListView) findViewById(R.id.listv_produtos);
        spinner = findViewById(R.id.menu_categorias);


        adapter = new ArrayAdapter<Categoria>(this, android.R.layout.simple_spinner_item, listCategoria);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //mListView.setAdapter(null);

                Categoria categoria = (Categoria) parent.getSelectedItem();
                displaySelectedCategory(categoria);

                Busca_produtos busca_produtos = new Busca_produtos();
                busca_produtos.request("categoria/" + categoria.getId(), "GET");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Produto prod = (Produto) parent.getItemAtPosition(position);
                ItemCarrinho item = new ItemCarrinho();
                for (int i = 0; i < listItemCarrinho.size(); i++) {
                    if (listItemCarrinho.get(i).getProduto().getId() == prod.getId()) {
                        item = listItemCarrinho.get(i);
                        break;
                    }
                }
                cdd = new Custom_alertDialog(AtividadeLoja.this, prod, item);
                cdd.show();
            }
        });

        upDate_view();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (spinner.getSelectedItem() == null) {
            Busca_categorias busca_categorias = new Busca_categorias();
            busca_categorias.request("categoriainfo", "GET");
        }
    }

    public void displaySelectedCategory(Categoria categoria) {

        int id = categoria.getId();
        String nome = categoria.getNome();

        String cat_info = "ID: " + id + "\n" + "Nome: " + nome;

        Toast.makeText(this, cat_info, Toast.LENGTH_LONG).show();


    }

    public void upDate_view() {

        buscaCarrinho.request("auth/carrinho", "GET", session.token());
    }


    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, AtividadeCarrinho.class);
        startActivity(i);
    }
}
