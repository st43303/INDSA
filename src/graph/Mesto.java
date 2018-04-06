/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

import java.util.Objects;
import tree.Point;

/**
 *
 * @author Miloslav Moravec
 */
public class Mesto extends Point {

    private String nazevMesta;

    public Mesto(int sirka, int delka, String nazevMesta) {
        super(sirka, delka);
        this.nazevMesta = nazevMesta;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    public String getJmeno() {
        return nazevMesta;
    }

    @Override
    public String toString() {
        return nazevMesta;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.nazevMesta);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Mesto other = (Mesto) obj;
        if (!Objects.equals(this.nazevMesta, other.nazevMesta)) {
            return false;
        }
        return true;
    }

}
