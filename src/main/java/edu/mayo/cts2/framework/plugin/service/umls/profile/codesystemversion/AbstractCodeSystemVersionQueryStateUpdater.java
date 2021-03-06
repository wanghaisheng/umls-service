package edu.mayo.cts2.framework.plugin.service.umls.profile.codesystemversion;

import edu.mayo.cts2.framework.filter.match.StateAdjustingPropertyReference.StateUpdater;
import edu.mayo.cts2.framework.model.core.MatchAlgorithmReference;
import edu.mayo.cts2.framework.plugin.service.umls.mapper.CodeSystemMapper;
import edu.mayo.cts2.framework.plugin.service.umls.mapper.CodeSystemVersionMapper;
import edu.mayo.cts2.framework.service.meta.StandardMatchAlgorithmReference;

public abstract class AbstractCodeSystemVersionQueryStateUpdater implements StateUpdater<CodeSystemVersionMapper.SearchObject>{

    private static final String WILDCARD = "%";

	@Override
	public CodeSystemVersionMapper.SearchObject updateState(CodeSystemVersionMapper.SearchObject currentState,
			MatchAlgorithmReference matchAlgorithm, 
			String queryString) {
		if (matchAlgorithm.equals(StandardMatchAlgorithmReference.CONTAINS.getMatchAlgorithmReference())) {
            return this.doSet(currentState, WILDCARD + queryString + WILDCARD);
		}
        if (matchAlgorithm.equals(StandardMatchAlgorithmReference.EXACT_MATCH.getMatchAlgorithmReference())) {
            return this.doSet(currentState, queryString);
		}
		if (matchAlgorithm.equals(StandardMatchAlgorithmReference.STARTS_WITH.getMatchAlgorithmReference())) {
            return this.doSet(currentState, queryString + WILDCARD);
		}

        throw new IllegalStateException();
    }

    protected abstract CodeSystemVersionMapper.SearchObject doSet(CodeSystemVersionMapper.SearchObject currentState, String query);

    protected static class AbbreviationStateUpdater extends AbstractCodeSystemVersionQueryStateUpdater {

        @Override
        protected CodeSystemVersionMapper.SearchObject doSet(CodeSystemVersionMapper.SearchObject currentState, String query) {
            if(currentState == null){
                currentState = new CodeSystemVersionMapper.SearchObject();
            }
            currentState.setAbbreviation(query);
            return currentState;
        }
    }

}
