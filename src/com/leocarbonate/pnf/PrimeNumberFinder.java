package com.leocarbonate.pnf;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingWorker;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class PrimeNumberFinder extends JFrame implements ActionListener{
    public static JToggleButton toggle;
    public static JProgressBar progress;
    
    public static JTextArea out;
    public static JScrollPane outscroll;
    public static JTextField minimum;
    public static JTextField maximum;
    public static JButton setmax;
    public static JPanel program;
    
    public static JLabel tested;
    public static JLabel counted;
    public static JLabel left;
    public static JLabel timee;
    public static JLabel timel;
    public static JLabel timed;
    public static JLabel status;
    
    public static File output;
    public static BufferedWriter writer;
    public static FileWriter filer;
    
    public static JPanel options;
    
    private static getJob gj;
    
    private int max = 2;
    private int min = 0;
    public static boolean done = false;
    
    byte entered = 0;
    
    final static boolean shouldFill = true;
    
    
    public PrimeNumberFinder() throws IOException{
        super("Prime Number Finder");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(400,400);
                
        GridBagConstraints c = new GridBagConstraints();
        if(shouldFill) {
            c.fill = GridBagConstraints.BOTH;
        }
        
        program = new JPanel(new GridBagLayout());
        
        toggle = new JToggleButton("Start");
        toggle.setActionCommand("start");
        toggle.addActionListener(this);
        c.gridx = 5;
        c.gridy = 3;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0.8;
        c.weighty = 0.001;
        program.add(toggle,c);
        
        progress = new JProgressBar(0,max);
        progress.setValue(0);
        progress.setStringPainted(true);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 5;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.weighty = 0.001;
        program.add(progress,c);
        
        BufferedImage myPicture = ImageIO.read(ClassLoader.getSystemResource("assets/img/lcsoft.png"));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        c.gridx = 5;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 2;
        c.weightx = 1.0;
        c.weighty = 0.001;
        program.add(picLabel,c);
    
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 5;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.weighty = 0.01;
        program.add(new JPanel(),c);
        
        out = new JTextArea(5, 10);
        out.setMargin(new Insets(5,5,5,5));
        out.setEditable(false);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.weighty = 1.0;
        program.add(new JScrollPane(out),c);
        
        minimum = new JTextField("Minimum",8);
        minimum.setActionCommand("setmax");
        minimum.addActionListener(this);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.weighty = 0.001;
        program.add(minimum,c);
        
        maximum = new JTextField("Maximum",8);
        maximum.selectAll();
        maximum.setActionCommand("setmax");
        maximum.addActionListener(this);
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.weighty = 0.001;
        program.add(maximum,c);
        
        setmax = new JButton("Set Maximum");
        setmax.setActionCommand("setmax");
        setmax.addActionListener(this);
        c.gridx = 2;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0.8;
        c.weighty = 0.001;
        program.add(setmax,c);
        
        JPanel numbers = new JPanel(new GridLayout(1,0));
        tested = new JLabel("Tested: 0");
        numbers.add(tested);
        
        counted = new JLabel("Counted: 0");
        numbers.add(counted);
        
        left = new JLabel("Left: 2");
        numbers.add(left);
        
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 6;
        c.gridheight = 1;
        c.weightx = 0.8;
        c.weighty = 0.001;
        program.add(numbers,c);
        
        JPanel time = new JPanel(new GridLayout(1,0));
        timee = new JLabel("Time Elapsed: 00:00:00");
        time.add(timee);
        
        timel = new JLabel("Estimated Time Left: null");
        time.add(timel);
        
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 6;
        c.gridheight = 1;
        c.weightx = 0.8;
        c.weighty = 0.001;
        program.add(time,c);
        
        options = new JPanel();
        options.add(new Options());
        c.gridx = 3;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 2;
        c.weightx = 0.01;
        c.weighty = 1.0;
        program.add(options,c);
        
        output = new File("Prime Numbers.txt");
        if (!output.exists()) {
            output.createNewFile();
        }
        filer = new FileWriter(output.getAbsoluteFile());
	writer = new BufferedWriter(filer);
	         
        program.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(program);
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public static void main(String[] args) throws IOException {
        PrimeNumberFinder pnf = new PrimeNumberFinder();
    }
    
    public void start(){
        toggle.setActionCommand("stop");
        toggle.setText("Stop");
        if(Options.ClearOutputAfterFinish == true){
            out.setText(null);
        }
        gj = new getJob();
        gj.execute();
        done = false;
    } public void stop(){
        gj.cancel(true);
        gj = null;
        toggle.setActionCommand("start");
        toggle.setText("Start");
        done = true;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if("start".equals(e.getActionCommand())){
            start();
        } else if("stop".equals(e.getActionCommand())){
            stop();
        } else if("setmax".equals(e.getActionCommand())){
            ++entered;
            
            setmax.setSelected(true);
            
            if(setmax.isSelected() == true){
                ++entered;
            }
            String maxs = maximum.getText();
            String mins = minimum.getText();
            
            //text if string
            
            double d = Double.parseDouble(maxs);
            max = (int)d;
            double f = Double.parseDouble(mins);
            min = (int)f;
            System.out.println("Set min to: " + min);
            System.out.println("Set max to: " + max);
            left.setText("Left: " + (max - min));
            
            progress.setMaximum(max - min);
            if(setmax.isSelected() == true && entered == 2){
                toggle.setSelected(true);
                start();
                entered = 0;
            }
        }
    }
    
    public class getJob extends SwingWorker<Integer, Integer>{
        boolean end;
        int p = 0;
        int i;
        
        @Override
        protected Integer doInBackground() throws Exception {
            int j = min, k = 0;
            while (!isCancelled()) {
                if(j < 2){
                    j = 2;
                }
                if(isPrime(j)) {
                    ++k;
                    //System.out.println(j);
                    out.append(Integer.toString(j) + "\n");
                    
                    tested.setText("Tested: " + j);
                    counted.setText("Counted: " + k);
                    left.setText("Left: " + ((max - min) - j));
                }
                    
                progress.setValue(j);
                ++j;
                if(!(j <= max)){
                    break;
                }
            }
            return i;
        }
        
        public boolean isPrime(int j){
            for(int asd=2; asd<j; asd++){
                if(j%asd == 0){
                    return false;
                }
            }
            return true;
        }
        @Override
        public void done(){
            done = true;
            
            if(Options.AlertAfterFinish == true){
                Toolkit.getDefaultToolkit().beep();
            }
            progress.setValue(progress.getMinimum());
            
            if(Options.AutoScrollAfterFinish == true){
                out.setCaretPosition(out.getDocument().getLength());
            }
            
            toggle.setActionCommand("start");
            toggle.setText("Start");
            
            toggle.setSelected(false);
            setmax.setSelected(false);
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(PrimeNumberFinder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}