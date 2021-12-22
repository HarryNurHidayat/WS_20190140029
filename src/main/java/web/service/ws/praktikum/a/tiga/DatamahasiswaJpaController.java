/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package web.service.ws.praktikum.a.tiga;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import web.service.ws.praktikum.a.tiga.exceptions.NonexistentEntityException;
import web.service.ws.praktikum.a.tiga.exceptions.PreexistingEntityException;

/**
 *
 * @author harry
 */
public class DatamahasiswaJpaController implements Serializable {

    public DatamahasiswaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("web.service_ws.praktikum.a.tiga_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public DatamahasiswaJpaController() {
    }
    

    public void create(Datamahasiswa datamahasiswa) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(datamahasiswa);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDatamahasiswa(datamahasiswa.getNim()) != null) {
                throw new PreexistingEntityException("Datamahasiswa " + datamahasiswa + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Datamahasiswa datamahasiswa) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            datamahasiswa = em.merge(datamahasiswa);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = datamahasiswa.getNim();
                if (findDatamahasiswa(id) == null) {
                    throw new NonexistentEntityException("The datamahasiswa with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Datamahasiswa datamahasiswa;
            try {
                datamahasiswa = em.getReference(Datamahasiswa.class, id);
                datamahasiswa.getNim();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The datamahasiswa with id " + id + " no longer exists.", enfe);
            }
            em.remove(datamahasiswa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Datamahasiswa> findDatamahasiswaEntities() {
        return findDatamahasiswaEntities(true, -1, -1);
    }

    public List<Datamahasiswa> findDatamahasiswaEntities(int maxResults, int firstResult) {
        return findDatamahasiswaEntities(false, maxResults, firstResult);
    }

    private List<Datamahasiswa> findDatamahasiswaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Datamahasiswa.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Datamahasiswa findDatamahasiswa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Datamahasiswa.class, id);
        } finally {
            em.close();
        }
    }

    public int getDatamahasiswaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Datamahasiswa> rt = cq.from(Datamahasiswa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
