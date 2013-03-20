package edu.mayo.cts2.framework.plugin.service.umls.profile.entity;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Component;

import edu.mayo.cts2.framework.model.command.Page;
import edu.mayo.cts2.framework.model.command.ResolvedReadContext;
import edu.mayo.cts2.framework.model.core.CodeSystemReference;
import edu.mayo.cts2.framework.model.core.CodeSystemVersionReference;
import edu.mayo.cts2.framework.model.core.EntityReference;
import edu.mayo.cts2.framework.model.core.SortCriteria;
import edu.mayo.cts2.framework.model.core.VersionTagReference;
import edu.mayo.cts2.framework.model.directory.DirectoryResult;
import edu.mayo.cts2.framework.model.entity.EntityDescription;
import edu.mayo.cts2.framework.model.entity.EntityDirectoryEntry;
import edu.mayo.cts2.framework.model.entity.EntityList;
import edu.mayo.cts2.framework.model.entity.EntityListEntry;
import edu.mayo.cts2.framework.model.service.core.EntityNameOrURI;
import edu.mayo.cts2.framework.plugin.service.umls.domain.entity.EntityFactory;
import edu.mayo.cts2.framework.plugin.service.umls.domain.entity.EntityRepository;
import edu.mayo.cts2.framework.plugin.service.umls.profile.AbstractUmlsBaseService;
import edu.mayo.cts2.framework.plugin.service.umls.profile.entity.EntityQueryBuilderFactory.EntityQueryBuilder;
import edu.mayo.cts2.framework.service.profile.entitydescription.EntityDescriptionReadService;
import edu.mayo.cts2.framework.service.profile.entitydescription.name.EntityDescriptionReadId;

@Component
public class UmlsEntityReadService 
	extends AbstractUmlsBaseService 
	implements EntityDescriptionReadService {

	@Resource
	private EntityRepository entityRepository;
	
	@Override
	public EntityDescription read(
			EntityDescriptionReadId identifier,
			ResolvedReadContext readContext) {
		String namespace = identifier.getEntityName().getNamespace();
		String name = identifier.getEntityName().getName();
		
		if (EntityFactory.MTH_NAMESPACE.equals(namespace))
			return this.entityRepository.getEntityByCui(name);
		
		return this.entityRepository.getEntityById(name, namespace);
	}

	@Override
	public boolean exists(EntityDescriptionReadId identifier,
			ResolvedReadContext readContext) {

		return (this.read(identifier, readContext) != null);
	}

	@Override
	public DirectoryResult<EntityListEntry> readEntityDescriptions(
			EntityNameOrURI entityId, SortCriteria sortCriteria,
			ResolvedReadContext readContext, Page page) {
		String field = "entity.descriptions.value";
		String match = entityId.getEntityName().getName();
		QueryBuilder qb = QueryBuilders.fuzzyQuery(field, match);
		return this.entityRepository.getEntityListEntriesByKeyword(qb, page.getStart(), page.getEnd());
	}

	@Override
	public EntityReference availableDescriptions(EntityNameOrURI entityId,
			ResolvedReadContext readContext) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntityList readEntityDescriptions(EntityNameOrURI entityId,
			ResolvedReadContext readContext) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CodeSystemReference> getKnownCodeSystems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CodeSystemVersionReference> getKnownCodeSystemVersions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VersionTagReference> getSupportedVersionTags() {
		// TODO Auto-generated method stub
		return null;
	}

}