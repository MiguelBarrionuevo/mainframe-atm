package bo.edu.ucb.sis213.Vista;

import javax.swing.*;
import bo.edu.ucb.sis213.Controlador.Controller;
import bo.edu.ucb.sis213.Modelo.Transaccion;
import bo.edu.ucb.sis213.Modelo.Usuario;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class WithdrawScreen extends JPanel {
    private JTextField amountField;
    private JButton withdrawButton;
    private JButton backButton;
    private Controller controller;

    public WithdrawScreen(Controller controller) {
        this.controller = controller;
        setLayout(new GridLayout(3, 1));

        amountField = new JTextField();
        withdrawButton = new JButton("Realizar retiro");
        backButton = new JButton("Volver al Menú");

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double amount = Double.parseDouble(amountField.getText());
                    if (amount > 0) {
                        Usuario usuarioActual = controller.getUsuarioActual();
                        boolean retiroExitoso = usuarioActual.retirar(amount); // Llama a la función de retiro en Usuario
        
                        if (retiroExitoso) {
                            // Crear una nueva transacción de retiro y guardarla en la base de datos
                            Transaccion transaccion = new Transaccion(usuarioActual.getId(), "RETIRO", amount, LocalDateTime.now());
                            controller.guardarTransaccionEnBaseDeDatos(controller.getConnection(), transaccion);
        
                            // Actualizar el saldo del usuario actual y guardarlo en la base de datos
                            controller.actualizarSaldoEnBaseDeDatos(controller.getConnection(), usuarioActual);
        
                            // Mostrar mensaje de éxito y regresar al menú principal
                            JOptionPane.showMessageDialog(null, "Retiro realizado exitosamente.");
                            controller.mostrarMainMenuScreen();
                        } else {
                            JOptionPane.showMessageDialog(null, "Saldo insuficiente para realizar el retiro.");
                        }
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

        add(new JLabel("Ingrese la cantidad a retirar: $"));
        add(amountField);
        add(withdrawButton);
        add(backButton);
    }
}
