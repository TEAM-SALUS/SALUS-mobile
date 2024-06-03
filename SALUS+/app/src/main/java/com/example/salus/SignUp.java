package com.example.salus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.salus.entidad.Condicion;
import com.example.salus.entidad.Usuario;
import com.example.salus.negocio.ICondicionNeg;
import com.example.salus.negocio.IUsuarioNeg;
import com.example.salus.negocioImpl.UsuarioNegImpl;

public class SignUp extends AppCompatActivity implements View.OnClickListener{
    Button btnRegistrarse;
    Context context;
    TextView signDni;
    TextView signEmail;
    TextView signPass;
    Usuario usuario;
    IUsuarioNeg iUsuarioNeg;
    ICondicionNeg conNI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
    }

    private void init(){
        context = getApplicationContext();
        signDni = findViewById(R.id.sign_dni);
        signEmail = findViewById(R.id.sign_email);
        signPass = findViewById(R.id.sign_pass);
        btnRegistrarse = findViewById(R.id.btn_registrarse);
    }
    private Usuario completarDatos(){
        Usuario u = new Usuario();
        Condicion c = new Condicion();
        c.setCodCondicion(1);
        String dni = signDni.getText().toString();
        String email = signEmail.getText().toString();
        String pass = signPass.getText().toString();
        u.setDni(Integer.parseInt(dni));
        //u.setNombre("");
        //u.setApellido("");
        //u.setDireccion("");
        //u.setCiudad("");
        //u.setTelefono("");
        u.setEmail(email);
        //u.setUsuario();
        u.setClave(pass);
        //u.setEstado();
        u.setCondicion(c);
        return u;
    }

    private void guardar(){
        iUsuarioNeg = new UsuarioNegImpl();
        usuario = completarDatos();
        boolean res = iUsuarioNeg.insertar(usuario, context);
        if (res){
            Toast.makeText(context, "Usuario registrado", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, login.class);
            startActivity(intent);
        }else{
            Toast.makeText(context, "Registro falló", Toast.LENGTH_LONG).show();
        }
    }
    public void irLogin(View view){
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        guardar();
    }
}