package bo.edu.ucb.sis213.Vista;

import bo.edu.ucb.sis213.Controlador.Controller;
import bo.edu.ucb.sis213.Modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BalanceScreen extends JPanel {
    private Usuario usuario;
    private Controller controller;

    public BalanceScreen(Usuario usuario, Controller controller) {
        this.usuario = usuario;
        this.controller = controller;
        
        setLayout(new BorderLayout());

        JLabel saldoLabel = new JLabel("Saldo actual: $" + usuario.getSaldo());
        saldoLabel.setHorizontalAlignment(JLabel.CENTER);
        add(saldoLabel, BorderLayout.CENTER);

        JButton backButton = new JButton("Volver al Menú");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.mostrarMainMenuScreen(); 
            }
        });
        add(backButton, BorderLayout.SOUTH);
    }
}

