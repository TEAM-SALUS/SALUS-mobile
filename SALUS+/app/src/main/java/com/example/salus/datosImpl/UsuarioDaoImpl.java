package com.example.salus.datosImpl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.salus.datos.IUsuarioDao;
import com.example.salus.entidad.Usuario;
import com.example.salus.negocio.ICondicionNeg;
import com.example.salus.negocioImpl.CondicionNegImpl;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDaoImpl implements IUsuarioDao {
    private SQLiteDatabase cn;
    private ICondicionNeg conNI;
    @Override
    public List<Usuario> obtenerTodos(Context context) {
        List<Usuario> lista = new ArrayList<Usuario>();
        try {
            cn = new ConexionSQLiteHelper(context).getReadableDatabase();
            Cursor c = cn.rawQuery(
                    "SELECT DNI_Us,Nombre_Us,Apellido_Us,Direccion_Us,Ciudad_Us,Telefono_Us,Email_Us,Usuario_Us,Clave_Us,Descripcion_Us,Estado,CodCondicion_Us FROM Usuario;",
                    null
            );
            if(c.moveToFirst()) {
                conNI = new CondicionNegImpl();
                do {
                    Usuario usuario = new Usuario();
                    //usuario.setDni(c.getInt(0));
                    //usuario.setNombre(c.getString(1));
                    //usuario.setApellido(c.getString(2));
                    //usuario.setDireccion(c.getString(3));
                    //usuario.setCiudad(c.getString(4));
                    //usuario.setTelefono(c.getString(5));
                    //usuario.setEmail(c.getString(6));
                    //usuario.setUsuario(c.getString(7));
                    //usuario.setClave(c.getString(8));
                    //usuario.setDescripcion(c.getString(9));
                    //usuario.setEstado(c.getString(10).equals("1"));
                    Log.d("codigo condicion", c.getInt(11)+"");
                    //usuario.setCondicion(conNI.listarUno(c.getInt(11),context));
                    lista.add(usuario);
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
    public Usuario obtenerUno(int id, Context context) {
        Usuario usuario = new Usuario();
        try {
            cn = new ConexionSQLiteHelper(context).getReadableDatabase();
            Cursor c = cn.rawQuery(
                    "SELECT DNI_Us,Nombre_Us,Apellido_Us,Direccion_Us,Ciudad_Us,Telefono_Us,Email_Us,Usuario_Us,Clave_Us,Descripcion_Us,Estado,CodCondicion_Us " +
                            "FROM Usuario WHERE DNI_Us = " + id + ";",
                    null
            );
            if(c.moveToFirst()) {
                conNI = new CondicionNegImpl();
                do {
                    //usuario.setDni(c.getInt(0));
                    //usuario.setNombre(c.getString(1));
                    //usuario.setApellido(c.getString(2));
                    //usuario.setDireccion(c.getString(3));
                    //usuario.setCiudad(c.getString(4));
                    //usuario.setTelefono(c.getString(5));
                    //usuario.setEmail(c.getString(6));
                    //usuario.setUsuario(c.getString(7));
                    //usuario.setClave(c.getString(8));
                    ///usuario.setDescripcion(c.getString(9));
                    //usuario.setEstado(c.getString(10).equals("1"));
                    //usuario.setCondicion(conNI.listarUno(c.getInt(11),context));
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cn.close();
        }
        return usuario;
    }
    @Override
    public boolean insertar(Usuario usuario, Context context) {
        boolean insert = true;
        try {
            cn = new ConexionSQLiteHelper(context).getWritableDatabase();
            /*
            cn.execSQL("INSERT INTO Usuario (Nombre_Us) " +
                    "VALUES ('" + usuario.getNombre() + "');");
            */
            ContentValues nuevoRegistro = new ContentValues();
            //nuevoRegistro.put("DNI_Us",usuario.getDni());
            //nuevoRegistro.put("Nombre_Us",usuario.getNombre());
            //nuevoRegistro.put("Apellido_Us",usuario.getApellido());
            //nuevoRegistro.put("Direccion_Us",usuario.getDireccion());
            //nuevoRegistro.put("Ciudad_Us",usuario.getCiudad());
            //nuevoRegistro.put("Telefono_Us",usuario.getTelefono());
            //nuevoRegistro.put("Email_Us",usuario.getEmail());
            //nuevoRegistro.put("Usuario_Us",usuario.getUsuario());
            //nuevoRegistro.put("Clave_Us",usuario.getClave());
            //nuevoRegistro.put("Descripcion_Us",usuario.getDescripcion());
            //nuevoRegistro.put("Estado",usuario.getEstado());
            //nuevoRegistro.put("CodCondicion_Us",usuario.getCondicion().getCodCondicion());
            cn.insert("Usuario",null,nuevoRegistro);
        } catch (Exception e) {
            e.printStackTrace();
            insert = false;
        } finally {
            cn.close();
        }
        return insert;
    }
    @Override
    public boolean editar(Usuario usuario, Context context) {
        boolean update = true;
        try {
            cn = new ConexionSQLiteHelper(context).getWritableDatabase();
            /*
            cn.execSQL("UPDATE Usuario SET Nombre_Us = '"+ usuario.getNombre()
                    +"' WHERE DNI_Us = '" + usuario.getDni() + "');");
            */
            ContentValues nuevoRegistro = new ContentValues();
            /*nuevoRegistro.put("Nombre_Us",usuario.getNombre());
            nuevoRegistro.put("Apellido_Us",usuario.getApellido());
            nuevoRegistro.put("Direccion_Us",usuario.getDireccion());
            nuevoRegistro.put("Ciudad_Us",usuario.getCiudad());
            nuevoRegistro.put("Telefono_Us",usuario.getTelefono());
            nuevoRegistro.put("Email_Us",usuario.getEmail());
            nuevoRegistro.put("Usuario_Us",usuario.getUsuario());
            nuevoRegistro.put("Clave_Us",usuario.getClave());
            nuevoRegistro.put("Descripcion_Us",usuario.getDescripcion());
            nuevoRegistro.put("Estado",usuario.getEstado());
            nuevoRegistro.put("CodCondicion_Us",usuario.getCondicion().getCodCondicion());
            cn.update("Usuario",
                    nuevoRegistro,
                    "DNI_Us = " + Integer.toString(usuario.getDni()),
                    null);*/
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
            cn.execSQL("DELETE FROM Usuario WHERE DNI_Us = " + id + ";");
            */
            cn.delete("Usuario","DNI_Us = " + id,null);
        } catch (Exception e) {
            e.printStackTrace();
            delete = false;
        } finally {
            cn.close();
        }
        return delete;
    }
    @Override
    public Usuario login(String email, String pass, Context context){
        Usuario usuario = new Usuario();
        cn = new ConexionSQLiteHelper(context).getReadableDatabase();
        String[] args = new String[] {email, pass};
        Cursor c = cn.rawQuery("SELECT * FROM Usuario WHERE Email_Us=? AND Clave_Us=?", args);
        if (c.getCount()>0){
            conNI = new CondicionNegImpl();
            if(c.moveToFirst()) {

                do {
                    /*usuario.setDni(c.getInt(0));
                    usuario.setNombre(c.getString(1));
                    usuario.setApellido(c.getString(2));
                    usuario.setDireccion(c.getString(3));
                    usuario.setCiudad(c.getString(4));
                    usuario.setTelefono(c.getString(5));
                    usuario.setEmail(c.getString(6));
                    usuario.setUsuario(c.getString(7));
                    usuario.setClave(c.getString(8));
                    usuario.setDescripcion(c.getString(9));
                    usuario.setEstado(c.getString(10).equals("1"));
                    usuario.setCondicion(conNI.listarUno(c.getInt(11),context));*/
                } while (c.moveToNext());
            }
            return usuario;
        }else {
            //usuario.setDni(-1);
            return usuario;
        }
    }
}
