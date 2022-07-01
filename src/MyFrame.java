package src;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Color;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.*;

public class MyFrame extends JFrame implements ActionListener {

    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMenu = new JMenu("File");
    private JMenu editMenu = new JMenu("Edit");
    private JMenu fontMenu = new JMenu("Font");


    private JMenuItem openItem = new JMenuItem("Open");
    private JMenuItem saveItem = new JMenuItem("Save");
    private JMenuItem newWindowItem = new JMenuItem("New Window");
    private JMenuItem colorChooserItem = new JMenuItem("Text Color");
    private JMenuItem boldItem = new JMenuItem("Bold");
    private JMenuItem italicItem = new JMenuItem("Italic");
    private JMenuItem exitItem = new JMenuItem("Exit");
    private JMenuItem fontComboBoxItem = new JMenuItem("Font");

    // I FONT
    private final String[] FONTS = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

    private JComboBox<String> fontComboBox = new JComboBox<>(FONTS);

    private JDialog d;

    private ImageIcon icon;
    private JTextArea textArea = new JTextArea();
    private JScrollPane scrollPane;
    private JPanel panel = new JPanel();

    private Font font = new Font("Arial", Font.PLAIN, 16);

    private JSpinner fontSizeSpinner = new JSpinner();

    private JFileChooser fileChooser;

    /*
     * COSTRUCTOR
     */
    MyFrame() {
        this.setTitle("Txt Editor");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new FlowLayout());

        this.setTextArea("Monospace", Font.PLAIN, 16);

        this.setScrollPane();
        this.initPanel();
        this.setFileMenu();
        this.setIcon();
        this.setMenuBar();
        this.setFontSize();

        this.setBackground(Color.black);
        this.setResizable(false);
        this.add(panel);

        this.setJMenuBar(menuBar);
        this.setVisible(true);
    }

    /*
     * FILE MENU
     */
    public void setFileMenu() {
        openItem.addActionListener(evt -> openFile());
        saveItem.addActionListener(evt -> saveFile());
        newWindowItem.addActionListener(evt -> newWindow());
        exitItem.addActionListener(evt -> System.exit(0));
        italicItem.addActionListener(evt -> setItalic());
        boldItem.addActionListener(evt -> setBold());
        colorChooserItem.addActionListener(evt -> setColor());
        fontComboBoxItem.addActionListener(this);
        fontComboBox.addActionListener(e -> this.selectFont());

        newWindowItem.setMnemonic(KeyEvent.VK_N);
        openItem.setMnemonic(KeyEvent.VK_O);
        saveItem.setMnemonic(KeyEvent.VK_S);
        exitItem.setMnemonic(KeyEvent.VK_X);
        fileMenu.setMnemonic(KeyEvent.VK_F);
        colorChooserItem.setMnemonic(KeyEvent.VK_C);
        editMenu.setMnemonic(KeyEvent.VK_E);
        boldItem.setMnemonic(KeyEvent.VK_B);
        italicItem.setMnemonic(KeyEvent.VK_I);

        colorChooserItem.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        openItem.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        saveItem.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        newWindowItem.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        exitItem.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        boldItem.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        italicItem.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        fileMenu.add(newWindowItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);

        editMenu.add(boldItem);
        editMenu.add(italicItem);
        editMenu.add(colorChooserItem);
        editMenu.add(fontSizeSpinner);

        this.fontMenu.add(fontComboBoxItem);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        this.d = new JDialog(this, "Font", true);
        this.d.setSize(new Dimension(100, 100));
        this.d.setLocationRelativeTo(this);
        this.d.add(fontComboBox);
        this.d.setResizable(false);
        this.d.setVisible(true);
    }

    private void selectFont() {
        this.textArea.setFont(new Font(fontComboBox.getSelectedItem().toString(), this.textArea.getFont().getStyle(), this.textArea.getFont().getSize()));
    }

    /*
     * SAVE FILE
     */
    public void saveFile() {
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
     * OPEN FILE
     */
    public void openFile() {

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
    }

    /*
     * NEW WINDOW
     */
    public void newWindow() {

        MyFrame newFrame = new MyFrame();
        newFrame.setVisible(true);

        System.out.println("New Window");
    }

    /*
     * INIT PANEL
     */
    public void initPanel() {
        panel.setBackground(Color.white);
        panel.setPreferredSize(this.getSize());
        panel.add(scrollPane);
        panel.setVisible(true);
    }

    /*
     * TEXTAREA
     */
    public void setTextArea(String fontName, int fontStyle, int fontSize) {
        textArea.setForeground(Color.black);
        textArea.setBackground(Color.white);

        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        textArea.setFont(new Font(fontName, fontStyle, fontSize));
        textArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        this.setScrollPane();
    }

    /*
     * BOLD ITEM
     */
    public void setBold() {
        if (textArea.getFont().isBold())
            return;

        int size = (int) fontSizeSpinner.getValue();
        textArea.setFont(new Font(textArea.getFont().toString(), Font.BOLD, size));
    }

    /*
     * ITALIC ITEM
     */
    public void setItalic() {
        if (textArea.getFont().isItalic())
            return;

        int size = (int) fontSizeSpinner.getValue();
        textArea.setFont(new Font(textArea.getFont().toString(), Font.ITALIC, size));
    }

    /*
     * SCROLLPANE
     */
    private void setScrollPane() {
        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(780, 520));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    }

    /*
     * ICON
     */
    public void setIcon() {
        icon = new ImageIcon("./assets/icon.png");
        this.setIconImage(icon.getImage());
    }

    /*
     * MENUBAR
     */
    public void setMenuBar() {
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(fontMenu);
    }

    /*
     * SET COLOR
     */
    public void setColor() {
        Color color = JColorChooser.showDialog(this, "Choose a color", Color.white);
        textArea.setForeground(color);
    }

    /*
     * CHANGE TEXT SIZE
     */
    public void setFontSize() {
        SpinnerModel model = new SpinnerNumberModel(16, 8, 72, 1);
        fontSizeSpinner.setModel(model);
        fontSizeSpinner.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        fontSizeSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int size = (int) fontSizeSpinner.getValue();
                font = new Font("Arial", Font.PLAIN, size);
                textArea.setFont(font);
            }
        });
    }

}
