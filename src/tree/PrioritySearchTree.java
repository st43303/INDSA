/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author Miloslav Moravec
 * @param <T>
 */
public class PrioritySearchTree<T extends Point> implements IPrioritySearchTree<T> {

    private Node<T> koren;
    private int mohutnost;

    public PrioritySearchTree() {
        this.mohutnost = 0;
    }

    @Override
    public int mohutnost() {
        return mohutnost;
    }

    @Override
    public boolean jePrazdny() {
        return mohutnost == 0;
    }

    @Override
    public void vybuduj(List<T> prvky) {
        if (prvky.isEmpty()) {
            throw new NullPointerException("Vstupní data jsou prázdná.");
        }
        this.mohutnost = prvky.size();
        List<T> data = prvky;
        //serazeni podle delky
        razeniPodleDelkySestupne(data);
        koren = new Node<>(data.get(0));
        data.remove(0);
        if (prvky.isEmpty()) {
            koren.hranice = koren.data.x;
            return;
        }
        //serazeni podle sirky
        razeniPodleSirkyVzestupne(data);
        double med = data.size() / 2;
        int median = (int) Math.ceil(med);
        koren.hranice = data.get(median).x;
        List<T> levyPodStrom = new ArrayList<>();
        List<T> pravyPodStrom = new ArrayList<>();
        data.forEach((hC) -> {
            if (hC.x < koren.hranice) {
                levyPodStrom.add(hC);
            } else {
                pravyPodStrom.add(hC);
            }
        });
        //budovani leveho podstromu
        vybuduj(levyPodStrom, koren, true);
        //budovani praveho podstromu
        vybuduj(pravyPodStrom, koren, false);
    }
    
    
    private void vybuduj(List<T> prvky, Node<T> predek, boolean levyPodstrom) {
        if (prvky.isEmpty()) {
            return;
        }
        List<T> data = prvky;
        //serazeni podle delky
        razeniPodleDelkySestupne(data);
        if (levyPodstrom == true) {
            predek.levyPotomek = new Node<>(data.get(0));
            predek.levyPotomek.otec = predek;
        } else {
            predek.pravyPotomek = new Node<>(data.get(0));
            predek.pravyPotomek.otec = predek;
        }
        data.remove(0);
        if (data.isEmpty()) {
            if (levyPodstrom == true) {
                predek.levyPotomek.hranice = predek.levyPotomek.data.x;
            } else {
                predek.pravyPotomek.hranice = predek.pravyPotomek.data.x;
            }
        } else {
            //serazeni podle sirky
            razeniPodleSirkyVzestupne(data);
            double med = data.size() / 2;
            int median = (int) Math.ceil(med);

            if (levyPodstrom == true) {
                predek.levyPotomek.hranice = data.get(median).x;
                List<T> levyPodStrom = new ArrayList<>();
                List<T> pravyPodStrom = new ArrayList<>();

                data.forEach((hC) -> {
                    if (hC.x < predek.levyPotomek.hranice) {
                        levyPodStrom.add(hC);
                    } else {
                        pravyPodStrom.add(hC);
                    }
                });

                vybuduj(levyPodStrom, predek.levyPotomek, true);
                vybuduj(pravyPodStrom, predek.levyPotomek, false);
            } else {
                List<T> levyPodStrom = new ArrayList<>();
                List<T> pravyPodStrom = new ArrayList<>();
                predek.pravyPotomek.hranice = data.get(median).x;

                data.forEach((hC) -> {
                    if (hC.x < predek.pravyPotomek.hranice) {
                        levyPodStrom.add(hC);
                    } else {
                        pravyPodStrom.add(hC);
                    }
                });
                vybuduj(levyPodStrom, predek.pravyPotomek, true);
                vybuduj(pravyPodStrom, predek.pravyPotomek, false);
            }
        }
    }

    @Override
    public void vloz(T data) {
        if (data == null) {
            throw new NullPointerException("Data pro vložení jsou prázdná.");
        }
        if (najdiPrvek(data.x, data.y) != null) {
            throw new IllegalArgumentException("Tento prvek již existuje.");
        }
        List<T> seznamPrvku = new ArrayList<>();
        Iterator<T> it = getIterator();
        while (it.hasNext()) {
            T prvek = it.next();
            seznamPrvku.add(prvek);
        }
        seznamPrvku.add(data);
        this.zrus();
        vybuduj(seznamPrvku);
    }

    @Override
    public T odeber(int x, int y) {
        Node<T> prvek = najdiPrvek(x, y);
        if (prvek == null) {
            throw new NullPointerException("Odebíraný prvek neexistuje.");
        }

        List<T> seznamPrvku = new ArrayList<>();
        Iterator<T> it = getIterator();
        while (it.hasNext()) {
            T data = it.next();
            seznamPrvku.add(data);
        }
        seznamPrvku.remove(prvek.data);
        this.zrus();
        vybuduj(seznamPrvku);
        return prvek.data;
    }

    @Override
    public T najdi(int x, int y) {
        return najdiPrvek(x, y).data;
    }

    @Override
    public List<T> najdiInterval(int x1, int y1, int x2) {
        List<T> list = new ArrayList<>();
        return najdiInterval(x1, y1, x2, koren.data.y, koren, list);
    }

    @Override
    public void zrus() {
        koren = null;
        mohutnost = 0;
    }

    @Override
    public Iterator<T> getIterator() {
        return new IteratorPreOrder();
    }

    private class IteratorPreOrder<T> implements Iterator<T> {

        private Stack<Node<T>> zasobnik;

        public IteratorPreOrder() {
            this.zasobnik = new Stack<>();
            if (koren != null) {
                this.zasobnik.add((Node<T>) koren);
            }

        }

        @Override
        public boolean hasNext() {
            return !zasobnik.isEmpty();
        }

        @Override
        public T next() {
            Node<T> node = zasobnik.pop();
            if (node.pravyPotomek != null) {
                zasobnik.push(node.pravyPotomek);
            }
            if (node.levyPotomek != null) {
                zasobnik.push(node.levyPotomek);
            }
            return node.data;
        }

    }

    private void razeniPodleDelkySestupne(List<T> data) {
        Collections.sort(data, (T t1, T t2) -> t1.y > t2.y ? -1 : (t1.y < t2.y) ? 1 : 0);
    }

    private void razeniPodleSirkyVzestupne(List<T> data) {
        Collections.sort(data, (T t1, T t2) -> t1.x < t2.x ? -1 : (t1.x > t2.x) ? 1 : 0);
    }

    private List<T> najdiInterval(double x1, double y1, double x2, double y2, Node<T> vrchol, List<T> list) {
        if (vrchol != null) {
            if (((porovnani(x1, vrchol.data.x)
                    && porovnani(vrchol.data.x, x2))
                    && porovnani(y1, vrchol.data.y))
                    && porovnani(vrchol.data.y, y2)) {

                list.add(vrchol.data);
            }
            if (!(((vrchol.levyPotomek == null) || !porovnani(y1, vrchol.levyPotomek.data.y)) || porovnani(vrchol.hranice, x1))) {
                najdiInterval(x1, y1, x2, y2, vrchol.levyPotomek, list);
            }

            if (((vrchol.pravyPotomek != null) && porovnani(y1, vrchol.pravyPotomek.data.y)) && porovnani(vrchol.hranice, x2)) {
                najdiInterval(x1, y1, x2, y2, vrchol.pravyPotomek, list);
            }
        }
        return list;
    }

    private Node<T> najdiPrvek(int x, int y) {
        Node<T> pomocny = koren;
        while (true) {
            if (pomocny == null) {
                return null;
            }
            if ((pomocny.data.x == x) && (pomocny.data.y == y)) {
                return pomocny;
            }
            if (pomocny.data.y < y) {
                return null;
            }
            if (pomocny.hranice > x) {
                pomocny = pomocny.levyPotomek;
            } else {
                pomocny = pomocny.pravyPotomek;
            }
        }
    }

    private boolean porovnani(double val1, double val2) {
        return val1 <= val2;
    }

    private class Node<T> {

        private Node<T> pravyPotomek;
        private Node<T> levyPotomek;
        private Node<T> otec;
        private double hranice;
        private T data;

        public Node(T data) {
            this.data = data;
        }
    }

}
