package IA1jun23;

import robocode.*;
import java.awt.Color;

public class R201807335 extends Robot {
    boolean haciaAdelante = true; // determina la dirección de movimiento del robot
    boolean derecha = true; // determina la dirección de giro del robot

    public void run() {
        setBodyColor(Color.BLACK); // Cambia el color del cuerpo del robot a negro
        setGunColor(Color.BLUE); // Cambia el color del arma del robot a azul
        setRadarColor(Color.BLUE); // Cambia el color del radar del robot a azul

        while (true) {
            // Movimiento del robot
            if (derecha) {
                avanzar(200); // se mueve hacia adelante
                haciaAdelante = true;
                girarDerecha(200); // gira a la derecha
            } else {
                avanzar(400); // se mueve hacia adelante
                haciaAdelante = false;
                girarIzquierda(400); // gira a la izquierda
            }
        }
    }

    // Mueve el robot hacia adelante una cierta distancia
    public void avanzar(int distancia) {
        ahead(distancia);
    }

    // Mueve el robot hacia atrás una cierta distancia
    public void retroceder(int distancia) {
        back(distancia);
    }

    // Gira el robot hacia la derecha un cierto número de grados
    public void girarDerecha(int grados) {
        turnRight(grados);
    }

    // Gira el robot hacia la izquierda un cierto número de grados
    public void girarIzquierda(int grados) {
        turnLeft(grados);
    }

    // Este método se llama cuando el robot detecta otro robot
    public void onScannedRobot(ScannedRobotEvent e) {
        double distanciaEnemigo = e.getDistance(); // la distancia al enemigo
        double anguloGun = getHeading() - getGunHeading() + e.getBearing();
        turnGunRight(anguloGun); // gira el arma hacia el enemigo

        // Fuego basado en la distancia al enemigo
        if (distanciaEnemigo < 100) {
            for (int i = 0; i < 3; i++) {
                fire(1);
            }
        } else if (distanciaEnemigo < 200) {
            for (int i = 0; i < 2; i++) {
                fire(1);
            }
        } else {
            fire(1);
        }

        // Realiza un barrido después de disparar
        barrido();
        // Intenta evadir después de disparar
        esquivarRobot();
    }

    // Este método se llama cuando el robot es golpeado por una bala
    public void onHitByBullet(HitByBulletEvent e) {
        // Cambia la dirección de giro cuando es golpeado
        if (derecha) {
            derecha = false;
        } else {
            derecha = true;
        }
        // Intenta evadir después de ser golpeado
        esquivarRobot();
    }

    // Este método se llama cuando el robot choca contra una pared
    public void onHitWall(HitWallEvent e) {
        if (haciaAdelante) {
            retroceder(400); // se mueve hacia atrás
            if (derecha) {
                girarIzquierda(-100); // gira a la izquierda
                derecha = false;
            } else {
                girarDerecha(-100); // gira a la derecha
                derecha = true;
                haciaAdelante = false;
            }
        } else {
            avanzar(400); // se mueve hacia adelante
            haciaAdelante = true;
            if (derecha) {
                girarIzquierda(100); // gira a la izquierda
                derecha = false;
            } else {
                girarDerecha(100); // gira a la derecha
                derecha = true;
            }
            haciaAdelante = true;
        }
    }

    // Este método se usa para evadir
    public void esquivarRobot() {
        if (haciaAdelante) {
            retroceder(100); // se mueve hacia atrás
            haciaAdelante = false;
        } else {
            avanzar(100); // se mueve hacia adelante
            haciaAdelante = true;
        }
    }

    // Este método se usa para realizar un barrido
    public void barrido() {
        for (int i = 0; i < 3; i++) {
            girarDerecha(30); // gira a la derecha
            avanzar(20); // se mueve hacia adelante
            girarIzquierda(30); // gira a la izquierda
            retroceder(20); // se mueve hacia atrás
        }
    }
}
