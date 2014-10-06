package com.maxwelldaemon;

public enum GameStep {

	SIMPLE_FIRST(5,0,"Five Oxigens"), SIMPLE_SECOND(7,0,"Seven Oxigens"), SIMPLE_THIRD(9,0,"Nign Oxigens"), SINGLE_FORTH(11,0,"Eleven Oxigens"), 
	FINAL(5,5,"Five Oxigens and Five Nitrogens"), STOP(0,0,"");
	
	private int oxigen;
	private int nitrogen;
	private String stepName;
	
	private GameStep(int oxigen, int nitrogen, String stepName) {
		this.oxigen = oxigen;
		this.nitrogen = nitrogen;
		this.stepName = stepName;
	}
	
	public int getOxigen() {
		return oxigen;
	}

	public int getNitrogen() {
		return nitrogen;
	}	
	
	public String getStepName() {
		return stepName;
	}
}
