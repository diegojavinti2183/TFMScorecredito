/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cooperativa.coopintranet.servicios;

import com.cooperativa.coopintranet.entidades.Redneuronal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author diego
 */
@Stateless
public class RedneuronalFacade extends AbstractFacade<Redneuronal> {

    @PersistenceContext(unitName = "coopIntranetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RedneuronalFacade() {
        super(Redneuronal.class);
    }
    
    public void actualizaEstado(){
        javax.persistence.Query q = getEntityManager().createNamedQuery("Redneuronal.updateRedEstado");
        q.setParameter("redEstado", true); 
        q.executeUpdate();
    }
    
    public Redneuronal cargar(){
        javax.persistence.Query q = getEntityManager().createNamedQuery("Redneuronal.findByRedEstado");
        Redneuronal red=null;
        q.setParameter("redEstado", true);
        if(!q.getResultList().isEmpty()){
            red = (Redneuronal) q.getSingleResult();
        }
        return red;
    }
    
}
