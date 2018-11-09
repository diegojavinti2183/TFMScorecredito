/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cooperativa.coopintranet.servicios;

import com.cooperativa.coopintranet.entidades.Parametros;
import java.math.BigDecimal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Diego
 */
@Stateless
public class ParametrosFacade extends AbstractFacade<Parametros> {
    @PersistenceContext(unitName = "coopIntranetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParametrosFacade() {
        super(Parametros.class);
    }
    
    public BigDecimal valorParametro(String nombre){
        BigDecimal valor = new BigDecimal("0");
        javax.persistence.Query q = getEntityManager().createNamedQuery("Parametros.findByParNombre");        
        q.setParameter("parNombre", nombre);
        if (q.getResultList().size() > 0) {
            valor = new BigDecimal(getEntityManager().createNamedQuery("Parametros.findByParNombre").setParameter("parNombre", nombre).getSingleResult().toString());            
        }
        return valor.divide(new BigDecimal("100"));
    }
}
