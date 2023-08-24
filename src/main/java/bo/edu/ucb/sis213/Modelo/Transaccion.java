package bo.edu.ucb.sis213.Modelo;

import java.time.LocalDateTime;

public class Transaccion {
    private int usuarioId;
    private String tipoOperacion;
    private double cantidad;
    private LocalDateTime fecha;

    public Transaccion(int usuarioId, String tipoOperacion, double cantidad, LocalDateTime fecha) {
        this.usuarioId = usuarioId;
        this.tipoOperacion = tipoOperacion;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }


    public int getUsuarioId() {
        return usuarioId;
    }

    public String getTipoOperacion() {
        return tipoOperacion;
    }

    public double getCantidad() {
        return cantidad;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }
}
