import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;
import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;
import static java.lang.Math.*;

public class ObrazPlanszy extends JFrame implements ActionListener, KeyListener {
    private static class MyPanel extends JPanel implements ActionListener, MouseListener {
        private final static int HEX_ROZMIAR = 5;
        private static final Color COLOR_RAMY = Color.BLACK;
        private final int sizeX;
        private final int sizeY;
        public JFrame wybierzZwierze;
        private final Swiat mojaGra;
        private JTextField getWiek;
        private JTextField getSila;
        private JTextField getIni;
        JComboBox<String> wybierzGatunek;
        JButton dodajOrganizm;
        String nazwaGatunku;
        private boolean czyRoslina;
        private int x;
        private int y;

        MyPanel(int sizeX, int sizeY, Swiat mojaGra) {
            this.sizeX = sizeX;
            this.sizeY = sizeY;
            this.mojaGra = mojaGra;
            this.addMouseListener(this);
        }

        public void paint(Graphics g) {
            super.paintComponent(g);
            if(mojaGra.getRodzajSwiata() == 'k')
            {
                for(int i = 0 ; i < sizeY ; i++)
                {
                    for(int j = 0 ; j < sizeX ; j++)
                    {
                        g.setColor(mojaGra.getOrganizm(i, j).Color());
                        g.fillRect(j*15, i*15, 15, 15);
                    }
                }
            }
            else if(mojaGra.getRodzajSwiata() == 's')
            {
                for(int i = 0 ; i < sizeY ; i++)
                {
                    for(int j = 0 ; j < sizeX ; j++)
                    {
                        rysujFigure(g, i, j);
                    }
                }
            }
        }
        private void rysujFigure(Graphics g, int posX, int posY)
        {
            int x = 10;
            int y = sizeY*HEX_ROZMIAR - (int) ((int)(1/2)*sqrt(pow(15*sizeX, 2)+pow(15*sizeY,2)));

            int dx = (int)((Math.cos(toRadians(60))*HEX_ROZMIAR)+0.5);
            int dy = (int)((Math.sin(toRadians(60))*HEX_ROZMIAR)+0.5);

            int newX = x+((HEX_ROZMIAR*2-dx)*posX);
            int newY = y-(dy*posX);

            newX+= ((HEX_ROZMIAR*2-dx)*posY);
            newY+= (dy*posY);

            rysujHexagon(g, newX, newY, posX, posY, toRadians(90));
        }
        private void rysujHexagon(Graphics g, int x, int y, int posX, int posY, double rotacja) {
            Polygon hex = new Polygon();
            for (int i = 0; i < 6; i++) {
                double angle = rotacja + 2 * Math.PI / 6 * (i + 0.5);
                int xPos = x + (int) (HEX_ROZMIAR * Math.cos(angle));
                int yPos = y + (int) (HEX_ROZMIAR * Math.sin(angle));
                hex.addPoint(xPos, yPos);
            }
            g.setColor(mojaGra.getOrganizm(posX,posY).Color());
            g.fillPolygon(hex);
            g.setColor(COLOR_RAMY);
            g.drawPolygon(hex);
        }

        public void stworzOrganizm(int x, int y) {

            wybierzZwierze = new JFrame("Generator organizmów");
            wybierzZwierze.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            wybierzZwierze.setLayout(null);
            wybierzZwierze.setSize(400, 500);

            String[] organizmy = {"Antylopa", "Barszcz Sosnowskiego", "Guarana",
                    "Lis", "Mlecz", "Owca", "Trawa", "Wilcze Jagody", "Wilk", "Zolw"};

            JLabel tekstInfo = new JLabel("Stwórz organizm: ");
            tekstInfo.setBounds(50, 10, 300, 30);
            tekstInfo.setFont(new Font("Arial", Font.BOLD, 20));
            tekstInfo.setHorizontalAlignment(JLabel.CENTER);

            wybierzGatunek = new JComboBox<String>(organizmy);
            wybierzGatunek.setBounds(50, 50, 300, 30);
            wybierzGatunek.setFont(new Font("Arial", Font.BOLD, 20));
            wybierzGatunek.addActionListener(this);

            getWiek = new JTextField("Wiek");
            getWiek.setBounds(50, 90, 300, 50);
            getWiek.setFont(new Font("Arial", Font.PLAIN, 20));

            getSila = new JTextField("Siła");
            getSila.setBounds(50, 150, 300, 50);
            getSila.setFont(new Font("Arial", Font.PLAIN, 20));

            getIni = new JTextField("Inicjatywa");
            getIni.setBounds(50, 210, 300, 50);
            getIni.setFont(new Font("Arial", Font.PLAIN, 20));

            dodajOrganizm = new JButton("Dodaj organizm");
            dodajOrganizm.setBounds(50, 280, 300, 50);
            dodajOrganizm.setFocusable(false);
            dodajOrganizm.setFont(new Font("Arial", Font.PLAIN, 20));
            dodajOrganizm.addActionListener(this);

            wybierzZwierze.add(tekstInfo);
            wybierzZwierze.add(wybierzGatunek);
            wybierzZwierze.add(getWiek);
            wybierzZwierze.add(getSila);
            wybierzZwierze.add(getIni);
            wybierzZwierze.add(dodajOrganizm);
            wybierzZwierze.setVisible(true);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if(mojaGra.getRodzajSwiata() == 'k')
            {
                x = e.getX()/15;
                y = e.getY()/15;
                if (mojaGra.getOrganizm(y, x) instanceof Ziemia) {
                    stworzOrganizm(x, y);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Pole zajęte.");
                    return;
                }
            }
            else if(mojaGra.getRodzajSwiata() == 's')
            {
                x = e.getX()/15;
                y = e.getY()/15;

                int wspX = (int) (x / (1.5 * HEX_ROZMIAR));
                int wspY = (int) ((y - wspX % 2 * HEX_ROZMIAR * Math.sqrt(3) / 2) / (HEX_ROZMIAR * Math.sqrt(3)));

                if (mojaGra.getOrganizm(wspY, wspX) instanceof Ziemia) {
                    stworzOrganizm(wspX, wspY);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Pole zajęte.");
                    return;
                }
            }
        }
        @Override
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == wybierzGatunek) {
                nazwaGatunku = (String) wybierzGatunek.getSelectedItem();
                if(nazwaGatunku == "Guarana" || nazwaGatunku == "Barszcz Sosnowskiego" || nazwaGatunku == "Mlecz" ||
                            nazwaGatunku == "Trawa" || nazwaGatunku == "Wilcze Jagody") {
                    getIni.setEditable(false);
                    czyRoslina = true;
                }
                else {
                    getIni.setEditable(true);
                    czyRoslina = false;
                }
            }
            if(e.getSource() == dodajOrganizm) {
                try {
                    int wiek = Integer.parseInt(getWiek.getText());
                    int sila = Integer.parseInt(getSila.getText());
                    int inicjatywa = 0;
                    if (!czyRoslina) {
                         inicjatywa = Integer.parseInt(getIni.getText());
                    }

                    if (wiek > 0 && sila > 0 && (inicjatywa > 0 || czyRoslina)) {
                        Organizm nowyOrganizm;
                        switch (nazwaGatunku) {
                            case "Antylopa" -> nowyOrganizm = new Antylopa(mojaGra, x, y, wiek, sila, inicjatywa);
                            case "Barszcz Sosnowskiego" -> nowyOrganizm = new BarszczSosnowskiego(mojaGra, x, y, wiek, sila);
                            case "Guarana" -> nowyOrganizm = new Guarana(mojaGra, x, y, wiek, sila);
                            case "Lis" -> nowyOrganizm = new Lis(mojaGra, x, y, wiek, sila, inicjatywa);
                            case "Mlecz" -> nowyOrganizm = new Mlecz(mojaGra, x, y, wiek, sila);
                            case "Owca" -> nowyOrganizm = new Owca(mojaGra, x, y, wiek, sila, inicjatywa);
                            case "Trawa" -> nowyOrganizm = new Trawa(mojaGra, x, y, wiek, sila);
                            case "Wilcze Jagody" -> nowyOrganizm = new WilczeJagody(mojaGra, x, y, wiek, sila);
                            case "Wilk" -> nowyOrganizm = new Wilk(mojaGra, x, y, wiek, sila, inicjatywa);
                            case "Zolw" -> nowyOrganizm = new Zolw(mojaGra, x, y, wiek, sila, inicjatywa);
                            default -> nowyOrganizm = null;
                        };
                        wybierzZwierze.dispose();
                        wybierzZwierze.setVisible(false);
                    }
                    else {
                        JOptionPane.showMessageDialog(this, "Podaj wartości większe niż 0.");
                    }
                }
                catch (NumberFormatException excpt) {
                    JOptionPane.showMessageDialog(this, "Wprowadź poprawne wartości.");
                }
            }

        }
    }
    private static class PanelTekstowy extends JPanel {
        private final Swiat mojaGra;
        private final JTextArea area;
        int aktualnaTura;

        PanelTekstowy(Swiat mojaGra) {
            aktualnaTura = mojaGra.getTura();
            this.mojaGra = mojaGra;

            area = new JTextArea();
            area.setOpaque(false);
            area.setFont(new Font("Arial", Font.BOLD, 20));
            area.setCaretColor(area.getBackground());

            add(area);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if(aktualnaTura == mojaGra.getTura()) {
                return;
            }
            aktualnaTura = mojaGra.getTura();
            area.setText(" ");
            area.setText("Tura: " + mojaGra.getTura() + "\n");
            for (int i = 0; i < mojaGra.getRozmKomentarzy(); i++) {
                area.setText(area.getText() + mojaGra.getKomentarz(i) + '\n');
            }
            mojaGra.czyscKomentarze();
            area.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        }
    }

    private final JFrame frame;
    private JFrame koniecGry;
    private JFrame zapisPliku;
    private final Swiat mojaGra;
    private final MyPanel panel;
    private final PanelTekstowy panelTxt;
    private final JButton kolejnaTura;
    private JButton koniecRozgrywki;
    private JButton zapiszDoPliku;
    private JTextField nazwaPliku;
    private final JButton zapiszGre;
    private final JButton wrocDoMenu;
    private final Font mojFont;

    public ObrazPlanszy(Swiat mojaGra) {
        this.mojaGra = mojaGra;
        int sizeX = mojaGra.getSizeX();
        int sizeY = mojaGra.getSizeY();

        //JFrame
        frame = new JFrame("Symulator Rozgrywki");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(15*sizeX + 600 , 15*sizeY + 300);
        frame.setLayout(null);

        //Font
        mojFont = new Font("Arial", Font.BOLD, 20);

        //MyPanel
        panel = new MyPanel(sizeX, sizeY, mojaGra);
        panel.setBounds(50, 10, 15*sizeX, 15*sizeY);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));

        panelTxt = new PanelTekstowy(mojaGra);
        panelTxt.setBounds(15*sizeX + 70, 10, 500, 15*sizeY + 240);
        panelTxt.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        panelTxt.setFont(mojFont);

        //Buttons
        kolejnaTura = new JButton("Nowa tura");
        kolejnaTura.setBounds(50, 15*sizeY + 50, 15*sizeX, 50);
        kolejnaTura.setFocusable(false);
        kolejnaTura.setFont(mojFont);
        kolejnaTura.addActionListener(this);

        zapiszGre = new JButton("Zapisz plansze");
        zapiszGre.setBounds(50, 15*sizeY + 130, 15*sizeX, 50);
        zapiszGre.setFocusable(false);
        zapiszGre.setFont(mojFont);
        zapiszGre.addActionListener(this);

        wrocDoMenu = new JButton("Wróć do Menu");
        wrocDoMenu.setBounds(50, 15*sizeY + 190, 15*sizeX, 50);
        wrocDoMenu.setFocusable(false);
        wrocDoMenu.setFont(mojFont);
        wrocDoMenu.addActionListener(this);

        frame.addKeyListener(this);
        frame.add(panel);
        frame.add(panelTxt);
        frame.add(kolejnaTura);
        frame.add(zapiszGre);
        frame.add(wrocDoMenu);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void koniecGry() {
        koniecGry = new JFrame("Koniec Gry");
        koniecGry.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        koniecGry.setSize(300 , 300);
        koniecGry.setLayout(null);

        JLabel info = new JLabel("Koniec Gry");
        info.setBounds(60, 60, 180, 50);
        info.setFont(mojFont);
        info.setHorizontalAlignment(JLabel.CENTER);

        koniecRozgrywki = new JButton("Powrót do Menu");
        koniecRozgrywki.setBounds(25, 130, 250, 50);
        koniecRozgrywki.setFocusable(false);
        koniecRozgrywki.setFont(mojFont);
        koniecRozgrywki.addActionListener(this);

        koniecGry.add(info);
        koniecGry.add(koniecRozgrywki);
        koniecGry.setResizable(false);
        koniecGry.setVisible(true);
    }
    private void zapiszDoPliku() {
        zapisPliku = new JFrame("Zapisywanie do pliku");
        zapisPliku.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        zapisPliku.setSize(450 , 250);
        zapisPliku.setResizable(false);
        zapisPliku.setLayout(null);

        JLabel tekstInfo = new JLabel("Wprowadź \nnazwe pliku: ");
        tekstInfo.setBounds(20, 10, 400, 30);
        tekstInfo.setFont(mojFont);
        tekstInfo.setHorizontalAlignment(JLabel.CENTER);

        nazwaPliku = new JTextField("Twoja nazwa");
        nazwaPliku.setBounds(20, 50, 400, 50);
        nazwaPliku.setFont(mojFont);

        zapiszDoPliku = new JButton("Zapisz do pliku");
        zapiszDoPliku.setBounds(20, 110, 400, 50);
        zapiszDoPliku.setFocusable(false);
        zapiszDoPliku.setFont(mojFont);
        zapiszDoPliku.addActionListener(this);
        zapiszDoPliku.addActionListener(this);

        zapisPliku.add(tekstInfo);
        zapisPliku.add(nazwaPliku);
        zapisPliku.add(zapiszDoPliku);
        zapisPliku.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == kolejnaTura)
        {
            panel.repaint();
            panelTxt.repaint();
            frame.requestFocus();
            if(!mojaGra.getCzlowiekZyje()) {
                koniecGry();
            }
        }
        else if(e.getSource() == wrocDoMenu || e.getSource() == koniecRozgrywki)
        {
            frame.dispose();
            frame.setVisible(false);
            if(e.getSource() == koniecRozgrywki)
            {
                koniecGry.dispose();
                koniecGry.setVisible(false);
            }
            Menu menu = new Menu();
        }
        else if(e.getSource() == zapiszGre)
        {
            zapiszDoPliku();
        }
        else if(e.getSource() == zapiszDoPliku)
        {
            String nazwa = nazwaPliku.getText();
            mojaGra.zapiszDoPliku(nazwa);
            zapisPliku.dispose();
            zapisPliku.setVisible(false);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        char direction = 'u';

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                if(mojaGra.getRodzajSwiata() == 'k') {
                    direction = 'w';
                }
                else {
                    direction = 's';
                }
                break;
            case KeyEvent.VK_DOWN:
                if(mojaGra.getRodzajSwiata() == 'k') {
                    direction = 's';
                }
                else {
                    direction = 'w';
                }
                break;
            case KeyEvent.VK_RIGHT:
                direction = 'd';
                break;
            case KeyEvent.VK_LEFT:
                direction = 'a';
                break;
        };

        mojaGra.wykonajTure(direction);
        mojaGra.rysujSwiat();
        panel.repaint();
        panel.requestFocusInWindow();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

}