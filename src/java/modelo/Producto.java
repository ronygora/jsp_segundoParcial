/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import javax.swing.table.DefaultTableModel;

        
/**
 *
 * @author Andres
 */
public class Producto extends parcial{
    private String producto;
    private int id_marca;
    Conexion cn;

    public Producto() {
    }

    public Producto(String producto, int id_marca, int id, String descripcion, double precio_costo, double precio_venta, int existencia) {
        super(id, descripcion, precio_costo, precio_venta, existencia);
        this.producto = producto;
        this.id_marca = id_marca;
    }
    
    
    

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getId_marca() {
        return id_marca;
    }

    public void setId_marca(int id_marca) {
        this.id_marca = id_marca;
    }
    
    
     public DefaultTableModel leer(){
 DefaultTableModel tabla = new DefaultTableModel();
 try{
     cn = new Conexion();
     cn.abrir_conexion();
      String query = "SELECT p.idProducto as id,p.producto,p.descripcion,p.precio_costo,p.precio_venta,p.existencia,m.marca,m.idMarca FROM productos as p inner join marcas as m on p.idMarca = m.idMarca;";
      ResultSet consulta = cn.conexionBD.createStatement().executeQuery(query);
      String encabezado[] = {"id","producto","descripcion","precio_costo","precio_venta","existencia","marca","idMarca"};
      tabla.setColumnIdentifiers(encabezado);
      String datos[] = new String[8];
      while (consulta.next()){
          datos[1] = consulta.getString("id");
          datos[2] = consulta.getString("producto");
          datos[3] = consulta.getString("descripcion");
          datos[4] = consulta.getString("marca");
          datos[5] = consulta.getString("precio_costo");
          datos[6] = consulta.getString("precio_venta");
          
          datos[7] = consulta.getString("existencia");
          
          tabla.addRow(datos);
      
      }
      
     cn.cerrar_conexion();
 }catch(SQLException ex){
     System.out.println(ex.getMessage());
 }
 return tabla;
 }
 
    @Override
    public int agregar(){
        int retorno =0;
        try{
            PreparedStatement parametro;
            cn = new Conexion();
            String query = "INSERT INTO productos (producto,descripcion,precio_costo,precio_venta,existencia,idMarca) VALUES (?,?,?,?,?,?);";
            cn.abrir_conexion();
            parametro = (PreparedStatement)cn.conexionBD.prepareStatement(query);
            parametro.setString(1,getProducto());
            parametro.setString(2,getDescripcion());
            parametro.setDouble(3, getPrecio_costo());
            parametro.setDouble(4, getPrecio_venta());
            parametro.setInt(5, getExistencia());
            parametro.setInt(6,getId_marca());
            retorno = parametro.executeUpdate();
            cn.cerrar_conexion();
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    return retorno;
    }
    
    @Override
    public int modificar (){
        int retorno =0;
        try{
            PreparedStatement parametro;
            cn = new Conexion();
            String query = "UPDATE productos SET producto = ?,descripcion= ?,precio_costo= ?,precio_venta= ?,existencia= ?,idMarca= ? WHERE idProducto = ?;";
            cn.abrir_conexion();
            parametro = (PreparedStatement)cn.conexionBD.prepareStatement(query);
            parametro.setString(1,getProducto());
            parametro.setString(2,getDescripcion());
            parametro.setDouble(3,getPrecio_costo());
            parametro.setDouble(4,getPrecio_venta());
            parametro.setInt(5,getExistencia());
            parametro.setInt(6,getId_marca());
            parametro.setInt(7,getId());
            retorno = parametro.executeUpdate();
            cn.cerrar_conexion();
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    return retorno;
    }
    
    @Override
    public int eliminar (){
        int retorno =0;
        try{
            PreparedStatement parametro;
            cn = new Conexion();
            String query = "DELETE FROM productos WHERE idProducto = ?;";
            cn.abrir_conexion();
            parametro = (PreparedStatement)cn.conexionBD.prepareStatement(query);
            parametro.setInt(1, getId());
            retorno = parametro.executeUpdate();
            cn.cerrar_conexion();
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    return retorno;
    }
    
}