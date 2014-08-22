package leocarbon.pnf;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class Options extends JPanel implements ActionListener {
    public JCheckBox autoscroll;
    public JCheckBox doneautoscroll;
    
    public JCheckBox clear;
            
    public JCheckBox fileout;
    public JButton filechoose;
    public JFileChooser filelocation;
    
    public JCheckBox alert;
    
    public static boolean AutoScrollDuringProcess;
    public static boolean AutoScrollAfterFinish = true;
    public static boolean AlertAfterFinish = true;
    public static boolean WriteToFile = false;
    public static boolean ClearOutputAfterFinish = false;
    
    public Options() {
        setLayout(new GridBagLayout());
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        
        JPanel process = new JPanel(new GridBagLayout()); {
            autoscroll = new JCheckBox("Scroll",false);
            autoscroll.setActionCommand("autoscroll");
            autoscroll.addActionListener(this);
            autoscroll.setEnabled(false);
            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 2;
            process.add(autoscroll,c);
        }
        process.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),"During Process: "));
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        add(process,c);
        
        JPanel output = new JPanel(new GridBagLayout()); {
            fileout = new JCheckBox("Output to file ...",false);
            fileout.setActionCommand("fileout");
            fileout.addActionListener(this);
            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 1;
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
        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.BOTH;
        add(finish,c);
        
        setBorder(BorderFactory.createEmptyBorder(16,16,16,16));
    }

    @Override
    public void actionPerformed(ActionEvent AE) {
        switch (AE.getActionCommand()) {
            case "autoscroll":
                break;
            case "doneautoscroll":
                if(doneautoscroll.isSelected() == true){
                    AutoScrollAfterFinish = true;
                } else {
                    AutoScrollAfterFinish = false;
                } break;
            case "clear":
                if(clear.isSelected() == true){
                    ClearOutputAfterFinish = true;
                } else {
                    ClearOutputAfterFinish = false;
                } break;
            case "fileout":
                if(fileout.isSelected() == true){
                    filechoose.setEnabled(true);
                    WriteToFile = true;
                } else {
                    filechoose.setEnabled(false);
                    WriteToFile = false;
                } break;
            case "choosefile":
                break;
            case "alert":
                if(alert.isSelected() == true){
                    AlertAfterFinish = true;
                } else {
                    AlertAfterFinish = false;
                } break;
        }
    }
}
