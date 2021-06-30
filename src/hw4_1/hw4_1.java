package hw4_1;

import java.util.Random;
import java.util.Scanner;

public class hw4_1 {
    public static char[][] map;
    public static final int SIZE = 5;
    public static final int DOTS_TO_WIN = 4;

    public static final char DOT_EMPTY = '•';
    public static final char DOT_X = 'X';
    public static final char DOT_O = 'O';
    public static Scanner sc = new Scanner(System.in);
    public static Random rand = new Random();

    public static void main(String[] args) {
        char a = DOT_X;   // Начинает игру
        initMap();
        while (true) {
            if (a == DOT_X) {
                humanTurn();
                a = DOT_O;
            } else {
                aiTurn();
//                aiTurnBlock();
                a = DOT_X;
            }

            printMap(map);

            if (checkWinLines(DOT_X)) {
                System.out.println("Победил человек");
                break;
            }

            if (checkWinLines(DOT_O)) {
                System.out.println("Победил Искуственный Интеллект");
                break;
            }

            if (isMapFull()) {
                System.out.println("Ничья...");
                break;
            }
        }
    }

    /**
     * Инициализация игрового поля
     */
    public static void initMap() {
        map = new char[SIZE][SIZE];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
        printMap(map);
    }

    /**
     * Печать игрового поля
     */
    public static void printMap(char[][] map) {
        for (int i = 0; i < SIZE; i++) {
            System.out.printf("%4d", i + 1);
        }
        System.out.println();

        for (int i = 0; i < SIZE; i++) {
            System.out.print(i + 1 + "  ");
            for (int j = 0; j < SIZE; j++) {
//                System.out.print(i + 1);
                System.out.print(map[i][j] + "   ");
            }
            System.out.println();
        }
    }

    /**
     * ход человка. Данные нужно вводить в консоли
     */
    public static void humanTurn() {
        int x, y;
        do {
            System.out.println("Введите координаты в формате X Y");
            x = sc.nextInt() - 1;
            y = sc.nextInt() - 1;
        } while (!isCellValid(x, y));
        map[y][x] = DOT_X;
    }

    /**
     * Метод проверки валидности введенных координат
     */
    public static boolean isCellValid(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) return false;
        if (map[y][x] == DOT_EMPTY) return true;
        return false;
    }

    /**
     * Проверка на ничью
     */
    public static boolean isMapFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY) return false;
            }
        }
        return true;
    }

    /**
     * Ходит машина. Данные генеряться случайно
     */
    public static void aiTurn() {
        int x, y;
        do {
            x = rand.nextInt(SIZE);
            y = rand.nextInt(SIZE);
        } while (!isCellValid(x, y));
        map[y][x] = DOT_O;
    }

    public static boolean checkWin(char dot) {
        int b = 0;

        // Проверка по вертикали
        for (int i = 0; i < SIZE; i++) {
            b = 0;
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] != dot) {            // проверка до первого значения не равного dot
                    continue;                      // можно использовать break,
                    // если количество ходов для победы будет равно длине массива
                } else {
                    b += 1;
                }
                if (b == DOTS_TO_WIN) return true; // если сумма одиноковых ячеек массива равна длине,
                // то победа
            }
        }

        // Проверка по горизонтали
        for (int i = 0; i < SIZE; i++) {
            b = 0;
            for (int j = 0; j < SIZE; j++) {
                if (map[j][i] != dot) {
                    continue;
                } else {
                    b += 1;
                }
                if (b == DOTS_TO_WIN) return true;
            }
        }

        // Проверка по диагонали, слева на право
        b = 0;
        for (int i = 0; i < SIZE; i++) {
            if (map[i][i] != dot) {
                continue;
            } else {
                b += 1;
            }
            if (b == DOTS_TO_WIN) return true;
        }

        b = 0;
        for (int i = 0; i < SIZE; i++) {
            if (i + 1 < SIZE && map[i][i + 1] == dot) {
                b += 1;
            } else {
                continue;
            }
            if (b == DOTS_TO_WIN) return true;
        }

        b = 0;
        for (int i = 0; i < SIZE; i++) {
            if (i + 1 < SIZE && map[i + 1][i] == dot) {
                b += 1;
            } else {
                continue;
            }
            if (b == DOTS_TO_WIN) return true;
        }

        // Проверка по диагонали, справа на лево
        b = 0;
        for (int i = 0; i < SIZE; i++) {
            int x = SIZE;
            if (map[i][x - 1 - i] != dot) {
                continue;
            } else {
                b += 1;
            }
            if (b == DOTS_TO_WIN) return true;
        }
        // Проверка по диагонали, справа на лево
        b = 0;
        for (int i = 0; i < SIZE; i++) {
            int x = SIZE;
            if (map[x - 1 - i][i] != dot) {
                continue;
            } else {
                b += 1;
            }
            if (b == DOTS_TO_WIN) return true;
        }
        return false;
    }

    static boolean checkLine(int cy, int cx, int vy, int vx, char dot, int dotsToWin) {
        if (cx + vx * (dotsToWin - 1) > SIZE - 1 || cy + vy * (dotsToWin - 1) > SIZE - 1 ||
                cy + vy * (dotsToWin - 1) < 0) {
            return false;
        }

        for (int i = 0; i < dotsToWin; i++) {
            if (map[cy + i * vy][cx + i * vx] != dot) {
                return false;
            }
        }
        return true;
    }

    static boolean checkWinLines(char dot) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (checkLine(i, j, 0, 1, dot, DOTS_TO_WIN) ||
                        checkLine(i, j, 1, 0, dot, DOTS_TO_WIN) ||
                        checkLine(i, j, 1, 1, dot, DOTS_TO_WIN) ||
                        checkLine(i, j, -1, 1, dot, DOTS_TO_WIN)) {
                    return true;
                }
            }
        }
        return false;
    }
}
