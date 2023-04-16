package lt.vu.common.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NamedQuery(name = "Course.findAll", query = "select t from Course as t")
@Getter @Setter
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "university_id")
    private University university;

    @ManyToMany
    @JoinTable(name = "Course_students",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "students_id"))
    private Set<Student> students = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return getId() != null && Objects.equals(getId(), course.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
