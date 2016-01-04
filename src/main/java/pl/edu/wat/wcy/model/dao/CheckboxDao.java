package pl.edu.wat.wcy.model.dao;

import pl.edu.wat.wcy.model.entities.Checkbox;
import pl.edu.wat.wcy.model.entities.State;

import java.util.List;

public class CheckboxDao extends GenericDaoImpl<Checkbox> {

    public CheckboxDao() {
        super(Checkbox.class);
    }

    public List<Checkbox> findAllCheckBoxesBasedOnState(State state) {
        return em.createQuery("from Checkbox c where c.state = :t").setParameter("t",state).getResultList();
    }

}
