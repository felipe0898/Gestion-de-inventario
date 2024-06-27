import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
  private static final String URL = "jdbc:mysql://localhost:3306/inventariodb";
  private static final String USER = "root";
  private static final String PASSWORD = "123";

  private Connection connection;

  public DatabaseManager() throws SQLException {
    connection = DriverManager.getConnection(URL, USER, PASSWORD);
  }

  public void close() throws SQLException {
    if (connection != null && !connection.isClosed()) {
      connection.close();
    }
  }

  public List<Producto> obtenerTodosLosProductos() throws SQLException {
    List<Producto> productos = new ArrayList<>();
    String query = "SELECT * FROM Productos";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        String nombre = rs.getString("nombre");
        int cantidad = rs.getInt("cantidad");
        double precio = rs.getDouble("precio");
        Producto producto = new Producto(nombre, cantidad, precio);
        productos.add(producto);
      }
    }
    return productos;
  }

  public void agregarProducto(Producto producto) throws SQLException {
    String query = "INSERT INTO Productos (nombre, cantidad, precio) VALUES (?, ?, ?)";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
      stmt.setString(1, producto.getNombre());
      stmt.setInt(2, producto.getCantidad());
      stmt.setDouble(3, producto.getPrecio());
      stmt.executeUpdate();
    }
  }

  public Producto buscarProducto(String nombre) throws SQLException {
    String query = "SELECT * FROM Productos WHERE nombre = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
      stmt.setString(1, nombre);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        return new Producto(rs.getString("nombre"), rs.getInt("cantidad"), rs.getDouble("precio"));
      } else {
        return null;
      }
    }
  }

  public void actualizarCantidadProducto(String nombre, int nuevaCantidad) throws SQLException {
    String query = "UPDATE Productos SET cantidad = ? WHERE nombre = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
      stmt.setInt(1, nuevaCantidad);
      stmt.setString(2, nombre);
      stmt.executeUpdate();
    }
  }

  public void actualizarPrecioProducto(String nombre, double nuevoPrecio) throws SQLException {
    String query = "UPDATE Productos SET precio = ? WHERE nombre = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
      stmt.setDouble(1, nuevoPrecio);
      stmt.setString(2, nombre);
      stmt.executeUpdate();
    }
  }

  public void eliminarProducto(String nombre) throws SQLException {
    String query = "DELETE FROM Productos WHERE nombre = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
      stmt.setString(1, nombre);
      stmt.executeUpdate();
    }
  }
}
