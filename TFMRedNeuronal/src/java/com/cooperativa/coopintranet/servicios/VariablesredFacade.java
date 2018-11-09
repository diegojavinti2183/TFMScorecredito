/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cooperativa.coopintranet.servicios;

import com.cooperativa.coopintranet.entidades.Variablesred;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author diego
 */
@Stateless
public class VariablesredFacade extends AbstractFacade<Variablesred> {

    @PersistenceContext(unitName = "coopIntranetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VariablesredFacade() {
        super(Variablesred.class);
    }
    
     public List<Variablesred> activosVariablesred() {
        javax.persistence.Query q = getEntityManager().createNamedQuery("Variablesred.findByVredEstado");
        q.setParameter("vredEstado", true);        
        if (!q.getResultList().isEmpty()) {
            return (List<Variablesred>) q.getResultList();
        } else {   
            return null;
        }
    }
     
    
}
