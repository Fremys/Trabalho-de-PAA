import java.util.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.*;

class DesperdicioFig {

	// ===== VARIÁVEIS GLOBAIS DO Branch And Bound =====
	private float menorGlobal = Float.MAX_VALUE;
	private ArrayList<Figura> resultBd;

	// ===== VARIÁVEIS GLOBAIS DO FORÇA BRUTA =====

	// número da permutação atual
	private static int cont = 0;

	// armazena a permutação corrente
	private ArrayList<Figura> p;

	// Armazena o vetor de menor desperdício
	private ArrayList<Figura> result;
	float menorDesperdicio = Float.MAX_VALUE;

	DesperdicioFig() {
	}

	// Método que verifica a igualdade de figuras
	private boolean isEqual(Figura fig1, Figura fig2) {
		return (fig1.getX1() == fig2.getX1() && fig1.getX2() == fig2.getX2() && fig1.getX3() == fig2.getX3());
	}

	// Método iterativo que calcula o desperdício de cada vetor de figuras
	public float desperdicio(ArrayList<Figura> fig) {

		// Definir dados
		float result = 0;
		float altura = 100;
		float area = 0;
		int somaAreasFig = 0;
		String finish = "";

		// Chamar Iteração
		for (int i = 0; i < (fig.size() - 1); i++) {
			// Pegar area do trapézio
			somaAreasFig += fig.get(i).getArea();

			// Descobrir x3 da próxima figura
			float x3Future = fig.get(i + 1).getX3();

			// somando sobra de x3 da figura analisada
			if (fig.get(i).getX3() < 0)
				result += (fig.get(i).getX3() * -1);

			if (fig.get(i).getX3() >= 0) {
				// Descobrir se a posição da sobra
				if (fig.get(i).getX1() >= (fig.get(i).getX2())) {
					// Verificar se sobra da figura analisada é maior que a sobra da posterior
					if ((fig.get(i).getX1() - (fig.get(i).getX2() + fig.get(i).getX3())) >= (x3Future * -1)) {
						finish = "up";
						result += fig.get(i).getX1();
					} else {
						finish = "down";
						result += fig.get(i).getX2();
					}
				} else {
					// Verificar se a sobra da figura analisada é maior que o encaixe da posterior
					if ((fig.get(i).getX2() - fig.get(i).getX1()) >= (x3Future)) {
						finish = "down";
						result += fig.get(i).getX2();
					} else {
						finish = "up";
						result += fig.get(i).getX1();
					}
				}

			} else {

				// Descobrir se a posição da sobra
				if (fig.get(i).getX1() >= fig.get(i).getX2() + fig.get(i).getX3()) {
					// Verificar se sobra da figura analisada é maior que a sobra da posterior
					if ((fig.get(i).getX1() - fig.get(i).getX2()) >= (x3Future * -1)) {
						finish = "up";
						result += fig.get(i).getX1();
					} else {
						finish = "down";
						result += fig.get(i).getX2();
					}
				} else {
					// Verificar se a sobra da figura analisada é maior que o encaixe da posterior
					if ((fig.get(i).getX2() - fig.get(i).getX1()) >= (x3Future)) {
						finish = "down";
						result += fig.get(i).getX2();
					} else {
						finish = "up";
						result += fig.get(i).getX1();
					}
				}
			}
		}

		// Tratando da última figura do vetor
		String sobra = null;

		if (fig.get(fig.size() - 1).getX3() >= 0) {
			sobra = fig.get(fig.size() - 1)
					.getX1() >= (fig.get(fig.size() - 1).getX2() + fig.get(fig.size() - 1).getX3()) ? "up" : "down";

			if (finish == "up") {
				result += fig.get(fig.size() - 1).getX1();
				// somar com sobra
				result += sobra == "down"
						? ((fig.get(fig.size() - 1).getX2() + fig.get(fig.size() - 1).getX3())
								- fig.get(fig.size() - 1).getX1())
						: (0);
			} else {

				result += fig.get(fig.size() - 1).getX2();

				// somar com a sobra
				result += sobra == "up"
						? fig.get(fig.size() - 1).getX1()
								- (fig.get(fig.size() - 1).getX2() + fig.get(fig.size() - 1).getX3())
						: 0;
			}

		} else {
			sobra = fig.get(fig.size() - 1).getX1() >= fig.get(fig.size() - 1).getX2() ? "up" : "down";

			if (finish == "up") {
				result += fig.get(fig.size() - 1).getX1();
				// somar com sobra
				result += sobra == "down" ? (fig.get(fig.size() - 1).getX2() - fig.get(fig.size() - 1).getX1()) : (0);
			} else {
				if (fig.get(fig.size() - 1).getX3() < 0) {
					result += (fig.get(fig.size() - 1).getX3() * -1);
				}
				result += fig.get(fig.size() - 1).getX2();

				// somar com a sobra
				result += sobra == "up" ? fig.get(fig.size() - 1).getX1() - fig.get(fig.size() - 1).getX2() : 0;
			}
		}

		// Pegar area do trapézio
		somaAreasFig += fig.get(fig.size() - 1).getArea();

		area = result * altura;

		return (area - somaAreasFig);
	}

	// ================ Branch and Bound ==================

	public ArrayList<Figura> branchAndBound(boolean heurisitica, int maxFig, ArrayList<Figura> fig) {
		if (heurisitica) {
			System.out.println("Ainda não implementado");
		} else {
			branchAndBoundGuloso(maxFig, fig, new ArrayList<Figura>());
			return result;
		}
		
		return null;

	}

	public void branchAndBoundGuloso(int maxFig, ArrayList<Figura> fig, ArrayList<Figura> figOrderFinal) {

		// Definir dados
		if (figOrderFinal.size() == maxFig) {
			//Cacucular desperidico
			float desperidicoFinal = desperdicio(figOrderFinal);
			
			//Verificar se é menor do que aquilo que ja temos
			if (menorGlobal > desperidicoFinal) {

				menorGlobal = desperidicoFinal;
				resultBd = figOrderFinal;
			}
		} else {

			for (int i = 0; i < fig.size(); i++) {

				// Colocar figura no array final para processamento
				figOrderFinal.add(fig.get(i));
				
				// remover figura adicoionada das opções
				fig.remove(i);
			
				
				if (desperdicio(figOrderFinal) < menorGlobal) {
					branchAndBoundGuloso(maxFig, fig, figOrderFinal);
				}
				
				
				//Adiconar figura removida
				fig.add(figOrderFinal.get(figOrderFinal.size() - 1));
				
				// retirar figura adiconada do array de processamento
				figOrderFinal.remove(figOrderFinal.size() - 1);
			}
			
		}

	}

	// ================ FORÇA BRUTA ==================

	// Método principal: recebe o vetor cujos elementos que serão permutados

	public ArrayList<Figura> menorDesperdicio(ArrayList<Figura> vet) {

		p = new ArrayList<Figura>();
		result = new ArrayList<Figura>();
		menorDesperdicio(vet, 0, Float.MAX_VALUE);
		return result;
	}

	// Método recursivo que implementa as permutações para descobrir o menor
	// desperdício

	private void menorDesperdicio(ArrayList<Figura> vet, int n, float desperdicio) {

		if (n == vet.size()) {
			cont++;
			float desperdicioCourrent = desperdicio(p);
			if (desperdicioCourrent < menorDesperdicio) {
				result = p;
				menorDesperdicio = desperdicioCourrent;
			}

		} else {

			for (int i = 0; i < vet.size(); i++) {

				boolean achou = false;

				for (int j = 0; j < n; j++) {
					if (isEqual(p.get(j), vet.get(i)))
						achou = true;
				}

				if (!achou) {
					if (p.size() > n) {
						p.remove(n);
					}
					p.add(n, vet.get(i));
					menorDesperdicio(vet, n + 1, desperdicio);
				}

			}

		}

	}

}

class Figura {
	// Ler dimensões da figura
	private float x1;
	private float x2;
	private float x3;
	private float altura = 100;

	public Figura(float x1, float x2, float x3) {

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
	public float getX1() {
		return x1;
	}

	/**
	 * @return the x2
	 */
	public float getX2() {
		return x2;
	}

	/**
	 * @return the x3
	 */
	public float getX3() {
		return x3;
	}

	/**
	 * @param x1 the x1 to set
	 */
	public void setX1(float x1) {
		this.x1 = x1;
	}

	/**
	 * @param x2 the x2 to set
	 */
	public void setX2(float x2) {
		this.x2 = x2;
	}

	/**
	 * @param x3 the x3 to set
	 */
	public void setX3(float x3) {
		this.x3 = x3;
	}

	public float[] getValueBases() {
		// Definir dados
		float result[] = new float[2];
		float x3Modified = this.x3 < 0 ? this.x3 * -1 : this.x3;

		if (this.x2 >= 0 && this.x3 <= 0) {
			if (this.x1 >= (this.x2 + x3Modified)) {
				result[0] = this.x1;
				result[1] = this.x2 + x3Modified;
			} else {
				result[0] = this.x2 + x3Modified;
				result[1] = this.x1;
			}
		} else {
			if (this.x3 >= 0) {
				if (this.x1 >= this.x2) {
					result[0] = this.x1;
					result[1] = this.x2;
				} else {
					result[0] = this.x2;
					result[1] = this.x1;
				}
			}
			if (this.x2 <= 0) {
				if (this.x1 >= x3Modified) {
					result[0] = this.x1;
					result[1] = x3Modified;
				} else {
					result[0] = x3Modified;
					result[1] = this.x1;
				}
			}
		}

		return result;
	}

	public float getValueBaseMenor() {

		return (this.x1 >= (this.x2 + this.x3) ? (this.x2 + this.x3) : this.x1);
	}

	public float getArea() {
		// Definir dados
		float basesValue[] = getValueBases();

		float area = ((basesValue[0] + basesValue[1]) * this.altura) / 2;

		return area;
	}

}

public class Main {

	public static int receberFigura(int x, int y, int z) {
		return (0);
	}

	public static boolean iEsqual(Figura fig1, Figura fig2) {
		return (fig1.getX1() == fig2.getX1() && fig1.getX2() == fig2.getX2() && fig1.getX3() == fig2.getX3());
	}

	public static ArrayList<Figura> lerTxt() throws IOException {
		// Definir dados
		BufferedReader buffRead = new BufferedReader(new FileReader("./DataIn.txt"));
		ArrayList<Figura> readFigs = new ArrayList<Figura>();

		// Ler quantidade de figuras
		int qtdFigs = Integer.parseInt(buffRead.readLine());

		// Salvar linhas processadas do txt
		String linha = null;

		int i = 0;

		while (i < qtdFigs) {

			// ler Linha
			linha = buffRead.readLine();

			// Processar dados
			String[] tmp = linha.split(" ");

			readFigs.add(new Figura(Float.parseFloat(tmp[0]), Float.parseFloat(tmp[1]), Float.parseFloat(tmp[2])));

			i++;
		}

		buffRead.close();

		return readFigs;

	}

	public static void main(String[] args) throws IOException {
		// Definir leitor de dados
		int qtdFiguras = 3;
		ArrayList<Figura> fig = new ArrayList<Figura>();

		ArrayList<Figura> forcaBruta;
		ArrayList<Figura> branchBound;
		DesperdicioFig des = new DesperdicioFig();

		// Definir dados de tempo
		long init;
		long end;
		long forcaBrutaTime;
		long branchAndBoundTime;

		// Ler Entrada
		fig = lerTxt();

		// Processar por força bruta
		init = System.currentTimeMillis();
		forcaBruta = des.menorDesperdicio(fig);
		end = System.currentTimeMillis();
		forcaBrutaTime = end - init;

		System.out.println(des.desperdicio(forcaBruta));
		System.out.println("Tempo decorrido: " + forcaBrutaTime);

		// Processar por branch and bound
		
		init = System.currentTimeMillis();
		branchBound = des.branchAndBound(false, fig.size(), fig);
		end = System.currentTimeMillis();
		branchAndBoundTime = end - init;

		System.out.println(des.desperdicio(branchBound));
		System.out.println("Tempo decorrido: " + branchAndBoundTime);
		

	}
}
