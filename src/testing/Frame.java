/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import simulator.*;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;
import java.awt.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.text.html.HTMLDocument;

public class Frame extends javax.swing.JFrame {

    DecimalFormat df = new DecimalFormat("#.##");
    boolean temp = true;
    int radSta = 0;
    Logic log = new Logic();
    boolean dd = true;

    public Frame() {
        initComponents();
        goClock goCl=new goClock(clock_tf);
        new Thread(goCl).start();
        log.setSpeed();
        log.cbFill(log.getSpeed(), this.speed_cb);
        log.setRoad();
        log.cbFill(log.getRoad(), this.road_cb);
        log.setWeather();
        log.cbFill(log.getWeather(), this.weather_cb);






    }

    class StartCar implements Runnable {

        JComboBox wet;
        JComboBox spd;
        JComboBox rd;
        JTextField f;
        JTextField fu;
        JTextField bfu;
        JTextField m;
        JTextField tm;
        JTextField rad;

        public StartCar(JComboBox wet, JComboBox spd, JComboBox rd, JTextField f, JTextField fu, JTextField bfu, JTextField m, JTextField tm, JTextField rad) {
            this.wet = wet;
            this.spd = spd;
            this.rd = rd;
            this.f = f;
            this.fu = fu;
            this.bfu = bfu;
            this.m = m;
            this.tm = tm;
            this.rad = rad;
        }

        @Override
        public void run() {
            
            double totmile = 0;


            while (dd) {

                Parts r = (Parts) this.rd.getSelectedItem();
                Parts w = (Parts) this.wet.getSelectedItem();
                Parts s = (Parts) this.spd.getSelectedItem();
                log.setfuelUsage(bfu, s, r, w);
                fu.setText(log.getFuelUsage() + "");
                log.setfuelUsage(bfu, s, r, w);
                log.setFuel(f);
                fu.setText("" + df.format(log.getFuelUsage()));
                log.setMilage();
                m.setText("" + df.format(log.getMilage()) + " km");
                log.removeFuel();
                f.setText(df.format(log.getFuel()) + "");
                log.setTotalMilage(s);
                totmile = totmile + (Double.parseDouble(s.getElement1()) / 60);
                tm.setText(df.format(totmile) + " km");
                rad.setText(log.radio[radSta]);
                //tm.setText(df.format(log.getTotalMilage())+" km");
                if (log.getFuel() < (log.getFuelUsage() / 60)) {
                    dd = false;
                }
                try {
                    Thread.sleep(250);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }
    class goClock implements Runnable{
        JTextField clock;

        public goClock(JTextField clock) {
            this.clock = clock;
        }
        

        @Override
        public void run() {
            while (true) {                
                for (int i = 0; i < 25; i++) {
                    for (int j = 0; j < 61; j++) {
                        clock.setText(i+" : "+j);
                        try {
                            Thread.sleep(250);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
        
    }

       
    class Logic {

        DecimalFormat df = new DecimalFormat("#.##");
        private double fuel;
        private double baseFuelUsage;
        private double fuelUsage;
        private double milage;
        private double totalMilage = 0;
        private List<Parts> speed = new ArrayList<Parts>();
        private List<Parts> weather = new ArrayList<Parts>();
        //private List<Parts> radio = new ArrayList<Parts>();
        private List<Parts> road = new ArrayList<Parts>();
        private String[] radio = {"Rock Radio  87.5MHz", "Funky All Night  89.6MHz", "Dark Metal  91.2MHz", "Classic  94.4MHz", "JazzJazzJazz  96.7MHz", "CinciLinci  99.9MHz"};

        public Parts addParts(String mask, String value) {
            Parts temp = new Parts(mask, value);
            return temp;


        }

        public String getRadioStation(int b) {
            return this.radio[b];
        }

        public void setfuelUsage(JTextField b, Parts s, Parts w, Parts r) {
            double spee = Double.parseDouble(s.element2);
            double wea = Double.parseDouble(w.element2);
            double roa = Double.parseDouble(r.element2);
            double bfu = Double.parseDouble(b.getText());
            this.fuelUsage = bfu * spee * wea * roa;
        }

        public void setMilage() {
            this.milage = (this.fuel / this.fuelUsage) * 100;
        }

        public double getMilage() {
            return milage;
        }

        public void setTotalMilage(Parts spe) {
            this.totalMilage = this.totalMilage + (Integer.parseInt(spe.getElement1()) / 60);
        }

        public double getTotalMilage() {
            return totalMilage;
        }

        public void setFuel(JTextField fuel) {
            double maxFuel = 60;
            if (Double.parseDouble(fuel.getText()) < maxFuel) {
                this.fuel = Double.parseDouble(fuel.getText());
            } else {
                this.fuel = maxFuel;
            }

        }

        public double getFuel() {
            return fuel;
        }

        public void removeFuel() {
            this.fuel = this.fuel - (this.fuelUsage / 60);
        }

        public double getFuelUsage() {
            return fuelUsage;
        }

        public void setBaseFuelUsage(JTextField a) {

            this.baseFuelUsage = Double.parseDouble(a.getText());
        }

        public void setSpeed() {
            this.speed.add(addParts("0", "0"));
            this.speed.add(addParts("20", "1.3"));
            this.speed.add(addParts("40", "1.2"));
            this.speed.add(addParts("60", "1.1"));
            this.speed.add(addParts("80", "1"));
            this.speed.add(addParts("100", "1.1"));
            this.speed.add(addParts("120", "1.2"));
            this.speed.add(addParts("140", "1.3"));
            this.speed.add(addParts("160", "1.4"));
            this.speed.add(addParts("180", "1.5"));
            this.speed.add(addParts("200", "1.6"));
        }
//        public void steRadio(){
//            this.radio.add(addParts("radio rock", ""));
//            this.radio.add(addParts("radio jazz", ""));
//            this.radio.add(addParts("funky all night", ""));
//            this.radio.add(addParts("classic music", ""));
//            this.radio.add(addParts("", ""));
//            this.radio.add(addParts("", ""));
//            this.radio.add(addParts("", ""));
//            this.radio.add(addParts("", ""));
//            
//        }

        public List<Parts> getSpeed() {
            return speed;
        }

        public void setRoad() {
            this.road.add(addParts("extra-urban", "1"));
            this.road.add(addParts("urban", "1.3"));
            this.road.add(addParts("combinede", "1.15"));

        }

        public List<Parts> getRoad() {
            return road;
        }

        public void setWeather() {
            this.weather.add(addParts("sunny", "1"));
            this.weather.add(addParts("rain", "1.2"));
            this.weather.add(addParts("snow", "1.3"));
            this.weather.add(addParts("fog", "1.4"));
        }

        public List<Parts> getWeather() {
            return weather;
        }

        public void cbFill(List lista, JComboBox jcb) {
            Iterator<Parts> it = lista.iterator();
            jcb.removeAllItems();
            while (it.hasNext()) {
                Parts temp = it.next();
                jcb.addItem(temp);

            }
        }
    }

    class Parts {

        private String element1;
        private String element2;

        public Parts(String element1, String element2) {
            this.element1 = element1;
            this.element2 = element2;
        }

        public String getElement1() {
            return element1;
        }

        public String getElement2() {
            return element2;
        }

        @Override
        public String toString() {
            return getElement1();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        speed_cb = new javax.swing.JComboBox();
        weather_cb = new javax.swing.JComboBox();
        road_cb = new javax.swing.JComboBox();
        start_b = new javax.swing.JButton();
        radio_tf = new javax.swing.JTextField();
        fuel_tf = new javax.swing.JTextField();
        baseFuelUsage_tf = new javax.swing.JTextField();
        fuelUsage_tf = new javax.swing.JTextField();
        mileage_tf = new javax.swing.JTextField();
        totalMileage_tf = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel8 = new javax.swing.JLabel();
        radioMinus_b = new javax.swing.JButton();
        radioPlus_b = new javax.swing.JButton();
        clock_tf = new javax.swing.JTextField();
        systemTime_JL = new javax.swing.JLabel();
        systemTime_TF = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        speed_cb.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        weather_cb.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        road_cb.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        start_b.setText("Start");
        start_b.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                start_bActionPerformed(evt);
            }
        });

        radio_tf.setText("radio stanica");

        fuel_tf.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        fuel_tf.setText("10");
        fuel_tf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fuel_tfActionPerformed(evt);
            }
        });

        baseFuelUsage_tf.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        baseFuelUsage_tf.setText("4.5");

        fuelUsage_tf.setEditable(false);

        mileage_tf.setEditable(false);
        mileage_tf.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        mileage_tf.setText("0");

        totalMileage_tf.setEditable(false);
        totalMileage_tf.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        totalMileage_tf.setText("0");
        totalMileage_tf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalMileage_tfActionPerformed(evt);
            }
        });

        jLabel1.setText("speed");

        jLabel2.setText("waether");

        jLabel3.setText("road");

        jLabel4.setText("fuel");

        jLabel5.setText("bfu");

        jLabel6.setText("fuel usage");

        jLabel7.setText("milage");

        jLabel8.setText("total m.");

        radioMinus_b.setText("<<");
        radioMinus_b.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioMinus_bActionPerformed(evt);
            }
        });

        radioPlus_b.setText(">>");
        radioPlus_b.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioPlus_bActionPerformed(evt);
            }
        });

        clock_tf.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        clock_tf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clock_tfActionPerformed(evt);
            }
        });

        systemTime_TF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                systemTime_TFActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(93, 93, 93)
                                .addComponent(jLabel1)
                                .addGap(80, 80, 80)
                                .addComponent(jLabel2)
                                .addGap(77, 77, 77)
                                .addComponent(jLabel3))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(62, 62, 62)
                                .addComponent(jLabel4)))
                        .addGap(0, 169, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(radio_tf, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(17, 17, 17)
                                        .addComponent(jLabel8)
                                        .addGap(19, 19, 19))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel5)
                                        .addGap(95, 95, 95))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(24, 24, 24)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(clock_tf)
                                                    .addComponent(fuel_tf, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE))
                                                .addGap(55, 55, 55)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(mileage_tf, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(baseFuelUsage_tf, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(23, 23, 23)
                                                .addComponent(jLabel7)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(totalMileage_tf, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(fuelUsage_tf, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel6)))))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(radioMinus_b)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(radioPlus_b, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(start_b, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(speed_cb, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(weather_cb, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(road_cb, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(142, 142, 142)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(systemTime_JL, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(systemTime_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(speed_cb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(weather_cb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(road_cb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(radio_tf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(radioPlus_b)
                            .addComponent(radioMinus_b))
                        .addGap(9, 9, 9))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(clock_tf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(baseFuelUsage_tf, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fuelUsage_tf, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fuel_tf, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(mileage_tf, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(totalMileage_tf, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(start_b, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(systemTime_JL)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(systemTime_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(jLabel4)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void totalMileage_tfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalMileage_tfActionPerformed
    }//GEN-LAST:event_totalMileage_tfActionPerformed

    private void start_bActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_start_bActionPerformed
        StartCar st = new StartCar(weather_cb, speed_cb, road_cb, fuel_tf, fuelUsage_tf, baseFuelUsage_tf, mileage_tf, totalMileage_tf, radio_tf);

        if (temp) {
            dd=true;
            
            new Thread(st).start();
            this.start_b.setBackground(Color.RED);
            this.start_b.setText("Pause");
            temp = false;
            
        } else {
            dd=false;
            this.start_b.setBackground(Color.GREEN);
            this.start_b.setText("Start");
            temp = true;
        }
    }//GEN-LAST:event_start_bActionPerformed

    private void fuel_tfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fuel_tfActionPerformed
    }//GEN-LAST:event_fuel_tfActionPerformed

    private void radioMinus_bActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioMinus_bActionPerformed
        if (this.radSta == 0) {
            this.radSta = log.radio.length-1;
        } else {
            this.radSta--;
        }
    }//GEN-LAST:event_radioMinus_bActionPerformed

    private void radioPlus_bActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioPlus_bActionPerformed
        if(this.radSta==log.radio.length-1){
           this.radSta = 0; 
        }else{
            this.radSta++;
        }
    }//GEN-LAST:event_radioPlus_bActionPerformed

    private void clock_tfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clock_tfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clock_tfActionPerformed

    private void systemTime_TFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_systemTime_TFActionPerformed

        Date date = new Date();
        String str = String.format("Current Date/Time : %tc", date );
        systemTime_TF.setText(str);
        

    }//GEN-LAST:event_systemTime_TFActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField baseFuelUsage_tf;
    private javax.swing.JTextField clock_tf;
    private javax.swing.JTextField fuelUsage_tf;
    private javax.swing.JTextField fuel_tf;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JTextField mileage_tf;
    private javax.swing.JButton radioMinus_b;
    private javax.swing.JButton radioPlus_b;
    private javax.swing.JTextField radio_tf;
    private javax.swing.JComboBox road_cb;
    private javax.swing.JComboBox speed_cb;
    private javax.swing.JButton start_b;
    private javax.swing.JLabel systemTime_JL;
    private javax.swing.JTextField systemTime_TF;
    private javax.swing.JTextField totalMileage_tf;
    private javax.swing.JComboBox weather_cb;
    // End of variables declaration//GEN-END:variables
}
