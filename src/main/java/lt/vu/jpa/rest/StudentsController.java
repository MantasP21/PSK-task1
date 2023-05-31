package lt.vu.jpa.rest;

import lt.vu.common.entity.Student;
import lt.vu.jpa.dto.StudentDTO;
import lt.vu.jpa.service.StudentService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Path("/students")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StudentsController {
    @Inject
    private StudentService studentService;

    @GET
    public Response getAll() {
        List<Student> students = studentService.findAll();
        List<StudentDTO> dtos = students.stream()
                .map(StudentDTO::new)
                .collect(Collectors.toList());

        return Response.ok(dtos).build();
    }

    @Path("/{id}")
    @GET
    public Response getById(@PathParam("id") final Integer id) {
        Student student = studentService.findOne(id);
        if (student == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(new StudentDTO(student)).build();
    }

    @POST
    public Response create(StudentDTO dto) {
        Student student = new Student();
        student.setName(dto.getName());
        student.setFavouriteNumber(dto.getFavouriteNumber());
        studentService.persist(student);
        return Response.ok(new StudentDTO(student)).build();
    }

    @Path("/{id}")
    @PUT
    public Response update(@PathParam("id") final Integer studentId, StudentDTO dto, @QueryParam("force") final boolean force) {
        Student existingStudent = studentService.findOne(studentId);
        if (existingStudent == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        try {
            StudentDTO result = new StudentDTO(studentService.update(dto));
            return Response.ok(result).build();
        } catch (OptimisticLockException ole) {
            if (force) {
                Student result = studentService.forceUpdate(dto);
                return Response.ok(result).build();
            } else {
                return Response.status(Response.Status.CONFLICT).build();
            }
        }
    }

}
