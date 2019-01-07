
package parquing;

import static java.lang.Thread.sleep;

public class Principal {

    public static void main(String[] args) {
        Parking parking = new Parking(10);
        for (int i = 1; i <= 20; i++) {
            Cotxe c = new Cotxe("Cotxe matrícula " + i, parking);
        }
    }
}

class Cotxe extends Thread {

    private Parking parking;

    public Cotxe(String matricula, Parking p) {
        super(matricula);
        this.parking = p;
        start();
    }

    public void run() {
        try {
            sleep((int) (Math.random() * 10000)); // Parar abans d'entrar al pàrquing
        } catch (InterruptedException e) {
        }

        parking.entra();
        System.out.println(getName() + ": entra al pàrquing");

        try {
            sleep((int) (Math.random() * 20000)); // Simular estada esperant un temps aleatori
        } catch (InterruptedException e) {
        }

        parking.surt();
        System.out.println(getName() + ": surt de pàrquing");
    }
}

class Parking {

    private int places;

    public Parking(int places) {
        if (places < 0) {
            places = 0;
        }

        this.places = places;
    }

    public synchronized void entra() { // cotxe entra al pàrquing
        while (places == 0) {
            try {
                System.out.println("Esperant, pàrquing ple.");
                wait();
            } catch (InterruptedException e) {
            }
        }
        places--;
    }

    public synchronized void surt() { // el coche deixa el pàrquing
        System.out.println("Plaça alliberada.");
        places++;
        notify();
    }
}
