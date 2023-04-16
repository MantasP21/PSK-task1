package lt.vu.jpa.model;

import lombok.Getter;
import lombok.Setter;
import lt.vu.common.entity.University;
import lt.vu.jpa.persistance.UniversitiesDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class UniversitiesJPAModel {
    @Inject
    private UniversitiesDAO universitiesDAO;

    @Getter
    private List<University> allUniversities;

    @Getter
    @Setter
    private University universityToCreate = new University();

    @PostConstruct
    public void init(){
        this.allUniversities = universitiesDAO.loadAll();
    }

    @Transactional
    public void createUniversity() {
        universitiesDAO.persist(universityToCreate);
    }
}
