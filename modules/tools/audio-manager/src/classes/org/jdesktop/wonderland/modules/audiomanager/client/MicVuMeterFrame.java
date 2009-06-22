/*
 * MicVuMeterFrame.java
 *
 * Created on June 19, 2009, 12:23 PM
 */
package org.jdesktop.wonderland.modules.audiomanager.client;

import org.jdesktop.wonderland.modules.audiomanager.common.VolumeUtil;

import org.jdesktop.wonderland.client.softphone.SoftphoneControl;
import org.jdesktop.wonderland.client.softphone.SoftphoneControlImpl;
import org.jdesktop.wonderland.client.softphone.SoftphoneListener;
import org.jdesktop.wonderland.client.softphone.MicrophoneInfoListener;

import org.jdesktop.wonderland.client.jme.Meter;


import java.io.IOException;

/**
 *
 * @author  jp
 */
public class MicVuMeterFrame extends javax.swing.JFrame implements SoftphoneListener,
	MicrophoneInfoListener, DisconnectListener {

    private AudioManagerClient client;

    private Meter meter;

    public MicVuMeterFrame() {
        initComponents();
    }

    /** Creates new form MicVuMeterFrame */
    public MicVuMeterFrame(AudioManagerClient client) {
	this.client = client;

        initComponents();

	client.addDisconnectListener(this);

        meter = new Meter("Vu:");

        meter.setPreferredSize(vuMeterPanel.getSize());

        meter.setMaxValue(1D);
        meter.setWarningValue(0.90);
        
        meter.setVisible(true);

        vuMeterPanel.add(meter);

        pack();
        validate();

        vuMeterPanel.setVisible(true);

	startVuMeter(true);
    }

    public void disconnected() {
	startVuMeter(false);
    }

    public void startVuMeter(boolean startVuMeter) {
        SoftphoneControl sc = SoftphoneControlImpl.getInstance();

	client.removeDisconnectListener(this);

        sc.removeSoftphoneListener(this);
        sc.removeMicrophoneInfoListener(this);

	if (startVuMeter) {
	    client.addDisconnectListener(this);
            sc.addSoftphoneListener(this);
            sc.addMicrophoneInfoListener(this);

	    try {
	        sc.sendCommandToSoftphone("getMicrophoneVolume");
	    } catch (IOException e) {
		System.out.println("Unable to get Microphone volume:  " 
		    + e.getMessage());
	    }
	}

        sc.startVuMeter(startVuMeter);
	setVisible(startVuMeter);
    }

    public void softphoneVisible(boolean isVisible) {
    }

    public void softphoneMuted(boolean muted) {
    }

    public void softphoneConnected(boolean connected) {
        SoftphoneControlImpl.getInstance().startVuMeter(connected);
    }

    public void softphoneExited() {
    }

    public void microphoneGainTooHigh() {
    }

    private static final int VU_COUNT = 10;

    private int count;

    private double volume;

    public void microphoneData(String data) {
        if (count == VU_COUNT) {
	    count = 0;

            volume = Math.round(Math.sqrt(volume) * 100) / 100D;

            meter.setValue(volume);
            vuMeterLabel.setText(String.valueOf(volume));

	    volume = 0;
        } else {
	    double volume = Double.parseDouble(data);

	    if (volume > this.volume) {
		this.volume = volume;
	    }
	}

	count++;
    }

    public void microphoneVolume(String data) {
	micVolumeSlider.setValue(VolumeUtil.getClientVolume(Double.parseDouble(data)));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        micVolumeSlider = new javax.swing.JSlider();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        vuMeterLabel = new javax.swing.JLabel();
        vuMeterPanel = new javax.swing.JPanel();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        micVolumeSlider.setMajorTickSpacing(1);
        micVolumeSlider.setMaximum(10);
        micVolumeSlider.setPaintLabels(true);
        micVolumeSlider.setPaintTicks(true);
        micVolumeSlider.setSnapToTicks(true);
        micVolumeSlider.setValue(5);
        micVolumeSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                micVolumeSliderStateChanged(evt);
            }
        });

        jLabel1.setText("Microphone Volume:");

        jLabel2.setText("VuMeter Value:");

        vuMeterLabel.setText(" ");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabel1)
                    .add(vuMeterLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 76, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel2))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(micVolumeSlider, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                    .add(vuMeterPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jLabel1))
                    .add(micVolumeSlider, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(jLabel2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(vuMeterLabel))
                    .add(vuMeterPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 27, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void micVolumeSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_micVolumeSliderStateChanged
        SoftphoneControl sc = SoftphoneControlImpl.getInstance();

        double volume = VolumeUtil.getServerVolume(micVolumeSlider.getValue());

        try {
            sc.sendCommandToSoftphone("microphoneVolume=" + volume);
        } catch (IOException e) {
            System.out.println("Unable to send microphone volume command to softphone:  " + e.getMessage());
        }
    }//GEN-LAST:event_micVolumeSliderStateChanged

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
	startVuMeter(false);
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new MicVuMeterFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSlider micVolumeSlider;
    private javax.swing.JLabel vuMeterLabel;
    private javax.swing.JPanel vuMeterPanel;
    // End of variables declaration//GEN-END:variables
}
