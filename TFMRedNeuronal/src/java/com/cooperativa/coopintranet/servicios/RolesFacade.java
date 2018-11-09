/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cooperativa.coopintranet.servicios;

import com.cooperativa.coopintranet.entidades.Roles;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author usuario
 */
@Stateless
public class RolesFacade extends AbstractFacade<Roles> {
    @PersistenceContext(unitName = "coopIntranetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RolesFacade() {
        super(Roles.class);
    }
    
    public Roles buscarPorRolNombre(String cadena) { 
        javax.persistence.Query q = getEntityManager().createQuery("SELECT r FROM Roles r where r.rolNombre='"+cadena+"'");
        return (Roles)q.getSingleResult();

    }
    
}
