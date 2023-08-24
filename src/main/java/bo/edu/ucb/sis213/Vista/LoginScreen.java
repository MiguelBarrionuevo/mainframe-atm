package bo.edu.ucb.sis213.Vista;

import javax.swing.*;

import bo.edu.ucb.sis213.Controlador.Controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends JPanel {
    private JTextField userField;
    private JPasswordField pinField;
    private JButton loginButton;
    private Controller controller;

    public LoginScreen(Controller controller) {
        this.controller = controller;
        setLayout(new GridLayout(3, 2));

        userField = new JTextField();
        pinField = new JPasswordField();

        loginButton = new JButton("Ingresar");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuarioIngresado = userField.getText(); // Obtener el usuario ingresado
                char[] pinCharArray = pinField.getPassword(); // Obtener el PIN ingresado como un arreglo de caracteres
                int pinIngresado = Integer.parseInt(new String(pinCharArray)); // Convertir el arreglo de caracteres a un entero

                controller.validarPIN(usuarioIngresado, pinIngresado); // Llamar al método validarPIN en Controller
            }
        });

        add(new JLabel("Usuario:"));
        add(userField);
        add(new JLabel("PIN:"));
        add(pinField);
        add(new JLabel());
        add(loginButton);
    }
}
