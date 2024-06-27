import java.util.ArrayList;

public class Burbuja {
    public static void ordenar(ArrayList<Producto> productos) {
        int nElementos = productos.size();
        Producto aux;

        for (int i = 0; i < nElementos - 1; i++) {
            for (int j = 0; j < nElementos - 1 - i; j++) {
                if (productos.get(j).getCantidad() > productos.get(j + 1).getCantidad()) {
                    aux = productos.get(j);
                    productos.set(j, productos.get(j + 1));
                    productos.set(j + 1, aux);
                }
            }
        }
    }
}
