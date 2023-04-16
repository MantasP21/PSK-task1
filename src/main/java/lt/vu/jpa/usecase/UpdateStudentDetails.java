package lt.vu.jpa.usecase;


import lombok.Getter;
import lombok.Setter;
import lt.vu.common.entity.Student;
import lt.vu.common.interceptor.LoggedInvocation;
import lt.vu.jpa.persistance.StudentsDAO;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Map;

@ViewScoped
@Named
@Getter @Setter
public class UpdateStudentDetails implements Serializable {
    @Inject
    private StudentsDAO studentsDAO;

    private Student student;
    private Integer currentCourseId;


    @PostConstruct
    private void init() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String courseId = requestParameters.get("courseId");
        if (courseId != null) {
            currentCourseId = Integer.parseInt(courseId);
        }
        Integer studentId = Integer.parseInt(requestParameters.get("studentId"));
        this.student = studentsDAO.findOne(studentId);
    }

    @Transactional
    @LoggedInvocation
    public String updateStudentPhoneNumber() {
        try{
            studentsDAO.update(this.student);
        } catch (OptimisticLockException e) {
            return "/studentDetails.xhtml?faces-redirect=true&courseid=" + currentCourseId + "&studentId=" + student.getId() + "error=optimistic-lock-exception";
        }
        return "students.xhtml?courseId=" + currentCourseId + "&faces-redirect=true";
    }
}
