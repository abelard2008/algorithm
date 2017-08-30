package com.patterns;

public class NutritionFacts {

	private final int servingSize;
	private final int servings;
	private final int calories;
	
	public static class Builder{
		private final int servingSize;
		private final int servings;
		
		private int calories = 0;
		
		public Builder(int servingSize, int servings){
			this.servingSize = servingSize;
			this.servings = servings;
		}
		
		public Builder calories(int val){
			calories = val; return this;
		}
		
		public NutritionFacts build(){
			return new NutritionFacts(this);
		}
	}
	private NutritionFacts(Builder builder){
		servingSize = builder.servingSize;
		servings = builder.servings;
		calories = builder.calories;
	}
	
	public static void main(String[] args){
		NutritionFacts cocaCola = new NutritionFacts.Builder(240, 8).calories(100).build();
	}
}
