package lt.vu.jpa.persistance;

import lt.vu.common.entity.Course;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class CoursesDAO {
    @Inject
    private EntityManager em;

    public List<Course> loadAll() {
        return em.createNamedQuery("Course.findAll", Course.class).getResultList();
    }

    public void persist(Course course){
        this.em.persist(course);
    }

    public Course findOne(Integer id) {
        return em.find(Course.class, id);
    }
}
