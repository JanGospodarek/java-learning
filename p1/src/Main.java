import java.util.Scanner;

enum Player {
    X,
    O
}

class Board {
    private final char[][] board = new char[3][3];

    Board() {
        initBoard();
        renderBoard();
    }

    void initBoard() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = '-';
    }

    void renderBoard() {
        System.out.println("  1 2 3");
        for (int i = 0; i < 3; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < 3; j++)
                System.out.print(board[i][j] + " ");
            System.out.println();
        }

    }

    public boolean placeMove(int row, int col, Player player) {
        if (board[row][col] == '-') {
            board[row][col] = player.toString().charAt(0);
            renderBoard();
            return true;
        } else {
            System.out.println("Invalid move!");
            return false;
        }
    }

    public boolean checkIsWin() {
        for (int i = 0; i < 3; i++) {
            boolean isSameR = true;
            boolean isSameC = true;
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-' || board[i][j] != board[i][0])
                    isSameR = false;
                if (board[j][i] == '-' || board[j][i] != board[0][i])
                    isSameC = false;
            }
            if (isSameR || isSameC)
                return true;
        }
        boolean isSameD1 = true;
        boolean isSameD2 = true;
        for (int i = 0; i < 3; i++) {
            if (board[i][i] == '-' || board[i][i] != board[0][0])
                isSameD1 = false;
            if (board[i][2 - i] == '-' || board[i][2 - i] != board[0][2])
                isSameD2 = false;
        }
        return isSameD1 || isSameD2;
    }

    public char[][] getBoard() {
        return board.clone();
    }
}

public class Main {

    static boolean isRunning = true;
    static Player currentPlayer = Player.X;

    public static void main(String[] args) {
        Board board = new Board();
        while (isRunning) {
            int[] cords = currentPlayer == Player.X ? readPlayerInput() : readComputerInput(board.getBoard());
            boolean isPlaced = board.placeMove(cords[0], cords[1], currentPlayer);
            if (isPlaced) {
                boolean isWinner = board.checkIsWin();
                if (isWinner) {
                    System.out.println(currentPlayer + " is the winner!");
                    isRunning = false;
                }
                currentPlayer = currentPlayer == Player.X ? Player.O : Player.X;
            }
        }
    }


    static int[] readPlayerInput() {
        boolean inputValid = false;
        int row = 0;
        int col = 0;
        while (!inputValid) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the row and column number to place x: ");
            row = scanner.nextInt();
            col = scanner.nextInt();
            if (row < 1 || row > 3 || col < 1 || col > 3)
                System.out.println("Invalid input! Please enter a number between 1 and 3");
            else
                inputValid = true;
        }
        return new int[]{row - 1, col - 1};

    }

    static int[] readComputerInput(char[][] board) {
        int[] cords = getRandomCords();
        while (board[cords[0]][cords[1]] != '-')
            cords = getRandomCords();
        return cords;
    }

    static int[] getRandomCords() {
        int row = (int) (Math.random() * 3);
        int col = (int) (Math.random() * 3);
        return new int[]{Math.max(row, 0), Math.max(col, 0)};
    }
}