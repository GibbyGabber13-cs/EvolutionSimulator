import java.awt.*;
import javax.swing.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class graphicsEv extends JFrame {
    public graphicsEv() {
        this.setTitle("Evolution Simulation");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setSize(800, 800); // Initial size
        this.add(new GridPanel());
        this.setVisible(true);
    }

    private class GridPanel extends JPanel {
        private List<List<String>> frames = new ArrayList<>(); // Store all frames
        private int currentFrameIndex = 0; // Track the current frame being rendered
        private int rows = 0;
        private int cols = 0;
        private int sleeptime = 20; // Time in milliseconds between frames

        public GridPanel() {
            new Thread(() -> {
                try {
                    FileReader fr = new FileReader("EvolutionOutput.txt");
                    Scanner myReader = new Scanner(fr);

                    // Read the file and split it into frames
                    List<String> currentFrame = new ArrayList<>();
                    while (myReader.hasNextLine()) {
                        String line = myReader.nextLine();
                        if (line.equals("------------------------------------------------------------------")) {
                            // End of a frame
                            if (!currentFrame.isEmpty()) {
                                frames.add(new ArrayList<>(currentFrame));
                                currentFrame.clear();
                            }
                        } else {
                            currentFrame.add(line);
                            cols = Math.max(cols, line.trim().split("\\s+").length); // Update column count
                        }
                    }
                    // Add the last frame if it exists
                    if (!currentFrame.isEmpty()) {
                        frames.add(currentFrame);
                    }
                    myReader.close();

                    rows = frames.stream().mapToInt(List::size).max().orElse(0); // Max rows in any frame

                    // Debugging: Print frame count and dimensions
                    System.out.println("Total Frames: " + frames.size());
                    System.out.println("Rows: " + rows + ", Columns: " + cols);

                    // Render each frame with a delay
                    while (currentFrameIndex < frames.size()) {
                        repaint(); // Trigger a repaint
                        Thread.sleep(sleeptime); // Wait for 1 second between frames
                        currentFrameIndex++; // Move to the next frame
                    }
                    System.out.println("End of render");
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }).start(); // Start the rendering thread
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            if (frames.isEmpty() || currentFrameIndex >= frames.size()) {
                System.out.println("No frames to render.");
                return; // Nothing to render
            }

            // Ensure cols and rows are greater than zero
            if (cols <= 0 || rows <= 0) {
                System.out.println("Invalid grid dimensions: cols=" + cols + ", rows=" + rows);
                return; // Prevent division by zero
            }

            // Calculate dynamic cell size
            int cellSize = Math.min(this.getWidth() / cols, this.getHeight() / rows);

            // Center the grid
            int xOffset = (this.getWidth() - (cols * cellSize)) / 2;
            int yOffset = (this.getHeight() - (rows * cellSize)) / 2;

            int y = yOffset;

            // Render the current frame
            List<String> currentFrame = frames.get(currentFrameIndex);
            for (String line : currentFrame) {
                int x = xOffset;
                String[] parts = line.trim().split("\\s+"); // Handle extra spaces
                for (String part : parts) {
                    if (part.equals("[_]")) {
                        g2d.setColor(Color.WHITE); // Empty space
                    } else if (part.length() > 1 && Character.isLetter(part.charAt(1))) {
                        g2d.setColor(Color.GREEN); // Alphabetical character
                    } else if (part.length() > 1 && Character.isDigit(part.charAt(1))) {
                        g2d.setColor(Color.RED); // Numeric character
                    }
                    g2d.fillRect(x, y, cellSize, cellSize); // Draw the cell
                    x += cellSize; // Move to the next column
                }
                y += cellSize; // Move to the next row
            }
        }
    }

    public static void main(String[] args) {
        new graphicsEv();
    }
}