package bo.edu.ucb.sis213;
import bo.edu.ucb.sis213.Controlador.Controller;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = Controller.establecerConexion();
        } catch (SQLException ex) {
            System.err.println("No se puede conectar a la Base de Datos");
            ex.printStackTrace();
            System.exit(1);
        }

        final Connection finalConnection = connection;

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Controller(finalConnection);
            }
        });
    }
    

}
