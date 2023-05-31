package lt.vu.jpa.dto;

import lombok.Data;
import lt.vu.common.entity.Course;

@Data
public class CourseDto {
    private String name;
    private Long universityId;

    public CourseDto(Course course) {
        this.name = course.getName();
        this.universityId = course.getUniversity().getId();
    }
}
