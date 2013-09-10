package connect.core;

import playn.core.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.pointer;

/**
 * Created by IntelliJ IDEA.
 * User: Ingemar
 * Date: 2012-06-10
 * Time: 16:11
 * To change this template use File | Settings | File Templates.
 */
public class GameScreen implements Screen {
    public static int BOARD_MAX_WIDTH = 7;
    public static int BOARD_MAX_HEIGHT = 6;
    public static int EMPTY = 0;
    public static int PLAYER_ONE = 1;
    public static int PLAYER_COMPUTER = 2;
    
    private GameEngine gameEngine;
    private int[][] board;
    private Disc disc;
    private List<Disc> allDiscs;
    private DiscType[] discTypes;
    private GroupLayer groupLayer;
    private int player;  // player one or two
    private boolean switchPlayers = false;
    private Random random = new Random();

    public GameScreen(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        init();
    }
    
    @Override
    public void init() {
        // Create and add background image layer
        Image bgImage = assets().getImage("images/bg.png");
        ImageLayer bgLayer = graphics().createImageLayer(bgImage);
        graphics().rootLayer().add(bgLayer);

        // Create a GroupLayer to hold the sprites
        groupLayer = graphics().createGroupLayer();
        graphics().rootLayer().add(groupLayer);

        // Create game board layer after, so discs lay behind
        Image boardImage = assets().getImage("images/board.png");
        ImageLayer boardLayer = graphics().createImageLayer(boardImage);
        boardLayer.setTranslation(110, 105);
        graphics().rootLayer().add(boardLayer);

        // Create game board
        createBoard();

        // Create player disc
        player = PLAYER_ONE;
        discTypes = DiscType.values();
        allDiscs = new ArrayList<Disc>();
        createDisc(0, 0);
    }

    public void createDisc(float x, float y) {
        disc = new Disc(groupLayer, x, y, discTypes[player]);
        allDiscs.add(disc);
        printBoard();
    }

    public void switchPlayer() {
        if (player == PLAYER_ONE) {
            player = PLAYER_COMPUTER;
        } else {
            player = PLAYER_ONE;
        }

        // Wait for current player to make its move
        switchPlayers = false;
    }

    private void createBoard() {
        board = new int[BOARD_MAX_HEIGHT][BOARD_MAX_WIDTH];
    }

    public void printBoard() {
        for (int row=0; row<BOARD_MAX_HEIGHT; row++) {
            for (int col=0; col<BOARD_MAX_WIDTH; col++) {
                System.out.print(board[row][col]);
            }
            System.out.println();
        }
        System.out.println();
    }

    @Override
    public void cleanup() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void pause() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void resume() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void handleMouseUpEvent(Mouse.ButtonEvent buttonEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void handleMouseDownEvent(Mouse.ButtonEvent buttonEvent) {
        if (player == PLAYER_ONE) {
            List<Integer> possibleColumns = getPossibleColumns();
            // Check if this is an empty slot
            if (possibleColumns.contains(disc.getCurrentColumn())) {
                int row = getFirstFreeRow(disc.getCurrentColumn());
                disc.drop(row);
                
                if (checkForVictory(row, disc.getCurrentColumn())) {
                    System.out.println("Player " + player + " wins!");
                    gameEngine.setScreen(new EndScreen(gameEngine));
                }

                switchPlayers = true;
            }
        }
    }

    @Override
    public void handleMouseMovedEvent(Mouse.MotionEvent event) {
        if (player == PLAYER_ONE && !disc.getState().equals(DiscState.MOVING)) {
            disc.followMouse(event);
        }
    }

    @Override
    public void update(float delta) {


        boolean allDiscsDone = true;
        for (Disc disc : allDiscs) {
            disc.update(delta);
            if (disc.getState().equals(DiscState.MOVING)) {
                allDiscsDone = false;
            }
        }

        if (switchPlayers && allDiscsDone) {
            // Swith to other player
            switchPlayer();
            // And create its next disc
            createDisc(0, 0);
        }

        if (player == PLAYER_COMPUTER && allDiscsDone) {
            computerMove();
        }
    }

    private void computerMove() {
        List<Integer> possibleColumns = think();
        int randomColumn;
        // If several columns are equally good blocked player one
        if (possibleColumns.size() > 1) {
            randomColumn = random.nextInt(possibleColumns.size()-1);
        } else {
            randomColumn = 0;
        }

        int bestBlocked = possibleColumns.get(randomColumn);
        System.out.println("size=" + possibleColumns.size() + ", best=" + bestBlocked);
        System.out.println();

        int row = getFirstFreeRow(bestBlocked);
        disc.setCurrentColumn(bestBlocked);
        disc.drop(row);

        if (checkForVictory(row, disc.getCurrentColumn())) {
            System.out.println("Player " + player + " wins!");
            gameEngine.setScreen(new EndScreen(gameEngine));
        }

        switchPlayers = true;
    }

    private List<Integer> think() {
        List<Integer> possibleColumns = getPossibleColumns();
        List<Integer> aiMoves = new ArrayList<Integer>(0);
        int blocked;
        int bestBlocked = 0;

        // Find best position to block
        for (int col=0; col<possibleColumns.size(); col++) {
            // Find first occupied row from bottom to top in current column
            int occupiedRow = BOARD_MAX_HEIGHT;
            for (int row=0; row<BOARD_MAX_HEIGHT; row++) {
                if (board[row][possibleColumns.get(col)] != EMPTY) {
                    occupiedRow = row;
                    break;
                }
            }

            System.out.print(String.format("%s,%s ", possibleColumns.get(col), occupiedRow));

            // Test placing player one tile here
            board[occupiedRow - 1][possibleColumns.get(col)] = 1;
            // Check which direction makes the best possible move
            blocked = countNeighbors(occupiedRow -1, possibleColumns.get(col), 0, 1) + countNeighbors(occupiedRow -1, possibleColumns.get(col), 0, -1);
            // Down
            blocked = Math.max(blocked, countNeighbors(occupiedRow -1, possibleColumns.get(col), 1, 0));
            // Diagonal
            blocked = Math.max(blocked, countNeighbors(occupiedRow -1, possibleColumns.get(col), 1, 1)
                    + countNeighbors(occupiedRow -1, possibleColumns.get(col), 1, -1));
            // Diagonal
            blocked = Math.max(blocked, countNeighbors(occupiedRow -1, possibleColumns.get(col), 1, 1)
                    + countNeighbors(occupiedRow -1, possibleColumns.get(col), -1, -1));

            System.out.println(blocked);
            if (blocked >= bestBlocked) {
                if (blocked > bestBlocked) {
                    bestBlocked = blocked;
                    aiMoves.clear();
                }
                aiMoves.add(possibleColumns.get(col));
            }

            // Revert test data
            board[occupiedRow - 1][possibleColumns.get(col)] = 0;
        }

        return aiMoves;
    }

    @Override
    public void draw() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<Integer> getPossibleColumns() {
        List<Integer> result = new ArrayList<Integer>();
        for (int i=0; i<BOARD_MAX_WIDTH; i++) {
            // Check if top position in each column is empty
            if (board[0][i] == EMPTY) {
                result.add(i);
            }
        }

        return result;
    }

    public int getFirstFreeRow(int column) {
        for (int i=0; i<BOARD_MAX_HEIGHT; i++) {
            // Check from top for first taken row
            if (board[i][column] != EMPTY) {
                // The one above is free
                board[i-1][column] = player;
                return i-1;
            }
        }

        // Set last in column as taken
        board[BOARD_MAX_HEIGHT-1][column] = player;
        // All free! Return last index
        return BOARD_MAX_HEIGHT-1;
    }

    public boolean checkForVictory(int row, int col) {
        // Horizontal. Right and left
        if (countNeighbors(row, col, 0, 1) + countNeighbors(row, col, 0, -1) > 2) {
            return true;
        } else if (countNeighbors(row, col, 1, 0) > 2){
            // Vertical. Only need to check downwards, since up is always empty
            return true;
        } else {
            // Digaonal. Top right to bottom left
            if (countNeighbors(row, col, -1, 1) + countNeighbors(row, col, 1, -1) > 2){
                return true;
            } else {
                // Digaonal. Bottom right to top left
                if (countNeighbors(row, col, 1, 1) + countNeighbors(row, col, -1, -1) > 2){
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

     // Recursive search from given spot in given direction for same value tiles.
    public int countNeighbors(int row, int col, int rowInc, int colInc) {
        if (getValue(row, col) == getValue(row+rowInc, col+colInc)) {
            return 1+countNeighbors(row+rowInc, col+colInc, rowInc, colInc);
        }
        return 0;
    }

    public int getValue(int row, int col) {
        if (row >= 0 && row <= (BOARD_MAX_HEIGHT - 1) && col >= 0 && col <= (BOARD_MAX_WIDTH - 1)) {
            return board[row][col];
        } else {
            return -1;
        }
    }
}
