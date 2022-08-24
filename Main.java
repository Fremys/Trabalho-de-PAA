import java.util.*;
import java.util.Scanner;

class Figura{
    //Ler dimensões da figura
    private int x1;
    private int x2;
    private int x3;
    private int altura;

    public Figura(int x1, int x2, int x3){
        
        //Inicializar
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
    }

    
    public Figura(){

        //Inicializar
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

    public int getValueBaseMaior(){

        return (this.x1 <= (this.x2 + this.x3) ? (this.x2 + this.x3) : this.x1);
    }

    public int getValueBaseMenor(){

        return (this.x1 >= (this.x2 + this.x3) ? (this.x2 + this.x3) : this.x1);
    }

    public int getArea(){
        int area = -1;

        area = (getValueBaseMaior() + getValueBaseMenor() * this.altura)/2;

        return area;
    }

    public String getConector(Figura figProx){
        String faceConector = "";

        if(this.getX2() < this.getX1()){

            if(((this.getX1() - this.getX2()) > (figProx.getX3() * -1))){
                faceConector = "up";
            }else{
                faceConector = "down";
            }
        }else{
            if(this.getX2() > this.getX1()){
                if(((this.getX2() - this.getX1()) > figProx.getX3())){
                    faceConector = "down";
                }else{
                    faceConector = "up";
                }
            }

        }
        return faceConector;
    }


}


public class Main{

    public static int receberFigura(int x, int y, int z){
        return(0);
    }

    public static void main(String[] args){
        //Definir leitor de dados
        Scanner read = new Scanner(System.in);
        int qtdFiguras = 2;
        Figura fig [] = new Figura[qtdFiguras];
        
        //Fazer leitura dos dados da figura
        for(int i = 0; i < qtdFiguras; i++ ){

            System.out.println("Digitar:" );

            System.out.println("x1:" );
            int x1 = read.nextInt();

            System.out.println("x2:" );
            int x2 = read.nextInt();

            System.out.println("x3:" );
            int x3 = read.nextInt();

            System.out.print("\n" );

            fig[i] = new Figura(x1, x2, x3);

        }

        System.out.println(fig[0].getConector(fig[1]));


    }
}
