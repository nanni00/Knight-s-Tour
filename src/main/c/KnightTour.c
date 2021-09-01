#include <jni.h>
#include "kthandler_KnightTourHandler.h"

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

#define DEBUG false


int xMove[8] = { 2, 2, 1, -1, -2, -2, -1, 1};
int yMove[8] = {-1, 1, 2, 2, 1, -1, -2, -2 };


/**
 * O(n) complexity and using few extra spaces, maybe this algorithm could be implemented
 * with better parameters.
 * @param t
 */
void rotateMoves(int t) {
    int newX[8] = { 0, };
    int newY[8] = { 0, };

    for (int i = 0; i < 8; ++i) {
        if (i + t > 7) {
            newX[t - 1] = xMove[i];
            newY[t - 1] = yMove[i];
        } else {
            newX[i + t] = xMove[i];
            newY[i + t] = yMove[i];
        }
    }

    for (int i = 0; i < 8; ++i) {
        xMove[i] = newX[i];
        yMove[i] = newY[i];
    }
}


void printBoard(int m, int n, int step, int *board) {
    if (step != m * n) {
        printf("\n\nStep %d\n\n", step);
    } else {
        printf("\n\nFinal Step\n\n");
    }

    for (int r = 0; r < m; ++r) {
        for (int c = 0; c < n; ++c) {
            printf("%d\t", board[r * n + c]);
        }
        printf("\n");
    }
    printf("\n");
}

bool conditionZero(int m, int n) {
    return m <= n;
}

bool conditionOne(int m, int n) {
    return (m % 2 != 0) && (n % 2) != 0;
}

bool conditionTwo(int m) {
    return (m == 1) || (m == 2) || (m == 4);
}

bool conditionThree(int m, int n) {
    return (m == 3) && ((n == 4) || (n == 6) || (n == 8));
}

bool isInBoard(int m, int n, int r, int c) {
    return ((r >= 0 && r < m) && (c >= 0 && c < n));
}

/**
 * Check if the position has been visited yet.
 * @param n the number of columns
 * @param r the position's row
 * @param c the position's column
 * @param board the chessboard
 * @return true if (r, c) is a new position, false otherwise
 */
bool notVisited(int n, int r, int c, const int *board) {
    return board[r * n + c] == 0;
}


/**
 * Count onward moves from the param position
 * @param m
 * @param n
 * @param r
 * @param c
 * @param board
 * @return
 */
int countOnwardMove(int m, int n, int r, int c, int *board) {
    int tot = 0;

    for (int i = 0; i < 8; ++i) {
        if (isInBoard(m, n,r + yMove[i], c + xMove[i])
        && notVisited(n,r + yMove[i], c + xMove[i], board)) {

            ++tot;
        }
    }

    return tot;
}


/**
 * Look for a new position on the board. If found a right one update currentR/C and return true,
 * false otherwise. If this is the last move and the tour is finished, return true too.
 * @param m
 * @param n
 * @param step
 * @param currentR
 * @param currentC
 * @param board
 * @return
 */
bool nextMove(int m, int n, int step, int *currentR, int *currentC, int *board) {

    bool canMove = false;

    int bestOnwardMove = 9;
    int iOnwardMove;
    int nextR, nextC;

    for (int i = 0; i < 8; ++i) {
        if (isInBoard(m, n,*currentR + yMove[i], *currentC + xMove[i])
            && notVisited(n,*currentR + yMove[i], *currentC + xMove[i], board)) {

            canMove = true;

            iOnwardMove = countOnwardMove(m, n, *currentR + yMove[i], *currentC + xMove[i], board);

            if (iOnwardMove < bestOnwardMove) {
                bestOnwardMove = iOnwardMove;
                nextR = *currentR + yMove[i];
                nextC = *currentC + xMove[i];
            }
        }
    }

    if (canMove) {
        *currentR = nextR;
        *currentC = nextC;
    }

    if (!canMove && step == m * n - 1) {
        // end
        return true;
    }

    return canMove;
}

bool WarnsdorffRule(int m, int n, int *board, int *tiePoint, int startRow, int startColumn) {
    int r = startRow, c = startColumn;

    int step = 1;
    int freeSquares = m * n;

    while (step < freeSquares) {
        if (nextMove(m, n, step, &r, &c, board)) {
            if (DEBUG)
                printBoard(m, n, step, board);

            ++step;
            board[r * n + c] = step;

        } else {
            // handle wrong tour
            return false;
        }
    }

    return true;
}


void copyArrayJava(int m, int n, const int *board, jintArray answer_array, JNIEnv *env) {
    (*env)->SetIntArrayRegion(env, answer_array, 0, m * n, (const jint *) board);
}

void copyArrayC(int m, int n, const int *board, int *path) {
    for (int i = 0; i < m * n; ++i) {
        path[i] = board[i];
    }
}

bool checkInput(int m, int n, int startRow, int startColumn, void *answer_array) {
    /* check input values */
    if (!isInBoard(m, n, startRow, startColumn)) {
        printf("ERROR. Invalid input: (%d, %d) is not on board.\n", startRow, startColumn);
        return false;
    }

    if (answer_array == NULL) {
        printf("ERROR. Invalid input: answer_array initialized 'NULL'\n");
        return false;
    }

    /* check condition 0: m <= n */
    if (conditionZero(m, n) && DEBUG) {
        printf("Condition 0 satisfied: m <= n\n\n");
    }

    /* check condition 1: m and n odd both */
    if (conditionOne(m, n)) {
        printf("Condition 1 satisfied: m and n both odd.\n");
        printf("Is not guaranteed a solution in that case\n\n");
    }

    /* check condition 2: */
    if (conditionTwo(m)) {
        printf("Condition 2 satisfied: m = 1, 2 or 4.\n");
        printf("Is not guaranteed a solution in that case\n\n");
    }

    /* check condition 3: */
    if (conditionThree(m, n)) {
        printf("Condition 3 satisfied: m = 3 and n = 4, 6 or 8.\n");
        printf("Is not guaranteed a solution in that case\n\n");
    }

    return true;
}

bool KnightTour(int m, int n, int startRow, int startColumn, int *board, int *tiePoint) {

    /* set the initial position as 1 */
    board[startRow * n + startColumn] = 1;

     int try = 0;
     if (WarnsdorffRule(m, n, board, tiePoint, startRow, startColumn)) {
        return true;
     } else {
        for (int i = 0; i < 7; ++i) {
            printf("Try %d failed. Retry...\n", i + 1);
            rotateMoves(try + 1);

            for (int j = 0; j < m * n; ++j) board[j] = 0;

            board[startRow * n + startColumn] = 1;

            if (WarnsdorffRule(m, n, board, tiePoint, startRow, startColumn)) {
                printf("Try %d succeeded.\n", try + 2);
                return true;
            }
        }
     }

    return false;
}

/**
  * Specific Java caller for the program.
  */
JNIEXPORT jboolean JNICALL Java_kthandler_KnightTourHandler_KnightTour_1JavaCaller (JNIEnv *env, jobject jobj, jint m, jint n, jint startRow, jint startColumn, jintArray answer_array) {
    //bool KnightTour(int m, int n, int startRow, int startColumn, int *answer_array) {
    /* board legend: 0 not occupied yet, a number greater than 0 means the knight step on the square */
    int *board;
    int *tiePoint;
    bool ret;

    if (!checkInput(m, n, startRow, startColumn, answer_array)) {
        return false;
    }

    /* create the board and tiePoint arrays */
    board = (int *) calloc(m * n, sizeof(int));
    tiePoint = NULL; // for now

    if ((ret = KnightTour(m, n, startRow, startColumn, board, tiePoint))) {
        copyArrayJava(m, n, board, answer_array, env);
    }

    if (board != NULL)
        free(board);

    if (tiePoint != NULL)
        free(tiePoint);

    return ret;
}

bool KnightTour_Ccaller(int m, int n, int startRow, int startColumn, int *path) {
    if (!checkInput(m, n, startRow, startColumn, path)) {
        return false;
    }

    bool ret;

    /* create the board and tiePoint arrays */
    int *board = (int *) calloc(m * n, sizeof(int));
    int *tiePoint = NULL; // for now

    if ((ret = KnightTour(m, n, startRow, startColumn, board, tiePoint))) {
        copyArrayC(m, n, board, path);
    }

    if (board != NULL)
        free(board);

    if (tiePoint != NULL)
        free(tiePoint);

    return ret;


}
