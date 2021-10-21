package dziedziczenie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;

public class Main extends JFrame {

    public Main() {
        this.setTitle("Pakowacz");
        this.setBounds(400, 400, 400, 400);
        this.setVisible(true);
        this.setJMenuBar(pasekMenu);

        JMenu menuPlik = pasekMenu.add(new JMenu("Plik"));

        Action akcjaDodawania = new Akcja("Dodaj", "Dodaj nowy wpis do archiwum", "control D", new ImageIcon("Dodaj.png"));
        Action akcjaUsuwania = new Akcja("Usuń", "Usuń zaznaczony pliki", "control U",new ImageIcon("Usuń.png"));
        Action akcjaZipowania = new Akcja("Zip", "Zipuj", "control Z", new ImageIcon("Zip.png"));

        JMenuItem menuOtworz = menuPlik.add(akcjaDodawania);
        JMenuItem menuUsun = menuPlik.add(akcjaUsuwania);
        JMenuItem menuZip = menuPlik.add(akcjaZipowania);

        bDodaj = new JButton(akcjaDodawania);
        bUsun = new JButton(akcjaUsuwania);
        bZip = new JButton(akcjaZipowania);

        lista.setBorder(BorderFactory.createEtchedBorder());
        GroupLayout layout = new GroupLayout(this.getContentPane());

        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addComponent(lista, 100, 150, Short.MAX_VALUE)
                        .addContainerGap(0, Short.MAX_VALUE)
                        .addGroup(
                                layout.createParallelGroup().addComponent(bDodaj).addComponent(bUsun).addComponent(bZip)

                        )
        );
        layout.setVerticalGroup(
                layout.createParallelGroup()
                        .addComponent(lista, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup().addComponent(bDodaj).addComponent(bUsun).addGap(50).addComponent(bZip)
                        )
        );

        this.getContentPane().setLayout(layout);
        this.setDefaultCloseOperation(3);
        this.pack();

    }
    private DefaultListModel modelListy = new DefaultListModel()
    {
        public void addElement(Object obj)
        {
            lista.add(obj);
            super.addElement(((File)obj).getName());
        }
        public  Object get (int index)
        {
            return lista.get(index);
        }
        ArrayList lista = new ArrayList();

        public Object remove(int index) //Tutaj dublujemy usuwanie indexów usniętych również z ArrayList
        {
            lista.remove(index);
            return super.remove(index);
        }
    };


    private JList lista = new JList(modelListy);
    private JButton bDodaj;
    private JButton bUsun;
    private JButton bZip;
    private JMenuBar pasekMenu = new JMenuBar();
    private JFileChooser wybieracz = new JFileChooser();

    public static void main(String[] args) {
        new Main();
    }

    private class Akcja extends AbstractAction {
        public Akcja(String nazwa, String opis, String klawiaturowySkrot) {
            this.putValue(Action.NAME, nazwa);
            this.putValue(Action.SHORT_DESCRIPTION, opis);
            this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(klawiaturowySkrot));;
        }

        public Akcja(String nazwa, String opis, String klawiaturowySkrot, Icon icon) {
            this(nazwa, opis, klawiaturowySkrot);
            this.putValue(Action.SMALL_ICON, icon);
        }

        public void actionPerformed(ActionEvent e)
        {
            if(e.getActionCommand().equals("Dodaj"))
             dodajWpisyDoArchiwum();
            if(e.getActionCommand().equals("Usuń"))
                usuwanieWpisowZList();
        if(e.getActionCommand().equals("Zip"))
                System.out.println("zIPOWANIE!!");
        }
        private void dodajWpisyDoArchiwum()
        {
        wybieracz.setCurrentDirectory(new File(System.getProperty("user.dir")));
        wybieracz.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        wybieracz.setMultiSelectionEnabled(true);

          int tmp = wybieracz.showDialog(rootPane,"Dodaj do archiwum");
            if( tmp == JFileChooser.APPROVE_OPTION) //Sprawdzamy czy jeżli 0 to działamy
            {
               File [] sciezki =  wybieracz.getSelectedFiles();
               for (int i = 0; i < sciezki.length; i++)
               {
                   System.out.println(sciezki[i]);
                   if (!czyWpisSiePowtarza(sciezki[i].getPath()))
                    modelListy.addElement(sciezki[i]);
               }
            }
        }
        private void usuwanieWpisowZList()
        {
            int [] tmp = lista.getSelectedIndices();
            for (int i = 0; i < tmp.length;i++)
            modelListy.remove(tmp[i]-i);


        }
        private boolean czyWpisSiePowtarza(String testowanyWpis)
        {
            for(int i = 0; i <modelListy.getSize();i++)
            {
                if(((File)modelListy.get(i)).getPath().equals(testowanyWpis))
                    return true;
            }
            return false;
        }
        private void stworzArchiwumZip()
        {

            wybieracz.showDialog(rootPane,"Kompresuj");

        }
    }
}