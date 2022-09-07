import java.util.*;
import java.util.Scanner;

class Figura {
    // Ler dimensões da figura
    private int x1;
    private int x2;
    private int x3;
    private int altura = 100;

    public Figura(int x1, int x2, int x3) {

        // Inicializar
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
    }

    public Figura() {

        // Inicializar
        this.x1 = 0;
        this.x2 = 0;
        this.x3 = 0;
    }

    /**
     * @return the x1
     */
    public int getX1() {
        return x1;
    }

    /**
     * @return the x2
     */
    public int getX2() {
        return x2;
    }

    /**
     * @return the x3
     */
    public int getX3() {
        return x3;
    }

    /**
     * @param x1 the x1 to set
     */
    public void setX1(int x1) {
        this.x1 = x1;
    }

    /**
     * @param x2 the x2 to set
     */
    public void setX2(int x2) {
        this.x2 = x2;
    }

    /**
     * @param x3 the x3 to set
     */
    public void setX3(int x3) {
        this.x3 = x3;
    }

    public int getValueBaseMaior() {

        return (this.x1 <= (this.x2 + this.x3) ? (this.x2 + this.x3) : this.x1);
    }

    public int getValueBaseMenor() {

        return (this.x1 >= (this.x2 + this.x3) ? (this.x2 + this.x3) : this.x1);
    }

    public int getArea() {
        int area = -1;

        area = ((getValueBaseMaior() + getValueBaseMenor()) * this.altura) / 2;

        return area;
    }

    /*
     * 
    public String getConector(Figura figProx) {
        String faceConector = "";

        if (this.getX2() < this.getX1()) {

            if (((this.getX1() - this.getX2()) > (figProx.getX3() * -1))) {
                faceConector = "up";
            } else {
                if (((this.getX1() - this.getX2()) == (figProx.getX3() * -1))) {
                    faceConector = "perfect";
                } else {

                    faceConector = "down";
                }
            }
        } else {
            if (this.getX2() > this.getX1()) {
                if (((this.getX2() - this.getX1()) > figProx.getX3())) {
                    faceConector = "down";
                } else {
                    if (((this.getX1() - this.getX2()) == (figProx.getX3() * -1))) {
                        faceConector = "perfect";
                    } else {

                        faceConector = "up";
                    }
                }
            }

        }
        return faceConector;
    }
    */
}

public class Main {

    public static int receberFigura(int x, int y, int z) {
        return (0);
    }

    public static float desperdicio(Figura[] fig) {

        // Definir dados
        float result = 0;
        int altura = 100;
        float area = 0;
        int somaAreasFig = 0;

        // Chamar Iteração
        for (int i = 0; i < (fig.length - 1); i++) {
            //Pegar area do trapézio
            somaAreasFig += fig[i].getArea();

            // Descobrir x3 da próxima figura
            float x3Future = fig[i + 1].getX3();

            //somando  sobra de x3 da figura analisada
            if (fig[i].getX3() < 0)
                result += (fig[i].getX3() * -1);

            // Descobrir se a posição da sobra
            if (fig[i].getX1() > fig[i].getX2()) {
                //Verificar se sobra da figura analisada é maior que a sobra da posterior
                if ((fig[i].getX1() - fig[i].getX2()) >= (x3Future * -1)) {
                    result += fig[i].getX1();
                } else {
                    result += fig[i].getX2();
                }
            } else {
                //Verificar se a sobra da figura analisada é maior que o encaixe da posterior
                if ((fig[i].getX2() - fig[i].getX1()) >= (x3Future)) {
                    result += fig[i].getX2();
                } else {
                    result += fig[i].getX1();
                }
            }
        }

        // Tratando da última figura do vetor
        result += fig[(fig.length - 1)].getX1() >= fig[(fig.length - 1)].getX2() ? fig[(fig.length - 1)].getX1() : fig[(fig.length - 1)].getX2();

        //Pegar area do trapézio
        somaAreasFig += fig[(fig.length - 1)].getArea();

        area = result * altura;

        return (area - somaAreasFig);
    }

    public static void main(String[] args) {
        // Definir leitor de dados
        Scanner read = new Scanner(System.in);
        int qtdFiguras = 2;
        Figura fig[] = new Figura[qtdFiguras];

        // Fazer leitura dos dados da figura
        for (int i = 0; i < qtdFiguras; i++) {

            System.out.println("Digitar:");

            System.out.println("x1:");
            int x1 = read.nextInt();

            System.out.println("x2:");
            int x2 = read.nextInt();

            System.out.println("x3:");
            int x3 = read.nextInt();

            System.out.print("\n");

            fig[i] = new Figura(x1, x2, x3);

        }

        System.out.println(desperdicio(fig));

    }
}
