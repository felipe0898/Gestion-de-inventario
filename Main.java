import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        Inventario inventario = new Inventario();
        inventario.cargarProductosDesdeDB();
        String opcion;

        do {
            opcion = JOptionPane.showInputDialog(menu());
            switch (opcion) {
                case "1":
                    inventario.agregarProducto();
                    break;
                case "2":
                    inventario.ordenarProductos();
                    break;
                case "3":
                    inventario.mostrarProductos();
                    break;
                case "4":
                    inventario.buscarProducto();
                    break;
                case "5":
                    inventario.eliminarProducto();
                    break;
                case "6":
                    inventario.actualizarCantidadProducto();
                    break;
                case "7":
                    inventario.actualizarPrecioProducto();
                    break;
                case "8":
                    inventario.mostrarArbolProductos();
                    break;
                case "9":
                    JOptionPane.showMessageDialog(null, "Saliendo...");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida. Por favor, elija una opción válida.");
            }
        } while (!opcion.equals("9"));
    }

    public static String menu() {
        return "Menú de opciones:\n" +
                "1. Agregar producto\n" +
                "2. Ordenar productos por cantidad (Burbuja)\n" +
                "3. Mostrar productos (Burbuja)\n" +
                "4. Buscar producto por nombre (Búsqueda lineal)\n" +
                "5. Eliminar producto (Búsqueda lineal)\n" +
                "6. Actualizar cantidad de producto (Búsqueda lineal)\n" +
                "7. Actualizar precio del producto (Búsqueda lineal)\n" +
                "8. Mostrar árbol de productos (Árbol binario de búsqueda)\n" +
                "9. Salir\n" +
                "Elija una opción:";
    }
}
