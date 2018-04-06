/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dijkstra;

import graph.IGraph;
import graph.KlicVrchol;
import graph.Mesto;
import graph.Silnice;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 *
 * @author Miloslav Moravec
 *
 */
public class DijkstraAlgorithm {

    private PriorityQueue<Node> fronta;
    private final List<Silnice> seznamHran;
    private Set<Mesto> prosleUzly;
    private Map<Mesto, Mesto> predchudci;
    private Map<Mesto, Integer> vzdalenosti;
    private final IGraph<Mesto, Silnice, KlicVrchol, String> graf;
    private final Comparator<Node> comparator;

    public DijkstraAlgorithm(IGraph<Mesto, Silnice, KlicVrchol, String> graf) {
        this.seznamHran = new ArrayList<>(graf.dejVsechnyHrany());
        this.graf = graf;
        this.comparator = new MestoComparator();
    }

    public LinkedList<LinkedList<Mesto>> dejTrasu(List<Mesto> list) {
        LinkedList<LinkedList<Mesto>> finalTrasa = new LinkedList<>();
        inicializace(list.get(0));

        for (int i = 1; i < list.size(); i++) {
            Mesto konec = list.get(i);
            LinkedList<Mesto> trasa = vypocetTrasy(konec);
            Collections.reverse(trasa);
            finalTrasa.add(trasa);
        }
        return finalTrasa;
    }

    private LinkedList<Mesto> vypocetTrasy(Mesto konec) {

        LinkedList<Mesto> trasa = new LinkedList<>();
        Mesto node = konec;

        if (predchudci.get(node) == null) {
            return null;
        }
        trasa.add(node);

        while (predchudci.get(node) != null) {
            node = predchudci.get(node);
            trasa.add(node);
        }
        return trasa;
    }

    private void inicializace(Mesto zacatek) {
        prosleUzly = new HashSet<>();
        vzdalenosti = new HashMap<>();
        predchudci = new HashMap<>();
        fronta = new PriorityQueue<>(comparator);
        fronta.add(new Node(zacatek, 0));
        vzdalenosti.put(zacatek, 0);

        while ((!fronta.isEmpty())) {
            Node node = fronta.peek();
            fronta.remove();
            najdiNejmensiVzdalenosti(node.getMesto());
            prosleUzly.add(node.getMesto());
        }
    }

    private int dejKratsiVzdalenost(Mesto mesto) {
        Integer vzdalenost = vzdalenosti.get(mesto);
        if (vzdalenost == null) {
            return Integer.MAX_VALUE;
        } else {
            return vzdalenost;
        }
    }

    private void najdiNejmensiVzdalenosti(Mesto node) {
        List<Mesto> sousedi = dejSousedy(node);

        for (Mesto mesto : sousedi) {
            if ((dejKratsiVzdalenost(node) + dejVzdalenost(node, mesto)) < (dejKratsiVzdalenost(mesto))) {
                vzdalenosti.put(mesto, dejKratsiVzdalenost(node) + dejVzdalenost(node, mesto));
                predchudci.put(mesto, node);
                fronta.add(new Node(mesto, dejKratsiVzdalenost(node) + dejVzdalenost(node, mesto)));
            }
        }
    }

    private int dejVzdalenost(Mesto mestoA, Mesto mestoB) {
        return graf.dejHodnoceniHrany(new KlicVrchol(mestoA.getX(), mestoA.getY(), mestoA.getJmeno()), new KlicVrchol(mestoB.getX(), mestoB.getY(), mestoB.getJmeno()));
    }

    private List<Mesto> dejSousedy(Mesto node) {
        List<Mesto> sousedi = new ArrayList<>();
        for (Silnice silnice : seznamHran) {
            if ((silnice.getMestoA().getX() == node.getX()) && (silnice.getMestoA().getY() == node.getY())) {
                Mesto opacne = graf.vratDruhyVrcholHrany(node, silnice);
                if (opacne != null) {
                    if (!jeProsly(opacne)) {
                        sousedi.add(opacne);
                    }
                }

            }
        }
        Iterator<Mesto> it = sousedi.iterator();
        List<Mesto> sousediNovy = new ArrayList<>();
        while (it.hasNext()) {
            Mesto mesto = it.next();
            if (mesto.equals(node)) {
                it.remove();
            } else {
                if (!sousediNovy.contains(mesto)) {
                    sousediNovy.add(mesto);
                }
            }

        }

        return sousediNovy;
    }

    private boolean jeProsly(Mesto mesto) {
        return prosleUzly.contains(mesto);
    }

    private class Node {

        private Mesto mesto;
        private Integer vzdalenost;

        public Node(Mesto mesto, Integer vzdalenost) {
            this.mesto = mesto;
            this.vzdalenost = vzdalenost;
        }

        public Integer getVzdalenost() {
            return vzdalenost;
        }

        public void setVzdalenost(Integer vzdalenost) {
            this.vzdalenost = vzdalenost;
        }

        public Mesto getMesto() {
            return mesto;
        }

        public void setMesto(Mesto mesto) {
            this.mesto = mesto;
        }

    }

    private class MestoComparator implements Comparator<Node> {

        @Override
        public int compare(Node nodeX, Node nodeY) {
            if (nodeX.getVzdalenost() < nodeY.getVzdalenost()) {
                return -1;
            }
            if (nodeX.getVzdalenost() > nodeY.getVzdalenost()) {
                return 1;
            }
            return 0;
        }

    }

}
