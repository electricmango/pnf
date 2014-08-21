package leocarbon.pnf;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

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
        c.fill = GridBagConstraints.BOTH;
        
        autoscroll = new JCheckBox("Auto Scroll During Process",false);
        autoscroll.setActionCommand("autoscroll");
        autoscroll.addActionListener(this);
        autoscroll.setEnabled(false);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        add(autoscroll,c);
        
        doneautoscroll = new JCheckBox("Auto Scroll After Finish",true);
        doneautoscroll.setActionCommand("doneautoscroll");
        doneautoscroll.addActionListener(this);
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        add(doneautoscroll,c);
        
        fileout = new JCheckBox("Output to file ...",false);
        fileout.setActionCommand("fileout");
        fileout.addActionListener(this);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        add(fileout,c);
        
        filechoose = new JButton("Choose file");
        filechoose.setActionCommand("choosefile");
        filechoose.addActionListener(this);
        filechoose.setEnabled(false);
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        add(filechoose,c);
        
        alert = new JCheckBox("Alert on finish",true);
        alert.setActionCommand("alert");
        alert.addActionListener(this);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        add(alert,c);
        
        clear = new JCheckBox("Clear Output After Finish",false);
        clear.setActionCommand("clear");
        clear.addActionListener(this);
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        add(clear,c);
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
