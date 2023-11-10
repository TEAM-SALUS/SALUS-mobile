package com.example.salus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.salus.entidad.Usuario;
import com.example.salus.negocio.IUsuarioNeg;
import com.example.salus.negocioImpl.UsuarioNegImpl;

import java.util.ArrayList;

public class login extends AppCompatActivity implements View.OnClickListener{
    Button btnIngresar;
    Context context;
    EditText loginEmail;
    EditText loginPass;
    IUsuarioNeg iUsuarioNeg;
    String email;
    String pass;

    public static final String DNI_CLIENT="DNICLIENTE";
    public static final String DNI_PROFESIONAL="DNIPROFECIONAL";
    public static final String COD_SERVICIO="CODSERVICIOS";
    public static final String FECHA_TURNO="FECHATURNO";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }
    private void init(){
        context = getApplicationContext();
        btnIngresar = findViewById(R.id.btn_ingresar);
        loginEmail = findViewById(R.id.login_email);
        loginPass = findViewById(R.id.login_pass);
    }
    public void irHome(View view){
        Intent intent = new Intent(this, home.class);
        startActivity(intent);
    }
    @Override
    public void onClick(View view) {
        ingresar();
    }

    public void ingresar(){
        iUsuarioNeg = new UsuarioNegImpl();
        email = loginEmail.getText().toString();
        pass = loginPass.getText().toString();
        Usuario user = new Usuario();
        user = (Usuario) iUsuarioNeg.login(email, pass, context);
        int res = user.getDni();
        Intent intent;
        if (res != -1){
            Toast.makeText(context, "Usuario logueado", Toast.LENGTH_LONG).show();
            SharedPreferences sharedpreferences = getSharedPreferences("shared_login_data",   Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putInt(DNI_CLIENT,user.getDni());
            editor.commit();
            intent = new Intent(this, home.class);
            startActivity(intent);
        }else{
            Toast.makeText(context, "Usuario y contraseña incorrectos", Toast.LENGTH_LONG).show();
        }
    }
}