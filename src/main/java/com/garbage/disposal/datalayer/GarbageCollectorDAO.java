package com.garbage.disposal.datalayer;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.garbage.disposal.model.GarbageBin;
import com.garbage.disposal.model.GarbageCollector;

@Transactional
@Repository
public interface GarbageCollectorDAO extends JpaRepository<GarbageCollector, Integer> {

	public GarbageCollector findOne(Integer id);

	//Persisting the registered user and corresponding bins
	@SuppressWarnings("unchecked")
	public GarbageCollector save(GarbageCollector garbageCollector);

	
	public GarbageCollector findByEmail(String email);
	
	//Verifying the credentials
	public GarbageCollector findByEmailAndGarbageSecurityPassword(String email,String password);
	
}