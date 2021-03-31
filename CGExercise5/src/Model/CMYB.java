package Model;

public class CMYB {
	
	private double cyan;
	private double magenta;
	private double yellow;
	private double black;
		
	
	public CMYB(double cyan2, double magenta2, double yellow2, double black2) {
		super();
		this.cyan = cyan2;
		this.magenta = magenta2;
		this.yellow = yellow2;
		this.black = black2;
	}
	
	public double getCyan() {
		return cyan;
	}
	public void setCyan(float cyan) {
		this.cyan = cyan;
	}
	public double getMagenta() {
		return magenta;
	}
	public void setMagenta(float magenta) {
		this.magenta = magenta;
	}
	public double getYellow() {
		return yellow;
	}
	public void setYellow(float yellow) {
		this.yellow = yellow;
	}
	public double getBlack() {
		return black;
	}
	public void setBlack(float black) {
		this.black = black;
	}
	
}
