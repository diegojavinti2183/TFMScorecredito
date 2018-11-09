/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cooperativa.coopintranet.servicios;

import com.cooperativa.coopintranet.entidades.Personas;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author DiegoJavier
 */
@Stateless
public class PersonasFacade extends AbstractFacade<Personas> {

    @PersistenceContext(unitName = "coopIntranetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonasFacade() {
        super(Personas.class);
    }
    
    public Personas identificacionPersona(String identificacion) {        
        javax.persistence.Query q = getEntityManager().createNamedQuery("Personas.findByPerIdentificacion");
        q.setParameter("identificacion", identificacion);
        if (!q.getResultList().isEmpty()) {
            return (Personas) q.getSingleResult();
        } else {
            return null;
        }       
    }
    
    public Personas codigoSocio(int codigo) {        
        javax.persistence.Query q = getEntityManager().createNamedQuery("Personas.findByPerCodigoSocio");
        q.setParameter("codigo", codigo);
        if (!q.getResultList().isEmpty()) {
            return (Personas) q.getSingleResult();
        } else {
            return null;
        }       
    }
    
    public Personas apellidoPersona(String apellido) {
        Personas person = null;
        javax.persistence.Query q = getEntityManager().createNamedQuery("Personas.findByPerIdentificacion");
        q.setParameter("apellido", apellido);
        int i = q.getResultList().size();
        if (i != 0) {
            return (Personas) q.getSingleResult();
        } else {
            return person;
        }
       
    }
    
}
