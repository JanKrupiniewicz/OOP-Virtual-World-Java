import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Menu extends JFrame implements ActionListener {
    private final JFrame frame;
    private JFrame wczytywaniePlanszy;
    private final JTextField getSizeX;
    private final JTextField getSizeY;
    private JTextField sciezkaPliku;
    private JCheckBox polaSzescienne;
    private JCheckBox polaKwadratowe;
    private final JButton startGry;
    private final JButton wczytajGre;
    private JButton zatwierdzPlik;
    private final Font mojFont;
    Menu() {
        //Jframe
        frame = new JFrame("OOP Symulator Świata");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);
        frame.setLayout(null);

        mojFont = new Font("Arial", Font.BOLD, 20);

        JLabel tytulGry = new JLabel("OOP SYMULATOR ŚWIATA");
        tytulGry.setBounds(50, 10, 300, 30);
        tytulGry.setFont(mojFont);
        tytulGry.setHorizontalAlignment(JLabel.CENTER);

        JLabel poleTxt1 = new JLabel("Wprowadź wymiar planszy: ");
        poleTxt1.setBounds(50, 60, 300, 30);
        poleTxt1.setFont(mojFont);
        poleTxt1.setHorizontalAlignment(JLabel.CENTER);

        getSizeX = new JTextField("oX");
        getSizeX.setBounds(50, 100, 300, 50);
        getSizeX.setFont(new Font("Arial", Font.PLAIN, 20));

        getSizeY = new JTextField("oY");
        getSizeY.setBounds(50, 190, 300, 50);
        getSizeY.setFont(new Font("Arial", Font.PLAIN, 20));

        JLabel poleTxt2 = new JLabel("Wybierz rodzaj planszy: ");
        poleTxt2.setBounds(50, 260, 300, 30);
        poleTxt2.setFont(mojFont);
        poleTxt2.setHorizontalAlignment(JLabel.CENTER);

        polaSzescienne = new JCheckBox();
        polaSzescienne.setText("Plansza sześcienna");
        polaSzescienne.setBounds(50, 310, 300, 30);
        polaSzescienne.setFocusable(false);
        polaSzescienne.setFont(new Font("Arial", Font.PLAIN, 20));
        polaSzescienne.setHorizontalAlignment(JCheckBox.CENTER);

        polaKwadratowe = new JCheckBox();
        polaKwadratowe.setText("Plansza kwadratowa");
        polaKwadratowe.setBounds(50, 360, 300, 30);
        polaKwadratowe.setFocusable(false);
        polaKwadratowe.setFont(new Font("Arial", Font.PLAIN, 20));
        polaKwadratowe.setHorizontalAlignment(JCheckBox.CENTER);
        polaKwadratowe.addActionListener(this);


        startGry = new JButton("Start rozgrywki");
        startGry.setBounds(50, 430, 300, 50);
        startGry.setFocusable(false);
        startGry.setFont(mojFont);
        startGry.addActionListener(this);

        wczytajGre = new JButton("Wczytaj planszę");
        wczytajGre.setBounds(50, 490, 300, 50);
        startGry.setFocusable(false);
        wczytajGre.setFont(mojFont);
        wczytajGre.addActionListener(this);

        frame.add(polaSzescienne);
        frame.add(polaKwadratowe);
        frame.add(poleTxt2);

        frame.add(tytulGry);
        frame.add(poleTxt1);
        frame.add(getSizeX);
        frame.add(getSizeY);
        frame.add(startGry);
        frame.add(wczytajGre);
        frame.setVisible(true);
    }

    private void wczytajPlansze() {
        wczytywaniePlanszy = new JFrame("Wczytwanie Planszy");
        wczytywaniePlanszy.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        wczytywaniePlanszy.setSize(400, 250);
        wczytywaniePlanszy.setLayout(null);
        wczytywaniePlanszy.setResizable(false);

        JLabel info = new JLabel("Wprowadź ścieżkę do pliku: ");
        info.setBounds(50, 10, 300, 30);
        info.setFont(mojFont);
        info.setHorizontalAlignment(JLabel.CENTER);

        sciezkaPliku = new JTextField();
        sciezkaPliku.setBounds(50, 60, 300, 50);
        sciezkaPliku.setFont(mojFont);

        zatwierdzPlik = new JButton("Zatwierdź podaną scieżkę");
        zatwierdzPlik.setBounds(50, 130, 300, 50);
        zatwierdzPlik.setFocusable(false);
        zatwierdzPlik.setFont(mojFont);
        zatwierdzPlik.addActionListener(this);

        wczytywaniePlanszy.add(info);
        wczytywaniePlanszy.add(sciezkaPliku);
        wczytywaniePlanszy.add(zatwierdzPlik);
        wczytywaniePlanszy.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == startGry)
        {
            try {
                int sizeX = Integer.parseInt(getSizeX.getText());
                int sizeY = Integer.parseInt(getSizeY.getText());
                if (sizeX > 9 && sizeY > 9) {
                    frame.dispose();
                    frame.setVisible(false);
                    if (polaKwadratowe.isSelected() && !polaSzescienne.isSelected()) {
                        Swiat mojaGra = new SwiatKwadratowy(sizeX, sizeY);
                        new ObrazPlanszy(mojaGra);
                    }
                    else if (!polaKwadratowe.isSelected() && polaSzescienne.isSelected()) {
                        Swiat mojaGra = new SwiatSzescienny(sizeX, sizeY);
                        new ObrazPlanszy(mojaGra);
                    }
                    else {
                        JOptionPane.showMessageDialog(this, "Wybierz rodzaj planszy.");
                        Menu menu = new Menu();
                    }
                }
                else {
                    JOptionPane.showMessageDialog(this, "Podaj wartości większe niż 10.");
                }
            }
            catch (NumberFormatException excpt) {
                JOptionPane.showMessageDialog(this, "Wprowadź poprawne wartości.");
            }
        }
        else if(e.getSource() == wczytajGre)
        {
            wczytajPlansze();
        }
        else if(e.getSource() == zatwierdzPlik)
        {
            String sciezka = sciezkaPliku.getText();
            File nowyPlik = new File(sciezka);
            if (!nowyPlik.exists()) {
                JOptionPane.showMessageDialog(null, "Plik nie istnieje.");
                return;
            }
            else {
                frame.dispose();
                wczytywaniePlanszy.dispose();
                frame.setVisible(false);
                wczytywaniePlanszy.setVisible(false);
                try {
                    if ('k' == Swiat.rodzajSwiata(sciezka)) {
                        Swiat mojaGra = new SwiatKwadratowy(sciezka);
                        new ObrazPlanszy(mojaGra);
                    }
                    else if ('s' == Swiat.rodzajSwiata(sciezka)) {
                        Swiat mojaGra = new SwiatSzescienny(sciezka);
                        new ObrazPlanszy(mojaGra);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Plik wadliwy.");
                        return;
                    }
                }
                catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
