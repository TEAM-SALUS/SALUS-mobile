package com.example.salus.datosImpl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.salus.datos.IServicioXProfesionalDao;
import com.example.salus.entidad.ServicioXProfesional;
import com.example.salus.negocio.IServicioNeg;
import com.example.salus.negocio.IUsuarioNeg;
import com.example.salus.negocioImpl.ServicioNegImpl;
import com.example.salus.negocioImpl.UsuarioNegImpl;

import java.util.ArrayList;
import java.util.List;

public class ServicioXProfesionalDaoImpl implements IServicioXProfesionalDao {
    private SQLiteDatabase cn;
    private IServicioNeg serNI;
    private IUsuarioNeg usuNI;
    @Override
    public List<ServicioXProfesional> obtenerTodos(Context context) {
        List<ServicioXProfesional> lista = new ArrayList<ServicioXProfesional>();
        try {
            cn = new ConexionSQLiteHelper(context).getReadableDatabase();
            Cursor c = cn.rawQuery("SELECT CodServicio_SerXPro, DNI_SerXPro FROM ServicioXProfesional",null);
            if(c.moveToFirst()) {
                serNI = new ServicioNegImpl();
                usuNI = new UsuarioNegImpl();
                do {
                    ServicioXProfesional serXPro = new ServicioXProfesional();
                    serXPro.setServicio_SXP(serNI.listarUno(c.getInt(0),context));
                    serXPro.setUsuario_SXP(usuNI.listarUno(c.getInt(1),context));
                    lista.add(serXPro);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cn.close();
        }
        return lista;
    }
    @Override
    public ServicioXProfesional obtenerUno(int idS, int idP, Context context) {
        ServicioXProfesional serXpro = new ServicioXProfesional();
        try {
            cn = new ConexionSQLiteHelper(context).getReadableDatabase();
            Cursor c = cn.rawQuery("SELECT CodServicio_SerXPro, DNI_SerXPro " +
                    "FROM ServicioXProfesional WHERE CodServicio_SerXPro = " + idS +
                    " AND DNI_SerXPro = " + idP + ";",null);
            if(c.moveToFirst()) {
                serNI = new ServicioNegImpl();
                usuNI = new UsuarioNegImpl();
                do {
                    serXpro.setServicio_SXP(serNI.listarUno(c.getInt(0),context));
                    serXpro.setUsuario_SXP(usuNI.listarUno(c.getInt(1),context));
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cn.close();
        }
        return serXpro;
    }
    @Override
    public boolean insertar(ServicioXProfesional servicioXProfesional, Context context) {
        boolean insert = true;
        try {
            cn = new ConexionSQLiteHelper(context).getWritableDatabase();
            /*
            cn.execSQL("INSERT INTO ServicioXProfesional (CodServicio_SerXPro) " +
                    "VALUES ('" + servicioXProfesional.getServicio_SXP().getCodServicio() + "');");
            */
            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put("CodServicio_SerXPro",servicioXProfesional.getServicio_SXP().getCodServicio());
            //nuevoRegistro.put("DNI_SerXPro",servicioXProfesional.getUsuario_SXP().getDni());
            cn.insert("ServicioXProfesional",null,nuevoRegistro);
        } catch (Exception e) {
            e.printStackTrace();
            insert = false;
        } finally {
            cn.close();
        }
        return insert;
    }
    @Override
    public boolean editar(ServicioXProfesional servicioXProfesional, Context context) {
        boolean update = true;
        try {
            cn = new ConexionSQLiteHelper(context).getWritableDatabase();
            /*
            cn.execSQL("UPDATE ServicioXProfesional SET ? = '"+ servicioXProfesional.get?()
                    +"' WHERE CodServicio_SerXPro = '" + servicioXProfesional.getServicio_SXP() + "');");
            */
            /*
            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put("?",servicioXProfesional.get?());
            cn.update("ServicioXProfesional",nuevoRegistro,"CodServicio_SerXPro = " +
                    Integer.toString(servicioXProfesional.getServicio_SXP().getCodServicio()),null);
             */
            Toast.makeText(context,"Esta tabla no tiene campos editables",Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            update = false;
        } finally {
            cn.close();
        }
        return update;
    }
    @Override
    public boolean borrar(int idS, int idP, Context context) {
        boolean delete = true;
        try {
            cn = new ConexionSQLiteHelper(context).getWritableDatabase();
            /*
            cn.execSQL("DELETE FROM ServicioXProfesional " +
                    "WHERE CodServicio_SerXPro = " + idS +
                    " AND DNI_SerXPro = " + idP + ";");
            */
            cn.delete("ServicioXProfesional",
                    "CodServicio_SerXPro = " + idS +
                    " AND DNI_SerXPro = " + idP + ";",
                    null);
        } catch (Exception e) {
            e.printStackTrace();
            delete = false;
        } finally {
            cn.close();
        }
        return delete;
    }
}
