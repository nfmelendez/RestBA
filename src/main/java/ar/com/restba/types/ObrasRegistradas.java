package ar.com.restba.types;

import java.util.Date;

public class ObrasRegistradas {

	private String nExpediente;
	private String direccion;
	private String smp;
	private String estadoTramite;
	private String fechaEstado;
	private String tipoObra;
	private String nombreProfesional;

	public ObrasRegistradas() {
	}

	public String getnExpediente() {
		return nExpediente;
	}

	public void setnExpediente(String nExpediente) {
		this.nExpediente = nExpediente;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getSmp() {
		return smp;
	}

	public void setSmp(String smp) {
		this.smp = smp;
	}

	public String getEstadoTramite() {
		return estadoTramite;
	}

	public void setEstadoTramite(String estadoTramite) {
		this.estadoTramite = estadoTramite;
	}

	public String getFechaEstado() {
		return fechaEstado;
	}

	public void setFechaEstado(String fechaEstado) {
		this.fechaEstado = fechaEstado;
	}

	public String getTipoObra() {
		return tipoObra;
	}

	public void setTipoObra(String tipoObra) {
		this.tipoObra = tipoObra;
	}

	public String getNombreProfesional() {
		return nombreProfesional;
	}

	public void setNombreProfesional(String nombreProfesional) {
		this.nombreProfesional = nombreProfesional;
	}

}
