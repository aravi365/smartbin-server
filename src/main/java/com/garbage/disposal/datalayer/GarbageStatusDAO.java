package com.garbage.disposal.datalayer;

import org.springframework.data.repository.CrudRepository;

import com.garbage.disposal.model.GarbageBin;
import com.garbage.disposal.model.GarbageCollector;

public interface GarbageStatusDAO extends CrudRepository<GarbageBin, Integer> {

	@Override
	public GarbageBin findOne(Integer id);

	@Override
	public GarbageBin save(GarbageBin garbageBin);

}
