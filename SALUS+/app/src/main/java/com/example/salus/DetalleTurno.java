package com.example.salus;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetalleTurno extends AppCompatActivity {

    private TextView tvTurnoDetalle;
    private Button btnEditar, btnEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_turno);

        // Obtener referencias a los elementos de la interfaz
        tvTurnoDetalle = findViewById(R.id.tvTurnoDetalle);
        btnEditar = findViewById(R.id.btnEditar);
        btnEliminar = findViewById(R.id.btnEliminar);

        // Configurar texto inicial (puedes eliminar esto si se va a configurar dinámicamente)
        tvTurnoDetalle.setText("Detalle del Turno");

        // Configurar listeners para los botones
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarTurno();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarTurno();
            }
        });
    }

    private void editarTurno() {
        // Lógica para editar el turno (por ejemplo, abrir una nueva actividad o fragmento)
        Toast.makeText(this, "Editar turno", Toast.LENGTH_SHORT).show();
    }

    private void eliminarTurno() {
        // Lógica para eliminar el turno (por ejemplo, enviar una solicitud de eliminación)
        Toast.makeText(this, "Eliminar turno", Toast.LENGTH_SHORT).show();
    }
}
