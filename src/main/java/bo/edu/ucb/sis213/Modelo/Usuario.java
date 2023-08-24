package bo.edu.ucb.sis213.Modelo;

public class Usuario {
    private int id;
    private String nombre;
    private int pin;
    private double saldo;
    private String alias;

    public Usuario(int id, String nombre, int pin, double saldo, String alias) {
        this.id = id;
        this.nombre = nombre;
        this.pin = pin;
        this.saldo = saldo;
        this.alias = alias;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPin() {
        return pin;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getAlias() {
        return alias;
    }

    // Métodos setters para actualizar los valores si es necesario
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    public void depositar(double amount) {
        if (amount > 0) {
            saldo += amount;
        }
    }

    public boolean retirar(double monto) {
        if (monto > 0 && saldo >= monto) {
            saldo -= monto;
            return true; // Retiro exitoso
        } else {
            return false; // Saldo insuficiente para el retiro
        }
    }

    public void setPin(int pin) {
        this.pin = pin;
    }
}
