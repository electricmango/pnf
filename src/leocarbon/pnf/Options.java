package leocarbon.pnf;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;
import static leocarbon.pnf.PrimeNumberFinder.PNF;

public class Options extends JPanel implements ActionListener {
    public JCheckBox autoscroll;
    public JCheckBox count;
    public JCheckBox fileout;
    public JButton filechoose;
    public JFileChooser filelocation;
    public JCheckBox alert;
    public JCheckBox doneautoscroll;
    public JCheckBox clear;
    public JCheckBox logging;
    public JLabel info;
    
    public static boolean AutoScrollDuringProcess = true;
    public static boolean countb = true;
    public static boolean AutoScrollAfterFinish = true;
    public static boolean AlertAfterFinish = true;
    public static boolean WriteToFile = false;
    public static boolean ClearOutputAfterFinish = false;
    public static boolean dologging = false;
    
    public Options() {
        setLayout(new GridBagLayout());
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        
        JPanel process = new JPanel(new GridBagLayout()); {
            autoscroll = new JCheckBox("Scroll",true);
            autoscroll.setActionCommand("autoscroll");
            autoscroll.addActionListener(this);
            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 2;
            process.add(autoscroll,c);
            
            count = new JCheckBox("Extra count data",true);
            count.setActionCommand("calc");
            count.addActionListener(this);
            count.setEnabled(false);
            c.gridy = 1;
            process.add(count,c);
        }
        process.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),"During Process: "));
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.BOTH;
        add(process,c);
        
        JPanel output = new JPanel(new GridBagLayout()); {
            fileout = new JCheckBox("Output to file ...",false);
            fileout.setActionCommand("fileout");
            fileout.addActionListener(this);
            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 1;
            c.fill = GridBagConstraints.HORIZONTAL;
            output.add(fileout,c);

            filechoose = new JButton("Choose file");
            filechoose.setActionCommand("choosefile");
            filechoose.addActionListener(this);
            filechoose.setEnabled(false);
            c.gridx = 0;
            c.gridy = 1;
            output.add(filechoose,c);
        }
        output.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),"Output"));
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        add(output,c);
        
        JPanel finish = new JPanel(new GridBagLayout()); {
            alert = new JCheckBox("Alert",true);
            alert.setActionCommand("alert");
            alert.addActionListener(this);
            c.gridx = 0;
            c.gridy = 0;
            c.fill = GridBagConstraints.HORIZONTAL;
            finish.add(alert,c);

            doneautoscroll = new JCheckBox("Auto Scroll",true);
            doneautoscroll.setActionCommand("doneautoscroll");
            doneautoscroll.addActionListener(this);
            c.gridx = 0;
            c.gridy = 1;
            finish.add(doneautoscroll,c);

            clear = new JCheckBox("Clear Output",false);
            clear.setActionCommand("clear");
            clear.addActionListener(this);
            c.gridx = 0;
            c.gridy = 2;
            finish.add(clear,c);
        }
        finish.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),"After Finish: "));
        c.gridx = 1;
        c.gridy = 0;
        c.gridheight = 2;
        c.fill = GridBagConstraints.BOTH;
        add(finish,c);
        
        JPanel loggingp = new JPanel(new GridBagLayout()); {
            logging = new JCheckBox("Logging",false);
            logging.setActionCommand("logging");
            logging.addActionListener(this);
            c.gridx = 0;
            c.gridheight = 1;
            c.fill = GridBagConstraints.HORIZONTAL;
            loggingp.add(logging);
        }
        loggingp.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),"Logging: "));
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 2;
        c.gridy = 0;
        c.gridheight = 2;
        add(loggingp,c);
        
        info = new JLabel("<html>Prime Number Finder is by leocarbon.<br>github.com/leocarbon/pnf</html>");
        info.setHorizontalAlignment(SwingConstants.CENTER);
        c.gridx = 0;
        c.gridy = 2;
        c.gridheight = 1;
        c.gridwidth = 4;
        add(info,c);
        setBorder(BorderFactory.createEmptyBorder(16,16,16,16));
    }

    @Override
    public void actionPerformed(ActionEvent AE) {
        switch (AE.getActionCommand()) {
            case "autoscroll":
                AutoScrollDuringProcess = autoscroll.isSelected();
                if(AutoScrollDuringProcess){
                    DefaultCaret outcaret = (DefaultCaret)PNF.out.getCaret();
                    outcaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
                    AutoScrollAfterFinish = true;
                } else {
                    DefaultCaret outcaret = (DefaultCaret)PNF.out.getCaret();
                    outcaret.setUpdatePolicy(DefaultCaret.UPDATE_WHEN_ON_EDT);
                }
                break;
            case "calc":
                countb = count.isSelected();
                break;
            case "doneautoscroll":
                if(doneautoscroll.isSelected()){
                    AutoScrollAfterFinish = true;
                } else {
                    AutoScrollAfterFinish = false;
                } break;
            case "clear":
                if(clear.isSelected()){
                    ClearOutputAfterFinish = true;
                } else {
                    ClearOutputAfterFinish = false;
                } break;
            case "fileout":
                if(fileout.isSelected()){
                    filechoose.setEnabled(true);
                    WriteToFile = true;
                } else {
                    filechoose.setEnabled(false);
                    WriteToFile = false;
                } break;
            case "choosefile":
                break;
            case "alert":
                if(alert.isSelected()){
                    AlertAfterFinish = true;
                } else {
                    AlertAfterFinish = false;
                } break;
            case "logging":
                dologging = logging.isSelected();
                break;
        }
    }
}
