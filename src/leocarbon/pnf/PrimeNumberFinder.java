package leocarbon.pnf;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.text.*;
import static leocarbon.pnf.Options.AutoScrollDuringProcess;
import static leocarbon.pnf.Options.dologging;
import org.apache.log4j.*;
import org.simplericity.macify.eawt.*;

public class PrimeNumberFinder extends JFrame implements ActionListener {
    public static PrimeNumberFinder PNF;
    private static Application eawt;
    
    public static JMenuBar menubar;
    public static JMenu menu;
    
    public JPanel program;
    public Options options;
    public JButton optionsJB;
    public boolean inoptions = false;
    
    public JButton toggle;
    public JProgressBar progress;
    
    public JTextArea out;
    public JScrollPane outscroll;
    public JTextField minimum;
    public JTextField maximum;
    public JButton setmax;
    
    public JLabel tested;
    public JLabel counted;
    public JLabel left;
    public JLabel timee;
    public JLabel timel;
    public JLabel timed;
    public JLabel status;
    
    public File output;
    public BufferedWriter writer;
    public FileWriter filer;
    
    private FindPrimeNumbers finder;
    
    private int max = 2;
    private int min = 0;
    public static boolean done = true;
    
    byte entered = 0;
    
    public static void main(String[] arguments) {
        if(System.getProperty("os.name").toLowerCase().contains("mac")){
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Color Utility");
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        }
        PNF = new PrimeNumberFinder();
    } public PrimeNumberFinder() {
        PropertyConfigurator.configure(getClass().getResource("/leocarbon/pnf/logging/log4j.properties"));
        
        //Extra implementations for Mac OS
        if(System.getProperty("os.name").toLowerCase().contains("mac")){
            eawt = new DefaultApplication();
            eawt.addApplicationListener(new MacOSXHandler());
            eawt.addPreferencesMenuItem();
            eawt.setEnabledPreferencesMenu(true);
            if(dologging) Logger.getLogger(PrimeNumberFinder.class.getName()).trace("Optimized GUI for Mac OS");
        }
        
        setTitle("Prime Number Finder");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GridBagConstraints c = new GridBagConstraints();
        
        program = new JPanel(new GridBagLayout());
        
        minimum = new JTextField("Minimum",8);
        minimum.setActionCommand("enter");
        minimum.addActionListener(this);
        c.gridx = 0;
        c.gridy = 0;
        program.add(minimum,c);
        
        maximum = new JTextField("Maximum",8);
        maximum.selectAll();
        maximum.setActionCommand("enter");
        maximum.addActionListener(this);
        c.gridx = 1;
        program.add(maximum,c);
        
        setmax = new JButton("Set Maximum");
        setmax.setActionCommand("setmax");
        setmax.addActionListener(this);
        c.gridx = 2;
        program.add(setmax,c);
        
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),"space");
        getRootPane().getActionMap().put("space", new AbstractAction(){ @Override public void actionPerformed(ActionEvent e){
            setmax.doClick();
        }});
        
        optionsJB = new JButton("Options");
        optionsJB.setActionCommand("options");
        optionsJB.addActionListener(this);
        c.gridx = 3;
        c.gridwidth = 1;
        c.gridheight = 1;
        program.add(optionsJB,c);
        
        out = new JTextArea(5, 10);
        out.setMargin(new Insets(5,5,5,5));
        out.setEditable(false);
        if(AutoScrollDuringProcess){
            DefaultCaret outcaret = (DefaultCaret)out.getCaret();
            outcaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        }
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 4;
        c.fill = GridBagConstraints.BOTH;
        program.add(new JScrollPane(out),c);
        
        progress = new JProgressBar(0,max);
        progress.setValue(0);
        progress.setStringPainted(true);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 3;
        program.add(progress,c);
        
        toggle = new JButton("Start");
        toggle.setActionCommand("start");
        toggle.addActionListener(this);
        c.gridx = 3;
        c.gridwidth = 1;
        program.add(toggle,c);
        
        JPanel numbers = new JPanel(new GridLayout(1,0)); {
            tested = new JLabel("Tested: 0");
            tested.setHorizontalAlignment(SwingConstants.CENTER);
            numbers.add(tested);

            counted = new JLabel("Counted: 0");
            counted.setHorizontalAlignment(SwingConstants.CENTER);
            numbers.add(counted);

            left = new JLabel("Left: 2");
            left.setHorizontalAlignment(SwingConstants.CENTER);
            numbers.add(left);
        } c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 4;
        program.add(numbers,c);
        
        JPanel time = new JPanel(new GridLayout(1,0)); {
            timee = new JLabel("Time elapsed: 00:00:00.000");
            timee.setHorizontalAlignment(SwingConstants.CENTER);
            time.add(timee);
            /*
            timel = new JLabel("Estimated time left: null");
            timel.setHorizontalAlignment(SwingConstants.CENTER);
            time.add(timel);
                    */
        } c.gridy = 4;
        c.gridwidth = 4;
        program.add(time,c);
        
        program.setBorder(BorderFactory.createEmptyBorder(16,16,16,16));
        add(program);
        
        options = new Options();
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        setMaximumSize(this.getSize());
    }
    
    long stime, ctime, etime;
    long ltime;
    public void start(){
        toggle.setActionCommand("stop");
        toggle.setText("Stop");
        if(Options.ClearOutputAfterFinish == true){
            out.setText(null);
        }
        try {
            if(options.fileout.isSelected()){
                if(output == null) options.choosefile();
                if(!output.exists()) output.createNewFile();
                filer = new FileWriter(output.getAbsoluteFile());
                writer = new BufferedWriter(filer);
            }
        } catch (IOException IOE) {
            Logger.getLogger(PrimeNumberFinder.class.getName()).warn(IOE);
        }
        options.autoscroll.setEnabled(false);
        options.fileout.setEnabled(false);
        options.filechoose.setEnabled(false);
        
        finder = new FindPrimeNumbers();
        finder.execute();
        stime = System.nanoTime();
        done = false;
    } public void stop(){
        finder.cancel(true);
        finder = null;
        
        options.autoscroll.setEnabled(true);
        options.fileout.setEnabled(true);
        if(options.fileout.isSelected()) options.filechoose.setEnabled(true);
        
        output = null;
        filer = null;
        writer = null;
        
        toggle.setActionCommand("start");
        toggle.setText("Start");
        
        toggle.setSelected(false);
        setmax.setSelected(false);
        
        stime = ctime = etime = ltime = 0;
        done = true;
    }
    
    @Override
    public void actionPerformed(ActionEvent AE) {
        switch(AE.getActionCommand()){
            case "options":
                if(!inoptions){
                    remove(program);
                    program.remove(optionsJB);
                    optionsJB.setText("Program");
                    options.add(optionsJB);
                    add(options);
                } else {
                    remove(options);
                    options.remove(optionsJB);
                    optionsJB.setText("Options");
                    program.add(optionsJB);
                    add(program);
                }
                inoptions = !inoptions;
                repaint();
                revalidate();
                break;
            case "start":
                start();
                break;
            case "stop":
                stop();
                break;
            case "enter":
                actionPerformed(new ActionEvent((Object)this,-1,"setmax"));
                setmax.setSelected(true);
                setmax.requestFocus();
                break;
            case "setmax":
                if(done){
                    String maxs, mins;
                    if(maximum.getText().matches("[0-9]+")) maxs = maximum.getText();
                    else maximum.setText(maxs = Integer.toString(Integer.MAX_VALUE));
                    max = Integer.parseInt(maxs);
                    
                    if(minimum.getText().matches("[0-9]+")) mins = minimum.getText();
                    else minimum.setText(mins = "0");
                    min = Integer.parseInt(mins);
                    
                    if(dologging){
                        Logger.getLogger(PrimeNumberFinder.class.getName()).info("Set min to: " + min);
                        Logger.getLogger(PrimeNumberFinder.class.getName()).info("Set max to: " + max);
                    }
                    left.setText("Left: " + (max - min));
                    
                    progress.setMaximum(max - min);
                    if(setmax.isSelected()){
                        toggle.setSelected(true);
                        start();
                    }
                } break;
        }
    }
    
    public class FindPrimeNumbers extends SwingWorker<Integer,Integer> {
        int p = 0;
        int i;
        @Override
        protected Integer doInBackground() {
            int j = min, k = 0, l = max-min;
            while (!isCancelled()){
                if(j < 2) j = 2;
                if(isPrime(j)){
                    ++k;
                        try {
                            if(writer != null) writer.append(Integer.toString(j) + "\n");
                        } catch (IOException IOE) {
                            if(dologging) Logger.getLogger(PrimeNumberFinder.class.getName()).error(IOE);
                        }
                        out.append(Integer.toString(j) + "\n");
                    tested.setText("Tested: " + j);
                    counted.setText("Counted: " + k);
                    left.setText("Left: " + l);
                }
                progress.setValue(j);
                ctime = System.nanoTime();
                etime = (ctime - stime)/1000000;
                //ltime = l/(j/etime)/1000; unused, maybe later.
                
                timee.setText(String.format("Time elapsed: %02d:%02d:%02d.%03d",(etime/1000/60/60)%60,(etime/1000/60)%60,(etime/1000)%60,etime%1000));
                //timel.setText("Estimated time left: "+ltime);
                
                ++j;
                --l;
                if(!(j <= max)) break;
            }
            return i;
        }
        
        public boolean isPrime(int j) {
            for(int d = 2; d < j; ++d) if(j%d == 0) return false;
            return true;
        }
        @Override
        public void done() {
            if(Options.AlertAfterFinish){
                Toolkit.getDefaultToolkit().beep();
            }
            progress.setValue(progress.getMinimum());
            
            if(Options.AutoScrollAfterFinish){
                out.setCaretPosition(out.getDocument().getLength());
            }
            
            stop();
            
            try {
                if(writer != null){
                    writer.flush();
                    writer.close();
                }
            } catch (IOException IOE) {
                if(dologging) Logger.getLogger(FindPrimeNumbers.class.getName()).warn(IOE);
            }
        }
    }
    public class MacOSXHandler implements ApplicationListener {
        @Override
        public void handlePreferences(ApplicationEvent AE) {
            PNF.actionPerformed(new ActionEvent((Object)this, -1, "options"));
            AE.setHandled(true);
        }
        @Override
        public void handleQuit(ApplicationEvent AE) {
            PNF.setVisible(false);
            PNF.dispose();
            PNF = null;
            System.gc();
            AE.setHandled(true);
        } @Override
        public void handleAbout(ApplicationEvent AE) {} @Override
        public void handleOpenApplication(ApplicationEvent AE) {} @Override
        public void handleOpenFile(ApplicationEvent AE) {} @Override
        public void handlePrintFile(ApplicationEvent AE) {} @Override
        public void handleReOpenApplication(ApplicationEvent AE) {}
    }
}