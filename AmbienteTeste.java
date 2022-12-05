import java.util.ArrayList;

class AmbienteTeste{
	public static void main(String args[]) {
		
		ArrayList<String> vetor =  new ArrayList<String>();
		
		Integer.parseInt("3");
		
		
		vetor.remove(0);
		
		vetor.add("a");
		vetor.add("a");
		
		
//		for(int i = 2; i < vetor.length; i++) {
//			vetor[0] = i;
//		}
		
		String teste = vetor.get(0);
		
		System.out.println(vetor);
	}
}