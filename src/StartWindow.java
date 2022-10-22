import javax.swing.*;

public class StartWindow extends JFrame {
    StartWindow() {
        setTitle("Snake Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 500);
        setLocation(200,200);
        add(new GameContent());
        setVisible(true);
    }

    public static void main(String[] args) {
        StartWindow startWindow = new StartWindow();
    }
}
