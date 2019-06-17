package com.example.marcio.lojavirtual.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.marcio.lojavirtual.Api_manipulation.Api_manipulation_authentication;
import com.example.marcio.lojavirtual.R;
import com.example.marcio.lojavirtual.Api_manipulation.Verifica_token;
import com.example.marcio.lojavirtual.model.Session;

import org.json.JSONException;
import org.json.JSONObject;

public class AtividadeLogin extends AppCompatActivity implements View.OnClickListener {


    Button btn_cadastrar;
    Button btn_logar;
    EditText email;
    EditText senha;

    public static Context context;
    public static Activity fa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        verify_auth();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.context = getApplicationContext();
        fa = this;


        email = (EditText) findViewById(R.id.edt_email);
        senha = (EditText) findViewById(R.id.edt_senha);

        btn_logar = (Button) findViewById(R.id.btn_logar);
        btn_logar.setOnClickListener(this);

        btn_cadastrar = (Button) findViewById(R.id.btn_cadastrar);
        btn_cadastrar.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_cadastrar) {

            Intent i = new Intent(AtividadeLogin.this, AtividadeCadastro.class);
            startActivity(i);
        } else if (v.getId() == R.id.btn_logar) {
            Intent intent = new Intent(AtividadeLogin.this, AtividadeLoja.class);
            startActivity(intent);
            /*JSONObject cliJson = new JSONObject();
            Api_manipulation_authentication request = new Api_manipulation_authentication();


            try {

                cliJson.put("senha", senha.getText());
                cliJson.put("email", email.getText());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            request.request("cliente/login", "POST", cliJson);*/
        }
    }

    public void verify_auth() {
        Verifica_token verifica = new Verifica_token();
        Session session = new Session(this);

        verifica.request("auth/carrinho", "GET", session.token());
    }
}
