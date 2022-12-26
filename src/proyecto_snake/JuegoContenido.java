package proyecto_snake;

import com.sun.source.tree.Scope;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class JuegoContenido extends JPanel implements ActionListener {

//pantalla,
    static final int PANTALLA = 600;
    static final int CUADRITO_SIZE = 25;
    static final int CUADRITOS_EN_PARALELO = (int) PANTALLA / CUADRITO_SIZE;

//serpiente,  
    static final int TOTAL_CUERPO_SNAKE = (PANTALLA * PANTALLA) / CUADRITO_SIZE;
    int[] snakeX = new int[TOTAL_CUERPO_SNAKE];
    int[] snakeY = new int[TOTAL_CUERPO_SNAKE];
    int cuerpo_snake = 3;//inicia con tamaño 3
    char direccion = 'd';//a w s d

//comida,
    int comidaX;
    int comidaY;

//timer,
    int DELAY = 200;//milisegundos
    Timer timer;
    boolean running = true;
//otros
    Random random = new Random();

    JLabel score = new JLabel("Score: 0");

    public JuegoContenido() {
        this.setPreferredSize(new Dimension(PANTALLA, PANTALLA));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);

        this.addKeyListener(new Controles());

        score.setSize(100, 100);
        score.setVisible(true);
        score.setForeground(Color.white);
        this.add(score);
        iniciarJuego();

    }

    public void iniciarJuego() {

        agregarComida();

        timer = new Timer(DELAY, this);
        timer.start();

    }

    public void agregarComida() {
        comidaX = random.nextInt(CUADRITOS_EN_PARALELO) * CUADRITO_SIZE;
        comidaY = random.nextInt(CUADRITOS_EN_PARALELO) * CUADRITO_SIZE;

    }

    public void moverSnake() {
        for (int i = cuerpo_snake; i > 0; i--) {
            snakeX[i] = snakeX[i - 1];
            snakeY[i] = snakeY[i - 1];
        }
        switch (direccion) {
            case 'd': {
                snakeX[0] = snakeX[0] + CUADRITO_SIZE;
                break;
            }
            case 'a': {
                snakeX[0] = snakeX[0] - CUADRITO_SIZE;
                break;
            }
            case 'w': {
                snakeY[0] = snakeY[0] - CUADRITO_SIZE;
                break;
            }
            case 's': {
                snakeY[0] = snakeY[0] + CUADRITO_SIZE;
                break;
            }
        }
    }

    public void chequearComida() {
        if (snakeX[0] == comidaX && snakeY[0] == comidaY) {
            cuerpo_snake++;
            agregarComida();
            if (timer.getDelay() > 60) {
                timer.setDelay(timer.getDelay() - 10);
            }

        }
    }

    public void chequearColisiones() {
        if (snakeX[0] < 0) {
            running = false;//detiene el proceso
        }

        if (snakeY[0] < 0) {
            running = false;//detiene el proceso
        }

        if (snakeX[0] > PANTALLA - CUADRITO_SIZE) {
            running = false;//detiene el proceso
        }

        if (snakeY[0] > PANTALLA - CUADRITO_SIZE) {
            running = false;//detiene el proceso
        }

        if (running == false) {
            JOptionPane.showMessageDialog(null, "PERDISTE!!");
            new Pantalla(cuerpo_snake);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (running) {
            score.setText("Score: " + cuerpo_snake);
            moverSnake();
            chequearComida();
            chequearColisiones();
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < CUADRITOS_EN_PARALELO; i++) {
            g.drawLine(0, CUADRITO_SIZE * i, PANTALLA, CUADRITO_SIZE * i);
            g.drawLine(CUADRITO_SIZE * i, 0, CUADRITO_SIZE * i, PANTALLA);
        }
        g.setColor(Color.red);
        g.fillOval(comidaX, comidaY, CUADRITO_SIZE, CUADRITO_SIZE);
        for (int i = 0; i < cuerpo_snake; i++) {

            g.setColor(Color.GREEN);
            g.fillRect(snakeX[i], snakeY[i], CUADRITO_SIZE - 1, CUADRITO_SIZE - 1);

        }
    }

    public class Controles extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyChar()) {
                case 'w': {
                    direccion = 'w';
                    break;
                }
                case 's': {
                    direccion = 's';
                    break;
                }
                case 'a': {
                    direccion = 'a';
                    break;
                }
                case 'd': {
                    direccion = 'd';
                    break;
                }
            }
        }
    }

}
