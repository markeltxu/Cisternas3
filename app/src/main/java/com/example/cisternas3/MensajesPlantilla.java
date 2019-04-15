package com.example.cisternas3;

public class MensajesPlantilla {

    String usuarioOrigen;
    String mensaje;
    String fechaHora;

    public MensajesPlantilla(String usuarioOrigen, String mensaje, String fechaHora) {
        this.usuarioOrigen = usuarioOrigen;
        this.mensaje = mensaje;
        this.fechaHora = fechaHora;
    }

    public String getUsuarioOrigen() {
        return usuarioOrigen;
    }

    public String getMensaje() {
        return mensaje;
    }



    public void setUsuarioOrigen(String usuarioOrigen) {
        this.usuarioOrigen = usuarioOrigen;
    }

    public void setMansaje(String mensaje) {
        this.mensaje = mensaje;
    }



    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }
}
