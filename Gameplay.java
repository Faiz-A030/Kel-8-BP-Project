import javax.swing.*;
import java.sql.SQLException;

public class Gameplay {
    public static final String TITLE = "Tic Tac Toe";

    public static void main(String[] args) {
        // Inisialisasi DatabaseHelper (sesuaikan dengan konfigurasi database Anda)
        try {
            DatabaseHelper dbHelper = new DatabaseHelper("jdbc:mysql://mysql-21c75f56-bp-001.f.aivencloud.com:28622/login_schema", "avnadmin", "AVNS_CM-W653DJq1UXikQyNt");

            // Tampilkan LoginSystemGUI terlebih dahulu
            SwingUtilities.invokeLater(() -> {
                new LoginSystemGUI(dbHelper, () -> {
                    // Callback ini dijalankan setelah login berhasil
                    SoundEffect.playBGMusic();
                    JFrame frame = new JFrame(TITLE);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setContentPane(new Menu(frame));
                    frame.pack();
                    frame.setSize(720, 900);
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                });
            });
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Failed to connect to database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
}