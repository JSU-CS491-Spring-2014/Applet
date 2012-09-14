
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * A Clause object is composed of text, conjunction, chapter, and verse.
 */
public class Clause extends JPanel {

    private String data;                // The text of the Clause
    private String conj;                // The conjunction of the Clause
    private String chap;                // The chapter number of the Clause
    private String vrse;                // The verse number of the Clause
    private int x;                      // The starting x-value for drawing
    private int y;                      // The starting y-value for drawing
    private int h;                      // The height needed to display the data
    private int w;                      // The width needed to display the data
    private int mid;                    // The mid-point of the Clause?
    private String[] text;              // The word-wrapped version of the data
    private Rectangle big;              // The outline for Node data?
    private Rectangle small;            // The outline for chapter/verse?
    private Point conjPoint;            // ?
    private final int CHAR_NUM = 36;    // Is this the maximum characters you want in a line?
    private JTextArea myTextArea;       // This is where I will show my data.
    private JScrollPane myScrollPane;   // This allows the User to scroll through the text area.

    public void enableTextArea() {
        myTextArea.setEnabled(true);
        myTextArea.requestFocus();
    }

    /**
     * Makes a new Clause object with x and y values set to 0.
     *
     * @param d the text data of the Clause
     * @param c the conjunction of the Clause
     * @param ch the chapter number of the Clause
     * @param v the verse number of the Clause
     */
    public Clause(String d, String c, String ch, String v) {
        super();
        data = d;
        conj = c;
        chap = ch;
        vrse = v;
        x = 0;
        y = 0;
        w = 200;
        setUpArray();
        calculateMidPoint();
        // makeBoxes();

        if (chap.isEmpty() || vrse.isEmpty()) {
            setBorder(javax.swing.BorderFactory.createTitledBorder(conj));
        } else {
            setBorder(javax.swing.BorderFactory.createTitledBorder(chap + ":" + vrse + " " + conj));
        }

        // Set my text box up
        myTextArea = new JTextArea();
        myTextArea.setLineWrap(true);
        myTextArea.setWrapStyleWord(true);
        myTextArea.setText(data);

        // Add scroll abilities to the text box
        myScrollPane = new JScrollPane();
        myScrollPane.setViewportView(myTextArea);

        // Add the scroll pane (with the text area inside) to the Clause
        setLayout(new GridLayout(1, 1));
        add(myScrollPane);

        // The text area should start disabled.
        myTextArea.setEnabled(false);

        // Add click to enable on the text areas.
        myTextArea.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                enableTextArea();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        // When focus is lost, disable the text area.
        myTextArea.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                myTextArea.setEnabled(false);
            }
        });
    }

    /**
     * This repositions the Clause in the NodePanel. This is called after updating.
     */
    public void updateClauseBounds() {
        setBounds(x, y, 260, 95);
    }

    /**
     * Sets the dimension for big, small, and sets the point for the conjunction
     * /
    private void makeBoxes() {
    // <-- x, y, h, w are used to make a Rectangle for the data, can use the check bounds method in GUI
    big = new Rectangle(x, y, w, h);
    
    //small box is for chapter verse
    small = new Rectangle(x, y - 12, 50, 12);
    
    //ConjPoint is the start point for displaying the Conjuntion
    conjPoint = new Point(5 + (x + (w + x)) / 2, y - 5);
    }*/
    /**
     * Calculates the mid point of the data box
     */
    private void calculateMidPoint() {
        mid = (y + (h + y)) / 2;
    }

    /**
     * Sets the word wrap String array. Each index of the array are a separate
     * line.
     */
    private void setUpArray() {
        String[] temp = new String[data.length() / CHAR_NUM + 2];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = "";
        }

        if (data.length() > CHAR_NUM) {
            char[] q = data.toCharArray();
            int startSub = 0;
            int endSub = 0;
            for (int i = 0; i < temp.length; i++) {
                for (int j = endSub + CHAR_NUM; j >= 0; j--) {
                    try {
                        if (q[j] == ' ' && j > endSub) {
                            endSub = j + 1;
                        }
                    } catch (ArrayIndexOutOfBoundsException ohNo) {
                        endSub = q.length;
                    }
                }

                temp[i] = data.substring(startSub, endSub);
                startSub = endSub;
            }
        } else {
            temp[0] = data;
            h = h + 9;
        }
        //Increment
        int total = 0;
        for (int i = 0; i < temp.length; i++) {
            if (!temp[i].equals("")) {
                h += 18;
                total++;
            }
        }
        text = new String[total];
        System.arraycopy(temp, 0, text, 0, text.length);
    }

    /**
     * Returns the XML of this Clause Possible conjunctions are not yet taken
     * into account
     *
     * @return result	String that has XML tags and clause data
     */
    public String toXML() {
        String result = "";
        result += "<clause><conj>" + conj + "</conj><text chapter=\"" + chap + "\" verse=\"" + vrse + ">" + data + "</text></clause>";
        return result;
    }

    @Override
    public String toString() {
        if (data.equals("root")) {
            return conj;
        } else {
            String result = "";
            result += chap + ":" + vrse + " Conj: " + conj + " Text: " + data;
            return result;
        }
    }

    //Getters
    /**
     * @return String text of the Clause
     */
    public String getData() {
        return data;
    }

    /**
     * @return String conjunction of the Clause
     */
    public String getConj() {
        return conj;
    }

    /**
     * @return String chapter of the Clause
     */
    public String getChap() {
        return chap;
    }

    /**
     * @return String verse of the Clause
     */
    public String getVrse() {
        return vrse;
    }

    /**
     * @return int x value of Clause
     */
    public int getX() {
        return x;
    }

    /**
     * @return int y value of Clause
     */
    public int getY() {
        return y;
    }

    /**
     * @return int h value of Clause
     */
    public int getH() {
        return h;
    }

    /**
     * @return int mid value of Clause
     */
    public int getMid() {
        return mid;
    }

    /**
     * @return String[] word wrap version of data(each line is an index of the
     * array is a single line)
     */
    public String[] getWordWrap() {
        return text;
    }

    public Rectangle getBig() {
        return big;
    }

    public Rectangle getSmall() {
        return small;
    }

    public Point getP() {
        return conjPoint;
    }

    //Setters
    /**
     * @param d	sets data equal to d, and recalculates height and word wrap
     */
    public void setData(String d) {
        data = d;
        h = 0;
        setUpArray();
        //makeBoxes();
    }

    /**
     * @param c sets conj equal to c
     */
    public void setConj(String c) {
        conj = c;
    }

    /**
     * @param c sets chap equal to c
     */
    public void setChap(String c) {
        chap = c;
    }

    /**
     * @param v sets vrse equal to v
     */
    public void setVrse(String v) {
        vrse = v;
    }

    /**
     * @param i sets x equal to i
     */
    public void setX(int i) {
        x = i;
        //makeBoxes();
        //updateClauseBounds();
    }

    /**
     * @param i sets y equal to i
     */
    public void setY(int i) {
        y = i;
        calculateMidPoint();
        //makeBoxes();
        //updateClauseBounds();
    }
    //For testing this class
	/*
     * public static void main(String[] args) { Clause c = new Clause("This is
     * sample text", "and", "1", "1"); System.out.println(c.toString() +"\n");
     * System.out.println(c.toXML()); }
     */
}