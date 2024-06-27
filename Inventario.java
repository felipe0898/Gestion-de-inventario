import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeSet;

public class Inventario {
  private DatabaseManager dbManager;
  private ArrayList<Producto> productos = new ArrayList<>();
  private TreeSet<Producto> arbolProductos = new TreeSet<>();

  public Inventario() {
    try {
      dbManager = new DatabaseManager();
    } catch (SQLException e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos.");
    }
  }

  public synchronized void cargarProductosDesdeDB() {
    try {
      productos.clear();
      DatabaseManager dbManager = new DatabaseManager();
      productos.addAll(dbManager.obtenerTodosLosProductos());
      arbolProductos.addAll(dbManager.obtenerTodosLosProductos());
      dbManager.close();
    } catch (SQLException e) {
      JOptionPane.showMessageDialog(null, "Error al cargar productos desde la base de datos: " + e.getMessage());
    }
  }

  public synchronized void agregarProducto() {
    String nombre = JOptionPane.showInputDialog("Digite el nombre del producto:");
    int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Digite la cantidad de productos:"));
    double precio = Double.parseDouble(JOptionPane.showInputDialog("Digite el precio del producto:"));
    Producto nuevoProducto = new Producto(nombre, cantidad, precio);

    try {
      dbManager.agregarProducto(nuevoProducto);
      productos.add(nuevoProducto);
      arbolProductos.add(nuevoProducto);
      JOptionPane.showMessageDialog(null, "Producto agregado exitosamente.");
    } catch (SQLException e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(null, "Error al agregar el producto.");
    }
  }

  public synchronized void ordenarProductos() {
    Burbuja.ordenar(productos);
    JOptionPane.showMessageDialog(null, "Productos ordenados por cantidad (Burbuja).");
  }

  public synchronized void mostrarProductos() {
    StringBuilder sb = new StringBuilder();
    sb.append("Inventario (Burbuja):\n\n");
    for (int i = productos.size() - 1; i >= 0; i--) {
      Producto producto = productos.get(i);
      sb.append("\nNombre: ").append(producto.getNombre())
          .append(", \nCantidad: ").append(producto.getCantidad())
          .append(", \nPrecio: ").append(producto.getPrecio())
          .append("\n");
    }
    JOptionPane.showMessageDialog(null, sb.toString());
  }

  public synchronized void buscarProducto() {
    String nombreBuscado = JOptionPane.showInputDialog("Digite el nombre del producto a buscar:");
    try {
      Producto producto = dbManager.buscarProducto(nombreBuscado);
      if (producto != null) {
        JOptionPane.showMessageDialog(null, "Producto encontrado:\nNombre: " + producto.getNombre() + "\nCantidad: "
            + producto.getCantidad() + "\nPrecio: " + producto.getPrecio());
      } else {
        JOptionPane.showMessageDialog(null, "El producto con el nombre especificado no se encuentra en el inventario.");
      }
    } catch (SQLException e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(null, "Error al buscar el producto.");
    }
  }

  public synchronized void actualizarCantidadProducto() {
    String nombreBuscado = JOptionPane.showInputDialog("Digite el nombre del producto a actualizar:");
    int nuevaCantidad = Integer.parseInt(JOptionPane.showInputDialog("Digite la nueva cantidad del producto:"));

    try {
      dbManager.actualizarCantidadProducto(nombreBuscado, nuevaCantidad);
      for (Producto producto : productos) {
        if (producto.getNombre().equalsIgnoreCase(nombreBuscado)) {
          producto.setCantidad(nuevaCantidad);
          break;
        }
      }
      JOptionPane.showMessageDialog(null, "Cantidad del producto actualizada exitosamente.");
    } catch (SQLException e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(null, "Error al actualizar la cantidad del producto.");
    }
  }

  public synchronized void actualizarPrecioProducto() {
    String nombreBuscado = JOptionPane.showInputDialog("Digite el nombre del producto a actualizar:");
    double nuevoPrecio = Double.parseDouble(JOptionPane.showInputDialog("Digite el nuevo precio del producto:"));

    try {
      dbManager.actualizarPrecioProducto(nombreBuscado, nuevoPrecio);
      for (Producto producto : productos) {
        if (producto.getNombre().equalsIgnoreCase(nombreBuscado)) {
          producto.setPrecio(nuevoPrecio);
          break;
        }
      }
      JOptionPane.showMessageDialog(null, "Precio del producto actualizado exitosamente.");
    } catch (SQLException e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(null, "Error al actualizar el precio del producto.");
    }
  }

  public synchronized void eliminarProducto() {
    String nombreBuscado = JOptionPane.showInputDialog("Digite el nombre del producto a eliminar:");

    try {
      dbManager.eliminarProducto(nombreBuscado);
      productos.removeIf(producto -> producto.getNombre().equalsIgnoreCase(nombreBuscado));
      arbolProductos.removeIf(producto -> producto.getNombre().equalsIgnoreCase(nombreBuscado));
      JOptionPane.showMessageDialog(null, "Producto eliminado exitosamente.");
    } catch (SQLException e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(null, "Error al eliminar el producto.");
    }
  }

  public synchronized void mostrarArbolProductos() {
    StringBuilder sb = new StringBuilder();
    sb.append("Árbol de productos (inorden):\n");
    for (Producto producto : arbolProductos) {
      // Buscar el producto correspondiente en la lista productos
      for (Producto prod : productos) {
        if (prod.getNombre().equalsIgnoreCase(producto.getNombre())) {
          // Actualizar la cantidad y el precio en el TreeSet
          producto.setCantidad(prod.getCantidad());
          producto.setPrecio(prod.getPrecio());
          break;
        }
      }
      sb.append("\nNombre: ").append(producto.getNombre())
          .append(", \nCantidad: ").append(producto.getCantidad())
          .append(", \nPrecio: ").append(producto.getPrecio())
          .append("\n");
    }
    JOptionPane.showMessageDialog(null, sb.toString());
  }

  public void close() {
    try {
      dbManager.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
