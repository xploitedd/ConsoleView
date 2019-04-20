package isel.leic.pg.console.test;

import isel.leic.pg.Console;

import java.awt.*;
import java.util.Random;

public class MiscTest {

    public static void main(String[] args) {
        int i = 0;
        Console.open("Demo Stars",20,40);
        //Console.clear();
        Random rnd = new Random();
        Console.println("Hello World");
        Console.println('a');
        Console.print(new Point(50, 0), new Point(305, 200), 4);
        //Console.startMusic("stars");
        /*while (i++ != Integer.MAX_VALUE) {
            String txt = "Iterations: " + i;
            Console.cursor(19, 39 - txt.length());
            Console.color(BLACK, WHITE);
            Console.print(txt);
            Console.cursor(rnd.nextInt(19),rnd.nextInt(40));
            Console.setForeground(rnd.nextInt(Console.MAX_COLORS));
            if (rnd.nextInt(20)==0) {
                Console.print('*');
                Console.sleep(100);
            }
            else Console.print(' ');
        }*/
        Console.stopMusic();
    }
}
