package com.ld43.game.math;

public class MathUtils {
    public static void main(String[] args) {
        double[] foo = movementVectors(1,3*Math.PI / 2);
        System.out.println(foo[0]);
        System.out.println(foo[1]);
    }

    public static double angleBetweenPoints(float fromX, float fromY, float toX, float toY) {
        if(toX >= fromX) {
            if(toY > fromY) { return Math.atan((toX - fromX) / (toY - fromY)); // Up left
            } else { return (- Math.atan((toY - fromY) / (toX - fromX)) + Math.PI / 2); } //Down right
        } else {
            if(toY >= fromY) { return Math.atan((fromY - toY) / (toX - fromX)) + 3*Math.PI / 2; //Up right
            } else { return Math.atan((toX - fromX) / (toY - fromY)) + Math.PI; } //Down left
        }
    }

    public static double[] movementVectors(float movement, double angle) {
        double[] movements = {Math.sin(angle) * movement, Math.cos(angle) * movement};
        return movements;
    }
}
