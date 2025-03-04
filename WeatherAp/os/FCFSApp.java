import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class FCFSApp {
    private JFrame frame;
    private JPanel panel;
    private JButton addButton;
    private JButton deleteButton;
    private JButton calculateButton;
    private JTextField processCountField;
    private Process[] processes;
    private int numProcesses = 0;
    private int totalProcesses = 0;

    public FCFSApp() {
        frame = new JFrame("FCFS Scheduling");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        panel = new JPanel(new GridBagLayout());
        frame.add(panel);

        addButton = new JButton("Add Process");
        deleteButton = new JButton("Delete Process");
        calculateButton = new JButton("Calculate Schedule");
        processCountField = new JTextField(5);

        processes = new Process[10];

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (totalProcesses == 0) {
                    String processCountString = processCountField.getText();
                    if (processCountString != null && !processCountString.isEmpty()) {
                        totalProcesses = Integer.parseInt(processCountString);
                        if (totalProcesses <= 0) {
                            JOptionPane.showMessageDialog(frame, "Please enter a valid number of processes.");
                        }
                    }
                } else if (numProcesses < totalProcesses) {
                    addProcess();
                } else {
                    JOptionPane.showMessageDialog(frame, "All processes added. Click 'Calculate Schedule'.");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int count = Integer.parseInt(processCountField.getText());
                if (count > numProcesses) {
                    JOptionPane.showMessageDialog(frame, "Invalid number of processes to delete.");
                } else {
                    deleteProcesses(count);
                }
            }
        });

        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculateSchedule();
            }
        });

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        panel.add(new JLabel("Enter the number of processes: "), c);
        c.gridx = 1;
        panel.add(processCountField, c);
        c.gridx = 2;
        panel.add(addButton, c);
        c.gridx = 3;
        panel.add(deleteButton, c);
        c.gridy = 1;
        panel.add(calculateButton, c);

        frame.setVisible(true);
    }

    private void addProcess() {
        String burstTimeString = JOptionPane.showInputDialog(frame, "Enter Burst Time for Process " + (numProcesses + 1) + ":");
        if (burstTimeString != null && !burstTimeString.isEmpty()) {
            int burstTime = Integer.parseInt(burstTimeString);
            processes[numProcesses] = new Process();
            processes[numProcesses].id = numProcesses + 1;
            processes[numProcesses].burstTime = burstTime;
            numProcesses++;
        }
    }

    private void deleteProcesses(int count) {
        for (int i = numProcesses - 1; i >= numProcesses - count; i--) {
            processes[i] = null;
        }
        numProcesses -= count;
    }

    private void calculateSchedule() {
        if (numProcesses == 0) {
            JOptionPane.showMessageDialog(frame, "No processes to schedule.");
            return;
        }

        Arrays.sort(processes, 0, numProcesses, (p1, p2) -> Integer.compare(p1.arrivalTime, p2.arrivalTime));

        int[] waitingTime = new int[numProcesses];
        int[] turnaroundTime = new int[numProcesses];

        waitingTime[0] = 0;
        turnaroundTime[0] = processes[0].burstTime;

        for (int i = 1; i < numProcesses; i++) {
            waitingTime[i] = turnaroundTime[i - 1];
            turnaroundTime[i] = waitingTime[i] + processes[i].burstTime;
        }

        displayResults(waitingTime, turnaroundTime);
    }

    private void displayResults(int[] waitingTime, int[] turnaroundTime) {
        StringBuilder resultMessage = new StringBuilder("Scheduling Results:\n");
        for (int i = 0; i < numProcesses; i++) {
            resultMessage.append("Process ").append(processes[i].id);
            resultMessage.append(" - Waiting Time: ").append(waitingTime[i]);
            resultMessage.append(", Turnaround Time: ").append(turnaroundTime[i]).append("\n");
        }

        JTextArea resultTextArea = new JTextArea(resultMessage.toString());
        resultTextArea.setEditable(false);
        JOptionPane.showMessageDialog(frame, resultTextArea, "Scheduling Results", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new FCFSApp();
            }
        });
    }
}

class Process {
    public int id;
    public int burstTime;
    public int arrivalTime;
}

