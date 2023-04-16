package lt.vu.common.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Version;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NamedQuery(name = "Student.findAll", query = "select a from Student as a")
@Getter @Setter
public class Student implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 50)
    @Column(name = "NAME")
    private String name;

    private String phoneNumber;

    @Version
    @Column(name = "OPT_LOCK_VERSION")
    private Integer version;

    @ManyToMany(mappedBy = "students")
    private Set<Course> courses = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return getId() != null && Objects.equals(getId(), student.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
