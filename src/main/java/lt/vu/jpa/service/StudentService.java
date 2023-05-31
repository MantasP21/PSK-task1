package lt.vu.jpa.service;

import lt.vu.common.entity.Student;
import lt.vu.jpa.dto.StudentDTO;
import lt.vu.jpa.persistance.StudentsDAO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class StudentService {
    @Inject
    private StudentsDAO studentsDAO;

    @Transactional
    public void persist(Student student) {
        studentsDAO.persist(student);
    }

    public List<Student> findAll() {
        return studentsDAO.findAll();
    }

    public Student findOne(Integer id) {
        return studentsDAO.findOne(id);
    }

    @Transactional
    public Student update(StudentDTO dto) {
        Student existingStudent = findOne(dto.getId());

        Student student = new Student();
        student.setId(dto.getId());
        student.setName(dto.getName());
        student.setFavouriteNumber(dto.getFavouriteNumber());
        student.setCourses(existingStudent.getCourses());
        student.setVersion(dto.getVersion());
        return studentsDAO.update(student);
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Student forceUpdate(StudentDTO dto) {
        Student student = findOne(dto.getId());
        student.setId(dto.getId());
        student.setName(dto.getName());
        student.setFavouriteNumber(dto.getFavouriteNumber());
        return studentsDAO.update(student);
    }
}
