package consulting.gigs.model;

import java.io.Serializable;

public class Visit implements Serializable {
    private int eve_id;
    private String eve_ubicacion;
    private String eve_fecha_sist;
    private String eve_hora_sist;
    private String eve_observaciones;
    private String tipo_evento_id;
    private String tipo_evento_descripcion;
    private String vis_id;
    private String vis_nombres;
    private String vis_apellidos;
    private String vis_identificacion;
    private String tipo_iden;
    private String tipo_iden_descripcion;
    private String vis_vis_empresa_representa;
    private String vis_piso;
    private String vis_oficina_aparta;
    private String vis_torre;
    private  String vis_motivo_visita;
    private String vis_cant_personas;
    private String vis_autoriza;

    public int getEve_id() {
        return eve_id;
    }

    public void setEve_id(int eve_id) {
        this.eve_id = eve_id;
    }

    public String getEve_ubicacion() {
        return eve_ubicacion;
    }

    public void setEve_ubicacion(String eve_ubicacion) {
        this.eve_ubicacion = eve_ubicacion;
    }

    public String getEve_fecha_sist() {
        return eve_fecha_sist;
    }

    public void setEve_fecha_sist(String eve_fecha_sist) {
        this.eve_fecha_sist = eve_fecha_sist;
    }

    public String getEve_hora_sist() {
        return eve_hora_sist;
    }

    public void setEve_hora_sist(String eve_hora_sist) {
        this.eve_hora_sist = eve_hora_sist;
    }

    public String getEve_observaciones() {
        return eve_observaciones;
    }

    public void setEve_observaciones(String eve_observaciones) {
        this.eve_observaciones = eve_observaciones;
    }

    public String getTipo_evento_descripcion() {
        return tipo_evento_descripcion;
    }

    public void setTipo_evento_descripcion(String tipo_evento_descripcion) {
        this.tipo_evento_descripcion = tipo_evento_descripcion;
    }

    public String getVis_nombres() {
        return vis_nombres;
    }

    public void setVis_nombres(String vis_nombres) {
        this.vis_nombres = vis_nombres;
    }

    public String getVis_apellidos() {
        return vis_apellidos;
    }

    public void setVis_apellidos(String vis_apellidos) {
        this.vis_apellidos = vis_apellidos;
    }

    public String getVis_oficina_aparta() {
        return vis_oficina_aparta;
    }

    public void setVis_oficina_aparta(String vis_oficina_aparta) {
        this.vis_oficina_aparta = vis_oficina_aparta;
    }

    public String getVis_cant_personas() {
        return vis_cant_personas;
    }

    public void setVis_cant_personas(String vis_cant_personas) {
        this.vis_cant_personas = vis_cant_personas;
    }

    public String getTipo_evento_id() {
        return tipo_evento_id;
    }

    public void setTipo_evento_id(String tipo_evento_id) {
        this.tipo_evento_id = tipo_evento_id;
    }

    public String getVis_id() {
        return vis_id;
    }

    public void setVis_id(String vis_id) {
        this.vis_id = vis_id;
    }

    public String getVis_identificacion() {
        return vis_identificacion;
    }

    public void setVis_identificacion(String vis_identificacion) {
        this.vis_identificacion = vis_identificacion;
    }

    public String getTipo_iden() {
        return tipo_iden;
    }

    public void setTipo_iden(String tipo_iden) {
        this.tipo_iden = tipo_iden;
    }

    public String getTipo_iden_descripcion() {
        return tipo_iden_descripcion;
    }

    public void setTipo_iden_descripcion(String tipo_iden_descripcion) {
        this.tipo_iden_descripcion = tipo_iden_descripcion;
    }

    public String getVis_vis_empresa_representa() {
        return vis_vis_empresa_representa;
    }

    public void setVis_vis_empresa_representa(String vis_vis_empresa_representa) {
        this.vis_vis_empresa_representa = vis_vis_empresa_representa;
    }

    public String getVis_piso() {
        return vis_piso;
    }

    public void setVis_piso(String vis_piso) {
        this.vis_piso = vis_piso;
    }

    public String getVis_torre() {
        return vis_torre;
    }

    public void setVis_torre(String vis_torre) {
        this.vis_torre = vis_torre;
    }

    public String getVis_motivo_visita() {
        return vis_motivo_visita;
    }

    public void setVis_motivo_visita(String vis_motivo_visita) {
        this.vis_motivo_visita = vis_motivo_visita;
    }

    public String getVis_autoriza() {
        return vis_autoriza;
    }

    public void setVis_autoriza(String vis_autoriza) {
        this.vis_autoriza = vis_autoriza;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "tipo_evento_descripcion='" + tipo_evento_descripcion + '\'' +
                ", vis_nombres='" + vis_nombres + '\'' +
                ", vis_apellidos='" + vis_apellidos + '\'' +
                ", vis_identificacion='" + vis_identificacion + '\'' +
                ", tipo_iden_descripcion='" + tipo_iden_descripcion + '\'' +
                ", vis_motivo_visita='" + vis_motivo_visita + '\'' +
                '}';
    }
}
