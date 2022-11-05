import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame {
    private final JFrame jframe;
    private JPanel panel;
    private JLabel login;
    private JLabel password;
    private JLabel command;
    private JTextField f1;
    private JPasswordField f2;
    private JTextField f3;
    private JTextArea textArea;
    private GridBagConstraints gbc = new GridBagConstraints(
            0, 0, 1, 1, 0, 0,
            GridBagConstraints.BASELINE_TRAILING,
            GridBagConstraints.NONE,
            new Insets(5, 5, 5, 5), 4, 6);
    private JScrollPane jScrollPane;

    /**
    Constructor creates a GUI using JFrame and adjust position of all elements.
     **/
    public Frame() {
        //Create frame
        jframe = new JFrame();
        panel = new JPanel(new GridBagLayout());
        jframe.setSize(100,100);
        jframe.add(panel);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setTitle("SSH to switches");
        //Login field
        login = new JLabel("Username");
        login.setBounds(10,20,80,25);
        f1 = new JTextField(20);
        f1.setBounds(100,20,165,25);

        // Password field creation
        password = new JLabel("Password");
        password.setBounds(10,50,80,25);
        f2 = new JPasswordField(20);
        f2.setBounds(100,20,165,25);

        // Command field creation
        command = new JLabel("Command");
        command.setBounds(10,20,80,25);
        f3 = new JTextField(20);
        f3.setBounds(100,20,165,25);

        //Text area creation
        textArea = new JTextArea(50,50);
        textArea.setMaximumSize(new Dimension(80,40));


        //Positioning
        panel.add(login,gbc);
        gbc.gridy = 1;
        panel.add(password,gbc);
        gbc.gridy = 2;
        panel.add(command,gbc);
        gbc.anchor = GridBagConstraints.BASELINE_LEADING;
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(f1,gbc);
        gbc.gridy = 1;
        panel.add(f2,gbc);
        gbc.gridy = 2;
        panel.add(f3,gbc);
        gbc.gridy = 3;
        panel.add(textArea,gbc);


        //Add Reset Button
        JButton button = new JButton("Clear");
        button.setBounds(10,80,80,25);
        gbc.gridx = 1;
        gbc.gridy = 4;
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                resetArea();
            }
        });
        panel.add(button,gbc);


        //Pack and display
        jframe.pack();
        jframe.setVisible(true);
    }

    public JButton addButton(String name) {
        JButton button = new JButton(name);
        button.setBounds(10,80,80,25);
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(button,gbc);
        jframe.pack();
        return button;
    }

    public String getUsername(){
        return f1.getText();
    }
    public String getPassword(){
        return new String(f2.getPassword());
    }
    public String getCommand(){
        return f3.getText();
    }
    public JTextArea getTextArea(){
        return this.textArea;
    }
    public void resetArea() {
        this.textArea.setText("");
    }
    public void clearFields(){
        this.f1.setText("");
        this.f2.setText("");
        this.f3.setText("");
    }
    public void addScrollBar(JTextArea textArea){
        jScrollPane = new JScrollPane(textArea);
        jScrollPane.setViewportView(textArea);
        jScrollPane.setWheelScrollingEnabled(true);
        gbc.gridy = 3;
        gbc.gridx = 1;
        panel.add(jScrollPane,gbc);
        jframe.pack();
        jframe.setVisible(true);
    }
}
