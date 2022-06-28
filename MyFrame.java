
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

    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenu editMenu = new JMenu("Edit");
    JMenuItem openItem = new JMenuItem("Open");
    JMenuItem saveItem = new JMenuItem("Save");
    JMenuItem newWindowItem = new JMenuItem("New Window");
    JMenuItem colorChooserItem = new JMenuItem("Text Color");
    JMenuItem exitItem = new JMenuItem("Exit");
    ImageIcon icon;
    JTextArea textArea = new JTextArea();
    JScrollPane scrollPane;
    JPanel panel = new JPanel();
    Font font = new Font("Arial", Font.PLAIN, 16);
    JSpinner fontSizeSpinner = new JSpinner();

    /*
     * COSTRUCTOR
     */
    MyFrame() {
        this.setTitle("Txt Editor");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());

        setTextArea();
        initPanel();
        setFileMenu();
        setIcon();
        setMenuBar();
        setFontSize();

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
        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        newWindowItem.addActionListener(this);
        exitItem.addActionListener(this);
        colorChooserItem.addActionListener(this);
        //dimensionItem.addActionListener(this);

        newWindowItem.setMnemonic(KeyEvent.VK_N);
        openItem.setMnemonic(KeyEvent.VK_O);
        saveItem.setMnemonic(KeyEvent.VK_S);
        exitItem.setMnemonic(KeyEvent.VK_X);
        fileMenu.setMnemonic(KeyEvent.VK_F);
        colorChooserItem.setMnemonic(KeyEvent.VK_C);
        editMenu.setMnemonic(KeyEvent.VK_E);
        

        colorChooserItem.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        openItem.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        saveItem.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        newWindowItem.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        exitItem.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        fileMenu.add(newWindowItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);

        editMenu.add(colorChooserItem);
        editMenu.add(fontSizeSpinner);
    }

    /*
     * INIT PANEL
     */
    public void initPanel(){
        panel.setBackground(Color.white);
        panel.setPreferredSize(this.getSize());
        panel.add(scrollPane);
        panel.setVisible(true);
    }

    /*
     * TEXTAREA
     */
    public void setTextArea() {
        textArea.setForeground(Color.black);
        textArea.setBackground(Color.white);
        
        
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        textArea.setFont(new Font("Monospace", Font.PLAIN, 16));
        textArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        this.setScrollPane();
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

    /*
     * ACTION LISTENER
     */
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
            dispose();

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
         * SET COLOR
         */
        if(e.getSource() == colorChooserItem){
            setColor();
        }

        /*
         * SET DIMENSION
         */
/*         if(e.getSource() == dimensionItem){
            setDimension();
        }
 */
        /*
         * EXIT
         */
        if (e.getSource() == exitItem) {
            System.exit(0);
        }
    }

}
