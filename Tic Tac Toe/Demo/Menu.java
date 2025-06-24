import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Menu extends JPanel {
    public Menu(JFrame frame) {

        setLayout(new BorderLayout());
        setBackground(Component.COLOR_BG);

        // Panel tengah untuk tombol dan logo
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        // Tambahkan logo Tic Tac Toe

        URL logoURL = getClass().getClassLoader().getResource("images/tictactoe_logo.png");
        if (logoURL != null) {
            ImageIcon originalLogo = new ImageIcon(logoURL);
            Image scaledImage = originalLogo.getImage().getScaledInstance(640, 360, Image.SCALE_SMOOTH);
            ImageIcon scaledLogoIcon = new ImageIcon(scaledImage);
            JLabel logoLabel = new JLabel(scaledLogoIcon);
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            centerPanel.add(Box.createVerticalStrut(30));
            centerPanel.add(logoLabel);

        } else {
            System.err.println("Logo image not found!");
            JLabel fallback = new JLabel("Tic Tac Toe");
            fallback.setFont(new Font("Verdana", Font.BOLD, 36));
            fallback.setForeground(Color.WHITE);
            fallback.setAlignmentX(Component.CENTER_ALIGNMENT);
            centerPanel.add(Box.createVerticalStrut(30));
            centerPanel.add(fallback);
        }

        centerPanel.add(Box.createVerticalStrut(90));
        centerPanel.add(createButton("Start The Game", new Color(2,21,38), () -> startDuoGame(frame)));
        centerPanel.add(Box.createVerticalStrut(15));

        add(centerPanel, BorderLayout.CENTER);

        // Panel bawah terpisah kiri dan kanan
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);

        // Panel kiri untuk tombol options
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        leftPanel.setOpaque(false);

        // Tombol options

        // Panel kanan untuk tombol exit
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        rightPanel.setOpaque(false);

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Verdana", Font.BOLD, 16));
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.setPreferredSize(new Dimension(100, 40));
        exitButton.addActionListener(e -> System.exit(0));
        rightPanel.add(exitButton);
        // Tambahkan ActionListener dengan konfirmasi
        exitButton.addActionListener(e -> {
            // Tampilkan dialog konfirmasi
            int confirm = JOptionPane.showConfirmDialog(
                    null, // Komponen parent (null untuk center di layar)
                    "Are you sure you want to exit the game?", // Pesan
                    "Exit Confirmation", // Judul dialog
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            // Jika user memilih YES
            if (confirm == JOptionPane.YES_OPTION) {
                int confirm1 = JOptionPane.showConfirmDialog(
                        null, // Komponen parent (null untuk center di layar)
                        "Are you really sure about that?", // Pesan
                        "Exit Confirmation", // Judul dialog
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (confirm1== JOptionPane.YES_OPTION) {
                    System.exit(0); // Keluar dari aplikasi
                }
            }
            // Jika NO, tidak melakukan apa-apa (dialog akan tertutup)
        });

        rightPanel.add(exitButton);

// Tambahkan ke bottomPanel
        bottomPanel.add(leftPanel, BorderLayout.WEST);
        bottomPanel.add(rightPanel, BorderLayout.EAST);

// Tambahkan bottomPanel ke layout utama
        add(bottomPanel, BorderLayout.SOUTH);

    }

    private JButton createButton(String text, Color color, Runnable action) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setAlignmentY(10);
        button.setFont(new Font("Verdana", Font.BOLD, 20));
        button.setMaximumSize(new Dimension(240, 50));
        button.setBackground(Component.COLOR_BG);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);

        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addActionListener(e -> action.run());
        return button;
    }

    private void startDuoGame(JFrame frame) {
        JTextField playerXField = new JTextField("Player X", 15);
        JTextField playerOField = new JTextField("Player O", 15);

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));

        panel.add(new JLabel("Enter name for Player Using X:"));
        panel.add(playerXField);
        panel.add(new JLabel("Enter name for Player Using O:"));
        panel.add(playerOField);

        int result = JOptionPane.showConfirmDialog(
                frame,
                panel,
                "Your Player Name's",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        String playerX = "Player X";
        String playerO = "Player O";

        if (result == JOptionPane.OK_OPTION) {
            String inputX = playerXField.getText().trim();
            String inputO = playerOField.getText().trim();

            if (!inputX.isEmpty()) playerX = inputX;
            if (!inputO.isEmpty()) playerO = inputO;

            frame.setContentPane(new GameMain(playerX, playerO));
            frame.setSize(400,400);

            frame.pack();
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        }else{
            frame.setContentPane(new Menu(frame));
            frame.revalidate();
            frame.repaint();
        }


    }



}