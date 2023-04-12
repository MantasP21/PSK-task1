package lt.vu.jpa.rest;

import lt.vu.common.dto.StudentDto;
import lt.vu.common.entity.Student;
import lt.vu.jpa.persistance.StudentsDAO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/students")
public class StudentsController {
    @Inject
    private StudentsDAO studentsDAO;

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") final Integer id) {
        Student student = studentsDAO.findOne(id);
        if (student == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        StudentDto studentDto = new StudentDto();
        studentDto.setName(student.getName());
        studentDto.setPhoneNumber(student.getPhoneNumber());
        studentDto.setCourseName(student.getCourse().getName());
        return Response.ok(studentDto).build();
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response update(@PathParam("id") Integer courseId, StudentDto studentData) {
        try {
            Student existingStudent = studentsDAO.findOne(courseId);
            if (existingStudent == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            existingStudent.setName(studentData.getName());
            existingStudent.setPhoneNumber(studentData.getPhoneNumber());
            studentsDAO.update(existingStudent);
            return Response.ok().build();
        } catch (OptimisticLockException ole) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }
}
