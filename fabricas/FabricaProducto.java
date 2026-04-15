package fabricas;

import modelo.Producto;

public class FabricaProducto {
    
    /**
     * Valida y crea un Producto.
     * Retorna el producto si es exitoso, lanza IllegalArgumentException si hay error.
     */
    public static Producto crearProducto(String nombre, String marca, String categoria, 
                                         String preStr, String stockStr) throws IllegalArgumentException {
        if (nombre.trim().isEmpty() || marca.trim().isEmpty() || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException("Todos los campos de texto son obligatorios.");
        }
        
        double precio;
        try {
            precio = Double.parseDouble(preStr);
            if (precio < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El precio debe ser un numero valido positivo.");
        }
        
        int stock;
        try {
            stock = Integer.parseInt(stockStr);
            if (stock < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La cantidad debe ser un numero entero positivo.");
        }
        
        return new Producto(nombre.trim(), marca.trim(), categoria.trim(), precio, stock);
    }
}
