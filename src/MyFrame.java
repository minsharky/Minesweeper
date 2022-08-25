import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;

public class MyFrame extends JFrame implements ActionListener {

    private JButton[][] board = new JButton[Custom.frameX.getValue()][Custom.frameY.getValue()];
    private final Boolean[][] enabled = new Boolean[board.length][board.length];
    private final String[][] numBoard = new String[board.length][board.length];
    private final JLabel lose = new JLabel("You Lose!");
    private final JLabel win = new JLabel("You Win!");
    private final JButton again = new JButton("Play Again?");
    private final JButton rules = new JButton("Rules");
    private final JButton custom = new JButton("Customize");
    private final JLabel stats1 = new JLabel("Bombs Percentage: " + (double) Custom.bombs.getValue() + "%");
    private final JLabel stats2 = new JLabel("Rows: " + Custom.frameX.getValue());
    private final JLabel stats3 = new JLabel("Columns: " + Custom.frameY.getValue());
    private int bombs;
    private int buttonsClicked;

    //Minesweeper Frame
    //Frame Size: 1000 x 1000
    MyFrame() {
        super("Minesweeper");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setSize(1000, 1000);

        //Win and lose JPanels and Try Again button are set to invisible
        lose.setBounds(750, 100, 200, 200);
        lose.setFont(new Font("Monaco", Font.BOLD, 20));
        win.setBounds(750, 100, 200, 200);
        win.setFont(new Font("Monaco", Font.BOLD, 20));

        again.setBounds(700, 250, 200, 50);
        again.setFont(new Font("Monaco", Font.BOLD, 20));
        again.setFocusable(false);
        again.addActionListener(this);

        //Customize and rules buttons and stats are added
        rules.setBounds(700, 550, 200, 50);
        rules.setFont(new Font("Monaco", Font.BOLD, 20));
        rules.setFocusable(false);
        rules.addActionListener(this);

        custom.setBounds(700, 500, 200, 50);
        custom.setFont(new Font("Monaco", Font.BOLD, 20));
        custom.setFocusable(false);
        custom.addActionListener(this);

        stats1.setBounds(700, 400, 200, 50);
        stats1.setFont(new Font("Monaco", Font.BOLD, 15));
        stats2.setBounds(700, 420, 200, 50);
        stats2.setFont(new Font("Monaco", Font.BOLD, 15));
        stats3.setBounds(700, 440, 200, 50);
        stats3.setFont(new Font("Monaco", Font.BOLD, 15));

        this.add(lose);
        this.add(win);
        this.add(again);
        this.add(rules);
        this.add(custom);
        this.add(stats1);
        this.add(stats2);
        this.add(stats3);
        again.setVisible(false);
        win.setVisible(false);
        lose.setVisible(false);

        //sets frame to be visible
        setVisible(true);

        //Sets up a 2-D Array of buttons
        int x;
        int y = 0;

        for (int i = 0; i < board.length; i++) {
            x = 20;
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = new JButton();
                board[i][j].setFont(board[i][j].getFont().deriveFont(9.0f));
                board[i][j].addActionListener(this);
                System.out.println(board[i][j].getText());
                board[i][j].setBounds(x, y, 40, 40);
                board[i][j].setFocusable(false);
                add(board[i][j]);
                x += 40;
                
                //Randomly assigns the board of text with bombs w/ randomBomb() method
                numBoard[i][j] = randomBomb();

                //Assigns all spaces in the enabled board to be false as all buttons are initially not clicked
                enabled[i][j] = false;
            }
            y += 40;
        }
        
        //Fills in the rest of the board according to where the bombs are
        fillArray(numBoard);
        this.repaint();
        System.out.println("bombs"+ bombs);
        System.out.println((double)Custom.bombs.getValue()/100);
    }
    
    //assigns a random bomb in a square some percentage of the time
    public String randomBomb() {
        String input;
        double random = (Math.random());
        if (random < (double)Custom.bombs.getValue()/100) {
            //"X" means bomb
            input = "X";
            bombs++;
        } else {
            input = "";
        }
        return input;
    }

    //clears space when a button's text is 0
    //will auto clear space
    public void clearSpace(JButton[][] board, int a, int b) {
        int horizontal;
        int vertical;

        for (int i = 0; i < 8; i++) {
            if (i == 0) {
                // up left
                horizontal = -1;
                vertical = -1;
            } else if (i == 1) {
                // up
                horizontal = 0;
                vertical = -1;
            } else if (i == 2) {
                // up right
                horizontal = 1;
                vertical = -1;
            } else if (i == 3) {
                // right
                horizontal = 1;
                vertical = 0;
            } else if (i == 4) {
                // down right
                horizontal = 1;
                vertical = 1;
            } else if (i == 5) {
                // down
                horizontal = 0;
                vertical = 1;
            } else if (i == 6) {
                // down left
                horizontal = -1;
                vertical = 1;
            } else {
                // left
                horizontal = -1;
                vertical = 0;
            }
            //checks to see if the button is not an edge or corner piece
            if ((a + vertical >= 0) && (a + vertical <= board.length - 1) && (b + horizontal >= 0) && (b + horizontal <= board.length - 1)) {
                //clicks the button
                board[a + vertical][b + horizontal].setEnabled(false);
                board[a + vertical][b + horizontal].setText(numBoard[a + vertical][b + horizontal]);

                //checks to see if the surrounding clicked buttons also are a "0" and if they have already been clicked
                if (board[a + vertical][b + horizontal].getText().equals("0") && !enabled[a + vertical][b + horizontal]) {
                enabled[a + vertical][b + horizontal] = true;
                //recursion and calls clearSpace again
                clearSpace(board, a + vertical, b + horizontal);
                }
            }
        }
    }

    //fills in the 2D array of bombs according to the bombs already randomly placed in it
    //assigns the number of bombs surrounding it on each button
    public void fillArray(String[][] Board) {
        int bomb;
        //checks every button
        for(int i = 0; i < Board.length;i++) {
            for (int j = 0; j < Board[0].length; j++) {
                bomb = 0;

                //buttons not an edge or corner
                if (((i >= 1) && (i < Board.length - 1)) && ((j >= 1) && (j < Board[0].length - 1))) {
                if (Board[i - 1][j - 1].equals("X")) {
                    bomb++;
                }
                if (Board[i - 1][j].equals("X")) {
                    bomb++;
                }
                if (Board[i - 1][j + 1].equals("X")) {
                    bomb++;
                }
                if (Board[i][j - 1].equals("X")) {
                    bomb++;
                }
                if (Board[i][j + 1].equals("X")) {
                    bomb++;
                }
                if (Board[i + 1][j - 1].equals("X")) {
                    bomb++;
                }
                if (Board[i + 1][j].equals("X")) {
                    bomb++;
                }
                if (Board[i + 1][j + 1].equals("X")) {
                    bomb++;
                }
                //top left corner
                } else if((i==0)&&(j==0)) {
                    if (Board[0][1].equals("X")) {
                        bomb++;
                    }
                    if (Board[1][0].equals("X")) {
                        bomb++;
                    }
                    if (Board[1][1].equals("X")) {
                        bomb++;
                    }
                }
                //bottom left corner
                else if((i==0)&&(j==Board.length-1)) {
                    if (Board[0][Board.length - 2].equals("X")) {
                        bomb++;
                    }
                    if (Board[1][Board.length - 1].equals("X")) {
                        bomb++;
                    }
                    if (Board[1][Board.length - 2].equals("X")) {
                        bomb++;
                    }
                }
                //top right corner
                else if((i==Board.length-1)&&(j==0)) {
                    if (Board[Board.length - 2][0].equals("X")) {
                        bomb++;
                    }
                    if (Board[Board.length - 2][1].equals("X")) {
                        bomb++;
                    }
                    if (Board[Board.length - 1][1].equals("X")){
                        bomb++;
                    }
                }
                //bottom right corner
                else if((i==Board.length-1)&&(j==Board.length-1)) {
                    if (Board[Board.length - 2][Board.length-2].equals("X")) {
                        bomb++;
                    }
                    if (Board[Board.length - 2][Board.length-1].equals("X")) {
                        bomb++;
                    }
                    if (Board[Board.length - 1][Board.length-2].equals("X")){
                        bomb++;
                    }
                }
                //buttons that are on the top edge
                else if ((i==0)) {
                    if (Board[i][j-1].equals("X")) {
                        bomb++;
                    }
                    if (Board[i][j+1].equals("X")) {
                        bomb++;
                    }
                    if (Board[i+1][j-1].equals("X")){
                        bomb++;
                    }
                    if (Board[i+1][j].equals("X")){
                        bomb++;
                    }
                    if (Board[i+1][j+1].equals("X")){
                        bomb++;
                    }
                }
                //buttons that are on bottom edge
                else if ((i==Board.length - 1)) {
                    if (Board[i][j-1] .equals("X")){
                        bomb++;
                    }
                    if (Board[i][j+1].equals("X")){
                        bomb++;
                    }
                    if (Board[i-1][j-1].equals("X")){
                        bomb++;
                    }
                    if (Board[i-1][j].equals("X")){
                        bomb++;
                    }
                    if (Board[i-1][j+1].equals("X")){
                        bomb++;
                    }
                }
                //buttons that are on leftmost edge
                else if ((j==0)) {
                    if (Board[i-1][j].equals("X")){
                        bomb++;
                    }
                    if (Board[i][j+1].equals("X")){
                        bomb++;
                    }
                    if (Board[i+1][j].equals("X")){
                        bomb++;
                    }
                    if (Board[i+1][j+1].equals("X")){
                        bomb++;
                    }
                    if (Board[i-1][j+1].equals("X")){
                        bomb++;
                    }
                }
                //buttons that are on rightmost edge
                else if ((j==Board.length - 1)) {
                    if (Board[i-1][j].equals("X")){
                        bomb++;
                    }
                    if (Board[i][j-1].equals("X")){
                        bomb++;
                    }
                    if (Board[i+1][j].equals("X")){
                        bomb++;
                    }
                    if (Board[i+1][j-1].equals("X")){
                        bomb++;
                    }
                    if (Board[i-1][j-1].equals("X")){
                        bomb++;
                    }
                }
                //if the button isn't a bomb, assign it the number of bombs surrounding it
                if(!(Board[i][j].equals("X"))) {
                    Board[i][j] = String.valueOf(bomb);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //if user clicks, rules, open up rules frame
        if(e.getSource() == rules) {
            new Rules();
        }
        //if user clicks, custom, open up custom frame
        if(e.getSource() == custom) {
            new Custom();
        }
        //if user clicks, play again, then reset board
        if(e.getSource() == again) {
            int x;
            int y=0;
            buttonsClicked=0;
            bombs=0;
            //sets all buttons to not clicked
            // sets a new board of bombs
            for (int i = 0; i < board.length; i++) {
                x = 20;
                for (int j = 0; j < board[0].length; j++) {
                    board[i][j].setEnabled(true);
                    board[i][j].setText(null);
                    x += 40;
                    numBoard[i][j] = randomBomb();
                    enabled[i][j] = false;
                }
                y += 40;
            }
            //hide the win and lose screen and play again button
            again.setVisible(false);
            win.setVisible(false);
            lose.setVisible(false);
            stats1.setText("Bombs Percentage: " + (double)Custom.bombs.getValue() + "%");
            stats2.setText("Rows: " + Custom.frameX.getValue());
            stats3.setText("Columns: " + Custom.frameY.getValue());
            fillArray(numBoard);
            System.out.println("bombs: " + bombs);
            System.out.println((double)Custom.bombs.getValue()/100);
            this.repaint();
        }
        //action for a button click
        for (int i = 0; i< board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                //sets a button as clicked and assigns it the number of bombs surrounding it
                if (e.getSource() == board[i][j]) {
                    board[i][j].setEnabled(false);
                    board[i][j].setText(numBoard[i][j]);
                    enabled[i][j] = true;

                    //if the button's text is 0, clearSpace
                    if (board[i][j].getText().equals("0")) {
                        clearSpace(board, i, j);
                    }
                    //if the button is a bomb, set the whole board to clicked
                    if (board[i][j].getText().equals("X")) {
                        lose.setVisible(true);
                        again.setVisible(true);
                        for (int k = 0; k < board.length; k++) {
                            for (int m = 0; m < board[0].length; m++) {
                                board[k][m].setEnabled(false);
                                board[k][m].setText(numBoard[k][m]);
                            }
                        }
                    }
                }
            }
        }
        //checks to see how many buttons are clicked
        int but =0;
        for(int m = 0; m < board.length; m++) {
            for(int n = 0; n < board[0].length;n++) {
                if(!board[m][n].isEnabled()) {
                    but++;
                }
            }
        }
        //checks to see how many buttons are needed to be clicked in order to win
        buttonsClicked= but;
        System.out.println("buttons clicked: " + buttonsClicked);
        if(buttonsClicked == ((board.length*board[0].length)-bombs)) {
            //you win!
            win.setVisible(true);
            again.setVisible(true);
        }
    }
}
