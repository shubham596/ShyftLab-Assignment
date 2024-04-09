

enum Color {
     BLACK,WHITE
}

enum PieceType {
     KNIGHT, PAWN,KING, QUEEN, ROOK, BISHOP
}



// piece
class Piece {
    private final PieceType type;
    private final Color color;

    public Piece(PieceType type, Color color) {
        this.type = type;
        this.color = color;
    }

    public PieceType getType() {
        return type;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return color + " " + type;
    }
}

// chess board 
class Board {
    private final Piece[][] board;

    public Board() {
        this.board = new Piece[8][8];
    }

    public void initialize() {
       
        placePiece(0, 0, new Piece(PieceType.ROOK, Color.WHITE));
     

        for (int i = 0; i < 8; i++) {
            placePiece(1, i, new Piece(PieceType.PAWN, Color.WHITE));
        }

     
        placePiece(7, 0, new Piece(PieceType.ROOK, Color.BLACK));
       

        
        for (int i = 0; i < 8; i++) {
            placePiece(6, i, new Piece(PieceType.PAWN, Color.BLACK));
        }
    }

    private void placePiece(int row, int col, Piece piece) {
        board[row][col] = piece;
    }

    //printing all the chess 8*8 piece
    public void display() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null) {
                    System.out.print(board[i][j] + " ");
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
    }

    //  moving pieces, checking checkmate.
    public boolean movePiece(int sr, int sc, int er, int ec) {
    Piece piece = board[sr][sc];
    if (piece == null) {
        System.out.println("No piece found at the starting position.");
        return false;
    }

    // valid move or not
    if (!isValidMove(sr, sc, er, ec, piece)) {
        System.out.println("Invalid move.");
        return false;
    }

    board[sr][sc] = null; 
    board[er][ec] = piece;
    return true;
}

private boolean isValidMove(int sr, int sc, int er, int ec, Piece piece) {
  
    if (er < 0 || er >= 8 || ec < 0 || ec >= 8) {
        return false;
    }

   
    if (board[er][ec] != null && board[er][ec].getColor() == piece.getColor()) {
        return false;
    }

    return true;
}


public boolean isCheckmate(Color color) {
        int kr = -1;
        int kingCol = -1;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board[i][j];
                if (piece != null && piece.getType() == PieceType.KING && piece.getColor() == color) {
                    kr = i;
                    kingCol = j;
                    break;
                }
            }
        }

        if (kr == -1 || kingCol == -1) {
            System.out.println("King not found!");
            return false;
        }


        if (isInCheck(kr, kingCol, color)) {
          
            if (!hasLegalMoves(kr, kingCol, color)) {
                return true;
            }
        }

        return false; 
    }
    
    private boolean hasLegalMoves(int kr, int kingCol, Color color) {
    
    int[][] directions = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1},           {0, 1},
            {1, -1}, {1, 0}, {1, 1}
    };

    for (int[] dir : directions) {
        int newRow = kr + dir[0];
        int newCol = kingCol + dir[1];

        if (isValidMove(kr, kingCol, newRow, newCol, new Piece(PieceType.KING, color))) {
           
            Piece temp = board[newRow][newCol];
            board[newRow][newCol] = board[kr][kingCol];
            board[kr][kingCol] = null;

            boolean stillInCheck = isInCheck(newRow, newCol, color);

         
            board[kr][kingCol] = board[newRow][newCol];
            board[newRow][newCol] = temp;

            if (!stillInCheck) {
                return true; 
            }
        }
    }

    return false; 
}


private boolean isInCheck(int kr, int kc, Color color) {
   
    Color opponentColor = (color == Color.WHITE) ? Color.BLACK : Color.WHITE;


    int[][] directions = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1},           {0, 1},
            {1, -1}, {1, 0}, {1, 1}
    };

    for (int[] dir : directions) {
        int newRow = kr + dir[0];
        int newCol = kc + dir[1];

        if (isValidPosition(newRow, newCol)) {
            Piece piece = board[newRow][newCol];
            if (piece != null && piece.getColor() == opponentColor) {
                
                if (canAttackKing(newRow, newCol, kr, kc)) {
                    return true; // King got check
                }
            }
        }
    }

    return false; // King is free
}

private boolean isValidPosition(int row, int col) {
    return row >= 0 && row < 8 && col >= 0 && col < 8;
}

private boolean canAttackKing(int ar, int ac, int kr, int kc) {
    Piece a = board[ar][ac];
    if (a == null) {
        return false; 
    }

    return isValidMove(ar, ac, kr, kc, a);
}


}


public class PlayChess {
    public static void main(String[] args) {
        Board b = new Board();
        b.initialize();
        

        //  moving a piece
         // Move white pawn from (1,0) to (3,0)
        boolean moved = b.movePiece(1, 0, 3, 0);
        if (moved) {
            System.out.println("Piece moved successfully!");
        }

        // Example of checking for checkmate
        boolean s = b.isCheckmate(Color.BLACK);
        if (s) {
            System.out.println("Checkmate! Game over.");
        } else {
            System.out.println("No checkmate yet.");
        }

        b.display();
    }
}
