import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MainGUI {

    private String FileAnalyse ;
    private String FileSave;

    private JPanel Panel ;

    private JButton Import ;
    private JButton Analyse ;
    private JButton Export;

    private JTextPane Result;
    private JTextPane CodeInput;
    private JTextField inputTextField;
    private JTextField outputTextField;


    public MainGUI() {

        Import.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileNameExtensionFilter filter = new FileNameExtensionFilter( "VC source code (.vc)", "vc");
                JFileChooser ImportFile = new JFileChooser();
                ImportFile.setFileFilter(filter);

                int select = ImportFile.showOpenDialog(null);
                if (select == JFileChooser.APPROVE_OPTION) {
                    String FileName = ImportFile.getSelectedFile().getName() ;
                    String dir = ImportFile.getCurrentDirectory().toString();
                    FileAnalyse = dir + "\\" + FileName;
                    ToastMessage message = new ToastMessage("You opened " + ImportFile.getSelectedFile().getName());
                    message.display();


                    // show code input :

                    String Content = "" ;
                    BufferedReader br = null;
                    try {

                        br = new BufferedReader(new FileReader(FileAnalyse));
                        String textInALine = br.readLine();
                        while (textInALine  != null) {

                            Content += textInALine +"\n" ;
                            textInALine = br.readLine();

                        }
                    } catch (IOException a) {
                        a.printStackTrace();
                    } finally {
                        try {
                            br.close();
                        } catch (IOException a) {
                            a.printStackTrace();
                        }
                    }

                    CodeInput.setText("");
                    CodeInput.setText(Content);
                }
            }
        });

        Export.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Set extension to txt
                FileNameExtensionFilter filter = new FileNameExtensionFilter( "Text File (.txt)", "txt");
                JFileChooser SaveFile = new JFileChooser();
                SaveFile.setFileFilter(filter);
                SaveFile.setAcceptAllFileFilterUsed(false);

                int select = SaveFile.showSaveDialog(null);
                if (select == JFileChooser.APPROVE_OPTION) {
                    String FileName = SaveFile.getSelectedFile().getName();
                    if (!FileName.endsWith(".txt")) {
                        FileName = FileName + ".txt";
                    }
                    String dir = SaveFile.getCurrentDirectory().toString();
                    FileSave = dir + "\\" + FileName;
                }

            }
        });

        Analyse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if((FileAnalyse != null)&&(FileSave != null))
                    {
                        Main tool = new Main() ;
                        tool.scanFile(FileAnalyse , FileSave);
                        Result.setText("");
                        Result.setText(tool.Result);
                        tool.setResult("");
                    } else {
                        ToastMessage message = new ToastMessage("You need to set both import and export path!");
                        message.display() ;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
    }

    public static void main(String[] args )
    {
        JFrame GUI = new JFrame("VC Scanner") ;
        GUI.setResizable(false);
        GUI.setContentPane(new MainGUI().Panel);
        GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GUI.pack();
        GUI.setVisible(true);
        GUI.setName("Compiler_Exe");
        GUI.setSize(new Dimension(900,600));
    }
}

class ToastMessage extends JFrame {
    public ToastMessage(final String message) {
        setUndecorated(true);
        setLayout(new GridBagLayout());
        setBackground(new Color(240,240,240,250));
        setLocationRelativeTo(null);
        setSize(300, 50);
        add(new JLabel(message));

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setShape(new  RoundRectangle2D.Double(0,0,getWidth(),
                        getHeight(), 20, 20));
            }
        });
    }

    public void display() {
        try {
            setOpacity(1);
            setVisible(true);
            Thread.sleep(500);

            //hide the toast message in slow motion
            for (double d = 1.0; d > 0.2; d -= 0.1) {
                Thread.sleep(100);
                setOpacity((float)d);
            }

            // set the visibility to false
            setVisible(false);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
