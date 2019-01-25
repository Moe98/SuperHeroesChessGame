package model.pieces;

import model.pieces.heroes.ActivatablePowerHero;
import model.pieces.heroes.Tech;

public class mainMethod {
	private int x;
	private int y;
	public mainMethod() {
		
	}
	public mainMethod(int x,int y){
		this.x=x;
		this.y=y;
	}
	public static void main(String[] args) {
		ActivatablePowerHero x=new Tech(null, null, null);
		if(x instanceof ActivatablePowerHero)
			System.out.println("aph");
		if(x instanceof Tech)
			System.out.println(x);
	}
}
