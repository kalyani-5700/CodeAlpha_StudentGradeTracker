import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Student {
    String name;
    int grade;

    Student(String name, int grade) {
        this.name = name;
        this.grade = grade;
    }
}

public class StudentGradeTrackerGUI extends JFrame {
    private JTextField nameField, gradeField;
    private JTextArea outputArea;
    private ArrayList<Student> students;

    public StudentGradeTrackerGUI() {
        setTitle("Student Grade Tracker");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        students = new ArrayList<>();

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        inputPanel.add(new JLabel("Student Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Grade:"));
        gradeField = new JTextField();
        inputPanel.add(gradeField);

        JButton addButton = new JButton("Add Student");
        JButton reportButton = new JButton("Generate Report");
        inputPanel.add(addButton);
        inputPanel.add(reportButton);

        add(inputPanel, BorderLayout.NORTH);

        // Output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // Button actions
        addButton.addActionListener(e -> addStudent());
        reportButton.addActionListener(e -> generateReport());
    }

    private void addStudent() {
        String name = nameField.getText().trim();
        String gradeText = gradeField.getText().trim();

        if (name.isEmpty() || gradeText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both name and grade.");
            return;
        }

        try {
            int grade = Integer.parseInt(gradeText);
            students.add(new Student(name, grade));
            outputArea.append("Added: " + name + " - " + grade + "\n");
            nameField.setText("");
            gradeField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Grade must be a number.");
        }
    }

    private void generateReport() {
        if (students.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No student data available.");
            return;
        }

        int sum = 0, highest = Integer.MIN_VALUE, lowest = Integer.MAX_VALUE;
        String topStudent = "", lowStudent = "";

        StringBuilder report = new StringBuilder("===== Student Grade Report =====\n");
        for (Student s : students) {
            report.append(s.name).append(" - ").append(s.grade).append("\n");
            sum += s.grade;
            if (s.grade > highest) {
                highest = s.grade;
                topStudent = s.name;
            }
            if (s.grade < lowest) {
                lowest = s.grade;
                lowStudent = s.name;
            }
        }

        double average = (double) sum / students.size();

        report.append("\nAverage Score: ").append(average);
        report.append("\nHighest Score: ").append(highest).append(" (by ").append(topStudent).append(")");
        report.append("\nLowest Score: ").append(lowest).append(" (by ").append(lowStudent).append(")\n");

        outputArea.setText(report.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentGradeTrackerGUI().setVisible(true);
        });
    }
} 