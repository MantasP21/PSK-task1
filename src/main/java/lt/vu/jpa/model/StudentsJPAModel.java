package lt.vu.jpa.model;

import lombok.Getter;
import lombok.Setter;
import lt.vu.common.entity.Course;
import lt.vu.common.entity.Student;
import lt.vu.common.interceptor.LoggedInvocation;
import lt.vu.jpa.persistance.StudentsDAO;
import lt.vu.jpa.persistance.CoursesDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Map;

@Model
public class StudentsJPAModel implements Serializable {
    @Inject
    private CoursesDAO coursesDAO;
    @Inject
    private StudentsDAO studentsDAO;

    @Getter @Setter
    private Course course;

    @Getter @Setter
    private Student studentToCreate = new Student();

    @PostConstruct
    public void init() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer courseId = Integer.parseInt(requestParameters.get("courseId"));
        this.course = coursesDAO.findOne(courseId);
    }

    @Transactional
    @LoggedInvocation
    public void createStudent() {
        studentToCreate.setCourse(this.course);
        studentsDAO.persist(studentToCreate);
    }
}
