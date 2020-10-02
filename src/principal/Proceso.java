package principal;

public class Proceso {
	private char idProceso;
	private int tiempo;
	private double tiempoRetorno;
	private int tiempoLlegada;
	
	public Proceso(char id, int time, int timeLlegada) {
        setID(id);
        setTiempo(time);
        setTiempoLlegada(timeLlegada);
    }

    private void setID(char id) {
		idProceso=id;		
	}

	public void setTiempo(int time) {
		if (time>=0) {
			tiempo=time;
		}		
	}

	public char getID() {
        return idProceso;
    }

    public int getTiempo() {
        return tiempo;
    }

	public double getTiempoRetorno() {
		return tiempoRetorno;
	}

	public void setTiempoRetorno(double tiempoRetorno) {
		this.tiempoRetorno = tiempoRetorno - tiempoLlegada;
	}

	public int getTiempoLlegada() {
		return tiempoLlegada;
	}

	private void setTiempoLlegada(int tiempoLlegada) {
		this.tiempoLlegada = tiempoLlegada;
	}
	

}
