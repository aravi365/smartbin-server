package com.garbage.disposal.datalayer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.garbage.disposal.model.BinRecords;

public interface BinRecordsDAO  extends JpaRepository<BinRecords, Integer>{
	
	
	public BinRecords findTopByLocationOrderByCleanedDateDesc(String location);

	public List<BinRecords> findTop2ByLocationOrderByCleanedDateDesc(String location);

}
