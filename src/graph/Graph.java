/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import tree.IPrioritySearchTree;
import tree.Point;
import tree.PrioritySearchTree;

/**
 *
 * @author Miloslav Moravec
 * @param <V> data vrcholu
 * @param <H> data hrany
 * @param <K> klic vrcholu
 * @param <E> klic hrany
 */
public class Graph<V extends Point, H, K extends Point, E extends Comparable> implements IGraph<V, H, K, E> {

    private final IPrioritySearchTree<Vrchol> strom;

    public Graph() {
        this.strom = new PrioritySearchTree<>();
    }

    @Override
    public int dejMohutnost() {
        return strom.mohutnost();
    }

    @Override
    public boolean jePrazdny() {
        return strom.jePrazdny();
    }

    @Override
    public void vlozVrchol(V dataVrcholu) {
        if (dataVrcholu == null) {
            throw new NullPointerException("Neplatná data");
        }
        Vrchol vrchol = new Vrchol(dataVrcholu);
        strom.vloz(vrchol);
    }

    @Override
    public void vlozHranu(H dataHrany, K klicVrcholu1, K klicVrcholu2, E klicHrany, boolean povoleno) {
        Vrchol vrcholStart = strom.najdi(klicVrcholu1.getX(), klicVrcholu1.getY());
        Vrchol vrcholCil = strom.najdi(klicVrcholu2.getX(), klicVrcholu2.getY());

        if ((vrcholStart == null) || (vrcholCil == null)) {
            throw new NullPointerException("Zadané vrcholy pro vytvoření hrany nebyly nalezeny.");
        }
        Hrana hrana = new Hrana(vrcholStart, vrcholCil, dataHrany, klicHrany, povoleno);
        vrcholStart.pridejHranu(hrana);
        vrcholCil.pridejHranu(hrana);
    }

    @Override
    public H dejHranu(K klicVrcholu1, K klicVrcholu2) {
        Vrchol vrchol1 = strom.najdi(klicVrcholu1.x, klicVrcholu1.y);
        Vrchol vrchol2 = strom.najdi(klicVrcholu2.x, klicVrcholu2.y);

        List<Hrana> seznamHran = dejVsechnyHranyGrafu();

        for (Hrana hrana : seznamHran) {
            if ((hrana.vrcholStart == vrchol1) && (hrana.vrcholCil == vrchol2)) {
                return hrana.data;
            } else if ((hrana.vrcholCil == vrchol1) && (hrana.vrcholStart == vrchol2)) {
                return hrana.data;
            }

        }
        return null;
    }

    @Override
    public int dejPocetHranVrcholu(K key) {
        Vrchol vrchol = strom.najdi(key.getX(), key.getY());
        if (vrchol == null) {
            throw new NullPointerException("Vrchol nenalezen.");
        }
        return vrchol.seznamHran.size();

    }

    @Override
    public int dejPocetHran() {
        List<E> klice = new ArrayList<>();
        Iterator<Vrchol> it = strom.getIterator();
        while (it.hasNext()) {
            Vrchol vrchol = it.next();
            Iterator<Hrana> itH = vrchol.seznamHran.iterator();
            while (itH.hasNext()) {
                Hrana hrana = itH.next();
                if (!klice.contains(hrana.getKlic())) {
                    klice.add(hrana.getKlic());
                }
            }
        }
        return klice.size();
    }

    @Override
    public List<V> dejSousedyVrcholu(K klicVrcholu) {
        Vrchol vrchol = strom.najdi(klicVrcholu.getX(), klicVrcholu.getY());
        if (vrchol == null) {
            throw new NullPointerException("Vrchol nenalezen.");
        }
        List<V> sousedi = new ArrayList<>();
        List<Hrana> hrany = this.dejVsechnyHranyGrafu();
        for (Hrana hrana : hrany) {
            if (hrana.vrcholStart == vrchol) {
                if (!sousedi.contains(hrana.vrcholCil.data)) {
                    sousedi.add(hrana.vrcholCil.data);
                }
            }
        }
        return sousedi;
    }

    @Override
    public List<V> dejVsechnyVrcholy() {
        List<V> list = new ArrayList<>();
        Iterator<V> it = getIteratorVrcholu();

        while (it.hasNext()) {
            list.add(it.next());
        }

        return list;
    }

    @Override
    public V najdiVrchol(K key) {
        Vrchol vrchol = strom.najdi(key.getX(), key.getY());
        return vrchol.getData();
    }

    @Override
    public List<H> dejHranyVrcholu(K klic) {
        Vrchol vrchol = strom.najdi(klic.getX(), klic.getY());
        if (vrchol == null) {
            throw new NullPointerException("Vrchol nenalezen.");
        }
        List<H> seznamHran = new ArrayList<>();
        vrchol.seznamHran.forEach((hrana) -> {
            seznamHran.add(hrana.data);
        });
        return seznamHran;
    }

    @Override
    public List<H> dejPovoleneHranyVrcholu(K klic) {
        Vrchol vrchol = strom.najdi(klic.getX(), klic.getY());
        if (vrchol == null) {
            throw new NullPointerException("Vrchol nenalezen.");
        }
        List<H> seznamHran = new ArrayList<>();
        for (Hrana hrana : vrchol.seznamHran) {
            if (hrana.povoleno == true) {
                seznamHran.add(hrana.data);
            }
        }
        return seznamHran;
    }

    @Override
    public int dejHodnoceniHrany(H data) {
        Hrana hrana = najdiHranuGrafu(data);
        if (hrana == null) {
            throw new NullPointerException("Hrana nenalezena");
        }
        Silnice silnice = (Silnice) hrana.data;
        return silnice.getHodnoceni();
    }

    @Override
    public V vratDruhyVrcholHrany(V prvniVrchol, H dataHrany) {
        Vrchol vrchol = strom.najdi(prvniVrchol.getX(), prvniVrchol.getY());
        if (vrchol == null) {
            throw new NullPointerException("Vrchol nenalezen.");
        }

        Hrana hrana = this.najdiHranuGrafu(dataHrany);
        if (hrana.povoleno == false) {
            return null;
        }
        if (hrana.vrcholStart.equals(vrchol)) {
            return hrana.vrcholCil.getData();
        } else if (hrana.vrcholCil.equals(vrchol)) {
            return hrana.vrcholStart.getData();
        }

        return null;
    }

    @Override
    public void setPovoleni(List<V> seznamVrcholu, boolean povoleni) {
        List<Hrana> seznamHran = dejVsechnyHranyGrafu();

        for (Hrana hrana : seznamHran) {
            for (V data : seznamVrcholu) {
                if ((data.getX() == hrana.vrcholStart.getX()) && (data.getY() == hrana.vrcholStart.getY())) {
                    hrana.povoleno = povoleni;
                } else if ((data.getX() == data.getX()) && (data.getY() == hrana.vrcholCil.getY())) {
                    hrana.povoleno = povoleni;
                }
            }
        }
    }

    @Override
    public int dejHodnoceniHrany(K klicVrchol1, K klicVrchol2) {
        Vrchol vrcholA = strom.najdi(klicVrchol1.x, klicVrchol1.y);
        Vrchol vrcholB = strom.najdi(klicVrchol2.x, klicVrchol2.y);

        List<Hrana> seznamHran = dejVsechnyHranyGrafu();

        for (Hrana hrana : seznamHran) {
            if ((hrana.vrcholStart.equals(vrcholA)) && (hrana.vrcholCil.equals(vrcholB))) {
                Silnice silnice = (Silnice) hrana.data;
                return silnice.getHodnoceni();
            } else if ((hrana.vrcholStart.equals(vrcholB)) && (hrana.vrcholCil.equals(vrcholA))) {
                Silnice silnice = (Silnice) hrana.data;
                return silnice.getHodnoceni();
            }
        }
        throw new RuntimeException("Should not happen");
    }

    @Override
    public List<K> dejKliceVrcholuHrany(E klic) {
        List<Hrana> listVsechHran = this.dejVsechnyHranyGrafu();
        List<K> kliceVrcholu = new ArrayList<>();
        for (Hrana hrana : listVsechHran) {
            if (hrana.getKlic().equals(klic)) {
                if (!kliceVrcholu.contains((K) new Point(hrana.vrcholStart.x, hrana.vrcholStart.y))) {
                    kliceVrcholu.add((K) new Point(hrana.vrcholStart.x, hrana.vrcholStart.y));
                }

                if (!kliceVrcholu.contains((K) new Point(hrana.vrcholCil.x, hrana.vrcholCil.y))) {
                    kliceVrcholu.add((K) new Point(hrana.vrcholCil.x, hrana.vrcholCil.y));
                }

            }
        }
        return kliceVrcholu;
    }

    private Hrana najdiHranuGrafu(H data) {
        List<Hrana> seznamHran = this.dejVsechnyHranyGrafu();
        Hrana hledana = null;
        for (Hrana hrana : seznamHran) {
            if (hrana.data == data) {
                hledana = hrana;
            }
        }
        return hledana;

    }

    @Override
    public List<V> dejVrcholyHrany(E klic) {
        List<Hrana> listVsechHran = this.dejVsechnyHranyGrafu();
        List<V> vrcholyHran = new ArrayList<>();
        for (Hrana hrana : listVsechHran) {
            if (hrana.getKlic().equals(klic)) {
                if (!vrcholyHran.contains((V) hrana.vrcholStart.getData())) {
                    vrcholyHran.add((V) hrana.vrcholStart.getData());
                }

                if (!vrcholyHran.contains((V) hrana.vrcholCil.getData())) {
                    vrcholyHran.add((V) hrana.vrcholCil.getData());
                }

            }
        }
        return vrcholyHran;

    }

    @Override
    public Iterator<H> dejIteratorHranVrcholu(K key) {
        Vrchol vrchol = strom.najdi(key.getX(), key.getY());
        if (vrchol == null) {
            throw new NullPointerException("Vrchol nenalezen.");
        }
        List<Hrana> collection = vrchol.seznamHran;
        List<H> list = new ArrayList<>();
        collection.forEach((hrana) -> {
            list.add(hrana.data);
        });
        return list.iterator();

    }

    @Override
    public V odeberVrchol(K key) {
        Vrchol vrchol = strom.najdi(key.getX(), key.getY());
        if (vrchol == null) {
            throw new NullPointerException("Vrchol nenalezen.");
        }
        List<E> seznamKlicu = new ArrayList<>();
        vrchol.seznamHran.forEach((hrana) -> {
            seznamKlicu.add(hrana.getKlic());
        });

        Iterator it = strom.getIterator();

        while (it.hasNext()) {
            Vrchol vrch = (Vrchol) it.next();
            Iterator<Hrana> itH = vrch.seznamHran.iterator();
            while (itH.hasNext()) {
                Hrana hrana = itH.next();
                if (seznamKlicu.contains(hrana.getKlic())) {
                    itH.remove();
                }
            }
        }
        return strom.odeber(key.getX(), key.getY()).data;
    }

    @Override
    public H odeberHranu(E klicHrany) {
        Iterator<Vrchol> it = strom.getIterator();
        H data = null;
        while (it.hasNext()) {
            Vrchol vrchol = it.next();
            Iterator<Hrana> itH = vrchol.seznamHran.iterator();
            while (itH.hasNext()) {
                Hrana hrana = itH.next();
                if (hrana.getKlic().equals(klicHrany)) {
                    itH.remove();
                }
            }
        }
        return data;
    }

    @Override
    public H najdiHranu(E klicHrany) {
        Iterator<Vrchol> it = strom.getIterator();
        H data = null;
        while (it.hasNext()) {
            Vrchol vrchol = it.next();
            for (Hrana hrana : vrchol.seznamHran) {
                if (klicHrany.equals(hrana.getKlic())) {
                    return hrana.data;
                }
            }
        }
        return data;
    }

    @Override
    public List<V> dejVrcholyZIntervalu(int x1, int y, int x2) {
        List<Vrchol> list = strom.najdiInterval(x1, y, x2);
        List<V> listData = new ArrayList<>();
        list.forEach((vrchol) -> {
            listData.add(vrchol.data);
        });
        return listData;
    }

    @Override
    public boolean existujeHrana(K klicVrcholu1, K klicVrcholu2) {
        Vrchol vrchol1 = strom.najdi(klicVrcholu1.getX(), klicVrcholu1.getY());
        Vrchol vrchol2 = strom.najdi(klicVrcholu2.getX(), klicVrcholu2.getY());
        if ((vrchol1 == null) || (vrchol2 == null)) {
            throw new NullPointerException("Vrchol nenalezen.");
        }
        for (Hrana hrana : vrchol1.seznamHran) {
            if ((hrana.vrcholCil.getData() == vrchol2.getData()) || (hrana.vrcholStart.getData() == vrchol2.getData())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<H> dejVsechnyHrany() {
        List<H> listHran = new ArrayList<>();
        Iterator<Vrchol> it = strom.getIterator();
        while (it.hasNext()) {
            Vrchol vrchol = it.next();
            vrchol.seznamHran.forEach((hrana) -> {
                listHran.add((H) hrana.data);
            });
        }
        return listHran;
    }

    @Override
    public void vybudujVrcholy(List<V> data) {
        List<Vrchol> list = new ArrayList<>();
        data.forEach((dataVrcholu) -> {
            list.add(new Vrchol(dataVrcholu));
        });
        strom.vybuduj(list);
    }

    private List<Hrana> dejVsechnyHranyGrafu() {
        List<Hrana> listHran = new ArrayList<>();
        Iterator<Vrchol> it = strom.getIterator();
        while (it.hasNext()) {
            Vrchol vrchol = it.next();
            vrchol.seznamHran.forEach((hrana) -> {
                listHran.add(hrana);
            });
        }
        return listHran;
    }

    @Override
    public Iterator<V> getIteratorVrcholu() {
        List<V> list = new ArrayList<>();
        Iterator<Vrchol> iterator = strom.getIterator();
        while (iterator.hasNext()) {
            Vrchol vrchol = iterator.next();
            list.add(vrchol.data);
        }
        return list.iterator();
    }

    @Override
    public void zrusHrany() {
        Iterator<Vrchol> it = strom.getIterator();
        while (it.hasNext()) {
            Vrchol vrchol = it.next();
            vrchol.seznamHran.clear();
        }
    }

    @Override
    public void zrus() {
        strom.zrus();
    }

    private class Vrchol extends Point {

        private final V data;
        private final List<Hrana> seznamHran;

        public Vrchol(V data) {
            super(data.getX(), data.getY());
            this.data = data;
            this.seznamHran = new ArrayList<>();
        }

        public void pridejHranu(Hrana hrana) {
            seznamHran.add(hrana);
        }

        public H odeberHranu(Hrana hrana) {
            Hrana odeber = seznamHran.remove(seznamHran.indexOf(hrana));
            return odeber.data;
        }

        public V getData() {
            return data;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 31 * hash + Objects.hashCode(this.data);
            hash = 31 * hash + Objects.hashCode(this.seznamHran);
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
            final Vrchol other = (Vrchol) obj;
            if (!Objects.equals(this.data, other.data)) {
                return false;
            }
            if (!Objects.equals(this.seznamHran, other.seznamHran)) {
                return false;
            }
            return true;
        }

    }

    private class Hrana implements Serializable {

        private final Vrchol vrcholStart;
        private final Vrchol vrcholCil;
        private final H data;
        private final E klic;
        private boolean povoleno;

        public Hrana(Vrchol vrcholStart, Vrchol vrcholCil, H data, E klic, boolean povoleno) {
            this.vrcholStart = vrcholStart;
            this.vrcholCil = vrcholCil;
            this.data = data;
            this.klic = klic;
            this.povoleno = povoleno;
        }

        public E getKlic() {
            return klic;
        }

    }

}
