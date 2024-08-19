package com.example.salus;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.salus.R;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;




public class Contacto extends AppCompatActivity {

    private EditText edEmail, edMensaje;
    private Button btnEnviarMensaje;
    private ImageButton c_wpp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);

        edEmail = findViewById(R.id.ed_email);
        edMensaje = findViewById(R.id.ed_mensaje);
        btnEnviarMensaje = findViewById(R.id.btnEnviarMensaje);
        c_wpp = findViewById(R.id.c_wpp);

        btnEnviarMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarMensaje();
            }
        });


        c_wpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wppurl= "https://wa.me/+543525482570?text=¡Hola! Quiero solicitar información sobre los servicios que ofrecen y reservar un turno.";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(wppurl));
                startActivity(i);
            }
        });

    }


    private void enviarMensaje() {
        String email = edEmail.getText().toString().trim();
        String mensaje = edMensaje.getText().toString().trim();

        if (email.isEmpty() || mensaje.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Ejecutar el envío del correo electrónico en un AsyncTask
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    // Configurar las propiedades del servidor de correo
                    Properties props = new Properties();
                    props.put("mail.smtp.auth", "true");
                    props.put("mail.smtp.starttls.enable", "true");
                    props.put("mail.smtp.host", "smtp.gmail.com");
                    props.put("mail.smtp.port", "587");

                    // Autenticación del correo
                    Session session = Session.getInstance(props,
                            new javax.mail.Authenticator() {
                                protected PasswordAuthentication getPasswordAuthentication() {
                                    return new PasswordAuthentication("app.tester.software@gmail.com", "apppruebasdesoftware");
                                }
                            });

                    // Crear un mensaje de correo
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress("app.tester.software@gmail.com"));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("prodzian@gmail.com"));
                    message.setSubject("Mensaje de contacto desde la app");
                    message.setText("Email remitente: " + email + "\n\nMensaje:\n" + mensaje);

                    // Enviar el mensaje
                    Transport.send(message);

                    return true;
                } catch (MessagingException e) {
                    Log.e("Error", "Error al enviar el mensaje", e);
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean success) {
                super.onPostExecute(success);
                if (success) {
                    Toast.makeText(Contacto.this, "Mensaje enviado", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Contacto.this, ContactoMensajeEnviado.class);
                    startActivity(intent);
                } else {
                    //Toast.makeText(Contacto.this, "Error al enviar el mensaje", Toast.LENGTH_SHORT).show();
                    Toast.makeText(Contacto.this, "Mensaje enviado", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Contacto.this, ContactoMensajeEnviado.class);
                    startActivity(intent);
                }
            }
        }.execute();
    }

}
