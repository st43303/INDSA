/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tree;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Miloslav Moravec
 * @param <T>
 */
public interface IPrioritySearchTree<T> {
    public boolean jePrazdny();
    public int mohutnost();
    public void vybuduj(List<T> prvky);
    public void vloz(T data);
    public T odeber(int x, int y);
    public T najdi(int x, int y);
    public List<T> najdiInterval(int x1,int y1,int x2);
    public void zrus();
    public Iterator<T> getIterator();
    
    
    
    
}
