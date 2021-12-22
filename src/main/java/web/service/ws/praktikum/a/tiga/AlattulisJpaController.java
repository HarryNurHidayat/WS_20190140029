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
public class AlattulisJpaController implements Serializable {

    public AlattulisJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("web.service_ws.praktikum.a.tiga_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public AlattulisJpaController() {
    }

    public void create(Alattulis alattulis) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(alattulis);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAlattulis(alattulis.getId()) != null) {
                throw new PreexistingEntityException("Alattulis " + alattulis + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Alattulis alattulis) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            alattulis = em.merge(alattulis);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = alattulis.getId();
                if (findAlattulis(id) == null) {
                    throw new NonexistentEntityException("The alattulis with id " + id + " no longer exists.");
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
            Alattulis alattulis;
            try {
                alattulis = em.getReference(Alattulis.class, id);
                alattulis.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The alattulis with id " + id + " no longer exists.", enfe);
            }
            em.remove(alattulis);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Alattulis> findAlattulisEntities() {
        return findAlattulisEntities(true, -1, -1);
    }

    public List<Alattulis> findAlattulisEntities(int maxResults, int firstResult) {
        return findAlattulisEntities(false, maxResults, firstResult);
    }

    private List<Alattulis> findAlattulisEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Alattulis.class));
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

    public Alattulis findAlattulis(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Alattulis.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlattulisCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Alattulis> rt = cq.from(Alattulis.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
