package lt.vu.jpa.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lt.vu.common.entity.Student;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class StudentDTO {
    private Integer id;
    private String name;
    private Integer favouriteNumber;
    private Integer version;
    private List<CourseDto> courses;

    public StudentDTO(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.favouriteNumber = student.getFavouriteNumber();
        this.version = student.getVersion();
        this.courses = student.getCourses().stream()
                .map(CourseDto::new)
                .collect(Collectors.toList());
    }
}
