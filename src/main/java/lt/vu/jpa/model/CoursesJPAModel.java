package lt.vu.jpa.model;

import lombok.Getter;
import lombok.Setter;
import lt.vu.common.entity.Course;
import lt.vu.jpa.persistance.CoursesDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class CoursesJPAModel {
    @Inject
    private CoursesDAO coursesDAO;

    @Getter
    private List<Course> allCourses;

    @Getter @Setter
    private Course courseToCreate = new Course();

    @PostConstruct
    public void init(){
        this.allCourses = coursesDAO.loadAll();
    }

    @Transactional
    public void createCourse(){
        this.coursesDAO.persist(courseToCreate);
    }

}
