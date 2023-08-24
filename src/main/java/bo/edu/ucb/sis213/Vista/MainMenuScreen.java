package bo.edu.ucb.sis213.Vista;

import javax.swing.*;

import bo.edu.ucb.sis213.Controlador.Controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuScreen extends JPanel {
    private JButton checkBalanceButton;
    private JButton depositButton;
    private JButton withdrawButton;
    private JButton changePinButton;
    private JButton exitButton;

    private Controller controller;

    public MainMenuScreen(Controller controller) {
        this.controller = controller;
        setLayout(new GridLayout(5, 1));

        checkBalanceButton = new JButton("Consultar saldo");
        depositButton = new JButton("Realizar un depósito");
        withdrawButton = new JButton("Realizar un retiro");
        changePinButton = new JButton("Cambiar PIN");
        exitButton = new JButton("Salir");

        // Agrega listeners para manejar las acciones
        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.mostrarBalanceScreen(); // Mostrar la pantalla de consulta de saldo
            }
        });
                
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.mostrarDepositScreen(); // Mostrar la pantalla de depósito
            }
        });
        
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.mostrarWithdrawScreen(); // Mostrar la pantalla de retiro
            }
        });
        
        changePinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.mostrarChangePinScreen(); // Mostrar la pantalla de cambio de PIN
            }
        });
        
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Salir de la aplicación
            }
        });

        add(checkBalanceButton);
        add(depositButton);
        add(withdrawButton);
        add(changePinButton);
        add(exitButton);
    }
}

