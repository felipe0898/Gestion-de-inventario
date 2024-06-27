public class Producto implements Comparable<Producto> {
  private String nombre;
  private int cantidad;
  private double precio;

  public Producto(String nombre, int cantidad, double precio) {
    this.nombre = nombre.toLowerCase(); // Convertir nombre a minúsculas
    this.cantidad = cantidad;
    this.precio = precio;
  }

  public String getNombre() {
    return nombre;
  }

  public int getCantidad() {
    return cantidad;
  }

  public double getPrecio() {
    return precio;
  }

  public void setCantidad(int cantidad) {
    this.cantidad = cantidad;
  }

  public void setPrecio(double precio) {
    this.precio = precio;
  }

  @Override
  public String toString() {
    return "Producto{" +
        "nombre='" + nombre + '\'' +
        ", cantidad=" + cantidad +
        ", precio=" + precio +
        '}';
  }

  @Override
  public int compareTo(Producto otro) {
    return this.nombre.compareTo(otro.nombre);
  }
}
