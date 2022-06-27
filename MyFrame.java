//STO PROVANDO

import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.*;

//TO DO: Aggiungere la scrollbar
public class MyFrame extends JFrame implements ActionListener {

    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenu editMenu = new JMenu("Edit");
    JMenuItem openItem = new JMenuItem("Open");
    JMenuItem saveItem = new JMenuItem("Save");
    JMenuItem saveAsItem = new JMenuItem("Save As");
    JMenuItem newWindowItem = new JMenuItem("New Window");
    JMenuItem exitItem = new JMenuItem("Exit");
    ImageIcon icon;
    JTextField textField = new JTextField();
    JTextArea textArea = new JTextArea(300, 1);

    MyFrame() {
        this.setTitle("Txt Editor");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());

        setTextArea();
        setFileMenu();
        setTextField();
        setIcon();
        setMenuBar();

        this.setBackground(Color.black);
        this.setResizable(false);
        this.add(textArea);
        this.add(textField);

        this.setJMenuBar(menuBar);
        this.setVisible(true);
    }

    /*
     * TEXTFIELD
     */
    private void setTextField() {
        textField.setPreferredSize(new Dimension(800, 200));
        textField.setForeground(Color.black);
        textField.setBackground(Color.white);
        textField.setEditable(true);
    }

    /*
     * FILE MENU
     */
    public void setFileMenu() {
        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        saveAsItem.addActionListener(this);
        newWindowItem.addActionListener(this);
        exitItem.addActionListener(this);

        newWindowItem.setMnemonic(KeyEvent.VK_N);
        openItem.setMnemonic(KeyEvent.VK_O);
        saveItem.setMnemonic(KeyEvent.VK_S);
        saveAsItem.setMnemonic(KeyEvent.VK_A);
        exitItem.setMnemonic(KeyEvent.VK_E);
        fileMenu.setMnemonic(KeyEvent.VK_F);

        fileMenu.add(newWindowItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.add(exitItem);
    }

    /*
     * TEXTAREA
     */
    public void setTextArea() {
        textArea.setPreferredSize(new Dimension(780, 200));
        textArea.setForeground(Color.black);
        textArea.setBackground(Color.white);
        textArea.setEditable(true);

        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

    }

    /*
     * ICON
     */
    public void setIcon() {
        icon = new ImageIcon("icon.png");
        this.setIconImage(icon.getImage());
    }

    /*
     * MENUBAR
     */
    public void setMenuBar() {
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser;

        /*
         * NEW WINDOW
         */
        if (e.getSource() == newWindowItem) {

            // TO DO: Fare in modo che se si chiude una finestra non si chiudano tutte

            MyFrame newFrame = new MyFrame();
            newFrame.setVisible(true);

            System.out.println("New Window");
        }

        /*
         * OPEN
         */
        if (e.getSource() == openItem) {

            // TO DO: Visualizzare il contenuto del file txt

            fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

            int response = fileChooser.showOpenDialog(this);

            if (response == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                try (FileReader fr = new FileReader(file);
                        BufferedReader br = new BufferedReader(fr)) {

                    String valueRead = br.readLine();
                    while (valueRead != null) {
                        textArea.append(valueRead + "\n");
                        valueRead = br.readLine();
                    }
                } catch (IOException ie) {
                    ie.printStackTrace();
                }
            }

            System.out.println("Open Item");
        }

        /*
         * SAVE
         */
        if (e.getSource() == saveItem) {
            fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

            int response = fileChooser.showSaveDialog(this);

            if (response == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                try (FileWriter fw = new FileWriter(file);
                        BufferedWriter bw = new BufferedWriter(fw)) {
                    if (!file.exists())
                        file.createNewFile();

                    bw.write(this.textArea.getText());
                } catch (IOException ie) {
                    ie.printStackTrace();
                }
            }
            System.out.println("Save Item");
        }

        /*
         * SAVE AS
         */
        if (e.getSource() == saveAsItem) {
            // TO DO: Salvare il file txt con un nome diverso
            System.out.println("Save As Item");
        }

        /*
         * EXIT
         */
        if (e.getSource() == exitItem) {
            System.exit(0);
        }

    }

}
