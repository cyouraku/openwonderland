/**
 * Project Wonderland
 *
 * Copyright (c) 2004-2008, Sun Microsystems, Inc., All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * $Revision$
 * $Date$
 * $State$
 */
package org.jdesktop.wonderland.modules.artimport.client.jme;

import java.io.File;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author paulby
 */
public class ModuleManagerUI extends javax.swing.JFrame {

    private File parentDir = null;
    private JFileChooser fc = new JFileChooser();

    /** Creates new form ModuleManagerUI */
    public ModuleManagerUI() {
        initComponents();

        parentDirButtonActionPerformed(null);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        parentDirGroup = new javax.swing.ButtonGroup();
        createModulePanel = new javax.swing.JPanel();
        createModuleB = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        moduleNameTF = new javax.swing.JTextField();
        parentDirTF = new javax.swing.JTextField();
        chooseDirB = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        toolsDirCB = new javax.swing.JCheckBox();
        worldDirCB = new javax.swing.JCheckBox();
        foundationDirCB = new javax.swing.JCheckBox();
        samplesDirCB = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        commonPackageCB = new javax.swing.JCheckBox();
        serverPackageCB = new javax.swing.JCheckBox();
        clientPackageCB = new javax.swing.JCheckBox();
        artCB = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        moduleDescriptionTF = new javax.swing.JTextField();

        setTitle("Module Manager");

        createModulePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Create New Module"));

        createModuleB.setText("Create Module");
        createModuleB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createModuleBActionPerformed(evt);
            }
        });

        jLabel1.setText("Module Name :");

        jLabel2.setText("Parent Directory :");

        moduleNameTF.setText("example");
        moduleNameTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moduleNameTFActionPerformed(evt);
            }
        });

        parentDirTF.setEnabled(false);
        parentDirTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parentDirTFActionPerformed(evt);
            }
        });

        chooseDirB.setText("Choose...");
        chooseDirB.setEnabled(false);
        chooseDirB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseDirBActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Module Parent Directory"));

        parentDirGroup.add(toolsDirCB);
        toolsDirCB.setText("Tools");
        toolsDirCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parentDirButtonActionPerformed(evt);
            }
        });

        parentDirGroup.add(worldDirCB);
        worldDirCB.setSelected(true);
        worldDirCB.setText("World");
        worldDirCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parentDirButtonActionPerformed(evt);
            }
        });

        parentDirGroup.add(foundationDirCB);
        foundationDirCB.setText("Foundation");
        foundationDirCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parentDirButtonActionPerformed(evt);
            }
        });

        parentDirGroup.add(samplesDirCB);
        samplesDirCB.setText("Samples");
        samplesDirCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parentDirButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(worldDirCB)
                    .add(foundationDirCB)
                    .add(toolsDirCB)
                    .add(samplesDirCB))
                .addContainerGap(162, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(toolsDirCB)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(worldDirCB)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(foundationDirCB)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(samplesDirCB)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        commonPackageCB.setSelected(true);
        commonPackageCB.setText("Common Package");
        commonPackageCB.setEnabled(false);

        serverPackageCB.setSelected(true);
        serverPackageCB.setText("Server Package");
        serverPackageCB.setEnabled(false);

        clientPackageCB.setSelected(true);
        clientPackageCB.setText("Client Package");
        clientPackageCB.setEnabled(false);

        artCB.setText("Include Art");
        artCB.setToolTipText("Include art resources in the module");

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .add(artCB)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 29, Short.MAX_VALUE)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(clientPackageCB)
                    .add(serverPackageCB))
                .add(35, 35, 35))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(130, Short.MAX_VALUE)
                .add(commonPackageCB)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(17, 17, 17)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(clientPackageCB)
                    .add(artCB))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(commonPackageCB)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(serverPackageCB)
                .add(188, 188, 188))
        );

        jLabel3.setText("Module Description :");

        moduleDescriptionTF.setText("Module Description");

        org.jdesktop.layout.GroupLayout createModulePanelLayout = new org.jdesktop.layout.GroupLayout(createModulePanel);
        createModulePanel.setLayout(createModulePanelLayout);
        createModulePanelLayout.setHorizontalGroup(
            createModulePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(createModulePanelLayout.createSequentialGroup()
                .add(createModulePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(createModulePanelLayout.createSequentialGroup()
                        .add(125, 125, 125)
                        .add(createModuleB))
                    .add(createModulePanelLayout.createSequentialGroup()
                        .add(42, 42, 42)
                        .add(jLabel2)
                        .add(18, 18, 18)
                        .add(createModulePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(createModulePanelLayout.createSequentialGroup()
                                .add(createModulePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .add(org.jdesktop.layout.GroupLayout.LEADING, parentDirTF, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(chooseDirB))))
                    .add(createModulePanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(createModulePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(jLabel3)
                            .add(jLabel1))
                        .add(18, 18, 18)
                        .add(createModulePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(moduleDescriptionTF, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
                            .add(moduleNameTF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 135, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        createModulePanelLayout.setVerticalGroup(
            createModulePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(createModulePanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(createModulePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(moduleNameTF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(11, 11, 11)
                .add(createModulePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel3)
                    .add(moduleDescriptionTF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(createModulePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(parentDirTF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel2)
                    .add(chooseDirB))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 39, Short.MAX_VALUE)
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 106, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(createModuleB)
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(createModulePanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .add(createModulePanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void createModuleBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createModuleBActionPerformed
        ModuleSourceManager mgr = new ModuleSourceManager();

        mgr.createModule(moduleNameTF.getText(), moduleDescriptionTF.getText(), parentDir, artCB.isSelected());

        JOptionPane.showMessageDialog(this, "Module created in Directory "+parentDir);
    }//GEN-LAST:event_createModuleBActionPerformed

    private void chooseDirBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseDirBActionPerformed
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fc.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            parentDir = fc.getSelectedFile();
            parentDirTF.setText(parentDir.getAbsolutePath());
        }

}//GEN-LAST:event_chooseDirBActionPerformed

    private void parentDirTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_parentDirTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_parentDirTFActionPerformed

    private void parentDirButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_parentDirButtonActionPerformed

        String dir;

        if (toolsDirCB.isSelected())
            dir = "tools";
        else if (worldDirCB.isSelected())
            dir = "world";
        else if (foundationDirCB.isSelected())
            dir = "foundation";
        else if (samplesDirCB.isSelected())
            dir = "samples";
        else {
            Logger.getAnonymousLogger().severe("Unknown directory selection, assuming world");
            dir = "world";
        }

        File defaultDir=new File(".."+File.separatorChar+"modules"+File.separatorChar+dir);
        if (defaultDir.exists()) {
            fc.setCurrentDirectory(defaultDir);
            parentDirTF.setText(defaultDir.getAbsolutePath());
            parentDir = defaultDir;
        }

    }//GEN-LAST:event_parentDirButtonActionPerformed

    private void moduleNameTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moduleNameTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_moduleNameTFActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ModuleManagerUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox artCB;
    private javax.swing.JButton chooseDirB;
    private javax.swing.JCheckBox clientPackageCB;
    private javax.swing.JCheckBox commonPackageCB;
    private javax.swing.JButton createModuleB;
    private javax.swing.JPanel createModulePanel;
    private javax.swing.JCheckBox foundationDirCB;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField moduleDescriptionTF;
    private javax.swing.JTextField moduleNameTF;
    private javax.swing.ButtonGroup parentDirGroup;
    private javax.swing.JTextField parentDirTF;
    private javax.swing.JCheckBox samplesDirCB;
    private javax.swing.JCheckBox serverPackageCB;
    private javax.swing.JCheckBox toolsDirCB;
    private javax.swing.JCheckBox worldDirCB;
    // End of variables declaration//GEN-END:variables

}
