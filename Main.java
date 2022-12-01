import java.util.*;
import java.util.Scanner;

class DesperdicioFig {

    // número da permutação atual
    private static int cont = 0;

    // armazena a permutação corrente
    private static Figura[] p;

    //Armazena o vetor de menor desperdício
    Figura[] result;

    DesperdicioFig() {
    }

    //Método que verifica a igualdade de figuras
    private boolean isEqual(Figura fig1, Figura fig2) {
        return (fig1.getX1() == fig2.getX1() && fig1.getX2() == fig2.getX2() && fig1.getX3() == fig2.getX3());
    }

    //Método iterativo que calcula o desperdício de cada vetor de figuras
    public float desperdicio(Figura[] fig) {

        // Definir dados
        float result = 0;
        int altura = 100;
        float area = 0;
        int somaAreasFig = 0;
        String finish = "";

        // Chamar Iteração
        for (int i = 0; i < (fig.length - 1); i++) {
            // Pegar area do trapézio
            somaAreasFig += fig[i].getArea();

            // Descobrir x3 da próxima figura
            float x3Future = fig[i + 1].getX3();

            // somando sobra de x3 da figura analisada
            if (fig[i].getX3() < 0)
                result += (fig[i].getX3() * -1);

            // Descobrir se a posição da sobra
            if (fig[i].getX1() > fig[i].getX2()) {
                // Verificar se sobra da figura analisada é maior que a sobra da posterior
                if ((fig[i].getX1() - fig[i].getX2()) >= (x3Future * -1)) {
                    finish = "up";
                    result += fig[i].getX1();
                } else {
                    finish = "down";
                    result += fig[i].getX2();
                }
            } else {
                // Verificar se a sobra da figura analisada é maior que o encaixe da posterior
                if ((fig[i].getX2() - fig[i].getX1()) >= (x3Future)) {
                    finish = "down";
                    result += fig[i].getX2();
                } else {
                    finish = "up";
                    result += fig[i].getX1();
                }
            }
        }

        // Tratando da última figura do vetor

        String sobra = fig[(fig.length - 1)].getX1() >= fig[(fig.length - 1)].getX2() ? "up" : "down";

        if(finish == "top"){
            result += fig[fig.length - 1].getX1();
            //somar com sobra
            result += sobra == "down" ? fig[fig.length - 1].getX2() - fig[fig.length - 1].getX1() : 0;
        }else{
            if(fig[fig.length - 1].getX3() < 0){
                result += (fig[fig.length - 1].getX3() * -1);
            }
            result += fig[fig.length - 1].getX2();

            //somar com a sobra
            result += sobra == "up" ? fig[fig.length - 1].getX1() - fig[fig.length - 1].getX2() : 0;
        }

        // Pegar area do trapézio
        somaAreasFig += fig[(fig.length - 1)].getArea();

        area = result * altura;

        return (area - somaAreasFig);
    }

    //Método principal: recebe o vetor cujos elementos que serão permutados
    public Figura[] menorDesperdicio(Figura[] vet) {
        p = new Figura[vet.length];
        result = new Figura[vet.length];
        menorDesperdicio(vet, 0, Float.MAX_VALUE);
        return result;
    }

    //Método recursivo que implementa as permutações para descobrir o menor desperdício
    private void menorDesperdicio(Figura[] vet, int n, float desperdicio) {
        float desperdicioCourrent = desperdicio;

        if (n == vet.length) {
            cont++;
            desperdicioCourrent = desperdicio(p);
            if(desperdicioCourrent < desperdicio) result = p;

        } else {

            for (int i = 0; i < vet.length; i++) {

                boolean achou = false;

                for (int j = 0; j < n; j++) {
                    if (isEqual(p[j], vet[i]))
                        achou = true;
                }

                if (!achou) {
                    p[n] = vet[i];
                    menorDesperdicio(vet, n + 1, desperdicioCourrent);
                }

            }

        }

    }


}

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

    public int[] getValueBases() {
        //Definir dados
        int result [] = new int[2];
        int x3Modified = this.x3 < 0 ? this.x3 * -1 : this.x3;

        if(this.x2 > 0 && this.x3 < 0){
            if(this.x1 >= (this.x2 + x3Modified)){
                result[0] = this.x1;
                result[1] = this.x2 + x3Modified;
            }else{
                result[0] = this.x2 + x3Modified;
                result[1] = this.x1;
            }
        }else{
            if(this.x3 > 0){
                if(this.x1 >= this.x2){
                    result[0] = this.x1;
                    result[1] = this.x2;
                }else{
                    result[0] = this.x2;
                    result[1] = this.x1;
                }
            }
            if(this.x2 < 0){
                if(this.x1 >= x3Modified){
                    result[0] = this.x1;
                    result[1] = x3Modified;
                }else{
                    result[0] = x3Modified;
                    result[1] = this.x1;
                }
            }
        }


        return result;
    }

    public int getValueBaseMenor() {

        return (this.x1 >= (this.x2 + this.x3) ? (this.x2 + this.x3) : this.x1);
    }

    public int getArea() {
        int area = -1;
        int basesValue [] = getValueBases();

        area = ((basesValue[0] + basesValue[1]) * this.altura) / 2;

        return area;
    }

    /*
     *
     * public String getConector(Figura figProx) {
     * String faceConector = "";
     *
     * if (this.getX2() < this.getX1()) {
     *
     * if (((this.getX1() - this.getX2()) > (figProx.getX3() * -1))) {
     * faceConector = "up";
     * } else {
     * if (((this.getX1() - this.getX2()) == (figProx.getX3() * -1))) {
     * faceConector = "perfect";
     * } else {
     *
     * faceConector = "down";
     * }
     * }
     * } else {
     * if (this.getX2() > this.getX1()) {
     * if (((this.getX2() - this.getX1()) > figProx.getX3())) {
     * faceConector = "down";
     * } else {
     * if (((this.getX1() - this.getX2()) == (figProx.getX3() * -1))) {
     * faceConector = "perfect";
     * } else {
     *
     * faceConector = "up";
     * }
     * }
     * }
     *
     * }
     * return faceConector;
     * }
     */
}


public class Main {

    public static int receberFigura(int x, int y, int z) {
        return (0);
    }

    public static boolean iEsqual(Figura fig1, Figura fig2) {
        return (fig1.getX1() == fig2.getX1() && fig1.getX2() == fig2.getX2() && fig1.getX3() == fig2.getX3());
    }

    public static void main(String[] args) {
        // Definir leitor de dados
        Scanner read = new Scanner(System.in);
        int qtdFiguras = 2;
        Figura fig[] = new Figura[qtdFiguras];
        Figura menorDesperdicio[];
        DesperdicioFig des = new DesperdicioFig();

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

        // menorDesperdicio = des.menorDesperdicio(fig);

        // System.out.println("menor desperdicio: " + des.desperdicio(fig));
        System.out.println("menor desperdicios: " + (fig[0].getArea()));
    }
}
