package trayectos;

public class TramoAPie extends TramoTrayecto{
	private double numKilometros;
	private Ritmo ritmo;
	public TramoAPie (String origen, String destino, double numKilometros) {
		super(origen, destino);
		this.ritmo = Ritmo.MODERADO;
		this.numKilometros = numKilometros;
	}
	
	public TramoAPie (String origen, String destino, double numKilometros,Ritmo ritmo ) {
		super(origen, destino);
		this.ritmo = ritmo;
		this.numKilometros = numKilometros;
	}
	public double getNumKilometros() {
		return numKilometros;
	}
	public Ritmo getRitmo() {
		return ritmo;
	}
	@Override
    public double tiempo() {
        return this.numKilometros * this.ritmo.getMinutosPorKm();
    }
	@Override
	public String toString() {
		return "A pie " + super.toString()  + " (ritmo " +  this.ritmo + ")";
	}
}
