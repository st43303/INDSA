/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

import java.util.Iterator;
import java.util.List;


/**
 *
 * @author Miloslav Moravec
 * @param <V> data vrcholu
 * @param <H> data hrany
 * @param <K> klic vrcholu
 * @param <E> klic hrany
 */
public interface IGraph<V,H, K, E> {
    public int dejMohutnost();
    public boolean jePrazdny();
    public void vlozVrchol(V dataVrcholu);
    public void vlozHranu(H dataHrany, K klicVrcholu1,K klicVrcholu2, E klicHrany, boolean povoleno);
    public V odeberVrchol(K key);
    public H odeberHranu(E klicHrany);
    public void zrus();
    public List<H> dejVsechnyHrany();
    public Iterator<V> getIteratorVrcholu();
    public void zrusHrany();
    public void vybudujVrcholy(List<V> data);
    public H najdiHranu(E klicHrany);
    public List<V> dejVrcholyZIntervalu(int x1, int y, int x2);
    public Iterator<H> dejIteratorHranVrcholu(K key);
    public int dejPocetHranVrcholu(K key);
    public List<V> dejVrcholyHrany(E klic);
    public List<H> dejHranyVrcholu(K klic);
    public List<H> dejPovoleneHranyVrcholu(K klic);
    public boolean existujeHrana(K klicVrcholu1, K klicVrcholu2);
    public int dejHodnoceniHrany(H data);
    public V vratDruhyVrcholHrany(V prvniVrchol, H hrana);
    public List<K> dejKliceVrcholuHrany(E klic);
    public int dejHodnoceniHrany(K klicVrchol1, K klicVrchol2);
    public List<V> dejSousedyVrcholu(K klicVrcholu);
    public H dejHranu(K klicVrcholu1, K klicVrcholu2);
    public List<V> dejVsechnyVrcholy();
    public V najdiVrchol(K key);
    public void setPovoleni(List<V> seznamVrcholu, boolean povoleni);
    public int dejPocetHran();
    
    
}
