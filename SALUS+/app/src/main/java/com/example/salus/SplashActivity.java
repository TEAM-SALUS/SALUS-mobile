package com.example.salus;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.salus.entidad.Categoria;
import com.example.salus.entidad.Condicion;
import com.example.salus.entidad.Servicio;
import com.example.salus.entidad.ServicioXProfesional;
import com.example.salus.entidad.Turno;
import com.example.salus.entidad.Usuario;
import com.example.salus.negocio.ICategoriaNeg;
import com.example.salus.negocio.ICondicionNeg;
import com.example.salus.negocio.IServicioNeg;
import com.example.salus.negocio.IServicioXProfesionalNeg;
import com.example.salus.negocio.ITurnoNeg;
import com.example.salus.negocio.IUsuarioNeg;
import com.example.salus.negocioImpl.CategoriaNegImpl;
import com.example.salus.negocioImpl.CondicionNegImpl;
import com.example.salus.negocioImpl.ServicioNegImpl;
import com.example.salus.negocioImpl.ServicioXProfesionalNegImpl;
import com.example.salus.negocioImpl.TurnoNegImpl;
import com.example.salus.negocioImpl.UsuarioNegImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {
    private static final String VIDEO_SAMPLE = "https://ia601604.us.archive.org/27/items/electricsheep-flock-248-7500-8/00248%3D09858%3D09858%3D09858.mp4";
    private static final String PLAYBACK_TIME = "play_time";
    private int mCurrentPosition = 0;
    private VideoView videoView;
    private Context context;
    private ICategoriaNeg catNI;
    private ICondicionNeg conNI;
    private IServicioNeg serNI;
    private IUsuarioNeg usuNI;
    private IServicioXProfesionalNeg serXProNI;
    private ITurnoNeg turNI;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        init();
        initVideo(savedInstanceState);
        //cargarBase();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void cargarBase() {
        context = getApplicationContext();
        catNI = new CategoriaNegImpl();
        conNI = new CondicionNegImpl();
        serNI = new ServicioNegImpl();
        usuNI = new UsuarioNegImpl();
        serXProNI = new ServicioXProfesionalNegImpl();
        turNI = new TurnoNegImpl();
        cargarCategoria();
        cargarCondicion();
        cargarServicio();
        cargarUsuario();
        cargarServicioXProfesional();
        caragrTurno();
    }
    private void cargarCategoria() {
        List<Categoria> lista = (ArrayList<Categoria>)catNI.listarTodos(context);
        if(lista.size() == 0) {
            catNI.insertar(new Categoria(1,"Odontologia"), context);
            catNI.insertar(new Categoria(2,"Neurologia"), context);
            catNI.insertar(new Categoria(3,"Psiquiatria"), context);
        }
    }
    private void cargarCondicion() {
        List<Condicion> lista = (ArrayList<Condicion>)conNI.listarTodos(context);
        if(lista.size() == 0) {
            conNI.insertar(new Condicion(1,"Paciente"), context);
            conNI.insertar(new Condicion(2,"Profesional"), context);
            conNI.insertar(new Condicion(3,"Administrador"), context);
        }
    }
    private void cargarServicio() {
        List<Servicio> lista = (ArrayList<Servicio>)serNI.listarTodos(context);
        if(lista.size() == 0) {
            serNI.insertar(new Servicio(1, "Tratamiento Conducto", "Se mata nervios de dientes", true,catNI.listarUno(1, context)), context);
            serNI.insertar(new Servicio(2, "Examen Electrocenografia", "Se revisa el cerebro", true,catNI.listarUno(2, context)), context);
            serNI.insertar(new Servicio(3, "Entrevista Psiquiatrica", "Se revisa salud mental", true,catNI.listarUno(3, context)), context);
        }
    }
    private void cargarUsuario() {
        List<Usuario> lista = (ArrayList<Usuario>)usuNI.listarTodos(context);
        if(lista.size() == 0) {
            //usuNI.insertar(new Usuario(11111111, "Nombre Uno", "Apellido Uno", "Direccion 1", "Ciudad 1", "Telefono 1","Email 1","Usuario 1","Clave 1","Descripcion 1",true,conNI.listarUno(1, context)), context);
           // usuNI.insertar(new Usuario(22222222, "Nombre Dos", "Apellido Dos", "Direccion 2", "Ciudad 2", "Telefono 2","Email 2", "Usuario 2", "Clave 2","Descripcion 2",true,conNI.listarUno(2, context)), context);
            //usuNI.insertar(new Usuario(0, "Nombre Admin", "Apellido Admin", "Direccion Admin", "Ciudad Admin", "Telefono Admin","Email admin","Usuario admin", "Clave 3","Descripcion 3",true,conNI.listarUno(3, context)), context);
        }
    }
    public void cargarServicioXProfesional() {
        List<ServicioXProfesional> lista = (ArrayList<ServicioXProfesional>)serXProNI.listarTodos(context);
        if(lista.size() == 0) {
            serXProNI.insertar(new ServicioXProfesional(serNI.listarUno(1,context),usuNI.listarUno(22222222,context)), context);
            serXProNI.insertar(new ServicioXProfesional(serNI.listarUno(2,context),usuNI.listarUno(22222222,context)), context);
            serXProNI.insertar(new ServicioXProfesional(serNI.listarUno(3,context),usuNI.listarUno(22222222,context)), context);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void caragrTurno() {
        List<Turno> lista = (ArrayList<Turno>)turNI.listarTodos(context);
        if(lista.size() == 0) {
           // turNI.insertar(new Turno(1, LocalDateTime.now(),LocalDateTime.of(2023,11,28,15,30,30), true, usuNI.listarUno(11111111,context), serXProNI.listarUno(1,22222222,context)), context);
           // turNI.insertar(new Turno(2,LocalDateTime.now(),LocalDateTime.of(2024,11,28,15,30,30), true, usuNI.listarUno(11111111,context), serXProNI.listarUno(2,22222222,context)), context);
           // turNI.insertar(new Turno(3,LocalDateTime.now(),LocalDateTime.of(2025,11,28,15,30,30), true, usuNI.listarUno(11111111,context), serXProNI.listarUno(3,22222222,context)), context);
        }
    }
    private void initVideo(Bundle savedInstanceState) {
        videoView = findViewById(R.id.videoView);
        MediaController controller = new MediaController(this);
        videoView.setMediaController(controller);
        controller.setMediaPlayer(videoView);
        if(savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
        }
    }
    private Uri getMedia(String mediaName) {
        return Uri.parse(mediaName);
        //return Uri.parse("https://archive.org/download/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4");
        // noo return Uri.parse("https://ia800201.us.archive.org/22/items/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet.ogv");
        // no return Uri.parse("https://archive.org/details/laksjnsmn/AA-Makup.mp4");
        //  no return Uri.parse("https://archive.org/details/electricsheep-flock-248-7500-8/00248%3D07638%3D07638%3D07638.avi");
        //  return Uri.parse("https://ia601604.us.archive.org/27/items/electricsheep-flock-248-7500-8/00248%3D07638%3D07638%3D07638.mp4");
        // no return Uri.parse("https://archive.org/details/2000-Matches/China.vs.Iraq/China_Iraq_F.wmv");
        //  return Uri.parse("https://ia804601.us.archive.org/29/items/national-geographic-the-grizzlies-1987/TheGrizzlies.mp4");
        // no   return Uri.parse("https://pixabay.com/es/videos/carros-rotonda-rond%C3%B3-flores-168811/");
        // return Uri.parse("https://pixabay.com/es/videos/p%C3%A1jaro-loro-naturaleza-animal-46026/");
    }
    private void initializePlayer(){
        //Uri videoUri = getMedia(VIDEO_SAMPLE);
        Uri videoUri = getMedia(VIDEO_SAMPLE);
        videoView.setVideoURI(videoUri);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                if(mCurrentPosition>0){
                    videoView.seekTo(mCurrentPosition);
                } else {
                    //Saltar a 1 muestra el primer cuadro del video
                    videoView.seekTo(1);
                }
                videoView.start();
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            @Override
            public void onCompletion(MediaPlayer mediaPlayer){
                Toast.makeText(SplashActivity.this, "Playback completed", Toast.LENGTH_SHORT).show();
                videoView.seekTo(1);
            }
        });
    }
    private void releasePlayer(){
        videoView.stopPlayback();
    }
    @Override
    protected void onStart(){
        super.onStart();
        initializePlayer();
    }
    @Override
    protected void onStop(){
        super.onStop();
        releasePlayer();
    }
    @Override
    protected void onPause(){
        super.onPause();
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.N){
            videoView.pause();
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt(PLAYBACK_TIME, videoView.getCurrentPosition());
    }

    private void init() {
        //int tiempo = 20000;
        int tiempo = 2000;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, tiempo);
    }
}