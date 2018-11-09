/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cooperativa.coopintranet.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author diego
 */
@Entity
@Table(name = "scoringdetalle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Scoringdetalle.findAll", query = "SELECT s FROM Scoringdetalle s")
    , @NamedQuery(name = "Scoringdetalle.findByDscorNumero", query = "SELECT s FROM Scoringdetalle s WHERE s.scoringdetallePK.dscorNumero = :dscorNumero")
    , @NamedQuery(name = "Scoringdetalle.findByDscorScoring", query = "SELECT s FROM Scoringdetalle s WHERE s.scoringdetallePK.dscorScoring = :dscorScoring")
    , @NamedQuery(name = "Scoringdetalle.findByDscorValor", query = "SELECT s FROM Scoringdetalle s WHERE s.dscorValor = :dscorValor")
    , @NamedQuery(name = "Scoringdetalle.findByDscorHomologacion", query = "SELECT s FROM Scoringdetalle s WHERE s.dscorHomologacion = :dscorHomologacion")})
public class Scoringdetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ScoringdetallePK scoringdetallePK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "dscor_valor")
    private BigDecimal dscorValor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dscor_homologacion")
    private BigDecimal dscorHomologacion;
    @JoinColumn(name = "dscor_scoring", referencedColumnName = "scor_numero", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Scoringpersonas scoringpersonas;
    @JoinColumn(name = "dscor_variable", referencedColumnName = "vred_codigo")
    @ManyToOne(optional = false)
    private Variablesred dscorVariable;

    public Scoringdetalle() {
    }

    public Scoringdetalle(ScoringdetallePK scoringdetallePK) {
        this.scoringdetallePK = scoringdetallePK;
    }

    public Scoringdetalle(ScoringdetallePK scoringdetallePK, BigDecimal dscorValor, BigDecimal dscorHomologacion) {
        this.scoringdetallePK = scoringdetallePK;
        this.dscorValor = dscorValor;
        this.dscorHomologacion = dscorHomologacion;
    }

    public Scoringdetalle(int dscorNumero, int dscorScoring) {
        this.scoringdetallePK = new ScoringdetallePK(dscorNumero, dscorScoring);
    }

    public ScoringdetallePK getScoringdetallePK() {
        return scoringdetallePK;
    }

    public void setScoringdetallePK(ScoringdetallePK scoringdetallePK) {
        this.scoringdetallePK = scoringdetallePK;
    }

    public BigDecimal getDscorValor() {
        return dscorValor;
    }

    public void setDscorValor(BigDecimal dscorValor) {
        this.dscorValor = dscorValor;
    }

    public BigDecimal getDscorHomologacion() {
        return dscorHomologacion;
    }

    public void setDscorHomologacion(BigDecimal dscorHomologacion) {
        this.dscorHomologacion = dscorHomologacion;
    }

    public Scoringpersonas getScoringpersonas() {
        return scoringpersonas;
    }

    public void setScoringpersonas(Scoringpersonas scoringpersonas) {
        this.scoringpersonas = scoringpersonas;
    }

    public Variablesred getDscorVariable() {
        return dscorVariable;
    }

    public void setDscorVariable(Variablesred dscorVariable) {
        this.dscorVariable = dscorVariable;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (scoringdetallePK != null ? scoringdetallePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Scoringdetalle)) {
            return false;
        }
        Scoringdetalle other = (Scoringdetalle) object;
        if ((this.scoringdetallePK == null && other.scoringdetallePK != null) || (this.scoringdetallePK != null && !this.scoringdetallePK.equals(other.scoringdetallePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cooperativa.coopintranet.entidades.Scoringdetalle[ scoringdetallePK=" + scoringdetallePK + " ]";
    }
    
}
