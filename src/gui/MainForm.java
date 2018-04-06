/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import file.SerializaceIO;
import graph.Graph;
import graph.IGraph;
import graph.KlicVrchol;
import graph.Mesto;
import graph.Silnice;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import tree.Point;
import java.awt.Cursor;
import java.util.Collections;

/**
 *
 * @author Miloslav Moravec
 */
public class MainForm extends javax.swing.JFrame {

    private IGraph<Mesto, Silnice, KlicVrchol, String> graf = new Graph<>();
    private List<Mesto> listVrcholuHrany = new ArrayList<>();
    private List<Mesto> listTrasy = new ArrayList<>();

    /**
     * Creates new form MainForm
     */
    public MainForm() {
        initComponents();
        nastaveni();
        nacist();
    }

    private void nacist() {
        try {
            this.graf = SerializaceIO.nacti("data", graf);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        aktualizace();

    }

    private void nastaveni() {
        this.setExtendedState(MainForm.MAXIMIZED_BOTH);
        buttonGroup1.add(jToggleButtonPridatHranu);
        buttonGroup1.add(jToggleButtonPridatVrchol);
        buttonGroup1.add(jToggleButtonInterval);
        buttonGroup1.add(jToggleButtonTrasa);
        rangeSlider.setMaximum((int) (platno.getWidth() * 1.75));
        rangeSlider.setMinimum(0);
        rangeSlider.setUpperValue((int) (platno.getWidth() * 1.75));
        rangeSlider.setValue(0);
        jSliderVertical.setMinimum(0);
        jSliderVertical.setMaximum((int) (platno.getHeight() * 1.75));
        jSliderVertical.setValue(platno.getHeight());
        jButtonZakaz.setEnabled(false);
        jListZakazanaMesta.setEnabled(false);

    }

    private void novaHrana(int hodnoceni, String oznaceniHrany, Mesto vrcholZacatek, Mesto vrcholKonec) {
        try {
            graf.vlozHranu(new Silnice(hodnoceni, oznaceniHrany, true, new Point(vrcholZacatek.x, vrcholZacatek.y), new Point(vrcholKonec.x, vrcholKonec.y)), new KlicVrchol(vrcholZacatek.x, vrcholZacatek.y, vrcholZacatek.getJmeno()), new KlicVrchol(vrcholKonec.x, vrcholKonec.y, vrcholKonec.getJmeno()), oznaceniHrany, true);
            graf.vlozHranu(new Silnice(hodnoceni, oznaceniHrany, true, new Point(vrcholKonec.x, vrcholKonec.y), new Point(vrcholZacatek.x, vrcholZacatek.y)), new KlicVrchol(vrcholKonec.x, vrcholKonec.y, vrcholKonec.getJmeno()), new KlicVrchol(vrcholZacatek.x, vrcholZacatek.y, vrcholZacatek.getJmeno()), oznaceniHrany, true);

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Zadaný klíč již existuje.\nZvolte prosím jiné označení klíče.", "Chyba", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void vykresleniIntervalu() {
        if (jToggleButtonInterval.isSelected()) {
            jButtonZakaz.setEnabled(true);
            platno.setBod(new Point(0, 0));
            jButtonPovolit.setEnabled(true);
            jListZakazanaMesta.setEnabled(true);
            DefaultListModel model = new DefaultListModel();
            int x1 = rangeSlider.getValue();
            int x2 = rangeSlider.getUpperValue() - x1;
            int y1 = platno.getHeight() - jSliderVertical.getValue();
            int y2 = this.getHeight();
            platno.setRectangle(x1, y1, x2, y2);
            List<Mesto> listVrcholu = graf.dejVrcholyZIntervalu(x1, y1, x2);
            for (Mesto data : listVrcholu) {
                model.addElement(data);
            }
            jListZakazanaMesta.setModel(model);

        } else {
            platno.setRectangle(0, 0, 0, 0);
            platno.setBod(new Point(0, 0));
            jListZakazanaMesta.removeAll();
            jButtonZakaz.setEnabled(false);
            jButtonPovolit.setEnabled(false);
            jListZakazanaMesta.setEnabled(false);

        }
    }

    private void setPovoleniHranam(boolean povoleni, List<Mesto> seznamMest) {
        List<Silnice> seznamSilnic = graf.dejVsechnyHrany();
        for (Silnice silnice : seznamSilnic) {
            for (Mesto mesto : seznamMest) {
                if ((mesto.getX() == silnice.getMestoA().getX()) && (mesto.getY() == silnice.getMestoA().getY())) {
                    silnice.setPovoleni(povoleni);
                } else if ((mesto.getX() == silnice.getMestoB().getX()) && (mesto.getY() == silnice.getMestoB().getY())) {
                    silnice.setPovoleni(povoleni);
                }
            }
        }
    }

    private void aktualizace() {
        jTextFieldPocetMest.setText("Počet měst:\t" + graf.dejMohutnost());
        jTextFieldPocetCest.setText("Počet cest:\t" + graf.dejPocetHran());
        platno.aktualizace(graf);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        platno = new gui.Platno();
        jPanelTlacitka = new javax.swing.JPanel();
        jButtonOdebratVrchol = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jToggleButtonPridatVrchol = new javax.swing.JToggleButton();
        jToggleButtonPridatHranu = new javax.swing.JToggleButton();
        jToggleButtonInterval = new javax.swing.JToggleButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListZakazanaMesta = new javax.swing.JList<>();
        jButtonZakaz = new javax.swing.JButton();
        jButtonPovolit = new javax.swing.JButton();
        jTextFieldPocetMest = new javax.swing.JTextField();
        jTextFieldPocetCest = new javax.swing.JTextField();
        jToggleButtonTrasa = new javax.swing.JToggleButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        rangeSlider = new gui.RangeSlider();
        jSliderVertical = new javax.swing.JSlider();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Dopravní síť | Semestrální práce A - Miloslav Moravec");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        platno.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                platnoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout platnoLayout = new javax.swing.GroupLayout(platno);
        platno.setLayout(platnoLayout);
        platnoLayout.setHorizontalGroup(
            platnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 994, Short.MAX_VALUE)
        );
        platnoLayout.setVerticalGroup(
            platnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 543, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(platno);

        jPanelTlacitka.setBackground(new java.awt.Color(153, 153, 153));

        jButtonOdebratVrchol.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-minus-16.png"))); // NOI18N
        jButtonOdebratVrchol.setText("Odebrat město");
        jButtonOdebratVrchol.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonOdebratVrchol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOdebratVrcholActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-line-16 remove.png"))); // NOI18N
        jButton4.setText("Odebrat komunikaci");
        jButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jToggleButtonPridatVrchol.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-plus-16.png"))); // NOI18N
        jToggleButtonPridatVrchol.setText("Přidat město");
        jToggleButtonPridatVrchol.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jToggleButtonPridatVrchol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonPridatVrcholActionPerformed(evt);
            }
        });

        jToggleButtonPridatHranu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-line-16.png"))); // NOI18N
        jToggleButtonPridatHranu.setText("Přidat komunikaci");
        jToggleButtonPridatHranu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jToggleButtonPridatHranu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonPridatHranuActionPerformed(evt);
            }
        });

        jToggleButtonInterval.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-radar-16.png"))); // NOI18N
        jToggleButtonInterval.setText("Intervalové hledání");
        jToggleButtonInterval.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jToggleButtonInterval.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonIntervalActionPerformed(evt);
            }
        });

        jScrollPane2.setViewportView(jListZakazanaMesta);

        jButtonZakaz.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-no-entry-16.png"))); // NOI18N
        jButtonZakaz.setText("Zakázat vybraná města");
        jButtonZakaz.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonZakaz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonZakazActionPerformed(evt);
            }
        });

        jButtonPovolit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-approval-16.png"))); // NOI18N
        jButtonPovolit.setText("Povolit vybraná města");
        jButtonPovolit.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButtonPovolit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonPovolit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPovolitActionPerformed(evt);
            }
        });

        jToggleButtonTrasa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-route-16.png"))); // NOI18N
        jToggleButtonTrasa.setText("Najít trasu");
        jToggleButtonTrasa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jToggleButtonTrasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonTrasaActionPerformed(evt);
            }
        });

        jButton1.setText("Hledej");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Bodové");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelTlacitkaLayout = new javax.swing.GroupLayout(jPanelTlacitka);
        jPanelTlacitka.setLayout(jPanelTlacitkaLayout);
        jPanelTlacitkaLayout.setHorizontalGroup(
            jPanelTlacitkaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButtonOdebratVrchol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jToggleButtonPridatVrchol, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jToggleButtonPridatHranu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jButtonZakaz, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButtonPovolit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTextFieldPocetMest)
            .addComponent(jTextFieldPocetCest, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanelTlacitkaLayout.createSequentialGroup()
                .addGroup(jPanelTlacitkaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToggleButtonTrasa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToggleButtonInterval, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelTlacitkaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanelTlacitkaLayout.setVerticalGroup(
            jPanelTlacitkaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTlacitkaLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jToggleButtonPridatVrchol)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonOdebratVrchol)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToggleButtonPridatHranu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelTlacitkaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButtonTrasa)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelTlacitkaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButtonInterval)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldPocetMest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldPocetCest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonPovolit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonZakaz)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE))
        );

        rangeSlider.setMajorTickSpacing(50);
        rangeSlider.setMinorTickSpacing(10);
        rangeSlider.setPaintTicks(true);
        rangeSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rangeSliderStateChanged(evt);
            }
        });

        jSliderVertical.setMajorTickSpacing(50);
        jSliderVertical.setMinorTickSpacing(10);
        jSliderVertical.setOrientation(javax.swing.JSlider.VERTICAL);
        jSliderVertical.setPaintTicks(true);
        jSliderVertical.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSliderVerticalStateChanged(evt);
            }
        });

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-import-16.png"))); // NOI18N
        jMenuItem1.setText("Načíst ze souboru");
        jMenuItem1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-export-16.png"))); // NOI18N
        jMenuItem2.setText("Uložit do souboru");
        jMenuItem2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-delete-16.png"))); // NOI18N
        jMenuItem3.setText("Ukončit program");
        jMenuItem3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-delete-16.png"))); // NOI18N
        jMenuItem4.setText("Smazat všechna města");
        jMenuItem4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-delete-16.png"))); // NOI18N
        jMenuItem5.setText("Smazat všechny komunikace");
        jMenuItem5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rangeSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSliderVertical, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelTlacitka, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelTlacitka, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rangeSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jSliderVertical, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        int ret = JOptionPane.showConfirmDialog(this, "Uložit změny?", "Konec programu", JOptionPane.YES_NO_CANCEL_OPTION);
        switch (ret) {
            case JOptionPane.CANCEL_OPTION:
                return;
            case JOptionPane.YES_OPTION:
                JFileChooser chooser = new JFileChooser();
                int value = chooser.showSaveDialog(this);
                if (value == JFileChooser.APPROVE_OPTION) {
                    String soubor = chooser.getSelectedFile().getAbsolutePath();
                    try {
                        SerializaceIO.uloz(soubor, graf);
                    } catch (IOException ex) {
                        Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                this.dispose();

                System.exit(0);
            case JOptionPane.NO_OPTION:
                this.dispose();
                System.exit(0);
            default:
                break;
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void platnoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_platnoMouseClicked
        if (jToggleButtonPridatVrchol.isSelected()) {
            String mesto = JOptionPane.showInputDialog(this, "Zadejte prosím název nového města", "Nové město", JOptionPane.QUESTION_MESSAGE);
            if ((!"".equals(mesto)) && (mesto != null)) {
                graf.vlozVrchol(new Mesto(evt.getX(), evt.getY(), mesto));
                aktualizace();
            }
            jToggleButtonPridatVrchol.setSelected(false);
        }

        if (jToggleButtonPridatHranu.isSelected()) {
            Iterator<Mesto> it = graf.getIteratorVrcholu();
            while (it.hasNext()) {
                Mesto vrchol = it.next();
                int x = vrchol.x;
                int y = vrchol.y;
                if ((evt.getX() >= x) && (evt.getX() <= x + 30)) {
                    if ((evt.getY() >= y) && (evt.getY() <= y + 30)) {
                        if (listVrcholuHrany.size() <= 2) {
                            listVrcholuHrany.add(vrchol);
                            platno.setListNoveHrany(listVrcholuHrany);
                            if (listVrcholuHrany.size() == 2) {
                                Mesto mestoA = listVrcholuHrany.get(0);
                                Mesto mestoB = listVrcholuHrany.get(1);
                                if (!graf.existujeHrana(new KlicVrchol(mestoA.x, mestoA.y, mestoA.getJmeno()), new KlicVrchol(mestoB.x, mestoB.y, mestoB.getJmeno()))) {
                                    String nazevHrany = JOptionPane.showInputDialog(this, "Zadejte prosím název nové komunikace", "Nová kommunikace", JOptionPane.QUESTION_MESSAGE);
                                    if(graf.najdiHranu(nazevHrany)==null){
                                        int hodnoceni = Integer.parseInt(JOptionPane.showInputDialog(this, "Zadejte prosím ohodnocení komunikace", "Komunikace " + nazevHrany, JOptionPane.QUESTION_MESSAGE));
                                    novaHrana(hodnoceni, nazevHrany, mestoA, mestoB);
                                    }else{
                                        JOptionPane.showMessageDialog(this, "Zadaný název již existuje.", "Chyba", JOptionPane.WARNING_MESSAGE);
                                    }
                                    
                                } else {
                                    JOptionPane.showMessageDialog(this, "Zde už cesta existuje.", "Chyba", JOptionPane.WARNING_MESSAGE);
                                }

                                listVrcholuHrany.clear();
                                platno.setListNoveHrany(listVrcholuHrany);
                                aktualizace();
                                jToggleButtonPridatHranu.setSelected(false);
                            }
                        }

                    }
                }
            }
        }
        if (jToggleButtonTrasa.isSelected()) {
            Iterator<Mesto> it = graf.getIteratorVrcholu();
            while (it.hasNext()) {
                Mesto vrchol = it.next();
                int x = vrchol.getX();
                int y = vrchol.getY();
                if ((evt.getX() >= x) && (evt.getX() <= x + 30)) {
                    if ((evt.getY() >= y) && (evt.getY() <= y + 30)) {
                        if (listTrasy.size() < 4) {
                            listTrasy.add(vrchol);
                            platno.setListTrasy(listTrasy);
                        } else {
                            listTrasy.set(3, vrchol);
                            platno.setListTrasy(listTrasy);
                        }
                    }
                }
            }
        }

    }//GEN-LAST:event_platnoMouseClicked

    private void jButtonOdebratVrcholActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOdebratVrcholActionPerformed
        buttonGroup1.clearSelection();
        platno.setBod(new Point(0, 0));
        platno.setRectangle(0, 0, 0, 0);
        jListZakazanaMesta.setModel(new DefaultListModel());
        jButtonZakaz.setEnabled(false);
        jButtonPovolit.setEnabled(false);
        jListZakazanaMesta.setEnabled(false);
        platno.setCursor(Cursor.getDefaultCursor());
        List<String> mesta = new ArrayList<>();
        if (graf.dejMohutnost() > 0) {
            Iterator<Mesto> iterator = graf.getIteratorVrcholu();
            while (iterator.hasNext()) {
                mesta.add(iterator.next().getJmeno());
            }

            Collections.sort(mesta);

            ImageIcon icon = new ImageIcon("./images/icons8-minus-16.png");
            String s = (String) JOptionPane.showInputDialog(
                    this,
                    "Vyberte město k odebrání",
                    "Odebrání města",
                    JOptionPane.PLAIN_MESSAGE, icon,
                    mesta.toArray(),
                    mesta.get(0));

            iterator = graf.getIteratorVrcholu();
            while (iterator.hasNext()) {
                Mesto data = iterator.next();
                if (data.getJmeno().equals(s)) {
                    graf.odeberVrchol(new KlicVrchol(data.x, data.y, s));
                    aktualizace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Neexistuje žádné město k odebrání", "Chyba", JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_jButtonOdebratVrcholActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        graf.zrus();
        aktualizace();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        graf.zrusHrany();
        aktualizace();
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        buttonGroup1.clearSelection();
        platno.setRectangle(0, 0, 0, 0);
        platno.setBod(new Point(0, 0));
        jListZakazanaMesta.setModel(new DefaultListModel());
        jButtonZakaz.setEnabled(false);
        jButtonPovolit.setEnabled(false);
        jListZakazanaMesta.setEnabled(false);
        platno.setCursor(Cursor.getDefaultCursor());

        List<Silnice> listHran = graf.dejVsechnyHrany();

        List<String> list = new ArrayList<>();
        if (listHran.size() > 0) {
            for (Silnice<String> hrana : listHran) {
                if (!list.contains(hrana.getNazev())) {
                    list.add((String) hrana.getNazev());
                }
            }
            Collections.sort(list);
            ImageIcon icon = new ImageIcon("./images/icons8-minus-16.png");
            String s = (String) JOptionPane.showInputDialog(
                    this,
                    "Vyberte komunikaci k odebrání",
                    "Odebrání komunikace",
                    JOptionPane.PLAIN_MESSAGE, icon,
                    list.toArray(),
                    list.get(0));

            listHran = graf.dejVsechnyHrany();

            for (Silnice<String> hrana : listHran) {
                if (hrana.getNazev().equals(s)) {
                    graf.odeberHranu((String) hrana.getNazev());
                }
            }
            aktualizace();

        } else {
            JOptionPane.showMessageDialog(this, "Neexistuje žádná komunikace k odebrání", "Chyba", JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_jButton4ActionPerformed

    private void jToggleButtonPridatHranuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonPridatHranuActionPerformed
        platno.setRectangle(0, 0, 0, 0);
        platno.setBod(new Point(0, 0));
        jListZakazanaMesta.setModel(new DefaultListModel());
        jButtonZakaz.setEnabled(false);
        jButtonPovolit.setEnabled(false);
        jListZakazanaMesta.setEnabled(false);
        platno.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_jToggleButtonPridatHranuActionPerformed

    private void jToggleButtonPridatVrcholActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonPridatVrcholActionPerformed
        platno.setRectangle(0, 0, 0, 0);
        platno.setBod(new Point(0, 0));
        jListZakazanaMesta.setModel(new DefaultListModel());
        jButtonZakaz.setEnabled(false);
        jButtonPovolit.setEnabled(false);
        jListZakazanaMesta.setEnabled(false);
        platno.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
    }//GEN-LAST:event_jToggleButtonPridatVrcholActionPerformed

    private void rangeSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rangeSliderStateChanged
        vykresleniIntervalu();

    }//GEN-LAST:event_rangeSliderStateChanged

    private void jSliderVerticalStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderVerticalStateChanged
        vykresleniIntervalu();

    }//GEN-LAST:event_jSliderVerticalStateChanged

    private void jToggleButtonIntervalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonIntervalActionPerformed
        vykresleniIntervalu();
        platno.setCursor(Cursor.getDefaultCursor());

    }//GEN-LAST:event_jToggleButtonIntervalActionPerformed

    private void jButtonZakazActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonZakazActionPerformed
        int x1 = rangeSlider.getValue();
        int x2 = rangeSlider.getUpperValue() - x1;
        int y1 = platno.getHeight() - jSliderVertical.getValue();
        int y2 = this.getHeight();
        List<Mesto> listVrcholu = graf.dejVrcholyZIntervalu(x1, y1, x2);
        setPovoleniHranam(false, listVrcholu);
        graf.setPovoleni(listVrcholu, false);

        aktualizace();
    }//GEN-LAST:event_jButtonZakazActionPerformed

    private void jButtonPovolitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPovolitActionPerformed
        int x1 = rangeSlider.getValue();
        int x2 = rangeSlider.getUpperValue() - x1;
        int y1 = platno.getHeight() - jSliderVertical.getValue();
        int y2 = this.getHeight();
        List<Mesto> listVrcholu = graf.dejVrcholyZIntervalu(x1, y1, x2);
        setPovoleniHranam(true, listVrcholu);
        graf.setPovoleni(listVrcholu, true);
        aktualizace();
    }//GEN-LAST:event_jButtonPovolitActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        JFileChooser chooser = new JFileChooser();
        int value = chooser.showSaveDialog(this);
        if (value == JFileChooser.APPROVE_OPTION) {
            String soubor = chooser.getSelectedFile().getAbsolutePath();
            try {
                SerializaceIO.uloz(soubor, graf);
            } catch (IOException ex) {
                Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        JFileChooser chooser = new JFileChooser();
        int value = chooser.showOpenDialog(this);
        if (value == JFileChooser.APPROVE_OPTION) {
            String soubor = chooser.getSelectedFile().getAbsolutePath();
            try {
                this.graf = SerializaceIO.nacti(soubor, graf);
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            aktualizace();
        }

    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jToggleButtonTrasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonTrasaActionPerformed
        platno.setRectangle(0, 0, 0, 0);
        platno.setBod(new Point(0, 0));
        jListZakazanaMesta.setModel(new DefaultListModel());
        jButtonZakaz.setEnabled(false);
        jButtonPovolit.setEnabled(false);
        jListZakazanaMesta.setEnabled(false);
        platno.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_jToggleButtonTrasaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        platno.setTrasa();
        listTrasy.clear();
        jToggleButtonTrasa.setSelected(false);
        platno.aktualizace(graf);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        int ret = JOptionPane.showConfirmDialog(this, "Uložit změny?", "Konec programu", JOptionPane.YES_NO_CANCEL_OPTION);
        switch (ret) {
            case JOptionPane.CANCEL_OPTION:
                return;
            case JOptionPane.YES_OPTION:
                JFileChooser chooser = new JFileChooser();
                int value = chooser.showSaveDialog(this);
                if (value == JFileChooser.APPROVE_OPTION) {
                    String soubor = chooser.getSelectedFile().getAbsolutePath();
                    try {
                        SerializaceIO.uloz(soubor, graf);
                    } catch (IOException ex) {
                        Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                this.dispose();

                System.exit(0);
            case JOptionPane.NO_OPTION:
                this.dispose();

                System.exit(0);
            default:
                break;
        }

    }//GEN-LAST:event_formWindowClosing

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        buttonGroup1.clearSelection();
        platno.setBod(new Point(0, 0));
        platno.setRectangle(0, 0, 0, 0);
        jListZakazanaMesta.setModel(new DefaultListModel());
        jButtonZakaz.setEnabled(false);
        jButtonPovolit.setEnabled(false);
        jListZakazanaMesta.setEnabled(false);
        platno.setCursor(Cursor.getDefaultCursor());
        List<String> mesta = new ArrayList<>();
        if (graf.dejMohutnost() > 0) {
            Iterator<Mesto> iterator = graf.getIteratorVrcholu();
            while (iterator.hasNext()) {
                mesta.add(iterator.next().getJmeno());
            }

            Collections.sort(mesta);

            ImageIcon icon = new ImageIcon("./images/icons8-minus-16.png");
            String s = (String) JOptionPane.showInputDialog(
                    this,
                    "Vyberte město k vyhledání",
                    "Vyhledávání města",
                    JOptionPane.PLAIN_MESSAGE, icon,
                    mesta.toArray(),
                    mesta.get(0));

            iterator = graf.getIteratorVrcholu();
            while (iterator.hasNext()) {
                Mesto data = iterator.next();
                if (data.getJmeno().equals(s)) {
                    platno.setBod(new Point(data.getX(), data.getY()));
                    aktualizace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Neexistuje žádné město k vyhledání", "Chyba", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButtonOdebratVrchol;
    private javax.swing.JButton jButtonPovolit;
    private javax.swing.JButton jButtonZakaz;
    private javax.swing.JList<String> jListZakazanaMesta;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JPanel jPanelTlacitka;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSlider jSliderVertical;
    private javax.swing.JTextField jTextFieldPocetCest;
    private javax.swing.JTextField jTextFieldPocetMest;
    private javax.swing.JToggleButton jToggleButtonInterval;
    private javax.swing.JToggleButton jToggleButtonPridatHranu;
    private javax.swing.JToggleButton jToggleButtonPridatVrchol;
    private javax.swing.JToggleButton jToggleButtonTrasa;
    private gui.Platno platno;
    private gui.RangeSlider rangeSlider;
    // End of variables declaration//GEN-END:variables
}
