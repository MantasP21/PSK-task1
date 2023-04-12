package lt.vu.mybatis.model;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mybatis.dao.CourseMapper;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class CoursesMyBatisModel {
    @Inject
    private CourseMapper courseMapper;

    @Getter
    private List<Course> allCourses;

    @Getter @Setter
    private Course courseToCreate = new Course();

    @PostConstruct
    public void init() {
        this.allCourses = courseMapper.selectAll();
    }

    @Transactional
    public String createCourse() {
        courseMapper.insert(courseToCreate);
        return "/myBatis/courses?faces-redirect=true";
    }
}
