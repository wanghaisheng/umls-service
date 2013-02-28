/*
* Copyright: (c) 2004-2013 Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Except as contained in the copyright notice above, or as used to identify
* MFMER as the author of this software, the trade names, trademarks, service
* marks, or product names of the copyright holder shall not be used in
* advertising, promotion or otherwise in connection with this software without
* prior written authorization of the copyright holder.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package edu.mayo.cts2.framework.plugin.service.umls.domain.entity;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.mayo.cts2.framework.model.directory.DirectoryResult;
import edu.mayo.cts2.framework.model.entity.EntityDirectoryEntry;
import edu.mayo.cts2.framework.plugin.service.umls.index.ElasticSearchDao;
import edu.mayo.cts2.framework.plugin.service.umls.index.IndexedEntity;

/**
 *
 * @author <a href="mailto:kevin.peterson@mayo.edu">Kevin Peterson</a>
 */
@Component
public class EntityRepository {
	
	@Resource
	private ElasticSearchDao elasticSearchDao;
	
	@Resource
	private EntityFactory entityFactory;
	
	private ObjectMapper jsonMapper = new ObjectMapper();
	
	public DirectoryResult<EntityDirectoryEntry> getEntityDirectoryEntriesByKeyword(QueryBuilder queryBuilder, int start, int end){
		SearchHits hits = elasticSearchDao.search(queryBuilder, start, end);
		
		List<EntityDirectoryEntry> list = new ArrayList<EntityDirectoryEntry>();
				
		for(SearchHit hit : hits.getHits()){
			try {
				IndexedEntity entity = 
					this.jsonMapper.readValue(hit.getSourceAsString(), IndexedEntity.class);
				
				entity.setScore(this.floatToDouble(hit.getScore()));
						
				list.add(entityFactory.createEntityDirectoryEntry(entity));
			} catch (Exception e) {
				throw new RuntimeException(e);
			} 
		}
		
		return new DirectoryResult<EntityDirectoryEntry>(list, true);
	}
	
	private double floatToDouble(float f){
		return (double)f;
	}
}
