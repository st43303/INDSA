/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file;

import graph.IGraph;
import graph.KlicVrchol;
import graph.Mesto;
import graph.Silnice;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Miloslav Moravec
 */
public class SerializaceIO {

    public static void uloz(String jmenoSouboru, IGraph<Mesto, Silnice, KlicVrchol,String> graf) throws IOException {
        try {
            Objects.requireNonNull(jmenoSouboru);
            try (ObjectOutputStream zapis = new ObjectOutputStream(new FileOutputStream(jmenoSouboru))) {
                int pocetVrcholu = graf.dejMohutnost();
                zapis.writeInt(pocetVrcholu);
                Iterator it = graf.getIteratorVrcholu();
                while (it.hasNext()) {
                    Mesto vrchol = (Mesto) it.next();
                    zapis.writeUTF(vrchol.getJmeno());
                    zapis.writeInt(vrchol.getX());
                    zapis.writeInt(vrchol.getY());
                }
                zapis.writeInt(graf.dejVsechnyHrany().size());
                Iterator itH = graf.dejVsechnyHrany().iterator();
                while (itH.hasNext()) {
                    Silnice hrana = (Silnice) itH.next();
                    zapis.writeObject(hrana);
                    List<Mesto> listVrcholuHrany = graf.dejVrcholyHrany((String) hrana.getNazev());
                    Mesto v1 = listVrcholuHrany.get(0);
                    Mesto v2 = listVrcholuHrany.get(1);
                    zapis.writeUTF((String) hrana.getNazev());
                    zapis.writeUTF(v1.getJmeno());
                    zapis.writeInt(v1.getX());
                    zapis.writeInt(v1.getY());
                    zapis.writeUTF(v2.getJmeno());
                    zapis.writeInt(v2.getX());
                    zapis.writeInt(v2.getY());
                }
            }
        } catch (IOException ex) {
            throw new IOException("Nelze ukladat do " + jmenoSouboru);
        }
    }

    public static IGraph<Mesto, Silnice, KlicVrchol,String> nacti(String jmenoSouboru, IGraph<Mesto, Silnice, KlicVrchol,String> graf) throws IOException, ClassNotFoundException {
        try {
            graf.zrus();
            Objects.requireNonNull(jmenoSouboru);
            try (ObjectInputStream cteni = new ObjectInputStream(new FileInputStream(jmenoSouboru))) {
                int pocetVrcholu = cteni.readInt();
                for (int i = 0; i < pocetVrcholu; i++) {
                    String jmeno=cteni.readUTF();
                    int x = cteni.readInt();
                    int y = cteni.readInt();
                    graf.vlozVrchol(new Mesto(x, y,jmeno));
                }
                int pocetHran = cteni.readInt();
                for (int j = 0; j < pocetHran; j++) {
                    Silnice hrana = (Silnice) cteni.readObject();
                    String klic=cteni.readUTF();
                    String jmeno1=cteni.readUTF();
                    int x1 = cteni.readInt();
                    int y1 = cteni.readInt();
                    String jmeno2=cteni.readUTF();
                    int x2 = cteni.readInt();
                    int y2 = cteni.readInt();
                    graf.vlozHranu(hrana, new KlicVrchol(x1, y1,jmeno1), new KlicVrchol(x2, y2,jmeno2),klic,hrana.isPovoleno());
                }
            }

        } catch (IOException ex) {
            throw new IOException("Nelze nacist z " + jmenoSouboru);
        }
        return graf;
    }

}
