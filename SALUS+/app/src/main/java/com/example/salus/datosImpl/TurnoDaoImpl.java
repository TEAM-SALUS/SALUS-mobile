package com.example.salus.datosImpl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.salus.datos.ITurnoDao;
import com.example.salus.entidad.Turno;
import com.example.salus.negocio.IServicioXProfesionalNeg;
import com.example.salus.negocio.IUsuarioNeg;
import com.example.salus.negocioImpl.ServicioXProfesionalNegImpl;
import com.example.salus.negocioImpl.UsuarioNegImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TurnoDaoImpl implements ITurnoDao {
    private SQLiteDatabase cn;
    private IUsuarioNeg usuNI;
    private IServicioXProfesionalNeg serXProNI;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public List<Turno> obtenerTodos(Context context) {
        List<Turno> lista = new ArrayList<Turno>();
        try {
            cn = new ConexionSQLiteHelper(context).getReadableDatabase();
            Cursor c = cn.rawQuery("SELECT CodTurno_Tur, Fecha_Tur, FechaAsignacion_Tur, Estado, DNI_CliTur, CodServicio_Tur, DNI_ProTur FROM Turno;",null);
            if(c.moveToFirst()) {
                usuNI = new UsuarioNegImpl();
                serXProNI = new ServicioXProfesionalNegImpl();
                do {
                    Turno tur = new Turno();
                    /*tur.setCodTurno(c.getInt(0));
                    tur.setFecha(LocalDateTime.parse(c.getString(1),DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
                    tur.setFechaAsignacion(LocalDateTime.parse(c.getString(2),DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
                    tur.setEstado(c.getString(3).equals("1"));
                    tur.setUsuarioCli(usuNI.listarUno(c.getInt(4),context));
                    tur.setServicioXProfesional(
                            serXProNI.listarUno(c.getInt(5), c.getInt(6),
                            context)
                    );*/
                    lista.add(tur);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cn.close();
        }
        return lista;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public Turno obtenerUno(int id, Context context) {
        Turno tur = new Turno();
        try {
            cn = new ConexionSQLiteHelper(context).getReadableDatabase();
            Cursor c = cn.rawQuery("SELECT CodTurno_Tur, Fecha_Tur, FechaAsignacion_Tur, Estado, DNI_CliTur, CodServicio_Tur, DNI_ProTur " +
                    " FROM Turno WHERE CodTurno_Tur = " + id + ";",null);
            if(c.moveToFirst()) {
                usuNI = new UsuarioNegImpl();
                serXProNI = new ServicioXProfesionalNegImpl();
                do {
                    /*tur.setCodTurno(c.getInt(0));
                    tur.setFecha(LocalDateTime.parse(c.getString(1),DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
                    tur.setFechaAsignacion(LocalDateTime.parse(c.getString(2),DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
                    tur.setEstado(c.getString(3).equals("1"));
                    tur.setUsuarioCli(usuNI.listarUno(c.getInt(4),context));
                    tur.setServicioXProfesional(
                            serXProNI.listarUno(c.getInt(5), c.getInt(6),
                                    context)
                    );*/
                } while(c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cn.close();
        }
        return tur;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean insertar(Turno turno, Context context) {
        boolean insert = true;
        try {
            cn = new ConexionSQLiteHelper(context).getWritableDatabase();
            /*
            cn.execSQL("INSERT INTO Turno (Fecha_Tur) " +
                    "VALUES ('" + turno.getFecha() + "');");
            */
            ContentValues nuevoRegistro = new ContentValues();
            //nuevoRegistro.put("CodTurno_Tur",turno.getCodTurno());
            //nuevoRegistro.put("Fecha_Tur",turno.getFechaH().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")).toString());
            //nuevoRegistro.put("FechaAsignacion_Tur",turno.getFechaAsignacion().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")).toString());
            //nuevoRegistro.put("Estado",turno.getEstado());
            //nuevoRegistro.put("DNI_CliTur",turno.getUsuarioCli().getDni());
            //nuevoRegistro.put("CodServicio_Tur",turno.getServicioXProfesional().getServicio_SXP().getCodServicio());
           // nuevoRegistro.put("DNI_ProTur",turno.getServicioXProfesional().getUsuario_SXP().getDni());
            cn.insert("Turno",null,nuevoRegistro);
        } catch (Exception e) {
            e.printStackTrace();
            insert = false;
        } finally {
            cn.close();
        }
        return insert;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean editar(Turno turno, Context context) {
        boolean update = true;
        try {
            cn = new ConexionSQLiteHelper(context).getWritableDatabase();
            /*
            cn.execSQL("UPDATE Turno SET Fecha_Tur = '"+ turno.getFecha()
                    +"' WHERE CodTurno_Tur = '" + turno.getCodTurno() + "');");
            */
            ContentValues nuevoRegistro = new ContentValues();
            //nuevoRegistro.put("Fecha_Tur",turno.getFechaH().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")).toString());
            //nuevoRegistro.put("FechaAsignacion_Tur",turno.getFechaAsignacion().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")).toString());
            //nuevoRegistro.put("Estado",turno.getEstado());
            //nuevoRegistro.put("DNI_CliTur",turno.getUsuarioCli().getDni());
            //nuevoRegistro.put("CodServicio_Tur",turno.getServicioXProfesional().getServicio_SXP().getCodServicio());
            //nuevoRegistro.put("DNI_ProTur",turno.getServicioXProfesional().getUsuario_SXP().getDni());
            /*cn.update("Turno",nuevoRegistro,"CodTurno_Tur = " +
                    Integer.toString(turno.getCodTurno()),null);*/
        } catch (Exception e) {
            e.printStackTrace();
            update = false;
        } finally {
            cn.close();
        }
        return update;
    }

    @Override
    public boolean borrar(int id, Context context) {
        boolean delete = true;
        try {
            cn = new ConexionSQLiteHelper(context).getWritableDatabase();
            /*
            cn.execSQL("DELETE FROM Turno WHERE CodTurno_Tur = " + id + ";");
            */
            cn.delete("Turno","CodTurno_Tur = " + id,null);
        } catch (Exception e) {
            e.printStackTrace();
            delete = false;
        } finally {
            cn.close();
        }
        return delete;
    }
}
