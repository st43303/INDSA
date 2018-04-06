/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dijkstra.DijkstraAlgorithm;
import graph.IGraph;
import graph.KlicVrchol;
import graph.Mesto;
import graph.Silnice;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import tree.Point;

/**
 *
 * @author Miloslav Moravec
 */
public class Platno extends javax.swing.JPanel {

    private IGraph<Mesto, Silnice, KlicVrchol, String> graf;
    private List<Mesto> listVrcholuHrany = new ArrayList<>();
    private List<Mesto> listTrasy = new ArrayList<>();
    private int x;
    private int y;
    private int width;
    private int height;
    private DijkstraAlgorithm dijkstra;
    private LinkedList<LinkedList<Mesto>> trasa = new LinkedList<>();
    private Point bod = new Point(0, 0);

    /**
     * Creates new form Platno
     */
    public Platno() {
        initComponents();
    }

    public void aktualizace(IGraph<Mesto, Silnice, KlicVrchol, String> graf) {
        this.graf = graf;
        dijkstra = new DijkstraAlgorithm(graf);
        this.repaint();
    }

    public void setListNoveHrany(List<Mesto> list) {
        this.listVrcholuHrany = list;
        this.repaint();
    }

    public void setListTrasy(List<Mesto> list) {
        this.listTrasy = list;
        this.repaint();
    }

    public void setTrasa() {
        trasa = dijkstra.dejTrasu(listTrasy);
        this.repaint();
    }

    public void setRectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        repaint();
    }

    public void setBod(Point bod) {
        this.bod = bod;
        repaint();
    }

    private void vykresliObdelnik(Graphics g) {
        g.setColor(new Color(0, 0, 255, 64));
        g.fillRect(x, y, width, height);
    }

    private void vykresliHrany(Graphics g, Silnice hrana) {
        Graphics2D g2d = (Graphics2D) g.create();
        if (hrana.isPovoleno()) {
            g2d.setColor(Color.GRAY);
        } else {
            g2d.setColor(Color.red);
        }
        Stroke dashed = new BasicStroke(3, BasicStroke.JOIN_ROUND, BasicStroke.JOIN_ROUND);
        g2d.setStroke(dashed);

        g2d.drawLine(hrana.getMestoA().x + 15, hrana.getMestoA().y + 15, hrana.getMestoB().x + 15, hrana.getMestoB().y + 15);
        g2d.setColor(Color.black);
        g2d.drawString(hrana.getNazev() + "[" + hrana.getHodnoceni() + "]", (hrana.getMestoA().x + hrana.getMestoB().x) / 2, (hrana.getMestoA().y + hrana.getMestoB().y) / 2);
    }

    private void vykresliTrasu(Graphics g) {
        if (!trasa.isEmpty()) {
            vykresleniTrasy(trasa.get(0), g);
            if (trasa.size() > 1) {
                vykresleniTrasy(trasa.get(1), g);
            }

            if (trasa.size() > 2) {
                vykresleniTrasy(trasa.get(2), g);
            }

        }
        trasa.clear();

    }

    private void vykresleniTrasy(LinkedList<Mesto> road, Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.GREEN);
        Stroke dashed = new BasicStroke(3, BasicStroke.JOIN_ROUND, BasicStroke.JOIN_ROUND);
        g2d.setStroke(dashed);

        for (int i = 0; i < road.size(); i++) {
            if (i + 1 != road.size()) {
                Mesto mestoA = road.get(i);
                Mesto mestoB = road.get(i + 1);
                g2d.drawLine(mestoA.x + 15, mestoA.y + 15, mestoB.x + 15, mestoB.y + 15);
            }

        }

    }

    private void vykresliObrazek(Graphics g) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("D:\\Users\\Miloslav Moravec\\Documents\\NetBeansProjects\\INDSA_SemA_Moravec\\src\\images\\kraje-cr.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(Platno.class.getName()).log(Level.SEVERE, null, ex);
        }
        int w = img.getWidth(null);
        int h = img.getHeight(null);
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        g.drawImage(img, 0, 0, null);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        // vykresliObrazek(g);

        vykresliObdelnik(g);
        g.setColor(Color.BLACK);
        Stroke dashed = new BasicStroke(3, BasicStroke.JOIN_ROUND, BasicStroke.JOIN_ROUND);
        g2d.setStroke(dashed);
        if (graf != null) {
            List<Silnice> seznamHran = graf.dejVsechnyHrany();
            for (Silnice silnice : seznamHran) {
                vykresliHrany(g, silnice);
            }
            vykresliTrasu(g);
            Iterator it = graf.getIteratorVrcholu();
            while (it.hasNext()) {
                Mesto vD = (Mesto) it.next();
                if (listVrcholuHrany.contains(vD)) {
                    g.setColor(Color.YELLOW);
                } else if (listTrasy.contains(vD)) {
                    g.setColor(Color.red);
                } else if ((vD.getX() == bod.getX()) && (vD.getY() == bod.getY())) {
                    g.setColor(Color.BLUE);
                } else {
                    g.setColor(Color.BLACK);
                }
                g.fillOval(vD.getX(), vD.getY(), 30, 30);
                g.setColor(Color.BLACK);
                g.drawString(vD.getJmeno(), vD.getX() + 30, vD.getY());

            }

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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        jMenuItem1.setText("Odeber Hradec");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        jMenuItem2.setText("Exit");
        jPopupMenu1.add(jMenuItem2);

        setBackground(new java.awt.Color(255, 255, 255));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased

    }//GEN-LAST:event_formMouseReleased

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

        graf.odeberVrchol(new KlicVrchol(150, 150, "Hradec Králové"));
        this.repaint();
    }//GEN-LAST:event_jMenuItem1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPopupMenu jPopupMenu1;
    // End of variables declaration//GEN-END:variables

}
