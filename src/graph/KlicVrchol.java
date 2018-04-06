/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

import tree.Point;

/**
 *
 * @author Miloslav Moravec
 */
public class KlicVrchol extends Point {
    private String nazevMesta;
      
     public KlicVrchol(int sirka, int delka, String nazevMesta) {
       super(sirka,delka);
       this.nazevMesta=nazevMesta;
    }
    
    @Override
    public int getX(){
        return x;
    }
    
    @Override
    public int getY(){
        return y;
    }
    
    public String getJmeno(){
        return nazevMesta;
    }

    @Override
    public String toString() {
        return "["+x+","+y+"] "+nazevMesta;
    }
}
