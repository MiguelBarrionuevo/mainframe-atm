package bo.edu.ucb.sis213.Vista;

import javax.swing.*;

import bo.edu.ucb.sis213.Controlador.Controller;
import bo.edu.ucb.sis213.Modelo.Usuario;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangePinScreen extends JPanel {
    private JPasswordField currentPinField;
    private JPasswordField newPinField;
    private JPasswordField confirmPinField;
    private JButton changePinButton;
    private JButton backButton;
    private Controller controller;

    public ChangePinScreen(Controller controller) {
        this.controller = controller;
        setLayout(new GridLayout(5, 2));

        currentPinField = new JPasswordField();
        newPinField = new JPasswordField();
        confirmPinField = new JPasswordField();
        changePinButton = new JButton("Cambiar PIN");
        backButton = new JButton("Volver al Menú");

        changePinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener el PIN actual del usuario como String
                String currentPin = new String(currentPinField.getPassword());

                // Obtener el nuevo PIN y la confirmación del nuevo PIN como Strings
                String newPin = new String(newPinField.getPassword());
                String confirmPin = new String(confirmPinField.getPassword());

                // Obtener el usuario actual del controlador
                Usuario usuarioActual = controller.getUsuarioActual();

                // Comparar los PIN ingresados
                if (Integer.parseInt(currentPin) == usuarioActual.getPin()) {
                    if (newPin.equals(confirmPin)) {
                        // Lógica para cambiar el PIN en el usuario y en la base de datos
                        usuarioActual.setPin(Integer.parseInt(newPin));
                        controller.actualizarPinEnBaseDeDatos(controller.getConnection(), usuarioActual);
                        JOptionPane.showMessageDialog(null, "PIN cambiado exitosamente.");
                        controller.mostrarMainMenuScreen();
                    } else {
                        JOptionPane.showMessageDialog(null, "Los nuevos PIN no coinciden.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "PIN actual incorrecto.");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.mostrarMainMenuScreen(); // Volver al menú principal
            }
        });

        add(new JLabel("Ingrese su PIN actual:"));
        add(currentPinField);
        add(new JLabel("Ingrese su nuevo PIN:"));
        add(newPinField);
        add(new JLabel("Confirme su nuevo PIN:"));
        add(confirmPinField);
        add(new JLabel());
        add(changePinButton);
        add(new JLabel());
        add(backButton);
    }
}
