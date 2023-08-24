package bo.edu.ucb.sis213.Vista;

import javax.swing.*;
import bo.edu.ucb.sis213.Controlador.Controller;
import bo.edu.ucb.sis213.Modelo.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class DepositScreen extends JPanel {
    private JTextField amountField;
    private JButton depositButton;
    private JButton backButton;
    private Controller controller;

    public DepositScreen(Controller controller) {
        this.controller = controller;
        setLayout(new GridLayout(4, 1));

        amountField = new JTextField();
        depositButton = new JButton("Realizar depósito");
        backButton = new JButton("Volver al Menú");

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener la cantidad ingresada y convertirla a un valor numérico
                try {
                    double amount = Double.parseDouble(amountField.getText());
                    Usuario usuarioActual = controller.getUsuarioActual();
                    if (amount > 0) {
                        // Crear una nueva transacción de depósito y guardarla en la base de datos
                        Transaccion transaccion = new Transaccion(usuarioActual.getId(), "DEPOSITO", amount, LocalDateTime.now());
                        guardarTransaccionEnBaseDeDatos(transaccion);
                        
                        // Actualizar el saldo del usuario actual y guardarlo en la base de datos
                        usuarioActual.depositar(amount);
                        actualizarSaldoEnBaseDeDatos(usuarioActual);
                        
                        // Mostrar mensaje de éxito y regresar al menú principal
                        JOptionPane.showMessageDialog(null, "Depósito realizado exitosamente.");
                        controller.mostrarMainMenuScreen();
                    } else {
                        JOptionPane.showMessageDialog(null, "Ingrese una cantidad válida.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Ingrese una cantidad válida.");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.mostrarMainMenuScreen(); // Volver al menú principal
            }
        });

        add(new JLabel("Ingrese la cantidad a depositar: $"));
        add(amountField);
        add(depositButton);
        add(backButton);
    }

    private void guardarTransaccionEnBaseDeDatos(Transaccion transaccion) {
        controller.guardarTransaccionEnBaseDeDatos(controller.getConnection(), transaccion); // Llamar al método en el controlador
    }

    private void actualizarSaldoEnBaseDeDatos(Usuario usuario) {
        controller.actualizarSaldoEnBaseDeDatos(controller.getConnection(), usuario); // Llamar al método en el controlador
    }
}
