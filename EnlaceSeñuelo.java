package redSocial;

public class EnlaceSeñuelo extends Enlace{

    int factCostExtra;
    int probRetorno;

    public EnlaceSeñuelo(Usuario uOrigen, Usuario uDestino, int coste, int probRetorno) {

        super(uOrigen, uDestino, coste);
        this.probRetorno = probRetorno;
    }

    public EnlaceSeñuelo(Usuario uOrigen, Usuario uDestino, int probRetorno) {
        super(uOrigen, uDestino);
        this.probRetorno = probRetorno;
    }

    public int getFactCostExtra() {
        return factCostExtra;
    }

    public int getProbRetorno() {
        return probRetorno;
    }

    @Override
    public int costeEspecial() {
        return  this.getCoste() * this.factCostExtra;
    }

    @Override
    public int costeReal() {
        return this.getCoste() + costeEspecial();
    }
}
