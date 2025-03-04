import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaskManagerGUI {
    private DefaultListModel<String> processListModel;
    private JList<String> processList;
    private JButton refreshButton;
    private JButton killButton;
    private JButton detailsButton;
    private JButton forceQuitButton;
    public class ProcessDetails {
        private String processName;
        private int processId;
        private String memoryUsage;



        
            public ProcessDetails(String processName, int processId, String memoryUsage) {
                this.processName = processName;
                this.processId = processId;
                this.memoryUsage = memoryUsage;
            }
        
            public String getProcessName() {
                return processName;
            }
        
            public int getProcessId() {
                return processId;
            }
        
            public String getMemoryUsage() {
                return memoryUsage;
            }
        }
        
    ProcessDetails details = new ProcessDetails("MyProcess", 12345, "512 MB");
    /**
     * 
     */
    public TaskManagerGUI() {
        JFrame frame = new JFrame("Task Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        processListModel = new DefaultListModel<>();
        processList = new JList<>(processListModel);

        refreshButton = new JButton("Refresh");
        killButton = new JButton("Kill Process");
        detailsButton = new JButton("Details");
        forceQuitButton = new JButton("Force Quit");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(refreshButton);
        buttonPanel.add(killButton);
        buttonPanel.add(detailsButton);
        buttonPanel.add(forceQuitButton);

        frame.add(new JScrollPane(processList), BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshProcessList();
            }
        });
        killButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = processList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String selectedProcess = processListModel.get(selectedIndex);
        
                    // Implement process termination logic here
                    try {
                        String osName = System.getProperty("os.name").toLowerCase();
                        if (osName.contains("win")) {
                            // For Windows: Use the taskkill command to terminate the process
                            ProcessBuilder processBuilder = new ProcessBuilder("taskkill", "/F", "/IM", selectedProcess);
                            java.lang.Process process = processBuilder.start();
                            process.waitFor(); // Wait for the process to complete
                        } else {
                            // For other operating systems, implement platform-specific logic
                            // You may need to use different commands or APIs to terminate processes
                            JOptionPane.showMessageDialog(frame, "Process termination not supported on this OS.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
        
                        JOptionPane.showMessageDialog(frame, "Killed process: " + selectedProcess, "Process Terminated", JOptionPane.INFORMATION_MESSAGE);
                        refreshProcessList(); // Refresh the list after termination
                    } catch (IOException | InterruptedException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Error terminating the process: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a process to kill.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        detailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = processList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String selectedProcess = processListModel.get(selectedIndex);
        
                    // Implement logic to show details of the selected process
                    String processDetails = getProcessDetails(selectedProcess);
        
                    if (processDetails != null) {
                        // Display the process details in a dialog
                        JOptionPane.showMessageDialog(frame, "Process Details:\n" + processDetails, "Details", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Details not available for the selected process.", "Details", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a process to view details.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        String ProcessDetails(String processName) {
            // Add logic to retrieve details of the selected process
            // You can use platform-specific commands or APIs to gather details
            // For demonstration purposes, we'll return a mock process detail.
            return "Process Name: " + processName + "\nProcess ID: 12345\nMemory Usage: 512 MB";
        }
        

        forceQuitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = processList.getSelectedIndex();
                if (selectedIndex != -1) {
                    // Implement logic to forcefully terminate the selected process (platform-specific)
                    // ...
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a process to force quit.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.setVisible(true);
    }

    protected String getProcessDetails(String selectedProcess) {
        return null;
    }

    private void refreshProcessList() {
        // Implement code to retrieve the list of running processes
        processListModel.clear(); // Clear the existing list
        List<String> runningProcesses = getRunningProcesses();
        for (String process : runningProcesses) {
            processListModel.addElement(process);
        }
    }

    // Implement the actual code to retrieve the list of running processes on your system
    private List<String> getRunningProcesses() {
        List<String> processes = new ArrayList<>();
        // Implement code to retrieve the list of running processes
        // Add each process name to the 'processes' list
        return processes;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TaskManagerGUI();
            }
        });
    }
}
