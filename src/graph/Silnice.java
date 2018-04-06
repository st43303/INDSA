/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

import java.io.Serializable;
import tree.Point;

/**
 *
 * @author Miloslav Moravec
 * @param <E>
 */
public class Silnice<E> implements Serializable {

    private int hodnoceni;
    private E nazev;
    private final Point mestoA;
    private final Point mestoB;
    private boolean povoleno;

    public Silnice(int hodnoceni, E klic, boolean povoleni, Point mestoA, Point mestoB) {
        this.hodnoceni = hodnoceni;
        this.nazev = klic;
        this.mestoA = mestoA;
        this.mestoB = mestoB;
        this.povoleno = povoleni;
    }

    public Point getMestoA() {
        return mestoA;
    }

    public Point getMestoB() {
        return mestoB;
    }

    public int getHodnoceni() {
        return hodnoceni;
    }
    
    public void setHodnoceni(int hodnoceni) {
        this.hodnoceni = hodnoceni;
    }

    public boolean isPovoleno() {
        return povoleno;
    }

    public void setPovoleni(boolean povoleno) {
        this.povoleno = povoleno;
    }

    public E getNazev() {
        return nazev;
    }

    public void setNazev(E klic) {
        this.nazev = klic;
    }

    @Override
    public String toString() {
        return nazev + " " + hodnoceni;
    }

}
