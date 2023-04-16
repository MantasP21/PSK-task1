package lt.vu.jpa.model;

import lombok.Getter;
import lombok.Setter;
import lt.vu.common.entity.Course;
import lt.vu.common.entity.University;
import lt.vu.jpa.dto.CourseDto;
import lt.vu.jpa.persistance.CoursesDAO;
import lt.vu.jpa.persistance.UniversitiesDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class CoursesJPAModel {
    @Inject
    private CoursesDAO coursesDAO;
    @Inject
    private UniversitiesDAO universitiesDAO;

    @Getter
    private List<Course> allCourses;

    @Getter @Setter
    private CourseDto courseToCreate = new CourseDto();

    @PostConstruct
    public void init(){
        this.allCourses = coursesDAO.loadAll();
    }

    @Transactional
    public void createCourse() {
        Course course = new Course();
        University university = universitiesDAO.findOne(courseToCreate.getUniversityId());
        course.setUniversity(university);
        course.setName(courseToCreate.getName());
        coursesDAO.persist(course);
    }

}
