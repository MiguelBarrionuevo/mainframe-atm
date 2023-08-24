
package bo.edu.ucb.sis213.Controlador;

import bo.edu.ucb.sis213.Modelo.*;
import bo.edu.ucb.sis213.Vista.*;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.sql.DriverManager;

public class Controller {
    private List<Usuario> usuarios;
    private List<Transaccion> transacciones;
    private Usuario usuarioActual;

    private JFrame frame;
    private JPanel currentPanel;

    private Connection connection;

    public Controller(Connection connection) {
        this.connection = connection;
        usuarios = new ArrayList<>();
        transacciones = new ArrayList<>();
        cargarUsuariosDesdeBaseDeDatos(connection); // Lógica para cargar usuarios desde la base de datos
        cargarTransaccionesDesdeBaseDeDatos(connection);

        frame = new JFrame("ATM App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        mostrarLoginScreen();

        frame.setVisible(true);
    }

    private void mostrarLoginScreen() {
        currentPanel = new LoginScreen(this);
        frame.getContentPane().removeAll();
        frame.add(currentPanel);
        frame.revalidate();
        frame.repaint();
    }

    public void mostrarMainMenuScreen() {
        currentPanel = new MainMenuScreen(this);
        frame.getContentPane().removeAll();
        frame.add(currentPanel);
        frame.revalidate();
        frame.repaint();
    }

    public void mostrarBalanceScreen() {
        currentPanel = new BalanceScreen(usuarioActual,this);
        frame.getContentPane().removeAll();
        frame.add(currentPanel);
        frame.revalidate();
        frame.repaint();
    }

    public void mostrarDepositScreen() {
        currentPanel = new DepositScreen(this);
        frame.getContentPane().removeAll();
        frame.add(currentPanel);
        frame.revalidate();
        frame.repaint();
    }

    public void mostrarWithdrawScreen() {
        currentPanel = new WithdrawScreen(this);
        frame.getContentPane().removeAll();
        frame.add(currentPanel);
        frame.revalidate();
        frame.repaint();
    }

    public void mostrarChangePinScreen() {
        currentPanel = new ChangePinScreen(this);
        frame.getContentPane().removeAll();
        frame.add(currentPanel);
        frame.revalidate();
        frame.repaint();
    }

    // Lógica para cargar usuarios desde la base de datos
    private void cargarUsuariosDesdeBaseDeDatos(Connection connection) {
    String query = "SELECT id, nombre, pin, saldo ,alias FROM usuarios";
    try {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nombre = resultSet.getString("nombre");
            int pin = resultSet.getInt("pin");
            double saldo = resultSet.getDouble("saldo");
            String alias = resultSet.getString("alias");
            Usuario usuario = new Usuario(id, nombre,pin,saldo, alias);
            usuarios.add(usuario);
        }

        resultSet.close();
        preparedStatement.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    }   

    public void validarPIN(String usuarioIngresado, int pinIngresado) {
        // Lógica para validar el PIN en la base de datos
        // Buscar el usuario por el nombre de usuario ingresado
        Usuario usuarioEncontrado = null;
        for (Usuario usuario : usuarios) {
            if (usuario.getAlias().equals(usuarioIngresado)) {
                usuarioEncontrado = usuario;
                break;
            }
        }

        if (usuarioEncontrado != null) {
            if (usuarioEncontrado.getPin() == pinIngresado) {
                usuarioActual = usuarioEncontrado; // Asignar usuario actual
                mostrarMainMenuScreen(); // Mostrar el menú principal
            } else {
                JOptionPane.showMessageDialog(null, "PIN incorrecto. Intente nuevamente.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no encontrado.");
        }
    }

// Lógica para cargar transacciones desde la base de datos
private void cargarTransaccionesDesdeBaseDeDatos(Connection connection) {
    String query = "SELECT id, usuario_id, tipo_operacion, cantidad, fecha FROM historico";
    try {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int usuarioId = resultSet.getInt("usuario_id");
            String tipoOperacion = resultSet.getString("tipo_operacion");
            double cantidad = resultSet.getDouble("cantidad");
            LocalDateTime fecha = resultSet.getTimestamp("fecha").toLocalDateTime();

            Transaccion transaccion = new Transaccion(usuarioId, tipoOperacion, cantidad, fecha);
            transacciones.add(transaccion);
        }

        resultSet.close();
        preparedStatement.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}

    // Lógica para guardar una transacción en la base de datos
    public void guardarTransaccionEnBaseDeDatos(Connection connection, Transaccion transaccion) {
        String insertQuery = "INSERT INTO historico (usuario_id, tipo_operacion, cantidad) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, transaccion.getUsuarioId());
            preparedStatement.setString(2, transaccion.getTipoOperacion());
            preparedStatement.setDouble(3, transaccion.getCantidad());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Lógica para actualizar el saldo de un usuario en la base de datos
    public void actualizarSaldoEnBaseDeDatos(Connection connection, Usuario usuario) {
        String updateQuery = "UPDATE usuarios SET saldo = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setDouble(1, usuario.getSaldo());
            preparedStatement.setInt(2, usuario.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void actualizarPinEnBaseDeDatos(Connection connection ,Usuario usuario) {
        String updateQuery = "UPDATE usuarios SET pin = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setInt(1, usuario.getPin());
            preparedStatement.setInt(2, usuario.getId());
    
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    

    public void setUsuarioActual(Usuario usuario) {
        this.usuarioActual = usuario;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = establecerConexion();
        } catch (SQLException ex) {
            System.err.println("No se puede conectar a Base de Datos");
            ex.printStackTrace();
            System.exit(1);
        }

        final Connection finalConnection = connection; // Crear una variable final para usar en la clase interna

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Controller(finalConnection); // Pasa la conexión al constructor de Controller
            }
        });
    }

    
    private static Connection establecerConexion() throws SQLException {
        String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/atm"; // Reemplaza con tu URL de base de datos
        String usuario = "root";
        String contraseña = "123456";

        Connection conexion = DriverManager.getConnection(jdbcUrl, usuario, contraseña);
        return conexion;
    }

    public Connection getConnection() {
        return connection;
    }
}
 
