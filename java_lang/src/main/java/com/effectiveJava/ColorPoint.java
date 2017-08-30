package com.effectiveJava;

class Point{
	private final int x;
	private final int y;
	public Point(int x, int y){
		this.x = x;
		this.y = y;
	}
	@Override
	public boolean equals(Object o){
		if(!(o instanceof Point))
			return false;
		Point p = (Point)o;
		return p.x == x && p.y == y;
					
	}
}
enum Color{
	RED, GREEN, BLUE
}

public class ColorPoint extends Point{
	private final Color color;
	public ColorPoint(int x, int y, Color color){
		super(x,y);
		this.color = color;
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof ColorPoint))
			return false;
		return super.equals(o) && ((ColorPoint)o).color == color;
	}
	
	public static void main(String[] args){
		ColorPoint cp = new ColorPoint(3,4, Color.RED);
		Point p = new Point(3,4);
		System.out.println(p.equals(cp));
		System.out.println(cp.equals(p));
		
	}

}
