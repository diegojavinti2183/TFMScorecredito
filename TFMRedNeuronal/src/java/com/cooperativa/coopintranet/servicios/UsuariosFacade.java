/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cooperativa.coopintranet.servicios;

import com.cooperativa.coopintranet.entidades.Usuarios;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Diego
 */
@Stateless
public class UsuariosFacade extends AbstractFacade<Usuarios> {
    @PersistenceContext(unitName = "coopIntranetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuariosFacade() {
        super(Usuarios.class);
    }
    
    public Integer usuarioRoles(int codigo) {
        Integer numero = 0;
        javax.persistence.Query q = getEntityManager().createNamedQuery("Usuarios.findByUsuRoles");
        q.setParameter("rolCodigo", codigo);
        if(q.getResultList().size() > 0){
            numero = Integer.parseInt(q.getSingleResult().toString());
        }
        return numero;
    }
    
    public List<Usuarios> usuariosRoles(int codigo) {
        javax.persistence.Query q = getEntityManager().createNamedQuery("Usuarios.findByUsuariosRol");
        q.setParameter("rolCodigo", codigo);     
        return q.getResultList();
    }
    
    public List<Usuarios> usuariosRoles(List<Integer> lista) {//Extrae los roles que contempla la lista
        javax.persistence.Query q = getEntityManager().createNamedQuery("Usuarios.findByUsuariosRolLista");
        q.setParameter("lista", lista);     
        return q.getResultList();
    }
    
}
