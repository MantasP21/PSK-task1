package lt.vu.jpa.model;

import lombok.Getter;
import lombok.Setter;
import lt.vu.common.entity.Course;
import lt.vu.common.entity.Student;
import lt.vu.common.interceptor.LoggedInvocation;
import lt.vu.jpa.persistance.CoursesDAO;
import lt.vu.jpa.persistance.StudentsDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Model
public class StudentJPAModel implements Serializable {
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
        String studentId = requestParameters.get("existing-student-form:select");
        if (studentId != null) {
            studentToCreate = studentsDAO.findOne(Integer.parseInt(studentId));
        }
        this.course = coursesDAO.findOne(courseId);
    }

    public Set<Student> getAvailableStudents() {
        Set<Student> students = course.getStudents();
        return course.getUniversity().getCourses().stream()
                .filter(c -> !Objects.equals(c.getId(), course.getId()))
                .flatMap(c -> c.getStudents().stream())
                .filter(student -> !students.contains(student))
                .collect(Collectors.toSet());
    }

    @Transactional
    @LoggedInvocation
    public void registerStudent() {
        course.getStudents().add(studentToCreate);
        studentToCreate.getCourses().add(this.course);

        coursesDAO.persist(course);
        studentsDAO.persist(studentToCreate);
    }
}
