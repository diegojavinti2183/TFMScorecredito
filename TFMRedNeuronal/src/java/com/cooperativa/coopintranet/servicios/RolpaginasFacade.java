/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cooperativa.coopintranet.servicios;

import com.cooperativa.coopintranet.entidades.Roles;
import com.cooperativa.coopintranet.entidades.Rolpaginas;
import com.cooperativa.coopintranet.entidades.Usuarios;
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
public class RolpaginasFacade extends AbstractFacade<Rolpaginas> {
    @PersistenceContext(unitName = "coopIntranetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RolpaginasFacade() {
        super(Rolpaginas.class);
    }
    
    public List<Rolpaginas> paginasRol(Roles cadena) {
        javax.persistence.Query q;        
        if(cadena != null){             
            q = getEntityManager().createNamedQuery("Rolpaginas.findByRolpRoles");
            q.setParameter("rolpRol", cadena);
        }else{
            q = getEntityManager().createQuery("SELECT r FROM Rolpaginas r ");
        }       
        return q.getResultList();
    }  
    
    public boolean permisosPaginas(String cadena,Usuarios usuario) {
        boolean bandera = false; 
        javax.persistence.Query q;
        q = getEntityManager().createQuery("SELECT r FROM Rolpaginas r where r.rolpRol = :rolpRol and r.rolpPagina.pagRuta = :pagRuta");
        q.setParameter("rolpRol", usuario.getUsuRol());
        q.setParameter("pagRuta", cadena);
        int i = q.getResultList().size();
        if (i > 0){
            bandera = true;
        }
        return bandera;
    }  
    
}
