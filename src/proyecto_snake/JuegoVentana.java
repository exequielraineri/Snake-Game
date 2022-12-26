package proyecto_snake;

import java.awt.HeadlessException;
import javax.swing.JFrame;

public class JuegoVentana extends JFrame {

    public JuegoVentana() {
        this.setTitle("Snake");
        this.add(new JuegoContenido());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
