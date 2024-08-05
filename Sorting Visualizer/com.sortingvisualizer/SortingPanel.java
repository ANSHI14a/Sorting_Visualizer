import javax.swing.*;
import java.awt.*;

public class SortingPanel extends JPanel {

    private int[] array;
    private int barWidth;

    public SortingPanel(int[] array, int barWidth) {
        this.array = array;
        this.barWidth = barWidth;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < array.length; i++) {
            int height = array[i];
            g.setColor(Color.BLUE);
            g.fillRect(i * barWidth, getHeight() - height, barWidth, height);
        }
    }
}

