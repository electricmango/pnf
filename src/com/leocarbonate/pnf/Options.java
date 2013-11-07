package com.leocarbonate.pnf;

import static com.leocarbonate.pnf.PrimeNumberFinder.shouldFill;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.text.DefaultCaret;

public class Options extends JPanel implements ActionListener{
    public static JCheckBox autoscroll;
    public static JCheckBox doneautoscroll;
    
    public static JCheckBox fileout;
    public static JButton filechoose;
    public static JFileChooser filelocation;
    
    public static JCheckBox alert;
    
    public static boolean AutoScrollDuringProcess;
    public static boolean AutoScrollAfterFinish = true;
    public static boolean AlertAfterFinish = true;
    public static boolean WriteToFile = false;
    
    public Options(){
        super(new GridBagLayout());
        
        GridBagConstraints c = new GridBagConstraints();
        if(shouldFill) {
            c.fill = GridBagConstraints.BOTH;
        }
        
        autoscroll = new JCheckBox("Auto Scroll During Process",false);
        autoscroll.setActionCommand("autoscroll");
        autoscroll.addActionListener(this);
        autoscroll.setEnabled(false);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0.01;
        c.weighty = 1.0;
        add(autoscroll,c);
        
        doneautoscroll = new JCheckBox("Auto Scroll After Finish",true);
        doneautoscroll.setActionCommand("doneautoscroll");
        doneautoscroll.addActionListener(this);
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0.01;
        c.weighty = 1.0;
        add(doneautoscroll,c);
        
        fileout = new JCheckBox("Output to file ...",false);
        fileout.setActionCommand("fileout");
        fileout.addActionListener(this);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0.01;
        c.weighty = 1.0;
        add(fileout,c);
        
        filechoose = new JButton("Choose file");
        filechoose.setActionCommand("choosefile");
        filechoose.addActionListener(this);
        filechoose.setEnabled(false);
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0.01;
        c.weighty = 1.0;
        add(filechoose,c);
        
        alert = new JCheckBox("Alert on finish",true);
        alert.setActionCommand("alert");
        alert.addActionListener(this);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0.01;
        c.weighty = 1.0;
        add(alert,c);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if("autoscroll".equals(ae.getActionCommand())){
        } else if("doneautoscroll".equals(ae.getActionCommand())){
            if(doneautoscroll.isSelected() == true){
                AutoScrollAfterFinish = true;
            } else {
                AutoScrollAfterFinish = false;
            }
        } else if("fileout".equals(ae.getActionCommand())){
            if(fileout.isSelected() == true){
                filechoose.setEnabled(true);
                WriteToFile = true;
            } else {
                filechoose.setEnabled(false);
                WriteToFile = false;
            }
        } else if("choosefile".equals(ae.getActionCommand())){
            
        } else if("alert".equals(ae.getActionCommand())){
            if(alert.isSelected() == true){
                AlertAfterFinish = true;
            } else {
                AlertAfterFinish = false;
            }
        }
    }
}
