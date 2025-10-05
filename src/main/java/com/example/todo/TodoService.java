package com.example.todo;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Stateless
@Transactional
public class TodoService {
    @PersistenceContext(unitName = "todoPU")
    private EntityManager em;

    public List<Todo> list() {
        return em.createQuery("SELECT t FROM Todo t ORDER BY t.id DESC", Todo.class).getResultList();
    }

    public Todo create(Todo t) {
        em.persist(t);
        return t;
    }

    public Todo toggle(long id) {
        Todo t = em.find(Todo.class, id);
        if (t == null) return null;
        t.setDone(!t.isDone());
        return t;
    }

    public boolean delete(long id) {
        Todo t = em.find(Todo.class, id);
        if (t == null) return false;
        em.remove(t);
        return true;
    }
}
