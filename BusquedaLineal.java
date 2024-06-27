import java.util.ArrayList;

public class BusquedaLineal {
  public static String buscar(ArrayList<Producto> productos, String nombre) {
    for (Producto producto : productos) {
      if (producto.getNombre().equalsIgnoreCase(nombre)) {
        return "Producto encontrado:\nNombre: " + producto.getNombre() +
            "\n  Cantidad: " + producto.getCantidad() +
            "\n  Precio: " + producto.getPrecio();
      }
    }
    return "El producto con el nombre especificado no se encuentra en el inventario.";
  }
}
