package com.example.salus.entidad;

import java.io.Serializable;

public class MiTurno implements Serializable {
        private int id;
        private boolean pagado;
        private String estado;
        private int turno_disponible;
        private int id_paciente;
        private int id_medico;
        private String obra_social;

        // Getters y Setters
        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public boolean isPagado() {
                return pagado;
        }

        public void setPagado(boolean pagado) {
                this.pagado = pagado;
        }

        public String getEstado() {
                return estado;
        }

        public void setEstado(String estado) {
                this.estado = estado;
        }

        public int getTurno_disponible() {
                return turno_disponible;
        }

        public void setTurno_disponible(int turno_disponible) {
                this.turno_disponible = turno_disponible;
        }

        public int getId_paciente() {
                return id_paciente;
        }

        public void setId_paciente(int id_paciente) {
                this.id_paciente = id_paciente;
        }

        public int getId_medico() {
                return id_medico;
        }

        public void setId_medico(int id_medico) {
                this.id_medico = id_medico;
        }

        public String getObra_social() {
                return obra_social;
        }

        public void setObra_social(String obra_social) {
                this.obra_social = obra_social;
        }

        @Override
        public String toString() {
                return "MiTurno{" +
                        "id=" + id +
                        ", pagado=" + pagado +
                        ", estado='" + estado + '\'' +
                        ", turno_disponible=" + turno_disponible +
                        ", id_paciente=" + id_paciente +
                        ", id_medico=" + id_medico +
                        ", obra_social='" + obra_social + '\'' +
                        '}';
        }
}
