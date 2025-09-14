import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * A simple To-Do List application using Java Swing
 * Perfect for AP Computer Science A students learning GUI programming
 */
public class ToDoListApp extends JFrame implements ActionListener {
    
    // GUI Components
    private JTextField taskInput;
    private JButton addButton;
    private JButton deleteButton;
    private JButton clearCompletedButton;
    private DefaultListModel<String> listModel;
    private JList<String> taskList;
    private JLabel statusLabel;
    
    // Data storage
    private ArrayList<Task> tasks;
    
    /**
     * Constructor - sets up the GUI and initializes data
     */
    public ToDoListApp() {
        tasks = new ArrayList<>();
        setupGUI();
    }
    
    /**
     * Sets up the main GUI components and layout
     */
    private void setupGUI() {
        setTitle("To-Do List Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null); // Center the window
        
        // Initialize components
        taskInput = new JTextField(20);
        addButton = new JButton("Add Task");
        deleteButton = new JButton("Toggle Complete");
        clearCompletedButton = new JButton("Clear Completed");
        
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        statusLabel = new JLabel("Tasks: 0 | Completed: 0");
        
        // Set up event listeners
        addButton.addActionListener(this);
        deleteButton.addActionListener(this);
        clearCompletedButton.addActionListener(this);
        
        // Allow Enter key to add tasks
        taskInput.addActionListener(this);
        
        // Layout setup
        setupLayout();
        
        // Initial display update
        updateDisplay();
    }
    
    /**
     * Sets up the layout of GUI components
     */
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Top panel for input
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("New Task:"));
        inputPanel.add(taskInput);
        inputPanel.add(addButton);
        
        // Center panel for the list
        JScrollPane scrollPane = new JScrollPane(taskList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Tasks"));
        
        // Bottom panel for buttons and status
        JPanel bottomPanel = new JPanel(new BorderLayout());
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearCompletedButton);
        
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        bottomPanel.add(statusLabel, BorderLayout.SOUTH);
        
        // Add panels to main frame
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Handles all button click events
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton || e.getSource() == taskInput) {
            addTask();
        } else if (e.getSource() == deleteButton) {
            toggleTaskComplete();
        } else if (e.getSource() == clearCompletedButton) {
            clearCompletedTasks();
        }
    }
    
    /**
     * Adds a new task to the list
     */
    private void addTask() {
        String taskText = taskInput.getText().trim();
        
        if (!taskText.isEmpty()) {
            Task newTask = new Task(taskText);
            tasks.add(newTask);
            taskInput.setText(""); // Clear input field
            updateDisplay();
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a task!", 
                                        "Empty Task", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /**
     * Toggles the completion status of the selected task
     */
    private void toggleTaskComplete() {
        int selectedIndex = taskList.getSelectedIndex();
        
        if (selectedIndex != -1) {
            Task selectedTask = tasks.get(selectedIndex);
            selectedTask.toggleComplete();
            updateDisplay();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to toggle!", 
                                        "No Selection", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Removes all completed tasks from the list
     */
    private void clearCompletedTasks() {
        int completedCount = 0;
        
        // Count completed tasks first
        for (Task task : tasks) {
            if (task.isComplete()) {
                completedCount++;
            }
        }
        
        if (completedCount > 0) {
            int choice = JOptionPane.showConfirmDialog(this, 
                "Remove " + completedCount + " completed task(s)?", 
                "Confirm Clear", JOptionPane.YES_NO_OPTION);
            
            if (choice == JOptionPane.YES_OPTION) {
                // Remove completed tasks (iterate backwards to avoid index issues)
                for (int i = tasks.size() - 1; i >= 0; i--) {
                    if (tasks.get(i).isComplete()) {
                        tasks.remove(i);
                    }
                }
                updateDisplay();
            }
        } else {
            JOptionPane.showMessageDialog(this, "No completed tasks to clear!", 
                                        "Nothing to Clear", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Updates the display to reflect current task list
     */
    private void updateDisplay() {
        // Clear and repopulate the list model
        listModel.clear();
        
        int completedCount = 0;
        
        for (Task task : tasks) {
            String displayText = task.isComplete() ? "✓ " + task.getDescription() : task.getDescription();
            listModel.addElement(displayText);
            
            if (task.isComplete()) {
                completedCount++;
            }
        }
        
        // Update status label
        statusLabel.setText("Tasks: " + tasks.size() + " | Completed: " + completedCount);
    }
    
    /**
     * Main method to run the application
     */
    public static void main(String[] args) {
        // Set look and feel to system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // If setting look and feel fails, continue with default
        }
        
        // Create and show the application
        SwingUtilities.invokeLater(() -> {
            new ToDoListApp().setVisible(true);
        });
    }
}