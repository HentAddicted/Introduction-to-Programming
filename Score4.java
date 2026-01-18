package Score4;


import java.util.Scanner;

public class Score4 {

private static final String RED = "\u001B[31m";
private static final String BLUE = "\u001B[34m";
private static final String GREEN = "\u001B[32m";
private static final String YELLOW = "\u001B[33m";
private static final String RESET = "\u001B[0m";

private char[][] board;

private int rows;
private int cols;

private int numPlayers;
private String[] playerNames;
private char[] symbols = {'X', 'O', '#', '$'};
private String[] colors = {RED, BLUE, GREEN, YELLOW};

private Scanner scanner = new Scanner(System.in);

public static void main(String[] args) {
Score4 game = new Score4();
game.initializeGame();
game.playGame();
}

private void initializeGame() {
System.out.println("Welcome to Score4!");

while (true) {
System.out.print("Enter number of players (2–4): ");
try {
numPlayers = Integer.parseInt(scanner.nextLine());
if (numPlayers < 2 || numPlayers > 4) {
System.out.println("Must be 2, 3, or 4 players.");
continue;
}
break;
} catch (Exception e) {
System.out.println("Invalid number.");
}
}

playerNames = new String[numPlayers];
for (int i = 0; i < numPlayers; i++) {
System.out.printf("Enter name for Player %d: ", i + 1);
String name = scanner.nextLine().trim();
if (name.isEmpty()) name = "Player " + (i + 1);
playerNames[i] = name;
}

if (numPlayers == 2) {
rows = 6; cols = 7;
} else if (numPlayers == 3) {
rows = 7; cols = 9;
} else {
rows = 7; cols = 12;
}

board = new char[rows][cols];
for (int r = 0; r < rows; r++)
for (int c = 0; c < cols; c++)
board[r][c] = '.';

System.out.println("\nPlayers:");
for (int i = 0; i < numPlayers; i++) {
System.out.println(colors[i] + playerNames[i] + RESET +
" → " + colors[i] + symbols[i] + RESET);
}

System.out.printf("\nBoard size: %d x %d\n\n", rows, cols);
}

private void playGame() {
int current = 0;
printBoard();

while (true) {

if (isBoardFull()) {
System.out.println("\nNo winner. The game ended without a winner.");
break;
}

String name = playerNames[current];
char symbol = symbols[current];
String color = colors[current];

System.out.printf("%s%s (%c)%s choose column (1..%d): ",
color, name, symbol, RESET, cols);

int col;
try {
col = Integer.parseInt(scanner.nextLine()) - 1;
} catch (Exception e) {
System.out.println("Invalid input.");
continue;
}

if (col < 0 || col >= cols) {
System.out.println("Column out of range.");
continue;
}

int row = dropPiece(col, symbol);
if (row == -1) {
System.out.println("Column is full.");
continue;
}

printBoard();

if (isWinningMove(row, col, symbol)) {
System.out.println(color + "\n🎉 " + name + " WINS THE GAME! 🎉" + RESET);
break;
}

if (isBoardFull()) {
System.out.println("\nNo winner. The game ended without a winner.");
break;
}

current = (current + 1) % numPlayers;
}

System.out.println("\nGame finished!");
}

private int dropPiece(int col, char sym) {
for (int r = rows - 1; r >= 0; r--) {
if (board[r][col] == '.') {
board[r][col] = sym;
return r;
}
}
return -1;
}

private boolean isBoardFull() {
for (int c = 0; c < cols; c++)
if (board[0][c] == '.') return false;
return true;
}

private boolean isWinningMove(int row, int col, char sym) {
return (count(row, col, 0, 1, sym) + count(row, col, 0, -1, sym) - 1 >= 4) ||
(count(row, col, 1, 0, sym) + count(row, col, -1, 0, sym) - 1 >= 4) ||
(count(row, col, 1, 1, sym) + count(row, col, -1, -1, sym) - 1 >= 4) ||
(count(row, col, 1, -1, sym) + count(row, col, -1, 1, sym) - 1 >= 4);
}

private int count(int r, int c, int dr, int dc, char sym) {
int cnt = 0;
while (r >= 0 && r < rows && c >= 0 && c < cols && board[r][c] == sym) {
cnt++;
r += dr;
c += dc;
}
return cnt;
}

private void printBoard() {
System.out.print(" ");
for (int c = 0; c < cols; c++) System.out.printf("%2d ", c + 1);
System.out.println();

for (int r = 0; r < rows; r++) {
System.out.printf("%2d ", r + 1);
for (int c = 0; c < cols; c++) {
char cell = board[r][c];
String out = " " + cell + " ";

switch (cell) {
case 'X': out = RED + out + RESET; break;
case 'O': out = BLUE + out + RESET; break;
case '#': out = GREEN + out + RESET; break;
case '$': out = YELLOW + out + RESET; break;
}
System.out.print(out);
}
System.out.println();
}
System.out.println();
}
}

