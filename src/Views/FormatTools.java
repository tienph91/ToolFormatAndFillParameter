
package Views;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import Controls.FormatAndFillParams;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FormatTools extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    FormatTools frame = new FormatTools();
                    frame.setVisible(true);
                    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                    frame.setLocation(dim.width / 2 - frame.getSize().width / 2,
                            dim.height / 2 - frame.getSize().height / 2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public FormatTools() {

        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "WikiTeX");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 820, 476);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JScrollPane scrollPane_2 = new JScrollPane();
        scrollPane_2.setBounds(413, 35, 393, 391);
        contentPane.add(scrollPane_2);

        final JTextArea txtSqlEnd = new JTextArea();
        txtSqlEnd.setEditable(false);
        txtSqlEnd.setTabSize(4);
        scrollPane_2.setViewportView(txtSqlEnd);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 35, 393, 317);
        contentPane.add(scrollPane);

        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(10, 372, 391, 54);
        contentPane.add(scrollPane_1);

        final JTextArea txtParams = new JTextArea();

        txtParams.setTabSize(4);
        scrollPane_1.setViewportView(txtParams);

        final JTextArea txtSqlStart = new JTextArea();
        txtSqlStart.setTabSize(4);
        scrollPane.setViewportView(txtSqlStart);
        txtSqlStart.requestFocus();

        JLabel lblNewLabel = new JLabel("sql");
        lblNewLabel.setBounds(10, 19, 46, 14);
        contentPane.add(lblNewLabel);

        JLabel lblParams = new JLabel("params");
        lblParams.setBounds(10, 357, 46, 14);
        contentPane.add(lblParams);

        final JLabel txtInfo = new JLabel("");
        txtInfo.setForeground(Color.RED);
        txtInfo.setBounds(413, 433, 393, 14);
        contentPane.add(txtInfo);

        JButton btnExecute = new JButton("Execute And Copy To ClipBoard");
        btnExecute.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {

                String sql = txtSqlStart.getText();
                String params = txtParams.getText();

                FormatAndFillParams tool = new FormatAndFillParams();
                txtSqlEnd.setText(tool.extractResult(sql, params));
                txtInfo.setText("Successfully copied to clipboard");

                StringSelection selection = new StringSelection(txtSqlEnd.getText());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, selection);

                Timer t = new Timer(8000, new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        txtInfo.setText("");
                    }
                });
                t.start();
            }
        });
        btnExecute.setBounds(586, 7, 208, 23);
        contentPane.add(btnExecute);

        JComboBox cmbOption = new JComboBox();
        cmbOption.setModel(new DefaultComboBoxModel(new String[] { "FillParms", "Format", "FillParams And Format" }));
        cmbOption.setBounds(413, 8, 163, 20);
        contentPane.add(cmbOption);

        txtParams.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent arg0) {
                if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
                    String sql = txtSqlStart.getText();
                    String params = txtParams.getText();

                    FormatAndFillParams tool = new FormatAndFillParams();
                    txtSqlEnd.setText(tool.extractResult(sql, params));
                    txtInfo.setText("Successfully copied to clipboard");

                    StringSelection selection = new StringSelection(txtSqlEnd.getText());
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(selection, selection);

                    Timer t = new Timer(8000, new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            txtInfo.setText("");
                        }
                    });
                    t.start();
                }
            }
        });
    }
}
