package com.example.marcio.lojavirtual.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.marcio.lojavirtual.Api_manipulation.Api_manipulation_register;
import com.example.marcio.lojavirtual.R;

import org.json.JSONException;
import org.json.JSONObject;

public class AtividadeCadastro extends AppCompatActivity implements View.OnClickListener {

    EditText nome;
    EditText email;
    EditText senha;
    Button btn_cadastrar;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        this.context = getApplicationContext();

        nome = (EditText) findViewById(R.id.edt_nome);
        email = (EditText) findViewById(R.id.edt_email);
        senha = (EditText) findViewById(R.id.edt_senha);
        btn_cadastrar = (Button) findViewById(R.id.btn_cadastrar);
        btn_cadastrar.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        JSONObject cliJson = new JSONObject();
        Api_manipulation_register request = new Api_manipulation_register();


        try {

            cliJson.put("nome", nome.getText());
            cliJson.put("senha", senha.getText());
            cliJson.put("email", email.getText());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.request("cliente", "POST", cliJson);
    }
}
