/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cooperativa.coopintranet.servicios;

import com.cooperativa.coopintranet.entidades.Usuarios;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author BA01000625
 */
@Stateless
public class LoginUsuariosFacade extends AbstractFacade<Usuarios> {

    @PersistenceContext(unitName = "coopIntranetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LoginUsuariosFacade() {
        super(Usuarios.class);
    }

    public Usuarios UserLogin(String usuario, String password) {
        Usuarios user = new Usuarios();
        javax.persistence.Query q = getEntityManager().createNamedQuery("Usuarios.findByUsuarioLogin");
        q.setParameter("usuUsuario", usuario);
        q.setParameter("usuPassword", password);        
        if (!q.getResultList().isEmpty()) {
            return (Usuarios) q.getSingleResult();
        } else {
            return user;
        }
    }
}
