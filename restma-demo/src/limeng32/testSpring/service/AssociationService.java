package limeng32.testSpring.service;

import java.util.List;

import limeng32.testSpring.mapper.AssociationMapper;
import limeng32.testSpring.pojo.Association;
import limeng32.testSpring.pojo.Writer;

import org.springframework.beans.factory.annotation.Autowired;

public class AssociationService extends ServiceSupport<Association> implements
		AssociationMapper {

	@Autowired
	private AssociationMapper mapper;

	@Autowired
	private WriterService writerService;

	@Override
	public Association select(int id) {
		return supportSelect(mapper, id);
	}

	@Override
	public List<Association> selectAll(Association t) {
		return supportSelectAll(mapper, t);
	}

	@Override
	public void insert(Association t) {
		supportInsert(mapper, t);
	}

	@Override
	public void update(Association t) {
		supportUpdate(mapper, t);
	}

	@Override
	public void updatePersistent(Association t) {
		supportUpdatePersistent(mapper, t);
	}

	@Override
	public void retrieve(Association t) {
		supportRetrieve(mapper, t);
	}

	@Override
	public void retrieveOnlyNull(Association t) {
		supportRetrieveOnlyNull(mapper, t);
	}

	@Override
	public void delete(Association t) {
		supportDelete(mapper, t);
	}

	@Override
	public int count(Association t) {
		return supportCount(mapper, t);
	}

	@Override
	public void loadWriter(Association association, Writer writer) {
		writer.setAssociation(association);
		association.setWriter(writerService.selectAll(writer));
	}

}
