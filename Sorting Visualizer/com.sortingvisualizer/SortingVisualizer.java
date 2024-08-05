import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class SortingVisualizer {

    private static final int ARRAY_SIZE = 100;
    private static final int BAR_WIDTH = 5;
    private static final int DELAY = 10;
    
    private int[] array = new int[ARRAY_SIZE];
    private SortingPanel sortingPanel;
    private JComboBox<String> algorithmComboBox;
    private JButton sortButton, generateButton;
    private volatile boolean sorting;

    public SortingVisualizer() {
        JFrame frame = new JFrame("Sorting Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        sortingPanel = new SortingPanel(array, BAR_WIDTH);
        frame.add(sortingPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        frame.add(controlPanel, BorderLayout.SOUTH);

        generateButton = new JButton("Generate Array");
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateRandomArray();
                sortingPanel.repaint();
            }
        });
        controlPanel.add(generateButton);

        algorithmComboBox = new JComboBox<>(new String[]{"Bubble Sort", "Quick Sort", "Insertion Sort"});
        controlPanel.add(algorithmComboBox);

        sortButton = new JButton("Sort");
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!sorting) {
                    sorting = true;
                    new Thread(() -> {
                        String algorithm = (String) algorithmComboBox.getSelectedItem();
                        switch (algorithm) {
                            case "Bubble Sort":
                                bubbleSort();
                                break;
                            case "Quick Sort":
                                quickSort(0, array.length - 1);
                                break;
                            case "Insertion Sort":
                                insertionSort();
                                break;
                        }
                        sorting = false;
                    }).start();
                }
            }
        });
        controlPanel.add(sortButton);

        generateRandomArray();
        frame.setVisible(true);
    }

    private void generateRandomArray() {
        Random random = new Random();
        for (int i = 0; i < ARRAY_SIZE; i++) {
            array[i] = random.nextInt(400);
        }
    }

    private void bubbleSort() {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    swap(j, j + 1);
                    sortingPanel.repaint();
                    sleep();
                }
            }
        }
    }

    private void quickSort(int low, int high) {
        if (low < high) {
            int pi = partition(low, high);
            quickSort(low, pi - 1);
            quickSort(pi + 1, high);
            sortingPanel.repaint();
            sleep();
        }
    }

    private int partition(int low, int high) {
        int pivot = array[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (array[j] < pivot) {
                i++;
                swap(i, j);
            }
        }
        swap(i + 1, high);
        return i + 1;
    }

    private void insertionSort() {
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j = j - 1;
                sortingPanel.repaint();
                sleep();
            }
            array[j + 1] = key;
        }
    }

    private void swap(int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private void sleep() {
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SortingVisualizer::new);
    }
}
